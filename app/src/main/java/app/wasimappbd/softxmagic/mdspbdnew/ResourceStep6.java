package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ResourceStep6 extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    EditText edt;
    SharedPreferences sharedPref;
    private static final int REQUEST_CODE = 1234;
    ImageButton bt_voiceinput;
    private static final String REQUIRED_MSG = "Sorry Username is Required";
    public DBHelper mydb;
    String uname;
    Cursor rs;
    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_resource_step6, null,false);
        drawer.addView(v,0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Username");

        Intent intv = getIntent();
        final String statusval = intv.getStringExtra("action");
        final int sid = intv.getIntExtra("subid",0);
        final String shelterCode = intv.getStringExtra("uniqueid");
        final String matintendname = intv.getStringExtra("matintname");
        final String sourcebrand = intv.getStringExtra("sourcebrand");
        final String stackyard = intv.getStringExtra("stackyard");
        final String qtyintend = intv.getStringExtra("qtyintend");


        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasText(edt)){
                    Intent intentnext = new Intent(getApplicationContext(), ResourceStep7.class);
                    intentnext.putExtra("subid", sid);
                    intentnext.putExtra("action", statusval);
                    intentnext.putExtra("uniqueid", shelterCode);
                    intentnext.putExtra("matintendname", matintendname);
                    intentnext.putExtra("sourcebrand", sourcebrand);
                    intentnext.putExtra("stackyard", stackyard);
                    intentnext.putExtra("qtyintend", qtyintend);
                    intentnext.putExtra("expmobil", edt.getText().toString());
                    startActivity(intentnext);
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

        edt = (EditText) findViewById(R.id.dscname);

        if(statusval.equals("edit")) {
            mydb = new DBHelper(this);
            rs = mydb.getDataSUbmittedForm(sid);
            rs.moveToFirst();

            uname = rs.getString(rs.getColumnIndex("UserName"));
            edt.setText(uname);
        }


        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                /*if(b == false){
                    getInputData(edt.getText().toString());
                }*/
            }
        });

        bt_voiceinput = (ImageButton) findViewById(R.id.ib_speak);
        bt_voiceinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startVoiceRecognitionActivity();
                datePicker();
            }
        });
    }


    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        //*************Call Time Picker Here ********************
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        edt.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void showToastMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }
}
