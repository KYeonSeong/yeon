package com.gallery.gallery.galleryUpload;


import org.springframework.stereotype.Service;

@Service
public class GalleryPageService {
    private final GalleryPageRepository galleryPageRepository;


    public GalleryPageService(GalleryPageRepository galleryPageRepository) {
        this.galleryPageRepository = galleryPageRepository;
    }
    public void saveGalleryPage(GalleryPage galleryPage) {
        galleryPageRepository.save(galleryPage);
    }
    
}
