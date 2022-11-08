package com.example.apptrip.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptrip.R;
import com.example.apptrip.databinding.FragmentUploadBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class FragmentUpload extends Fragment {
    private FragmentUploadBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUploadBinding.inflate(inflater,container,false);

        binding.btnUploadCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upLoadRespone = "SUCCESS";
                String userID = "vm123";
                String number = "2";
                String names = "Android , Conference , Client Meeting";
                String message = "successful";
                postDataUsingVolley(upLoadRespone,userID,number,names,message);
            }
        });

        return binding.getRoot();
    }

    private void postDataUsingVolley(String upLoadRespone,String userID , String number , String names , String message) {
        // url to post our data
        String url = "https://cwservice1786.herokuapp.com/sendPayLoad";
        //String url = "https://reqres.in/api/users";
        binding.idLoadingPB.setVisibility(View.VISIBLE);
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                binding.idLoadingPB.setVisibility(View.GONE);

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);
                    // below are the strings which we
                    // extract from our json object.
                    String upLoadRespone = respObj.getString("upload");
                    String userID = respObj.getString("user");
                    String number = respObj.getString("number");
                    String names = respObj.getString("names");
                    String message = respObj.getString("message");
                    showDialog("{uploadResponseCode :" + upLoadRespone+"\n"+
                                "userid :" + userID+"\n"+
                                "number :" + number +"\n"+
                                "names :" + names+"\n"+
                                "message :" + message +"\n"+
                                "upload - all done ! }");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                showDialog("Fail to get response =" + error);
                binding.idLoadingPB.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("upload",upLoadRespone);
                params.put("user",userID);
                params.put("number",number);
                params.put("names",names);
                params.put("message",message);
                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void showDialog(String message){//khởi tạo dialog
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_confirm);
        TextView txtContent = (TextView) dialog.findViewById(R.id.tv_content);
        txtContent.setText(message);
        Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
