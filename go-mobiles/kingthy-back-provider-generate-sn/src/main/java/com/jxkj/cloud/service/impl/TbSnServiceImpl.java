package com.jxkj.cloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxkj.cloud.service.GenSnNumberHelper;
import com.jxkj.cloud.service.TbSnService;
import com.kingthy.entity.Sn;

@Service
public class TbSnServiceImpl implements TbSnService
{
    
    @Autowired
    private GenSnNumberHelper genSnNumberHelper;
    
    @Override
    public String generateSn(String type)
    {
        // TODO Auto-generated method stub
        switch (type)
        {
            case "goods":
                return genSnNumberHelper.generate(Sn.Type.goods);
            case "order":
                return genSnNumberHelper.generate(Sn.Type.order);
            case "payment":
                return genSnNumberHelper.generate(Sn.Type.payment);
            case "refunds":
                return genSnNumberHelper.generate(Sn.Type.refunds);
            case "shipping":
                return genSnNumberHelper.generate(Sn.Type.shipping);
            case "product":
                return genSnNumberHelper.generate(Sn.Type.product);
            case "opus":
                return genSnNumberHelper.generate(Sn.Type.opus);
            case "shoppingCat":
                return genSnNumberHelper.generate(Sn.Type.shoppingCat);
            case "accessories":
                return genSnNumberHelper.generate(Sn.Type.accessories);
            case "material":
                return genSnNumberHelper.generate(Sn.Type.material);
        }
        return "error";
    }
    
}
