package com.teamtf.portalamikom.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamtf.portalamikom.R;
import com.teamtf.portalamikom.model.News;

import java.util.ArrayList;

public class NewsAdapter {

    private ArrayList<News> dataInformasi;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvTitle;
        private TextView tvDate;
        private ImageView ivNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_info_title);
            tvDate = itemView.findViewById(R.id.tv_info_date);
            ivNews = itemView.findViewById(R.id.iv_info);

//            itemView.setOnClickListener(this);
        }

        public ViewHolder(@NonNull View itemView, TextView tvTitle, TextView tvDate) {
            super(itemView);
            this.tvTitle = tvTitle;
            this.tvDate = tvDate;
        }

        void bindTo(News currentInfo){
            tvTitle.setText(currentInfo.getTitle());
            tvDate.setText(currentInfo.getDate());
            Glide.with(context).load(currentInfo.getImgResource()).into(ivNews);
        }

        @Override
        public void onClick(View v) {
            News currentInfo = dataInformasi.get(getAdapterPosition());

//            Intent previewInfo = new Intent(context,PreviewInfoActivity.class);

        }
    }
}
