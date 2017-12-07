package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.kingthy.dubbo.basedata.service.*;
import com.kingthy.dubbo.docking.service.*;
import com.kingthy.entity.*;
import com.kingthy.service.TaskIfiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:57 on 2017/9/26.
 * @Modified by:
 */
@Service
public class TaskIfiServiceImpl implements TaskIfiService
{
    private static final Logger LOG = LoggerFactory.getLogger(TaskIfiServiceImpl.class);

    @Reference(version = "1.0.0", timeout = 3000)
    private ColourIfiDubboService colourIfiDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private BaseColourInfoDubboService baseColourInfoDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private ComponentTypeIfiDubboService componentTypeIfiDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private PartsCategoryDubboService partsCategoryDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private PartsTypeIfiDubboService partsTypeIfiDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private SparePartDubboService sparePartDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private ClassCategoryDubboService classCategoryDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private StyleTypeIfiDubboService styleTypeIfiDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private IfiMeasureUnitDubboService ifiMeasureUnitDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MeasureUnitDubboService measureUnitDubboService;

    @Override
    public void executeColourIfi(Integer pageNum, Integer pageSize, ShardingContext shardingContext) throws Exception {

        List<ColourIfi> colourIfiList = colourIfiDubboService.queryListByLimit(pageNum, pageSize, Integer.valueOf(shardingContext.getShardingParameter()));

        Map<String, String> map = new HashMap<>(pageSize);
        // 先新增在修改
        if (Integer.valueOf(shardingContext.getShardingParameter()).compareTo(ColourIfi.OperStType.update.getValue()) == 0){

            List<String> colourIdList = new ArrayList<>();

            if (!colourIfiList.isEmpty()){

                colourIfiList.forEach(c -> colourIdList.add(c.getCode()));

                colourIfiDubboService.queryUpdateListByListCode(colourIdList).forEach(c -> map.put(c.getCode(), c.getCode()));
            }
        }

        colourIfiList
                .forEach(c -> {

                    if (map.containsKey(c.getCode())){
                        return;
                    }

                    int result = 0;

                    try {

                        result = syncBaseColour(c, shardingContext.getShardingItem());

                    } catch (Exception e) {
                        LOG.error("同步颜色表[base_colour_info]失败" + e);
                    }

                    //标记数据已删除
                    ColourIfi.StatusType statusType = result > 0 ? ColourIfi.StatusType.success : ColourIfi.StatusType.fail;

                    colourIfiDubboService.updateStatusById(c.getId(), statusType);
                });
    }

    @Override
    public void executeComponentTypeIfi(Integer pageNum, Integer pageSize, ShardingContext shardingContext) throws Exception
    {
        List<ComponentTypeIfi> componentTypeIfiList = componentTypeIfiDubboService.queryListByLimit(pageNum, pageSize, Integer.valueOf(shardingContext.getShardingParameter()));

        Map<String, String> map = new HashMap<>(pageSize);
        // 先新增在修改
        if (Integer.valueOf(shardingContext.getShardingParameter()).compareTo(ComponentTypeIfi.OperStType.update.getValue()) == 0){

            List<String> list = new ArrayList<>();

            if (!componentTypeIfiList.isEmpty()){

                componentTypeIfiList.forEach(c -> list.add(c.getCode()));

                componentTypeIfiDubboService.queryUpdateListByListCode(list).forEach(c -> map.put(c.getCode(), c.getCode()));
            }
        }

        componentTypeIfiList.forEach(c -> {

            //过滤修改的 下次在执行
            if (map.containsKey(c.getCode())){
                return;
            }

            int result = 0;

            try {

                result = syncPartsCategory(c, shardingContext.getShardingItem());

            } catch (Exception e) {
                LOG.error("同步部件表[parts_category]失败" + e);
            }

            //标记数据已删除
            ComponentTypeIfi.StatusType statusType = result > 0 ? ComponentTypeIfi.StatusType.success : ComponentTypeIfi.StatusType.fail;

            componentTypeIfiDubboService.updateStatusById(c.getId(), statusType);
        });

    }

    @Override
    public void executePartsTypeIfi(Integer pageNum, Integer pageSize, ShardingContext shardingContext) throws Exception {

        List<PartsTypeIfi> partsTypeIfiList = partsTypeIfiDubboService.queryListByLimit(pageNum, pageSize, Integer.valueOf(shardingContext.getShardingParameter()));

        Map<String, String> map = new HashMap<>(pageSize);
        // 先新增在修改
        if (Integer.valueOf(shardingContext.getShardingParameter()).compareTo(PartsTypeIfi.OperStType.update.getValue()) == 0){

            List<String> list = new ArrayList<>();

            if (!partsTypeIfiList.isEmpty()){

                partsTypeIfiList.forEach(c -> list.add(c.getCode()));

                partsTypeIfiDubboService.queryUpdateListByListCode(list).forEach(c -> map.put(c.getCode(), c.getCode()));
            }
        }


        partsTypeIfiList.forEach(c -> {

            if (map.containsKey(c.getCode())){
                return;
            }

            int result = 0;

            try {

                result = syncSparePart(c, shardingContext.getShardingItem());

            } catch (Exception e) {
                LOG.error("同步零件表[spare_part]失败" + e);
            }

            //标记数据已删除
            PartsTypeIfi.StatusType statusType = result > 0 ? PartsTypeIfi.StatusType.success : PartsTypeIfi.StatusType.fail;

            partsTypeIfiDubboService.updateStatusById(c.getId(), statusType);
        });
    }

    /**
     * 款式表
     * @param pageNum
     * @param pageSize
     * @param shardingContext
     * @throws Exception
     */
    @Override
    public void executeStyleTypeIfi(Integer pageNum, Integer pageSize, ShardingContext shardingContext) throws Exception {

        List<StyleTypeIfi> styleTypeIfiList = styleTypeIfiDubboService.queryListByLimit(pageNum, pageSize, Integer.valueOf(shardingContext.getShardingParameter()));

        Map<String, String> map = new HashMap<>(pageSize);
        // 先新增在修改
        if (Integer.valueOf(shardingContext.getShardingParameter()).compareTo(StyleTypeIfi.OperStType.update.getValue()) == 0){

            List<String> list = new ArrayList<>();

            if (!styleTypeIfiList.isEmpty()){

                styleTypeIfiList.forEach(c -> list.add(c.getCode()));

                styleTypeIfiDubboService.queryUpdateListByListCode(list).forEach(c -> map.put(c.getCode(), c.getCode()));
            }
        }

        styleTypeIfiList.forEach(c -> {

            if (map.containsKey(c.getCode())){
                return;
            }

            int result = 0;

            try {

                result = syncClassCategory(c, shardingContext.getShardingItem());

            } catch (Exception e) {
                LOG.error("同步品类表[class_category]失败" + e);
            }

            //标记数据已删除
            StyleTypeIfi.StatusType statusType = result > 0 ? StyleTypeIfi.StatusType.success : StyleTypeIfi.StatusType.fail;

            styleTypeIfiDubboService.updateStatusById(c.getId(), statusType);
        });
    }

    /**
     * 计量单位
     * @param pageNum
     * @param pageSize
     * @param shardingContext
     * @throws Exception
     */
    @Override
    public void executeIfiMeasureUnit(Integer pageNum, Integer pageSize, ShardingContext shardingContext) throws Exception {

        List<IfiMeasureUnit> ifiMeasureUnitList = ifiMeasureUnitDubboService.queryListByLimit(pageNum, pageSize, Integer.valueOf(shardingContext.getShardingParameter()));

        Map<String, String> map = new HashMap<>(pageSize);
        // 先新增在修改
        if (Integer.valueOf(shardingContext.getShardingParameter()).compareTo(IfiMeasureUnit.OperStType.update.getValue()) == 0){

            List<String> list = new ArrayList<>();

            if (!ifiMeasureUnitList.isEmpty()){

                ifiMeasureUnitList.forEach(c -> list.add(c.getCode()));

                ifiMeasureUnitDubboService.queryUpdateListByListCode(list).forEach(c -> map.put(c.getCode(), c.getCode()));
            }
        }

        for (IfiMeasureUnit c : ifiMeasureUnitList){

            if (map.containsKey(c.getCode())){
                continue;
            }

            int result = syncMeasureUnit(c, shardingContext.getShardingItem());

            //标记数据已删除
            IfiMeasureUnit.StatusType statusType = result > 0 ? IfiMeasureUnit.StatusType.success : IfiMeasureUnit.StatusType.fail;

            ifiMeasureUnitDubboService.updateStatusById(c.getId(), statusType);

        }
    }

    private int syncMeasureUnit(IfiMeasureUnit c, int shardingItem){
        MeasureUnit var = new MeasureUnit();
        var.setCode(c.getCode());
        var.setName(c.getName());
        var.setSortId(c.getSortId());

        int result = 0;
        //按操作分片处理
        switch (shardingItem)
        {
            case 0:
                var.setCreator(c.getUpdaterId());
                var.setCreateDate(new Date());
                var.setDelFlag(false);
                var.setVersion(0);
                result = measureUnitDubboService.insert(var);
                break;
            case 1:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                result = measureUnitDubboService.updateByCode(var);
                break;
            case 2:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                var.setDelFlag(true);
                result = measureUnitDubboService.deleteByCode(var);
                break;
            default:break;
        }
        return result;
    }

    /**
     * 同步零件表
     * @param c
     * @param shardingItem
     * @return
     */
    private int syncSparePart(PartsTypeIfi c, int shardingItem){
        SparePart var = new SparePart();
        var.setSn(c.getCode());
        var.setClassName(c.getName());

        int result = 0;
        //按操作分片处理
        switch (shardingItem)
        {
            case 0:
                var.setCreator(c.getUpdaterId());
                var.setCreateDate(new Date());
                var.setDelFlag(false);
                var.setVersion(0);
                result = sparePartDubboService.insert(var);
                break;
            case 1:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                result = sparePartDubboService.updateBySn(var);
                break;
            case 2:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                var.setDelFlag(true);
                result = sparePartDubboService.deleteBySn(var);
                break;
            default:break;
        }
        return result;
    }

    /**
     * 同步颜色表
     * @param c
     * @param shardingItem
     * @return
     * @throws Exception
     */
    private int syncBaseColour(ColourIfi c,int shardingItem) throws Exception{
        BaseColourInfo var = new BaseColourInfo();
        var.setCode(c.getCode());
        var.setBarcode(c.getBarcode());
        var.setImageSrc(c.getImageSrc());
        var.setName(c.getName());
        var.setRgb(c.getRgb());

        int result = 0;
        //按操作分片处理
        switch (shardingItem)
        {
            case 0:

                if (baseColourInfoDubboService.selectColourCountByCode(c.getCode()) > 0){
                    break;
                }
                var.setCreater(c.getUpdaterId());
                var.setCreateTime(new Date());
                var.setStatus("1");
                result = baseColourInfoDubboService.insert(var);
                break;
            case 1:
                var.setUpdater(c.getUpdaterId());
                var.setUpdateTime(new Date());
                result = baseColourInfoDubboService.updateByCode(var);
                break;
            case 2:
                var.setUpdater(c.getUpdaterId());
                var.setUpdateTime(new Date());
                var.setStatus("0");
                result = baseColourInfoDubboService.deleteByCode(var);
                break;
            default:break;
        }

        return result;

    }

    /**
     * 同步部件表
     * @param c
     * @param shardingItem
     * @return
     * @throws Exception
     */
    private int syncPartsCategory(ComponentTypeIfi c, int shardingItem) throws Exception{

        PartsCategory var = new PartsCategory();
        var.setSn(c.getCode());
        var.setClassName(c.getName());
        var.setModifyDate(new Date());
        var.setModifier(c.getUpdaterId());

        int result = 0;
        //按操作分片处理
        switch (shardingItem)
        {
            case 0:
                var.setCreator(c.getUpdaterId());
                var.setCreateDate(new Date());
                var.setStatus(true);
                var.setDelFlag(false);
                var.setVersion(0);
                result = partsCategoryDubboService.create(var);
                break;
            case 1:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                result = partsCategoryDubboService.updateBySn(var);
                break;
            case 2:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                var.setDelFlag(true);
                result = partsCategoryDubboService.deleteBySn(var);
                break;
            default:break;
        }

        return result;
    }

    /**
     * 同步品类表
     * @param c
     * @param shardingItem
     * @return
     * @throws Exception
     */
    private int syncClassCategory(StyleTypeIfi c, int shardingItem) throws Exception{
        ClassCategory var = new ClassCategory();
        var.setClassName(c.getName());
        var.setCode(c.getCode());
        int result = 0;
        //按操作分片处理
        switch (shardingItem)
        {
            case 0:
                var.setCreator(c.getUpdaterId());
                var.setCreateDate(new Date());
                var.setStatus(true);
                var.setDelFlag(false);
                var.setVersion(0);
                result = classCategoryDubboService.insert(var);
                break;
            case 1:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                result = classCategoryDubboService.updateByStyleCode(var);
                break;
            case 2:
                var.setModifier(c.getUpdaterId());
                var.setModifyDate(new Date());
                var.setDelFlag(true);
                result = classCategoryDubboService.delByStyleCode(var);
                break;
            default:break;
        }

        return result;
    }

}
