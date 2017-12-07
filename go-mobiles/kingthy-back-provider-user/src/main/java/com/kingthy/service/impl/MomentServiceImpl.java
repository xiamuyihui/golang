package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.dto.MomentDto;
import com.kingthy.dubbo.review.service.LikesDubboService;
import com.kingthy.dubbo.review.service.MomentCommentDubboService;
import com.kingthy.dubbo.review.service.MomentsDubboService;
import com.kingthy.dubbo.user.service.AttentionDubboService;
import com.kingthy.entity.Moments;
import com.kingthy.exception.BizException;
import com.kingthy.request.QueryMomentPageReq;
import com.kingthy.service.MomentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MomentServiceImpl(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月17日 17:29
 *
 * @version 1.0.0
 */
@Service("momentService")
public class MomentServiceImpl implements MomentService
{

    @Reference(version = "1.0.0",timeout = 3000)
    private MomentsDubboService momentsDubboService;

    @Reference(version = "1.0.0",timeout = 3000)
    private LikesDubboService likesDubboService;

    @Reference(version = "1.0.0",timeout = 3000)
    private MomentCommentDubboService momentCommentDubboService;

    @Reference(version = "1.0.0",timeout = 3000)
    private AttentionDubboService attentionDubboService;

    private static final int VERSION = 1;
    private static final String TIME_FILD_CHECK="time";
    @Override
    public int publishMoment(Moments moments)
    {
        Date date = new Date();
        moments.setCreateDate(date);
        moments.setModifyDate(date);
        moments.setCreator("Creator");
        moments.setModifier("Modifier");
        moments.setVersion(VERSION);
        moments.setDelFlag(false);
        moments.setLikeAmount(0L);
        moments.setCommentAmount(0L);
        int result = momentsDubboService.insert(moments);
        if(result <= 0)
        {
            throw new BizException("发布动态失败");
        }
        return result;
    }

    @Override
    public PageInfo<MomentDto> queryMomentPage(QueryMomentPageReq queryMomentPageReq)
    {
        if(queryMomentPageReq.getOrder() != null && TIME_FILD_CHECK.equals(queryMomentPageReq.getOrder())){
            queryMomentPageReq.setOrder("create_date desc");
        }else{
            queryMomentPageReq.setOrder("like_amount desc ,comment_amount desc");
        }
        if(queryMomentPageReq.getMembers().size() == 0){
            queryMomentPageReq.setMembers(null);
        }
        PageInfo<MomentDto> pageInfo = momentsDubboService.selectMoment(queryMomentPageReq);
        List<MomentDto> moments = pageInfo.getList();
        if(moments!=null && !moments.isEmpty()){
            moments.forEach(momentDto -> {
                momentDto.setIsAttention("0");
            });
            ArrayList<MomentDto> dtoArrayList = (ArrayList<MomentDto>) moments;
            //进行装配
            List<String> isattentionList = attentionDubboService.isattention(queryMomentPageReq.getMemberUuid(),dtoArrayList);
            if (isattentionList != null && !isattentionList.isEmpty()){
                for (MomentDto momentDto:moments){
                    for (String isattention : isattentionList){
                        if (momentDto.getMemberUuid() !=null && momentDto.getMemberUuid().equals(isattention)){
                            momentDto.setIsAttention("1");
                            break;
                        }
                    }
                }
            }
        }
        return pageInfo;
    }

    @Override
    public int update(String uuid, Integer likes, Integer comments)
    {
        int result = momentsDubboService.updateLikes(uuid,likes,comments);
        if(result == 0)
        {
            throw new BizException("修改热度失败");
        }
        return result;
    }

    @Override
    public int delete(String uuid, String memberUuid)
    {
        return momentsDubboService.deleteMoment(uuid,memberUuid);
    }
}
