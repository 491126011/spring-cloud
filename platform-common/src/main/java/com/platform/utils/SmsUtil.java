package com.platform.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能:		web.cr6868.com HTTP接口 发送短信
 * 说明:		http://web.cr6868.com/asmx/smsservice.aspx?name=登录名&pwd=接口密码&mobile=手机号码&content=内容&sign=签名&stime=发送时间&type=pt&extno=自定义扩展码
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
public class SmsUtil {

    /**
     * 创锐平台发送短信
     *
     * @param name          用户名
     * @param pwd           密码(md5加密)
     * @param mobileString  电话号码字符串，中间用英文逗号间隔
     * @param contextString 内容字符串
     * @param sign          签名
     * @param stime         追加发送时间，可为空，为空为及时发送
     * @param extno         扩展码，必须为数字 可为空
     * @return
     * @throws Exception
     */
    public static String crSendSms(String name, String pwd, String mobileString, String contextString, String sign, String stime, String extno) throws Exception {
        StringBuffer param = new StringBuffer();
        param.append("name=" + name);
        param.append("&pwd=" + pwd);
        param.append("&mobile=").append(mobileString);
        param.append("&content=").append(URLEncoder.encode(contextString, "UTF-8"));
        if (StringUtils.isNotEmpty(stime)) {
            param.append("&stime=" + stime);
        }
        param.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));
        param.append("&type=pt");
        if (StringUtils.isNotEmpty(extno)) {
            param.append("&extno=").append(extno);
        }

        URL localURL = new URL("http://web.cr6868.com/asmx/smsservice.aspx?");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String resultBuffer = "";

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(param.toString());
            outputStreamWriter.flush();

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            inputStream = httpURLConnection.getInputStream();
            resultBuffer = convertStreamToString(inputStream);

        } finally {

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return resultBuffer;
    }

    /**
     * 阿里云平台发送短信
     * @param accessKeyId
     * @param accessKeySecret
     * @param signName
     * @param phone
     * @return
     * @throws ClientException
     */
    public static String aliSendSms(String accessKeyId, String accessKeySecret, String signName,String templateCode, String phone) throws ClientException {

        //产品名称,产品域名
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(signName);
        request.setTemplateCode("SMS_144145503");

        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        String code = "289586";
        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"" + code + "\"}");

        //outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        StringBuffer buf = new StringBuffer();
        buf.append("短信接口返回的数据----------------");
        buf.append("Code=" + sendSmsResponse.getCode()).append(",");
        buf.append("Message=" + sendSmsResponse.getMessage()).append(",");
        buf.append("RequestId=" + sendSmsResponse.getRequestId()).append(",");
        buf.append("BizId=" + sendSmsResponse.getBizId()).append(",");

        //TODO 待处理
        //查明细
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            StringBuffer qbuf = new StringBuffer();
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(sendSmsResponse,accessKeyId,accessKeySecret,phone);
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                qbuf.append("SmsSendDetailDTO["+i+"]:").append(".");
                qbuf.append("Content=" + smsSendDetailDTO.getContent()).append(".");
                qbuf.append("ErrCode=" + smsSendDetailDTO.getErrCode()).append(".");
                qbuf.append("OutId=" + smsSendDetailDTO.getOutId()).append(".");
                qbuf.append("PhoneNum=" + smsSendDetailDTO.getPhoneNum()).append(".");
                qbuf.append("ReceiveDate=" + smsSendDetailDTO.getReceiveDate()).append(".");
                qbuf.append("SendDate=" + smsSendDetailDTO.getSendDate()).append(".");
                qbuf.append("SendStatus=" + smsSendDetailDTO.getSendStatus()).append(".");
                qbuf.append("Template=" + smsSendDetailDTO.getTemplateCode()).append(".");
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }

        return buf.toString();
    }


    /**
     * 查询短信发送结果
     * @param response
     * @param accessKeyId
     * @param accessKeySecret
     * @param phone
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySendDetails(SendSmsResponse response,String accessKeyId, String accessKeySecret,String phone) throws ClientException {

        //产品名称,产品域名
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(phone);
        request.setBizId(response.getBizId());
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        request.setPageSize(10L);
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    /**
     * 转换返回值类型为UTF-8格式.
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }
}
