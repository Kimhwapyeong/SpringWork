package com.momo.vo;

import lombok.Data;

@Data
public class BookVO {
	
	private String no;		// 도서 일련번호
	private String title;	// 도서명
	private String author;	// 작가
	
	private String sfile;	// 저장된 파일명
	private String ofile;	// 원본 파일명
	
	private String id;		// 대여자 id
	private String rentyn;	// 도서 대여여부
	private String rentStr; /// 사용자에게 대여여부를 보여줄 때?
	
	private String rentno;	// 대여번호
	private String startDate;	// 대여시작일
	private String endDate;		// 반납가능일
	private String returnDate;	// 반납일
	private String overDate;	// 연체일
	
}
