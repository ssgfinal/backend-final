package ssg.com.houssg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.InnerDto;
import ssg.com.houssg.service.InnerService;


@RestController
public class InnerController {
	
	@Autowired
	public InnerService service;

	// (9) 이미지 업로드 진행
		@PostMapping("inner-file")
		public String multiFileUpload(@RequestParam("file") List<MultipartFile> multiFileList, @RequestParam("description") String fileContent, @RequestParam("room_number") Integer room_number, HttpServletRequest request) {
			System.out.println("파일 이미지");
			// 받아온것 출력 확인
			System.out.println("multiFileList : " + multiFileList);
			System.out.println("fileContent : " + fileContent);
			
			// path 가져오기
			String path = request.getSession().getServletContext().getRealPath("/upload");
			String root = path + "\\" + "uploadFiles";
			
			File fileCheck = new File(root);
			
			if(!fileCheck.exists()) fileCheck.mkdirs();
			
			
			List<Map<String, String>> fileList = new ArrayList<>();
			
			for(int i = 0; i < multiFileList.size(); i++) {
				String originFile = multiFileList.get(i).getOriginalFilename();
				String ext = originFile.substring(originFile.lastIndexOf("."));
				String changeFile = UUID.randomUUID().toString() + ext;					
				Map<String, String> map = new HashMap<>();
				map.put("originFile", originFile);
				map.put("changeFile", changeFile);		
				fileList.add(map);
			}	
			
			// 파일업로드
			try {
				for(int i = 0; i < multiFileList.size(); i++) {
					File uploadFile = new File(root + "\\" + fileList.get(i).get("changeFile"));
					multiFileList.get(i).transferTo(uploadFile);
				}		
				System.out.println("다중 파일 업로드 성공");			
			} catch (Exception e ) {
				System.out.println("다중 파일 업로드 실패");
				// 만약 업로드 실패하면 파일 삭제
				for(int i = 0; i < multiFileList.size(); i++) {
					new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
				}		
				e.printStackTrace();
			}
			System.out.println(fileList);
			List<String> changeFileList = new ArrayList<>();
			for(Map<String, String> fileName:fileList) {
				changeFileList.add(fileName.get("changeFile"));
			}
			InnerDto dto = new InnerDto(
					room_number,
					changeFileList.size()>0 ? changeFileList.get(0) : null,
					changeFileList.size()>1 ? changeFileList.get(1) : null,
					changeFileList.size()>2 ? changeFileList.get(2) : null,
					changeFileList.size()>3 ? changeFileList.get(3) : null,
					changeFileList.size()>4 ? changeFileList.get(4) : null,
					changeFileList.size()>5 ? changeFileList.get(5) : null,
					changeFileList.size()>6 ? changeFileList.get(6) : null,
					changeFileList.size()>7 ? changeFileList.get(7) : null,
					changeFileList.size()>8 ? changeFileList.get(8) : null,
					changeFileList.size()>9 ? changeFileList.get(9) : null);
			
			System.out.println(dto.toString());	
			service.insertInnerView(dto);		
			return "result";
		}
}
