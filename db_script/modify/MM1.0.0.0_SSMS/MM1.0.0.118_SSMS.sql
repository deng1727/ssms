---新增数据导出菜单权限
insert into t_right r values('1_0807_RES_BLACKLIST','内容数据导出','内容数据导出','2_0801_RESOURCE',0);
insert into t_roleright(ROLEID,RIGHTID) values(1,'1_0807_RES_BLACKLIST');


alter table T_CONTENT_BACKLIST add create_time DATE default sysdate not null ;
alter table T_CONTENT_BACKLIST add modify_time DATE default sysdate not null ;
alter table T_CONTENT_BACKLIST add black_type NUMBER(2) default 1 not null ;
comment on column T_CONTENT_BACKLIST.create_time is '创建时间';
comment on column T_CONTENT_BACKLIST.modify_time is '修改时间';
comment on column T_CONTENT_BACKLIST.black_type is '黑名单类型： 1.嫌疑刷榜 2.首次刷榜 3.多次刷榜  （2次以上）';

insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ADD_BLACK_OK', '内容{0}加入黑名单成功！', '内容加入黑名单成功');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ADD_BLACK_FAIL', '内容{0}加入黑名单失败！', '内容加入黑名单失败');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_BLACK_EXIST', '内容{0}已经在黑名单中！', '内容已经在黑名单中');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODIFY_BLACK_OK', '修改黑名单内容{0}成功！', '修改黑名单内容成功');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODIFY_BLACK_FAIL', '修改黑名单内容{0}失败！', '修改黑名单内容失败');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DEL_BLACK_OK', '删除黑名单内容{0}成功！', '删除黑名单内容成功');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DEL_BLACK_FAIL', '删除黑名单内容{0}失败！', '删除黑名单内容失败');

---基地音乐分离
-- Create table
create table T_MB_MUSIC
(
  MUSICID    VARCHAR2(30) not null,
  SONGNAME   VARCHAR2(200),
  SINGER     VARCHAR2(100),
  VALIDITY   VARCHAR2(512),
  UPDATETIME VARCHAR2(30),
  CREATETIME VARCHAR2(30),
  DELFLAG    NUMBER(4) default 0 not null
);
-- Add comments to the columns 
comment on column T_MB_MUSIC.MUSICID
  is '歌曲ID';
comment on column T_MB_MUSIC.SONGNAME
  is '歌曲名称';
comment on column T_MB_MUSIC.SINGER
  is '歌手名称';
comment on column T_MB_MUSIC.VALIDITY
  is '有效期';
comment on column T_MB_MUSIC.UPDATETIME
  is '更新时间';
comment on column T_MB_MUSIC.CREATETIME
  is '创建时间';
  comment on column T_MB_MUSIC.DELFLAG
  is '删除标记，0，未删除；1，已删除';
-- Create/Recreate indexes 
create unique index PK_MB_MUSIC_ID on T_MB_MUSIC (MUSICID);

- Create table
create table T_MB_CATEGORY
(
  CATEGORYID       VARCHAR2(30) not null,
  CATEGORYNAME     VARCHAR2(200) not null,
  PARENTCATEGORYID VARCHAR2(30),
  TYPE             VARCHAR2(10),
  DELFLAG          VARCHAR2(2),
  CREATETIME       VARCHAR2(30),
  CATEGORYDESC     VARCHAR2(500),
  SORTID           NUMBER(8),
  SUM              NUMBER(8) default 0 not null
);
-- Add comments to the columns 
comment on column T_MB_CATEGORY.CATEGORYID
  is '货架ID';
comment on column T_MB_CATEGORY.CATEGORYNAME
  is '货架名称';
comment on column T_MB_CATEGORY.PARENTCATEGORYID
  is '货架父ID';
comment on column T_MB_CATEGORY.TYPE
  is '类型';
comment on column T_MB_CATEGORY.DELFLAG
  is '删除标记';
comment on column T_MB_CATEGORY.CREATETIME
  is '创建时间';
comment on column T_MB_CATEGORY.CATEGORYDESC
  is '货架描述';
comment on column T_MB_CATEGORY.SORTID
  is '货架排序';
comment on column T_MB_CATEGORY.SUM
  is '货架商品数量';
-- Create/Recreate indexes 
create unique index PK_T_MB_CATEGORY_ID on T_MB_CATEGORY (CATEGORYID);

-- Create table
create table T_MB_REFERENCE
(
  MUSICID    VARCHAR2(30) not null,
  CATEGORYID VARCHAR2(30) not null,
  MUSICNAME  VARCHAR2(200),
  CREATETIME VARCHAR2(30),
  SORTID     NUMBER(8)
  
);
-- Add comments to the columns 
comment on column T_MB_REFERENCE.MUSICID
  is '音乐ID';
comment on column T_MB_REFERENCE.CATEGORYID
  is '货架ID';
comment on column T_MB_REFERENCE.CREATETIME
  is '创建时间';
comment on column T_MB_REFERENCE.SORTID
  is '排序';
comment on column T_MB_REFERENCE.MUSICNAME
  is '歌曲名称';
-- Create/Recreate indexes 
create index INDEX_TB_REFERENCE_CAID on T_MB_REFERENCE (CATEGORYID);
create unique index PK_MUSIC_CATEID on T_MB_REFERENCE (MUSICID, CATEGORYID);

------授权给终端门户使用
grant select on T_MB_MUSIC to &portalmo;--终端门户
grant select on T_MB_CATEGORY to &portalmo;--终端门户
grant select on T_MB_REFERENCE to &portalmo;--终端门户


insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100002166', '最新', '', '1', '0', '2010-05-10 09:03:00', '最新', 0, 0);

insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100000445', '排行', '', '1', '0', '2010-05-10 09:03:00', '排行', 0, 0);

insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100001913', '专辑', '', '1', '0', '2010-05-10 09:03:00', '专辑', 0, 0);

insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100001914', '热门', '', '1', '0', '2010-05-10 09:03:00', '热门', 0, 0);
---创建序列
-- Create sequence 
create sequence SEQ_BM_CATEGORY_ID  minvalue 100002167  maxvalue 999999999  start with 100004206  increment by 1  nocache  cycle;



--修改注释
comment on column T_CATERULE_COND.CID is '获取货架的货架内码.空则本字段无效';
comment on column T_R_GCONTENT.ONLINETYPE  is '1：离线应用，2：在线应用';

--G+游戏包修改表结构
alter table T_GAME_BASE add state varchar2(2) default 1 not null;
comment on column T_GAME_BASE.state
  is '当前状态1:新建,2:更新,3:删除';

--小编推荐
alter table T_CONTENT_COUNT add recommend_grade number(6,2);
comment on column T_CONTENT_COUNT.recommend_grade
  is '推荐评分';

--变更表结构
alter table T_CONTENT_COUNT modify LATESTCOUNT default null null;
alter table T_CONTENT_COUNT modify RECOMMENDCOUNT default null null;
alter table T_CONTENT_COUNT modify COMPECOUNT default null null;  
  
--使用原来创建的dblink：PPMSTOSSMS创建同义词
create or replace synonym ppms_v_cm_content_recommend for v_cm_content_recommend@PPMSTOSSMS;

----ap过有效期过滤应用
--使用原来创建的dblink：PPMSTOSSMS创建同义词
create or replace synonym v_valid_company for v_valid_company@PPMSTOSSMS;

create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       c.ContentCode,
       c.Keywords,
       decode(c.status,
              '0006',
              decode(f.status, 2, '0006', 5, '0008'),
              '1006',
              decode(f.status, 2, '0006', 5, '0008'),
              '0015',
              decode(f.status||f.substatus, '61', '0006','0008'),
              '1015',
              decode(f.status||f.substatus, '61', '0006','0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L',d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype in ('1','2')--增加widget应用;
;
----创建ppms_v_service 视图 添加基地游戏表业务数据关联
create or replace view ppms_v_service as
select p.contentid,
       v1.apcode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,
              '00',
              'S',
              '01',
              'W',
              '02',
              'M',
              '10',
              'A',
              '05',
              'E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.paytype,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       decode(p.chargetime || v2.paytype, '20', p.feedesc, v2.chargedesc) as chargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company    v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
   and c.thirdapptype in ('1', '2')
  UNION ALL
 select
       t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       t.servflag,
       t.chargetype,
       null,
       t.mobileprice,
       null,
       t.chargedesc,
       'B',
       'G',--全网业务
       t.servdesc,
       null,
       t.lupddate
  from t_game_service t;



drop materialized view V_THIRD_SERVICE;
create materialized view V_THIRD_SERVICE
refresh force on demand
as
select v1.apcode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,
              '00',
              'S',
              '01',
              'W',
              '02',
              'M',
              '10',
              'A',
              '05',
              'E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       v2.ChargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company v1, v_om_product v2
 where v1.companyid = v2.CompanyID
   and v1.icptype = 1
   and v2.ProviderType = 'V';

------新增5个自动上架规则
insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (152, 'WWW门户小编推荐', 0, 0, 1, null, 100);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (152, '', 10, 'c.RECOMMEND_GRADE is not null', '', -1, null);

insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('17657767', 152, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (153, 'WWW门户小编推荐排行', 0, 0, 1, null, 0);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (153, '', 10, 'c.RECOMMEND_GRADE is not null', 'dayOrderTimes desc,c.RECOMMEND_GRADE desc,createdate desc,mobilePrice desc,name asc', -1, null);

insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('17657766', 153, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


----终端门户小编推荐自动上架规则，需要修改cid17657767

insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (157, '终端门户小编推荐', 0, 0, 1, null, 100);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (157, '', 10, 'c.RECOMMEND_GRADE is not null', '', -1, null);

---精品库
insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (157, '18147540', 1, '', 'sortID desc,marketDate desc', 10, null);

insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('18147539', 157, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));




--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.097_SSMS','MM1.0.0.118_SSMS');
commit;