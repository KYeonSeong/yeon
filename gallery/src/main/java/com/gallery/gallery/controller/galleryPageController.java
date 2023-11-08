package com.gallery.gallery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gallery.gallery.galleryPage.GalleryPageDao;
import com.gallery.gallery.galleryPage.GalleryPageVo;

@RestController
public class galleryPageController {
    @Autowired
    GalleryPageDao dao;

    // 검색
    @RequestMapping("/searchView")
    public ModelAndView searchView(String findStr) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/Main/searchView");
        List<GalleryPageVo> list = dao.search(findStr);
        mv.addObject("list", list);
        return mv;
    }

}
