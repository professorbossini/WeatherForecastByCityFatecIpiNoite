package br.com.bossini.weatherforecastbycityfatecipinoite;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class ForecastAdapter
                    extends ArrayAdapter <Forecast> {

    public ForecastAdapter (Context context,
                            List <Forecast> forecast){
        super (context, -1, forecast);
    }
}
