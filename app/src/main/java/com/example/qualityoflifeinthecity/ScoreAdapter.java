package com.example.qualityoflifeinthecity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    ArrayList<Score> list;
    Context context;

    public ScoreAdapter(ArrayList<Score> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.score_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryText.setText(list.get(position).category_name);
        holder.scoreText.setText(String.format("%.2f",list.get(position).score_out_of_10));
        holder.progressBar.setMax(10);
        holder.progressBar.setProgress((int) list.get(position).score_out_of_10);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView categoryText;
         TextView scoreText;
         ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.categoryText);
            scoreText = itemView.findViewById(R.id.scoreText);
            progressBar = itemView.findViewById(R.id.simpleProgressBar);
        }
    }
}
