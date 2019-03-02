
-- Create table
create table t_wp_content
(
  ID   VARCHAR2(32)not null,
  appname       VARCHAR2(100)not null,
  appurl        VARCHAR2(512)not null,
  appid         VARCHAR2(50)not null,
  applogo       VARCHAR2(512),
  apptype1      VARCHAR2(30),
  apptype2      VARCHAR2(30),
  appdetail     VARCHAR2(2048),
  appsp         VARCHAR2(100),
  appversion    VARCHAR2(20),
  appprice      NUMBER(10,2),
  appscore      VARCHAR2(20),
  appscorenum   NUMBER(10),
  apppic        VARCHAR2(200),
  appsize       VARCHAR2(20),
  appupdatedate VARCHAR2(20),
  appsupportsys VARCHAR2(50),
  apprelates    VARCHAR2(400),
  Pic1 VARCHAR2(512),
  Pic2 VARCHAR2(512),
  Pic3 VARCHAR2(512),
  Pic4 VARCHAR2(512),
  Pic5 VARCHAR2(512),
  Pic6 VARCHAR2(512),
  Pic7 VARCHAR2(512),
  Pic8 VARCHAR2(512),
  updatetime date
)
-- Add comments to the columns 
comment on column t_wp_content.id
  is '��������ID';
comment on column t_wp_content.appname
  is 'Ӧ�õ�������Ϣ';
comment on column t_wp_content.appurl
  is 'Ӧ����WP�ĵ�ַ��Ϣ';
comment on column t_wp_content.appid
  is 'Ӧ�õ�ID';
comment on column t_wp_content.applogo
  is 'Ӧ��LOGO���ļ�����';
comment on column t_wp_content.apptype1
  is 'һ������';
comment on column t_wp_content.apptype2
  is '��������';
comment on column t_wp_content.appdetail
  is '˵����Ϣ(����������ϸ��Ϣ���֡�)��������ȣ�2048';
comment on column t_wp_content.appsp
  is '������';
comment on column t_wp_content.appversion
  is '�汾˵��';
comment on column t_wp_content.appprice
  is '�۸�';
comment on column t_wp_content.appscore
  is '����';
comment on column t_wp_content.appscorenum
  is '���ִ���';
comment on column t_wp_content.apppic
  is '��ͼ���ļ�����';
comment on column t_wp_content.appsize
  is '���ش�С';
comment on column t_wp_content.appupdatedate
  is '����ʱ��';
comment on column t_wp_content.appsupportsys
  is 'ϵͳ';
comment on column t_wp_content.apprelates
  is '���Ӧ��';
comment on column t_wp_content.Pic1
  is '��ͼ1';
comment on column t_wp_content.Pic2
  is '��ͼ2';
comment on column t_wp_content.Pic3
  is '��ͼ3';
comment on column t_wp_content.Pic4
  is '��ͼ4';
comment on column t_wp_content.Pic5
  is '��ͼ5';
comment on column t_wp_content.Pic6
  is '��ͼ6';
comment on column t_wp_content.Pic7
  is '��ͼ7';
comment on column t_wp_content.Pic8
  is '��ͼ8';
comment on column t_wp_content.updatetime
  is '����ʱ��';

-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_content
  add constraint pk_twp_content_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_appid on t_wp_content ( appid);

-- Create table
create sequence SEQ_t_wp_content_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;




-- Create table
create table t_wp_category
(
  id         varchar2(32),
  categoryid varchar2(32),
  cname      varchar2(500),
  cdesc      varchar2(2000),
  pic        varchar2(500),
  isshow     varchar2(2),
  parentcid  varchar2(32),
  sortid     number(8),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_wp_category.id
  is '��������ID';
comment on column t_wp_category.categoryid
  is '���ܱ���ID';
comment on column t_wp_category.cname
  is '��������';
comment on column t_wp_category.cdesc
  is 'wp��������';
comment on column t_wp_category.pic
  is 'wp����ͼƬ';
comment on column t_wp_category.isshow
  is '�Ƿ����Ż�չʾ��1��չʾ��0����չʾ';
comment on column t_wp_category.parentcid
  is '�����ܱ���ID';
comment on column t_wp_category.sortid
  is '�����';
comment on column t_wp_category.lupdate
  is 'ͬ��������ʱ��';

-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_category
  add constraint pk_twp_cate_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_cateid on t_wp_category ( categoryid);


create sequence SEQ_T_WP_category_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


-- Create table
create table t_wp_reference
(
  id         varchar2(32),
  appid      varchar2(50),
  categoryid varchar2(32),
  cname      varchar2(100),
  sortid     number(8),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_wp_reference.id
  is '��������ID';
comment on column t_wp_reference.appid
  is 'Ӧ��ID';
comment on column t_wp_reference.categoryid
  is '����ID';
comment on column t_wp_reference.cname
  is 'Ӧ������';
comment on column t_wp_reference.sortid
  is '�����';
comment on column t_wp_reference.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_reference
  add constraint pk_twp_ref_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_appid_cateid on t_wp_reference (appid, categoryid);


create sequence SEQ_T_WP_reference_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


-- Create table
create table t_wp_tactic
(
  id         varchar2(32),
  apptype    varchar2(60),
  categoryid varchar2(32),
  tatype     varchar2(32),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_wp_tactic.id
  is '��������ID';
comment on column t_wp_tactic.apptype
  is 'Ӧ�����';
comment on column t_wp_tactic.categoryid
  is '���ܱ���ID';
comment on column t_wp_tactic.tatype
  is '�������ͣ�����';
comment on column t_wp_tactic.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_tactic
  add constraint idx_t_wp_static_id primary key (ID);

create sequence SEQ_T_WP_tactic_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

-- Create the synonym 
create or replace synonym CM_RESOURCE_SERVER
  for CM_RESOURCE_SERVER@DL_MM_PPMS_NEW;
  


create sequence SEQ_T_WP_category_CID
minvalue 11000
maxvalue 999999999999
start with 11000
increment by 1
nocache
cycle;

-- Add/modify columns 
alter table T_WP_TACTIC add type VARCHAR2(60);
alter table T_WP_TACTIC modify tatype default 0;
alter table T_WP_TACTIC modify lupdate default sysdate;
-- Add comments to the columns 
comment on column T_WP_TACTIC.type
  is 'һ������';
comment on column T_WP_TACTIC.tatype
  is '�������ͣ�Ĭ��0�����ã�';
  
 ---------------------------- ----------------------------
 -------------�洢���̿�ʼ����Ҫ����ִ��-----------------
create or replace procedure P_WP_REFRESHCATEREF is
--�Ӷ������
v_nstatus     number;
begin
--��ش洢��������İ�
  v_nstatus := pg_log_manage.f_startlog('P_WP_REFRESHCATEREF',
                                        'WPӦ�÷��������Ʒ�ϼܣ�����P_WP_REFRESHCATEREF');

   ---- select t.categoryid from T_WP_CATEGORY t,t_wp_tactic s,t_wp_reference r,t_wp_content c where c.appid=r.appid and r.categoryid=t.categoryid and s.categoryid=t.categoryid and t.parentcid='10001' and c.apptype2 != s.apptype
-- �¼ܱ������������Ӧ��
delete from t_wp_reference where id in (
select r.id from T_WP_CATEGORY t,t_wp_tactic s,t_wp_reference r,t_wp_content c where c.appid=r.appid and r.categoryid=t.categoryid and s.categoryid=t.categoryid and t.parentcid='10001' and c.apptype2 != s.apptype and c.apptype1='Ӧ��');
-- �¼ܱ��������������Ϸ
delete from t_wp_reference where id in (
select r.id from T_WP_CATEGORY t,t_wp_tactic s,t_wp_reference r,t_wp_content c where c.appid=r.appid and r.categoryid=t.categoryid and s.categoryid=t.categoryid and t.parentcid='10002' and c.apptype2 != s.apptype and c.apptype1='��Ϸ');

-- ���ݷ����ϼܲ����ϼ�δ�ϼܷ�����ܵ�Ӧ�õ��������
   for vc in (select t.apptype,t.categoryid,t.type from T_WP_TACTIC t) loop
insert into t_wp_reference
  select SEQ_T_WP_reference_ID.Nextval,
         appid,
         vc.categoryid,
         appname   as cname,
         rownum                as         sortid,
         sysdate                as        lupdate
    from (select * from   (select c.appid, c.appname, c.updatetime
             from t_wp_content c
            where c.apptype2 = vc.apptype
              and c.apptype1 =  vc.type ) s1 where not exists
          (select 1
             from (select r.appid
                     from t_wp_reference r
                    where r.categoryid = vc.categoryid ) s2
            where s1.appid = s2.appid ) order by s1.updatetime asc);

  end loop;
  --- �¼��Ѿ����ߵ�Ӧ�õ���Ʒ
  delete from t_wp_reference r where not exists (select 1 from t_wp_content c where c.appid=r.appid);
  
  
  --����ɹ�����ִ�����д����־
  v_nstatus := pg_log_manage.f_successlog;
  commit;
exception
  when others then
    rollback;
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;

 ---------------------------- ----------------------------
 -------------�洢���̽�������Ҫ����ִ��-----------------
------------��ʼ��WP�������ݿ�ʼ----------
insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10000', '�����������', '�����������', '', '1', '-1', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10001', 'Ӧ��', 'Ӧ��', '', '1', '10000', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10002', '��Ϸ', '��Ϸ', '', '1', '10000', 1, to_date('19-01-2015', 'dd-mm-yyyy'));


insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '���ְ���', '���ְ���', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '������Ƶ', '������Ƶ', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, 'ʵ�ù���', 'ʵ�ù���', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��������', '��������', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '����Ӧ��', '����Ӧ��', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��������', '��������', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '���ε���', '���ε���', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��������', '��������', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��Ƭ��ֽ', '��Ƭ��ֽ', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '�罻����', '�罻����', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '�����˶�', '�����˶�', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '�������', '�������', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '����칫', '����칫', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '�鿯�Ķ�', '�鿯�Ķ�', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '������ѧ', '������ѧ', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, 'ʱ��Ҫ��', 'ʱ��Ҫ��', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '����ð��', '����ð��', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '������Ϸ', '������Ϸ', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '������Ϸ', '������Ϸ', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��������', '��������', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��ͥͬ��', '��ͥͬ��', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '���ֽ���', '���ֽ���', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, 'ƽ̨����', 'ƽ̨����', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, 'Ȥζ����', 'Ȥζ����', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��������', '��������', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '��ɫ����', '��ɫ����', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '�����Ϸ', '�����Ϸ', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '�˶�����', '�˶�����', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, 'ս��ģ��', 'ս��ģ��', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, 'Xbox����', 'Xbox����', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));


insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10100', '�����񵥻���', '�����񵥻���', '', '1', '-1', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10200', '����ר�����', '����ר�����', '', '1', '-1', 1, to_date('19-01-2015', 'dd-mm-yyyy'));


insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '���ְ���', '11000', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '������Ƶ', '11001', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, 'ʵ�ù���', '11002', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��������', '11003', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '����Ӧ��', '11004', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��������', '11005', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '���ε���', '11006', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��������', '11007', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��Ƭ��ֽ', '11008', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '�罻����', '11009', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '�����˶�', '11010', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '�������', '11011', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '����칫', '11012', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '�鿯�Ķ�', '11013', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '������ѧ', '11014', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, 'ʱ��Ҫ��', '11015', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), 'Ӧ��');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '����ð��', '11016', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '������Ϸ', '11017', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '������Ϸ', '11018', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��������', '11019', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��ͥͬ��', '11020', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '���ֽ���', '11021', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, 'ƽ̨����', '11022', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, 'Ȥζ����', '11023', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��������', '11024', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '��ɫ����', '11025', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '�����Ϸ', '11026', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '�˶�����', '11027', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, 'ս��ģ��', '11028', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, 'Xbox����', '11029', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '��Ϸ');

commit;
------------��ʼ��WP�������ݽ���----------


-----MM5.1��������ר�������Զ����³�ʼ�����ݿ�ʼ-----
declare 
  cateruleid number(8);
begin

insert into t_caterule_cond_base(base_id,base_name,base_sql) values(82,'��ȡMM5.1��������AP��Ϣ','select g.id from t_r_gcontent g where  g.icpcode = ''138690''');

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1��������ר��',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555193',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,null,'g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1��������ר��-��������',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555194',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''��������''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1��������ר��-����ɫ��',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555195',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''����ɫ��''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1��������ר��-��ͨ��Ϸ',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555196',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''��ͨ��Ϸ''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1��������ר��-���ո�Ц',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555197',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''���ո�Ц''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

insert into t_a_auto_category(id,categoryid,isnulltosync) values(86,'100038586','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(87,'100038587','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(88,'100038588','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(89,'100038589','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(90,'100038590','1');

commit;
end;

-----MM5.1��������ר�������Զ����³�ʼ�����ݽ���-----




