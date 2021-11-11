package com.xyhan.sms.CodeUtil;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.debug("send to mobile ：{}, code : {}", mobile, code);
    }
}