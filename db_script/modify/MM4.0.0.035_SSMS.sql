
---------------------------------------

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'4.0.0.0','MM4.0.0.0.015_SSMS','MM4.0.0.0.035_SSMS');



-----���û��ܺ�ȫ��ͼ��������ͬ������ʼ-----
------�������������ʵ�������д-----
-----1����t_r_exportsql_ftp���в����������£�------
------ftpip��fip��ip��ַ��
------ftpport���˿ںţ�
------ftpname���û�����
------ftpkey�����룬
------ftppath��·��---
------ftpid���������ݿ����е����ݣ���дһ�������ڵ����֣������ظ�-----
insert into t_r_exportsql_ftp(ftpip,ftpport,ftpname,ftpkey,ftppath,ftpid) 
  values('10.1.3.203','22','mm_dcmq','mm_dcmq','mm_dcmq/max/report','5');

-----2����t_r_exportsql_group���в����������£�-----
------groupid����������id���������ݿ����е����ݣ���дһ�������ݿ��в����ڵ�id�������ظ���-------
------tomail����ǰ�����������ʼ���ַ��
------mailtitle����ǰ�����������ʼ����⣬
------starttime����ǰ��������ִ��ʱ�䣬
------timetype��ִ��ʱ������ 1:ÿ�� 2:ÿ�ܣ�
------timetypecon����ִ��ʱ������Ϊ2ʱ����ǰ�ֶζ�Ӧ�����壬Ϊ�ܼ�ִ��
------ftpid��t_r_exportsql_ftp���ftpidһ��---
------url�����������url֪ͨ��ַ
------ÿ��1�Σ�ʱ������ã�����Ϊ��02:00��ÿ��ȫ��ͬ��
insert into t_r_exportsql_group(groupid,tomail,mailtitle,starttime,timetype,timetypecon,ftpid,url)
    values (15,'dongke@aspirecn.com,baojun@aspirecn.com,zhanghuan@aspirecn.com','ȫ��ͼ��������ͬ��','0200','1','7','5',null);

------3����t_r_exportsql���в����������£�------
------id�����id���������ݿ����е����ݣ���дһ�������ݿ��в����ڵ�id�������ظ���-------
------exportsql��������sql��䣬
------exportname��������������
------exporttype�������ļ����� 1:csv 2:txt 3:excel��
------exportpagenum���������ݷ�ҳ������ 0:Ϊ���޶� ���999999��
------exporttypeother�����EXPORTType����Ϊ1,2���ֶ�Ϊ���ݷָ��������EXPORTTypeΪ3���ֶ�Ϊѡ����1��97-03�棬2��07�棬
------exportline�������ֶ���---
------filename�����������ļ���Ĭ�ϡ����ʱ�䣬
------filepath�����������ļ�����·��---
------encoder���ļ�����9��
------exportbyauto���ļ�������ʽ1:ֻ���ֶ� 2:�ֶ��Զ�������---
------groupid��t_r_exportsql_group���groupidһ��---

------������T_DISSERTATION����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(63,'select t.dissid,t.logourl,t.descr,t.keywords,t.startdate,t.enddate,t.categoryid,t.categoryname,t.status,t.dissname,t.disstype,t.relation,t.dissurl from T_DISSERTATION t','ȫ��ͼ����T_DISSERTATION������ͬ��','2',50000,'0x1f',13,'611_T_DISSERTATION_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_GAME_SERVICE����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(64,'select s.icpcode,s.servname,s.icpservid from T_GAME_SERVICE s','ȫ��ͼ����T_GAME_SERVICE������ͬ��','2',50000,'0x1f',3,'611_T_GAME_SERVICE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_MB_MUSIC����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(65,'select m.musicid,m.songname,m.singer,m.validity,m.updatetime,m.createtime,m.delflag,m.tags,m.music_pic from T_MB_MUSIC m','ȫ��ͼ����T_MB_MUSIC������ͬ��','2',80000,'0x1f',9,'611_T_MB_MUSIC_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_MB_MUSIC_NEW����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(66,'select m.musicid,m.songname,m.singer,m.validity,m.updatetime,m.createtime,m.delflag,m.tags,m.music_pic,m.productmask,m.singersid,m.pubtime,m.onlinetype,m.colortype,m.ringtype,m.songtype,m.dolbytype,m.losslessmusic,m.format320kbps from T_MB_MUSIC_NEW m','ȫ��ͼ����T_MB_MUSIC_NEW������ͬ��','2',80000,'0x1f',19,'611_T_MB_MUSIC_NEW_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_R_EMPHA_CATEGORY����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(67,'select c.categoryid,c.categoryname,c.time,c.flag from T_R_EMPHA_CATEGORY c','ȫ��ͼ����T_R_EMPHA_CATEGORY������ͬ��','2',50000,'0x1f',4,'611_T_R_EMPHA_CATEGORY_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_R_GCONTENT����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(68,'select tg.id,tg.name,tg.catename,tg.appcatename,tg.appcateid,tg.spname,tg.icpcode,tg.icpservid,tg.price,tg.expire,tg.contentid,tg.companyid,tg.productid,tg.onlinetype,tg.chargetime,tg.pvcid,tg.cityid,tg.subtype,tg.provider from T_R_GCONTENT tg','ȫ��ͼ����T_R_GCONTENT������ͬ��','2',50000,'0x1f',19,'611_T_R_GCONTENT_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_R_REFERENCE����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(69,'select r.refnodeid from T_R_REFERENCE r','ȫ��ͼ����T_R_REFERENCE������ͬ��','2',80000,'0x1f',1,'611_T_R_REFERENCE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_RB_BOOK����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(70,'select b.bookid,b.bookname from T_RB_BOOK b','ȫ��ͼ����T_RB_BOOK������ͬ��','2',80000,'0x1f',2,'611_T_RB_BOOK_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_RB_MONTHLY����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(71,'select m.categoryid,m.categoryname,m.decrisption,m.fee,m.url,m.lupdate from T_RB_MONTHLY m','ȫ��ͼ����T_RB_MONTHLY������ͬ��','2',50000,'0x1f',6,'611_T_RB_MONTHLY_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_VB_VIDEO����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(72,'select v.pkgid,v.pkgname,v.fee,v.createdate,v.lupdate,v.type,v.videourl from T_VB_VIDEO v','ȫ��ͼ����T_VB_VIDEO������ͬ��','2',50000,'0x1f',7,'611_T_VB_VIDEO_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������V_CLMS_CONTENT����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(73,'select vc.icpcode,vc.contentid,vc.name,vc.introduction,vc.catename,vc.appcatename,vc.mobileprice,vc.marketdate,vc.lupddate,vc.chargetime,vc.subtype,vc.logo4 from V_CLMS_CONTENT vc','ȫ��ͼ����V_CLMS_CONTENT������ͬ��','2',50000,'0x1f',12,'611_V_CLMS_CONTENT_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������V_CLMS_CONTENTTAG����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(74,'select cc.tagid,cc.contentid,cc.opdate,cc.createdate from V_CLMS_CONTENTTAG cc','ȫ��ͼ����V_CLMS_CONTENTTAG������ͬ��','2',50000,'0x1f',4,'611_V_CLMS_CONTENTTAG_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������V_CLMS_TAG����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(75,'select ct.tagid,ct.tagowner,ct.taglevel,ct.parenttagid,ct.tagname,ct.opdate,ct.createdate from V_CLMS_TAG ct','ȫ��ͼ����V_CLMS_TAG������ͬ��','2',50000,'0x1f',7,'611_V_CLMS_TAG_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������VR_CATEGORY����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(76,'select c.categoryid,c.categoryname,c.parentcategoryid,c.state,c.changedate,c.relation,c.id,c.sortid from VR_CATEGORY c','ȫ��ͼ����VR_CATEGORY������ͬ��','2',50000,'0x1f',8,'611_VR_CATEGORY_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������VR_GOODS����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(77,'select g.goodsid,g.spcode,g.servicecode,g.spid,g.serviceid,g.contentid,g.categoryid,g.goodsname,g.umflag from VR_GOODS g','ȫ��ͼ����VR_GOODS������ͬ��','2',50000,'0x1f',9,'611_VR_GOODS_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������VR_GOODS_HIS����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(78,'select h.id,h.goodsid,h.goodsname,h.contentid,h.categoryid,h.spid,h.spcode,h.serviceid,h.servicecode,h.state,h.laststate,h.changedate,h.actiontype from VR_GOODS_HIS h','ȫ��ͼ����VR_GOODS_HIS������ͬ��','2',100000,'0x1f',13,'611_VR_GOODS_HIS_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������T_GAME_BASE����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(79,'select b.pkgid,b.pkgname,b.pkgdesc,b.cpname,b.servicecode,b.fee,b.pkgurl,b.picurl1,b.picurl2,b.picurl3,b.picurl4,b.updatetime,b.createtime,b.state,b.provincectrol from T_GAME_BASE b','ȫ��ͼ����T_GAME_BASE������ͬ��','2',50000,'0x1f',15,'611_T_GAME_BASE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������VR_A8_GOODS����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(80,'select a.goodsid,a.contentid,a.categoryid,a.goodsname,a.singer,a.singerzone from VR_A8_GOODS a','ȫ��ͼ����VR_A8_GOODS������ͬ��','2',50000,'0x1f',6,'611_VR_A8_GOODS_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');

------������V_WWW_TOP60REFERENCE����ͬ��-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(81,'select t.day,t.goodsid,t.contentid,t.sortid,t.subname,t.name from V_WWW_TOP60REFERENCE t','ȫ��ͼ����V_WWW_TOP60REFERENCE������ͬ��','2',50000,'0x1f',6,'611_V_WWW_TOP60REFERENCE_%YYYYMMDD%_%NNNNNN%','/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report','GBK','2','15');


-----���û���ļ������������-----


commit;


------

