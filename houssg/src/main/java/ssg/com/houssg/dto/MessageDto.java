package ssg.com.houssg.dto;

// 메시지 수신자, 내용 저장
public class MessageDto {
    private String to;
    private String content;
    
    
    public MessageDto() {
    }

    public MessageDto(String to, String content) {
        this.to = to;
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
