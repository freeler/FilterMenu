package com.freeler.flitermenu.listener;

/**
 * @author: xuzeyang
 * @Date: 2020/1/13
 */
public interface Convert<T, U> {
    U apply(T t);
}
