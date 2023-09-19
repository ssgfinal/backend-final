# houssg Backend

## 개발 환경 실행 방법

### db띄우는 방법

- 명령어 실행하는 디렉터리에 'db' 폴더가 있어야 합니다.
- db 처음 띄우는 경우, 반드시 db에 접근해서 database를 직접 생성해주세요.
- 
```bash
docker run -d \
    -p 3306:3306 \
    -e MARIADB_ROOT_PASSWORD="root 암호로 쓸 값" \
    -v ${PWD}/db:/var/lib/mysql \
    --name mariadb \
    mariadb:11.1.2
```

### redis 띄우는 방법

```bash
docker run -d \
    -p 6379:6379 \
    --name redis
    redis:7.2.1
```

### 이미지 빌드 방법

```bash
cd houssg # 폴더 제거되면 여기도 신경써서 제거해주세요.
docker build -t [빌드할 이미지 명] .
```

### 백엔드 띄우는 법

- 위의 방식으로 db, redis를 로컬에 띄운경우
```bash
docker run -d \
    -p 3200:3200 \
    -e DB_HOST=db \
    -e DB_USER=[db유저] \
    -e DB_PASSWORD=[db암호] \
    -e JWT_SECRET=[jwt시크릿] \
    -e REDIS_HOST=redis \
    -e NCS_ACCESSKEY=[네이버클라우드 accesskey] \
    -e NCS_SECRETKEY=[네이버클라우드 secretkey] \
    --link mariadb:db \
    --link redis:redis \
    [빌드한 이미지명]
```

- 외부 db를 사용하는 경우
```
docker run -d \
    -p 3200:3200 \
    -e DB_HOST=[db접속정보] \
    -e DB_USER=[db유저] \
    -e DB_PASSWORD=[db암호] \
    -e JWT_SECRET=[jwt시크릿] \
    -e REDIS_HOST=[redis접속정보] \
    -e NCS_ACCESSKEY=[네이버클라우드 accesskey] \
    -e NCS_SECRETKEY=[네이버클라우드 secretkey] \
    [빌드한 이미지명]
```