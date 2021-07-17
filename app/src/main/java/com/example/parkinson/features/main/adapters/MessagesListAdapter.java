package com.example.parkinson.features.main.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkinson.R;
import com.example.parkinson.model.general_models.Medicine;
import com.example.parkinson.model.general_models.MedicineCategory;

import java.util.ArrayList;
import java.util.List;


public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    List<String> messages;

    public MessagesListAdapter(List<String> messages){
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String message = messages.get(position);
        Context context = holder.itemView.getContext();
        if(message.equals("UnansweredQuestionnaire")){
            holder.message.setText(context.getString(R.string.new_questionnaire));
        } else if(message.equals("NeedToUpdateMedicine")){
            holder.message.setText((context.getString(R.string.fill_medications)));
        } else {
            holder.message.setText((context.getString(R.string.no_messages)));
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.itemMessage);
        }
    }

}