package ssg.com.houssg.dto;

import java.util.List;

// sms 전송 요청 관련 데이터 저장 (sms 전송하기 위한 정보)
public class SmsRequestDto {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    // messges >> 여러명에게 전달 할 경우
    private List<MessageDto> messages;
    
    
    public SmsRequestDto() {
    }

    public SmsRequestDto(String type, String contentType, String countryCode, String from, String content, List<MessageDto> messages) {
        this.type = type;
        this.contentType = contentType;
        this.countryCode = countryCode;
        this.from = from;
        this.content = content;
        this.messages = messages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

	@Override
	public String toString() {
		return "SmsRequestDto [type=" + type + ", contentType=" + contentType + ", countryCode=" + countryCode
				+ ", from=" + from + ", content=" + content + ", messages=" + messages + "]";
	}
}
