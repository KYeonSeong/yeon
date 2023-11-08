package com.gallery.gallery.center;

import lombok.Data;

@Data
public class Pager {
    int startNo = 1, endNo;
    int listSize = 8;
    int blockSize = 5;
    int startPage = 1, endPage;
    int totSize, totPage;
    int nowPage = 1;
    String id;
    int auth;

    public Pager() {
        pageCompute();
    }

    public Pager(int totSize, int nowPage) {
        this.totSize = totSize;
        this.nowPage = nowPage;
        pageCompute();
    }

    public void setTotSize(int totSize) {
        this.totSize = totSize;
        pageCompute();
    }

    public void pageCompute() {
        totPage = (int) Math.ceil(totSize / (double) listSize);
        startNo = (nowPage - 1) * listSize;
        endNo = startNo + listSize;
        if (endNo > totSize)
            endNo = totSize;
        endPage = (int) Math.ceil(nowPage / (double) blockSize) * blockSize;
        startPage = endPage - blockSize + 1;
        if (endPage > totPage)
            endPage = totPage;
        if (startPage < 1)
            startPage = 1;

    }
}
