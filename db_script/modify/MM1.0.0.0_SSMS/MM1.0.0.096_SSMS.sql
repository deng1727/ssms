--------ͬһ��ʵ�������
-----��PPMS���ݿ��û�����Ȩ������
grant select on v_cm_content_zcom to &ssms;--����ϵͳ
grant select on ppms_service to &ssms;--����ϵͳ

---��SSMS�û��´���ͬ���
create synonym ppms_v_cm_content_zcom  for &mm_ppms.v_cm_content_zcom;
create or replace synonym ppms_service        for &mm_ppms.ppms_service;

--------ͬһ��ʵ�����end-------

--------��ͬʵ�������
-----��SSMS�û��½�����PPMS��Dblink(����Ѿ�����dblink���˲���ʡ��)
create database link PPMSTOSSMS connect to MM_CMS identified by MM_CMS using 'DB3_9';
---��SSMS�û��´���ͬ���
create or replace synonym ppms_v_cm_content_zcom        for v_cm_content_zcom@PPMSTOSSMS;
create or replace synonym ppms_service        for   ppms_service@PPMSTOSSMS;

--------��ͬʵ����� end-----

-----����ZCOM_ID�Զ���������
-- Create sequence 
create sequence SEQ_ZCOM_ID
minvalue 1
maxvalue 99999999
start with 1087
increment by 1
nocache
cycle;


-- ����Zcom������ʱ��ÿ��drop�ñ���PPMS��ͼV_CM_CONTENT_ZCOMȡ����
create table V_CM_CONTENT_ZCOM
(
  CONTENTID     VARCHAR2(12),
  CONTENTCODE   VARCHAR2(10),
  NAME          VARCHAR2(60),
  APPDESC       VARCHAR2(4000),
  CONTENTFEE    NUMBER(8),
  ONLINEDATE    DATE,
  PRODUCTID     VARCHAR2(12),
  CHARGETIME    NUMBER(1),
  CARTOONPICURL VARCHAR2(256),
  LOGO1URL      VARCHAR2(256),
  LOGO2URL      VARCHAR2(256),
  LOGO3URL      VARCHAR2(256),
  LOGO4URL      VARCHAR2(256),
  ICPSERVID     VARCHAR2(10),
  ICPCODE       VARCHAR2(6),
  LUPDDATE      DATE
);

----- ����Zcom ͬ����ʱ��
create table T_SYNCTIME_TMP_ZCOM
(
  NAME      VARCHAR2(60),
  CONTENTID VARCHAR2(12),
  LUPDDATE  DATE
);



-- ����Zcomͬ����ʷʱ���
create table T_LASTSYNCTIME_ZCOM
(
  LASTTIME DATE not null
);

-- ����Zcom������Ϣ��
create table Z_PPS_MAGA
(
  ID    NUMBER not null,
  NAME  VARCHAR2(200) not null,
  LOGO  VARCHAR2(100),
  DESCS VARCHAR2(2000)
);

-- ����Zcom���������
create table Z_PPS_MAGA_LS
(
  ID             NUMBER,
  MAGA_NAME      VARCHAR2(100),
  MAGA_PERIODS   VARCHAR2(10),
  MAGA_OFFICE    VARCHAR2(400),
  MAGA_DATE      VARCHAR2(100),
  PERIOD         VARCHAR2(100),
  PRICE          NUMBER,
  CONTENTID      VARCHAR2(30),
  CHARGETYPE     VARCHAR2(2),
  UPTIME         VARCHAR2(50),
  CARTOONPIC     VARCHAR2(400),
  LOG1           VARCHAR2(400),
  LOG2           VARCHAR2(400),
  LOG3           VARCHAR2(400),
  LOG4           VARCHAR2(400),
  PARENT_ID      NUMBER,
  MAGA_FULL_NAME VARCHAR2(100),
  FULL_DEVICE_ID clob,
  ICPCODE        VARCHAR2(100),
  ICPSERVID      VARCHAR2(100),
  SIZES          VARCHAR2(20),
  PERFIX         VARCHAR2(500),
  PLATFORM       VARCHAR2(500),
  LUPDDATE       DATE
);
-- Add comments to the columns 
comment on column Z_PPS_MAGA_LS.ID
  is 'id';
comment on column Z_PPS_MAGA_LS.MAGA_NAME
  is '��������';
comment on column Z_PPS_MAGA_LS.MAGA_PERIODS
  is '��������';
comment on column Z_PPS_MAGA_LS.MAGA_OFFICE
  is '����';
comment on column Z_PPS_MAGA_LS.MAGA_DATE
  is '��������';
comment on column Z_PPS_MAGA_LS.PERIOD
  is '��������';
comment on column Z_PPS_MAGA_LS.PRICE
  is '�۸�λ:��';
comment on column Z_PPS_MAGA_LS.CONTENTID
  is '����ID';
comment on column Z_PPS_MAGA_LS.CHARGETYPE
  is '�Ʒ�����';
comment on column Z_PPS_MAGA_LS.UPTIME
  is '�ϼ�ʱ��';
comment on column Z_PPS_MAGA_LS.CARTOONPIC
  is 'Ԥ��ͼURL';
comment on column Z_PPS_MAGA_LS.LOG1
  is 'log1';
comment on column Z_PPS_MAGA_LS.LOG2
  is 'log2';
comment on column Z_PPS_MAGA_LS.LOG3
  is 'log3';
comment on column Z_PPS_MAGA_LS.LOG4
  is 'log4';
comment on column Z_PPS_MAGA_LS.PARENT_ID
  is '��ID';
comment on column Z_PPS_MAGA_LS.MAGA_FULL_NAME
  is 'ȫ��';
comment on column Z_PPS_MAGA_LS.SIZES
  is '��С (��λ��)';
comment on column Z_PPS_MAGA_LS.PERFIX
  is '�����׺��������,55.sis��';
comment on column Z_PPS_MAGA_LS.PLATFORM
  is 'Ӧ������ƽ̨(kjava,s60)';

---���½ű���commond��ִ��
begin
ctx_ddl.create_preference('chinese_lexer','CHINESE_VGRAM_LEXER');
end;
/
-- ��commondִ�н���


create index IND_V_DEVICEID on z_pps_maga_ls(full_device_id) indextype is ctxsys.context parameters('lexer chinese_lexer');
-----��ʼ��zcom��������
insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (854, 'cookie world', '/defaultSite/image/icon.png', '��cookie world�����ɹ����ձ���ҵ������������ģ����������һ������Ӣ��ͥ�ĸ߶�����ʱ����־��Ŀ�������ӵ��0-7�꺢�ӵ�ʱ�и�ĸ����Ӣ��ͥ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1619, '�ټҽ�̳', '/defaultSite/image/icon.png', '���ټҽ�̳��������˵��ʷΪ����ǿ��̽����ʷ���ࡣͬʱ�������漰�������ġ���ѧ���۵���෽�档');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1455, '��������', '/defaultSite/image/icon.png', '���������¡���־���¶�������������Сƽ���ٷ����ˡ�ԭ����ʱ���ܱ༭��¸����ٳ����ˣ��ԡ��й���һ������������־�������ţ��ԡ����˶��Ǽ�¼�ߡ�Ϊ�쿯���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (608, '�Ƹ�����', '/defaultSite/image/icon.png', '���Ȿ��־����ͬ��һ�����ʽ���������������������һȺ�ˣ�������������ȴ���������ң����������ֲ�������ࣻ��ҵ�гɵ���׷��������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1549, '����־', '/defaultSite/image/icon.png', '������־����Ϊ���ڵ�һʱ���ܿ�����2006��7�´������������С����ʷ��С��й����İ쿯�����֡����ʻ�����������ʱ�л���ʵ���ԡ��İ쿯��ּ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1461, '�����ܱ�', '/defaultSite/image/icon.png', '�������ܱ��������Ϸ���ҵ��ý���ţ����Ϸ����б��������죬��һ��Ϊ���㵱���й��������ĸ߶˶��ߡ���ᾫӢ�Ķ�������Ƴ���һ��Ʒλ���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (435, '���˵���', '/defaultSite/image/icon.png', '�����˵��ԡ���־���й���һ��רҵIT����ý�壬���Ƚ�����Ʒ���⡱�ĸ�������й���ʹ�����⡱�Ŀ�ѧ��ʶ����ϵ�����ݴ�½���������������ƾ���Ź��ʻ���ʵ����Ϊ�й�IT�����г��ġ��̿��顱��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (592, '�ùܼ�', '/defaultSite/image/icon.png', '��ʱ�С����������ڿ��г��������������Ʒ����־Good Housekeepingǿǿ���֣���������������������Ʒλ�Ͷ�ʱ�м�ͥ�������������������ں���һ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (814, '��Ϫ', '/defaultSite/image/icon.png', '����Ϫ���ǹ����׼����ִ�����ʱ���鰮Ϊ�����Ů��ʱ����־����Ŀǰ�й����֪���ȵ��ഺʱ������־��������ĵط����а����а���ĵط����С���Ϫ����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (586, '���ĵ���', '/defaultSite/image/icon.png', '�����ĵ����ǹ��ڵ�һ����Ʒλ�����չ�ֶ�Ԫ�Ļ�����־���Զ����ӵ㡢������ġ���ѧ����Ψ��չ��Ϊ�쿯��ּ���п��ۺ�ĿƼ�������Դ����ȷ���ȫ���Ļ���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1378, '��������', '/defaultSite/image/icon.png', '�����������־������FROM LIFE,ABOUT LIFE,FOR LIFE���������ͻ������ķ������ߣ�����߶����ʽ����ʱ��������Ϊ�����㣬��������������֮����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1581, '�����ھ�����', '/defaultSite/image/icon.png', '�������ھ����ˡ���־������1988�꣬�ǹ����⹫�����е���ó������־��ϵ��е��ҵ�����硰������ý�������Ա������ó����Ψһ��������־���й��߶�Ⱥ���������ҵ����֮һ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (889, '����', '/defaultSite/image/icon.png', '��ORANGE��������־��һ���������ר����ʱ����־��ÿ��ͨ��FASHION NEWS��BEAUTY NEWS����װ�༭�����ݱ༭�Ǹ������ṩ��һ���ܵ����ĵĹ���ָ�ϡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1593, '������', '/defaultSite/image/icon.png', '�������硷��һ������������ȫ��λ��ӳ�����������Ρ����á��Ļ����Ƽ������µ��ۺ��Ե��й����������и߶����Ӱ����������ý�壬������־����ر��������ֲᣬ˼�������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1345, '������ֲ�', '/defaultSite/image/icon.png', '��������ֲ�����־����ע��Ŀ��۽��ڹ����߹�ע�����������ص��ע���������������ȹ��ҵĸ�ˮƽְҵ��������ע�����������ش����£������й�������ҵ�ķ�չ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1566, '����������ͥ', '/defaultSite/image/icon.png', '������������ͥ����ǰ��Ϊ�����ջ�����ͥ�о�����ͨ������������г�������ʱ��������Ѷ����Ϊ���ǹ������ü�ͥ�������г�����ʱ�з�ꡣ');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (581, '����װ', '/defaultSite/image/icon.png', '������װ�����й���һ�������Ĵ���������־��һ�������ۺ���ʱ����־�����������������˹���������ȷ������Ҫ������Ҳ���̶ȵ��������ֽ�������˵���ʵ����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1387, '�϶������ܿ�', '/defaultSite/image/icon.png', '�й�������ĩ���������ֲ��ڱ𴦣�������ǿ������϶��ܿ���־��һ�������ṩ�̣��ṩ���ֵĿ���������Ŀ�����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (863, '�Ϸ�', '/defaultSite/image/icon.png', '���Ϸ硷��һ���Գ������顢�������Ϊ�������ݵ��ഺŮ����־�����ص���Ʒ�񣬾��ʵ�ʱ�����֣����µ������װ�������ഺ�ļ��ļ��飬���޳����ﲻͬ�羰�İ�����¡�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1486, '������ĩ', '/defaultSite/image/icon.png', '��������ĩ�����������걨ȫ���ӱ���һ���³���������ܱ������߶�λ��35�����µĳ���֪ʶ���ӣ���Щ׷�������顢�罻��̸�ʡ�������Ʒλ�������ˡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (582, 'ʱ�а�ɯ', '/defaultSite/image/icon.png', '��ʱ�а�ɯ���ǡ�ʱ�С���ӵ��135����ʷȫ����������ʱװ��־��Harper s BAZAAR����Ȩ�����Ľᾧ����һ��ȫ���Ե��������������ĸ߼�ʱװ��־��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (578, 'ʱ������', '/defaultSite/image/icon.png', '��ʱ�����Ρ�������ʱ����־�����������ҵ���ѧ���������Դ�ͷḻ���飬�����ڳ�Ϊ������������֪��ǿ�������ߵ���Ϣ��Դ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (574, 'ʱ��ʱ��', '/defaultSite/image/icon.png', 'һ�����ӱ��鱦Ϊ����Ԫ�ص�ʱ��������־��һ�����˽��й��ڵ��ӱ��鱦�����г�����ҵ����־��һ����׼��λ�ڸ߶������г��ġ���߹��Ͷ�ʻر���ʱ��������־��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (572, 'ʱ������', '/defaultSite/image/icon.png', '��ʱ�����������������ѷ��������ʿ��ȫ��λ��ָ���������������������롢��Ȥ���������Լ������������־�����й���Գɹ���ʿ�ġ����Ӱ�����������������ڿ���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1569, '�г��t��', '/defaultSite/image/icon.png', '���г��t�����ԡ����Ű쿯����˼�룬���߳�ȥ���������������ȫ��������쿯���Կ�������Ұ�������ڶ�ҵ���ǻۣ���Դ�ҵ�ߡ���С��ҵ��Ӫ�ߡ�������ע���õ���ʿ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1543, '��ϯ�����', '/defaultSite/image/icon.png', '����ϯ����١����ɹ��ڴ�ý��ͷ��������紫ý������������ý���ʱ���ͷIDG����Ͷ�ʵ�ý��������䶨λΪ�����ڵ�һ���������е�����CFO��Ⱥ�����רҵ��Ѷý�塱��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1467, 'Ͷ�������', '/defaultSite/image/icon.png', '��Ͷ������ơ��Ǽ�����Ͷ��Ϊ���򡢴����������Ϊ��Ѷ�Ĺ���Ψһ���ۺ��¿�����־��רҵ��ˮ׼Ϊ�����ṩͶ����Ƶ���ѯ����Ϊ�쿯��ּ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1493, '��Ĭ���', '/defaultSite/image/icon.png', '����Ĭ��ء����ִ��˵Ŀ���ά���أ����������ˡ�Ů����֮��ĸ�Ц���£�Ũ�����������е����Ρ��ĵ�������office���Ȥ�����£�������������еĿ������룡');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (14909, '�й����ú���Ϣ��', '/defaultSite/image/icon.png', '���й����ú���Ϣ�������й��׼�רע�ڲ�ҵ�������ű����Ĳƾ�����־����������Ƚ���߲���ߣ��ƶ���ҵ����ת�����������ҵ��ͬ��չ������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1363, '�й������ܿ�', '/defaultSite/image/icon.png', '���й������ܿ����������ձ��������������־���ǹ���ĿǰΩһһ��������Ϊ�����ۺϾ������ܿ���־��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1598, '�й���ҵ��', '/defaultSite/image/icon.png', '���й���ҵ�ҡ���־���й�������ҵ�ƾ���־���ϵ��쵼�ߣ����й���ҵ�ҽײ㹲ͬ�ɳ���������1985�꣬�����ձ������죬�й�������ҵ�ƾ���־���ϵ��쵼�ߡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1451, '�й�����', '/defaultSite/image/icon.png', '���й���������Ψһ�ԡ��й�����ͷ������������־�������߹���50����������£��ѿ��Ը���й��������������͵Ĺ��������룬�ڹ�����ӵ�й㷺�Ķ��ߺ�Ӱ������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (924, '׿Խ���', '/defaultSite/image/icon.png', '��׿Խ��ơ���־����������ڵ�רҵ�Ժ�Ȩ���ԣ��������ڸ������е�ָ����λ�������Ӱ���ŵ�ǰ�ͽ����ƾ��ߡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1327, '������ֲ�', '/defaultSite/image/icon.png', '��������ֲ���������1993��5�£���һ���Ա��������������Ϊ������˹�����̳�ȵ���Ļ��ۺ����ڿ���������һ����������������ǵĿ��֡�');

-----
Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)

                                                Values   ('14104148', 140, NULL, SYSDATE);

 Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME,RANDOMFACTOR)

                                        Values(140, '�ƶ���Ʒ', 0, 0, 1,NULL,100);

Insert into T_CATERULE_COND (RULEID, CONDTYPE, WSQL)

                                                Values(140, 10, 'icpcode = ''100246''');



----���v_service��zcomӦ�õĹ���
drop materialized view V_SERVICE;
create table V_SERVICE as 
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
       decode(p.chargetime || v2.paytype, '20', p.feedesc, v2.chargedesc) as chargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from om_company v1, v_om_product v2, OM_PRODUCT_CONTENT p, cm_content c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id 
    and c.thirdapptype in ('1','2');  --��ͨӦ�ú�widgetӦ��;

create unique index index_v_service_pk on V_SERVICE (icpcode, icpservid, providertype);
create index index_v_service on v_service(contentid);



----ppms ����ͬ���Ż���v_cm_content��ͼ�޸�Ϊ��

RENAME  v_cm_content  TO ppms_v_cm_content;
create table v_cm_content as select * from ppms_v_cm_content;
----����APӦ�ú�����
create table T_CONTENT_BACKLIST
(
  CONTENTID VARCHAR2(30),
  INDATE    VARCHAR2(30)
);
comment on column T_CONTENT_BACKLIST.CONTENTID
  is 'Ӧ������ID';
comment on column T_CONTENT_BACKLIST.INDATE
  is '��������Ч��';
-- Create/Recreate indexes 
create index T_CONTENT_BLACKLIST_CID on T_CONTENT_BACKLIST (CONTENTID);
---ȫ�� ����Ʒ��������վ�Ʒ���ֽ�������
update t_caterule set RANDOMFACTOR='0' where ruleid='1';
update t_caterule_cond set osql='c.COMPECOUNT desc nulls last '  where ruleid='1';


--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.095_SSMS','MM1.0.0.096_SSMS');
commit;