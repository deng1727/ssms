-- Add/modify columns 
alter table T_SYNC_TACTIC_MOTO add SUBTYPE VARCHAR2(2);
-- Add comments to the columns 
comment on column T_SYNC_TACTIC_MOTO.SUBTYPE
  is '12 MOTO应用,16 htc应用';


update t_sync_tactic_moto t set t.subtype='12';


create or replace view v_cm_motoext as
select t.appid,
         t.bcontenttype,
         t.btype,
         t.developername,
         t.onlinedate,
         e.typename,
         e.stypename
    from s_cm_content_motoext t, s_cm_ext_moto_type e
   where t.btype = e.stypeid
   and t.thirdapptype = '12' and e.thirdapptype='12';


create or replace view v_cm_htcext as
select t.appid,
         t.bcontenttype,
         t.btype,
         t.developername,
         t.onlinedate,
         e.typename,
         e.stypename
    from s_cm_content_motoext t, s_cm_ext_moto_type e
   where t.btype = e.stypeid
   and t.thirdapptype = '16' and e.thirdapptype='16';


create table CM_CONTENT_HTCEXT as select * from v_cm_htcext ;




create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,
              '10',
              c.oviappid,
              '12',
              c.oviappid,
	      '16',
              c.oviappid,
              c.contentcode) ContentCode,
       c.Keywords,
       decode(c.status,
              '0006',
              decode(f.status, 2, '0006', 5, '0008'),
              '1006',
              decode(f.status, 2, '0006', 5, '0008'),
              '0015',
              decode(f.status || f.substatus, '61', '0006', '0008'),
              '1015',
              decode(f.status || f.substatus, '61', '0006', '0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L', d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,
              '0006',
              f.onlinedate,
              '1006',
              f.onlinedate,
              f.SubOnlineDate) as marketdate,
       --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM创业计划优秀应用展示',
                     e.companyname)) as companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----增加应用更新时间
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype) thirdapptype,
       c.pvcid,
       c.citysid as cityid,
       decode(f.chargetime ||
              decode(c.chargetype, '01', '02', c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
              '1',
              '0') as othernet
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f --
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or f.status = 5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status = 6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus in ('2', '3', '5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in ('1', '2', '7', '11', '12', '13', '14','16') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'));



create or replace view ppms_v_service as
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
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
   and (c.thirdapptype in ('1', '2','6','7','11','12','13','14','16') or (c.thirdapptype = '5' and c.Jilstatus = '1') )
  UNION ALL
 select
       t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       --8,
       t.servflag,--新游戏，折扣类型
       '0'||t.chargetype as chargetype,--新游戏，计费类型
       null,
       t.mobileprice,
        t.ptypeid,--新游戏，支付方式
       t.chargedesc,--新游戏，资费类型
       'B',
       'G',--全网业务
       t.servtype,--新游戏，业务类型
       t.firsttype,--新游戏，首发类型
       t.lupddate
  from t_game_service_new t;



insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (662, '606785201', 'all', '0', null, 0, '系统.工具', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (666, '606785202', 'all', '0', null, 0, '生活.优惠', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (669, '606785203', 'all', '0', null, 0, '影音.视频', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (673, '606785204', 'all', '0', null, 0, '输入.浏览', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (677, '606785205', 'all', '0', null, 0, '购物.支付', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (667, '606785206', 'all', '0', null, 0, '交通.旅行', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (649, '606785207', 'all', '0', null, 0, '书籍.阅读', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (650, '606785208', 'all', '0', null, 0, '学习.幼教', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (651, '606785209', 'all', '0', null, 0, '拍摄.美化', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (652, '606785210', 'all', '0', null, 0, '壁纸.主题', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (653, '606785211', 'all', '0', null, 0, '金融.理财', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (654, '606785212', 'all', '0', null, 0, '新闻.天气', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (655, '606785213', 'all', '0', null, 0, '趣味.其他', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (656, '606785214', 'all', '0', null, 0, '办公.效率', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (657, '606785215', 'all', '0', null, 0, '社交.聊天', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (658, '606785216', 'all', '0', null, 0, '健康.医疗', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (659, '606785217', 'all', '0', null, 0, '体育.竞技', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (660, '606785218', 'all', '0', null, 0, '动作冒险', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (661, '606785219', 'all', '0', null, 0, '棋牌天地', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (663, '606785220', 'all', '0', null, 0, '战略经营', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (664, '606785221', 'all', '0', null, 0, '赛车竞速', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (665, '606785223', 'all', '0', null, 0, '儿童娱乐', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (668, '606785225', 'all', '0', null, 0, '网络游戏', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (670, '606785227', 'all', '0', null, 0, '音乐节奏', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (671, '606785228', 'all', '0', null, 0, '休闲益智', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (672, '606785230', 'all', '0', null, 0, '模拟游戏', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (674, '606785231', 'all', '0', null, 0, '角色扮演', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (675, '606785232', 'all', '0', null, 0, '体育运动', '2013-01-24 15:22:28', null, '16');
insert into T_SYNC_TACTIC_MOTO (ID, CATEGORYID, CONTENTTYPE, UMFLAG, CONTENTTAG, TAGRELATION, APPCATENAME, CRATETIME, LASTUPDATETIME, SUBTYPE)
values (676, '606785233', 'all', '0', null, 0, '射击飞行', '2013-01-24 15:22:28', null, '16');



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.129_SSMS','MM1.1.1.135_SSMS');


commit;