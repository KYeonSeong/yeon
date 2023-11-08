package com.gallery.gallery.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeAtt {
    int sno, pSno;
    String oriFile, sysFile;

    public FreeAtt(){}
    public FreeAtt(int sno, String oriFile, String sysFile){
        this.pSno = sno;
        this.oriFile = oriFile;
        this.sysFile = sysFile;
    }
}
