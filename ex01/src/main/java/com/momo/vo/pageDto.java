package com.momo.vo;

import lombok.Data;

@Data
public class pageDto {

	Criteria cri;	// 페이지번호, 페이지당 게시물 수
	int total;		// 총 게시물의 수
	
	int startNo;	// 페이지 블록의 시작번호
	int endNo;		// 페이지 블록의 끝번호
	
	boolean prev, next;	// 이전, 다음 버튼 활성(true)/비활성(false) 
	
	int realEnd;	// 페이지 블록의 진짜 끝 번호
	
	public pageDto(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		// 페이지 블럭의 끝번호
		endNo = (int)(Math.ceil(cri.getPageNo()/5.0) * 5);
		startNo = endNo - (5 - 1);
		
		
		realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount()));
		
		endNo = realEnd > endNo ? endNo : realEnd;
		
		prev = startNo > 1 ? true : false;
		next = endNo == realEnd ? false : true;
	}
}
