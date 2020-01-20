package com.freeler.flitermenu;

import android.content.Context;
import android.view.View;

import com.freeler.flitermenu.helper.FilterViewHelper;
import com.freeler.flitermenu.listener.Convert;
import com.freeler.flitermenu.listener.OnFilterValueChangeListener;

import java.util.Map;

/**
 * @author: xuzeyang
 * @Date: 2020/1/13
 */
public abstract class FilterView<T> {

    private Context context;
    private String titleName;
    private T value;
    private FilterViewHelper helper;
    private OnFilterValueChangeListener onFilterValueChangeListener;
    private Convert<T, String> displayConvert;
    private Convert<T, Map<String, Object>> valueToServiceConvert;

    protected FilterView(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public FilterView<T> setTitleName(String titleName) {
        this.titleName = titleName;
        return this;
    }

    public FilterView<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public T getValue() {
        return value;
    }

    public FilterView<T> setDisplayConvert(Convert<T, String> convert) {
        this.displayConvert = convert;
        return this;
    }

    public Convert<T, String> getDisplayConvert() {
        return displayConvert;
    }

    public String getDisplayValue() {
        if (value == null) {
            return titleName;
        }
        if (displayConvert != null) {
            return displayConvert.apply(value);
        }
        return value.toString();
    }

    public FilterView<T> setValueToService(Convert<T, Map<String, Object>> convert) {
        this.valueToServiceConvert = convert;
        return this;
    }

    public Map<String, Object> getServerValue() {
        if (valueToServiceConvert != null) {
            return valueToServiceConvert.apply(value);
        }
        return null;
    }

    public void setMenuHelper(FilterViewHelper helper) {
        this.helper = helper;
    }

    public FilterViewHelper getMenuHelper() {
        return helper;
    }

    public void setOnFilterValueChangeListener(OnFilterValueChangeListener onFilterValueChangeListener) {
        this.onFilterValueChangeListener = onFilterValueChangeListener;
    }

    public OnFilterValueChangeListener getOnFilterValueChangeListener() {
        return onFilterValueChangeListener;
    }

    protected boolean isValueChange(T value1, T value2) {
        return value1 != value2;
    }

    public abstract void onOpen();


    public abstract View getView();


}
