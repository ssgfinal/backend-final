package ssg.com.houssg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.subst.Token.Type;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.AccommodationParam;
import ssg.com.houssg.service.AccommodationService;


@RestController
public class AccommodationController {

    @Autowired
    private AccommodationService service;
    

    @GetMapping("getAllAccom")
    public List<AccommodationDto> getAllAccom() {
    	System.out.println("리스트에 접근합니다");
        return service.getAllAccom();
    }
    
    
    @GetMapping("getAccomType")
    public List<AccommodationDto> getAccomType(String type){
    	System.out.println("타입별 검색");
    	if(type==null) {
    		
    	}
    	return service.getAccomType(type);
    }
    
    @GetMapping("getAddressSearch")
    public List<AccommodationDto> getAddressSearch(@RequestParam String search) {
        System.out.println("주소별 검색");
        AccommodationParam param = new AccommodationParam();
        search = "%" + search.replace(" ", "%") + "%";
        param.setSearch(search);
        List<AccommodationDto> accommodations = service.getAddressSearch(param);
        return accommodations;
    }
    
    // (3) 숙소 등록이 진행됨
    @PostMapping("addAccom")
    public String addAccom(AccommodationDto dto) {
    	//
    	System.out.println(dto.toString());
        int count = service.addAccom(dto); // (4) 숙소 등록
        if(count == 0) { // (5-1) 정상 등록 실패 시, "NO"를 반환
        	return "NO";
        }
        return Integer.toString(dto.getAccomNumber()); // (5-2) 정상 등록 시, auto_increment된 accom_number를 반환 
    }
    
    @GetMapping("getMyAccom")
    public List<AccommodationDto> getMyAccom(String id){
    	System.out.println("내숙소 조회");
    	return service.getMyAccom(id);
    }
    
    @PostMapping("updateAccom")// 수정해야함(오류남)
    public String updateAccom(AccommodationDto dto) {
    	System.out.println("숙소 수정");
    	System.out.println(dto.toString());
    	int count = service.updateAccom(dto);
    	if(count == 0) {
    		return "NO";
    	}
    	return "YES";
    }

}
