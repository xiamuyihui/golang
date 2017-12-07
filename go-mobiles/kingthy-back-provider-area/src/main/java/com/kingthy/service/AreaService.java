package com.kingthy.service;

/**
 * @author shenghuizhao
 */
public interface AreaService
{

    /**
     * 获取所有省
     * @return
     */
    java.util.List<com.kingthy.entity.Area> getProvince();

    /**
     * 查询地区
     * @param grade
     * @param parentId
     * @return
     */
    java.util.List<com.kingthy.entity.Area> getCity(Integer grade, Integer parentId);
}
