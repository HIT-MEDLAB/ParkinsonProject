package com.example.parkinson.model.general_models;

import androidx.annotation.NonNull;

public class Time {
    private int minutes, hour;

    public Time(){}

    public Time(int minutes, int hour) {
        this.minutes = minutes;
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    /*
    פונקצייה שמחזירה את הזמן כסטרינג מלא כמו שעון דיגטלי
     */
    public String fullTime(){
        String hoursString;
        String minutesString;
        if(hour < 10){
            hoursString = "0" + hour;
        } else {
            hoursString = String.valueOf(hour);
        }

        if(minutes == 0){
            minutesString = "00";
        } else {
            minutesString = "30";
        }

         if(minutes == -1 && hour == -1){
             minutesString = "00";
             hoursString = "00";
         }

        return hoursString +":"+ minutesString;
    }

    @NonNull
    @Override
    public String toString() {
        return fullTime();
    }
}
