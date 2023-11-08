package com.gallery.gallery.customer;



import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.board.FreePage;
import com.gallery.gallery.board.FreeVo;
import com.gallery.gallery.board.ReviewPage;
import com.gallery.gallery.board.ReviewVo;
import com.gallery.gallery.mybatis.MybaFactory;


@Component
public class CustomerDao {
        SqlSession session;
        FreePage page;
        ReviewPage rPage;

    public CustomerDao(){
        session = MybaFactory.getFactory().openSession();
   } 

   //회원정보 최신화용(페이지 이동/새로고침 -> 포인트 최신화)
   public CustomerVo selectCustomer(String id){
        CustomerVo vo = null;                
        session.insert("mypage.selectCustomer",id);
        vo=session.selectOne("mypage.selectCustomer",id);         
        session.commit();              
        return vo;        
    }

    public CustomerVo checkCustomer(String id){
        CustomerVo vo = session.selectOne("mypage.checkCustomer",id);
        return vo;
    
    }

    //예매내역,환불신청내역
    public List<TicketVo> selectTicket(String id){
        List<TicketVo> tList = null;                
        session.insert("mypage.selectTicket",id);
        tList=session.selectList("mypage.selectTicket",id);        
        session.commit();              
        return tList;        
    }

    //예매내역-->상세보기(티켓정보)
    public List<TicketVo> ticketPreview(String id,int payNum,String company){        
        TicketVo vo = new TicketVo();
        List<TicketVo> tList = null;
        vo.setId(id);
        vo.setPayNum(payNum);
        vo.setCompany(company);
        tList=session.selectList("mypage.ticketPreview",vo);                 
        return tList;
    }

    //예매내역-->상세보기(티켓넘버)
    public List<TicketNumberVo> ticketNumber(int payNum){                
        List<TicketNumberVo> nList = null;        
        nList=session.selectList("mypage.ticketNumber",payNum);          
        return nList;
    }

    //예매내역-->환불신청
    public String refund(TicketVo vo){
        String msg="";
        int cnt = 0;        
        int cnt2 = 0;
        TicketVo tVo = new TicketVo();//환불금액 계산용 

        try{
            cnt = session.update("mypage.refund",vo); //ticket상태 2로
            tVo=session.selectOne("mypage.compute",vo);                  
            vo.setCash(tVo.getCash()+tVo.getTotAmt());//환불금액을 회원캐쉬에            
            cnt2 = session.update("mypage.refundCash",vo);             
            if(cnt<1&&cnt2<1){
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

    /* ===================================================================== */

    //내 게시물
    public List<FreeVo> mySelect(FreePage page){
        int totSize = getTotSize(page);                      
        page.setTotSize(totSize);
        page.pageCompute();
        this.page = page;
        List<FreeVo> list = session.selectList("free.mySelect", page);        
        return list;
    }

    public int getTotSize(FreePage page){
        int totSize = session.selectOne("free.totSize", page);
        return totSize;
    }    

    public FreePage getPage(){
        return this.page;
    }


    /* ===================================================================== */

    //회원정보 수정

    public String customerModify(CustomerVo vo){
        String msg="";
        int cnt=0;     
        String pwdCheck = vo.getPwd();
        try{
            if(pwdCheck.equals("")){
                cnt=session.update("mypage.customerModify2",vo);    
            }else{
                cnt=session.update("mypage.customerModify",vo);
            }

            if(cnt<1){
                msg="저장중 오류 발생";
                throw new Exception(msg);
            }else{
                msg="회원정보 수정완료";
                session.commit();
            }

        }catch(Exception ex){
            msg=ex.getMessage();
            session.rollback();           
        }
        return msg;
    }

    //회원탈퇴
    public String deleteCustomer(String id){
        String msg="";
        int cnt=0;
        try{
            session.insert("mypage.customerDelete",id);
            cnt=session.delete("mypage.customerDelete",id);
            System.out.println(cnt);
            if(cnt<1){
                msg="삭제중 오류 발생";
                throw new Exception(msg);
            }
            session.commit();            

        }catch(Exception ex){
            msg=ex.getMessage();
            session.rollback();
        }
        return msg;
    }
    
    
    
    /* ===================================================================== */

    //내 후기게시물
    public List<ReviewVo> myReviewSelect(ReviewPage rPage){
        int totSize = getTotSize(rPage);                      
        rPage.setTotSize(totSize);
        rPage.pageCompute();
        this.rPage = rPage;        
        List<ReviewVo> list = session.selectList("review.myReviewSelect", rPage);        
        return list;
    }

    

    public int getTotSize(ReviewPage rPage){
        int totSize = session.selectOne("review.totSize", rPage);
        return totSize;
    }

    public ReviewPage getReviewPage(){
        return this.rPage;
    }

     /* ===================================================================== */

}