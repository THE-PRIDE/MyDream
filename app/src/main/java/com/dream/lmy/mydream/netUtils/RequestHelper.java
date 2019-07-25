package com.dream.lmy.mydream.netUtils;

import android.text.TextUtils;

import com.dream.lmy.mydream.netUtils.callback.JsonDataCallback;
import com.dream.lmy.mydream.netUtils.callback.StringDataCallback;
import com.dream.lmy.mydream.netUtils.request.MyRequestBody;
import com.dream.lmy.mydream.netUtils.response.MyResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {

    private static final Object LOCKOBJECT = new Object();
    private static RequestHelper instance;
    private static final int DEFAULT_TIMEOUT = 8000;
    private JsonDataCallback jsonDataCallback;
    private StringDataCallback stringDataCallback;

    /**
     * @return ICSService
     */
    public static RequestHelper getInstance() {
        synchronized (LOCKOBJECT) {
            if (null == instance) {
                instance = new RequestHelper();
            }
            return instance;
        }
    }

    /**
     * 使用上送报文，响应报文都为字符串
     *
     * @param myRequestBody 上送报文
     */
    public void checkVerifyCode(String myRequestBody) {
        Retrofit retrofit = createRetrofit();
        if (retrofit == null) {
            return;
        }
        RequestManager manager = retrofit.create(RequestManager.class);
        Call<String> call = manager.checkVerifyCode(myRequestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //适用于上送报文，返回报文都是字符串
//                try {
//                    JSONObject jsonObject1 = new JSONObject(response.body());
//                    JSONObject jsonObject = jsonObject1.getJSONObject("Head");
//                    String ReturnCode = jsonObject.getString("ReturnCode");
//                    int anInt  = jsonObject.getInt("JnlId");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                if (stringDataCallback != null){
                    stringDataCallback.getDataCallback(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    /**
     * 使用上送报文，响应报文都为JSON对象
     *
     * @return
     */
    public void getToken(MyRequestBody myRequestBody) {
        Retrofit retrofit = createRetrofit();
        if (retrofit == null) {
            return;
        }
        RequestManager manager = retrofit.create(RequestManager.class);
        Call<MyResponseBody> call = manager.getToken(myRequestBody);
        call.enqueue(new Callback<MyResponseBody>() {
            @Override
            public void onResponse(Call<MyResponseBody> call, Response<MyResponseBody> response) {
                if (jsonDataCallback != null){
                    jsonDataCallback.getDataCallback(response.body());
                }
            }

            @Override
            public void onFailure(Call<MyResponseBody> call, Throwable t) {

            }
        });
    }

    private Retrofit createRetrofit() {
        OkHttpClient client = createOkHttp();
        if (client == null) {
            return null;
        }
        return new Retrofit.Builder()
                //设置基础URL
                .baseUrl(RequestConfig.baseUrl)
                //添加上送报文，请求报文转换类(GsonConverterFactory是将上送实体，转成json格式上送，如果需要上送字符串，需要换另一个转换类)
                .addConverterFactory(GsonConverterFactory.create())
                //上送报文格式为字符串，打开下行，需在gradle配置响应jar包
//                .addConverterFactory(ScalarsConverterFactory.create())
                //添加OkHttp实例
                .client(client)
                .build();
    }

    /**
     * 获取OkHttpClient
     *
     * @return OkHttpClient
     */
    private OkHttpClient createOkHttp() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new MyTrustManager()};
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
            builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new MyHostnameVerifier());
            return builder.build();
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (KeyManagementException e) {
            return null;
        }
    }

    private static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }

    private static class MyHostnameVerifier implements HostnameVerifier {
        //此列表为受信任的主机名列表
        private static String[] VERIFY_HOST_NAME_ARRAY = new String[]{};

        @Override
        public boolean verify(String hostname, SSLSession sslSession) {
            if (TextUtils.isEmpty(hostname)) {
                return false;
            }
            //TODO 此处默认让其返回true，如果主机名列表能拿到，则把非符号去掉
            return !Arrays.asList(VERIFY_HOST_NAME_ARRAY).contains(hostname);
        }
    }

    public class Tls12SocketFactory extends SSLSocketFactory {
        private final String[] TLS_SUPPORT_VERSION = {"TLSv1.1", "TLSv1.2"};

        final SSLSocketFactory delegate;

        public Tls12SocketFactory(SSLSocketFactory base) {
            this.delegate = base;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return patch(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return patch(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return patch(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket patch(Socket s) {
            if (s instanceof SSLSocket) {
                ((SSLSocket) s).setEnabledProtocols(TLS_SUPPORT_VERSION);
                s = new NoSSLv3SSLSocket((SSLSocket) s);
            }
            return s;
        }

        private class NoSSLv3SSLSocket extends DelegateSSLSocket {

            private NoSSLv3SSLSocket(SSLSocket delegate) {
                super(delegate);
            }

            @Override
            public void setEnabledProtocols(String[] protocols) {
                if (protocols == null) {
                    return;
                }
                if (protocols.length == 1 && "TLSv1".equals(protocols[0])) {
                    List<String> enabledProtocols = new ArrayList<>(Arrays.asList(delegate.getEnabledProtocols()));
                    if (enabledProtocols.size() > 1) {
                        enabledProtocols.remove("TLSv1");
                    }
                    protocols = enabledProtocols.toArray(new String[enabledProtocols.size()]);
                }

                super.setEnabledProtocols(protocols);
            }
        }

        private class DelegateSSLSocket extends SSLSocket {
            protected final SSLSocket delegate;

            DelegateSSLSocket(SSLSocket delegate) {
                this.delegate = delegate;
            }

            @Override
            public String[] getSupportedCipherSuites() {
                return delegate.getSupportedCipherSuites();
            }

            @Override
            public String[] getEnabledCipherSuites() {
                return delegate.getEnabledCipherSuites();
            }

            @Override
            public void setEnabledCipherSuites(String[] suites) {
                delegate.setEnabledCipherSuites(suites);
            }

            @Override
            public String[] getSupportedProtocols() {
                return delegate.getSupportedProtocols();
            }

            @Override
            public String[] getEnabledProtocols() {
                return delegate.getEnabledProtocols();
            }

            @Override
            public void setEnabledProtocols(String[] protocols) {
                delegate.setEnabledProtocols(protocols);
            }

            @Override
            public SSLSession getSession() {
                return delegate.getSession();
            }

            @Override
            public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
                delegate.addHandshakeCompletedListener(listener);
            }

            @Override
            public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
                delegate.removeHandshakeCompletedListener(listener);
            }

            @Override
            public void startHandshake() throws IOException {
                delegate.startHandshake();
            }

            @Override
            public void setUseClientMode(boolean mode) {
                delegate.setUseClientMode(mode);
            }

            @Override
            public boolean getUseClientMode() {
                return delegate.getUseClientMode();
            }

            @Override
            public void setNeedClientAuth(boolean need) {
                delegate.setNeedClientAuth(need);
            }

            @Override
            public void setWantClientAuth(boolean want) {
                delegate.setWantClientAuth(want);
            }

            @Override
            public boolean getNeedClientAuth() {
                return delegate.getNeedClientAuth();
            }

            @Override
            public boolean getWantClientAuth() {
                return delegate.getWantClientAuth();
            }

            @Override
            public void setEnableSessionCreation(boolean flag) {
                delegate.setEnableSessionCreation(flag);
            }

            @Override
            public boolean getEnableSessionCreation() {
                return delegate.getEnableSessionCreation();
            }

            @Override
            public void bind(SocketAddress localAddr) throws IOException {
                delegate.bind(localAddr);
            }

            @Override
            public synchronized void close() throws IOException {
                delegate.close();
            }

            @Override
            public void connect(SocketAddress remoteAddr) throws IOException {
                delegate.connect(remoteAddr);
            }

            @Override
            public void connect(SocketAddress remoteAddr, int timeout) throws IOException {
                delegate.connect(remoteAddr, timeout);
            }

            @Override
            public SocketChannel getChannel() {
                return delegate.getChannel();
            }

            @Override
            public InetAddress getInetAddress() {
                return delegate.getInetAddress();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return delegate.getInputStream();
            }

            @Override
            public boolean getKeepAlive() throws SocketException {
                return delegate.getKeepAlive();
            }

            @Override
            public InetAddress getLocalAddress() {
                return delegate.getLocalAddress();
            }

            @Override
            public int getLocalPort() {
                return delegate.getLocalPort();
            }

            @Override
            public SocketAddress getLocalSocketAddress() {
                return delegate.getLocalSocketAddress();
            }

            @Override
            public boolean getOOBInline() throws SocketException {
                return delegate.getOOBInline();
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                return delegate.getOutputStream();
            }

            @Override
            public int getPort() {
                return delegate.getPort();
            }

            @Override
            public synchronized int getReceiveBufferSize() throws SocketException {
                return delegate.getReceiveBufferSize();
            }

            @Override
            public SocketAddress getRemoteSocketAddress() {
                return delegate.getRemoteSocketAddress();
            }

            @Override
            public boolean getReuseAddress() throws SocketException {
                return delegate.getReuseAddress();
            }

            @Override
            public synchronized int getSendBufferSize() throws SocketException {
                return delegate.getSendBufferSize();
            }

            @Override
            public int getSoLinger() throws SocketException {
                return delegate.getSoLinger();
            }

            @Override
            public synchronized int getSoTimeout() throws SocketException {
                return delegate.getSoTimeout();
            }

            @Override
            public boolean getTcpNoDelay() throws SocketException {
                return delegate.getTcpNoDelay();
            }

            @Override
            public int getTrafficClass() throws SocketException {
                return delegate.getTrafficClass();
            }

            @Override
            public boolean isBound() {
                return delegate.isBound();
            }

            @Override
            public boolean isClosed() {
                return delegate.isClosed();
            }

            @Override
            public boolean isConnected() {
                return delegate.isConnected();
            }

            @Override
            public boolean isInputShutdown() {
                return delegate.isInputShutdown();
            }

            @Override
            public boolean isOutputShutdown() {
                return delegate.isOutputShutdown();
            }

            @Override
            public void sendUrgentData(int value) throws IOException {
                delegate.sendUrgentData(value);
            }

            @Override
            public void setKeepAlive(boolean keepAlive) throws SocketException {
                delegate.setKeepAlive(keepAlive);
            }

            @Override
            public void setOOBInline(boolean oobinline) throws SocketException {
                delegate.setOOBInline(oobinline);
            }

            @Override
            public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
                delegate.setPerformancePreferences(connectionTime, latency, bandwidth);
            }

            @Override
            public synchronized void setReceiveBufferSize(int size) throws SocketException {
                delegate.setReceiveBufferSize(size);
            }

            @Override
            public void setReuseAddress(boolean reuse) throws SocketException {
                delegate.setReuseAddress(reuse);
            }

            @Override
            public synchronized void setSendBufferSize(int size) throws SocketException {
                delegate.setSendBufferSize(size);
            }

            @Override
            public void setSoLinger(boolean on, int timeout) throws SocketException {
                delegate.setSoLinger(on, timeout);
            }

            @Override
            public synchronized void setSoTimeout(int timeout) throws SocketException {
                delegate.setSoTimeout(timeout);
            }

            @Override
            public void setSSLParameters(SSLParameters p) {
                delegate.setSSLParameters(p);
            }

            @Override
            public void setTcpNoDelay(boolean on) throws SocketException {
                delegate.setTcpNoDelay(on);
            }

            @Override
            public void setTrafficClass(int value) throws SocketException {
                delegate.setTrafficClass(value);
            }

            @Override
            public void shutdownInput() throws IOException {
                delegate.shutdownInput();
            }

            @Override
            public void shutdownOutput() throws IOException {
                delegate.shutdownOutput();
            }

            @Override
            public String toString() {
                return delegate.toString();
            }

            @Override
            public boolean equals(Object o) {
                return delegate.equals(o);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }
    }

}
