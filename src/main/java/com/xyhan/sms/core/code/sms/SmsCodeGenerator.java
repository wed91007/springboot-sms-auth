package com.xyhan.sms.core.code.sms;

import com.xyhan.sms.config.ConstantsConfig;
import com.xyhan.sms.core.code.ValidateCode;
import com.xyhan.sms.core.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.apache.commons.lang3.RandomStringUtils;


// 短信验证码生成器
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(ConstantsConfig.SMS_RANDOM_SIZE);
        return new ValidateCode(code, ConstantsConfig.SMS_EXPIRE_SECOND);
    }
}
