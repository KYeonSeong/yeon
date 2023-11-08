// 게시판 목록 내리면 따라오게 하기
window.addEventListener("scroll",function(){
    let boardAside = document.querySelector(".boardAside");
    let boardSection = document.querySelector(".boardSection");

    // boardAside와 boardSection 현재 위치 높이
    let boardAsideRect = boardAside.getBoundingClientRect();
    let boardSectionRect = boardSection.getBoundingClientRect();

    // 현재 스크롤 위치
    let scrollY = window.scrollY || window.pageYOffset;

    // section 영역의 높이
    let boardSectionHeight = boardSectionRect.height;

    // 스크롤 위치에 따라 aside 위치 조정
    if(scrollY < boardSectionHeight - boardAsideRect.height){
        boardAside.style.top = scrollY + "px";
    }else{
        boardAside.style.top = boardSectionHeight - boardAsideRect.height + "px";
    }
});

// 자유글 해당 게시글의 상세페이지로
function freeView(sno){
    let temp = document.frmFree;    
    temp.sno.value = sno;
    let param = $(".frmFree").serialize();
    $(".boardSection").load("/freeView", param);
}

// 후기글 해당 게시글의 상세페이지로
function reviewView(sno){
    let temp = document.frmReview;
    temp.sno.value = sno;
    let param = $(".frmReview").serialize();
    $(".boardSection").load("/reviewView", param);
}

// 자유글 페이지 이동
function move(nowPage){
    let frm = document.frmFree;
    frm.nowPage.value = nowPage;
    let param = $(frm).serialize();
    $(".boardSection").load("/freeBoardList", param);
}

// 후기글 페이지 이동
function move2(nowPage){
    let frm = document.frmReview;
    frm.nowPage.value = nowPage;
    let param = $(frm).serialize();
    $(".boardSection").load("/reviewBoardList", param);
}

// 게시글의 비밀번호가 맞다면 해당 자유글 수정페이지로
function freeBoardModify(){
    let param = $(".frmFree").serialize();
    $(".boardSection").load("/goFreeModify", param);
}

// 게시글의 비밀번호가 맞다면 해당 자유글 삭제
function freeBoardDelete(){
    let param = $(".frmFree").serialize();
    $.get("/freeDelete", param, function(msg){
        if(msg != '') alert(msg);
        $(".boardSection").load("/freeBoardList", param);
    })
}

// 첨부파일 삭제일 시 표시
function delCheck(box){
    let p = box.parentNode;
    if(box.checked){
        p.style.textDecoration="line-through";
        p.style.color="#f00";
    }else{
        p.style.textDecoration="none";
        p.style.color="";
    }
}

// 자동으로 댓글 입력란 및 출력란의 높이 조절
function AutoDocHeight(textarea){
    textarea.style.height = "auto";
    textarea.style.height = (textarea.scrollHeight) + 'px'; // 스크롤 높이로 설정
}

$(document).ready(function(){
    /*--------------------------------------자유 게시판--------------------------------------*/
    // Aside영역 자유 게시판 버튼
    $('.boardAside').on('click', '.freeBtn', function(){
        $('.boardSection').load('/freeBoardList');
    })

    // Section영역 자유 게시판 목록 버튼
    $('.boardSection').on('click', '.freeBtn', function () {
        $('.boardSection').load('/freeBoardList');
    })

    $(".boardSection").on("click", ".btnFreeFind", function(){
        move(1);
    })

    // 자유글 쓰기 들어가기
    $(".boardSection").on("click", ".goFreeRegister", function(){
        let frm = document.frmFree;
        let param= $(frm).serialize();
        let nal = new Date().toLocaleString();
        param += "&nal=" + nal;
        $(".boardSection").load("/goFreeRegister");
    })


    // 자유글 작성 후 등록
    $(".boardSection").on("click", ".btnFreeRegister", function(){
        let temp = document.frmFree;

        if(!temp.subject.value){
            alert("제목을 입력해주세요");
            return;
        }
        else if(!temp.doc.value){
            alert("내용을 입력해주세요");
            return;
        }
        else if(!temp.freePwd.value){
            alert("게시글 비밀번호를 입력해주세요");
            return;
        }

        temp.enctype="multipart/form-data";
        let frm = new FormData(temp);
        $.ajax({
            type : "POST",
            url : "/freeRegister",
            contentType : false,
            processData : false,
            data : frm,
            success : function(msg){
                if(msg !="") alert(msg);
                $(".freeBtn").click();                
            }
        })
    })

    // 자유글 수정버튼
    $(".boardSection").on("click", ".goFreeModify", function(){
        $("#modal").fadeIn();
        $("#modal").attr("data-action", "modify"); // 작업 구분 속성
    })

    // 자유글 삭제버튼
    $(".boardSection").on("click", ".btnFreeDelete", function(){
        $("#modal").fadeIn();
        $("#modal").attr("data-action", "delete"); // 작업 구분 속성
    })

    // modal 창 닫기
    $(".boardSection").on("click", ".btnClose", function(){
        $("#modal").fadeOut();
    })

    // 암호 확인 버튼 클릭 시
    $(".boardSection").on("click", ".btnCheck", function(){
        let pwdCheck = $(".pwdCheck").val();
        let freePwd = $(".freePwd").val();
        let action = $("#modal").attr("data-action"); // 수정, 삭제 작업

        if(pwdCheck==freePwd){
            if (action=="delete"){
                freeBoardDelete();
            }else if (action=="modify"){
                freeBoardModify();
            }
            $("#modal").fadeOut(); // 모달 창 닫기
        }else{
            alert("해당 게시글의 비밀번호와 일치하지 않습니다");
        }
    })

    // 자유글 수정
    $(".boardSection").on("click", ".btnFreeModify" ,function(){
        let temp = document.frmFree;

        if(!temp.subject.value){
            alert("제목을 입력해주세요");
            return;
        }
        else if(!temp.doc.value){
            alert("내용을 입력해주세요");
            return;
        }

        temp.enctype="multipart/form-data";
        let frm = new FormData(temp);
        $.ajax({
            type : "POST",
            url : "/freeModify",
            contentType : false,
            processData : false,
            data : frm,
            success : function(msg){
                if(msg !="") alert(msg);
                move(temp.nowPage.value);
            }
        })
    })

    // 자유글 댓글 달기
    $(".boardSection").on("click", ".btnFreeRepl", function(){
        let frm = document.frmFreeRepl;

        if(!frm.doc.value){
            alert("내용을 입력해주세요");
            return;
        }

        let formData = new FormData(frm);

        $.ajax({
            type : "POST",
            url : "/freeRepl",
            contentType : false,
            processData : false,
            data : formData,
            success: function(msg){
                if(msg !== "") alert(msg);
                freeView(frm.freeBoardSno.value);
            }
        })
    })

    // 자유글 댓글의 답글 폼 띄우기
    $(".boardSection").on("click", ".btnFreeReplReply", function(){
        let replSno = $(this).attr("data-repl-sno"); // 선택한 댓글의 sno 값
        let frmFreeReplR = $(`.frmFreeReplR[data-repl-sno='${replSno}']`);
    
        // 다른 답글 폼은 숨기기
        $(".frmFreeReplR").not(frmFreeReplR).hide();

        // 선택한 답글 폼 토글
        frmFreeReplR.toggle();
    })

    // 답글쓰기
    $(".boardSection").on("click", ".btnFreeReplR", function () {
        let frm = $(this).closest(".frmFreeReplR");
        let doc = frm.find(".doc");

        if (!doc.val()) {
            alert("내용을 입력해주세요");
            return;
        }

        let deep = parseInt(frm.find("input[name='deep']").val());

        // 답글 깊이가 8을 초과하는 경우 오류 메시지 표시
        if (deep >= 8) {
            alert("답글을 더이상 작성할 수 없습니다");
            frm.hide();
            doc.val(""); // doc 내용 지우기
            return;
        }

        let formData = new FormData();

        // 수정된 부분: 필요한 데이터 수동으로 FormData에 추가
        formData.append("doc", doc.val());
        formData.append("id", frm.find("input[name='id']").val());
        formData.append("freeBoardSno", frm.find("input[name='freeBoardSno']").val());
        formData.append("grp", frm.find("input[name='grp']").val());
        formData.append("seq", frm.find("input[name='seq']").val());
        formData.append("deep", frm.find("input[name='deep']").val());

        $.ajax({
            type: "POST",
            url: "/freeReplR",
            contentType: false,
            processData: false,
            data: formData,
            success: function (msg) {
                if (msg !== "") alert(msg);
                freeView(frm.find("[name=freeBoardSno]").val());
            }
        })
    })

    //자유글 댓글, 답글 수정하기 버튼
    $(".boardSection").on("click", ".goFreeReplModify", function(){
        let frm = $(this).closest(".replList");
        let replDoc = frm.find(".doc");
        let ReplModify = frm.find(".btnFreeReplModify");
        let ReplDelete = frm.find(".btnFreeReplDelete");
        let ReplCancel = frm.find(".goFreeReplModifyCancel");

        // 내용 저장
        frm.data("originalDoc", replDoc.val());

        // 텍스트 상자 readonly 속성 해제
        replDoc.prop("readonly", false);

        // "수정"과 "삭제" 버튼 활성화
        ReplModify.show();
        ReplDelete.show();
        ReplCancel.show()
        $(this).hide(); // 수정하기 버튼 감춤
    })

    // 자유글 댓글, 답글 취소 버튼
    $(".boardSection").on("click", ".goFreeReplModifyCancel", function(){
        let frm = $(this).closest(".replList");
        let replDoc = frm.find(".doc");
        let ReplModify = frm.find(".btnFreeReplModify");
        let ReplDelete = frm.find(".btnFreeReplDelete");
        let ReplCancel = frm.find(".goFreeReplModifyCancel");

        // 원래 내용 다시 나오게 하기
        replDoc.val(frm.data("originalDoc"));

        // 텍스트 상자 readonly 속성 설정
        replDoc.prop("readonly", true);

        // "수정"과 "삭제" 버튼 숨기기
        ReplModify.hide();
        ReplDelete.hide();
        ReplCancel.hide(); // 취소 버튼 숨기기
        frm.find(".goFreeReplModify").show(); // 수정하기 버튼 보이기
    })

    // 자유글 댓글, 답글 수정
    $(".boardSection").on("click", ".btnFreeReplModify", function(){
        let frm = $(this).closest(".replList"); // 가장 가까운 .replList 엘리먼트 선택
        let docCheck = frm.find(".doc");

        if(!docCheck.val()){
            alert("내용을 입력해주세요");
            return;
        }

        let formData = new FormData(frm[0]); // 첫 번째 엘리먼트를 FormData에 전달
        $.ajax({
            type: "POST",
            url: "/freeReplModify",
            contentType: false,
            processData: false,
            data: formData,
            success: function(msg){
                if (msg !== "") alert(msg);
                freeView(frm.find("[name=freeBoardSno]").val()); // grp 값을 찾아서 사용
        }
        })
    })

    // 자유글 댓글, 답글 삭제
    $(".boardSection").on("click", ".btnFreeReplDelete", function(){
        let frm = $(this).closest(".replList"); // 가장 가까운 .replList 엘리먼트 선택
        let param = frm.serialize(); // 폼 데이터 직렬화
        $.post("/freeReplDelete", param, function(msg){
            if (msg !== "") alert(msg);
            freeView(frm.find("[name=freeBoardSno]").val()); // grp 값을 찾아서 사용
        })
    })
 
    /*--------------------------------------후기 게시판--------------------------------------*/

    // Aside영역 후기 게시판 버튼
    $(".boardAside").on("click", ".reviewBtn", function(){
        $(".boardSection").load("/reviewBoardList");
    })


    

    // Section영역 후기 게시판 목록 버튼
    $(".boardSection").on("click", ".reviewBtn", function(){
        $(".boardSection").load("/reviewBoardList");
    })
       
    $(".boardSection").on("click", ".btnReviewFind", function(){
        move2(1);
    })
    
    // 후기글 쓰기 들어가기
    $(".boardSection").on("click", ".goReviewRegister", function(){
        let frm = document.frmReview;
        let param= $(frm).serialize();
        let nal = new Date().toLocaleString();
        param += "&nal=" + nal;
        $(".boardSection").load("/goReviewRegister");
    })
    
    // 후기글 작성 후 등록
    $(".boardSection").on("click", ".btnReviewRegister", function(){
        let temp = document.frmReview;
    
        if(!temp.subject.value){
            alert("제목을 입력해주세요");
            return;
        }
        else if(!temp.doc.value){
            alert("내용을 입력해주세요");
            return;
        }
        else if(!temp.payNum.value){
            alert("후기를 작성할 전시회 구매내역을 선택해주세요");
            return;
        }
    
        temp.enctype="multipart/form-data";
        let frm = new FormData(temp);
        $.ajax({
            type : "POST",
            url : "/reviewRegister",
            contentType : false,
            processData : false,
            data : frm,
            success : function(msg){
                if(msg !="") alert(msg);
                $(".reviewBtn").click();
            }
        })
    })
    
    // 후기글 수정 페이지
    $(".boardSection").on("click", ".goReviewModify", function(){
        let param = $(".frmReview").serialize();
        $(".boardSection").load("/goReviewModify", param);
    })
    
    // 후기글 수정
    $(".boardSection").on("click", ".btnReviewModify", function(){
        let temp = document.frmReview;
    
        if(!temp.subject.value){
            alert("제목을 입력해주세요");
            return;
        }
        else if(!temp.doc.value){
            alert("내용을 입력해주세요");
            return;
        }
    
        temp.enctype="multipart/form-data";
        let frm = new FormData(temp);
        $.ajax({
            type : "POST",
            url : "/reviewModify",
            contentType : false,
            processData : false,
            data : frm,
            success : function(msg){
                if(msg !="") alert(msg);
                move2(temp.nowPage.value);
            }
        })
    })
    
    // 후기글 삭제
    $(".boardSection").on("click", ".btnReviewDelete", function(){
        let param = $(".frmReview").serialize();
        $.get("/reviewDelete", param, function(msg){
            if(msg != '') alert(msg);
            $(".boardSection").load("/reviewBoardList", param);
        })
    })
    
    // 후기글 댓글 달기
    $(".boardSection").on("click", ".btnReviewRepl", function(){
        let frm = document.frmReviewRepl; // 폼 요소를 직접 참조
    
        if(!frm.doc.value){
            alert("내용을 입력해주세요");
            return;
        }
    
        let formData = new FormData(frm);
        $.ajax({
            type : "POST",
            url : "/reviewRepl",
            contentType : false,
            processData : false,
            data : formData,
            success: function(msg){
                if(msg !== "") alert(msg);
                reviewView(frm.sno.value);
            }
        })
    });
    
    // 후기글 댓글 수정하기 버튼
    $(".boardSection").on("click", ".goReviewReplModify", function(){
        let frm = $(this).closest(".replList");
        let replDoc = frm.find(".doc");
        let ReplModify = frm.find(".btnReviewReplModify");
        let ReplDelete = frm.find(".btnReviewReplDelete");
        let ReplCancel = frm.find(".goReviewReplModifyCancel");
    
        // 내용 저장
        frm.data("originalDoc", replDoc.val());
    
        // 텍스트 상자 readonly 속성 해제
        replDoc.prop("readonly", false);
    
        // "수정"과 "삭제" 버튼 활성화
        ReplModify.show();
        ReplDelete.show();
        ReplCancel.show()
        $(this).hide(); // 수정하기 버튼 감춤
    })
    
    // 후기글 댓글 수정 취소 버튼
    $(".boardSection").on("click",".goReviewReplModifyCancel", function(){
        let frm = $(this).closest(".replList");
        let replDoc = frm.find(".doc");
        let ReplModify = frm.find(".btnReviewReplModify");
        let ReplDelete = frm.find(".btnReviewReplDelete");
        let ReplCancel = frm.find(".goReviewReplModifyCancel");
    
        // 원래 내용 다시 나오게 하기
        replDoc.val(frm.data("originalDoc"));
    
        // 텍스트 상자 readonly 속성 설정
        replDoc.prop("readonly", true);
    
        // "수정"과 "삭제" 버튼 숨기기
        ReplModify.hide();
        ReplDelete.hide();
        ReplCancel.hide(); // 취소 버튼 숨기기
        frm.find(".goReviewReplModify").show(); // 수정하기 버튼 보이기
    });
    
    // 후기글 댓글 수정
    $(".boardSection").on("click", ".btnReviewReplModify", function(){
        let frm = $(this).closest(".replList"); // 가장 가까운 .replList 엘리먼트 선택
        let docCheck = frm.find(".doc");
    
        if (!docCheck.val()) {
            alert("내용을 입력해주세요");
            return;
        }
    
        let formData = new FormData(frm[0]); // 첫 번째 엘리먼트를 FormData에 전달
        $.ajax({
            type: "POST",
            url: "/reviewReplModify",
            contentType: false,
            processData: false,
            data: formData,
            success: function(msg){
                if (msg !== "") alert(msg);
                reviewView(frm.find("[name=grp]").val()); // grp 값을 찾아서 사용
            }
        })
    })
    
    // 후기글 댓글 삭제
    $(".boardSection").on("click", ".btnReviewReplDelete", function(){
        let frm = $(this).closest(".replList"); // 가장 가까운 .replList 엘리먼트 선택
        let param = frm.serialize(); // 폼 데이터 직렬화
        $.post("/reviewReplDelete", param, function(msg){
            if (msg !== "") alert(msg);
            reviewView(frm.find("[name=grp]").val()); // grp 값을 찾아서 사용
        });
    })

    /*--------------------------------------자유와 후기 공통 사용--------------------------------------*/
    // 파일 첨부관련
    $(".boardSection").off("change").on("change", ".attFile", function(){
        let fileInput = $(this)[0];
        let fileText = $(this).closest(".fileUpload").find(".fileText")[0];
    
        if(fileInput.files.length > 0){
            if(fileInput.files.length == 1){
                fileText.textContent = fileInput.files[0].name;
            }else{
                fileText.textContent = `첨부사진 ${fileInput.files.length}개를 선택함`;
            }
        }else{
            fileText.textContent = "첨부";
        }
    })

    // 댓글 입력란의 높이 조절 이벤트 리스너 추가
    $(".boardSection").on("input", ".doc", function () {
        AutoDocHeight(this);
    })
});
