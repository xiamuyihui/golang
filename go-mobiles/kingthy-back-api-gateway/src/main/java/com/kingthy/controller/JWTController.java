package com.kingthy.controller;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingthy.dto.UserDTO;
import com.kingthy.entity.Member;
import com.kingthy.exception.ResultStatusCode;
import com.kingthy.response.WebApiResponse;
import com.kingthy.security.AccessToken;
import com.kingthy.security.Audience;
import com.kingthy.service.MemberService;
import com.kingthy.util.JwtUtil;
import com.kingthy.util.MD5Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * User:pany <br>
 * Date:2016-11-3 <br>
 * Time:16:47 <br>
 * 
 */
@RestController
@Api(value = "/", description = "有关Java Web Token的操作")
public class JWTController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTController.class);
    
    private final static String LOGIN_NAME = "LOGIN:NAME";
    
    private final static String LOGIN_MOBILE = "LOGIN:MOBILE";
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private Audience audience;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @RequestMapping(value = "api/oauth/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取access_token", httpMethod = "POST", notes = "成功返回access_token", response = WebApiResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = WebApiResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure")})
    public Object getAccessToken(@ApiParam(value = "登录信息", required = true) @RequestBody UserDTO loginParameter)
    {
        WebApiResponse WebApiResponse = new WebApiResponse();
        
        // 验证clientID
        if (loginParameter.getClientId() == null
            || (loginParameter.getClientId().compareTo(audience.getClientId()) != 0))
        {
            WebApiResponse.setCode(ResultStatusCode.INVALID_CLIENT_ID.getCode());
            WebApiResponse.setMessage(ResultStatusCode.INVALID_CLIENT_ID.getMessage());
            return WebApiResponse;
        }
        
        // 验证用户名密码
        Member user = memberService.findUserInfoByName(loginParameter.getPhone());
        if (user == null)
        {
            WebApiResponse.setCode(ResultStatusCode.INVALID_USER_NAME.getCode());
            WebApiResponse.setMessage(ResultStatusCode.INVALID_USER_NAME.getMessage());
            
            return WebApiResponse;
        }
        else
        {
            String md5Password = MD5Util.getMD5(loginParameter.getPwd() + user.getSalt());
            LOGGER.info("md5Password is: " + md5Password);
            if (md5Password.compareTo(user.getPassWord()) != 0)
            {
                WebApiResponse.setCode(ResultStatusCode.INVALID_PASSWORD.getCode());
                WebApiResponse.setMessage(ResultStatusCode.INVALID_PASSWORD.getMessage());
                
                return WebApiResponse;
            }
        }
        
        // 拼装accessToken
        String accessToken = JwtUtil.createJWT(loginParameter, null, audience);
        // 写入缓存
        stringRedisTemplate.opsForHash().put(accessToken, LOGIN_NAME, user.getUserName());
        stringRedisTemplate.opsForHash().put(accessToken, LOGIN_MOBILE, user.getPhone());
        stringRedisTemplate.expire(accessToken, 7, TimeUnit.DAYS);
        // 返回accessToken
        AccessToken accessTokenEntity = new AccessToken();
        accessTokenEntity.setAccessToken(accessToken);
        accessTokenEntity.setExpiresIn(audience.getExpiresSecond());
        accessTokenEntity.setTokenType("bearer");
        
        WebApiResponse.setCode(ResultStatusCode.OK.getCode());
        WebApiResponse.setMessage(ResultStatusCode.OK.getMessage());
        WebApiResponse.setData(accessTokenEntity);
        return WebApiResponse;
    }
}