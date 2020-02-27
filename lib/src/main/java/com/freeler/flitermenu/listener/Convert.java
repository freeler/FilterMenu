package com.freeler.flitermenu.listener;

/**
 * 装换器
 *
 * @author: freeler
 * @Date: 2020/1/13
 */
public interface Convert<T, U> {
    U apply(T t);
}
