package com.freeler.flitermenu.helper;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freeler.flitermenu.view.DropDownMenu;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 筛选工具
 *
 * @author: freeler
 * @Date: 2020/1/11
 */
public class FilterViewHelper {

    private WeakReference<Context> context;
    private WeakReference<DropDownMenu> dropDownMenuWeakReference;
    private List<Filter> filterViews;
    private boolean isDefaultChanged;
    private OnValueChangeListener onValueChangeListener;

    private FilterViewHelper(Builder builder) {
        this.context = new WeakReference<>(builder.downMenuWeakReference.get().getContext());
        this.dropDownMenuWeakReference = builder.downMenuWeakReference;
        this.filterViews = builder.filterViews == null ? new ArrayList<Filter>() : builder.filterViews;
        this.isDefaultChanged = builder.isDefaultChanged;
        this.onValueChangeListener = builder.onValueChangeListener;
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        final DropDownMenu dropDownMenu = dropDownMenuWeakReference.get();

        final Map<String, Object> menuValues = new HashMap<>();
        List<String> titles = new ArrayList<>();
        List<View> views = new ArrayList<>();

        for (Filter filter : filterViews) {
            titles.add(filter.getTabName());
            views.add(filter.getView());
            Object value = filter.getValue();
            Map serverValue = filter.getConvertValue();
            if (value != null && serverValue != null && !serverValue.isEmpty())
                menuValues.putAll(serverValue);

            filter.setMenuHelper(this);
            filter.setOnChangeListener(new Filter.OnFilterValueChangeListener() {
                @Override
                public void changed(@Nullable Filter filter) {
                    dropDownMenu.setTabText(Objects.requireNonNull(filter).getTabName());
                    menuValues.putAll(filter.getConvertValue());
                    activateChanged(filter, menuValues);
                }
            });

        }

        // isDefaultChanged为true时，初始话触发valueChange
        if (isDefaultChanged) activateChanged(null, menuValues);

        dropDownMenu.setDropDownMenu(titles, views);
        dropDownMenu.setOnMenuShowListener(new DropDownMenu.OnMenuShowListener() {
            @Override
            public void showAtPosition(int position) {
                filterViews.get(position).onOpen();
            }
        });


    }


    private void activateChanged(Filter filterView, Map<String, Object> map) {
        if (onValueChangeListener != null) {
            onValueChangeListener.changed(filterView, map);
        }
    }

    public void hide() {
        DropDownMenu dropDownMenu = dropDownMenuWeakReference.get();
        if (dropDownMenu != null && dropDownMenu.isShowing()) dropDownMenu.closeMenu();
    }

    public static class Builder {
        private WeakReference<DropDownMenu> downMenuWeakReference;
        private List<Filter> filterViews = new ArrayList<>();
        private boolean isDefaultChanged;
        private OnValueChangeListener onValueChangeListener;

        public Builder(DropDownMenu dropDownMenu) {
            this.downMenuWeakReference = new WeakReference<>(dropDownMenu);
        }

        /**
         * 添加筛选项
         *
         * @param view Filter，自定义样式需自行继承
         */
        public Builder addFilterView(Filter view) {
            if (view != null) filterViews.add(view);
            return this;
        }

        public Builder addFilterViews(@NonNull List<Filter> views) {
            filterViews.addAll(views);
            return this;
        }

        /**
         * 是否需要自动触发默认筛选
         *
         * @param isDefaultChanged true：触发
         */
        public Builder withDefaultChanged(boolean isDefaultChanged) {
            this.isDefaultChanged = isDefaultChanged;
            return this;
        }

        /**
         * 筛选项的值发生变化时会触发，接口回调会返回一个筛选过后的Map<String,Object>，
         * 每个筛选条件对应Key，筛选值对应Value
         *
         * @param onValueChangeListener 筛选项的值发生变化监听的接口回调
         */
        public Builder addListener(OnValueChangeListener onValueChangeListener) {
            this.onValueChangeListener = onValueChangeListener;
            return this;
        }

        /**
         * 构造FilterViewHelper对象
         */
        public FilterViewHelper build() {
            return new FilterViewHelper(this);
        }

    }

    public interface OnValueChangeListener {
        void changed(@Nullable Filter filterView, @NonNull Map<String, Object> map);
    }
}

