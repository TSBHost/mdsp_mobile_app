package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/*import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;*/

public class ResourceDetails extends NavigationActivity {

    ProgressDialog progressDialog;

    TextView title,date,shelter,c1,c2,c3,c4,c5;
    ImageView imgv1,imgv2,imgv3,imgv4,imgv5;
    Config conf=new Config();
    private final String UPLOAD_URL = conf.getURL()+"resources_insert.php";
    String resmaterials,ndate,sheltercode;
    int submittedid;
    Bitmap bmp1,bmp2,bmp3,bmp4,bmp5;
    ActionBar actionBar;
    private DBHelper mydb;
    public final static String APP_PATH_SD_CARD = "/MDSP/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "resources";
    Context context;
    String Image1,Image2,Image3,Image4,Image5;
    Button UploadImageToServer;
    String imgfile1,imgfile2,imgfile3,imgfile4,imgfile5,cap1,cap2,cap3,cap4,cap5;
    Cursor rs;
    ProgressDialog loading;
    LinearLayout frame1,frame2,frame3,frame4,frame5;
    Dialog dialog;
    Button dialogButton;
    ImageView dImg;
    //public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
   // public static final MediaType MEDIA_TYPE = MediaType.parse("image/WEBP");
   // private static final String IMGUR_CLIENT_ID = "45612848382fb69";
   // public MultipartBody.Builder multipartBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_shelter_details);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_resource_details, null, false);
        drawer.addView(view,0);

       // UploadImageToServer = (Button)  view.findViewById(R.id.sendbtn);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Resources Details ");
        /*UploadImageToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToServer(submittedid,resmaterials,sheltercode,imgfile1,imgfile2,imgfile3,imgfile4,imgfile5,
                        cap1,cap2,cap3,cap4,cap5);
            }
        });*/

        UploadDataSoSerter uploadServer = new UploadDataSoSerter();
        uploadServer.execute();

        frame1 = (LinearLayout) findViewById(R.id.frm1);
        frame2 = (LinearLayout) findViewById(R.id.frm2);
        frame3 = (LinearLayout) findViewById(R.id.frm3);
        frame4 = (LinearLayout) findViewById(R.id.frm4);
        frame5 = (LinearLayout) findViewById(R.id.frm5);

    }



    public String getStringImage(Bitmap bmp){

        if(bmp!=null) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.WEBP, 100, bao);

            byte[] ba = bao.toByteArray();
            String imgstr = Base64.encodeToString(ba, Base64.DEFAULT);
            return imgstr;
        }
        else {
            return "";
        }

    }

   public void uploadToServer(final int submid, final String materials,final String shcode, final String imgf1,final String imgf2,final String imgf3,final String imgf4,final String imgf5,
                              final String capt1,final String capt2,final String capt3,final String capt4,final String capt5){

        loading = ProgressDialog.show(ResourceDetails.this,"Sending data","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String s) {
                       loading.dismiss();
                       try {
                           JSONObject json = new JSONObject(s);
                           int refid = json.getInt("refid");
                           Bundle extras = getIntent().getExtras();
                           int subid = extras.getInt("id");

                           customToast("Successfully sent to server");
                           mydb.updateResourceForm(subid,refid);
                           Intent inthome = new Intent(getApplicationContext(), GridViewActivity.class);
                           startActivity(inthome);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       loading.dismiss();
                       // UploadImageToServer.setBackgroundColor(getResources().getColor(R.color.colorAsh));
                       //UploadImageToServer.setEnabled(false);
                       if (error instanceof NetworkError) {
                       } else if (error instanceof ServerError) {
                       } else if (error instanceof AuthFailureError) {
                       } else if (error instanceof ParseError) {
                       } else if (error instanceof NoConnectionError) {
                           customToast("Oops! Disconnected");
                       } else if (error instanceof TimeoutError) {

                           customToast("Successfully sent to server");
                           Intent inthome = new Intent(getApplicationContext(), GridViewActivity.class);
                           startActivity(inthome);
                       }
                   }
               }){

           @Override
           protected Response<String> parseNetworkResponse(NetworkResponse response) {
               if (response.headers == null)
               {
                   // cant just set a new empty map because the member is final.
                   response = new NetworkResponse(
                           response.statusCode,
                           response.data,
                           Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                           response.notModified,
                           response.networkTimeMs);
               }

               return super.parseNetworkResponse(response);
           }
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               sharedPref = getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
               int userid = sharedPref.getInt("userid", 0);

               Map<String,String> params = new Hashtable<String, String>();
               params.put("userid", Integer.toString(userid));
               params.put("image1", imgf1);
               params.put("image2", imgf2);
               params.put("image3", imgf3);
               params.put("image4", imgf4);
               params.put("image5", imgf5);
               params.put("caption1", capt1);
               params.put("caption2", capt2);
               params.put("caption3", capt3);
               params.put("caption4", capt4);
               params.put("caption5", capt5);
               params.put("materials", materials);
               params.put("shelter", shcode);
               return params;
           }
       };

       stringRequest.setRetryPolicy(new DefaultRetryPolicy(
               100000,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

       RequestQueue requestQueue = Volley.newRequestQueue(this);
       //Adding request to the queue
       requestQueue.add(stringRequest);
    }

    public class UploadDataSoSerter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            //loading = ProgressDialog.show(ResourceDetails.this,"Loading Shelter data","Please wait...",false,false);
            loading = new ProgressDialog(ResourceDetails.this);
            loading.setTitle("Loading Shelter data");
            loading.setMessage("Please Wait...");
            loading.setIcon(R.drawable.loader);
            loading.show();
        }


        @Override
        protected Void doInBackground(Void...params) {
            mydb = new DBHelper(ResourceDetails.this);
            Bundle extras = getIntent().getExtras();
            int subid = extras.getInt("id");
            try {
                //means this is the view part not the add contact part.
                rs = mydb.getResourceData(subid);
                rs.moveToFirst();
                submittedid = subid;
                resmaterials = rs.getString(rs.getColumnIndex("materials"));
                sheltercode = rs.getString(rs.getColumnIndex("shelter"));
                ndate = rs.getString(rs.getColumnIndex("date"));

                rs.close();

                Cursor res1 = mydb.getResourceGallery(subid);
                res1.moveToFirst();

                if(res1!=null && res1.getCount() > 0)
                {
                    if (res1.moveToFirst())
                    {
                        cap1 = res1.getString(res1.getColumnIndex("Caption1"));
                        Image1 = res1.getString(res1.getColumnIndex("Image1"));
                        cap2 = res1.getString(res1.getColumnIndex("Caption2"));
                        Image2 = res1.getString(res1.getColumnIndex("Image2"));
                        cap3 = res1.getString(res1.getColumnIndex("Caption3"));
                        Image3 = res1.getString(res1.getColumnIndex("Image3"));
                        cap4 = res1.getString(res1.getColumnIndex("Caption4"));
                        Image4 = res1.getString(res1.getColumnIndex("Image4"));
                        cap5 = res1.getString(res1.getColumnIndex("Caption5"));
                        Image5 = res1.getString(res1.getColumnIndex("Image5"));
                    }
                }

                Log.v("ImageCaptions", cap1);
                res1.close();
               // String fullPath = getFilesDir().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
                String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
                //Log.v("fullimagepath", fullPath);

                String imgpath1 = fullPath + "/" + Image1;
                BitmapFactory.Options options1 = new BitmapFactory.Options();
                options1.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bmp1 = BitmapFactory.decodeFile(imgpath1, options1);

                String imgpath2 = fullPath + "/" + Image2;
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bmp2 = BitmapFactory.decodeFile(imgpath2, options2);

                String imgpath3 = fullPath + "/" + Image3;
                BitmapFactory.Options options3 = new BitmapFactory.Options();
                options3.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bmp3 = BitmapFactory.decodeFile(imgpath3, options3);

                String imgpath4 = fullPath + "/" + Image4;
                BitmapFactory.Options options4 = new BitmapFactory.Options();
                options4.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bmp4 = BitmapFactory.decodeFile(imgpath4, options4);

                String imgpath5 = fullPath + "/" + Image5;
                BitmapFactory.Options options5 = new BitmapFactory.Options();
                options5.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bmp5 = BitmapFactory.decodeFile(imgpath5, options5);
            }
            catch (Exception e){
                Log.v("CatchVal", e.toString());
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            shelter = (TextView) findViewById(R.id.titles);
            title = (TextView) findViewById(R.id.materials);
            imgv1 = (ImageView) findViewById(R.id.img1);
            imgv2 = (ImageView) findViewById(R.id.img2);
            imgv3 = (ImageView) findViewById(R.id.img3);
            imgv4 = (ImageView) findViewById(R.id.img4);
            imgv5 = (ImageView) findViewById(R.id.img5);

            c1 = (TextView) findViewById(R.id.caption1);
            c2 = (TextView) findViewById(R.id.caption2);
            c3 = (TextView) findViewById(R.id.caption3);
            c4 = (TextView) findViewById(R.id.caption4);
            c5 = (TextView) findViewById(R.id.caption5);
            date = (TextView) findViewById(R.id.datetime);

            shelter.setText(sheltercode);
            title.setText(resmaterials);
            date.setText(ndate);

            if(Image1!=null) {
                frame1.setVisibility(View.VISIBLE);
                imgv1.setVisibility(View.VISIBLE);
                imgv1.setImageBitmap(bmp1);
                imgfile1 = getStringImage(bmp1);
                c1.setText(cap1);
                c1.setVisibility(View.VISIBLE);
                imgv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showBigImage(bmp1);
                    }
                });
            }
            else {
                frame1.setVisibility(View.GONE);
                imgfile1 = "";
                imgv1.setVisibility(View.GONE);
                c1.setVisibility(View.GONE);
            }

            if(Image2!=null) {
                frame2.setVisibility(View.VISIBLE);
                imgv2.setVisibility(View.VISIBLE);
                imgv2.setImageBitmap(bmp2);
                imgfile2 = getStringImage(bmp2);
                c2.setVisibility(View.VISIBLE);
                c2.setText(cap2);
                imgv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showBigImage(bmp2);
                    }
                });
            }
            else {
                frame2.setVisibility(View.GONE);
                imgfile2 = "";
                imgv2.setVisibility(View.GONE);
                c2.setVisibility(View.GONE);
            }


            if(Image3!=null) {
                frame3.setVisibility(View.VISIBLE);
                imgv3.setVisibility(View.VISIBLE);
                imgv3.setImageBitmap(bmp3);
                imgfile3 = getStringImage(bmp3);
                c3.setText(cap3);
                c3.setVisibility(View.GONE);
                imgv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showBigImage(bmp3);
                    }
                });
            }
            else {
                frame3.setVisibility(View.GONE);
                imgfile3 = "";
                imgv3.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
            }


            if(Image4!=null) {
                frame4.setVisibility(View.VISIBLE);
                imgv4.setVisibility(View.VISIBLE);
                imgv4.setImageBitmap(bmp4);
                imgfile4 = getStringImage(bmp4);
                c4.setVisibility(View.GONE);
                c4.setText(cap4);
                imgv4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showBigImage(bmp4);
                    }
                });
            }
            else {
                frame4.setVisibility(View.GONE);
                imgfile4 = "";
                imgv4.setVisibility(View.GONE);
                c4.setVisibility(View.GONE);
            }


            if(Image5!=null) {
                frame5.setVisibility(View.VISIBLE);
                imgv4.setVisibility(View.VISIBLE);
                imgv5.setImageBitmap(bmp5);
                imgfile5 = getStringImage(bmp5);
                c5.setVisibility(View.GONE);
                c5.setText(cap5);
                imgv5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showBigImage(bmp5);
                    }
                });
            }
            else {
                frame5.setVisibility(View.GONE);
                imgfile5 = "";
                c5.setVisibility(View.GONE);
                imgv5.setVisibility(View.GONE);
            }
            //rs.close();
            loading.dismiss();
        }
    }


    public void showBigImage(Bitmap bmp){
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        int DeviceTotalWidth = metrics.widthPixels;
        int DeviceTotalHeight = metrics.heightPixels;

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.photo_dialog);
        dialog.getWindow().setLayout(DeviceTotalWidth ,DeviceTotalHeight);
        dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dImg = (ImageView) dialog.findViewById(R.id.galleryImg);
        dImg.setImageBitmap(bmp);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sendresource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Bundle extras = getIntent().getExtras();
        int subid = extras.getInt("id");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            exit_app();
        }

        if (id == R.id.sendtoserver) {
            uploadToServer(submittedid,resmaterials,sheltercode,imgfile1,imgfile2,imgfile3,imgfile4,imgfile5,
                    cap1,cap2,cap3,cap4,cap5);
        }
        /*if (id == R.id.syncdata) {
            syncdata();
        }*/

        return super.onOptionsItemSelected(item);
    }


    public void exit_app() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure ? you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
