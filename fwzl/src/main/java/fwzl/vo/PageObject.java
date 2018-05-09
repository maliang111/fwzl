package fwzl.vo;

import java.io.Serializable;

/**
 * 分页对象
 * @author 马亮
 */
public class PageObject implements Serializable {

    private static final long serialVersionUID = -8733980893210221049L;
    private Object data;

    private Integer totalCount;

    public PageObject() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
