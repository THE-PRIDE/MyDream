package com.dream.lmy.mydream.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dream.lmy.mydream.R;

import java.util.List;

public class QueryLocationAdapter extends RecyclerView.Adapter<QueryLocationAdapter.MyViewHolder> {


    private List<String> listData;

    public QueryLocationAdapter(List<String> listData){
        this.listData = listData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_query_data,null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvItem.setText(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void notifyListUpdate(List<String> newListData){
        listData = newListData;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvItem;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTvItem = itemView.findViewById(R.id.tv_item_data);
        }
    }
}
