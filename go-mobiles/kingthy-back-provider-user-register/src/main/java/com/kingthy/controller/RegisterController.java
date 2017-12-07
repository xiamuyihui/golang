package com.kingthy.controller;


import com.kingthy.common.ValidMobile;
import com.kingthy.dto.SmsReponseCode;
import com.kingthy.dto.UserRegDTO;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.RegisterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController()
@RequestMapping("/member")
/**
 * 注册
 * @author pany
 */
public class RegisterController
{
    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    DiscoveryClient client;

    @ApiOperation(value = "用户注册接口", notes = "根据手机号进行用户注册")
    @PostMapping(value = "/createUser")
    public WebApiResponse<?> createUser(
        @RequestBody @ApiParam(name = "userDTO", value = "用户注册接口", required = true) UserRegDTO userRegDTO)
        throws IOException
    {
        try {
            WebApiResponse<String> validateResult = validateUserTelphone(userRegDTO);
            if (validateResult == null) {
                int result = registerService.createUser(userRegDTO);
                if (result > 0) {
                    return WebApiResponse.success("OK");
                } else if (result == -1) {
                    return WebApiResponse.error("该手机号已存在");
                }
            }
            return validateResult;
        }catch (Exception ex){
            LOG.error("createUser error:"+ex.toString());
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "忘记密码", notes = "根据手机号重置密码")
    @RequestMapping(value = "/restPwd", method = RequestMethod.POST)
    public WebApiResponse<String> restPwd(
        @RequestBody @ApiParam(name = "UserRegDTO", value = "用户名,密码,验证码必填", required = true) UserRegDTO userRegDTO)
    {
        try {
            WebApiResponse<String> validateResult = validateUserTelphone(userRegDTO);
            if (validateResult == null) {
                registerService.updateUserPwd(userRegDTO);
                return WebApiResponse.success("密码更改成功");
            }
            return validateResult;
        }catch (Exception ex){
            LOG.error("restPwd error:"+ex.toString());
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "获取验证码", notes = "根据手机号获取验证码")
    @GetMapping("/getVerificationCode/{phoneNum}")
    public WebApiResponse<?> getVerificationCode(
        @PathVariable @ApiParam(name = "phoneNum", value = "手机号", required = true) String phoneNum)
    {
        try {
            if (StringUtils.isBlank(phoneNum)) {
                return WebApiResponse.error("手机号不能为空");
            }
            if (!ValidMobile.isMobileNO(phoneNum)) {
                return WebApiResponse.error("非法的手机号");
            }
            // 给调用方产生随机验证码
            String key = "verify-code:" + phoneNum;
            String verificationCode = "";
            String value = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(value)) {
                verificationCode = value;
                return WebApiResponse.success(verificationCode);
            }
            verificationCode = getRandNum();
            stringRedisTemplate.opsForValue().set(key, verificationCode);
            stringRedisTemplate.expire(key, 180, TimeUnit.SECONDS);
            return WebApiResponse.success(verificationCode);
        }catch (Exception ex){
            LOG.error("getVerificationCode error:"+ex.toString());
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }

    /**
     * 生成随机码 getRandNum(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @return String
     * @throws @since 1.0.0
     */
    public static String getRandNum()
    {
        Random random = new Random();

        String result = "";
        int max=6;
        for (int i = 0; i < max; i++)
        {

            result += random.nextInt(10);
        }
        return result;
    }

    private WebApiResponse<String> validateUserTelphone(UserRegDTO userDTO)
    {
        String key = "verify-code:" + userDTO.getPhone();
        String verificationCode = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(userDTO.getPhone()) || StringUtils.isBlank(userDTO.getPwd()))
        {
            return WebApiResponse.error("手机号或密码不能为空");
        }
        if (!ValidMobile.isMobileNO(userDTO.getPhone()))
        {
            return WebApiResponse.error("非法的手机号");
        }
        if (StringUtils.isBlank(verificationCode))
        {
            return WebApiResponse.error("验证码已过期，请重新获取");
        }
        else if (!verificationCode.equals(userDTO.getVerificationCode()))
        {
            return WebApiResponse.error("验证码输入错误，请输入：" + verificationCode);
        }

        return null;
    }
}
