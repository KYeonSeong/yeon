package com.gallery.gallery.center;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class noticeDao {
    SqlSession session;
    Pager page;

    public noticeDao() {
        session = MybaFactory.getFactory().openSession();
    }

    // 등록
    public String register(noticeVo vo) {
        String msg = "";
        int cnt = session.insert("center.nregister", vo);

        if (cnt > 0) {
            session.commit();
        } else {
            msg = "error 발생";
        }
        return msg;
    }

    // 화면에 리스트
    public List<noticeVo> list(Pager page) {
        int totSize = getTotSize(page);
        page.setTotSize(totSize);
        page.pageCompute();
        this.page = page;
        List<noticeVo> list = session.selectList("center.nlist", page);
        return list;
    }

    public int getTotSize(Pager page) {
        int totSize = session.selectOne("center.ntotSize", page);
        return totSize;
    }

    public Pager getPage() {
        return page;
    }



    // view
    public noticeVo view(int sno) {
        noticeVo vo = null;
        vo = session.selectOne("center.nview", sno);
        return vo;
    }

    // 수정
    public String update(noticeVo vo) {
        String msg = "";
        int cnt = session.update("center.nupdate", vo);
        if (cnt > 0) {
            session.commit();
        } else {
            msg = "수정 중 오류발생";
            session.rollback();
        }
        return msg;
    }

    // 삭제
    public String delete(noticeVo vo) {
        String msg = "";
        int cnt = session.delete("center.ndelete", vo);
        if (cnt > 0) {
            session.commit();
        } else {
            msg = "삭제 중 오류발생";
            session.rollback();
        }
        return msg;
    }
}
