package com.freeler.flitermenu.helper.grid;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freeler.flitermenu.helper.Filter;
import com.freeler.flitermenu.listener.Convert;
import com.freeler.flitermenu.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表多选
 *
 * @author: freeler
 * @Date: 2020/3/12
 */
public class GridListFilter<T> extends Filter<List<T>> {

    private List<T> data = new ArrayList<>();
    private TitleStyle titleStyle;
    private String headName;
    private int spanCount = 4;
    private List<T> choiceData = new ArrayList<>();
    private Convert<T, String> displayConvert;
    private GridAdapter adapter;

    public GridListFilter(Context context) {
        super(context);
    }

    public GridListFilter<T> setOptions(@NonNull List<T> data) {
        this.data = data;
        return this;
    }

    public GridListFilter<T> setHeadName(String headName) {
        this.headName = headName;
        return this;
    }

    public GridListFilter<T> setHeadStyle(TitleStyle titleStyle) {
        this.titleStyle = titleStyle;
        return this;
    }

    public GridListFilter<T> setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        return this;
    }

    @Override
    public Filter<List<T>> setDisplayConvert(Convert<List<T>, String> convert) {
        throw new IllegalArgumentException("please replace with setMultiDisplayConvert method");
    }

    public GridListFilter<T> setMultiDisplayConvert(Convert<T, String> convert) {
        this.displayConvert = convert;
        return this;
    }

    @Override
    public Filter<List<T>> setValue(List<T> value) {
        if (value != null) {
            choiceData.addAll(value);
        }
        return super.setValue(value);
    }

    @Override
    public String getTabName() {
        if (getValue() == null || getValue().isEmpty()) {
            return getTitleName();
        }
        if (getValue().size() == 1 && displayConvert != null) {
            return displayConvert.apply(getValue().get(0));
        }
        return getTitleName() + "（多选）";
    }

    @Override
    public void onOpen() {
        adapter.notifyDataSetChanged();
    }


    @Override
    public View getView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        linearLayout.setPadding(ConvertUtils.dp2Px(20), 0, ConvertUtils.dp2Px(20), 0);

        linearLayout.addView(getHeadView());
        linearLayout.addView(getRecyclerView());
        linearLayout.addView(getButtons());

        return linearLayout;
    }

    private View getHeadView() {
        TextView textView = new TextView(getContext());
        if (headName != null) {
            textView.setText(headName);
        }
        if (titleStyle != null) {
            titleStyle.setStyle(textView);
        } else {
            textView.setTextSize(16);
            textView.setTextColor(Color.parseColor("#222222"));
            textView.setPadding(0, ConvertUtils.dp2Px(20), 0, ConvertUtils.dp2Px(20));
        }
        return textView;
    }

    private RecyclerView getRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridVerticalItemDecoration(spanCount, ConvertUtils.dp2Px(10), ConvertUtils.dp2Px(10)));
        RecyclerView.Adapter adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @SuppressWarnings("unchecked")
    private RecyclerView.Adapter getAdapter() {
        adapter = new GridAdapter(getContext(), data, choiceData, displayConvert);
        adapter.setOnItemClickListener(new GridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                T t = data.get(position);
                if (choiceData.contains(t)) {
                    choiceData.remove(t);
                } else {
                    choiceData.add(t);
                }
            }
        });
        return adapter;
    }

    private View getButtons() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ConvertUtils.dp2Px(50)));
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ConvertUtils.dp2Px(40), 1.0f);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, ConvertUtils.dp2Px(40), 1.0f);
        lp2.setMarginStart(ConvertUtils.dp2Px(10));

        linearLayout.addView(resetView(), lp);
        linearLayout.addView(confirmView(), lp2);
        return linearLayout;
    }

    private View resetView() {
        TextView resetTv = new TextView(getContext());
        resetTv.setGravity(Gravity.CENTER);
        resetTv.setBackgroundColor(Color.parseColor("#cccccc"));
        resetTv.setText("不限条件");
        resetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceData.clear();
                changeValueWithHide(choiceData);
            }
        });
        return resetTv;
    }

    private View confirmView() {
        TextView confirmTv = new TextView(getContext());
        confirmTv.setGravity(Gravity.CENTER);
        confirmTv.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        confirmTv.setBackgroundColor(Color.parseColor("#148927"));
        confirmTv.setText("确认");
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValueWithHide(choiceData);
            }
        });
        return confirmTv;
    }


    public interface TitleStyle {
        void setStyle(TextView textView);
    }


}
