package com.jxkj.cloud.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jxkj.cloud.util.FreeMarkerUtils;
import com.jxkj.cloud.util.JedisLock;
import com.kingthy.dubbo.order.service.SnDubboService;
import com.kingthy.entity.Sn;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 订单号生成
 * 
 * @author pany
 * 
 */
@Service
@PropertySource("classpath:resource.properties")
public class GenSnNumberHelper implements InitializingBean
{
//    @Autowired
//    private SnMapper tbSnMapper;

    @Reference(version = "1.0.0", timeout = 3000)
    private SnDubboService snDubboService;

    @Autowired
    private Environment environment;

    @Autowired
    private JedisLock jedisLock;
    
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    class HiloOptimizer
    {
        private Sn.Type type;
        
        private String prefix = "";// sn的前缀，如'201312'
        
        private int maxLo = 0;// 商品数量基数，如外面设置为100，则sn后三位从000到999
        
        private int middleValue = 0;//
        
        private long firstValue = 0l;//
        
        private long lastValue = 0l;//
        
        public HiloOptimizer(Sn.Type type, String prefix, int maxLo)
        {
            this.type = type;
            this.prefix = (prefix != null ? prefix.replace("{", "${") : "");
            this.maxLo = maxLo;
            this.middleValue = (maxLo + 1);
        }
        
        public String generate(Sn.Type type)
        {
            try
            {
                /*lock.writeLock().lock();

                try {
                    jedisLock.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                if (this.middleValue > this.maxLo)
                {
                    this.lastValue = getLastValue(this.type);
                    this.middleValue = (this.lastValue == 0L ? 1 : 0);
                    this.firstValue = (this.lastValue * (this.maxLo + 1));
                }
                return FreeMarkerUtils.renderString(this.prefix, null) + (this.firstValue + this.middleValue++)
                    + type.getValue();
            }
            finally
            {

                /*jedisLock.release();
                lock.writeLock().unlock();*/
            }
        }
    }
    
    private long getLastValue(Sn.Type type)
    {

        long lastValue = 0;

        /*
        Example snExample = new Example(Sn.class);
        Example.Criteria criteria = snExample.createCriteria();
        criteria.andEqualTo("type", Integer.valueOf(type.getValue()));
        Sn sn = tbSnMapper.selectByExample(snExample).get(0);
        */

        Sn var = new Sn();
        var.setType(Integer.valueOf(type.getValue()));
        Sn sn = snDubboService.selectOne(var);

        if (sn != null)
        {
            lastValue = sn.getLastValue();
        }
        
//        sn.setLastValue(lastValue + 1);
//        tbSnMapper.updateByPrimaryKeySelective(sn);
//        tbSnMapper.updateLastValue(sn);
        snDubboService.updateLastValue(sn);
        return lastValue;
    }
    
    private HiloOptimizer goodsHiloOptimizer = null;
    
    private HiloOptimizer orderHiloOptimizer = null;
    
    private HiloOptimizer paymentHiloOptimizer = null;
    
    private HiloOptimizer refundsHiloOptimizer = null;
    
    private HiloOptimizer shippingHiloOptimizer = null;
    
    private HiloOptimizer productHiloOptimizer = null;
    
    private HiloOptimizer opusHiloOptimizer = null;
    
    private HiloOptimizer shoppingCatHiloOptimizer = null;
    
    private HiloOptimizer accessoriesHiloOptimizer = null;

    private HiloOptimizer materialHiloOptimizer = null;

    private HiloOptimizer balanceHiloOptimizer = null;
    
    @Value("${sn.goods.prefix}")
    private String snGoodsPrefix;// = environment.getProperty("sn.goods.prefix");
    
    @Value("${sn.goods.maxLo}")
    private int snGoodsMaxLo;// = Integer.valueOf(environment.getProperty("sn.goods.prefix"));
    
    @Value("${sn.order.prefix}")
    private String snOrderPrefix;// = environment.getProperty("sn.order.prefix");;
    
    @Value("${sn.order.maxLo}")
    private int snOrderMaxLo;// = Integer.valueOf(environment.getProperty("sn.order.maxLo"));;
    
    @Value("${sn.payment.prefix}")
    private String snPaymentPrefix;// = environment.getProperty("sn.payment.prefix");
    
    @Value("${sn.payment.maxLo}")
    private int snPaymentMaxLo;// = Integer.valueOf(environment.getProperty("sn.payment.maxLo"));;
    
    @Value("${sn.refunds.prefix}")
    private String snRefundsPrefix;// = environment.getProperty("sn.refunds.prefix");
    
    @Value("${sn.refunds.maxLo}")
    private int snRefundsMaxLo;// = Integer.valueOf(environment.getProperty("sn.refunds.maxLo"));;
    
    @Value("${sn.shipping.prefix}")
    private String snShippingPrefix;// = environment.getProperty("sn.shipping.prefix");
    
    @Value("${sn.shipping.maxLo}")
    private int snShippingMaxLo;// = Integer.valueOf(environment.getProperty("sn.shipping.maxLo"));;
    
    @Value("${sn.product.prefix}")
    private String snProductPrefix;// = environment.getProperty("sn.product.prefix");
    
    @Value("${sn.product.maxLo}")
    private int snProductMaxLo;// = Integer.valueOf(environment.getProperty("sn.product.maxLo"));;
    
    @Value("${sn.opus.prefix}")
    private String snOpusPrefix;// = environment.getProperty("sn.goods.prefix");
    
    @Value("${sn.opus.maxLo}")
    private int snOpusMaxLo;// = Integer.valueOf(environment.getProperty("sn.opus.maxLo"));;
    
    @Value("${sn.shoppingCat.prefix}")
    private String snShoppingCatPrefix;// = environment.getProperty("sn.shoppingCat.prefix");
    
    @Value("${sn.shoppingCat.maxLo}")
    private int snShoppingMaxLo;// = Integer.valueOf(environment.getProperty("sn.shoppingCat.maxLo"));;

    // @Value("${sn.balance.prefix}")
    private String snBalancePrefix;
    
    // @Value("${sn.balance.maxLo}")
    private int snBalanceMaxLo;

    @Value("${sn.accessories.prefix}")
    private String snAccessoriesPrefix;// = environment.getProperty("sn.accessories.prefix");

    @Value("${sn.accessories.maxLo}")
    private int snAccessoriesMaxLo;// = Integer.valueOf(environment.getProperty("sn.accessories.maxLo"));;

    @Value("${sn.material.prefix}")
    private String snMaterialPrefix;// = environment.getProperty("sn.material.prefix");

    @Value("${sn.material.maxLo}")
    private int snMaterialMaxLo;// = Integer.valueOf(environment.getProperty("sn.material.maxLo"));;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    public void afterPropertiesSet()
        throws Exception
    {
        goodsHiloOptimizer = new HiloOptimizer(Sn.Type.goods, snGoodsPrefix, snGoodsMaxLo);
        orderHiloOptimizer = new HiloOptimizer(Sn.Type.order, snOrderPrefix, snOrderMaxLo);
        paymentHiloOptimizer = new HiloOptimizer(Sn.Type.payment, snPaymentPrefix, snPaymentMaxLo);
        refundsHiloOptimizer = new HiloOptimizer(Sn.Type.refunds, snRefundsPrefix, snRefundsMaxLo);
        shippingHiloOptimizer = new HiloOptimizer(Sn.Type.shipping, snShippingPrefix, snShippingMaxLo);
        productHiloOptimizer = new HiloOptimizer(Sn.Type.product, snProductPrefix, snProductMaxLo);
        opusHiloOptimizer = new HiloOptimizer(Sn.Type.opus, snOpusPrefix, snOpusMaxLo);
        shoppingCatHiloOptimizer = new HiloOptimizer(Sn.Type.opus, snShoppingCatPrefix, snShoppingMaxLo);
        balanceHiloOptimizer = new HiloOptimizer(Sn.Type.BALANCE, snBalancePrefix, snBalanceMaxLo);
        accessoriesHiloOptimizer = new HiloOptimizer(Sn.Type.accessories, snAccessoriesPrefix, snAccessoriesMaxLo);
        materialHiloOptimizer = new HiloOptimizer(Sn.Type.material, snMaterialPrefix, snMaterialMaxLo);
    }
    
    public String generate(Sn.Type type)
    {
        Assert.notNull(type);
        if (type == Sn.Type.goods)
        {
            return this.goodsHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.order)
        {
            return this.orderHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.payment)
        {
            return this.paymentHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.refunds)
        {
            return this.refundsHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.shipping)
        {
            return this.shippingHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.product)
        {
            return this.productHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.opus)
        {
            return this.opusHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.shoppingCat)
        {
            return this.shoppingCatHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.BALANCE)
        {
            return this.balanceHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.accessories)
        {
            return this.accessoriesHiloOptimizer.generate(type);
        }
        if (type == Sn.Type.material)
        {
            return this.materialHiloOptimizer.generate(type);
        }
        return null;
    }
    
}
