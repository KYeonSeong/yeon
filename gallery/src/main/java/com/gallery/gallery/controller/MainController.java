package com.gallery.gallery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gallery.gallery.galleryPage.GalleryPageDao;
import com.gallery.gallery.galleryPage.GalleryPageVo;

import jakarta.servlet.http.HttpSession;

@RestController
public class MainController {
  @Autowired
  GalleryPageDao dao;

  // ---------------------------홈화면---------------------------
  @RequestMapping("/")
  public ModelAndView index(HttpSession session) {
    // session.setAttribute("checkLogin", "0"); //페이지 불러오면 일단0
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/index");
    List<GalleryPageVo> list = dao.searchList();
    mv.addObject("list", list);
    return mv;
  }

  // 전시회 클릭하면 상세페이지로 이동
  @RequestMapping("/detailPage")
  public ModelAndView detailPage(@RequestParam("gallery_num") String gallery_num) {
    ModelAndView mv = new ModelAndView();
    List<GalleryPageVo> gList = dao.detailPage(gallery_num);
    mv.addObject("gList", gList);
    mv.setViewName("/detailPage/detailindex");
    return mv;
  }

  // ----------------------------고객센터----------------------------
  @RequestMapping("/centerMain")
  public ModelAndView centerMain() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/Main/centerMain");
    return mv;
  }

  @RequestMapping("/customerMain")
  public ModelAndView customerMain() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/Main/customerMain");
    return mv;
  }

  @RequestMapping("/sellerMain")
  public ModelAndView sellerMain() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/Main/sellerMain");
    return mv;
  }

  // ----------------------------게시판----------------------------
  @RequestMapping("/boardMain")
  public ModelAndView boardMain() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/Main/boardMain");
    return mv;
  }

  // ------------------------------마이페이지---------------
  @RequestMapping("/checkUserRole")
  @ResponseBody
  public Map<String, Object> checkUserRole(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    int userRole = (int) session.getAttribute("auth");
    response.put("role", userRole);
    return response;
  }
}