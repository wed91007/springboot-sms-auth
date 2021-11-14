package com.xyhan.sms.core.code.image;

import com.xyhan.sms.core.code.ValidateCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
@Slf4j
@EqualsAndHashCode(callSuper=false)
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireId) {
        super(code, LocalDateTime.now().plusSeconds(expireId));
        this.image = image;
//        log.debug("new ImageCode created");
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime localDateTime) {
        super(code, localDateTime);
        this.image = image;
//        log.debug("new ImageCode created");
    }
}