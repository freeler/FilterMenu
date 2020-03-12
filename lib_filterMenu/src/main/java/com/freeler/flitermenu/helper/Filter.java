package com.freeler.flitermenu.helper;

import android.content.Context;
import android.view.View;

import com.freeler.flitermenu.listener.Convert;
import com.freeler.flitermenu.listener.OnFilterValueChangeListener;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * 给View添加规则
 *
 * @author: freeler
 * @Date: 2020/1/13
 */
public abstract class Filter<T> {

    private WeakReference<Context> context;
    private String titleName;
    private T value;
    private FilterViewHelper helper;
    private OnFilterValueChangeListener onChangeListener;
    private Convert<T, String> displayConvert;
    private Convert<T, Map<String, Object>> valueConvert;

    protected Filter(Context context) {
        this.context = new WeakReference<>(context);
    }

    protected Context getContext() {
        return context.get();
    }

    public Filter<T> setTitleName(String titleName) {
        this.titleName = titleName;
        return this;
    }

    public String getTitleName() {
        return titleName;
    }

    public Filter<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public T getValue() {
        return value;
    }

    /**
     * value的转换，这是给用户看的内容
     * 例如：1 convert后是“男”，表示value的定义是1，用户实际看到的显示是“男”
     */
    public Filter<T> setDisplayConvert(Convert<T, String> convert) {
        this.displayConvert = convert;
        return this;
    }

    protected Convert<T, String> getDisplayConvert() {
        return displayConvert;
    }

    /**
     * 返回筛选项标题的字符串
     */
    public String getTabName() {
        if (value == null) {
            return titleName;
        }
        if (displayConvert != null) {
            return displayConvert.apply(value);
        }
        return value.toString();
    }

    /**
     * value的转换，这是用于上传的
     * 例如：convert后是Map<Sting,Integer>("gender",1)，表示上传时候key为"gender"，value为1
     */
    public Filter<T> setValueConvert(Convert<T, Map<String, Object>> convert) {
        this.valueConvert = convert;
        return this;
    }

    public Map<String, Object> getConvertValue() {
        if (valueConvert != null) {
            return valueConvert.apply(value);
        }
        return null;
    }

    protected void changeValueWithHide(T value) {
        changeValue(value);
        menuHide();
    }

    public void setMenuHelper(FilterViewHelper helper) {
        this.helper = helper;
    }

    private void menuHide() {
        if (helper != null) {
            helper.hide();
        }
    }

    /**
     * 设置valueChange接口
     */
    public void setOnChangeListener(OnFilterValueChangeListener onFilterValueChangeListener) {
        this.onChangeListener = onFilterValueChangeListener;
    }

    /**
     * 触发valueChange监听
     */
    protected void changeValue(T value) {
        if (onChangeListener != null) {
            setValue(value);
            onChangeListener.changed(this);
        }
    }

    /**
     * 筛选项展开时触发
     */
    public abstract void onOpen();

    /**
     * 筛选项展开时显示的View
     */
    public abstract View getView();


}
