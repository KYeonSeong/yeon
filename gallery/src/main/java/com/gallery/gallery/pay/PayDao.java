package com.gallery.gallery.pay;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.customer.TicketVo;
import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class PayDao {
    SqlSession session;

    public PayDao(){
        session = MybaFactory.getFactory().openSession();
    }
    
    // public List<PayVo> getUserPayList(String id){
    //     List<PayVo> userPayList = session.selectList("pay.userPayList", id);
    //     return userPayList;
    // }

    public String realPayment (String id, TicketVo vo){        
        String msg="";
        int cnt = 0;
        int cntCash = 0;
        int cnt2 = 0;
        int cnt3 = 0;
        int i;
        int payNum = session.selectOne("pay.getTicketSerial", "i");
        vo.setPayNum(payNum);
        vo.setId(id);
        vo.setCash(vo.getPr());        
        
        
        try{

            cnt = session.insert("pay.paymentR",vo);//결제
            cntCash = session.update("pay.cashUpdate",vo);//포인트 차감
            for(i=0;i<vo.getTotEa();i++){
                cnt2 = session.insert("pay.ticketNumber",vo); //티켓넘버생성               
            }
            cnt3 = session.insert("pay.createTicket",vo); //티켓생성
            if(cnt<1&&cnt2<1&&cnt3<1){
                msg="저장중 오류 발생";
                throw new Exception(msg);
            }
            session.commit();

        }catch(Exception ex){
            msg=ex.getMessage();
            session.rollback();           
        }

        return msg;
    }
}
