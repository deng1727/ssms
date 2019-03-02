--����037�汾T_CATEGORY_RULE������
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
'���ܲ��Թ����������ܵ���ع���';

comment on column T_CATEGORY_RULE.CID is
'�����Ӧ�Ļ��ܵĻ�������';

comment on column T_CATEGORY_RULE.RULEID is
'���ܶ�Ӧ�Ĺ���ID';

comment on column T_CATEGORY_RULE.LASTEXCUTETIME is
'�ϴ�ִ��ʱ�䣬��Ҫ��ȷ����';

comment on column T_CATEGORY_RULE.EFFECTIVETIME is
'������Чʱ��';

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
'���Թ����';

comment on column T_CATERULE.RULEID is
'����Ψһ��ID';

comment on column T_CATERULE.RULENAME is
'��������';

comment on column T_CATERULE.RULETYPE is
'�������� 0��ˢ�»�������Ʒ��1����������Ʒ����˳��
';

comment on column T_CATERULE.INTERVALTYPE is
'ִ��ʱ�������� 0���죻1���ܣ�2����
';

comment on column T_CATERULE.EXCUTEINTERVAL is
'ִ�е�ʱ����';

comment on column T_CATERULE.EXCUTETIME is
'��һ��ʱ����֮�ڵ�ִ�����ӡ�
���IntervalType=0�����ֶ���Ч��
���IntervalType=1�����ֶα�ʾ���ܼ�ִ�С�
���IntervalType=2�����ֶα�ʾ���µĵڼ���ִ�С�
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
'���Թ���������';

comment on column T_CATERULE_COND.RULEID is
'����Ψһ��ID';

comment on column T_CATERULE_COND.CID is
'��ȡ���ܵĻ������룬���CondType=0���ֶ���Ч
';

comment on column T_CATERULE_COND.CONDTYPE is
'�������� 0���Ӳ�Ʒ���ȡ��1���Ӿ�Ʒ���ȡ
';

comment on column T_CATERULE_COND.WSQL is
'��ȡ������sql';

comment on column T_CATERULE_COND.OSQL is
'��ȡ������sql';

comment on column T_CATERULE_COND.COUNT is
'��ȡ����Ʒ����';

comment on column T_CATERULE_COND.SORTID is
'���������ָһ�������Ӧ������֮���ִ�д���';

/*==============================================================*/
/* Index: IDX_COND_RULEID                                       */
/*==============================================================*/
create index IDX_COND_RULEID on T_CATERULE_COND (
   RULEID ASC
);

/*==============================================================*/
/* T_CATEGORY_RULE������                                        */
/*==============================================================*/
delete from T_CATEGORY_RULE;

-----------------------------------
--        ����:WWW����           --
-----------------------------------

------------------------------------
--        �������ͣ�����          --
------------------------------------

--һ�����ܣ�WWW���
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
--һ�����ܣ�WWW��Ϸ
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
--һ�����ܣ�WWW����
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
--        �������ͣ����          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810803', 2, NULL, SYSDATE);
------------------------------------
--        �������ͣ�����          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1814487', 3, NULL, SYSDATE);
------------------------------------
--        �������ͣ��Ƽ�          --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810805', 4, NULL, SYSDATE);
------------------------------------
--        �������ͣ�������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815682', 5, NULL, SYSDATE);
------------------------------------
--        �������ͣ�������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1815681', 6, NULL, SYSDATE);
------------------------------------
--        �������ͣ�������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1810802', 7, NULL, SYSDATE);

------------------------------------
--        �������ͣ�Ʒ��          --
------------------------------------
--OPHONE
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842657', 8, NULL, SYSDATE);
--ŵ����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842658', 9, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842659', 10, NULL, SYSDATE);
--���ᰮ����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842660', 11, NULL, SYSDATE);
--LG
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842662', 12, NULL, SYSDATE);
--Ħ������
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842661', 13, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842663', 14, NULL, SYSDATE);
--���մ�
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842664', 15, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1860924', 16, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1860925', 17, NULL, SYSDATE);
--������
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881309', 18, NULL, SYSDATE);
--CECT
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881454', 19, NULL, SYSDATE);
--����Ѷ
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881455', 20, NULL, SYSDATE);
--imate
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881456', 21, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842666', 22, NULL, SYSDATE);
--UT˹�￵
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
--����������
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881462', 28, NULL, SYSDATE);
--��Ѷ
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881463', 29, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881464', 30, NULL, SYSDATE);
--��Ѷ
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881465', 31, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881466', 32, NULL, SYSDATE);
--��˶
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881468', 33, NULL, SYSDATE);
--��¼
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881467', 34, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881470', 35, NULL, SYSDATE);
--���
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881469', 36, NULL, SYSDATE);
--�Ź�TCT
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881471', 37, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881472', 38, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881473', 39, NULL, SYSDATE);
--�ִ��
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881474', 40, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881475', 41, NULL, SYSDATE);
--ʢ̩
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881476', 42, NULL, SYSDATE);
--���
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881477', 43, NULL, SYSDATE);
--������
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881478', 44, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881516', 45, NULL, SYSDATE);
--�к�
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881479', 46, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881480', 47, NULL, SYSDATE);
--��֥
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881481', 48, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881482', 49, NULL, SYSDATE);
--����
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
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1842665', 55, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881488', 56, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881489', 57, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881490', 58, NULL, SYSDATE);
--��Ϊ
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881491', 59, NULL, SYSDATE);
--��̩
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881492', 60, NULL, SYSDATE);
--����Խ
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881493', 61, NULL, SYSDATE);
--��ݮ
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881494', 62, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881495', 63, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881496', 64, NULL, SYSDATE);
--��ŷ��
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881497', 65, NULL, SYSDATE);
--������
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881498', 66, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881499', 67, NULL, SYSDATE);
--��ά
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881500', 68, NULL, SYSDATE);
--Palm
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881501', 69, NULL, SYSDATE);
--��������
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
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881505', 73, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881506', 74, NULL, SYSDATE);
--��һ
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881507', 75, NULL, SYSDATE);
--�û�
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881508', 76, NULL, SYSDATE);
--����
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881509', 77, NULL, SYSDATE);
--�ִ�
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881510', 78, NULL, SYSDATE);
--Ħ�մ�
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881511', 79, NULL, SYSDATE);
--��̩
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881512', 80, NULL, SYSDATE);
--��è
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881513', 81, NULL, SYSDATE);
--���
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881514', 82, NULL, SYSDATE);
--����Ʒ��
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1881515', 83, NULL, SYSDATE);

-----------------------------------
--     ����:���Ű��ն˻���       --
-----------------------------------

------------------------------------
--        �������ͣ�����          --
------------------------------------

--�������ܣ�Ӧ�����
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

--�������ܣ��ֻ���Ϸ
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

--�������ܣ��ֻ�����
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
--      �������ͣ�������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257460', 84, NULL, SYSDATE);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257459', 85, NULL, SYSDATE);
------------------------------------
--      �������ͣ�����Ƽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257457', 86, NULL, SYSDATE);
------------------------------------
--      �������ͣ�����Ǽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257461', 87, NULL, SYSDATE);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257458', 88, NULL, SYSDATE);
------------------------------------
--      �������ͣ���Ϸ���        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257462', 89, NULL, SYSDATE);
------------------------------------
--      �������ͣ���Ϸ����        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257463', 90, NULL, SYSDATE);
------------------------------------
--      �������ͣ���Ϸ�Ƽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257464', 91, NULL, SYSDATE);
------------------------------------
--      �������ͣ���Ϸ�Ǽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257465', 92, NULL, SYSDATE);
------------------------------------
--      �������ͣ���Ϸ����        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257466', 93, NULL, SYSDATE);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257467', 94, NULL, SYSDATE);
------------------------------------
--      �������ͣ���������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257468', 95, NULL, SYSDATE);
--      �������ͣ������Ƽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257469', 96, NULL, SYSDATE);
------------------------------------
--      �������ͣ������Ǽ�        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257470', 97, NULL, SYSDATE);
------------------------------------
--      �������ͣ���������        --
------------------------------------
Insert into T_CATEGORY_RULE
   (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
 Values
   ('1257471', 98, NULL, SYSDATE);

/*==============================================================*/
/* T_CATERULE������                                             */
/*==============================================================*/
delete from T_CATERULE;

--������ܹ���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (1, '����', 1, 0, 1,
    NULL);
--WWW��ѻ��ܹ���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (2, '���', 0, 0, 1,
    NULL);
--WWW���»��ܹ���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (3, '����', 0, 0, 1,
    NULL);
--WWW�Ƽ����ܹ���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (4, '�Ƽ�', 0, 0, 1,
    NULL);
--WWW�����л��ܹ���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (5, '������', 0, 1, 1,
    1);
--WWW�����л��ܹ���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (6, '������', 0, 2, 1,
    1);
--WWW�����л��ܹ���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (7, '������', 0, 0, 1,
    NULL);

------------------------------------
--        Ʒ�ƻ��ܹ���            --
------------------------------------
--OPHONE
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (8, 'OPHONE', 0, 0, 1,
    NULL);
--ŵ����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (9, 'ŵ����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (10, '����', 0, 0, 1,
    NULL);
--���ᰮ����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (11, '���ᰮ����', 0, 0, 1,
    NULL);
--LG
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (12, 'LG', 0, 0, 1,
    NULL);
--Ħ������
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (13, 'Ħ������', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (14, '����', 0, 0, 1,
    NULL);
--���մ�
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (15, '���մ�', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (16, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (17, '����', 0, 0, 1,
    NULL);
--������
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (18, '������', 0, 0, 1,
    NULL);
--CECT
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (19, 'CECT', 0, 0, 1,
    NULL);
--����Ѷ
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (20, '����Ѷ', 0, 0, 1,
    NULL);
--imate
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (21, 'imate', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (22, '����', 0, 0, 1,
    NULL);
--UT˹�￵
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (23, 'UT˹�￵', 0, 0, 1,
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
--����������
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (28, '����������', 0, 0, 1,
    NULL);
--��Ѷ
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (29, '��Ѷ', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (30, '����', 0, 0, 1,
    NULL);
--��Ѷ
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (31, '��Ѷ', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (32, '����', 0, 0, 1,
    NULL);
--��˶
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (33, '��˶', 0, 0, 1,
    NULL);
--��¼
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (34, '��¼', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (35, '����', 0, 0, 1,
    NULL);
--���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (36, '���', 0, 0, 1,
    NULL);
--�Ź�TCT
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (37, '�Ź�TCT', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (38, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (39, '����', 0, 0, 1,
    NULL);
--�ִ��
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (40, '�ִ��', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (41, '����', 0, 0, 1,
    NULL);
--ʢ̩
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (42, 'ʢ̩', 0, 0, 1,
    NULL);
--���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (43, '���', 0, 0, 1,
    NULL);
--������
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (44, '������', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (45, '����', 0, 0, 1,
    NULL);
--�к�
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (46, '�к�', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (47, '����', 0, 0, 1,
    NULL);
--��֥
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (48, '��֥', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (49, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (50, '����', 0, 0, 1,
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
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (55, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (56, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (57, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (58, '����', 0, 0, 1,
    NULL);
--��Ϊ
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (59, '��Ϊ', 0, 0, 1,
    NULL);
--��̩
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (60, '��̩', 0, 0, 1,
    NULL);
--����Խ
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (61, '����Խ', 0, 0, 1,
    NULL);
--��ݮ
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (62, '��ݮ', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (63, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (64, '����', 0, 0, 1,
    NULL);
--��ŷ��
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (65, '��ŷ��', 0, 0, 1,
    NULL);
--������
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (66, '������', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (67, '����', 0, 0, 1,
    NULL);
--��ά
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (68, '��ά', 0, 0, 1,
    NULL);
--Palm
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (69, 'Palm', 0, 0, 1,
    NULL);
--��������
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (70, '��������', 0, 0, 1,
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
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (73, '����', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (74, '����', 0, 0, 1,
    NULL);
--��һ
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (75, '��һ', 0, 0, 1,
    NULL);
--�û�
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (76, '�û�', 0, 0, 1,
    NULL);
--����
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (77, '����', 0, 0, 1,
    NULL);
--�ִ�
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (78, '�ִ�', 0, 0, 1,
    NULL);
--Ħ�մ�
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (79, 'Ħ�մ�', 0, 0, 1,
    NULL);
--��̩
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (80, '��̩', 0, 0, 1,
    NULL);
--��è
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (81, '��è', 0, 0, 1,
    NULL);
--���
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (82, '���', 0, 0, 1,
    NULL);
--����Ʒ��
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (83, '����Ʒ��', 0, 0, 1,
    NULL);

------------------------------------
--      �������ͣ�������        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (84, '������', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (85, '�������', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ�����Ƽ�        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (86, '����Ƽ�', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ�����Ǽ�        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (87, '����Ǽ�', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (88, '�������', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ���Ϸ���        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (89, '��Ϸ���', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ���Ϸ����        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (90, '��Ϸ����', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ���Ϸ�Ƽ�        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (91, '��Ϸ�Ƽ�', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ���Ϸ�Ǽ�        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (92, '��Ϸ�Ǽ�', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ���Ϸ����        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (93, '��Ϸ����', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ��������        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (94, '�������', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ���������        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (95, '��������', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ������Ƽ�        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (96, '�����Ƽ�', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ������Ǽ�        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (97, '�����Ǽ�', 0, 0, 1,
    NULL);
------------------------------------
--      �������ͣ���������        --
------------------------------------
Insert into T_CATERULE
   (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,
    EXCUTETIME)
 Values
   (98, '��������', 0, 0, 1,
    NULL);

/*==============================================================*/
/* T_CATERULE_COND������                                        */
/*==============================================================*/
delete from T_CATERULE_COND;

--�����������
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (1, NULL, 0, NULL, 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    -1, NULL);

--WWW��ѻ��ܹ�������
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

--WWW���»��ܹ�������
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

--WWW�Ƽ����ܹ�������
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

--WWW�����л��ܹ�������
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

--WWW�����л��ܹ�������
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

--WWW�����л��ܹ�������
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
--       Ʒ�ƻ��ܹ�������         --
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
--ŵ����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, '1885167', 1, 'BRAND like ''%ŵ����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%ŵ����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%ŵ����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (9, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%ŵ����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (10, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--���ᰮ����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, '1885167', 1, 'BRAND like ''%���ᰮ����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%���ᰮ����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%���ᰮ����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (11, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%���ᰮ����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
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
--Ħ������
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, '1885167', 1, 'BRAND like ''%Ħ������%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%Ħ������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%Ħ������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (13, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%Ħ������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (14, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--���մ�
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, '1885167', 1, 'BRAND like ''%���մ�%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%���մ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%���մ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (15, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%���մ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (16, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (17, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--������
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, '1885167', 1, 'BRAND like ''%������%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (18, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
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
--����Ѷ
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, '1885167', 1, 'BRAND like ''%����Ѷ%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (20, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
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
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (22, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--UT˹�￵
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, '1885167', 1, 'BRAND like ''%UT˹�￵%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%UT˹�￵%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%UT˹�￵%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (23, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%UT˹�￵%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
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
--����������
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, '1885167', 1, 'BRAND like ''%����������%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (28, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��Ѷ
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, '1885167', 1, 'BRAND like ''%��Ѷ%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (29, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (30, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��Ѷ
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, '1885167', 1, 'BRAND like ''%��Ѷ%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (31, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��Ѷ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (32, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��˶
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, '1885167', 1, 'BRAND like ''%��˶%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��˶%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��˶%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (33, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��˶%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��¼
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, '1885167', 1, 'BRAND like ''%��¼%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��¼%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��¼%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (34, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��¼%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (35, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--���
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, '1885167', 1, 'BRAND like ''%���%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (36, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--�Ź�TCT
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, '1885167', 1, 'BRAND like ''%�Ź�TCT%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%�Ź�TCT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%�Ź�TCT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (37, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%�Ź�TCT%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (38, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (39, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--�ִ��
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, '1885167', 1, 'BRAND like ''%�ִ��%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%�ִ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%�ִ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (40, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%�ִ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (41, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--ʢ̩
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, '1885167', 1, 'BRAND like ''%ʢ̩%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%ʢ̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%ʢ̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (42, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%ʢ̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--���
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, '1885167', 1, 'BRAND like ''%���%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (43, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--������
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, '1885167', 1, 'BRAND like ''%������%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (44, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (45, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--�к�
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, '1885167', 1, 'BRAND like ''%�к�%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%�к�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%�к�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (46, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%�к�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (47, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��֥
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, '1885167', 1, 'BRAND like ''%��֥%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��֥%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��֥%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (48, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��֥%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (49, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (50, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
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
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (55, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (56, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (57, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (58, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��Ϊ
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, '1885167', 1, 'BRAND like ''%��Ϊ%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��Ϊ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��Ϊ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (59, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��Ϊ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��̩
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, '1885167', 1, 'BRAND like ''%��̩%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (60, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����Խ
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, '1885167', 1, 'BRAND like ''%����Խ%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����Խ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����Խ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (61, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����Խ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��ݮ
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, '1885167', 1, 'BRAND like ''%��ݮ%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��ݮ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��ݮ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (62, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��ݮ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (63, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (64, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��ŷ��
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, '1885167', 1, 'BRAND like ''%��ŷ��%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��ŷ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��ŷ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (65, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��ŷ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--������
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, '1885167', 1, 'BRAND like ''%������%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (66, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (67, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��ά
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, '1885167', 1, 'BRAND like ''%��ά%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��ά%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��ά%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (68, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��ά%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--Palm
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (69, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��������
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, '1885167', 1, 'BRAND like ''%��������%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (70, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��������%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
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
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (73, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (74, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��һ
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, '1885167', 1, 'BRAND like ''%��һ%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��һ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��һ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (75, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��һ%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--�û�
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, '1885167', 1, 'BRAND like ''%�û�%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%�û�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%�û�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (76, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%�û�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, '1885167', 1, 'BRAND like ''%����%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (77, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--�ִ�
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, '1885167', 1, 'BRAND like ''%�ִ�%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%�ִ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%�ִ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (78, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%�ִ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--Ħ�մ�
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, '1885167', 1, 'BRAND like ''%Ħ�մ�%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%Ħ�մ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%Ħ�մ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (79, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%Ħ�մ�%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��̩
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, '1885167', 1, 'BRAND like ''%��̩%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (80, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��̩%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--��è
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, '1885167', 1, 'BRAND like ''%��è%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%��è%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%��è%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (81, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%��è%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--���
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, '1885167', 1, 'BRAND like ''%���%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (82, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%���%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);
--����Ʒ��
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, '1885167', 1, 'BRAND like ''%����Ʒ��%''', 'sortID desc,marketDate desc',
    -1, NULL);

Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, NULL, 0, 'type=''nt:gcontent:appSoftWare''and BRAND like ''%����Ʒ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 1);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, NULL, 0, 'type=''nt:gcontent:appGame''and BRAND like ''%����Ʒ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 2);
Insert into T_CATERULE_COND
   (RULEID, CID, CONDTYPE, WSQL, OSQL,
    COUNT, SORTID)
 Values
   (83, NULL, 0, 'type=''nt:gcontent:appTheme''and BRAND like ''%����Ʒ��%''', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',
    10, 3);

------------------------------------
--      �������ͣ�������        --
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
--      �������ͣ��������        --
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
--      �������ͣ�����Ƽ�        --
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
--      �������ͣ�����Ǽ�        --
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
--      �������ͣ��������        --
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
--      �������ͣ���Ϸ���        --
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
--      �������ͣ���Ϸ����        --
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
--      �������ͣ���Ϸ�Ƽ�        --
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
--      �������ͣ���Ϸ�Ǽ�        --
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
--      �������ͣ���Ϸ����        --
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
--      �������ͣ��������        --
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
--      �������ͣ���������        --
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
--      �������ͣ������Ƽ�        --
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
--      �������ͣ������Ǽ�        --
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
--      �������ͣ���������        --
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

--������ͼ�ű�v_cm_content��ȥ���ж�����c.status = '0008'
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


--ssms �û��� ͨ��PPMS��DBLINK�ķ�ʽ����ͬ���CM_CT_DEVICE��T_DEVICE_BRAND
---Ŀǰ�����Ļ���ָ��PPMS��DBLINK����Ϊ��DL_PPMS_DEVICE
------------------------------start------------------------

create synonym CM_CT_DEVICE        for &db_user .DL_PPMS_DEVICE       ;
create synonym T_DEVICE_BRAND        for &db_user .DL_PPMS_DEVICE       ;

--------------------------------end----------------------------------

--��������MMӦ���Ѿ�֧�ֵĻ����б���ͼ
create or replace view v_content_device
as
select distinct d.adapte_id as device_id
from CM_CT_DEVICE d,CM_CONTENT c
where c.contentid=d.contentid 
  and c.STATUS in ('0006','1006')
  and d.adapte_type=0;

---��Ҫ��v_content_device��ͼ�����Ż�����ͬ���ʹ�á�
--�ֱ���wwwpas��mopas��pcpas�����Ż�����ͬ��ʡ�����������¡�
--��Ŀǰ��������һ�����ݿ�ʵ�������Ա��������ű���ʱֻ�ṩͬһ��ʵ���µĴ���ͬ��ʽű�����
--------------------start--------------------------
---��ssms�û�ִ����Ȩ���
ACCEPT ssms_db_user CHAR prompt 'input MM.PAS DB username:' 

grant select on v_content_device        to &ssms_db_user;

----���Ż����ݿ���ִ�д�����䡣
ACCEPT ppms_db_user CHAR prompt 'input MM.SSMS DB username:' 

create synonym v_content_device        for &ppms_db_user .v_content_device       ;

--------------------end----------------------------


--����Ӧ��֧�ֵ�Ʒ���ֶΡ� 
alter table T_R_GCONTENT add brand varchar2(4000);
-- Add comments to the columns 
comment on column T_R_GCONTENT.brand
  is 'Ӧ��֧�ֵ�Ʒ��';

--��Ҫ���ն��Ż�mopas��t_r_gcontent�ﻯ��ͼ���´�����
--����mopas�û�ִ�����½ű�
---------------start--------------------------------------------------
drop materialized view t_r_gcontent;

create materialized view t_r_gcontent as select * from s_r_gcontent;
--���������������ﻯ��ͼ��Ӱ��

alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;


--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);

----------------end-----------------------------------------------------

---�������ݵ����˵�Ȩ��
insert into t_right r values('0_0801_RES_DATA_EXPORT','�������ݵ���','�������ݵ���','2_0801_RESOURCE',0);

--��Ӱ汾
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.037_SSMS','MM1.0.0.040_SSMS');


commit;