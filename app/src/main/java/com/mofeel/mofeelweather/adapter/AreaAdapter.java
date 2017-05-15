package com.mofeel.mofeelweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mofeel.mofeelweather.model.entity.BaseInfo;

import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/11 15:02
 * @email:18328541378@163.com
 *
 * 基于ListView无法响应到AppbarLayout的事件 选择使用RecyclerView
 *
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.BaseVH> {

    private List<? extends BaseInfo> list;

    private Context context;

    private IOnItemClickListener listener;

    public interface IOnItemClickListener {
        void onItemClickListener(BaseInfo baseInfo);
    }

    public AreaAdapter(List<? extends BaseInfo> list, Context context) {
        this.list = list;
        this.context = context;

    }

    public void setListener(IOnItemClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<? extends BaseInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public AreaAdapter.BaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        AreaAdapter.BaseVH vh = new AreaAdapter.BaseVH(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(AreaAdapter.BaseVH holder, final int position) {
        holder.tv.setText(list.get(position).getName());
        if (listener != null)
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 回调回传
                    listener.onItemClickListener(list.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    class BaseVH extends RecyclerView.ViewHolder {
        TextView tv;

        public BaseVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
