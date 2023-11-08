// 아이디 찾기
$(".idFindBtns").on("click", function () {
  let findTel = $(".findTel").val();
  let findEmail = $(".findEmail").val();
  let emailVerified = isEmailVerified; // 이메일 인증 여부
  let errorMessage = "";
  let resultMessage = $(".resultMessage"); // 결과 메시지를 선택

  // 필수 입력 필드를 검사하고 빈칸이 있는 경우 메시지를 설정하고 포커스 이동
  if (!findTel) {
    errorMessage = "휴대폰 번호를 입력하세요.";
    $(".findTel").focus();
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
      url: "/findId",
      contentType: "application/json",
      dataType: "text",
      data: JSON.stringify({ findTel: findTel, findEmail: findEmail }),
      success: function (result) {
        // 아이디를 찾았을 때 메시지를 파란색으로 스타일을 지정
        resultMessage.html("<span style='color: blue;'>" + result + "</span>");
      },
      error: function (xhr, status, error) {
        resultMessage.text(xhr.responseText);
      }
    });
  } else {
    // 메시지를 출력
    resultMessage.html("<span style='color: red;'>" + errorMessage + "</span>");
  }
});

// 이메일 조건 부여
$(".findEmail").on("keyup", function() {
  const email = $(this).val();
  const emailPattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z0-9_-]+$/;
  if (!emailPattern.test(email)) {
    $(".idEmailCondition").text("올바른 이메일 형식이 아니예요.").css("color", "red").css("font-size", "12px");
  }else{
    $(".idEmailCondition").text("");
  }
})

$(".emailSendBtn").on("click", function(){
  // 이메일 인증
  let findEmail = $(".findEmail").val();
  if (!findEmail) {
    // 이메일이 입력되지 않은 경우 에러 메시지를 출력하고 함수를 종료
    $(".idEmailCondition").html("<span style='color: red; font-size: 12px;'>이메일을 입력하세요.</span>");
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

