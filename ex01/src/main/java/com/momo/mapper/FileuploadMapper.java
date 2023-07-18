package com.momo.mapper;

import java.util.List;

import com.momo.vo.FileuploadVO;

public interface FileuploadMapper {
	
	/**
	 * 하나의 게시판에 대한 파일 조회
	 * @param bno
	 * @return
	 */
	public List<FileuploadVO> getList(int bno);
	
	public int insert(FileuploadVO vo);
}
