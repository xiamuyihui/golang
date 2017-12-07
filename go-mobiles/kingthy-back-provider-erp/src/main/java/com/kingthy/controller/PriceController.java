package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingthy.common.CommonUtils;
import com.kingthy.conf.RedisManager;
import com.kingthy.response.WebApiResponse;
import com.kingthy.util.HttpClientHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name MaterialPriceController
 * @description 与工厂erp对接查询价格接口
 * @create 2017/9/26
 */
@RestController
@RequestMapping("/price")
public class PriceController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private RedisManager redisManager;

    @Value("${address.material}")
    private String material;

    @ApiOperation(value = "查询价格", notes = "")
    @GetMapping(value = "/{type}/{code}")
    public WebApiResponse getPrice(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token, @PathVariable String type, @PathVariable String code) {
        try {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            map.put("type",type);
            String resultStr = HttpClientHelper.sendPost(material, map, "utf-8");
            JSONObject resultData = JSON.parseObject(resultStr);
            JSONObject data = (JSONObject) resultData.getJSONArray("data").get(0);
            String price = data.get("price").toString();
            return WebApiResponse.success(price);
        } catch (Exception e) {
            LOG.error("根据code查询价格出错，异常信息" + e);
        }
        try {
            String price = redisManager.get(type+":"+code);
            if (StringUtils.isNotEmpty(price)){
                return WebApiResponse.success(price);
            }
        } catch (Exception e) {
            LOG.error("根据code查询价格出错，异常信息" + e);
        }
        return WebApiResponse.error("价格查询出错");
    }
}
