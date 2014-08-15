package my.android.notes.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import my.android.notes.app.R;


public class HttpSpr extends Activity {

    private TextView textSite;
    private EditText urlText;
    private Button goToSite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_spr);
        textSite = (TextView) findViewById(R.id.displaySite);
        urlText = (EditText) findViewById(R.id.urlText);
        goToSite = (Button) findViewById(R.id.go_to_site);

        goToSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnect()) {
                    Uri address = Uri.parse(urlText.getText().toString());
                    try {
                    Intent openLink = new Intent(Intent.ACTION_VIEW, address);
                    startActivity(openLink);
                   }
                   catch (android.content.ActivityNotFoundException e)
                   {
                       Toast toast = Toast.makeText(getApplicationContext(), "Can not load the page. Maybe URL is incorrect.", Toast.LENGTH_LONG);
                       toast.setGravity(Gravity.CENTER, 0,-250);
                       toast.show();

                   }

                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No connections available", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0,-250);
                    toast.show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.http_spr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings_spr) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isConnect(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netwInfo = connManager.getActiveNetworkInfo();
        if(netwInfo != null && netwInfo.isConnected())
        return true;
        else return false;
    }


    public void clickManage(View v) {

        String stringURL = urlText.getText().toString();

        if (isConnect()) {
            new DownloadWebPage().execute(stringURL);
        } else {
            textSite.setText("No connections available");
        }
    }

    private class DownloadWebPage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            try {
                return downloadURL(url[0]);
            } catch (IOException e) {
                return "Can not load the page. Maybe URL is incorrect.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textSite.setText(result);
        }

    }


    private String downloadURL(String myURL) throws IOException {

        InputStream stream = null;
//        int length = 1000;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(myURL);
            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setReadTimeout(50000);
//            urlConnection.setConnectTimeout(50000);
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoInput(true);

            urlConnection.connect();

//            int responseCode = urlConnection.getResponseCode();
//            Toast.makeText(this, "The response from server is: " + response, Toast.LENGTH_SHORT).show();
//            Log.d(getClass().getName(), "The response is: " + response);

            stream = new BufferedInputStream(urlConnection.getInputStream());

            String page = convertStream(stream, urlConnection.getContentLength());

            return page;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }
    }

    public String convertStream(InputStream strm, int len) throws IOException {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = strm.read();
        while(result != -1) {
            byte b = (byte)result;
            buf.write(b);
            result = strm.read();
        }
        return buf.toString();
    }




}
