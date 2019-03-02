-- Add/modify columns 
alter table T_RB_BOOK_NEW add createDate date default SYSDATE;
-- Add comments to the columns 
comment on column T_RB_BOOK_NEW.createDate
  is '数据入库时间';
  
  
  
  ---分类订阅
-- Create table分类订阅详情表
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
  is '图书ID';
comment on column t_rb_type_detail.ordertime
  is '订购时间';
comment on column t_rb_type_detail.lupdate
  is '最后更新时间';
comment on column t_rb_type_detail.typeid
  is '分类ID';
  
  -- Create table分类订阅汇总表
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
  is '分类ID';
comment on column t_rb_type_count.name
  is '分类名称';
  comment on column t_rb_type_count.descs
  is '分类描述';
  comment on column t_rb_type_count.picurl
  is '分类图片';
    comment on column t_rb_type_count.counts
  is '分类数量';
    comment on column t_rb_type_count.categoryid
  is '大分类对应货架ID:1001, 1002, 1003, 10041, 1005, 1007, 1008, 1009,
        1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017';
    comment on column t_rb_type_count.cid
  is '大分类对应货架表主键ID';
comment on column t_rb_type_count.lupdate
  is '最后更新时间';
 
 ----初始化数据 
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

  ----作者订阅
  -- Create table作者订阅详情表
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
  is '图书ID';
comment on column t_rb_author_detail.ordertime
  is '订购时间';
comment on column t_rb_author_detail.lupdate
  is '最后更新时间';
comment on column t_rb_author_detail.authorid
  is '作者ID';
  
  -- Create table作者订阅汇总表
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
  is '作者ID';
comment on column t_rb_author_count.name
  is '作者名称';
  comment on column t_rb_author_count.descs
  is '作者描述';
  comment on column t_rb_author_count.picurl
  is '作者图片';
    comment on column t_rb_author_count.counts
  is '作者数量';
comment on column t_rb_author_count.lupdate
  is '最后更新时间';
  
  
  ----作者初始化
  insert into t_rb_author_count (id,name,descs,picurl,counts,lupdate)
  select t.authorid,
         t.authorname,
         t.authordesc,
         t.authorpic,
         0            as counts,
         sysdate      as lupdate
    from t_rb_author_new t;
    
    commit;
  
  
  -----分类订阅当前(历史)数据视图
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
        
   -----作者订阅当前(历史)数据视图      
create or replace view v_rb_authorord_his as
select b.bookid,b.bookname,b.lupdate,y.authorid,y.authorname
  from t_rb_book_new       b,
       t_rb_author_new           y
 where b.authorid = y.authorid
      and b.delflag='0';
      
      create table t_rb_typeord_his as select * from v_rb_typeord_his  ; 
  
     create table t_rb_authorord_his as select * from v_rb_authorord_his  ; 
                
                
----------------存储过程
create or replace procedure P_baseRead_count as
  v_nrecod      number;
  v_nstatus number; --纪录监控包状态

  v_sql_insert varchar2(3000);
  v_sql_del    varchar2(1200);
  v_sql_count  varchar2(1200);

  CURSOR cateids IS
    select CATEGORYID from t_rb_type_count group by CATEGORYID; --得到分类货架id

  v_categoryType cateids%rowtype; --分类货架id

begin

  v_nstatus := pg_log_manage.f_startlog('P_baseRead_count',
                                        '用于图书分类作者订阅排行统计结果');

  for v_categoryType in cateids LOOP
  
    -- 得到当前类型下，视图中存在，但昨天数据不存在的数据。插入对比后数据至临时表中。
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

  -- 去除数据表中重复数据。只保留最新时间数据
  v_sql_del := 'delete from t_rb_type_detail a
  where a.lupdate <
       (select max(b.lupdate)
          from t_rb_type_detail b
         where b.bookid = a.bookid and b.typeid = a.typeid)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;

  -- 删除下线图书
  v_sql_del := 'delete from t_rb_type_detail a where not exists (select 1 from t_rb_book_new n where n.bookid = a.bookid and n.delflag=0)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;
  
  for rec in (select typeid, count(1) countnum from t_rb_type_detail group by typeid) LOOP

    -- 得到当前类型下，视图中存在，但昨天数据不存在的数据。插入对比后数据至临时表中。
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
    --如果失败，将执行情况写入日志
    DBMS_OUTPUT.put_line('出错了');
    v_nstatus := pg_log_manage.f_errorlog;
end;


create or replace procedure P_baseRead_author_count as
  v_nrecod      number;
  v_nstatus number; --纪录监控包状态

  v_sql_insert varchar2(3000);
  v_sql_del    varchar2(1200);
  v_sql_count  varchar2(1200);

begin

    v_nstatus := pg_log_manage.f_startlog('P_baseRead_author_count',
                                        '用于图书分类作者订阅排行统计结果');

  v_sql_insert :='insert into t_rb_author_detail (BOOKID, ordertime, lupdate, authorid)
 select bookid, sysdate, sysdate, t.authorid
    from t_rb_book_new t where to_char(t.createdate, ''YYYYMMDD'') = to_char(sysdate, ''YYYYMMDD'') and  delflag=0'; 
  DBMS_OUTPUT.put_line(v_sql_insert);
  execute immediate v_sql_insert;

  -- 去除数据表中重复数据。只保留最新时间数据
  v_sql_del := 'delete from t_rb_author_detail a
  where a.lupdate <
       (select max(b.lupdate)
          from t_rb_author_detail b
         where b.bookid = a.bookid and b.authorid = a.authorid)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;

  -- 删除下线图书
  v_sql_del := 'delete from t_rb_author_detail a where not exists (select 1 from t_rb_book_new n where n.bookid = a.bookid and n.delflag=0)';

  DBMS_OUTPUT.put_line(v_sql_del);
  execute immediate v_sql_del;

  for rec in (select authorid, count(1) countnum from t_rb_author_detail group by authorid) LOOP
  
    -- 得到当前类型下，视图中存在，但昨天数据不存在的数据。插入对比后数据至临时表中。
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
    --如果失败，将执行情况写入日志
    DBMS_OUTPUT.put_line('出错了');
    v_nstatus := pg_log_manage.f_errorlog;
end;


----创建新榜单统计全视图数据源同义词
create or replace synonym s_report_MT_RANK_INDEX_D   for V_SSMS_MT_RANK_INDEX_D@report105.oracle.com;
create table T_MT_RANK_INDEX_D as select * from s_report_MT_RANK_INDEX_D where 1=2;
create table  T_MT_RANK_INDEX_D_TRA as select * from T_MT_RANK_INDEX_D;

insert into t_r_exportsql_por (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK, TO_WHERE, TO_COLUMNS)
values (9, '同步新榜单报表全视图统计数据', 'S_REPORT_MT_RANK_INDEX_D', 'T_MT_RANK_INDEX_D', 'T_MT_RANK_INDEX_D_TRA', 't_MT_RANK_INDEX_D_bak', '', '');

----创建应用热度,最热，飙升视图
create or replace view v_content_newscore as
select  d.content_id, (VIEW_7DAYS_CNT+DL_7DAYS_CNT+FEE_7DAYS_CNT)*111 as hotscore,0 as newRANK_HOT,0 as souar from T_MT_RANK_INDEX_D d;

------------修改android_list增加三个字段----
create or replace view v_android_list as
select g.contentid,

--decode(v.mobileprice, 0, 1, 2)||to_char(trunc(l.createtime),'yyyymmdd')||decode(catename, '软件', 1, '游戏', 1, '主题', 2, 3)
--||to_char(l.createtime, 'hh24miss') as rank_new,

to_char(updatetime,'yymmdd')||decode(v.mobileprice, 0, 1, 0)||(2000000-to_char(l.createtime,'yymmdd'))||decode(catename, '软件', 2, '游戏', 2, '主题', 1, 0)||
(4000-to_char(l.createtime, 'hh24mi')) as rank_new

,(nvl(a.ADD_ORDER_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_all


,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_fee
,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_hot
,(1000+nvl(c.scores,-200))*1000||(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymm') as rank_scores

,decode(g.catename,'软件','appSoftWare','游戏','appGame','主题','appTheme','') as catename


,g.name,a.ADD_7DAYS_DOWN_COUNT,a.add_order_count,v.mobileprice
--decode(v.mobileprice, 0, 0, 10) as mobileprice_alias,
--trunc(l.createtime) as createtime_trunc,
--decode(catename, '软件', 1,  '主题', 2, 10) as catename_alias,
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
  is '热度';
comment on column T_A_ANDROID_LIST.newrank_hot
  is '新最热综合得分';
comment on column T_A_ANDROID_LIST.souar
  is '新飙升综合得分';
  
  
  ---创建新图书大小分类映射关系视图
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
  is '应用ID';
comment on column T_APPLY_RECOMMAND.paycode
  is '计费点ID';
comment on column T_APPLY_RECOMMAND.state
  is '申请状态';
comment on column T_APPLY_RECOMMAND.type
  is '参与包权益类型';
comment on column T_APPLY_RECOMMAND.apply_date
  is '参与包权益时间';
comment on column T_APPLY_RECOMMAND.exit_date
  is '退出包权益时间';
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
values(10,'计费点9折"或者"计费点接受MM币消费"的AP信息排序','s_om_apply_charge_recommand','T_APPLY_RECOMMAND','T_APPLY_RECOMMAND_tra','T_APPLY_RECOMMAND_bak');

insert into t_caterule_cond_base(base_id,base_name,base_sql) values(56,'计费点9折"或者"计费点接受MM币消费"的AP信息排序','select g.id from t_r_gcontent g, v_content_last l, t_portal_down_d v,t_apply_recommand f
where  g.contentid = l.contentid and l.contentid=v.content_id and l.osid=''9''  and v.portal_id=''10'' and  v.content_id=f.contentid');
declare 
ruleid number(8);
--本地化应用软件更新规则
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule_cond values(ruleid,null,0,'f.contentid=''1''','v.add_order_count desc,l.updatetime desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,56);
insert into t_caterule_cond values(ruleid,null,0,'f.contentid=''0''','v.add_order_count desc,l.updatetime desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,56);
commit;

