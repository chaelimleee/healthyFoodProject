// 사상체질별 정보 클래스
class SasangType {
    constructor(maxAnswer) {
        switch (maxAnswer) {
            case "a":
                this.name = "태양인";
                this.description = "태양인은 호홉기계인 폐기능은 좋으나 간기능은 상대적으로 나쁜 체질(폐대 간소형)";
                this.healthManagement = "태양인은 항시 분노와 슬픔을 누르고 무서움을 다스리며 약물이나 식중독에 걸리기 쉬우므로 음식을 탐하지 말고 가려먹고 소식하시는게 좋습니다";
				this.goodFoodList = "1. 메밀냉면, 2. 우렁이 초무침";
				this.foodRecommend = ["태양인에게 좋은 음식으로는 담백하고 차가운 성질의 음식으로 가물치, 게, 고구마, 꽁치, 낙지, 넙치, 문어, 냉면, 메밀, 쌀, 호밀, 보리, 콩, 새우(대하), 소라, 쌀, 오징어, 우렁이, 자라, 장어, 전복,  청어, 고등어, 토란, 해삼, 홍합 등 조개류가 좋습니다 ", "피해야 할 음식으로는 닭고기, 돼지고기, 개고기(보신탕), 소고기 등 기름진 육류와 상하거나 독성이 있는 음식이며 모든 음식과 약이 독이 될 수 있으므로 가급적 소식하시거나 가려서 드시는게 좋습니다"];
                break;
            case "b":
                this.name = "태음인";
                this.description = "태음인은 간기능은 좋으나 호흡기계인 폐기능이 상대적으로 나쁜 체질(간대 폐소형)";
                this.healthManagement = "태음인은 항시 기쁨과 슬픔을, 두려움을 감정을 다스리고 지나치게 건강에 자신하지마시고 성인병예방을 위해 절주와 식사량을 조절하시는게 좋습니다.";
                this.goodFoodList = "1. 소고기 미나리 솥밥, 2.소고기 무국";
				this.foodRecommend = ["태음인에게 좋은 음식으로는 소고기,  감자, 고구마, 갈치, 고등어, 대구, 메기, 명란, 미꾸라지, 우렁이,  조기, 준치, 장어, 참치,  쌀, 현미, 수수, 율무,  찹쌀, 밀가루,  해삼, 해파리 등 있습니다", "피해야 할 음식으로는 닭고기, 돼지고기, 개고기(보신탕), 염소고기, 꿩고기, 게, 굴,  넙치, 계란(달걀), 달팽이,  메밀, 문어, 보리, 복어, 소라, 우렁이, 조개, 콩, 토란, 팥, 홍합, 밀가루콩, 팥, 가물치, 가재 등이 있습니다," ];
                break;
            case "c":
                this.name = "소음인";
                this.description = "소음인은 비뇨, 내분비, 생식 기능은 좋으나 소화기능이 상대적으로 나쁜 체질(신대 비소형 )";
                this.goodFoodList = "1.불닭 장칼국수, 2.추어탕"; 
				this.healthManagement = "소음인은 항시 기쁨과 슬픔, 불안같은 감정을 잘 다스리고 항상 몸을 따뜻하게 하여야 합니다";
                this.foodRecommend = ["소음인에게 좋은 음식으로는 갈치, 감자, 꿩고기, 도미, 대구, 도루묵, 도토리, 메기, 메추리, 명태, 미꾸라지, 민어, 개고기(보신탕), 붕어, 쌀, 쏘가리, 염소고기, 양고기, 조기, 참치, 찹쌀 칠면조, 밀가루, 현미 등이 있습니다", "피하셔야할 음식으로는 가물치, 가재, 거위, 게, 고구마, 곤약, 굴, 꽁치, 낙지, 넙치, 녹두, 달팽이, 돼지고기, 메밀, 문어, 밀가루, 보리, 복어, 빵, 소라, 소고기, 오리고기, 오리알, 오징어, 우렁이, 율무, 자라, 장어, 전복, 콩, 토끼고기, 팥, 홍어, 홍합 등이 있습니다 "];
                break;
            case "d":
                this.name = "소양인";
                this.description = "소양인은 소화기능은 좋으나 비뇨, 내분비, 생식 기능이 상대적으로 나쁜 체질(비대 신소형)";
                this.healthManagement = "소양인은 항시 슬픔과 두려움을 잘 다스리고 변덕과 분노(화)를 잘조절하고 몸의 열기를 잘 발산하여 내려야 합니다 ";
                this.goodFoodList ="1. 마늘소스 통삼겹살, 2. 냉라면";
				this.foodRecommend = ["소양인에게 좋은 음식으로는 가물치, 가재, 가자미, 강낭콩, 계란(달걀), 거위, 게, 고구마, 곤약, 굴, 꽁치, 낙지, 넙치, 녹두,  달팽이, 도미, 돼지고기, 두부, 멍게, 메밀, 문어, 보리, 복어, 쌀, 콩, 새우(대하), 성게, 소라, 잉어, 오리, 오리알, 오징어, 우렁이, 자라, 전복, 토끼고기, 밀가루, 토란, 팥, 해삼, 홍어, 홍합 등이 있습니다", "피하셔야할 음식으로는 갈치, 고등어, 꿩고기, 닭고기, 도토리, 메기, 미꾸라지, 개고기(보신탕), 수수, 소고기, 염소고기, 옥수수, 율무, 장어, 조기, 찹쌀, 칠면조, 밀가루, 현미 등이 있습니다"];
                break;
            default:
                break;
        }
    }
}


// 폼 요소 가져오기
//var form = document.getElementById('testForm');
var form = document.querySelector('[id^=testForm]');

// 클릭 이벤트에 대한 핸들러 할당
form.addEventListener('click', function(event) {
	
	let memberEmail = form.id.split('_')[1];
	//alert("memberEmail 다시 확인 >> " + memberEmail );
	
    // 클릭된 요소가 내부의 라디오 버튼이면 submitHandler 함수 호출
    if (event.target.type === 'radio') {
        submitHandler(memberEmail);
    }
});

// 폼 제출 이벤트에 대한 핸들러 할당
form.addEventListener('submit', function(event) {
    // 기본 동작 방지
    event.preventDefault();
    // 아무 동작도 수행하지 않음
});

// submitHandler 함수
function submitHandler(memberEmail) {
    var selectedAnswers = {}; // 선택된 답변을 저장할 객체

    // 각 질문에 대해 사용자가 선택한 답변 확인
    var radios = form.querySelectorAll('input[type="radio"]:checked');
    radios.forEach(function(radio) {
        var answer = radio.value;
        selectedAnswers[answer] = (selectedAnswers[answer] || 0) + 1;
    });

   console.log("selectedAnswers: ", selectedAnswers);
	console.log("radios: ", radios);

    // 모든 질문에 대한 답변을 확인한 후에 실행
    if (radios.length === 20) {
        // 최대 답변 찾기
        var maxAnswer = "";
        var maxCount = 0;
        for (const answer in selectedAnswers) {
            if (selectedAnswers[answer] > maxCount) {
                maxAnswer = answer;
            	maxCount = selectedAnswers[answer];
				console.log("maxAnswer: ", maxAnswer);
				console.log("maxCount: ", maxCount);
				console.log("memberEmail: ", memberEmail);
				
            }
        }
        showResult(memberEmail,maxAnswer, maxCount, selectedAnswers);
    } 
    return selectedAnswers;
}
var selectedAnswers = submitHandler;

// showResult 함수
function showResult(memberEmail, maxAnswer, maxCount,selectedAnswers) {
    const percentage = (maxCount / 20) * 100;
    
	var percentageA = (selectedAnswers.a /20) * 100;
    var percentageB = (selectedAnswers.b /20) * 100;
    var percentageC = (selectedAnswers.c /20) * 100;
    var percentageD = (selectedAnswers.d /20) * 100;

	console.log("macCount체질확률(%): ", percentage);
    console.log("태양인 확률(%): ", percentageA);
    console.log("태음인 확률(%): ", percentageB);
    console.log("소음인 확률(%): ", percentageC);
    console.log("소양인 확률(%): ", percentageD);
	

	if (percentage > 25) {
        const type = new SasangType(maxAnswer);
        console.log(type.name);
        // 로컬스토리지에 결과 저장 
        localStorage.setItem("percentage", percentage.toFixed(1));
        localStorage.setItem("percentageA", percentageA.toFixed(1));
        localStorage.setItem("percentageB", percentageB.toFixed(1));
        localStorage.setItem("percentageC", percentageC.toFixed(1));
        localStorage.setItem("percentageD", percentageD.toFixed(1));     
        localStorage.setItem("sasangType", type.name);
        localStorage.setItem("description", type.description);
        localStorage.setItem("healthManagement", type.healthManagement);
        localStorage.setItem("goodFoodList", type.goodFoodList);
        localStorage.setItem("foodRecommend", JSON.stringify(type.foodRecommend));


		axios.get(`/healthyFoodProject/sasang/sasangResult.do/${type.name}`)
				 .then(function(response) {
					
						let responseData = response.data;
						console.log("response.data : ", responseData);
						
						// alert(responseData);
						
						/*if(responseData == '회원정보 체질 수정에 성공하셨습니다.'){
							let result = document.getElementById('result');
							result.innerHTML=resultText;
						} else {
							result.innerHTML='';
						}*/
					 })
					 .catch(function(err) {
						console.error("서버 에러가 발생했습니다. >> " + err);
						alert(err);
						//result.innerHTML='';
					 }
			 );
			 // axios
		window.location.href = "sasangResult.do/" + type.name;
        //window.location.href = "sasangResult.do/"+memberEmail + "/" + type.name;
		//window.location.href = "sasangResult.do";	
		return type.name;
	
        // result.html로 이동
	} // if
	
} // showResult
