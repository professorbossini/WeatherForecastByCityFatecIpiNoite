package br.com.bossini.weatherforecastbycityfatecipinoite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ForecastAdapter
                    extends ArrayAdapter <Forecast> {

    public ForecastAdapter (Context context,
                            List <Forecast> forecast){
        super (context, -1, forecast);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        Context context = getContext();
        LayoutInflater inflater =
                    LayoutInflater.from(context);
        View raiz = inflater.
                inflate(R.layout.list_item, parent, false);

        Forecast caraDaVez = getItem(position);

        TextView dayTextView = raiz.findViewById(R.id.dayTextView);
        TextView lowTextView = raiz.findViewById(R.id.lowTextView);
        TextView highTextView = raiz.findViewById(R.id.highTextView);
        TextView humidityTextView = raiz.findViewById(R.id.humidityTextView);
        ImageView conditionImageView = raiz.findViewById(R.id.conditionImageView);
        new BaixaImagem(conditionImageView).
                execute(context.getString(R.string.image_download_url,
                        caraDaVez.iconName));
        dayTextView.setText(context.
                getString(R.string.day_description, caraDaVez.dayOfWeek,
                        caraDaVez.description));

        lowTextView.setText(context.getString(
                R.string.low_temp, caraDaVez.minTemp));

        highTextView.setText(context.getString(
                R.string.high_temp,caraDaVez.maxTemp));

        humidityTextView.setText (context.getString(
                R.string.humidity, caraDaVez.humidity));
        return raiz;
    }
    private class BaixaImagem extends AsyncTask <String, Void, Bitmap>{

        private ImageView conditionImageView;
        public BaixaImagem (ImageView conditionImageView){
            this.conditionImageView = conditionImageView;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream =
                        connection.getInputStream();
                Bitmap figura = BitmapFactory.decodeStream(inputStream);
                return figura;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap figura) {
            conditionImageView.setImageBitmap(figura);
        }
    }
}
