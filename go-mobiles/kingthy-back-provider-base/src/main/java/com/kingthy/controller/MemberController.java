/**
 * 系统项目名称
 * com.kingthy.controller
 * MemberController.java
 * 
 * 2017年4月18日-下午7:57:33
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.controller;


import com.kingthy.dto.MemberDTO;
import com.kingthy.entity.Member;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 *
 * 会员中心--信息管理
 * 
 * 李克杰 2017年4月18日 下午7:57:33
 * 
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("/member")
public class MemberController
{
    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
    
    @Autowired
    MemberService memberService;
    

    @PostMapping("/getMemberList")
    public WebApiResponse<?> getMemberList(@RequestBody  Member member)
    {
        try
        {
            List<Member> list = memberService.getMemberList(member);
            return WebApiResponse.success(list);
        }
        catch (Exception e)
        {
            LOG.error("MemberController.getMemberList error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
        
    }
    @GetMapping("/getMemberByUuid/{uuid}")
    public WebApiResponse<?> getMemberByUuid(@PathVariable  String uuid)
    {
        try
        {
            MemberDTO member = memberService.getMemberByUuid(uuid);
            return WebApiResponse.success(member);
        }
        catch (Exception e)
        {
            LOG.error("MemberController.getMemberByUuid error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }

    }
    /**
     * 新增
     *
     */
    @PostMapping("/addMember")
    public WebApiResponse<?> addMember(@RequestBody  Member member)
    {
        try
        {

            if(member==null){
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }
            int res = memberService.addMember(member);
            if (res > 0)
            {
                return WebApiResponse.success();
            }
            else
            {
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }

        }
        catch (Exception e)
        {
            LOG.error("MemberController.updateMember error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }


    /**
     * 更新
     * 
     */

    @PostMapping("/updateMember")
    public WebApiResponse<?> updateMember(@RequestBody  Member member)
    {
        try
        {
            if (StringUtils.isBlank(member.getUuid()))
            {
                return WebApiResponse.error("参数uuid不能为空");
            }
            // 更新
            int res = memberService.updateMember(member);
            if (res > 0)
            {
                return WebApiResponse.success();
            }
            else
            {
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }
            
        }
        catch (Exception e)
        {
            LOG.error("MemberController.updateMember error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    /**
     * 删除
     *
     */
    @GetMapping("/deleteMember/{uuid}")
    public WebApiResponse<?> deleteMember(@PathVariable String uuid)
    {
        try
        {
            int res = memberService.deleteMember(uuid);
            if (res > 0)
            {
                return WebApiResponse.success();
            }
            else
            {
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }

        }
        catch (Exception e)
        {
            LOG.error("MemberController.updateMember error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
}
