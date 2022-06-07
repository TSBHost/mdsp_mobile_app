package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SyncData extends NavigationActivity implements View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    private DBHelper mydb;
    HashMap<String, String> queryValues;
    Context context;
    Config conf = new Config();
    Button sheltbtn,subformbtn,feedbtn;
    private final String URLShelter = conf.getURL()+"get_shelter_list.php";
    private final String URLSform = conf.getURL()+"get_submitted_form_list_sync.php";
    private final String URLFeedback = conf.getURL()+"get_feedback.php";
    ProgressDialog loadingprog;
    Boolean shelterflag,feedbackFlag,submissionFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sync);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_sync_data, null);
        drawer.addView(v, 0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sync Data");

        sheltbtn = (Button) findViewById(R.id.shelterbtn);
        subformbtn = (Button) findViewById(R.id.submitform);
        feedbtn = (Button) findViewById(R.id.feedbackbtn);

        sheltbtn.setOnClickListener(this);
        subformbtn.setOnClickListener(this);
        feedbtn.setOnClickListener(this);
        sharedPref = getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shelterbtn:
                checkConnection("shelter");
                break;
            case R.id.submitform:
                checkConnection("submission");
                break;
            case R.id.feedbackbtn:
                checkConnection("feedback");
                break;
        }
    }

    public void shelterCode(final String xtval){
        //loadingprog = ProgressDialog.show(this,"Data loading and Syncing","Please wait...",false,false);
        loadingprog = new ProgressDialog(this);
        loadingprog.setTitle("Shelter Data loading");
        loadingprog.setMessage("Loading Please Wait...");
        loadingprog.setCancelable(false);
        loadingprog.show();
        StringRequest request = new StringRequest(Request.Method.POST,  URLShelter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Log.d("ShelterList: ",response);
                    JSONArray JA=new JSONArray(response);
                    for(int i = 0; i < JA.length(); i++) {
                        JSONObject obj = JA.getJSONObject(i);
                        int totalitem = obj.getInt("totalitems");
                        if(JA.length() == totalitem) {
                            loadingprog.setTitle("Data Syncing");
                            queryValues = new HashMap<String, String>();
                            queryValues.put("code", obj.getString("shelter"));
                            mydb = new DBHelper(SyncData.this);
                            shelterflag = mydb.insertShelter(queryValues);
                        }
                    }

                    if(shelterflag) {
                        Toast.makeText(getApplicationContext(), "Successfully Sync Data", Toast.LENGTH_LONG).show();
                        loadingprog.dismiss();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Failed Syncing Data", Toast.LENGTH_LONG).show();
                        loadingprog.dismiss();
                    }
                    int totaldata = mydb.numberOfRows();
                    Log.v("TotalSyncShelter", Integer.toString(totaldata));

                }
                catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //progressDialog.dismiss();
                Log.e("Error", "Failed");
                Toast.makeText(getApplicationContext(),
                        "Data Loading failed", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new Hashtable<String, String>();
                params.put("allshelter", xtval);
                //params.put("terms", "FEP");

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    public void submittedFormCode(){
        final ProgressDialog loading = ProgressDialog.show(this,"Data Syncing","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST,  URLSform, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("SubmittedForms: ",response);
                    JSONArray JA=new JSONArray(response);
                    for(int i = 0; i < JA.length(); i++) {
                        JSONObject obj = JA.getJSONObject(i);
                        queryValues = new HashMap<String, String>();
                        queryValues.put("userid", obj.getString("userid"));
                        queryValues.put("ShelterCode", obj.getString("shelter"));
                        queryValues.put("MajorTasks", obj.getString("mtask"));
                        queryValues.put("Tasks", obj.getString("task"));
                        queryValues.put("Comments", obj.getString("comm"));
                        queryValues.put("Latitude", obj.getString("lat"));
                        queryValues.put("Longitude", obj.getString("lon"));
                        queryValues.put("SubmisionDate", obj.getString("sdate"));

                        mydb = new DBHelper(SyncData.this);
                        submissionFlag = mydb.insertSubmittedForm(queryValues);
                    }

                    if(submissionFlag) {
                        Toast.makeText(getApplicationContext(), "Successfull Synced Data", Toast.LENGTH_LONG).show();

                        loading.dismiss();
                        int userid = sharedPref.getInt("userid", 0);
                        ArrayList array_list = mydb.getAllLikeForms(userid);
                        Log.v("userformlist", array_list.toString());
                        mydb.close();
                    }

                } catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //progressDialog.dismiss();
                Log.e("Error", "Failed");
                Toast.makeText(getApplicationContext(),
                        "Data Loading failed", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int userid = sharedPref.getInt("userid", 0);
                Log.d("UserIdSession: ",Integer.toString(userid));
                Map<String,String> params = new Hashtable<String, String>();
                params.put("userid", Integer.toString(userid));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    public void feedbackList(){
        final ProgressDialog loading = ProgressDialog.show(this,"Data Syncing","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST,  URLFeedback, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("FeedbackForSubmission: ",response);
                    JSONArray JA=new JSONArray(response);
                    for(int i = 0; i < JA.length(); i++) {
                        JSONObject obj = JA.getJSONObject(i);
                        queryValues = new HashMap<String, String>();
                        //$data['totalform'] = $stmt->num_rows;
                        int userid = sharedPref.getInt("userid", 0);

                        queryValues.put("userid", Integer.toString(userid));
                        queryValues.put("shelter", obj.getString("shelter"));
                        queryValues.put("username", obj.getString("uname"));
                        queryValues.put("mtask", obj.getString("mtask"));
                        queryValues.put("date", obj.getString("sdate"));
                        queryValues.put("subid", obj.getString("subid"));
                        queryValues.put("hqf1", obj.getString("reply"));
                        queryValues.put("hqfd1", obj.getString("rdate"));
                        queryValues.put("hqf2", obj.getString("reply2"));
                        queryValues.put("hqfd2", obj.getString("rdate2"));
                        queryValues.put("hqf3", obj.getString("reply3"));
                        queryValues.put("hqfd3", obj.getString("rdate3"));
                        queryValues.put("ufeed1", obj.getString("ufeed"));
                        queryValues.put("ufeed2", obj.getString("ufeed1"));

                        mydb = new DBHelper(SyncData.this);
                        feedbackFlag = mydb.insertFeedback(queryValues);
                    }

                    if(feedbackFlag) {
                        Toast.makeText(getApplicationContext(), "Successfull Synced Data", Toast.LENGTH_LONG).show();

                        loading.dismiss();
                        //int userid = sharedPref.getInt("userid", 0);
                        //ArrayList array_list = mydb.getAllLikeFeedback(userid);
                        //Log.v("userformlist", array_list.toString());
                        mydb.close();
                    }

                } catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //progressDialog.dismiss();
                Log.e("Error", "Failed");
                Toast.makeText(getApplicationContext(),
                        "Data Loading failed", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int userid = sharedPref.getInt("userid", 0);

                Map<String,String> params = new Hashtable<String, String>();
                params.put("userid", Integer.toString(userid));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }





    private void checkConnection(String methodeType) {
        boolean isConnected = ConnectivityReceiver.isConnected(this);
        //showSnack(isConnected);
        String message;
        if (isConnected) {
            message = "Connected";
            if(methodeType.equals("shelter")) {
                shelterCode("allshelter");
            }
            else if(methodeType.equals("submission")) {
                submittedFormCode();
            }
            else if(methodeType.equals("feedback")) {
                feedbackList();
            }
        }
        else {
            message = "Network Failed. Please Check connection";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            final RecyclerView rcv = (RecyclerView) findViewById(R.id.recylv);
            rcv.setLayoutManager(new LinearLayoutManager(this));
            rcv.setAdapter(new DisconnectAdapter(this));
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        //showSnack(isConnected);
        String message;
        if (isConnected) {
            Toast.makeText(getApplicationContext(), "Connected Plase Try again", Toast.LENGTH_LONG).show();
            //color = Color.WHITE;
        } else {
            message = "Network Failed. Please Check connection";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            final RecyclerView rcv = (RecyclerView) findViewById(R.id.recylv);
            rcv.setLayoutManager(new LinearLayoutManager(this));
            rcv.setAdapter(new DisconnectAdapter(this));
        }
    }
}
