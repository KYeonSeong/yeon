package com.gallery.gallery.joinjava;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class JoinDao {
  SqlSession session;

  public JoinDao(){
    session = MybaFactory.getFactory().openSession();
  }

  public String joinGeneralR(GeneralVo vo){
    String msg = "";
    int cnt = 0;
    try{
      cnt = session.insert("join.joinGeneral", vo);
      if(cnt<1){
        msg = "오류 발생";
        throw new Exception(msg);
      }
      session.commit();
    }catch(Exception ex){
      msg = ex.getMessage();
      session.rollback();
    }
    return msg;
  }

  public String joinCompanyR(CompanyVo vo){
    String msg = "";
    int cnt = 0;
    try{
      cnt = session.insert("join.joinCompany", vo);
      if(cnt<1){
        msg = "오류 발생";
        throw new Exception(msg);
      }
      session.commit();
    }catch(Exception ex){
      msg = ex.getMessage();
      session.rollback();
    }
    System.out.println(msg);
    return msg;
  }

  public boolean isIdExists(String id) {
    int count = session.selectOne("join.isIdExists", id);
    return count > 0;
  }

  public boolean isTelExists(String tel) {
    int count = session.selectOne("join.isTelExists", tel);
    return count > 0;
  }

  public boolean isEmailExists(String email) {
    int count = session.selectOne("join.isEmailExists", email);
    return count > 0;
  }
}
