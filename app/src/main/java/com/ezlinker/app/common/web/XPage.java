package com.ezlinker.app.common.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wangwenhai
 * @date 2020/2/21
 * File description: 自定义MongoDb分页,为了结合MybatisPlus的分页插件功能
 */
public class XPage<T> implements IPage<T> {
    private List<T> list;
    private long total;
    private List<OrderItem> orders;
    private Pageable pageable;

    public XPage(List<T> list, long total, List<OrderItem> orders, Pageable pageable) {
        this.list = list;
        this.total = total;
        this.orders = orders;
        this.pageable = pageable;
    }

    @Override
    public List<OrderItem> orders() {
        return orders;
    }

    @Override
    public List<T> getRecords() {
        return list;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        return this;
    }

    @Override
    public long getSize() {
        return list.size();
    }

    @Override
    public IPage<T> setSize(long size) {
        return this;
    }

    @Override
    public long getCurrent() {
        return pageable.getPageNumber();
    }

    @Override
    public IPage<T> setCurrent(long current) {
        return this;
    }
}