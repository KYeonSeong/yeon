// 홈버튼 클릭시 메인화면으로
$('.logoBtn').on('click', function () {
    location.href = "/";
})

// 검색 버튼
$('.searchBtn').on('click', function () {
    var findStr = $(".searchBox").val();

    if (findStr.trim() !== "") {
        // AJAX 요청 보내기
        $.ajax({
            type: "GET",
            url: "/searchView",
            data: { findStr: findStr },
            success: function (data) {
                $(".mainSection").html(data);
                $('.art').on('click', function () {
                    let gallery_num = $(this).find("#gallery_num").val();
                    let param = "gallery_num=" + gallery_num;
                    $.post("/detailPage", param, function (data) {
                        $(".mainSection").html(data);
                    });
                });
            }
        });
    } else {
        $(".mainSection").load("/searchView", function () {
            $('.art').on('click', function () {
                let gallery_num = $(this).find("#gallery_num").val();
                let param = "gallery_num=" + gallery_num;
                $.post("/detailPage", param, function (data) {
                    $(".mainSection").html(data);
                });
            })
        });
    }
});

//사진 클릭 시 해당 정보로 이동


$('.art').on('click', function () {
    let gallery_num = $(this).find("#gallery_num").val();
    let param = "gallery_num=" + gallery_num;
    // param += "&id=" + "${sessionScope.id}"; 
    // console.log(param) 
    // $(".mainSection").load("/detailPage")
    $.post("/detailPage", param, function (data) {
        $(".mainSection").html(data);
    });
})


//-------하단---------
$('.noticeBtn').on('click', function () {
    $(".mainSection").load("/centerMain", function () {
        $(".centerSection").load("/notice"); // 공지사항
    });
})

$('.FAQBtn').on('click', function () {
    $(".mainSection").load("/centerMain", function () {
        $(".centerSection").load("/FAQ"); //자주 묻는 질문
    })
})

$('.inquriyBtn').on('click', function () {
    $(".mainSection").load("/centerMain", function () {
        $(".centerSection").load("/inquriy"); //고객센터 1:1

    })
})

$('.centerBtn').on('click', function () {
    $(".mainSection").load("/centerMain", function () {
        $(".centerSection").load("/FAQ"); //자주 묻는 질문
    });
})
//----------연성****로그인------------
// 버튼 클릭 시 로그인 화면으로 
$(".loginBtn").on("click", function () {
    location.href = "login";
})

// 버튼 클릭 시 회원가입 화면으로
$(".signBtn").on("click", function () {
    location.href = "/joinMemberShip";
})

// 로그아웃 버튼
$(".btnLogout").on("click", function () {
    $.post("/logoutR", null, function (msg){
        location.href = "/";

    } )
    })


/* 대엽*********메인페이지 스크립트 */

function reload() {
    $.post("/sectionTop", { id: "${sessionScope.id}" }, function (data1) {
        $('.customerMypage_header').html(data1)
    });
}

$('.myPageBtn').on('click', function () {
    $.post("/checkUserRole", function (data) {
        if (data.role === 1) {
            // 권한이 1인 경우 mypage 화면을 불러옴
            $('.mainSection').load("/mypage", function () {
                reload();
                $.post("/ticketView", { id: "${sessionScope.id}" }, function (data2) {
                    $('.customerMypage_right_wrap').html(data2)
                });
            });
        } if (data.role === 2) {
            //sellerpage 화면을 불러옴
            $('.mainSection').load("/sellerpage");
        }
    });
});






//----------------------게시판 연결----------------------
//게시판 연결
$('.freeBtn').on('click', function () {
    $(".mainSection").load("/boardMain", function () {
        $(".boardSection").load("/freeBoardList"); //상단 고객센터 메인
    })
})

$('.reviewBtn').on('click', function () {
    $(".mainSection").load("/boardMain", function () {
        $(".boardSection").load("/reviewBoardList");
    })
})



