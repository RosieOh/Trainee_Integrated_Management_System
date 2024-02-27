# 기본 이미지 선택
FROM some-base-image

# 작업 디렉토리 설정
# 이거 확인 해봐야합니다!
WORKDIR /usr/src/app/

# ARG 속성 추가 - 여러번 사용되는 문자열이나 숫자 등을 변수로 만들어주는 속성
ARG JAR_PATH=./build/libs

# 로컬 빌드 경로에서 JAR 파일을 이미지로 복사
COPY ./build/libs/kdt-0.0.1-SNAPSHOT.jar ./build/libs/kdt-0.0.1-SNAPSHOT.jar

# 애플리케이션 실행 명령어 설정
CMD ["java","-jar","./build/libs/kdt-0.0.1-SNAPSHOT.jar"]