-- add/modify columns ��t_r_gcontent����logo6�ֶ�
alter table t_r_gcontent add logo6 varchar2(256);
-- add comments to the columns 
comment on column t_r_gcontent.logo6
  is 'logo6��ַ,���120*120Ӧ�ô�ͼ��';
  
-- add/modify columns ��cm_ct_appgame����logo6�ֶ�
alter table cm_ct_appgame add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appgame.logo6
  is 'logo6��ַ,���120*120Ӧ�ô�ͼ��';

-- add/modify columns ��cm_ct_appsoftware����logo6�ֶ�
alter table cm_ct_appsoftware add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appsoftware.logo6
  is 'logo6��ַ,���120*120Ӧ�ô�ͼ��';

-- add/modify columns ��cm_ct_apptheme����logo6�ֶ�
alter table cm_ct_apptheme add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_apptheme.logo6
  is 'logo6��ַ,���120*120Ӧ�ô�ͼ��';

-- add/modify columns ��cm_ct_appgame_tra����logo6�ֶ�
alter table cm_ct_appgame_tra add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appgame_tra.logo6
  is 'logo6��ַ,���120*120Ӧ�ô�ͼ��';
  
-- add/modify columns ��cm_ct_appsoftware_tra����logo6�ֶ�
alter table cm_ct_appsoftware_tra add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_appsoftware_tra.logo6
  is 'logo6��ַ,���120*120Ӧ�ô�ͼ��';
  
-- add/modify columns ��cm_ct_apptheme_tra����logo6�ֶ�
alter table cm_ct_apptheme_tra add logo6 varchar2(256);
-- add comments to the columns 
comment on column cm_ct_apptheme_tra.logo6
  is 'logo6��ַ,���120*120Ӧ�ô�ͼ��';

  -- add/modify columns ��cm_ct_appgame����PKGEXTRACTICON�ֶ�
alter table cm_ct_appgame add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appgame.PKGEXTRACTICON
  is 'PKGEXTRACTICON��ַ,��ų������ȡ��logoԭͼ';

-- add/modify columns ��cm_ct_appsoftware����PKGEXTRACTICON�ֶ�
alter table cm_ct_appsoftware add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appsoftware.PKGEXTRACTICON
  is 'PKGEXTRACTICON��ַ,��ų������ȡ��logoԭͼ';

-- add/modify columns ��cm_ct_apptheme����PKGEXTRACTICON�ֶ�
alter table cm_ct_apptheme add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_apptheme.PKGEXTRACTICON
  is 'PKGEXTRACTICON��ַ,��ų������ȡ��logoԭͼ';

-- add/modify columns ��cm_ct_appgame_tra����PKGEXTRACTICON�ֶ�
alter table cm_ct_appgame_tra add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appgame_tra.PKGEXTRACTICON
  is 'PKGEXTRACTICON��ַ,��ų������ȡ��logoԭͼ';
  
-- add/modify columns ��cm_ct_appsoftware_tra����PKGEXTRACTICON�ֶ�
alter table cm_ct_appsoftware_tra add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_appsoftware_tra.PKGEXTRACTICON
  is 'PKGEXTRACTICON��ַ,��ų������ȡ��logoԭͼ';
  
-- add/modify columns ��cm_ct_apptheme_tra����PKGEXTRACTICON�ֶ�
alter table cm_ct_apptheme_tra add PKGEXTRACTICON VARCHAR2(10);
-- add comments to the columns 
comment on column cm_ct_apptheme_tra.PKGEXTRACTICON
  is 'PKGEXTRACTICON��ַ,��ų������ȡ��logoԭͼ';
  
-- �������ݱ�
CREATE TABLE V_ARTICLE
(
  CONTENTID  NUMBER(10),
  TITLE      VARCHAR2(255 BYTE),
  SUBTITLE   VARCHAR2(255 BYTE),
  AUTHOR     VARCHAR2(25 BYTE),
  SOURCE     NUMBER(1),
  LABEL      VARCHAR2(255 BYTE),
  COVER      VARCHAR2(200),
  BRIEF      VARCHAR2(200 BYTE),
  STAR       NUMBER(2),
  PUBTIME    DATE,
  EDITTIME   DATE,
  TYPE       NUMBER(2),
  APPID      VARCHAR2(255 BYTE),
  CONTENT    CLOB,
  STATUS     NUMBER(1)
);
-- Add comments to the table 
comment on table V_ARTICLE
  is '�������ݱ�';
-- Add comments to the columns 
comment on column V_ARTICLE.CONTENTID
  is '����ID';
comment on column V_ARTICLE.TITLE
  is '����';
comment on column V_ARTICLE.SUBTITLE
  is '�̱���';
comment on column V_ARTICLE.AUTHOR
  is '������';
comment on column V_ARTICLE.SOURCE
  is '1��ԭ����2��ת�أ�3�����룻4���ٷ�';
comment on column V_ARTICLE.LABEL
  is '��ǩ�������ǩʱʹ�÷ֺš�;�������';
comment on column V_ARTICLE.COVER
  is 'ͼƬ��Ե�ַ';
comment on column V_ARTICLE.BRIEF
  is '���¼��';
comment on column V_ARTICLE.STAR
  is '��Ʒ���֣�1~5';
comment on column V_ARTICLE.PUBTIME
  is '����ʱ��';
comment on column V_ARTICLE.EDITTIME
  is '�޸�ʱ��';
comment on column V_ARTICLE.TYPE
  is '�������ͣ�1����Ϸ����2��С�����3����ѡר��4�����ڻ���';
comment on column V_ARTICLE.APPID
  is '���¶�ӦӦ��id�����ʱʹ�÷ֺš�;�������';
comment on column V_ARTICLE.CONTENT
  is '��������';
comment on column V_ARTICLE.STATUS
  is '0��	�༭��1��	����2��	���أ�3��	����';

CREATE TABLE V_ARTICLE_REFERENCE
(
  APPID      VARCHAR2(30 BYTE),
  CONTENTID  NUMBER(10),
  STATUS     NUMBER(1)
);
-- Add comments to the table 
comment on table V_ARTICLE_REFERENCE
  is '���º�Ӧ�ù�����';
COMMENT ON COLUMN V_ARTICLE_REFERENCE.APPID IS 'Ӧ��ID';
COMMENT ON COLUMN V_ARTICLE_REFERENCE.CONTENTID IS '����ID';
COMMENT ON COLUMN V_ARTICLE_REFERENCE.STATUS IS '״̬0���༭��1������2�����أ�3������4�����ߣ�5�����ߣ�';

--��������������µ�����¼
insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (29, 'select CONTENTID,TITLE,LABEL,PUBTIME from V_ARTICLE', '����ϵͳ�ļ�-��������', '2', 50000, '0x01', to_date('23-10-2013 17:04:00', 'dd-mm-yyyy hh24:mi:ss'), 4, 'zhuanti', '/opt/aspire/product/chroot_panguso/panguso/mo', 'GB18030', '2', '1', '69', 3);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.105_SSMS','MM2.0.0.0.109_SSMS');

commit;








---------- 
create table T_VO_RECOMMEND
(
  RECOMMENDID   VARCHAR2(60) not null primary key,
  RECOMMENDNAME VARCHAR2(512) not null,
  PROGRAMID     VARCHAR2(60) not null,
  SORTID        NUMBER(10),
  UPDATETIME    DATE default sysdate
);
COMMENT ON COLUMN T_VO_RECOMMEND.RECOMMENDID IS '�Ƽ���ʶ';
COMMENT ON COLUMN T_VO_RECOMMEND.RECOMMENDNAME IS '�Ƽ�����';
COMMENT ON COLUMN T_VO_RECOMMEND.PROGRAMID IS '��Ŀ��ʶ';
COMMENT ON COLUMN T_VO_RECOMMEND.SORTID IS '�������';
COMMENT ON COLUMN T_VO_RECOMMEND.UPDATETIME IS '������ʱ��';

alter table t_vo_program add SORTID  NUMBER(10);
alter table t_vo_program add isLink VARCHAR2(10);
COMMENT ON COLUMN t_vo_program.SORTID IS '������š���С��������';
COMMENT ON COLUMN t_vo_program.isLink IS '1��������,2����������';

commit;