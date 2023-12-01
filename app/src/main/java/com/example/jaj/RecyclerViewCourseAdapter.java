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

public class RecyclerViewCourseAdapter extends RecyclerView.Adapter<RecyclerViewCourseAdapter.MyViewHolder> {
    private Context context;
    private List<Course> courses = new ArrayList<>();

    public RecyclerViewCourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public RecyclerViewCourseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout or giving a look to our rows
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_course, parent, false);
        return new RecyclerViewCourseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCourseAdapter.MyViewHolder holder, int position) {

        holder.txtCourseTitle.setText(courses.get(position).getCourseName());
        holder.txtCourseDescription.setText(courses.get(position).getCourseDescription());
        holder.btnViewListTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseId = courses.get(position).getCourseId()+"";
                System.out.println("Course ID: " + courseId);
                Intent intent = new Intent(view.getContext(), BookTraining.class);
                intent.putExtra("courseId", courseId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public void updateData(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing all the views
        //similar to onCreate method

        CardView btnViewListTraining;

        TextView txtCourseTitle,txtCourseDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btnViewListTraining = itemView.findViewById(R.id.btnViewListTraining);
            txtCourseTitle = itemView.findViewById(R.id.txtCourseTitle);
            txtCourseDescription = itemView.findViewById(R.id.txtCourseDescription);

        }
    }
}
