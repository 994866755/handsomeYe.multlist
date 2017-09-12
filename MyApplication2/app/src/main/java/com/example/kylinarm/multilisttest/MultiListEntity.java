package com.example.kylinarm.multilisttest;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public interface MultiListEntity<T> {

    public List<T> getMultiListChildData();

    public String getData();

}
