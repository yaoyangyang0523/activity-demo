package com.yang.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * explain：分页实体类
 *
 * @author yang
 * @date 2021/1/4
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -5395997221963176643L;
    private List<T> rows;
    private int pageNumber;
    private int pageSize;
    private int total;
    private int totalRow;

    public Page(List<T> rows, int pageNumber, int pageSize, int total) {
        this.rows = rows;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
        this.totalRow = jsTotalRow();
    }

    public Page() {}

    public List<T> getRows() {
        return this.rows;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getTotal() {
        return this.total;
    }

    public int getTotalRow() {
        return this.totalRow;
    }

    public boolean isFirstPage() {
        return this.pageNumber == 1;
    }

    public boolean isLastPage() {
        return this.pageNumber >= this.total;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("pageNumber : ").append(this.pageNumber);
        msg.append("\npageSize : ").append(this.pageSize);
        msg.append("\ntotalPage : ").append(this.total);
        msg.append("\ntotalRow : ").append(this.totalRow);
        return msg.toString();
    }

    /**
     * 计算总页数
     * @return
     */
    private int jsTotalRow() {
        return (this.total % this.pageSize == 0) ? this.total / this.pageSize : this.total / this.pageSize + 1;
    }

    /**
     * 用于查询分页
     * @param obj
     */
    public static void handlePage(JSONObject obj) {
        if (obj == null) {
            obj = new JSONObject();
        }

        int num = 1;
        int size = 10;

        // 对页数处理
        if (obj.get("pageNumber") == null || obj.getInteger("pageNumber") < 1) {
            obj.put("pageNumber", num);
        } else {
            num = obj.getInteger("pageNumber");
        }

        // 对每页记录处理
        if (obj.get("pageSize") == null || obj.getInteger("pageSize") < 1) {
            obj.put("pageSize", size);
        } else {
            size = obj.getInteger("pageSize");
        }

        // 对ORACLE查询分页进行处理
        obj.put("pageStart", (num - 1) * size);
        obj.put("pageEnd", num * size);
    }
}
