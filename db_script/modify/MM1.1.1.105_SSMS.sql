
--游戏开始
alter table t_game_tw_new add pkgid varchar2(32);
comment on column t_game_tw_new.pkgid;
  is '包标识';
alter table t_game_pkg drop column pkgurl;
alter table t_game_content add price number(10);

drop table T_GAME_PKG;

create table T_GAME_PKG
(
  SERVICECODE     VARCHAR2(16),
  PKGID           VARCHAR2(32) not null,
  PKGNAME         VARCHAR2(64) not null,
  PKGDESC         VARCHAR2(512) not null,
  CPNAME          VARCHAR2(64),
  FEE             NUMBER not null,
  PICURL1         VARCHAR2(255) not null,
  PICURL2         VARCHAR2(255) not null,
  PICURL3         VARCHAR2(255) not null,
  PICURL4         VARCHAR2(255) not null,
  UPDATETIME      DATE default sysdate,
  PROVINCECTROL   VARCHAR2(50),
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
  PKGREFNUM       NUMBER(10),
  primary key(PKGID)
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
comment on column T_GAME_PKG.PKGREFNUM
  is '该包下的商品数目';
  
--游戏结束

  
  
 -- Add/modify columns 
alter table T_RB_CATEGORY_NEW add DELFLAG NUMBER(2) default 0;
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW.DELFLAG
  is '删除标志位（0：正常，1：删除）';
  
  
-- Add/modify columns 
alter table T_VO_PRODUCT add freeType VARCHAR2(4);
alter table T_VO_PRODUCT add freeEffecTime VARCHAR2(14);
alter table T_VO_PRODUCT add freeTimeFail VARCHAR2(14);
-- Add comments to the columns 
comment on column T_VO_PRODUCT.freeType
  is '免费属性 0-无免费体验 1-新产品 2-初次订购';
comment on column T_VO_PRODUCT.freeEffecTime
  is '免费体验生效时间';
comment on column T_VO_PRODUCT.freeTimeFail
  is '免费体验失效时间';

  

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.098_SSMS','MM1.1.1.105_SSMS');


commit;