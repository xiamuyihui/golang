package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.kingthy.dubbo.basedata.service.MaterielCategoryDubboService;
import com.kingthy.dubbo.docking.service.IfiMaterialTypeDubboService;
import com.kingthy.entity.IfiMaterialType;
import com.kingthy.entity.MaterielCategory;
import com.kingthy.service.IfiMaterialTypeService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:35 on 2017/9/22.
 * @Modified by:
 */
@Service
public class IfiMaterialTypeServiceImpl implements IfiMaterialTypeService
{

    @Reference(version = "1.0.0", timeout = 3000)
    private IfiMaterialTypeDubboService ifiMaterialTypeDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MaterielCategoryDubboService materielCategoryDubboService;

    @Override
    public void executeSyn(Integer pageNum, Integer pageSize, ShardingContext shardingContext) throws Exception{

        List<IfiMaterialType> ifiMaterialTypeList = ifiMaterialTypeDubboService.queryListByLimit(pageNum, pageSize, Integer.valueOf(shardingContext.getShardingParameter()));

        Map<String, String> map = new HashMap<>(pageSize);
        // 先新增在修改
        if (Integer.valueOf(shardingContext.getShardingParameter()).compareTo(IfiMaterialType.OperStType.update.getValue()) == 0){

            List<String> colourIdList = new ArrayList<>();

            if (!ifiMaterialTypeList.isEmpty()){

                ifiMaterialTypeList.forEach(c -> colourIdList.add(c.getCode()));

                ifiMaterialTypeDubboService.queryUpdateListByListCode(colourIdList).forEach(c -> map.put(c.getCode(), c.getCode()));
            }
        }

        for (IfiMaterialType c : ifiMaterialTypeList){

            if (map.containsKey(c.getCode())){
                continue;
            }

            int result = syncMaterielCategory(c, shardingContext.getShardingItem());

            //标记数据已删除
            IfiMaterialType.StatusType statusType = result > 0 ? IfiMaterialType.StatusType.success : IfiMaterialType.StatusType.fail;

            ifiMaterialTypeDubboService.updateStatusById(c.getId(), statusType);

        }

    }

    private int syncMaterielCategory(IfiMaterialType c,int shardingItem) throws Exception{
        MaterielCategory var = new MaterielCategory();
        var.setCode(c.getCode());
        var.setClassName(c.getName());
        var.setDelFlag(false);
        var.setStatus(true);
        var.setLeaf(c.getLeaf());
        var.setParentId(c.getParentId());
        var.setTreePath(c.getPath());
        var.setCippRecoedId(c.getCippRecoedId());

        int result = 0;
        //按操作分片处理
        switch (shardingItem)
        {
            case 0:
                var.setCreateDate(new Date());
                var.setCreator(c.getUpdaterId());

                result = materielCategoryDubboService.insert(var);
                break;
            case 1:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                result = materielCategoryDubboService.updateByCode(var);
                break;
            case 2:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                var.setDelFlag(true);
                result = materielCategoryDubboService.deleteByCode(var);
                break;
            default:break;
        }

        return result;

    }
}
