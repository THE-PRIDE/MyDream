package com.dream.lmy.mydream.encryptUtils.constants;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加解密常量
 */
public class SecurityConstants {

    //平台公钥证书
    private String platformCert = "MIIDPzCCAiegAwIBAgIEGldzAzANBgkqhkiG9w0BAQQFADBeMQswCQYDVQQGEwJDTjEQMA4GA1UECBMHR3VhbmdYaTEQMA4GA1UEBxMHTmFuTmluZzEMMAoGA1UEChMDQkJHMQwwCgYDVQQLEwNCQkcxDzANBgNVBAMTBlJvb3RDQTAeFw0xOTA3MDQwOTI2MTJaFw0yMjA3MDMwOTI2MTJaMGUxCzAJBgNVBAYTAkNOMRAwDgYDVQQIEwdHdWFuZ1hpMRAwDgYDVQQHEwdOYW5OaW5nMQwwCgYDVQQKEwNCQkcxDDAKBgNVBAsTA0JCRzEWMBQGA1UEAxMNMTkyLjE2OC4xLjExNjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJLKuc10Z7aSj+tsNZ20E0tn9IQke6iBf4M7DguJL3P6Pvggf+GRFUdRFEpi/6mSqkA7Afe/6HgkBXw2KJplUO/jqsCMBqMogw0irAeiuGiBQaY3NQXyNYsYAdbrIpDQattTJikw7tReLmqaEg2JteV5gu0YXBqBBGgtIG0IeR2RXl9r+1fan0WHqouKT76Pef3lq+/H3sd3a9ow5tZJFk9UlYbnxozArc/V1UFGOVD4rFGcBVnZTqakChDJ3bkFVO6cCEwoPdPDE3Pf1EJ5XG/uN4fE7okfJ5iKgit6XE+WdeXrpYU6XWODhvHrHG3h2bitjAuWsij2La6T2xtPheECAwEAATANBgkqhkiG9w0BAQQFAAOCAQEAe8wdqJiyWxBvNS64eCGYTJVwoR7F/sXXGVmBxD8iSPjmN3cWjkKmsW7aPK45nv3hJUnGfWnB/HIq1XURvUEXmzIczRzsgZoM6D94NF7DtIyVDYgOBAw2hXl+3I+94cQ1duYF5sAsPNgKBXYmTRibh9g4ZDa1xrZSEw33i2A7WEzu4eg5l7qUaf+vxKs9BjwYw8J7mrRdp2bpQQzPsScDWafIu6Kp6VzOhEuAldqrombydvCPVm6nKr2AS+nUZQlXJsN+KUAzdH7edhsdKIVmHjT1kQSkySjrdytPmk191DS++mddRuzNl3Tfr033n0BA2wNSQeWmYYvpB8W8IatSOg==";
    //平台私钥证书
    private String platformPrivateKey = "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";

    //开发者公钥证书
    private String devCert = "MIIDODCCAiCgAwIBAgIEInyQIzANBgkqhkiG9w0BAQQFADBeMQswCQYDVQQGEwJDTjEQMA4GA1UECBMHR3VhbmdYaTEQMA4GA1UEBxMHTmFuTmluZzEMMAoGA1UEChMDQkJHMQwwCgYDVQQLEwNCQkcxDzANBgNVBAMTBlJvb3RDQTAeFw0xOTA3MDQwOTI4MjlaFw0yMjA3MDMwOTI4MjlaMF4xCzAJBgNVBAYTAkNOMRAwDgYDVQQIEwdHdWFuZ1hpMRAwDgYDVQQHEwdOYW5OaW5nMQwwCgYDVQQKEwNERVYxDDAKBgNVBAsTA0RFVjEPMA0GA1UEAxMGUFVCTElDMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy31Ydmt49ix1E/dStLrgF4Czub98zkgsq9YuGmO90t7iERecyBSd7BB42gc5O2J+abvvTyIuFfdH2dqNg6D5okRXy/bEw+v6EmTe9G+I51xtcJ8qog9oMuZMbdwolIhSAR6yt+4ijvDiM9+CFO+Gf0+oXicj4Xauxc8VnwqY2Fl6Y+l0wt+juLmiO5tddSQeok5Bw6NgEVNjWoJiCz6CqAdJvA+BfOZxccy51W8lx06mdQroNFbC02y7pEjIJPHas6/N0kMOCPkLCddMvW1UPqVpj1BsgTqizyFQjwMQyZ4mm7C3gQs+b46cdS/5cCES62NSpgVFO/ZbeoCpWG6CPQIDAQABMA0GCSqGSIb3DQEBBAUAA4IBAQAxlCotANlbaBVNsoab8+PQ1cbD/47KNKdxPRhAn4qXcvdMazsYBMjwpAbhUjpPVEbHtkArIxc4/Es/X+zqr4zkisfSM1/TUFT8JXlq4PflLtbqfax+LpdE3lPhF2md4lHvHaW5BmqLl2ePDTMMtXODIPB7EsShoR1xNl/8Ijr5x+v9yesjVj+BJxDe9tP9iqWGZMkhsXuEyIr3QU516jb/DY2NiI15b74jE8sYLou2iO2MSnWNZlwvcVMeBlfmxgdTaoA23BiONVulj7yLh9wQOWoLJ8VBmfw+unO/J5ltLNwnV3hdL09ek24LlhlUO1xGlhusz+RV6anW06/4S6lf";
    //开发者私钥
    private String devPrivateKey = "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";
    //开发者加密密钥
    private String encryptKey = "1277984e1277984e1277984e";
    //证书别名
    private String certAlias = "dev_test";
    //私钥密码
    private String keyPassword = "123456";
    //证书序列号
    private String certSerialNo = "1277984e";

    private static SecurityConstants instance;

    public static SecurityConstants getInstance() {
        if (instance == null) {
            instance = new SecurityConstants();
        }
        return instance;

    }

    public String getPlatformCert() {
        return platformCert;
    }

    public void setPlatformCert(String platformCert) {
        this.platformCert = platformCert;
    }

    public String getPlatformPrivateKey() {
        return platformPrivateKey;
    }

    public void setPlatformPrivateKey(String platformPrivateKey) {
        this.platformPrivateKey = platformPrivateKey;
    }

    public String getDevCert() {
        return devCert;
    }

    public void setDevCert(String devCert) {
        this.devCert = devCert;
    }

    public String getDevPrivateKey() {
        return devPrivateKey;
    }

    public void setDevPrivateKey(String devPrivateKey) {
        this.devPrivateKey = devPrivateKey;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getCertAlias() {
        return certAlias;
    }

    public void setCertAlias(String certAlias) {
        this.certAlias = certAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getCertSerialNo() {
        return certSerialNo;
    }

    public void setCertSerialNo(String certSerialNo) {
        this.certSerialNo = certSerialNo;
    }

    // 获取秘钥
    public SecretKey getSecretKey() {
        SecretKey deskey = null;
//        KeyGenerator kg = null;
//        Provider[] providers = Security.getProviders();
//        try {
//            kg = KeyGenerator.getInstance("DESede");
//            kg.getProvider().getName();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        SecureRandom secureRandom = null;
//        try {
//            secureRandom = SecureRandom.getInstance("SHA1PRNG");
//            secureRandom.setSeed(encryptKey.getBytes());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
////        kg.init(168, new SecureRandom(encryptKey.getBytes()));
//        kg.init(168,   secureRandom );
//        Provider provider = kg.getProvider();
//        String providerName = provider.getName();
//        SecretKey secretKey = kg.generateKey();
//        deskey = new SecretKeySpec(secretKey.getEncoded(), "DESede");
        deskey = new SecretKeySpec(encryptKey.getBytes(), "DESede");
        return deskey;
    }


}
