package com.xyhan.sms.CodeUtil;

import org.springframework.web.context.request.ServletWebRequest;
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
