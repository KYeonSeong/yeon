package com.gallery.gallery.detailPage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.board.ReviewAtt;
import com.gallery.gallery.board.ReviewVo;
import com.gallery.gallery.customer.CustomerVo;
import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class DetailPageDao {
    SqlSession session;
    String uploadPath = "D:\\work-project\\work-project\\gallery\\src\\main\\resources\\static\\reviewUpload\\";

    public DetailPageDao(){
        session = MybaFactory.getFactory().openSession();
    }

    public List<DetailPageVo> galleryInfo(DetailPageVo vo){
        List<DetailPageVo> gList = session.selectList("detailPage.galleryInfo", vo);
        session.commit();
        return gList;
    }
    public CustomerVo customerInfo(String id){
        session.update("detailPage.customerInfo", id);
        CustomerVo cList = session.selectOne("detailPage.customerInfo", id);
        session.commit();
        return cList;
    }

    //안내
    public DetailPageVo info(String gallery_num){
        DetailPageVo vo = session.selectOne("detailPage.information", gallery_num);
        return vo;
    }
    
    // QnA 등록
    public String qnaRegister(QnaVo qnaVo){
        String msg = "";
        int cnt = 0;
        try{
            int sno = session.selectOne("detailPage.getSerial", "i");
            qnaVo.setSno(sno);
            cnt = session.insert("detailPage.qnaRegister", qnaVo);
            if(cnt>0){
                session.commit();
                msg = "QnA가 등록되었습니다";
            }
        }catch(Exception ex){
            ex.printStackTrace();
            session.rollback();
            msg = "QnA 등록중 오류 발생";
        }
        return msg;
    }
    //QNA 답변 등록
    public String qnaAnswer(QnaVo qnaVo){
        String msg = "";
        int cnt = 0;
        try{
            int sno = session.selectOne("detailPage.getSerial", "i");
            qnaVo.setSno(sno);
            cnt = session.insert("detailPage.qnaAnswer", qnaVo);
            if(cnt>0){
                msg = "답변이 등록되었습니다";
                session.commit();
            }
        }catch(Exception ex){
            ex.printStackTrace();
            session.rollback();
            msg = "답변 등록중 오류 발생";
        }
        return msg;
    }

    //QNA 보기
    public List<QnaVo> qnaList(String gallery_num){
        List<QnaVo> qnaList = session.selectList("detailPage.qnaList", gallery_num);
        return qnaList;
    }

      // 후기
    public List<ReviewVo> reviewList(String gallery_num){
        List<ReviewVo> reviewList = session.selectList("detailPage.review", gallery_num);
        
        for(ReviewVo review : reviewList){
            List<ReviewAtt> attFiles = attFiles(review.getSno()); // 해당 review의 attFiles 가져옴
            review.setAttFiles(attFiles); // review 객체에 attFiles 설정
        }
        return reviewList;
    }
    // 후기 이미지
    public List<ReviewAtt> attFiles(int sno){
        List<ReviewAtt> attFiles = session.selectList("detailPage.attFiles", sno);
        return attFiles;
    }
    public PayVo payView(String gallery_num){
        PayVo payVo = session.selectOne("detailPage.payView", gallery_num);
        return payVo;
    }
}
