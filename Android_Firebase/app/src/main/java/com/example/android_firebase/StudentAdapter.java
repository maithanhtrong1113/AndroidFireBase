package com.example.android_firebase;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    List<Student> list = new ArrayList<>();
    LayoutInflater layoutInflater;
    OnStudentListener onStudentListener;
    interface OnStudentListener{
        void onDiaChiClick(int position);
    }

    public void setOnStudentListener(OnStudentListener onStudentListener) {
        this.onStudentListener = onStudentListener;
    }

    public StudentAdapter(Context context,List<Student> list, OnStudentListener onStudentListener) {
        this.list = list;
        this.onStudentListener = onStudentListener;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.student,parent,false),onStudentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.age.setText(list.get(position).getAge()+"");
        holder.address.setText(list.get(position).getAddress());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imgEdit.getContext(), EditAction.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("student",list.get(position));
                intent.putExtras(bundle);
                holder.imgEdit.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,age;
        ImageView imgEdit;
        OnStudentListener onStudentListener;
        public ViewHolder(@NonNull View itemView, OnStudentListener onStudentListener) {
            super(itemView);
            this.onStudentListener = onStudentListener;
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            address = itemView.findViewById(R.id.address);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }
}
