package com.platform.api;

import com.platform.utils.PosterUtils;
import com.platform.utils.R;
import com.platform.utils.ResourceUtil;
import com.platform.utils.Wechat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/qrcode")
public class ApiQrcodeController {

    @GetMapping
    public void createQrCode(@RequestHeader("params") String params,@RequestHeader("page") String page, HttpServletResponse response) {

        try {
            String accessToken = Wechat.wxGetAccessToken(ResourceUtil.getConfigByName("wx.appId"), ResourceUtil.getConfigByName("wx.secret"));
            byte[] miniQrCodeOutputStream = Wechat.getMiniQrCodeBytes(accessToken,params, page, true);
            response.setContentType("image/png");
            response.getOutputStream().write(miniQrCodeOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
