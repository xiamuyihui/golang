package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.user.service.BodyModelDubboService;
import com.kingthy.dubbo.user.service.FigureDubboService;
import com.kingthy.entity.Figure;
import com.kingthy.exception.BizException;
import com.kingthy.service.BodyModelService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

 
 /**
 *
 * 人体模型 [业务处理实现类]
 * 
 * @author likejie 2017-4-21
 * 
 * @version 1.0.0
 *
 */
@Service("bodyModelService")
public class BodyModelServiceImpl implements BodyModelService {

	 @Reference(version = "1.0.0")
	 private BodyModelDubboService bodyModelDubboService;

	 @Reference(version = "1.0.0")
	 private FigureDubboService figureDubboService;

	 private static final int VERSION = 1;

	 @Override
	 public List<Figure> getBodyModelList(Figure figure) {

		 return figureDubboService.select(figure);
	 }

	 @Override
	 public int createBodyModel(Figure figure)
	 {
		 Date date = new Date();
		 figure.setCreateDate(date);
		 figure.setModifyDate(date);
		 figure.setCreator("creator");
		 figure.setModifier("modifier");
		 figure.setVersion(VERSION);
		 figure.setDelFlag(false);
		 figure.setIsDefault(false);
		 int result = figureDubboService.insert(figure);
		 if(result == 0){
			 throw new BizException("创建模型失败");
		 }
		 return result;
	 }

	 @Override
	 public int deleteBodyModel(String uuid)
	 {
		 int result = bodyModelDubboService.deleteBodyModel(uuid);
		 if(result == 0){
			 throw new BizException("修改模型失败");
		 }
		 return result;
	 }

	 @Override
	 public int updateBodyModel(Figure figure)
	 {
		 int result = figureDubboService.updateByUuid(figure);
		 if(result == 0){
			 throw new BizException("修改模型失败");
		 }
		 return result;
	 }

 }
