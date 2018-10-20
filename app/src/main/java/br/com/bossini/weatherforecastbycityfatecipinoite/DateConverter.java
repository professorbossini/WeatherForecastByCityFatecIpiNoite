package br.com.bossini.weatherforecastbycityfatecipinoite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {
    public static String converteNumeroParaDiaDaSemana (long timeStamp){
        Calendar agora = Calendar.getInstance();
        agora.setTimeInMillis(timeStamp * 1000);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEEE", new Locale("fr", "CA"));
        TimeZone tz = TimeZone.getDefault();
        agora.add(Calendar.MILLISECOND, tz.getOffset(agora.getTimeInMillis()));
        String diaDaSemana = simpleDateFormat.format(agora.getTime());
        return diaDaSemana;
    }
}
