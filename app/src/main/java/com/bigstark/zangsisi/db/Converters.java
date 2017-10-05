package com.bigstark.zangsisi.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by gangdaegyu on 2017. 10. 5..
 */

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


}
