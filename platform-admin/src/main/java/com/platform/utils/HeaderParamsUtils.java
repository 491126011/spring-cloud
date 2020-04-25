package com.platform.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HeaderParamsUtils {

    /**
     * 从请求头里获取businessId
     * @return
     */
    public static Long getSellerId(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String sellerId = request.getHeader("sellerId");
        return Long.valueOf(StringUtils.isNotEmpty(sellerId) ? sellerId : "1");
    }

}
