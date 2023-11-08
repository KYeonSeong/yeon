package com.gallery.gallery.detailPage;

import lombok.Data;

@Data
public class DetailPageVo {
    String ticket_info;
    String agency_notice;
    String use_info;
    String photo_path;
    String notice;
    String location;
    String location_info;
    String seller_info;
    String gallery_name;
    String gallery_num;
    String name;
    String tel;
    String email;
    String id;
    String payTime;
    String company;

    
    int payNum;
    int adult_price;
    int adultEa;
    int adultAmt;
    int teen_price;
    int teenEa;
    int teenAmt;
    int kid_price;
    int kidEa;
    int kidAmt;
    int totAmt;
    int totEa;
    int cash;

    public DetailPageVo(){}
    public DetailPageVo(String gallery_num){
        this.gallery_num = gallery_num;
    }
}
