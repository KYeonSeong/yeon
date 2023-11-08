
//상품등록
$('#sellerupdate').on('click', function() {   
    // sellerupdate.html 페이지로 이동
    window.location.href = "/sellerupdate"; 
});

//상품내역
$('#gallerylist').on('click', function () {    
     $.post("/gallerylist", {company: "${sessionScope.company}"}, function (data) {
         $('.sellerMypage_right_wrap').html(data);
     });
})

//구매&환불관리
$('#sellerrefund').on('click',function(){         
    $('.sellerMypage_right_wrap').load("/sellerrefund");
})

// //매출관리
// $('#sellerData').on('click',function(){         
//     $('.sellerMypage_right_wrap').load("/sellerData");
// })

// //회원정보수정
// $('sellermodify').on('click',function(){         
//     $('.sellerMypage_right_wrap').load("/sellermodify");
// })

// 마이페이지 -> 공지 목록
$('#noticeBtn').on('click',function(){
    $(".mainSection").load("/centerMain", function () {
        $(".centerSection").load("/notice"); //자주 묻는 질문
    })
})

// 마이페이지 -> FAQ 목록
$('#FAQBtn').on('click',function(){
    $(".mainSection").load("/centerMain", function () {
        $(".centerSection").load("/FAQ"); //자주 묻는 질문
    })        
})

// 마이페이지 -> 1:1문의 목록
$('#inquriyBtn').on('click',function(){
    $(".mainSection").load("/centerMain", function () {
        $(".centerSection").load("/inquriy"); //자주 묻는 질문
    })        
})
