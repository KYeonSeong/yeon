package com.gallery.gallery.galleryUpload;
import java.nio.file.Path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GalleryController {

    @Autowired
    private GalleryPageRepository galleryPageRepository;

    @PostMapping("/saveSellerInfo")
    public String saveSellerInfo(
            @RequestParam("company") String company,
            @RequestParam("galleryName") String galleryName,
            @RequestParam("galleryNum") String galleryNum,
            @RequestParam("kidPrice") int kidPrice,
            @RequestParam("teenPrice") int teenPrice,
            @RequestParam("adultPrice") int adultPrice,
            @RequestParam("ticketInfo") String ticketInfo,
            @RequestParam("agencyNotice") String agencyNotice,
            @RequestParam("useInfo") String useInfo,
            @RequestParam("locationInfo") String locationInfo,
            @RequestParam("sellerInfo") String sellerInfo,
            @RequestParam("location") String location, // 추가: location 파라미터
    
            @RequestParam("photo") MultipartFile photo,
            RedirectAttributes redirectAttributes) {

                String photoPath = null;
                if (!photo.isEmpty()) {
                    try {
                        photoPath = saveUploadedFile(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 예외 처리 로직 추가
                    }
                }

        // 데이터베이스에 저장
    GalleryPage galleryPage = new GalleryPage(company, galleryName, galleryNum, kidPrice, teenPrice, adultPrice, ticketInfo, agencyNotice, useInfo, locationInfo, sellerInfo, photoPath, location); // location 추가
    galleryPageRepository.save(galleryPage);

    redirectAttributes.addFlashAttribute("message", "데이터가 성공적으로 저장되었습니다.");

    return "redirect:/sellerMyPage/sellerSection";
}
//     @GetMapping("/sellerMyPage/sellerIndex")
// public String sellerIndex() {
//     // 처리할 내용을 여기에 작성
//     return "sellerIndex"; // 적절한 뷰 이름을 반환
// }
// @GetMapping("/sellerMyPage/sellerSection")
// public String sellerSection() {
//     // 판매자 섹션 관련 처리
//     return "sellerSection"; // 해당 뷰 이름 또는 리다이렉트 경로
// }

private String saveUploadedFile(MultipartFile file) throws IOException {
    // 파일을 저장할 경로 설정
    // String uploadDir = "C://Users//y//Videos//9-1_2//9-1_2//gallery//src//main//resources//static//freeUpload";
    String uploadDir = "C://Users//200423//Desktop//work-project 230907//gallery//src//main//resources//static//sellerUpload";
    
    // 업로드된 파일의 원본 이름
    String originalFileName = file.getOriginalFilename();
    
    // 파일 저장 경로 및 이름 설정
    String filePath = Paths.get(uploadDir, originalFileName).toString();
    
    // 파일 저장
    byte[] bytes = file.getBytes();
    Path path = Paths.get(filePath);
    Files.write(path, bytes);

    // 파일의 저장된 경로 반환
    // return filePath;
    // 파일의 이름 반환
    return "/sellerUpload/"+originalFileName;
} 

}