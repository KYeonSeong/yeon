package com.gallery.gallery.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReply {
    int sno, grp;
    String id, nal, doc;

    public ReviewReply(){}
    public ReviewReply(int sno){
        this.grp = sno;
    }
}
