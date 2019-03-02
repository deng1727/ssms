--修改黑名单表
alter table T_CONTENT_BACKLIST add STARTDATE VARCHAR2(30);
alter table T_CONTENT_BACKLIST add TYPE NUMBER(2);
alter table T_CONTENT_BACKLIST rename column indate to ENDDATE;
comment on column T_CONTENT_BACKLIST.STARTDATE
  is '黑名单开始期';
comment on column T_CONTENT_BACKLIST.TYPE
  is '类型：1，期间不上线，其他时间上线；2，期间上线，其他不上线。';



update T_CONTENT_BACKLIST set STARTDATE='20100101';
update T_CONTENT_BACKLIST set TYPE=1;

-- 用于货架规则表id
create sequence SEQ_CATERULE_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

-- 用于规则条件表id
create sequence SEQ_CATERULE_COND_ID
minvalue 1
maxvalue 9999999999
start with 1000
increment by 1
nocache
cycle;

-- 新增字段
alter table T_CATERULE_COND add id NUMBER(10);
-- Add comments to the columns 
comment on column T_CATERULE_COND.id
  is '条件唯一的ID';

-- 新增字段内容
update t_caterule_cond t
   set t.id = seq_caterule_cond_id.nextval;

-- 修改新增字段为不可空
alter table T_CATERULE_COND modify id not null;

insert into t_right t(t.rightid,t.name,t.levels)values('2_1301_UPDATE','货架自动更新',2);
insert into t_right t(t.rightid,t.name,t.parentid,t.levels)values('0_1301_CATEGORY','货架管理','2_1301_UPDATE',0);
insert into t_right t(t.rightid,t.name,t.parentid,t.levels)values('0_1301_RULE','规则管理','2_1301_UPDATE',0);
insert into T_ROLERIGHT (ROLEID, RIGHTID)values (1, '2_1301_UPDATE');

---基地图书分离

/*==============================================================*/
/* Table: T_RB_AUTHOR                                           */
/*==============================================================*/
create table T_RB_AUTHOR  (
   AUTHORID             VARCHAR2(25)                    not null,
   AUTHORNAME           VARCHAR2(50)                    not null,
   AUTHORDESC           VARCHAR2(1024),
   constraint PK_T_RB_AUTHOR primary key (AUTHORID)
);

comment on column T_RB_AUTHOR.AUTHORID is
'作者ID';

comment on column T_RB_AUTHOR.AUTHORNAME is
'作者';

comment on column T_RB_AUTHOR.AUTHORDESC is
'描述';

/*==============================================================*/
/* Table: T_RB_BOOK                                             */
/*==============================================================*/
create table T_RB_BOOK  (
   BOOKID               VARCHAR2(16)                    not null,
   BOOKNAME             VARCHAR2(100)                   not null,
   KEYWORD              VARCHAR2(1024),
   LONGRECOMMEND        VARCHAR2(200)                   not null,
   SHORTRECOMMEND       VARCHAR2(100)                   ,
   DESCRIPTION          VARCHAR2(2048),
   AUTHORID             VARCHAR2(25)                    not null,
   AUTHORNAME           VARCHAR2(50)                    not null,
   TYPEID               VARCHAR2(20)                    ,
   SUBTYPEID            VARCHAR2(20)                    not null,
   INTIME               VARCHAR2(14)                    not null,
   BOOKURL              VARCHAR2(255)                   not null,
   CHARGETYPE           VARCHAR2(2)                     not null,
   FEE                  VARCHAR2(10)                    not null,
   ISFINISH             VARCHAR2(2)                     not null,
   DELFLAG              NUMBER(1)                      default 0 not null,
   LUPDATE              date                           default SYSDATE not null,
   constraint PK_T_RB_BOOK primary key (BOOKID)
);

comment on column T_RB_BOOK.BOOKID is
'图书ID';

comment on column T_RB_BOOK.BOOKNAME is
'图书名称';

comment on column T_RB_BOOK.KEYWORD is
'图书关键字';

comment on column T_RB_BOOK.LONGRECOMMEND is
'长推荐语';

comment on column T_RB_BOOK.SHORTRECOMMEND is
'短推荐语';

comment on column T_RB_BOOK.DESCRIPTION is
'图书简介';

comment on column T_RB_BOOK.AUTHORID is
'作者ID';

comment on column T_RB_BOOK.AUTHORNAME is
'作者';

comment on column T_RB_BOOK.TYPEID is
'图书分类ID 大分类';
comment on column T_RB_BOOK.SUBTYPEID is
'图书小分类ID 35个基础分类';

comment on column T_RB_BOOK.INTIME is
'入库时间 YYYYMMDDHH24MISS';

comment on column T_RB_BOOK.BOOKURL is
'图书WAP页面入口地址';

comment on column T_RB_BOOK.CHARGETYPE is
'费用类型 费用类型：0免费；1按本计费；2、按章计费；3、按字计费';

comment on column T_RB_BOOK.FEE is
'费率，单位：厘
当chargeType = 0时，fee必须为0';

comment on column T_RB_BOOK.ISFINISH is
'是否完本';

comment on column T_RB_BOOK.DELFLAG is
'是否删除 0：未删除  1：已删除';

comment on column T_RB_BOOK.LUPDATE is
'最后更新时间';

/*==============================================================*/
/* Table: T_RB_CATEGORY                                         */
/*==============================================================*/
create table T_RB_CATEGORY  (
   ID                   NUMBER(10)                      not null,
   CATEGORYID           VARCHAR2(20)                    not null,
   CATEGORYNAME         VARCHAR2(200)                   not null,
   CATALOGTYPE          VARCHAR2(2)                     not null,
   DECRISPTION          VARCHAR2(256),
   PARENTID             VARCHAR2(20)                    not null,
   PICURL               VARCHAR2(1024),
   LUPDATE              date                           default SYSDATE not null,
   TOTAL                NUMBER(10),
   constraint PK_T_RB_CATEGORY primary key (ID)
);

comment on column T_RB_CATEGORY.ID is
'主ID';

comment on column T_RB_CATEGORY.CATEGORYID is
'货架ID 对应专区、排行
排行货架
2T   点击总榜  2M点击月榜  2E 点击周榜
3T   畅销总榜  3M 畅销月榜  3E 畅销周榜
5T  搜索总榜  5M 搜索月榜  5E 搜索周榜
7T   鲜花总榜  7M 鲜花月榜  7E 鲜花周榜
';

comment on column T_RB_CATEGORY.CATEGORYNAME is
'货架名称';

comment on column T_RB_CATEGORY.CATALOGTYPE is
'货架类型 
1：推荐专区
2：首发专区
3：专题
12：排行';

comment on column T_RB_CATEGORY.DECRISPTION is
'简介';

comment on column T_RB_CATEGORY.PARENTID is
'父货架ID';

comment on column T_RB_CATEGORY.PICURL is
'专区图片，初期不提供';

comment on column T_RB_CATEGORY.LUPDATE is
'最后更新时间';
comment on column T_RB_CATEGORY.TOTAL
  is '该货架下所有的商品总数';
  
  
/*==============================================================*/
/* Table: T_RB_MONTHLY                                         */
/*==============================================================*/
create table T_RB_MONTHLY  (
   CATEGORYID           VARCHAR2(20)                    not null,
   CATEGORYNAME         VARCHAR2(200)                   not null,
   DECRISPTION          VARCHAR2(256),
   FEE                  VARCHAR2(10),
   URL                  VARCHAR2(1024),
   LUPDATE              date                           default SYSDATE not null,
   constraint PK_T_RB_MONTHLY primary key (CATEGORYID)
);

comment on column T_RB_MONTHLY.CATEGORYID is
'包月id';

comment on column T_RB_MONTHLY.CATEGORYNAME is
'包月名称';

comment on column T_RB_MONTHLY.DECRISPTION is
'简介';

comment on column T_RB_MONTHLY.FEE is
'资费，TYPE为包月用 单位：厘';

comment on column T_RB_MONTHLY.URL is
'链接到图书基地的专区URL链接地址,CATALOGTYPE为包月时必须';

comment on column T_RB_MONTHLY.LUPDATE is
'最后更新时间'; 
  
  
/*==============================================================*/
/* Table: T_RB_RECOMMEND                                        */
/*==============================================================*/
create table T_RB_RECOMMEND  (
   RECOMMENDID          VARCHAR2(20)                    not null,
   TYPEID               VARCHAR2(20)                    ,
   BOOKID               VARCHAR2(20)                    not null,
   CREATETIME           date                           default SYSDATE not null
);

comment on column T_RB_RECOMMEND.RECOMMENDID is
'推荐ID';

comment on column T_RB_RECOMMEND.TYPEID is
'图书分类ID';

comment on column T_RB_RECOMMEND.BOOKID is
'图书ID';

comment on column T_RB_RECOMMEND.CREATETIME is
'创建时间';

/*==============================================================*/
/* Table: T_RB_REFERENCE                                        */
/*==============================================================*/
create table T_RB_REFERENCE  (
   CID                  NUMBER(10)                      not null,
   CATEGORYID           VARCHAR2(20)                    not null,
   BOOKID               VARCHAR2(60)                    not null,
   SORTNUMBER           Number(6,0),
   RANKVALUE            Number(10,0)
);

comment on column T_RB_REFERENCE.CID is
'货架ID';

comment on column T_RB_REFERENCE.CATEGORYID is
'对应包月、专区或排行id';

comment on column T_RB_REFERENCE.BOOKID is
'图书ID';

comment on column T_RB_REFERENCE.SORTNUMBER is
'排序序号。从小到大排列。必须是整数，取值范围在-999999到999999之间';

comment on column T_RB_REFERENCE.RANKVALUE is
'排行依据值';

/*==============================================================*/
/* Table: T_RB_TYPE                                             */
/*==============================================================*/
create table T_RB_TYPE  (
   TYPEID               VARCHAR2(20)                    not null,
   TYPENAME             Varchar2(100)                   not null,
   PARENTID             VARCHAR2(20)                    ,
   TOTALBOOKS           NUMBER(10),
   constraint PK_T_RB_TYPE primary key (TYPEID)
);

comment on column T_RB_TYPE.TYPEID is
'图书分类ID';

comment on column T_RB_TYPE.TYPENAME is
'图书分类名称';

comment on column T_RB_TYPE.PARENTID is
'父分类ID';
comment on column T_RB_TYPE.TOTALBOOKS
  is '该分类下的图书总数';

---创建序列
-- Create sequence 
create sequence SEQ_RB_CATEGORY_ID  minvalue 100002167  maxvalue 999999999  start with 100004206  increment by 1  nocache  cycle;

--create index
create unique index PK_T_RB_AUTHOR_ID on T_RB_AUTHOR(AUTHORID);
create unique index PK_T_RB_BOOK_ID on T_RB_BOOK(BOOKID);
create unique index PK_T_RB_CATEGORY_ID on T_RB_CATEGORY(ID);
create index IDX_T_RB_CATEGORY_ID on T_RB_CATEGORY(CATEGORYID);
create unique index PK_T_RB_RECOMMEND_IDX on T_RB_RECOMMEND (TYPEID, BOOKID);
create index IDX_T_RB_REFERENCE_ID on T_RB_REFERENCE(CATEGORYID,BOOKID);
create index IDX_T_RB_REFERENCE_CATE_ID on T_RB_REFERENCE(CATEGORYID);
create index IDX_T_RB_REFERENCE_BOOK_ID on T_RB_REFERENCE(BOOKID);
create unique index PK_T_TYPE_ID on T_RB_TYPE(TYPEID);

insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'100000001','推荐','1','推荐','0');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'100000002','首发','2','首发','0');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'100000003','专题','3','专题','0');
--insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
--values(SEQ_RB_CATEGORY_ID.NEXTVAL,'100000004','包月','11','包月','0');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'100000005','排行','12','排行','0');

insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'2T','点击总榜','12','点击总榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'2M','点击月榜','12','点击月榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'2E','点击周榜','12','点击周榜','100000005');

insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'3T','畅销总榜','12','畅销总榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'3M','畅销月榜','12','畅销月榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'3E','畅销周榜','12','畅销周榜','100000005');

insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'5T','搜索总榜','12','搜索总榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'5M','搜索月榜','12','搜索月榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'5E','搜索周榜','12','搜索周榜','100000005');

insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'7T','鲜花总榜','12','鲜花总榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'7M','鲜花月榜','12','鲜花月榜','100000005');
insert into T_RB_CATEGORY(ID,CATEGORYID,CATEGORYNAME,CATALOGTYPE,DECRISPTION,PARENTID)
values(SEQ_RB_CATEGORY_ID.NEXTVAL,'7E','鲜花周榜','12','鲜花周榜','100000005');

insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000000','都市言情','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000001','穿越幻想','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000002','玄幻奇幻','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000003','武侠仙侠','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000004','游戏竞技','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000005','浪漫青春','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000006','历史军事','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000007','灵异悬疑','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000008','名著传记','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000009','科幻小说','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000010','影视剧本','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000011','时尚生活','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000012','官场职场','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000013','经管励志','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000014','教育社科','0');
insert into T_RB_TYPE(TYPEID,TYPENAME,Parentid)values('1000015','短篇小品','0');

commit;


------授权给终端门户使用
grant select on T_RB_AUTHOR to &portalmo;--终端门户
grant select on T_RB_BOOK to &portalmo;--终端门户
grant select on T_RB_CATEGORY to &portalmo;--终端门户
grant select on  T_RB_RECOMMEND to &portalmo;--终端门户
grant select on T_RB_REFERENCE to &portalmo;--终端门户
grant select on T_RB_TYPE to &portalmo;--终端门户
grant select on T_RB_MONTHLY to &portalmo;--终端门户

-----添加视频基地元数据表
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
  is '视频包ID';
comment on column T_VB_VIDEO.PKGNAME
  is '视频包名称';
comment on column T_VB_VIDEO.FEE
  is '该视频产品包的资费';
comment on column T_VB_VIDEO.CREATEDATE
  is '创建时间';
comment on column T_VB_VIDEO.LUPDATE
  is '最后更新时间';
create unique index PKGID_PK on T_VB_VIDEO (PKGID);


create table t_new_old_cate_mapping
(
  newname   varchar2(50) not null,
  oldname   varchar2(50) not null,
  oldcateid varchar2(20) not null
)
;
-- Add comments to the columns 
comment on column t_new_old_cate_mapping.newname
  is '新分类名称';
comment on column t_new_old_cate_mapping.oldname
  is '旧分类名称';
comment on column t_new_old_cate_mapping.oldcateid
  is '旧分类id';

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('系统', '系统工具', '1');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('无线', '系统工具', '1');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('安全', '系统工具', '1');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('资讯', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('金融', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('理财', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('美化', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('生活', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('图像', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('词典', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('书籍', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('旅行', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('地图', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('医疗', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('音乐', '多媒体软件', '3');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('视频', '多媒体软件', '3');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('通讯', '通信辅助', '4');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('聊天', '通信辅助', '4');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('输入', '通信辅助', '4');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('商务', '网络软件', '5');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('娱乐', '网络软件', '5');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('查询', '网络软件', '5');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('角色', '角色扮演', '6');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('动作', '动作格斗', '7');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('格斗', '动作格斗', '7');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('体育', '体育竞技', '8');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('赛车', '体育竞技', '8');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('射击', '射击飞行', '9');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('飞行', '射击飞行', '9');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('策略', '策略回合', '10');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('冒险', '冒险模拟', '11');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('经营', '冒险模拟', '11');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('休闲', '休闲趣味', '12');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('养成', '休闲趣味', '12');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('棋牌', '棋牌益智', '13');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('益智', '棋牌益智', '13');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('其他', '其他', '14');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('爱情', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('搞笑', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('星座', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('色彩', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('中国风', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('恐怖', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('涂鸦', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('创意', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('人物', '人物', '16');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('插画', '人物', '16');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('影视', '影视', '17');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('节日', '节日', '18');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('夜光', '科技', '19');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('动态', '科技', '19');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('军事', '科技', '19');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('游戏', '游戏', '20');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('体育', '体育', '21');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('动物', '动物', '22');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('卡通', '卡通', '23');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('明星', '明星', '24');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('汽车', '汽车', '25');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('风景', '风景', '26');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('建筑', '风景', '26');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('植物', '风景', '26');



--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.123_SSMS','MM1.0.0.125_SSMS');
commit;