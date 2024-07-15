package com.union.mall.system.listener;

import com.alibaba.excel.event.AnalysisEventListener;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public abstract class MyAnalysisEventListener<T> extends AnalysisEventListener<T> {

    private String msg;
    public abstract String getMsg();
}
