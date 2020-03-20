package com.freeler.flitermenu.helper.list;

import android.content.Context;
import android.graphics.Color;
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
 * @author: freeler
 * @Date: 2020/1/13
 */
public class ListAdapter<T> extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    /**
     * item文字普通颜色
     */
    private final int normalTextColor = Color.parseColor("#535757");
    /**
     * item文字选中颜色
     */
    private final int selectedTextColor = Color.parseColor("#00AE66");

    /**
     * 上下文
     */
    private Context context;
    /**
     * 筛选项数据源
     */
    private List<T> list;
    /**
     * item显示需要字符串，用于把T转换为String，如果为null则直接调用toString()方法
     */
    private Convert<T, String> convert;
    /**
     * item点击监听
     */
    private OnItemClickListener listener;
    /**
     * 当前选中的值
     */
    private T choiceValue;


    public ListAdapter(Context context, @NonNull List<T> list) {
        this(context, list, null);
    }

    public ListAdapter(Context context, @NonNull List<T> list, Convert<T, String> convert) {
        this.context = context;
        this.list = list;
        this.convert = convert;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, final int position) {
        T t = list.get(position);
        String value = t == null ? "不限" : t.toString();
        if (convert != null && t != null)
            value = convert.apply(t);

        holder.line.setText(value);

        String currentValueStr = choiceValue == null ? "不限" : choiceValue.toString();
        if (convert != null && choiceValue != null)
            currentValueStr = convert.apply(choiceValue);

        if (currentValueStr.equals(value)) {
            holder.line.setTextColor(selectedTextColor);
        } else {
            holder.line.setTextColor(normalTextColor);
        }

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

    public void setValue(T value) {
        this.choiceValue = value;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.tv_line);
        }
    }

}
