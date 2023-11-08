
//환불신청 클릭시 상단정보 최신화(캐쉬반환)
function reload(){
    $.post("/sectionTop", {id: "${sessionScope.id}"}, function(data1){                                         
        $('.customerMypage_header').html(data1)          
    });
}
function reloadTicketView(){
    reload();         
    $.post("/ticketView", {id: "${sessionScope.id}"}, function(data){            
        $('.customerMypage_right_wrap').html(data)          
    });     
}


//상세보기
function view(payNum){
    let frm = document.frm;
    let param = $('.frm').serialize();
    param += "&payNum="+payNum;        
    $.ajax({
        type : 'POST',
        url  : '/detailView',
        contentType :'application/x-www-form-urlencoded',
        processData :true,
        data : param,
        success : function(msg){
            $('.customerMypage_right_wrap').html(msg);
        }
    });
}


//환불신청버튼 클릭
function refund(payNum){
    let confirmBox = confirm("정말 환불하시겠습니까?");
    if(confirmBox==true){        
        let param = "&payNum="+payNum+"&id="+"${sessionScope.id}";        
        $.ajax({
            type : 'POST',
            url  : '/refund',
            contentType :'application/x-www-form-urlencoded',
            processData :true,
            data : param,
            success : function(msg){
                if(msg!='')alert(msg); //메시지 발생할때만 알람호출
                else alert("환불신청완료");
                reloadTicketView(); 
            }        
        });
    }else{
        alert("환불이 취소되었습니다.");                
     } 
}

