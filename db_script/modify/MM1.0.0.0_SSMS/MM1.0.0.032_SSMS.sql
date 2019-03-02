--���ɻ��ܹ�����Ա�
CREATE TABLE T_Category_Rule
(
   CID              VARCHAR2 (30)	NOT NULL,
   refreshGoods     NUMBER (1, 0)	DEFAULT 0 not null,
   getElite         NUMBER (1, 0)	DEFAULT 0 not null,
   eliteCID         VARCHAR2 (30),
   getEliteWSql	    VARCHAR2 (1000),
   getEliteOSql     VARCHAR2 (1000),
   eliteCount       NUMBER (8, 0)	default -1 not null,
   getContentWSql   VARCHAR2 (1000),
   getContentOSql   VARCHAR2 (1000),
   maxGoodsCount    NUMBER (8, 0)	default -1 not null,
   reorderGoods     NUMBER (1, 0)	DEFAULT 0 not null,
   excuteInterval   NUMBER (5, 0)	NOT NULL,
   lastExcuteTime   DATE,
   effectiveTime    DATE		default sysdate not null,
   CONSTRAINT T_Category_Rule_PK PRIMARY KEY (CID)
);

COMMENT ON TABLE T_Category_Rule IS '���ܲ��Թ����������ܵ���ع���';

COMMENT ON COLUMN T_Category_Rule.CID IS '�����Ӧ�Ļ��ܵĻ�������';

COMMENT ON COLUMN T_Category_Rule.refreshGoods IS
'�Ƿ���Ҫˢ�»�������Ʒ 0������Ҫ��1����Ҫ��';

COMMENT ON COLUMN T_Category_Rule.getElite IS
'�Ƿ�Ӿ�Ʒ����ܻ�ȡ 0������Ҫ��1����Ҫ��';

COMMENT ON COLUMN T_Category_Rule.eliteCID IS '��Ʒ����ܵĻ�������';

COMMENT ON COLUMN T_Category_Rule.getEliteWSql IS
'�Ӿ�Ʒ����ܻ�ȡ��Ʒ������sql';

COMMENT ON COLUMN T_Category_Rule.getEliteOSql IS
'�Ӿ�Ʒ����ܻ�ȡ��Ʒ������sql';

COMMENT ON COLUMN T_Category_Rule.eliteCount IS '�Ӿ�Ʒ����ܻ�ȡ����Ʒ����';

COMMENT ON COLUMN T_Category_Rule.getContentWSql IS
'�Ӳ�Ʒ���ȡ��Ʒ������sql';

COMMENT ON COLUMN T_Category_Rule.getContentOSql IS
'�Ӳ�Ʒ���ȡ��Ʒ������sql';

COMMENT ON COLUMN T_Category_Rule.maxGoodsCount IS '����������Ʒ�������';

COMMENT ON COLUMN T_Category_Rule.reorderGoods IS
'�Ƿ���Ҫ�Ի�������Ʒ�������� 0������Ҫ��1����Ҫ��';

COMMENT ON COLUMN T_Category_Rule.excuteInterval IS 'ִ��֮��������λΪ��';

COMMENT ON COLUMN T_Category_Rule.lastExcuteTime IS
'�ϴ�ִ��ʱ�䣬��Ҫ��ȷ����';

COMMENT ON COLUMN T_Category_Rule.effectiveTime IS
'������Чʱ��';


-- T_PPS_COMMENT_GRADE��ͼҪ��Ȩ��SSMS�û�ʹ�á�


--1.1 ���PORTALCOMMON��MM.SSMS��ͬһ���ݿ�ʵ���£���PORTALCOMMON�û���ִ��������Ȩ�ű���
 
ACCEPT ssms_db_user CHAR prompt 'input MM.SSMS DB username:';

grant select on T_PPS_COMMENT_GRADE        to &pas_db_user;

--2  ��MM SSMS�û���ִ�����´���ͬ��ʽű���
 
ACCEPT db_user CHAR prompt 'input PORTALCOMMON DB username:';

create or replace synonym T_PPS_COMMENT_GRADE         for &db_user .T_PPS_COMMENT_GRADE      ;





--1.2  ���PORTALCOMMON��MM.SSMS����ͬһ���ݿ�ʵ���£�ִ�����´���dblink�Լ�ͬ��ʽű������Ƹ���ʵ������滻��
create database link PORTALCOMMONTOSSMS connect to PORTALCOMMON identified by PORTALCOMMON using 'DB3_9';

create or replace synonym T_PPS_COMMENT_GRADE        for T_PPS_COMMENT_GRADE@PORTALCOMMONTOSSMS;

--����������Ʒ�Զ����¹��� begin
-----------------------------------
--        ����:WWW����           --
-----------------------------------

------------------------------------
--        �������ͣ�����          --
------------------------------------

--һ�����ܣ�WWW���
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7064', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7008', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7005', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7010', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7012', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7004', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);

--һ�����ܣ�WWW��Ϸ
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7014', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7022', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7020', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7016', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7052', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('691704', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7026', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7024', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7018', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);

--һ�����ܣ�WWW����
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7042', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7050', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7034', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7044', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7036', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7028', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7046', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7048', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7030', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7040', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7032', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7038', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);

------------------------------------
--        �������ͣ����          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810803', 1, 0, NULL, NULL, 
    NULL, -1, 'mobilePrice=0', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--        �������ͣ�����          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1814487', 1, 0, NULL, NULL, 
    NULL, -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--        �������ͣ��Ƽ�          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810805', 1, 1, '1826912', NULL, 
    'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--        �������ͣ�������        --
------------------------------------    
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815682', 1, 0, NULL, NULL, 
    NULL, -1, NULL, 'monthordertimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 30, NULL, to_date('2009-09-01 00:00:00','yyyy-mm-dd hh24:mi:ss'));
------------------------------------
--        �������ͣ�������        --
------------------------------------ 
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815681', 1, 0, NULL, NULL, 
    NULL, -1, NULL, 'weekordertimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, to_date('2009-08-24 00:00:00','yyyy-mm-dd hh24:mi:ss'));
------------------------------------
--        �������ͣ�������        --
------------------------------------ 
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810802', 1, 0, NULL, NULL, 
    NULL, -1, NULL, 'orderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ�Ӧ�þ�Ʒ��      --
------------------------------------     
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1826912', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
-----------------------------------
--     ����:���Ű��ն˻���       --
-----------------------------------

------------------------------------
--        �������ͣ�����          --
------------------------------------

--�������ܣ�Ӧ�����
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7009', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7006', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7011', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7013', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7007', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);

--�������ܣ��ֻ���Ϸ
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7015', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7023', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7021', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7017', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7053', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7628', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7027', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7025', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7019', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);

--�������ܣ��ֻ�����
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7043', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7051', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7035', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7045', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7037', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7029', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7047', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7049', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7031', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7041', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7033', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7039', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ�������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257460', 1, 0, NULL, NULL, 
    NULL, -1, 'mobilePrice=0 and type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257459', 1, 0, NULL, NULL, 
    NULL, -1, 'type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ�����Ƽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257457', 1, 1, '1826912', 'type=''nt:gcontent:appSoftWare''', 
    'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 'type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--      �������ͣ�����Ǽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257461', 1, 0, NULL, NULL, 
    NULL, -1, 'type=''nt:gcontent:appSoftWare''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257458', 1, 0, NULL, NULL, 
    NULL, -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appSoftWare''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ���Ϸ���        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257462', 1, 0, NULL, NULL, 
    NULL, -1, 'mobilePrice=0 and type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ���Ϸ����        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257463', 1, 0, NULL, NULL, 
    NULL, -1, 'type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ���Ϸ�Ƽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257464', 1, 1, '1826912', 'type=''nt:gcontent:appGame''', 
    'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 'type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--      �������ͣ���Ϸ�Ǽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257465', 1, 0, NULL, NULL, 
    NULL, -1, 'type=''nt:gcontent:appGame''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ���Ϸ����        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257466', 1, 0, NULL, NULL, 
    NULL, -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appGame''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257467', 1, 0, NULL, NULL, 
    NULL, -1, 'mobilePrice=0 and type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ���������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257468', 1, 0, NULL, NULL, 
    NULL, -1, 'type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ������Ƽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257469', 1, 1, '1826912', 'type=''nt:gcontent:appTheme''', 
    'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 'type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--      �������ͣ������Ǽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257470', 1, 0, NULL, NULL, 
    NULL, -1, 'type=''nt:gcontent:appTheme''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ���������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257471', 1, 0, NULL, NULL, 
    NULL, -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appTheme''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      �������ͣ�������        --
------------------------------------
--װ���ر�
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1782548', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 30, NULL, sysdate);
--������->Ʒ��->game loft
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827053', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 180, NULL, sysdate);
--������->Ʒ��->������ʹ
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827054', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 180, NULL, sysdate);
--������->Ʒ��->�����׸�
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827055', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 180, NULL, sysdate);
--������->ר��->�������
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827607', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, sysdate);
--������->ר��->����ٿ�
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827608', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, sysdate);
--������->ר��->ѧϰ԰��
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827609', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, sysdate);
--����������Ʒ�Զ����¹��� end

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.023_SSMS','MM1.0.0.029_SSMS');
commit;