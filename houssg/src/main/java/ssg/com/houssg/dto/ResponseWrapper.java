package ssg.com.houssg.dto;

import java.util.List;

public class ResponseWrapper<T> {
    private List<T> data;
    private int totalCount;

    public ResponseWrapper(List<T> data, int totalCount) {
        this.data = data;
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
