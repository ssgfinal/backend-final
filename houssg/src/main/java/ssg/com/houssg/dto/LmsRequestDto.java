package ssg.com.houssg.dto;

import java.util.List;

public class LmsRequestDto {
	private String type;
	private String contentType;
	private String countryCode;
	private String subject;
	private String from;
	private String content;
	private List<MessageDto> messages;

	public LmsRequestDto() {
	}

	public LmsRequestDto(String type, String contentType, String countryCode, String from, String content,
			String subject, List<MessageDto> messages) {
		this.type = type;
		this.contentType = contentType;
		this.countryCode = countryCode;
		this.from = from;
		this.content = content;
		this.subject = subject;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
		return "LmsRequestDto [type=" + type + ", contentType=" + contentType + ", countryCode=" + countryCode
				+ ", Subject=" + subject + ", from=" + from + ", content=" + content + "]";
	}

}
