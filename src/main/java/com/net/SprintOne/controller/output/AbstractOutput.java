package com.net.SprintOne.controller.output;
import org.springframework.data.domain.Pageable;
public class AbstractOutput{
    private int page;
    private int totalPage;
    public int getPage(){
        return page;
    }
    public void setPage(int page){
        this.page = page;
    }
    public int getTotalPage(){
        return totalPage;
    }
    public void setTotalPage(int totalPage){
        this.totalPage = totalPage;
    }
}