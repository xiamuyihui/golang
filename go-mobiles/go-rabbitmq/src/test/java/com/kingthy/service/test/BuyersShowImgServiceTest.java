package com.kingthy.service.test;

import com.kingthy.KingthyProviderRabbitmqApplication;
import com.kingthy.entity.SensitiveWord;
import com.kingthy.mapper.SensitiveWordMapper;
import com.kingthy.service.BuyersShowImgService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @AUTHORS xumin
 * @Description:
 * @DATE Created by 11:33 on 2017/5/17.
 * @Modified by:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderRabbitmqApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@Transactional
public class BuyersShowImgServiceTest {

    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;

    @Autowired
    private BuyersShowImgService buyersShowImgService;

    @Test
    public void saveSensitiveWord(){

        try{
            Set<String> set = new HashSet<String>();
            File file = new File("D:\\SensitiveWord.txt");    //读取文件
            InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");

            if(file.isFile() && file.exists()){      //文件流是否存在

                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            }

            else{         //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }

            for (String word : set){
                SensitiveWord sensitiveWord = new SensitiveWord();

                sensitiveWord.setCreateDate(new Date());
                sensitiveWord.setWord(word);
                sensitiveWord.setDelFlag(false);
                sensitiveWord.setVersion(0);

//                sensitiveWordMapper.insert(sensitiveWord);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Autowired
    private ElasticsearchOperations es;

    @Test
    public void testElasticsearch(){
        try {
//            System.out.println("-------");
            String goodsUuid = "97137901346757558";
            List<String> list = new ArrayList<>() ;
//            list.add("97137901346757558");
            list.add("97137901346757557");
            list.add("97137901346757553");
            list.add("97137901346757548");
            list.add("97137901346757547");
            list.add("97137901346757546");
            list.add("97100764777809188");
            list.add("97100764777809191");
            list.add("97100764777808606");
            list.add("97137901346757558");

            list.forEach(id -> {
                try {
//                    buyersShowImgService.testElasticsearch(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            buyersShowImgService.testElasticsearch("97100764777808606");

            /*System.out.println("--ElasticSearch-->");
            Client client = es.getClient();
            Map<String, String> asMap = client.settings().getAsMap();

            asMap.forEach((k, v) -> {
                System.out.println(k + " = " + v);
            });
            System.out.println("<--ElasticSearch--");*/
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }
}
