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
public class GridAdapter<T> extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private Context context;
    private List<T> list;
    private List<T> choiceList;
    private Convert<T, String> convert;
    private OnItemClickListener listener;

    public GridAdapter(Context context, @NonNull List<T> list, @NonNull List<T> choiceList) {
        this(context, list, choiceList, null);
    }

    public GridAdapter(Context context, @NonNull List<T> list, @NonNull List<T> choiceList, Convert<T, String> convert) {
        this.context = context;
        this.list = list;
        this.choiceList = choiceList;
        this.convert = convert;
    }

    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filer_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridAdapter.ViewHolder holder, final int position) {
        final T t = list.get(position);
        String value = t.toString();
        if (convert != null) value = convert.apply(t);
        holder.line.setText(value);
        holder.line.setBackgroundColor(Color.parseColor("#cccccc"));
        setChoice(holder, t);

        if (listener != null) {
            holder.line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChoiceByClick(holder, t);
                    listener.onClick(position);
                }
            });
        }
    }

    private void setChoice(GridAdapter.ViewHolder holder, T t) {
        if (changeToString(choiceList).contains(t.toString())) {
            holder.line.setBackgroundColor(Color.parseColor("#148927"));
        } else {
            holder.line.setBackgroundColor(Color.parseColor("#cccccc"));
        }
    }

    private void setChoiceByClick(GridAdapter.ViewHolder holder, T t) {
        if (changeToString(choiceList).contains(t.toString())) {
            holder.line.setBackgroundColor(Color.parseColor("#cccccc"));
        } else {
            holder.line.setBackgroundColor(Color.parseColor("#148927"));
        }
    }

    private List<String> changeToString(List<T> list) {
        ArrayList<String> strings = new ArrayList<>();
        for (T t1 : list) {
            strings.add(t1.toString());
        }
        return strings;
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
