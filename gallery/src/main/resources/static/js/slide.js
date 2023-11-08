function Slider(target, type) {
  // 상태
  let index = 1;
  let isMoved = true;
  const speed = 1000; // ms

  // 방향
  const transform = "transform " + speed / 1000 + "s";
  let translate = (i) => "translateX(-" + 100 * i + "%)";

  // 슬라이더
  const slider = document.querySelector("#slider");
  const sliderRects = slider.getClientRects()[0];
  slider.style["overflow"] = "hidden";

  // 슬라이더 화면 컨테이너
  const container = document.createElement("div");
  container.style["display"] = "flex";
  container.style["width"] = sliderRects.width + "px";
  container.style["height"] = sliderRects.height + "px";
  container.style["transform"] = translate(index);

  // 슬라이더 화면 목록
  let boxes = [].slice.call(slider.children);
  boxes = [].concat(boxes[boxes.length - 1], boxes, boxes[0]);

  // 슬라이더 화면 스타일
  const size = boxes.length;
  for (let i = 0; i < size; i++) {
    const box = boxes[i];
    box.style["flex"] = "none";
    box.style["flex-wrap"] = "wrap";
    box.style["height"] = "100%";
    box.style["width"] = "100%";
    container.appendChild(box.cloneNode(true));
  }

  // 처음/마지막 화면 눈속임 이벤트
  container.addEventListener("transitionstart", function () {
    isMoved = false;
    setTimeout(() => {
      isMoved = true;
    }, speed);
  });
  container.addEventListener("transitionend", function () {
    // 처음으로 순간이동
    if (index === size - 1) {
      index = 1;
      container.style["transition"] = "none";
      container.style["transform"] = translate(index);
    }
    // 끝으로 순간이동
    if (index === 0) {
      index = size - 2;
      container.style["transition"] = "none";
      container.style["transform"] = translate(index);
    }
  });

  // 슬라이더 붙이기
  slider.innerHTML = "";
  slider.appendChild(container);

  return {
    move: function (i) {
      if (isMoved === true) {
        index = i;
        container.style["transition"] = transform;
        container.style["transform"] = translate(index);
      }
    },
    next: function () {
      if (isMoved === true) {
        index = (index + 1) % size;
        container.style["transition"] = transform;
        container.style["transform"] = translate(index);
      }
    },
    prev: function () {
      if (isMoved === true) {
        index = index === 0 ? index + size : index;
        index = (index - 1) % size;
        container.style["transition"] = transform;
        container.style["transform"] = translate(index);
      }
    }
  };
}

const s1 = new Slider("#slider1", "H");

setInterval(() => {
  s1.next();
}, 1000)



// 밑으로 가는 버튼
document.addEventListener('DOMContentLoaded', function() {
  var scrollDownBtn = document.getElementById('scrollDownBtn');
  
  window.addEventListener('scroll', function() {
      // 현재 스크롤 위치
      var scrollPosition = window.scrollY || window.pageYOffset;
      
      // 페이지 내용의 높이
      var contentHeight = document.documentElement.scrollHeight;
      
      // 브라우저 높이
      var windowHeight = window.innerHeight;
      
      // 스크롤이 맨 밑으로 갔을 때 버튼 숨김
      if (scrollPosition + windowHeight >= contentHeight) {
          scrollDownBtn.style.opacity = 0;
      } else {
          scrollDownBtn.style.opacity = 1;
      }
  });
});