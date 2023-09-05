package ssg.com.houssg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.SmsResponseDto;
import ssg.com.houssg.service.SmsService;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


@RestController
@RequiredArgsConstructor
public class SmsController {

	@Autowired
    private SmsService smsService;
    
 

	@PostMapping("/user/sms")
	public ResponseEntity<SmsResponseDto> sendSms(@RequestBody RequestDto request) {
	    try {
	        SmsResponseDto data = smsService.sendSms(request.getRecipientPhoneNumber(), request.getContent());
	        return ResponseEntity.ok().body(data);
	    } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | URISyntaxException e) {
	        // 예외 처리 코드를 추가하세요.
	        e.printStackTrace(); // 예외를 적절하게 처리하거나 로그에 기록하세요.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 예외 발생 시 500 에러 반환
	    }
	}

}
