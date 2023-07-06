package com.momo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.mapper.BoardMapper;
import com.momo.vo.BoardVO;
import com.momo.vo.Criteria;
import com.momo.vo.pageDto;

/**
 * 각 계층간의 연결은 인터페이스를 활용하여 느슨한 결합을 합니다.
 * 느슨한 결합 : 하나의 컴포넌트의 변경이 다른 컴포넌트들의 변경을 요구하는 위험을 줄이는 것을
 * 				목적으로 하는 시스템에서 컴포넌트 간의 내부 의존성을 줄이는 것을 추구하는 디자인 목표
 * 
 * Service
 * 		계층 구조상 미즈니스 영역을 담당하는 객체임을 표시
 * 
 * root-context.xml
 * 	component-scan 속성에 패키지를 등록합니다.
 * 
 * 서비스를 Interface로 생성하는 이유
 * 
 * 1. 내부로직의 분리
 * 	인터페이스를 사용함으로써 내부로직의 변경, 수정시 유연하게 대처할 수 있다
 * 2. 구현체의 전환이 용이
 * 	구현체의 변경, 교체가 가능하다
 * 3. 테스트 용이성
 * 	단위테스트시 테스트용 구현체를 이용함으로써 테스트를 수행
 */

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper boardMapper;

	@Override
	public List<BoardVO> getListXml(Criteria cri, Model model) {
		/*
		 * 1. 리스트 조회
		 * 		- 검색어, 페이지정보(startNo ~ endNo까지 조회)
		 * 2. 총 건수 조회
		 * 3. pageDto 객체 생성 
		 */
		int totalCnt = boardMapper.getTotalCnt(cri);
		pageDto pageDto = new pageDto(cri, totalCnt);
		List<BoardVO> list = boardMapper.getListXml(cri);
		
		
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("pageDto", pageDto);
		model.addAttribute("list", list);
		return null;
	}

	@Override
	public int insert(BoardVO board) {
		
		return boardMapper.insert(board);
	}

	@Override
	public int insertSelectKey(BoardVO board) {
		
		return boardMapper.insertSelectKey(board);
	}

	@Override
	public BoardVO getOne(String bno) {
	
		return boardMapper.getOne(bno);
	}

	@Override
	public int delete(String bno) {
		
		return boardMapper.delete(bno);
	}

	@Override
	public int update(BoardVO board) {
		
		return boardMapper.update(board);
	}

	@Override
	public int getTotalCnt(Criteria cri) {
	
		return boardMapper.getTotalCnt(cri);
	}

	
}
