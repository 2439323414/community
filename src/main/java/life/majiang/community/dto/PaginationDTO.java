package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;


    public void setPageination(Integer totalPage, Integer page, Integer size) {


        this.page = page;
        //页面的分页个数
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page-i >0){
                pages.add(0,page-i);
            }
            if(page+i <=totalPage){
                pages.add(page+i);
            }
        }

        //是否显示上一页
        if (page==1){
            showPrevious=false;
        }else{
            showPrevious = true;
        }
        //是否显示下一页
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        //是否显示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage=true;
        }
        //是否最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
