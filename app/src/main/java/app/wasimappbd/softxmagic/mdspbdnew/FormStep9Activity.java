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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FormStep9Activity extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    RadioGroup rdg,rg;
    SharedPreferences sharedPref;
    ListView lstv;
    LinearLayout linearLayout;
    String matt1,matt2,matt3,matt4,matt5;
    TextView field1,field2,field3,field4,field5;
    RadioGroup rd1,rd2,rd3,rd4,rd5;
    String radVal,rdb1,rdb2,rdb3,rdb4,rdb5,mantf1,mantf2,mantf3,mantf4,mantf5,matdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_form_step9, null,false);
        drawer.addView(v,0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Resources: Materials");

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
        final String machS = intv.getStringExtra("machinery");
        final String machd = intv.getStringExtra("machdetails");

        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new   View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matdetails = mantf1+","+mantf2+","+mantf3+","+mantf4+","+mantf5;
                if (validationSuccess()) {
                    Intent intentnext = new Intent(getApplicationContext(), FormStep10Activity.class);
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
                    intentnext.putExtra("machinery", machS);
                    intentnext.putExtra("machdetails", machd);
                    intentnext.putExtra("materials", radVal);
                    intentnext.putExtra("matdetails", matdetails);
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
        field1 = (TextView)   findViewById(R.id.matpname);
        field2 = (TextView)   findViewById(R.id.matpname1);
        field3 = (TextView)   findViewById(R.id.matpname2);
        field4 = (TextView)   findViewById(R.id.matpname3);
        field5 = (TextView)   findViewById(R.id.matpname4);


        rd1 = (RadioGroup)   findViewById(R.id.matpgroup);
        rd2 = (RadioGroup)   findViewById(R.id.matpgroup1);
        rd3 = (RadioGroup)   findViewById(R.id.matpgroup2);
        rd4 = (RadioGroup)   findViewById(R.id.matpgroup3);
        rd5 = (RadioGroup)   findViewById(R.id.matpgroup4);


        matt1 = field1.getText().toString();
        matt2 = field2.getText().toString();
        matt3 = field3.getText().toString();
        matt4 = field4.getText().toString();
        matt5 = field5.getText().toString();

        rdg = (RadioGroup)   findViewById(R.id.material);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                /// Get selected radio value inner radio group
                RadioButton orgrBtn = (RadioButton)   findViewById(i);
                radVal = orgrBtn.getText().toString();

                /// Get radio nalue into session
                /*SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("material", radVal);
                editor.commit();*/
                showRadioButtonDialog(radVal);

                rd1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb1 = orgrBtn.getText().toString();
                        mantf1 = matt1+":"+rdb1;
                    }
                });

                //////////// Second Radio button action ///////////////
                rd2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb2 = orgrBtn.getText().toString();
                        mantf2 = matt2+":"+rdb2;
                    }
                });

                //////////// Third Radio button action ///////////////
                rd3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb3 = orgrBtn.getText().toString();
                        mantf3 = matt3+":"+rdb3;
                    }
                });

                //////////// Fourth Radio button action ///////////////
                rd4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb4 = orgrBtn.getText().toString();
                        mantf4 = matt4+":"+rdb4;
                    }
                });

                //////////// Fifth Radio button action ///////////////
                rd5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton orgrBtn = (RadioButton)   findViewById(i);
                        rdb5 = orgrBtn.getText().toString();
                        mantf5 = matt5+":"+rdb5;
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
        alertDialogBuilder.setMessage("Please Select Material Status").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
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
            //Toast.makeText(getApplicationContext(),"Please select Material Status",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
