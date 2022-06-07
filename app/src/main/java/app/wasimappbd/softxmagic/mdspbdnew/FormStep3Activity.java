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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormStep3Activity extends NavigationActivity {

    View v;
    Button nextbtn,prevbtn;
    Context context;
    SharedPreferences sharedPref;
    int positionval;


    String existorg = null;
    FormModel fmodel = new FormModel();
    public DBHelper mydb;
    String desig,orgn;
    Cursor rs;
    RadioGroup rdg,rg;

    String orgText=null;
    String desgText=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_form_step1);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_form_step3, null,false);
        drawer.addView(v,0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Organization & Designation");

        Intent intv = getIntent();
        final String shelterCode = intv.getStringExtra("uniqueid");
        final String statusval = intv.getStringExtra("action");
        final int sid = intv.getIntExtra("subid",0);
        //Toast.makeText(getApplicationContext(), shelterCode, Toast.LENGTH_SHORT).show();

        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validationSuccess(statusval)) {
                    Intent intentnext = new Intent(getApplicationContext(), FormStep4Activity.class);
                    intentnext.putExtra("subid", sid);
                    intentnext.putExtra("action", statusval);
                    intentnext.putExtra("uniqueid", shelterCode);
                    intentnext.putExtra("organization", orgn);
                    intentnext.putExtra("designation", desig);
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



        if(statusval.equals("edit")){
            prevbtn.setVisibility(View.GONE);
            mydb = new DBHelper(this);
            rs = mydb.getDataSUbmittedForm(sid);
            rs.moveToFirst();

            RadioButton orgrBtn1 = (RadioButton) findViewById(R.id.org1);
            RadioButton orgrBtn2 = (RadioButton) findViewById(R.id.org2);
            RadioButton orgrBtn3 = (RadioButton) findViewById(R.id.org3);
            RadioButton orgrBtn4 = (RadioButton) findViewById(R.id.org4);
            RadioButton orgrBtn5 = (RadioButton) findViewById(R.id.org5);
            RadioButton orgrBtn6 = (RadioButton) findViewById(R.id.org6);
            String checkvalue1 = orgrBtn1.getText().toString();
            String checkvalue2 = orgrBtn2.getText().toString();
            String checkvalue3 = orgrBtn3.getText().toString();
            String checkvalue4 = orgrBtn4.getText().toString();
            String checkvalue5 = orgrBtn5.getText().toString();
            String checkvalue6 = orgrBtn6.getText().toString();

            //Toast.makeText(getActivity(), orgn+" "+checkvalue3, Toast.LENGTH_SHORT).show();

            if(checkvalue1.equals(orgn)) orgrBtn1.setChecked(true);
            else if(checkvalue2.equals(orgn)) orgrBtn2.setChecked(true);
            else if(checkvalue3.equals(orgn)) orgrBtn3.setChecked(true);
            else if(checkvalue4.equals(orgn)) orgrBtn4.setChecked(true);
            else if(checkvalue5.equals(orgn)) orgrBtn5.setChecked(true);
            else if(checkvalue6.equals(orgn)) orgrBtn6.setChecked(true);
        }

        rdg = (RadioGroup) findViewById(R.id.orggroup);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton orgrBtn = (RadioButton) findViewById(i);
                orgn = orgrBtn.getText().toString();
                showRadioButtonDialog(orgn);
            }
        });
    }

    private void showRadioButtonDialog(final String selText) {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_OPTIONS_PANEL);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_radio);
        List<String> stringList=new ArrayList<String>();

        if(selText.equals("LGED")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.lged)));
        }
        else if(selText.equals("D&SC")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.dnsc)));
        }
        else if(selText.equals("World Bank")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.worldbank)));
        }
        else if(selText.equals("School Management Committe")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.school_ms)));
        }
        else if(selText.equals("Monitoring and Evaluation")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.monitoring)));
        }
        else if(selText.equals("Contractor")){
            stringList.addAll(Arrays.asList(getResources().getStringArray(R.array.contractor)));
        }
        else {
            dialog.dismiss();
        }



        for(int i=0;i<stringList.size();i++) {
            stringList.get(i);
        }
        rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        final TextView mhead = (TextView) dialog.findViewById(R.id.masterhead);
        mhead.setText(selText);

        for(int i=0;i<stringList.size();i++){
            final RadioButton rb = new RadioButton(this);

            String selectedBtn = stringList.get(i);
            //Toast.makeText(getActivity(), selectedBtn+" - "+desig, Toast.LENGTH_SHORT).show();
            if(selectedBtn.equals(desig)) rb.setChecked(true);

            rb.setText(selectedBtn);
            rb.setPadding(15,15,15,15);
            rb.setTextSize(17);
            rb.setId(i);
            rb.setGravity(Gravity.LEFT);
            rg.addView(rb);
        }

        dialog.show();


        Button closebt = (Button) dialog.findViewById(R.id.canceltn);
        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);
                if(selectedId!= -1) {
                    desig = radioButton.getText().toString();
                    dialog.dismiss();
                }
                else{
                    desig = "";
                    dialog.dismiss();
                }
            }
        });


       /* Button okbt = (Button) dialog.findViewById(R.id.okbtn);
        okbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);

                if(selectedId!= -1) {
                    String selectedBtn = radioButton.getText().toString();
                    *//*SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("designation", selectedBtn);
                    editor.commit();*//*
                    desig = selectedBtn;
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Select a option or Close window", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }

    private void AlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please Select One Organization and Designation").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
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
            if(rdg.getCheckedRadioButtonId()<=0){
                Toast.makeText(getApplicationContext(),"Please select Organization",Toast.LENGTH_SHORT).show();

                if(rg.getCheckedRadioButtonId() < 0){
                    Toast.makeText(getApplicationContext(),"Please select designation",Toast.LENGTH_SHORT).show();
                    return false;
                }

                return false;
            }


        }
        else{
            if(rdg.getCheckedRadioButtonId()<=0){
                Toast.makeText(getApplicationContext(),"Please select Organization",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(rg.getCheckedRadioButtonId() < 0){
                Toast.makeText(getApplicationContext(),"Please select designation",Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        return true;
    }
}
