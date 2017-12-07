package com.kingthy.controller;

import com.kingthy.cache.RedisManager;
import com.kingthy.dto.WSMessageDTO;
import com.kingthy.response.WebApiResponse;
import com.kingthy.websocket.IEndpoint;
import com.kingthy.websocket.SocketClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 *
 *
 * Created by likejie on 2017/6/9.
 */
@Api
@RestController
@RequestMapping("/message")
public class MessageController {
    protected static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisManager redisManager;

//    @ApiOperation(value = "发送消息到指定客户端")
//    @PostMapping("/sendMessage")
//    public WebApiResponse<?> sendMessage(@RequestBody  @ApiParam(name = "messageDTO", value = "对象") WSMessageDTO messageDTO)
//    {
//        try
//        {
//            IEndpoint con=SocketClient.CLIENTS.get(messageDTO.getConnectionId());
//            if(con!=null){
//                con.send(messageDTO);
//            }else{
//                return WebApiResponse.error("参数connectionId对应的连接不存在");
//            }
//            return WebApiResponse.success();
//        }
//        catch (Exception e)
//        {
//            logger.error("MemberController.sendMessage error:" + e.toString());
//            return WebApiResponse.error(e.toString());
//        }
//    }

    @ApiOperation(value = "获取连接数")
    @PostMapping("/getConnectionCount")
    public WebApiResponse<?> getConnectionCount()
    {
        try
        {
            return WebApiResponse.success(SocketClient.CLIENTS.size());
        }
        catch (Exception e)
        {
            logger.error("MemberController.updateMember error:" + e.toString());
            return WebApiResponse.error(e.toString());
        }
    }

    @ApiOperation(value = "发送消息到指定客户端")
    @PostMapping("/sendModelMessage")
    public WebApiResponse<?> sendModelMessage(@RequestBody  @ApiParam(name = "messageDTO", value = "对象") WSMessageDTO messageReq)
    {
        try
        {
            String memberUuid = redisManager.get(messageReq.getToken());
            String connectionId = redisManager.get("websocket"+memberUuid);
            if(connectionId == null){
                logger.error("MemberController.getConnectionId error: 没有找到连接id" );
                return WebApiResponse.error("该用户没有连接");
            }
            IEndpoint con=SocketClient.CLIENTS.get(connectionId);
            if(con!=null){
                con.send(messageReq);
            }else{
                return WebApiResponse.error("参数connectionId对应的连接不存在");
            }
            return WebApiResponse.success();
        }
        catch (Exception e)
        {
            logger.error("MemberController.updateMember error:" + e.toString());
            return WebApiResponse.error(e.toString());
        }
    }



}
