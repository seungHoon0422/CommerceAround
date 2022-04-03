<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ include
file="/template/header.jsp" %>
<script
  type="text/javascript"
  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=11cdde848f21d2505c9c25681fd8d6e1"
></script>
<c:if test="${!empty msg}">
  <script>
    alert("${msg}");
    //location.href = "${root}/member";
  </script>
</c:if>
<script>
  var isLogin = false;
</script>
<c:if test="${!empty memberInfo}">
  <script>
    isLogin = true;
  </script>
</c:if>
<c:if test="${empty dongCode || empty largeCode}">
  <script>
    alert("지역과 업종을 선택하세요.");
    location.href = "${root}/member";
  </script>
</c:if>

<script>
  var map;
  var markers = []; //마커 제거를 위해 현재 마커들 저장해놓음
  var storeData;
  var totalData = 0;

<<<<<<< HEAD
  var interestedJson;

  const countPerPage = 15;
  const naviSize = 10;
=======
const countPerPage = 15;
const naviSize = 10;
>>>>>>> 1010c7761ecb80260ef02023ea8c6a61b9efb3ff


  $(function () {
  	console.log("window loaded");

<<<<<<< HEAD
  	if (isLogin) {
  		console.log("flag!!");
  		$("#mapDiv").attr('class','container col-sm-8');
  		$("#tableDiv").attr('style', "display: ");
  		$.ajax({
  			url: '${root}/interested',
  			data: {
  				action : 'list',
  				id : '\${memberInfo}.id',
  				dongCode : '${dongCode}',
  				largeCode : '${largeCode}',
  			},
  			type: 'GET',
  			dataType: 'JSON',
  			success: function (response) {
  				interestedJson = response;
  				showInterestedList();
  			},
  			error : function () {
  				interestedJson = '';
  				showInterestedList();
  			}
  		});

  		//관심지역 이동
  		$(document).on("click", ".interested-region", function () {
  			let dName = $(this).parent().children('#dongName').val();
  			let dCode = $(this).parent().children('#dongCode').val();
  			let lCode = $(this).parent().children('#largeCode').val();
  			location.href=`${root}/commerce?action=main&dongName=\${dName}&dongCode=\${dCode}&largeCode=\${lCode}`;
  		});

  		//inter-delete-Btn
  		$(document).on("click", ".inter-delete-Btn", function () {
=======
	if (isLogin) {
		console.log("flag!!");
		$("#mapDiv").attr('class','container col-sm-8');
		$("#tableDiv").attr('style', "display: ");
		$.ajax({
			url: '${root}/interested',
			data: {
				action : 'list',
				id : '\${memberInfo}.id',
				dongCode : '${dongCode}',
				largeCode : '${largeCode}',
			},
			type: 'GET',
			dataType: 'JSON',
			success: function (response) {
				showInterestedList(response);
			},
			error : function () {
				showInterestedList('');
			}
		});
		
		//관심지역 이동
		$(document).on("click", ".interested-region", function () {
			let dName = $(this).parent().children('#dongName').val();
			let dCode = $(this).parent().children('#dongCode').val();
			let lCode = $(this).parent().children('#largeCode').val();
			location.href=`${root}/commerce?action=main&dongName=\${dName}&dongCode=\${dCode}&largeCode=\${lCode}`;
		});
		
		//inter-delete-Btn
		$(document).on("click", ".inter-delete-Btn", function () {
			
			let dName = $(this).parent().prevAll('#dongName').val();
			let dCode = $(this).parent().prevAll('#dongCode').val();
			let lCode = $(this).parent().prevAll('#largeCode').val();

			$.ajax({
				url: '${root}/interested',
				data: {
					action : 'delete',
					dongCode : dCode,
					largeCode : lCode
				},
				type: 'GET',
				success: function () {
					location.reload();
				}
			});
		});
	}
		 //맵 생성
	var container = document.getElementById("kmap");
	var options = {
	   	center: new kakao.maps.LatLng(33.450701, 126.570667),
	   	level: 3,
	 	};
	 
	map = new kakao.maps.Map(container, options);
	
	$(document).on("change", "#middle", function () {
		let dongName = '${dongName}';
		let dongCode = '${dongCode}';
		let middleCode = $("option:selected", this).val();
		let pg = 1;
		getData(dongName, dongCode, middleCode, pg);
	});
	
	
	$(document).on("click", "#interested-btn", function () {
		let dongCode = '${dongCode}';
		let largeCode = '${largeCode}';
		
		$.ajax({
			url: '${root}/interested',
			data: {
				action : 'regist',
				dongCode : dongCode,
				largeCode : largeCode
			},
			type: 'GET',
			success: function () {
				location.reload();
			}
		});
	});
	
	$(document).on("click", "#search-btn", function () {
		let dongName = '${dongName}';
		let dongCode = '${dongCode}';
		let middleCode = $("option:selected", "#middle").val();
		let pg = 1;
		let key = $("option:selected", "#key").val();
		let word = $("#word").val();
		getData(dongName, dongCode, middleCode, pg, key, word);
	});
	
	/*
	페이지 누르면 지도는 그대로, 표에 출력되는 데이터만 변경됨
	즉 DB까지 갈 필요는 없음
	중분류 선택해서 DB에서 JSON데이터 받아오면
	그 데이터는 여기의 전역변수값으로 저장해두기
	그리고 이후 페이징 요청, 그에 맞는 표 출력은 전역변수 데이터로 활용하기
	페이징 넘버마다 클릭이벤트 생성 -> 페이지 넘버만큼 표출력 -> js반복문으로 li출력 -> 선택된 번호랑 같은값이면 active
	*/
	$(document).on("click", ".page-item", function () {
		//버튼 눌리면
		//1. 표 출력 변경 (선택된 페이지 넘버 필요)
		let curPageNo = $(this).attr("data-pg");
		makeList(curPageNo);
		//2. 페이지 넘버, active변경(선택된 페이지넘버)
		makePagingView(curPageNo);
	});
	
});
		
	function getData(dongName, dongCode, middleCode, pg, key, word) {
		$.ajax({
			url: '${root}/commerce',
			data: {
				action : 'map',
				dongName : dongName,
				dongCode : dongCode,
				middleCode : middleCode,
				pg : 1,
				key : key,
				word : word
			},
			type: 'GET',
			dataType: 'JSON',
			success: function (response) {
				//json으로 받은 commerce객체 리스트
				//위경도로 마커뿌리고
				storeData = response;
	            makeMarker(storeData); //지도 만들기
				makeList(1); //표 만들기
				//객체정보로 info출력
				//여기서 초기 페이징 뷰 생성, 1번부터 ~까지
				makePagingView(1);
			}
		});
	}
	
	function showInterestedList(data) {
		let interestedList = '';
		let list = data;
		
		for(let i = 0; i < list.length; i++) {
        	let cur = list[i];
        	if (!cur)
        		continue;

	        let dongName = cur['dongName'];
			let largeName = cur['largeName'];
	        let doCode = cur['dongCode'];
			let lCode = cur['largeCode'];
			console.log('dongName: ' + dongName);
			
			interestedList += `
			<tr>
				<input type="hidden" id="dongName" name="dongName" value="\${dongName}"/>
				<input type="hidden" id="dongCode" name="dongCode" value="\${doCode}"/>
				<input type="hidden" id="largeCode" name="largeCode" value="\${lCode}"/>
			    <td class="interested-region">\${dongName}</td>
			    <td class="interested-region">\${largeName}</td>
			    <td><input type="button" class="inter-delete-Btn" value="삭제" /></td>
			</tr>
			`;
        }
		if (!interestedList){
			interestedList += '<tr><td colspan="3">관심지역이 없습니다.</td></tr>';
		}
		
        $("#interested-list").empty().append(interestedList);
        $("tr:first").css("background", "black").css("color", "white");
        $("tr:odd").css("background", "lightgray");
	}
	
	function makeList(curPageNo) {
		/*
		데이터는 서블릿에서 JSON으로 전부 받아오기
		페이지에 맞는 데이터 10개(currentPerPage)
		뿌리는 방법
		=> 여기서 반복문 인덱스 조정해서 뿌리기
		=> 필요한 정보
			- 현재 페이지 -> 선택된거(active)
			- countPerPage -> (얘네는 전역변수 설정?)
			- naviSize 		 ->
			- totalData -> data의 length가 될듯
		=> 시작 인덱스 = (현재페이지  - 1) * curr
		=> 종료 인덱스 = (현재페이지 - 1) * curr + naviSize (미만)
			현재 2페이지 -> 10 ~ 19번
		*/
		let storeListInfo = '';
		let startIdx = (curPageNo - 1) * countPerPage;
		let endIdx = startIdx + naviSize;
		if (endIdx > totalData)
			endIdx = totalData;
        for(let i = startIdx; i < endIdx; i++) {
        	let cur = storeData[i];
>>>>>>> 1010c7761ecb80260ef02023ea8c6a61b9efb3ff

  			let dName = $(this).parent().prevAll('#dongName').val();
  			let dCode = $(this).parent().prevAll('#dongCode').val();
  			let lCode = $(this).parent().prevAll('#largeCode').val();
  			/*
  			location.href=`${root}/interested?action=delete&dongName=\${dName}&dongCode=\${dCode}&largeCode=\${lCode}`;
  			*/

  			$.ajax({
  				url: '${root}/interested',
  				data: {
  					action : 'delete',
  					dongCode : dCode,
  					largeCode : lCode,
  				},
  				type: 'GET',
  				success: function () {
  					location.reload();
  				}
  			});
  		});
  	}
  		 //맵 생성
  	var container = document.getElementById("kmap");
  	var options = {
  	   	center: new kakao.maps.LatLng(33.450701, 126.570667),
  	   	level: 3,
  	 	};

  	map = new kakao.maps.Map(container, options);

  	$(document).on("change", "#middle", function () {
  		let dongName = '${dongName}';
  		let dongCode = '${dongCode}';
  		let middleCode = $("option:selected", this).val();

  		/*[중분류 변경하면 관심등록 버튼 생성하는 부분]
  		----------------------------------------------------
  		let tag =`
  			<input type="hidden" name="action" value="regist">
  			<input type="hidden" name="dongCode" value="\${dongCode}">
  			<input type="hidden" name="middleCode" value="\${middleCode}">
  			<button type="button" id="interested-btn" class="ml-1 btn btn-outline-primary">관심지역 등록</button>
  			`;
  		$("#interested-form").empty().append(tag);
  		$(document).on("click", "#interested-btn", function () {
  			$("#interested-form").submit();
  		});
  		-------------------------------------------------------*/
  		let pg = 1;
  		getData(dongName, dongCode, middleCode, pg);
  	});


  	$(document).on("click", "#interested-btn", function () {

  		//$("#interested-form").submit();
  		/*
  		<form class="form-inline" id="interested-form" action="${root}/interested">
  			<input type="hidden" name="action" value="regist">
  			<input type="hidden" name="dongName" value="${dongName}">
  			<input type="hidden" name="dongCode" value="${dongCode}">
  			<input type="hidden" name="largeCode" value="${largeCode}">
  			<button type="button" id="interested-btn" class="ml-1 btn btn-outline-primary">관심지역 등록</button>
  		</form>
  		*/
  		$.ajax({
  			url: '${root}/interested',
  			data: {
  				action : 'regist',
  				dongCode : ${dongCode},
  				largeCode : ${largeCode},
  			},
  			type: 'GET',
  			success: function () {
  				location.reload();
  			}
  		});
  	});

  	$(document).on("click", "#search-btn", function () {
  		let dongName = '${dongName}';
  		let dongCode = '${dongCode}';
  		let middleCode = $("option:selected", "#middle").val();
  		let pg = 1;
  		let key = $("option:selected", "#key").val();
  		let word = $("#word").val();
  		getData(dongName, dongCode, middleCode, pg, key, word);
  	});

  	/*
  	페이지 누르면 지도는 그대로, 표에 출력되는 데이터만 변경됨
  	즉 DB까지 갈 필요는 없음
  	중분류 선택해서 DB에서 JSON데이터 받아오면
  	그 데이터는 여기의 전역변수값으로 저장해두기
  	그리고 이후 페이징 요청, 그에 맞는 표 출력은 전역변수 데이터로 활용하기
  	페이징 넘버마다 클릭이벤트 생성 -> 페이지 넘버만큼 표출력 -> js반복문으로 li출력 -> 선택된 번호랑 같은값이면 active
  	*/
  	$(document).on("click", ".page-item", function () {
  		//버튼 눌리면
  		//1. 표 출력 변경 (선택된 페이지 넘버 필요)
  		let curPageNo = $(this).attr("data-pg");
  		makeList(curPageNo);
  		//2. 페이지 넘버, active변경(선택된 페이지넘버)
  		makePagingView(curPageNo);
  	});

  });

  	function getData(dongName, dongCode, middleCode, pg, key, word) {
  		$.ajax({
  			url: '${root}/commerce',
  			data: {
  				action : 'map',
  				dongName : dongName,
  				dongCode : dongCode,
  				middleCode : middleCode,
  				pg : 1,
  				key : key,
  				word : word
  			},
  			type: 'GET',
  			dataType: 'JSON',
  			success: function (response) {
  				//json으로 받은 commerce객체 리스트
  				//위경도로 마커뿌리고
  				storeData = response;
  	            makeMarker(storeData); //지도 만들기
  				makeList(1); //표 만들기
  				//객체정보로 info출력
  				//여기서 초기 페이징 뷰 생성, 1번부터 ~까지
  				makePagingView(1);
  			}
  		});
  	}

  	function showInterestedList(delDongCode, delLargeCode) {
  		let interestedList = '';
  		let list = interestedJson;

  		for(let i = 0; i < list.length; i++) {
          	let cur = list[i];
          	if (!cur)
          		continue;

  	        let dongName = cur['dongName'];
  			let largeName = cur['largeName'];
  	        let doCode = cur['dongCode'];
  			let lCode = cur['largeCode'];
  			console.log('dongName: ' + dongName);

  			if (delDongCode == doCode && delLargeCode == lCode) {
  				list[i] = '';
  				continue;
  			}

  			interestedList += `
  			<tr>
  				<input type="hidden" id="dongName" name="dongName" value="\${dongName}"/>
  				<input type="hidden" id="dongCode" name="dongCode" value="\${doCode}"/>
  				<input type="hidden" id="largeCode" name="largeCode" value="\${lCode}"/>
  			    <td class="interested-region">\${dongName}</td>
  			    <td class="interested-region">\${largeName}</td>
  			    <td><input type="button" class="inter-delete-Btn" value="삭제" /></td>
  			</tr>
  			`;
          }
  		if (!interestedList){
  			interestedList += '<tr><td colspan="3">관심지역이 없습니다.</td></tr>';
  		}

          $("#interested-list").empty().append(interestedList);
          $("tr:first").css("background", "black").css("color", "white");
          $("tr:odd").css("background", "lightgray");
  	}

  	function makeList(curPageNo) {
  		/*
  		데이터는 서블릿에서 JSON으로 전부 받아오기
  		페이지에 맞는 데이터 10개(currentPerPage)
  		뿌리는 방법
  		=> 여기서 반복문 인덱스 조정해서 뿌리기
  		=> 필요한 정보
  			- 현재 페이지 -> 선택된거(active)
  			- countPerPage -> (얘네는 전역변수 설정?)
  			- naviSize 		 ->
  			- totalData -> data의 length가 될듯
  		=> 시작 인덱스 = (현재페이지  - 1) * curr
  		=> 종료 인덱스 = (현재페이지 - 1) * curr + naviSize (미만)
  			현재 2페이지 -> 10 ~ 19번
  		*/
  		let storeListInfo = '';
  		let startIdx = (curPageNo - 1) * countPerPage;
  		let endIdx = startIdx + naviSize;
  		if (endIdx > totalData)
  			endIdx = totalData;
          for(let i = startIdx; i < endIdx; i++) {
          	let cur = storeData[i];

  	        let name = cur['name'];
  			let addr = cur['address'];
  			let floor = cur['floor'];
  			let smallName = cur['smallName'];

  			storeListInfo += `
  			<tr>
  			    <td>\${name}</td>
  			    <td>\${addr}</td>
  			    <td>\${floor}</td>
  			    <td>\${smallName}</td>
  			</tr>
  			`;
          }
          $("#storelist").empty().append(storeListInfo);
          $("tr:first").css("background", "black").css("color", "white");
          $("tr:odd").css("background", "#46FFFF");
      }

  	function makeMarker(data) {
  		for (let m of markers) {
  			m.setMap(null);
  		}
  		markers = [];

  		var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
  		    // 마커 이미지의 이미지 크기 입니다
  		var imageSize = new kakao.maps.Size(24, 35);

  		var moveLocation = null;
  		//data에는 commerceDto list의 json형식
  		let cnt = 0;
  		for(var cur of data) {
  			cnt++;
  			let name = cur['name'];
  			let addr = cur['address'];
  			let floor = cur['floor'];
  			if (floor)
  				floor += "층";
  			let lon = cur['lon'];
  			let lat = cur['lat'];

  			var p = {
  				title: name,
  				content: `
  					<div style="padding:5px;">상호명 : \${name} <br>
  					주소 : \${addr} \${floor}<br>
  					<a href="#">큰지도보기</a> `,
  				latlng: new kakao.maps.LatLng(lat, lon)
  			};

  			// 마커 이미지를 생성합니다
  			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

  			// 마커를 생성합니다
  			var marker = new kakao.maps.Marker({
  				map: map, // 마커를 표시할 지도
  				position: p.latlng, // 마커를 표시할 위치
  				title: p.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
  				image: markerImage, // 마커 이미지
  			});

  			var infowindow = new kakao.maps.InfoWindow({
  				title: p.title,
  				content: p.content, // 인포윈도우에 표시할 내용
  			});

  			// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
  			// 이벤트 리스너로는 클로저를 만들어 등록합니다
  			// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
  			kakao.maps.event.addListener(
  				marker,
  				"mouseover",
  				makeOverListener(map, marker, infowindow)
  			);
  			kakao.maps.event.addListener(marker, "mouseout", makeOutListener(infowindow));

  			kakao.maps.event.addListener(marker, "click", makeClickListener(map, marker, infowindow));

  			if (!moveLocation) {
  				moveLocation = new kakao.maps.LatLng(p.latlng.Ma, p.latlng.La);
  				map.setLevel(6);
  				map.setCenter(moveLocation);
  			}

  			markers.push(marker);
  		}
  		totalData = cnt;
  	}

  	// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
  	function makeOverListener(map, marker, infowindow) {
  		return function () {
  			infowindow.open(map, marker);
  		};
  	}

  	// 인포윈도우를 닫는 클로저를 만드는 함수입니다
  	function makeOutListener(infowindow) {
  		return function () {
  			infowindow.close();
  		};
  	}

  	function makeClickListener(map, marker, infowindow) {
  		return function () {
  			var pos = marker.getPosition();
  			console.log(pos);
  			map.setLevel(4);
  			map.panTo(pos);
  		};
  	}

  	function makePagingView(curPageNo) {

  		curPageNo = parseInt(curPageNo);

  		let maxPageNo = parseInt(totalData / countPerPage);
  		if (maxPageNo == 0)
  			maxPageNo = 1;

  		let startPageNo = parseInt((curPageNo - 1) / naviSize) + 1;
  		if (startPageNo > maxPageNo)
  			startPageNo = maxPageNo;

  		let endPageNo = startPageNo + naviSize - 1;
  		if (endPageNo > maxPageNo)
  			endPageNo = maxPageNo;

  		let prevPageNo = curPageNo == 1 ? 1 : curPageNo - 1;
  		let appendList = `<li class="page-item" data-pg="1">
  							<a class="page-link" href="#">최신</a>
  						  </li>
  			  			  <li class="page-item" data-pg="\${prevPageNo}">
  			  			  	<a class="page-link" href="#">이전</a>
  			  			  </li>`;
  		for (let i = startPageNo; i <= endPageNo; i++) {
  			let isActive = (i == curPageNo) ? 'active' : "";
  			appendList += `<li class="page-item \${isActive}" data-pg="\${i}">
  						     <a href="#" class="page-link">\${i}</a>
  						   </li>`;
  		}
  		let nextPageNo = curPageNo + 1;
  		if (nextPageNo > maxPageNo)
  			nextPageNo = maxPageNo;
  		appendList += `<li class="page-item" data-pg="\${nextPageNo}">
  			  				<a class="page-link" href="#">이후</a>
  	  			  		  </li>
  	  			  		  <li class="page-item" data-pg="\${maxPageNo}">
  						    <a class="page-link" href="#">마지막</a>
  					      </li>`;
  		$("#page-nav").empty().append(appendList);
  	}
</script>

<div class="container">
<<<<<<< HEAD
  <!-- 중분류 선택바, 검색 버튼 -->
  <div class="row">
    <div
      class="col text-center mb-2"
      style="
        width: auto;
        height: 200px;
        background-size: cover;
        background-image: url('https://ww.namu.la/s/dd28d29e650bff5a90776fda52187f6488962321db575dd030b0127bc4f5a4cea014119787459f2d3c5436e57b9f566e466ffcbaca7cd4fb4763a5b210cd94c4d2c1a0da69d83d9d3e39395881b0f06a3996f5f3099f1973e9b41e56a2fe7661');
      "
    >
      <div style="height: 200px; color: white; font-size: 50px; line-height: 150px">
        Commerce Around
      </div>
    </div>
  </div>
  <div class="row mt-5">
    <div class="col text-center mt-2 mb-1" style="width: auto">
      <select class="custom-select" id="middle">
        <c:if test="${!empty middleList}">
          <c:forEach var="list" items="${middleList}">
            <option value="${list.middleCode}">${list.middleName}</option>
          </c:forEach>
        </c:if>
      </select>
=======
	
	<!-- 중분류 선택바, 검색 버튼 -->
	<div class="row" >
		<div class="col text-center mb-2" style=" width : auto; height : 200px;background-size : cover; background-image :
  url('https://ww.namu.la/s/dd28d29e650bff5a90776fda52187f6488962321db575dd030b0127bc4f5a4cea014119787459f2d3c5436e57b9f566e466ffcbaca7cd4fb4763a5b210cd94c4d2c1a0da69d83d9d3e39395881b0f06a3996f5f3099f1973e9b41e56a2fe7661');">
			<div style="height:200px; color : white; font-size : 50px; line-height : 150px;">Commerce Around</div>
		</div>
	</div>
	<div class="row mt-5" >
		<div class="col text-center mt-2 mb-1" style="width : auto">
			<select class="custom-select" id="middle">
			<c:if test="${!empty middleList}">
				<c:forEach var="list" items="${middleList}">
					<option value="${list.middleCode}">${list.middleName}</option>
				</c:forEach>
			</c:if>
			</select>
		</div>
		<div>
		<!-- 관심지역 등록버튼 -->
			<button type="button" id="interested-btn" name="interested-btn" class="ml-1 btn btn-outline-primary">관심지역 등록</button>
		</div>
		<div class="m-3 row justify-content-end">
           	<form class="form-inline" action="${root}/commerce">
            	<input type="hidden" name="action" value="map">
            	<input type="hidden" name="pg" value="1">
            	<select name="key" id="key" class="form-control">
            		<option value="name"> 상호명
            		<option value="smallName"> 업종
            		<option value="address"> 주소
            	</select>
            	<input type="text" class="ml-1 form-control" id="word" name="word">
            	<button type="button" id="search-btn" class="ml-1 btn btn-outline-primary">검색</button>
           	</form>
         </div>
	</div>
	
	<!-- 지도, 관심지역 리스트 -->
	<div class="row mt-3">
		<div id="mapDiv" class="container col-sm-12">
			<div id="kmap" style="height: 450px"></div>
		</div>
		<div id="tableDiv" class="container col-sm-4" style="display: none">
			<div class="row">
				<table id="example-table-2" width="100%" class="table table-bordered table-hover text-center">
					<thead>
						<tr>
							<th>지역</th>
							<th>업종</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody id="interested-list">				
						<!-- 관심지역 리스트 -->
					</tbody>
				</table>
			</div>
		</div>
 	</div>
  		
	<div class="row mt-5">
      <table class="table table-striped">
        <thead>
          <tr class="text-center">
            <th>상호명</th>
            <th>주소</th>
  	        <th>층</th>
            <th>업종소분류</th>
          </tr>
        </thead>
        <tbody id="storelist"></tbody>
      </table>
>>>>>>> 1010c7761ecb80260ef02023ea8c6a61b9efb3ff
    </div>
    <div>
      <!-- 관심지역 등록버튼 -->
      <button type="button" id="interested-btn" class="ml-1 btn btn-outline-primary">
        관심지역 등록
      </button>
    </div>
    <div class="m-3 row justify-content-end">
      <form class="form-inline" action="${root}/commerce">
        <input type="hidden" name="action" value="map" />
        <input type="hidden" name="pg" value="1" />
        <select name="key" id="key" class="form-control">
          <option value="name">상호명</option>
          <option value="smallName">업종</option>
          <option value="address">주소</option>
        </select>

        <input type="text" class="ml-1 form-control" id="word" name="word" />
        <button type="button" id="search-btn" class="ml-1 btn btn-outline-primary">검색</button>
      </form>
    </div>
  </div>

  <!-- 지도, 관심지역 리스트 -->
  <div class="row mt-3">
    <div id="mapDiv" class="container col-sm-12">
      <div id="kmap" style="height: 450px"></div>
    </div>
    <div id="tableDiv" class="container col-sm-4" style="display: none">
      <div class="row">
        <table
          id="example-table-2"
          width="100%"
          class="table table-bordered table-hover text-center"
        >
          <thead>
            <tr>
              <th>지역</th>
              <th>업종</th>
              <th>삭제</th>
            </tr>
          </thead>
          <tbody id="interested-list">
            <!-- 관심지역 리스트 -->
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <div class="row mt-5">
    <table class="table table-striped">
      <thead>
        <tr class="text-center">
          <th>상호명</th>
          <th>주소</th>
          <th>층</th>
          <th>업종소분류</th>
        </tr>
      </thead>
      <tbody id="storelist"></tbody>
    </table>
  </div>
  <div>
    <ul id="page-nav" class="pagination"></ul>
  </div>
</div>

<%@ include file="/template/footer.jsp" %>
