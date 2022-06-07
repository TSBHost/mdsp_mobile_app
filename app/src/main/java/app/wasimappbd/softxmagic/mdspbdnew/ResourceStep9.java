package app.wasimappbd.softxmagic.mdspbdnew;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class ResourceStep9 extends NavigationActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_resource_step9, null,false);
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
        final String expmobil = intv.getStringExtra("expmobil");
        final String matdetails = intv.getStringExtra("matdetails");
        final String contrrep = intv.getStringExtra("contrrep");

        //Toast.makeText(getApplicationContext(),statusval+"\n"+sid+"\n"+shelterCode+"\n"+matintendname+"\n"+stackyard+"\n"+qtyintend+"\n"+expmobil+"\n"+matdetails+"\n"+contrrep, Toast.LENGTH_LONG).show();
        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasText(edt)){
                    Intent intentnext = new Intent(getApplicationContext(), ResourceStep9.class);
                    intentnext.putExtra("subid", sid);
                    intentnext.putExtra("action", statusval);
                    intentnext.putExtra("uniqueid", shelterCode);
                    intentnext.putExtra("matintendname", matintendname);
                    intentnext.putExtra("sourcebrand", sourcebrand);
                    intentnext.putExtra("stackyard", stackyard);
                    intentnext.putExtra("qtyintend", qtyintend);
                    intentnext.putExtra("expmobil", expmobil);
                    intentnext.putExtra("matdetails", matdetails);
                    intentnext.putExtra("contrrep", contrrep);
                   // intentnext.putExtra("contrrep", contrrep);
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

        edt = (EditText) findViewById(R.id.usertaskcomment);

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
                startVoiceRecognitionActivity();
            }
        });
    }

    private void startVoiceRecognitionActivity() {
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
                    if(edt.getText().length() > 0)
                    {
                        edt.setText( edt.getText().toString() + Query);
                    }
                    else
                    {
                        edt.setText(Query);
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
