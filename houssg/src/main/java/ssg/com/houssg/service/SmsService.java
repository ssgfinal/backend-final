package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import ssg.com.houssg.dao.SmsCodeDao;
import ssg.com.houssg.dto.MessageDto;
import ssg.com.houssg.dto.SmsCodeDto;
import ssg.com.houssg.dto.SmsRequestDto;
import ssg.com.houssg.dto.SmsResponseDto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SmsService {

	@Value("${naver-cloud-sms.serviceId}")
	private String serviceId;
	@Value("${naver-cloud-sms.accessKey}")
	private String accessKey;
	@Value("${naver-cloud-sms.secretKey}")
	private String secretKey;

	@Autowired
	public SmsCodeDao smsCodeDao;

	public SmsResponseDto sendSms(String recipientPhoneNumber, String content, HttpSession session)
			throws Exception {
		Long time = System.currentTimeMillis();
		
		// DB에서 recipientPhoneNumber로 이미 저장된 인증 번호 레코드를 조회
	    SmsCodeDto smsCodeDto = smsCodeDao.getCodeByphoneNumber(recipientPhoneNumber);

	    if (smsCodeDto == null) {
	        // 기존 레코드가 없으면 새로운 6자리 인증 번호 생성
	    	smsCodeDto = new SmsCodeDto();
	    	smsCodeDto.setPhoneNumber(recipientPhoneNumber);
	    	smsCodeDto.setVerificationCode(generateVerificationCode());
	    	smsCodeDto.setExpirationTime(new Date(System.currentTimeMillis() + 3 * 60 * 1000 + 30 * 1000));
	    	smsCodeDao.saveCode(smsCodeDto);
	    }
	    else {
	        // 기존 레코드가 있으면 request_count를 확인하여 제한을 검사하고 업데이트
	        if (smsCodeDto.getRequestCount() >= 5) {
	            // 제한을 초과한 경우
	            throw new Exception("요청 횟수 제한을 초과했습니다.");
	        }
	    }
	    
	    // 새로운 6자리 인증 번호 생성
	    String newVerificationCode = generateVerificationCode();

	    // 유저에게 새로운 인증 번호를 전달 또는 저장
	    smsCodeDto.setVerificationCode(newVerificationCode);

	    // 유효 시간 업데이트 (3분 30초)
	    smsCodeDto.setExpirationTime(new Date(System.currentTimeMillis() + 3 * 60 * 1000 + 30 * 1000));
	    
	    // request_count 업데이트
	    smsCodeDto.setRequestCount(smsCodeDto.getRequestCount() + 1);
	    // DB에 저장 또는 업데이트
	    smsCodeDao.updateCode(smsCodeDto);

	    // DB에 저장된 정보
	    System.out.println(smsCodeDto);

		String smsContent = "[HOUS-SG] 인증번호 " + "[" + newVerificationCode + "]" + "를 입력해주세요.";

		// 메시지 생성
		List<MessageDto> messages = new ArrayList<>();
		messages.add(new MessageDto(recipientPhoneNumber, smsContent));

		SmsRequestDto smsRequest = new SmsRequestDto("SMS", "COMM", "82", "01025070634", smsContent, messages);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(smsRequest);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", this.accessKey);
		String sig = makeSignature(time); // 암호화
		headers.set("x-ncp-apigw-signature-v2", sig);

		HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		SmsResponseDto smsResponse = restTemplate.postForObject(
				new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages"), body,
				SmsResponseDto.class);
		return smsResponse;
	}

	// 6자리 난수 생성
	public String generateVerificationCode() {
		SecureRandom secureRandom = new SecureRandom();
		int code = secureRandom.nextInt(900000) + 100000; // 100000부터 999999까지의 난수 생성
		return String.valueOf(code);
	}

	public String makeSignature(Long time)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + this.serviceId + "/messages";
		String timestamp = time.toString();
		String accessKey = this.accessKey;
		String secretKey = this.secretKey;

		String message = new StringBuilder().append(method).append(space).append(url).append(newLine).append(timestamp)
				.append(newLine).append(accessKey).toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return encodeBase64String;
	}
}