-- 회원 테이블
CREATE TABLE user (
    id VARCHAR(45),
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(45) NOT NULL,
    phonenumber INT,
    auth TINYINT,
    point INT,
    PRIMARY KEY(id, auth)
);

-- 숙소 테이블
CREATE TABLE accommodation (
    accom_number INT PRIMARY KEY AUTO_INCREMENT,
    accom_name VARCHAR(45) NOT NULL,
    accom_address VARCHAR(45) NOT NULL,
    tele_number VARCHAR(45) NOT NULL,
    accom_category VARCHAR(45) NOT NULL,
    accom_details LONGTEXT,
    check_in TIME NOT NULL,
    check_out TIME NOT NULL,
    business_number INT NOT NULL,
    id VARCHAR(45) NOT NULL,
    auth TINYINT NOT NULL,
    approval_request TINYINT NOT NULL,
    deletion_request TINYINT NOT NULL,
    review_number INT NOT NULL,
    img VARCHAR(255) NOT NULL,
    add_request TINYINT NOT NULL,
    UNIQUE KEY (tele_number),
    FOREIGN KEY(id,auth) REFERENCES user(id,auth),
    FOREIGN KEY(review_number) REFERENCES review(review_number)
);

-- 객실 테이블
CREATE TABLE rooms (
    room_number INT PRIMARY KEY AUTO_INCREMENT,
    room_category VARCHAR(45) NOT NULL,
    room_details LONGTEXT NOT NULL,
    room_price INT NOT NULL,
    room_availability TINYINT NOT NULL,
    accom_number INT NOT NULL,
    room_use TINYINT NOT NULL,
    FOREIGN KEY (accom_number) REFERENCES accommodation(accom_number)
);

-- 리뷰 테이블
CREATE TABLE review (
    review_number INT PRIMARY KEY AUTO_INCREMENT,
    review_content LONGTEXT NOT NULL,
    review_rating decimal(4,2) NOT NULL,
    review_creation_time DATETIME NOT NULL,
    review_comment LONGTEXT,
    review_comment_time DATETIME NOT NULL,
    manager_id VARCHAR(45),
	report_status TINYINT NOT NULL,
    id VARCHAR(45) NOT NULL,	
	reservation_number INT NOT NULL,
	room_number INT NOT NULL,
	accom_number INT NOT NULL,
	img VARCHAR(255) NOT NULL,
	UNIQUE KEY (reservation_number),
    FOREIGN KEY (id) REFERENCES user(id),
    FOREIGN KEY (manager_id) REFERENCES user(id),
    FOREIGN KEY (reservation_number) REFERENCES reservation(reservation_number),
    FOREIGN KEY (room_number) REFERENCES rooms(room_number),
    FOREIGN KEY (accom_number) REFERENCES accommodation(accom_number)
);    

-- 예약 테이블
CREATE TABLE reservation (
    reservation_number INT PRIMARY KEY AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status TINYINT NOT NULL,
    payment_time TIMESTAMP NOT NULL,
    payment_amount INT NOT NULL,
    id VARCHAR(45) NOT NULL,
    room_number INT NOT NULL,
	guest_name VARCHAR(45) NOT NULL,
    guest_phone VARCHAR(45) NOT NULL,
    accom_number INT NOT NULL,	
    FOREIGN KEY (room_number) REFERENCES rooms(room_number),
    FOREIGN KEY (id) REFERENCES user(id),
    FOREIGN KEY (accom_number) REFERENCES accommodation(accom_number)
);

-- 찜 테이블
CREATE TABLE favorite (
    id VARCHAR(45) NOT NULL,
    accom_number INT NOT NULL,
    FOREIGN KEY (accom_number) REFERENCES accommodation(accom_number),
    FOREIGN KEY (id) REFERENCES user(id)
);

-- 쿠폰(발행) 테이블
CREATE TABLE coupon (
    coupon_number INT PRIMARY KEY,
    discount INT NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    coupon_name VARCHAR(45) NOT NULL)
);

-- 쿠폰(고객) 테이블
CREATE TABLE userCoupon (
    coupon_number INT NOT NULL,
    is_used TINYINT NOT NULL,
    id VARCHAR(45) NOT NULL,
    coupon_name VARCHAR(45) NOT NULL,
    FOREIGN KEY (id) REFERENCES user(id),
    FOREIGN KEY (coupon_number) REFERENCES coupon(coupon_number)
);



-- innerview 테이블
CREATE TABLE innerview (
    room_number INT NOT NULL,
    img1 VARCHAR(255),
    img2 VARCHAR(255),
    img3 VARCHAR(255),
    img4 VARCHAR(255),
    img5 VARCHAR(255),
    img6 VARCHAR(255),
    img7 VARCHAR(255),
    img8 VARCHAR(255),
    img9 VARCHAR(255),
    img10 VARCHAR(255),
    FOREIGN KEY (room_number) REFERENCES rooms(room_number)
);

-- 
CREATE TABLE facility (
    accom_number INT NOT NULL,
    nearby_sea BOOLEAN,
    parking_available BOOLEAN,
    pool BOOLEAN,
    spa BOOLEAN,
    wifi BOOLEAN,
    twin_bed BOOLEAN,
    barbecue BOOLEAN,
    no_smoking BOOLEAN,
    luggage_storage BOOLEAN,
    free_movie_ott BOOLEAN,
    FOREIGN KEY (accom_number) REFERENCES accommodation(accom_number)
);

CREATE TABLE room_service (
    room_number INT NOT NULL,
    city_view BOOLEAN,
    ocean_view BOOLEAN,
    pc BOOLEAN,
    no_smoking BOOLEAN,
    double_bed BOOLEAN,
    queen_bed BOOLEAN,
    king_bed BOOLEAN,
    FOREIGN KEY (room_number) REFERENCES rooms(room_number)
);