package com.freeler.flitermenu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freeler.flitermenu.R;
import com.freeler.flitermenu.listener.Convert;

import java.util.List;

/**
 * 筛选单选列表
 *
 * @author: xuzeyang
 * @Date: 2020/1/13
 */
public class ListFilterAdapter<T> extends RecyclerView.Adapter<ListFilterAdapter.ViewHolder> {

    private Context context;
    private List<T> list;
    private Convert<T, String> convert;
    private OnItemClickListener listener;

    public ListFilterAdapter(Context context, @NonNull List<T> list) {
        this(context, list, null);
    }

    public ListFilterAdapter(Context context, @NonNull List<T> list, Convert<T, String> convert) {
        this.context = context;
        this.list = list;
        this.convert = convert;
    }

    @NonNull
    @Override
    public ListFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFilterAdapter.ViewHolder holder, final int position) {
        T t = list.get(position);
        String value = t.toString();
        if (convert != null) value = convert.apply(t);
        holder.line.setText(value);
        if (listener != null) {
            holder.line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.tv_line);
        }
    }

}
