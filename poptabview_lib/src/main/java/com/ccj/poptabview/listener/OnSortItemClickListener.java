package com.ccj.poptabview.listener;

import java.util.List;

/**
 * 复杂筛选的回调
 * Created by chenchangjun on 17/7/7.
 */

public interface OnSortItemClickListener {
    /**
     * 筛选分类、商城点击
     */
    void onSortItemClick(int position, List<Integer> filterTabBeen);
}
