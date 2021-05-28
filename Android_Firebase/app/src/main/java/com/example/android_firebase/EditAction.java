package com.example.android_firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditAction extends AppCompatActivity {
    TextView id;
    EditText name,age,address;
    Button btnEdit;
    String url = "https://60ad919b80a61f00173312d8.mockapi.io/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        id = findViewById(R.id.id);
        name = findViewById(R.id.editName);
        age = findViewById(R.id.editAge);
        address = findViewById(R.id.editAddress);
        btnEdit = findViewById(R.id.btnEdit);
        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        Student student = (Student) bundle.getSerializable("student");
        id.setText(new StringBuilder("ID: ").append(student.getId()));
        name.setText(student.getName());
        age.setText(student.getAge()+"");
        address.setText(student.getAddress());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutAPI(url);
            }
        });
    }
    private void PutAPI(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "/" + id.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText( EditAction.this, "Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( EditAction.this,MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( EditAction.this, "Error by Put data!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",name.getText().toString());
                params.put("age",age.getText().toString());
                params.put("address",address.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}