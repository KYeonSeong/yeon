package com.gallery.gallery.customer;



import lombok.Data;

@Data
public class TicketVo {    
    String id;
    String name;        
    int payNum;
    int cash;
    int adultEa;
    int teenEa;
    int kidEa;
    int adult_price;
    int teen_price;
    int kid_price;
    int refundCash;
    int totEa;
    int totAmt;
    int adultAmt;
    int teenAmt;
    int kidAmt;
    int status;
    int pr;
    String location_info;
    String company;
    String tel;    
    String gallery_num;
    String gallery_name;
    String ticketNum;
    String payTime;
    String refundTime;
    String photo_path;
    String tNumber;
    String totAmtStr;
    
   

    public int getTotEa() {
        return adultEa + teenEa + kidEa;
    }   
    
    public int getAdultAmt() {
        return adultEa*adult_price;
    }

    public int getTeenAmt() {
        return teenEa*teen_price;
    }

    public int getKidAmt() {
        return kidEa*kid_price;
    }        

    public int getTotAmt(){
        return getAdultAmt() + getTeenAmt() + getKidAmt();
    }

    

    public String getTotAmtStr(){
        totAmtStr=Integer.toString(getTotAmt());
        return totAmtStr;
    }
}

