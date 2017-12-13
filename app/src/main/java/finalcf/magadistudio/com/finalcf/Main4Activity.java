package finalcf.magadistudio.com.finalcf;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import static android.R.attr.name;
import static finalcf.magadistudio.com.finalcf.MainActivity.ss;

public class Main4Activity extends Activity {
    TextView tx ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        tx= (TextView)findViewById(R.id.textView5);
        DownloadTask1 task = new DownloadTask1();
        String str=MainActivity.ss;
        task.execute("http://codeforces.com/api/user.info?handles="+str);

    }
    public class DownloadTask1 extends AsyncTask<String, Void , String> {


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

            }   catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }
        String sss="";
        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);

            try {
                JSONObject jsonObject = new JSONObject(res);
                String id= jsonObject.getString("result");
                Log.i("rank ",id);

                JSONArray arr = new JSONArray(id);

                for (int i=0;i<arr.length();i++)
                {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    sss = " handle         :" + jsonPart.getString("handle") + "\n" +
                            " country        :" + jsonPart.getString("country") + "\n" +
                            " city              :" + jsonPart.getString("city") + "\n" +
                            " rating          :" + jsonPart.getString("rating") + "\n" +
                            " friendOfCount :" + jsonPart.getString("friendOfCount") + "\n" +
                            " firstName     :" + jsonPart.getString("firstName") + "\n" +
                            " lastName      :" + jsonPart.getString("lastName") + "\n" +
                            " organization :" + jsonPart.getString("organization") + "\n" +
                            " rank            :" + jsonPart.getString("rank") + "\n" +
                            " maxRating    :" + jsonPart.getString("maxRating");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            tx.setText(sss);

        }


    }


}
