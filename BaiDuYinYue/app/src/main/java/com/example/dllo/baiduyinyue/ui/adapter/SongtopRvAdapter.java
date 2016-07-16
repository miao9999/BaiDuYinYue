package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.SongTopBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Limiao on 16/7/13.
 * 乐库--排行的Adapter
 */
public class SongtopRvAdapter extends RecyclerView.Adapter<SongtopRvAdapter.SongtopViewHolder> {
    private List<SongTopBean.ContentBean> contentBeen;
    private Context context;
    private SongtopRvClickListener listener;

    public void setListener(SongtopRvClickListener listener) {
        this.listener = listener;
        notifyDataSetChanged();
    }

    public void setContentBeen(List<SongTopBean.ContentBean> contentBeen) {
        this.contentBeen = contentBeen;
        notifyDataSetChanged();
    }

    public SongtopRvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SongtopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SongtopViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_songtop_rv,parent,false);
        holder = new SongtopViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SongtopViewHolder holder, int position) {
        SongTopBean.ContentBean contentBean = contentBeen.get(position);
        holder.nameTv.setText(contentBean.getName());
        List<SongTopBean.ContentBean.Bean> been = contentBean.getContent();
        holder.author1Tv.setText(been.get(0).getAuthor());
        holder.title1Tv.setText(been.get(0).getTitle());
        holder.author2Tv.setText(been.get(1).getAuthor());
        holder.title2Tv.setText(been.get(1).getTitle());
        holder.author3Tv.setText(been.get(2).getAuthor());
        holder.title3Tv.setText(been.get(2).getTitle());
        String url = contentBean.getPic_s210();
        Picasso.with(context).load(url).resize(200,200).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                listener.onRvClick(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentBeen.size();
    }

    class SongtopViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameTv,author1Tv,title1Tv,author2Tv,title2Tv,author3Tv,title3Tv;
        public SongtopViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.songtop_rv_iv);
            nameTv = (TextView) itemView.findViewById(R.id.songtop_rv_sort_tv);
            title1Tv = (TextView) itemView.findViewById(R.id.songtop_rv_detail1_tv);
            author1Tv = (TextView) itemView.findViewById(R.id.songtop_rv_author1_tv);
            author2Tv = (TextView) itemView.findViewById(R.id.songtop_rv_author2_tv);
            author3Tv = (TextView) itemView.findViewById(R.id.songtop_rv_author3_tv);
            title2Tv = (TextView) itemView.findViewById(R.id.songtop_rv_detail2_tv);
            title3Tv = (TextView) itemView.findViewById(R.id.songtop_rv_detail3_tv);
        }
    }

    public interface SongtopRvClickListener{
        void onRvClick(int pos);
    }
}
