/**
 * 
 */

// 도로명 주소 검색
function getPostcodeAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수(도로명 주소)
            var extraAddr = ''; // 조합형 주소 변수
            
            ////////////////////////////////////////////////////////////////
            
            console.log("도로명 주소 : " + data.roadAddress);
            console.log("지번 주소(자동처리 : 지번 미출력시 자동 입력처리) : " + data.autoJibunAddress);

            // 법정동명이 있을 경우 추가한다.
            if(data.bname !== ''){
                extraAddr += data.bname;
            }
            // 건물명이 있을 경우 추가한다.
            if(data.buildingName !== ''){
                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            
            // 조합형 주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
            // fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                // fullAddrJibun += (extraAddr !== '' ? ' ('+ extraAddr +')' : ''); // javateacher 추가
            // }

            // javateacher end)
            
            ////////////////////////////////////////////////////////////////
                
            // 주소 정보 전체 필드 및 내용 확인 : javateacher
            var output = '';
            for (var key in data) {
                output += key + ":" +  data[key]+"\n";
            }
            
            console.log("-----------------------------")
            console.log(output);
            console.log("-----------------------------")

            // 3단계 : 해당 필드들에 정보 입력
            // 우편번호와 주소 정보를 해당 필드에 넣는다.

            // javateacher) 본 회원가입 코드에서는 도로명으로 선택하든 지번 주소로 선택하든
            // 일괄적으로 도로명으로 기본주소가 들어가도록 설정하였습니다.

            let memberZipFld = document.getElementById('memberZip');
            let memberAddress1Fld = document.getElementById('memberAddress1');
			let memberAddress2Fld = document.getElementById('memberAddress2');
            let addressFldErrPnl = document.getElementById('address_fld_err_pnl');

            memberZipFld.value = data.zonecode; // 5자리 우편번호 사용
			memberAddress1Fld.value = data.roadAddress;// 도로명 주소
			//jibunAddressFld.value = fullAddrJibun; // 지번 주소
            
            // 커서를 상세주소 필드로 이동한다.
            memberAddress2Fld.focus();                        

            // 주소 필드 점검
            isCheckAddressFldValid(memberZipFld, memberAddress1Fld, memberAddress2Fld, addressFldErrPnl);
        }   
    }).open();
}

/////////////////////////////////////////////////////////////////////////////////////////////

// 에러 메시징 함수
// 기능 : 필드별로 폼 점검 시행 후 에러 메시징(패널)
//        개별 필드 체크 플래그에 리턴
// 1) 함수명 : isCheckFldValid
// 2) 인자 :
// 필드 (아이디) 변수(fld), 필드 기정값(initVal),
// 필드별 정규표현식(유효성 점검 기준) (regex)
// 필드별 에러 패널(errPnl), 필드별 에러 메시지(errMsg)
// 3) 리턴 : fldCheckFlag : boolean (true/false) : 유효/무효
// 4) 용례(usage) :  
// idCheckFlag = isCheckFldValid(idFld, 
//                        /^[a-zA-Z]{1}\w{7,19}$/,
//                        idFldErrPnl,
//                        "",     
//                        "회원 아이디는 8~20사이의 영문으로 
//                        시작하여 영문 대소문자/숫자로 입력하십시오.")
function isCheckFldValid(fld, regex, initVal, errPnl, errMsg) {

    // 리턴값 : 에러 점검 플래그
    let fldCheckFlag = false;

    // 체크 대상 필드 값 확인
    console.log(`체크 대상 필드 값 : ${fld.value}`);

    // 폼 유효성 점검(test)
    console.log(`폼 유효성 점검 여부 : ${regex.test(fld.value)}`);

    if (regex.test(fld.value) == false) {
		
		console.log("폼 점검 오류 발생!");
				
        errPnl.style.height = "50px"; 
        errPnl.innerHTML = errMsg; 

        // 기존 필드 데이터 초기화
        // fld.value = "";
        fld.value = initVal;
        //fld.focus(); // 재입력 준비 //0402 수정. 이걸 없애면 재입력 할 수 있게 됨. !!!
        
        fldCheckFlag = false;

		console.log("폼 점검 오류 발생 처리 끝!");

    } else { // 정상

        // 에러 패널 초기화
        errPnl.style.height = "0"; 
        errPnl.innerHTML = "";

        fldCheckFlag = true;
    } // if

    return fldCheckFlag;
} //

////////////////////////////////////////////////////


// 우편번호/주소 필드 점검
function isCheckAddressFldValid(memberZipFld, memberAddress1Fld, memberAddress2Fld, addressFldErrPnl) {

    let resultFlag = false;

	let memberZipFldVal = memberZipFld.value;
	let memberAddress1FldVal = memberAddress1Fld.value;
	let memberAddress2FldVal = memberAddress2Fld.value;
    

    // 점검 경우(주소 정보가 필수사항이 아닌 경우) : 점검 오류 발생 경우
    
    // 1) 우편번호/기본주소가 채워져 있는데 상세주소가 비워져 있는 경우
    //    - 에러 메시지 : 상세 주소를 입력하십시오.

    // 2) 우편번호/기본주소가 비워져 있는데 상세주소가 채워져 있는 경우
    //    - 에러 메시지 : 주소 검색을 통해서 우편번호와 기본주소를 입력하십시오.

	console.log("\n----------------------------------\n")
	
	console.log("우편번호 필드(길이) : " + memberZipFldVal.length);
	console.log("도로명 주소 필드(길이) : " + memberAddress1FldVal.length);
	console.log("상세 주소 필드(길이) : " + memberAddress2FldVal.length);
	
	// 주소 필드들의 길이로 점검
	
    // 1) 상세주소 미입력시
    if (memberZipFldVal.length != 0 && memberAddress1FldVal.length != 0 && 
		memberAddress2FldVal.length == 0) {  
			
		console.log("주소 필드 에러 메시지 : 상세주소를 넣지 않았습니다.")	
    
        resultFlag = false;
        addressFldErrPnl.innerHTML = "상세 주소를 입력하십시오.";

    // 2) 기본주소 미입력시(주소 미검색)
	} else if (memberZipFldVal.length == 0 && memberAddress1FldVal.length == 0 && 
                memberAddress2FldVal.length != 0) {
				
		console.log("주소 필드 에러 메시지 : 주소 검색을 통해서 우편번호와 기본주소를 입력하십시오.")
		
        resultFlag = false;
        addressFldErrPnl.innerHTML = "주소 검색을 통해서 우편번호와 기본주소를 입력하십시오.";

	// 3) 모든 조건 충족
    } else {              
        
		console.log("주소 필드 에러 메시지 : 모든 조건 만족")
        // 
        resultFlag = true;                
        addressFldErrPnl.innerHTML = ""; // 필드 에러 메시지 초기화
    }
    
    return resultFlag;
} //    

////////////////////////////////////////////////////

window.onload = () => {

    // 각 필드들의 에러 점검 여부 (플래그(flag) 변수)
    let idCheckFlag = false;

	// 아이디 중복 점검 플래그
	let idDuplicatedCheckFlag = false;  

    // 패쓰워드 점검 플래그 
    let pwCheckFlag = false;

    // 이름 점검 플래그
    let nameCheckFlag = false;

    // 닉네임 점검 플래그
    let nickNameCheckFlag = false;
    
	// 닉네임 중복 점검 플래그
    let nickNameDuplicatedCheckFlag = false;

    // 연락처 점검 플래그
    let mobileCheckFlag = false;

	// 연락처(휴대폰) 중복 점검 플래그
	let mobileDuplicatedCheckFlag = false;
	
    // 주소 점검 플래그
    // 주소에 대한 사항이 필수가 아니라 선택 사항인 경우는 
    // 입력하지 않아도 무관하기 때문에 초기 상태(무입력 상태)도 true로 간주하여
    // 초기값 true 설정
    let addressCheckFlag = true;

    // 아이디 필드 폼 점검(form validation)
    // 아이디 필드 인식
    let idFld = document.getElementById("memberEmail");//0402 수정

    // 아이디 에러 패널 인식
    let idFldErrPnl = document.getElementById("id_fld_err_pnl");

    // 패쓰워드 필드 인식
    let pwFld = document.getElementById("memberPw");//0402 수정

    // 패쓰워드 에러 패널 인식
    let pwFldErrPnl = document.getElementById("password_fld_err_pnl");

    // 이름 필드 인식
    let nameFld = document.getElementById("memberName");//0402 수정

    // 이름 에러 패널 인식
    let nameFldErrPnl = document.getElementById("name_fld_err_pnl");

    // 닉네임 필드 인식
    let nickNameFld = document.getElementById("memberNick");

    // 닉네임 에러 패널 인식
    let nickNameFldErrPnl = document.getElementById("nickName_fld_err_pnl");

    // 연락처(휴대폰) 필드 인식
    let mobileFld = document.getElementById("memberMobile");//0402 수정

    // 연락처(휴대폰) 필드 에러 패널 인식
    let mobileFldErrPnl = document.getElementById("mobile_fld_err_pnl");

    // 우편번호/주소 필드 인식
    let memberZipFld = document.getElementById("memberZip");

    let memberAddress1Fld = document.getElementById("memberAddress1");

	let memberAddress2Fld = document.getElementById("memberAddress2");

    // 주소 필드 에러 패널 인식
    let addressFldErrPnl = document.getElementById("address_fld_err_pnl");

    ////////////////////////////////////////////////////////////////////////
	// 입력필드들에서 엔터키누를 때 전송 방지. 0426 
	document.addEventListener('keydown', function(event) {
	  if (event.keyCode === 13) {
	    event.preventDefault();
	  };
	}, true);
	
    // 아이디 필드 입력 후 이벤트 처리
    idFld.onblur = (e) => {

        // 아이디 필드 값 확인
        console.log(`아이디 필드 값 : ${idFld.value}`);

        // 아이디 필드 유효성 점검(validation)
        // 기준)
        /*
            1) 길이(length) : 8~20자
            2) 첫자 제한: alphabet(영문)으로 시작
            3) 한글 입력 제한 : 불가.
            4) 제약조건 : 영문자 및 숫자만을 허용
            5) regex(정규표현식) : /^[a-zA-Z]{1}\w{7,19}$/
            참고) https://www.regexpal.com/
            6) 메시징 : 회원 아이디는 8~20사이의 영문으로 시작하여 
                       영문 대소문자/숫자로 입력하십시오.
        */   
		idCheckFlag = isCheckFldValid(idFld, 
                            /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i,
                            idFld.value,
                            idFldErrPnl,
                            "회원 아이디는 이메일로 적어주십시오.");
		
		idDuplicatedCheckFlag = false;
		
        // 폼 유효성 점검(test)
		console.log("idCheckFlag => " + idCheckFlag);
		console.log("idDuplicatedCheckFlag => " + idDuplicatedCheckFlag);

        if (idCheckFlag == true && idDuplicatedCheckFlag == false) {

			console.log("아이디 중복 점검 진입");
			
			// 아이디(이메일) 중복 점검
			axios.get(`/healthyFoodProject/member/hasFld/MEMBER_EMAIL/${idFld.value}`)
				 .then(function(response) {
					
					alert("중복 점검 아이디!!");
					
					idDuplicatedCheckFlag = response.data;
					
					console.log("response.data : ", response.data);
	
					let idDupErrMsg = idDuplicatedCheckFlag == true ? "중복되는 이메일이 존재합니다" : "사용가능한 이메일입니다"				   
					console.log(idDupErrMsg);
					
					alert(idDupErrMsg);
					
					// 메시지 반복 출력 방지 : 출력할 메시지 있으면 출력
					if (idDuplicatedCheckFlag == true) {
					
						idFld.value = "";
					}					
						
				 })
				 .catch(function(err) {
					console.error("이메일아이디 중복 점검 중 서버 에러가 발견되었습니다.");
				 });
			
            // 에러 패널 초기화
            idFldErrPnl.style.height = "0"; 
            idFldErrPnl.innerHTML = "";

            // 에러 점검 플래그
            idCheckFlag = true;
        } // if 아이디 중복 점검

    } // idFld.onblur ...

    //////////////////////////////////////           

    // 패쓰워드 필드 입력 후 이벤트 처리 : keyup: 다 완성했다는 보장은 없음(실시간) // onblur 다 입력하고 빠져나갈 떼
    pwFld.onblur = (e) => { 
		
		// 기준)
        /*
            1) 길이(length) 8~20 : {8,20}
            2) 최소 1개의 숫자 포함 : (?=.*\d)
            3) 최소 1개의 특수문자 포함 : (?=.*\W)
            4) 대문자 1개 이상 포함 : (?=.*[A-Z])
            5) 소문자 1개 이상 포함 : (?=.*[a-z])
            6) regex(정규표현식) : (?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,20}
            7) 메시징 : 회원 패쓰워드는 영문 대소/숫자/특수문자 1개이상 포함하여 8~20자로 작성하십시오..
        */
		
        console.log("패쓰워드 필드 keyup")
        pwCheckFlag = isCheckFldValid(pwFld,                                 
                        /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,20}/,
                        pwFld.value,
                        pwFldErrPnl,
                        "회원 패쓰워드는 영문 대소/숫자/특수문자 1개이상 포함하여 8~20자로 작성하십시오.");
    } //     

    /////////////////////////////////////

    // 이름 필드 입력 후 이벤트 처리 : keyup
    nameFld.onblur = (e) => {
		
        // 이름 필드 유효성 점검(validation)
        // 기준)
        /*
            1) 한글"만" : 3~50자 (성함 사이 띄워쓰기 포함)
            2) 성/함(이름) : 1자만 띄워쓰기 ex) 홍 길동, 남궁 민수
            3) regex(정규표현식) : /^[가-힣]{1,2}[\s]{1}[가-힣]{1,47}$/
            4) 메시징 : 회원 이름은 한글 이름만 허용됩니다. 제시된 예와 같이 작성해주세요.
        */
		
        console.log("이름 필드 점검");

                        /*/^[가-힣]{1,2}[\s]{1}[가-힣]{1,47}$/,*/
        nameCheckFlag = isCheckFldValid(nameFld,  
						/^[가-힣]{1,2}[\s]{1}[가-힣]{1,47}$/,                            
                        nameFld.value,
                        nameFldErrPnl,
                        "회원 이름은 한글 이름만 허용됩니다. 제시된 예와 같이 작성해주세요.");
    } //    

    ///////////////////////////////////////////////////////////////////////
	
	// 닉네임 필드 입력 후 이벤트 처리 : onblur
    nickNameFld.onblur = (e) => {
		
        console.log("닉네임 필드 blur")
        nickNameCheckFlag = isCheckFldValid(nickNameFld,
					    /^[\w가-힣]{2,16}$/,    	                          
                        nickNameFld.value,
                        nickNameFldErrPnl,
						"회원 닉네임은 한글 혹은 영문 2~16자 이내로 작성해주세요.");
		
		nickNameDuplicatedCheckFlag = false;
		
		// 폼 유효성 점검(test)
		console.log("nickNameCheckFlag => " + nickNameCheckFlag);
		console.log("nickNameDuplicatedCheckFlag => " + nickNameDuplicatedCheckFlag);

        if (nickNameCheckFlag == true && nickNameDuplicatedCheckFlag == false) {

			console.log("닉네임 중복 점검 진입");
			
			// 닉네임 중복 점검
			axios.get(`/healthyFoodProject/member/hasFld/MEMBER_NICK/${nickNameFld.value}`)
				 .then(function(response) {
					
					alert("중복 점검 닉네임!!");
					
					nickNameDuplicatedCheckFlag = response.data;
					
					console.log("response.data : ", response.data);
	
					let nickNameDupErrMsg = nickNameDuplicatedCheckFlag == true ? "중복되는 닉네임이 존재합니다" : "사용가능한 닉네임입니다"				   
					console.log(nickNameDupErrMsg);
					
					alert(nickNameDupErrMsg);
					
					// 메시지 반복 출력 방지 : 출력할 메시지 있으면 출력
					if (nickNameDuplicatedCheckFlag == true) {
					
						nickNameFld.value = "";
					}					
						
				 })
				 .catch(function(err) {
					console.error("이메일아이디 중복 점검 중 서버 에러가 발견되었습니다.");
				 });
			
            // 에러 패널 초기화
            nickNameFldErrPnl.style.height = "0"; 
            nickNameFldErrPnl.innerHTML = "";

            // 에러 점검 플래그
            nickNameCheckFlag = true;
        } // if 아이디 중복 점검
                        
    } //    
    
    ///////////////////////////////////////////////////////////////////////

    // 주소 필드 입력 후 이벤트 처리 : 상세주소 필드 => onblur
    // 점검 사항 :
    // 1) 주소 필드의 경우 필수 사항인 경우는 우편번호/기본주소/상세주소가 다 들어가는지 점검해야 합니다.
    // 2) 필수 사항이 아닐 경우는 다 비워져 있는 경우는 문제가 안되며, 그렇지 않고 필드 한개가 누락된 경우는
    //    폼 점검 에러를 유발하도록 구성합니다.

    memberAddress2Fld.onblur = (e) => {
	
		console.log("상세주소 폼 점검");

        // 점검 경우(주소 정보가 필수사항이 아닌 경우) : 점검 오류 발생 경우
        
        // 1) 우편번호/기본주소가 채워져 있는데 상세주소가 비워져 있는 경우
        //    - 에러 메시지 : 상세 주소를 입력하십시오.

        // 2) 우편번호/기본주소가 비워져 있는데 상세주소가 채워져 있는 경우
        //    - 에러 메시지 : 주소 검색을 통해서 우편번호와 기본주소를 입력하십시오.

        console.log("주소 필드에러 메시지 : " + addressFldErrPnl.innerHTML);

        addressCheckFlag = isCheckAddressFldValid(memberZipFld, memberAddress1Fld, memberAddress2Fld, addressFldErrPnl);

    } //

    ///////////////////////////////////////////////////////////////////////

     // (검색된) 주소 삭제
    let addressDeleteBtn = document.getElementById("address_delete_btn");

    addressDeleteBtn.onclick = function(e) {

        console.log("주소 삭제");

        memberZip.value = "";
        memberAddress1Fld.value = "";
        // jibunAddressFld.value = "";

		// 주소 에러 메시지 제거
		addressFldErrPnl.innerHTML = ""; // 에러 메시지 삭제
    } //

    /////////////////////////////////////////////////////////////////

	// 연락처(휴대폰) 중복 점검 : AJAX axios
	mobileFld.onblur = (e) => {
		
		mobileCheckFlag = isCheckFldValid(mobileFld,
                        /^010\d{4}\d{4}$/,
                        mobileFld.value,
                        mobileFldErrPnl,
                        "회원 연락처(휴대폰)를 제시된 예와 같이 작성해주세요.")

		mobileDuplicatedCheckFlag = false;

		console.log("mobileCheckFlag => " + mobileCheckFlag);
		console.log("mobileDuplicatedCheckFlag => " + mobileDuplicatedCheckFlag);
			
		if (mobileCheckFlag == true && mobileDuplicatedCheckFlag == false) {
			 
			console.log("mobile중복점검 진입 확인");
			
			// mobile ==> MEMBER_MOBILE 변경함
			axios.get(`/healthyFoodProject/member/hasFld/MEMBER_MOBILE/${mobileFld.value}`)
				 .then(function(response) {
					
					mobileDuplicatedCheckFlag = response.data;
					console.log("response.data : ", response.data);
					
					let mobileDupErrMsg = mobileDuplicatedCheckFlag == true ? "중복되는 연락처(휴대폰)가 존재합니다" : "사용가능한 연락처(휴대폰)입니다"			   
					console.log(mobileDupErrMsg);
					
					alert(mobileDupErrMsg);
					
					// 메시지 반복 출력 방지 : 출력할 메시지 있으면 출력
					if (mobileDuplicatedCheckFlag == true) {

						mobileFld.value = "";
					}	
						
				 })
				 .catch(function(err) {//에러 시. 
					console.error("연락처(휴대폰) 중복 점검 중 서버 에러가 발견되었습니다.");
					//mobileDuplicatedCheckFlag = false;				
				 });
		
		} // if	
				
	} //


    /////////////////////////////////////////////////////////////////
 
    // 전송 버튼 이벤트 처리
    let frm = document.getElementById("frm");

    frm.onsubmit = function(e) {
	
		alert("회원가입 폼점검");	
	
        console.log("\n######## 회원가입폼 전체점검 ###############################\n");

        console.log(`아이디 점검 플래그(idCheckFlag) : ${idCheckFlag}`);
        console.log(`패쓰워드 점검 플래그(pwCheckFlag) : ${pwCheckFlag}`);
        console.log(`이름 점검 플래그(nameCheckFlag) : ${nameCheckFlag}`);
        console.log(`닉네임 점검 플래그(nickNameCheckFlag) : ${nickNameCheckFlag}`);

        // 이메일 및 연락처 점검 플래그
        console.log(`이메일 점검 플래그(emailCheckFlag) : ${emailCheckFlag}`);
        console.log(`연락처(휴대폰) 점검 플래그(mobileCheckFlag) : ${mobileCheckFlag}`);
		console.log(`연락처(집전화) 점검 플래그(phoneCheckFlag) : ${phoneCheckFlag}`);
		
        // 주소 필드 점검 플래그
        addressCheckFlag = isCheckAddressFldValid(memberZipFld, memberAddress1Fld, memberAddress2Fld, addressFldErrPnl); 

        console.log(`주소 점검 플래그(addressCheckFlag) : ${addressCheckFlag}`);
		console.log("주소 플래그 동등 여부 " + (addressCheckFlag == false));

        // 생일 필드 점검 플래그
        console.log(`생일 점검 플래그(birthdayCheckFlag) : ${birthdayCheckFlag}`);

		// 아이디/이메일/연락처(휴대폰) 중복 점검 플래그
		// 주의) 이 플래그들은 false 이어야 중복되지 않는 값을 의미합니다.  
		console.log(`아이디 중복 점검 플래그(idDuplicatedCheckFlag) : ${idDuplicatedCheckFlag}`);
		console.log(`이메일 중복 점검 플래그(emailDuplicatedCheckFlag) : ${emailDuplicatedCheckFlag}`);
		console.log(`연락처(휴대폰) 중복 점검 플래그(mobileDuplicatedCheckFlag) : ${mobileDuplicatedCheckFlag}`);
		
		console.log("\n######################################################\n\n");
		
		alert("잠시 전체 폼점검 플래그 확인");
				
        // 모든 플래그 참(true) : 논리곱(&&)
		// 집전화 필드
		// 아이디/이메일/연락처(휴대폰) 중복 점검 필드 추가
		// 주의) 아이디/이메일/연락처(휴대폰) 중복 점검 필드는 false(중복 안됨)이어야 정상값입니다.
        if (idCheckFlag == true &&
            pwCheckFlag == true &&
            nameCheckFlag == true &&
            nickNameCheckFlag == true &&
            mobileCheckFlag == true &&
            addressCheckFlag == true &&
			idDuplicatedCheckFlag == false &&
			emailDuplicatedCheckFlag == false &&
			mobileDuplicatedCheckFlag == false)
        {
            alert("회원가입 정보 전송");

        } else {
	
            // TODO
			alert("폼 점검 오류");
            console.log("폼 점검 오류");
	
            // 필드들을 종합적으로 일일이 점검할 필요가 있기 때문에 
            // if ~ else if문은 사용하지 않고 개별 if문을 사용하도록 하겠습니다.

            // 아이디 필드 재점검                    
            if (idCheckFlag == false) {

                idCheckFlag = isCheckFldValid(idFld, 
                            /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i,
                            idFld.value,
                            idFldErrPnl,
                            "회원 아이디는 이메일로 적어주십시오.");
            } //

            // 패쓰워드 필드 재점검
            if (pwCheckFlag == false) {

                pwCheckFlag = isCheckFldValid(pwFld,                                 
                            /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,20}/,
                            "",
                            pwFldErrPnl,
                            "회원 패쓰워드는 영문 대소/숫자/특수문자 "+ 
                            "1개이상 포함하여 8~20자로 작성하십시오.");
            } //    

            // 이름 필드 재점검
            if (nameCheckFlag == false) {

                nameCheckFlag = isCheckFldValid(nameFld,                                 
                                /^[가-힣]{1,2}[\s]{1}[가-힣]{1,47}$/,
                                nameFld.value,
                                nameFldErrPnl,
                                "회원 이름은 한글 이름만 허용됩니다. 제시된 예와 같이 작성해주세요.");

            } //

            // 닉네임 필드 재점검
            if (nickNameCheckFlag == false) {

                nickNameCheckFlag = isCheckFldValid(nickNameFld,
					    /^[\w가-힣]{2,16}$/,    	                          
                        nickNameFld.value,
                        nickNameFldErrPnl,
						"회원 닉네임은 한글 혹은 영문 2~16자 이내로 작성해주세요.");

            } //nickNameCheckFlag
               

            // 연락처 필드 재점검
            if (mobileCheckFlag == false) {

                mobileCheckFlag = isCheckFldValid(mobileFld,
                        /^010\d{4}\d{4}$/,
                        mobileFld.value,
                        mobileFldErrPnl,
                        "회원 연락처(휴대폰)를 제시된 예와 같이 작성해주세요.");
            } // 

            // 우편번호/주소 필드 재점검
            if (addressCheckFlag == false) {
                
                addressCheckFlag = isCheckAddressFldValid(memberZipFld, memberAddress1Fld, jibunAddressFld, memberAddress2Fld, addressFldErrPnl); 
            } //

			// 아이디/이메일/연락처(휴대폰) 중복 재점검에 따른 최종 메시징			
			if (idDuplicatedCheckFlag == true) {
				alert("중복되는 아이디가 존재합니다");
			}
			
			if (mobileDuplicatedCheckFlag == true) {
				alert("중복되는 연락처(휴대폰)가 존재합니다");
			}
			
		    // 전송 방지
		    e.preventDefault();			
        } //

    } // frm.onsubmit ...

} // window.onload ...