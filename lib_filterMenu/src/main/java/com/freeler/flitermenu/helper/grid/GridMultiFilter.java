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

import com.freeler.flitermenu.R;
import com.freeler.flitermenu.helper.Filter;
import com.freeler.flitermenu.listener.Convert;
import com.freeler.flitermenu.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 网格列表多选
 *
 * @author: freeler
 * @Date: 2020/3/12
 */
public class GridMultiFilter<T> extends Filter<List<T>> {

    private static final float defaultTextSize = 16f;
    /**
     * 左边距
     */
    private static final int paddingStart = ConvertUtils.dp2Px(20);
    /**
     * 右边距
     */
    private static final int paddingEnd = ConvertUtils.dp2Px(20);
    /**
     * 列表项垂直间距
     */
    private static final int itemVerticalSpace = ConvertUtils.dp2Px(15);
    /**
     * 列表项水平间距
     */
    private static final int itemHorizontalSpace = ConvertUtils.dp2Px(15);
    /**
     * 分割线高度
     */
    private static final int dividerHeight = ConvertUtils.dp2Px(0.5f);
    /**
     * 分割线距顶部高度
     */
    private static final int dividerMarginTop = ConvertUtils.dp2Px(15);
    /**
     * 底部高度
     */
    private static final int bottomHeight = ConvertUtils.dp2Px(60);
    /**
     * 按钮高度
     */
    private static final int buttonHeight = ConvertUtils.dp2Px(40);
    /**
     * 按钮间距
     */
    private static final int buttonMargin = ConvertUtils.dp2Px(15);


    /**
     * 自定义标题样式
     */
    private TitleStyle titleStyle;
    /**
     * 标题名称
     */
    private String headName;
    /**
     * 标题显示转换
     */
    private Convert<T, String> displayConvert;
    /**
     * 一行最多显示的数量
     */
    private int spanCount = 4;
    /**
     * 筛选项数据集合
     */
    private List<T> data = new ArrayList<>();
    /**
     * 记录已选中的筛选项集合
     */
    private List<T> choiceData = new ArrayList<>();
    /**
     * adapter
     */
    private GridMultiAdapter<T> adapter;


    public GridMultiFilter(Context context) {
        super(context);
    }

    /**
     * 设置筛选项数据
     *
     * @param data 数据源
     */
    public GridMultiFilter<T> setOptions(@NonNull List<T> data) {
        this.data = data;
        return this;
    }

    /**
     * 设置标题名称
     *
     * @param headName 标题描述
     */
    public GridMultiFilter<T> setHeadName(String headName) {
        this.headName = headName;
        return this;
    }

    /**
     * 设置自定义标题样式
     *
     * @param titleStyle 标题回调
     */
    public GridMultiFilter<T> setHeadStyle(TitleStyle titleStyle) {
        this.titleStyle = titleStyle;
        return this;
    }

    /**
     * 设置一行最多显示多少个
     *
     * @param spanCount 最多显示的个数，默认是4
     */
    public GridMultiFilter<T> setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        return this;
    }

    @Override
    public Filter<List<T>> setDisplayConvert(Convert<List<T>, String> convert) {
        throw new IllegalArgumentException("please replace with setMultiDisplayConvert method");
    }

    public GridMultiFilter<T> setMultiDisplayConvert(Convert<T, String> convert) {
        this.displayConvert = convert;
        return this;
    }

    @Override
    public Filter<List<T>> setValue(List<T> value) {
        if (value != null) {
            choiceData.addAll(value);
            distinctBy(choiceData);
        }
        return super.setValue(value);
    }

    private void distinctBy(List<T> data) {
        HashSet<String> set = new HashSet<>();
        List<T> list = new ArrayList<>();
        for (T datum : data) {
            String key = datum.toString();
            if (set.add(key)) {
                list.add(datum);
            }
        }
        data.clear();
        data.addAll(list);
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
        List<T> value = getValue();
        choiceData.clear();
        if (value != null) {
            choiceData.addAll(value);
            distinctBy(choiceData);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public View getView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));

        linearLayout.addView(getHeadView());
        linearLayout.addView(getRecyclerView());
        linearLayout.addView(getDividerView());
        linearLayout.addView(getButtons());

        return linearLayout;
    }

    /**
     * 标题
     */
    private View getHeadView() {
        TextView textView = new TextView(getContext());
        textView.setPadding(paddingStart, ConvertUtils.dp2Px(20), paddingEnd, ConvertUtils.dp2Px(20));
        textView.setTextSize(defaultTextSize);
        textView.setTextColor(Color.parseColor("#535757"));
        if (headName != null) {
            textView.setText(headName);
        }
        if (titleStyle != null) {
            titleStyle.setStyle(textView);
        }
        return textView;
    }

    /**
     * 筛选项
     */
    private RecyclerView getRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setPadding(paddingStart, 0, paddingEnd, 0);
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, itemVerticalSpace, itemHorizontalSpace));
        RecyclerView.Adapter adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @SuppressWarnings("unchecked")
    private RecyclerView.Adapter getAdapter() {
        adapter = new GridMultiAdapter(getContext(), data, choiceData, displayConvert);
        adapter.setOnItemClickListener(new GridMultiAdapter.OnItemClickListener() {
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

    /**
     * 分割线
     */
    private View getDividerView() {
        View view = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dividerHeight);
        layoutParams.setMargins(0, dividerMarginTop, 0, 0);
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(Color.parseColor("#EEEEEE"));
        return view;
    }

    /**
     * 底部按钮
     */
    private View getButtons() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, bottomHeight));
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setPadding(paddingStart, 0, paddingEnd, 0);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, buttonHeight, 1.0f);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, buttonHeight, 1.0f);
        lp2.setMarginStart(buttonMargin);

        linearLayout.addView(resetView(), lp);
        linearLayout.addView(confirmView(), lp2);
        return linearLayout;
    }

    /**
     * 重置按钮
     */
    private View resetView() {
        TextView resetTv = new TextView(getContext());
        resetTv.setGravity(Gravity.CENTER);
        resetTv.setTextColor(Color.parseColor("#A1A2A4"));
//        resetTv.setBackgroundColor(Color.parseColor("#F5F5F5"));
        resetTv.setBackgroundResource(R.drawable.corner_gray_radius_2);
        resetTv.setText("不限条件");
        resetTv.setTextSize(defaultTextSize);
        resetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValueWithHide(null);
            }
        });
        return resetTv;
    }

    /**
     * 确认按钮
     */
    private View confirmView() {
        TextView confirmTv = new TextView(getContext());
        confirmTv.setGravity(Gravity.CENTER);
        confirmTv.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
//        confirmTv.setBackgroundColor(Color.parseColor("#00AE66"));
        confirmTv.setBackgroundResource(R.drawable.corner_green_radius_2);
        confirmTv.setText("确定");
        confirmTv.setTextSize(defaultTextSize);
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<T> objects = new ArrayList<>(choiceData);
                changeValueWithHide(objects);
            }
        });
        return confirmTv;
    }


    public interface TitleStyle {
        void setStyle(TextView textView);
    }


}
