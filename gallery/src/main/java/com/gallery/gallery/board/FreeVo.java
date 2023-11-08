package com.gallery.gallery.board;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeVo {
    int sno, grp, deep, seq, hit, attCnt, pSno, freeBoardSno;
    String id, nal, doc, subject;
    String freePwd;

    List<FreeAtt> attFiles;
    List<FreeReply> replies;
    String[] delFile;

    public FreeVo(){}
    public FreeVo(int sno, String subject){
        this.sno = sno;
        this.subject = subject;
    }
}
