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

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.net.URISyntaxException;
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

	public SmsResponseDto sendSms(String recipientPhoneNumber, String verificationCode, HttpSession session)
			throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException,
			URISyntaxException {
		Long time = System.currentTimeMillis();
//
//		// 6자리 난수 생성
//		String verificationCode = generateVerificationCode();
////      session.setAttribute("verificationCode", verificationCode);
////      System.out.println("세션에 저장된 인증번호: " + session.getAttribute("verificationCode"));
//
//		// 세션 아이디 생성
//		String sessionId = generateSessionId();
//		
//		// 세션 대신 DB에 세션 아이디, 인증 번호 및 유효 시간 저장
//		SmsCodeDto smsCodeDto = new SmsCodeDto();
//		smsCodeDto.setSessionId(sessionId); // 세션 아이디 생성 메서드 필요
//		smsCodeDto.setVerificationCode(verificationCode);
//		smsCodeDto.setExpirationTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5분 유효 시간 설정
//
//		// DB에 저장
//		smsCodeDao.saveCode(smsCodeDto);
//
//		// DB에 저장된 정보
//		System.out.println(smsCodeDto);

		String smsContent = "[HOUSS-G] 인증번호 " + "[" + verificationCode + "]" + "를 입력해주세요.";

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
//		smsResponse.setSessionId(sessionId);
		return smsResponse;
	}

	// 6자리 난수 생성
	public String generateVerificationCode() {
		SecureRandom secureRandom = new SecureRandom();
		int code = secureRandom.nextInt(900000) + 100000; // 100000부터 999999까지의 난수 생성
		return String.valueOf(code);
	}

	// 세션아이디 생성
//	public String generateSessionId() {
//		SecureRandom secureRandom = new SecureRandom();
//		byte[] sessionIdBytes = new byte[16];
//		secureRandom.nextBytes(sessionIdBytes);
//		return Base64.encodeBase64String(sessionIdBytes);
//	}

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

