package com.gallery.gallery.detailPage;

import lombok.Data;

@Data
public class PayVo {
    String payNum, gallery_num, id, payTime;
    int adultEa, teenEa, kidEa, adultAmt, teenAmt, kidAmt;
    int adult_price, teen_price, kid_price;
    int ticketEa;
    int totAmt;

    public PayVo(){}

    
}
