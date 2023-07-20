package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.momo.vo.FileuploadVO;

@Service
public interface FileuploadService {

	public List<FileuploadVO> getList(int bno);
	
	public int insert(FileuploadVO vo);
	
	public int delete(FileuploadVO vo);
	
	public int fileupload(List<MultipartFile> files, int bno);
	
	public String getFolder();
	
}
