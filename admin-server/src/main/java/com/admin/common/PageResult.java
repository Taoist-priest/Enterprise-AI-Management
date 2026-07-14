package com.admin.common;

import lombok.Data;

/**
 * 分页查询结果
 */
@Data
public class PageResult<T> {

    private long total;
    private long current;
    private long size;
    private T records;

    public static <T> PageResult<T> of(long total, long current, long size, T records) {
        PageResult<T> page = new PageResult<>();
        page.setTotal(total);
        page.setCurrent(current);
        page.setSize(size);
        page.setRecords(records);
        return page;
    }
}
