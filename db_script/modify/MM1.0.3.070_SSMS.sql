

insert into t_right (rightid,name,descs,parentid,levels) values('1_0811_PIVOT_DEVICE','重点机型管理','重点机型管理','2_0801_RESOURCE','0');
insert into t_right (rightid,name,descs,parentid,levels) values('1_0812_PIVOT_CONTENT','重点内容管理','重点内容管理','2_0801_RESOURCE','0');

insert into t_roleright (roleid,rightid) values(1,'1_0811_PIVOT_DEVICE');
insert into t_roleright (roleid,rightid) values(1,'1_0812_PIVOT_CONTENT');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.065_SSMS','MM1.0.3.070_SSMS');
commit;

------------------------------------------------
-- 注以下所有脚本已在现网执行过。现网不需要执行
----------------------------------------------
create table t_pivot_device
(
  DEVICE_ID   NUMBER(8) not null,
  DEVICE_NAME VARCHAR2(100) not null,
  BRAND_NAME  VARCHAR2(100),
  OS_NAME     VARCHAR2(100),
  CREDATE     DATE default sysdate
)
;
-- Add comments to the columns 
comment on column t_pivot_device.DEVICE_ID
  is '重点机型id';
comment on column t_pivot_device.DEVICE_NAME
  is '重点机型名称';
comment on column t_pivot_device.BRAND_NAME
  is '品牌名称';
comment on column t_pivot_device.OS_NAME
  is '开发平台';
comment on column T_PIVOT_DEVICE.CREDATE
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_pivot_device
  add constraint PK_t_pivot_device primary key (DEVICE_ID);


-- Create table
create table T_PIVOT_CONTENT
(
  content_id   VARCHAR2(30) not null,
  content_name VARCHAR2(300) not null,
  ap_id        VARCHAR2(20),
  ap_name      VARCHAR2(100),
  CREDATE     DATE default sysdate
)
;
-- Add comments to the columns 
comment on column T_PIVOT_CONTENT.content_id
  is '内容编码';
comment on column T_PIVOT_CONTENT.content_name
  is '内容名称，对应资讯内容的媒体名称';
comment on column T_PIVOT_CONTENT.ap_id
  is '企业代码';
comment on column T_PIVOT_CONTENT.ap_name
  is '企业名称';
comment on column T_PIVOT_CONTENT.credate
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_PIVOT_CONTENT
  add constraint pk_T_PIVOT_CONTENT primary key (CONTENT_ID);


-- 视频榜中榜数据制作
alter table T_VB_VIDEO add type VARCHAR2(10);
-- Add comments to the columns 
comment on column T_VB_VIDEO.type
  is '类型：1,视频包;2,视频广告;3,视频节目';
  
-- Add/modify columns 
alter table T_VB_VIDEO add videoUrl VARCHAR2(255);
-- Add comments to the columns 
comment on column T_VB_VIDEO.videoUrl
  is '视频URL';



  -- Create table
create table T_VB_CATEGORY
(
  CATEGORYID       VARCHAR2(30) not null,
  CATEGORYNAME     VARCHAR2(200) not null,
  PARENTCATEGORYID VARCHAR2(30),
  TYPE             VARCHAR2(10),
  DELFLAG          VARCHAR2(2),
  CREATETIME       date,
  LUPDDATE         date,
  CATEGORYDESC     VARCHAR2(1000),
  SORTID           NUMBER(8),
  SUM              NUMBER(8) default 0 not null,
  ALBUM_SINGER     VARCHAR2(100),
  PLATFORM         VARCHAR2(200) default '{0000}',
  CITYID           VARCHAR2(4000) default '{0000}'
);
-- Add comments to the columns 
comment on column T_VB_CATEGORY.CATEGORYID
  is '货架ID';
comment on column T_VB_CATEGORY.CATEGORYNAME
  is '货架名称';
comment on column T_VB_CATEGORY.PARENTCATEGORYID
  is '货架父ID';
comment on column T_VB_CATEGORY.TYPE
  is '是否在门户展示 1：是 0：否';
comment on column T_VB_CATEGORY.DELFLAG
  is '删除标记';
comment on column T_VB_CATEGORY.CREATETIME
  is '创建时间';
comment on column T_VB_CATEGORY.CATEGORYDESC
  is '货架描述';
comment on column T_VB_CATEGORY.SORTID
  is '货架排序';
comment on column T_VB_CATEGORY.SUM
  is '货架商品数量';
comment on column T_VB_CATEGORY.PLATFORM
  is '平台适配关系';
comment on column T_VB_CATEGORY.CITYID
  is '地市适配关系';
-- Create/Recreate indexes 
create unique index PK_T_VB_CATEGORY_NEW_ID on T_VB_CATEGORY (CATEGORYID);
-- Grant/Revoke object privileges 
grant select on T_VB_CATEGORY to portalmo with grant option;


-- Create table
create table T_VB_REFERENCE
(
  VIDEOID    VARCHAR2(30) not null,
  CATEGORYID VARCHAR2(30) not null,
  CREATETIME DATE,
  SORTID     NUMBER(8)
);
-- Add comments to the columns 
comment on column T_VB_REFERENCE.VIDEOID
  is '音乐ID';
comment on column T_VB_REFERENCE.CATEGORYID
  is '货架ID';
comment on column T_VB_REFERENCE.CREATETIME
  is '创建时间';
comment on column T_VB_REFERENCE.SORTID
  is '排序';
-- Create/Recreate indexes 
create index INDEX_T_VB_REFERENCE_CAID on T_VB_REFERENCE (CATEGORYID);
create unique index PK_T_VB_REF_CATEID on T_VB_REFERENCE (VIDEOID, CATEGORYID);
-- Grant/Revoke object privileges 

grant select on T_VB_REFERENCE to portalmo with grant option;


-- Create sequence 
create sequence SEQ_vB_CATEGORY_ID
minvalue 100000001
maxvalue 999999999
start with 100000001
increment by 1
nocache
cycle;


update t_vb_video t set  t.type='1';


insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035161148', '大爱至上！高贵空姐舍己为人', 0, to_date('25-07-2011 16:08:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:08:46', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30309814'||chr(38)||'iContentId=500005912'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035168255', '魔术师失手砍下模特头', 0, to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:00:59', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30306049'||chr(38)||'iContentId=500009489'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035090592', '《硬汉2奉陪到底》上', 0, to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:00:59', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30303330'||chr(38)||'iContentId=37452018'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035130415', '《倩女幽魂》上', 0, to_date('25-07-2011 00:59:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30303330'||chr(38)||'iContentId=37464945'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035016416', '《画皮》第01集', 0, to_date('25-07-2011 00:01:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30309753'||chr(38)||'iContentId=37439575'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035143444', '实拍蒙面刑警押药家鑫赴刑场', 0, to_date('25-07-2011 00:00:59', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 12:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30198228'||chr(38)||'iContentId=37467993'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035115967', '《功夫熊猫》上', 0, to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:00:59', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30303105'||chr(38)||'iContentId=37464876'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035160452', '甄子丹新片《武侠》抢先看', 0, to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:01:00', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30010574'||chr(38)||'iContentId=500003231'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035184525', '越南妄图用战争拖垮中国', 0, to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:01:00', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30198235'||chr(38)||'iContentId=500020772'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035151625', '高考改革将打破一考定终身', 0, to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.101.123/mmarket/compile.msp?iNodeId=30306598'||chr(38)||'iContentId=37470976'||chr(38)||'channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035148846', '监控实拍老奶奶拐卖孩子', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n52071d2c11073876.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2036002873', '《裸婚时代》第1集', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/n10053213d2c500067142.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035190056', '整容惹祸！媳妇是猛男', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n10032742d2c500024991.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035222992', '揭秘美军航母的七大致命克星', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n10034922d2c500053534.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035217733', '谢霆锋含泪承认婚变', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n10022828d2c500049094.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2036000158', '台制航母杀手试射脱靶', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n86262d2c500062611.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035086996', '花儿到哪儿去了', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n10045772d2c11016896.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035218262', '变态母亲用微波炉烤死女儿', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n52071d2c500049755.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035221488', '未成年涉毒一半是女孩', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n52955d2c500052600.jsp?channelid=0_10030001006', '2');

insert into t_vb_video (PKGID, PKGNAME, FEE, CREATEDATE, LUPDATE, VIDEOURL, TYPE)
values ('2035218956', '男子为生儿子37年不洗澡', 0, to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-07-2011 16:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'http://211.136.165.53/wap/n120428d2c500050209.jsp?channelid=0_10030001006', '2');


declare 
wapcid varchar2(30);
mocid varchar2(30);
begin
 select SEQ_vB_CATEGORY_ID.Nextval into wapcid  from dual;
 select SEQ_vB_CATEGORY_ID.Nextval into mocid   from dual;

insert into t_vb_category
  (categoryid, categoryname, parentcategoryid, type, delflag,CREATETIME,LUPDDATE) values
  (wapcid,'wap榜中榜视频专区',null,'1','0',sysdate,sysdate);


insert into t_vb_category
  (categoryid, categoryname, parentcategoryid, type, delflag,CREATETIME,LUPDDATE) values
  (mocid,'终端榜中榜视频专区',null,'1','0',sysdate,sysdate);



insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035161148', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 1);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035168255', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035090592', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 3);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035130415', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 4);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035016416', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 5);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035143444', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 6);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035115967', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 7);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035160452', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 8);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035184525', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 9);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035151625', mocid, to_date('25-07-2011 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), 10);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035148846', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 1);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2036002873', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035190056', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 3);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035222992', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 4);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035217733', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 5);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2036000158', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 6);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035086996', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 7);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035218262', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 8);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035221488', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 9);

insert into t_vb_reference (VIDEOID, CATEGORYID, CREATETIME, SORTID)
values ('2035218956', wapcid, to_date('25-07-2011 16:04:13', 'dd-mm-yyyy hh24:mi:ss'), 10);

commit;
end;





