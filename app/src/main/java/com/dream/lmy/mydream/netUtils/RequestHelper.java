package com.dream.lmy.mydream.netUtils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;

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
     * @param num 参数
     */
    public void getBookList(int num) {
        Retrofit retrofit = createRetrofit();
        if (null == retrofit) {
            return;
        }

        RequestManager manager = retrofit.create(RequestManager.class);

        Call call = manager.getResponse();
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }

    public void addBookToList(String name) {

        Retrofit retrofit = createRetrofit();
        if (null == retrofit) {
            return;
        }
        RequestManager manager = retrofit.create(RequestManager.class);
        Call call = manager.addBook(name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }

    /**
     * 同步请求
     *
     * @return 列表数据
     */
    public Response getListOther() throws IOException {
        Retrofit retrofit = createRetrofit();
        RequestManager manager = retrofit.create(RequestManager.class);
        Call call = manager.getList();
        Response response;
        response = call.execute();
        return response;
    }

    public void getList() {
        Retrofit retrofit = createRetrofit();
        RequestManager manager = retrofit.create(RequestManager.class);
        Call call = manager.getList();

//        try {
//            //主线程调用此方法，会抛异常 NetworkOnMainThreadException
//            Response response = call.execute();
//            ResponseBody responseBody = (ResponseBody) response.body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                List<JsonObject> data = body.getData();
                JsonObject jsonObjects = data.get(0);
                jsonObjects.get("name");
                //本地广播发送到UI界面，更新
                Log.e("TEST", jsonObjects.get("name").toString());
                Log.e("TEST", body.getErrorCode() + "");
                Log.e("TEST", body.getErrorMsg());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    private Retrofit createRetrofit() {
        OkHttpClient client = createClient();
        if (client == null){
            return null;
        }
        return new Retrofit.Builder().baseUrl(RequestConfig.baseUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build();
    }

    private OkHttpClient createClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new MyTrustManager()};
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(6000, TimeUnit.MILLISECONDS);
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new MyHostnameVerifier());

            OkHttpClient client = builder.build();
            return client;
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (KeyManagementException e) {
            return null;
        } catch (RuntimeException exception) {
            throw exception;
        }
    }

    /**
     * 获取OkHttpClient
     *
     * @return OkHttpClient
     */
    private OkHttpClient createOkhttp() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new MyTrustManager()};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
//            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
//            builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new MyHostnameVerifier());

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (KeyManagementException e) {
            return null;
        } catch (RuntimeException exception) {
            throw exception;
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
