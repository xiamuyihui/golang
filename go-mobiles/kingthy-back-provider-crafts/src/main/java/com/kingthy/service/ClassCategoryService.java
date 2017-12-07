package com.kingthy.service;

import com.kingthy.dto.CategoryTreeDto;
import com.kingthy.entity.ClassCategory;
import com.kingthy.request.CategoryReq;
import com.kingthy.request.TransferCategoryReq;

import java.util.List;

/**
 * 
 *
 * ClassClassCategoryService(品类分类接口)
 * 
 * 陈钊 2017年3月29日 下午1:56:11
 * 
 * @version 1.0.0
 *
 */
public interface ClassCategoryService
{
    /**
     *
     * findAllTopNodes(查询所有品类的顶级节点)
     *
     * @return 陈钊 List<ClassCategory>
     * @exception @since 1.0.0
     */
    List<ClassCategory> findAllTopNodes();

    /**
     *
     * findAllNodes(查询所有品类)
     *
     * @return List<ClassCategory>
     * @exception @since 1.0.0
     */
    List<ClassCategory> findAllNodes();

    /**
     *
     * findAllChildNodes(根据节点主键查询其所有的子节点)
     *
     * @return <b>创建人：</b>陈钊<br/>
     *         List<classCategoryUuid>
     * @exception @since 1.0.0
     */
    List<ClassCategory> findAllChildNodes(String classCategoryUuid);

    /**
     *
     * findAllChildNodesNum(根据节点Uuid查询其所有子节点数量)
     *
     * @param classCategoryUuid
     * @return <b>创建人：</b>陈钊<br/>
     *         int
     * @exception @since 1.0.0
     */
    int findAllChildNodesNum(String classCategoryUuid);

    /**
     *
     * findClassCategoryByUuid(根据uuid查询单个分类信息)
     *
     * @param classCategoryUuid
     * @return <b>创建人：</b>陈钊<br/>
     *         ClassCategory
     * @exception @since 1.0.0
     */
    ClassCategory findClassCategoryByUuid(String classCategoryUuid);

    /**
     *
     * findAll(查询所有节点信息)
     *
     * @return <b>创建人：</b>陈钊<br/>
     *         List<ClassCategory>
     * @exception @since 1.0.0
     */
    List<ClassCategory> findAll();

    /**
     *
     * getTree(查询树结构)
     *
     * @return <b>创建人：</b>陈钊<br/>
     *         List<CategoryTreeDto>
     * @exception @since 1.0.0
     */
    List<CategoryTreeDto> getTree();
}
