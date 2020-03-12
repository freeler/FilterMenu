package com.freeler.flitermenu.listener;

import androidx.annotation.Nullable;

import com.freeler.flitermenu.helper.Filter;


/**
 * 单个Filter值变化监听
 *
 * @author: freeler
 * @Date: 2020/1/13
 */
public interface OnFilterValueChangeListener {
    void changed(@Nullable Filter filter);
}
