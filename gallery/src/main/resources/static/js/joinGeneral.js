$(document).ready(function() {

  // 이용약관 전체 선택 / 해제
  $("#consentAll").click(function() {
    if($("#consentAll").is(":checked")) {
      $("input[name=consent]").prop("checked", true);
    } else {
      $("input[name=consent]").prop("checked", false);
    }
  });

  $("input[name=consent]").click(function() {
    var total = $("input[name=consent]").length;
    var checked = $("input[name=consent]:checked").length;

    if(total !== checked) {
      $("#consentAll").prop("checked", false);
    } else {
      $("#consentAll").prop("checked", true);
    }
  });

  // 아이디 조건 부여
  $(".generalId").on("keyup", function() {
    const id = $(this).val();
    const idPattern = /^[a-zA-Z0-9_-]{4,20}$/;
    const conditionMessage = "4~20자의 영문, 숫자, 특수기호(_)(-)만 사용할 수 있어요.";

    if (!idPattern.test(id)) {
      $(".idCondition").text(conditionMessage).css("color", "red").css("font-size", "12px");
    } else {
      // 아이디 중복 여부 확인
      $.ajax({
        type: "POST",
        url: "/checkIdExists",
        data: { id: id },
        success: function(result) {
          if (result.exists) {
            $(".idCondition").text("이미 사용중인 아이디예요.").css("color", "red").css("font-size", "12px");
          } else {
            $(".idCondition").text("사용 가능한 아이디예요.").css("color", "blue").css("font-size", "12px");
          }
        }
      });
    }
  });

  // 비밀번호 조건 부여
  $(".generalPwd").on("keyup", function() {
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
  $(".generalPwdCk").on("keyup", function() {
    const pwd = $(".generalPwd").val();
    const pwdCk = $(this).val();
    if (pwd !== pwdCk) {
      $(".pwdCkCondition").text("비밀번호가 일치하지 않아요").css("color", "red").css("font-size", "12px");
    } else {
      $(".pwdCkCondition").text("비밀번호가 일치해요.").css("color", "blue").css("font-size", "12px");
    }
  });

  // 이름 조건 부여
  $(".generalName").on("keyup", function() {
    const name = $(this).val();
    const namePattern = /^[a-zA-Z가-힣]+$/;
    if (!namePattern.test(name)) {
      $(".nameCondition").text("이름은 한글 또는 영문으로만 입력 가능해요.").css("color", "red").css("font-size", "12px");
    } else {
      $(".nameCondition").empty();
    }
  });

  // 생년월일 조건 부여
  $(".generalBirth").on("change", function() {
    const birth = $(this).val();
    // 생년월일 값을 FormData에 추가
    frm.append("birth", birth);
  });

  // 이메일 조건 부여
  $(".generalEmail").on("keyup", function() {
    const email = $(this).val();
    const emailPattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z0-9_-]+$/;
    if (!emailPattern.test(email)) {
      $(".emailCondition").text("올바른 이메일 형식이 아니예요.").css("color", "red").css("font-size", "12px");
    } else {
      // 이메일 중복 여부 확인
      $.ajax({
        type: "POST",
        url: "/checkEmailExists",
        data: { email: email },
        success: function(result) {
          if (result.exists) {
            $(".emailCondition").text("이미 사용 중인 이메일이예요.").css("color", "red").css("font-size", "12px");
          } else {
            $(".emailCondition").text("사용가능한 이메일이예요.").css("color", "blue").css("font-size", "12px");
          }
        }
      });
    }
  });

  // 휴대폰번호 조건 부여
  $(".generalTel").on("keyup", function() {
    const tel = $(this).val();
    const telPattern = /^[0-9]+$/;
    if (!telPattern.test(tel)) {
      $(".telCondition").text("휴대폰번호는 숫자로만 입력 가능해요.").css("color", "red").css("font-size", "12px");
    } else {
      // 휴대폰번호 중복 여부 확인
      $.ajax({
        type: "POST",
        url: "/checkTelExists",
        data: { tel: tel },
        success: function(result) {
          if (result.exists) {
            $(".telCondition").text("이미 사용 중인 전화번호예요.").css("color", "red").css("font-size", "12px");
          } else {
            $(".telCondition").text("사용가능한 전화번호예요.").css("color", "blue").css("font-size", "12px");
          }
        }
      });
    }
  });

  $(".signUpGeneral").on("click", function() {
    let temp = document.frmGeneral;
    let frm = new FormData(temp);

    // 모든 텍스트창에 정보가 입력되어야 가입이 가능
    let isFormValid = true;
    let errorField = null; // 중복 오류가 발생한 필드를 저장할 변수
      
    temp.querySelectorAll('input[type="text"], input[type="password"], input[type="email"]').forEach(input => {
      if (!input.value) {
        isFormValid = false;
        input.focus(); // 가입 불가 시 해당 텍스트창으로 커서 이동
        errorField = input; // 에러가 발생한 필드 저장
        return false; // 하나라도 비어있으면 반복문 종료
      }

      if ($(".idCondition").text() === "이미 사용중인 아이디예요.") {
        errorField = $(".generalId");
        return false;
      }
      if ($(".emailCondition").text() === "이미 사용 중인 이메일이예요.") {
        errorField = $(".generalEmail");
        return false;
      }
      if ($(".telCondition").text() === "이미 사용 중인 전화번호예요.") {
        errorField = $(".generalTel");
        return false;
      }
    });

    if (!isFormValid) {
      alert("모든 정보를 입력해주세요.");
      errorField.focus(); // 에러가 발생한 필드로 커서 이동
      return;
    }
    
    // 아이디 조건 확인
    const id = $(".generalId").val();
    const idPattern = /^[a-zA-Z0-9_-]{4,20}$/;
    if (!idPattern.test(id)) {
      $(".idCondition").text("4~20자의 영문, 숫자, 특수기호(_)(-)만 사용할 수 있어요");
      $(".generalid").focus();
      return;
    }
    
    // 비밀번호 조건 확인
    const pwd = $(".generalPwd").val();
    const pwdPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&]).{8,}$/;
    if (!pwdPattern.test(pwd)) {
      $(".pwdCondition").text("비밀번호는 8자리 이상 영문(대+소문자)/숫자/특수문자 조합으로 입력해주세요.");
      $(".generalPwd").focus();
      return;
    }
    
    // 비밀번호 확인 일치 여부 확인
    const pwdCk = $(".generalPwdCk").val();
    if (pwd !== pwdCk) {
      $(".pwdCkCondition").text("비밀번호가 일치하지 않아요");
      $(".generalPwdCk").focus();
      return;
    }
    
    // 이름 조건 확인
    const name = $(".generalName").val();
    const namePattern = /^[a-zA-Z가-힣]+$/;
    if (!namePattern.test(name)) {
      $(".nameCondition").text("이름은 한글 또는 영문으로만 입력 가능해요.");
      $(".generalName").focus();
      return;
    }
    
    // 생년월일 조건 확인
    const birth = $(".generalBirth").val();
    if (!birth) {
      alert("생년월일을 입력해주세요.");
      $(".generalBirth").focus();
      return;
    }
    
    // 이메일 조건 확인
    const email = $(".generalEmail").val();
    const emailPattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z0-9_-]+$/;
    if (!emailPattern.test(email)) {
      $(".emailCondition").text("올바른 이메일 형식이 아니예요.");
      $(".generalEmail").focus();
      return;
    }
    
    // 휴대전화 조건 확인
    const tel = $(".generalTel").val();
    const telPattern = /^[0-9]+$/;
    if (!telPattern.test(tel)) {
      $(".telCondition").text("휴대폰번호는 숫자로만 입력 가능해요.");
      $(".generalTel").focus();
      return;
    }
    
    // 이메일 인증 여부 확인
    if (!isEmailVerified) {
      alert("이메일 인증을 먼저 진행해주세요.");
      $(".emailNumber").focus();
      return;
    }

    if (errorField) {
      errorField.focus();
      return;
    }

    // 모든 체크박스가 선택되었는지 확인
    let checkboxes = $(".consent");
    let allChecked = true;
    for (let i = 0; i < checkboxes.length; i++) {
      if (!checkboxes[i].checked) {
        allChecked = false;
        break;
      }
    }

    if (!allChecked) {
      alert("모든 동의사항에 체크해주세요.");
      return;
    }

    // 회원가입 처리
    $.ajax({
      type: "POST",
      url: "/joinGeneralR",
      contentType: false,
      processData: false,
      data: frm,
      success: function(msg) {
        if (msg !== "") {
          alert(msg);
        } else {
          alert("회원가입이 완료 되었어요.");
          window.location.href = "/"; // index 페이지로 이동
        }
      }
    }); 
  });
  
  // 이메일 인증
  let isEmailVerified = false; // 이메일 인증 여부

  $(".emailSendBtn").on("click", function(){

    // 이메일 중복 여부 확인
    let findEmail = $(".generalEmail").val();
    if (!findEmail) {
      // 이메일이 입력되지 않은 경우 에러 메시지를 출력하고 함수를 종료
      $(".emailCondition").html("<span style='color: red; font-size: 12px;'>이메일을 입력하세요.</span>");
      $(".generalEmail").focus();
      return;
    }

    // 이메일 중복 여부 확인을 위한 AJAX 요청
    $.ajax({
      type : "POST",
      url : "/checkEmailExists",
      data : {"email" : $(".generalEmail").val()},
      success : function(result) {
        if (result.exists) {
        } else {
          // 이메일 중복이 아닌 경우에만 인증번호 전송
          $.ajax({
            type : "POST",
            url : "/emailSend",
            dataType : "json",
            data : {"emailSend" : $(".generalEmail").val()},
            success : function(data){
              alert("인증번호 발송");
              $(".Confirm").attr("value",data);
              isEmailVerified = false; // 새로운 인증번호를 발송할 때마다 초기화
            }
          });
        }
      }
    });
  })

  $(".emailConfirmBtn").on("click", function(){
    let number1 = $(".emailNumber").val();
    let number2 = $(".Confirm").val();

    if(number2 === ""){
      alert("인증번호를 입력하세요.");
      $(".emailNumber").focus();
    }else if(number1 === number2){
      alert("인증되었어요.");
      isEmailVerified = true; // 인증 완료로 설정
    }else{
      alert("인증번호가 달라요.");
    }
  });
 
});
