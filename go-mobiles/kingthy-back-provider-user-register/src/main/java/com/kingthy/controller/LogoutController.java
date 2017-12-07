package com.kingthy.controller;

import com.kingthy.entity.Member;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api
@RestController
@RequestMapping("/member")

/**
 * 登出
 * @author pany
 */
public class LogoutController
{
    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RegisterService registerService;

    @ApiOperation(value = "用户注销接口", notes = "")
    @GetMapping("/logout/{token}")
    public WebApiResponse<?> logout(@PathVariable @ApiParam(name = "token", value = "令牌", required = true) String token) {
        try {
            if (StringUtils.isBlank(token)) {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }
            String memberUuid = stringRedisTemplate.opsForValue().get(token);
            if (StringUtils.isBlank(memberUuid)) {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.NOT_LOGIN.getValue());
            }
            Member member = registerService.findMemberByUuid(memberUuid);
            if (null == member) {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.UNEXIST_USER.getValue());
            }
            String phone = member.getPhone();
            //让登陆标识失效1
            boolean existLogout = stringRedisTemplate.opsForValue().get("login:" + phone) == null ? true : false;
            if (existLogout) {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.LOGOUT_ERROR.getValue());
            }
            boolean phoneFlag = stringRedisTemplate.expire("login:" + phone, -1, TimeUnit.SECONDS);
            boolean tokenFlag = stringRedisTemplate.expire(token, -1, TimeUnit.SECONDS);
            LOG.debug("phoneFlag>>>" + phoneFlag);
            LOG.debug("tokenFlag>>>" + tokenFlag);
            if (phoneFlag == Boolean.TRUE && tokenFlag == Boolean.TRUE) {
                return WebApiResponse.success();
            } else {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.LOGOUT_ERROR.getValue());
            }
        }catch (Exception ex){
            LOG.error("logout error:"+ex.toString());
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }
}
