package br.com.bossini.weatherforecastbycityfatecipinoite;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    private List <Forecast> previsoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        previsoes = new ArrayList<>();
        locationEditText =
                findViewById(R.id.locationEditText);
        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade =
                        locationEditText.
                                getEditableText().
                                toString();
                String end =
                        getString(R.string.web_service_url);
                end += cidade;
                String chave =
                        getString(R.string.api_key);
                end += "&APPID=";
                end += chave;
                end += "&units=metric&lang=pt";
                new ObtemTemperaturas().execute(end);
            }
        });
    }

    private class ObtemTemperaturas
            extends AsyncTask <String, Void, String>{
        @Override
        protected String doInBackground(String... enderecos) {
            try{
                URL url = new URL(enderecos[0]);
                HttpURLConnection connection =
                        (HttpURLConnection)url.openConnection();
                InputStream inputStream =
                        connection.getInputStream();
                InputStreamReader inputStreamReader =
                        new InputStreamReader(inputStream);
                BufferedReader reader =
                        new BufferedReader(inputStreamReader);
                String linha = null;
                final StringBuilder stringBuilder = new StringBuilder("");
                while ((linha = reader.readLine()) != null)
                    stringBuilder.append(linha);
                reader.close();
                return stringBuilder.toString();
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonS) {
            //Toast.makeText(MainActivity.this, jsonS, Toast.LENGTH_SHORT).show();
            previsoes.clear();
            try{
                JSONObject json = new JSONObject(jsonS);
                JSONArray list = json.getJSONArray("list");
                for (int i = 0; i < list.length(); i++){
                    JSONObject previsao = list.getJSONObject(i);
                    long dt = previsao.getLong("dt");
                    JSONObject main = previsao.getJSONObject("main");
                    double temp_min = main.getDouble("temp_min");
                    double temp_max = main.getDouble("temp_max");
                    int humidity = main.getInt("humidity");
                    JSONArray weather = previsao.getJSONArray("weather");
                    JSONObject unicoNoWeather = weather.getJSONObject(0);
                    String description = unicoNoWeather.getString("description");
                    String icon = unicoNoWeather.getString("icon");
                    Forecast forecast =
                            new Forecast(
                                    DateConverter.
                                            converteNumeroParaDiaDaSemana(dt),
                                    temp_min, temp_max,
                                    humidity, description, icon);
                    previsoes.add(forecast);
                }
                Toast.makeText(MainActivity.this, previsoes.toString(), Toast.LENGTH_SHORT).show();

            }
            catch (JSONException e){
                e.printStackTrace();
            }


        }
    }
}

/*new Thread(new Runnable() {
@Override
public void run() {
        String cidade =
        locationEditText.
        getEditableText().
        toString();
        String end =
        getString(R.string.web_service_url);
        end += cidade;
        String chave =
        getString(R.string.api_key);
        end += "&APPID=";
        end += chave;
        end += "&units=metric";
        try{
        URL url = new URL(end);
        HttpURLConnection connection =
        (HttpURLConnection)url.openConnection();
        InputStream inputStream =
        connection.getInputStream();
        InputStreamReader inputStreamReader =
        new InputStreamReader(inputStream);
        BufferedReader reader =
        new BufferedReader(inputStreamReader);
        String linha = null;
final StringBuilder stringBuilder = new StringBuilder("");
        while ((linha = reader.readLine()) != null)
        stringBuilder.append(linha);
        reader.close();
        runOnUiThread(()->{
        Toast.makeText(MainActivity.this,
        stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        });
        }
        catch (MalformedURLException e){
        e.printStackTrace();
        }
        catch (IOException e){
        e.printStackTrace();
        }

        }
        }).start();*/
