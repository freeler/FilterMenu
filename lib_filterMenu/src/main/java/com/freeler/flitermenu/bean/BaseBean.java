package com.freeler.flitermenu.bean;

/**
 * @author: xuzeyang
 * @Date: 2020/3/12
 */
public class BaseBean<J, K> {

    private J t1;
    private K t2;

    public BaseBean(J t1, K t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public J getT1() {
        return t1;
    }

    public K getT2() {
        return t2;
    }
}
