---添加精品评分列
alter table T_CONTENT_COUNT add compecount number default 0 not null;
 ----添加MO_widget专区自动上架规则  T_CATEGORY_RULE 中cid为更新货架
  Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('6565769', 138, NULL, SYSDATE);
 Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(138, 'MO_widget专区', 0, 0, 1,NULL);
Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(138, '', 10, 'SUBTYPE=2', ' dbms_random.value() ',-1, NULL);

 ----添加www_widget专区自动上架规则  T_CATEGORY_RULE 中cid为更新货架
  Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('10791615', 139, NULL, SYSDATE);
 Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(139, 'WWW_widget专区', 0, 0, 1,NULL);
Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(139, '', 10, 'SUBTYPE=2', ' dbms_random.value() ',-1, NULL);


alter table T_R_GCONTENT add subtype varchar2(2) default 0;
-- Add comments to the columns 
comment on column T_R_GCONTENT.subtype
  is '1表示mm普通应用,2表示widget应用,0表示无意义';

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
              '0015',
              decode(f.status||f.substatus, '61', '0006','0008'),
              '1015',
              decode(f.status||f.substatus, '61', '0006','0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L',d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company         e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype in ('1','2')--增加widget应用
   ;

   --屏蔽精品货架
   update t_r_category c set c.state=0 where c.id in ('1257461','1257465','1257470');
   -- 屏蔽精品货架下的机型货架
   update t_r_category c set c.state=0 where c.id in (select id from t_r_base b where b.parentid in ('1257461','1257465','1257470') and b.type='nt:category');

--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.093_SSMS','MM1.0.0.095_SSMS');
commit;