package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SaveResourcesActivity extends NavigationActivity {

    Config conf=new Config();
    private final String URL = conf.getURL()+"get_form_list.php";
    ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    private DBHelper mydb;
    ArrayList<SaveResourcesModel> arrayList = new ArrayList<SaveResourcesModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_save_resources, null, false);
        drawer.addView(view,0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Send to Server");
        autocomplete();
    }


    public void autocomplete(){
        final RecyclerView rcv = (RecyclerView) findViewById(R.id.recylv);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        mydb = new DBHelper(this);
        sharedPref = getSharedPreferences("LOGININFO", MODE_PRIVATE);
        int userid = sharedPref.getInt("userid", 0);
        //Log.v("UserId",Integer.toString(userid));

        Cursor response = mydb.getAllResources(0,userid);
        int totalitem = response.getCount();
        Log.v("TotalData",Integer.toString(totalitem));

        if(response!= null){
            if(response.moveToFirst()){
                do{
                    SaveResourcesModel saveitem = new SaveResourcesModel();
                    saveitem.setId(response.getInt(response.getColumnIndex("id")));
                    saveitem.setShelter(response.getString(response.getColumnIndex("shelter")));
                    saveitem.setMaterials(response.getString(response.getColumnIndex("materials")));
                    saveitem.setDate(response.getString(response.getColumnIndex("date")));
                    arrayList.add(saveitem);
                }
                while(response.moveToNext());
            }
        }
        if(totalitem > 0) {
            rcv.setAdapter(new RecyleSavedResourcesAdapter(this, arrayList));
            response.close();
        }
        else{
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.zerodata);
            linearLayout.setVisibility(View.VISIBLE);
            rcv.setVisibility(View.GONE);
        }
    }

}
