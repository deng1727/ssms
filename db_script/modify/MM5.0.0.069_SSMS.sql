insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS)
values ('1_0813_RES_LOCK_LOCATION', '商品锁定管理', '商品锁定管理', '2_0801_RESOURCE', 0);

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

  --前移
  CURSOR CUR_MOVE_FORWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID, T.SORTID
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.SORTID < P_CUR_SORTID
       AND T.SORTID >= P_MOVE_SORTID
       AND T.ISLOCK = 0
     ORDER BY T.SORTID desc;
  --后移
  CURSOR CUR_MOVE_BACKWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                 P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID, T.SORTID
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
  
    SELECT COUNT(1),MAX(SORTID)
      INTO V_COUNT,V_GOODS_MOVE_SORTID
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

  --前移
  CURSOR CUR_MOVE_FORWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID, T.SORTID
      FROM t_r_reference T
     WHERE T.CATEGORYID = P_CATEGORYID
       AND T.SORTID < P_CUR_SORTID
       AND T.SORTID >= P_MOVE_SORTID
       AND T.ISLOCK = 0
     ORDER BY T.SORTID desc;
  --后移
  CURSOR CUR_MOVE_BACKWARD_GOODS(P_MOVE_SORTID t_r_reference.SORTID%TYPE,
                                 P_CUR_SORTID  t_r_reference.SORTID%TYPE) IS
    SELECT ROWID ROW_ID, T.SORTID
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
        dbms_output.put_line('前移' || V_GOODS_MOVE_SORTID || '|' ||
                             V_GOODS_CUR_SORTID);
        FOR REC_MOVE_GOODS IN CUR_MOVE_FORWARD_GOODS(V_GOODS_MOVE_SORTID,
                                                     V_GOODS_CUR_SORTID) LOOP
          UPDATE t_r_reference T
             SET T.SORTID = V_GOODS_CUR_SORTID
           WHERE ROWID = REC_MOVE_GOODS.ROW_ID;
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