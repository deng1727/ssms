-- Add/modify columns 
alter table T_RB_BOOK_NEW add createDate date default SYSDATE;
-- Add comments to the columns 
comment on column T_RB_BOOK_NEW.createDate
  is '�������ʱ��';
  
  
  
  ---���ඩ��
-- Create table���ඩ�������
create table t_rb_type_detail
(
  bookid    varchar2(30),
  ordertime date,
  lupdate   date,
  typeid    varchar2(200)
)
;
-- Add comments to the columns 
comment on column t_rb_type_detail.bookid
  is 'ͼ��ID';
comment on column t_rb_type_detail.ordertime
  is '����ʱ��';
comment on column t_rb_type_detail.lupdate
  is '������ʱ��';
comment on column t_rb_type_detail.typeid
  is '����ID';
  
  -- Create table���ඩ�Ļ��ܱ�
create table t_rb_type_count
(
  id  varchar2(30),
  name varchar2(300),
  descs varchar2(4000),
  picurl varchar2(400),
  counts   number(11),
  categoryid   varchar2(30), 
  cid   varchar2(30),
  lupdate date
)
;
-- Add comments to the columns 
comment on column t_rb_type_count.id
  is '����ID';
comment on column t_rb_type_count.name
  is '��������';
  comment on column t_rb_type_count.descs
  is '��������';
  comment on column t_rb_type_count.picurl
  is '����ͼƬ';
    comment on column t_rb_type_count.counts
  is '��������';
    comment on column t_rb_type_count.categoryid
  is '������Ӧ����ID:1001, 1002, 1003, 10041, 1005, 1007, 1008, 1009,
        1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017';
    comment on column t_rb_type_count.cid
  is '������Ӧ���ܱ�����ID';
comment on column t_rb_type_count.lupdate
  is '������ʱ��';
 
 ----��ʼ������ 
  insert into t_rb_type_count (id,name,descs,picurl,counts,categoryid,cid,lupdate)
select t.typeid,
       n.categoryname,
       n.decrisption,
       'http://u5.mm-img.com:80/rs/res/book/ssms/bookorder/'||n.categoryid||'.png' as  picurl,
       0 as counts,
       n.categoryid,  n.id,sysdate as lupdate
  from t_rb_category_new n, t_rb_type t
 where n.parentid = '21307'
    and n.delflag='0'
   and t.typename = n.categoryname;

  ----���߶���
  -- Create table���߶��������
create table t_rb_author_detail
(
  bookid    varchar2(30),
  ordertime date,
  lupdate   date,
  authorid    varchar2(200)
)
;
-- Add comments to the columns 
comment on column t_rb_author_detail.bookid
  is 'ͼ��ID';
comment on column t_rb_author_detail.ordertime
  is '����ʱ��';
comment on column t_rb_author_detail.lupdate
  is '������ʱ��';
comment on column t_rb_author_detail.authorid
  is '����ID';
  
  -- Create table���߶��Ļ��ܱ�
create table t_rb_author_count
(
  id  varchar2(30),
  name varchar2(300),
  descs varchar2(4000),
  picurl varchar2(400),
  counts   number(11),
  lupdate date
)
;
-- Add comments to the columns 
comment on column t_rb_author_count.id
  is '����ID';
comment on column t_rb_author_count.name
  is '��������';
  comment on column t_rb_author_count.descs
  is '��������';
  comment on column t_rb_author_count.picurl
  is '����ͼƬ';
    comment on column t_rb_author_count.counts
  is '��������';
comment on column t_rb_author_count.lupdate
  is '������ʱ��';
  
  
  ----���߳�ʼ��
  insert into t_rb_author_count (id,name,descs,picurl,counts,lupdate)
  select t.authorid,
         t.authorname,
         t.authordesc,
         t.authorpic,
         0            as counts,
         sysdate      as lupdate
    from t_rb_author_new t;
    
    commit;
  
  
  -----���ඩ�ĵ�ǰ(��ʷ)������ͼ
create or replace view v_rb_typeord_his as
select b.bookid,b.bookname,s.clicknum,r.categoryid,y.typeid as subtypeid,y.parentid,y.typename
  from t_rb_book_new       b,
       t_rb_statistics_new s,
       t_rb_reference_new  r,
       t_rb_type           y
 where b.bookid = r.bookid
   and b.bookid = s.contentid
   and y.typeid = b.typeid
   and b.delflag='0'
   and y.parentid is not null
   and y.parentid != '0'
   and r.categoryid in
       ('1001', '1002', '1003', '10041', '1005', '1007', '1008', '1009',
        '1010', '1011', '1012', '1013', '1014', '1015', '1016', '1017');
        
   -----���߶��ĵ�ǰ(��ʷ)������ͼ      
create or replace view v_rb_authorord_his as
select b.bookid,b.bookname,b.lupdate,y.authorid,y.authorname
  from t_rb_book_new       b,
       t_rb_author_new           y
 where b.authorid = y.authorid
      and b.delflag='0';
      
      create table t_rb_typeord_his as select * from v_rb_typeord_his  ; 
  
     create table t_rb_authorord_his as select * from v_rb_authorord_his  ; 
                
                
----------------�洢����
create or replace procedure P_baseRead_count as
  v_nrecod      number;
  v_nstatus number; --��¼��ذ�״̬

  v_sql_insert varchar2(3000);
  v_sql_del    varchar2(1200);
  v_sql_count  varchar2(1200);

  CURSOR cateids IS
    select CATEGORYID from t_rb_type_count group by CATEGORYID; --�õ��������id

  v_categoryType cateids%rowtype; --�������id

begin

  v_nstatus := pg_log_manage.f_startlog('P_baseRead_count',
                                        '����ͼ��������߶�������ͳ�ƽ��');

  for v_categoryType in cateids LOOP
  
    -- �õ���ǰ�����£���ͼ�д��ڣ����������ݲ����ڵ����ݡ�����ԱȺ���������ʱ���С�
    v_sql_insert := 'insert into t_rb_type_detail t (BOOKID, ORDERTIME, LUPDATE, TYPEID) 
    select bookid, sysdate, sysdate, parentid
    from (select *
          from (select *
                  from v_rb_typeord_his vs
                 where vs.categoryid = ' ||
                    v_categoryType.CATEGORYID || '
                 order by vs.clicknum desc) t
         where rownum < 501) v
         where v.bookid not in (select bookid
                          from (select *
                                  from t_rb_typeord_his vt
                                 where vt.categoryid = ' ||
                    v_categoryType.CATEGORYID || '
                                 order by vt.clicknum desc) t
                         where rownum < 501)';
  
    --DBMS_OUTPUT.put_line(v_sql_insert);
    execute immediate v_sql_insert;
    --DBMS_OUTPUT.put_line('==================================================================================');
  END LOOP;

  -- ȥ�����ݱ����ظ����ݡ�ֻ��������ʱ������
  v_sql_del := 'delete from t_rb_type_detail a
  where a.lupdate <
       (select max(b.lupdate)
          from t_rb_type_detail b
         where b.bookid = a.bookid and b.typeid = a.typeid)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;

  -- ɾ������ͼ��
  v_sql_del := 'delete from t_rb_type_detail a where not exists (select 1 from t_rb_book_new n where n.bookid = a.bookid and n.delflag=0)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;
  
  for rec in (select typeid, count(1) countnum from t_rb_type_detail group by typeid) LOOP

    -- �õ���ǰ�����£���ͼ�д��ڣ����������ݲ����ڵ����ݡ�����ԱȺ���������ʱ���С�
    v_sql_count := 'update t_rb_type_count c set c.counts= ' ||
                   rec.countnum ||
                   ', c.lupdate=sysdate where c.id = ' || rec.typeid ;
  
    --DBMS_OUTPUT.put_line(v_sql_count);
    execute immediate v_sql_count;
    --DBMS_OUTPUT.put_line('==================================================================================');
  END LOOP;
  
  execute immediate 'delete from t_rb_typeord_his';
  
  execute immediate 'insert into t_rb_typeord_his select * from v_rb_typeord_his';
  
   commit;
   v_nrecod:=0;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );

exception

  when others then
    rollback;
    --���ʧ�ܣ���ִ�����д����־
    DBMS_OUTPUT.put_line('������');
    v_nstatus := pg_log_manage.f_errorlog;
end;


create or replace procedure P_baseRead_author_count as
  v_nrecod      number;
  v_nstatus number; --��¼��ذ�״̬

  v_sql_insert varchar2(3000);
  v_sql_del    varchar2(1200);
  v_sql_count  varchar2(1200);

begin

    v_nstatus := pg_log_manage.f_startlog('P_baseRead_author_count',
                                        '����ͼ��������߶�������ͳ�ƽ��');

  v_sql_insert :='insert into t_rb_author_detail (BOOKID, ordertime, lupdate, authorid)
 select bookid, sysdate, sysdate, t.authorid
    from t_rb_book_new t where to_char(t.createdate, ''YYYYMMDD'') = to_char(sysdate, ''YYYYMMDD'') and  delflag=0'; 
  DBMS_OUTPUT.put_line(v_sql_insert);
  execute immediate v_sql_insert;

  -- ȥ�����ݱ����ظ����ݡ�ֻ��������ʱ������
  v_sql_del := 'delete from t_rb_author_detail a
  where a.lupdate <
       (select max(b.lupdate)
          from t_rb_author_detail b
         where b.bookid = a.bookid and b.authorid = a.authorid)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;

  -- ɾ������ͼ��
  v_sql_del := 'delete from t_rb_author_detail a where not exists (select 1 from t_rb_book_new n where n.bookid = a.bookid and n.delflag=0)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;

  for rec in (select authorid, count(1) countnum from t_rb_author_detail group by authorid) LOOP
  
    -- �õ���ǰ�����£���ͼ�д��ڣ����������ݲ����ڵ����ݡ�����ԱȺ���������ʱ���С�
    v_sql_count := 'update t_rb_author_count c set c.counts= ' ||
                   rec.countnum ||
                   ', c.lupdate=sysdate where c.id = ' ||
                   rec.authorid ;
  
    --DBMS_OUTPUT.put_line(v_sql_count);
    execute immediate v_sql_count;
    --DBMS_OUTPUT.put_line('==================================================================================');
  END LOOP;
  
  execute immediate 'delete from t_rb_authorord_his';
  
  execute immediate 'insert into t_rb_authorord_his select * from v_rb_authorord_his';
  
  commit;
   v_nrecod:=0;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception

  when others then
    rollback;
    --���ʧ�ܣ���ִ�����д����־
    DBMS_OUTPUT.put_line('������');
    v_nstatus := pg_log_manage.f_errorlog;
end;


----�����°�ͳ��ȫ��ͼ����Դͬ���
create or replace synonym s_report_MT_RANK_INDEX_D   for V_SSMS_MT_RANK_INDEX_D@report105.oracle.com;
create table T_MT_RANK_INDEX_D as select * from s_report_MT_RANK_INDEX_D where 1=2;
create table  T_MT_RANK_INDEX_D_TRA as select * from T_MT_RANK_INDEX_D;

insert into t_r_exportsql_por (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK, TO_WHERE, TO_COLUMNS)
values (9, 'ͬ���°񵥱���ȫ��ͼͳ������', 'S_REPORT_MT_RANK_INDEX_D', 'T_MT_RANK_INDEX_D', 'T_MT_RANK_INDEX_D_TRA', 't_MT_RANK_INDEX_D_bak', '', '');

----����Ӧ���ȶ�,���ȣ������ͼ
create or replace view v_content_newscore as
select  d.content_id, (VIEW_7DAYS_CNT+DL_7DAYS_CNT+FEE_7DAYS_CNT)*111 as hotscore,0 as newRANK_HOT,0 as souar from T_MT_RANK_INDEX_D d;

------------�޸�android_list���������ֶ�----
create or replace view v_android_list as
select g.contentid,

--decode(v.mobileprice, 0, 1, 2)||to_char(trunc(l.createtime),'yyyymmdd')||decode(catename, '���', 1, '��Ϸ', 1, '����', 2, 3)
--||to_char(l.createtime, 'hh24miss') as rank_new,

to_char(updatetime,'yymmdd')||decode(v.mobileprice, 0, 1, 0)||(2000000-to_char(l.createtime,'yymmdd'))||decode(catename, '���', 2, '��Ϸ', 2, '����', 1, 0)||
(4000-to_char(l.createtime, 'hh24mi')) as rank_new

,(nvl(a.ADD_ORDER_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_all


,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_fee
,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_hot
,(1000+nvl(c.scores,-200))*1000||(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymm') as rank_scores

,decode(g.catename,'���','appSoftWare','��Ϸ','appGame','����','appTheme','') as catename


,g.name,a.ADD_7DAYS_DOWN_COUNT,a.add_order_count,v.mobileprice
--decode(v.mobileprice, 0, 0, 10) as mobileprice_alias,
--trunc(l.createtime) as createtime_trunc,
--decode(catename, '���', 1,  '����', 2, 10) as catename_alias,
--to_char(l.createtime, 'hh24miss') as createtime_tochar,

,c.scores,l.createtime,l.updatetime,g.companyid
,nvl(d.daynum,0) as daynum,
decode(n.hotscore,null,0,n.hotscore) as hotscore,
decode(n.newRANK_HOT,null,0,n.newRANK_HOT) as newRANK_HOT,
decode(n.souar,null,0,n.souar) as souar
  from
       v_datacenter_cm_content g,
       t_a_dc_ppms_service   v,
       t_r_servenday_temp_a a,
       v_content_last    l,
       v_serven_sort    c,
       v_content_newscore n,
       ( select distinct r.contentid from t_a_cm_device_resource r where r.pid is not null) r,
       (select contentid,sum(downcount) as daynum from t_a_content_downcount where trunc_lupdate=trunc(sysdate) group by contentid) d


 where l.contentid = g.contentid
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = a.CONTENT_ID
   and l.osid = '9'
   and g.contentid = c.CONTENT_ID
   and c.os_id=9
   and g.contentid = n.content_id(+)
   and g.contentid=d.contentid(+)
   and g.contentid=r.contentid;

-------------------------


alter table T_A_ANDROID_LIST add hotscore number default 0;
alter table T_A_ANDROID_LIST add newrank_hot number default 0;
alter table T_A_ANDROID_LIST add souar number default 0;
comment on column T_A_ANDROID_LIST.hotscore
  is '�ȶ�';
comment on column T_A_ANDROID_LIST.newrank_hot
  is '�������ۺϵ÷�';
comment on column T_A_ANDROID_LIST.souar
  is '������ۺϵ÷�';
  
  
  ---������ͼ���С����ӳ���ϵ��ͼ
create or replace view t_rb_typemaping_new as
select t.typeid, t.typename, t.parentid
  from t_rb_type t, t_rb_type_new t1
 where t.typeid = t1.typeid
   and t.parentid is not null and t.parentid !='0' 
union
select s.typeid,s.typename,s.parentid from t_rb_type s where s.parentid='0';


  
  insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'3.0.0.0','MM3.0.0.0.029_SSMS','MM3.0.0.035_SSMS');



create or replace synonym s_om_apply_charge_recommand
  for v_om_apply_charge_recommand@DL_MM_PPMS_NEW;
-- Create table
create table T_APPLY_RECOMMAND
(
  contentid  VARCHAR2(12),
  paycode    VARCHAR2(20),
  state      VARCHAR2(1),
  type       VARCHAR2(1),
  apply_date DATE,
  exit_date  DATE
);

-- Add comments to the columns 
comment on column T_APPLY_RECOMMAND.contentid
  is 'Ӧ��ID';
comment on column T_APPLY_RECOMMAND.paycode
  is '�Ʒѵ�ID';
comment on column T_APPLY_RECOMMAND.state
  is '����״̬';
comment on column T_APPLY_RECOMMAND.type
  is '�����Ȩ������';
comment on column T_APPLY_RECOMMAND.apply_date
  is '�����Ȩ��ʱ��';
comment on column T_APPLY_RECOMMAND.exit_date
  is '�˳���Ȩ��ʱ��';
create table T_APPLY_RECOMMAND_tra
(
  contentid  VARCHAR2(12),
  paycode    VARCHAR2(20),
  state      VARCHAR2(1),
  type       VARCHAR2(1),
  apply_date DATE,
  exit_date  DATE
);

insert into t_r_exportsql_por(id,name,from_name,to_name,to_name_tra,to_name_bak)
values(10,'�Ʒѵ�9��"����"�Ʒѵ����MM������"��AP��Ϣ����','s_om_apply_charge_recommand','T_APPLY_RECOMMAND','T_APPLY_RECOMMAND_tra','T_APPLY_RECOMMAND_bak');

insert into t_caterule_cond_base(base_id,base_name,base_sql) values(56,'�Ʒѵ�9��"����"�Ʒѵ����MM������"��AP��Ϣ����','select g.id from t_r_gcontent g, v_content_last l, t_portal_down_d v,t_apply_recommand f
where  g.contentid = l.contentid and l.contentid=v.content_id and l.osid=''9''  and v.portal_id=''10'' and  v.content_id=f.contentid');
declare 
ruleid number(8);
--���ػ�Ӧ��������¹���
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule_cond values(ruleid,null,0,'f.contentid=''1''','v.add_order_count desc,l.updatetime desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,56);
insert into t_caterule_cond values(ruleid,null,0,'f.contentid=''0''','v.add_order_count desc,l.updatetime desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,56);
commit;

