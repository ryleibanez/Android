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

public class RecyclerViewNotificationAdapter extends RecyclerView.Adapter<RecyclerViewNotificationAdapter.MyViewHolder> {
    private Context context;
    private List<NotificationModel> trainingModels = new ArrayList<>();

    public RecyclerViewNotificationAdapter(Context context, List<NotificationModel> userModel){
        this.context = context;
        this.trainingModels = userModel;
    }

    @NonNull
    @Override
    public RecyclerViewNotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout or giving a look to our rows
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_view_notification, parent, false);
        return new RecyclerViewNotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewNotificationAdapter.MyViewHolder holder, int position) {
        //Assigning values on our row
        holder.notification.setText(trainingModels.get(position).getMessage().trim());

    }

    @Override
    public int getItemCount() {
        return trainingModels.size();
    }
    public void updateData(List<NotificationModel> newUsers) {
        this.trainingModels = newUsers;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing all the views
        //similar to onCreate method
        TextView notification;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notification = itemView.findViewById(R.id.txtNotification);
        }
    }

}
