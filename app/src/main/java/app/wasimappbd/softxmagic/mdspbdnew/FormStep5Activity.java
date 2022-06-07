package app.wasimappbd.softxmagic.mdspbdnew;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormStep5Activity extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    SharedPreferences sharedPref;
    String mtaskv,taskv;
    public DBHelper mydb;
    Cursor rs;
    RadioGroup rdg,rg;
    String mTaskVal = null;
    String sTaskVal = null;
    String sqlitetask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DBHelper(this);

        if (isLogin() && read_memory() != 0) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_form_step5, null,false);
            drawer.addView(v,0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Major & Sub Tasks");

            Intent intv = getIntent();
            final String statusval = intv.getStringExtra("action");
            final int sid = intv.getIntExtra("subid",0);
            final String shelterCode = intv.getStringExtra("uniqueid");
           // final String orgText = intv.getStringExtra("organization");
           // final String desgText = intv.getStringExtra("designation");
           // final String shUser = intv.getStringExtra("dscuname");

            nextbtn = (Button) findViewById(R.id.nextbtn);
            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validationSuccess(statusval)) {
                        Intent intentnext = new Intent(getApplicationContext(), FormStep6Activity.class);
                        intentnext.putExtra("subid", sid);
                        intentnext.putExtra("action", statusval);
                        intentnext.putExtra("uniqueid", shelterCode);
                        intentnext.putExtra("majortask", mtaskv);
                        intentnext.putExtra("subtask", taskv);
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

            rdg = (RadioGroup) findViewById(R.id.orggroup);
            /*int RANGE_HOURS = 10;
            for (int i = 0; i < RANGE_HOURS; i++) {
                RadioButton button = new RadioButton(this);
                button.setId(i);
                button.setText(Integer.toString(i));
                rdg.addView(button);
            }
*/          ArrayList array_list = mydb.getAllTasks1();
            Log.v("workphases", array_list.toString());
            Cursor response = mydb.getAllTasks();

            int totalitem = response.getCount();

            if(response!= null){
                if(response.moveToFirst()){

                    int j = 0;
                    do{
                        j++;
                        sqlitetask = response.getString(response.getColumnIndex("MajorTasks"));

                        RadioButton button = new RadioButton(this);
                        button.setId(j);
                        button.setTextSize(20);
                        button.setPadding(7,0,0,0);
                        button.setText(sqlitetask);
                        rdg.addView(button);

                    }while(response.moveToNext());
                }

            }


           /* ArrayList array_list = mydb.getAllTasks();

            for (int j = 0; j <= array_list.size(); j++) {
                    sqlitetask = " " + array_list.add("task_name");
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(task);
                    rdg.addView(radioButton);
            }

            Log.v("sqlitetask",sqlitetask);*/

            rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    /// Get selected radio value inner radio group
                    RadioButton orgrBtn = (RadioButton) findViewById(i);

                    mtaskv = orgrBtn.getText().toString();
                    showRadioButtonDialog(mtaskv);
                }
            });

        } else {
            Intent i = new Intent(this.getBaseContext().getApplicationContext(), LoginActivity.class);
            this.startActivity(i);
        }
    }

    private void showRadioButtonDialog(final String selText) {
        List<String> stringList=new ArrayList<String>();

        //mydb = new DBHelper(getApplicationContext());
        //ArrayList array_list = mydb.getLikeShelter(selText);

        Cursor response = mydb.getAllSubTasks(selText);
        int totalitem = response.getCount();

        if(response!= null){
            if(response.moveToFirst()){
                int j = 0;
                do{
                    j++;
                    String sqlsubtask = response.getString(response.getColumnIndex("MajorTasks"));
                    stringList.add(sqlsubtask);
                }while(response.moveToNext());
            }
        }

       /* if(selText.equals("Site Handover to Contractor")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.handover_contractor)));
        }
        else if(selText.equals("Initial Work / Preworks")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.initial_work)));
        }
        else if(selText.equals("Sub Structure")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.sub_structure)));
        }
        else if(selText.equals("Super Structure Works - GF")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.super_structuregnd)));
        }
        else if(selText.equals("Super Structure Works - FF")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.super_structure1st)));
        }
        else if(selText.equals("Super Structure Works - SF")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.super_structure2nd)));
        }
        else if(selText.equals("Super Structure Works - Roof")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.super_structureroof)));
        }
        else if(selText.equals("Concrete Works Others")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.concreate_work)));
        }
        else if(selText.equals("Finishing Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.fnishing_works)));
        }
        else if(selText.equals("Electrical Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.electric_work)));
        }
        else if(selText.equals("Plumbing Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.plumbing_work)));
        }
        else if(selText.equals("Furniture Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.furn_work)));
        }
        else if(selText.equals("Road Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.road_work)));
        }
        else if(selText.equals("Lab Test - Building Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.quality_assu_work)));
        }
        else if(selText.equals("Lab Test - Road Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.lab_task_works)));
        }
        else if(selText.equals("MDSP Trainings")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.mdsp_projects_rel)));
        }
        else if(selText.equals("Social Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.social_works)));
        }
        else if(selText.equals("Environmental Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.env_works)));
        }
        else if(selText.equals("Rehabilation Works")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.reh_works)));
        }
        else if(selText.equals("No Work at Site")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.no_work_at_site)));
        }
        else if(selText.equals("General Meetings")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.general_meeting)));
        }*/

        final CharSequence[] dialogList=  stringList.toArray(new CharSequence[stringList.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(FormStep5Activity.this);
        if(selText.equals("Sub Structure") || selText.equals("Super Structure Works - GF")
                || selText.equals("Super Structure Works - FF") || selText.equals("Super Structure Works - SF") || selText.equals("Super Structure Works - Roof") || selText.equals("Concrete Works Others")){
            builderDialog.setTitle(selText+"\nEnsure QA tests are performed.");
        }
        else{
            builderDialog.setTitle(selText);
        }

        //builderDialog.setMessage("Pleaes Ensure the Relevent ");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count]; // set is_checked boolean false;
        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton, boolean isChecked) {
                    }
                });
        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);
                            if (checked) {
                                if (stringBuilder.length() > 0) stringBuilder.append(", ");
                                stringBuilder.append(list.getItemAtPosition(i));
                            }
                        }

                        if (stringBuilder.toString().trim().equals("")) {
                            //diag.setText("Click here to open Dialog");
                            taskv = "";
                            //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                            stringBuilder.setLength(0);
                        } else {
                            //diag.setText(stringBuilder);
                            taskv = stringBuilder.toString();
                            //Toast.makeText(getApplicationContext(),""+stringBuilder,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // diag.setText("Click here to open Dialog");
                        taskv = "";
                        //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();

    }


    private void AlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please Select Major Task and Sub Task").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
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
        if(stsval.equals("edit")){
            /*if(rdg.getCheckedRadioButtonId()<=0){
                Toast.makeText(getApplicationContext(),"Please select Organization",Toast.LENGTH_SHORT).show();

                if(rg.getCheckedRadioButtonId() < 0){
                    Toast.makeText(getApplicationContext(),"Please select designation",Toast.LENGTH_SHORT).show();
                    return false;
                }

                return false;
            }*/


        }
        else{
            if (rdg.getCheckedRadioButtonId() == -1){
                Toast.makeText(getApplicationContext(),"Please select Major Task",Toast.LENGTH_SHORT).show();
                return false;
            }
 /*
            if(rg.getCheckedRadioButtonId() < 0){
                Toast.makeText(getApplicationContext(),"Please select designation",Toast.LENGTH_SHORT).show();
                return false;
            }*/
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
