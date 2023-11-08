/* CustomerMypage */


//포인트 새로고침(필요할때만 사용)
function reload(){
    $.post("/sectionTop", {id: "${sessionScope.id}"}, function(data1){                                         
        $('.customerMypage_header').html(data1)          
    });
}

//예매내역
$('#ticketView').on('click',function(){   
    reload();        
    $.post("/ticketView", {id: "${sessionScope.id}"}, function(data){                    
        $('.customerMypage_right_wrap').html(data)          
    }); 
})  

//취소/환불내역
$('#refundView').on('click',function(){
    reload();      
    $.post("/refundView", {id: "${sessionScope.id}"}, function(data){            
        $('.customerMypage_right_wrap').html(data)          
    }); 
})


//회원정보수정 버튼클릭

$('#modify').on('click',function(){        
    $.post("/customerModify", {id: "${sessionScope.id}"}, function(data){                    
        $('.customerMypage_right_wrap').html(data)          
    }); 
})

function sessionNowPage(nowPage){ //세션에 now페이지를 등록
    let frm = document.frmFree;
    frm.nowPage.value = nowPage;
    let param = $(frm).serialize();
    
    
    $.post("/sessionNowPage",param, function(msg){
        if(msg!=""){
            alert("나우페이지를 찾지 못함!")
        }
    });
}

function sessionNowPage2(nowPage2){ //세션에 now페이지를 등록    
    let frm = document.frmReview;
    frm.nowPage.value = nowPage2;
    let param = $(frm).serialize();
    
    
    $.post("/sessionNowPage2",param, function(msg){
        if(msg!=""){
            alert("나우페이지를 찾지 못함!")
        }
    });
}

//나의 게시물 클릭
$('#myBoard').on('click', function () {   
       $.post("/myFreeBoardList", {id: "${sessionScope.id}"}, function(data){                    
        $('.customerMypage_right_wrap').html(data); 
        let nowPage = $('input[name="nowPage"]').val();
        sessionNowPage(nowPage);
    });
})
function freeView(sno){
    let temp = document.frmFree;    
    temp.sno.value = sno;
    let param = $(".frmFree").serialize();        
    $(".mainSection").load("/boardMain",function(){
        $(".boardSection").load("/myFreeView", param); 
    });
}
function move(nowPage){//페이지를 이동할때도 nowPage값 세션에등록  
    let frm = document.frmFree;
    frm.nowPage.value = nowPage;
    let param = $(frm).serialize();           
    $.post("/myFreeBoardList", param, function(data){                    
        $('.customerMypage_right_wrap').html(data); 
        let nowPage = $('input[name="nowPage"]').val(); 
        sessionNowPage(nowPage);   
    });         
}


//나의 후기게시글 버튼클릭
$('#myReview').on('click',function(){         
    $.post("/myReviewBoardList", {id: "${sessionScope.id}"}, function(data){                    
        $('.customerMypage_right_wrap').html(data); 
        let nowPage = $('input[name="nowPage"]').val();
        sessionNowPage2(nowPage);
    });
})
function reviewView(sno){
    let temp = document.frmReview;    
    temp.sno.value = sno;
    let param = $(".frmReview").serialize();        
    
    $(".mainSection").load("/boardMain",function(){
        $(".boardSection").load("/myReviewView", param); 
    });
}
function move2(nowPage2){//페이지를 이동할때도 nowPage값 세션에등록  
    let frm = document.frmReview;
    frm.nowPage.value = nowPage2;
    let param = $(frm).serialize();           
    $.post("/myFreeBoardList", param, function(data){                    
        $('.customerMypage_right_wrap').html(data); 
        let nowPage2 = $('input[name="nowPage"]').val(); 
        sessionNowPage(nowPage2);   
    });         
}




  



/////////////////////////고객센터 링크////////////////////////////


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
