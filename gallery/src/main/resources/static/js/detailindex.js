// 사이트 로드 시 발동
$(document).ready(function () {
    $('#ticketType, #ticketEa, #adultEa, #teenEa, #kidEa, #totAmount, #selectEa1, #selectEa2, #selectEa3,.viewTotAmt').css("display", "none");
    $('#btnPayment').css('background-color', '#ddd');
    $('#btnPayment').prop('disabled', true);
    $('#btnRealPayment').prop('disabled', true);
    // $('#btnInfo').click();
    $('.totAmt').hide();
    $('.answerRegister').hide();
    if ($('#selectEa1').is(':hidden')) {
        $('.adultAmt').val('0');
    }
    if ($('.adultEa').val() == '0') {
        $('#adultTicketingInfo').hide();
    }
    if ($('#selectEa2').is(':hidden')) {
        $('.teenAmt').val('0');
    }
    if ($('.teenEa').val() == '0') {
        $('#teenTicketingInfo').hide();
    }
    if ($('#selectEa3').is(':hidden')) {
        $('.kidAmt').val('0');
    }
    if ($('.kidEa').val() == '0') {
        $('#kidTicketingInfo').hide();
    }
    payResult();
    // 카카오지도 초기화 함수
    function initializeMap() {
        var mapContainer = document.getElementById('map');
        var mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667),
            level: 3
        };
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 마커 초기화
        var marker = null;

        // 검색 버튼 클릭 시 실행할 함수
        $('#mapSee').click(function () {
            var location = document.frmIndex.location.value;

            // 주소 검색 및 마커 표시 함수
            searchAddressAndDisplayMarker(location);
        });

        // 주소로 좌표를 검색하고 마커를 표시하는 함수
        function searchAddressAndDisplayMarker(address) {
            var geocoder = new kakao.maps.services.Geocoder();
            geocoder.addressSearch(address, function (result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                    // 이전 마커 삭제
                    if (marker) {
                        marker.setMap(null);
                    }

                    // 결과값으로 받은 위치를 마커로 표시합니다
                    marker = new kakao.maps.Marker({
                        map: map,
                        position: coords
                    });

                    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                    map.setCenter(coords);
                }
            });
        }
    }

    // 카카오지도 초기화
    kakao.maps.load(initializeMap);
    $('#mapSee').click();


})






// 결제하기 활성화/비활성화
$('#btnOption').on('click', function () {
    $('#ticketType').show();
})

$('.price').click(function () {
    $(this).toggleClass('selected');

    if ($('.price.selected').length > 0) {
        $('#btnPayment').prop('disabled', false);
        $('#btnPayment').css({
            'background-color':'#576F72',
            'color':'#fff'
        });
        $('#ticketEa, .totAmt, .viewTotAmt').show();
    } else {
        $('#btnPayment').prop('disabled', true);
        $('#btnPayment').css('background-color', '#ddd');
        $('#ticketEa, .totAmt, .viewTotAmt').hide();
    }
})


//성인금액 계산
function adultCompute() {
    let a = $('.adult_price').val();
    let b = $('#adultEa').val();
    let c = a * b;
    $('.adultAmt').val(c);
    totAmt();
}

//청소년금액 계산
function teenCompute() {
    let a = $('.teen_price').val();
    let b = $('#teenEa').val();
    let c = a * b;
    $('.teenAmt').val(c);
    totAmt();
}

//어린이금액 계산
function kidCompute() {
    let a = $('.kid_price').val();
    let b = $('#kidEa').val();
    let c = a * b;
    $('.kidAmt').val(c);
    totAmt();
}

//총금액 계산
function totAmt() {
    let a = Number($('.adultAmt').val());
    let t = Number($('.teenAmt').val());
    let k = Number($('.kidAmt').val());
    let totAmt = a + t + k;
    let aEa = Number($('.adultEa').val());
    let tEa = Number($('.teenEa').val());
    let kEa = Number($('.kidEa').val());
    let totEa = aEa + tEa + kEa;


    $('.totAmt').val(totAmt);
    $('.totEa').val(totEa);
}



function payResult() {
    let cash = Number($('.cash').val());
    let totAmt = Number($('.totAmt').val());
    let payResult = cash - totAmt;
    let pr = cash - totAmt;
    let formattedResult = payResult.toLocaleString('ko-KR') + '원';
    $('.pr').val(pr);
    if (payResult < 0) {
        $('.payResult').val(formattedResult);
    } else {
        $('.payResult').val(formattedResult);
    }
}



// 수량 선택 버튼
$('#selectEa1 .minus').click(function () {
    if (count1 > 1) { count1 = count1 - 1; }
    $('#adultEa').val(count1);
    adultCompute();

})
$('#selectEa1 .plus').click(function () {
    count1 = count1 + 1;
    $('#adultEa').val(count1);
    adultCompute();
})
$('#selectEa2 .minus').click(function () {
    if (count2 > 1) { count2 = count2 - 1; }
    $('#teenEa').val(count2);
    teenCompute();
})
$('#selectEa2 .plus').click(function () {
    count2 = count2 + 1;
    $('#teenEa').val(count2);
    teenCompute();
})
$('#selectEa3 .minus').click(function () {
    if (count3 > 1) { count3 = count3 - 1; }
    $('#kidEa').val(count3);
    kidCompute();
})
$('#selectEa3 .plus').click(function () {
    count3 = count3 + 1;
    $('#kidEa').val(count3);
    kidCompute();
})

// 권종 선택 버튼
var count1 = 0;
var count2 = 0;
var count3 = 0;


$('#adult_price').on('click', function(){
    $('#adultEa').toggle();
    $('#selectEa1').toggle();
    let adult_price = $('.adult_price').val();

    if ($('#adultEa').is(':visible')) { //:visible = 화면에 보여지는 상태
        count1 = 1;
        $('#adultEa').val(count1);
        $('.adultAmt').val(adult_price)
    } else {
        count1 = 0;
        $('#adultEa').val(count1);
        $('.adultAmt').val(0);
    }
    totAmt();
})

$('#teen_price').on('click', function(){
    $('#teenEa').toggle();
    $('#selectEa2').toggle();
    let teen_price = $('.teen_price').val();

    if ($('#teenEa').is(':visible')) {
        count2 = 1;
        $('#teenEa').val(count2);
        $('.teenAmt').val(teen_price);
    } else {
        count2 = 0;
        $('#teenEa').val(count2);
        $('.teenAmt').val(0);
    }
    totAmt();
})

$('#kid_price').on('click', function(){
    $('#kidEa').toggle();
    $('#selectEa3').toggle();
    let kid_price = $('.kid_price').val();

    if ($('#kidEa').is(':visible')) {
        count3 = 1;
        $('#kidEa').val(count3);
        $('.kidAmt').val(kid_price);
    } else { 
        count3 = 0;
        $('#kidEa').val(count3);
        $('.kidAmt').val(0);
    }
    totAmt();
})


// detailindex에서 안내, 후기, QnA, 장소, 환불정책 클릭 시 이벤트
$('#btnDetailZone button').click(function () {
    // 모든 버튼의 효과를 지우고, 현재 클릭된 버튼만 활성화
    $('#btnDetailZone button').removeClass('active');
    $(this).addClass("active");
})

// 결제 동의 여부 체크박스 체크 시 결제버튼 활성화, 비체크 시 비활성화
$('#agree').on('change', function () {
    if ($('#agree').is(':checked')) {
        $('#btnRealPayment').prop('disabled', false);
        $('#btnRealPayment').css({
            'color':'#fff',
            'background-color':'#576F72'
        });
    } else {
        $('#btnRealPayment').prop('disabled', true);
        $('#btnRealPayment').css('color', '#ccc');
    }
})


// 이미지 펼쳐보기
$('#btnSee').click(function(){
    $('#btnSee').css('display', 'none');
    $("#imgCrop").css({
        'width':'1100px',
        'height':'auto',
        'text-align':'center'
    });
    $('#poster').css({
        'display':'block',
        'width':'1000px',
        'height':'100%'
    });
})


//  각각의 btnAnswer 버튼 클릭 시 qnaResult 영역toggle
$(".qnaResult .btnAnswer").click(function (event) {
    event.preventDefault();
    var qnaResult = $(this).closest(".qnaResult");
    var answerSection = qnaResult.find(".answerRegister");

    // 해당 qnaResult 내부의 answerRegister 토글
    answerSection.slideToggle();
});

/*------------------------------------------------------------- */


$('#btnRefund').click(function () {
    $('#result').load('/detailRefund');
})

//결제하기 버튼클릭!!
//이 페이지에 있는 form정보 serialize로 예매하기 페이지로 보내기
// if ($('.id').val() === "${sessionScope.id}") {

$('#btnPayment').click(function () {
    let param = $('#frmIndex').serialize();

    if ($('.checkLogin').val()==="") {            
         alert("로그인해야 구매 가능합니다");
        window.location.replace("/login");
    } else {
        $.post("/myDetailInfo", param, function (data) {
            $('.mainSection').html(data);
        });
    }
});


function reload() {
    $.post("/sectionTop", { id: "${sessionScope.id}" }, function (data1) {
        $('.customerMypage_header').html(data1)
    });
}
function reloadTicketView() {
    reload();
    $('.mainSection').load("/mypage", function () {
        reload();
        $.post("/ticketView", { id: "${sessionScope.id}" }, function (data2) {
            $('.customerMypage_right_wrap').html(data2)
        });
    });
}

$('#btnRealPayment').click(function () {
    if ($('.pr').val() < 0) {
        alert("잔액이 부족합니다");
    } else {
        let param = $('#frmPayment').serialize();
        $.ajax({
            type: 'GET',
            url: '/realPayment',
            processData: false,
            contentType: false,
            data: param,
            success: function (msg) {
                if (msg != '') {
                    alert(msg); //메시지 발생할때만 알람호출
                }
                reloadTicketView();
            }
        })
    }
})


//information
$('#btnInfo').click(function(){
    var gallery_num = document.frmIndex.gallery_num.value;

    $.ajax({
        url: '/detailInfo',
        method: 'POST',
        data: { gallery_num:gallery_num },
        success: function(response) {
            $('#result').html(response);
            console.log(response);
        },
        error: function(error) {
            console.error(error);
        }
    });
});


// 후기
$('#btnReview').click(function(){
    var gallery_num = document.frmIndex.gallery_num.value;
    $.ajax({
        url: '/detailReview',
        method: 'GET',
        data: { gallery_num:gallery_num},
        success: function(response) {
            $('#result').html(response);
            console.log(response);
        },
        error: function(error) {
            console.error(error);
        }
    });
});



// QnA
$('#btnQNA').click(function () {
    var gallery_num = document.frmIndex.gallery_num.value;

    $.ajax({
        url: '/detailQna',
        method: 'POST',
        data: { gallery_num: gallery_num },
        success: function (response) {
            $('#result').html(response);
            console.log(response);
        },
        error: function (error) {
            console.error(error);
        }
    });
});

$('#qnaRegister').click(function () {
    let gallery_num = document.frmIndex.gallery_num.value;

    if ($('.checkLogin').val()==="") {            
        alert("로그인해야 작성 가능합니다");
        window.location.replace("/login");
    }else{
        let nal = new Date().toLocaleString();
        let doc = $('#qnaContainer .doc').val();
        let temp = document.frmQna;
        temp.enctype = 'multipart/form-data';
        let frm = new FormData(temp);
        frm.append('nal', nal);
        frm.delete('doc'); // 답변의 내용의 name도 doc여서 삭제 후 추가함
        frm.append('doc', doc);
        frm.append('gallery_num', gallery_num);
        $.ajax({
            type: 'POST',
            url: '/qnaRegister',
            processData: false,
            contentType: false,
            data: frm,
            success: function (msg) {
                if (msg === 'QnA가 등록되었습니다.') {
                    alert(msg);
                } else {
                    alert(msg);
                }
            }
        }).done(function () {
            // AJAX 요청 완료 후 초기화
            $('#btnQNA').click();
        });
   }
});

$('.btnAnswerRegister').click(function (){
    let gallery_num = document.frmIndex.gallery_num.value;
    // 버튼을 클릭한 부모 .qnaResult 요소를 찾음
    let qnaResult = $(this).closest('.qnaResult');

    // .question 내부의 값을 가져옴
    let doc = qnaResult.find('.qnaDoc').val();
    let grp = qnaResult.find('.grp').val();

    console.log('doc:', doc);
    console.log('grp:', grp);
    if ($('.checkLogin').val()==="") {            
        alert("로그인해야 작성 가능합니다");
        window.location.replace("/login");
   }else{
        let nal = new Date().toLocaleString();
        let temp = document.frmQna;
        temp.enctype = 'multipart/form-data';
        let frm = new FormData(temp);
        frm.append('nal', nal);
        frm.delete('doc'); // 질문의 내용의 name도 doc여서 삭제 후 추가함
        frm.delete('grp');
        frm.append('doc', doc);
        frm.append('grp', grp);
        frm.append('gallery_num', gallery_num);
        $.ajax({
            type: 'POST',
            url: '/qnaAnswer',
            processData: false,
            contentType: false,
            data: frm,
            success: function (msg) {
                if (msg === '답변이 등록되었습니다.') {
                    alert(msg);
                }
            },
            error: function (error) {
                console.error(error);
            }
        }).done(function () {
            // AJAX 요청 완료 후 초기화
            $('#btnQNA').click();
        });
   }
})

// 장소
$('#btnLocation').click(function(){
   
    var gallery_num = document.frmIndex.gallery_num.value;

    $.ajax({
        url: '/detailLocation',
        method: 'POST',
        data: { gallery_num:gallery_num },
        success: function(response) {
            $('#result').html(response);
            console.log(response);
        },
        error: function(error) {
            console.error(error);
        }
    });
});
