package com.example.jaj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<TrainingModel> trainingModels = new ArrayList<>();

    public RecyclerViewAdapter(Context context, List<TrainingModel> userModel){
        this.context = context;
        this.trainingModels = userModel;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout or giving a look to our rows
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        //Assigning values on our row
        holder.upcoming.setText(trainingModels.get(position).getTrainingName().trim() + "\n" + new DateConverter().date(trainingModels.get(position).getTrainingDate())+ ": " + new DateConverter().time(trainingModels.get(position).getTrainingStart()) + " - " + new DateConverter().time(trainingModels.get(position).getTrainingEnd()));

    }

    @Override
    public int getItemCount() {
        return trainingModels.size();
    }
    public void updateData(List<TrainingModel> newUsers) {
        this.trainingModels = newUsers;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing all the views
        //similar to onCreate method
        TextView upcoming;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            upcoming = itemView.findViewById(R.id.upcomingDetail);
        }
    }

}
