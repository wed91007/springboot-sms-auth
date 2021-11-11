package com.xyhan.sms.CodeUtil;

import com.xyhan.sms.Config.MyConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.apache.commons.lang3.RandomStringUtils;


// 短信验证码生成器
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(MyConstants.SMS_RANDOM_SIZE);
        return new ValidateCode(code, MyConstants.SMS_EXPIRE_SECOND);
    }

}
