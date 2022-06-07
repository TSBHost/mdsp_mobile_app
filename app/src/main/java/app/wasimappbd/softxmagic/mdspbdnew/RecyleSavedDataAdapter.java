package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RecyleSavedDataAdapter extends RecyclerView.Adapter<RecyleSavedDataAdapter.CustomVHolder> {

    //CustomVHolder cvh;
    private Context context;

    Activity activity;
    List<SaveSubmissionModel> data = Collections.emptyList();
    //private final String IUrl = conf.getImgURL()+"news/";
    private List<String> currentSelectedItems = new ArrayList<>();
    SaveSubmissionActivity saveactivity;

    public RecyleSavedDataAdapter(Context context, List<SaveSubmissionModel> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public CustomVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_sendtoserver, parent, false);
        //cvh = new CustomVHolder(view);
        return new CustomVHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomVHolder holder, final int position) {

        final SaveSubmissionModel objIncome = data.get(position);

            holder.titem.setText(data.get(position).getShelter());
            holder.mtask.setText(data.get(position).getMtask());
            holder.dated.setText(data.get(position).getDate());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "ID: "+news.getId(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, SavedSubmissionDetails.class);
                    intent.putExtra("id",data.get(position).getId());
                    context.startActivity(intent);
                }
            });


       /* holder.cbSelect.setTag(position);
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.cbSelect.getTag();
                if (data.get(pos).getSelected()) {
                    data.get(pos).setSelected(false);
                } else {
                    data.get(pos).setSelected(true);
                    String strval = Integer.toString(data.get(pos).getId());

                    Toast.makeText(context, strval + " clicked!", Toast.LENGTH_SHORT).show();
                    currentSelectedItems.add(strval);

                    Intent intent = new Intent(context, SavedSubmissionDetails.class);
                    intent.putExtra("id",data.get(position).getId());
                    context.startActivity(intent);
                }
            }
        });*/

        /*holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                String strval = Integer.toString(data.get(position).getId());

                if(compoundButton.isChecked())
                {
                    compoundButton.setChecked(true);
                    currentSelectedItems.add(strval);
                    //holder.userdata.setBackgroundColor(context.getResources().getColor(R.color.black_overlay));
                    holder.userdata.setBackgroundResource(R.drawable.checked_box);
                    //Toast.makeText(context, strval + " clicked!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    compoundButton.setChecked(false);
                    currentSelectedItems.remove(strval);
                    holder.userdata.setBackgroundResource(R.drawable.box_shadow);
                   // Toast.makeText(context, strval + " removed!", Toast.LENGTH_SHORT).show();
                }
                saveactivity = new SaveSubmissionActivity();
                saveactivity.getCheckedItems(currentSelectedItems);
                //Toast.makeText(context, currentSelectedItems.toString() + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomVHolder extends RecyclerView.ViewHolder{

        TextView titem,mtask,dated;
        LinearLayout userdata;
        //public CheckBox cbSelect;
        public CustomVHolder(View itemView) {
            super(itemView);
            titem = (TextView) itemView.findViewById(R.id.titles);
            mtask = (TextView) itemView.findViewById(R.id.mtask);
            dated = (TextView) itemView.findViewById(R.id.datetime);
            //cbSelect = (CheckBox) itemView.findViewById(R.id.cbSelect);
            userdata = (LinearLayout) itemView.findViewById(R.id.userdata);
        }
    }
}
