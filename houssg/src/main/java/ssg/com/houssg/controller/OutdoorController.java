package ssg.com.houssg.controller;


import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.OutdoorDto;
import ssg.com.houssg.service.OutdoorService;

@RestController
public class OutdoorController {

	@Autowired
	public OutdoorService service;
	

	// (9) 이미지 업로드 진행
	@PostMapping("multi-file")
	public String multiFileUpload(@RequestParam("file") List<MultipartFile> multiFileList, @RequestParam("description") String fileContent, @RequestParam("accom_number") Integer accom_number, HttpServletRequest request) {
		
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
		OutdoorDto dto = new OutdoorDto(
				accom_number,
				changeFileList.size()>0 ? changeFileList.get(0) : null,
				changeFileList.size()>1 ? changeFileList.get(1) : null,
				changeFileList.size()>2 ? changeFileList.get(2) : null,
				changeFileList.size()>3 ? changeFileList.get(3) : null,
				changeFileList.size()>4 ? changeFileList.get(4) : null,
				changeFileList.size()>5 ? changeFileList.get(5) : null
		);
		System.out.println(dto.toString());	
		service.insertOutdoorView(dto);		
		return "result";
	}
	
}
