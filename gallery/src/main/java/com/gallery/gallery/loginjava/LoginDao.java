package com.gallery.gallery.loginjava;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class LoginDao {
  SqlSession session;
  public LoginDao(){
    session = MybaFactory.getFactory().openSession();
  }

  public LoginVo login(LoginVo vo){
    session.insert("login.loginCheck", vo);
    LoginVo rVo = session.selectOne("login.loginCheck", vo);

    return rVo;
  }

  public String findIdByPhoneAndEmail(String findTel, String findEmail){
    return session.selectOne("login.findIdByPhoneAndEmail", new IdFindRequest(findTel, findEmail));
  }

  public String findPwdByIdAndEmail(String findId, String findEmail){
    return session.selectOne("login.findPwdByIdAndEmail", new PwdFindRequest(findId, findEmail));
  }

  

  public String resetPassword(LoginVo vo){
    String msg = "";
    int cnt = 0;
    String newPwd = vo.getPwd();
    try{
      if(newPwd != null && !newPwd.isEmpty()){
        cnt=session.update("login.resetPassword", vo);
      }
      
      if(cnt<1){
        msg = "저장 중 오류 발생";
        throw new Exception(msg);
        
      }else{
        msg="회원정보 수정완료";
        session.commit();
      }
    }catch(Exception ex){
      ex.printStackTrace();
      msg = ex.getMessage();
      System.out.println(ex.getMessage());
      session.rollback();
    }
    return msg;
  }

}
