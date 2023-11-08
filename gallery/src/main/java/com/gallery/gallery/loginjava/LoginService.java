package com.gallery.gallery.loginjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
  @Autowired
  private LoginDao loginDao; // 로그인 DAO를 주입 받음

  public String findIdByPhoneAndEmail(String findTel, String findEmail){
    //DB에서 휴대폰 번호와 이메일로 아이디를 찾아 반환하는 로직
    return loginDao.findIdByPhoneAndEmail(findTel, findEmail);
  }

  public String findPwdByIdAndEmail(String findId, String findEmail){
    //DB에서 아이디와 이메일로 비밀번호를 찾아 반환하는 로직
    return loginDao.findPwdByIdAndEmail(findId, findEmail);
  }
}
 
