package com.xyhan.sms.config;

public class ConstantsConfig {
    public static final String SESSION_KEY = "SESSION_KEY_CODE";

    // 图片宽度
    public static final int WIDTH = 90;

    // 图片高度
    public static final int HEIGHT = 20;

    //图片验证码过期时间
    public static final int IMAGE_EXPIRE_SECOND = 30;

    //图片验证码位数
    public static final int IMAGE_RANDOM_SIZE = 4;

    //验证码位数
    public static final int SMS_RANDOM_SIZE = 6;

    //验证码过期时间
    public static final int SMS_EXPIRE_SECOND = 120;

    //请求间隔时间
    public static final long PHONE_REQUEST_INTERVAL = 60;

    //总请求次数
    public static final long MAX_REQUEST_PER_PHONE_USER = 3;

}

