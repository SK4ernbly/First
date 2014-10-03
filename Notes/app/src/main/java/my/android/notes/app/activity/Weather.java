package my.android.notes.app.activity;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import my.android.notes.app.R;

public class Weather extends Activity {

    private Button checkWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        overridePendingTransition(R.anim.weather_transl, R.anim.weather_alpha);
        checkWeather = (Button) findViewById(R.id.button_check);
        checkWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadWeatherInfo();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.weather_transl, R.anim.weather_alpha);
    }

    private void loadWeatherInfo() throws IOException, XmlPullParserException {
        URL url = new URL("http://export.yandex.ru/weather-ng/forecasts/33372.xml");
        XmlPullParser xpp =  XmlPullParserFactory.newInstance().newPullParser();
        xpp.setInput(url.openConnection().getInputStream(), null);
        while(xpp.getEventType() != XmlPullParser.END_DOCUMENT){
            if(xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("day date")){

            }
        }



    }


    private boolean isConnect(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netwInfo = connManager.getActiveNetworkInfo();
        if(netwInfo != null && netwInfo.isConnected())
            return true;
        else return false;
    }
}
