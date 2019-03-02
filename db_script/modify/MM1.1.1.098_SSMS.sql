--动漫开始;

delete from t_cb_category c where c.parentcategoryid='100000001';
delete from t_cb_category c where c.parentcategoryid='100000002';

insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000020', '搞笑', '100000001', null, null, '0', null, null, null, null, '15000');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000021', '冒险', '100000001', null, null, '0', null, null, null, null, '15001');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000022', '运动', '100000001', null, null, '0', null, null, null, null, '15002');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000023', '情感', '100000001', null, null, '0', null, null, null, null, '15003');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000024', '科幻', '100000001', null, null, '0', null, null, null, null, '15004');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000025', '悬疑', '100000001', null, null, '0', null, null, null, null, '15005');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000026', '国学', '100000001', null, null, '0', null, null, null, null, '15006');

insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000027', '校园', '100000001', null, null, '0', null, null, null, null, '15012');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000028', '恐怖', '100000001', null, null, '0', null, null, null, null, '15013');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000029', '经典', '100000001', null, null, '0', null, null, null, null, '15014');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000030', '恋爱', '100000001', null, null, '0', null, null, null, null, '15015');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000031', '战争', '100000001', null, null, '0', null, null, null, null, '15016');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000032', '热血', '100000001', null, null, '0', null, null, null, null, '15017');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000033', '宫廷', '100000001', null, null, '0', null, null, null, null, '15018');

insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000034', '穿越', '100000001', null, null, '0', null, null, null, null, '15019');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000035', '武侠', '100000001', null, null, '0', null, null, null, null, '15020');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000036', '玄幻', '100000001', null, null, '0', null, null, null, null, '15021');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000037', '宅男', '100000001', null, null, '0', null, null, null, null, '15022');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000038', '腐女', '100000001', null, null, '0', null, null, null, null, '15023');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000039', '都市', '100000001', null, null, '0', null, null, null, null, '15024');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000040', '萌系', '100000001', null, null, '0', null, null, null, null, '15025');



insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000050', '搞笑', '100000002', null, null, '0', null, null, null, null, '15400');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000051', '冒险', '100000002', null, null, '0', null, null, null, null, '15401');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000052', '国学', '100000002', null, null, '0', null, null, null, null, '15406');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000053', '亲子', '100000002', null, null, '0', null, null, null, null, '15407');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000054', '经典', '100000002', null, null, '0', null, null, null, null, '15410');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000055', '校园', '100000002', null, null, '0', null, null, null, null, '15411');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000056', '武侠', '100000002', null, null, '0', null, null, null, null, '15414');

insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000057', '情感', '100000002', null, null, '0', null, null, null, null, '15415');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000058', '运动', '100000002', null, null, '0', null, null, null, null, '15416');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000059', '战争', '100000002', null, null, '0', null, null, null, null, '15417');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000060', '热血', '100000002', null, null, '0', null, null, null, null, '15418');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000061', '都市', '100000002', null, null, '0', null, null, null, null, '15419');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000062', '科幻', '100000002', null, null, '0', null, null, null, null, '15420');
insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000063', '萌系', '100000002', null, null, '0', null, null, null, null, '15421');


insert into T_CB_CATEGORY (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, PICTURE, DELFLAG, CATEGORYDESC, SORTID, CREATETIME, LUPDATE, CATEGORYVALUE)
values ('100000064', '资讯', '100000002', null, null, '0', null, null, null, null, '15422');

--动漫结束



--游戏开始

drop table T_GAMESTOP;

-- Create table
create table T_GAMESTOP
(
  SERVICECODE  VARCHAR2(16) not null,
  PKGID        VARCHAR2(32),
  CONTENTCODE  VARCHAR2(32) not null,
  ISSTOP       NUMBER(1) not null,
  PROVINCEID   VARCHAR2(12) not null,
  PROVINCENAME VARCHAR2(21) not null,
  STOPTIME     VARCHAR2(14),
  OPERATETYPE  NUMBER(2) not null,
  LUPDATE      DATE default sysdate not null,
  STATUS       NUMBER(1) default 1 not null
);
-- Add comments to the columns 
comment on column T_GAMESTOP.SERVICECODE
  is '业务代码/套餐包ID';
comment on column T_GAMESTOP.PKGID
  is '包代码';
comment on column T_GAMESTOP.CONTENTCODE
  is '内容代码';
comment on column T_GAMESTOP.ISSTOP
  is '是否暂停省测批开';
comment on column T_GAMESTOP.PROVINCEID
  is '被暂停省份的ID';
comment on column T_GAMESTOP.PROVINCENAME
  is '被暂停省份名称';
comment on column T_GAMESTOP.STOPTIME
  is '暂停时间';
comment on column T_GAMESTOP.OPERATETYPE
  is '操作类型';
comment on column T_GAMESTOP.LUPDATE
  is '最后更新时间';
comment on column T_GAMESTOP.STATUS
  is '0：有效数据，1：货架正在同步的数据，暂时无效';
  

drop table T_GAME_CONTENT;  
-- Create table
create table T_GAME_CONTENT
(
  CONTENTCODE           VARCHAR2(32) not null,
  NAME                  VARCHAR2(400) not null,
  SHORTNAME             VARCHAR2(400),
  DESCR                 VARCHAR2(1000) not null,
  CPID                  VARCHAR2(16) not null,
  CPNAME                VARCHAR2(200) not null,
  OPERA                 VARCHAR2(1000),
  EFFECTIVEDATE         VARCHAR2(14),
  INVALIDDATE           VARCHAR2(14),
  LOGO                  VARCHAR2(200) not null,
  DEVICEUA              VARCHAR2(400),
  GAMETYPEID            NUMBER(4),
  FILESIZE              NUMBER(12),
  CONTENTTYPE           VARCHAR2(14) not null,
  PKGTYPE               NUMBER(1),
  GAMEPOOL              NUMBER(1),
  FREEDOWNLOADNUM       NUMBER(9),
  CHARGETYPE            NUMBER(2) not null,
  SCALE                 VARCHAR2(20),
  LUPDATE               DATE default sysdate,
  STATUS                NUMBER(1) default 1,
  LOGO1                 VARCHAR2(256),
  LOGO2                 VARCHAR2(256),
  LOGO3                 VARCHAR2(256),
  LOGO4                 VARCHAR2(256),
  WWWPROPAPICTURE1      VARCHAR2(256),
  WWWPROPAPICTURE2      VARCHAR2(256),
  WWWPROPAPICTURE3      VARCHAR2(256),
  CLIENTPREVIEWPICTURE1 VARCHAR2(256),
  CLIENTPREVIEWPICTURE2 VARCHAR2(256),
  CLIENTPREVIEWPICTURE3 VARCHAR2(256),
  CLIENTPREVIEWPICTURE4 VARCHAR2(256),
  PICTURE1              VARCHAR2(256),
  PICTURE2              VARCHAR2(256),
  PICTURE3              VARCHAR2(256),
  PICTURE4              VARCHAR2(256),
  PICTURE5              VARCHAR2(256),
  FULLDEVICEID          CLOB,
  BRAND                 VARCHAR2(4000),
  FULLDEVICENAME        CLOB
);

-- Add comments to the columns 
comment on column T_GAME_CONTENT.CONTENTCODE
  is '内容代码/套餐包';
comment on column T_GAME_CONTENT.NAME
  is '内容名称';
comment on column T_GAME_CONTENT.SHORTNAME
  is '内容简称';
comment on column T_GAME_CONTENT.DESCR
  is '内容简介';
comment on column T_GAME_CONTENT.CPID
  is 'CP代码';
comment on column T_GAME_CONTENT.CPNAME
  is 'CP名称';
comment on column T_GAME_CONTENT.OPERA
  is '操作简介';
comment on column T_GAME_CONTENT.EFFECTIVEDATE
  is '产品生效日期';
comment on column T_GAME_CONTENT.INVALIDDATE
  is '产品失效日期';
comment on column T_GAME_CONTENT.LOGO
  is '产品LOGO';
comment on column T_GAME_CONTENT.DEVICEUA
  is '支持的终端类型';
comment on column T_GAME_CONTENT.GAMETYPEID
  is '游戏分类标识';
comment on column T_GAME_CONTENT.FILESIZE
  is '文件大小';
comment on column T_GAME_CONTENT.CONTENTTYPE
  is '内容类型';
comment on column T_GAME_CONTENT.PKGTYPE
  is '套餐业务类型';
comment on column T_GAME_CONTENT.GAMEPOOL
  is '是否启用游戏池';
comment on column T_GAME_CONTENT.FREEDOWNLOADNUM
  is '用户免费下载次数';
comment on column T_GAME_CONTENT.CHARGETYPE
  is '操作类型';
comment on column T_GAME_CONTENT.SCALE
  is '分成比例';
comment on column T_GAME_CONTENT.LUPDATE
  is '入库时间';
comment on column T_GAME_CONTENT.STATUS
  is '0：有效数据，1：正在同步中的数据';
  
drop table T_GAME_TW_NEW;
-- Create table
create table T_GAME_TW_NEW
(
  CPID             VARCHAR2(16) not null,
  CPNAME           VARCHAR2(200) not null,
  CPSERVICEID      VARCHAR2(16) not null,
  SERVICENAME      VARCHAR2(400) not null,
  SERVICESHORTNAME VARCHAR2(400) not null,
  SERVICEDESC      VARCHAR2(1000),
  OPERATIONDESC    VARCHAR2(1000),
  SERVICETYPE      NUMBER(2) not null,
  SERVICEPAYTYPE   NUMBER(2) not null,
  SERVICESTARTDATE VARCHAR2(10) not null,
  SERVICEENDDATE   VARCHAR2(10) not null,
  FEE              NUMBER(10) not null,
  SERVICE_URL      VARCHAR2(400),
  SERVICEFEETYPE   NUMBER(2) not null,
  GAMETYPE         NUMBER(4) not null,
  MMGAMETYPE       VARCHAR2(100) not null,
  GAMETYPE_DESC    VARCHAR2(40) not null,
  SERVICEFLAG      NUMBER(1),
  FEETYPE          VARCHAR2(14),
  FIRSTTYPE        VARCHAR2(5),
  OLDPRICE         NUMBER(10),
  primary key(CPSERVICEID)
);

-- Add comments to the columns 
comment on column T_GAME_TW_NEW.CPID
  is 'CP的合作代码';
comment on column T_GAME_TW_NEW.CPNAME
  is 'CP名称';
comment on column T_GAME_TW_NEW.CPSERVICEID
  is '产品的业务代码';
comment on column T_GAME_TW_NEW.SERVICENAME
  is '产品名称';
comment on column T_GAME_TW_NEW.SERVICESHORTNAME
  is '产品简称';
comment on column T_GAME_TW_NEW.SERVICEDESC
  is '产品简介';
comment on column T_GAME_TW_NEW.OPERATIONDESC
  is '操作简介';
comment on column T_GAME_TW_NEW.SERVICETYPE
  is '业务类型     1:客户端单机,2:客户端网游,3:WAP网游,4:WAP单机';
comment on column T_GAME_TW_NEW.SERVICEPAYTYPE
  is '支付方式      1:点数,2:话费';
comment on column T_GAME_TW_NEW.SERVICESTARTDATE
  is '产品生效日期   格式:YYYYMMDDHH24MISS';
comment on column T_GAME_TW_NEW.SERVICEENDDATE
  is '产品失效日期   格式:YYYYMMDDHH24MISS';
comment on column T_GAME_TW_NEW.FEE
  is '资费(分)';
comment on column T_GAME_TW_NEW.SERVICE_URL
  is '入口URL（下载url）  图文游戏的链接地址。';
comment on column T_GAME_TW_NEW.SERVICEFEETYPE
  is '计费方式 1：免费
2：按次/按条计费
3：包月计费
5：包次计费
7：包天计费
9：按栏目包月计费
';
comment on column T_GAME_TW_NEW.GAMETYPE
  is '游戏基地分类   游戏分类的可选值只能是以下的其中一个：
MMORPG(网游)
竞速
动作冒险
文字冒险
音乐
动作
棋牌
策略
体育
角色扮演
射击
益智
动作益智
';
comment on column T_GAME_TW_NEW.MMGAMETYPE
  is '游戏分类MM名称';
comment on column T_GAME_TW_NEW.GAMETYPE_DESC
  is '游戏分类基地名称';
comment on column T_GAME_TW_NEW.SERVICEFLAG
  is '折扣类型 0：全价业务
1：打折业务
2：普通业务
';
comment on column T_GAME_TW_NEW.FEETYPE
  is '资费类型:
1 包外价格；2 包内价格
';
comment on column T_GAME_TW_NEW.FIRSTTYPE
  is '首发类型 1:当月首发
2:历史首发
99:其他
';
comment on column T_GAME_TW_NEW.OLDPRICE
  is '原价（分）';
  
  
drop table   T_GAME_PKG;

-- Create table
create table T_GAME_PKG
(
  SERVICECODE     VARCHAR2(16) not null,
  PKGID           VARCHAR2(32) not null,
  PKGNAME         VARCHAR2(64) not null,
  PKGDESC         VARCHAR2(512) not null,
  CPNAME          VARCHAR2(64),
  FEE             NUMBER not null,
  PKGURL          VARCHAR2(300) not null,
  PICURL1         VARCHAR2(255) not null,
  PICURL2         VARCHAR2(255) not null,
  PICURL3         VARCHAR2(255) not null,
  PICURL4         VARCHAR2(255) not null,
  UPDATETIME      DATE default sysdate,
  PROVINCECTROL   VARCHAR2(50) not null,
  PKGTYPE         NUMBER(1),
  GAMEPOOL        NUMBER(1),
  FREEDOWNLOADNUM NUMBER(9),
  SERVICENAME     VARCHAR2(400),
  CPID            VARCHAR2(16),
  SERVICETYPE     NUMBER(2),
  PAYTYPE         NUMBER(2),
  OLDPRICE        NUMBER(12),
  FEETYPE         VARCHAR2(14),
  BILLTYPE        VARCHAR2(2),
  FIRSTTYPE       VARCHAR2(5),
  DISCOUNTTYPE    NUMBER(1),
  FULLDEVICEID    CLOB,
  FULLDEVICENAME  CLOB,
  primary key(SERVICECODE)
);
-- Add comments to the columns 
comment on column T_GAME_PKG.SERVICECODE
  is '业务代码(包的业务代码)';
comment on column T_GAME_PKG.PKGID
  is '包标识';
comment on column T_GAME_PKG.PKGNAME
  is '游戏包名称';
comment on column T_GAME_PKG.PKGDESC
  is '游戏包介绍';
comment on column T_GAME_PKG.CPNAME
  is '发行商';
comment on column T_GAME_PKG.FEE
  is '费率';
comment on column T_GAME_PKG.PKGURL
  is '游戏包入口URL';
comment on column T_GAME_PKG.PICURL1
  is '规格 30x30 picture1';
comment on column T_GAME_PKG.PICURL2
  is '规格 34x34 picture2';
comment on column T_GAME_PKG.PICURL3
  is '规格 50x50 picture3';
comment on column T_GAME_PKG.PICURL4
  is '规格 65x65 picture4';
comment on column T_GAME_PKG.PROVINCECTROL
  is '分成比例';
comment on column T_GAME_PKG.PKGTYPE
  is '套餐业务类型 1：游戏玩家
2：独立包
3：其他包
';
comment on column T_GAME_PKG.GAMEPOOL
  is '针对套餐包 0:启用
1：不启用
';
comment on column T_GAME_PKG.FREEDOWNLOADNUM
  is '对于启用游戏池中的业务免费下载次数';
comment on column T_GAME_PKG.SERVICENAME
  is '业务名称（计费业务名称）';
comment on column T_GAME_PKG.CPID
  is 'CPid';
comment on column T_GAME_PKG.SERVICETYPE
  is '业务类型';
comment on column T_GAME_PKG.PAYTYPE
  is '支付方式';
comment on column T_GAME_PKG.OLDPRICE
  is '原价资费';
comment on column T_GAME_PKG.FEETYPE
  is '资费类型';
comment on column T_GAME_PKG.BILLTYPE
  is '计费类型';
comment on column T_GAME_PKG.FIRSTTYPE
  is '首发类型';
comment on column T_GAME_PKG.DISCOUNTTYPE
  is '折扣类型';
comment on column T_GAME_PKG.FULLDEVICEID
  is '包适配机型ID';
comment on column T_GAME_PKG.FULLDEVICENAME
  is '包适配机型NAME';



-- Create table
create table T_GAME_STATISTCS
(
  SERVICECODE  VARCHAR2(16) not null,
  DOWNLOADNUM  NUMBER(10),
  LOGINNUM     NUMBER(10),
  BROADCASTNUM NUMBER(10),
  PAYNUM       NUMBER(10),
  GAMELEVEL    NUMBER(10),
  COMMENTNUM   NUMBER(10),
  SCORE        NUMBER(10),
  STARTTIME    VARCHAR2(14) not null,
  STARTNUM     NUMBER(10),
  status       number(1)
);
-- Add comments to the columns 
comment on column T_GAME_STATISTCS.SERVICECODE
  is '计费业务代码';
comment on column T_GAME_STATISTCS.DOWNLOADNUM
  is '统计MM渠道的下载量';
comment on column T_GAME_STATISTCS.LOGINNUM
  is '统计MM渠道的登录次数';
comment on column T_GAME_STATISTCS.BROADCASTNUM
  is '统计MM渠道的点播次数';
comment on column T_GAME_STATISTCS.PAYNUM
  is '统计MM渠道的付费次数';
comment on column T_GAME_STATISTCS.GAMELEVEL
  is '星级,分5星';
comment on column T_GAME_STATISTCS.COMMENTNUM
  is '评论数';
comment on column T_GAME_STATISTCS.SCORE
  is '分数';
comment on column T_GAME_STATISTCS.STARTTIME
  is '上线时间';
comment on column T_GAME_STATISTCS.STARTNUM
  is '上线天数';
  
comment on column T_GAME_STATISTCS.STATUS
  is '0：有效数据，1：正在同步中的数据';
  

drop table  T_GAME_SERVICE_NEW
-- Create table
create table T_GAME_SERVICE_NEW
(
  ICPCODE     VARCHAR2(16),
  SPNAME      VARCHAR2(200) not null,
  ICPSERVID   VARCHAR2(16) not null,
  SERVNAME    VARCHAR2(50) not null,
  SERVDESC    VARCHAR2(1000),
  CHARGETYPE  VARCHAR2(2),
  CHARGEDESC  VARCHAR2(200),
  MOBILEPRICE NUMBER(8),
  LUPDDATE    DATE not null,
  SERVTYPE    VARCHAR2(2) not null,
  SERVFLAG    NUMBER(1),
  PTYPEID     NUMBER(2) not null,
  CONTENTID   VARCHAR2(30),
  OLDPRICE    NUMBER(8),
  FIRSTTYPE   VARCHAR2(5),
  CONTENTTAG  VARCHAR2(32)
);
-- Add comments to the columns 
comment on column T_GAME_SERVICE_NEW.ICPCODE
  is 'CP的合作代码';
comment on column T_GAME_SERVICE_NEW.SPNAME
  is 'CP名称';
comment on column T_GAME_SERVICE_NEW.ICPSERVID
  is '产品的业务代码';
comment on column T_GAME_SERVICE_NEW.SERVNAME
  is '产品名称';
comment on column T_GAME_SERVICE_NEW.SERVDESC
  is '产品简介';
comment on column T_GAME_SERVICE_NEW.CHARGETYPE
  is '计费类型

1：免费
2：按次/按条计费
3：包月计费
5：包次计费
7：包天计费
9：按栏目包月计费
';
comment on column T_GAME_SERVICE_NEW.CHARGEDESC
  is '资费类型  1 包外价格；2 包内价格';
comment on column T_GAME_SERVICE_NEW.MOBILEPRICE
  is '单位:分 （与业务代码关联）';
comment on column T_GAME_SERVICE_NEW.LUPDDATE
  is '业务最后更新时间';
comment on column T_GAME_SERVICE_NEW.SERVTYPE
  is '业务类型。1:客户端单机,2:客户端网游,3:WAP网游,4:WAP单机
只给1客户端单机的。
';
comment on column T_GAME_SERVICE_NEW.SERVFLAG
  is '折扣类型 0：全价业务
1：打折业务
2：普通业务
';
comment on column T_GAME_SERVICE_NEW.PTYPEID
  is '支付方式。1:点数 2:话费';
comment on column T_GAME_SERVICE_NEW.CONTENTID
  is '内容id';
comment on column T_GAME_SERVICE_NEW.OLDPRICE
  is '原价（单位：分）';
comment on column T_GAME_SERVICE_NEW.FIRSTTYPE
  is '首发类型1:当月首发
2:历史首发
99:其他
';
comment on column T_GAME_SERVICE_NEW.CONTENTTAG
  is '记录该业务的原始内容ID（货架辅助字段）';

--游戏结束


-- Create table
create table T_exigence_Lasttime
(
  Lasttime date not null
)
;
-- Add comments to the columns 
comment on column T_EXIGENCE_LASTTIME.LASTTIME
  is '自动更新自动任务最后获取内容时间';


-- Create table
create table t_content_whitelist
(
  COMPANYID VARCHAR2(20) not null
)
;
-- Add comments to the columns 
comment on column T_CONTENT_WHITELIST.COMPANYID
  is '白名单companyid';


-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_UPGRADE
  for V_CM_CONTENT_UPGRADE@DL_PPMS_DEVICE;

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (50, '从报表提供移动生活飙升排行数据', 'select b.id from t_r_base b, t_r_gcontent g, v_service v, t_content_count c,t_portal_down_d d,v_content_last l where l.contentid = g.contentid and b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = d.content_id and g.contentid = c.contentid(+) and g.provider != ''B'' and d.PORTAL_ID=10 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');


insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '移动生活飙升_ophone', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('移动生活飙升_ophone', null, 50, 'g.icpcode in (''100246'',''100066'',''135500'',''135501'',''135429'',''135436'',''135428'',''135434'',''135427'',''135435'',''135502'',''135217'',''135405'',''135378'',''135424'',''135425'',''135439'',''135404'',''135423'',''135406'',''135407'',''135408'',''135403'',''135431'',''135426'',''135433'',''135432'',''135430'',''135503'',''135504'',''135505'',''135535'',''135506'',''135507'',''135508'',''135509'',''135510'',''135511'',''135512'',''135513'',''135514'',''135515'',''135516'',''135517'',''135518'',''135536'',''135519'',''135520'',''135521'',''135522'',''135523'',''136070'',''135524'',''135525'',''135526'')  and l.osid = ''3''', 'd.add_7days_order_count desc,c.RECOMMENDCOUNT desc nulls last', -1, 1, SEQ_CATERULE_COND_ID.nextval, 50);

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '移动生活飙升_android', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('移动生活飙升_android', null, 50, 'g.icpcode in (''100246'',''100066'',''135500'',''135501'',''135429'',''135436'',''135428'',''135434'',''135427'',''135435'',''135502'',''135217'',''135405'',''135378'',''135424'',''135425'',''135439'',''135404'',''135423'',''135406'',''135407'',''135408'',''135403'',''135431'',''135426'',''135433'',''135432'',''135430'',''135503'',''135504'',''135505'',''135535'',''135506'',''135507'',''135508'',''135509'',''135510'',''135511'',''135512'',''135513'',''135514'',''135515'',''135516'',''135517'',''135518'',''135536'',''135519'',''135520'',''135521'',''135522'',''135523'',''136070'',''135524'',''135525'',''135526'')  and l.osid = ''9''', 'd.add_7days_order_count desc,c.RECOMMENDCOUNT desc nulls last', -1, 1, SEQ_CATERULE_COND_ID.nextval, 50);


-- Create table
create table t_rb_moDirectory_new
(
  MoDirectoryId varchar2(20) not null,
  cateId        varchar2(64) not null,
  lastTime      date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_rb_moDirectory_new.MoDirectoryId
  is '终端目录标识';
comment on column t_rb_moDirectory_new.cateId
  is '专区标识';
comment on column t_rb_moDirectory_new.lastTime
  is '最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_rb_moDirectory_new
  add constraint k_tp_moDir primary key (MODIRECTORYID);

drop table T_RB_COMMENT_NEW;
drop table t_rb_bookcontent_new;

create or replace synonym REPORT_COMMENT
  for TR_BOOK_COMMENT@REPORT105.ORACLE.COM;

create table t_rb_comment_new as select * from REPORT_COMMENT where 1=2;
  
create table t_rb_comment_new_tra as select * from REPORT_COMMENT where 1=2;


-----------------------------------------报表中基地阅读评论数据同步存储过程
create or replace procedure P_REPORT_COMMENT as
 v_nstatus number;--纪录监控包状态
  v_sql_f varchar2(1200);
    v_nindnum    number;--记录数据是否存在
      v_nrecod      number;
begin
v_nstatus:=pg_log_manage.f_startlog('P_REPORT_COMMENT','报表中基地阅读评论数据同步');
  --清空结果历史表数据

  execute immediate 'truncate table t_rb_comment_new_tra';
  v_sql_f := 'insert into t_rb_comment_new_tra
          select * from REPORT_COMMENT t where t.file_time = to_char(sysdate-1,''yyyymmdd'')';
  execute immediate v_sql_f;
  v_nrecod:=SQL%ROWCOUNT;

  select count(9) into v_nindnum from t_rb_comment_new_tra ;
  if v_nindnum>0 then
        --如果不为空，将切换表
    execute  immediate   'alter table t_rb_comment_new rename to t_rb_comment_new_bak';
    execute  immediate   'alter table t_rb_comment_new_tra rename to t_rb_comment_new';
    execute  immediate   'alter table t_rb_comment_new_bak rename to t_rb_comment_new_tra';
    commit;
  else
     raise_application_error(-20088,'报表提供数据为空');
  end if;

  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
      --如果失败，将执行情况写入日志
      v_nstatus:=pg_log_manage.f_errorlog;
end;



----------------------------------------job定时任务
variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'P_REPORT_COMMENT;',
                      next_date => to_date('27-09-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.095_SSMS','MM1.1.1.098_SSMS');


commit;