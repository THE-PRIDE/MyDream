package com.dream.lmy.mydream.encryptUtils.util;

import android.util.Base64;

import com.dream.lmy.mydream.common.PrintTimeUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertUtil {

    /**
     * 读取文件中的私钥
     *
     * @param alias    别名
     * @param privateKey   开发者私钥
     * @param password 密码
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     * @throws UnrecoverableKeyException
     */
    public static PrivateKey readPrivateKey(String alias, String privateKey, String password) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
        PrintTimeUtils.getCurrentTime();
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        PrintTimeUtils.getCurrentTime();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(privateKey,Base64.DEFAULT));
        PrintTimeUtils.getCurrentTime();
        keyStore.load(byteArrayInputStream, password.toCharArray());
        PrintTimeUtils.getCurrentTime();
        byteArrayInputStream.close();
        PrintTimeUtils.getCurrentTime();
        return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
    }

    /**
     * 读取X.509证书
     *
     * @param certBase64 证书路径
     * @return
     * @throws CertificateException
     * @throws IOException
     */
    public static X509Certificate readX509Certificate(String certBase64)
            throws CertificateException, IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(certBase64,Base64.DEFAULT));
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(byteArrayInputStream);
        byteArrayInputStream.close();
        return cert;
    }
}
