package br.com.bossini.weatherforecastbycityfatecipinoite;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Forecast {
    public final String dayOfWeek;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String iconName;
    public final String description;

    public Forecast (String dayOfWeek, double minTemp,
                     double maxTemp, double humidity,
                        String description, String iconName){
        this.dayOfWeek = dayOfWeek;
        NumberFormat numberFormat = NumberFormat.getInstance();
        this.minTemp = numberFormat.format(minTemp);
        this.maxTemp = numberFormat.format(maxTemp);
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        this.humidity = percentFormat.format(humidity / 100);
        this.iconName = iconName;
        this.description = description;


    }

    @Override
    public String toString() {
        return dayOfWeek + ", min: " + minTemp + ", max: " + maxTemp;
    }
}
