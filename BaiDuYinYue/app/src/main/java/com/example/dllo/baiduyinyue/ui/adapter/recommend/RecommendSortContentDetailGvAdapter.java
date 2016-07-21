package com.example.dllo.baiduyinyue.ui.adapter.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.RecommendSortContentBean;
import com.example.dllo.baiduyinyue.utils.L;

import java.util.List;

/**
 * Created by Limiao on 16/7/19.
 * 推荐--歌曲分类详情--具体分类的Adapter
 */
public class RecommendSortContentDetailGvAdapter extends BaseAdapter{
    private RecommendSortContentBean recommendSortContentBean;
    private List<String> tags;
    private Context context;

    public RecommendSortContentDetailGvAdapter(Context context) {
        this.context = context;
//        tags = recommendSortContentBean.getTags();
    }

    public void setRecommendSortContentBean(RecommendSortContentBean recommendSortContentBean) {
        this.recommendSortContentBean = recommendSortContentBean;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        L.e("tags",tags.size() + "");
        return tags == null ? 0 : tags.size();
//        return 30;
    }

    @Override
    public Object getItem(int position) {
        return tags == null ? null : tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetaliViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_sort_content_detail_gv_item,parent,false);
            holder = new DetaliViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (DetaliViewHolder) convertView.getTag();
        }
        holder.textView.setText(recommendSortContentBean.getTaglistBeen().get(position).getTitle());
        return convertView;
    }

    class DetaliViewHolder{
        private TextView textView;
        public DetaliViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_recommend_sort_content_gv_detail_tv);
        }
    }
}
