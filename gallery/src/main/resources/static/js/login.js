//******************연성part******************* */
let isEmailVerified = false;
// 로그인 버튼 클릭 이벤트
$(".loginBtns").on("click", function () {
  loginUser();
});

// 엔터 키로 로그인 가능하도록 처리
$(".frmLogin").on("keydown", function (event) {
  if (event.keyCode === 13) { // Enter 키의 keyCode는 13
    loginUser();
  }
});

// 실제 로그인 기능을 수행하는 함수
function loginUser() {
  let param = $(".frmLogin").serialize();
  $.post("/loginR", param, function (msg) {
    if (msg === "") {
      alert("로그인 되었어요.");
      window.location.replace("/");
    } else {
      alert(msg);
    }
  });
}

// 버튼 클릭 시 회원가입 화면으로
$(".signBtn").on("click", function () {
  location.href = "/joinMemberShip";
})

// 버튼 클릭 시 아이디 찾기 화면으로
$(".idFindBtn").on("click", function () {
  location.href = "/idFind";
})

// 버튼 클릭 시 비밀번호 찾기 화면으로
$(".pwdFindBtn").on("click", function () {
  location.href = "/pwdFind";
})

// 버튼 클릭 시 일반 회원가입 화면으로
$(".joinGeneralBtn").on("click", function () {
  location.href = "/joinGeneral";
})

// 버튼 클릭 시 사업자 회원가입 화면으로
$(".joinCompanyBtn").on("click", function () {
  location.href = "/joinCompany";
})

// 로고 클릭 시 홈 화면으로
$(".logoImage").on("click", function() {
  location.href = "/";
})

// // 버튼 클릭 시 아이디 찾기 페이지 내용 출력
// $(".idFindBtn").on("click", function () {
//   loadPageContent("/idFind"); // 아이디 찾기 페이지 URL
// });

// // 버튼 클릭 시 비밀번호 찾기 페이지 내용 출력
// $(".pwdFindBtn").on("click", function () {
//   loadPageContent("/pwdFind"); // 비밀번호 찾기 페이지 URL
// });

// // 페이지 내용 로드 함수
// function loadPageContent(pageUrl) {
//   $.ajax({
//     type: "GET",
//     url: pageUrl,
//     success: function (data) {
//       $("#pageContent").html(data); // 페이지 내용을 출력할 요소에 내용을 삽입
//     },
//     error: function (xhr, status, error) {
//       console.error("페이지를 불러오는데 실패했습니다.");
//     }
//   });
// }
