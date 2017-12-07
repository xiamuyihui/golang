package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.MaterialAccessoriesDTO;
import com.kingthy.dubbo.basedata.service.AccessoriesDubboService;
import com.kingthy.dubbo.basedata.service.MaterialDubboService;
import com.kingthy.dubbo.basedata.service.MaterielCategoryDubboService;
import com.kingthy.dubbo.docking.service.MaterialFIfoDubboService;
import com.kingthy.dubbo.docking.service.MaterialMIfoDubboService;
import com.kingthy.entity.*;
import com.kingthy.service.MaterialAccessoriesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:25 on 2017/9/20.
 * @Modified by:
 */
@Service
public class MaterialAccessoriesServiceImpl implements MaterialAccessoriesService
{

    @Reference(version = "1.0.0", timeout = 3000)
    private AccessoriesDubboService accessoriesDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MaterialFIfoDubboService materialFIfoDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MaterialDubboService materialDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MaterialMIfoDubboService materialMIfoDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MaterielCategoryDubboService materielCategoryDubboService;


    @Override
    public int syncCIPPInfo(MaterialAccessoriesDTO message) throws Exception {

        int result = 0;

        //辅料资料
        if (message.getTableType().compareTo(MaterialAccessoriesDTO.TableType.accessories.getValue()) == 0){

            Accessories a = accessoriesDubboService.selectAccessoriesByUuid(message.getCode());

            //非入库的数据不同步
            if(a.getStatus() == null || Accessories.AccessoriesType.warehousing.getValue() != a.getStatus().intValue()){
                return 1;
            }

            if (a != null){
                MaterialFIfo t = new MaterialFIfo();

                t.setCode(a.getCode());

                t.setColorCode(a.getColor());
                t.setCreateTime(new Date());
                t.setOperSt(message.getOperSt());
                t.setName(a.getName());
                t.setUnit(a.getMeasurement());
                t.setImageSrc(a.getImage());
                t.setPrice(a.getPrice());
                t.setIfStatus(MaterialMIfo.StatusType.init.getValue());
                t.setUpdaterId(UPDATER_ID);

                MaterielCategory var1 = new MaterielCategory();
                var1.setUuid(a.getMaterielUuid());
                MaterielCategory materielCategory = materielCategoryDubboService.selectOne(var1);
                if (materielCategory != null){
                    t.setType(materielCategory.getCode());
                }

                t.setSpecs(a.getSpecification());

                StringBuffer supplierRemark = new StringBuffer();

                supplierRemark.append("名称: ");
                supplierRemark.append(a.getSupplier());
                supplierRemark.append("; ");
                supplierRemark.append("地址: ");
                supplierRemark.append(a.getSupplierAddress());
                supplierRemark.append("; ");
                supplierRemark.append("联系人: ");
                supplierRemark.append(a.getLinkman());
                supplierRemark.append("; ");
                supplierRemark.append("手机: ");
                supplierRemark.append(a.getLinkphone());
                supplierRemark.append("; ");
                supplierRemark.append("电话: ");
                supplierRemark.append(a.getLinktel());
                //供应商信息备注
                t.setSupplierRemark(supplierRemark.toString());
                t.setShrink(a.getIsShrink());
                t.setSpecs(a.getSpecification());

                result = materialFIfoDubboService.insert(t);
            }

        }
        //面料资料
        else if (message.getTableType().compareTo(MaterialAccessoriesDTO.TableType.material.getValue()) == 0){

            Material material = materialDubboService.selectMaterialByUuid(message.getCode());

            //非入库的数据不同步
            if(material.getStatus() == null || Material.MaterialType.warehousing.getValue() != material.getStatus().intValue()){
                return 1;
            }

            if (material != null){

                MaterialMIfo t = new MaterialMIfo();

                t.setCode(material.getCode());
                t.setColorCode(material.getColor());
                t.setCreateTime(new Date());
                t.setOperSt(message.getOperSt());
                t.setName(material.getName());
                t.setUnit(material.getMeasurement());
                t.setImageSrc(material.getImage());
                t.setPrice(material.getPrice());
                t.setIfStatus(MaterialMIfo.StatusType.init.getValue());
                t.setUpdaterId(UPDATER_ID);
//                t.setType(material.getMaterielUuid());

                MaterielCategory var1 = new MaterielCategory();
                var1.setUuid(material.getMaterielUuid());
                MaterielCategory materielCategory = materielCategoryDubboService.selectOne(var1);
                if (materielCategory != null){
                    t.setType(materielCategory.getCode());
                }

                StringBuffer supplierRemark = new StringBuffer();

                supplierRemark.append("名称: ");
                supplierRemark.append(material.getSupplier());
                supplierRemark.append("; ");
                supplierRemark.append("地址: ");
                supplierRemark.append(material.getSupplierAddress());
                supplierRemark.append("; ");
                supplierRemark.append("联系人: ");
                supplierRemark.append(material.getLinkman());
                supplierRemark.append("; ");
                supplierRemark.append("手机: ");
                supplierRemark.append(material.getLinkphone());
                supplierRemark.append("; ");
                supplierRemark.append("电话: ");
                supplierRemark.append(material.getLinktel());
                //供应商信息备注
                t.setSupplierRemark(supplierRemark.toString());

                t.setBreadth(new BigDecimal(material.getLength()));
                t.setShrink(material.getIsShrink());
                t.setSafeInventory(material.getIsStock());
                t.setWeight(material.getWeight());

                result = materialMIfoDubboService.insert(t);
            }

        }
        return result;
    }
    private static final String UPDATER_ID = CommonUtils.updaterId;
}
