package com.xyhan.sms.CodeUtil;

import com.xyhan.sms.config.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateCodeController {

    @Autowired
    private SessionStrategy sessionStrategy;

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private DefaultSmsCodeSender defaultSmsCodeSender;

    @GetMapping("code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), MyConstants.SESSION_KEY, smsCode);
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        defaultSmsCodeSender.send(mobile, smsCode.getCode());
    }
}