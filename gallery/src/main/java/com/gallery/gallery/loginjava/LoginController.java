package com.gallery.gallery.loginjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
  @Autowired
  LoginDao loginDao;

  @RequestMapping("/login")
  public ModelAndView login() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/loginHtml/login");
    return mv;
  }

  @RequestMapping("/loginR")
  public String loginR(HttpSession session, LoginVo vo) {
    String msg = "";
    LoginVo rVo = loginDao.login(vo);
  
    if (rVo != null) {      
      session.setAttribute("id", rVo.getId());
      session.setAttribute("checkLogin", rVo.getId());
      session.setAttribute("auth", rVo.getAuth());
      session.setAttribute("tel", rVo.getTel());

      // name과 company가 null이 아닌 경우에만 세션에 설정    

      if (rVo.getName() != null) {
        session.setAttribute("name", rVo.getName());
      }

      if (rVo.getCompany() != null) {
        session.setAttribute("company", rVo.getCompany());
      }

      // 필요한 경우 추가적인 체크나 작업 수행

    } else {              
      msg = "아이디 또는 비밀번호를 확인해주세요.";
    }

    // 디버깅 목적으로 (rVo가 null이 아닌지 확인한 후에 속성에 접근)
    if (rVo != null) {
      System.out.println(rVo.getId());
      System.out.println(rVo.getName());
      System.out.println(rVo.getCompany());
      System.out.println(rVo.getAuth());
    }

    return msg;
  }

  @RequestMapping("/logoutR")
  public void logoutR(HttpSession session) {
    session.setAttribute("checkLogin", "0");    
    session.invalidate();
  }

  @RequestMapping("/idFind")
  public ModelAndView idFind() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/loginhtml/idFind");
    return mv;
  }

  @PostMapping("/findId")
  public ResponseEntity<String> findId(@RequestBody IdFindRequest request) {
    String id = loginDao.findIdByPhoneAndEmail(request.getFindTel(), request.getFindEmail());
    if (id != null) {
      return ResponseEntity.ok("찾으시는 아이디는 " + id + "이예요.");
    } else {
      return ResponseEntity.badRequest().body("일치하는 정보가 없어요. 다시 확인해 주세요");
    }
  }

  @RequestMapping("/pwdFind")
  public ModelAndView pwdFind() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/loginhtml/pwdFind");
    return mv;
  }

  @PostMapping("/findPwd")
  public ResponseEntity<String> findPwd(@RequestBody PwdFindRequest request) {
    String pwd = loginDao.findPwdByIdAndEmail(request.getFindId(), request.getFindEmail());
    if (pwd != null) {
      return ResponseEntity.ok("");
    } else {
      return ResponseEntity.badRequest().body("일치하는 정보가 없어요. 다시 확인해 주세요");
    }
  }

  @RequestMapping("/resetPwd")
  public ModelAndView resetPwd() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/loginhtml/resetPwd");
    return mv;
  }

  @RequestMapping(value = "/resetPassword", method=RequestMethod.POST)
  public String resetPassword(@ModelAttribute LoginVo vo){
    String msg = "";
    
    
    try {
        // 비밀번호 변경을 DAO를 통해 수행
        msg = loginDao.resetPassword(vo);

        if ("회원정보 수정완료".equals(msg)) {
            // 비밀번호 변경이 성공한 경우
            // 여기에서 추가 작업을 수행할 수 있습니다.
        }
    } catch (Exception ex) {
        msg = ex.getMessage();
        System.out.println(ex.getMessage());
    }

    return msg;
  }

}
