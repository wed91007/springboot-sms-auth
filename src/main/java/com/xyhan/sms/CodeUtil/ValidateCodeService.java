package com.xyhan.sms.CodeUtil;

import com.xyhan.sms.Config.MyConstants;
import com.xyhan.sms.RedisData.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@CrossOrigin(origins = "http://localhost:4200")
public class ValidateCodeService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsCodeGenerator smsCodeGenerator;

    @Autowired
    private DefaultSmsCodeSender defaultSmsCodeSender;

    private  String tokenId="TOKEN-PHONE-";

    public Map<String, Object> SmsRedisValidate(HttpServletRequest request, HttpServletResponse response, String phone) {
        Map<String, Object> map = new HashMap<>();
        //从redis取出手机号进行校验
        //如果redis不存在该手机：生成验证码，写入redis，返回。设置失效时间。
        //如果redis存在该手机：判断请求时间间隔是否超过；
        if (!redisService.exist(tokenId + phone)) { //当前不验证码存在，产生新验证

            //生成sms code
            ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
            //store in redis

            map.put("verifyCode", smsCode.getCode());
            map.put("phone", phone);

            if (response.getStatus() == 200) {
                map.put("status", "OK");
                redisService.set(tokenId + phone, smsCode.getCode());
                redisService.expire(tokenId + phone, smsCode.getExpireIn());
                //记录次数
                redisService.set(smsCode.getCode(), "1");
                redisService.expire(smsCode.getCode(), smsCode.getExpireIn());

                redisService.set(phone, "1");
                redisService.expire(phone, MyConstants.PHONE_REQUEST_INTERVAL);

                //send code to phone (show in logs)
                defaultSmsCodeSender.send(phone, smsCode.getCode());
            }
        } else {//当前验证码存在，进行判断
            String smsInMemory = redisService.get(tokenId + phone);
            if (!redisService.exist(phone)) {         //距上次请求超过1分钟，phone记录不存在
                if (response.getStatus() == 200) {
                    map.put("status", "OK");
                    long ttl = redisService.ttl(tokenId + phone);

                    redisService.set(phone, "1");
                    redisService.expire(phone, Math.min(ttl, MyConstants.PHONE_REQUEST_INTERVAL)); //有效期最多至sms相同有效期
                    map.put("verifyCode", smsInMemory);
                    map.put("phone", phone);
                    defaultSmsCodeSender.send(phone, smsInMemory);
                }
            } else {//距上次请求未超过1分钟
                if (response.getStatus() == 200) {
                    map.put("status", "repeated");
                }
            }
        }
        return map;
    }







}