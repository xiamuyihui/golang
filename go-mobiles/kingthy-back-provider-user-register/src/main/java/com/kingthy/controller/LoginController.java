package com.kingthy.controller;

import com.kingthy.common.ValidMobile;
import com.kingthy.dto.UserDTO;
import com.kingthy.entity.Member;
import com.kingthy.response.WebApiResponse;
import com.kingthy.security.Audience;
import com.kingthy.security.ResultStatusCode;
import com.kingthy.service.LoginService;
import com.kingthy.util.JwtUtil;
import com.kingthy.util.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Api
@RestController()
@RequestMapping("/member")


/**
 *  登录
 * @author pany
 */
public class LoginController

{
    private static final Logger LOGGER= LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private Audience audience;
    
    @ApiOperation(value = "用户登录接口", notes = "")
    @PostMapping("/login")
    public WebApiResponse<?> submit(
        @RequestBody @ApiParam(name = "userDTO", value = "用户对象", required = true) UserDTO userDTO)
    {

        try {
            if (StringUtils.isBlank(userDTO.getPhone()) || StringUtils.isBlank(userDTO.getPwd())) {
                return WebApiResponse.error("手机号,用户名或密码不能为空");
            }
            String loginName="";
            Member member=null;
            WebApiResponse<Member> result=null;

            WebApiResponse webApiResponse = new WebApiResponse();
            // 验证clientID
            if (userDTO.getClientId() == null || (userDTO.getClientId().compareTo(audience.getClientId()) != 0)) {
                webApiResponse.setCode(ResultStatusCode.INVALID_CLIENT_ID.getCode());
                webApiResponse.setMessage(ResultStatusCode.INVALID_CLIENT_ID.getMessage());
                return webApiResponse;
            }
            if (!ValidMobile.isMobileNO(userDTO.getPhone())) {
                return WebApiResponse.error("无法识别的手机号");
            }else{
                loginName = userDTO.getPhone();
                member = loginService.findUserByName(null, loginName);
            }
            if (null == member) {
                return WebApiResponse.error("该账号" + loginName + "还未注册");
            } else {
                result = isValidateAccount(loginName, member);
            }
            if (null != result) {
                return result;
            }
            // 比对用户名密码 暂时未对密码进行加密 上线前需对密码以及手机号进行加密处理
            String pwdFromWeb = MD5Util.MD5Encode(userDTO.getPwd() + member.getSalt());
            WebApiResponse<?> validateResult = validatePassword(member, pwdFromWeb);
            if (validateResult.getCode() != 0) {
                return validateResult;
            }
            member.setLastLoginDate(new Date());
            String tokenKey="login:" + loginName;
            if (stringRedisTemplate.hasKey(tokenKey)) {
                String accessToken = stringRedisTemplate.opsForValue().get(tokenKey);
                return WebApiResponse.success(accessToken);
            }
            String accessToken = JwtUtil.createJWT(member, audience);
            member.setToken(accessToken);
            loginService.updateUser(member);
            // 写入缓存
            stringRedisTemplate.opsForValue().set(tokenKey, accessToken,7,TimeUnit.DAYS);
            stringRedisTemplate.opsForValue().set(accessToken, member.getUuid(),7,TimeUnit.DAYS);

            return WebApiResponse.success(accessToken);
        }catch (Exception ex){
            LOGGER.error("submit error:"+ex.toString());
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    /**
     * 密码验证
     * 
     * @param member
     * @param pwdFromWeb
     * @return
     * @author 潘勇
     * @return WebApiResponse<String>
     * @begin 2017-03-29 15:23
     * @since 1.0.0
     */
    private WebApiResponse<?> validatePassword(Member member, String pwdFromWeb)
    {
        try {
            if (!pwdFromWeb.equals(member.getPassWord())) {
                // 失败后，失败次数+1
                int loginFailureCount = (member.getLoginFailureCount() == null ? 0 : member.getLoginFailureCount()) + 1;
                if (loginFailureCount >= 1) {
                    member.setLoginFailureCount(loginFailureCount);
                    int fcount=5;
                    if (loginFailureCount >= fcount) {
                        member.setIsLocked(true);
                        member.setLockedDate(new Date());
                    }
                    loginService.updateUser(member);

                    return WebApiResponse.error("密码错误，若连续  5 次密码错误账号将被锁定,剩余重试次数:" + (5 - loginFailureCount));
                }
                return WebApiResponse.error("用户名或密码错误");
            }
            return WebApiResponse.success("");
        }catch (Exception ex){
            LOGGER.error("validatePassword error:"+ex.toString());
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    /**
     * 用户账户验证
     * 
     * @param loginName
     * @param member
     * @return
     * @author 潘勇
     * @return WebApiResponse<Member>
     * @begin 2017-03-29 15:23
     * @since 1.0.0
     */
    private WebApiResponse<Member> isValidateAccount(String loginName, Member member)
    {
        // 该用户是否被冻结
        if (member.getIsEnabled())
        {
            return WebApiResponse.error("该账号" + loginName + "已被冻结");
        }
        // 该用户是否被锁定
        if (member.getIsLocked())
        {
            Date lockedDate = member.getLockedDate();
            Date unlockDate = DateUtils.addMinutes(lockedDate, 30);
            if (new Date().after(unlockDate))
            {
                unLockUser(member);
            }
            else
            {
                return WebApiResponse.error("该账号" + loginName + "已被冻结");
            }
            return WebApiResponse.error("该账号" + loginName + "已被锁定");
        }
        return null;
    }
    
    /**
     * 解锁用户
     * 
     * @param member
     * @author 潘勇
     * @return void
     * @begin 2017-03-29 15:22
     * @since 1.0.0
     */
    private void unLockUser(Member member)
    {
        member.setLoginFailureCount(0);
        member.setIsLocked(false);
        member.setLockedDate(null);
        loginService.updateUser(member);
    }
    
}
