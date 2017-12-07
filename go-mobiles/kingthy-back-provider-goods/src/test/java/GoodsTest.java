import com.kingthy.KingthyProviderGoodsApplication;
import com.kingthy.dto.MaterielAndStyleDTO;
import com.kingthy.request.GoodsClickReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GoodsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GoodsTest(描述其作用)
 * <p>
 * 赵生辉 2017年05月15日 16:00
 *
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderGoodsApplication.class,properties = "eureka.client.enabled=false")
@Transactional
@Rollback
public class GoodsTest
{

    @Autowired
    public GoodsService goodsService;

    @Test
    public void downGoods()
    {
        //int result = goodsService.downGoods("97100764777809191");
        //System.out.println(result);
    }
    /*@Test
    public void createGoods()
    {
        Goods goods = new Goods();
        int result = goodsService.createGoods(goods);
    }
*/

    @Test
    public void queryMaterielAndStyle(){

        try{
            WebApiResponse<?> response = goodsService.queryMaterielAndStyle();

            Assert.assertTrue("成功", response.getCode() == 0);

            if (response.getCode() == 0 || !StringUtils.isEmpty(response.getData())){
                MaterielAndStyleDTO s = (MaterielAndStyleDTO) response.getData();
                Assert.assertTrue("success", s.getMateriel().size() > 0);
                Assert.assertTrue("success", s.getStyle().size() > 0);
            }
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }

    }

    @Test
    public void goodsClick(){

        try {
            List<GoodsClickReq> goodsClickReq = new ArrayList<>();
            String [] goodsIds = {"97137901346757849", "97137901346757848", "97137901346757846"};
            Random random = new Random();
            for (String goodsId : goodsIds){

                GoodsClickReq req = new GoodsClickReq();
                req.setGoodsUuid(goodsId);
                req.setClickCount(random.nextInt(10) + 5);

                goodsClickReq.add(req);
            }

            WebApiResponse<?> response = goodsService.goodsClick(goodsClickReq);

            Assert.assertTrue("成功", response.getCode() == 0);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }
}
