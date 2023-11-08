package com.gallery.gallery.galleryPage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.mybatis.MybaFactory;

@Component
public class GalleryPageDao {

    SqlSession session;

    public GalleryPageDao() {
        session = MybaFactory.getFactory().openSession();
    }

    public List<GalleryPageVo> search(String findStr) {
        List<GalleryPageVo> list = session.selectList("galleryPage.search", findStr);

        return list;
    }

    public List<GalleryPageVo> searchList() {
        List<GalleryPageVo> list = session.selectList("galleryPage.searchList");
        return list;
    }

    public List<GalleryPageVo> detailPage(String gallery_num) {
        List<GalleryPageVo> list = session.selectList("galleryPage.detailPage", gallery_num);
        return list;
    }
}
