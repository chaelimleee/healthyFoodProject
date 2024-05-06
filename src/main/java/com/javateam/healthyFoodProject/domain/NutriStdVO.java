package com.javateam.healthyFoodProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="NUTRI_STD_TBL")
@Data
public class NutriStdVO {

	@Id
	@Column
	private int num;

	@Column
	private char gender; // 성별

	@Column
	private String age; // 나이대(연령)

	@Column
	private int energy; // 에너지(kcal/일)

	@Column
	private int carbohydrate; // 탄수화물(g/일)

	@Column
	private int protein; // 단백질(g/일)

	@Column
	private String sugar; // 당류(%) : 총에너지 대비

	@Column
	private int natrium; // 나트륨(mg)

	@Column
	private int cholesterol; // 콜레스테롤(mg) : 지질(총계)

	@Column
	private String fat; // 지방(%) : 지질

	@Column(name = "FATTY_ACID")
	private int fattyAcid; // 포화지방산(%) : 지질

	@Column(name = "TRANS_FATTY_ACID")
	private int transFattyAcid; // 트랜스지방산(%) : 지질
}
