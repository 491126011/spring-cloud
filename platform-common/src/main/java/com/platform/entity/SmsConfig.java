package com.platform.entity;

import com.platform.validator.group.AliyunGroup;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 名称：SmsConfig <br>
 * 描述：短信配置信息<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：创瑞
     */
    @Range(min = 1, max = 3, message = "类型错误")
    private Integer type;

    /**
     * 短信发送域名
     */
    private String domain;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码(md5加密)
     */
    private String pwd;

    /**
     * 签名
     */
    private String sign;

    /**
     * 阿里云 key
     */
    @NotBlank(message = "阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
    private String aliSmsAccessKeyId;

    /**
     * 阿里云 Key Secret
     */
    @NotBlank(message = "阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
    private String aliSmsAccessKeySecret;

    /**
     * 阿里云签名
     */
    @NotBlank(message = "阿里云签名不能为空", groups = AliyunGroup.class)
    private String aliSmsSignName;

    /**
     * 模板Code
     */
    private String templateCode;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAliSmsAccessKeyId() {
        return aliSmsAccessKeyId;
    }

    public void setAliSmsAccessKeyId(String aliSmsAccessKeyId) {
        this.aliSmsAccessKeyId = aliSmsAccessKeyId;
    }

    public String getAliSmsAccessKeySecret() {
        return aliSmsAccessKeySecret;
    }

    public void setAliSmsAccessKeySecret(String aliSmsAccessKeySecret) {
        this.aliSmsAccessKeySecret = aliSmsAccessKeySecret;
    }

    public String getAliSmsSignName() {
        return aliSmsSignName;
    }

    public void setAliSmsSignName(String aliSmsSignName) {
        this.aliSmsSignName = aliSmsSignName;
    }
}
