package com.gallery.gallery.galleryUpload;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gallerypage")
public class GalleryPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String galleryName;
    private String galleryNum;
    private int kidPrice;
    private int teenPrice;
    private int adultPrice;
    private String ticketInfo;
    private String agencyNotice;
    private String useInfo;
    private String locationInfo;
    private String sellerInfo;
    private String photoPath;
    private String location;

    public GalleryPage() {
        // 기본 생성자
    }

    public GalleryPage(String company, String galleryName, String galleryNum, int kidPrice, int teenPrice,
            int adultPrice, String ticketInfo, String agencyNotice, String useInfo, String locationInfo,
            String sellerInfo, String photoPath, String location) {
        this.company = company;
        this.galleryName = galleryName;
        this.galleryNum = galleryNum;
        this.kidPrice = kidPrice;
        this.teenPrice = teenPrice;
        this.adultPrice = adultPrice;
        this.ticketInfo = ticketInfo;
        this.agencyNotice = agencyNotice;
        this.useInfo = useInfo;
        this.locationInfo = locationInfo;
        this.sellerInfo = sellerInfo;
        this.photoPath = photoPath;
        this.location = location;
    }

    // Getter와 Setter 추가
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGalleryname() {
        return galleryName;
    }

    public void setGalleryname(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getGalleryNum() {
        return galleryNum;
    }

    public void setGalleryNum(String galleryNum) {
        this.galleryNum = galleryNum;
    }

    public int getKidPrice() {
        return kidPrice;
    }

    public void setKidPrice(int kidPrice) {
        this.kidPrice = kidPrice;
    }

    public int getTeenPrice() {
        return teenPrice;
    }

    public void setTeenPrice(int teenPrice) {
        this.teenPrice = teenPrice;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }

  
    public String getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(String ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    public String getAgencyNotice() {
        return agencyNotice;
    }

    public void setAgencyNotice(String agencyNotice) {
        this.agencyNotice = agencyNotice;
    }

    public String getUseInfo() {
        return useInfo;
    }

    public void setUseInfo(String useInfo) {
        this.useInfo = useInfo;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(String sellerInfo) {
        this.sellerInfo = sellerInfo;
    }


    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}