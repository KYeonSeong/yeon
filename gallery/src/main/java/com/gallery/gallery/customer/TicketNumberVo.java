package com.gallery.gallery.customer;

import lombok.Data;

@Data
public class TicketNumberVo {
    int sno;
    int payNum;
    String gallery_num;
    String tNum;



    public String getSno(){
        return String.format("%03d",sno);
    }

    public String getPayNum(){
        return String.format("%03d",payNum);        
    }   

    public String getTNum(){
        return this.gallery_num+"-"+getPayNum()+"-"+getSno();
    }
}
