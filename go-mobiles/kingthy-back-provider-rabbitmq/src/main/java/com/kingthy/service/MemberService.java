package com.kingthy.service;

import com.kingthy.dto.MemberDTO;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Created by likejie on 2017/9/22.
 */
@FeignClient(value="provider-user-dubbo",fallback = MemberService.HystrixClientFallbackImpl.class)
public interface MemberService {

    /**
     * 修改会员
     * @param member
     * @return
     */
    @RequestMapping(value = "/member/updateMember", method = RequestMethod.POST)
    WebApiResponse<?> updateMember(@RequestBody MemberDTO member);

    @Component
    class HystrixClientFallbackImpl implements MemberService {

        private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

        @Override
        public WebApiResponse<?> updateMember(MemberDTO member) {

            LOGGER.error("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }
}
