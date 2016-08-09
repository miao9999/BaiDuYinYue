package com.example.dllo.baiduyinyue.ui.adapter.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendSortHeadBean;

import java.util.List;

/**
 * Created by Limiao on 16/7/19.
 * 推荐--分类--上面显示部分的Adapter
 */
public class RecommendSortHeadAdapter extends BaseAdapter {
    private List<RecommendSortHeadBean.TaglistBean> taglistBeen;
    private Context context;
    private int imgIds[];

    public void setImgIds(int[] imgIds) {
        this.imgIds = imgIds;
        notifyDataSetChanged();
    }

    public RecommendSortHeadAdapter(Context context) {
        this.context = context;
    }

    public void setTaglistBeen(List<RecommendSortHeadBean.TaglistBean> taglistBeen) {
        this.taglistBeen = taglistBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return taglistBeen == null ? 0 : taglistBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return taglistBeen == null ? 0 : taglistBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendSoroViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recomment_sort_head_gv,parent,false);
            holder = new RecommendSoroViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (RecommendSoroViewHolder) convertView.getTag();
        }
        holder.textView.setText(taglistBeen.get(position).getTitle());
        holder.imageView.setImageResource(imgIds[position]);
        return convertView;
    }

    class RecommendSoroViewHolder {
        private TextView textView;
        private ImageView imageView;
        public RecommendSoroViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_recommend_sort_head_tv);
            imageView = (ImageView) view.findViewById(R.id.item_recommend_sort_head_iv);
        }
    }
}
