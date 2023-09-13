package ssg.com.houssg.dto;

public class ReviewDto {

	private int reviewNumber;
	private String reviewContent;
	private double reviewRating;
	private String reviewCreationTime;
	private String reviewComment;
	private String reviewCommentTime;
	private int reportStatus;
	private String id;
	private int reservationNumber;
	private int roomNumber;
	private int accomNumber;
	private String img;
	private String roomCategory;
	private String accomName;
	private String nickname;
	
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
	public int getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getId() {
		return id;
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
	public ReviewDto() {
		super();
	}
	public ReviewDto(int reviewNumber, String reviewContent, double reviewRating, String reviewCreationTime,
			String reviewComment, String reviewCommentTime, int reportStatus, String id, int reservationNumber, int roomNumber, int accomNumber,
			String img) {
		super();
		this.reviewNumber = reviewNumber;
		this.reviewContent = reviewContent;
		this.reviewRating = reviewRating;
		this.reviewCreationTime = reviewCreationTime;
		this.reviewComment = reviewComment;
		this.reviewCommentTime = reviewCommentTime;
		this.reportStatus = reportStatus;
		this.id = id;
		this.reservationNumber = reservationNumber;
		this.roomNumber = roomNumber;
		this.accomNumber = accomNumber;
		this.img = img;
	}
	
	
	
	
}