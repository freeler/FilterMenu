package com.freeler.flitermenu.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freeler.flitermenu.FilterView;

import java.util.Map;

/**
 * 值变化监听
 *
 * @author: xuzeyang
 * @Date: 2020/1/13
 */
public interface OnValueChangeListener {
    void changed(@Nullable FilterView filterView, @NonNull Map<String, Object> map);
}
