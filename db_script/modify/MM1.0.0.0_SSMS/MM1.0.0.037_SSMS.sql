--备份032版本数据
create table t_category_rule_20090825_bak as select * from T_CATEGORY_RULE;

--删除032版本数据
delete from T_CATEGORY_RULE;

--添加新数据
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
   ('1810803', 1, 1, '1885166', NULL, 
    'sortID desc,marketDate desc', -1, 'mobilePrice=0', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--        货架类型：最新          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1814487', 1, 1, '1885168', NULL, 
    'sortID desc,marketDate desc', -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--        货架类型：推荐          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810805', 1, 1, '1885167', NULL, 
    'sortID desc,marketDate desc', -1, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--        货架类型：月排行        --
------------------------------------    
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815682', 1, 1, '1885169', NULL, 
    'sortID desc,marketDate desc', -1, NULL, 'monthordertimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 30, NULL, to_date('2009-09-01 00:00:00','yyyy-mm-dd hh24:mi:ss'));
------------------------------------
--        货架类型：周排行        --
------------------------------------ 
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815681', 1, 1, '1885170', NULL, 
    'sortID desc,marketDate desc', -1, NULL, 'weekordertimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 7, NULL, to_date('2009-08-31 00:00:00','yyyy-mm-dd hh24:mi:ss'));
------------------------------------
--        货架类型：总排行        --
------------------------------------ 
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810802', 1, 1, '1885171', NULL, 
    'sortID desc,marketDate desc', -1, NULL, 'orderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
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
   ('1257460', 1, 1, '1885172', 'type=''nt:gcontent:appSoftWare''', 
    'sortID desc,marketDate desc', -1, 'mobilePrice=0 and type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：软件排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257459', 1, 1, '1885173', 'type=''nt:gcontent:appSoftWare''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：软件推荐        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257457', 1, 1, '1885174',  'type=''nt:gcontent:appSoftWare''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--      货架类型：软件星级        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257461', 1, 1, '1885175', 'type=''nt:gcontent:appSoftWare''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appSoftWare''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：软件最新        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257458', 1, 1, '1885176', 'type=''nt:gcontent:appSoftWare''', 
    'sortID desc,marketDate desc', -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appSoftWare''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：游戏免费        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257462', 1, 1, '1885177', 'type=''nt:gcontent:appGame''', 
    'sortID desc,marketDate desc', -1, 'mobilePrice=0 and type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：游戏排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257463', 1, 1, '1885178', 'type=''nt:gcontent:appGame''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：游戏推荐        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257464', 1, 1, '1885179',  'type=''nt:gcontent:appGame''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--      货架类型：游戏星级        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257465', 1, 1, '1885180', 'type=''nt:gcontent:appGame''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appGame''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：游戏最新        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257466', 1, 1, '1885181', 'type=''nt:gcontent:appGame''', 
    'sortID desc,marketDate desc', -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appGame''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：主题免费        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257467', 1, 1, '1885182', 'type=''nt:gcontent:appTheme''', 
    'sortID desc,marketDate desc', -1, 'mobilePrice=0 and type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：主题排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257468', 1, 1, '1885183', 'type=''nt:gcontent:appTheme''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：主题推荐        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257469', 1, 1, '1885184',  'type=''nt:gcontent:appTheme''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc', -1, 
    1, 3, NULL, sysdate);
------------------------------------
--      货架类型：主题星级        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257470', 1, 1, '1885185', 'type=''nt:gcontent:appTheme''', 
    'sortID desc,marketDate desc', -1, 'type=''nt:gcontent:appTheme''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc', -1, 
    1, 1, NULL, sysdate);
------------------------------------
--      货架类型：主题最新        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, REFRESHGOODS, GETELITE, ELITECID, GETELITEWSQL, 
    GETELITEOSQL, ELITECOUNT, GETCONTENTWSQL, GETCONTENTOSQL, MAXGOODSCOUNT, 
    REORDERGOODS, EXCUTEINTERVAL, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257471', 1, 1, '1885186', 'type=''nt:gcontent:appTheme''', 
    'sortID desc,marketDate desc', -1, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appTheme''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc', -1, 
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


--添加版本
--insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.032_SSMS','MM1.0.0.037_SSMS');


commit;