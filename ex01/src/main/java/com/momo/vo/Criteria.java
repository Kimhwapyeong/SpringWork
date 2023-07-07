package com.momo.vo;

import lombok.Data;

@Data
public class Criteria {
	
	private String searchField; // 검색조건
	private String searchWord;	// 검색어
	
	private int pageNo = 1;		// 요청 페이지 번호
	private int amount = 10;	// 페이지 당 게시물 수

	private int startNo = 1;
	private int endNo = 10;
	
	
	/// 필드값이 넘어와서 객체 입력될 때 set 메서드를 사용한다.
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if(pageNo > 0) {
			endNo = pageNo * amount;
			startNo = pageNo * amount - (amount - 1);
		}
		
	}
	
	
}
