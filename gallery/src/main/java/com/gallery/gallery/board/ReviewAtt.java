package com.gallery.gallery.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewAtt {
    int sno, pSno;
    String oriFile, sysFile;

    public ReviewAtt(){}
    public ReviewAtt(int sno, String oriFile, String sysFile){
        this.pSno = sno;
        this.oriFile = oriFile;
        this.sysFile = sysFile;
    }
}
