package com.xyhan.sms.CodeUtil;

public interface SmsCodeSender {
    //至少需要手机号和验证码
    void send(String mobile, String code);
}
