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
		
	@Autowired
	FileuploadService service;

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

		int insertRes = service.fileupload(files, bno);
//		files.forEach(file ->{   /// forEach를 사용하면 밖에서 선언된 변수에 접근 불가하여 변경
		
//		});
		/// 메시지를 String으로 저장해서 쿼리스트링으로 넘겨주면 한글 깨짐
		rttr.addFlashAttribute("message", insertRes + "건 저장되었습니다");
		return "redirect:/file/fileupload";
	}

	@PostMapping("/file/fileuploadActionFetch")
	public @ResponseBody Map<String, Object> fileuploadAction(List<MultipartFile> files, int bno) {
		
		log.info("fileuploadActionFetch");
		int insertRes = service.fileupload(files, bno);
		log.info("업로드 건수 : " + insertRes);
		return responseMap("success", insertRes + "건 저장되었습니다.");
	}
	

	
	
	@GetMapping("/file/list/{bno}")
	public @ResponseBody Map<String, Object> fileuploadList(@PathVariable("bno") int bno) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", service.getList(bno));
		
		return map;
	}
	
	public static void main(String[] args) {
		LocalDate currentDate = LocalDate.now();
		String uploadPath = currentDate.toString().replace("-", File.separator) + File.separator;

		System.out.println("currentDate : " + currentDate);
		System.out.println("경로 : " + uploadPath);
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












