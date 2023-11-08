package com.gallery.gallery.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FreeController {
    @Autowired
    FreeDao dao;

    @RequestMapping("/freeBoardList")
    public ModelAndView select(FreePage page){
        ModelAndView mv = new ModelAndView();
        List<FreeVo> list = dao.select(page);
        mv.addObject("list", list);
        mv.addObject("page", dao.getPage());
        mv.setViewName("board/free/freeList");
        return mv;
    }

    @RequestMapping("/goFreeRegister")
    public ModelAndView freeRegister(FreePage page){
        ModelAndView mv = new ModelAndView();
        mv.addObject("page", page);
        mv.setViewName("board/free/freeRegister");
        return mv;
    }

    @RequestMapping(value = "/freeRegister")
    public String register(@RequestParam("attFile") List<MultipartFile> mul, @ModelAttribute FreeVo vo, @ModelAttribute FreePage page){
        String msg = "";
        msg = dao.register(vo, mul);
        return msg;
    }

    @RequestMapping(value = "/freeView")
    public ModelAndView view(int sno, FreePage page){
        ModelAndView mv = new ModelAndView();
        FreeVo vo = dao.view(sno);

        // 댓글 데이터 조회 및 설정
        List<FreeReply> replies = dao.getReplies(sno);
        vo.setReplies(replies);

        mv.addObject("vo", vo);
        mv.addObject("page", dao.getPage());
        mv.setViewName("board/free/freeView");
        return mv;
    }

    @RequestMapping(value = "/myFreeView")
    public ModelAndView myView(int sno, FreePage page
    ,@SessionAttribute("nowPage") int nowPage){      
        ModelAndView mv = new ModelAndView();
        FreeVo vo = dao.view(sno);                

        // 댓글 데이터 조회 및 설정
        List<FreeReply> replies = dao.getReplies(sno);
        vo.setReplies(replies);
        mv.addObject("vo", vo);
        mv.addObject("page", page);        
        mv.setViewName("board/free/freeView");
        return mv;
    }

    @RequestMapping("/goFreeModify")
    public ModelAndView freemodify(int sno, FreePage page){
        ModelAndView mv = new ModelAndView();
        FreeVo vo = dao.view(sno);
        mv.addObject("vo", vo);
        mv.addObject("page", dao.getPage());
        mv.setViewName(("board/free/freeModify"));
        return mv;
    }

    @RequestMapping(value = "/freeModify", method=RequestMethod.POST)
    public String modify(@RequestParam("attFile") List<MultipartFile> mul, @ModelAttribute FreeVo vo, @ModelAttribute FreePage page){
        ModelAndView mv = new ModelAndView();
        String msg = "아무것도 하지 않고 메세지 전달";
        msg = dao.modify(vo, mul);
        return msg;
    }

    @RequestMapping(value = "/freeDelete")
    public String delete(FreeVo vo, FreePage page){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.delete(vo);
        return msg;
    }

    @RequestMapping(value = "/freeRepl", method = RequestMethod.POST)
    public String repl(FreeReply repl){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.repl(repl);
        return msg;
    }

    @RequestMapping(value = "/freeReplR", method = RequestMethod.POST)
    public String replR(FreeReply repl){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.replR(repl);
        return msg;
    }

    @RequestMapping(value = "/freeReplModify", method = RequestMethod.POST)
    public String modifyReply(FreeReply repl){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.modifyReply(repl);
        return msg;
    }

    @RequestMapping(value = "/freeReplDelete", method = RequestMethod.POST)
    public String deleteReply(FreeReply repl){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        msg = dao.deleteReply(repl);
        return msg;
    }
}
