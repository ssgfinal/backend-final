-- 회원 테이블
CREATE TABLE User (
    id VARCHAR(45),
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(45) NOT NULL,
    user_phone_number VARCHAR(45),
    auth TINYINT,
    bank_name VARCHAR(45),
    account_number VARCHAR(45),
    PRIMARY KEY(id, auth)
);


-- 숙소 테이블
CREATE TABLE Accommodation (
    accom_number INT PRIMARY KEY AUTO_INCREMENT,
    accom_name VARCHAR(45) NOT NULL,
    accom_address VARCHAR(45) NOT NULL,
    tele_number VARCHAR(45) NOT NULL,
    accom_category VARCHAR(45) NOT NULL,
    accom_details LONGTEXT,
    outdoor_view LONGBLOB,
    check_in TIME NOT NULL,
    check_out TIME NOT NULL,
    business_number INT NOT NULL,
    id VARCHAR(45) NOT NULL,
    auth TINYINT NOT NULL,
    approval_request TINYINT NOT NULL,
    deletion_request TINYINT NOT NULL,
    zip_code VARCHAR(45) NOT NULL,
    FOREIGN KEY(id,auth) REFERENCES User(id,auth)
);

-- 객실 테이블
CREATE TABLE Rooms (
    room_number INT PRIMARY KEY AUTO_INCREMENT,
    room_name VARCHAR(45) NOT NULL,
    room_category VARCHAR(45) NOT NULL,
    room_details LONGTEXT NOT NULL,
    inner_view LONGBLOB NOT NULL,
    room_price INT NOT NULL,
    room_availability TINYINT NOT NULL,
    accom_number INT NOT NULL,
    FOREIGN KEY (accom_number) REFERENCES Accommodation(accom_number)
);

-- 예약 테이블
CREATE TABLE Reservation (
    reservation_number INT PRIMARY KEY AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status INT NOT NULL,
    time TIME NOT NULL,
    payment_amount INT NOT NULL,
    id VARCHAR(45) NOT NULL,
    room_number INT NOT NULL,
	guest_name VARCHAR(45) NOT NULL,
    guest_phone VARCHAR(45) NOT NULL,
    accom_number INT NOT NULL,	
    FOREIGN KEY (room_number) REFERENCES Rooms(room_number),
    FOREIGN KEY (id) REFERENCES User(id),
    FOREIGN KEY (accom_number) REFERENCES Accommodation(accom_number)
);

-- 리뷰 테이블
CREATE TABLE Review (
    review_number INT PRIMARY KEY AUTO_INCREMENT,
    review_content LONGTEXT NOT NULL,
    review_rating decimal(4,2) NOT NULL,
    review_creation_time TIMESTAMP NOT NULL,
    review_comment LONGTEXT NOT NULL,
	report_status TINYINT NOT NULL,
    id VARCHAR(45) NOT NULL,	
	reservation_number INT NOT NULL,
    FOREIGN KEY (id) REFERENCES User(id),
    FOREIGN KEY (reservation_number) REFERENCES Reservation(reservation_number)
);    
   



-- 찜 테이블
CREATE TABLE Favorite (
    is_favorite TINYINT NOT NULL,
    id VARCHAR(45) NOT NULL,
    accom_number INT NOT NULL,
    FOREIGN KEY (accom_number) REFERENCES Accommodation(accom_number),
    FOREIGN KEY (id) REFERENCES User(id)
);

-- 쿠폰(발행) 테이블
CREATE TABLE Coupon (
    coupon_number INT PRIMARY KEY,
    discount INT NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    coupon_name VARCHAR(45) NOT NULL
);

-- 쿠폰(고객) 테이블
CREATE TABLE UserCoupon (
    coupon_number INT NOT NULL,
    is_used TINYINT NOT NULL,
    id VARCHAR(45) NOT NULL,
    coupon_name VARCHAR(45) NOT NULL,
    FOREIGN KEY (id) REFERENCES User(id),
    FOREIGN KEY (coupon_number) REFERENCES Coupon(coupon_number)
);
