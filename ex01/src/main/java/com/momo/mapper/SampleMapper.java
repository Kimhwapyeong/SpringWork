package com.momo.mapper;

import org.apache.ibatis.annotations.Select;

public interface SampleMapper {
	
	@Select("select sysdate from dual")
	String getTime();
	
	String getTime2();
}
