package ssg.com.houssg.dto;

public class ReviewDto {

	private int reviewNumber;
	private String reviewContent;
	private double reviewRating;
	private String reviewCreationTime;
	private String reviewComment;
	private String reviewCommentTime;
	private String managerId;
	private int reportStatus;
	private String id;
	private int reservationNumber;
	private int roomNumber;
	private int accomNumber;
	private String img;
	private String roomCategory;
	private String accomName;
	private String nickname;
	private String reportMessage;
	
	public int getReviewNumber() {
		return reviewNumber;
	}
	public void setReviewNumber(int reviewNumber) {
		this.reviewNumber = reviewNumber;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public double getReviewRating() {
		return reviewRating;
	}
	public void setReviewRating(double reviewRating) {
		this.reviewRating = reviewRating;
	}
	public String getReviewCreationTime() {
		return reviewCreationTime;
	}
	public void setReviewCreationTime(String reviewCreationTime) {
		this.reviewCreationTime = reviewCreationTime;
	}
	public String getReviewComment() {
		return reviewComment;
	}
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}
	public String getReviewCommentTime() {
		return reviewCommentTime;
	}
	public void setReviewCommentTime(String reviewCommentTime) {
		this.reviewCommentTime = reviewCommentTime;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public int getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public String getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}
	public String getAccomName() {
		return accomName;
	}
	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getReportMessage() {
		return reportMessage;
	}
	public void setReportMessage(String reportMessage) {
		this.reportMessage = reportMessage;
	}
	public ReviewDto() {
		super();
	}
	public ReviewDto(int reviewNumber, String reviewContent, double reviewRating, String reviewCreationTime,
			String reviewComment, String reviewCommentTime, String managerId, int reportStatus, String id,
			int reservationNumber, int roomNumber, int accomNumber, String img, String roomCategory, String accomName,
			String nickname, String reportMessage) {
		super();
		this.reviewNumber = reviewNumber;
		this.reviewContent = reviewContent;
		this.reviewRating = reviewRating;
		this.reviewCreationTime = reviewCreationTime;
		this.reviewComment = reviewComment;
		this.reviewCommentTime = reviewCommentTime;
		this.managerId = managerId;
		this.reportStatus = reportStatus;
		this.id = id;
		this.reservationNumber = reservationNumber;
		this.roomNumber = roomNumber;
		this.accomNumber = accomNumber;
		this.img = img;
		this.roomCategory = roomCategory;
		this.accomName = accomName;
		this.nickname = nickname;
		this.reportMessage = reportMessage;
	}
	@Override
	public String toString() {
		return "ReviewDto [reviewNumber=" + reviewNumber + ", reviewContent=" + reviewContent + ", reviewRating="
				+ reviewRating + ", reviewCreationTime=" + reviewCreationTime + ", reviewComment=" + reviewComment
				+ ", reviewCommentTime=" + reviewCommentTime + ", managerId=" + managerId + ", reportStatus="
				+ reportStatus + ", id=" + id + ", reservationNumber=" + reservationNumber + ", roomNumber="
				+ roomNumber + ", accomNumber=" + accomNumber + ", img=" + img + ", roomCategory=" + roomCategory
				+ ", accomName=" + accomName + ", nickname=" + nickname + ", reportMessage=" + reportMessage + "]";
	}

	
}
