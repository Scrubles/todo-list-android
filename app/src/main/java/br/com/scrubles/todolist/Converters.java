package br.com.scrubles.todolist;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class Converters {

    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        if(value == null)
            return null;

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(value);
        return date;
    }

    @TypeConverter
    public static Long dateToTimestamp(Calendar date) {
        return date == null ? null : date.getTimeInMillis();
    }
}
