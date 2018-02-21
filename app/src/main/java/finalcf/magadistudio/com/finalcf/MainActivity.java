package finalcf.magadistudio.com.finalcf;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity  {


    private ProgressDialog progressDialog;
    public static String ss=null;
    EditText name;
    int  chk  =0;
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
        name=(AutoCompleteTextView) findViewById(R.id.nn);
        DatabaseHandler db = new DatabaseHandler(this);


        List<Contact> contacts = db.getAllContacts();
        int i=0;

        for(Contact cn : contacts ){

            i++;
        }
        String[] str=new String[i];

        i=0;

        for(Contact cn : contacts ){

            str[i++]=  cn.getName();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,str);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.nn);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);

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
                    check_json=1;
                }
                if(res.equals("")){

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

            if (check_json == 1) {
                if (chk == 1) {

                    startActivity(intent);
                    chk = 0;
                } else
                    Toast.makeText(getApplication(), " connection faild ! ", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplication(), " wrong handle name ", Toast.LENGTH_LONG).show();
                check_json=0;
            }

        }
    }



}

