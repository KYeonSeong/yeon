package com.gallery.gallery.board;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class ReviewDao {
    SqlSession session;
    ReviewPage page;
    String uploadPath = "C:\\Users\\y\\Desktop\\work-project\\gallery\\src\\main\\resources\\static\\reviewUpload\\";

    public ReviewDao(){
        session = MybaFactory.getFactory().openSession();
    }

    public ReviewPage getPage(){
        return this.page;
    }

    public List<ReviewVo> select(ReviewPage page){
        int totSize = getTotSize(page);
        page.setTotSize(totSize);
        page.pageCompute();
        this.page = page;
        List<ReviewVo> list = session.selectList("review.select", page);
        return list;
    }

    public int getTotSize(ReviewPage page){
        int totSize = session.selectOne("review.totSize", page);
        return totSize;
    }

    public ReviewVo view(int sno){
        session.update("review.hitUpdate", sno);
        ReviewVo vo = session.selectOne("review.view", sno);
        List<ReviewAtt> attFiles = session.selectList("review.attFiles", sno);
        vo.setAttFiles(attFiles);
        return vo;
    }

    public String register(ReviewVo vo, List<MultipartFile> mul){
        String msg = "";
        int cnt = 0;
        List<ReviewAtt> attFiles = new ArrayList<>();
        try{
            int sno = session.selectOne("review.getSerial", "i");
            vo.setSno(sno);
            cnt = session.insert("review.register", vo);
            if(cnt<1){
                msg = "저장중 오류 발생!!!";
                throw new Exception(msg);
            }
            for(MultipartFile f : mul){
                if(f.getOriginalFilename().equals("")) continue;
                UUID uuid = UUID.randomUUID();
                String sysFile= String.format("%s-%s", uuid.randomUUID(), f.getOriginalFilename());
                File saveFile = new File(uploadPath + sysFile);
                f.transferTo(saveFile);
                ReviewAtt att = new ReviewAtt(sno, f.getOriginalFilename(), sysFile);
                attFiles.add(att);
            }

            if(attFiles.size()>0){ // 첨부파일이 있는 경우
                cnt = session.insert("review.attRegister", attFiles);
                if(cnt != attFiles.size()){
                    msg = "첨부파일 정보 저장중 오류 발생!!!";
                    throw new Exception(msg);
                }
            }
            session.commit();

        }catch(Exception ex){
            msg = ex.getMessage();
            session.rollback();
            // 첨부이미지 삭제
            for(ReviewAtt att : attFiles){
                File delFile = new File(uploadPath + att.getSysFile());
                if(delFile.exists()) delFile.delete();
            }
        }
        return msg;
    }

    public String modify(ReviewVo vo, List<MultipartFile> mul){
        String msg = "";
        int cnt = 0;
        List<ReviewAtt> attFiles = null;
        try{
            /*게시물 수정*/
            cnt = session.update("review.update", vo);
            if(cnt<1){
                msg = "정보 수정중 오류 발생!!!";
                throw new Exception(msg);
            }

            /*새로운 첨부파일 업로드*/
            attFiles = new ArrayList<>();
            for(MultipartFile f : mul){
                if(f.getOriginalFilename().equals("")) continue;
                UUID uuid = UUID.randomUUID();
                String sysFile = uuid.toString() + "-" + f.getOriginalFilename();
                File saveFile = new File(uploadPath + sysFile);
                f.transferTo(saveFile);
                ReviewAtt att = new ReviewAtt(vo.getSno(),f.getOriginalFilename(), sysFile);
                attFiles.add(att);
            }

            /*boardAtt에 저장*/
            if(attFiles.size()>0){
                cnt = session.insert("review.attRegister", attFiles);
                if(cnt != attFiles.size()){
                    msg = "정보 수정중 오류 발생!!!";
                    throw new Exception(msg);
                }
            }

            /*삭제된 첨부파일 처리(delAtt)*/
            if(vo.getDelFile() != null){
                cnt = session.delete("review.deleteAtt", vo.getDelFile());
                if (cnt != vo.getDelFile().length){
                    msg = "첨부파일 삭제중 오류 발생!!!";
                    throw new Exception();
                }
                for(String f : vo.getDelFile()){
                    File delFile = new File(uploadPath + f);
                    if(delFile.exists()) delFile.delete();
                }
            }

            session.commit();
        }catch(Exception ex){
            session.rollback();
            for(ReviewAtt att : attFiles){
                File f = new File(uploadPath + att.getSysFile());
                if(f.exists()) f.delete();
            }
        }
        return msg;
    }

    public String delete(ReviewVo vo){
        String msg = "";
        int cnt = 0;
        List<ReviewAtt> delFileList = null;
        try{            
            cnt = session.delete("review.delete", vo.getSno());
            if(cnt<1){
                msg = "게시글 삭제중 오류 발생!!!";
                throw new Exception(msg);
            }
            /*삭제할 파일 목록*/
            delFileList = session.selectList("review.attFiles", vo.getSno());

            String[] delFile = new String[delFileList.size()];
            for(int i=0; i<delFileList.size(); i++){
                ReviewAtt att = delFileList.get(i);
                delFile[i] = att.getSysFile();
            }

            if(delFileList.size()>0){
                cnt = session.delete("review.deleteAtt", delFile);
            
                if(cnt != delFile.length){
                    msg = "첨부파일 정보 삭제중 오류 발생!!!";
                    throw new Exception(msg);
                }
                for(String f : delFile){
                    File file = new File(uploadPath + f);
                    if(file.exists()) file.delete();
                }
            }
            session.commit();
            
        }catch(Exception ex){
            msg = ex.getMessage();
            session.rollback();
        }
        return msg;
    }

    public String repl(ReviewVo vo){
        String msg = "";
        int cnt = 0;
        try{
            cnt = session.insert("review.repl", vo); // 새로운 댓글 등록
            if(cnt<1){
                msg = "댓글 저장중 오류 발생!!!";
                throw new Exception(msg);
            }
            session.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            session.rollback();
        }
        return msg;
    }

    public List<ReviewReply> getReplies(int sno){
        List<ReviewReply> replies = session.selectList("review.replies", sno);
        return replies;
    }

    public String modifyReply(ReviewReply repl) {
        String msg = "";
        int cnt = 0;
        try {
            cnt = session.update("review.replUpdate", repl);
            if (cnt<1) {
                msg = "정보 수정중 오류 발생!!!";
                throw new Exception(msg);
            } else {
                session.commit();
            }
        } catch (Exception ex) {
            session.rollback();
        }
        return msg;
    }
    
    public String deleteReply(ReviewReply repl) {
        String msg = "";
        int cnt = 0;
        try {
            cnt = session.delete("review.replDelete", repl.getSno());
            if (cnt<1){
                msg = "게시글 삭제중 오류 발생!!!";
                throw new Exception(msg);
            } else {
                session.commit();
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
            session.rollback();
        }
        return msg;
    }
}
