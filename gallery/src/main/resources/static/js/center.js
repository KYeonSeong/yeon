
function imove(nowPage) {
    let frm = document.inquriyForm;
    frm.nowPage.value = nowPage;
    let param = $(frm).serialize();
    $('.centerSection').load("/inquriy", param);
}

function nmove(nowPage) {
    let frm = document.noticeForm;
    frm.nowPage.value = nowPage;
    let param = $(frm).serialize();
    $('.centerSection').load("/notice", param);
}

function fmove(nowPage) {
    let frm = document.faqForm;
    frm.nowPage.value = nowPage;
    let param = $(frm).serialize();
    $('.centerSection').load("/FAQ", param);
}

$(document).ready(function () {

    $('#exampleTextarea').on('input', function () {
        var content = $(this).val();
        var format = content.replace(/\n/g, '<br>');
        $('#centerContent').html(format);
    });

    // ----------------공지사항-----------------
    // 공지 목록
    $('.centerSection').on('click', '.noticeBtn', function () {
        $('.centerSection').load('/notice');
    })

    $('.centerAside').on('click', '.noticeBtn', function () {
        $('.centerSection').load('/notice');
    })


    // 공지 작성
    $('.centerSection').on('click', '.noticeRBtn', function () {
        $('.centerSection').load('/noticeRegister');
    })

    //공지 등록
    $('.centerSection').on('click', '.noticeRegisterBtn', function () {
        var subject = $('.nSub').val();
        var doc = $('.nDoc').val();

        $.ajax({
            type: 'POST',
            url: '/noticeR',   //controller에서 확인
            data: { subject: subject, doc: doc },
            success: function (msg) {
                $('.centerSection').load('/notice');
            }
        });
    })

    // 공지 상세보기
    $('.centerSection').on('click', '.nlist_content', function () {
        $('#noticeForm').serialize();
        var sno = $(this).find('.nNo').text();
        $('.centerSection').load('/noticeView?sno=' + sno);

        $.ajax({
            type: 'GET',
            url: '/noticeView',
            data: { sno: parseInt(sno) },
            dataType: 'JSON',
            success: function (data) {
            }
        });
    });

    // 공지수정
    $('.centerSection').on('click', '.noticeModify', function () {
        $('#noticeViewForm').serialize();
        var sno = $('.nqv_sno').val();
        $('.centerSection').load('/noticeModify?sno=' + sno);

        $.ajax({
            type: 'GET',
            url: '/noticeModify',
            data: { sno: parseInt(sno) },
            dataType: 'JSON',
            success: function (data) {
            }
        });

    })

    $('.centerSection').on('click', '.noticeModifyBtn', function () {
        var subject = $('.nmSub').val();
        var doc = $('.nmDoc').val();
        var sno = $('.nmqv_sno').val()

        $.ajax({
            type: 'POST',
            url: '/nMod',
            data: { sno: parseInt(sno), subject: subject, doc: doc },
            success: function (data) {
                $('.centerSection').load('/notice');
            }
        });
    })


    // 공지 삭제
    $('.centerSection').on('click', '.noticeDel', function () {
        var sno = $('.nqv_sno').val();

        $.ajax({
            type: 'POST',
            url: '/nDel',
            data: { sno: parseInt(sno) },
            success: function (msg) {
                $('.centerSection').load('/notice');
            }
        });
    })


    // ----------------자주 묻는 질문--------------
    // FAQ 목록
    $('.centerSection').on('click', '.FAQBtn', function () {
        $('.centerSection').load('/FAQ');

    })
    $('.centerAside').on('click', '.FAQBtn', function () {
        $('.centerSection').load('/FAQ');
    })

    // FAQ 작성
    $('.centerSection').on('click', '.FAQRBtn', function () {
        $('.centerSection').load('/faqRegister');
    })

    // FAQ 등록
    $('.centerSection').on('click', '.faqRegisterBtn', function () {
        var subject = $('.fSub').val();
        var doc = $('.fDoc').val();

        $.ajax({
            type: 'POST',
            url: '/faqR',   //controller에서 확인
            data: { subject: subject, doc: doc },
            success: function (data) {
                $('.centerSection').load('/FAQ');
            }
        });
    })

    // FAQ상세보기
    $('.centerSection').on('click', '.flist_content', function () {
        $('#faqForm').serialize();
        var sno = $(this).find('.fNo').text();
        $('.centerSection').load('/faqView?sno=' + sno);

        $.ajax({
            type: 'GET',
            url: '/faqView',
            data: { sno: parseInt(sno) },
            dataType: 'JSON',
            success: function (data) {
            }
        });
    });


    // FAQ 수정
    $('.centerSection').on('click', '.faqModify', function () {
        $('#faqViewForm').serialize();
        var sno = $('.fqv_sno').val();
        $('.centerSection').load('/faqModify?sno=' + sno);

        $.ajax({
            type: 'GET',
            url: '/faqModify',
            data: { sno: parseInt(sno) },
            dataType: 'JSON',
            success: function (data) {
            }
        });

    })

    $('.centerSection').on('click', '.faqModifyBtn', function () {
        var subject = $('.fmSub').val();
        var doc = $('.fmDoc').val();
        var sno = $('.fmqv_sno').val()

        $.ajax({
            type: 'POST',
            url: '/fMod',
            data: { sno: parseInt(sno), subject: subject, doc: doc },
            success: function (data) {
                $('.centerSection').load('/FAQ');
            }
        });
    })


    // FAQ 삭제
    $('.centerSection').on('click', '.faqDel', function () {
        var sno = $('.fqv_sno').val();

        $.ajax({
            type: 'POST',
            url: '/fDel',
            data: { sno: parseInt(sno) },
            success: function (msg) {
                $('.centerSection').load('/FAQ');
            }
        });
    })


    // -----------------1:1 문의------------------
    // 1:1 목록 aside
    $('.centerAside').on('click', '.inquriyBtn', function () {
        $('.centerSection').load("/inquriy");
    })
    // 1:1 목록 section
    $('.centerSection').on('click', '.inquriyBtn', function () {
        $('.centerSection').load("/inquriy");

    })



    // 1:1문의 작성
    $('.centerSection').on('click', '.inquriyRBtn', function () {
        $('.centerSection').load('/inquriyRegister')
    })

    // 1:1 문의 등록 버튼
    $('.centerSection').on('click', '.inquriyRegisterBtn', function () {

        var subject = $('.inSub').val();
        var tel = $('.inTel').val();
        var doc = $('.inDoc').val();

        if (/^\d{10,11}$/.test(tel)) {
            $.ajax({
                type: 'POST',
                url: '/inquriyR',   //controller에서 확인
                data: { subject: subject, tel: tel, doc: doc },
                success: function (data) {
                    $('.centerSection').load('/inquriy');
                }
            });
        } else {
            alert("연락처는 10-11자리 숫자만 가능합니다")
        }
    });



    // inquriy 상세보기
    $('.centerSection').on('click', '.ilist_content', function () {
        $('#inquriyForm').serialize();
        var sno = $(this).find('.iNo').text();

        $('.centerSection').load('/inquriyView?sno=' + sno);

        $.ajax({
            type: 'GET',
            url: '/inquriyView',
            data: { sno: parseInt(sno) },
            dataType: 'JSON',
            success: function (data) {
            }
        });
    });

    // 1:1 답변 달기 (admin)
    $('.centerSection').on('click', '.inquriyRepl', function () {
        var sno = $('.iqvsno').text();
        $('.centerSection').load('/inquriyReply?sno=' + parseInt(sno));
    });

    // admin 답변 등록 버튼 클릭 시
    $('.centerSection').on('click', '.adminRegisterBtn', function () {
        var sno = $('.ivrsno').text();
        var answer = $('.admin_ta').val();

        $.ajax({
            type: 'POST',
            url: '/inquriyRepl',
            data: { sno: parseInt(sno), answer: answer },
            success: function (data) {
                $('.centerSection').load('/inquriy');
            }
        });
    });

    // 1:1 삭제

    $('.centerSection').on('click', '.inquriyDel', function () {
        $('#inquriyViewForm').serialize();
        var sno = $('.iqvsno').text();

        $.ajax({
            type: 'POST',
            url: '/iDel',
            data: { sno: parseInt(sno) },
            success: function (msg) {
                $('.centerSection').load('/inquriy');
            }
        });
    })
});



