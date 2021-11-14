package com.xyhan.sms.CodeUtil;

import org.slf4j.Logger;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ImageCode.class);
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireId) {
        super(code, LocalDateTime.now().plusSeconds(expireId));
        this.image = image;
        log.debug("new ImageCode created");
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime localDateTime) {
        super(code, localDateTime);
        this.image = image;
        log.debug("new ImageCode created");
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String toString() {
        return "ImageCode(image=" + this.getImage() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ImageCode)) return false;
        final ImageCode other = (ImageCode) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$image = this.getImage();
        final Object other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ImageCode;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $image = this.getImage();
        result = result * PRIME + ($image == null ? 43 : $image.hashCode());
        return result;
    }
}