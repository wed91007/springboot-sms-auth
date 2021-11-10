package com.xyhan.sms.CodeUtil;

import com.xyhan.sms.CodeUtil.SmsCodeSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送模拟
 * @author Administrator
 *
 */
@Slf4j
@Service
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.debug("send to mobile ：{}, code : {}", mobile, code);
    }
}