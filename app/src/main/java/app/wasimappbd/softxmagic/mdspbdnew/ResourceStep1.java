package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourceStep1 extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    SharedPreferences sharedPref;
    RadioGroup rd1;
    String rdb1;
    private static final String REQUIRED_MSG = "Sorry a Valid Shelter Code is Required";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLogin() && read_memory() != 0) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_resource_step1, null,false);
            drawer.addView(v,0);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Enter Shelter Code");
            rd1 = (RadioGroup)   findViewById(R.id.matpgroup);

            Intent intv = getIntent();
            final String statusval = intv.getStringExtra("action");
            final int sid = intv.getIntExtra("subid",0);

            nextbtn = (Button) findViewById(R.id.nextbtn);
            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validationSuccess(statusval)) {
                        Intent intentnext;
                        if(rdb1.equals("Yes"))
                        {
                            intentnext = new Intent(getApplicationContext(), ResourceStep4.class);
                        }
                        else{
                            intentnext = new Intent(getApplicationContext(), ResourceStep3.class);
                        }
                        startActivity(intentnext);
                    } else {
                        AlertDialog();
                    }

                }
            });

            prevbtn = (Button) findViewById(R.id.prevbtn);
            prevbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inteprev = new Intent(getApplicationContext(), ResourceActivity.class);
                    startActivity(inteprev);
                }
            });

            rd1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    RadioButton orgrBtn = (RadioButton)   findViewById(i);
                    rdb1 = orgrBtn.getText().toString();
                }
            });
        }
        else {
            Intent i = new Intent(this.getBaseContext().getApplicationContext(), LoginActivity.class);
            this.startActivity(i);
        }

    }


       private void showToastMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }



    private void AlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please Select One Organization and Designation").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
    private Boolean validationSuccess(String stsval){
            if(rd1.getCheckedRadioButtonId()<=0){
                Toast.makeText(getApplicationContext(),"Please select Organization",Toast.LENGTH_SHORT).show();
                return false;
            }
       return true;
    }

    public Boolean isLogin() {
        sharedPref = this.getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
        Boolean s = sharedPref.getBoolean("loggedin", true);
        return s;
    }

    public int read_memory() {
        sharedPref = this.getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
        int s = sharedPref.getInt("userid", 0);
        return s;
    }
}
