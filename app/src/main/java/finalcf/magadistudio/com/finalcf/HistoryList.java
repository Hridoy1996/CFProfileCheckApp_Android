package finalcf.magadistudio.com.finalcf;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistoryList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        ListView lstvw = (ListView)findViewById(R.id.listviewid);

        DatabaseHandler db = new DatabaseHandler(this);

        final ArrayList<String> making_list = new ArrayList<String>();

        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Handle: " + cn.getName();
            making_list.add(log);

        }

        ArrayAdapter<String> araadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,making_list);
        lstvw.setAdapter(araadapter);

    }
}
