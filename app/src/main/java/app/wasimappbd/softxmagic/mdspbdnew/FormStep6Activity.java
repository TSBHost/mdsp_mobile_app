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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class FormStep6Activity extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    EditText edt;
    SharedPreferences sharedPref;
    private static final int REQUEST_CODE = 1234;
    ImageButton bt_voiceinput;
    private static final String REQUIRED_MSG = "Sorry! Need to add some comments";
    public DBHelper mydb;
    String ucomments;
    Cursor rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLogin() && read_memory() != 0) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_form_step6, null,false);
            drawer.addView(v,0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Task Related Comments");
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            Intent intv = getIntent();
            final String statusval = intv.getStringExtra("action");
            final int sid = intv.getIntExtra("subid",0);
            final String shelterCode = intv.getStringExtra("uniqueid");
           // final String orgText = intv.getStringExtra("organization");
            //final String desgText = intv.getStringExtra("designation");
            //final String shUser = intv.getStringExtra("dscuname");
            final String mTask = intv.getStringExtra("majortask");
            final String sTask = intv.getStringExtra("subtask");

            //Toast.makeText(getApplicationContext(), sTask,Toast.LENGTH_LONG).show();

            nextbtn = (Button) findViewById(R.id.nextbtn);
            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hasText(edt)){
                        Intent intentnext;
                        if(statusval.equals("edit")) {
                            intentnext = new Intent(getApplicationContext(), FormStep10Activity.class);
                        }
                        else{
                            intentnext = new Intent(getApplicationContext(), FormStep10Activity.class);
                        }
                        intentnext.putExtra("subid", sid);
                        intentnext.putExtra("action", statusval);
                        intentnext.putExtra("uniqueid", shelterCode);
                        //intentnext.putExtra("organization", orgText);
                       // intentnext.putExtra("designation", desgText);
                       // intentnext.putExtra("dscuname", shUser);
                        intentnext.putExtra("majortask", mTask);
                        intentnext.putExtra("subtask", sTask);
                        intentnext.putExtra("ucomment", edt.getText().toString());
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

                ucomments = rs.getString(rs.getColumnIndex("Comments"));
                edt.setText(ucomments);
            }

            edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                }
            });
            bt_voiceinput = (ImageButton) findViewById(R.id.ib_speak);
            bt_voiceinput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVoiceRecognitionActivity();
                }
            });
        } else {
            Intent i = new Intent(this.getBaseContext().getApplicationContext(), LoginActivity.class);
            this.startActivity(i);
        }
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
