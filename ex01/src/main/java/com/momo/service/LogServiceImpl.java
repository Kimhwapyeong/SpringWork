package com.momo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momo.mapper.LogMapper;
import com.momo.vo.LogVO;

@Service
public class LogServiceImpl implements LogService{

	@Autowired
	LogMapper logMapper;
	
	@Override
	public int insert(LogVO vo) {
		
		return logMapper.insert(vo);
	}

}
