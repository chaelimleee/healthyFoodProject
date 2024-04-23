
        // 로컬 스토리지에서 결과 데이터 가져오기
        var percentage = parseFloat(localStorage.getItem("percentage"));
        var percentageA = parseFloat(localStorage.getItem("percentageA"));
        var typeA = isNaN(percentageA) ? 0 : percentageA;
        var percentageB = parseFloat(localStorage.getItem("percentageB"));
        var typeB = isNaN(percentageB) ? 0 : percentageB;
        var percentageC = parseFloat(localStorage.getItem("percentageC"))
        var typeC = isNaN(percentageC) ? 0 : percentageC;
        var percentageD = parseFloat(localStorage.getItem("percentageD"))
        var typeD = isNaN(percentageD) ? 0 : percentageD;
        var sasangType = localStorage.getItem("sasangType");
        var description = localStorage.getItem("description");
        var healthManagement = localStorage.getItem("healthManagement");
        var goodFoodList = localStorage.getItem("goodFoodList")
        var foodRecommend = JSON.parse(localStorage.getItem("foodRecommend"));

        // 결과를 HTML에 표시하기
        document.getElementById("percentage").textContent = percentage;
        document.getElementById("Taeyangin").textContent = typeA;
        document.getElementById("Taeumin").textContent = typeB;
        document.getElementById("Soumin").textContent = typeC;
        document.getElementById("Soyangin").textContent = typeD;
        document.getElementById("sasangType").textContent = sasangType;
        document.getElementById("description").textContent = description;
        document.getElementById("healthManagement").textContent = healthManagement;
        document.getElementById("goodFoodList").textContent = goodFoodList;
        var foodRecommendList = document.getElementById("foodRecommend");
        foodRecommend.forEach(function(food) {
            var listItem = document.createElement("li");
            listItem.textContent = food;
            foodRecommendList.appendChild(listItem);
        });

