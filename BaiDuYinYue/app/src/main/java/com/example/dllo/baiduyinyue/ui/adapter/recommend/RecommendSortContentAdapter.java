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
import com.example.dllo.baiduyinyue.views.MyGridView;

import java.util.List;

/**
 * Created by Limiao on 16/7/19.
 * 推荐--歌曲分类详情具体内容的Adapter
 */
public class RecommendSortContentAdapter extends BaseAdapter {
    private List<String> tagBeen;
    private RecommendSortContentDetailGvAdapter recommendSortContentDetailGvAdapter;
    private RecommendSortContentBean recommendSortContentBean;
    private Context context;
    private static final int TITLE_TYPE = 0;
    private static final int DETAIIL_TYPE = 1;
    private static final int TOTAL_TYPE = 2;


    public void setRecommendSortContentBean(RecommendSortContentBean recommendSortContentBean) {
        this.recommendSortContentBean = recommendSortContentBean;
        notifyDataSetChanged();
    }


    public RecommendSortContentAdapter(Context context) {
        this.context = context;
        recommendSortContentDetailGvAdapter = new RecommendSortContentDetailGvAdapter(context);
    }

    @Override
    public int getCount() {
        return tagBeen == null ? 0 : tagBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return tagBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int type = position % 2;
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return TOTAL_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendSortTitleViewHolder recommendSortTitleViewHolder = null;
        RecommendSortDetailViewHolder recommendSortDetailViewHolder = null;
        int type = getItemViewType(position);
        switch (type) {
            case TITLE_TYPE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_sort_content_gv_title, parent, false);
                    recommendSortTitleViewHolder = new RecommendSortTitleViewHolder(convertView);
                    convertView.setTag(recommendSortTitleViewHolder);
                } else {
                    recommendSortTitleViewHolder = (RecommendSortTitleViewHolder) convertView.getTag();
                }
                recommendSortTitleViewHolder.textView.setText(recommendSortContentBean.getTags().get(position));
                break;

            case DETAIIL_TYPE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_sort_content_gv_detail, parent, false);
                    recommendSortDetailViewHolder = new RecommendSortDetailViewHolder(convertView);
                    convertView.setTag(recommendSortDetailViewHolder);
                } else {
                    recommendSortDetailViewHolder = (RecommendSortDetailViewHolder) convertView.getTag();
                }
                recommendSortContentDetailGvAdapter.setRecommendSortContentBean(recommendSortContentBean);
                recommendSortDetailViewHolder.myGridView.setAdapter(recommendSortContentDetailGvAdapter);
                break;
        }

        return convertView;
    }

    class RecommendSortDetailViewHolder {
        private MyGridView myGridView;

        public RecommendSortDetailViewHolder(View view) {
            myGridView = (MyGridView) view.findViewById(R.id.item_recommend_sort_content_gv_detail_gv);
        }
    }

    class RecommendSortTitleViewHolder {
        private TextView textView;

        public RecommendSortTitleViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_recommend_sort_content_gv_title_tv);
        }
    }
}
