package com.momo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.momo.mapper.FileuploadMapper;
import com.momo.vo.FileuploadVO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Log4j
public class FileuploadServiceImpl implements FileuploadService{

	@Autowired
	FileuploadMapper mapper;
	
	@Override
	public int insert(FileuploadVO vo) {
		
		return mapper.insert(vo);
	}

	@Override
	public List<FileuploadVO> getList(int bno) {
		
		return mapper.getList(bno);
	}

	@Override
	public int delete(FileuploadVO vo) {
		// 파일 삭제
		
		// 데이터 베이스에서 삭제
		return mapper.delete(vo);
	}
	
	public final String ATTACHES_DIR = "\\c:upload\\tmp\\";
	
	/**
	 * 첨부파일 저장 및 데이터 베이스에 등록
	 * @param files
	 * @param bno
	 * @return
	 */
	public int fileupload(List<MultipartFile> files, int bno) {
		int insertRes = 0;
		for(MultipartFile file : files) {
			// 선택된 파일이 없는 경우 다음 파일로 이동
			if(file.isEmpty()) {
				/// 현재 파일 선택창을 3개를 놨는데, 1, 3번만 파일을 선택할 수 있기 때문에
				/// return이 아니고 continue를 쓴다.
				continue;
			}
			
			log.info("oFileName : " + file.getOriginalFilename());
			log.info("name : " + file.getName());
			log.info("size : " + file.getSize());
			try {
				//UUID
				/*
				 * 소프트웨어 구축에 쓰이는 식별자 표준 
				 * 파일이름이 중복되어 파일이 소실되지 않도록 uuid를 붙여서 저장
				 * 굉장히 드물게 같은 id가 나올 수 있음
				 */
				UUID uuid = UUID.randomUUID();
				String saveFileName = uuid + "_" + file.getOriginalFilename();
				File sFile = new File(ATTACHES_DIR + getFolder() + saveFileName);
				
				// file(원본파일) sFile(저장할 대상 파일)에 저장
				file.transferTo(sFile);

				FileuploadVO vo = new FileuploadVO();
				// 주어진 파일의 Mime유형
				String contentType =
							Files.probeContentType(sFile.toPath());
				
				// Mime타입을 확인하여 이미지인 경우 썸네일을 생성
					/// null 체크를 하지 않으면 사진 파일이 아닌 파일을 업로드 했을 경우 NullpointerException 발생
				if(contentType != null && contentType.startsWith("image")) {
					vo.setFiletype("I");
					// 썸네일 생성 경로
					String thmbnail = ATTACHES_DIR + getFolder() + "s_" + saveFileName;
					// 썸네일 생성
					// 원본파일, 크기, 저장될 경로
					Thumbnails.of(sFile).size(100, 100).toFile(thmbnail);
				} else {
					vo.setFiletype("F");
				}
				
				vo.setBno(bno);
				vo.setFilename(file.getOriginalFilename());
				vo.setUploadpath(getFolder());
				vo.setUuid(uuid.toString());
				
				int res = mapper.insert(vo);
				
				if(res>0) {
					insertRes++;
				}
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return insertRes;
	}

	// 중복 방지용 
	// 		업로드 날짜를 폴더 이름으로 사용
	//		2023/07/18
	public String getFolder() {
		LocalDate currentDate = LocalDate.now();	                       
		String uploadPath = currentDate.toString().replace("-", File.separator) + File.separator;
															/// 마지막 구분자를 넣어주지 않으면 경로로 인식하지 못하고, 마지막 일(day)이 파일명으로 등록 됨
		log.info("currentDate : " + currentDate);
		log.info("경로 : " + uploadPath);
		
		// 폴더 생성(없으면)
		File saveDir = new File(ATTACHES_DIR + uploadPath);
		if(!saveDir.exists()) {
			if(saveDir.mkdirs()) {
				log.info("폴더 생성!!!");
			}else {
				log.info("폴더 생성 실패!!");
			}
		}
		return uploadPath;
	}
	
}
