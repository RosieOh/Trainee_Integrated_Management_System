# 🏫KDT_Landing
## 취업을 향한 나의 발걸음, Genia Chunjae Academy
### 📚 Tech Stack
#### 💻 Development
<img src="https://skillicons.dev/icons?i=java,spring,mysql,html,css,js,docker& perline="/>

#### ⌛ Developed Period
##### 2024.02.21 ~

## 🧑‍🦲 팀원(가나다순)

<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/RosieOh"><img src="https://github.com/SP0F0/.github/assets/62829894/89996fac-c626-44e8-ba10-3dcc17252079" width="100px;" alt=""/><br /><sub><b>오태훈</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/sendjin5"><img src="https://github.com/ECO-TVY/.github/assets/104690434/69313dae-3288-47d1-aec3-f5314eb32fa3" width="100px;" alt=""/><br /><sub><b>황교진</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>

## 진행 방식

**도커 파일 작성:**
```dockerfile
# 기본 이미지 선택
FROM some-base-image

# 추후 수정 요함!
# 작업 디렉토리 설정
WORKDIR /usr/src/app

# ARG 속성 추가 - 여러번 사용되는 문자열이나 숫자 등을 변수로 만들어주는 속성
ARG JAR_PATH=./build/libs

# 로컬 빌드 경로에서 JAR 파일을 이미지로 복사
COPY ./build/libs/kdt-0.0.1-SNAPSHOT.jar ./build/libs/kdt-0.0.1-SNAPSHOT.jar

# 애플리케이션 실행 명령어 설정
CMD ["java","-jar","./build/libs/kdt-0.0.1-SNAPSHOT.jar"]
```

### 도커 이미지 빌드
```
docker build -t springbootapp .
```

### 도커 컨테이너 생성 및 실행
```
docker run -d --name my_app_container springbootapp
```


# 트러블 슈팅(아래는 예시입니다!! 이대로 오류 사항 토글 박스로 작성 부탁드려요!)

<details>
 <summary><b>GlobalExceptionHandler에서의 of 메서드 오류</b></summary>

>  ErrorResponse 클래스에 of 메서드가 없어서 해당 오류가 발생
- GlobalExceptionHandler에서 ErrorResponse 호출 시 미 호출로 인한 해당 오류가 발생

## 원인이 뭘까? 🧐
> import com.dalbang.global.error.*; <- 이게 아닌 import org.springframework.web.ErrorResponse; 자체 내장 클래스로 지정되어서 호출이 되지 않았음

## 어떻게 해결하나요? 🧐
> import의 경로 수정
- import com.dalbang.global.error.*; 로 수정
</details>

## 💡 Commit Convention

|       Tags       |               Explanation               |
| :--------------: | :-------------------------------------: |
|       Feat       |            새로운 기능 추가             |
|       Fix        |                버그 수정                |
| !BREAKING CHANGE |         커다란 API 변경의 경우          |
|     !HOTFIX      |          급한 치명적 버그 수정          |
|      Build       |           빌드 관련 파일 수정           |
|      Design      |        CSS를 포함 UI 디자인 변경        |
|       Docs       |                문서 수정                |
|      Style       | 코드 포맷팅, 세미콜론 누락, 코드 변경 X |
|     Refactor     |              코드 리팩토링              |
|     Comment      |        필요한 주석 추가 및 변경         |
|       Test       |            테스트 코드 수정             |
|      Rename      |         파일, 폴더명 이름 수정          |
|      Remove      |             파일, 폴더 삭제             |
|      chore       |            빌드, 패키지 수정            |

