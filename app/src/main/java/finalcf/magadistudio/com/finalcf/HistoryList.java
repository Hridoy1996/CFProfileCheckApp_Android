package finalcf.magadistudio.com.finalcf;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class HistoryList extends AppCompatActivity {


    private String TAG = HistoryList.class.getSimpleName();

    private ArrayList<Contact> images;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);

        fetchImages();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(),
                recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplication(),"bhai re bhai aste tip",Toast.LENGTH_LONG).show();

            }
        }));


    }

    private void fetchImages() {

        images.clear();

      DatabaseHandler db = new DatabaseHandler(this);
      List<Contact> contacts = db.getAllContacts();

        for(Contact cn : contacts ){
            Contact listitemmm = new Contact(
                    cn.getName(),cn.get_imageurl()
            );
            images.add(listitemmm);
        }


        mAdapter.notifyDataSetChanged();

    }

}
