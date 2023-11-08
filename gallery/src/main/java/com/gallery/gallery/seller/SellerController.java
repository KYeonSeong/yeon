package com.gallery.gallery.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@RestController
public class SellerController {
    @Autowired
    SellerDao Dao;


   @RequestMapping("/sellerpage")
    public ModelAndView sellerMypage(){
        ModelAndView mv = new ModelAndView();              
        mv.setViewName("/sellerMyPage/sellerIndex");
        return mv;
    }
    //등록한 겔러리
        @RequestMapping("/gallerylist")
    public ModelAndView gallerylist(@SessionAttribute("company") String company){
        ModelAndView mv = new ModelAndView();
        List<gellerylistVo> galleryList = Dao.sellergallerylist(company);
        mv.addObject("tList", galleryList);
        mv.setViewName("/sellerMypage/sellergallerylist");         
        return mv;
    }
    //매출관리
    // @RequestMapping("/sellerData")
    // public ModelAndView sellerData(SellerVo vo){
    //     ModelAndView mv = new ModelAndView();              
    //     mv.setViewName("/sellerMyPage/sellerData");
    //     return mv;
    // }
    //    @RequestMapping("/sellerGellery")
    // public ModelAndView sellergellery(SellerVo vo){
    //     ModelAndView mv = new ModelAndView();              
    //     mv.setViewName("/sellerMyPage/sellerGellery");
    //     return mv;
    // }
    //구매환불관리
        @RequestMapping("/sellerrefund")
    public ModelAndView sellerrefund(SellerVo vo){
        ModelAndView mv = new ModelAndView();              
        mv.setViewName("/sellerMyPage/sellerrefund");
        return mv;
    }
    // //회원정보 수정
    //  @RequestMapping("/sellermodify")
    // public ModelAndView sellermodify(SellerVo vo){
    //     ModelAndView mv = new ModelAndView();              
    //     mv.setViewName("/sellerMyPage/sellerModify");
    //     return mv;
    // }
    //갤러리
     @RequestMapping("/sellerupdate")
    public ModelAndView sellerupdate(SellerVo vo){
        ModelAndView mv = new ModelAndView();              
        mv.setViewName("/sellerMyPage/sellerupdate");
        return mv;
    }
    
}

