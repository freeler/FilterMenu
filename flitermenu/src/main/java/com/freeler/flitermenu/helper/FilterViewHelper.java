package com.freeler.flitermenu.helper;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freeler.flitermenu.FilterMenu;
import com.freeler.flitermenu.listener.OnValueChangeListener;
import com.freeler.flitermenu.FilterView;
import com.freeler.flitermenu.listener.OnFilterValueChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 筛选工具方法
 *
 * @author: xuzeyang
 * @Date: 2020/1/11
 */
public class FilterViewHelper {

    private FilterMenu menu;
    private List<FilterView> filterViews;
    private boolean isDefaultChanged;
    private OnValueChangeListener onValueChangeListener;

    private FilterViewHelper(Builder builder) {
        this.menu = builder.menu;
        this.filterViews = builder.filterViews == null ? new ArrayList<FilterView>() : builder.filterViews;
        this.isDefaultChanged = builder.isDefaultChanged;
        this.onValueChangeListener = builder.onValueChangeListener;
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        final Map<String, Object> menuValues = new HashMap<>();
        List<String> titles = new ArrayList<>();
        List<View> views = new ArrayList<>();

        for (FilterView filterView : filterViews) {
            titles.add(filterView.getDisplayValue());
            views.add(filterView.getView());
            Object value = filterView.getValue();
            Map serverValue = filterView.getServerValue();
            if (value != null && serverValue != null && !serverValue.isEmpty())
                menuValues.putAll(serverValue);

            filterView.setMenuHelper(this);
            filterView.setOnFilterValueChangeListener(new OnFilterValueChangeListener() {
                @Override
                public void changed(@Nullable FilterView filterView) {
                    menu.setTabText(Objects.requireNonNull(filterView).getDisplayValue());
                    menuValues.putAll(filterView.getServerValue());
                    activateChanged(filterView, menuValues);
                }
            });

        }

        // isDefaultChanged为true时，初始话触发valueChange
        if (isDefaultChanged) activateChanged(null, menuValues);

        menu.setMenu(titles, views);
        menu.setOnMenuShowListener(new FilterMenu.OnMenuShowListener() {
            @Override
            public void showAtPosition(int position) {
                filterViews.get(position).onOpen();
            }
        });


    }

    private void activateChanged(FilterView filterView, Map<String, Object> map) {
        if (onValueChangeListener != null) {
            onValueChangeListener.changed(filterView, map);
        }
    }

    public void hide() {
        if (menu.isShowing()) menu.closeMenu();
    }

    public static class Builder {
        private FilterMenu menu;
        private List<FilterView> filterViews = new ArrayList<>();
        private boolean isDefaultChanged;
        private OnValueChangeListener onValueChangeListener;

        public Builder(FilterMenu menu) {
            this.menu = menu;
        }

        /**
         * 添加筛选项
         *
         * @param view FilterView，自定义样式需自行继承
         */
        public Builder addFilterView(FilterView view) {
            if (view != null) filterViews.add(view);
            return this;
        }

        public Builder addFilterViews(@NonNull List<FilterView> views) {
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
        public Builder onValueChange(OnValueChangeListener onValueChangeListener) {
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


}
