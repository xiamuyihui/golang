package com.kingthy.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingthy.dto.UserDTO;
import com.kingthy.entity.TbUser;
import com.kingthy.security.Audience;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * User:pany <br>
 * Date:2016-11-3 <br>
 * Time:16:44 <br>
 */
public class JwtUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    public static Claims parseJWT(String jsonWebToken, String base64Security)
    {
        try
        {
            long beginTime = System.currentTimeMillis();
            Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(jsonWebToken)
                .getBody();
            LOGGER.info("--->>>parseJWT 总耗时：" + (System.currentTimeMillis() - beginTime) + " 毫秒");
            return claims;

        }
        catch (Exception ex)
        {
            LOGGER.error("exception message is: " + ex.getMessage());
            return null;
        }
    }

    public static String createJWT(UserDTO loginParameter, TbUser user, Audience audience)
    {
        long ttlmillis = audience.getExpiresSecond() * 1000;
        String base64Security = audience.getBase64Secret();
        Date now = new Date(System.currentTimeMillis());

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .claim("unique_name", loginParameter.getPhone())
            // .claim("mobile", user.getMobile())
            .setIssuer(audience.getName())
            .setAudience(audience.getClientId())
            .signWith(SignatureAlgorithm.HS256, signingKey);

        // 添加Token过期时间
        if (ttlmillis >= 0)
        {
            Date exp = new Date(System.currentTimeMillis() + ttlmillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        // 生成JWT
        return builder.compact();
    }
}