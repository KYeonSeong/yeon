package com.gallery.gallery.ticket;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.customer.TicketVo;
import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class TicketDao {
    SqlSession ticketSession;

    public TicketDao(){
        ticketSession = MybaFactory.getFactory().openSession();
    }
    
    public List<TicketVo> getUserTicketList(String id){
        List<TicketVo> userTicketList = ticketSession.selectList("ticket.userTicketList", id);
        return userTicketList;
    }
}
