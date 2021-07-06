package com.net.SprintOne.controller.output;
import org.springframework.data.domain.Pageable;
public class AbstractOutput{
    private int currentPage;
    private int totalItems;
    private int count;
    private int pageLimit;
    public int getCurrentPage(){
        return currentPage;
    }
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public int getTotalItems(){
        return totalItems;
    }
    public void setTotalItems(int totalItems){
        this.totalItems = totalItems;
    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getPageLimit(){
        return pageLimit;
    }
    public void setPageLimit(int pageLimit){
        this.pageLimit = pageLimit;
    }
}