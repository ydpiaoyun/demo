package com.yun.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class HashUtils {

    private static byte[] secret = new byte[0];

    public static int hash(String data) {
        return data.hashCode() & Integer.MAX_VALUE;
    }

    /**
     * @param clasims 数据声明
     * @return
     */
    public static String genToken(Map<String, Object> clasims) {
        return Jwts.builder()
                .setClaims(clasims)
                .setExpiration(generateExiprationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Claims genClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }


    private static Date generateExiprationDate() {
        return new Date();
    }
}