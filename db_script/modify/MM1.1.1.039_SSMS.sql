insert into T_KEY_BASE (KEYID, KEYNAME, KEYTABLE, KEYDESC, KEYTYPE)
values ('41', 'book_banner_pic', 't_rb_category', '�ͻ���bannerͼ����', '2');

update T_CATERULE_COND_BASE set BASE_SQL = 'select b.id from t_r_base b, t_r_gcontent g, t_r_down_sort_wap s, t_portal_down_d d, v_service v where b.id = g.id and d.content_id = g.contentid and s.content_id = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 2 and s.os_id = 2 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))' where BASE_ID = 30;

update T_CATERULE_COND_BASE set BASE_SQL = 'select b.id from t_r_base b, t_r_gcontent g, V_CONTENT_TIME d, v_service v where b.id = g.id and d.contentid = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%''  and d.updatetime=sysdate-1 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))' where BASE_ID = 35;

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (32, 'WWW�������,����,�ܰ�������Ϣ', 'select b.id from t_r_base b, t_r_gcontent g, t_portal_down_d d, v_service v, V_CONTENT_TIME t where b.id = g.id and d.content_id = g.contentid and t.contentid = g.contentid  and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 1 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');


insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (33, 'WWW�������ͳ����Ϣ', 'select b.id from t_r_base b, t_r_gcontent g, t_r_down_sort_wap s, t_portal_down_d d, v_service v, V_CONTENT_TIME t where b.id = g.id and d.content_id = g.contentid and t.contentid = g.contentid and s.content_id = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 1 and s.os_id = 1 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');


insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1032, 'www����񵥹���', 0, 0, 1, 0, 0, -1);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1033, 'www��Ѱ񵥹���', 0, 0, 1, 0, 0, null);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1034, 'www���Ѱ񵥹���', 0, 0, 1, 0, 0, null);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1035, 'www�ܰ񵥹���', 0, 0, 1, 0, 0, null);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1036, 'www���°񵥹���', 7, 0, 1, 0, 5, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1032, null, 0, null, 's.standard_score desc, d.add_7days_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1420, 33);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1033, null, 0, 'mobilePrice=0', 'd.add_7days_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1421, 32);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1034, null, 0, 'mobilePrice>0', 'd.add_7days_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1422, 32);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1035, null, 0, null, 'd.add_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1423, 32);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1036, null, 0, 'g.catename in (''���'', ''��Ϸ'', ''����'')', 'to_date(substr(d.updatetime,0,10)) desc, decode(g.catename, ''���'', 1,''��Ϸ'', 1, ''����'',2) asc,to_date(d.createtime) asc,g.name asc', -1, 1, 1424, 35);



-- Add/modify columns 
alter table T_RB_CATEGORY add SORTID NUMBER(8) default 0;
-- Add comments to the columns 
comment on column T_RB_CATEGORY.SORTID
  is '��������';

create table t_vb_program
(
  ProgramID   varchar2(10) not null,
  ProgramNAME varchar2(128) not null,
  ContentID   varchar2(21) not null
)
;
-- Add comments to the columns 
comment on column t_vb_program.ProgramID
  is '��ĿID';
comment on column t_vb_program.ProgramNAME
  is '��Ŀ��������';
comment on column t_vb_program.ContentID
  is '��ƵID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_vb_program
  add constraint t_vb_program_key primary key (PROGRAMID);

-- Create table
create table t_vb_programDetail
(
  programID   varchar2(10) not null,
  programNAME varchar2(128) not null,
  nodeID      varchar2(21) not null,
  description varchar2(4000) not null,
  filesize        NUMBER(12),
  duration    varchar2(21),
  logoUrl     varchar2(512) not null,
  programUrl  varchar2(512) not null,
  accessType  varchar2(2) not null
)
;
-- Add comments to the columns 
comment on column t_vb_programDetail.programID
  is '��ĿID';
comment on column t_vb_programDetail.programNAME
  is '��Ŀ��������';
comment on column t_vb_programDetail.nodeID
  is '��ĿID';
comment on column t_vb_programDetail.description
  is '���';
comment on column t_vb_programDetail.filesize
  is '�ļ���С';
comment on column t_vb_programDetail.duration
  is 'ʱ��';
comment on column t_vb_programDetail.logoUrl
  is 'LOGO URL';
comment on column t_vb_programDetail.programUrl
  is '��ĿURL';
comment on column t_vb_programDetail.accessType
  is '�������Ż� 0.MM�ͻ��� 1.wap';

-- Create table
create table t_vb_node
(
  NodeId       varchar2(10) not null,
  NodeName     varchar2(128) not null,
  ClassLevel   number(2) not null,
  ShowPosition varchar2(3),
  accessType   number(2) not null
)
;
-- Add comments to the columns 
comment on column t_vb_node.NodeId
  is '��ĿId';
comment on column t_vb_node.NodeName
  is '��Ŀ��������';
comment on column t_vb_node.ClassLevel
  is '�㼶 �޶�Ϊ1��2��3';
comment on column t_vb_node.ShowPosition
  is 'MM�ͻ���չʾ��� 0�� û��MMչʾ 1�� Ϊ��MM �ͻ���չʾ';
comment on column t_vb_node.accessType
  is '�������Ż�: 0.MM�ͻ��� 1.wap';





-------------------����Ϊ �洢���̣���Ҫ����ִ��-----
/*
��    ��:  wwwAndwap�����Դ���ݼ���
����ժҪ:  ��Ҫ����www��wap�Ż�����񵥵��������ָ��÷�
��    ��:  ��
�� �� ��:  jobֱ�ӵ���
�� �� ��:  chenhuoping
��������:  2012-03-01
Դ    ��:  report_servenday_wap,v_cm_content
Ŀ �� ��:  t_r_down_sort_new_wap,t_r_down_sort_old_wap,t_r_down_sort_wap

������ʷ��
2012-03-21     dongke      ���www����������
*/

create or replace procedure P_BINDING_SORT_FINAL_WAPAWWW as
                      type type_table_osid is table of number index by binary_integer; --�Զ�������
                      type type_table_sort is table of number index by binary_integer; --�Զ�������
                      v_type_nosid   type_table_osid; --OS_ID����
                      v_type_nsort   type_table_sort; --SORT_NUM_OLD����
                      v_nosidi       number; --iphone����
                      v_nosida       number; --wap�Ż�����
                      v_nosids       number; --www�Ż�����
                    
                      v_nsortmaxi    number; --iphone�������ֵ
                      v_nsortmaxs    number; --www�Ż��������ֵ
                      v_nsortmaxa    number; --wap�Ż��������ֵ
                      v_vsqlinsert   varchar2(1500); --��̬SQL
                      v_vsqlalter    varchar2(1500); --��̬SQL
                      v_vsqltruncate varchar2(1500); --��̬SQL
                      --v_vconstant  varchar(6):='0006'; --����
                      v_nindnum    number;--��¼�����Ƿ����
                      v_nstatus number;--��¼��ذ�״̬
                      v_ncount number;--��¼report_servenday��ļ�¼��
                    
                    begin
                    v_nstatus:=pg_log_manage.f_startlog('P_BINDING_SORT_FINAL_WAP','���ܰ�WAPAndWWW');
                    
                    --����û���ṩ�������̽������׳�����
                    --1��WWW
                    --2:WAP
                    --10:MOPPS
                    ---999:ȫ��
                    
                    select count(9) into v_ncount from report_servenday_wap a
                         where a.os_id in (2,1)
                          and  rownum<2;
                    if v_ncount=0 then
                      raise_application_error(-20088,'�����ṩ��¼��Ϊ0');
                      end if;
                    
                      --ɾ������
                        select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID_WAP';
                         if v_nindnum>0 then
                       execute  immediate ' drop index IND_DOWNSORTO_CONTENTID_WAP';
                      end if;
                    
                        select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID_WAP';
                         if v_nindnum>0 then
                     execute  immediate ' drop index IND_DOWNSORT_CONTENTID_WAP';
                      end if;
                    
                        --ɾ��ǰ������
                      v_vsqltruncate := 'truncate table t_r_down_sort_new_wap ';
                       execute immediate v_vsqltruncate;
                       v_vsqltruncate := 'truncate table t_r_down_sort_wap ';
                      execute immediate v_vsqltruncate;
                      --��ֹ��д������־
                      execute immediate ' alter table t_r_down_sort_new_wap  nologging';
                      execute immediate ' alter table t_r_down_sort_old_wap  nologging';
                      execute immediate ' alter table t_r_down_sort_wap  nologging';
                    
                      --���뱨���ԭʼ���ݵ�����
                      insert /*+ append parallel(t_r_down_sort_new ,4) */ into t_r_down_sort_new_wap
                        (content_id, os_id, SORT_NUM,down_count,add_order_count)
                        select
                        /*+  parallel(report_servenday,4) */
                         content_id,
                         os_id,
                         dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
                         add_7days_down_count,add_order_count
                          from report_servenday_wap a
                         where a.os_id in(2,1);
                    commit;
                      --����OS_ID�����ȡt_r_down_sort.sort_num_old�����ֵ�����ڸ��±���ǰһ��δ�ṩconten_id������ֵ
                      select os_id, max(sort_num) bulk collect
                        into v_type_nosid, v_type_nsort
                        from t_r_down_sort_new_wap
                       group by os_id;
                    
                      for i in 1 .. sql%rowcount loop
                        if v_type_nosid(i) = 2 then
                          ----wap�Ż�
                          v_nosida    := 2;
                          v_nsortmaxa := v_type_nsort(i);
                        end if;
                         if v_type_nosid(i) = 1 then
                          ----www�Ż�
                          v_nosids    := 1;
                          v_nsortmaxs := v_type_nsort(i);
                        end if;
                    
                      end loop;
                    
                      --���¡����뱨����δ�ṩconten_idֵ������
                        --����һ��wap�Ż�����
                        select count(9) into v_nindnum  from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID_WAP';
                        if v_nindnum=0 then
                      execute  immediate   ' create index IND_DOWNSORT_CONTENTID_WAP  on   t_r_down_sort_new_wap (content_id) ';
                      end if;
                      v_vsqlinsert:= 'insert  into t_r_down_sort_new_wap (content_id,os_id,sort_num,down_count)
                    select /*+ parallel(v,4) */ v.contentid,2,' || v_nsortmaxa  ||
                                   '+ 1,0  from v_cm_content v
                    where v.status=''0006'' and  not exists (select 1 from t_r_down_sort_new_wap  n where n.content_id=v.contentid and n.os_id=2)';
                    
                    execute immediate v_vsqlinsert;
                    
                     --���¡����뱨����δ�ṩconten_idֵ������
                        --����һ��www�Ż�����
                        
                      v_vsqlalter:= 'insert  into t_r_down_sort_new_wap (content_id,os_id,sort_num,down_count)
                    select /*+ parallel(v,4) */ v.contentid,1,' || v_nsortmaxs  ||
                                   '+ 1,0  from v_cm_content v
                    where v.status=''0006'' and  not exists (select 1 from t_r_down_sort_new_wap  n where n.content_id=v.contentid and n.os_id=1)';
                    
                    execute immediate v_vsqlalter;
                    
                      
                      commit;
                       /*  --�������ṩ���ݣ�ǰ��û�����ݣ�����ǰ������
                      v_vsqlstr:='insert into t_r_down_sort_old
                        (content_id, os_id, sort_num)
                        select n.content_id,n.os_id,decode(n.os_id,9,'||v_nsortmaxa||',3,'||v_nsortmaxi||')
                          from t_r_down_sort_new  n
                         where not exists (select 1
                                  from t_r_down_sort_old  o
                                 where n.content_id = o.content_id
                                   and n.os_id = n.os_id)';
                         execute immediate   v_vsqlstr;*/
                         select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID_WAP';
                         if v_nindnum=0 then
                      execute  immediate   ' create index IND_DOWNSORTO_CONTENTID_wap  on   t_r_down_sort_old_wap (content_id) parallel';
                      end if;
                       insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort_wap
                        (content_id, os_id, sort_num_new, sort_num_old, minus_sort_null,minus_down_count,add_order_count,Standard_score)
                    select content_id,
                           os_id,
                           sort_num_new,
                           sort_num_old,
                           minus_sort_null,
                           minus_down_count,
                           add_order_count,
                    
                           decode((max(B) over(partition by os_id) - min(B) over( partition by os_id)),0,0, trunc((a -
                           abs(a) * (1 - (B - min(B) over(partition by os_id)) / (max(B) over( partition by os_id) - min(B) over( partition by os_id))))*0.6+b*0.4,3)) Standard_score
                      from (select n.content_id,
                           n.os_id,
                           n.sort_num sort_num_new,
                           o.sort_num sort_num_old,
                           o.sort_num - n.sort_num minus_sort_null,
                           n.down_count - o.down_count minus_down_count,
                    
                           decode(stddev(o.sort_num - n.sort_num) over(partition by o.os_id),
                                  0,
                                  0,
                                  ((o.sort_num - n.sort_num) - avg(o.sort_num - n.sort_num)
                                   over(partition by o.os_id)) / stddev(o.sort_num - n.sort_num) over(partition by o.os_id)) A,
                           decode(stddev(n.down_count-o.down_count) over(partition by n.os_id),
                                  0,
                                  0,
                                  ((n.down_count - o.down_count) -
                                  avg(n.down_count - o.down_count) over(partition by o.os_id)) /
                                  stddev(n.down_count-o.down_count) over(partition by n.os_id)) B,
                    
                           n.add_order_count
                      from t_r_down_sort_old_wap  o, t_r_down_sort_new_wap  n
                     where o.content_id = n.content_id
                       and o.os_id = n.os_id)g;
                       v_nstatus:=pg_log_manage.f_successlog(v_nstatus);
                      commit;
                    /* execute  immediate ' drop index IND_DOWNSORTO_CONTENTID ';
                     execute  immediate ' drop index IND_DOWNSORT_CONTENTID ';*/
                    
                      --���������ݱ�Ϊǰ������
                      v_vsqlalter := 'alter table t_r_down_sort_old_wap  rename to t_r_down_sort_old_wap_1 ';
                      execute immediate v_vsqlalter;
                    
                      v_vsqlalter := 'alter table t_r_down_sort_new_wap  rename to t_r_down_sort_old_wap  ';
                      execute immediate v_vsqlalter;
                    
                      v_vsqlalter := 'alter table t_r_down_sort_old_wap_1 rename to t_r_down_sort_new_wap  ';
                      execute immediate v_vsqlalter;
                    
                      exception
                      when others then
                          v_nstatus:=pg_log_manage.f_errorlog;
                    
end;
------------------------------------------------------------------------
--�������1000�����ݿ��޸Ŀ�ʼ
update t_caterule_cond set wsql='type=''nt:gcontent:appTheme'' and mobilePrice>0' where ruleid = 222;
update t_caterule_cond set wsql='mobilePrice = 0 and type=''nt:gcontent:appTheme''' where ruleid=94;
update t_caterule_cond set wsql='mobilePrice = 0' where ruleid=2;
update t_caterule_cond set wsql='mobilePrice>1000 and mobilePrice<=2000' where ruleid=149 and condtype=10;
update t_caterule_cond set wsql='mobilePrice>0' where ruleid=411;
update t_caterule_cond set wsql='mobilePrice=0' where ruleid=413;
update t_caterule_cond set wsql='mobilePrice=0' where ruleid=283;
update t_caterule_cond set wsql='mobilePrice>0' where ruleid=284;
update t_caterule_cond set wsql='mobilePrice=0' where ruleid=285;
update t_caterule_cond set wsql='mobilePrice>0' where ruleid=286;
update t_caterule_cond set wsql='mobilePrice>1000 and mobilePrice<=2000' where ruleid=111 and condtype=10;
--�������1000�����ݿ��޸Ľ���
------------------------------------------------------------------------
--�������ص��뿪ʼ
create table T_CB_COMICSERIES
(
  CONTENTCODE        VARCHAR2(60) not null,
  COUNT              NUMBER(12) not null,
  COMICSERISCATEGORY VARCHAR2(128) not null,
  COMICSERISTYPE     VARCHAR2(50) not null,
  STYLE              VARCHAR2(50) not null,
  COLOUR             VARCHAR2(50) not null,
  PRODUCTZONE        VARCHAR2(8) not null,
  LANGUAGE           VARCHAR2(50) not null,
  PUBLISHDATE        VARCHAR2(8),
  SERIALISESTATUS    VARCHAR2(8),
  CANTO              VARCHAR2(8) not null,
  primary key (CONTENTCODE)
);

-- Create table
create table T_CB_CP
(
  CPID   VARCHAR2(60) not null,
  CPNAME VARCHAR2(60) not null,
  primary key (CPID)
);
create table T_CB_DM
(
  CONTENTCODE      VARCHAR2(60) not null,
  NAME             VARCHAR2(512) not null,
  DESCRIPTION      VARCHAR2(1024) not null,
  CPID             VARCHAR2(60) not null,
  AUTHOR           VARCHAR2(60) not null,
  TYPE             VARCHAR2(8) not null,
  KEYWORD          VARCHAR2(4000),
  EXPIRETIME       VARCHAR2(20) not null,
  DEFAULTPRICEINFO VARCHAR2(4000),
  OWNERTYPE        VARCHAR2(1),
  CONTENTHLRCODE   VARCHAR2(2) not null,
  CONTENTFIRSTCODE VARCHAR2(60) not null,
  SIMGURL1         VARCHAR2(512),
  SIMGURL2         VARCHAR2(512),
  SIMGURL3         VARCHAR2(512),
  SIMGURL4         VARCHAR2(512),
  OPERATE          VARCHAR2(1) not null,
  BOSSSERCODE      VARCHAR2(60),
  URL1             VARCHAR2(512) not null,
  URL2             VARCHAR2(512),
  URL3             VARCHAR2(512),
  CREATETIME       VARCHAR2(14) not null,
  UPDATETIME       VARCHAR2(14) not null,
  DELFLAG          NUMBER(1) default 0 not null,
  primary key (CONTENTCODE)

);

create table T_CB_INFO
(
  CONTENTCODE VARCHAR2(60) not null,
  CONTENTS    VARCHAR2(4000) not null,
  PICTURE     VARCHAR2(4000),
  SOURCE      VARCHAR2(60),
  primary key (CONTENTCODE)
);
create table T_CB_RANK
(
  RANKID      VARCHAR2(20) not null,
  RANKNAME    VARCHAR2(60) not null,
  CONTENTCODE VARCHAR2(20) not null,
  SORTNUMBER  NUMBER(6) not null,
  primary key (RANKID, CONTENTCODE)
);
create table T_CB_THEME
(
  THEMECATEGORY VARCHAR2(100) not null,
  CONTENTCODE   VARCHAR2(60) not null,
primary key (CONTENTCODE)
);
create table T_CB_TVSERIES
(
  CONTENTCODE      VARCHAR2(60) not null,
  COUNT            NUMBER(12),
  TVSERISCATEGORY  VARCHAR2(100),
  DIRECTOR         VARCHAR2(4000),
  LEADINGACTOR     VARCHAR2(4000),
  OTHERACTOR       VARCHAR2(4000),
  PRODUCTZONE      VARCHAR2(50),
  LANGUAGE         VARCHAR2(50),
  PUBLISHDATE      VARCHAR2(8),
  TVSERISSERISTYPE VARCHAR2(50),
  STYLE            VARCHAR2(50),
  COLOUR           VARCHAR2(50),
  SERIALISESTATUS  VARCHAR2(4),
  CANTO            VARCHAR2(4),
  primary key (CONTENTCODE)
);

-- Add comments to the columns 
comment on column T_CB_DM.CONTENTCODE
  is '����Ψһ��ʶ';
comment on column T_CB_DM.NAME
  is '��������';
comment on column T_CB_DM.DESCRIPTION
  is '��������';
comment on column T_CB_DM.CPID
  is '�����ṩ��';
comment on column T_CB_DM.AUTHOR
  is '����ԭ���ߣ���������';
comment on column T_CB_DM.TYPE
  is '�������ͣ�ȡֵ����:
101:Theme������
104:MovieSeries������Ƭ
115:Information����Ѷ
221:ComicSeries��������
';
comment on column T_CB_DM.KEYWORD
  is '���ݹؼ��֣�������ֶ�Σ���ֵ֮��������"|"������';
comment on column T_CB_DM.EXPIRETIME
  is '���ݳ���ʱ�䡣�Ż����ݸ��ֶξ�����ʱֹͣչʾ���ݸ��ն��û�����ʽΪyyyy-MM-dd''T''HH:mm:ss''Z''
��ʽ��YYYYMMDDHHMMSS
�磺20090601010101
';
comment on column T_CB_DM.DEFAULTPRICEINFO
  is '���ݵ�Ĭ���ʷ�����';
comment on column T_CB_DM.OWNERTYPE
  is '�����ṩ������. ȡֵ���£�                                                                  0��SPCP
1���ն��û�
2����Ӫ��(Ԥ��)
3�������û�(Ԥ��)��
Ĭ�ϱ�ʾSPCP��
';
comment on column T_CB_DM.CONTENTHLRCODE
  is '���ݹ����أ�
00:����(ȫ��)
01:����
02:���
03:�ӱ�
04:ɽ��
05:���ɹ�
06:����
07:����
08:������
09:�Ϻ�
10:����
11:�㽭
12:����
13:����
14:����
15:ɽ��
16:����
17:����
18:����
19:�㶫
20:����
21:����
22:����
23:�Ĵ�
24:����
25:����
26:����
27:����
28:�ຣ
29:����
30:�½�
31:����
';
comment on column T_CB_DM.CONTENTFIRSTCODE
  is '���ݵ�����ĸ';
comment on column T_CB_DM.SIMGURL1
  is 'Ԥ��ͼ1������Ĺ���С��MM��Ӫȷ����60X80';
comment on column T_CB_DM.SIMGURL2
  is 'Ԥ��ͼ2150X200';
comment on column T_CB_DM.SIMGURL3
  is 'Ԥ��ͼ3120X160';
comment on column T_CB_DM.SIMGURL4
  is 'Ԥ��ͼ4 90X120';
comment on column T_CB_DM.OPERATE
  is '1��������
2�����£�
3������
���ڱ�ʾ���ݵĴ���״̬
';
comment on column T_CB_DM.BOSSSERCODE
  is 'Boss��ļƷѴ��룻����Ѷ����Ϊ�գ�
ע�����������ṩҵ������
';
comment on column T_CB_DM.URL1
  is '��������ҳURL������WAP';
comment on column T_CB_DM.URL2
  is '��������ҳURL������';
comment on column T_CB_DM.CREATETIME
  is '���ݴ���ʱ�䣬����,��ʽ��YYYYMMDDHHMMSS
�磺20090601010101
';
comment on column T_CB_DM.UPDATETIME
  is '����������ʱ�䣬����,��ʽ��YYYYMMDDHHMMSS
�磺20090601010101
';
comment on column T_CB_DM.DELFLAG
  is '0��û�б�ɾ����1��ɾ��';

-- Add comments to the columns 
comment on column T_CB_INFO.CONTENTCODE
  is '����Ψһ��ʶ';
comment on column T_CB_INFO.CONTENTS
  is '��Ѷ����';
comment on column T_CB_INFO.PICTURE
  is '��Ѷ���׵�ͼƬ������֧��ͼ��Ч��չʾ����������ֶ�Σ���ֵ֮��������"|"����.';
comment on column T_CB_INFO.SOURCE
  is '��Ѷ��Դ';
  
-- Add comments to the columns 
comment on column T_CB_RANK.RANKID
  is '1500�����������а������У�
1501�����������а������У�
1502�����������а������У�
1503����������а������У�
1504����������а������У�
1505����������а������У�
1506�����������а������У�
1507�����������а������У�
1508�����������а������У�
1524�����������а������У�
1525�����������а������У�
1526�����������а������У�
1527����������а������У�
1528����������а������У�
1529����������а������У�
1530�����������а������У�
1531�����������а������У�
1532�����������а������У�
1572���⳩�����а������У�
1573���⳩�����а������У�
1574���⳩�����а������У�
1575���������а������У�
1576���������а������У�
1577���������а������У�
1578�����������а������У�
1579�����������а������У�
1580�����������а������У�
';
comment on column T_CB_RANK.RANKNAME
  is 'ר���µĶ���(��rankid�ľ�������)
����
����������������������У������У������У����� 
';
comment on column T_CB_RANK.CONTENTCODE
  is '����Ψһ��ʶ';
comment on column T_CB_RANK.SORTNUMBER
  is '������š���С�������С�������������ȡֵ��Χ��-999999��999999֮�䡣';
  
  
-- Add comments to the columns 
comment on column T_CB_THEME.THEMECATEGORY
  is '������࣬������ֶ�Σ���ֵ֮��������"|"����:
15500:��Ц
15501:��ϵ
15502:Ψ��
15503:����
15504:����
15505:У԰
15506:����
15507:�˶�
15508:��Ϸ
15509:�ֻ�
16017:����
';
comment on column T_CB_THEME.CONTENTCODE
  is '����Ψһ��ʶ';
  
-- Add comments to the columns 
comment on column T_CB_TVSERIES.CONTENTCODE
  is '����Ψһ��ʶ';
comment on column T_CB_TVSERIES.COUNT
  is '����Ƭ�е����ĸ���';
comment on column T_CB_TVSERIES.TVSERISCATEGORY
  is '����Ƭ���࣬������ֶ�Σ���ֵ֮��������"|"����:
15400:��Ц
15401:ð��
15402:�˶�
15403:���
15404:�ƻ�
15405:����
15406:��ѧ
15407:����
15408:����
15409:ʱѶ
15410:����
16017:����
';
comment on column T_CB_TVSERIES.DIRECTOR
  is '���ߣ�������ֶ�Σ�������ֶ�Σ���ֵ֮��������"|"����';
comment on column T_CB_TVSERIES.LEADINGACTOR
  is '���ݣ�������ֶ�Σ�������ֶ�Σ���ֵ֮��������"|"����';
comment on column T_CB_TVSERIES.OTHERACTOR
  is '������Ա��������ֶ�Σ�������ֶ�Σ���ֵ֮��������"|"����';
comment on column T_CB_TVSERIES.PRODUCTZONE
  is '��Ʒ����:
5460:ŷ��
5461:��½
5462:̨��
5463:�ձ�
5464:����
5465:����
5466:���
5468:����
';
comment on column T_CB_TVSERIES.LANGUAGE
  is '���֣�������ֶ�Σ���ֵ֮��������"|"����:
5420:����
5421:Ӣ��
5422:����
5423:����
5424:����
';
comment on column T_CB_TVSERIES.PUBLISHDATE
  is '�������ڡ���ʽΪYYYY';
comment on column T_CB_TVSERIES.TVSERISSERISTYPE
  is '���ͣ�������ֶ�Σ���ֵ֮��������"|"����:
5430:TV��
5431:OVA��
5432:OAD��
5433:�糡��
5434:���˰�
5435:�ص��
5436:����Ƭ
5437:����
';
comment on column T_CB_TVSERIES.STYLE
  is '���������ֶ�Σ���ֵ֮��������"|"����:
5440:дʵ
5441:Q��
5442:�ŵ�
5443:�ִ�
5444:����
';
comment on column T_CB_TVSERIES.COLOUR
  is '��ɫ��������ֶ�Σ���ֵ֮��������"|"����:
5450:�ڰ�
5451:ȫ��
5452:���
';
comment on column T_CB_TVSERIES.SERIALISESTATUS
  is '���ص�״̬:
7100:���
7101:����
';
comment on column T_CB_TVSERIES.CANTO
  is 'ƪ������:
7112:��ƪ
7111:��ƪ
7110:��ƪ
';

--�������ص������
------------------------------------------------------------------------

------------------------------------------------------------------------
------------------------------------------------------------------------
------------------------------------------------------------------------





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.035_SSMS','MM1.1.1.039_SSMS');
commit;
