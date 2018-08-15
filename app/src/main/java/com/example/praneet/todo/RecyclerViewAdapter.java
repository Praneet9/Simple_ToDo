package com.example.praneet.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> item_title;
    private ArrayList<String> item_subtitle;
    private ArrayList<Boolean> checkbox;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> item_title, ArrayList<String> item_subtitle, ArrayList<Boolean> checkbox, Context mContext) {
        this.item_title = item_title;
        this.item_subtitle = item_subtitle;
        this.checkbox = checkbox;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.title.setText(item_title.get(position));
        holder.subtitle.setText(item_subtitle.get(position));
        holder.checkBox.setChecked(checkbox.get(position));

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on " + item_title.get(holder.getAdapterPosition()));
                CheckBox check;
                int pos = holder.getAdapterPosition();

                check = v.findViewById(R.id.checkBox);

                if (!check.isChecked())
                {
                    check.setChecked(true);
                    checkbox.set(pos,true);
                    Collections.swap(item_title, pos, item_title.size() - 1);
                    Collections.swap(item_subtitle, pos, item_subtitle.size() - 1);
                    Collections.swap(checkbox, pos, checkbox.size() - 1);
                    notifyItemMoved(pos, item_title.size() - 1);
                }
                else {
                    check.setChecked(false);
                    checkbox.set(pos,false);
                    Collections.swap(item_title, pos, 0);
                    Collections.swap(item_subtitle, pos, 0);
                    Collections.swap(checkbox, pos, 0);
                    notifyItemMoved(pos, 0);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return item_subtitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView title, subtitle;

        LinearLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            title = itemView.findViewById(R.id.list_title);
            subtitle = itemView.findViewById(R.id.subtitle);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }

    }

}
