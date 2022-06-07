package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class RecyleSavedResourcesAdapter extends RecyclerView.Adapter<RecyleSavedResourcesAdapter.CustomVHolder> {

    //CustomVHolder cvh;
    private Context context;

    Activity activity;
    List<SaveResourcesModel> data = Collections.emptyList();

    public RecyleSavedResourcesAdapter(Context context, List<SaveResourcesModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public CustomVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_resources, parent, false);
        //cvh = new CustomVHolder(view);
        return new CustomVHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomVHolder holder, final int position) {

            holder.titem.setText(data.get(position).getMaterials());
            holder.dated.setText(data.get(position).getDate());
            holder.shelt.setText(data.get(position).getShelter());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "ID: "+news.getId(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, ResourceDetails.class);
                    intent.putExtra("id",data.get(position).getId());
                    context.startActivity(intent);

                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomVHolder extends RecyclerView.ViewHolder{

        TextView titem,dated,shelt;
        public CustomVHolder(View itemView) {
            super(itemView);
            shelt = (TextView) itemView.findViewById(R.id.shelter);
            titem = (TextView) itemView.findViewById(R.id.titles);
            dated = (TextView) itemView.findViewById(R.id.datetime);
        }
    }
}
