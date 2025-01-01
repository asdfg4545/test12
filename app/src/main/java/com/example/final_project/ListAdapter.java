package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter  extends BaseAdapter {
    public List<costList> mList;
    private List<costList> getmList;
    private LayoutInflater mLayoutInflater;

    public ListAdapter(List<costList> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list_item, null);

        costList item = mList.get(position);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_money = view.findViewById(R.id.tv_money);
        tv_title.setText(item.getTitle());
        tv_date.setText(item.getDate());
        tv_money.setText(item.getMoney());

        return view;
    }

    public ListAdapter(Context context, List<costList> list) {
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }
}
