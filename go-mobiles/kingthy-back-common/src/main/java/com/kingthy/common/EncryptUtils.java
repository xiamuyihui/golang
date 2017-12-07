package com.kingthy.common;

import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类 Created by likejie on 2017/9/8.
 */
public class EncryptUtils
{
    
    private static final String encryptType = "AES";
    
    /**
     * 根据秘钥，初始化 AES Cipher
     * 
     * @param salt 秘钥
     * @param cipherMode
     * @return
     */
    public static Cipher initAESCipher(int cipherMode, String salt)
    {
        // 创建Key gen
        KeyGenerator keyGenerator;
        Cipher cipher = null;
        try
        {
            keyGenerator = KeyGenerator.getInstance(encryptType);
            keyGenerator.init(128, new SecureRandom(salt.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] codeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(codeFormat, encryptType);
            cipher = Cipher.getInstance(encryptType);
            // 初始化
            cipher.init(cipherMode, key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cipher;
    }
    
    /**
     *
     * 字符串加密
     * 
     * @param content 加密内容
     * @param salt 加密秘钥
     */
    public static String AESEncode(String content, String salt)
    {
        try
        {
            Cipher cipher = initAESCipher(Cipher.ENCRYPT_MODE, salt);
            // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            // 根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = new byte[0];
            if (null != cipher)
            {
                byte_AES = cipher.doFinal(byte_encode);
            }
            // 将加密后的数据转换为字符串
            String AES_encode = Base64.getEncoder().encodeToString(byte_AES);
            // 将字符串返回
            return AES_encode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 字符串解密
     * 
     * @param content 解密密内容
     * @param salt 秘钥
     **/
    public static String AESDncode(String content, String salt)
    {
        try
        {
            Cipher cipher = initAESCipher(Cipher.DECRYPT_MODE, salt);
            byte[] byte_content = Base64.getDecoder().decode(content);
            // 解密
            byte[] byte_decode = new byte[0];
            if (null != cipher)
            {
                byte_decode = cipher.doFinal(byte_content);
            }
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 对文件进行AES加密
     * 
     * @param bytes
     * @param fileName
     * @param salt
     * @return
     */
    public static File aes_encryptFile(byte[] bytes, String fileName, String salt)
    {
        
        // 新建临时加密文件
        File encrypfile = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        CipherInputStream cipherInputStream = null;
        try
        {
            inputStream = new ByteArrayInputStream(bytes);
            // 创建临时文件
            encrypfile = File.createTempFile(fileName, "");
            outputStream = new FileOutputStream(encrypfile);
            /** 初始化秘钥 **/
            Cipher cipher = initAESCipher(Cipher.ENCRYPT_MODE, salt);
            // 以加密流写入文件
            cipherInputStream = new CipherInputStream(inputStream, cipher);
            // 将加密过的流输出到文件
            byte[] cache = new byte[1024];
            int nRead;
            while ((nRead = cipherInputStream.read(cache)) != -1)
            {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != cipherInputStream)
                {
                    cipherInputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                if (null != inputStream)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                if (null != outputStream)
                {
                    outputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return encrypfile;
    }
    
    /**
     * AES方式解密文件
     * 
     * @param inputStream
     * @return
     */
    public static File aes_decryptFile(InputStream inputStream, String fileName, String salt)
    {
        File decryptFile = null;
        OutputStream outputStream = null;
        CipherOutputStream cipherOutputStream = null;
        try
        {
            /** 初始化秘钥 **/
            Cipher cipher = initAESCipher(Cipher.DECRYPT_MODE, salt);
            
            decryptFile = File.createTempFile(fileName, "");
            outputStream = new FileOutputStream(decryptFile);
            cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = inputStream.read(buffer)) >= 0)
            {
                cipherOutputStream.write(buffer, 0, r);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != cipherOutputStream)
                {
                    cipherOutputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                if (null != inputStream)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                if (null != outputStream)
                {
                    outputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return decryptFile;
    }
    
    // 生成随机数字和字母,
    public static String getStringRandom(int length)
    {
        
        String val = "";
        Random random = new Random();
        
        // 参数length，表示生成几位随机数
        for (int i = 0; i < length; i++)
        {
            
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum))
            {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            }
            else if ("num".equalsIgnoreCase(charOrNum))
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
}
