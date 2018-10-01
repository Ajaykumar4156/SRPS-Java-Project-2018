--uncomment the following line you want to test database (give privileges to this user )
--CREATE USER SRPS IDENTIFIED BY hello123

--Table for Department
create table DEPT
(
  DEPT_NAME VARCHAR2(20) not null
    primary key,
  BULIDING  VARCHAR2(10)
)
/

--Table for Teacher
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

--Table for Courses
create table COURSES
(
  C_ID     VARCHAR2(20) not null
    primary key,
  C_TITLE  VARCHAR2(50) not null,
  C_DEPT   VARCHAR2(20)
    constraint COURSES_DEPT_DEPT_NAME_FK
    references DEPT,
  C_CREDIT NUMBER(3, 2)
)
/

-- Table for Student
create table STUDENT
(
  S_ID      NUMBER        not null
    primary key,
  S_NAME    VARCHAR2(100) not null,
  S_DEPT    VARCHAR2(20)
    constraint STUDENT_DEPT__DEPT_NAME_FK
    references DEPT,
  ADDRESS   VARCHAR2(100),
  S_CONTACT VARCHAR2(15)
)
/

-- Table for storing marks
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
  FINALEXAM FLOAT,
  QUIZ1     FLOAT        not null, --The teacher must need to submit at least 1 quiz result while he's updating the marks
  QUIZ2     FLOAT,
  QUIZ3     FLOAT,
  QUIZ4     FLOAT,
  QUIZ5     FLOAT
)
/


