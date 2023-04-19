package com.example.snake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class historyadapter extends FirebaseRecyclerAdapter<historymodel,historyadapter.myhistoryadapter> {
    public historyadapter(@NonNull FirebaseRecyclerOptions<historymodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myhistoryadapter holder, int position, @NonNull historymodel model) {
        if (position >= getItemCount()) {
            return; // invalid position, do nothing
        }
        holder.snakeidtxt.setText("Snake ID: " + model.getSnakeid());
        holder.snakecodetxt.setText("Rescue Code: " + model.getSnakecode());
    }

    @NonNull
    @Override
    public myhistoryadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myhistoryadapter(view);
    }

    class myhistoryadapter extends RecyclerView.ViewHolder {
        TextView snakeidtxt,snakecodetxt;
        public myhistoryadapter(@NonNull View itemView) {
            super(itemView);
            snakeidtxt = itemView.findViewById(R.id.snakeidhistorytxt);
            snakecodetxt = itemView.findViewById(R.id.rescuecodehistorytxt);
        }
    }
}