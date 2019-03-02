--1��MMӦ����ͼ��v_mm_content
--2��Ӧ�������ϵ��ͼ�� v_mm_content_device
--3��Ӧ��ͼƬ��ͼ��v_mm_content_pic

--��ʱ������
/**
create or replace synonym V_DATACENTER_CM_CONTENT
  for GCENTER.V_MM_CONTENT@DL_MMUC_SPZX;
  
create or replace synonym V_DC_CM_DEVICE_RESOURCE
	for GCENTER.V_MM_CONTENT_DEVICE@DL_MMUC_SPZX;
  
create or replace synonym V_R_CONTENT_PIC
  for GCENTER.V_MM_CONTENT_PIC@DL_MMUC_SPZX;

commit;
*/ 

alter table t_content_push_adv add URI varchar2(1000)
alter table t_content_push_adv add PUSH_PIC varchar2(800)
alter table t_content_push_adv add TYPE varchar2(1)
alter table t_content_push_adv add VERSIONNAME varchar2(500)
comment on column SSMS.T_CONTENT_PUSH_ADV.URI
  is 'Ӧ��������';
comment on column SSMS.T_CONTENT_PUSH_ADV.TYPE
  is '0��Ӧ�����ͣ�1��ý�廯�������ͣ�2��Deeplinkҳ������';
comment on column SSMS.T_CONTENT_PUSH_ADV.PUSH_PIC
  is '����ͼƬ';
comment on column SSMS.T_CONTENT_PUSH_ADV.VERSIONNAME
  is '�汾��';
commit;




insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
values (16, 'zhanghuan@aspirecn.com,ouyangguangming@aspirecn.com', '��׼�Ƽ�ƽ̨-��Ƶ�Ķ�����ͬ��', '1910', '1', '7', '0', '');



insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (93, 'select typeid,typename from T_RB_TYPE_NEW', '��׼�Ƽ�ƽ̨-�Ķ������ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:04:00', 'dd-mm-yyyy hh24:mi:ss'), 2, 'i_rb_type_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '90', 16);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (94, 'select BOOKID, BOOKNAME, KEYWORD, LONGRECOMMEND, SHORTRECOMMEND, DESCRIPTION, AUTHORID, TYPEID, INTIME, CHARGETYPE, FEE, ISFINISH, WORDCOUNT, CHAPTERCOUNT, FREECHAPTERCOUNT from t_rb_book_new where delflag=0', '��׼�Ƽ�ƽ̨-�Ķ���ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:04:00', 'dd-mm-yyyy hh24:mi:ss'), 15, 'i_rb_book_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '36437', 16);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (95, 'select AUTHORID, AUTHORNAME, AUTHORDESC, ISORIGINAL, ISPUBLISH  from T_RB_AUTHOR_NEW', '��׼�Ƽ�ƽ̨-���߱�ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:05:00', 'dd-mm-yyyy hh24:mi:ss'), 5, 'i_rb_author_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '7348', 16);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (96, 'select c.categoryid,c.categoryname,r.bookid,r.sortnumber from T_RB_REFERENCE_NEW r,T_RB_CATEGORY_NEW c where c.parentid=''202'' and c.categoryid=r.categoryid order by c.categoryid,r.sortnumber desc ', '��׼�Ƽ�ƽ̨-���а����ݱ�ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:05:00', 'dd-mm-yyyy hh24:mi:ss'), 4, 'i_rb_reference_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '21349',16);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (97, 'select ID,PROGRAMID,CMSID,NAME,NAME1,NAME2,CREATETIMEV,UPDATETIMEV,PUBLISHTIMEV,PRDPACK_ID,PRODUCT_ID,CATEGORY,TYPE,SERIALCONTENTID,SERIALSEQUENCE,SERIALCOUNT,FORMTYPE,COPYRIGHTTYPE,VIDEONAME,VSHORTNAME,VAUTHOR,DIRECTRECFLAG,AREA,TERMINAL,WAY,PUBLISH,KEYWORD,CDURATION,DISPLAYTYPE,DISPLAYNAME,ASSIST,LIVESTATUS,LUPDATE,EXETIME,AUTHORIZATIONWAY,MIGUPUBLISH,BCLICENSE,INFLUENCE,ORIPUBLISH,SUBSERIAL_IDS,FEETYPE,DETAIL from t_v_dprogram', '��׼�Ƽ�ƽ̨-��Ƶ��ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:05:00', 'dd-mm-yyyy hh24:mi:ss'), 42, 'i_v_dprogram_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '4765', 16);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (98, 'select ID, PROGRAMID, CMS_ID, PROPERTYKEY, PROPERTYVALUE, LUPDATE, EXETIME from t_v_videosPropertys', '��׼�Ƽ�ƽ̨-��Ƶ���������ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:06:00', 'dd-mm-yyyy hh24:mi:ss'), 7, 'i_v_dprogram_p_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '9890', 16);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (99, 'select ID, NAME, CATENAME, APPCATENAME, APPCATEID, SPNAME, ICPCODE, ICPSERVID, CONTENTTAG, SINGER, PRICE, EXPIRE, AUDITIONURL, INTRODUCTION, NAMELETTER, SINGERLETTER, DOWNLOADTIMES, SETTIMES, BIGCATENAME, CONTENTID, COMPANYID, PRODUCTID, KEYWORDS, CREATEDATE, MARKETDATE, LUPDDATE, LANGUAGE, WWWPROPAPICTURE1, WWWPROPAPICTURE2, WWWPROPAPICTURE3, CLIENTPREVIEWPICTURE1, CLIENTPREVIEWPICTURE2, CLIENTPREVIEWPICTURE3, CLIENTPREVIEWPICTURE4, PROVIDER, HANDBOOK, MANUAL, HANDBOOKPICTURE, USERGUIDE, USERGUIDEPICTURE, GAMEVIDEO, LOGO1, LOGO2, LOGO3, CARTOONPICTURE, DEVICENAME, DEVICENAME02, DEVICENAME03, DEVICENAME04, DEVICENAME05, DEVICENAME06, DEVICENAME07, DEVICENAME08, DEVICENAME09, DEVICENAME10, DEVICENAME11, DEVICENAME12, DEVICENAME13, DEVICENAME14, DEVICENAME15, DEVICENAME16, DEVICENAME17, DEVICENAME18, DEVICENAME19, DEVICENAME20, DAYSEARCHTIMES, WEEKSEARCHTIMES, MONTHSEARCHTIMES, SEARCHTIMES, DAYSCANTIMES, WEEKSCANTIMES, MONTHSCANTIMES, SCANTIMES, DAYORDERTIMES, WEEKORDERTIMES, MONTHORDERTIMES, ORDERTIMES, DAYCOMMENTTIMES, WEEKCOMMENTTIMES, MONTHCOMMENTTIMES, COMMENTTIMES, DAYMARKTIMES, WEEKMARKTIMES, MONTHMARKTIMES, MARKTIMES, DAYCOMMENDTIMES, WEEKCOMMENDTIMES, MONTHCOMMENDTIMES, COMMENDTIMES, DAYCOLLECTTIMES, WEEKCOLLECTTIMES, MONTHCOLLECTTIMES, COLLECTTIMES, AVERAGEMARK, ISSUPPORTDOTCARD, PROGRAMSIZE, PROGRAMID, ONLINETYPE, VERSION, PICTURE1, PICTURE2, PICTURE3, PICTURE4, PICTURE5, PICTURE6, PICTURE7, PICTURE8, PLATFORM, CHARGETIME, LOGO4, BRAND, SERVATTR, SUBTYPE, PVCID, CITYID, FULLDEVICENAME, MATCH_DEVICEID, EXPPRICE, FULLDEVICEID, MODAYORDERTIMES, PLUPDDATE, OTHERNET, RICHAPPDESC, ADVERTPIC, PRICETYPE, COMPAREDNUMBER, FUNCDESC, MSTATUS, LOGO5, LOGO6, ISMMTOEVENT, COPYRIGHTFLAG, MAPNAME, CHANNELDISPTYPE from t_r_gcontent', '��׼�Ƽ�ƽ̨-Ӧ�����ݱ�ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:06:00', 'dd-mm-yyyy hh24:mi:ss'), 134, 'i_r_gcontent_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '9890', 16);

insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (100, 'PID, DEVICE_ID, DEVICE_NAME, CONTENTID, CONTENTNAME, RESOURCEID, ID, ABSOLUTEPATH, URL, PROGRAMSIZE, CREATEDATE, PROSUBMITDATE, MATCH, VERSION, PERMISSION, ISCDN, VERSIONNAME, PICTURE1, PICTURE2, PICTURE3, PICTURE4, CLIENTID, PKGNAME, VERSIONDESC, CDNURL, ISWHITELIST, CAFLAG, CADEV, CAORG, CAVALIDATEDATE, WAPURL, MOURL, CERMD5, MD5CODE, PCURL, WWWURL, THIRDCOLLECT', '��׼�Ƽ�ƽ̨-���������ͬ��', '2', 50000, '0x09', to_date('03-07-2017 17:06:00', 'dd-mm-yyyy hh24:mi:ss'), 37, 'i_device_resource_%YYYYMMDD%_%NNNNNN%', '/opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/sftp_ssms_report', 'UTF-8', '2', '1', '9890', 16);

commit;



create or replace view v_clms_content as
 select t.icpcode, t.contentid,t.name,t.introduction,t.catename,t.appcatename,v.mobileprice,t.marketdate,t.lupddate, t.chargetime,t.subtype,t.logo4 
 from t_r_gcontent t,v_service v 
 where t.icpservid=v.icpservid and t.contentid=v.contentid and t.provider != 'B';

commit;