package com.gallery.gallery.seller;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class SellerVo {
    
String id,company,galleryName,galleryNum,ticketInfo,agencyNotice,useInfo,locationInfo,sellerInfo;
int kidPrice,teenPrice,adultPrice;
MultipartFile image;
}
