// 네이버 간편 로그인
let naverLogin = new naver.LoginWithNaverId(
  {
    clientId: "hYsQKl6v3FJ2WWltK_Rs", //내 애플리케이션 정보에 cliendId를 입력해줍니다.
    callbackUrl: "http://localhost/", // 내 애플리케이션 API설정의 Callback URL 을 입력해줍니다.
    isPopup: false,
    callbackHandle: true
  }
);	

naverLogin.init();

window.addEventListener('load', function () {
naverLogin.getLoginStatus(function (status) {
  if (status) {
    let email = naverLogin.user.getEmail(); // 필수로 설정할것을 받아와 아래처럼 조건문을 줍니다.
      
    if( email == undefined || email == null) {
      alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
      naverLogin.reprompt();
      return;
    }
  } else {
  }
});
});


let testPopUp;
function openPopUp() {
  testPopUp= window.open("https://nid.naver.com/nidlogin.logout", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,width=1,height=1");
}
function closePopUp(){
  testPopUp.close();
}

function naverLogout() {
openPopUp();
setTimeout(function() {
  closePopUp();
  }, 1000);
}