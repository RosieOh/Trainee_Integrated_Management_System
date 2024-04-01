# 📝 ChunjaeIT LMS
## 수강생들의 효율적인 학습 관리를 위한 LMS 서비스, ChunjaeIT LMS
### 📚 Tech Stack
#### 💻 Development
<img src="https://skillicons.dev/icons?i=java,spring,mysql,html,css,js,docker,grafana,prometheus& perline="/>

#### ⌛ Developed Period
##### 2024.04.01 ~

## 🧑‍🦲 팀원(가나다순)

<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/RosieOh"><img src="https://github.com/ECO-TVY/.github/assets/104690434/69313dae-3288-47d1-aec3-f5314eb32fa3" width="100px;" alt=""/><br /><sub><b>김현경</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/sendjin5"><img src="https://github.com/ECO-TVY/.github/assets/104690434/69313dae-3288-47d1-aec3-f5314eb32fa3" width="100px;" alt=""/><br /><sub><b>오태훈</b></sub></a><br /></td>
 <td align="center"><a href="https://github.com/sendjin5"><img src="https://github.com/ECO-TVY/.github/assets/104690434/69313dae-3288-47d1-aec3-f5314eb32fa3" width="100px;" alt=""/><br /><sub><b>이소윤</b></sub></a><br /></td>
 <td align="center"><a href="https://github.com/sendjin5"><img src="https://github.com/ECO-TVY/.github/assets/104690434/69313dae-3288-47d1-aec3-f5314eb32fa3" width="100px;" alt=""/><br /><sub><b>황교진</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>


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

## 🎯 Commit Convention

- feat: Add a new feature
- fix: Bug fix
- docs: Documentation updates
- style: Code formatting, missing semicolons, cases where no code change is involved
- refactor: Code refactoring
- test: Test code, adding refactoring tests
  -hore: Build task updates, package manager updates

## 💡 PR/Commit Emoji Convetion

| 아이콘 | 코드                       | 설명                     |
| ------ | -------------------------- | ------------------------ |
| 🎨     | :art                       | Improving structure/format of the code   |
| ⚡️    | :zap                       | Performance improvement               |
| 🔥     | :fire                      | 	Code/file deletion          |
| 🐛     | :bug                       | Bug fix             |
| 🚑     | :ambulance                 | Critical fix|
| ✨     | :sparkles                  | New features               |
| 💄     | :lipstick                  | Adding/updating UI/style files |
| ⏪     | :rewind                    | Reverting changes     |
| 🔀     | :twisted_rightwards_arrows | Branch merging            |
| 💡     | :bulb                      | Adding/updating comments         |
| 🗃      | :card_file_box             | Database-related modifications   |