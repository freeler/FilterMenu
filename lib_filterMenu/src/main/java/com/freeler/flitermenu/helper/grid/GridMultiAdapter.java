package com.freeler.flitermenu.helper.grid;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选单选列表
 *
 * @author: freeler
 * @Date: 2020/1/13
 */
public class GridMultiAdapter<T> extends RecyclerView.Adapter<GridMultiAdapter.ViewHolder> {

    /**
     * 默认颜色
     */
    private int normalBgResource = R.drawable.corner_gray_radius_2;
    //    private int normalBgColor = Color.parseColor("#F5F5F5");
    private int normalTextColor = Color.parseColor("#909195");
    /**
     * 选中颜色
     */
    private int selectedBgResource = R.drawable.corner_light_green_radius_2;
    //    private int selectedBgColor = Color.parseColor("#DBF2E7");
    private int selectedTextColor = Color.parseColor("#00AE66");

    /**
     * 上下文
     */
    private Context context;
    /**
     * 筛选项数据
     */
    private List<T> list;
    /**
     * 已勾选的筛选项数据
     */
    private List<T> choiceList;
    /**
     * item显示需要字符串，用于把T转换为String，如果为null则直接调用toString()方法
     */
    private Convert<T, String> convert;
    /**
     * item点击监听
     */
    private OnItemClickListener listener;

    public GridMultiAdapter(Context context, @NonNull List<T> list, @NonNull List<T> choiceList) {
        this(context, list, choiceList, null);
    }

    public GridMultiAdapter(Context context, @NonNull List<T> list, @NonNull List<T> choiceList, Convert<T, String> convert) {
        this.context = context;
        this.list = list;
        this.choiceList = choiceList;
        this.convert = convert;
    }

    @NonNull
    @Override
    public GridMultiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filer_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridMultiAdapter.ViewHolder holder, final int position) {
        final T t = list.get(position);
        String value = t.toString();
        if (convert != null) {
            value = convert.apply(t);
        }
        holder.line.setText(value);
        initItemColorStatus(holder, t);

        // item点击事件
        if (listener != null) {
            holder.line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reverseItemColorStatus(holder, t);
                    listener.onClick(position);
                }
            });
        }
    }

    /**
     * 给item设置背景颜色
     *
     * @param holder ViewHolder
     * @param t      泛型对象
     */
    private void initItemColorStatus(GridMultiAdapter.ViewHolder holder, T t) {
        itemColorStatus(holder, t, false);
    }

    /**
     * 点击item时改变背景颜色
     *
     * @param holder ViewHolder
     * @param t      泛型对象
     */
    private void reverseItemColorStatus(GridMultiAdapter.ViewHolder holder, T t) {
        itemColorStatus(holder, t, true);
    }

    private void itemColorStatus(GridMultiAdapter.ViewHolder holder, T t, boolean isReverse) {
        ArrayList<String> strings = new ArrayList<>();
        for (T t1 : choiceList) {
            strings.add(t1.toString());
        }
        boolean contains = strings.contains(t.toString());
        if (isReverse) contains = !contains;
        if (contains) {
//            holder.line.setBackgroundColor(selectedBgColor);
            holder.line.setBackgroundResource(selectedBgResource);
            holder.line.setTextColor(selectedTextColor);
        } else {
//            holder.line.setBackgroundColor(normalBgColor);
            holder.line.setBackgroundResource(normalBgResource);
            holder.line.setTextColor(normalTextColor);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.tv_line);
        }
    }

}
