package com.teamtf.portalamikom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamtf.portalamikom.PreviewActivity;
import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.model.News;
import com.teamtf.portalamikom.model.NewsListHome;

import java.util.ArrayList;

public class NewsListHomeAdapter extends RecyclerView.Adapter<NewsListHomeAdapter.ViewHolder>{

    private ArrayList<NewsListHome> news;
    private Context context;

    public NewsListHomeAdapter(ArrayList<NewsListHome> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_news_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsListHome currentNewsData = news.get(position);
        holder.bindTo(currentNewsData);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvDate;
        private ImageView ivNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_news_title);
            tvDate = itemView.findViewById(R.id.tv_news_date);
            ivNews = itemView.findViewById(R.id.iv_news);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            NewsListHome currentNewsData = news.get(getAdapterPosition());

            Intent i = new Intent(context, PreviewActivity.class);
            i.putExtra("id",currentNewsData.getId());
            context.startActivity(i);
        }

        private void bindTo(NewsListHome currentNews) {
            tvTitle.setText(currentNews.getTitle());
            tvDate.setText(currentNews.getDate());
            Glide.with(context).load(currentNews.getImage()).into(ivNews);
        }
    }
}
