package com.xyhan.sms.core.code;

import com.xyhan.sms.core.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
