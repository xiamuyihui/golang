package sign;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付时签名
 *
 * SignUtils
 * 
 * 潘勇 2017年1月13日 下午11:45:41
 * 
 * @version 1.0.0
 *
 */
public class SignUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(SignUtils.class);
    
    private static final String ALGORITHM = "RSA";
    
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    
    public static String sign(String content, String privateKey)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            
            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            
            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e)
        {
            LOG.error("签名出现错误:", e);
        }
        
        return null;
    }
    
}
