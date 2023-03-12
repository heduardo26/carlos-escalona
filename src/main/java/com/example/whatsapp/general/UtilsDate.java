package com.example.whatsapp.general;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilsDate {
    public static LocalDateTime getCurrentDateAndTime(){
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now;
    }


}
