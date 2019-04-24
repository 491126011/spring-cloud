package com.platform.api;

import com.platform.annotation.LoginUser;
import com.platform.entity.UserVo;
import com.platform.util.wechat.MD5;
import com.platform.utils.CharUtil;
import com.platform.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = "商户支付")
@RestController
@RequestMapping("/api/changpay")
public class ApiChanPayController {

    private static final String SERVER_URL = "https://pay.ebjfinance.com/wechatjspay.php";

    private static final String SERVER_QUERY_URL = "https://pay.ebjfinance.com/weixin/wechatpayquery.php";

    //TODO 调试时记得更换ID
    private static final String merid = "merid";

    /**
     * 获取支付的请求参数
     */
    @ApiOperation(value = "获取支付的请求参数")
    @PostMapping("prepay")
    public Object payPrepay(@LoginUser UserVo loginUser, Integer orderId, BigDecimal orderMoney) {

        SimpleDateFormat sf = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS);
        String orderTime = sf.format(new Date());

        String merchantOutOrderNo = String.valueOf(orderId);
        String noncestr = CharUtil.getRandomString(32);
        String param = "merchantOutOrderNo="+merchantOutOrderNo+"&merid="+merid+"&noncestr="+noncestr+"&orderMoney="+orderMoney+"&orderTime="+orderTime;
        String sign = MD5.getMessageDigest(param);
        param = param +"&sign="+sign;

        return param;
    }

}
