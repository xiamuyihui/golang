package com.kingthy.controller;

import com.kingthy.dto.*;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:42 on 2017/9/25.
 * @Modified by:
 */

@Api
@RestController
@RequestMapping("/ifo/order")
public class IfoOrderInfoController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(IfoOrderInfoController.class);
    public static final String MESSAGE = "服务器出错";

    @Autowired
    private IfoOrderInfoService ifoOrderInfoService;

    @Autowired
    private IfoOrderDetailBomService ifoOrderDetailBomService;

    @Autowired
    private IfoOrderStyleFileService ifoOrderStyleFileService;

    @Autowired
    private IfoStitchingStyleService ifoStitchingStyleService;

    @Autowired
    private IfoProcessInfoService ifoProcessInfoService;

    @ApiOperation(value = "创建CIPP订单信息", notes = "")
    @PostMapping("/full/create")
    public WebApiResponse<?> createFullIfoOrder(@RequestBody @ApiParam(name = "ifoOrderDTO", value = "订单信息", required = true) IfoFullOrderDTO ifoFullOrderDTO){

        WebApiResponse<?> result = null;

        try {

            if (ifoFullOrderDTO == null || ifoFullOrderDTO.getItemDTOList() == null ||
                    ifoFullOrderDTO.getItemDTOList().isEmpty()){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }

            result = ifoOrderInfoService.createFullIfoOrder(ifoFullOrderDTO);

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("-------------创建CIPP所有订单信息 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "创建CIPP订单", notes = "")
    @PostMapping("/create")
    public WebApiResponse<?> createIfoOrder(@RequestBody @ApiParam(name = "ifoOrderDTO", value = "订单信息", required = true) IfoOrderItemDTO ifoOrderDTO){

        WebApiResponse<?> result = null;

        try {

            result = ifoOrderInfoService.createIfoOrder(ifoOrderDTO);

        }catch (Exception e){

            LOGGER.error("-------------创建CIPP订单接口 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }


/*
    @ApiOperation(value = "创建CIPP子订单", notes = "")
    @PostMapping("/item")
    public WebApiResponse<?> createOrderItem(@RequestBody @ApiParam(name = "dto", value = "订单接口", required = true) IfoOrderItemDTO ifoOrderItemDTO){

        WebApiResponse<?> result = null;

        try {

            result = ifoOrderInfoDetailService.createOrderItem(ifoOrderItemDTO);

        }catch (Exception e){

            LOGGER.error("-------------创建CIPP子订单接口 失败: "+e+ "-------------");
            e.printStackTrace();
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }
*/


    @ApiOperation(value = "创建CIPP子订单BOM", notes = "")
    @PostMapping("/bom")
    public WebApiResponse<?> createOrderItemBom(@RequestBody @ApiParam(name = "dto", value = "BOM接口", required = true)
                                                            IfoOrderDetailBomDTO detailBomDTO)
    {

        WebApiResponse<?> result = null;

        try {

            result = ifoOrderDetailBomService.createOrderItemBom(detailBomDTO);

        }catch (Exception e){

            LOGGER.error("-------------创建CIPP子订单BOM 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "款式文件接口", notes = "")
    @PostMapping("/style")
    public WebApiResponse<?> createOrderStyleFile(@RequestBody @ApiParam(name = "dto", value = "款式文件接口", required = true)
                                                         IfoOrderStyleFileDTO ifoOrderStyleFileDTO){

        WebApiResponse<?> result = null;

        try {

            result = ifoOrderStyleFileService.createOrderStyleFile(ifoOrderStyleFileDTO);

        }catch (Exception e){

            LOGGER.error("-------------创建款式文件接口 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "c", notes = "")
    @PostMapping("/stitching")
    public WebApiResponse<?> createStitchingStyle(@RequestBody @ApiParam(name = "dto", value = "缝合关系接口", required = true)
                                                     IfoStitchingStyleDTO ifoStitchingStyleDTO){

        WebApiResponse<?> result = null;

        try {

            result = ifoStitchingStyleService.createStitchingStyle(ifoStitchingStyleDTO);

        }catch (Exception e){

            LOGGER.error("-------------创建缝合关系接口表 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "缝制工艺工序接口表", notes = "")
    @PostMapping("/process")
    public WebApiResponse<?> createIfoProcess(@RequestBody @ApiParam(name = "dto", value = "缝制工艺工序接口", required = true)
                                                         IfoProcessInfoDTO ifoProcessInfoDTO){

        WebApiResponse<?> result = null;

        try {

            result = ifoProcessInfoService.createIfoProcess(ifoProcessInfoDTO);

        }catch (Exception e){

            LOGGER.error("-------------缝制工艺工序接口表 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }

}
