-- Oracle schema for the authentication module.
-- UUIDs are stored as RAW(16), booleans as NUMBER(1), timestamps in UTC.

-- Employee table
CREATE TABLE EMPLOYEE (
    USER_ID RAW(16) NOT NULL,
    CONSTRAINT PK_EMPLOYEE PRIMARY KEY (USER_ID)
);

CREATE TABLE IAM_Authentication_Email (
    Email_Id      RAW(16)       NOT NULL,
    User_Id       RAW(16)       NOT NULL,
    Email         VARCHAR2(255) NOT NULL,
    Is_Primary    NUMBER(1)     DEFAULT 0 NOT NULL,
    Is_Verified   NUMBER(1)     DEFAULT 0 NOT NULL,
    Is_Deleted    NUMBER(1)     DEFAULT 0 NOT NULL,
    Deleted_At    TIMESTAMP(6),
    CONSTRAINT PK_IAM_Auth_Email PRIMARY KEY (Email_Id)
);
CREATE INDEX IX_IAM_Auth_Email_Email  ON IAM_Authentication_Email (Email);
CREATE INDEX IX_IAM_Auth_Email_UserId ON IAM_Authentication_Email (User_Id);

CREATE TABLE IAM_Authentication_Password (
    Password_Id        RAW(16)       NOT NULL,
    User_Id            RAW(16)       NOT NULL,
    Password_Hash      VARCHAR2(255) NOT NULL,
    Password_Algorithm VARCHAR2(50)  NOT NULL,
    Is_Active          NUMBER(1)     DEFAULT 1 NOT NULL,
    Is_Deleted         NUMBER(1)     DEFAULT 0 NOT NULL,
    Deleted_At         TIMESTAMP(6),
    CONSTRAINT PK_IAM_Auth_Password PRIMARY KEY (Password_Id)
);
CREATE INDEX IX_IAM_Auth_Password_UserId ON IAM_Authentication_Password (User_Id);

CREATE TABLE IAM_Auth_RefreshToken (
    Refresh_Token_Id RAW(16)       NOT NULL,
    User_Id          RAW(16)       NOT NULL,
    Token            VARCHAR2(500) NOT NULL,
    Expire_At        TIMESTAMP(6)  NOT NULL,
    Is_Revoked       NUMBER(1)     DEFAULT 0 NOT NULL,
    Revoked_At       TIMESTAMP(6),
    CONSTRAINT PK_IAM_Auth_RefreshToken PRIMARY KEY (Refresh_Token_Id)
);
CREATE INDEX IX_IAM_Auth_RefToken_Token ON IAM_Auth_RefreshToken (Token);

CREATE TABLE IAM_Auth_PasswordResetToken (
    Password_Reset_Token_Id RAW(16)       NOT NULL,
    User_Id                 RAW(16)       NOT NULL,
    Token                   VARCHAR2(500) NOT NULL,
    Expire_At               TIMESTAMP(6)  NOT NULL,
    Is_Used                 NUMBER(1)     DEFAULT 0 NOT NULL,
    Created_At              TIMESTAMP(6)  NOT NULL,
    CONSTRAINT PK_IAM_Auth_PwdResetToken PRIMARY KEY (Password_Reset_Token_Id)
);
CREATE INDEX IX_IAM_Auth_PwdReset_Token ON IAM_Auth_PasswordResetToken (Token);

CREATE TABLE IAM_Auth_SetPasswordToken (
    Set_Password_Token_Id RAW(16)       NOT NULL,
    User_Id               RAW(16)       NOT NULL,
    Token                 VARCHAR2(500) NOT NULL,
    Expire_At             TIMESTAMP(6)  NOT NULL,
    Is_Used               NUMBER(1)     DEFAULT 0 NOT NULL,
    Created_At            TIMESTAMP(6)  NOT NULL,
    CONSTRAINT PK_IAM_Auth_SetPwdToken PRIMARY KEY (Set_Password_Token_Id)
);
CREATE INDEX IX_IAM_Auth_SetPwd_Token ON IAM_Auth_SetPasswordToken (Token);

CREATE TABLE IAM_Auth_LoginSession (
    Session_Id       RAW(16)      NOT NULL,
    User_Id          RAW(16)      NOT NULL,
    Refresh_Token_Id RAW(16),
    Device_Name      VARCHAR2(100),
    Device_Type      VARCHAR2(50),
    Ip_Address       VARCHAR2(45),
    User_Agent       VARCHAR2(255),
    Login_At         TIMESTAMP(6) NOT NULL,
    Logout_At        TIMESTAMP(6),
    Is_Active        NUMBER(1)    DEFAULT 1 NOT NULL,
    CONSTRAINT PK_IAM_Auth_LoginSession PRIMARY KEY (Session_Id)
);
CREATE INDEX IX_IAM_Auth_LoginSession_UID ON IAM_Auth_LoginSession (User_Id);

ALTER TABLE IAM_Authentication_Email
ADD CONSTRAINT FK_Auth_Email_Employee
FOREIGN KEY (User_Id) REFERENCES EMPLOYEE(User_Id);

ALTER TABLE IAM_Authentication_Password
ADD CONSTRAINT FK_Auth_Password_Employee
FOREIGN KEY (User_Id) REFERENCES EMPLOYEE(User_Id);

ALTER TABLE IAM_Auth_RefreshToken
ADD CONSTRAINT FK_Auth_Refresh_Employee
FOREIGN KEY (User_Id) REFERENCES EMPLOYEE(User_Id);

ALTER TABLE IAM_Auth_PasswordResetToken
ADD CONSTRAINT FK_Auth_PwdReset_Employee
FOREIGN KEY (User_Id) REFERENCES EMPLOYEE(User_Id);

ALTER TABLE IAM_Auth_SetPasswordToken
ADD CONSTRAINT FK_Auth_SetPwd_Employee
FOREIGN KEY (User_Id) REFERENCES EMPLOYEE(User_Id);

ALTER TABLE IAM_Auth_LoginSession
ADD CONSTRAINT FK_Auth_Session_Employee
FOREIGN KEY (User_Id) REFERENCES EMPLOYEE(User_Id);

ALTER TABLE IAM_Auth_LoginSession
ADD CONSTRAINT FK_Auth_Session_RefreshToken
FOREIGN KEY (Refresh_Token_Id)
REFERENCES IAM_Auth_RefreshToken(Refresh_Token_Id);