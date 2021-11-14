package com.xyhan.sms.CodeUtil;

import java.time.LocalDateTime;

public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    private long expireIn;

    public ValidateCode(String code, int expireIn){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
        this.expireIn = expireIn;
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        super();
        this.code = code;
        this.expireTime = expireTime;
    }
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(getExpireTime());
    }

    public String getCode() {
        return this.code;
    }

    public LocalDateTime getExpireTime() {
        return this.expireTime;
    }

    public long getExpireIn() {
        return this.expireIn;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ValidateCode)) return false;
        final ValidateCode other = (ValidateCode) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$expireTime = this.getExpireTime();
        final Object other$expireTime = other.getExpireTime();
        if (this$expireTime == null ? other$expireTime != null : !this$expireTime.equals(other$expireTime))
            return false;
        if (this.getExpireIn() != other.getExpireIn()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ValidateCode;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $expireTime = this.getExpireTime();
        result = result * PRIME + ($expireTime == null ? 43 : $expireTime.hashCode());
        final long $expireIn = this.getExpireIn();
        result = result * PRIME + (int) ($expireIn >>> 32 ^ $expireIn);
        return result;
    }

    public String toString() {
        return "ValidateCode(code=" + this.getCode() + ", expireTime=" + this.getExpireTime() + ", expireIn=" + this.getExpireIn() + ")";
    }
}
