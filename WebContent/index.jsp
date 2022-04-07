<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/template/header.jsp" %>

<script>
$(function () {
    // 1. 시도 정보 가져오기
    sendRequest("sido", "*00000000");

	$(document).on("change", "#sido", function () {
		let regcode = $("option:selected", this).val();
		sendRequest("gugun", regcode);
	});

	$(document).on("change", "#gugun", function () {
		let regcode = $("option:selected", this).val();
		sendRequest("dong", regcode);
	});
	
	function sendRequest(selid, regcode) {
      $.ajax({
        url: "${root}/commerce",
        type: "GET",
        data: {
      	  	action : selid,
      	  	code : regcode
      	  },
        dataType: "JSON",
        success: function (response) {
          addOption(selid, response);
        },
        error: function (xhr, status, msg) {
          console.log("상태값 : " + status + " Http에러메시지 : " + msg);
        },
      });
  	}
  
	function addOption(selid, data) {
		let options = ``;
		let initOption = `<option>Choose..</option>`;
		let codeOption = selid + "Code";
		let nameOption = selid + "Name";
		selid = "#" + selid;
		$(selid).empty().append(initOption);
		for(let cur of data) {
			options += '<option value="' + cur[codeOption] + '">' + cur[nameOption] + '</option>\n';
		}
		$(selid).append(options);
    }
    // 2. onchange event 등록
    
    
    // 3. 대분류 버튼 onclick event add
	$.ajax({
		url: "${root}/commerce",
		type: "GET",
		data: {
			action : "largeCode"
		},
		dataType: "JSON",
		success: function (response) {
			let code='';
			for(let node of response){
			let largeCode = node["largeCode"];
			let largeName = node["largeName"];
			code += `<div class="col-3 p-2 text-center ">`+
						`<button type="button" name="largeButton" class="btn btn-info btn-lg" value="` +largeCode+`">`+largeName + 
						`</button></div>`;
			}
			$("#largeRow").append(code);
			$(".btn").each(function(index, element) {
				$(this).on("click", function() {
					let dongCode = $("#dong option:selected").val();
					let dongName = $("#dong option:selected").text();
					let largeCode = $(this).val();
					location.href = "${root}/commerce?action=main&dongCode="+dongCode+"&dongName="+dongName+"&largeCode="+largeCode;
				});
			});
		},
		error: function (xhr, status, msg) {
			console.log("상태값 : " + status + " Http에러메시지 : " + msg);
		},
	});
});
</script>

<div class="container">
	<div class="row" >
		<div class="col text-center mb-2" style=" width : auto; height : 500px;background-size : cover; background-image :
  url('https://ww.namu.la/s/dd28d29e650bff5a90776fda52187f6488962321db575dd030b0127bc4f5a4cea014119787459f2d3c5436e57b9f566e466ffcbaca7cd4fb4763a5b210cd94c4d2c1a0da69d83d9d3e39395881b0f06a3996f5f3099f1973e9b41e56a2fe7661');">
			<div style="height:200px; color : white; font-size : 50px; line-height : 150px;">Commerce Around</div>
		</div>
	</div>
	
	<div class="row mt-5">
		<div class="container">
			<div class="row">
				<div class="col-4">
					<h5 class="text-center font-weight-bold">관심지역 선택</h5>
					<div class="row">
						<div class="input-group mb-3">
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="sido">시,도</label>
						  </div>
						  <select class="custom-select" id="sido">
						  
						  </select>
						</div>
					</div>
					<div class="row">
						<div class="input-group mb-3">
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="gugun">군,구</label>
						  </div>
						  <select class="custom-select" id="gugun">
						    <option selected>Choose...</option>
						  </select>
						</div>
					</div>
					<div class="row">
						<div class="input-group mb-3">
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="dong">동</label>
						  </div>
						  <select class="custom-select" id="dong">
						    <option selected>Choose...</option>
						  </select>
						</div>
					</div>

				</div>
				<div class="col">
					<div>
						<h4 class="text-center mb-3 font-weight-bold">Description</h4>
						<div class="text-center font-weight-bold">
						<div>1. 관심지역의 주소를 시, 군, 동 순서로 선택</div>
						<div>2. 상권 대분류 버튼 중 한가지를 선택</div>
						<div>3. 상권 중분류 항목 선택</div>
						</div>
					</div>
				</div>
			</div>
		</div>
  </div>
  
  <div class="text-center font-weight-bold" style="font-size:30px; margin : 30px;" id="largeButton">상권 대분류 버튼</div>
  <div class="row" id="largeRow">
  </div>
</div>






<%@ include file="/template/footer.jsp" %>