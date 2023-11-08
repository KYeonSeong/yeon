package com.gallery.gallery.pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayVo {
    String gallery_num, id, payTime;
    int adultEa,teenEa,kidEa,adultAmt,teenAmt,kidAmt;
    int adult_price,teen_price,kid_price;    
    int totAmt,totEa;
    int payNum;

    public int getTotEa() {
        return adultEa + teenEa + kidEa;
    }   
}
