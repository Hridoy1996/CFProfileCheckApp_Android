package finalcf.magadistudio.com.finalcf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static finalcf.magadistudio.com.finalcf.Main2Activity.*;

public class MainActivity extends Activity  {
    public static String ss=null;
    String isok="";
    EditText name;

    public  void login(View view)
    {
        ss= name.getText().toString();
        Intent intent =new Intent(MainActivity.this,Main2Activity.class);



        if (ss == null || ss.isEmpty()) {
            Toast.makeText(getApplicationContext(), " user name can't be empty " , Toast.LENGTH_LONG).show();
        }
        else {
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText) findViewById(R.id.nn);
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {
        String image1=null;

        @Override
        protected String doInBackground(String... urls) {
            String res = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;
                    res += current;
                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            try {
                JSONObject jsonObject = new JSONObject(res);
                String profile = jsonObject.getString("result");
                JSONArray ara = new JSONArray(profile);
                for (int i = 0; i < ara.length(); i++) {

                    JSONObject elements = ara.getJSONObject(i);

                   image1 = elements.getString("titlePhoto");

                   // tv1.setText(  elements.getString("firstName") + " " +  elements.getString("lastName") );

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ImageLoaderTask imageLoaderTask = new ImageLoaderTask();
            imageLoaderTask.execute(image1);
        }
    }

    private class ImageLoaderTask extends AsyncTask<String, Void, Drawable> {
        @Override
        protected Drawable doInBackground(String... imageUrls) {
            Drawable image = null;
            try {
                String url = imageUrls[0];
                if (null != url) {
                    image = getDrawable(url);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            return image;
        }

        protected void onPostExecute(Drawable drawable) {
            if (drawable != null) {
               // ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setBackgroundDrawable(drawable);
            }
        }


        private Drawable getDrawable(String address) {
            try {
                URL url = new URL(address);
                InputStream is = (InputStream) url.getContent();
                Drawable d = Drawable.createFromStream(is, "src");
                return d;
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }
    }

}



