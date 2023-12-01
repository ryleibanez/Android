package com.example.jaj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewBookAdapter extends RecyclerView.Adapter<RecyclerViewBookAdapter.MyViewHolder> {
    private Context context;
    private List<TrainingModel> trainingModels = new ArrayList<>();

    public RecyclerViewBookAdapter(Context context, List<TrainingModel> trainingModels) {
        this.context = context;
        this.trainingModels = trainingModels;
    }

    @NonNull
    @Override
    public RecyclerViewBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout or giving a look to our rows
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_book, parent, false);
        return new RecyclerViewBookAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBookAdapter.MyViewHolder holder, int position) {
        holder.txtBookTitle.setText(trainingModels.get(position).getTrainingName());
        holder.txtDescription.setText(trainingModels.get(position).getTrainingDescription());
        holder.btnViewTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = new trainingController().getInstructor(trainingModels.get(position).getTrainingID()+"", context.getApplicationContext());
                Intent intent = new Intent(view.getContext(), ViewTraining.class);
                intent.putExtra("tid",trainingModels.get(position).getTrainingID()+"");
                intent.putExtra("name",name);
                intent.putExtra("location",trainingModels.get(position).getTrainingLocation());
                intent.putExtra("description",trainingModels.get(position).getTrainingDescription());
                intent.putExtra("date",trainingModels.get(position).getTrainingDate());
                intent.putExtra("title",trainingModels.get(position).getTrainingName());
                intent.putExtra("startTime",trainingModels.get(position).getTrainingStart());
                intent.putExtra("endTime",trainingModels.get(position).getTrainingEnd());


                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return trainingModels.size();
    }
    public void updateData(List<TrainingModel> trainingModels) {
        this.trainingModels = trainingModels;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing all the views
        //similar to onCreate method

        CardView btnViewTraining;

        TextView txtBookTitle,txtDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btnViewTraining = itemView.findViewById(R.id.btnViewTraining);
            txtBookTitle = itemView.findViewById(R.id.txtBookTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);

        }
    }



}
