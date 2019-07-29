package com.dream.lmy.mydream.encryptUtils.util;

import android.util.Base64;

import com.dream.lmy.mydream.common.Logger;
import com.dream.lmy.mydream.encryptUtils.constants.SecurityConstants;

import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * 加解密工具
 */
public class EncryptUtils {

    private static String TAG = EncryptUtils.class.getSimpleName();
    private static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 签名数据
     *
     * @param dataStr 待签名数据
     * @return 签名及加密结果
     * @throws Exception 异常
     */
    public static String encryptString(String dataStr) {
        //对json进行加密
        Logger.error(TAG, "明文：" + dataStr);
        String encryptData = encryptMode(SecurityConstants.getInstance().getSecretKey(), dataStr);
        Logger.error(TAG, "加密后的密文：" + encryptData);
        //对加密后的数据加签
        String signature = null;
        if (encryptData != null) {
            try {
                signature = sign(encryptData.getBytes(DEFAULT_CHARSET), SecurityConstants.getInstance().getDevCert(),
                        SecurityConstants.getInstance().getCertAlias(), SecurityConstants.getInstance().getDevPrivateKey(), SecurityConstants.getInstance().getKeyPassword());
            } catch (Exception e) {
                Logger.error(TAG, "签名失败:" + e.toString());
                return "";
            }
        }
        Logger.error(TAG, "签名域：" + signature);
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(encryptData).append("{Signature=").append(signature).append("}");
        Logger.error(TAG, "最终密文：" + stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * 验证签名
     *
     * @param resData 待验签数据
     * @return 验签及解密结果
     * @throws Exception 异常
     */
    public static String decryptString(String resData) {
        Logger.error(TAG, "返回数据：" + resData);
        String placeholder = "{Signature=";
        if (resData.contains(placeholder)) {
            String signatureRes = resData.substring(resData.indexOf(placeholder) + placeholder.length(), resData.lastIndexOf("}"));
            resData = resData.substring(0, resData.indexOf(placeholder));
            Logger.error(TAG, "返回结果：" + resData);
            //验证签名
            boolean verify = false;
            try {
                verify = verifySign(resData.getBytes(), signatureRes, SecurityConstants.getInstance().getPlatformCert());
            } catch (Exception e) {
                Logger.error(TAG, "验签失败:" + e.toString());
                return "";
            }
            Logger.error(TAG, "验签结果：" + verify);
            //解密
            String decryptResult = decryptMode(SecurityConstants.getInstance().getSecretKey(), resData);
            Logger.error(TAG, "解密结果：" + decryptResult);
            return decryptResult;
        }
        return "";
    }

    /**
     * 3DES加密
     *
     * @param keyByte 秘钥
     * @param data    加密数据
     * @return String 密文
     */
    private static String encryptMode(SecretKey keyByte, String data) {
        try {
            byte[] bytes = data.getBytes();
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, keyByte);
            byte[] encrypt = cipher.doFinal(bytes);
            return Base64.encodeToString(encrypt, Base64.DEFAULT).replace("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "加密失败:" + e.toString());
        }
        return null;
    }

    /**
     * 3DES解密
     *
     * @param keyByte 密钥
     * @param data    需要解密的数据
     * @return String 明文
     */
    private static String decryptMode(SecretKey keyByte, String data) {
        try {
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, keyByte);
            byte[] result = c1.doFinal(Base64.decode(data, Base64.DEFAULT));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "解密失败:" + e.toString());

        }
        return null;
    }

    /**
     * 签名
     *
     * @param data       加签数据
     * @param certBase64 开发者公钥
     * @param alias      证书别名
     * @param privateKey 开发者私钥
     * @param password   私钥密码
     */
    private static String sign(byte[] data, String certBase64, String alias, String privateKey, String password)
            throws Exception {
        X509Certificate cert = CertUtil.readX509Certificate(certBase64);
        Signature signature = Signature.getInstance(cert.getSigAlgName());
        // TODO 读取私钥，第一次会特别耗时，待优化
        PrivateKey readPrivateKey = CertUtil.readPrivateKey(alias, privateKey, password);
        signature.initSign(readPrivateKey);
        signature.update(data);
        byte[] signData = signature.sign();
        return Base64.encodeToString(signData, Base64.DEFAULT);
    }

    /**
     * 验签
     *
     * @param data      传输数据
     * @param sign      签名后的数据
     * @param publicKey 开房平台的公钥
     * @return 验证结果
     * @throws Exception 异常
     */
    private static boolean verifySign(byte[] data, String sign, String publicKey) throws Exception {
        X509Certificate cert = CertUtil.readX509Certificate(publicKey);
        Signature signature = Signature.getInstance(cert.getSigAlgName());
        signature.initVerify(cert.getPublicKey());
        signature.update(data);
        return signature.verify(Base64.decode(sign, Base64.DEFAULT));
    }
}
