package app.wasimappbd.softxmagic.mdspbdnew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormStep2Activity extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    SharedPreferences sharedPref;
    private DBHelper mydb;
    private static final int REQUEST_CODE = 1234;
    //ImageButton bt_voiceinput;
    AutoCompleteTextView actv;
    Cursor rs;
    private static final String REQUIRED_MSG = "Sorry a Valid Shelter Code is Required";
    private static final String REQUIRED_VSLID_MSG = "Please Enter a Valid Shelter Code";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);
        if (isLogin() && read_memory() != 0) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_form_step2, null,false);
            drawer.addView(v,0);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Enter Shelter Code");
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            Intent intv = getIntent();
            final String statusval = intv.getStringExtra("action");
            final int sid = intv.getIntExtra("subid",0);

            nextbtn = (Button) findViewById(R.id.nextbtn);
            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hasText(actv)){

                        try {

                            mydb = new DBHelper(FormStep2Activity.this);
                            Cursor rs = mydb.checkExisting(actv.getText().toString());
                            rs.moveToFirst();
                            rs.close();
                            if (rs.getCount() > 0) {
                                Intent intentnext = new Intent(getApplicationContext(), FormStep5Activity.class);
                                intentnext.putExtra("subid", sid);
                                intentnext.putExtra("action", statusval);
                                intentnext.putExtra("uniqueid", actv.getText().toString());
                                startActivity(intentnext);
                            } else {
                                //Toast.makeText(getApplicationContext(),"Please Enter a Valid Shelter Code", Toast.LENGTH_LONG).show();
                                actv.setError(REQUIRED_VSLID_MSG);
                            }
                            mydb.close();
                        } catch (SQLException s) {
                            new Exception("Error with DB Open");
                        }




                    }

                }
            });

            prevbtn = (Button) findViewById(R.id.prevbtn);
            prevbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inteprev = new Intent(getApplicationContext(), FormStep1Activity.class);
                    startActivity(inteprev);
                }
            });

            actv = (AutoCompleteTextView) findViewById(R.id.uniqueid);
            actv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                }
            });
            actv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //autocomplete(actv.getText().toString());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    autocomplete(actv.getText().toString(),getDistrictId());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    autocomplete(actv.getText().toString(),getDistrictId());
                }
            });


           /* bt_voiceinput = (ImageButton) findViewById(R.id.ib_speak);
            bt_voiceinput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVoiceRecognitionActivity();
                }
            });*/

            if(statusval.equals("edit")) {
                mydb = new DBHelper(this);
                rs = mydb.getDataSUbmittedForm(sid);
                rs.moveToFirst();

                String scode = rs.getString(rs.getColumnIndex("ShelterCode"));
                actv.setText(scode);
            }

        } else {
            Intent i = new Intent(this.getBaseContext().getApplicationContext(), LoginActivity.class);
            this.startActivity(i);
        }
    }


    public void autocomplete(final String xtval, final int did){
        if(xtval!="") {
            //Toast.makeText(getApplicationContext(), xtval, Toast.LENGTH_SHORT).show();
            mydb = new DBHelper(getApplicationContext());
            ArrayList array_list = mydb.getLikeShelter(xtval,did);

            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (getApplicationContext(), R.layout.spinner_dropdown_item, array_list);
            actv.setThreshold(1);
            actv.setAdapter(dataAdapter);
            mydb.close();
        }
    }

    /*private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the word");
        startActivityForResult(intent, REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    String Query = textMatchList.get(0);
                    // uf.setText(Query);
                    if(actv.getText().length() > 0)
                    {
                        actv.setText( actv.getText().toString() + Query);
                    }
                    else
                    {
                        actv.setText(Query);
                    }
                }

                //Result code for various error.
            } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
                showToastMessage("Network Error");
            } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
                showToastMessage("No Match");
            } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
                showToastMessage("Server Error");
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
*/
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

    public int getDistrictId() {
        sharedPref = this.getSharedPreferences("LOGININFO", Context.MODE_PRIVATE);
        int s = sharedPref.getInt("district", 0);
        return s;
    }
}
