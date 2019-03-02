 --069和075版本合并到一起上线（169行以后的，属于069版本）
  -- Create table
create table t_r_commerce
(
  contentid varchar2(40),
  hotscore  number
)
;
-- Add comments to the table 
comment on table t_r_commerce
  is '商业化合作应用列表';
-- Add comments to the columns 
comment on column t_r_commerce.contentid
  is '应用id';
comment on column t_r_commerce.hotscore
  is '显示的最低热度值';
  
  
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
decode(n.souar,null,0,n.souar) as souar,
decode(m.hotlist,null,-0.001*mg.sortid,m.hotlist) as hotlist,
decode(m.riselist,null,0,m.riselist) as riselist
  from
       t_r_gcontent g,
       t_a_dc_ppms_service   v,
       t_r_servenday_temp_a a,
       v_content_last    l,
       v_serven_sort    c,
       --v_content_newscore n,
       (select case
         when cn.content_id is null then
          co.contentid
         when co.contentid is null then
          cn.content_id
         else
          co.contentid
       end as content_id,
       cn.newRANK_HOT,
       cn.souar,
       case
         when nvl(cn.hotscore,0) < nvl(co.hotscore,0) then
          co.hotscore
         else
          cn.hotscore
       end as hotscore
  from v_content_newscore cn full join t_r_commerce co
 on cn.content_id = co.contentid)n,
       mid_table m,
       ( select distinct r.contentid from t_a_cm_device_resource r where r.pid is not null) r,
       (select g.contentid,row_number() over(order by g.plupddate desc) sortid from t_r_gcontent g where not exists(select 1 from mid_table m where g.contentid = m.appid))mg,
       (select contentid,sum(downcount) as daynum from t_a_content_downcount where trunc_lupdate=trunc(sysdate) group by contentid) d


 where l.contentid = g.contentid
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = a.CONTENT_ID
   and l.osid = '9'
   and g.contentid = c.CONTENT_ID
   and c.os_id=9
   and g.subtype !='16'
   and g.contentid = n.content_id(+)
   and g.contentid = m.appid(+)
   and g.contentid=d.contentid(+)
   and g.contentid=r.contentid
   and g.contentid=mg.contentid(+);

CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin as
  v_nstatus     number;
  v_nrecod      number;
begin
 -------执行游戏榜单计算存储过程-----------------------
P_mt_rank_index_fin_game;
 -------执行软件榜单计算存储过程-----------------------
P_mt_rank_index_fin_software;

v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','最热和飙升榜单计算');

 --------------最热榜单干预----------------
update mid_table t1
  set t1.hotlist
     = (select t1.hotlist*(decode(t2.type,'1',t2.param,1))
          from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='1')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='1');
 
 --------------最新榜单干预----------------
update mid_table t1
  set t1.riselist
     = (select t1.riselist*(decode(t2.type,'2',t2.param,1))
     from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='2')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='2');

 -------最热细粒度干预-----------------------
  update mid_table t1
  set t1.hotlist
     = (select t2.M_HOTLIST+(decode(t2.type,'3',t2.param,0))
          from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='3')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='3');

 -------最新细粒度干预-----------------------
update mid_table t1
  set t1.riselist
     = (select t2.M_RISELIST+(decode(t2.type,'4',t2.param,0))
     from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='4')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='4');

  -------分类最热数据干预-----------------------
update mid_table m1 set m1.hotlist =
 (select (v.hotlist + b.param) h from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v
where gc.contentid = b.contentid and gc.appcateid = v.appcateid and
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5')
 where  exists(select 1 from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v
where gc.contentid = b.contentid and gc.appcateid = v.appcateid and
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5');
 update mid_table m
   set m.hotlist = nvl((m.hotlist-0.000001* (select sortid
                        from (select m.appid,
                                     row_number() over(order by g.plupddate desc) sortid
                                from mid_table m, t_r_gcontent g
                               where  g.contentid = m.appid) mg
                       where mg.appid = m.appid)),m.hotlist)
 where m.rowid >(select min(m1.rowid) from mid_table m1 where m1.hotlist=m.hotlist);
 commit;

  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
  
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
    
end P_mt_rank_index_fin;

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM5.0.0.0.069_SSMS','MM5.0.0.0.075_SSMS');
commit;

CREATE OR REPLACE FUNCTION F_FORMATE_DATE(P_DATESTR IN VARCHAR2)
  RETURN VARCHAR2 IS
  R_DATESTR VARCHAR2(50);
BEGIN
  IF P_DATESTR IS NOT NULL THEN
    R_DATESTR := REPLACE(P_DATESTR, '-', '');
    R_DATESTR := REPLACE(R_DATESTR, ' ', '');
    R_DATESTR := REPLACE(R_DATESTR, ':', '');
  END IF;
  RETURN R_DATESTR;
END;
-- Add/modify columns 
alter table T_R_REFERENCE add islock number(2) default 0;
alter table T_R_REFERENCE add locktime date;
alter table T_R_REFERENCE add lockuser VARCHAR2(100);
alter table T_R_REFERENCE add locknum number;
-- Add comments to the columns 
comment on column T_R_REFERENCE.locknum
  is '被锁定的位置';
comment on column T_R_REFERENCE.islock
  is '位置是否已经被锁定。0 未锁定；1 已锁定';
comment on column T_R_REFERENCE.locktime
  is '锁定/解锁时间';
comment on column T_R_REFERENCE.lockuser
  is '锁定人';
  -- Add/modify columns 
alter table T_R_CATEGORY add islock number(2) default 0;
alter table T_R_CATEGORY add locktime VARCHAR2(30);
-- Add comments to the columns 
comment on column T_R_CATEGORY.islock
  is '位置是否已经被锁定。0 未锁定；1 已锁定';
comment on column T_R_CATEGORY.locktime
  is '锁定/解锁时间';
 
 
 create or replace procedure P_LOCK_CATEGORY_AUTO_FIX(p_categoryid varchar2,
                                                X_MSG        OUT VARCHAR2) is
  v_nstatus number;
  v_nrecod  number;
  cursor c_base is
    select r.categoryid c_cid, r.id c_rid, r.locknum c_locknum
      from t_r_reference r
     where r.categoryid = p_categoryid
       and r.islock = 1
     order by r.locknum;
  c_row c_base%rowtype;
begin
  v_nstatus := pg_log_manage.f_startlog('P_LOCK_CATEGORY_FIX',
                                        '自动修正货架商品锁定位置的排序号');
  for c_row in c_base loop
    P_LOCK_CATEGORY_AUTO(c_row.c_cid, c_row.c_rid, c_row.c_locknum, x_msg);
  end loop;
 
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);

exception
  when others then
    rollback;
    DBMS_OUTPUT.PUT_LINE('execute error occurred.');
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end P_LOCK_CATEGORY_AUTO_FIX; 

CREATE OR REPLACE PROCEDURE P_LOCK_CATEGORY_AUTO(P_CATEGORYID VARCHAR2,
                                                 P_RID        VARCHAR2,
                                                 P_LOCKNUM    NUMBER,
                                                 X_MSG        OUT VARCHAR2) IS
  v_nstatus            number;
  v_nrecod             number := 0;
  V_COUNT              NUMBER;
  V_LOCK               NUMBER;
  V_GOODS_CUR_LOCATION NUMBER; --记录商品当前货架的位置
  V_GOODS_CUR_SORTID   t_r_reference.SORTID%TYPE;
  V_GOODS_MOVE_SORTID  t_r_reference.SORTID%TYPE;
  V_CATID              VARCHAR2(50);
  --前移
  CURSOR CUR_MOVE_FORWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID,
           T.SORTID,
           T.REFNODEID,
           T.GOODSID,
           F_FORMATE_DATE(T.LOADDATE) LOADDATE
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.SORTID < P_CUR_SORTID
       AND T.SORTID >= P_MOVE_SORTID
       AND T.ISLOCK = 0
     ORDER BY T.SORTID desc;
  --后移
  CURSOR CUR_MOVE_BACKWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                 P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID,
           T.SORTID,
           T.REFNODEID,
           T.GOODSID,
           F_FORMATE_DATE(T.LOADDATE) LOADDATE
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.SORTID <= P_MOVE_SORTID
       AND T.SORTID > P_CUR_SORTID
       AND T.ISLOCK = 0
     ORDER BY T.SORTID;

BEGIN
  v_nstatus := pg_log_manage.f_startlog('P_LOCK_CATEGORY_AUTO',
                                        '锁定货架商品位置P_CATEGORYID=' ||
                                        P_CATEGORYID || ',P_RID=' || P_RID ||
                                        ',P_LOCKNUM=' || P_LOCKNUM);
  SELECT ID
    INTO V_CATID
    FROM T_R_CATEGORY T
   WHERE T.CATEGORYID = P_CATEGORYID;

  SELECT T.NUM
    INTO V_GOODS_CUR_LOCATION
    FROM (select r.id,
                 row_number() over(partition by r.categoryid order by r.sortid DESC) num
            from t_r_reference r
           WHERE R.categoryid = P_CATEGORYID) T
   WHERE T.id = P_RID;
  --dbms_output.put_line(P_RID || '|' || V_GOODS_CUR_LOCATION);

  IF P_LOCKNUM > V_GOODS_CUR_LOCATION THEN
    --商品位置往前移动
    SELECT T.SORTID
      INTO V_GOODS_CUR_SORTID
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.ID = P_RID;
  
    SELECT count(1), MAX(SORTID)
      INTO V_COUNT, V_GOODS_MOVE_SORTID
      FROM (SELECT ROWNUM RN, A.CATEGORYID, A.REFNODEID, A.SORTID
              FROM (SELECT T.CATEGORYID, T.REFNODEID, T.SORTID
                      FROM t_r_reference T
                     WHERE T.CATEGORYID = P_CATEGORYID
                     ORDER BY T.SORTID DESC) A)
     WHERE RN = P_LOCKNUM;
    IF V_COUNT = 0 THEN
      SELECT (MIN(SORTID) - 1)
        INTO V_GOODS_MOVE_SORTID
        FROM T_R_REFERENCE R
       WHERE R.CATEGORYID = P_CATEGORYID;
    END IF;
    UPDATE t_r_reference T
       SET T.SORTID = V_GOODS_MOVE_SORTID
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.ID = P_RID;
    --dbms_output.put_line('前移' || V_GOODS_MOVE_SORTID || '|' ||V_GOODS_CUR_SORTID);
    FOR REC_MOVE_GOODS IN CUR_MOVE_FORWARD_GOODS(V_GOODS_MOVE_SORTID,
                                                 V_GOODS_CUR_SORTID) LOOP
      UPDATE t_r_reference T
         SET T.SORTID = V_GOODS_CUR_SORTID
       WHERE ROWID = REC_MOVE_GOODS.ROW_ID;
      --发送修改消息给数据中心
      insert into t_a_messages
        (id, status, type, transactionid, message)
      values
        (SEQ_T_A_MESSAGES.NEXTVAL,
         -1,
         'RefModifyReq',
         '',
         REC_MOVE_GOODS.GOODSID || ':' || P_CATEGORYID || ':' || V_CATID || ':' ||
         REC_MOVE_GOODS.REFNODEID || ':' || V_GOODS_CUR_SORTID || ':' ||
         REC_MOVE_GOODS.LOADDATE);
      V_GOODS_CUR_SORTID := REC_MOVE_GOODS.SORTID;
      v_nrecod           := v_nrecod + 1;
    END LOOP;
    X_MSG := 'SUCCESS';
    COMMIT;
  ELSIF P_LOCKNUM < V_GOODS_CUR_LOCATION THEN
    --商品位置往后移动
    SELECT T.SORTID
      INTO V_GOODS_CUR_SORTID
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.ID = P_RID;
  
    SELECT COUNT(1), MAX(SORTID)
      INTO V_COUNT, V_GOODS_MOVE_SORTID
      FROM (SELECT ROWNUM RN, A.CATEGORYID, A.REFNODEID, A.SORTID
              FROM (SELECT T.CATEGORYID, T.REFNODEID, T.SORTID
                      FROM t_r_reference T
                     WHERE T.CATEGORYID = P_CATEGORYID
                     ORDER BY T.SORTID DESC) A)
     WHERE RN = P_LOCKNUM;
    IF V_COUNT = 0 THEN
      SELECT (MIN(SORTID) - 1)
        INTO V_GOODS_MOVE_SORTID
        FROM T_R_REFERENCE R
       WHERE R.CATEGORYID = P_CATEGORYID;
    END IF;
    UPDATE t_r_reference T
       SET T.SORTID = V_GOODS_MOVE_SORTID
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.ID = P_RID;
    -- dbms_output.put_line('后移' || V_GOODS_MOVE_SORTID || '|' ||V_GOODS_CUR_SORTID);
    FOR REC_MOVE_GOODS IN CUR_MOVE_BACKWARD_GOODS(V_GOODS_MOVE_SORTID,
                                                  V_GOODS_CUR_SORTID) LOOP
      UPDATE t_r_reference T
         SET T.SORTID = V_GOODS_CUR_SORTID
       WHERE ROWID = REC_MOVE_GOODS.ROW_ID;
      --发送修改消息给数据中心
      insert into t_a_messages
        (id, status, type, transactionid, message)
      values
        (SEQ_T_A_MESSAGES.NEXTVAL,
         -1,
         'RefModifyReq',
         '',
         REC_MOVE_GOODS.GOODSID || ':' || P_CATEGORYID || ':' || V_CATID || ':' ||
         REC_MOVE_GOODS.REFNODEID || ':' || V_GOODS_CUR_SORTID || ':' ||
         REC_MOVE_GOODS.LOADDATE);
      V_GOODS_CUR_SORTID := REC_MOVE_GOODS.SORTID;
      v_nrecod           := v_nrecod + 1;
    END LOOP;
    X_MSG := 'SUCCESS';
    COMMIT;
  ELSE
    X_MSG := 'THE LOCKNUM HAS MATCHED';
  END IF;

  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
EXCEPTION
  WHEN OTHERS THEN
    X_MSG := 'EXCEPTION';
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
END P_LOCK_CATEGORY_AUTO;

CREATE OR REPLACE PROCEDURE P_LOCK_CATEGORY(P_CATEGORYID VARCHAR2,
                                            P_RID        VARCHAR2,
                                            P_LOCKNUM    NUMBER,
                                            P_TYPE       VARCHAR2,
                                            X_MSG        OUT VARCHAR2) IS
  v_nstatus            number;
  v_nrecod             number;
  V_COUNT              NUMBER;
  V_LOCK               NUMBER;
  V_GOODS_CUR_LOCATION NUMBER; --记录商品当前货架的位置
  V_GOODS_CUR_SORTID   t_r_reference.SORTID%TYPE;
  V_GOODS_MOVE_SORTID  t_r_reference.SORTID%TYPE;
  V_GOODS_ID           VARCHAR2(100) := '';
  V_CATID              VARCHAR2(100) := '';

  --前移
  CURSOR CUR_MOVE_FORWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID,
           T.SORTID,
           T.REFNODEID,
           T.GOODSID,
           F_FORMATE_DATE(T.LOADDATE) LOADDATE
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.SORTID < P_CUR_SORTID
       AND T.SORTID >= P_MOVE_SORTID
       AND T.ISLOCK = 0
     ORDER BY T.SORTID desc;
  --后移
  CURSOR CUR_MOVE_BACKWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                 P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID,
           T.SORTID,
           T.REFNODEID,
           T.GOODSID,
           F_FORMATE_DATE(T.LOADDATE) LOADDATE
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.SORTID <= P_MOVE_SORTID
       AND T.SORTID > P_CUR_SORTID
       AND T.ISLOCK = 0
     ORDER BY T.SORTID;
  --删除
  CURSOR CUR_MOVE_DET_GOODS(P_CUR_SORTID t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID, T.SORTID
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.SORTID < P_CUR_SORTID
       AND T.ISLOCK = 0
     ORDER BY T.SORTID DESC;

BEGIN
  v_nstatus := pg_log_manage.f_startlog('P_LOCK_CATEGORY',
                                        '锁定货架商品位置P_CATEGORYID=' ||
                                        P_CATEGORYID || ',P_RID=' || P_RID ||
                                        ',P_LOCKNUM=' || P_LOCKNUM ||
                                        ',P_TYPE=' || P_TYPE);
  SELECT ID
    INTO V_CATID
    FROM T_R_CATEGORY T
   WHERE T.CATEGORYID = P_CATEGORYID;
  IF P_TYPE = 'UPDATE' THEN
    SELECT COUNT(1)
      INTO V_COUNT
      FROM t_r_reference GD
     WHERE GD.CATEGORYID = P_CATEGORYID
       AND GD.LOCKNUM = P_LOCKNUM;
    --dbms_output.put_line(P_RID || '|' || V_COUNT);
    IF V_COUNT > 0 THEN
      X_MSG := '此位置已被其它商品定位';
    ELSE
      SELECT T.NUM
        INTO V_GOODS_CUR_LOCATION
        FROM (select r.id,
                     row_number() over(partition by r.categoryid order by r.sortid DESC) num
                from t_r_reference r
               WHERE R.categoryid = P_CATEGORYID) T
       WHERE T.id = P_RID;
      -- dbms_output.put_line(P_RID || '|' || V_GOODS_CUR_LOCATION);
      IF P_LOCKNUM = V_GOODS_CUR_LOCATION THEN
        --如果定位的位置与当地商品所在货架的位置一直，则只需更新货架上
        --此商品的定位号，无需处理其它商品的排序号
        UPDATE t_r_reference GD
           SET GD.LOCKNUM = P_LOCKNUM, GD.ISLOCK = 1, GD.LOCKTIME = SYSDATE
         WHERE GD.CATEGORYID = P_CATEGORYID
           AND GD.ID = P_RID;
      
      ELSIF P_LOCKNUM > V_GOODS_CUR_LOCATION THEN
        --商品位置往前移动
      
        SELECT T.SORTID
          INTO V_GOODS_CUR_SORTID
          FROM t_r_reference T
         WHERE T.CATEGORYID = P_CATEGORYID
           AND T.ID = P_RID;
      
        SELECT COUNT(1), MAX(SORTID)
          INTO V_COUNT, V_GOODS_MOVE_SORTID
          FROM (SELECT ROWNUM RN, A.CATEGORYID, A.REFNODEID, A.SORTID
                  FROM (SELECT T.CATEGORYID, T.REFNODEID, T.SORTID
                          FROM t_r_reference T
                         WHERE T.CATEGORYID = P_CATEGORYID
                         ORDER BY T.SORTID DESC) A)
         WHERE RN = P_LOCKNUM;
      
        IF V_COUNT = 0 THEN
          SELECT (MIN(SORTID) - 1)
            INTO V_GOODS_MOVE_SORTID
            FROM T_R_REFERENCE R
           WHERE R.CATEGORYID = P_CATEGORYID;
        END IF;
        UPDATE t_r_reference T
           SET T.SORTID   = V_GOODS_MOVE_SORTID,
               T.LOCKNUM  = P_LOCKNUM,
               T.ISLOCK   = 1,
               T.LOCKTIME = SYSDATE
         WHERE T.CATEGORYID = P_CATEGORYID
           AND T.ID = P_RID;
        -- dbms_output.put_line('前移' || V_GOODS_MOVE_SORTID || '|' ||V_GOODS_CUR_SORTID);
        FOR REC_MOVE_GOODS IN CUR_MOVE_FORWARD_GOODS(V_GOODS_MOVE_SORTID,
                                                     V_GOODS_CUR_SORTID) LOOP
          UPDATE t_r_reference T
             SET T.SORTID = V_GOODS_CUR_SORTID
           WHERE ROWID = REC_MOVE_GOODS.ROW_ID;
          --发送修改消息给数据中心
          insert into t_a_messages
            (id, status, type, transactionid, message)
          values
            (SEQ_T_A_MESSAGES.NEXTVAL,
             -1,
             'RefModifyReq',
             '',
             REC_MOVE_GOODS.GOODSID || ':' || P_CATEGORYID || ':' ||
             V_CATID || ':' || REC_MOVE_GOODS.REFNODEID || ':' ||
             V_GOODS_CUR_SORTID || ':' || REC_MOVE_GOODS.LOADDATE);
          V_GOODS_CUR_SORTID := REC_MOVE_GOODS.SORTID;
        END LOOP;
      
      ELSE
        --商品位置往后移动
        SELECT T.SORTID
          INTO V_GOODS_CUR_SORTID
          FROM t_r_reference T
         WHERE T.CATEGORYID = P_CATEGORYID
           AND T.ID = P_RID;
      
        SELECT COUNT(1), MAX(SORTID)
          INTO V_COUNT, V_GOODS_MOVE_SORTID
          FROM (SELECT ROWNUM RN, A.CATEGORYID, A.REFNODEID, A.SORTID
                  FROM (SELECT T.CATEGORYID, T.REFNODEID, T.SORTID
                          FROM t_r_reference T
                         WHERE T.CATEGORYID = P_CATEGORYID
                         ORDER BY T.SORTID DESC) A)
         WHERE RN = P_LOCKNUM;
        IF V_COUNT = 0 THEN
          SELECT (MIN(SORTID) - 1)
            INTO V_GOODS_MOVE_SORTID
            FROM T_R_REFERENCE R
           WHERE R.CATEGORYID = P_CATEGORYID;
        END IF;
        UPDATE t_r_reference T
           SET T.SORTID   = V_GOODS_MOVE_SORTID,
               T.LOCKNUM  = P_LOCKNUM,
               T.ISLOCK   = 1,
               T.LOCKTIME = SYSDATE
         WHERE T.CATEGORYID = P_CATEGORYID
           AND T.ID = P_RID;
        --dbms_output.put_line('后移' || V_GOODS_MOVE_SORTID || '|' || V_GOODS_CUR_SORTID);
        FOR REC_MOVE_GOODS IN CUR_MOVE_BACKWARD_GOODS(V_GOODS_MOVE_SORTID,
                                                      V_GOODS_CUR_SORTID) LOOP
          UPDATE t_r_reference T
             SET T.SORTID = V_GOODS_CUR_SORTID
           WHERE ROWID = REC_MOVE_GOODS.ROW_ID;
          --发送修改消息给数据中心
          insert into t_a_messages
            (id, status, type, transactionid, message)
          values
            (SEQ_T_A_MESSAGES.NEXTVAL,
             -1,
             'RefModifyReq',
             '',
             REC_MOVE_GOODS.GOODSID || ':' || P_CATEGORYID || ':' ||
             V_CATID || ':' || REC_MOVE_GOODS.REFNODEID || ':' ||
             V_GOODS_CUR_SORTID || ':' || REC_MOVE_GOODS.LOADDATE);
          V_GOODS_CUR_SORTID := REC_MOVE_GOODS.SORTID;
        END LOOP;
      
      END IF;
    END IF;
  
  ELSIF P_TYPE = 'DEL' THEN
    SELECT T.SORTID, T.GOODSID
      INTO V_GOODS_CUR_SORTID, V_GOODS_ID
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.ID = P_RID;
  
    FOR REC_MOVE_GOODS IN CUR_MOVE_DET_GOODS(V_GOODS_CUR_SORTID) LOOP
      UPDATE t_r_reference T
         SET T.SORTID = V_GOODS_CUR_SORTID
       WHERE ROWID = REC_MOVE_GOODS.ROW_ID;
      V_GOODS_CUR_SORTID := REC_MOVE_GOODS.SORTID;
    END LOOP;
    DELETE FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.ID = P_RID;
    COMMIT;
    SELECT COUNT(1)
      INTO V_LOCK
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.ISLOCK = 1;
    IF V_LOCK = 0 THEN
      UPDATE T_R_CATEGORY
         SET ISLOCK = 0, LOCKTIME = SYSDATE
       WHERE CATEGORYID = P_CATEGORYID;
    END IF;
  
  ELSE
    X_MSG := 'NOT MATCH';
  END IF;

  if V_GOODS_ID = '' then
    X_MSG := 'SUCCESS';
  ELSE
    --V_GOODS_ID != '' ,说明是删除的，要返回被删除的goodsid
    X_MSG := 'SUCCESS' || '|' || V_GOODS_ID;
  END IF;
  COMMIT;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
EXCEPTION
  WHEN OTHERS THEN
    X_MSG := 'EXCEPTION';
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
END P_LOCK_CATEGORY;

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM5.0.0.0.065_SSMS','MM5.0.0.0.069_SSMS');
commit;