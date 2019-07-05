package com.dream.lmy.mydream.mockLocation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dream.lmy.mydream.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<String> stringList;
    private Context context;
    private IMocKClickListener mockClickListener;

    public LocationAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mock_location, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTvMockLocation.setText(stringList.get(position));
        holder.mTvMockLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mockClickListener.mockListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void setMockClickListener(IMocKClickListener mockClickListener) {
        this.mockClickListener = mockClickListener;
    }

    public void notifyMockData(List<String> locationList) {
        this.stringList = locationList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvMockLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvMockLocation = itemView.findViewById(R.id.tv_item_mock_location);
        }

    }
}
