Kakao.init('71f4ab9b24af388a3f539c1a32366920'); //발급받은 키 중 javascript키를 사용해준다.
//카카오로그인
function kakaoLogin() {
    Kakao.Auth.login({
      success: function (response) {
        Kakao.API.request({
          url: '/v2/user/me',
          success: function (response) {
        	  // 로그인 성공 후 인덱스 페이지로 이동
          window.location.href = '/';
          },
          fail: function (error) {
          },
        })
      },
      fail: function (error) {
      },
    })
  }
//카카오로그아웃  
function kakaoLogout() {
    if (Kakao.Auth.getAccessToken()) {
      Kakao.API.request({
        url: '/v1/user/unlink',
        success: function (response) {
        },
        fail: function (error) {
        },
      })
      Kakao.Auth.setAccessToken(undefined)
    }
  }  ``