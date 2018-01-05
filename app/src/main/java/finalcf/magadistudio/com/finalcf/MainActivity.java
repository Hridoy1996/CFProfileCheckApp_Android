package finalcf.magadistudio.com.finalcf;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static finalcf.magadistudio.com.finalcf.Main2Activity.*;

public class MainActivity extends Activity  {
     DatabaseHandler db = new DatabaseHandler(this);

    private ProgressDialog progressDialog;
    public static String ss=null;
    EditText name;
    int  chk  =1;
    String profile =null;

    public void HistoryClick(View view){
        Intent intent =new Intent(MainActivity.this,HistoryList.class);

        startActivity(intent);

    }

    public  void login(View view) {
        progressDialog = ProgressDialog.show(this, "","Please Wait...", true);
        if (!isOnline()) {
            progressDialog.dismiss();
            Toast.makeText(getApplication(), "internet connection failed", Toast.LENGTH_LONG).show();

        }
        else {

        ss = name.getText().toString();
        if (ss.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(getApplication(), "Blank text", Toast.LENGTH_LONG).show();
        } else {
            DownloadTask task = new DownloadTask();


            task.execute("http://codeforces.com/api/user.info?handles=" + ss);

        }
    }

    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText) findViewById(R.id.nn);

        db.deleteAll();



    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        int check_json=0;
        String stattus=null;

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
                if(res.equals("")){
                    check_json=0;
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
                profile = jsonObject.getString("status");
                if (!profile.isEmpty()) chk = 1;
                else chk = 0;


            } catch (JSONException e) {

                e.printStackTrace();
            }

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            progressDialog.dismiss();

             if (check_json == 0) {
                if (chk == 1) {
                    db.addContact(new Contact(ss));
                    startActivity(intent);
                    chk = 0;
                } else
                    Toast.makeText(getApplication(), " connection faild ! ", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(getApplication(), " wrong handle name ", Toast.LENGTH_LONG).show();

        }
    }



}



