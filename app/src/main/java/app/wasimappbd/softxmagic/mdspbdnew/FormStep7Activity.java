package app.wasimappbd.softxmagic.mdspbdnew;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class FormStep7Activity extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    SharedPreferences sharedPref;
    LinearLayout linearLayout;
    String mant1,mant2,mant3,mant4,mant5,mant6,mant7,mant8,mant9;
    TextView field1,field2,field3,field4,field5,field6,field7,field8,field9;
    RadioGroup rd1,rd2,rd3,rd4,rd5,rd6,rd7,rd8,rd9;
    String allselv;
    Map<String, String> map = new HashMap<String, String>();
    RadioGroup rdg,rg;
    String radVal,rdb1,rdb2,rdb3,rdb4,rdb5,rdb6,rdb7,rdb8,rdb9,mantf1,mantf2,mantf3,mantf4,mantf5,mantf6,mantf7,mantf8,mantf9,manpdetails;
    public DBHelper mydb;
    Cursor rs;
    String yesnoval = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_form_step7, null,false);
        drawer.addView(v,0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Resources: Manpower");


        Intent intv = getIntent();
        final String statusval = intv.getStringExtra("action");
        final int sid = intv.getIntExtra("subid",0);
        final String shelterCode = intv.getStringExtra("uniqueid");
        final String orgText = intv.getStringExtra("organization");
        final String desgText = intv.getStringExtra("designation");
        final String shUser = intv.getStringExtra("dscuname");
        final String mTask = intv.getStringExtra("majortask");
        final String sTask = intv.getStringExtra("subtask");
        final String uComm = intv.getStringExtra("ucomment");

        /*Toast.makeText(getApplicationContext(), shelterCode+""+orgText+""+desgText+""+mTask+""
                +sTask+""+shUser+""+uComm, Toast.LENGTH_SHORT).show();*/

        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manpdetails = mantf1+","+mantf2+","+mantf3+","+mantf4+","+mantf5+","+mantf6+","+mantf7+","+mantf8+","+mantf9;
                if (validationSuccess()) {
                    Intent intentnext = new Intent(getApplicationContext(), FormStep8Activity.class);
                    intentnext.putExtra("subid", sid);
                    intentnext.putExtra("action", statusval);
                    intentnext.putExtra("uniqueid", shelterCode);
                    intentnext.putExtra("organization", orgText);
                    intentnext.putExtra("designation", desgText);
                    intentnext.putExtra("dscuname", shUser);
                    intentnext.putExtra("majortask", mTask);
                    intentnext.putExtra("subtask", sTask);
                    intentnext.putExtra("ucomment", uComm);
                    intentnext.putExtra("manpower", radVal);
                    intentnext.putExtra("manpdetails", manpdetails);
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
                onBackPressed();
            }
        });


        sharedPref = getSharedPreferences("REGISTRATION", Context.MODE_PRIVATE);
        linearLayout = (LinearLayout)   findViewById(R.id.listlayout);
        // lstv = (ListView)   findViewById(R.id.listview);
        field1 = (TextView)   findViewById(R.id.manpname);
        field2 = (TextView)   findViewById(R.id.manpname1);
        field3 = (TextView)   findViewById(R.id.manpname2);
        field4 = (TextView)   findViewById(R.id.manpname3);
        field5 = (TextView)   findViewById(R.id.manpname4);
        field6 = (TextView)   findViewById(R.id.manpname5);
        field7 = (TextView)   findViewById(R.id.manpname6);
        field8 = (TextView)   findViewById(R.id.manpname7);
        field9 = (TextView)   findViewById(R.id.manpname8);

        rd1 = (RadioGroup)   findViewById(R.id.manpgroup);
        rd2 = (RadioGroup)   findViewById(R.id.manpgroup1);
        rd3 = (RadioGroup)   findViewById(R.id.manpgroup2);
        rd4 = (RadioGroup)   findViewById(R.id.manpgroup3);
        rd5 = (RadioGroup)   findViewById(R.id.manpgroup4);
        rd6 = (RadioGroup)   findViewById(R.id.manpgroup5);
        rd7 = (RadioGroup)   findViewById(R.id.manpgroup6);
        rd8 = (RadioGroup)   findViewById(R.id.manpgroup7);
        rd9 = (RadioGroup)   findViewById(R.id.manpgroup8);

        mant1 = field1.getText().toString();
        mant2 = field2.getText().toString();
        mant3 = field3.getText().toString();
        mant4 = field4.getText().toString();
        mant5 = field5.getText().toString();
        mant6 = field6.getText().toString();
        mant7 = field7.getText().toString();
        mant8 = field8.getText().toString();
        mant9 = field9.getText().toString();


        if(statusval.equals("edit")) {
            mydb = new DBHelper(this);
            rs = mydb.getDataSUbmittedForm(sid);
            rs.moveToFirst();
            yesnoval = rs.getString(rs.getColumnIndex("ManPower"));
            showRadioButtonDialog(yesnoval);
            RadioButton orgrBtn1 = (RadioButton)  findViewById(R.id.org1);
            RadioButton orgrBtn2 = (RadioButton)  findViewById(R.id.org2);
            String checkvalue1 = orgrBtn1.getText().toString();
            String checkvalue2 = orgrBtn2.getText().toString();

            if(checkvalue1.equals(yesnoval)) orgrBtn1.setChecked(true);
            else if(checkvalue2.equals(yesnoval)) orgrBtn2.setChecked(true);
        }

        rdg = (RadioGroup)   findViewById(R.id.manpower);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                /// Get selected radio value inner radio group
                RadioButton orgrBtn = (RadioButton)   findViewById(i);
                radVal = orgrBtn.getText().toString();
                showRadioButtonDialog(radVal);

                //////////// First Radio button action ///////////////
                rd1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb1 = orgrBtn.getText().toString();
                        mantf1 = mant1+":"+rdb1;
                    }
                });

                //////////// Second Radio button action ///////////////
                rd2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb2 = orgrBtn.getText().toString();
                        mantf2 = mant2+":"+rdb2;
                    }
                });

                //////////// Third Radio button action ///////////////
                rd3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb3 = orgrBtn.getText().toString();
                        mantf3 = mant3+":"+rdb3;
                    }
                });

                //////////// Fourth Radio button action ///////////////
                rd4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb4 = orgrBtn.getText().toString();
                        mantf4 = mant4+":"+rdb4;
                    }
                });

                //////////// Fifth Radio button action ///////////////
                rd5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb5 = orgrBtn.getText().toString();
                        mantf5 = mant5+":"+rdb5;
                    }
                });

                //////////// Sixth Radio button action ///////////////
                rd6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb6 = orgrBtn.getText().toString();
                        mantf6 = mant6+":"+rdb6;
                    }
                });
                //////////// Seventh Radio button action ///////////////
                rd7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb7 = orgrBtn.getText().toString();
                        mantf7 = mant7+":"+rdb7;
                    }
                });
                //////////// Eighth Radio button action ///////////////
                rd8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb8 = orgrBtn.getText().toString();
                        mantf8 = mant8+":"+rdb8;
                    }
                });
                //////////// Ningth Radio button action ///////////////
                rd9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb9 = orgrBtn.getText().toString();
                        mantf9 = mant9+":"+rdb9;
                    }
                });


            }
        });

    }

    private void showRadioButtonDialog(final String selText) {
        if(selText.equals("Yes")) {
            linearLayout.setVisibility(View.VISIBLE);
        }
        else{
            linearLayout.setVisibility(View.GONE);
        }
    }


    private void AlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please select Manpower Status").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
    @SuppressLint("ResourceType")
    private Boolean validationSuccess(){
        if(rdg.getCheckedRadioButtonId()<=0){
            //Toast.makeText(getApplicationContext(),"Please select Manpower Status",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
