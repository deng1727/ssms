
insert into t_mb_category_new (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, LUPDDATE, CATEGORYDESC, SORTID, SUM, ALBUM_ID, ALBUM_PIC, RATE, ALBUM_SINGER, PLATFORM, CITYID)
values ('100000006', '��������', '', '1', '0', '2010-05-10 09:03:00', '2011-05-13 22:56:00', '��������', 0, 0, '', '', 1, '', '{0000}', '{0000}');



--------------------------------------------
------���³�ʼ����Ϸ���ط���ӳ���ϵ------
--------------------------------------------
create table T_GAME_CATE_MAPPING_bak1 as select ��* from T_GAME_CATE_MAPPING;

delete from T_GAME_CATE_MAPPING;

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('MMORPG', '����', '26', '76639099');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����ð��', '����', '16', '76639096');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����ð��', 'ð��', '19', '76639098');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '14', '76639103');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '16', '76639096');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '15', '76639100');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '20', '76639095');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '17', '76639102');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('��ɫ����', '��ɫ', '24', '76639097');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('���', '���', '18', '76639101');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('��������', '����', '16', '76639096');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('ð��', 'ð��', '19', '76639098');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('��', '����', '16', '76639096');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '14', '76639103');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('�ۺ�', '����', '26', '76639099');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('��ս��', '����', '16', '76639096');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('�ɻ���', '����', '22', '280196035');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '16', '76639096');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('��ɫ��', '��ɫ', '24', '76639097');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('������', '����', '15', '76639100');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('������', '����', '17', '76639102');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('������', '����', '26', '76639099');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����ר��', '����', '26', '76639099');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('������', '����', '14', '76639103');

----
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '21', '280196034');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('������', '����', '14', '76639103');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '14', '76639103');

-----
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '26', '76639099');


insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('������', '����', '20', '76639095');

insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME, ALILID, CID)
values ('����', '����', '26', '76639099');



------------------------------------------------------------------------
------------------------------------------------------------------------
--------����Ϊһ���洢���̣���Ҫһ��ִ��---------------------------------------------------
------------------------------------------------------------------------
create or replace procedure p_Binding_sort_Final2 as
  type type_table_osid is table of number index by binary_integer; --�Զ�������
  type type_table_sort is table of number index by binary_integer; --�Զ�������
  v_type_nosid   type_table_osid; --OS_ID����
  v_type_nsort   type_table_sort; --SORT_NUM_OLD����
  v_nosidi       number; --iphone����
  v_nosida       number; --Android����
  v_nosids       number; --S60����

  v_nsortmaxi    number; --iphone�������ֵ
  v_nsortmaxs    number; --S60�������ֵ
  v_nsortmaxa    number; --Android�������ֵ
  v_vsqlinsert   varchar2(1500); --��̬SQL
  v_vsqlalter    varchar2(1500); --��̬SQL
  v_vsqltruncate varchar2(1500); --��̬SQL
  --v_vconstant  varchar(6):='0006'; --����
  v_nindnum    number;--��¼�����Ƿ����
  v_nstatus number;--��¼��ذ�״̬
  v_ncount number;--��¼report_servenday��ļ�¼��

begin
v_nstatus:=pg_log_manage.f_startlog('P_BINDING_SORT_FINAL2','���ܰ�');

--����û���ṩ�������̽������׳�����
select count(9) into v_ncount from report_servenday a
     where a.os_id in (9, 3,1)
       and stat_time = to_char(sysdate-1,'yyyymmdd');
if v_ncount=0 then 
  raise_application_error(-20088,'�����ṩ��¼��Ϊ0');
  end if;

  --ɾ������
    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID';
     if v_nindnum>0 then
   execute  immediate ' drop index IND_DOWNSORTO_CONTENTID';
  end if;

    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID';
     if v_nindnum>0 then
 execute  immediate ' drop index IND_DOWNSORT_CONTENTID';
  end if;

    --ɾ��ǰ������
  v_vsqltruncate := 'truncate table t_r_down_sort_new ';
   execute immediate v_vsqltruncate;
   v_vsqltruncate := 'truncate table t_r_down_sort ';
  execute immediate v_vsqltruncate;
  --��ֹ��д������־
  execute immediate ' alter table t_r_down_sort_new  nologging';
  execute immediate ' alter table t_r_down_sort_old  nologging';
  execute immediate ' alter table t_r_down_sort  nologging';

  --���뱨���ԭʼ���ݵ�����
  insert /*+ append parallel(t_r_down_sort_new ,4) */ into t_r_down_sort_new
    (content_id, os_id, SORT_NUM,down_count,add_order_count)
    select
    /*+  parallel(report_servenday,4) */
     content_id,
     os_id,
     dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
     add_7days_down_count,add_order_count
      from report_servenday a
     where a.os_id in (9, 3,1)
       and stat_time = to_char(sysdate-1,'yyyymmdd') ;
commit;
  --����OS_ID�����ȡt_r_down_sort.sort_num_old�����ֵ�����ڸ��±���ǰһ��δ�ṩconten_id������ֵ
  select os_id, max(sort_num) bulk collect
    into v_type_nosid, v_type_nsort
    from t_r_down_sort_new
   group by os_id;

  for i in 1 .. sql%rowcount loop
    if v_type_nosid(i) = 9 then
      v_nosida    := 9;
      v_nsortmaxa := v_type_nsort(i);
    end if;

    if v_type_nosid(i) = 3 then
      v_nosidi    := 3;
      v_nsortmaxi := v_type_nsort(i);
    end if;
      if v_type_nosid(i) = 1 then
      v_nosids    := 1;
      v_nsortmaxs := v_type_nsort(i);
    end if;

  end loop;

  --���¡����뱨����δ�ṩconten_idֵ������
    --����һ��iphone����
    select count(9) into v_nindnum  from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID';
    if v_nindnum=0 then
  execute  immediate   ' create index IND_DOWNSORT_CONTENTID  on   t_r_down_sort_new (content_id) ';
  end if;
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v,4) */ v.contentid,3,' || v_nsortmaxi  ||
               '+ 1,0  from V_CONTENT_LAST v
where v.osid=3 and  not exists (select 1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=3)';
execute immediate v_vsqlinsert;

  --����һ��s60����
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,1,' || v_nsortmaxs  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=1 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=1)';

execute immediate v_vsqlinsert;

    --����һ��android����
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,9,' || v_nsortmaxa  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=9 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=9)';

execute immediate v_vsqlinsert;
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
     select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID';
     if v_nindnum=0 then
  execute  immediate   ' create index IND_DOWNSORTO_CONTENTID  on   t_r_down_sort_old (content_id) parallel';
  end if;
   insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort
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
  from t_r_down_sort_old  o, t_r_down_sort_new  n
 where o.content_id = n.content_id
   and o.os_id = n.os_id)g;
   v_nstatus:=pg_log_manage.f_successlog;
  commit;
/* execute  immediate ' drop index IND_DOWNSORTO_CONTENTID ';
 execute  immediate ' drop index IND_DOWNSORT_CONTENTID ';*/

  --���������ݱ�Ϊǰ������
  v_vsqlalter := 'alter table t_r_down_sort_old  rename to t_r_down_sort_old_1 ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_new  rename to t_r_down_sort_old  ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_old_1 rename to t_r_down_sort_new  ';
  execute immediate v_vsqlalter;

  exception
  when others then
      v_nstatus:=pg_log_manage.f_errorlog;

end;
------------------------------------------------------------------------
------------------------------------------------------------------------
------------------------------------------------------------------------

-- Add/modify columns 
alter table T_EXIGENCECONTENT add TYPE number(2) default 0 not null;
-- Add comments to the columns 
comment on column T_EXIGENCECONTENT.TYPE
  is 'ͬ�����ͣ�0,��ͬ����1�����ߣ�2�����£�3������; 4, ʧ��';

-- Add/modify columns 
alter table T_EXIGENCECONTENT add SUBTYPE VARCHAR2(2) default 0 not null;
-- Add comments to the columns 
comment on column T_EXIGENCECONTENT.SUBTYPE
  is '1��ʾmm��ͨӦ��,2��ʾwidgetӦ��,3��ʾZCOMӦ��,4��ʾFMMӦ�ã�5��ʾjilӦ�ã�6��ʾMM����Ӧ�ã�7��ʾ����Ӧ�ã�8��ʾ��������Ӧ�ã�9��ʾ���MM��10��ʾOVIӦ�ã�11��ʾ�ײ�';



-- Add/modify columns 
alter table T_GOODS_CHANGE_HIS add PATH VARCHAR2(300);
alter table T_GOODS_CHANGE_HIS add catetype VARCHAR2(30);
alter table T_GOODS_CHANGE_HIS add Contentid VARCHAR2(30);
-- Add comments to the columns 
comment on column T_GOODS_CHANGE_HIS.PATH
  is '·��';
comment on column T_GOODS_CHANGE_HIS.catetype
  is 'һ������';
comment on column T_GOODS_CHANGE_HIS.Contentid
  is '����ID';

CREATE OR REPLACE VIEW v_exigencecent as
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
       decode(c.thirdapptype,
              '11',
              c.pkgfee||'',
              decode(p.chargetime || v2.paytype,
                     '20',
                     p.feedesc,
                     v2.chargedesc)) as chargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company    v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c,
       t_exigencecontent e  
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
   and (c.thirdapptype in ('1', '2','6','7','11','12') or (c.thirdapptype = '5' and c.Jilstatus = '1'))
   and  c.contentid=e.contentid
   and  p.contentid=e.contentid
   and e.type = 0;


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.125_SSMS','MM1.0.3.130_SSMS');
commit;
