package com.momo.fileupload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.service.FileuploadService;
import com.momo.vo.FileuploadVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class FileuploadServiceTest {
	
	@Autowired
	FileuploadService service;
	
	@Test
	public void test() {
		assertNotNull(service);
	}
	
	@Test
	public void getList() {
		List<FileuploadVO> list = service.getList(8);
		
		list.forEach(file ->{
			log.info("내가 출력한 게 어디 있나 : " + file.getFilename());
		});
		
	}
	
	@Test
	public void insert() {
		FileuploadVO vo = new FileuploadVO();
		vo.setFilename("filename2");
		vo.setBno(8);
		vo.setFiletype("I");
		vo.setUploadpath("uploadpath2");
		vo.setUuid("uuid2");
		
		int res = service.insert(vo);
		assertEquals(1, res);
	}
	
	@Test
	public void delete(){
		FileuploadVO vo = new FileuploadVO();
		vo.setUuid("4a4decc1-5414-4e5f-b902-8550f982660c");
		vo.setBno(8);
		
		int res = service.delete(vo);
		
		assertEquals(1, res);
	}
}
