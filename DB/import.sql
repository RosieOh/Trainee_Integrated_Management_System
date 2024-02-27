create database kdtlanding;

-- 개인 컴퓨터에서 DB 자체 UTF-8 인식 오류 시 사용바랍니다.
-- DB 생성 직후 사용 요함!!
ALTER DATABASE kdtlanding DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use kdtlanding;

commit;

CREATE TABLE user(
    no bigint PRIMARY KEY,
    email varchar(100) not null,
    pw varchar(300),
    tel varchar(20),
    sign int default 0, -- 신청자는 1, 미신청자 0
    regdate DATETIME DEFAULT CURRENT_TIMESTAMP(),
);
