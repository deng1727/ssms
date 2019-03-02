--备份037版本T_CATEGORY_RULE表及数据
create table t_category_rule_ssms037_bak as select * from T_CATEGORY_RULE;

drop table T_CATEGORY_RULE cascade constraints;
/*==============================================================*/
/* Table: T_CATEGORY_RULE                                       */
/*==============================================================*/
create table T_CATEGORY_RULE  (
   CID                  varchar2(30)                    not null,
   RULEID               number(8,0)                     not null,
   LASTEXCUTETIME       DATE,
   EFFECTIVETIME        DATE                           default sysdate,
   constraint PK_T_CATEGORY_RULE primary key (CID)
);

comment on table T_CATEGORY_RULE is
'货架策略规则表，保存货架的相关规则';

comment on column T_CATEGORY_RULE.CID is
'规则对应的货架的货架内码';

comment on column T_CATEGORY_RULE.RULEID is
'货架对应的规则ID';

comment on column T_CATEGORY_RULE.LASTEXCUTETIME is
'上次执行时间，需要精确到秒';

comment on column T_CATEGORY_RULE.EFFECTIVETIME is
'规则生效时间';

--drop table T_CATERULE cascade constraints;
/*==============================================================*/
/* Table: T_CATERULE                                            */
/*==============================================================*/
create table T_CATERULE  (
   RULEID               number(8,0)                     not null,
   RULENAME             VARCHAR2(30),
   RULETYPE             number(1,0)                    default 0 not null,
   INTERVALTYPE         number(2,0)                    default 0 not null,
   EXCUTEINTERVAL       number(5,0)                     not null,
   EXCUTETIME           number(5,0),
   constraint PK_T_CATERULE primary key (RULEID)
);

comment on table T_CATERULE is
'策略规则表';

comment on column T_CATERULE.RULEID is
'规则唯一的ID';

comment on column T_CATERULE.RULENAME is
'规则名称';

comment on column T_CATERULE.RULETYPE is
'规则类型 0：刷新货架下商品；1：货架下商品重排顺序
';

comment on column T_CATERULE.INTERVALTYPE is
'执行时间间隔类型 0：天；1：周；2：月
';

comment on column T_CATERULE.EXCUTEINTERVAL is
'执行的时间间隔';

comment on column T_CATERULE.EXCUTETIME is
'在一个时间间隔之内的执行日子。
如果IntervalType=0，本字段无效。
如果IntervalType=1，本字段表示在周几执行。
如果IntervalType=2，本字段表示在月的第几天执行。
';

--drop index IDX_COND_RULEID;
--drop table T_CATERULE_COND cascade constraints;
/*==============================================================*/
/* Table: T_CATERULE_COND                                       */
/*==============================================================*/
create table T_CATERULE_COND  (
   RULEID               number(8,0)                     not null,
   CID                  VARCHAR2(30),
   CONDTYPE             number(1,0)                    default 0 not null
      constraint CKC_CONDTYPE_T_CATERU check (CONDTYPE in (0,1)),
   WSQL                 varchar2(1000),
   OSQL                 varchar2(1000),
   COUNT                number(8,0)                    default -1,
   SORTID               number(8,0)		       default 1
);

comment on table T_CATERULE_COND is
'策略规则条件表';

comment on column T_CATERULE_COND.RULEID is
'规则唯一的ID';

comment on column T_CATERULE_COND.CID is
'获取货架的货架内码，如果CondType=0则本字段无效
';

comment on column T_CATERULE_COND.CONDTYPE is
'条件类型 0：从产品库获取；1：从精品库获取
';

comment on column T_CATERULE_COND.WSQL is
'获取的条件sql';

comment on column T_CATERULE_COND.OSQL is
'获取的排序sql';

comment on column T_CATERULE_COND.COUNT is
'获取的商品数量';

comment on column T_CATERULE_COND.SORTID is
'排序次序，是指一个规则对应条件的之间的执行次序';

/*==============================================================*/
/* Index: IDX_COND_RULEID                                       */
/*==============================================================*/
create index IDX_COND_RULEID on T_CATERULE_COND (
   RULEID ASC
);

/*==============================================================*/
/* T_CATEGORY_RULE表数据                                        */
/*==============================================================*/
delete from T_CATEGORY_RULE;

-----------------------------------
--        大类:WWW货架           --
-----------------------------------

------------------------------------
--        货架类型：分类          --
------------------------------------

--一级货架：WWW软件
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7064', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7008', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7005', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7010', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7012', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7004', 1, NULL, SYSDATE);
--一级货架：WWW游戏
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7014', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7022', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7020', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7016', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7052', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('691704', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7026', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7024', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7018', 1, NULL, SYSDATE);
--一级货架：WWW主题
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7042', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7050', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7034', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7044', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7036', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7028', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7046', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7048', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7030', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7040', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7032', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7038', 1, NULL, SYSDATE);
------------------------------------
--        货架类型：免费          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810803', 2, NULL, SYSDATE);
------------------------------------
--        货架类型：最新          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1814487', 3, NULL, SYSDATE);
------------------------------------
--        货架类型：推荐          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810805', 4, NULL, SYSDATE);
------------------------------------
--        货架类型：月排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815682', 5, NULL, SYSDATE);
------------------------------------
--        货架类型：周排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815681', 6, NULL, SYSDATE);
------------------------------------
--        货架类型：总排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810802', 7, NULL, SYSDATE);

------------------------------------
--        货架类型：品牌          --
------------------------------------
--OPHONE
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842657', 8, NULL, SYSDATE);
--诺基亚
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842658', 9, NULL, SYSDATE);
--三星
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842659', 10, NULL, SYSDATE);
--索尼爱立信
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842660', 11, NULL, SYSDATE);
--LG
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842662', 12, NULL, SYSDATE);
--摩托罗拉
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842661', 13, NULL, SYSDATE);
--联想
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842663', 14, NULL, SYSDATE);
--多普达
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842664', 15, NULL, SYSDATE);
--松下
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1860924', 16, NULL, SYSDATE);
--戴尔
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1860925', 17, NULL, SYSDATE);
--飞利浦
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881309', 18, NULL, SYSDATE);
--CECT
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881454', 19, NULL, SYSDATE);
--艾美讯
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881455', 20, NULL, SYSDATE);
--imate
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881456', 21, NULL, SYSDATE);
--中兴
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842666', 22, NULL, SYSDATE);
--UT斯达康
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881457', 23, NULL, SYSDATE);
--UBiQUiO
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881458', 24, NULL, SYSDATE);
--OQO
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881459', 25, NULL, SYSDATE);
--OKWAP
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881460', 26, NULL, SYSDATE);
--HKC
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881461', 27, NULL, SYSDATE);
--明基西门子
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881462', 28, NULL, SYSDATE);
--乐讯
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881463', 29, NULL, SYSDATE);
--技嘉
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881464', 30, NULL, SYSDATE);
--汇讯
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881465', 31, NULL, SYSDATE);
--惠普
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881466', 32, NULL, SYSDATE);
--华硕
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881468', 33, NULL, SYSDATE);
--华录
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881467', 34, NULL, SYSDATE);
--华立
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881470', 35, NULL, SYSDATE);
--宏基
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881469', 36, NULL, SYSDATE);
--优冠TCT
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881471', 37, NULL, SYSDATE);
--倚天
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881472', 38, NULL, SYSDATE);
--夏新
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881473', 39, NULL, SYSDATE);
--沃达丰
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881474', 40, NULL, SYSDATE);
--天语
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881475', 41, NULL, SYSDATE);
--盛泰
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881476', 42, NULL, SYSDATE);
--神达
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881477', 43, NULL, SYSDATE);
--三巨网
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881478', 44, NULL, SYSDATE);
--琦基
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881516', 45, NULL, SYSDATE);
--中恒
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881479', 46, NULL, SYSDATE);
--海尔
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881480', 47, NULL, SYSDATE);
--东芝
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881481', 48, NULL, SYSDATE);
--大显
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881482', 49, NULL, SYSDATE);
--波导
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881483', 50, NULL, SYSDATE);
--HTC
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881484', 51, NULL, SYSDATE);
--O2
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881485', 52, NULL, SYSDATE);
--HTO
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881487', 53, NULL, SYSDATE);
--Velocity
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881486', 54, NULL, SYSDATE);
--酷派
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842665', 55, NULL, SYSDATE);
--金鹏
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881488', 56, NULL, SYSDATE);
--金立
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881489', 57, NULL, SYSDATE);
--华禹
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881490', 58, NULL, SYSDATE);
--华为
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881491', 59, NULL, SYSDATE);
--恒泰
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881492', 60, NULL, SYSDATE);
--衡天越
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881493', 61, NULL, SYSDATE);
--黑莓
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881494', 62, NULL, SYSDATE);
--海信
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881495', 63, NULL, SYSDATE);
--东信
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881496', 64, NULL, SYSDATE);
--振华欧比
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881497', 65, NULL, SYSDATE);
--万利达
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881498', 66, NULL, SYSDATE);
--德赛
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881499', 67, NULL, SYSDATE);
--创维
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881500', 68, NULL, SYSDATE);
--Palm
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881501', 69, NULL, SYSDATE);
--阿尔卡特
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881502', 70, NULL, SYSDATE);
--IDO
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881503', 71, NULL, SYSDATE);
--TCL
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881504', 72, NULL, SYSDATE);
--明基
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881505', 73, NULL, SYSDATE);
--魅族
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881506', 74, NULL, SYSDATE);
--众一
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881507', 75, NULL, SYSDATE);
--厦华
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881508', 76, NULL, SYSDATE);
--夏普
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881509', 77, NULL, SYSDATE);
--现代
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881510', 78, NULL, SYSDATE);
--摩普达
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881511', 79, NULL, SYSDATE);
--汉泰
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881512', 80, NULL, SYSDATE);
--熊猫
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881513', 81, NULL, SYSDATE);
--宇达
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881514', 82, NULL, SYSDATE);
--其他品牌
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881515', 83, NULL, SYSDATE);

-----------------------------------
--     大类:集团版终端货架       --
-----------------------------------

------------------------------------
--        货架类型：分类          --
------------------------------------

--二级货架：应用软件
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7009', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7006', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7011', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7013', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7007', 1, NULL, SYSDATE);

--二级货架：手机游戏
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7015', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7023', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7021', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7017', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7053', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7628', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7027', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7025', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7019', 1, NULL, SYSDATE);

--二级货架：手机主题
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7043', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7051', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7035', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7045', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7037', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7029', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7047', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7049', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7031', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7041', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7033', 1, NULL, SYSDATE);
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('7039', 1, NULL, SYSDATE);

------------------------------------
--      货架类型：软件免费        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257460', 84, NULL, SYSDATE);
------------------------------------
--      货架类型：软件排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257459', 85, NULL, SYSDATE);
------------------------------------
--      货架类型：软件推荐        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257457', 86, NULL, SYSDATE);
------------------------------------
--      货架类型：软件星级        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257461', 87, NULL, SYSDATE);
------------------------------------
--      货架类型：软件最新        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257458', 88, NULL, SYSDATE);
------------------------------------
--      货架类型：游戏免费        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257462', 89, NULL, SYSDATE);
------------------------------------
--      货架类型：游戏排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257463', 90, NULL, SYSDATE);
------------------------------------
--      货架类型：游戏推荐        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257464', 91, NULL, SYSDATE);
------------------------------------
--      货架类型：游戏星级        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257465', 92, NULL, SYSDATE);
------------------------------------
--      货架类型：游戏最新        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257466', 93, NULL, SYSDATE);
------------------------------------
--      货架类型：主题免费        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257467', 94, NULL, SYSDATE);
------------------------------------
--      货架类型：主题排行        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257468', 95, NULL, SYSDATE);
--      货架类型：主题推荐        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257469', 96, NULL, SYSDATE);
------------------------------------
--      货架类型：主题星级        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257470', 97, NULL, SYSDATE);
------------------------------------
--      货架类型：主题最新        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257471', 98, NULL, SYSDATE);

/*==============================================================*/
/* T_CATERULE表数据                                             */
/*==============================================================*/
delete from T_CATERULE;

--分类货架规则
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (1, '分类', 1, 0, 1,
    NULL);
--WWW免费货架规则
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (2, '免费', 0, 0, 1,
    NULL);
--WWW最新货架规则
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (3, '最新', 0, 0, 1,
    NULL);
--WWW推荐货架规则
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (4, '推荐', 0, 0, 1,
    NULL);
--WWW周排行货架规则
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (5, '周排行', 0, 1, 1,
    1);
--WWW月排行货架规则
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (6, '月排行', 0, 2, 1,
    1);
--WWW总排行货架规则
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (7, '总排行', 0, 0, 1,
    NULL);

------------------------------------
--        品牌货架规则            --
------------------------------------
--OPHONE
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (8, 'OPHONE', 0, 0, 1,
    NULL);
--诺基亚
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (9, '诺基亚', 0, 0, 1,
    NULL);
--三星
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (10, '三星', 0, 0, 1,
    NULL);
--索尼爱立信
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (11, '索尼爱立信', 0, 0, 1,
    NULL);
--LG
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (12, 'LG', 0, 0, 1,
    NULL);
--摩托罗拉
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (13, '摩托罗拉', 0, 0, 1,
    NULL);
--联想
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (14, '联想', 0, 0, 1,
    NULL);
--多普达
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (15, '多普达', 0, 0, 1,
    NULL);
--松下
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (16, '松下', 0, 0, 1,
    NULL);
--戴尔
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (17, '戴尔', 0, 0, 1,
    NULL);
--飞利浦
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (18, '飞利浦', 0, 0, 1,
    NULL);
--CECT
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (19, 'CECT', 0, 0, 1,
    NULL);
--艾美讯
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (20, '艾美讯', 0, 0, 1,
    NULL);
--imate
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (21, 'imate', 0, 0, 1,
    NULL);
--中兴
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (22, '中兴', 0, 0, 1,
    NULL);
--UT斯达康
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (23, 'UT斯达康', 0, 0, 1,
    NULL);
--UBiQUiO
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (24, 'UBiQUiO', 0, 0, 1,
    NULL);
--OQO
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (25, 'OQO', 0, 0, 1,
    NULL);
--OKWAP
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (26, 'OKWAP', 0, 0, 1,
    NULL);
--HKC
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (27, 'HKC', 0, 0, 1,
    NULL);
--明基西门子
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (28, '明基西门子', 0, 0, 1,
    NULL);
--乐讯
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (29, '乐讯', 0, 0, 1,
    NULL);
--技嘉
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (30, '技嘉', 0, 0, 1,
    NULL);
--汇讯
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (31, '汇讯', 0, 0, 1,
    NULL);
--惠普
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (32, '惠普', 0, 0, 1,
    NULL);
--华硕
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (33, '华硕', 0, 0, 1,
    NULL);
--华录
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (34, '华录', 0, 0, 1,
    NULL);
--华立
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (35, '华立', 0, 0, 1,
    NULL);
--宏基
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (36, '宏基', 0, 0, 1,
    NULL);
--优冠TCT
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (37, '优冠TCT', 0, 0, 1,
    NULL);
--倚天
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (38, '倚天', 0, 0, 1,
    NULL);
--夏新
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (39, '夏新', 0, 0, 1,
    NULL);
--沃达丰
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (40, '沃达丰', 0, 0, 1,
    NULL);
--天语
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (41, '天语', 0, 0, 1,
    NULL);
--盛泰
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (42, '盛泰', 0, 0, 1,
    NULL);
--神达
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (43, '神达', 0, 0, 1,
    NULL);
--三巨网
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (44, '三巨网', 0, 0, 1,
    NULL);
--琦基
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (45, '琦基', 0, 0, 1,
    NULL);
--中恒
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (46, '中恒', 0, 0, 1,
    NULL);
--海尔
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (47, '海尔', 0, 0, 1,
    NULL);
--东芝
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (48, '东芝', 0, 0, 1,
    NULL);
--大显
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (49, '大显', 0, 0, 1,
    NULL);
--波导
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (50, '波导', 0, 0, 1,
    NULL);
--HTC
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (51, 'HTC', 0, 0, 1,
    NULL);
--O2
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (52, 'O2', 0, 0, 1,
    NULL);
--HTO
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (53, 'HTO', 0, 0, 1,
    NULL);
--Velocity
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (54, 'Velocity', 0, 0, 1,
    NULL);
--酷派
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (55, '酷派', 0, 0, 1,
    NULL);
--金鹏
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (56, '金鹏', 0, 0, 1,
    NULL);
--金立
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (57, '金立', 0, 0, 1,
    NULL);
--华禹
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (58, '华禹', 0, 0, 1,
    NULL);
--华为
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (59, '华为', 0, 0, 1,
    NULL);
--恒泰
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (60, '恒泰', 0, 0, 1,
    NULL);
--衡天越
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (61, '衡天越', 0, 0, 1,
    NULL);
--黑莓
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (62, '黑莓', 0, 0, 1,
    NULL);
--海信
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (63, '海信', 0, 0, 1,
    NULL);
--东信
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (64, '东信', 0, 0, 1,
    NULL);
--振华欧比
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (65, '振华欧比', 0, 0, 1,
    NULL);
--万利达
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (66, '万利达', 0, 0, 1,
    NULL);
--德赛
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (67, '德赛', 0, 0, 1,
    NULL);
--创维
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (68, '创维', 0, 0, 1,
    NULL);
--Palm
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (69, 'Palm', 0, 0, 1,
    NULL);
--阿尔卡特
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (70, '阿尔卡特', 0, 0, 1,
    NULL);
--IDO
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (71, 'IDO', 0, 0, 1,
    NULL);
--TCL
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (72, 'TCL', 0, 0, 1,
    NULL);
--明基
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (73, '明基', 0, 0, 1,
    NULL);
--魅族
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (74, '魅族', 0, 0, 1,
    NULL);
--众一
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (75, '众一', 0, 0, 1,
    NULL);
--厦华
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (76, '厦华', 0, 0, 1,
    NULL);
--夏普
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (77, '夏普', 0, 0, 1,
    NULL);
--现代
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (78, '现代', 0, 0, 1,
    NULL);
--摩普达
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (79, '摩普达', 0, 0, 1,
    NULL);
--汉泰
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (80, '汉泰', 0, 0, 1,
    NULL);
--熊猫
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (81, '熊猫', 0, 0, 1,
    NULL);
--宇达
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (82, '宇达', 0, 0, 1,
    NULL);
--其他品牌
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (83, '其他品牌', 0, 0, 1,
    NULL);

------------------------------------
--      货架类型：软件免费        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (84, '软件免费', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：软件排行        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (85, '软件排行', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：软件推荐        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (86, '软件推荐', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：软件星级        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (87, '软件星级', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：软件最新        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (88, '软件最新', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：游戏免费        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (89, '游戏免费', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：游戏排行        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (90, '游戏排行', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：游戏推荐        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (91, '游戏推荐', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：游戏星级        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (92, '游戏星级', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：游戏最新        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (93, '游戏最新', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：主题免费        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (94, '主题免费', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：主题排行        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (95, '主题排行', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：主题推荐        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (96, '主题推荐', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：主题星级        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (97, '主题星级', 0, 0, 1,
    NULL);
------------------------------------
--      货架类型：主题最新        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (98, '主题最新', 0, 0, 1,
    NULL);

/*==============================================================*/
/* T_CATERULE_COND表数据                                        */
/*==============================================================*/
delete from T_CATERULE_COND;

--分类规则条件
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (1, NULL, 0, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);

--WWW免费货架规则条件
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (2, '1885166', 1, NULL, 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (2, NULL, 0, 'mobilePrice=0', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);

--WWW最新货架规则条件
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (3, '1885168', 1, NULL, 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (3, NULL, 0, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc',
    -1, NULL);

--WWW推荐货架规则条件
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (4, '1885167', 1, NULL, 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (4, NULL, 0, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);

--WWW周排行货架规则条件
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (5, '1885170', 1, NULL, 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (5, NULL, 0, NULL, 'weekordertimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);

--WWW月排行货架规则条件
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (6, '1885169', 1, NULL, 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (6, NULL, 0, NULL, 'monthordertimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);

--WWW总排行货架规则条件
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (7, '1885171', 1, NULL, 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (7, NULL, 0, NULL, 'orderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);

------------------------------------
--       品牌货架规则条件         --
------------------------------------
--OPHONE
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (8, '1885167', 1, 'PLATFORM like ''%ophone os%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (8, NULL, 0, 'type=''nt:gcontent:appSoftWare''and PLATFORM like ''%ophone os%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (8, NULL, 0, 'type=''nt:gcontent:appGame''and PLATFORM like ''%ophone os%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (8, NULL, 0, 'type=''nt:gcontent:appTheme''and PLATFORM like ''%ophone os%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--诺基亚
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, '1885167', 1, 'BRAND like ''%诺基亚%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%诺基亚%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%诺基亚%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%诺基亚%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--三星
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, '1885167', 1, 'BRAND like ''%三星%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%三星%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%三星%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%三星%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--索尼爱立信
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, '1885167', 1, 'BRAND like ''%索尼爱立信%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%索尼爱立信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%索尼爱立信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%索尼爱立信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--LG
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (12, '1885167', 1, 'BRAND like ''%LG%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (12, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%LG%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (12, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%LG%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (12, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%LG%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--摩托罗拉
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, '1885167', 1, 'BRAND like ''%摩托罗拉%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%摩托罗拉%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%摩托罗拉%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%摩托罗拉%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--联想
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, '1885167', 1, 'BRAND like ''%联想%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%联想%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%联想%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%联想%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--多普达
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, '1885167', 1, 'BRAND like ''%多普达%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%多普达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%多普达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%多普达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--松下
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, '1885167', 1, 'BRAND like ''%松下%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%松下%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%松下%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%松下%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--戴尔
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, '1885167', 1, 'BRAND like ''%戴尔%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%戴尔%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%戴尔%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%戴尔%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--飞利浦
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, '1885167', 1, 'BRAND like ''%飞利浦%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%飞利浦%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%飞利浦%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%飞利浦%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--CECT
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (19, '1885167', 1, 'BRAND like ''%CECT%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (19, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%CECT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (19, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%CECT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (19, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%CECT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--艾美讯
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, '1885167', 1, 'BRAND like ''%艾美讯%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%艾美讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%艾美讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%艾美讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--imate
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (21, '1885167', 1, 'BRAND like ''%i-mate%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (21, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%i-mate%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (21, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%i-mate%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (21, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%i-mate%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--中兴
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, '1885167', 1, 'BRAND like ''%中兴%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%中兴%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%中兴%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%中兴%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--UT斯达康
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, '1885167', 1, 'BRAND like ''%UT斯达康%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%UT斯达康%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%UT斯达康%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%UT斯达康%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--UBiQUiO
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (24, '1885167', 1, 'BRAND like ''%UBiQUiO%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (24, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%UBiQUiO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (24, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%UBiQUiO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (24, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%UBiQUiO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--OQO
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (25, '1885167', 1, 'BRAND like ''%OQO%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (25, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%OQO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (25, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%OQO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (25, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%OQO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--OKWAP
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (26, '1885167', 1, 'BRAND like ''%OKWAP%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (26, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%OKWAP%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (26, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%OKWAP%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (26, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%OKWAP%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--HKC
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (27, '1885167', 1, 'BRAND like ''%HKC%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (27, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%HKC%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (27, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%HKC%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (27, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%HKC%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--明基西门子
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, '1885167', 1, 'BRAND like ''%明基西门子%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%明基西门子%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%明基西门子%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%明基西门子%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--乐讯
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, '1885167', 1, 'BRAND like ''%乐讯%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%乐讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%乐讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%乐讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--技嘉
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, '1885167', 1, 'BRAND like ''%技嘉%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%技嘉%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%技嘉%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%技嘉%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--汇讯
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, '1885167', 1, 'BRAND like ''%汇讯%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%汇讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%汇讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%汇讯%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--惠普
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, '1885167', 1, 'BRAND like ''%惠普%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%惠普%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%惠普%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%惠普%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--华硕
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, '1885167', 1, 'BRAND like ''%华硕%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%华硕%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%华硕%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%华硕%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--华录
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, '1885167', 1, 'BRAND like ''%华录%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%华录%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%华录%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%华录%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--华立
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, '1885167', 1, 'BRAND like ''%华立%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%华立%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%华立%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%华立%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--宏基
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, '1885167', 1, 'BRAND like ''%宏基%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%宏基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%宏基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%宏基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--优冠TCT
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, '1885167', 1, 'BRAND like ''%优冠TCT%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%优冠TCT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%优冠TCT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%优冠TCT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--倚天
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, '1885167', 1, 'BRAND like ''%倚天%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%倚天%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%倚天%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%倚天%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--夏新
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, '1885167', 1, 'BRAND like ''%夏新%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%夏新%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%夏新%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%夏新%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--沃达丰
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, '1885167', 1, 'BRAND like ''%沃达丰%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%沃达丰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%沃达丰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%沃达丰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--天语
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, '1885167', 1, 'BRAND like ''%天语%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%天语%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%天语%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%天语%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--盛泰
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, '1885167', 1, 'BRAND like ''%盛泰%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%盛泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%盛泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%盛泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--神达
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, '1885167', 1, 'BRAND like ''%神达%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%神达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%神达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%神达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--三巨网
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, '1885167', 1, 'BRAND like ''%三巨网%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%三巨网%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%三巨网%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%三巨网%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--琦基
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, '1885167', 1, 'BRAND like ''%琦基%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%琦基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%琦基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%琦基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--中恒
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, '1885167', 1, 'BRAND like ''%中恒%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%中恒%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%中恒%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%中恒%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--海尔
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, '1885167', 1, 'BRAND like ''%海尔%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%海尔%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%海尔%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%海尔%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--东芝
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, '1885167', 1, 'BRAND like ''%东芝%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%东芝%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%东芝%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%东芝%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--大显
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, '1885167', 1, 'BRAND like ''%大显%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%大显%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%大显%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%大显%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--波导
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, '1885167', 1, 'BRAND like ''%波导%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%波导%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%波导%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%波导%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--HTC
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (51, '1885167', 1, 'BRAND like ''%HTC%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (51, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%HTC%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (51, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%HTC%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (51, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%HTC%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--O2
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (52, '1885167', 1, 'BRAND like ''%O2%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (52, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%O2%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (52, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%O2%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (52, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%O2%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--HTO
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (53, '1885167', 1, 'BRAND like ''%HTO%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (53, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%HTO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (53, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%HTO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (53, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%HTO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--Velocity
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (54, '1885167', 1, 'BRAND like ''%Velocity%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (54, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%Velocity%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (54, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%Velocity%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (54, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%Velocity%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--酷派
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, '1885167', 1, 'BRAND like ''%酷派%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%酷派%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%酷派%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%酷派%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--金鹏
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, '1885167', 1, 'BRAND like ''%金鹏%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%金鹏%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%金鹏%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%金鹏%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--金立
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, '1885167', 1, 'BRAND like ''%金立%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%金立%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%金立%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%金立%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--华禹
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, '1885167', 1, 'BRAND like ''%华禹%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%华禹%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%华禹%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%华禹%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--华为
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, '1885167', 1, 'BRAND like ''%华为%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%华为%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%华为%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%华为%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--恒泰
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, '1885167', 1, 'BRAND like ''%恒泰%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%恒泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%恒泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%恒泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--衡天越
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, '1885167', 1, 'BRAND like ''%衡天越%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%衡天越%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%衡天越%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%衡天越%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--黑莓
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, '1885167', 1, 'BRAND like ''%黑莓%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%黑莓%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%黑莓%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%黑莓%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--海信
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, '1885167', 1, 'BRAND like ''%海信%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%海信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%海信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%海信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--东信
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, '1885167', 1, 'BRAND like ''%东信%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%东信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%东信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%东信%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--振华欧比
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, '1885167', 1, 'BRAND like ''%振华欧比%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%振华欧比%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%振华欧比%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%振华欧比%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--万利达
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, '1885167', 1, 'BRAND like ''%万利达%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%万利达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%万利达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%万利达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--德赛
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, '1885167', 1, 'BRAND like ''%德赛%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%德赛%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%德赛%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%德赛%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--创维
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, '1885167', 1, 'BRAND like ''%创维%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%创维%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%创维%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%创维%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--Palm
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, '1885167', 1, 'BRAND like ''%奔迈%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%奔迈%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%奔迈%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%奔迈%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--阿尔卡特
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, '1885167', 1, 'BRAND like ''%阿尔卡特%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%阿尔卡特%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%阿尔卡特%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%阿尔卡特%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--IDO
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (71, '1885167', 1, 'BRAND like ''%IDO%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (71, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%IDO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (71, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%IDO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (71, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%IDO%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--TCL
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (72, '1885167', 1, 'BRAND like ''%TCL%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (72, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%TCL%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (72, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%TCL%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (72, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%TCL%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--明基
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, '1885167', 1, 'BRAND like ''%明基%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%明基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%明基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%明基%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--魅族
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, '1885167', 1, 'BRAND like ''%魅族%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%魅族%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%魅族%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%魅族%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--众一
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, '1885167', 1, 'BRAND like ''%众一%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%众一%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%众一%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%众一%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--厦华
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, '1885167', 1, 'BRAND like ''%厦华%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%厦华%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%厦华%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%厦华%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--夏普
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, '1885167', 1, 'BRAND like ''%夏普%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%夏普%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%夏普%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%夏普%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--现代
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, '1885167', 1, 'BRAND like ''%现代%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%现代%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%现代%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%现代%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--摩普达
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, '1885167', 1, 'BRAND like ''%摩普达%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%摩普达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%摩普达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%摩普达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--汉泰
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, '1885167', 1, 'BRAND like ''%汉泰%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%汉泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%汉泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%汉泰%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--熊猫
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, '1885167', 1, 'BRAND like ''%熊猫%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%熊猫%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%熊猫%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%熊猫%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--宇达
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, '1885167', 1, 'BRAND like ''%宇达%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%宇达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%宇达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%宇达%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--其他品牌
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, '1885167', 1, 'BRAND like ''%其他品牌%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%其他品牌%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%其他品牌%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%其他品牌%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);

------------------------------------
--      货架类型：软件免费        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (84, '1885172', 1, 'type=''nt:gcontent:appSoftWare''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (84, NULL, 0, 'mobilePrice=0 and type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：软件排行        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (85, '1885173', 1, 'type=''nt:gcontent:appSoftWare''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (85, NULL, 0, 'type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：软件推荐        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (86, '1885174', 1, 'type=''nt:gcontent:appSoftWare''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (86, NULL, 0, 'type=''nt:gcontent:appSoftWare''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：软件星级        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (87, '1885175', 1, 'type=''nt:gcontent:appSoftWare''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (87, NULL, 0, 'type=''nt:gcontent:appSoftWare''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：软件最新        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (88, '1885176', 1, 'type=''nt:gcontent:appSoftWare''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (88, NULL, 0, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appSoftWare''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：游戏免费        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (89, '1885177', 1, 'type=''nt:gcontent:appGame''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (89, NULL, 0, 'mobilePrice=0 and type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：游戏排行        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (90, '1885178', 1, 'type=''nt:gcontent:appGame''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (90, NULL, 0, 'type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：游戏推荐        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (91, '1885179', 1, 'type=''nt:gcontent:appGame''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (91, NULL, 0, 'type=''nt:gcontent:appGame''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：游戏星级        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (92, '1885180', 1, 'type=''nt:gcontent:appGame''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (92, NULL, 0, 'type=''nt:gcontent:appGame''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：游戏最新        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (93, '1885181', 1, 'type=''nt:gcontent:appGame''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (93, NULL, 0, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appGame''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：主题免费        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (94, '1885182', 1, 'type=''nt:gcontent:appTheme''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (94, NULL, 0, 'mobilePrice=0 and type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：主题排行        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (95, '1885183', 1, 'type=''nt:gcontent:appTheme''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (95, NULL, 0, 'type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：主题推荐        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (96, '1885184', 1, 'type=''nt:gcontent:appTheme''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (96, NULL, 0, 'type=''nt:gcontent:appTheme''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：主题星级        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (97, '1885185', 1, 'type=''nt:gcontent:appTheme''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (97, NULL, 0, 'type=''nt:gcontent:appTheme''', 'starLevel desc,dayOrderTimes desc,createdate desc,mobilePrice desc,name asc',
    -1, NULL);
------------------------------------
--      货架类型：主题最新        --
------------------------------------
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (98, '1885186', 1, 'type=''nt:gcontent:appTheme''', 'sortID desc,marketDate desc',
    -1, NULL);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (98, NULL, 0, 'trunc(months_between(sysdate,to_date(createDate,''YYYY-MM-DD HH24:MI:SS'')))=0 and type=''nt:gcontent:appTheme''', 'createdate desc,dayOrderTimes desc,mobilePrice desc,name asc',
    -1, NULL);

---------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------

--更新视图脚本v_cm_content。去除判断条件c.status = '0008'
create or replace view v_cm_content as
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
              c.status) as status,
       c.createdate,
       f.onlinedate as marketdate,
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate) as lupddate,
       f.chargeTime
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company         e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid
   and (c.status = '0006' or c.status = '1006')
   and d.AuditStatus = '0003'
   and f.ID = d.ID
   and c.contentid = f.contentid;  


--ssms 用户下 通过PPMS的DBLINK的方式创建同义词CM_CT_DEVICE，T_DEVICE_BRAND
---目前现网的货架指向PPMS的DBLINK名称为：DL_PPMS_DEVICE
------------------------------start------------------------

create synonym CM_CT_DEVICE        for &db_user .DL_PPMS_DEVICE       ;
create synonym T_DEVICE_BRAND        for &db_user .DL_PPMS_DEVICE       ;

--------------------------------end----------------------------------

--创建所有MM应用已经支持的机型列表视图
create or replace view v_content_device
as
select distinct d.adapte_id as device_id
from CM_CT_DEVICE d,CM_CONTENT c
where c.contentid=d.contentid 
  and c.STATUS in ('0006','1006')
  and d.adapte_type=0;

---需要把v_content_device视图给各门户创建同义词使用。
--分别向wwwpas，mopas，pcpas三个门户创建同义词。创建语句如下。
--（目前现网是在一个数据库实例，所以本次增量脚本暂时只提供同一个实例下的创建同义词脚本。）
--------------------start--------------------------
---在ssms用户执行授权语句
ACCEPT ssms_db_user CHAR prompt 'input MM.PAS DB username:' 

grant select on v_content_device        to &ssms_db_user;

----在门户数据库中执行创建语句。
ACCEPT ppms_db_user CHAR prompt 'input MM.SSMS DB username:' 

create synonym v_content_device        for &ppms_db_user .v_content_device       ;

--------------------end----------------------------


--新增应用支持的品牌字段。 
alter table T_R_GCONTENT add brand varchar2(4000);
-- Add comments to the columns 
comment on column T_R_GCONTENT.brand
  is '应用支持的品牌';

--需要对终端门户mopas的t_r_gcontent物化视图重新创建。
--请在mopas用户执行以下脚本
---------------start--------------------------------------------------
drop materialized view t_r_gcontent;

create materialized view t_r_gcontent as select * from s_r_gcontent;
--更改其他对其他物化视图的影响

alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;


--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);

----------------end-----------------------------------------------------

---新增数据导出菜单权限
insert into t_right r values('0_0801_RES_DATA_EXPORT','内容数据导出','内容数据导出','2_0801_RESOURCE',0);

--添加版本
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.037_SSMS','MM1.0.0.040_SSMS');


commit;