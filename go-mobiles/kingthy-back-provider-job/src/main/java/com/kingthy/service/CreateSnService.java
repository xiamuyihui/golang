package com.kingthy.service;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:37 on 2017/11/21.
 * @Modified by:
 */
public interface CreateSnService
{

    void saveSnToRedis(String key, String type);
    void checkSnSize(String key, String type);
}
