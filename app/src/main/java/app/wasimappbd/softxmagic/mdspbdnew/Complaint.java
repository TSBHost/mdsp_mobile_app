package app.wasimappbd.softxmagic.mdspbdnew;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public class Complaint extends Fragment {

    EditText name,mobile,comp,email;
    ProgressDialog loading;
    Button btnSubmit;
    Config conf=new Config();
    private final String UPLOAD_URL = conf.getURL()+"complaint.php";
    private static final String REQUIRED_MSG = "Sorry This Field is Required";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complaint, container, false);

        name = (EditText) view.findViewById(R.id.userName);
        mobile = (EditText) view.findViewById(R.id.mobileno);
        email = (EditText) view.findViewById(R.id.femail);
        comp = (EditText) view.findViewById(R.id.complaint);
        btnSubmit = (Button) view.findViewById(R.id.registerBtn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasText(name,comp)) {
                    insertMethod(name.getText().toString(), mobile.getText().toString(),email.getText().toString(),comp.getText().toString());
                }
            }
        });

        return view;
    }

    public void insertMethod(final String iname,final String imobile, final String iemail,final String icompl){
        // Toast.makeText(getApplicationContext(), "Shelter: "+shelter+"\nOrg: "+org+"\nImgn: "+imgname1, Toast.LENGTH_LONG).show();
        loading = ProgressDialog.show(getActivity(),"Sending data","Please wait...",false,false);
        btnSubmit.setBackgroundColor(getResources().getColor(R.color.colorAsh));
        btnSubmit.setEnabled(false);
        Toast.makeText(getActivity(), iname+":"+imobile+":"+":"+iemail+":"+icompl, Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        btnSubmit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        btnSubmit.setEnabled(true);

                        Toast.makeText(getActivity(), "Successfully sent to server", Toast.LENGTH_LONG).show();
                        Intent inthome = new Intent(getActivity(), GridViewActivity.class);
                        startActivity(inthome);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        btnSubmit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        btnSubmit.setEnabled(true);
                        if (error instanceof NetworkError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(getActivity(),
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers == null)
                {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                            response.notModified,
                            response.networkTimeMs);
                }

                return super.parseNetworkResponse(response);
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();

                params.put("name", iname);
                params.put("mobile", imobile);
                //params.put("location", istate);
                params.put("complaint", icompl);
                params.put("email", iemail);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public static boolean hasText(EditText n,EditText c) {

        String na = n.getText().toString().trim();
        //String mb = m.getText().toString().trim();
        //String em = e.getText().toString().trim();
        String com = c.getText().toString().trim();
        n.setError(null);
        //m.setError(null);
       // e.setError(null);
        c.setError(null);

        // length 0 means there is no text
        if (na.length() == 0) {
            n.setError(REQUIRED_MSG);
            return false;
        }
       /* else if (mb.length() == 0) {
            m.setError(REQUIRED_MSG);
            return false;
        }
        else  if (em.length() == 0) {
            e.setError(REQUIRED_MSG);
            return false;
        }*/
        else if (com.length() == 0) {
            c.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }
}
