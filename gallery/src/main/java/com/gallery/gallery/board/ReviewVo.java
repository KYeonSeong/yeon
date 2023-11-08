package com.gallery.gallery.board;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewVo {
    int sno, grp, hit, payNum, replCnt, pSno;
    String id, nal, doc, subject, gallery_num;

    List<ReviewAtt> attFiles;
    List<ReviewReply> replies;
    String[] delFile;

    public ReviewVo(){}
    public ReviewVo(int sno, String subject){
        this.sno = sno;
        this.subject = subject;
    }
}
