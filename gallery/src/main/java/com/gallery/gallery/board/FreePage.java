package com.gallery.gallery.board;

import lombok.Data;

@Data
public class FreePage {
    int startNo=1, endNo;
    int listSize=12;
    int blockSize=4;
    int startPage=1, endPage;
    int totSize, totPage;
    int nowPage=1;

    String findStr;
    String id;

    public FreePage(){}
    public FreePage(int totSize, int nowPage){
        this.totSize = totSize;
        this.nowPage = nowPage;
        pageCompute();
    }

    public void pageCompute(){
        totPage = (int)Math.ceil(totSize/(double)listSize);
        endNo = listSize * nowPage;
        startNo = endNo - listSize;
        if(endNo > totSize) endNo = totSize;

        endPage = (int)Math.ceil(nowPage/(double)blockSize) * blockSize;
        startPage = endPage - blockSize+1;
        if(endPage>totPage) endPage = totPage;
    } 
}
