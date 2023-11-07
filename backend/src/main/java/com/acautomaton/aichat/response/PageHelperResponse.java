package com.acautomaton.aichat.response;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHelperResponse {
    private Boolean isFirstPage;
    private Boolean isLastPage;
    private Integer pageNum;
    private Integer pageSize;
    private Integer prePage;
    private Integer nextPage;
    private Integer pages;
    private List<?> records;

    public <T> PageHelperResponse(PageInfo<T> pageInfo) {
        this.isFirstPage = pageInfo.isIsFirstPage();
        this.isLastPage = pageInfo.isIsLastPage();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.prePage = pageInfo.getPrePage();
        this.nextPage = pageInfo.getNextPage();
        this.pages = pageInfo.getPages();
        this.records = pageInfo.getList();
    }
}
