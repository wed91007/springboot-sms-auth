package com.xyhan.sms.core.controller;

import com.xyhan.sms.config.ConstantsConfig;
import com.xyhan.sms.core.code.sms.DefaultSmsCodeSender;
import com.xyhan.sms.core.code.image.ImageCodeService;
import com.xyhan.sms.core.code.ValidateCodeGenerator;
import com.xyhan.sms.core.code.ValidateCodeService;
import com.xyhan.sms.data.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ValidateCodeController {
    private  String tokenId="TOKEN-PHONE-";

    @Autowired
    ImageCodeService imageCodeService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SessionStrategy sessionStrategy;

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private DefaultSmsCodeSender defaultSmsCodeSender;

    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping("/auth/sms")
    public Map<String, Object> createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        //get phone number
        String phone = ServletRequestUtils.getStringParameter(request, "phone");
        String imgCode = ServletRequestUtils.getStringParameter(request, "imgCode");

        Map<String, Object> map = new HashMap<>();
        String sessionCode = (String) sessionStrategy.getAttribute(new ServletWebRequest(request), ConstantsConfig.SESSION_KEY);

        if(sessionCode != null && sessionCode.equals(imgCode)) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
            map = validateCodeService.SmsRedisValidate(request, response, phone);
        }
        else{
            map.put("status", "img code error");
        }
        return map;
    }

    @PostMapping("/auth/login")
    public Map<String, Object> validateLogin(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException{
        Map<String,Object> map;
        String phone = ServletRequestUtils.getStringParameter(request, "phone");
        String sms = ServletRequestUtils.getStringParameter(request, "sms");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        map = validateCodeService.smsLoginValidate(request, response, phone, sms);
        return map;
    }

    @GetMapping("auth/getImage")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        imageCodeService.pngCodeGenerate(request, response);
    }

    @GetMapping("auth/getImage/base64")
    public Map<String, String> createImageCodeBase64(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return imageCodeService.base64CodeGenerate(request, response);
    }



}