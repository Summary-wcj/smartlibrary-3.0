package net.unifound.smartlibrary.common.result;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * 分页结果对象,这里以layui框架的table为标准
 */
public class PageResult<T> {
    //总条数
    private long total;
    //当前页
    private long current;
    //总页码
    private long pages;
    //每页多少条
    private long size;
    //是否有上一页
    private boolean hasPrevious;
    //是否有下一页
    private boolean hasNext;
    //当前数据
    private List<T> data;

    public PageResult() {
    }

    public PageResult(List<T> rows) {
        this.data = rows;
        this.total = rows.size();
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.data = rows;
    }
    public PageResult(Page<T> page, List<T> rows) {
        this.total = page.getTotal();
        this.current=page.getCurrent();
        this.pages=page.getPages();
        this.size=page.getSize();
        this.hasPrevious=page.hasPrevious();
        this.hasNext=page.hasNext();
        this.data = rows;
    }
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.total = data.size();
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
