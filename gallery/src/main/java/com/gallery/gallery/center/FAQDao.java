package com.gallery.gallery.center;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class FAQDao {
    SqlSession session;
    Pager page;

    public FAQDao() {
        session = MybaFactory.getFactory().openSession();
    }

    // 등록
    public String register(FAQVo vo) {
        String msg = "";
        int cnt = session.insert("center.fregister", vo);

        if (cnt > 0) {
            session.commit();
        } else {
            msg = "error 발생";
        }
        return msg;
    }

    // 화면에 리스트
    public List<FAQVo> list(Pager page) {
        int totSize = getTotSize(page);
        page.setTotSize(totSize);
        page.pageCompute();
        this.page = page;
        List<FAQVo> list = session.selectList("center.flist", page);
        return list;
    }

    public int getTotSize(Pager page) {
        int totSize = session.selectOne("center.ftotSize", page);
        return totSize;
    }

    public Pager getPage() {
        return page;
    }

    // view
    public FAQVo view(int sno) {
        FAQVo vo = null;
        vo = session.selectOne("center.fview", sno);
        return vo;
    }

    // 수정
    public String update(FAQVo vo) {
        String msg = "";
        int cnt = session.update("center.fupdate", vo);
        if (cnt > 0) {
            session.commit();
        } else {
            msg = "수정 중 오류발생";
            session.rollback();
        }
        return msg;
    }

    // 삭제
    public String delete(FAQVo vo) {
        String msg = "";
        int cnt = session.delete("center.fdelete", vo);
        if (cnt > 0) {
            session.commit();
        } else {
            msg = "삭제 중 오류발생";
            session.rollback();
        }
        return msg;
    }

}
