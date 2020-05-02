package com.platform.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class Wechat {

    private static Object __LOCK = new Object();
    public static final long EXPIRES_TIME = 7200;

    public static String _access_token = null;

    public static long expires_time = System.currentTimeMillis() + EXPIRES_TIME * 1000;

    public static JSONObject __doCheckJsonResult(String jsonStr) throws Exception {
        if (!StringUtils.isNotEmpty(jsonStr)) {
            return null;
        }
        JSONObject _result = JSON.parseObject(jsonStr);
        if (_result.containsKey("errcode") && _result.getIntValue("errcode") != 0) {
            throw new Exception("[" + _result.getIntValue("errcode") + "]" + _result.getString("errmsg"));
        }
        return _result;
    }


    public static String wxGetAccessToken(String accountId, String appSecret) throws Exception {
        if (!StringUtils.isNotEmpty(accountId)) {
            throw new IllegalArgumentException("accountId is not null");
        }
        JSONObject _accessToken = null;
        synchronized (__LOCK) {
            long _currentTime = System.currentTimeMillis();
            if (_access_token == null || (_currentTime >= expires_time)) {
                _accessToken = Wechat.__doCheckJsonResult(HttpClientHelper.doGet(WX_API.WX_ACCESS_TOKEN.concat("&appid=") + accountId + "&secret=" + appSecret, true));
                expires_time = _currentTime + _accessToken.getIntValue("expires_in") * 1000;
                _access_token = _accessToken.getString("access_token");
            }
        }
        return _access_token;
    }

    /*
     * 获取 二维码图片
     *
     */
    public static String getMiniQrCode(String accessToken, String uploadPath, Long businessId,  String page, boolean isHyaline) {
        String fileName = businessId + "-code.png";
        String bizPath = "files" + File.separator + "qrcode";
        File file = new File(uploadPath + File.separator + bizPath);
        if (!file.exists()) {
            file.mkdirs();// 创建文件根目录
        }
        String savePath = file.getPath() + File.separator + fileName;
        try {
            String wxCodeURL = WX_API.GET_WX_ACODE_UNLIMIT.concat(accessToken);
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", "businessId=" + businessId);
            paramJson.put("page", page);
            paramJson.put("width", 430);
            paramJson.put("is_hyaline", isHyaline);


            //开始获取数据
            InputStream inputStream = HttpClientHelper.doPostInputStream(wxCodeURL, true, paramJson.toString());
//
//            BufferedReader _reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            String _buffer = null;
//            StringBuilder _resultSB = new StringBuilder();
//            while ((_buffer = _reader.readLine()) != null) {
//                _resultSB.append(_buffer);
//            }
//            System.out.println(_resultSB.toString());

            BufferedInputStream bis = new BufferedInputStream(inputStream);
            OutputStream os = new FileOutputStream(new File(savePath));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }


            inputStream.close();
            bis.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePath;
    }

    /*
     * 获取 二维码图片
     *
     */
    public static String getMiniQrCodeBase64(String accessToken,  Long businessId,  String page, boolean isHyaline) {
        String encodeStr ="";
        try {
            String wxCodeURL = WX_API.GET_WX_ACODE_UNLIMIT.concat(accessToken);
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", "businessId=" + businessId);
            paramJson.put("page", page);
            paramJson.put("width", 430);
            paramJson.put("is_hyaline", isHyaline);


            //开始获取数据
            InputStream inputStream = HttpClientHelper.doPostInputStream(wxCodeURL, true, paramJson.toString());
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
                byteArrayOutputStream.write(buff, 0, rc);
            }
            encodeStr = Base64.encode(new String(byteArrayOutputStream.toByteArray()));
            inputStream.close();
            bis.close();
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 获取 二维码图片
     * @param accessToken
     * @param scene 扫码后小程序进入页面时带的参数 如：id=1
     * @param page 扫码后进入的页面
     * @param isHyaline 是否需要透明底色，为 true 时，生成透明底色的小程序返回值
     * @return
     */
    public static byte[] getMiniQrCodeBytes(String accessToken,  String scene,  String page, boolean isHyaline) {
        InputStream inputStream = null;
        byte[] bytes = null;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            String wxCodeURL = WX_API.GET_WX_ACODE_UNLIMIT.concat(accessToken);
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", scene);
            paramJson.put("page", page);
            paramJson.put("width", 73);
            paramJson.put("is_hyaline", isHyaline);


            //开始获取数据
            inputStream = HttpClientHelper.doPostInputStream(wxCodeURL, true, paramJson.toString());

            //这一段代码保留，为了防止二码出不来，打印返回日志查看
//            BufferedReader _reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            String _buffer = null;
//            StringBuilder _resultSB = new StringBuilder();
//            while ((_buffer = _reader.readLine()) != null) {
//                _resultSB.append(_buffer);
//            }
//            System.out.println(_resultSB.toString());

            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
                byteArrayOutputStream.write(buff, 0, rc);
            }
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static class WX_API {
        public static final String WX_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

        public static final String GET_WX_ACODE_UNLIMIT = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    }

    public static void main(String[] args) throws Exception {
        String appId = "wxac803b89ec64cf2e";
        String secret = "ba565f24a214e6b20ef78b8b34e6c9ba";
        String accessToken = Wechat.wxGetAccessToken(appId, secret);
        Wechat.getMiniQrCodeBytes(accessToken,"", "pages/goods/goods", true);
    }

}
