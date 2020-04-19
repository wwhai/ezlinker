package com.ezlinker.app.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.ezlinker.app.common.exception.TokenException;
import com.ezlinker.app.common.exception.XException;

/**
 * 组件的Token生成器
 * 核心设计思路：使用Base64密钥加密ID
 * Base64.decode(token)-->array[]=[clientId,Entry]
 */
public class ModuleTokenUtil {
    private static final RSA rsa = new RSA(
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJjaCAPbxJNLRxBRdHUcdbrfdMCtm3rRXhpJ4t9qyCYLHiX6rRl+zy3Vk6hzpix1BBrJBci2P3lA6HeVve5bZ4englbXj+VtYvY/tlzir84/o5Nus14BihzFHILzhZbpmZ1Qz6hzy14beU4roFFfiQJ1aH+f1B5OTulRXPidQSEpAgMBAAECgYBh0Z6xgXKC5Pj8i3RhAi6hwiGAsVnANr7nSgOkAT1Hg3Pu0Eb4+vtxlK4jbqeY6eYPkjheY6upWI2tAiqDoQFWgmr4PRy4gCJLYVznvi/BQ4BmuxRB5ipTYlHAKuXyTC6gTWXKhbG4YpOgZC+Jc7Wtw5yICBJUmvRmdhotEA8XgQJBAOQk3L/zNys3FPq4/XvYw8k1XFO9eXh/b4KAU78+CFulBRQvJ7jHcF/AY7yac2VaHjxLiBUi3xcAMMyHHVT015kCQQCrg75q9bgo5ZK3LAkhP1V+xse4Uq7ZhnPhY0NsFHLMuK7L3uPx7gvGQedgdzxEeMy3LfMvoiwof+cbMVuZ91ARAkBTz+b47VS7H4UphoQ41y014dCFQvo49jf07UfW3eccI8d13szs6PezdW4uf18dQLoS1Hytpx3lyUAkIw73gushAkEAjOJt4pwuhuF076NraHUGvFl6Kq04VRP9UfFgq4Hsfr9hTbOr18lqwJLYBURMLA5yUjGbZdA3p5jQdGTJ0qH0kQJAEZoeDclMZwq2YZR/+BHtEzQJb0mS1BaHdchx/CEPEaPovLI4UbStc9NrUu9j0x/YuG1odc9cJpC3SHIwL079UA==",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY2ggD28STS0cQUXR1HHW633TArZt60V4aSeLfasgmCx4l+q0Zfs8t1ZOoc6YsdQQayQXItj95QOh3lb3uW2eHp4JW14/lbWL2P7Zc4q/OP6OTbrNeAYocxRyC84WW6ZmdUM+oc8teG3lOK6BRX4kCdWh/n9QeTk7pUVz4nUEhKQIDAQAB"
    );


    private static byte[] enRSA(String content) {
        return rsa.encrypt(content.getBytes(CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);

    }

    private static byte[] deRSA(byte[] bytes) {
        return rsa.decrypt(bytes, KeyType.PrivateKey);
    }


    public static String token(String componentId) {
        byte[] encrypt = enRSA(componentId);
        return Base64.encode(encrypt);
    }

    /**
     * 从token解析出用户的信息
     *
     * @param token
     * @return
     */
    public static String parse(String token) throws XException {
        try {
            byte[] decrypt = Base64.decode(token);
            return new String(deRSA(decrypt), CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            throw new TokenException("Invalid token", "Token不合法");
        }

    }

    public static void main(String[] args) throws XException {
        String id = "1111";
        String token = token(id);
        System.out.println("token:" + token);
        String d = parse("AWb4K54RQzWeH7dH/TAvi5VVT3TBfNqBIfPb8g3m4HNIKeh2QbU8NreAgfczO19YWJo4pEbuxeLGyS1hag2cUjC8Ya+911cdeCXojRf4HSvz5OPxnL/ABUmrc3om1+7cxkKN+Wnyb4iGkdVheHkdnlNy6xlF0nc5k6EtxxpJvMY=");
        System.out.println("id:" + d);
    }
}
