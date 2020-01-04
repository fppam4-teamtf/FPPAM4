package com.teamtf.portalamikom.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.model.NewsList;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{

    private ArrayList<NewsList> newsData;
    private Context context;

    public NewsListAdapter(ArrayList<NewsList> newsData, Context context){
        this.newsData = newsData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_content_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsList currentNewsData = newsData.get(position);
        holder.bindTo(currentNewsData);
    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView number, tvTitle, tvDate;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.tv_number);
            tvTitle = itemView.findViewById(R.id.tv_content_title);
            tvDate = itemView.findViewById(R.id.tv_content_date);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {

            NewsList currentNewsData = newsData.get(getAdapterPosition());
            Log.d("News Data", "onClick: itemview = "+currentNewsData);
        }

        private void bindTo(NewsList currentNewsData) {
            number.setText(String.valueOf(currentNewsData.getId()));
            tvTitle.setText(currentNewsData.getTitle());
            tvDate.setText(currentNewsData.getDate());
        }


    }
}
