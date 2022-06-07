package app.wasimappbd.softxmagic.mdspbdnew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class ResourceActivity extends NavigationActivity {

    View view;
    Button nextbtn,prevbtn;
    Context context;
    String editstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_resource, null,false);
        drawer.addView(view,0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Resources");

        Intent intv = getIntent();
        final String statusval = intv.getStringExtra("action");
        final int sid = intv.getIntExtra("subid",0);

        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentnext = new Intent(getApplicationContext(), ResourceStep1.class);
                intentnext.putExtra("subid", sid);
                intentnext.putExtra("action", statusval);
                startActivity(intentnext);
            }
        });

        prevbtn = (Button) findViewById(R.id.prevbtn);
        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inteprev = new Intent(getApplicationContext(), GridViewActivity.class);
                startActivity(inteprev);
            }
        });
    }
}
