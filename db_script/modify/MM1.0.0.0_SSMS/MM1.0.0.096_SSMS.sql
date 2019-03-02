--------同一个实例情况：
-----在PPMS数据库用户下授权给货架
grant select on v_cm_content_zcom to &ssms;--货架系统
grant select on ppms_service to &ssms;--货架系统

---在SSMS用户下创建同义词
create synonym ppms_v_cm_content_zcom  for &mm_ppms.v_cm_content_zcom;
create or replace synonym ppms_service        for &mm_ppms.ppms_service;

--------同一个实例情况end-------

--------不同实例情况：
-----在SSMS用户下建立到PPMS的Dblink(如果已经建立dblink，此步骤省略)
create database link PPMSTOSSMS connect to MM_CMS identified by MM_CMS using 'DB3_9';
---在SSMS用户下创建同义词
create or replace synonym ppms_v_cm_content_zcom        for v_cm_content_zcom@PPMSTOSSMS;
create or replace synonym ppms_service        for   ppms_service@PPMSTOSSMS;

--------不同实例情况 end-----

-----创建ZCOM_ID自动生成序列
-- Create sequence 
create sequence SEQ_ZCOM_ID
minvalue 1
maxvalue 99999999
start with 1087
increment by 1
nocache
cycle;


-- 创建Zcom内容临时表，每次drop该表并将PPMS视图V_CM_CONTENT_ZCOM取过来
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

----- 创建Zcom 同步临时表
create table T_SYNCTIME_TMP_ZCOM
(
  NAME      VARCHAR2(60),
  CONTENTID VARCHAR2(12),
  LUPDDATE  DATE
);



-- 创建Zcom同步历史时间表
create table T_LASTSYNCTIME_ZCOM
(
  LASTTIME DATE not null
);

-- 创建Zcom分类信息表
create table Z_PPS_MAGA
(
  ID    NUMBER not null,
  NAME  VARCHAR2(200) not null,
  LOGO  VARCHAR2(100),
  DESCS VARCHAR2(2000)
);

-- 创建Zcom内容详情表
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
  is '刊物名称';
comment on column Z_PPS_MAGA_LS.MAGA_PERIODS
  is '刊物期数';
comment on column Z_PPS_MAGA_LS.MAGA_OFFICE
  is '刊社';
comment on column Z_PPS_MAGA_LS.MAGA_DATE
  is '出刊日期';
comment on column Z_PPS_MAGA_LS.PERIOD
  is '出刊周期';
comment on column Z_PPS_MAGA_LS.PRICE
  is '价格单位:厘';
comment on column Z_PPS_MAGA_LS.CONTENTID
  is '内容ID';
comment on column Z_PPS_MAGA_LS.CHARGETYPE
  is '计费类型';
comment on column Z_PPS_MAGA_LS.UPTIME
  is '上架时间';
comment on column Z_PPS_MAGA_LS.CARTOONPIC
  is '预览图URL';
comment on column Z_PPS_MAGA_LS.LOG1
  is 'log1';
comment on column Z_PPS_MAGA_LS.LOG2
  is 'log2';
comment on column Z_PPS_MAGA_LS.LOG3
  is 'log3';
comment on column Z_PPS_MAGA_LS.LOG4
  is 'log4';
comment on column Z_PPS_MAGA_LS.PARENT_ID
  is '父ID';
comment on column Z_PPS_MAGA_LS.MAGA_FULL_NAME
  is '全称';
comment on column Z_PPS_MAGA_LS.SIZES
  is '大小 (单位Ｋ)';
comment on column Z_PPS_MAGA_LS.PERFIX
  is '程序后缀（带名字,55.sis）';
comment on column Z_PPS_MAGA_LS.PLATFORM
  is '应用适配平台(kjava,s60)';

---以下脚本在commond下执行
begin
ctx_ddl.create_preference('chinese_lexer','CHINESE_VGRAM_LEXER');
end;
/
-- 在commond执行结束


create index IND_V_DEVICEID on z_pps_maga_ls(full_device_id) indextype is ctxsys.context parameters('lexer chinese_lexer');
-----初始化zcom分类数据
insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (854, 'cookie world', '/defaultSite/image/icon.png', '《cookie world》是由广州日报报业集团主管主办的，华语世界第一份面向精英家庭的高端育儿时尚杂志。目标读者是拥有0-7岁孩子的时尚父母、精英家庭。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1619, '百家讲坛', '/defaultSite/image/icon.png', '《百家讲坛》是以正说历史为主，强调探究历史真相。同时，还将涉及地理、天文、文学理论等诸多方面。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1455, '博客天下', '/defaultSite/image/icon.png', '《博客天下》杂志由新东方教育名博徐小平担纲发行人、原京华时报总编辑朱德付担纲出版人，以“中国第一本博客新闻杂志”的名号，以“人人都是记录者”为办刊理念。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (608, '财富生活', '/defaultSite/image/icon.png', '打开这本杂志，如同打开一种生活方式。在这里，还可以邂逅这样一群人，他们享受生活却不铺张奢靡；特立独行又不标榜另类；事业有成但不追逐名利。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1549, '风尚志', '/defaultSite/image/icon.png', '《风尚志》作为国内第一时尚周刊，自2006年7月创刊以来，秉承“国际风尚、中国表达”的办刊理念，坚持“国际化、本土化、时尚化、实用性”的办刊宗旨。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1461, '风尚周报', '/defaultSite/image/icon.png', '《风尚周报》隶属南方报业传媒集团，由南方都市报倾力打造，是一本为满足当今中国最主流的高端读者、社会精英阅读需求而推出的一线品位读物。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (435, '个人电脑', '/defaultSite/image/icon.png', '《个人电脑》杂志是中国第一本专业IT评测媒体，首先将“产品评测”的概念带到中国，使“评测”的科学意识和体系在神州大陆上落地生根，并且凭借着国际化的实力成为中国IT评测市场的“教科书”。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (592, '好管家', '/defaultSite/image/icon.png', '《时尚》与在美国期刊市场享誉百年的著名品牌杂志Good Housekeeping强强联手，将精良的制作、独到的品位和对时尚家庭生活的敏锐理解完美地融合在一起。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (814, '花溪', '/defaultSite/image/icon.png', '《花溪》是国内首家以现代都市时尚情爱为主题的女性时尚杂志，是目前中国最具知名度的青春时尚类杂志。有生活的地方就有爱，有爱情的地方就有《花溪》。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (586, '华夏地理', '/defaultSite/image/icon.png', '《华夏地理》是国内第一本高品位的深度展现多元文化的杂志。以独家视点、深度人文、科学精神、唯美展现为办刊宗旨，倚靠雄厚的科技人文资源，深度发掘全球文化。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1378, '环球生活', '/defaultSite/image/icon.png', '《环球生活》杂志倡导“FROM LIFE,ABOUT LIFE,FOR LIFE”生活理念，突出环球的风尚视线，立足高端生活方式，以时尚生活作为出发点，阐述“发现生活之美。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1581, '进出口经理人', '/defaultSite/image/icon.png', '《进出口经理人》杂志创办于1988年，是国内外公开发行的外贸商务杂志，系机械工业出版社“机工传媒”家族成员。是外贸领域唯一的商务杂志和中国高端群体的主流商业读本之一。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (889, '橘子', '/defaultSite/image/icon.png', '《ORANGE》橘子杂志是一本购物达人专属的时尚杂志。每月通过FASHION NEWS，BEAUTY NEWS，服装编辑和美容编辑们给读者提供另一种周到贴心的购物指南。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1593, '看世界', '/defaultSite/image/icon.png', '《看世界》是一本充满活力的全方位反映当今世界政治、经济、文化、科技、军事的综合性的中国名刊，具有高度社会影响力的新锐媒体，当今有志青年必备的生活手册，思想读本。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1345, '篮球俱乐部', '/defaultSite/image/icon.png', '《篮球俱乐部》杂志将关注的目光聚焦在广大读者关注的篮球领域，重点关注世界尤其是美国等国家的高水平职业联赛，关注世界性篮球重大赛事，关心中国篮球事业的发展。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1566, '恋爱婚姻家庭', '/defaultSite/image/icon.png', '《恋爱婚姻家庭》的前身为《安徽婚姻家庭研究》。通过倡导健康和谐理念，传播时尚生活资讯，成为人们构建美好家庭、创造和谐生活的时尚风标。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (581, '男人装', '/defaultSite/image/icon.png', '《男人装》是中国第一本公开的纯男性向杂志，一本男性综合类时尚杂志，它不仅满足了男人功名，身体等方面的需要，而且也最大程度的体现了现今社会男人的真实欲求。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1387, '南都娱乐周刊', '/defaultSite/image/icon.png', '中国新型周末读本。娱乐不在别处，生活就是看法，南都周刊立志做一个看法提供商，提供娱乐的看法，生活的看法。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (863, '南风', '/defaultSite/image/icon.png', '《南风》是一本以城市心情、爱情故事为主体内容的青春女性杂志，独特的设计风格，精彩的时尚文字，精致的形象包装，张扬青春四季的激情，网罗城市里不同风景的爱情故事。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1486, '青年周末', '/defaultSite/image/icon.png', '《青年周末》，北京青年报全资子报，一份新潮和新锐的周报。读者定位于35岁以下的城市知识分子，那些追求观念不落伍、社交有谈资、生活有品位的年轻人。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (582, '时尚芭莎', '/defaultSite/image/icon.png', '《时尚芭莎》是《时尚》与拥有135年历史全球最著名的时装杂志《Harper s BAZAAR》版权合作的结晶，是一本全球性的真正引导潮流的高级时装杂志。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (578, '时尚旅游', '/defaultSite/image/icon.png', '《时尚旅游》是整合时尚杂志社与美国国家地理学会的优势资源和丰富经验，致力于成为积极主动，求知欲强的旅行者的信息来源。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (574, '时尚时间', '/defaultSite/image/icon.png', '一本以钟表、珠宝为主题元素的时尚生活杂志；一本最了解中国内地钟表珠宝消费市场的行业性杂志；一本精准定位于高端消费市场的、最具广告投资回报的时尚生活杂志。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (572, '时尚先生', '/defaultSite/image/icon.png', '《时尚先生》在生活消费方面给予男士们全方位的指导，是描述成熟男性理想、兴趣、好奇心以及热情生活的杂志。是中国面对成功男士的、最具影响力的生活消费类期刊。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1569, '市场瞭望', '/defaultSite/image/icon.png', '《市场瞭望》以“开门办刊”的思想，“走出去，请进来”，调动全社会力量办刊，以开阔的视野，集纳众多业界智慧，针对创业者、中小企业经营者、其他关注经济的人士。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1543, '首席财务官', '/defaultSite/image/icon.png', '《首席财务官》是由国内传媒巨头计算机世界传媒集团与美国传媒和资本巨头IDG联合投资的媒体机构，其定位为“国内第一本公开发行的面向CFO人群服务的专业资讯媒体”。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1467, '投资与理财', '/defaultSite/image/icon.png', '《投资与理财》是集引导投资为导向、传播大众理财为资讯的国内唯一的综合月刊。杂志以专业的水准为大众提供投资理财的咨询服务为办刊宗旨。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1493, '幽默天地', '/defaultSite/image/icon.png', '《幽默天地》是现代人的快乐维生素，它讲述男人、女人们之间的搞笑故事，浓缩百姓生活中的尴尬、荒诞，演绎office里的趣闻轶事，重现社会生活中的快乐密码！');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (14909, '中国经济和信息化', '/defaultSite/image/icon.png', '《中国经济和信息化》是中国首家专注于产业经济新闻报道的财经类杂志，致力于深度解读高层决策，推动产业经济转型升级，与产业共同发展进步。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1363, '中国经济周刊', '/defaultSite/image/icon.png', '《中国经济周刊》是人民日报社主管主办的杂志，是国内目前惟一一份以政经为主的综合经济类周刊杂志。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1598, '中国企业家', '/defaultSite/image/icon.png', '《中国企业家》杂志是中国主流商业财经杂志公认的领导者，与中国企业家阶层共同成长。创刊于1985年，经济日报社主办，中国主流商业财经杂志公认的领导者。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1451, '中国体育', '/defaultSite/image/icon.png', '《中国体育》，唯一以“中国”字头命名的体育杂志，我们走过的50余载峥嵘岁月，已可以俯瞰中国体育几代人铸就的光荣与梦想，在国内外拥有广泛的读者和影响力。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (924, '卓越理财', '/defaultSite/image/icon.png', '《卓越理财》杂志在理财领域内的专业性和权威性，决定其在该领域中的指导地位，极大地影响着当前和今后理财决策。');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1327, '足球俱乐部', '/defaultSite/image/icon.png', '《足球俱乐部》创刊于1993年5月，是一本以报道国际足球风云为主，兼顾国内足坛热点的文化综合类期刊，让我们一起享受足球带给我们的快乐。');

-----
Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)

                                                Values   ('14104148', 140, NULL, SYSDATE);

 Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME,RANDOMFACTOR)

                                        Values(140, '移动精品', 0, 0, 1,NULL,100);

Insert into T_CATERULE_COND (RULEID, CONDTYPE, WSQL)

                                                Values(140, 10, 'icpcode = ''100246''');



----添加v_service对zcom应用的过滤
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
    and c.thirdapptype in ('1','2');  --普通应用和widget应用;

create unique index index_v_service_pk on V_SERVICE (icpcode, icpservid, providertype);
create index index_v_service on v_service(contentid);



----ppms 内容同步优化，v_cm_content视图修改为表

RENAME  v_cm_content  TO ppms_v_cm_content;
create table v_cm_content as select * from ppms_v_cm_content;
----增加AP应用黑名单
create table T_CONTENT_BACKLIST
(
  CONTENTID VARCHAR2(30),
  INDATE    VARCHAR2(30)
);
comment on column T_CONTENT_BACKLIST.CONTENTID
  is '应用内容ID';
comment on column T_CONTENT_BACKLIST.INDATE
  is '黑名单有效期';
-- Create/Recreate indexes 
create index T_CONTENT_BLACKLIST_CID on T_CONTENT_BACKLIST (CONTENTID);
---全部 榜单商品排序规则按照精品评分降序排列
update t_caterule set RANDOMFACTOR='0' where ruleid='1';
update t_caterule_cond set osql='c.COMPECOUNT desc nulls last '  where ruleid='1';


--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.095_SSMS','MM1.0.0.096_SSMS');
commit;