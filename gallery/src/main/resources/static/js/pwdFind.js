// 비밀번호 변경하러 가기
$(".pwdChangeBtns").on("click", function () {
  let findId = $(".findId").val();
  let findEmail = $(".findEmail").val();
  let emailVerified = isEmailVerified; // 이메일 인증 여부
  let errorMessage = "";
  let resultMessage = $(".resultMessage"); // 결과 메시지를 선택

  // 필수 입력 필드를 검사하고 빈칸이 있는 경우 메시지를 설정하고 포커스 이동
  if (!findId) {
    errorMessage = "아이디를 입력하세요.";
    $(".findId").focus();
  } else if (!findEmail) {
    errorMessage = "이메일을 입력하세요.";
    $(".findEmail").focus();
  } else if (!emailVerified) {
    errorMessage = "이메일 인증을 완료하세요.";
  }

  // 메시지가 비어 있으면 서버로 정보 전송
  if (!errorMessage) {
    // 서버로 정보 전송
    $.ajax({
      type: "POST",
      url: "/findPwd",
      contentType: "application/json",
      dataType: "text",
      data: JSON.stringify({ findId: findId, findEmail: findEmail }),
      success: function (result) {
        // 아이디를 찾았을 때 메시지를 파란색으로 스타일을 지정
        resultMessage.html("<span style='color: blue;'>" + result + "</span>");
        // 정보 입력 및 이메일 인증이 완료되었을 때 페이지로 이동
        if (emailVerified) {
         location.href="/resetPwd"; // 비밀번호 찾기 페이지 URL
        }
      },
      error: function (xhr, status, error) {
        resultMessage.text(xhr.responseText);
      }
    });
  } else {
    // 메시지를 출력
    resultMessage.html("<span style='color: red; font-size: 12px;'>" + errorMessage + "</span>");
  }
});

// 이메일 조건 부여
$(".findEmail").on("keyup", function() {
  const email = $(this).val();
  const emailPattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z0-9_-]+$/;
  if (!emailPattern.test(email)) {
    $(".pwdEmailCondition").text("올바른 이메일 형식이 아니예요.").css("color", "red").css("font-size", "12px");
  }else{
    $(".pwdEmailCondition").text("");
  }
})

$(".emailSendBtn").on("click", function(){
  // 이메일 인증
  let findEmail = $(".findEmail").val();
  if (!findEmail) {
    // 이메일이 입력되지 않은 경우 에러 메시지를 출력하고 함수를 종료
    $(".pwdEmailCondition").html("<span style='color: red; font-size: 12px;'>이메일을 입력하세요.</span>");
    return;
  }

  $.ajax({
    type : "POST",
    url : "/emailSend",
    dataType : "json",
    data : {"emailSend" : $(".findEmail").val()},
    success : function(data){
      alert("인증번호 발송");
      $(".Confirm").attr("value",data);
      isEmailVerified = false; // 새로운 인증번호를 발송할 때마다 초기화
    },
    error: function (xhr, status, error) {
      // 에러 발생 시 에러 메시지 출력
      alert("이메일 인증 요청 중 오류가 발생했습니다.");
      isEmailVerified = false; // 인증 실패로 설정
    },
  })
})
$(".emailConfirmBtn").on("click", function(){
  let number1 = $(".emailNumber").val();
  let number2 = $(".Confirm").val();
  
  if(number2 === ""){
    alert("인증번호를 입력하세요.")
    $(".emailNumber").focus();
  }else if(number1 === number2){
    alert("인증되었어요.");
    isEmailVerified = true; // 인증 완료로 설정
  }else{
    alert("인증번호가 달라요.");
    isEmailVerified = false; // 인증 실패로 설정
  }
});

// 비밀번호 조건 부여
$(".newPassword").on("keyup", function() {
  const pwd = $(this).val();
  const pwdPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,}$/;
  const isValid = pwdPattern.test(pwd);
  const conditionMessage = "비밀번호는 8자리 이상 영문(대+소문자)/숫자/특수문자 조합으로 입력해주세요.";

  if (isValid) {
    $(".pwdCondition").text("사용 가능해요.").css("color", "blue").css("font-size", "12px");
  } else {
    $(".pwdCondition").text(conditionMessage).css("color", "red").css("font-size", "12px");
  }
});

// 비밀번호 확인 체크
$(".confirmPassword").on("keyup", function() {
  const pwd = $(".newPassword").val();
  const pwdCk = $(this).val();
  if (pwd !== pwdCk) {
    $(".pwdCkCondition").text("비밀번호가 일치하지 않아요").css("color", "red").css("font-size", "12px");
  } else {
    $(".pwdCkCondition").text("비밀번호가 일치해요.").css("color", "blue").css("font-size", "12px");
  }
});

var pwdFlag=false;//비번양식
var pwdSameCheck=false;//새비번=새비번확인
var p1,p2;

// 비밀번호 변경
$(".pwdChangeSubmit").on("click", function () {
  let p1 = $('.newPassword').val();
  let p2 = $('.confirmPassword').val();

  // 비밀번호 조건 확인
  const pwdPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,}$/;
  if (!pwdPattern.test(p1)) {
    $(".pwdCondition").text("비밀번호는 8자리 이상 영문(대문자+소문자)/숫자/특수문자 조합으로 입력해주세요.")
                    .css("color", "red")
                    .css("font-size", "12px");
    return;
  }

  // 비밀번호 확인 일치 여부 확인
  if (p1 !== p2) {
    $(".pwdCkCondition").text("비밀번호가 일치하지 않아요").css("color", "red").css("font-size", "12px");
    return;
  }

  // 비밀번호 변경을 서버로 전송
  let param = {
    newPassword: p1,
    confirmPassword: p2
  };

  $.ajax({
    type: "POST",
    url: "/resetPassword", // 비밀번호 변경을 처리하는 서버 엔드포인트
    data: param,
    success: function (msg) {
      alert(msg);
      // 비밀번호 변경 성공 시 다른 작업을 수행하려면 여기에 추가
    },
    error: function (xhr, status, error) {
      // 비밀번호 변경 실패 시, 에러 처리를 여기에 추가
      alert("비밀번호 변경 실패. 다시 시도해주세요."); // 예시: 비밀번호 변경 실패 메시지
    },
  });
});