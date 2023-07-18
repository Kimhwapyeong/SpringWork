package com.momo.vo;

import lombok.Data;

@Data
public class FileuploadVO {
	
	private String uuid;
	private String uploadpath;
	private String filename;
	private String filetype;
	private int bno;
	
	// 저장된 파일경로
	private String savePath;
	private String s_savePath;
	//uploadpath + uuid + "_" + filename;
}
