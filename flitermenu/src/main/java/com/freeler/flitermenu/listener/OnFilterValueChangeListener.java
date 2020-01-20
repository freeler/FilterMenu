package com.freeler.flitermenu.listener;

import androidx.annotation.Nullable;

import com.freeler.flitermenu.FilterView;


/**
 * 值变化监听
 *
 * @author: xuzeyang
 * @Date: 2020/1/13
 */
public interface OnFilterValueChangeListener {
    void changed(@Nullable FilterView filterView);
}
