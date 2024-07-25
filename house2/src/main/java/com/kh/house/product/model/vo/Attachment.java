package com.kh.house.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	public class Attachment {
		private int fileNo;
		private int refHouseNo;
		private String originName;
		private String changeName;
		


}
