-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.5.2-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- mydb 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mydb`;

-- 테이블 mydb.board 구조 내보내기
CREATE TABLE IF NOT EXISTS `board` (
  `YEAR` varchar(4) NOT NULL COMMENT '년도',
  `TERM` varchar(2) NOT NULL COMMENT '하기',
  `LECTURE_NO` int(11) NOT NULL COMMENT '강좌번호',
  `BOARD_CD` varchar(6) NOT NULL COMMENT '게시판구분코드 :1 공지사항, 2 QnA',
  `NO` bigint(20) NOT NULL DEFAULT 0 COMMENT '일련번호',
  `SEQ_NO` int(11) NOT NULL COMMENT '하위일련번호',
  `TITLE` varchar(200) DEFAULT NULL COMMENT '주제',
  `CONTENT` varchar(2000) DEFAULT NULL COMMENT '내용',
  `READ_CNT` int(11) DEFAULT NULL COMMENT '조회수',
  `FILE_NM` varchar(200) DEFAULT NULL COMMENT '파일명',
  `SAVE_FILE_NM` varchar(200) DEFAULT NULL COMMENT '저장파일명',
  `INPUT_ID` varchar(45) DEFAULT NULL COMMENT '입력자 아이디',
  `INPUT_DATE` datetime DEFAULT NULL COMMENT '입력일시',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정자아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`YEAR`,`TERM`,`LECTURE_NO`,`BOARD_CD`,`NO`,`SEQ_NO`),
  CONSTRAINT `fk_BOARD_LECTURE1` FOREIGN KEY (`YEAR`, `TERM`, `LECTURE_NO`) REFERENCES `lecture` (`YEAR`, `TERM`, `LECTURE_NO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.course 구조 내보내기
CREATE TABLE IF NOT EXISTS `course` (
  `YEAR` varchar(4) NOT NULL,
  `TERM` varchar(2) NOT NULL,
  `LECTURE_NO` int(11) NOT NULL,
  `COURSE_NO` int(11) NOT NULL COMMENT '단계번호',
  `QUESTION_NO` int(11) NOT NULL COMMENT '문항번호',
  `QUESTION` varchar(200) DEFAULT NULL COMMENT '질의문항',
  `FILE_NM` varchar(200) DEFAULT NULL COMMENT '파일명',
  `SAVE_FILE_NM` varchar(200) DEFAULT NULL COMMENT '저장파일명',
  `FINISH_YN` varchar(1) DEFAULT NULL COMMENT '완료여부: 사용 1, 미사용 0',
  `INPUT_ID` varchar(45) DEFAULT NULL COMMENT '입력자 아이디',
  `INPUT_DATE` datetime DEFAULT NULL COMMENT '입력일시',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정자아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`YEAR`,`TERM`,`LECTURE_NO`,`COURSE_NO`,`QUESTION_NO`),
  CONSTRAINT `fk_table1_LECTURE1` FOREIGN KEY (`YEAR`, `TERM`, `LECTURE_NO`) REFERENCES `lecture` (`YEAR`, `TERM`, `LECTURE_NO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.dae_cd 구조 내보내기
CREATE TABLE IF NOT EXISTS `dae_cd` (
  `DAE_CD` varchar(3) NOT NULL COMMENT '대코드',
  `DAE_NM` varchar(200) DEFAULT NULL COMMENT '대코드명',
  `USE_YN` varchar(1) DEFAULT NULL COMMENT '사용여부 : 1사용, 0미사용',
  `INPUT_ID` varchar(45) DEFAULT NULL COMMENT '입력자 아이디',
  `INPUT_DATE` datetime DEFAULT NULL COMMENT '입력일시',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정자아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`DAE_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.enroll 구조 내보내기
CREATE TABLE IF NOT EXISTS `enroll` (
  `YEAR` varchar(4) NOT NULL COMMENT '년도',
  `TERM` varchar(2) NOT NULL COMMENT '학기',
  `LECTURE_NO` int(11) NOT NULL COMMENT '강좌번호',
  `USER_ID` varchar(30) NOT NULL COMMENT '사용자아이디',
  `REGISTER_DT` varchar(8) DEFAULT NULL COMMENT '등록일',
  `APPROVAL_CD` varchar(6) DEFAULT NULL COMMENT '승인코드 : 신청, 승인',
  `APPROVAL_DT` varchar(8) DEFAULT NULL COMMENT '승인일자',
  `PASS_COURSE_NO` int(11) DEFAULT NULL COMMENT '통과한단계번호',
  `STUDY_TIME` int(11) DEFAULT NULL COMMENT '공부시간',
  `INPUT_ID` varchar(45) DEFAULT NULL COMMENT '입력자 아이디',
  `INPUT_DATE` datetime DEFAULT NULL COMMENT '입력일시',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정자아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`YEAR`,`TERM`,`LECTURE_NO`,`USER_ID`),
  KEY `fk_ENROLL_USER1_idx` (`USER_ID`),
  CONSTRAINT `fk_ENROLL_LECTURE1` FOREIGN KEY (`YEAR`, `TERM`, `LECTURE_NO`) REFERENCES `lecture` (`YEAR`, `TERM`, `LECTURE_NO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ENROLL_USER1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.lecture 구조 내보내기
CREATE TABLE IF NOT EXISTS `lecture` (
  `YEAR` varchar(4) NOT NULL COMMENT '년도',
  `TERM` varchar(2) NOT NULL COMMENT '학기',
  `LECTURE_NO` int(11) NOT NULL COMMENT '강좌번호',
  `LECTURE_NM` varchar(200) DEFAULT NULL COMMENT '강좌명',
  `SCHOOL_CD` varchar(6) DEFAULT NULL,
  `GRADE` int(11) DEFAULT NULL COMMENT '학년',
  `BAN` varchar(10) DEFAULT NULL COMMENT '반',
  `ENROLL_ST_DT` varchar(8) DEFAULT NULL COMMENT '수강신청시작일',
  `ENROLL_ED_DT` varchar(8) DEFAULT NULL,
  `MAX_CNT` int(11) DEFAULT NULL COMMENT '수강신청최대인원',
  `TEACHER_ID` varchar(45) DEFAULT NULL COMMENT '선생님 아이디',
  `TEACHER_NM` varchar(45) DEFAULT NULL COMMENT '선생님 이름',
  `INPUT_ID` varchar(45) DEFAULT NULL COMMENT '입력자 아이디',
  `INPUT_DATE` datetime DEFAULT NULL COMMENT '입력일시',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정자아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`YEAR`,`TERM`,`LECTURE_NO`),
  KEY `fk_LECTURE_TERM_CD_idx` (`YEAR`,`TERM`),
  CONSTRAINT `fk_LECTURE_TERM_CD` FOREIGN KEY (`YEAR`, `TERM`) REFERENCES `term_cd` (`YEAR`, `TERM`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.so_cd 구조 내보내기
CREATE TABLE IF NOT EXISTS `so_cd` (
  `DAE_CD` varchar(3) NOT NULL,
  `SO_CD` varchar(6) NOT NULL,
  `SO_NM` varchar(200) DEFAULT NULL,
  `USE_YN` varchar(1) DEFAULT NULL,
  `BIGO` varchar(200) DEFAULT NULL COMMENT '비고',
  PRIMARY KEY (`DAE_CD`,`SO_CD`),
  CONSTRAINT `fk_SO_CD_DAE_CD1` FOREIGN KEY (`DAE_CD`) REFERENCES `dae_cd` (`DAE_CD`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.study 구조 내보내기
CREATE TABLE IF NOT EXISTS `study` (
  `YEAR` varchar(4) NOT NULL COMMENT '년도',
  `TERM` varchar(2) NOT NULL COMMENT '학기',
  `LECTURE_NO` int(11) NOT NULL COMMENT '강좌번호',
  `USER_ID` varchar(45) NOT NULL COMMENT '아이디',
  `COURSE_NO` int(11) NOT NULL COMMENT '단계번호',
  `QUESTION_NO` int(11) NOT NULL COMMENT '문항번호',
  `SEQ_NO` int(11) NOT NULL COMMENT '일련번호',
  `ANSWER` varchar(200) DEFAULT NULL COMMENT '학생답',
  `SYSTEM_TIME` datetime DEFAULT NULL COMMENT '시스템 시간',
  `CORRECT_YN` varchar(1) DEFAULT NULL COMMENT '정답여부: 정답1, 오답0',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`YEAR`,`TERM`,`LECTURE_NO`,`USER_ID`,`COURSE_NO`,`QUESTION_NO`,`SEQ_NO`),
  KEY `FK_study_course` (`YEAR`,`TERM`,`LECTURE_NO`,`COURSE_NO`,`QUESTION_NO`),
  KEY `FK_study_user` (`USER_ID`),
  CONSTRAINT `FK_study_course` FOREIGN KEY (`YEAR`, `TERM`, `LECTURE_NO`, `COURSE_NO`, `QUESTION_NO`) REFERENCES `course` (`YEAR`, `TERM`, `LECTURE_NO`, `COURSE_NO`, `QUESTION_NO`),
  CONSTRAINT `FK_study_user` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='학생이 받아쓰기 진행한 기록';

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.term_cd 구조 내보내기
CREATE TABLE IF NOT EXISTS `term_cd` (
  `YEAR` varchar(4) NOT NULL COMMENT '년도',
  `TERM` varchar(2) NOT NULL COMMENT '학기',
  `MANAGE_ST_DT` varchar(8) DEFAULT NULL COMMENT '관리시작일',
  `MANAGE_ED_DT` varchar(8) DEFAULT NULL COMMENT '관리종료일',
  `ENROLL_ST_DT` varchar(8) DEFAULT NULL COMMENT '수강신청시작일',
  `ENROLL_ED_DT` varchar(8) DEFAULT NULL COMMENT '수강신청종료일',
  `USE_YN` varchar(1) DEFAULT NULL COMMENT '사용여부 : 사용 1, 미사용 0',
  `BIGO` varchar(200) DEFAULT NULL COMMENT '비고',
  `INPUT_ID` varchar(45) DEFAULT NULL COMMENT '입력자 아이디',
  `INPUT_DATE` datetime DEFAULT NULL COMMENT '입력일시',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정자아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`YEAR`,`TERM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mydb.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `USER_ID` varchar(45) NOT NULL COMMENT '아이디',
  `PW` varchar(50) NOT NULL COMMENT '패스워드',
  `SCHOOL_CD` varchar(6) DEFAULT NULL COMMENT '학교코드 : 만천초등학교, 한림초등학교\n\n입력하기 전에 선택하도록 함',
  `POSITION_CD` varchar(6) NOT NULL COMMENT '신분구분코드 : 관리자, 선생님, 학생',
  `KOR_NM` varchar(45) DEFAULT NULL COMMENT '한글이름',
  `END_NM` varchar(45) DEFAULT NULL COMMENT '영문이름',
  `GRADE` int(11) NOT NULL COMMENT '학년',
  `BAN` varchar(10) DEFAULT NULL COMMENT '반',
  `BIRTH_DT` varchar(15) DEFAULT NULL COMMENT '생년월일',
  `CEL_PHONE_NO` varchar(45) DEFAULT NULL COMMENT '핸드폰번호',
  `HOM_PHONE_NO` varchar(45) DEFAULT NULL COMMENT '집전화번호',
  `GENDER_CD` varchar(45) DEFAULT NULL COMMENT '성별코드 : 남, 여',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '이메일 주소',
  `REGISTER_DT` varchar(8) DEFAULT NULL COMMENT '가입일',
  `LOGIN_DATE` datetime DEFAULT NULL COMMENT '로그인시간',
  `LOGOUT_DATE` datetime DEFAULT NULL COMMENT '로그아웃시간',
  `INPUT_ID` varchar(45) DEFAULT NULL COMMENT '입력자 아이디',
  `INPUT_DATE` datetime DEFAULT NULL COMMENT '입력일시',
  `UPDATE_ID` varchar(45) DEFAULT NULL COMMENT '수정자아이디',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `idname_UNIQUE` (`PW`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
