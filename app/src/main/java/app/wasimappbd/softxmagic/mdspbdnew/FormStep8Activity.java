package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FormStep8Activity extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    SharedPreferences sharedPref;
    ListView lstv;
    LinearLayout linearLayout;
    String macht1,macht2,macht3,macht4,macht5,macht6,macht7,macht8,macht9,macht10,macht11,macht12,macht13;
    TextView field1,field2,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13;
    RadioGroup rd1,rd2,rd3,rd4,rd5,rd6,rd7,rd8,rd9,rd10,rd11,rd12,rd13;
    RadioGroup rdg;
    String radVal,rdb1,rdb2,rdb3,rdb4,rdb5,rdb6,rdb7,rdb8,rdb9,rdb10,rdb11,rdb12,rdb13,
            mantf1,mantf2,mantf3,mantf4,mantf5,mantf6,mantf7,mantf8,mantf9,mantf10,mantf11,mantf12,mantf13,machdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_form_step8, null,false);
        drawer.addView(v,0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Resources: Machinery");

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
        final String manS = intv.getStringExtra("manpower");
        final String mand = intv.getStringExtra("manpdetails");

       /* Toast.makeText(getApplicationContext(), shelterCode+"\n"+orgText+" \n"+desgText+" \n"+mTask+" \n"
                +sTask+" \n"+shUser+" \n"+uComm+" \n"+manS+" \n"+mand, Toast.LENGTH_SHORT).show();

        Log.v("intentvalue",shelterCode+"\n"+orgText+" \n"+desgText+" \n"+mTask+" \n"
                +sTask+" \n"+shUser+" \n"+uComm+" \n"+manS+" \n"+mand);*/

        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                machdetails = mantf1+","+mantf2+","+mantf3+","+mantf4+","+mantf5+","+mantf6+","+
                        mantf7+","+mantf8+","+mantf9+","+mantf10+","+mantf11+","+mantf12+","+mantf13;
                if (validationSuccess()) {
                    Intent intentnext = new Intent(getApplicationContext(), FormStep9Activity.class);
                    intentnext.putExtra("subid", sid);
                    intentnext.putExtra("action", statusval);
                    intentnext.putExtra("uniqueid", shelterCode);
                    intentnext.putExtra("organization", orgText);
                    intentnext.putExtra("designation", desgText);
                    intentnext.putExtra("dscuname", shUser);
                    intentnext.putExtra("majortask", mTask);
                    intentnext.putExtra("subtask", sTask);
                    intentnext.putExtra("ucomment", uComm);
                    intentnext.putExtra("manpower", manS);
                    intentnext.putExtra("manpdetails", mand);
                    intentnext.putExtra("machinery", radVal);
                    intentnext.putExtra("machdetails", machdetails);
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
        field1 = (TextView)   findViewById(R.id.machpname);
        field2 = (TextView)   findViewById(R.id.machpname1);
        field3 = (TextView)   findViewById(R.id.machpname2);
        field4 = (TextView)   findViewById(R.id.machpname3);
        field5 = (TextView)   findViewById(R.id.machpname4);
        field6 = (TextView)   findViewById(R.id.machpname5);
        field7 = (TextView)   findViewById(R.id.machpname6);
        field8 = (TextView)   findViewById(R.id.machpname7);
        field9 = (TextView)   findViewById(R.id.machpname8);
        field10 = (TextView)   findViewById(R.id.machpname9);
        field11 = (TextView)   findViewById(R.id.machpname10);
        field12 = (TextView)   findViewById(R.id.machpname11);
        field13 = (TextView)   findViewById(R.id.machpname12);

        rd1 = (RadioGroup)   findViewById(R.id.machpgroup);
        rd2 = (RadioGroup)   findViewById(R.id.machpgroup1);
        rd3 = (RadioGroup)   findViewById(R.id.machpgroup2);
        rd4 = (RadioGroup)   findViewById(R.id.machpgroup3);
        rd5 = (RadioGroup)   findViewById(R.id.machpgroup4);
        rd6 = (RadioGroup)   findViewById(R.id.machpgroup5);
        rd7 = (RadioGroup)   findViewById(R.id.machpgroup6);
        rd8 = (RadioGroup)   findViewById(R.id.machpgroup7);
        rd9 = (RadioGroup)   findViewById(R.id.machpgroup8);
        rd10 = (RadioGroup)   findViewById(R.id.machpgroup9);
        rd11 = (RadioGroup)   findViewById(R.id.machpgroup10);
        rd12 = (RadioGroup)   findViewById(R.id.machpgroup11);
        rd13 = (RadioGroup)   findViewById(R.id.machpgroup12);

        macht1 = field1.getText().toString();
        macht2 = field2.getText().toString();
        macht3 = field3.getText().toString();
        macht4 = field4.getText().toString();
        macht5 = field5.getText().toString();
        macht6 = field6.getText().toString();
        macht7 = field7.getText().toString();
        macht8 = field8.getText().toString();
        macht9 = field9.getText().toString();
        macht10 = field10.getText().toString();
        macht11 = field11.getText().toString();
        macht12 = field12.getText().toString();
        macht13 = field13.getText().toString();

        rdg = (RadioGroup)   findViewById(R.id.machinary);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                /// Get selected radio value inner radio group
                RadioButton orgrBtn = (RadioButton)   findViewById(i);
                radVal = orgrBtn.getText().toString();

                /// Get radio nalue into session
               /* SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("machinery", radVal);
                editor.commit();*/
                showRadioButtonDialog(radVal); //////////// First Radio button action ///////////////

                rd1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb1 = orgrBtn.getText().toString();
                        mantf1 = macht1+":"+rdb1;
                    }
                });

                //////////// Second Radio button action ///////////////
                rd2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb2 = orgrBtn.getText().toString();
                        mantf2 = macht2+":"+rdb2;
                    }
                });

                //////////// Third Radio button action ///////////////
                rd3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb3 = orgrBtn.getText().toString();
                        mantf3 = macht3+":"+rdb3;
                    }
                });

                //////////// Fourth Radio button action ///////////////
                rd4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb4 = orgrBtn.getText().toString();
                        mantf4 = macht4+":"+rdb4;
                    }
                });

                //////////// Fifth Radio button action ///////////////
                rd5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb5 = orgrBtn.getText().toString();
                        mantf5 = macht5+":"+rdb5;
                    }
                });

                //////////// Sixth Radio button action ///////////////
                rd6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb6 = orgrBtn.getText().toString();
                        mantf6 = macht6+":"+rdb6;
                    }
                });
                //////////// Seventh Radio button action ///////////////
                rd7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb7 = orgrBtn.getText().toString();
                        mantf7 = macht7+":"+rdb7;
                    }
                });
                //////////// Eighth Radio button action ///////////////
                rd8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb8 = orgrBtn.getText().toString();
                        mantf8 = macht8+":"+rdb8;
                    }
                });
                //////////// Ningth Radio button action ///////////////
                rd9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb9 = orgrBtn.getText().toString();
                        mantf9 = macht9+":"+rdb9;
                    }
                });
                //////////// Ningth Radio button action ///////////////
                rd10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb10 = orgrBtn.getText().toString();
                        mantf10 = macht10+":"+rdb10;
                    }
                });
                //////////// Ningth Radio button action ///////////////
                rd11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb11 = orgrBtn.getText().toString();
                        mantf11 = macht11+":"+rdb11;
                    }
                });
                //////////// Ningth Radio button action ///////////////
                rd12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb12 = orgrBtn.getText().toString();
                        mantf12 = macht12+":"+rdb12;
                    }
                });
                //////////// Ningth Radio button action ///////////////
                rd13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb13 = orgrBtn.getText().toString();
                        mantf13 = macht13+":"+rdb13;
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
        alertDialogBuilder.setMessage("Please select Machinery Status").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
    private Boolean validationSuccess(){
        if(rdg.getCheckedRadioButtonId()<=0){
           // Toast.makeText(getApplicationContext(),"Please select Machinery Status",Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(rg.getCheckedRadioButtonId() < 0){
            Toast.makeText(getApplicationContext(),"Please select Sub Task",Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }
}
