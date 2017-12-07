package com.kingthy.unionpay;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingthy.unionpay.sdk.AcpService;
import com.kingthy.unionpay.sdk.LogUtil;
import com.kingthy.unionpay.sdk.SdkConstants;

/**
 * @author
 * 重要：联调测试时请仔细阅读注释！
 * 
 * 产品：代付产品<br>
 * 功能：后台通知接收处理示例 <br>
 * 日期： 2015-09<br>
 * 版本： 1.0.0 版权： 中国银联<br>
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 * 该接口参考文档位置：open.unionpay.com帮助中心 下载 产品接口规范 《代付产品接口规范》，<br>
 * 《平台接入接口规范-第5部分-附录》（内包含应答码接口规范，全渠道平台银行名称-简码对照表）， 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案： 调试过程中的问题或其他问题请在
 * https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 * 测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案 2） 咨询在线人工支持：
 * open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。 交易说明： 前台类交易成功才会发送后台通知。后台类交易（有后台通知的接口）交易结束之后成功失败都会发通知。
 * 为保证安全，涉及资金类的交易，收到通知后请再发起查询接口确认交易成功。不涉及资金的交易可以以通知接口respCode=00判断成功。
 * 未收到通知时，查询接口调用时间点请参照此FAQ：https://open.unionpay.com/ajweb/help/faq/list?id=77&level=0&from=0
 */
public class BackRcvResponse extends HttpServlet
{
    
    @Override
    public void init()
        throws ServletException
    {
        /**
         * 请求银联接入地址，获取证书文件，证书路径等相关参数初始化到SDKConfig类中 在java main 方式运行时必须每次都执行加载 如果是在web应用开发里,这个方法可使用监听的方式写入缓存,无须在这出现
         */
        // 这里已经将加载属性文件的方法挪到了web/AutoLoadServlet.java中
        // SdkConfig.getConfig().loadPropertiesFromSrc(); //从classpath加载acp_sdk.properties文件
        super.init();
    }
    
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		String encoding = req.getParameter(SdkConstants.PARAM_ENCODING);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				
				valideData.put(key, value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			if(null!= valideData){

				//获取后台通知的数据，其他字段也可用类似方式获取
                String orderId =valideData.get("orderId");

				String respCode = valideData.get("respCode");
			}
			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
			
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200  状态码
		resp.getWriter().print("ok");
	}
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        this.doPost(req, resp);
    }
    
    /**
     * 获取请求参数中所有的信息
     * 
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request)
    {
        Map<String, String> res = new HashMap<>(1);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp)
        {
            while (temp.hasMoreElements())
            {
                String en = (String)temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en)))
                {
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
