package com.platform.utils;


import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class HttpClientHelper {

    public static final String DEFAULT_CHARSET = "UTF-8";

    // 连接超时时间
    public static int CONNECTION_TIMEOUT = 30000;

    // 读数据超时时间
    public static int READ_DATA_TIMEOUT = 30000;

    /**
     * @param url
     * @param https
     * @return 发送GET请求并返回回应结果
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyManagementException
     */
    public static String doGet(String url, boolean https) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, KeyManagementException {
        StringBuilder _resultSB = null;
        URLConnection _conn = null;
        if (https) {
            SSLContext _context = SSLContext.getInstance("SSL", "SunJSSE");
            _context.init(null, new TrustManager[] { new WeChatX509TrustManager() }, new java.security.SecureRandom());
            SSLSocketFactory _sslFactory = _context.getSocketFactory();
            //
            _conn = new URL(url).openConnection();
            ((HttpsURLConnection) _conn).setSSLSocketFactory(_sslFactory);
        } else {
            _conn = new URL(url).openConnection();
        }
        _conn.setConnectTimeout(CONNECTION_TIMEOUT);
        _conn.setReadTimeout(READ_DATA_TIMEOUT);
        if (https) {
            ((HttpsURLConnection) _conn).setRequestMethod("GET");
        } else {
            ((HttpURLConnection) _conn).setRequestMethod("GET");
        }
        _conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        _conn.setDoOutput(true);
        _conn.setDoInput(true);
        _conn.connect();

        InputStream _inputStream = _conn.getInputStream();
        BufferedReader _reader = new BufferedReader(new InputStreamReader(_inputStream, DEFAULT_CHARSET));
        String _buffer = null;
        _resultSB = new StringBuilder();
        while ((_buffer = _reader.readLine()) != null) {
            _resultSB.append(_buffer);
        }
        _inputStream.close();
        if (_conn != null) {
            if (https) {
                ((HttpsURLConnection) _conn).disconnect();
            } else {
                ((HttpURLConnection) _conn).disconnect();
            }
        }
        String _result = _resultSB.toString();
        return _result;
    }

    /**
     * @param url
     * @param https
     * @param params
     * @return 发送POST请求并返回回应结果
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyManagementException
     */
    public static String doPost(String url, boolean https, String params) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, KeyManagementException {
        StringBuilder _resultSB = null;
        URLConnection _conn = null;

        if (https) {
            SSLContext _context = SSLContext.getInstance("SSL", "SunJSSE");
            _context.init(null, new TrustManager[] { new WeChatX509TrustManager() }, new java.security.SecureRandom());
            SSLSocketFactory _sslFactory = _context.getSocketFactory();
            _conn = new URL(url).openConnection();
            ((HttpsURLConnection) _conn).setSSLSocketFactory(_sslFactory);
        } else {
            _conn = new URL(url).openConnection();
        }
        //
        _conn.setConnectTimeout(CONNECTION_TIMEOUT);
        _conn.setReadTimeout(READ_DATA_TIMEOUT);
        if (https) {
            ((HttpsURLConnection) _conn).setRequestMethod("POST");
        } else {
            ((HttpURLConnection) _conn).setRequestMethod("POST");
        }
        _conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        _conn.setDoOutput(true);
        _conn.setDoInput(true);
        _conn.connect();

        OutputStream _outputStream = _conn.getOutputStream();
        _outputStream.write(params.getBytes(DEFAULT_CHARSET));
        _outputStream.flush();
        _outputStream.close();

        InputStream _inputStream = _conn.getInputStream();
        BufferedReader _reader = new BufferedReader(new InputStreamReader(_inputStream, DEFAULT_CHARSET));
        String _buffer = null;
        _resultSB = new StringBuilder();
        while ((_buffer = _reader.readLine()) != null) {
            _resultSB.append(_buffer);
        }
        _inputStream.close();
        if (_conn != null) {
            if (https) {
                ((HttpsURLConnection) _conn).disconnect();
            } else {
                ((HttpURLConnection) _conn).disconnect();
            }
        }
        String _result = _resultSB.toString();
        return _result;
    }

    /**
     * post请求，返回InputStream
     * @param url
     * @param https
     * @param params
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     * @throws KeyManagementException
     */
    public static InputStream doPostInputStream(String url, boolean https, String params) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, KeyManagementException {

        URLConnection _conn = null;

        if (https) {
            SSLContext _context = SSLContext.getInstance("SSL", "SunJSSE");
            _context.init(null, new TrustManager[] { new WeChatX509TrustManager() }, new java.security.SecureRandom());
            SSLSocketFactory _sslFactory = _context.getSocketFactory();
            _conn = new URL(url).openConnection();
            ((HttpsURLConnection) _conn).setSSLSocketFactory(_sslFactory);
        } else {
            _conn = new URL(url).openConnection();
        }
        //
        _conn.setConnectTimeout(CONNECTION_TIMEOUT);
        _conn.setReadTimeout(READ_DATA_TIMEOUT);
        if (https) {
            ((HttpsURLConnection) _conn).setRequestMethod("POST");
        } else {
            ((HttpURLConnection) _conn).setRequestMethod("POST");
        }
        _conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        _conn.setDoOutput(true);
        _conn.setDoInput(true);
        _conn.connect();

        OutputStream _outputStream = _conn.getOutputStream();
        _outputStream.write(params.getBytes(DEFAULT_CHARSET));
        _outputStream.flush();
        _outputStream.close();

        if (_conn != null) {
            if (https) {
                ((HttpsURLConnection) _conn).disconnect();
            } else {
                ((HttpURLConnection) _conn).disconnect();
            }
        }

        return _conn.getInputStream();
    }
}


class WeChatX509TrustManager implements X509TrustManager {

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

}
