package com.example.android_firebase;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView email;
    String url = "https://60ad919b80a61f00173312d8.mockapi.io/users";
    RecyclerView recyclerView;
    StudentAdapter adapter;
    List<Student> list = new ArrayList<>();
    FloatingActionButton btnAdd;
    StudentAdapter.OnStudentListener onStudentListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        email= findViewById(R.id.user);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        email.setText("User: "+bundle.getString("email"));
        getJsonObjectArray(url,list);
        adapter = new StudentAdapter(this,list,onStudentListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        adapter.setOnStudentListener(new StudentAdapter.OnStudentListener() {
            @Override
            public void onDiaChiClick(int position) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, AddAction.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getJsonObjectArray(String url, List<Student> list){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0 ; i<response.length();i++){
                            try {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                Student student = new Student();
                                student.setId(jsonObject.getString("id").toString());
                                student.setAge(jsonObject.getInt("age"));
                                student.setName(jsonObject.getString("name"));
                                student.setAddress(jsonObject.getString("address"));
                                list.add(student);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,
                                "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}