<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="path" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KDT Rending</title>

</head>
<body>

<jsp:include page="./include/head.jsp" />
<div class="main">
    <header>
        <!-- topHeader -->
        <div class="topHeader">
            <a href="/">Home</a>
            <a href="">풀스택(java)</a>
            <a href="">빅데이터</a>
            <a href="">PM(프로덕트 매니저)</a>
            <a href="${path }/apply/list">신청목록으로</a>
        </div>

        <!-- bottomHeader -->
        <div class="bottomHeader">
            <h3>빅데이터/풀스택(Java)/프로덕트매니저 과정 모집 예정 </h3>

        </div>
    </header>
    <!-- main body -->
    <div class="container">
        <!-- banner -->
        <section class="banner">
            <div class="ban_wrap">
                <p class="ban ban_1">생성AI시대,</p>
                <p class="ban ban_2">빅데이터에 다이브​,</p>
                <p class="ban ban_3">커리어 퀀텀점프!</p>
                <p class="ban ban_4">2023년 생성AI 시대, 노베이스 비전공자도</p>
                <p class="ban ban_5">유망직종 빅데이터 전문가가 될 수 있습니다!</p>
                <p class="ban ban_6">대한민국 1위 교육출판 전문 기업 천재교육과 함께 교육의 미래를 선도할 도전적이고 열정적인 인재를 기다립니다.
                    '천재 IT교육센터'는 고용노동부 K-digital Training 프로그램의 정식 운영 기관으로, 천재교육의 ​빅데이터를 활용한 프로젝트 기반의 디지털 인재 양성 기관입니다.</p>

                <button type="button" class="ban_btn" id="ban_btn">사전신청 ></button>
                <p class="ban ban_7">※모집 정원(20명)에 따라 예정된 기한보다 조기 마감될 수 있습니다</p>
            </div>
        </section>

        <!-- section 1 -->
        <section class="sec sec_1">
            <div>내용</div>
        </section>

        <section class="sec sec_2">
            <div>내용</div>


        </section>

        <section class="sec sec_3">
            <div>내용</div>


        </section>

        <section class="sec sec_4">
            <div>내용</div>


        </section>

        <section class="sec sec_5">
            <div>내용</div>


        </section>

        <section class="sec sec_6">
            <div class="con_wrap">
                <h1>자주 묻는 질문</h1>
                <div class="faq_wrap">
                    <a href="#faq_con1">
                        ※교육전 선수 지식이 필요할까요?
                    </a>
                    <div class="faq_con" id="faq_con1">
                        <p>
                            천재교육 geniA.아카데미는 프로그래밍에 대한 기본 지식과 경험 순으로 교육생을 선발하지는 않습니다. 다만 교육생의 사전 역량을 파악을 위해 '프로그래밍 경험과 역량'을 지원 절차 상 확인하는 점 참고 부탁드립니다.
                        </p>
                    </div>
                </div>
                <div class="faq_wrap">
                    <a href="#faq_con2">
                        ※회사를 다니고 있는 주니어 개발자나 개발 경력이 있는 사람도 지원이 가능한가요?
                    </a>
                    <div class="faq_con" id="faq_con2">
                        <p>
                            네, 지원 가능합니다.
                            하지만, geniA.아카데미의 교육 과정은 채용 연계가 목표인 교육 과정으로, 자기계발 등의 역량 강화를 위해 수강하려는 재직자 등은 선발에서 제외됩니다.
                        </p>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>


<!-- Bottom banner -->
<div>

</div>



<!-- footer -->
<footer>
    <div class="footer">
        <div class="footer_wrap">
            <div class="footer_wrap_in">
                <p>
                    (주)천재교육
                    서울특별시 디지털로9길 23 마리오아울렛2관 11층 천재IT교육센터
                </p>
            </div>
            <div class="footer_wrap_in">
                <p>
                    접수 문의 및 사이트 이용 문의 : 02-3282-8589
                </p>
            </div>
        </div>
        <div class="footer_wrap">
            <div class="footer_wrap_in">
                <p>
                    E-Mali: genia@chunjae.co.kr
                </p>
            </div>
            <div class="footer_wrap_in">
                <p>
                    COPYRIGHT ⓒ 2023 BY CHUNJAE CO.,LTD ALL RIGHTS RESERVED
                </p>
            </div>
        </div>
    </div>
</footer>

<!-- sign Form -->
<div class="sign_form_back">
    <div class="sign_form">
        <form action="cssa/apply/register" method="post">
            <button class="close_btn" id="close_btn">X</button>
            <div class="sign_title">
                <h2> 신청 양식 폼</h2>
            </div>
            <div class="sign_top">
                <ul>
                    <h4>신청자 정보 <em>*</em></h4>
                    <hr>
                    <li class="sign_list">
                        <div class="sign_list_wrap">
                            <div class="sign_list_title">신청 과목</div>
                            <div class="sign_list_title">이름</div>
                            <div class="sign_list_title">나이</div>
                            <div class="sign_list_title">연락처</div>
                            <div class="sign_list_title">이메일</div>
                        </div>
                        <div class="sign_list_wrap">
                            <select name="category" class="sign_list_input">
                                <option value="FullStack">풀스택(Java)</option>
                                <option value="BigData">빅데이터</option>
                                <option value="ProductManager">PM</option>
                            </select>
                            <input class="sign_list_input" name="name">
                            <input class="sign_list_input" name="age">
                            <input class="sign_list_input" name="tel">
                            <input class="sign_list_input" name="email">
                        </div>
                        <div class="sign_list_wrap">
                            <button type="button" class="sign_list_btn">인증</button>
                        </div>
                    </li>

                    <h4>요청 사항 <em>*</em></h4>
                    <hr>
                    <li class="sign_list">
                        <div class="sign_list_wrap">
                            <div class="sign_list_title">
                                <textarea class="sign_list_textarea" cols="50" rows="5" ></textarea>
                            </div>
                        </div>
                    </li>

                    <h4>개인 정보 이용 동의 <em>*</em></h4>
                    <hr>
                    <li class="sign_list">
                        <div class="sign_list_title">
                            <textarea class="sign_list_textarea" cols="50" rows="5"></textarea>
                        </div>
                        <div class="sign_list_title">
                            <input class="sign_list_chek" type="checkbox" id="sign_chek" name="comment">
                            <label class="sign_list_chek" for="sign_chek">개인정보 수집 및 이용에 동의합니다.<em> (필수)</em></label>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="sign_bottom">
                <button type="submit" class="sign_btn"> 사전 예약 신청</button>
            </div>
        </form>
    </div>
</div>


<jsp:include page="./include/footer.jsp" />


</body>
<script>
    $(document).ready(function(){
        $("#ban_btn").click(function(){
            $(".sign_form_back").css("display", "block")
        })
        $("#close_btn").click(function(){
            $(".sign_form_back").css("display", "none")
        })
    })
</script>
</html>