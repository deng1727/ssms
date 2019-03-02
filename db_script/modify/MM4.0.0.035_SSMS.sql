
---------------------------------------

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'4.0.0.0','MM4.0.0.0.015_SSMS','MM4.0.0.0.035_SSMS');



-----配置货架和全视图报表数据同步任务开始-----
------以下数据请根据实际情况填写-----
-----1、向t_r_exportsql_ftp表中插入数据如下：------
------ftpip：fip的ip地址，
------ftpport：端口号，
------ftpname：用户名，
------ftpkey：密码，
------ftppath：路径---
------ftpid：根据数据库已有的数据，填写一个不存在的数字，不能重复-----
insert into t_r_exportsql_ftp(ftpip,ftpport,ftpname,ftpkey,ftppath,ftpid) 
  values('10.1.3.203','22','mm_dcmq','mm_dcmq','mm_dcmq/max/report','5');

-----2、向t_r_exportsql_group表中插入数据如下：-----
------groupid：分组任务id，根据数据库已有的数据，填写一个在数据库中不存在的id，不能重复，-------
------tomail：当前分组任务发送邮件地址，
------mailtitle：当前分组任务发送邮件标题，
------starttime：当前分组任务执行时间，
------timetype：执行时间类型 1:每天 2:每周，
------timetypecon：当执行时间类型为2时，当前字段对应有意义，为周几执行
------ftpid：t_r_exportsql_ftp表的ftpid一致---
------url：生成完后发起url通知地址
------每天1次（时间可配置，初步为：02:00）每次全量同步
insert into t_r_exportsql_group(groupid,tomail,mailtitle,starttime,timetype,timetypecon,ftpid,url)
    values (15,'dongke@aspirecn.com,baojun@aspirecn.com,zhanghuan@aspirecn.com','全视图报表数据同步','0200','1','7','5',null);

------3、向t_r_exportsql表中插入数据如下：------
------id：编号id，根据数据库已有的数据，填写一个在数据库中不存在的id，不能重复，-------
------exportsql：导出用sql语句，
------exportname：导出任务名，
------exporttype：导出文件类型 1:csv 2:txt 3:excel，
------exportpagenum：导出内容分页缓存量 0:为不限定 最多999999，
------exporttypeother：如果EXPORTType类型为1,2此字段为数据分隔符。如果EXPORTType为3此字段为选择项1：97-03版，2：07版，
------exportline：导出字段数---
------filename：导出生成文件名默认。后加时间，
------filepath：导出生成文件所在路径---
------encoder：文件编码9，
------exportbyauto：文件导出方式1:只能手动 2:手动自动都可以---
------groupid：t_r_exportsql_group表的groupid一致---

------创建表T_DISSERTATION数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(63,'select t.dissid,t.logourl,t.descr,t.keywords,t.startdate,t.enddate,t.categoryid,t.categoryname,t.status,t.dissname,t.disstype,t.relation,t.dissurl from T_DISSERTATION t','全视图报表T_DISSERTATION表数据同步','2',50000,'0x1f',13,'611_T_DISSERTATION_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_GAME_SERVICE数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(64,'select s.icpcode,s.servname,s.icpservid from T_GAME_SERVICE s','全视图报表T_GAME_SERVICE表数据同步','2',50000,'0x1f',3,'611_T_GAME_SERVICE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_MB_MUSIC数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(65,'select m.musicid,m.songname,m.singer,m.validity,m.updatetime,m.createtime,m.delflag,m.tags,m.music_pic from T_MB_MUSIC m','全视图报表T_MB_MUSIC表数据同步','2',80000,'0x1f',9,'611_T_MB_MUSIC_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_MB_MUSIC_NEW数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(66,'select m.musicid,m.songname,m.singer,m.validity,m.updatetime,m.createtime,m.delflag,m.tags,m.music_pic,m.productmask,m.singersid,m.pubtime,m.onlinetype,m.colortype,m.ringtype,m.songtype,m.dolbytype,m.losslessmusic,m.format320kbps from T_MB_MUSIC_NEW m','全视图报表T_MB_MUSIC_NEW表数据同步','2',80000,'0x1f',19,'611_T_MB_MUSIC_NEW_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_R_EMPHA_CATEGORY数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(67,'select c.categoryid,c.categoryname,c.time,c.flag from T_R_EMPHA_CATEGORY c','全视图报表T_R_EMPHA_CATEGORY表数据同步','2',50000,'0x1f',4,'611_T_R_EMPHA_CATEGORY_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_R_GCONTENT数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(68,'select tg.id,tg.name,tg.catename,tg.appcatename,tg.appcateid,tg.spname,tg.icpcode,tg.icpservid,tg.price,tg.expire,tg.contentid,tg.companyid,tg.productid,tg.onlinetype,tg.chargetime,tg.pvcid,tg.cityid,tg.subtype,tg.provider from T_R_GCONTENT tg','全视图报表T_R_GCONTENT表数据同步','2',50000,'0x1f',19,'611_T_R_GCONTENT_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_R_REFERENCE数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(69,'select r.refnodeid from T_R_REFERENCE r','全视图报表T_R_REFERENCE表数据同步','2',80000,'0x1f',1,'611_T_R_REFERENCE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_RB_BOOK数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(70,'select b.bookid,b.bookname from T_RB_BOOK b','全视图报表T_RB_BOOK表数据同步','2',80000,'0x1f',2,'611_T_RB_BOOK_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_RB_MONTHLY数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(71,'select m.categoryid,m.categoryname,m.decrisption,m.fee,m.url,m.lupdate from T_RB_MONTHLY m','全视图报表T_RB_MONTHLY表数据同步','2',50000,'0x1f',6,'611_T_RB_MONTHLY_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_VB_VIDEO数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(72,'select v.pkgid,v.pkgname,v.fee,v.createdate,v.lupdate,v.type,v.videourl from T_VB_VIDEO v','全视图报表T_VB_VIDEO表数据同步','2',50000,'0x1f',7,'611_T_VB_VIDEO_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表V_CLMS_CONTENT数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(73,'select vc.icpcode,vc.contentid,vc.name,vc.introduction,vc.catename,vc.appcatename,vc.mobileprice,vc.marketdate,vc.lupddate,vc.chargetime,vc.subtype,vc.logo4 from V_CLMS_CONTENT vc','全视图报表V_CLMS_CONTENT表数据同步','2',50000,'0x1f',12,'611_V_CLMS_CONTENT_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表V_CLMS_CONTENTTAG数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(74,'select cc.tagid,cc.contentid,cc.opdate,cc.createdate from V_CLMS_CONTENTTAG cc','全视图报表V_CLMS_CONTENTTAG表数据同步','2',50000,'0x1f',4,'611_V_CLMS_CONTENTTAG_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表V_CLMS_TAG数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(75,'select ct.tagid,ct.tagowner,ct.taglevel,ct.parenttagid,ct.tagname,ct.opdate,ct.createdate from V_CLMS_TAG ct','全视图报表V_CLMS_TAG表数据同步','2',50000,'0x1f',7,'611_V_CLMS_TAG_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表VR_CATEGORY数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(76,'select c.categoryid,c.categoryname,c.parentcategoryid,c.state,c.changedate,c.relation,c.id,c.sortid from VR_CATEGORY c','全视图报表VR_CATEGORY表数据同步','2',50000,'0x1f',8,'611_VR_CATEGORY_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表VR_GOODS数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(77,'select g.goodsid,g.spcode,g.servicecode,g.spid,g.serviceid,g.contentid,g.categoryid,g.goodsname,g.umflag from VR_GOODS g','全视图报表VR_GOODS表数据同步','2',50000,'0x1f',9,'611_VR_GOODS_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表VR_GOODS_HIS数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(78,'select h.id,h.goodsid,h.goodsname,h.contentid,h.categoryid,h.spid,h.spcode,h.serviceid,h.servicecode,h.state,h.laststate,h.changedate,h.actiontype from VR_GOODS_HIS h','全视图报表VR_GOODS_HIS表数据同步','2',100000,'0x1f',13,'611_VR_GOODS_HIS_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表T_GAME_BASE数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(79,'select b.pkgid,b.pkgname,b.pkgdesc,b.cpname,b.servicecode,b.fee,b.pkgurl,b.picurl1,b.picurl2,b.picurl3,b.picurl4,b.updatetime,b.createtime,b.state,b.provincectrol from T_GAME_BASE b','全视图报表T_GAME_BASE表数据同步','2',50000,'0x1f',15,'611_T_GAME_BASE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表VR_A8_GOODS数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(80,'select a.goodsid,a.contentid,a.categoryid,a.goodsname,a.singer,a.singerzone from VR_A8_GOODS a','全视图报表VR_A8_GOODS表数据同步','2',50000,'0x1f',6,'611_VR_A8_GOODS_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------创建表V_WWW_TOP60REFERENCE数据同步-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(81,'select t.day,t.goodsid,t.contentid,t.sortid,t.subname,t.name from V_WWW_TOP60REFERENCE t','全视图报表V_WWW_TOP60REFERENCE表数据同步','2',50000,'0x1f',6,'611_V_WWW_TOP60REFERENCE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');


-----配置汇聚文件导出任务结束-----


commit;


------

