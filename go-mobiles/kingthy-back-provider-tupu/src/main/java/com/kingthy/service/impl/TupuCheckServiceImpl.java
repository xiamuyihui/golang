package com.kingthy.service.impl;

import com.kingthy.conf.Api;
import com.kingthy.service.TupuCheckService;
import com.kingthy.util.ConfigUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("tupuCheckService")
public class TupuCheckServiceImpl implements TupuCheckService
{
    public static final String privateKey = TupuCheckServiceImpl.class.getResource("/pkcs8_private_key.pem").getPath();

    public static final String secretId = "5926808ef1813f8ba9a80e07";

    //public String privateKey = "/pkcs8_private_key.pem";

    public static final String requestUrl = "http://api.open.tuputech.com/v3/recognition/";

    @Override
    public JSONObject checkImager(List<String> urlpath)
    {

        // fileList imageFile or url
        ArrayList<String> fileList = (ArrayList)urlpath;
        // tags
        String[] tags = {"name"};

        String privateKeyPath = privateKey.substring(1);
        privateKeyPath = privateKeyPath.replace("/","\\");
        Api api = new Api(secretId, privateKeyPath, requestUrl);
        return api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_IMAGE_TYPE, fileList, tags);
    }

    @Override
    public JSONObject checkImagerUrl(List<String> urlpath)
    {
        ArrayList<String> fileList = (ArrayList)urlpath;
        // tags
        String tags[] = {"name"};

        String privateKeyPath = privateKey.substring(1);
        privateKeyPath = privateKeyPath.replace("/","\\");
        Api api = new Api(secretId, privateKeyPath, requestUrl);


        return api.doApiTest(ConfigUtil.UPLOAD_TYPE.UPLOAD_URI_TYPE, fileList, tags);
    }

    /*public static void main(String[] args)
        throws FileNotFoundException
    {
        //String privateKey = "/pkcs8_private_key.pem";
        String privateKey = TupuCheckServiceImpl.class.getResource("/pkcs8_private_key.pem").getPath();
        String privateKeyPath = privateKey.substring(1);
        privateKeyPath = privateKeyPath.replace("/","\\");
        System.out.println(privateKeyPath);
        File private_key_pem = new File(privateKeyPath);
        InputStream inPrivate = new FileInputStream(private_key_pem);
        System.out.println(inPrivate.toString());
    }*/

}
