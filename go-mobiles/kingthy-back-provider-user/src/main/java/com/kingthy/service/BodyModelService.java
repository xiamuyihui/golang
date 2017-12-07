package com.kingthy.service;

import com.kingthy.entity.Figure;

import java.util.List;

/**
 *
 * 人体模型[业务逻辑处理接口]
 * 
 * @author likejie 2017-4-21
 * 
 * @version 1.0.0
 *
 */
public interface BodyModelService {

	/**
	 * 根据条件获取列表
	 * @param figure
	 * @return
	 */
	List<Figure> getBodyModelList(Figure figure);

	/**
	  * 创建一个人体模型
	 *  @param figure
	  * @return bodyModel
	  */
	int createBodyModel(Figure figure);

	/**
	  * 删除人体模型
	  *  @param uuid
	  * @return bodyModel
	  */
	int deleteBodyModel(String uuid);

	/**
	  * 修改人体模型详情
	  * @param figure
	  * @return bodyModel
	  */
	int updateBodyModel(Figure figure);


	

}
