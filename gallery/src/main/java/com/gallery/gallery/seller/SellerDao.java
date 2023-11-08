package com.gallery.gallery.seller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.gallery.gallery.mybatis.MybaFactory;
@Component
public class SellerDao {
    SqlSession session;




public SellerDao(){
    session = MybaFactory.getFactory().openSession();
}


public List<SellerVo> select(String id ){
    List<SellerVo> list = null;
    list=session.selectList("sellerpage.select",id);    
    session.commit();
    return list;        

}
public List<gellerylistVo> sellergallerylist(String company) {
    List<gellerylistVo> galleryList = null;
    session.selectList("sellerpage.sellergallerylist", company);
    galleryList = session.selectList("sellerpage.sellergallerylist", company);
    session.commit(); 
    return galleryList;
}

}
