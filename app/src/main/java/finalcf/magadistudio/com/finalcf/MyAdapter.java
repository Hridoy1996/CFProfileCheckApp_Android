package finalcf.magadistudio.com.finalcf;

/**
 * Created by hrido on 2/5/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Contact>listitems;
    private Context context;

    public MyAdapter(List<Contact> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Contact listitemm = listitems.get(position);

        holder.Name.setText(listitemm.getName());
        Picasso.with(context).load(listitemm.get_imageurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Name;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.des);
            imageView = (ImageView) itemView.findViewById(R.id.imageid);

        }
    }
}
