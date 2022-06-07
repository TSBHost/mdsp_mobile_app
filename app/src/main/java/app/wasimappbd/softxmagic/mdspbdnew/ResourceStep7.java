package app.wasimappbd.softxmagic.mdspbdnew;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class ResourceStep7 extends NavigationActivity implements View.OnClickListener{
    View v;
    Spinner matspin;
   // Button nextbtn,prevbtn;
    LinearLayout img1area,img2area,img3area,img4area,img5area,allimga;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5;
    ImageButton TakeP1,TakeP2,TakeP3,TakeP4,TakeP5,imgG1,imgG2,imgG3,imgG4,imgG5;
    TextView cap1,cap2,cap3,cap4,cap5;
    ImageView imgv1,imgv2,imgv3,imgv4,imgv5;
    public  static final int RequestPermissionCode  = 1 ;
    ToggleButton plusbtn,pbtn1,pbtn2,pbtn3,pbtn4;

    SharedPreferences sharedPref;
    String str;
    private DBHelper mydb;
    HashMap<String, String> submissionValues;
    HashMap<String, String> galleryValues;
    File file;
    OutputStream fOut,fOut1,fOut2,fOut3,fOut4,fOut5;
    FileOutputStream fileoutputstream;
    String filename1,filename2,filename3,filename4,filename5;
    public final static String APP_PATH_SD_CARD = "/MDSP/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "resources";
    ProgressDialog loading;
    Cursor rs;
    String capt1,capt2,capt3,capt4,capt5,imge1,imge2,imge3,imge4,imge5;
    String CaptureTime1, CaptureTime2, CaptureTime3, CaptureTime4, CaptureTime5,Rand1,Rand2,Rand3,Rand4,Rand5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_resource_step7, null,false);
        drawer.addView(v,0);
        allimga = (LinearLayout) findViewById(R.id.allimgarea);
        img1area = (LinearLayout) findViewById(R.id.image1area);
        img2area = (LinearLayout) findViewById(R.id.image2area);
        img3area = (LinearLayout) findViewById(R.id.image3area);
        img4area = (LinearLayout) findViewById(R.id.image4area);
        img5area = (LinearLayout) findViewById(R.id.image5area);

        plusbtn = (ToggleButton) findViewById(R.id.addimg);
        pbtn1 = (ToggleButton) findViewById(R.id.addimg1);
        pbtn2 = (ToggleButton) findViewById(R.id.addimg2);
        pbtn3 = (ToggleButton) findViewById(R.id.addimg3);
        pbtn4 = (ToggleButton) findViewById(R.id.addimg4);

        cap1 = (TextView)  findViewById(R.id.caption1);
        cap2 = (TextView)   findViewById(R.id.caption2);
        cap3 = (TextView)   findViewById(R.id.caption3);
        cap4 = (TextView)   findViewById(R.id.caption4);
        cap5 = (TextView)   findViewById(R.id.caption5);

        TakeP1 = (ImageButton)   findViewById(R.id.takephoto1);
        TakeP2 = (ImageButton)   findViewById(R.id.takephoto2);
        TakeP3 = (ImageButton)   findViewById(R.id.takephoto3);
        TakeP4 = (ImageButton)   findViewById(R.id.takephoto4);
        TakeP5 = (ImageButton)   findViewById(R.id.takephoto5);
        imgG1 = (ImageButton)   findViewById(R.id.img1gallery1);
        imgG2 = (ImageButton)   findViewById(R.id.img1gallery2);
        imgG3 = (ImageButton)   findViewById(R.id.img1gallery3);
        imgG4 = (ImageButton)   findViewById(R.id.img1gallery4);
        imgG5 = (ImageButton)   findViewById(R.id.img1gallery5);

        TakeP1.setOnClickListener(this);
        TakeP2.setOnClickListener(this);
        TakeP3.setOnClickListener(this);
        TakeP4.setOnClickListener(this);
        TakeP5.setOnClickListener(this);
        imgG1.setOnClickListener(this);
        imgG2.setOnClickListener(this);
        imgG3.setOnClickListener(this);
        imgG4.setOnClickListener(this);
        imgG5.setOnClickListener(this);
        plusbtn.setOnClickListener(this);
        pbtn1.setOnClickListener(this);
        pbtn2.setOnClickListener(this);
        pbtn3.setOnClickListener(this);
        pbtn4.setOnClickListener(this);

        imgv1 = (ImageView)   findViewById(R.id.imageView1);
        imgv2 = (ImageView)   findViewById(R.id.imageView2);
        imgv3 = (ImageView)   findViewById(R.id.imageView3);
        imgv4 = (ImageView)   findViewById(R.id.imageView4);
        imgv5 = (ImageView)   findViewById(R.id.imageView5);
        //nextbtn = (Button) findViewById(R.id.nextbtn);

        EnableRuntimePermissionToAccessCamera();
        matspin = (Spinner) findViewById(R.id.matSpinner);
        matspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = matspin.getItemAtPosition(position).toString();
               // Toast.makeText(getApplicationContext(),selectedItem,Toast.LENGTH_LONG).show();

                if(position != 0){
                   // nextbtn.setVisibility(View.VISIBLE);
                    allimga.setVisibility(View.VISIBLE);
                    Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
                    allimga.startAnimation(animSlideDown);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //EnableRuntimePermissionToAccessCamera();
       /* nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImageNameFieldOnServer = imgv1.toString();
                nextbtn.setBackgroundColor(getResources().getColor(R.color.colorAsh));
                nextbtn.setEnabled(false);
                //loading = ProgressDialog.show(getActivity(),"Saving data","Please wait...",false,false);
                loading = new ProgressDialog(ResourceStep7.this);
                loading.setMessage("Loading Please Wait...");
                loading.setIcon(R.drawable.loader);
                loading.show();
                //uploadImage();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                nextbtn.setEnabled(true);
                                uploadImage();

                            }
                        });
                    }
                }, 1000);
            }
        });

        prevbtn = (Button) findViewById(R.id.prevbtn);
        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        if(imgv1.getDrawable()!=null){
            pbtn1.setVisibility(View.VISIBLE);
        }
        else{
            pbtn1.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takephoto1:
                captureImage(11);
                break;
            case R.id.img1gallery1:
                showFileChooser(1);
                break;
            case R.id.takephoto2:
                captureImage(12);
                break;
            case R.id.img1gallery2:
                showFileChooser(2);
                break;
            case R.id.takephoto3:
                captureImage(13);
                break;
            case R.id.img1gallery3:
                showFileChooser(3);
                break;
            case R.id.takephoto4:
                captureImage(14);
                break;
            case R.id.img1gallery4:
                showFileChooser(4);
                break;
            case R.id.takephoto5:
                captureImage(15);
                break;
            case R.id.img1gallery5:
                showFileChooser(5);
                break;
            case R.id.addimg:
                showHideFunc(1);
                break;
            case R.id.addimg1:
                showHideFunc(2);
                break;
            case R.id.addimg2:
                showHideFunc(3);
                break;
            case R.id.addimg3:
                showHideFunc(4);
                break;
            case R.id.addimg4:
                showHideFunc(5);
                break;

        }
    }



    private void showHideFunc(int order) {
        //Toast.makeText(getApplicationContext(),""+order, Toast.LENGTH_LONG).show();
        if(order==1) {
            if(img1area.getVisibility() == View.GONE){
                img1area.setVisibility(View.VISIBLE);
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                img1area.startAnimation(animSlideDown);
            }
            else{
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                img1area.startAnimation(animSlideDown);
                img1area.setVisibility(View.GONE);
            }
        }
        else if(order==2) {
            if(img2area.getVisibility() == View.GONE){
                img2area.setVisibility(View.VISIBLE);
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                img2area.startAnimation(animSlideDown);
            }
            else{
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                img2area.startAnimation(animSlideDown);
                img2area.setVisibility(View.GONE);
            }
        }
        else if(order==3) {
            if(img3area.getVisibility() == View.GONE){
                img3area.setVisibility(View.VISIBLE);
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                img3area.startAnimation(animSlideDown);
            }
            else{
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                img3area.startAnimation(animSlideDown);
                img3area.setVisibility(View.GONE);
            }
        }
        else if(order==4) {
            if(img4area.getVisibility() == View.GONE){
                img4area.setVisibility(View.VISIBLE);
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                img4area.startAnimation(animSlideDown);
            }
            else{
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                img4area.startAnimation(animSlideDown);
                img4area.setVisibility(View.GONE);
            }
        }
        else if(order==5) {
            if(img5area.getVisibility() == View.GONE){
                img5area.setVisibility(View.VISIBLE);
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                img5area.startAnimation(animSlideDown);
            }
            else{
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                img5area.startAnimation(animSlideDown);
                img5area.setVisibility(View.GONE);
            }
        }
        /*else if(order==3) {
            img3area.setVisibility(View.VISIBLE);
            Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            img3area.startAnimation(animSlideDown);
            img2area.setVisibility(View.GONE);
            img1area.setVisibility(View.GONE);
            img4area.setVisibility(View.GONE);
            img5area.setVisibility(View.GONE);
        }
        else if(order==4) {
            img4area.setVisibility(View.VISIBLE);
            Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            img4area.startAnimation(animSlideDown);
            img2area.setVisibility(View.GONE);
            img3area.setVisibility(View.GONE);
            img1area.setVisibility(View.GONE);
            img5area.setVisibility(View.GONE);
        }
        else if(order==5) {
            img5area.setVisibility(View.VISIBLE);
            Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            img5area.startAnimation(animSlideDown);
            img2area.setVisibility(View.GONE);
            img3area.setVisibility(View.GONE);
            img4area.setVisibility(View.GONE);
            img1area.setVisibility(View.GONE);
        }*/
    }

    private void showFileChooser(int REQ_CODE) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_CODE);
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }
    private void captureImage(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = null;

        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", f);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }


        startActivityForResult(takePictureIntent, actionCode);
    }

    private void setCemeraPic1() {
       /*int targetW = imgv1.getWidth();
       int targetH = imgv1.getHeight();*/
        int targetW = imgv1.getWidth();
        int targetH = imgv1.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 5;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap1 = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgv1.setImageBitmap(bitmap1);
        imgv1.setVisibility(View.VISIBLE);
    }
    private void setCemeraPic2() {
        int targetW = imgv2.getWidth();
        int targetH = imgv2.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 7;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap2 = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgv2.setImageBitmap(bitmap2);
        imgv2.setVisibility(View.VISIBLE);
    }
    private void setCemeraPic3() {
        int targetW = imgv3.getWidth();
        int targetH = imgv3.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 7;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap3 = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgv3.setImageBitmap(bitmap3);
        imgv3.setVisibility(View.VISIBLE);
    }
    private void setCemeraPic4() {
        int targetW = imgv4.getWidth();
        int targetH = imgv4.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 7;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap4 = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgv4.setImageBitmap(bitmap4);
        imgv4.setVisibility(View.VISIBLE);
    }
    private void setCemeraPic5() {
        int targetW = imgv5.getWidth();
        int targetH = imgv5.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 7;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap5 = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgv5.setImageBitmap(bitmap5);
        imgv5.setVisibility(View.VISIBLE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Random random = new Random();
        Date c = Calendar.getInstance().getTime();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            CaptureTime1 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand1 = String.format("%04d", random.nextInt(10000));

            Glide.with(imgv1.getContext()).load(data.getData().toString()).asBitmap().into(imgv1);
            pbtn1.setVisibility(View.VISIBLE);
        }


        if (requestCode == 11 && resultCode == RESULT_OK) {
            CaptureTime1 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand1 = String.format("%04d", random.nextInt(10000));

            setCemeraPic1();
            pbtn1.setVisibility(View.VISIBLE);
        }


        if (requestCode == 2  && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            CaptureTime2 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand2 = String.format("%04d", random.nextInt(10000));

            Glide.with(imgv2.getContext()).load(data.getData().toString()).asBitmap().into(imgv2);
            pbtn2.setVisibility(View.VISIBLE);
        }

        if (requestCode == 12 && resultCode == RESULT_OK) {
            CaptureTime2 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand2 = String.format("%04d", random.nextInt(10000));

            setCemeraPic2();
            pbtn2.setVisibility(View.VISIBLE);
        }


        if (requestCode == 3   && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            CaptureTime3 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand3 = String.format("%04d", random.nextInt(10000));

            Glide.with(imgv3.getContext()).load(data.getData().toString()).asBitmap().into(imgv3);
            pbtn3.setVisibility(View.VISIBLE);
        }



        if (requestCode == 13 && resultCode == RESULT_OK) {
            CaptureTime3 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand3 = String.format("%04d", random.nextInt(10000));

            setCemeraPic3();
            pbtn3.setVisibility(View.VISIBLE);
        }


        if (requestCode == 4  && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            CaptureTime4 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand4 = String.format("%04d", random.nextInt(10000));

            Glide.with(imgv4.getContext()).load(data.getData().toString()).asBitmap().into(imgv4);
            pbtn4.setVisibility(View.VISIBLE);
        }



        if (requestCode == 14 && resultCode == RESULT_OK) {
            CaptureTime4 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand4 = String.format("%04d", random.nextInt(10000));

            setCemeraPic4();
            pbtn4.setVisibility(View.VISIBLE);
        }


        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            CaptureTime5 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand5 = String.format("%04d", random.nextInt(10000));

            Glide.with(imgv5.getContext()).load(data.getData().toString()).asBitmap().into(imgv5);
        }



        if (requestCode == 15 && resultCode == RESULT_OK) {
            CaptureTime5 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(c);
            Rand5 = String.format("%04d", random.nextInt(10000));

            setCemeraPic5();
        }

    }


    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(getApplicationContext(),"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @SuppressLint("LongLogTag")
    public String imeiid() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = "", tmSerial, androidId = "";
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            tmDevice = "" + tm.getDeviceId();
            Log.v("DeviceIMEI", "" + tmDevice);
            tmSerial = "" + tm.getSimSerialNumber();
            Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
            Log.v("androidId CDMA devices", "" + androidId);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();
            Log.v("deviceIdUUID universally unique identifier", "" + deviceId);
            String deviceModelName = android.os.Build.MODEL;
            Log.v("Model Name", "" + deviceModelName);
            String deviceUSER = android.os.Build.USER;
            Log.v("Name USER", "" + deviceUSER);
            String devicePRODUCT = android.os.Build.PRODUCT;
            Log.v("PRODUCT", "" + devicePRODUCT);
            String deviceHARDWARE = android.os.Build.HARDWARE;
            Log.v("HARDWARE", "" + deviceHARDWARE);
            String deviceBRAND = android.os.Build.BRAND;
            Log.v("BRAND", "" + deviceBRAND);
            String myVersion = android.os.Build.VERSION.RELEASE;
            Log.v("VERSION.RELEASE", "" + myVersion);
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Log.v("VERSION.SDK_INT", "" + sdkVersion);
            //Toast.makeText(getApplicationContext(),deviceId,Toast.LENGTH_LONG).show();
        }
        String uniquekey = tmDevice;
        // Toast.makeText(getApplicationContext(),uniquekey,Toast.LENGTH_LONG).show();
        return uniquekey;
    }

    private void uploadImage(){
        Intent intv = getIntent();
        final String sheltid = intv.getStringExtra("uniqueid");

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        imgv1.buildDrawingCache();
        imgv2.buildDrawingCache();
        imgv3.buildDrawingCache();
        imgv4.buildDrawingCache();
        imgv5.buildDrawingCache();

        bitmap1 = imgv1.getDrawingCache();
        bitmap2 = imgv2.getDrawingCache();
        bitmap3 = imgv3.getDrawingCache();
        bitmap4 = imgv4.getDrawingCache();
        bitmap5 = imgv5.getDrawingCache();
        //String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
        //String fullPath = getFilesDir().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //filename1 = sheltid+"_"+ts+"_image1.webp";
            filename1 = sheltid+"_"+CaptureTime1+imeiid()+Rand1+"_resources1.webp";
            File file1 = new File(fullPath, filename1);
            file1.createNewFile();
            fOut1 = new FileOutputStream(file1);
            bitmap1.compress(Bitmap.CompressFormat.WEBP, 100, fOut1);
            fOut1.flush();
            fOut1.close();
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file1.getAbsolutePath(), file1.getName(), file1.getName());

            filename2 = sheltid+"_"+CaptureTime2+imeiid()+Rand2+"_resources2.webp";
            File file2 = new File(fullPath, filename2);
            file2.createNewFile();
            fOut2 = new FileOutputStream(file2);
            bitmap2.compress(Bitmap.CompressFormat.WEBP, 100, fOut2);
            fOut2.flush();
            fOut2.close();
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file2.getAbsolutePath(), file2.getName(), file2.getName());

            filename3 = sheltid+"_"+CaptureTime3+imeiid()+Rand3+"_resources3.webp";
            File file3 = new File(fullPath, filename3);
            file3.createNewFile();
            fOut3 = new FileOutputStream(file3);
            bitmap3.compress(Bitmap.CompressFormat.WEBP, 100, fOut3);
            fOut3.flush();
            fOut3.close();
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file3.getAbsolutePath(), file3.getName(), file3.getName());

            filename4 = sheltid+"_"+CaptureTime4+imeiid()+Rand4+"_resources4.webp";
            File file4 = new File(fullPath, filename4);
            file4.createNewFile();
            fOut4 = new FileOutputStream(file4);
            bitmap4.compress(Bitmap.CompressFormat.WEBP, 100, fOut4);
            fOut4.flush();
            fOut4.close();
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file4.getAbsolutePath(), file4.getName(), file4.getName());

            filename5 = sheltid+"_"+CaptureTime5+imeiid()+Rand5+"_resources5.webp";
            File file5 = new File(fullPath, filename5);
            file5.createNewFile();
            fOut5 = new FileOutputStream(file5);
            bitmap5.compress(Bitmap.CompressFormat.WEBP, 100, fOut5);
            fOut5.flush();
            fOut5.close();
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file5.getAbsolutePath(), file5.getName(), file5.getName());

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
        }

        sharedPref = getSharedPreferences("LOGININFO", MODE_PRIVATE);
        int userid = sharedPref.getInt("userid", 0);
        final String matd = matspin.getSelectedItem().toString();


        String caption1 = cap1.getText().toString();
        String caption2 = cap2.getText().toString();
        String caption3 = cap3.getText().toString();
        String caption4 = cap4.getText().toString();
        String caption5 = cap5.getText().toString();


        mydb = new DBHelper(this);
            submissionValues = new HashMap<String, String>();
            submissionValues.put("userid", Integer.toString(userid));
            submissionValues.put("materials", matd);
            submissionValues.put("shelter", sheltid);

            if (mydb.insertResourceForm(submissionValues)) {
                int lastsubid = mydb.getLastResourceId();
                mydb.insertResourceGallery(userid, lastsubid, caption1, caption2, caption3, caption4, caption5,
                        filename1, filename2, filename3, filename4, filename5);
                Toast.makeText(getApplicationContext(), "Successfully Saved Data", Toast.LENGTH_LONG).show();
                loading.dismiss();
                Intent inthome = new Intent(getApplicationContext(), GridViewActivity.class);
                startActivity(inthome);
            } else {
                Toast.makeText(getApplicationContext(), "Failed to update data !", Toast.LENGTH_LONG).show();
                loading.dismiss();
                Intent inthome = new Intent(getApplicationContext(), GridViewActivity.class);
                startActivity(inthome);
            }



        int totaldata = mydb.numberOfRowsSUbmittedForm();
        int totalgallery = mydb.numberOfRowsGallery();
        Log.v("TotalSaveResource", Integer.toString(totaldata)+". \n TotalResourceGallery"+Integer.toString(totalgallery));
        mydb.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saveresources, menu);
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

        if (id == R.id.prevbtn) {
            onBackPressed();
        }

        if (id == R.id.savetostorage) {
            //nextbtn.setBackgroundColor(getResources().getColor(R.color.colorAsh));
           // nextbtn.setEnabled(false);
            //loading = ProgressDialog.show(getActivity(),"Saving data","Please wait...",false,false);
            loading = new ProgressDialog(ResourceStep7.this);
            loading.setMessage("Loading Please Wait...");
            loading.setIcon(R.drawable.loader);
            loading.show();
            //uploadImage();
            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //nextbtn.setEnabled(true);
                            uploadImage();

                        }
                    });
                }
            }, 1000);
        }
        /*if (id == R.id.syncdata) {
            syncdata();
        }*/

        return super.onOptionsItemSelected(item);
    }

}