create table t_gametype(
id number(12),
name varchar2(40),
mmid number(12),
mmname varchar2(40),
primary key(id)
);

-- Create table
create table T_GAME_CONTENT
(
  CONTENTCODE     VARCHAR2(32) not null,
  NAME            VARCHAR2(400) not null,
  SHORTNAME       VARCHAR2(400),
  DESCR           VARCHAR2(1000) not null,
  CPID            VARCHAR2(16) not null,
  CPNAME          VARCHAR2(200) not null,
  OPERA           VARCHAR2(1000),
  EFFECTIVEDATE   VARCHAR2(14),
  INVALIDDATE     VARCHAR2(14),
  LOGO            VARCHAR2(200) not null,
  DEVICEUA        VARCHAR2(400),
  GAMETYPEID      NUMBER(4) not null,
  FILESIZE        NUMBER(12),
  CONTENTTYPE     VARCHAR2(14) not null,
  PKGTYPE         NUMBER(1),
  GAMEPOOL        NUMBER(1),
  FREEDOWNLOADNUM NUMBER(9),
  CHARGETYPE      NUMBER(2) not null,
  SCALE           VARCHAR2(20),
  LUPDATE         DATE default sysdate,
  primary key(CONTENTCODE)
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
  SERVICESTATUS    NUMBER(1) not null,
  FEE              NUMBER(10) not null,
  SERVICEFEEDESC   VARCHAR2(400) not null,
  SERVICE_URL      VARCHAR2(400) not null,
  SERVICEFEETYPE   NUMBER(2) not null,
  GAMETYPE         NUMBER(4) not null,
  MMGAMETYPE       VARCHAR2(100) not null,
  GAMETYPE_DESC    VARCHAR2(40) not null,
  SERVICEFLAG      NUMBER(1),
  PTYPEID          NUMBER(2) not null,
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
  is '产品生效日期   业务生效日期，格式"yyyy-mm-dd"';
comment on column T_GAME_TW_NEW.SERVICEENDDATE
  is '产品失效日期   商用业务在失效日期后，自动变为下线业务。"yyyy-mm-dd"';
comment on column T_GAME_TW_NEW.SERVICESTATUS
  is '业务状态    1:待上传游戏内容,2:待测试,3:商用,4:暂停,5:待下线,0:已下线';
comment on column T_GAME_TW_NEW.FEE
  is '资费(厘)';
comment on column T_GAME_TW_NEW.SERVICEFEEDESC
  is '资费描述';
comment on column T_GAME_TW_NEW.SERVICE_URL
  is '入口URL（下载url）  图文游戏的链接地址。';
comment on column T_GAME_TW_NEW.SERVICEFEETYPE
  is '计费方式   0:免费,
1:包月,
2:客户端单机按次(首次使用时付费),
3:道具按次,
4:套餐按月,
5:下载时按次计费,
6:Wap按次（每次使用都计费）,
7:客户端单机短信计费,
8:客户端网游短信计费。
只给0、3、6的，
0：免费
3：图文网游
5：图文单机
';
comment on column T_GAME_TW_NEW.GAMETYPE
  is '游戏分类   游戏分类的可选值只能是以下的其中一个：
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
comment on column T_GAME_TW_NEW.GAMETYPE_DESC
  is '游戏分类名称';
comment on column T_GAME_TW_NEW.SERVICEFLAG
  is '0:普通业务,1:套餐内业务';
comment on column T_GAME_TW_NEW.PTYPEID
  is '业务推广方式';




create table T_GAME_PKG
(
  SERVICECODE     VARCHAR2(16) not null,
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
  primary key(SERVICECODE)
)
-- Add comments to the columns 
comment on column T_GAME_PKG.SERVICECODE
  is '业务代码';
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
  
create table T_GAME_PKG_REF
(
  PKGID       VARCHAR2(32) not null,
  SERVICECODE VARCHAR2(16) not null,
  primary key(PKGID,SERVICECODE)
);

comment on column T_GAME_PKG_REF.PKGID
  is '包标识ID';
comment on column T_GAME_PKG_REF.SERVICECODE
  is '包里面的内容ID';
  
create table T_GAME_SERVICE_NEW
(
  ICPCODE     VARCHAR2(16),
  SPNAME      VARCHAR2(200) not null,
  ICPSERVID   VARCHAR2(16) not null,
  SERVNAME    VARCHAR2(50) not null,
  SERVDESC    VARCHAR2(200),
  CHARGETYPE  VARCHAR2(2),
  CHARGEDESC  VARCHAR2(200),
  MOBILEPRICE NUMBER(8),
  LUPDDATE    DATE not null,
  SERVTYPE    NUMBER(2) not null,
  SERVFLAG    NUMBER(1),
  PTYPEID     NUMBER(2) not null,
  CONTENTID   VARCHAR2(30),
  primary key(ICPSERVID)
);

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
  is '业务标识。0:普通业务,1:套餐内业务';
comment on column T_GAME_SERVICE_NEW.PTYPEID
  is '支付方式。1:点数 2:话费';
comment on column T_GAME_SERVICE_NEW.CONTENTID
  is '内容id';
  

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
  LUPDATE      DATE default sysdate not null
);
-- Add comments to the columns 
comment on column t_gamestop.serviceCode
  is '业务代码/套餐包ID';
comment on column t_gamestop.pkgid
  is '包代码';
comment on column t_gamestop.contentCode
  is '内容代码';
comment on column t_gamestop.isStop
  is '是否暂停省测批开';
comment on column t_gamestop.provinceId
  is '被暂停省份的ID';
comment on column t_gamestop.provinceName
  is '被暂停省份名称';
comment on column t_gamestop.stopTime
  is '暂停时间';
comment on column t_gamestop.operateType
  is '操作类型';
comment on column t_gamestop.lupdate
  is '最后更新时间';
  
  
alter table t_cb_content modify  INFO_PIC varchar2(800);


-- Add/modify columns 
alter table T_RB_CATEGORY_NEW add BUSINESSTIME VARCHAR2(14);
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW.BUSINESSTIME
  is '上线时间';

-- Add/modify columns 
create table T_RB_COMMENT_NEW
(
  COMMENTID   VARCHAR2(60) not null,
  NICKNAME    VARCHAR2(60) not null,
  USERID      VARCHAR2(11) not null,
  COMMENTDESC VARCHAR2(4000) not null,
  TIME        VARCHAR2(14) not null
);

comment on column T_RB_COMMENT_NEW.COMMENTID
  is '评论ID';
comment on column T_RB_COMMENT_NEW.NICKNAME
  is '昵称';
comment on column T_RB_COMMENT_NEW.USERID
  is '用户标识 不带＋86手机号码';
comment on column T_RB_COMMENT_NEW.COMMENTDESC
  is '评论内容';
comment on column T_RB_COMMENT_NEW.TIME
  is '时间 格式：YYYYMMDDHH24MISS';

alter table T_RB_COMMENT_NEW
  add constraint PK_COMMENT_ID primary key (COMMENTID);

-- Add/modify columns 
alter table T_RB_AUTHOR_NEW modify ISORIGINAL null;
alter table T_RB_AUTHOR_NEW modify ISPUBLISH null;

-- Add/modify columns 
alter table T_RB_BOOK_NEW modify LONGRECOMMEND null;



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.089_SSMS','MM1.1.1.095_SSMS');


commit;