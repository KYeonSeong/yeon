package com.gallery.gallery.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gallery.gallery.customer.TicketVo;

@RestController
public class TicketController {
    @Autowired
    TicketDao dao;

    @RequestMapping(value = "/getTicketList", method = RequestMethod.GET)
    public List<TicketVo> getUserTicketList(String userId){
        return dao.getUserTicketList(userId);
    } 
}
