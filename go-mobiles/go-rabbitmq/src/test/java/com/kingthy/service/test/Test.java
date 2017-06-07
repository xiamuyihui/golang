package com.kingthy.service.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.entity.EsGoods;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHORS xumin
 * @Description:
 * @DATE Created by 19:20 on 2017/5/22.
 * @Modified by:
 */
public class Test {
    public static void main(String[] args) {
        String sql = "[{\"opusImage\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008.jpg\",\"maxImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008_500x500.jpg\",\"minImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008_50x50.jpg\",\"midImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008_200x200.jpg\",\"isCover\":true},{\"opusImage\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072.jpg\",\"maxImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072_500x500.jpg\",\"minImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072_50x50.jpg\",\"midImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072_200x200.jpg\",\"isCover\":false},{\"opusImage\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282.jpg\",\"maxImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282_500x500.jpg\",\"minImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282_50x50.jpg\",\"midImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282_200x200.jpg\",\"isCover\":false}]";


        String sql2 = "[{\"image\":[{\"image\":\"http://192.168.1.217/group1/M00/00/16/wKgB21kQ_8mAEMPKAAId_Mq98Rg134.jpg\"}]," +
                "\"partsubStatus\":\"\",\"name\":\"黄色弹力绳\",\"uuid\":\"97071061572517922\"}]";

//        List<EsGoods.GoodsImage> goodsImages = JSON.parseArray(sql, EsGoods.GoodsImage.class);
//        JSON.parseObject(sql2, EsGoods.GoodsImage.class)

        String sql3 = " {\"uuid\":\"97071061572518338\",\"createDate\":1492684084000,\"goodsName\":\"春季的新服装\",\"goodsFeature\":\"必备的春夏新装\",\"cover\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008_50x50.jpg\",\"clicks\":7,\"standardPrice\":499.99,\"goodsStyleUuid\":\"97071061572518328\",\"materielInfo\":[{\"uuid\":null,\"name\":\"碎花纹涤棉\",\"image\":\"http://192.168.1.217/group1/M00/00/15/wKgB21jztL-AITUGAAvqH_kipG8997.jpg\"},{\"uuid\":null,\"name\":\"花纹斜纹棉\",\"image\":\"http://192.168.1.217/group1/M00/00/15/wKgB21jztL-AITUGAAvqH_kipG8997.jpg\"}],\"goodsImage\":[{\"opusImage\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008.jpg\",\"maxImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008_500x500.jpg\",\"minImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008_50x50.jpg\",\"midImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-jjGAaaq_AAJ-apOgyRA008_200x200.jpg\",\"cover\":true},{\"opusImage\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072.jpg\",\"maxImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072_500x500.jpg\",\"minImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072_50x50.jpg\",\"midImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB2lj-ld6AdqdfAABDYvtAT0M072_200x200.jpg\",\"cover\":false},{\"opusImage\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282.jpg\",\"maxImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282_500x500.jpg\",\"minImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282_50x50.jpg\",\"midImg\":\"http://192.168.1.217/group1/M00/00/15/wKgB21kHiKCATlPUAAiQfKHDaaQ282_200x200.jpg\",\"cover\":false}],\"goodsCategoryUuid\":\"97071061572518316\"}";

        Gson gson = new Gson();
//        EsGoods.GoodsImage g = gson.fromJson(sql2, EsGoods.GoodsImage.class) ;

//        List<EsGoods.GoodsImage> goodsImages = gson.fromJson(sql, List.class);

//        EsGoods egoods = gson.fromJson(sql3, EsGoods.class);

//        System.out.println(goodsImages);
//        System.out.println(egoods);


        Type type2 = new TypeToken<ArrayList<EsGoods.MaterielInfo>>(){}.getType();

//        List<EsGoods.MaterielInfo> materielInfos = new ArrayList<>();

        List<EsGoods.MaterielInfo> materielInfos = gson.fromJson(sql2, type2);

        System.out.println(materielInfos.size());
    }
}
