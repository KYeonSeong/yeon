package com.gallery.gallery.detailPage;

import lombok.Data;

@Data
public class QnaVo {
    int sno, grp, deep;
    String id, doc, nal, gallery_num;
    
    public QnaVo(){}
    public QnaVo(String id, String gallery_num, int sno){
        this.id = id;
        this.gallery_num = gallery_num;
        this.sno = sno;
    }
}
