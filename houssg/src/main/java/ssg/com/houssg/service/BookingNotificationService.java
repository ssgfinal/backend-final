package ssg.com.houssg.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ssg.com.houssg.dto.LmsRequestDto;
import ssg.com.houssg.dto.MessageDto;
import ssg.com.houssg.dto.SmsResponseDto;

@Service
public class BookingNotificationService {
	@Value("${naver-cloud-sms.serviceId}")
	private String serviceId;
	@Value("${naver-cloud-sms.accessKey}")
	private String accessKey;
	@Value("${naver-cloud-sms.secretKey}")
	private String secretKey;


	public SmsResponseDto sendLms(String recipientPhoneNumber, String content)
			throws Exception {
		Long time = System.currentTimeMillis();

		// 메시지 생성
		List<MessageDto> messages = new ArrayList<>();
		messages.add(new MessageDto(recipientPhoneNumber, content));
		String subject = "HOUS-SG";
		LmsRequestDto lmsRequest = new LmsRequestDto("LMS", "COMM", "82", "01025070634", content, subject ,messages);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(lmsRequest);

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
