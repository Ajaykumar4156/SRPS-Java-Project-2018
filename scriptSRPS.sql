create table DEPT
(
  DEPT_NAME VARCHAR2(20) not null
    primary key,
  BULIDING  VARCHAR2(10)
)
/

create table TEACHER
(
  T_ID      NUMBER        not null
    primary key,
  T_NAME    VARCHAR2(100) not null,
  T_DEPT    VARCHAR2(20)
    constraint TEACHER_DEPT__DEPT_NAME_FK
    references DEPT,
  T_ADDRESS VARCHAR2(100),
  T_CONTACT VARCHAR2(15)
)
/

create table COURSES
(
  C_ID     VARCHAR2(20) not null
    primary key,
  C_TITLE  VARCHAR2(50) not null,
  C_DEPT   VARCHAR2(20)
    constraint COURSES_DEPT_DEPT_NAME_FK
    references DEPT
    on delete cascade,
  C_CREDIT NUMBER(3, 2) default NULL
)
/

create table STUDENT
(
  S_ID      NUMBER        not null
    primary key,
  S_NAME    VARCHAR2(100) not null,
  S_DEPT    VARCHAR2(20)
    constraint STUDENT_DEPT__DEPT_NAME_FK
    references DEPT,
  S_ADDRESS VARCHAR2(100),
  S_CONTACT VARCHAR2(15)
)
/

create table MARKS
(
  M_SID     NUMBER       not null
    constraint MARKS_STUDENT_S_ID_FK
    references STUDENT,
  M_DEPT    VARCHAR2(20) not null
    constraint MARKS_DEPT_DEPT_NAME_FK
    references DEPT,
  M_CID     VARCHAR2(20) not null
    constraint MARKS_COURSES_C_ID_FK
    references COURSES,
  MIDEXAM   FLOAT,
  FINALEXAM FLOAT
)
/

create view STD_MARKS_DETAILS as
  SELECT S_ID AS STUDENT_ID, S_NAME AS STUDENT_NAME, C_ID AS COURSE_ID, C_TITLE AS COURSE_NAME, MIDEXAM, FINALEXAM
  FROM STUDENT,
       COURSES,
       MARKS
  WHERE S_ID = M_SID
    AND M_CID = C_ID
    AND S_DEPT = M_DEPT
/


