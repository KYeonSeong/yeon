////////////////////////회원정보 수정/////////////////////////

$('.emailBtn').on('click',function(){ //이메일 수정버튼
    $('input[name="email"]').prop('readonly', false);
})

$('.telBtn').on('click',function(){ //휴대폰 수정버튼 
    $('input[name="tel"]').prop('readonly', false);
})



var pwdFlag=false;//비번양식
var pwdSameCheck=false;//새비번=새비번확인
var telFlag=true; //값 수정안할땐 true여야함
var emailFlag=true; //값 수정안할땐 true여야함
var p1,p2;

$(".modifyBtn").on('click',function(){     //회원정보 수정클릭버튼
    let frm = document.frm;
    let param = $(frm).serialize();    
    if(frm.pwdView.value=="") {
        alert("기존 비밀번호를 입력하세요")
        return
    }    
    p1= $('.pwd').val();
    p2=$('.pwdCheck').val();      

    if(p1===""&&p2===""){
    pwdFlag=true;
    pwdSameCheck=true;
    }
    if(p1!=p2){
        alert("새 비밀번호를 똑같이 입력해주세요.")
        return
    }   
    pwdSameCheck=true;
    
    if(pwdFlag==true&&emailFlag==true&&telFlag==true&&pwdSameCheck==true){
        $.ajax({
            type: "POST",
            url: "/customerModifyR",
            data: param,
            success: function(msg) {
                alert(msg);
                $.post("/customerModify", {id: "${sessionScope.id}"}, function(data){                    
                    $('.customerMypage_right_wrap').html(data)          
                }); 
                        
            }
          });
    }else{
    if(pwdFlag==false){alert("비밀번호 형식을 확인해주세요."); return}
    if(pwdSameCheck==false){alert("새 비밀번호를 확인해주세요.");return}
    if(emailFlag==false){alert("이메일을 확인해주세요.");return}
    if(telFlag==false){alert("휴대폰 번호를 확인해주세요.");return} 
    }
     
    })

//회원탈퇴


$(".delAccountBtn").on('click',function(){  
    let confirmBox = confirm("정말 탈퇴하시겠습니까?");
    if(confirmBox==true){
        let param="id="+"${sessionScope.id}";
        $.ajax({
          type : 'POST',
          url  : '/deleteCustomer',
          contentType :'application/x-www-form-urlencoded',
          processData :true,
          data : param,
          success : function(msg){
              if(msg!='')alert(msg); //메시지 발생할때만 알람호출
              else alert("회원탈퇴 완료");
              
              $.post("/logoutR", null, function (msg){
                location.href = "/";        
            } )
                                   
            }        
        });
    }else{
        alert("회원탈퇴가 취소되었습니다.");
    }
})
    



// 비밀번호 조건 부여
$(".pwd").on("keyup", function() {
  pwdSameCheck=false;
    const pwd = $(this).val();
    const pwdPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,}$/;
    const isValid = pwdPattern.test(pwd);
    const conditionMessage = "비밀번호는 8자리 이상 영문(대+소문자)/숫자/특수문자 조합으로 입력해주세요.";

    if (isValid) {
      $(".pwdCondition").text("사용 가능해요.").css("color", "blue").css("font-size", "12px");
      pwdFlag=true;
    } else {
      $(".pwdCondition").text(conditionMessage).css("color", "red").css("font-size", "12px");
      pwdFlag=false;
      return
    }
  });
  // 비밀번호 확인 체크
  $(".pwdCheck").on("keyup", function() {
    const pwd = $(".pwd").val();
    const pwdCk = $(this).val();
    if (pwd !== pwdCk) {
      $(".pwdCheckCondition").text("비밀번호가 일치하지 않아요").css("color", "red").css("font-size", "12px");
      pwdSameCheck=false;
      return;
    } else {
      $(".pwdCheckCondition").text("비밀번호가 일치해요.").css("color", "blue").css("font-size", "12px");      
      pwdSameCheck=true;      
      return;
    }
  });

  
 // 이메일 조건 부여
 $(".email").on("keyup", function() {
    let e = $('input[name="email"]').val();
    const email = $(this).val();
    const emailPattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z0-9_-]+$/;
    if (!emailPattern.test(email)) {
      $(".emailCondition").text("올바른 이메일 형식이 아니예요.").css("color", "red").css("font-size", "12px");
      emailFlag=false;
      return
    } else {
      // 이메일 중복 여부 확인
      $.ajax({
        type: "POST",
        url: "/checkEmailExists",
        data: { email: email },
        success: function(result) {
          if (result.exists) {
              $(".emailCondition").text("이미 사용 중인 이메일이예요.").css("color", "red").css("font-size", "12px");
              emailFlag=true;
              return            
            } else {            
                $(".emailCondition").text("사용가능한 이메일이예요.").css("color", "blue").css("font-size", "12px");
                emailFlag=false;
            return
          }
        }
      });      
    }
  });

    
    
// 휴대폰번호 조건 확인

$(".tel").on("keyup", function() {
  const tel = $(this).val();
  const telPattern = /^[0-9]+$/;
  if (!telPattern.test(tel)) {
    $(".telCondition").text("휴대폰번호는 숫자로만 입력 가능해요.").css("color", "red").css("font-size", "12px");
    // telFlag에 false 할당
    telFlag = false;
    return
  } else {
    // 휴대폰번호 중복 여부 확인
    $.ajax({
      type: "POST",
      url: "/checkTelExists",
      data: { tel: tel },
      success: function(result) {
        if (result.exists) {
          $(".telCondition").text("이미 사용 중인 전화번호예요.").css("color", "red").css("font-size", "12px");        
          telFlag = false;
          return
        } else {
          $(".telCondition").text("사용가능한 전화번호예요.").css("color", "blue").css("font-size", "12px");        
          telFlag = true;
        }        
        
      }
    });
  }
});

