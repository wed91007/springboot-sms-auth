package com.xyhan.sms.core.code.image;

import com.xyhan.sms.config.ConstantsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageCodeService {
    @Autowired
    SessionStrategy sessionStrategy;

    @Autowired
    ImageCodeGenerator imageCodeGenerator;

    public void pngCodeGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("image/png");
            addResponseHeader(request, response);
            ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
            String codeInSession = imageCode.getCode();
            sessionStrategy.removeAttribute(new ServletWebRequest(request), ConstantsConfig.SESSION_KEY);
            sessionStrategy.setAttribute(new ServletWebRequest(request), ConstantsConfig.SESSION_KEY, codeInSession);
            ImageIO.write(imageCode.getImage(), "PNG", response.getOutputStream());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Map<String, String> base64CodeGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> map = new HashMap<>();
        try{
            addResponseHeader(request, response);
            ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
            String codeInSession = imageCode.getCode();
            sessionStrategy.removeAttribute(new ServletWebRequest(request), ConstantsConfig.SESSION_KEY);
//            log.debug("removed old img code");
            sessionStrategy.setAttribute(new ServletWebRequest(request), ConstantsConfig.SESSION_KEY, codeInSession);
//            log.debug("img code in session: "+imageCode.getCode());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(imageCode.getImage(), "PNG", bos);
            byte[] bytes = bos.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            String base64String = "";
            base64String = encoder.encodeToString(bytes);
            map.put("url", "data:image/png;base64," + base64String);
            map.put("status", "200");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private void addResponseHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expire", "0");
        response.setHeader("Pragma", "no-cache");
    }
}
