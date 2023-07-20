package com.momo.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.momo.service.FileuploadService;
import com.momo.vo.FileuploadVO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@Log4j
public class FileuploadController extends CommonRestController{
		
	/**
	 * 파일 업로드용 라이브러리 추가
	 * commons-fileupload
	 * 
	 * cos.jar와 달리 파일을 저장하는 로직이 추가 되어야 합니다!
	 * 
	 * 1. 라이브러리 추가
	 * 2. multpartResolver 빈 등록
	 * 3. 메서드의 매개변수로 MultipartFile이용
	 */

	@GetMapping("/file/fileupload")
	public void fileupload() {
		
	}
	
	private static final String ATTACHES_DIR = "c:\\upload\\tmp\\";
	
	/**
	 * 오류가 나는 경우
	 *  - 전달된 파일이 없는 경우
	 * 	- enctype="multipart/form-data" 오타가 있을 때
	 * 	- 설정이 안되었을 때
	 * 		라이브러리 추가, 빈 등록
	 * @param files
	 * @return
	 */
	@PostMapping("/file/fileuploadAction")
	public String fileuploadAction(List<MultipartFile> files, int bno, RedirectAttributes rttr) {

		int insertRes = fileupload(files, bno);
//		files.forEach(file ->{   /// forEach를 사용하면 밖에서 선언된 변수에 접근 불가하여 변경
		
//		});
		/// 메시지를 String으로 저장해서 쿼리스트링으로 넘겨주면 한글 깨짐
		rttr.addFlashAttribute("message", insertRes + "건 저장되었습니다");
		return "redirect:/file/fileupload";
	}

	@PostMapping("/file/fileuploadActionFetch")
	public @ResponseBody Map<String, Object> fileuploadAction(List<MultipartFile> files, int bno) {
		
		log.info("fileuploadActionFetch");
		int insertRes = fileupload(files, bno);
		log.info("업로드 건수 : " + insertRes);
		return responseMap("success", insertRes + "건 저장되었습니다.");
	}
	

	
	@Autowired
	FileuploadService service;
	
	@GetMapping("/file/list/{bno}")
	public @ResponseBody Map<String, Object> fileuploadList(@PathVariable("bno") int bno) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", service.getList(bno));
		
		return map;
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
	
	public static void main(String[] args) {
		LocalDate currentDate = LocalDate.now();
		String uploadPath = currentDate.toString().replace("-", File.separator) + File.separator;

		System.out.println("currentDate : " + currentDate);
		System.out.println("경로 : " + uploadPath);
	}
	
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
				
				int res = service.insert(vo);
				
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
	
	@GetMapping("/file/delete/{bno}/{uuid}")
	public @ResponseBody Map<String, Object> delete(
												@PathVariable("uuid") String uuid
												, @PathVariable("bno") int bno){
		
		FileuploadVO vo = new FileuploadVO();
		vo.setBno(bno);
		vo.setUuid(uuid);
		
		int res = service.delete(vo);
		if(res>0) {
			return responseMap(REST_SUCCESS, "삭제 성공");
		} else {
			return responseMap(REST_FAIL, "삭제 실패");
		}
	}
	
	/**
	 * 파일 다운로드
	 * 		컨텐츠 타입을 다운로드 받을 수 있는 형식으로 지정하여 
	 * 		브라우저에서 파일을 다운로드 할 수 있게 처리
	 * @param fileName
	 * @return
	 */
	@GetMapping("/file/download")
	public ResponseEntity<byte[]> download(String fileName){
		log.info("download file : " + fileName);
		HttpHeaders headers = new HttpHeaders();
		
		File file = new File(ATTACHES_DIR + fileName);
		
		if(file.exists()) {
			// 컨텐츠 타입을 지정
			// APPLICATION_OCTET_STREAM : 이진 파일의 콘텐츠 유형
			headers.add("contentType"
					, MediaType.APPLICATION_OCTET_STREAM.toString());
		
			// 컨텐츠에 대한 추가 설명 및 파일이름 한글 처리
			try {
				headers.add("Content-Disposition"
						, "attachment; filename=\""
						+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
				return new ResponseEntity<>(
								FileCopyUtils.copyToByteArray(file)
								, headers
								, HttpStatus.OK
						);
			} catch (UnsupportedEncodingException e) {
			
				e.printStackTrace();
				return new ResponseEntity<>(
						HttpStatus.INTERNAL_SERVER_ERROR);
						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}












