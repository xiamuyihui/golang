package com.kingthy.service;

import java.util.List;

import net.sf.json.JSONObject;

public interface TupuCheckService
{
    /**
     * 
     * checkImager(对图片进行暴力色情检测)
     * 
     * @param urlpath
     * @return 赵生辉 String
     * @exception @since 1.0.0
     */
    JSONObject checkImager(List<String> urlpath);

    JSONObject checkImagerUrl(List<String> urlpath);
}
