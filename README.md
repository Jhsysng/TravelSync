# TravelSync
![header](https://capsule-render.vercel.app/api?type=waving&color=6DB3E7&height=200&section=header&text=TravelSync&fontSize=75&fontColor=fff)

## 2023-02 SoftwareEngineering uh?ban -TravelSync- spring boot backend

<br/>

## 어?금지 백엔드
| 이름  | Email                |
|-----|----------------------|
| 조현성 | y\*\*g***8@naver.com |
---
## Tech Stack
### Develop
![Java17](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white)

![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSONWebTokens&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-02569B?style=for-the-badge&logo=JPA&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)
### Infra
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-269539?style=for-the-badge&logo=NGINX&logoColor=white)
![GCP](https://img.shields.io/badge/GCP-4285F4?style=for-the-badge&logo=GoogleCloud&logoColor=white)
![sonarqube](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=SonarQube&logoColor=white)

![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white)
![GithubActions](https://img.shields.io/badge/GithubActions-2088FF?style=for-the-badge&logo=GithubActions&logoColor=white)
![Terrafom](https://img.shields.io/badge/Terraform-623CE4?style=for-the-badge&logo=Terraform&logoColor=white)
---
## Architecture
![travelsyncarch](https://github.com/Jhsysng/TravelSync/assets/67987132/e805fa40-e9b0-40af-a9bd-2cc5fe221828)
----
## API
![TravelSyncAPI](https://github.com/Jhsysng/TravelSync/assets/67987132/4f9707a2-ad0c-4a79-b43e-a64309001258)

## 실행
### 1. Create YML
```bash
sudo vim ./src/main/resources/application.yml
```

```yml
spring:
  config:
    import:
      - app.yml
    datasource:
        url: jdbc:mariadb://localhost:3306/travelsync
        driver-class-name: org.mariadb.jdbc.Driver
        username: { your username }
        password: { your password }

    jpa:
        hibernate:
          ddl-auto: update
        properties:
          hibernate:
              format_sql: true
```
```bash
sudo vim ./src/main/resources/app.yml
```
```yml
app:
  jwt:
    secret: { your secret key }
    accessToken:
      expiration: 3600
    refreshToken:
      expiration: 86400
```
### 2. Create ENV File

.env.dev
```bash
sudo vim ./.env.dev
```
```env
SPRING_DATASOURCE_URL=jdbc:mariadb://database:3306/travelsync
SPRING_DATASOURCE_USERNAME= { your username }
SPRING_DATASOURCE_PASSWORD= { your password }
```
.env.db
```bash
sudo vim ./.env.db
```
```env
MYSQL_HOST=localhost database
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD= { your password }
MYSQL_USER= { your username }
MYSQL_PASSWORD= { your password }
MYSQL_DATABASE=travelsync
```
### 3. Docker-compose
```bash
sudo docker-compose up -d
```
----
# TravelSync
### 패키지 여행시 동행자를 찾아주는 서비스
> 여행계획을 관리해주고 공유하며 동행자를 찾아주는 서비스 TravelSync</br>
## To FrontEnd
### [![Frontend Repository](https://img.shields.io/badge/TravelSync-Frontend-blue?style=for-the-badge&logo=github)](https://github.com/dailyrunner/TravelSync_client_new)
### 📌 프로젝트 화면 예시
### 홈화면</br>
![TravelSyncStart](https://github.com/Jhsysng/TravelSync/assets/67987132/877b36dc-7f2c-4f8a-8b60-f9cd54c2b2d2)

### 메인화면</br>
 ![TravelSync Main](https://github.com/Jhsysng/TravelSync/assets/67987132/e0d463e8-383f-4bd3-bbe2-40e95e5b9b81)

### 그룹 가입</br>
 ![TravelSyncJoin](https://github.com/Jhsysng/TravelSync/assets/67987132/d538d7fd-0549-4333-99df-d9699a44a553)
 
### 여행계획</br>
![TravelSyncPlan](https://github.com/Jhsysng/TravelSync/assets/67987132/93044da3-14c9-4e81-883a-3c4227b5ed60)

### 인원 점검</br>
![TravelSyncLocationSearch](https://github.com/Jhsysng/TravelSync/assets/67987132/fd9ce110-139d-49c7-9eef-bf1397ef0993)