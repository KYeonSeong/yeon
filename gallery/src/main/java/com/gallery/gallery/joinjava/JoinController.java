package com.gallery.gallery.joinjava;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JoinController {
  @Autowired
  JoinDao dao;

  private final MailService mailService;

  @RequestMapping("/joinMemberShip")
  public ModelAndView joinMembership(){
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/member/joinMemberShip");
    return mv;
  }
  
  @RequestMapping("/joinGeneral")
  public ModelAndView joinGeneral(){
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/member/joinGeneral");
    return mv;
  }

  @RequestMapping(value = "/joinGeneralR", method=RequestMethod.POST)
  public String joinGeneralR(@ModelAttribute GeneralVo vo){
    String msg = "";
    msg = dao.joinGeneralR(vo);
    return msg;
  }

  @RequestMapping("/joinCompany")
  public ModelAndView joinCompany(){
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/member/joinCompany");
    return mv;
  }

  @RequestMapping(value = "/joinCompanyR", method=RequestMethod.POST)
  public String joinCompanyR(@ModelAttribute CompanyVo vo){
    String msg = "";
    msg = dao.joinCompanyR(vo);
    return msg;
  }

  @RequestMapping("/checkIdExists")
  public Map<String, Boolean> checkIdExists(@RequestParam String id){
    boolean exists = dao.isIdExists(id);
    Map<String, Boolean> result = new HashMap<>();
    result.put("exists", exists);
    return result;
  }

  @RequestMapping("/checkTelExists")
  public Map<String, Boolean> checkTelExists(@RequestParam String tel){
    boolean exists = dao.isTelExists(tel);
    Map<String, Boolean> result = new HashMap<>();
    result.put("exists", exists);
    return result;
  }

  @RequestMapping("/checkEmailExists")
  public Map<String, Boolean> checkEmailExists(@RequestParam String email){
    boolean exists = dao.isEmailExists(email);
    Map<String, Boolean> result = new HashMap<>();
    result.put("exists", exists);
    return result;
  }

  @ResponseBody
  @PostMapping("/emailSend")
  public String MailSend(String emailSend){
    int number = mailService.sendMail(emailSend);
    String num = "" + number;

    return num;
  }

  
}
