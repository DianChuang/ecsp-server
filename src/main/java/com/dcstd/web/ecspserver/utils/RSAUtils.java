package com.dcstd.web.ecspserver.utils;

import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.exception.CustomException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtils {
    static final String ALGORITHM = "RSA";

    static GlobalConfiguration staticGlobalConfiguration;

    @Resource
    GlobalConfiguration globalConfiguration;

    @PostConstruct
    public void init() {
        staticGlobalConfiguration = globalConfiguration;
    }


    /**
     * 从文件中读取公钥为PublicKey对象
     * @param filename 公钥保存路径，相对于classpath
     * @return 公钥对象
     * @throws Exception 读取公钥抛出的异常类型
     */
    public static PublicKey getPublicKey(String filename) throws Exception {
        System.out.println("0");
        byte[] bytes = FileIOUtils.readBytesFromFile(filename);

        byte[] decodeBytes = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodeBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePublic(spec);
    }

    /**
     * 从文件中读取私钥为PrivateKey对象
     * @param filename 私钥保存路径，相对于classpath
     * @return 私钥对象
     * @throws Exception 读取私钥抛出的异常类型
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] bytes = FileIOUtils.readBytesFromFile(filename);
        System.out.println(bytes);
        byte[] decodeBytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodeBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePrivate(spec);
    }



    /**
     * RSA公钥加密
     *
     * @param plainText 明文
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String plainText) throws Exception {
        String publicKeyPath = staticGlobalConfiguration.getFilePath() + staticGlobalConfiguration.getPublicFileName();
        System.out.println(publicKeyPath);
        // base64编码的公钥
        PublicKey publicKey = getPublicKey(publicKeyPath);
        // RSA加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherBytes);
    }

    /**
     * RSA私钥解密
     *
     * @param cipherText 密文
     * @return 明文
     */
    public static String decrypt(String cipherText) throws Exception {
        String privateKeyPath = staticGlobalConfiguration.getFilePath() + staticGlobalConfiguration.getPrivateFileName();
        // 64位解码加密后的字符串
        byte[] inputBytes;
        inputBytes = Base64.getDecoder().decode(cipherText.getBytes(StandardCharsets.UTF_8));
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        // RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(inputBytes));
    }
}
