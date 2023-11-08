package com.gallery.gallery.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gallery.gallery.center.FAQDao;
import com.gallery.gallery.center.FAQVo;
import com.gallery.gallery.center.Pager;
import com.gallery.gallery.center.noticeDao;
import com.gallery.gallery.center.noticeVo;
import com.gallery.gallery.center.questionDao;
import com.gallery.gallery.center.questionVo;

import jakarta.servlet.http.HttpSession;

@RestController
public class centerController {
    @Autowired
    private questionDao qDao;

    @Autowired
    private noticeDao nDao;

    @Autowired
    private FAQDao fDao;

    // inquriy**********************
    // inquriy 상세화면
    @RequestMapping("/inquriyRegister")
    public ModelAndView inquriyRegister() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/customerCenter/inquriy/inquriyRegister");
        return mv;
    }

    // inquriy 데이터베이스에 저장
    @RequestMapping("/inquriyR")
    public String inquriyR(HttpSession session, questionVo vo) {
        String userId = (String) session.getAttribute("id");
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        vo.setNal(sdf.format(nowDate));
        vo.setStatus("n");
        vo.setId(userId);

        String msg = "";
        msg = qDao.register(vo);
        return msg;
    }

    // 1:1 답장

    @RequestMapping("/inquriyReply")
    public ModelAndView inquriyReply(int sno, HttpSession session) {
        session.setAttribute("sno", sno);
        ModelAndView mv = new ModelAndView();
        mv.addObject("sno", sno);
        mv.setViewName("/customerCenter/inquriy/inquriyReply");
        return mv;
    }

    @RequestMapping("/inquriyRepl")
    public ModelAndView inquriyRepl(questionVo vo, HttpSession session) {
        int sno = (int) session.getAttribute("sno");
        vo.setSno(sno);
        String msg = qDao.update(vo);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/inquriyReply?sno=" + sno); // 리다이렉트 설정
        return mv;
    }

    // 상세보기
    @RequestMapping("/inquriyView")
    public ModelAndView inquriyView(@RequestParam(required = true) int sno) {
        ModelAndView mv = new ModelAndView();
        questionVo vo = qDao.view(sno);
        mv.addObject("iqv", vo);
        mv.setViewName("/customerCenter/inquriy/inquriyView");
        return mv;
    }

    // 목록
    @RequestMapping("/inquriy")
    public ModelAndView inquriy(HttpSession session, Pager page) {
        int userAuth = (int) session.getAttribute("auth");
        String userId = (String) session.getAttribute("id");
        page.setId(userId);
        page.setAuth(userAuth);
        ModelAndView mv = new ModelAndView();
        List<questionVo> list = qDao.list(page);
        mv.addObject("questionList", list);
        mv.addObject("ipage", qDao.getPage());
        mv.setViewName("/customerCenter/inquriy/inquriy");
        return mv;
    }

    // 삭제
    @RequestMapping("/iDel")
    public String delete(questionVo vo) {
        String msg = "";
        msg = qDao.delete(vo);
        return msg;
    }
    // FAQ*********************

    @RequestMapping("/FAQ")
    public ModelAndView FAQ(Pager page) {
        ModelAndView mv = new ModelAndView();
        List<FAQVo> list = fDao.list(page);
        mv.addObject("faqList", list);
        mv.addObject("fpage", fDao.getPage());
        mv.setViewName("/customerCenter/FAQ/FAQ");
        return mv;
    }

    // faq 데이터베이스에 저장
    @RequestMapping("/faqR")
    public String faqR(FAQVo vo) {
        String msg = "";
        msg = fDao.register(vo);
        return msg;
    }

    // faq 등록화면
    @RequestMapping("/faqRegister")
    public ModelAndView faqRegister() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/customerCenter/FAQ/faqRegister");
        return mv;
    }

    // FAQ 상세화면
    @RequestMapping("/faqView")
    public ModelAndView faqView(@RequestParam(required = true) int sno) {
        ModelAndView mv = new ModelAndView();
        FAQVo vo = fDao.view(sno);
        mv.addObject("fqv", vo);
        mv.setViewName("/customerCenter/FAQ/faqView");
        return mv;
    }

    // faq 수정
    @RequestMapping("/fMod")
    public String update(FAQVo vo) {
        String msg = "";
        msg = fDao.update(vo);
        return msg;
    }

    @RequestMapping("/faqModify")
    public ModelAndView faqModify(@RequestParam(required = true) int sno) {
        ModelAndView mv = new ModelAndView();
        FAQVo vo = fDao.view(sno);
        mv.addObject("fmqv", vo);
        mv.setViewName("/customerCenter/FAQ/faqModify");
        return mv;
    }

    // 삭제
    @RequestMapping("/fDel")
    public String delete(FAQVo vo) {
        String msg = "";
        msg = fDao.delete(vo);
        return msg;
    }

    // notice**********************
    // 공지 등록화면
    @RequestMapping("/noticeRegister")
    public ModelAndView noticeRegister() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/customerCenter/notice/noticeRegister");
        return mv;
    }

    // notice 데이터베이스에 저장
    @RequestMapping("/noticeR")
    public String noticeR(noticeVo vo) {
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        vo.setNal(sdf.format(nowDate));
        String msg = "";
        msg = nDao.register(vo);
        return msg;
    }

    // noticeView
    @RequestMapping("/noticeView")
    public ModelAndView noticeView(@RequestParam(required = true) int sno) {
        ModelAndView mv = new ModelAndView();
        noticeVo vo = nDao.view(sno);
        mv.addObject("nqv", vo);
        mv.setViewName("/customerCenter/notice/noticeView");
        return mv;
    }

    // 목록
    @RequestMapping("/notice")
    public ModelAndView notice(Pager page) {
        ModelAndView mv = new ModelAndView();
        List<noticeVo> list = nDao.list(page);

        mv.addObject("noticeList", list);
        mv.addObject("npage", nDao.getPage());
        mv.setViewName("/customerCenter/notice/notice");
        return mv;
    }

    // notice 수정
    @RequestMapping("/nMod")
    public String update(noticeVo vo) {
        String msg = "";
        msg = nDao.update(vo);
        return msg;
    }

    @RequestMapping("/noticeModify")
    public ModelAndView noticeModify(@RequestParam(required = true) int sno) {
        ModelAndView mv = new ModelAndView();
        noticeVo vo = nDao.view(sno);
        mv.addObject("nmqv", vo);
        mv.setViewName("/customerCenter/notice/noticeModify");
        return mv;
    }

    // 삭제
    @RequestMapping("/nDel")
    public String delete(noticeVo vo) {
        String msg = "";
        msg = nDao.delete(vo);
        return msg;
    }

}
