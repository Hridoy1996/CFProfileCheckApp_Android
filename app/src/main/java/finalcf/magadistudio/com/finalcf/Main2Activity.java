package finalcf.magadistudio.com.finalcf;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.name;

public class Main2Activity extends Activity {
    DatabaseHandler db = new DatabaseHandler(this);
    TextView tv1 ;

    String image1=null;


    public void onclick_profile (View view){
        ProgressDialog progressDialog;
        Intent intent1 =new Intent(Main2Activity.this,Main4Activity.class);
        startActivity(intent1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv1= (TextView)findViewById(R.id.textView2);

        DownloadTask task = new DownloadTask();
        String str=MainActivity.ss;
        task.execute("http://codeforces.com/api/user.info?handles="+str);


    }

    public class DownloadTask extends AsyncTask<String, Void, String> {


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

                    tv1.setText(  elements.getString("firstName") + " " +  elements.getString("lastName") );

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ImageLoaderTask imageLoaderTask = new ImageLoaderTask();
            imageLoaderTask.execute(image1);
            db.addContact(new Contact(MainActivity.ss,image1));
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

                CircleImageView imageView = (CircleImageView) findViewById(R.id.imageView);
             //   imageView.setBackground(drawable);
                imageView.setImageDrawable(drawable);

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