package com.gallery.gallery.detailPage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.gallery.gallery.board.ReviewAtt;
import com.gallery.gallery.board.ReviewVo;
import com.gallery.gallery.customer.CustomerDao;
import com.gallery.gallery.customer.CustomerVo;

import jakarta.servlet.http.HttpSession;

@RestController
public class DetailPageController {
    @Autowired
    DetailPageDao dao;
    @Autowired
    CustomerDao cDao;

    @RequestMapping("/myDetailInfo")
    public ModelAndView information(
            HttpSession session, 
            @ModelAttribute DetailPageVo vo, 
            @SessionAttribute("id") String id){
        ModelAndView mv = new ModelAndView();        
        session.setAttribute("adultEa", vo.getAdultEa());
        session.setAttribute("adultAmt", vo.getAdultAmt());
        session.setAttribute("teenEa", vo.getTeenEa());
        session.setAttribute("teenAmt", vo.getTeenAmt());
        session.setAttribute("kidEa", vo.getKidEa());
        session.setAttribute("kidAmt", vo.getKidAmt());      
        session.setAttribute("totAmt", vo.getTotAmt());
        session.setAttribute("totEa", vo.getTotEa());
        session.setAttribute("gallery_num", vo.getGallery_num());        
        session.setAttribute("company", vo.getCompany());        
        List<DetailPageVo> gList = dao.galleryInfo(vo);
        CustomerVo cVo = dao.customerInfo(id);               
        
        mv.addObject("gList", gList);
        mv.addObject("cVo", cVo);
        mv.setViewName("detailpage/payment");
        return mv;
    }

   
    @RequestMapping("/detailInfo")
    public ModelAndView information(@RequestParam("gallery_num") String gallery_num){
        ModelAndView mv = new ModelAndView();
        DetailPageVo vo = dao.info(gallery_num);
        mv.addObject("vo", vo);
        mv.setViewName("detailpage/detailinfo");
        return mv;
    }

    @RequestMapping("/paymentUpdate")
    public ModelAndView paymentUpdate(@SessionAttribute("id") String id){
        ModelAndView mv = new ModelAndView();
        CustomerVo vo = cDao.selectCustomer(id);
        mv.addObject("vo", vo);               
        mv.setViewName("detailpage/payment");
        return mv;
    }

      // 후기
    @RequestMapping("/detailReview") 
    public ModelAndView review(String gallery_num){
        ModelAndView mv = new ModelAndView();
        List<ReviewVo> reviewList = dao.reviewList(gallery_num);
        for (ReviewVo review : reviewList) {
            List<ReviewAtt> attFiles = dao.attFiles(review.getSno()); // 해당 review의 attFiles 가져옴
            review.setAttFiles(attFiles); // review 객체에 attFiles 설정
        }
        mv.addObject("reviewList", reviewList);
        mv.setViewName("detailpage/detailreview");
        return mv;
    }



    // QnA
    @RequestMapping("/detailQna")
    public ModelAndView QNA(String gallery_num){
        ModelAndView mv = new ModelAndView();
        List<QnaVo> qnaList = dao.qnaList(gallery_num);
        mv.addObject("qnaList", qnaList);
        mv.setViewName("detailpage/detailqna");
        return mv;
    }

    // QnA 등록
    @RequestMapping(value="/qnaRegister", method=RequestMethod.POST)
    public String qnaRegister(@ModelAttribute QnaVo qnaVo){
        String msg = "";
        msg = dao.qnaRegister(qnaVo);
        return msg;
    }

    // QnA 답변 등록
    @RequestMapping(value="/qnaAnswer", method=RequestMethod.POST)
    public String qnaAnswer(@ModelAttribute QnaVo qnaVo){
        String msg = "";
        msg = dao.qnaAnswer(qnaVo);
        return msg;
    }

     // 장소안내
     @RequestMapping("/detailLocation") 
     public ModelAndView location(String gallery_num){
         ModelAndView mv = new ModelAndView();
         DetailPageVo vo = dao.info(gallery_num);
         mv.addObject("vo", vo);
         mv.setViewName("detailpage/detailLocation");
         return mv;
     }

     // 환불규정
    @RequestMapping("/detailRefund") 
    public ModelAndView refund(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("detailpage/detailrefund");
        return mv;
    }

}
