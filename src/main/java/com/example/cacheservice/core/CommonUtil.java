package com.example.cacheservice.core;

import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
    public static Boolean isDateExpire(Date expiredTime) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long expiredTimeStamp = expiredTime.getTime();
        return now > expiredTimeStamp;
    }

    public static Date getDateByExpire(Long expire) {
        return new Date(System.currentTimeMillis() + expire);
    }
}
