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
public class FreeDao {
    SqlSession session;
    FreePage page;
    String uploadPath = "C:\\Users\\y\\Desktop\\work-project\\gallery\\src\\main\\resources\\static\\freeUpload\\";

    public FreeDao(){
        session = MybaFactory.getFactory().openSession();
    }

    public FreePage getPage(){
        return this.page;
    }

    public List<FreeVo> select(FreePage page) {
        int totSize = getTotSize(page);
        page.setTotSize(totSize);
        page.pageCompute();
        this.page = page;
        List<FreeVo> list = session.selectList("free.select", page);
        return list;
    }

    public int getTotSize(FreePage page){
        int totSize = session.selectOne("free.totSize", page);
        return totSize;
    }

    public FreeVo view(int sno){
        session.update("free.hitUpdate", sno);
        FreeVo vo = session.selectOne("free.view", sno);
        List<FreeAtt> attFiles = session.selectList("free.attFiles", sno);
        vo.setAttFiles(attFiles);
        return vo;
    }

    public String register(FreeVo vo, List<MultipartFile> mul){
        String msg = "";
        int cnt = 0;
        List<FreeAtt> attFiles = new ArrayList<>();

        try{
            int sno = session.selectOne("free.getSerial", "i");
            vo.setSno(sno);
            cnt = session.insert("free.register", vo);
            if(cnt<1){
                msg = "게시글 등록 중 오류가 발생했습니다";
                throw new Exception(msg);
            }
            for(MultipartFile f : mul){
                if(f.getOriginalFilename().equals("")) continue;
                UUID uuid = UUID.randomUUID();
                String sysFile= String.format("%s-%s", uuid.randomUUID(), f.getOriginalFilename());
                File saveFile = new File(uploadPath + sysFile);
                f.transferTo(saveFile);
                FreeAtt att = new FreeAtt(sno, f.getOriginalFilename(), sysFile);
                attFiles.add(att);
            }

            if(attFiles.size()>0){ // 첨부파일이 있는 경우
                cnt = session.insert("free.attRegister", attFiles);
                if(cnt != attFiles.size()){
                    msg = "게시글 첨부파일 정보 저장 중 오류가 발생했습니다";
                    throw new Exception(msg);
                }
            }
            session.commit();

        }catch(Exception ex){
            msg = ex.getMessage();
            session.rollback();
            // 첨부이미지 삭제
            for(FreeAtt att : attFiles){
                File delFile = new File(uploadPath + att.getSysFile());
                if(delFile.exists()) delFile.delete();
            }
        }
        return msg;
    }

    public String modify(FreeVo vo, List<MultipartFile> mul){
        String msg = "";
        int cnt = 0;
        List<FreeAtt> attFiles = null;
        try{
            /*게시물 수정*/
            cnt = session.update("free.update", vo);
            if(cnt<1){
                msg = "정보 수정 중 오류가 발생했습니다";
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
                FreeAtt att = new FreeAtt(vo.getSno(),f.getOriginalFilename(), sysFile);
                attFiles.add(att);
            }

            /*boardAtt에 저장*/
            if(attFiles.size()>0){
                cnt = session.insert("free.attRegister", attFiles);
                if(cnt != attFiles.size()){
                    msg = "정보 수정 중 오류가 발생했습니다";
                    throw new Exception(msg);
                }
            }

            /*삭제된 첨부파일 처리(delAtt)*/
            if(vo.getDelFile() != null){
                cnt = session.delete("free.deleteAtt", vo.getDelFile());
                if (cnt != vo.getDelFile().length){
                    msg = "첨부파일 삭제 중 오류가 발생했습니다";
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
            for(FreeAtt att : attFiles){
                File f = new File(uploadPath + att.getSysFile());
                if(f.exists()) f.delete();
            }
        }
        return msg;
    }

    public String delete(FreeVo vo){
        String msg = "";
        int cnt = 0;
        List<FreeAtt> delFileList = null;
        cnt = session.selectOne("free.checkRepl", vo);
        try{
            if(cnt>0){
                msg = "해당 게시글에 댓글이 존재하여 삭제가 불가능합니다";
                throw new Exception(msg);
            }

            cnt = session.delete("free.delete", vo.getSno());
            if(cnt<1){
                msg = "게시글 삭제 중 오류가 발생했습니다";
                throw new Exception(msg);
            }
            /*삭제할 파일 목록*/
            delFileList = session.selectList("free.attFiles", vo.getSno());

            String[] delFile = new String[delFileList.size()];
            for(int i=0; i<delFileList.size(); i++){
                FreeAtt att = delFileList.get(i);
                delFile[i] = att.getSysFile();
            }

            if(delFileList.size()>0){
                cnt = session.delete("free.deleteAtt", delFile);
            
                if(cnt != delFile.length){
                    msg = "첨부파일 정보 삭제 중 오류가 발생했습니다";
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

    public String repl(FreeReply repl){
        String msg = "";
        int cnt = 0;
        try{
            int sno = session.selectOne("free.getSerialR", "i");
            repl.setSno(sno);
            repl.setGrp(sno);
            cnt = session.insert("free.repl",repl); // 댓글 등록
            if(cnt < 1){
                msg = "댓글 저장 중 오류 발생!!!";
                throw new Exception(msg);
            }
            session.commit();
        } catch(Exception ex){
            ex.printStackTrace();
            session.rollback();
        }
        return msg;
    }

    public String replR(FreeReply repl){
        String msg = "";
        int cnt = 0;

        try{
            // 1) board 댓글 저장
            session.update("free.seqUp", repl);
            session.commit();

            int sno = session.selectOne("free.getSerialR", "i");
            repl.setSno(sno);
            repl.setSeq(repl.getSeq()+1);
            repl.setDeep(repl.getDeep()+1);
            cnt = session.insert("free.replRep", repl);
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

    public List<FreeReply> getReplies(int sno){
        List<FreeReply> replies = session.selectList("free.replies", sno);
        return replies;        
    }

    public String modifyReply(FreeReply repl) {
        String msg = "";
        int cnt = 0;
        try {
            cnt = session.update("free.replUpdate", repl);
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
    
    public String deleteReply(FreeReply repl) {
        String msg = "";
        int cnt = 0;
        try {
            cnt = session.delete("free.replDelete", repl.getSno());
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
