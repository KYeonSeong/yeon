package com.gallery.gallery.customer;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.gallery.gallery.board.FreePage;
import com.gallery.gallery.board.FreeVo;
import com.gallery.gallery.board.ReviewPage;
import com.gallery.gallery.board.ReviewVo;

import jakarta.servlet.http.HttpSession;


@RestController
public class CustomerController {
    @Autowired
    CustomerDao dao;    

    //예매내역
    @RequestMapping("/ticketView")
    public ModelAndView ticketView(@SessionAttribute("id") String id){
        ModelAndView mv = new ModelAndView();        
        List<TicketVo> tList = dao.selectTicket(id);        
        mv.addObject("tList", tList);
        mv.setViewName("/customerMypage/customerTicketView");         
        return mv;
    }

    //내 게시글
    @RequestMapping("/myFreeBoardList")
    public ModelAndView myFreeBoardList(@SessionAttribute("id") String id,FreePage page){        
        ModelAndView mv = new ModelAndView();
        page.setId(id);        
        List<FreeVo> list = dao.mySelect(page);
        mv.addObject("list", list);
        mv.addObject("page", dao.getPage());
        mv.setViewName("/customerMypage/customerMyBoard");
        return mv;        
    }

    //내 후기게시글
    @RequestMapping("/myReviewBoardList")
    public ModelAndView select(@SessionAttribute("id") String id,ReviewPage rPage){
        ModelAndView mv = new ModelAndView();
        rPage.setId(id); 
        List<ReviewVo> list = dao.myReviewSelect(rPage);
        mv.addObject("list", list);
        mv.addObject("page2", dao.getReviewPage());
        mv.setViewName("/customerMypage/customerMyReview");
        return mv;
    }
    



    //마이페이지 메인
    @RequestMapping("/mypage")
    public ModelAndView customerMypage(){
        ModelAndView mv = new ModelAndView();              
        mv.setViewName("/customerMypage/customerIndex");
        return mv;
    }
    
    //회원정보 최신화용(페이지 이동/새로고침 -> 포인트 최신화)
    @RequestMapping("/sectionTop")
    public ModelAndView sectionTop(@SessionAttribute("id") String id){
        ModelAndView mv = new ModelAndView();
        CustomerVo vo = dao.selectCustomer(id);        
        mv.addObject("vo", vo);               
        mv.setViewName("/customerMypage/customerHeader");
        return mv;
    }

    
    //예매내역-->상세보기
    @RequestMapping("/detailView")
    public ModelAndView detailView(@SessionAttribute("id") String id,    
                            @RequestParam("payNum") int payNum,
                            @RequestParam("company") String company){                                          
        ModelAndView mv = new ModelAndView();
        List<TicketVo> tList=dao.ticketPreview(id,payNum,company);
        List<TicketNumberVo> nList=dao.ticketNumber(payNum);  
        mv.addObject("tList", tList);        
        mv.addObject("nList", nList);
        mv.setViewName("/customerMypage/customerDetailView");        
        return mv;
    }

    //예매내역-->환불신청
    @RequestMapping("/refund")
    public String refund(@SessionAttribute("id") String id,TicketVo vo){
        String msg="";                
        vo.setId(id);
        msg=dao.refund(vo);
        return msg;
    } 

      
    //환불신청내역
    @RequestMapping("/refundView")
    public ModelAndView refundView(@SessionAttribute("id") String id){
        ModelAndView mv = new ModelAndView();        
        List<TicketVo> tList = dao.selectTicket(id);
        mv.addObject("tList", tList);
        mv.setViewName("/customerMypage/customerRefundView");         
        return mv;
    }   
   
    //회원정보 수정페이지 이동
    @RequestMapping("/customerModify")
    public ModelAndView customerModify(@SessionAttribute("id") String id){
        ModelAndView mv = new ModelAndView();        
        CustomerVo vo = dao.selectCustomer(id);        
        mv.addObject("vo", vo);
        mv.setViewName("/customerMypage/customerModify");         
        return mv;
    }

    //회원수정 버튼클릭
    @RequestMapping("/customerModifyR")
    public String customerModify(@SessionAttribute("id") String id,CustomerVo vo){
        //vo는 html에서 받아온 정보
        //dbVo는 디비에 들어있는 정보

        String msg="";
        CustomerVo dbVo =dao.checkCustomer(id);        
        String pwd= vo.getPwdView(); 
        String dbPwd = dbVo.getPwd();       
        if(pwd.equals(dbPwd)){
            msg=dao.customerModify(vo);            
        }else{
             msg="기존 비밀번호를 입력해야 수정이 가능합니다.";
        }
        return msg;
    } 

    //회원탈퇴 버튼클릭
    @RequestMapping("/deleteCustomer")
    public String deleteCustomer(@SessionAttribute("id") String id){
        String msg="";
        msg = dao.deleteCustomer(id);        
        return msg;
    }



     //내 게시물 nowPage 세션에 등록
    @RequestMapping("/sessionNowPage")
    public void sessionNowPage(HttpSession session,@ModelAttribute FreePage page){
        session.setAttribute("nowPage", page.getNowPage());                      
    }    

    @RequestMapping("/sessionNowPage2")
    public void sessionNowPage2(HttpSession session,@ModelAttribute ReviewPage page){
        session.setAttribute("nowPage", page.getNowPage());                      
    }    
}