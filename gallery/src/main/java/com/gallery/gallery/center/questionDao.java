package com.gallery.gallery.center;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class questionDao {
    SqlSession session;
    Pager page;

    public questionDao() {
        session = MybaFactory.getFactory().openSession();
    }

    // 등록
    public String register(questionVo vo) {
        String msg = "";
        int cnt = session.insert("center.iregister", vo);

        if (cnt > 0) {
            session.commit();
        } else {
            msg = "error 발생";
            session.rollback();
        }
        return msg;
    }

    public String update(questionVo vo) {
        String msg = "";
        int cnt = session.update("center.adminrepl", vo);

        if (cnt > 0) {
            session.commit();
        } else {
            msg = "error 발생";
            session.rollback();
        }
        return msg;
    }

    // 화면에 리스트
    public List<questionVo> list(Pager page) {
        int totSize = getTotSize(page);
        page.setTotSize(totSize);
        page.pageCompute();
        this.page = page;
        List<questionVo> list = session.selectList("center.ilist", page);
        return list;

    }

    public int getTotSize(Pager page) {
        int totSize = session.selectOne("center.itotSize", page);
        return totSize;
    }

    public Pager getPage() {
        return page;
    }

    // view
    public questionVo view(int sno) {
        questionVo vo = null;
        vo = session.selectOne("center.iview", sno);
        return vo;
    }

    // 삭제
    public String delete(questionVo vo) {
        String msg = "";
        int cnt = session.delete("center.idelete", vo);
        if (cnt > 0) {
            session.commit();
        } else {
            msg = "삭제 중 오류발생";
            session.rollback();
        }
        return msg;
    }

}
