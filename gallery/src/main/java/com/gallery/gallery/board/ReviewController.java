package com.gallery.gallery.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ReviewController {
    @Autowired
    ReviewDao dao;

    @RequestMapping("/reviewBoardList")
    public ModelAndView select(ReviewPage page){
        ModelAndView mv = new ModelAndView();
        List<ReviewVo> list = dao.select(page);
        mv.addObject("list", list);
        mv.addObject("page2", dao.getPage());
        mv.setViewName("board/review/reviewList");
        return mv;
    }

    @RequestMapping("/goReviewRegister")
    public ModelAndView reviewRegister(ReviewPage page){
        ModelAndView mv = new ModelAndView();
        mv.addObject("page2", page);
        mv.setViewName("board/review/reviewRegister");
        return mv;
    }
    
    @RequestMapping(value = "/reviewRegister")
    public String register(@RequestParam("attFile") List<MultipartFile> mul, @ModelAttribute ReviewVo vo, @ModelAttribute ReviewPage page){
        String msg = "";
        msg = dao.register(vo, mul);
        return msg;
    }

    @RequestMapping(value = "/reviewView")
    public ModelAndView view(int sno, FreePage page){
        ModelAndView mv = new ModelAndView();
        ReviewVo vo = dao.view(sno);

        // 댓글 데이터 조회 및 설정
        List<ReviewReply> replies = dao.getReplies(sno);
        vo.setReplies(replies);

        mv.addObject("vo", vo);
        mv.addObject("page2", dao.getPage());        
        mv.setViewName("board/review/reviewView");
        return mv;
    }

    @RequestMapping(value = "/myReviewView")
    public ModelAndView view(int sno, ReviewPage page){
        ModelAndView mv = new ModelAndView();
        ReviewVo vo = dao.view(sno);

        // 댓글 데이터 조회 및 설정
        List<ReviewReply> replies = dao.getReplies(sno);
        vo.setReplies(replies);
        mv.addObject("vo", vo);
        mv.addObject("page2", page);
        mv.setViewName("board/review/reviewView");
        return mv;
    }


    @RequestMapping("/goReviewModify")
    public ModelAndView freemodify(int sno, ReviewPage page){
        ModelAndView mv = new ModelAndView();
        ReviewVo vo = dao.view(sno);
        mv.addObject("vo", vo);
        mv.addObject("page2", dao.getPage());
        mv.setViewName(("board/review/reviewModify"));
        return mv;
    }

    @RequestMapping(value = "/reviewModify", method=RequestMethod.POST)
    public String modify(@RequestParam("attFile") List<MultipartFile> mul, @ModelAttribute ReviewVo vo, @ModelAttribute ReviewPage page){
        ModelAndView mv = new ModelAndView();
        String msg = "아무것도 하지 않고 메세지 전달";
        msg = dao.modify(vo, mul);
        return msg;
    }

    @RequestMapping(value = "/reviewDelete")
    public String delete(ReviewVo vo, ReviewPage page){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.delete(vo);
        return msg;
    }

    @RequestMapping(value="/reviewRepl", method=RequestMethod.POST) // method 지정할 때는 value 속성도 지정해줘야 한다
    public String repl(ReviewVo vo){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.repl(vo);
        return msg;
    }

    @RequestMapping(value = "/reviewReplModify", method = RequestMethod.POST)
    public String modifyReply(ReviewReply repl){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.modifyReply(repl);
        return msg;
    }

    @RequestMapping(value = "/reviewReplDelete", method = RequestMethod.POST)
    public String deleteReply(ReviewReply repl){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.deleteReply(repl);
        return msg;
    }
}
