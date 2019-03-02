--.1.table

create table T_USER
(
  USERID      VARCHAR2(20) not null,
  NAME        VARCHAR2(30),
  SEX         VARCHAR2(1),
  PASSWORD    VARCHAR2(32) not null,
  BIRTHDAY    VARCHAR2(10),
  CERTTYPE    VARCHAR2(2),
  CERTID      VARCHAR2(30),
  COMPANYNAME VARCHAR2(100),
  COMPANYADDR VARCHAR2(160),
  POSTCODE    VARCHAR2(10),
  PHONE       VARCHAR2(40),
  MOBILE      VARCHAR2(16),
  EMAIL       VARCHAR2(80),
  QQ          VARCHAR2(15),
  MSN         VARCHAR2(80),
  STATE       NUMBER(2),
  CHECKDESC   VARCHAR2(250),
  MISC_ID     VARCHAR2(4),   
  constraint T_USER_PK primary key (USERID)
);

create table T_PAGEURI
(
  PAGEURI VARCHAR2(300) not null,
  RIGHTID VARCHAR2(30) not null,
  DESCS   VARCHAR2(200),
  constraint T_PAGEURI_PK primary key (PAGEURI) 
);


create table T_RIGHT
(
  RIGHTID VARCHAR2(30) not null,
  NAME    VARCHAR2(40) not null,
  DESCS   VARCHAR2(200),
  PARENTID VARCHAR2(30),
  LEVELS   NUMBER(1) default 0 not null,
  constraint T_RIGHT_PK primary key (RIGHTID) 
);


create table T_ROLE
(
  ROLEID NUMBER(10) not null,
  NAME   VARCHAR2(40) not null,
  DESCS  VARCHAR2(200) not null,
  Provinces varchar2(20),
  constraint T_ROLE_PK primary key (ROLEID) 
);


create table T_ROLERIGHT
(
  ROLEID  NUMBER(10) not null,
  RIGHTID VARCHAR2(30) not null,
  constraint T_ROLERIGHT_PK primary key (ROLEID,RIGHTID)
);


create table T_ROLESITERIGHT
(
  ROLEID NUMBER(10) not null,
  NODEID VARCHAR2(30) not null,
  constraint T_ROLESITERIGHT_PK primary key (ROLEID,NODEID)
);

create table T_USERROLE
(
  USERID VARCHAR2(20) not null,
  ROLEID NUMBER(10) not null,
  constraint T_USERROLE_PK primary key (USERID,ROLEID)
);

create table T_ACTIONLOG
(
  LOGID        NUMBER(10) not null,
  ACTIONTIME   VARCHAR2(14) not null,
  USERID       VARCHAR2(20) not null,
  USERNAME     VARCHAR2(30),
  ROLES        VARCHAR2(100),
  IP           VARCHAR2(24) not null,
  ACTIONTYPE   VARCHAR2(40) not null,
  ACTIONTARGET VARCHAR2(300),
  ACTIONRESULT NUMBER(1) not null,
  ACTIONDESC   VARCHAR2(200) ,
  constraint T_ACTIONLOG_PK primary key (LOGID) 
);

create table t_resource
(
  resourceKey varchar2(60),
  resourceValue varchar2(1024),
  remard varchar2(200),
  primary key(resourceKey)
); 

--��Դ����_������Ϣ��
create table T_R_Base 
(
 ID varchar2(30) not null,
 parentID varchar2(30) not null,
 path varchar2(300) not null,
 type varchar2(30) not null
); 
create index INDEX1_T_R_BASE on T_R_BASE (PARENTID);
alter table T_R_BASE add constraint  pk_t_r_base primary key(id);
--��Դ����_������Ϣ��
create table T_R_Category 
(
 ID varchar2(30) not null,
 name varchar2(100) not null,
 descs varchar2(500),
 sortID number(3,0) not null,
 ctype number(2,0) not null,
 CATEGORYID VARCHAR2(20),
 DELFLAG NUMBER(2),
 CHANGEDATE VARCHAR2(30),
 STATE NUMBER(2),
 PARENTCATEGORYID VARCHAR2(20),
 RELATION VARCHAR2(20),
 picurl varchar2(256),
 statistic number(11)
);
comment on column T_R_Category.RELATION  is '�����ŵ� W:WWW�ŵ� O:�ն��ŵ� P:PC�ŵ� ע����Զ�ѡ������������W,P����ʾ����WWW��PC�ŵ꣩';
comment on column T_R_CATEGORY.picurl is '����Ԥ��ͼ';
comment on column T_R_CATEGORY.statistic is '����ͳ����Ϣ';
alter table t_r_category add constraint  pk_t_r_category primary key(id);
create  index IDX_T_R_CATEGORY_CATEGORYID on T_R_CATEGORY (CATEGORYID);
create  index IDX_T_R_CATEGORY_PCATEGORYID on T_R_CATEGORY (PARENTCATEGORYID);

--��Դ����_������չ��Ϣ��
create table T_R_Reference
(
 ID varchar2(30) not null,
 refNodeID varchar2(30) not null,
 sortID number(8,0),
 goodsID VARCHAR2(50),
 categoryID VARCHAR2(20),
 loaddate VARCHAR2(30),
 variation number(5,0)
); 
comment on column T_R_REFERENCE.variation
  is '����id�ı����+��ʾ������-��ʾ�½���99999 ��ʾ����';
alter table t_r_reference  add constraint  pk_t_r_reference primary key(id);
create index IDX_T_R_REFERENCE_CATEGORYID on T_R_REFERENCE (CATEGORYID);
create unique index INDX_T_R_REF_GOODSID on T_R_REFERENCE (GOODSID);
create index IND_T_R_REFERENCE_REFNODEID on T_R_REFERENCE (REFNODEID);
create unique index IND_T_R_REFERENCE_ID on T_R_REFERENCE (ID);
                          
--�������ݱ�
create table t_r_gcontent
(
  id                   VARCHAR2(30) not null,
  name                 VARCHAR2(300),
  cateName             VARCHAR2(60),
  appCateName          VARCHAR2(200),
  appCateID            VARCHAR2(7),
  spName               VARCHAR2(100),
  icpCode              VARCHAR2(20),
  icpServId            VARCHAR2(30),
  contentTag           VARCHAR2(50),
  singer               VARCHAR2(200),
  price                VARCHAR2(10),
  expire               VARCHAR2(20),
  auditionUrl          VARCHAR2(400),
  introduction         varchar2(4000),
  NAMELETTER           VARCHAR2(10),
  SINGERLETTER         VARCHAR2(10),
  DOWNLOADTIMES        NUMBER(10),
  SETTIMES             NUMBER(10),
  BIGCATENAME          VARCHAR2(30),
  ContentID	           VARCHAR2(30),
  companyID	           VARCHAR2(20),
  productID	           VARCHAR2(30),
  keywords	           VARCHAR2(3000),
  createDate           VARCHAR2(30),
  marketDate           VARCHAR2(30),
  lupddate             VARCHAR2(30),
  LANGUAGE	           VARCHAR2(1),
  WWWPROPAPICTURE1     Varchar2(256),
  WWWPROPAPICTURE2     Varchar2(256),
  WWWPROPAPICTURE3     Varchar2(256),
  CLIENTPREVIEWPICTURE1 Varchar2(256),
  CLIENTPREVIEWPICTURE2 Varchar2(256),
  CLIENTPREVIEWPICTURE3 Varchar2(256),
  CLIENTPREVIEWPICTURE4 Varchar2(256),
  PROVIDER	           varchar2(200),
  HandBook             varchar2(4000), 
  Manual               Varchar2(256),
  HandBookPicture      Varchar2(256),
  UserGuide            varchar2(4000),
  UserGuidePicture     Varchar2(256),
  GameVideo            Varchar2(256),
  LOGO1		             varchar2(256),
  LOGO2		             varchar2(256),
  LOGO3		             varchar2(256),
  LOGO4		             varchar2(256),
  CartoonPicture	     Varchar2(256),
  devicename	         varchar2(4000),
  devicename02	       varchar2(4000),
  devicename03	       varchar2(4000),
  devicename04	       varchar2(4000),
  devicename05	       varchar2(4000),
  devicename06	       varchar2(4000),
  devicename07	       varchar2(4000),
  devicename08	       varchar2(4000),
  devicename09	       varchar2(4000),
  devicename10	       varchar2(4000),
  devicename11	       varchar2(4000),
  devicename12	       varchar2(4000),
  devicename13	       varchar2(4000),
  devicename14	       varchar2(4000),
  devicename15	       varchar2(4000),
  devicename16	       varchar2(4000),
  devicename17	       varchar2(4000),
  devicename18	       varchar2(4000),
  devicename19	       varchar2(4000),
  devicename20	       varchar2(4000),
  daySearchTimes       number(15) default 0,
  weekSearchTimes      number(15) default 0,
  monthSearchTimes     number(15) default 0,
  searchTimes          number(15) default 0,
  dayScanTimes         number(15) default 0,
  weekScanTimes        number(15) default 0,
  monthScanTimes       number(15) default 0,
  scanTimes            number(15) default 0,
  dayOrderTimes        number(15) default 0,
  weekOrderTimes       number(15) default 0,
  monthOrderTimes      number(15) default 0,
  orderTimes           number(15) default 0,
  dayCommentTimes      number(15) default 0,
  weekCommentTimes     number(15) default 0,
  monthCommentTimes    number(15) default 0,
  commentTimes         number(15) default 0,
  dayMarkTimes         number(15) default 0,
  weekMarkTimes        number(15) default 0,
  monthMarkTimes       number(15) default 0,
  markTimes            number(15) default 0,
  dayCommendTimes      number(15) default 0,
  weekCommendTimes     number(15) default 0,
  monthCommendTimes    number(15) default 0,
  commendTimes         number(15) default 0,
  dayCollectTimes      number(15) default 0,
  weekCollectTimes     number(15) default 0,
  monthCollectTimes    number(15) default 0,
  collectTimes         number(15) default 0,
  averageMark          number(15) default 0,
  isSupportDotcard     varchar2(2),
  programsize          number(8),
  programID            Varchar2(12),
  onlinetype           number(1),
  version              varchar2(30),
  picture1             Varchar2(256),
  picture2             Varchar2(256),
  picture3             Varchar2(256),
  picture4             Varchar2(256),
  picture5             Varchar2(256),
  picture6             Varchar2(256),
  picture7             Varchar2(256),
  picture8             Varchar2(256),
  platform             Varchar2(200),
  chargeTime           Varchar2(1),
  SERVATTR             VARCHAR2(1),
  subtype              varchar2(2) default 0,
  pvcid                 VARCHAR2(10),
  cityid                VARCHAR2(20)
);
alter table T_R_GCONTENT add constraint  pk_t_r_gcontent primary key(id); 
create index INDEX_GCONTENT_CONTENTID on T_R_GCONTENT (CONTENTID);
create index IND_T_R_GCONTENT on T_R_GCONTENT (ICPCODE, ICPSERVID);
comment on column t_r_gcontent.ID is '����ID����Ӧ��Ѷ������dcmp��ͷ����Ӧ������clr��ͷ����Ӧȫ����a8��ͷ��';
comment on column t_r_gcontent.NAME is '�������ƣ���Ӧ��Ѷ���ݵ�ý������';
comment on column t_r_gcontent.CATENAME is 'ҵ��������ƣ���Ӧȫ���ĸ���������ɷ��ࡣ';
comment on column t_r_gcontent.appCateName is '�����ڲ�Ʒ��Ӧ����ϵͳ�е�Ӧ�÷������ƣ���Ӧȫ���ĸ������Է��ࡣ';
comment on column t_r_gcontent.appCateID is '�����ڲ�Ʒ��Ӧ����ϵͳ�е�Ӧ�÷���ID';
comment on column t_r_gcontent.SPNAME is '��ҵ����';
comment on column t_r_gcontent.ICPCODE is '��ҵ����';
comment on column t_r_gcontent.ICPSERVID is 'ҵ�����';
comment on column t_r_gcontent.CONTENTTAG is '�������룬�ⲿϵͳ����Դ��Ψһ��ʶ����Ӧ��Ѷͷ��id';
comment on column t_r_gcontent.SINGER is '����';
comment on column t_r_gcontent.PRICE is '�۸��Է�Ϊ��λ';
comment on column t_r_gcontent.EXPIRE is '��Ч�ڣ������ڲ��壬��ʽΪYYYYMMDD��';
comment on column t_r_gcontent.AUDITIONURL is '����������ַ����Ӧ��Ѷ��ý������url����Ӧȫ����aac����HTTP URL';
comment on column t_r_gcontent.INTRODUCTION is '���ܣ���Ӧ��Ѷ��ͷ����������';
comment on column t_r_gcontent.NAMELETTER is '�������Ƽ�������ĸ';
comment on column t_r_gcontent.SINGERLETTER is '�������Ƽ�������ĸ';
comment on column t_r_gcontent.DOWNLOADTIMES is '�������ش���';
comment on column t_r_gcontent.SETTIMES is '�������ô���';
comment on column t_r_gcontent.BIGCATENAME is '����������ƣ���Ӧȫ�����ֵ�������';
comment on column t_r_gcontent.CONTENTID is '�������룬��Ӧ������������룬��Ӧ��Ѷ��ý��ID����Ӧȫ���ĸ���ID��';
comment on column t_r_gcontent.COMPANYID is '��ҵ����';
comment on column t_r_gcontent.PRODUCTID is 'ҵ������';
comment on column t_r_gcontent.KEYWORDS is '���ݱ�ǩ';
comment on column t_r_gcontent.CREATEDATE is '���ݴ�������';
comment on column t_r_gcontent.marketDate is '��������ʱ��';
comment on column t_r_gcontent.lupddate is '����������ʱ��';
comment on column t_r_gcontent.LANGUAGE is '���֧�ֵ����ԣ�1:�������ġ�2:�������� 3:Ӣ�� 0:������';
comment on column t_r_gcontent.WWWPROPAPICTURE1 is 'WWW����ͼ��ַ����ҳ���ͼ';
comment on column t_r_gcontent.WWWPROPAPICTURE2 is 'WWW�б�Сͼ���ַ����ҳչʾ���б�ҳչʾ';
comment on column t_r_gcontent.WWWPROPAPICTURE3 is 'WWW��׼չʾͼ3��ַ����Ʒ��ϸҳ';
comment on column t_r_gcontent.ClientPreviewPicture1 is '�ն�Ԥ��ͼ1��ַ��ͼƬ���150x160';
comment on column t_r_gcontent.ClientPreviewPicture2 is '�ն�Ԥ��ͼ2��ַ��ͼƬ���180x180';
comment on column t_r_gcontent.ClientPreviewPicture3 is '�ն�Ԥ��ͼ3��ַ��ͼƬ���210x220';
comment on column t_r_gcontent.ClientPreviewPicture4 is '�ն�Ԥ��ͼ4��ַ��ͼƬ���240x300';
comment on column t_r_gcontent.PROVIDER is '�����ṩ��';
comment on column t_r_gcontent.HandBook is '����ָ�ϣ���Ӧ��Ѷ��ͷ������';
comment on column t_r_gcontent.MANUAL is 'ʹ���ֲ��ַ����Ӧ��Ѷ��ͷ���������ӣ���Ӧȫ�����lrcURL';
comment on column t_r_gcontent.HandBookPicture is '����ָ��ͼƬ��ַ';
comment on column t_r_gcontent.UserGuide is '����˵��';
comment on column t_r_gcontent.UserGuidePicture is '����˵��ͼƬ��ַ����Ӧ��Ѷ��ͷ��ͼƬurl';
comment on column t_r_gcontent.GameVideo is '��Ϸ��Ƶ��ַ';
comment on column t_r_gcontent.Logo1 is '�ն�չʾLOGO1��ַ��ͼƬ���30x30';
comment on column t_r_gcontent.Logo2 is '�ն�չʾLOGO2��ַ��ͼƬ���34x34';
comment on column t_r_gcontent.Logo3 is '�ն�չʾLOGO3��ַ��ͼƬ���50x50';
comment on column t_r_gcontent.Logo4 is '�ն�չʾLOGO4��ַ��ͼƬ���65x65';
comment on column t_r_gcontent.CartoonPicture is '���������ͼ��ַ����Ӧ��Ѷ��ý��ͼ��url����Ӧ�����ն�������ַclientAuditionUrl����Ӧȫ��mp3����HTTP URL';
comment on column t_r_gcontent.DEVICENAME is '���������ֻ��ͺţ���{}��Ϊ�߽�����Զ��ŷָ�';
comment on column t_r_gcontent.daysearchtimes is '������������';
comment on column t_r_gcontent.weeksearchtimes is '������������';
comment on column t_r_gcontent.monthsearchtimes is '������������';
comment on column t_r_gcontent.searchtimes is '�ۼ���������';
comment on column t_r_gcontent.dayscantimes is '�����������';
comment on column t_r_gcontent.weekscantimes is '�����������';
comment on column t_r_gcontent.monthscantimes is '�����������';
comment on column t_r_gcontent.scantimes is '�ۼ��������';
comment on column t_r_gcontent.dayordertimes is '���ն�������';
comment on column t_r_gcontent.weekordertimes is '���ܶ�������';
comment on column t_r_gcontent.monthordertimes is '���¶�������';
comment on column t_r_gcontent.ordertimes is '�ۼƶ�������';
comment on column t_r_gcontent.daycommenttimes is '�������۴���';
comment on column t_r_gcontent.weekcommenttimes is '�������۴���';
comment on column t_r_gcontent.monthcommenttimes is '�������۴���';
comment on column t_r_gcontent.commenttimes is '�ۼ����۴���';
comment on column t_r_gcontent.daymarktimes is '�������ִ���';
comment on column t_r_gcontent.weekmarktimes is '�������ִ���';
comment on column t_r_gcontent.monthmarktimes is '�������ִ���';
comment on column t_r_gcontent.marktimes is '�ۼ����ִ���';
comment on column t_r_gcontent.daycommendtimes is '�����Ƽ�����';
comment on column t_r_gcontent.weekcommendtimes is '�����Ƽ�����';
comment on column t_r_gcontent.monthcommendtimes is '�����Ƽ�����';
comment on column t_r_gcontent.commendtimes is '�ۼ��Ƽ�����';
comment on column t_r_gcontent.daycollecttimes is '�����ղش���';
comment on column t_r_gcontent.weekcollecttimes is '�����ղش���';
comment on column t_r_gcontent.monthcollecttimes is '�����ղش���';
comment on column t_r_gcontent.collecttimes is '�ۼ��ղش���';
comment on column t_r_gcontent.averagemark is '���ݵļ�Ȩƽ���֣���Ӧ���塢ȫ���Ĳ���ʱ�������룩';
comment on column t_r_gcontent.isSupportDotcard is '�Ƿ�֧�ֵ㿨֧����0����֧�� 1��֧��';
comment on column t_r_gcontent.programsize is '����װ����С����λΪK';
comment on column t_r_gcontent.programID is '����ID';
comment on column t_r_gcontent.onlinetype is 'Ӧ�����࣬1������Ӧ�ã�2������Ӧ��';
comment on column t_r_gcontent.version is '���Ӧ�ð汾��';
comment on column t_r_gcontent.picture1 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ1';
comment on column t_r_gcontent.picture2 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ2';
comment on column t_r_gcontent.picture3 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ3';
comment on column t_r_gcontent.picture4 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ4';
comment on column t_r_gcontent.picture5 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ5';
comment on column t_r_gcontent.picture6 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ6';
comment on column t_r_gcontent.picture7 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ7';
comment on column t_r_gcontent.picture8 is 'WWW��ϸ��ͼ�����ʹ�ý�ͼ8';
comment on column t_r_gcontent.platform is '�������ƽ̨���ͣ�ȡֵ����kjava��mobile��symbian�ȡ���{}��Ϊ�߽�����Զ��ŷָ�';
comment on column t_r_gcontent.chargeTime is '�Ʒ�ʱ�� 1������ʱ�Ʒ� 2�������Ʒ�';
comment on column T_R_GCONTENT.subtype is '1��ʾmm��ͨӦ��,2��ʾwidgetӦ��,0��ʾ������';
comment on column T_R_GCONTENT.pvcid  is 'Ӧ�ù���ʡ��id�� 0000��ʾȫ��';
comment on column T_R_GCONTENT.cityid  is 'Ӧ�ù����е�id�� 0000��ʾpvcid��ָ��ȫʡҵ��';

--�ϴ���������ʱ���
create table t_lastsynctime
(
  Lasttime    date not null
);
 
--ͬ��������ʱ��
create table t_synctime_tmp
(
  ID    NUMBER(10) not null,
  contentid  VARCHAR2(12) not null,
  name  VARCHAR2(300) not null,
  status  VARCHAR2(4),
  contentType  VARCHAR2(32),
  LUPDDate date,
  constraint KEY_synctime_tmp primary key (id)
);

--������Ʒ��ʷ��Ϣ��
Create table t_goods_his
(
  id          VARCHAR2(30),
  goodsID		  VARCHAR2(50),
  icpcode			VARCHAR2(20),
  icpservid		VARCHAR2(30),
  contentid		VARCHAR2(30),
  categoryID	VARCHAR2(20),
  goodsName		VARCHAR2(100),
  state 			NUMBER(2),
  changeDate 	DATE,
  actionType 	NUMBER(2),
  lastState 	NUMBER(2),
  constraint KEY_t_goods_his primary key (id)
);
create index index_goods_his on t_goods_his(contentid);
--CMS����ͬ���ϼܲ��Ա�
create table t_sync_tactic
(
  ID           NUMBER(10,0) not null, --���ݿ�����
  categoryID   VARCHAR2(20) not null, --���ݻ��ܷ���ID
  contentType  VARCHAR2(30) not null, --��������
  umFlag       VARCHAR2(1) not null,  --ҵ��ͨ��  W:WAP��S:SMS��M:MMS��E:WWW��A:CMNET��
  contentTag   VARCHAR2(200), --���ݱ�ǩ
  tagRelation  NUMBER(2,0),   --���ݱ�ǩ�߼���ϵ 0=��;  1=and;  2=or
  appCateName  VARCHAR2(40), --Ӧ�÷�������
  crateTime    varchar2(20),  --����ʱ��
  lastUpdateTime varchar2(20), --����޸�ʱ��
  constraint pk_t_sync_tactic primary key(ID)
);

--���ݵ����¼��
create table t_report_import_date 
(
 type number(8,0) primary key,
 lastdate varchar2(12),
 description varchar2(100)
);

--�汾��Ϣ��
create table DBVERSION
(
  DBSEQ         NUMBER(6),
  DBVERSION     VARCHAR2(20) not null,
  LASTDBVERSION VARCHAR2(100),
  PATCHVERSION  VARCHAR2(100) not null,
  PATCHDATE     DATE default SYSDATE not null,
  DESCRIPTION   VARCHAR2(200),
  primary key (PATCHVERSION)
);
create unique index IDX_UNIQUE_DBVERSION on DBVERSION (LASTDBVERSION, PATCHVERSION);

--���ɻ��ܹ�����Ա�
create table T_CATEGORY_RULE
(
  CID            VARCHAR2(30) not null,
  RULEID         NUMBER(8) not null,
  LASTEXCUTETIME DATE,
  EFFECTIVETIME  DATE default sysdate
);
comment on table T_CATEGORY_RULE
  is '���ܲ��Թ����������ܵ���ع���';
-- Add comments to the columns 
comment on column T_CATEGORY_RULE.CID
  is '�����Ӧ�Ļ��ܵĻ�������';
comment on column T_CATEGORY_RULE.RULEID
  is '���ܶ�Ӧ�Ĺ���ID';
comment on column T_CATEGORY_RULE.LASTEXCUTETIME
  is '�ϴ�ִ��ʱ�䣬��Ҫ��ȷ����';
comment on column T_CATEGORY_RULE.EFFECTIVETIME
  is '������Чʱ��';
alter table T_CATEGORY_RULE
  add constraint PK_T_CATEGORY_RULE primary key (CID);

  create table T_CATERULE
(
  RULEID         NUMBER(8) not null,
  RULENAME       VARCHAR2(30),
  RULETYPE       NUMBER(1) default 0 not null,
  INTERVALTYPE   NUMBER(2) default 0 not null,
  EXCUTEINTERVAL NUMBER(5) not null,
  EXCUTETIME     NUMBER(5),
  randomfactor number(3)	 
);
comment on table T_CATERULE
  is '���Թ����';
-- Add comments to the columns 
comment on column T_CATERULE.RULEID
  is '����Ψһ��ID';
comment on column T_CATERULE.RULENAME
  is '��������';
comment on column T_CATERULE.RULETYPE
  is '�������� 0��ˢ�»�������Ʒ��1����������Ʒ����˳��
��5���������ͼ����Ӫ�Ƽ�ͼ�顣6:������Ӫ������������';
comment on column T_CATERULE.INTERVALTYPE
  is 'ִ��ʱ�������� 0���죻1���ܣ�2����
';
comment on column T_CATERULE.EXCUTEINTERVAL
  is 'ִ��֮��������λΪ��';
comment on column T_CATERULE.EXCUTETIME
  is '��һ��ʱ����֮�ڵ�ִ�����ӡ�
���IntervalType=0�����ֶ���Ч��
���IntervalType=1�����ֶα�ʾ���ܼ�ִ�С�
���IntervalType=2�����ֶα�ʾ���µĵڼ���ִ�С�';
comment on column T_CATERULE.randomfactor
  is '����ϼ����ӡ���Ʒ���ϼ�ǰ�Ƿ���Ҫ�漴����0�������100���漴��1~99 ���ͻ���С���';

alter table T_CATERULE
  add constraint PK_T_CATERULE primary key (RULEID);

create table T_CATERULE_COND
(
  id           NUMBER(10) not null,
  RULEID   NUMBER(8) not null,
  CID      VARCHAR2(30),
  CONDTYPE NUMBER(2) default 0 not null,
  WSQL     VARCHAR2(1000),
  OSQL     VARCHAR2(1000),
  COUNT    NUMBER(8) default -1,
  SORTID   NUMBER(8) default 1
);
comment on table T_CATERULE_COND
  is '���Թ���������';
-- Add comments to the columns 
comment on column T_CATERULE_COND.id
  is '����Ψһ��ID';
comment on column T_CATERULE_COND.RULEID
  is '����Ψһ��ID';
comment on column T_CATERULE_COND.CID
  is '��ȡ���ܵĻ�������.�����ֶ���Ч';
comment on column T_CATERULE_COND.CONDTYPE
  is '�������� 10���Ӳ�Ʒ���ȡ����ҵ���������Ϸ�����⣩������������Ϸ��11���Ӳ�Ʒ����ȡ������Ϸҵ��12���Ӳ�Ʒ���ȡ������ҵ��1���Ӿ�Ʒ���ȡ��';
comment on column T_CATERULE_COND.WSQL
  is '��ȡ������sql';
comment on column T_CATERULE_COND.OSQL
  is '��ȡ������sql';
comment on column T_CATERULE_COND.COUNT
  is '��ȡ����Ʒ����';
comment on column T_CATERULE_COND.SORTID
  is '���������ָһ�������Ӧ������֮���ִ�д���';
-- Create/Recreate indexes 
create index IDX_COND_RULEID on T_CATERULE_COND (RULEID);

create table v_cm_device_resource as
select *
  from ppms_CM_DEVICE_RESOURCE p
 where exists
 (select 1 from t_r_gcontent g where g.contentid = p.contentid);

--.2.constraint


--.3.view
--����CMS��������ͼ
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
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--ȫ��ȡonlinedate��ʡ��ȡSubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap����
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
   and d. ProductStatus  in ('2','3','5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype in ('1','2')--����widgetӦ��
   ;

create table v_cm_content as select * from ppms_v_cm_content;

-- Create table
create table T_GAME_SERVICE
(
  CONTENTID VARCHAR2(30),
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
  PTYPEID     NUMBER(2) not null
);
-- Add comments to the columns 
comment on column T_GAME_SERVICE.CONTENTID
  is '����id';
comment on column T_GAME_SERVICE.ICPCODE
  is 'CP�ĺ�������';
comment on column T_GAME_SERVICE.SPNAME
  is 'CP����';
comment on column T_GAME_SERVICE.ICPSERVID
  is '��Ʒ��ҵ�����';
comment on column T_GAME_SERVICE.SERVNAME
  is '��Ʒ����';
comment on column T_GAME_SERVICE.SERVDESC
  is '��Ʒ���';
comment on column T_GAME_SERVICE.CHARGETYPE
  is '�Ʒ�����

01 �����
02 ������
03 ������
04 ������
05 ������
06 ���״�ʹ�ð���
07�������ݼƷ�
08�����ζ����ݼƷ�
';
comment on column T_GAME_SERVICE.CHARGEDESC
  is '�Ʒ�����';
comment on column T_GAME_SERVICE.MOBILEPRICE
  is '�ʷѣ���λ��';
comment on column T_GAME_SERVICE.LUPDDATE
  is 'ҵ��������ʱ��';
comment on column T_GAME_SERVICE.SERVTYPE
  is 'ҵ�����͡�1:�ͻ��˵���,2:�ͻ�������,3:WAP����,4:WAP����
ֻ��1�ͻ��˵����ġ�
';
comment on column T_GAME_SERVICE.SERVFLAG
  is 'ҵ���ʶ��0:��ͨҵ��,1:�ײ���ҵ��';
comment on column T_GAME_SERVICE.PTYPEID
  is 'ҵ���ƹ㷽ʽ��1	���ѹ���ͻ��˵�����Ϸ
2	�ͻ������Σ������������
3	�������ι���WAP��Ϸ
4	���WAP��Ϸ
5	WAP���Σ������������
6	����ת������ѹ���ͻ��˵�����Ϸ
7	����������ת������ѹ���ͻ��˵�����Ϸ
8	�����棬���ѹ���ͻ��˵�����Ϸ
9	���Ѱ��ι���WAP��Ϸ
10	���żƷ�����
11	����ת������żƷѵ�����Ϸ
12	�����ۿ۰棬���ѹ���ͻ��˵�����Ϸ
13	����ת���������棬���żƷѵ�����Ϸ
14	5Ԫ�����ײ�����ѿͻ��˵�����Ϸ
15	5Ԫ�����ײ��ڴ��ۿͻ��˵�����Ϸ
16	TDҵ�񣬻��ѹ���ͻ��˵�����Ϸ
';

-- Create/Recreate indexes 
create unique index INDEXT_T_GAME_SERVICE_KEY on T_GAME_SERVICE (ICPCODE, ICPSERVID);

----����ppms_v_service ��ͼ ��ӻ�����Ϸ��ҵ�����ݹ���
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
       'A',--���߼Ʒ�
       null,
       8,
       t.chargetype,
       null,
       t.mobileprice,
       null,
       t.chargedesc,
       'B',
       'G',--ȫ��ҵ��
       t.servdesc,
       null,
       t.lupddate
  from t_game_service t
  ;



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
   
   

--�ϼ���Ʒ��Ϣ��ͼ
create or replace view vr_goods
(goodsid, spcode, servicecode, spid, serviceid, contentid, categoryid, goodsname,umflag)
as
select t1.goodsid,t2.icpcode,t2.icpservid,t2.companyid,t2.productid,t2.contentid,t1.categoryid,t2.name, s.umflag  
from t_r_reference t1 , t_r_gcontent t2 LEFT OUTER JOIN  v_service s on(t2.icpcode= s.icpcode and t2.icpservid=s.icpservid)
where t2.id = t1.refnodeid and ascii(substr(t2.id,1,1))>47 and ascii(substr(t2.id,1,1))<58;

--������Ϣ��ͼ
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     

--��ʷ��Ʒ��Ϣ��ͼ
create or replace view vr_goods_his 
(id,goodsid,spCode,serviceCode,spID,serviceID,contentid,categoryid,goodsName,state,changeDate,actionType,lastState)
as 
select to_number(t1.id),t1.goodsid,t1.icpcode,t1.icpservid,t2.companyid,t2.productid,t1.contentid,t1.categoryid,t1.goodsName,t1.state,t1.changeDate,t1.actionType,t1.lastState 
from t_goods_his t1,t_r_gcontent t2
where t1.contentid = t2.contentid;

---a8������Ʒ��Ϣ��
create or replace view vr_a8_goods
(goodsid, contentid, categoryid, goodsname,singer,singerzone)
as
select t1.goodsid,
       t2.contentid,
       t1.categoryid,
       t2.name,
       t2.singer,
       t2.bigcatename
  from t_r_reference t1, t_r_gcontent t2
 where t2.id = t1.refnodeid
   and t2.id like 'a8%';

-- Create  T_SINGER table
create table T_SINGER
(
  ID          VARCHAR2(25) not null,
  NAME        VARCHAR2(50) not null,
  REGION      VARCHAR2(40),
  TYPE        VARCHAR2(40),
  FIRSTLETTER VARCHAR2(50),
  primary key (ID)
);
-- Add comments to the columns 
comment on column T_SINGER.ID
  is '����ID';
comment on column T_SINGER.NAME
  is '	��������';
comment on column T_SINGER.REGION
  is '���ֵ���';
comment on column T_SINGER.TYPE
  is '��������';
comment on column T_SINGER.FIRSTLETTER
  is '	��������ĸ';
-- Create/Recreate indexes 
create index T_SINGER_INITIAL on T_SINGER (FIRSTLETTER);
create index T_SINGER_REGIN on T_SINGER (REGION);
create index T_SINGER_TYPE on T_SINGER (TYPE);




create table t_bookcate_mapping
(
  bookCateid varchar2(25) not null,
  id         varchar2(30) not null,
  primary key (bookCateid)
)
;
-- Add comments to the columns 
comment on column t_bookcate_mapping.bookCateid
  is '����ͼ�����ID';
comment on column t_bookcate_mapping.id
  is '��Ӧ���ܷ���id';




create table t_book_commend
(
  ID      VARCHAR2(30) not null,
  YBOOKID VARCHAR2(30),
  JBOOKID VARCHAR2(30)
)
;
-- Add comments to the columns 
comment on column T_BOOK_COMMEND.ID
  is 'ͼ����ܷ���id';
comment on column T_BOOK_COMMEND.YBOOKID
  is '��Ӫ�Ƽ�ͼ��id';
comment on column T_BOOK_COMMEND.JBOOKID
  is '����ͼ��id';
-- Create/Recreate indexes 
create index t_book_commend_id on t_book_commend (ID);

-- Create table
create table T_BOOK_AUTHOR
(
  AUTHORID   VARCHAR2(25) not null,
  AUTHORNAME VARCHAR2(50),
  AUTHORDESC VARCHAR2(1024),
  primary key (AUTHORID)
);
-- Add comments to the columns 
comment on column T_BOOK_AUTHOR.AUTHORID
  is '����id';
comment on column T_BOOK_AUTHOR.AUTHORNAME
  is '��������';
comment on column T_BOOK_AUTHOR.AUTHORDESC
  is '��������';
create index T_BOOK_AUTHOR_NAME on T_BOOK_AUTHOR (AUTHORNAME);
--.4.sequence

----���Ӷ���֧��ƽ̨��

-- Create table
create table T_COMIC_PLATFORM
(
  PLATFORMID VARCHAR2(20),
  PLATFORM   VARCHAR2(20)
);
comment on column T_COMIC_PLATFORM.PLATFORMID  is '֧��ƽ̨ID,ȡֵ�����100��101��102��200��300��400';
comment on column T_COMIC_PLATFORM.PLATFORM    is '֧��ƽ̨����ȡֵ���S602nd, S603rd, S605th , WM , Kjava ,OMS';

--������Ҫ��ʱ����������Ʒ�Ļ��ܱ�
-- Create table
create table T_CATEGORY_EXPORT
(
  CID VARCHAR2(30) not null
);
comment on column T_CATEGORY_EXPORT.CID  is '��Ҫ��ʱ����������Ʒ�Ļ���ID';

-- Create table T_GAME_CATE_MAPPING
create table T_GAME_CATE_MAPPING
(
  BASECATENAME VARCHAR2(100) not null,
  MMCATENAME   VARCHAR2(100)
);
-- Add comments to the columns 
comment on column T_GAME_CATE_MAPPING.BASECATENAME
  is '���ط�������';
comment on column T_GAME_CATE_MAPPING.MMCATENAME
  is 'MM��������';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_CATE_MAPPING
  add primary key (BASECATENAME);

-- Create table
create table T_GAME_UA_MAPPING
(
  BASEUA     VARCHAR2(500) not null,
  DEVICENAME VARCHAR2(500)
);
-- Add comments to the columns 
comment on column T_GAME_UA_MAPPING.BASEUA
  is '����UA��Ϣ';
comment on column T_GAME_UA_MAPPING.DEVICENAME
  is 'MM��Ӧ����UA��devicename';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_UA_MAPPING
  add primary key (BASEUA);
 

 create table T_CONTENT_COUNT
(
  CONTENTID      NVARCHAR2(30) not null,
  LATESTCOUNT    NUMBER default 0 not null,
  RECOMMENDCOUNT NUMBER default 0 not null,
  COUNTTIME      NVARCHAR2(30),
  COMPECOUNT     NUMBER default 0 not null
);

---����ͬ�� ģ��������Ϣ���Ż��� ��ͼ090patch
create or replace view v_match_device_resource 
(device_id, device_name, contentid, contentname, match)
as
select t.device_id,t.device_name,t.contentid,t.contentname,decode(match,1,1,2,2,3,2) match from v_cm_device_resource t;



--�޸��ṩ���������ͼ vr_category
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     

-----�޸���T_R_CATEGORY�ֶκ���

comment on column T_R_CATEGORY.STATE is '�����Ƿ����Ż�չʾ��1��չʾ��0����չʾ';


--�޸��ṩ���������ͼ vr_category
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     

-----����ZCOM_ID�Զ���������
-- Create sequence 
create sequence SEQ_ZCOM_ID
minvalue 1
maxvalue 99999999
start with 1087
increment by 1
nocache
cycle;


-- ����Zcom������ʱ��ÿ��drop�ñ���PPMS��ͼV_CM_CONTENT_ZCOMȡ����
create table V_CM_CONTENT_ZCOM
(
  CONTENTID     VARCHAR2(12),
  CONTENTCODE   VARCHAR2(10),
  NAME          VARCHAR2(60),
  APPDESC       VARCHAR2(4000),
  CONTENTFEE    NUMBER(8),
  ONLINEDATE    DATE,
  PRODUCTID     VARCHAR2(12),
  CHARGETIME    NUMBER(1),
  CARTOONPICURL VARCHAR2(256),
  LOGO1URL      VARCHAR2(256),
  LOGO2URL      VARCHAR2(256),
  LOGO3URL      VARCHAR2(256),
  LOGO4URL      VARCHAR2(256),
  ICPSERVID     VARCHAR2(10),
  ICPCODE       VARCHAR2(6),
  LUPDDATE      DATE
);
-- ����Zcom���������ϵ��ʱ��ÿ��drop�ñ���PPMS��ͼV_CM_DEVICE_RESOURCEȡ����
create table V_CM_DEVICE_RESOURCE_ZCOM
(
  PID           VARCHAR2(12),
  DEVICE_ID     NUMBER(8),
  DEVICE_NAME   VARCHAR2(100),
  CONTENTID     VARCHAR2(12),
  CONTENTNAME   VARCHAR2(60),
  RESOURCEID    VARCHAR2(10),
  ID            VARCHAR2(12),
  SERVICECODE   VARCHAR2(10),
  ABSOLUTEPATH  VARCHAR2(256),
  URL           VARCHAR2(256),
  PROGRAMSIZE   NUMBER(8),
  JARFILEPATH   VARCHAR2(256),
  JADFILEPATH   VARCHAR2(256),
  CONTENTUUID   VARCHAR2(50),
  CREATEDATE    DATE,
  PROSUBMITDATE DATE,
  MATCH         NUMBER(1),
  PLATFORM      VARCHAR2(20),
  OSNAME        VARCHAR2(100)
);
----- ����Zcom ͬ����ʱ��
create table T_SYNCTIME_TMP_ZCOM
(
  NAME      VARCHAR2(60),
  CONTENTID VARCHAR2(12),
  LUPDDATE  DATE
);



-- ����Zcomͬ����ʷʱ���
create table T_LASTSYNCTIME_ZCOM
(
  LASTTIME DATE not null
);

-- ����Zcom������Ϣ��
create table Z_PPS_MAGA
(
  ID    NUMBER not null,
  NAME  VARCHAR2(200) not null,
  LOGO  VARCHAR2(100),
  DESCS VARCHAR2(2000)
);

-- ����Zcom���������
create table Z_PPS_MAGA_LS
(
  ID             NUMBER,
  MAGA_NAME      VARCHAR2(100),
  MAGA_PERIODS   VARCHAR2(10),
  MAGA_OFFICE    VARCHAR2(50),
  MAGA_DATE      VARCHAR2(50),
  PERIOD         VARCHAR2(10),
  PRICE          NUMBER,
  CONTENTID      VARCHAR2(30),
  CHARGETYPE     VARCHAR2(2),
  UPTIME         VARCHAR2(50),
  CARTOONPIC     VARCHAR2(50),
  LOG1           VARCHAR2(50),
  LOG2           VARCHAR2(50),
  LOG3           VARCHAR2(50),
  LOG4           VARCHAR2(50),
  PARENT_ID      NUMBER,
  MAGA_FULL_NAME VARCHAR2(100),
  FULL_DEVICE_ID VARCHAR2(4000),
  ICPCODE        VARCHAR2(100),
  ICPSERVID      VARCHAR2(100),
  SIZES          VARCHAR2(20),
  PERFIX         VARCHAR2(50),
  PLATFORM       VARCHAR2(50),
  LUPDDATE        DATE
);
create index IND_V_DEVICEID on z_pps_maga_ls(full_device_id) indextype is ctxsys.context parameters('lexer chinese_lexer');
----����APӦ�ú�����
create table T_CONTENT_BACKLIST
(
  CONTENTID VARCHAR2(30),
  INDATE    VARCHAR2(30)
);
comment on column T_CONTENT_BACKLIST.CONTENTID
  is 'Ӧ������ID';
comment on column T_CONTENT_BACKLIST.INDATE
  is '��������Ч��';
-- Create/Recreate indexes 
create index T_CONTENT_BLACKLIST_CID on T_CONTENT_BACKLIST (CONTENTID);

-- ������Ϸ����Ϣ��
-- Create table
create table T_GAME_BASE
(
  PKGID       VARCHAR2(25) not null,
  PKGNAME     VARCHAR2(64) not null,
  PKGDESC     VARCHAR2(512) not null,
  CPNAME      VARCHAR2(64),
  SERVICECODE VARCHAR2(30),
  FEE         NUMBER not null,
  PKGURL      VARCHAR2(300) not null,
  PICURL1     VARCHAR2(255) not null,
  PICURL2     VARCHAR2(255) not null,
  PICURL3     VARCHAR2(255) not null,
  PICURL4     VARCHAR2(255) not null,
  UPDATETIME  DATE default sysdate not null,
  CREATETIME  DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_GAME_BASE.PKGID
  is '��Ϸ��id';
comment on column T_GAME_BASE.PKGNAME
  is '��Ϸ������';
comment on column T_GAME_BASE.PKGDESC
  is '��Ϸ������';
comment on column T_GAME_BASE.CPNAME
  is '������';
comment on column T_GAME_BASE.SERVICECODE
  is 'ҵ�����';
comment on column T_GAME_BASE.FEE
  is '����';
comment on column T_GAME_BASE.PKGURL
  is '��Ϸ�����URL';
comment on column T_GAME_BASE.PICURL1
  is '��� 30x30 picture1';
comment on column T_GAME_BASE.PICURL2
  is '��� 34x34 picture2';
comment on column T_GAME_BASE.PICURL3
  is '��� 50x50 picture3';
comment on column T_GAME_BASE.PICURL4
  is '��� 65x65 picture4';
comment on column T_GAME_BASE.UPDATETIME
  is '�������ʱ��';
comment on column T_GAME_BASE.CREATETIME
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_BASE
  add primary key (PKGID);
  
  -- Create table�������ܼ�ػ������ܱ�
create table T_CATEGORY_MONITOR
(
  ID               VARCHAR2(30),
  NAME             VARCHAR2(100),
  CATEGORYID       VARCHAR2(20),
  PARENTCATEGORYID VARCHAR2(20),
  CATEGORYTYPE     VARCHAR2(2)
);
create index INDEX_MONITOR_CATEGORYID on T_CATEGORY_MONITOR (CATEGORYID);
-- Add comments to the columns 
comment on column T_CATEGORY_MONITOR.ID
  is '��������ID';
comment on column T_CATEGORY_MONITOR.NAME
  is '��������';
comment on column T_CATEGORY_MONITOR.CATEGORYID
  is '����ID';
comment on column T_CATEGORY_MONITOR.PARENTCATEGORYID
  is '���ܸ�ID';
comment on column T_CATEGORY_MONITOR.CATEGORYTYPE
  is '�������ͣ�0,ֻȡ��������Ϣ;1,ȡ��������Ϣ�Լ�һ���ӻ�����Ϣ;9,ȡ��������Ϣ�Լ��������������Ϣ';
  
  ---�������ַ���
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
  is '����ID';
comment on column T_MB_MUSIC.SONGNAME
  is '��������';
comment on column T_MB_MUSIC.SINGER
  is '��������';
comment on column T_MB_MUSIC.VALIDITY
  is '��Ч��';
comment on column T_MB_MUSIC.UPDATETIME
  is '����ʱ��';
comment on column T_MB_MUSIC.CREATETIME
  is '����ʱ��';
    comment on column T_MB_MUSIC.DELFLAG
  is 'ɾ����ǣ�0��δɾ����1����ɾ��';
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
  is '����ID';
comment on column T_MB_CATEGORY.CATEGORYNAME
  is '��������';
comment on column T_MB_CATEGORY.PARENTCATEGORYID
  is '���ܸ�ID';
comment on column T_MB_CATEGORY.TYPE
  is '����';
comment on column T_MB_CATEGORY.DELFLAG
  is 'ɾ�����';
comment on column T_MB_CATEGORY.CREATETIME
  is '����ʱ��';
comment on column T_MB_CATEGORY.CATEGORYDESC
  is '��������';
comment on column T_MB_CATEGORY.SORTID
  is '��������';
comment on column T_MB_CATEGORY.SUM
  is '������Ʒ����';
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
  is '����ID';
comment on column T_MB_REFERENCE.CATEGORYID
  is '����ID';
comment on column T_MB_REFERENCE.CREATETIME
  is '����ʱ��';
comment on column T_MB_REFERENCE.SORTID
  is '����';
comment on column T_MB_REFERENCE.MUSICNAME
  is '��������';
-- Create/Recreate indexes 
create index INDEX_TB_REFERENCE_CAID on T_MB_REFERENCE (CATEGORYID);
create unique index PK_MUSIC_CATEID on T_MB_REFERENCE (MUSICID, CATEGORYID);

---��������
-- Create sequence 
create sequence SEQ_BM_CATEGORY_ID  minvalue 100002167  maxvalue 999999999  start with 100004206  increment by 1  nocache  cycle;

--G+��Ϸ���޸ı�ṹ
alter table T_GAME_BASE add state varchar2(2) default 1 not null;
comment on column T_GAME_BASE.state
  is '��ǰ״̬1:�½�,2:����,3:ɾ��';

--С���Ƽ�
alter table T_CONTENT_COUNT add recommend_grade number(6,2);
comment on column T_CONTENT_COUNT.recommend_grade
  is '�Ƽ�����';
  
  
----  APӦ��ˢ���ǰ60��Ʒ���Ԥ�����ṩ������ʹ�� ���������񵥻���ID���ã�
----- ���Ի�����Ҫ�޸���Ӧ����ID
----  �ն��Ż�
create or replace view v_mo_top60reference as
select q.day,q.goodsid,q.contentid,q.sortid,q.subname, y.name
    from t_r_category y,

         (select to_char(sysdate, 'yyyyMMdd') day,
                 r.goodsid,
                 g.contentid,
                 r.sortid,
                 c.name subname,
                 c.parentcategoryid
            from t_r_reference r, t_r_Category c, t_r_gcontent g
           where r.categoryid = c.categoryid
             and r.refnodeid = g.id
             and c.id in ('1257460', '1257459', '1257457', '1257458',
        '1257462', '1257463', '1257464', '1257466',
                          '1257467', '1257468', '1257469', '1257471')
             and r.sortid > 940) q
   where y.categoryid = q.parentcategoryid;
-----WWW�Ż�   
create or replace view v_www_top60reference as
select q.day,q.goodsid,q.contentid,q.sortid,q.subname, y.name
    from t_r_category y,

         (select to_char(sysdate, 'yyyyMMdd') day,
                 r.goodsid,
                 g.contentid,
                 r.sortid,
                 c.name subname,
                 c.parentcategoryid
            from t_r_reference r, t_r_Category c, t_r_gcontent g
           where r.categoryid = c.categoryid
             and r.refnodeid = g.id
             and c.id in ('1810803', '1810805', '1815681', '1814487')
             and r.sortid > 940) q
   where y.categoryid = q.parentcategoryid;

-----�����Ƶ����Ԫ���ݱ�
-- Create table
create table T_VB_VIDEO
(
  PKGID      VARCHAR2(10),
  PKGNAME    VARCHAR2(100),
  FEE        NUMBER,
  CREATEDATE DATE,
  LUPDATE    DATE
);
-- Add comments to the columns 
comment on column T_VB_VIDEO.PKGID
  is '��Ƶ��ID';
comment on column T_VB_VIDEO.PKGNAME
  is '��Ƶ������';
comment on column T_VB_VIDEO.FEE
  is '����Ƶ��Ʒ�����ʷ�';
comment on column T_VB_VIDEO.CREATEDATE
  is '����ʱ��';
comment on column T_VB_VIDEO.LUPDATE
  is '������ʱ��';
create unique index PKGID_PK on T_VB_VIDEO (PKGID);

--����˹���ԤӦ��----
create table T_INTERVENOR
(
  ID          VARCHAR2(30) not null,
  NAME        VARCHAR2(300) not null,
  STARTDATE   DATE not null,
  ENDDATE     DATE not null,
  STARTSORTID NUMBER(8) not null,
  ENDSORTID   NUMBER(8) not null
);

comment on column T_INTERVENOR.ID
  is '��Ԥ�������';
comment on column T_INTERVENOR.NAME
  is '��Ԥ��������';
comment on column T_INTERVENOR.STARTDATE
  is '��Ԥ��ʼʱ��';
comment on column T_INTERVENOR.ENDDATE
  is '��Ԥ����ʱ��';
comment on column T_INTERVENOR.STARTSORTID
  is '��Ԥ��ʼ����';
comment on column T_INTERVENOR.ENDSORTID
  is '��Ԥ�������������뿪ʼ������ͬΪ�̶�����������Ϊ��������';
  
alter table T_INTERVENOR
  add primary key (ID);  

create table T_INTERVENOR_CATEGORY_MAP
(
  INTERVENORID VARCHAR2(30) not null,
  CATEGORYID   VARCHAR2(30) not null,
  SORTID       NUMBER(3) default 1
);

comment on column T_INTERVENOR_CATEGORY_MAP.INTERVENORID
  is '����id';
comment on column T_INTERVENOR_CATEGORY_MAP.CATEGORYID
  is '��id';
comment on column T_INTERVENOR_CATEGORY_MAP.SORTID
  is '���������';


create table T_INTERVENOR_GCONTENT_MAP
(
  INTERVENORID VARCHAR2(30) not null,
  GCONTENTID   VARCHAR2(30) not null,
  SORTID       NUMBER(8) default 1
);

comment on column T_INTERVENOR_GCONTENT_MAP.INTERVENORID
  is '����id';
comment on column T_INTERVENOR_GCONTENT_MAP.GCONTENTID
  is '����id';
comment on column T_INTERVENOR_GCONTENT_MAP.SORTID
  is '�����������е�����';
  
  create table T_EXPORT_TOPLIST
(
  ID         NUMBER(2) not null,
  NAME       VARCHAR2(50),
  CATEGORYID VARCHAR2(30) not null,
  COUNT      NUMBER(5) default 30 not null,
  CONDITION  VARCHAR2(200)
);
-- Add comments to the columns 
comment on column T_EXPORT_TOPLIST.ID
  is '��ר��ID';
comment on column T_EXPORT_TOPLIST.NAME
  is '�񵥻�ר������';
comment on column T_EXPORT_TOPLIST.CATEGORYID
  is '���ܱ���';
comment on column T_EXPORT_TOPLIST.COUNT
  is '��ȡ���������Ʒ����';
comment on column T_EXPORT_TOPLIST.CONDITION
  is '�񵥻�ȡ����';


create sequence SEQ_ACTIONLOG_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle;


create sequence SEQ_ROLE_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache cycle;


--�������ܱ������У�9λ��
create sequence SEQ_CATEGORY_ID
minvalue 100000000
maxvalue 999999999
start with 100000000
increment by 1
nocache cycle;

--����ͬ��ʱ����ʱ����
create sequence SEQ_SYNCTIME_TMP_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache cycle;


--������Ʒ��ʷ��Ϣ��ID����
create sequence SEQ_GOODS_HIS_ID
minvalue 1
maxvalue 999999999999999
start with 1
increment by 1
nocache cycle;

--����ͬ������ID����
create sequence SEQ_SYNC_TACTIC_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache cycle;

--���ݰ汾����
create sequence SEQ_DB_VERSION
minvalue 1000
maxvalue 999999
start with 1000
increment by 1
nocache cycle;

-- ���ڻ��ܹ����id
create sequence SEQ_CATERULE_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

-- ���ڹ���������id
create sequence SEQ_CATERULE_COND_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;



create table T_TOPLIST
(
  TIME       VARCHAR2(8),
  CONTENTID  VARCHAR2(12) not null,
  COUNT      VARCHAR2(11),
  TYPE       VARCHAR2(2) not null,
  UPDATETIME DATE default SYSDATE not null,
  constraint T_TOPLIST_PK primary key (CONTENTID,TYPE)
);
-- Add comments to the columns 
comment on column T_TOPLIST.TIME
  is 'ͳ������,��ʽ��YYYYMMDD';
comment on column T_TOPLIST.CONTENTID
  is 'Ӧ�ñ�ʶ';
comment on column T_TOPLIST.COUNT
  is 'TypeΪ1|2ʱ�������ۺ��Ƽ�ָ��; Ϊ3|4ʱ����̽�Ƽ��÷�;';
comment on column T_TOPLIST.TYPE
  is '������:1Ӧ������;2�����������;3Ӧ����̽;4���������̽';
comment on column T_TOPLIST.UPDATETIME
  is '���һ�θ��µ�ʱ��';

create table T_CY_PRODUCTLIST
(
  STATTIME        VARCHAR2(8),
  CONTENTID        VARCHAR2(12) not null primary key  ,
  CONTENTNAME      VARCHAR2(60),
  DOWNLOADUSERNUM  NUMBER(12),
  TESTUSERNUM      NUMBER(12),
  TESTSTAR         NUMBER(12),
  STARSCORECOUNT   NUMBER(12),
  GLOBALSCORECOUNT NUMBER(12),
  UPDATETIME       DATE default SYSDATE not null
);
-- Add comments to the columns 
comment on column T_CY_PRODUCTLIST.STATTIME
  is 'ͳ������,��ʽ��YYYYMMDD';
comment on column T_CY_PRODUCTLIST.CONTENTID
  is '����ID';
comment on column T_CY_PRODUCTLIST.CONTENTNAME
  is '��������';
comment on column T_CY_PRODUCTLIST.DOWNLOADUSERNUM
  is '�����û���';
comment on column T_CY_PRODUCTLIST.TESTUSERNUM
  is '�����û���';
comment on column T_CY_PRODUCTLIST.TESTSTAR
  is '�����Ǽ�';
comment on column T_CY_PRODUCTLIST.STARSCORECOUNT
  is '��̽�Ƽ��÷�';
comment on column T_CY_PRODUCTLIST.GLOBALSCORECOUNT
  is '�����ۺ��Ƽ�ָ��';
comment on column T_CY_PRODUCTLIST.UPDATETIME
  is '���һ�θ��µ�ʱ��';

--.5.trigger

--.6.function

--.7.procedure

--8.job

--�˹���Ԥ����id
create sequence SEQ_INTERVENOR_ID
minvalue 1
maxvalue 9999999999
start with 200
increment by 1
nocache
cycle;







