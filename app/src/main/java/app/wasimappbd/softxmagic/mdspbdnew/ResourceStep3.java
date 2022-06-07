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

public class ResourceStep3 extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLogin() && read_memory() != 0) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_resource_step3, null,false);
            drawer.addView(v,0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Material Mobilization Form");

            nextbtn = (Button) findViewById(R.id.nextbtn);
            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentnext = new Intent(getApplicationContext(), GridViewActivity.class);
                    //Intent intentnext = new Intent(getApplicationContext(), ImageCompress.class);
                    startActivity(intentnext);
                }
            });

            prevbtn = (Button) findViewById(R.id.prevbtn);
            prevbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        else {
            Intent i = new Intent(this.getBaseContext().getApplicationContext(), LoginActivity.class);
            this.startActivity(i);
        }
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
