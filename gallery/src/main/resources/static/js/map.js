// map.js 파일
$(document).ready(function () {
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
        $('#locationRegister').click(function () {
            var location = document.getElementById('location').value;

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
                } else {
                    // 오류 처리
                    alert('주소 검색에 실패했습니다.');
                }
            });
        }
    }

    // 카카오지도 초기화
    kakao.maps.load(initializeMap);
});
