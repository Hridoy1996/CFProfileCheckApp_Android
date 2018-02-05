package finalcf.magadistudio.com.finalcf;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistoryList extends Activity {
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<Contact> listitems;
    public  static int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DatabaseHandler db = new DatabaseHandler(this);

      //  final ArrayList<String> making_list = new ArrayList<String>();
        listitems = new ArrayList<>();
        List<Contact> contacts = db.getAllContacts();

        for(Contact cn : contacts ){
            Contact listitemmm = new Contact(
                    cn.getName(),cn.get_imageurl()
            );
            listitems.add(listitemmm);
        }


        adapter = new MyAdapter(listitems, this);
        recyclerView.setAdapter(adapter);

    }
}