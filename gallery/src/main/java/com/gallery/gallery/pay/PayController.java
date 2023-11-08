package com.gallery.gallery.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.gallery.gallery.customer.TicketVo;

@RestController
public class PayController {
    @Autowired
    PayDao dao;

    // @RequestMapping(value = "/getPayList", method = RequestMethod.GET)
    // public List<PayVo> getUserPayList(String userId){
    //     return dao.getUserPayList(userId);
    // }

    @RequestMapping("/realPayment")
    public String pay(@SessionAttribute("id") String id,
    @ModelAttribute TicketVo vo){
        String msg="";
        vo.setId(id);
        msg=dao.realPayment(id,vo);               
        return msg;
    } 
}
