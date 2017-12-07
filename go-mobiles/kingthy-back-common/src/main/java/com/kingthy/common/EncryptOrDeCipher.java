package com.kingthy.common;

import ma.glasnost.orika.impl.generator.specification.Convert;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 加密解密函数 EncryptOrDeCipher
 *
 * EncryptOrDeCipher
 * 
 * 潘勇 2017年1月13日 下午2:47:50
 * 
 * @version 1.0.0
 *
 */
public class EncryptOrDeCipher
{
    private static final String SALT = "zcbdcyjq";// 私钥 AES固定格式为128/192/256
    
    private static final String encryptType = "AES";
    
    /**
     * 加密处理 aes_encrypt(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param password
     * @return String
     * @author pany
     * @exception @since 1.0.0
     */
    public static String aes_encrypt(String password)
    {
        try
        {
            byte[] keyBytes = Arrays.copyOf(SALT.getBytes("ASCII"), 16);
            
            SecretKey key = new SecretKeySpec(keyBytes, encryptType);
            Cipher cipher = Cipher.getInstance(encryptType);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] cleartext = password.getBytes("UTF-8");
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            
            return new String(Hex.encodeHex(ciphertextBytes)).toUpperCase();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密处理 aes_decrypt(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param encryptResult
     * @return String
     * @author pany
     * @exception @since 1.0.0
     */
    public static String aes_decrypt(String encryptResult)
    {
        try
        {
            byte[] keyBytes = Arrays.copyOf(SALT.getBytes("ASCII"), 16);
            
            SecretKey key = new SecretKeySpec(keyBytes, encryptType);
            Cipher cipher = Cipher.getInstance(encryptType);
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            byte[] cleartext = Hex.decodeHex(encryptResult.toCharArray());
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            
            return new String(ciphertextBytes, "UTF-8");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }







    public static void main(String[] args)
    {


        // String content = "2088222172464839";
        // 加密
        // System.out.println("加密前：" + content);
        // String encryptResult = aes_encrypt(content);
        // System.out.println("加密后：" + encryptResult);
        // 解密
        String content2 =
            "B7D178D1F3BB8F78808FF2FA9E8301F72BA597A548D6B7B81031263B057EFA8F68595E4B99315FE00BE97AFAF8899336DBD188AD1A31E134DFDF52133077677CD2D839EBA3292E055E3DAA71B77D8230AD08C8922E262055D05B2B30A46C3A68CCC67E0E69A8A8AA0884D5AB2CAB8ED800848763DA4049508A64A6B90397162BFC8E0E5F0A3B667E704EAA543D770BFE0A5B58DE684F72912BB7A8D3C390BBC3BB4F44B9144886B998F80F51E9BE64EEBF4187F454B0FE86FD16F179D5203A9FC929465DBBDE2E5F0251EA2C0D3AD9765EA34523CFE022FD0681E01DDD9184867347DB02B9A677F18639B8DEEA20739F45E71C2BBFF414400CAE304C01754BF3BC76BF1628EBA34BF7FCE959E35C334AC5CA7334D3C1E6956DF0A4AF41E683156F1120E9FD07C4C37C9C085B98A59375CB79EA61C692FF079ED86DEA0F12DF0000067EA7F2E1F7E3F2A5330191FAB0B47913E887EA1B14477EEEBF938B73508333B47251BE357656E00EA1A1BAE9E4832268B63866317E8DBFDBC32C933C82C1116D146F3D1E2C2EBA9031D5B52F227367BD9DE32E2C2655E0A99164F783F8CB07518D8F859FEB875C04C5054968E40E33660128BDCEB96E4BF96B782ACBFA556BD8C78A03346F1D165874EB825A1EE6236AB67EF3798B578FB57F6A1CE73195531CC96040547354A3EA087467CFF74BEC5653521027B48139DE3E8239BDAAF996B825BAEC45481227FAAB7D10D055A2774980AFFE29F7FA08ABFFB3B27AF90B045EF6FE74538630AC73D4722B3D6A5071139D918C998299E42F12854CB21F305D8180E8FCEFE13DF89AF331FC1B0F6017A2C5119FA9A9E2FB3B30A587D10B4FC7332793810CE709A701EFD4857CB75083BA1CAB06A40CEE7D51D914AA610545FF2A864DA4AE371135FD3165246931AFB93FB15FE021A0E5EE27FD4A80ED2B7FED81FC70A7A46A62B59BB7F756A5BC274C512C345B3A8A5D7F1D9F235A3A45880D00C2F54A9B3A957C96CD27D0B0BE52E5FCA4B29A226A69E6661694214DD719FDD44BB6F4245A50DB849D789709B4E590C80D07283AD4DF6D95F0A4D265A1BA426E470252C58B2A83F7CB81D259531FA2215D8BB4DD5713474B05062A2F928822ED240A421AA41C24EFAF9B329BF1FA43A1FAD808D803C7D28DF3C33B457253121B1031D32C63D3649AB014D0732A543700D8D193D8730344830EDCF2D4E49F";
        // String decryptResult = aes_decrypt(encryptResult);
        String decryptResult = aes_decrypt(content2);
        System.out.println("解密后：" + decryptResult);
        
        // SELECT HEX(aes_encrypt("password", "0123456789012345"));
        // SELECT aes_decrypt(UNHEX("E75C7C56AFB3EA4360A9856456F1C8A2"), "0123456789012345");
    }
    
}
