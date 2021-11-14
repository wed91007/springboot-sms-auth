package com.xyhan.sms.CodeUtil;

import com.xyhan.sms.Config.MyConstants;
import com.xyhan.sms.RedisData.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ValidateCodeController {
    private  String tokenId="TOKEN-PHONE-";


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

        String sessionCode = (String) sessionStrategy.getAttribute(new ServletWebRequest(request), MyConstants.SESSION_KEY);

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
        Map<String,Object> map = new HashMap<>();
        String phone = ServletRequestUtils.getStringParameter(request, "phone");
        String sms = ServletRequestUtils.getStringParameter(request, "sms");

        String smsInMemory = redisService.get(tokenId+phone);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        long smsRequestTimes = Integer.parseInt(redisService.get(smsInMemory));
        if(smsRequestTimes <= MyConstants.MAX_REQUEST_PER_PHONE_USER) {//验证次数小于规定
            redisService.set(smsInMemory, Long.toString(smsRequestTimes+1));
            if(StringUtils.isEmpty(smsInMemory)) {
                map.put("status", 404);
            }
            else if(!"".equals(smsInMemory) && !sms.equals(smsInMemory)) {
                map.put("status", 500);
            }
            else{
                map.put("status", 200);
            }
        }
        else{
            if(redisService.exist(tokenId+phone)) {
                if(redisService.exist(smsInMemory)) redisService.remove(smsInMemory);
                if(redisService.exist(phone)) redisService.remove(phone);
                redisService.remove(tokenId+phone);
                map.put("status", 403);
            }
        }
        return map;
    }

    @GetMapping("auth/getImage")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
            String codeInSession = imageCode.getCode();
            sessionStrategy.removeAttribute(new ServletWebRequest(request), MyConstants.SESSION_KEY);
            sessionStrategy.setAttribute(new ServletWebRequest(request), MyConstants.SESSION_KEY, codeInSession);
            ImageIO.write(imageCode.getImage(), "PNG", response.getOutputStream());

            log.debug("image code in memory: "+codeInSession);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("auth/getImage/base64")
    public Map<String, String> createImageCodeBase64(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> map = new HashMap<>();

        try{
            response.setContentType("image/png");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");

            ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
            String codeInSession = imageCode.getCode();
            sessionStrategy.removeAttribute(new ServletWebRequest(request), MyConstants.SESSION_KEY);
            log.debug("removed old img code");
            sessionStrategy.setAttribute(new ServletWebRequest(request), MyConstants.SESSION_KEY, codeInSession);
            log.debug("img code in session: "+imageCode.getCode());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(imageCode.getImage(), "PNG", bos);
            byte[] bytes = bos.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            String base64String = "";
            base64String = encoder.encodeToString(bytes);
            map.put("url", "data:image/png;base64," + base64String);
            map.put("status", "200");

            log.debug("base64 code in memory: "+codeInSession);
            log.debug("SessionId:" + request.getSession().getId());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return map;
    }



}