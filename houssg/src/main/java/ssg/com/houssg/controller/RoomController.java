package ssg.com.houssg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.InnerDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.service.InnerService;
import ssg.com.houssg.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	private RoomService service;

	@Autowired
	private InnerService innerService;

	@PostMapping("/upload-and-add")
	public ResponseEntity<String> uploadAndAdd(
	    @RequestParam("file") List<MultipartFile> multiFileList,
	    @RequestParam("description") String fileContent,
	    @RequestParam("room_number") Integer room_number,
	    HttpServletRequest request,
	    @RequestParam RoomDto roomDto
	) {
	    // 이미지 업로드 로직
	    System.out.println("파일 이미지");
	    // 받아온 것 출력 확인
	    System.out.println("multiFileList : " + multiFileList);
	    System.out.println("fileContent : " + fileContent);

	    // path 가져오기
	    String path = request.getSession().getServletContext().getRealPath("/upload");
	    String root = path + "\\" + "uploadFiles";

	    File fileCheck = new File(root);

	    if (!fileCheck.exists()) fileCheck.mkdirs();

	    List<Map<String, String>> fileList = new ArrayList<>();

	    for (int i = 0; i < multiFileList.size(); i++) {
	        String originFile = multiFileList.get(i).getOriginalFilename();
	        String ext = originFile.substring(originFile.lastIndexOf("."));
	        String changeFile = UUID.randomUUID().toString() + ext;
	        Map<String, String> map = new HashMap<>();
	        map.put("originFile", originFile);
	        map.put("changeFile", changeFile);
	        fileList.add(map);
	    }

	    // 파일 업로드
	    try {
	        for (int i = 0; i < multiFileList.size(); i++) {
	            File uploadFile = new File(root + "\\" + fileList.get(i).get("changeFile"));
	            multiFileList.get(i).transferTo(uploadFile);
	        }
	        System.out.println("다중 파일 업로드 성공");
	    } catch (Exception e) {
	        System.out.println("다중 파일 업로드 실패");
	        // 만약 업로드 실패하면 파일 삭제
	        for (int i = 0; i < multiFileList.size(); i++) {
	            new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
	        }
	        e.printStackTrace();
	        // 업로드 실패 시 500 Internal Server Error 반환
	        return new ResponseEntity<>("파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    System.out.println(fileList);
	    List<String> changeFileList = new ArrayList<>();
	    for (Map<String, String> fileName : fileList) {
	        changeFileList.add(fileName.get("changeFile"));
	    }
	    InnerDto innerDto = new InnerDto(
	            room_number,
	            changeFileList.size() > 0 ? changeFileList.get(0) : null,
	            changeFileList.size() > 1 ? changeFileList.get(1) : null,
	            changeFileList.size() > 2 ? changeFileList.get(2) : null,
	            changeFileList.size() > 3 ? changeFileList.get(3) : null,
	            changeFileList.size() > 4 ? changeFileList.get(4) : null,
	            changeFileList.size() > 5 ? changeFileList.get(5) : null,
	            changeFileList.size() > 6 ? changeFileList.get(6) : null,
	            changeFileList.size() > 7 ? changeFileList.get(7) : null,
	            changeFileList.size() > 8 ? changeFileList.get(8) : null,
	            changeFileList.size() > 9 ? changeFileList.get(9) : null);

	    System.out.println(innerDto.toString());
	    innerService.insertInnerView(innerDto);

	    // 방 추가 로직
	    System.out.println(roomDto.toString());
	    int roomCount = service.addRoom(roomDto);
	    if (roomCount == 0) {
	        // 추가 실패 시 500 Internal Server Error 반환
	        return new ResponseEntity<>("방 추가 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // 업로드 성공 시 200 OK 응답 반환
	    return new ResponseEntity<>("파일 업로드 및 방 추가 성공", HttpStatus.OK);
	}
	
	 @GetMapping("room/get")
	    public ResponseEntity<List<RoomDto>> choiceAccom(@RequestParam int roomNumber) {
	        System.out.println(roomNumber);
	        System.out.println("숙소상세로 들어갑니다");
	        List<RoomDto> list = service.choiceAccom(roomNumber);

	        if (list != null && !list.isEmpty()) {
	            // 숙소 정보가 존재할 경우 200 OK 응답과 데이터 반환
	            return new ResponseEntity<>(list, HttpStatus.OK);
	        } else {
	            // 숙소 정보가 없을 경우 404 Not Found 응답 반환
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
}
