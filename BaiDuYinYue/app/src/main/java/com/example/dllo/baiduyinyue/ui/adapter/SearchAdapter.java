package com.example.dllo.baiduyinyue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.baiduyinyue.R;
import com.example.dllo.baiduyinyue.mode.bean.SearchBean;
import com.example.dllo.baiduyinyue.utils.L;

import java.util.List;

/**
 * Created by Limiao on 16/7/17.
 * 搜索界面的Adapter
 */
public class SearchAdapter extends BaseAdapter{
    private List<SearchBean.MySearchBean> mySearchBeen;
    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setMySearchBeen(List<SearchBean.MySearchBean> mySearchBeen) {
        this.mySearchBeen = mySearchBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mySearchBeen == null ? 0 : mySearchBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mySearchBeen == null ? 0 : mySearchBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_gv,parent,false);
            holder = new SearchViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (SearchViewHolder) convertView.getTag();
        }
        holder.textView.setText(mySearchBeen.get(position).getWord());
        return convertView;
    }

    class SearchViewHolder{
        private TextView textView;
        public SearchViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_search_gv_tv);
        }
    }
}
