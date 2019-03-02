--生成货架规则策略表
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

COMMENT ON TABLE T_Category_Rule IS '货架策略规则表，保存货架的相关规则';

COMMENT ON COLUMN T_Category_Rule.CID IS '规则对应的货架的货架内码';

COMMENT ON COLUMN T_Category_Rule.refreshGoods IS
'是否需要刷新货架下商品 0：不需要；1：需要。';

COMMENT ON COLUMN T_Category_Rule.getElite IS
'是否从精品库货架获取 0：不需要；1：需要。';

COMMENT ON COLUMN T_Category_Rule.eliteCID IS '精品库货架的货架内码';

COMMENT ON COLUMN T_Category_Rule.getEliteWSql IS
'从精品库货架获取产品的条件sql';

COMMENT ON COLUMN T_Category_Rule.getEliteOSql IS
'从精品库货架获取产品的排序sql';

COMMENT ON COLUMN T_Category_Rule.eliteCount IS '从精品库货架获取的商品数量';

COMMENT ON COLUMN T_Category_Rule.getContentWSql IS
'从产品库获取产品的条件sql';

COMMENT ON COLUMN T_Category_Rule.getContentOSql IS
'从产品库获取产品的排序sql';

COMMENT ON COLUMN T_Category_Rule.maxGoodsCount IS '本货架下商品最大数量';

COMMENT ON COLUMN T_Category_Rule.reorderGoods IS
'是否需要对货架下商品重新排序 0：不需要；1：需要。';

COMMENT ON COLUMN T_Category_Rule.excuteInterval IS '执行之间间隔，单位为天';

COMMENT ON COLUMN T_Category_Rule.lastExcuteTime IS
'上次执行时间，需要精确到秒';

COMMENT ON COLUMN T_Category_Rule.effectiveTime IS
'规则生效时间';


-- T_PPS_COMMENT_GRADE视图要授权给SSMS用户使用。


--1.1 如果PORTALCOMMON和MM.SSMS在同一数据库实例下，在PORTALCOMMON用户下执行如下授权脚本：
 
ACCEPT ssms_db_user CHAR prompt 'input MM.SSMS DB username:';

grant select on T_PPS_COMMENT_GRADE        to &pas_db_user;

--2  在MM SSMS用户下执行如下创建同义词脚本：
 
ACCEPT db_user CHAR prompt 'input PORTALCOMMON DB username:';

create or replace synonym T_PPS_COMMENT_GRADE         for &db_user .T_PPS_COMMENT_GRADE      ;





--1.2  如果PORTALCOMMON和MM.SSMS不在同一数据库实例下，执行如下创建dblink以及同义词脚本，名称根据实际情况替换。
create database link PORTALCOMMONTOSSMS connect to PORTALCOMMON identified by PORTALCOMMON using 'DB3_9';

create or replace synonym T_PPS_COMMENT_GRADE        for T_PPS_COMMENT_GRADE@PORTALCOMMONTOSSMS;

--现网货架商品自动更新规则 begin
-----------------------------------
--        大类:WWW货架           --
-----------------------------------

------------------------------------
--        货架类型：分类          --
------------------------------------

--一级货架：WWW软件
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

--一级货架：WWW游戏
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

--一级货架：WWW主题
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
--        货架类型：免费          --
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
--        货架类型：最新          --
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
--        货架类型：推荐          --
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
--        货架类型：月排行        --
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
--        货架类型：周排行        --
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
--        货架类型：总排行        --
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
--      货架类型：应用精品库      --
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
--     大类:集团版终端货架       --
-----------------------------------

------------------------------------
--        货架类型：分类          --
------------------------------------

--二级货架：应用软件
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

--二级货架：手机游戏
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

--二级货架：手机主题
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
--      货架类型：软件免费        --
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
--      货架类型：软件排行        --
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
--      货架类型：软件推荐        --
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
--      货架类型：软件星级        --
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
--      货架类型：软件最新        --
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
--      货架类型：游戏免费        --
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
--      货架类型：游戏排行        --
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
--      货架类型：游戏推荐        --
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
--      货架类型：游戏星级        --
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
--      货架类型：游戏最新        --
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
--      货架类型：主题免费        --
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
--      货架类型：主题排行        --
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
--      货架类型：主题推荐        --
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
--      货架类型：主题星级        --
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
--      货架类型：主题最新        --
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
--      货架类型：热卖场        --
------------------------------------
--装机必备
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1782548', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 30, NULL, sysdate);
--热卖场->品牌->game loft
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827053', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 180, NULL, sysdate);
--热卖场->品牌->空中信使
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827054', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 180, NULL, sysdate);
--热卖场->品牌->掌中米格
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827055', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 180, NULL, sysdate);
--热卖场->专题->理财助手
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827607', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, sysdate);
--热卖场->专题->生活百科
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827608', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, sysdate);
--热卖场->专题->学习园地
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1827609', 0, 0, NULL, NULL, 
    NULL, -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, sysdate);
--现网货架商品自动更新规则 end

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.023_SSMS','MM1.0.0.029_SSMS');
commit;