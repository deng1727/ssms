-- 201412������Ƶ�İ�

----------������ ��ʼ---------
-- Create table��Ŀ������
create table t_v_sprogram
(
  id         varchar2(32),
  programid  varchar2(21),
  cmsid      varchar2(21),
  channelid  varchar2(100),
  pubtime    varchar2(20),
  status     varchar2(2),
  exestatus  varchar2(2)  default '0',
  updatetime date default sysdate
)
;
-- Add comments to the columns 
comment on column t_v_sprogram.id
  is '����ID';
comment on column t_v_sprogram.programid
  is 'OMS����ID����ĿID';
comment on column t_v_sprogram.cmsid
  is 'CMS����ID';
comment on column t_v_sprogram.channelid
  is '����ID';
  comment on column t_v_sprogram.pubtime
  is '����ʱ��';
  comment on column t_v_sprogram.status
  is '12�ѷ��� 22������';
    comment on column t_v_sprogram.exestatus
  is '��������״̬��1�Ѵ��� 0δ����2����ʧ��';
comment on column t_v_sprogram.updatetime
  is '������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_sprogram
  add constraint pk_tvsprogram_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvprogram_pid on t_v_sprogram (programid);
create index idx_tvprogram_cid on t_v_sprogram (cmsid);


-- Create table��Ŀ�����
create table t_v_dprogram
(
  id               varchar2(32),
  programid        varchar2(21),
  cmsid            varchar2(21),
  name             varchar2(128),
  name1            varchar2(128),
  name2            varchar2(128),
  createtimev      VARCHAR2(20),
  updatetimev      VARCHAR2(20),
  publishtimev     VARCHAR2(20),
  prdpack_id       VARCHAR2(20),
  product_id       VARCHAR2(20),
  category         NUMBER(2),
  type             NUMBER(2),
  serialcontentid  VARCHAR2(30),
  serialsequence   NUMBER(4),
  serialcount      NUMBER(4),
  subserial_ids    VARCHAR2(1000),
  formtype         NUMBER(2),
  copyRightType    VARCHAR2(1),
  videoname        VARCHAR2(128),
  vshortname       VARCHAR2(100),
  vauthor          VARCHAR2(200),
  directrecflag    NUMBER(2),
  area             VARCHAR2(50),
  terminal         VARCHAR2(50),
  way              NUMBER(2),
  publish          NUMBER(2),
  keyword          VARCHAR2(4000),
  cduration        Number(5),
  displaytype      Number(10),
  displayname      VARCHAR2(100),
  assist           VARCHAR2(4000),
  livestatus       VARCHAR2(2)  default '0',
  lupdate          date default sysdate,
  exetime          date
)
;
-- Add comments to the columns 
comment on column t_v_dprogram.id
  is '����ID';
comment on column t_v_dprogram.programid
  is 'OMS����ID����ĿID';
comment on column t_v_dprogram.cmsid
  is 'CMS����ID,��ƵID';
comment on column t_v_dprogram.name
  is '��Ŀ����';
comment on column t_v_dprogram.name1
  is '��Ŀ����1��Ԥ��';
comment on column t_v_dprogram.name2
  is '��Ŀ����2��Ԥ��';
comment on column t_v_dprogram.createtimev
  is '��Ƶ�����ṩ�Ĵ���ʱ���ʽ��2014-09-19 14:32:19';
comment on column t_v_dprogram.updatetimev
  is '��Ƶ�����ṩ�ĸ���ʱ���ʽ��2014-09-19 14:32:19';
comment on column t_v_dprogram.publishtimev
  is '��Ƶ�����ṩ�ķ���ʱ���ʽ��2014-09-19 14:32:19';
comment on column t_v_dprogram.prdpack_id
  is '��Ʒ��ID';
comment on column t_v_dprogram.product_id
  is '��ƷID������Ϊ�գ�Ϊ��ʱ���ݼ۸��Բ�Ʒ�����ü۸�Ϊ׼';

comment on column t_v_dprogram.category
  is '�������ö��ֵ: 1-��Ƶ;2-��Ƶ;3-ͼ��;4-����;5-���ã�';
comment on column t_v_dprogram.type
  is '�������ͣ�1-�㲥��2-ֱ����3-���أ�4-�㲥+���أ�5-ģ��ֱ�� 0-��� 6�����ݼ���Ԥ��ֵ�� 7-������� 8-����ֱ�� 9-����ģ��ֱ��';
comment on column t_v_dprogram.serialcontentid
  is '���������缯������ID������FormtypeΪ7ʱ��Ч';
comment on column t_v_dprogram.serialsequence
  is '�����ھ缯�е���ţ�����CategoryΪ7ʱ��Ч
��1��ʼ����������
';
comment on column t_v_dprogram.serialcount
  is '�缯���ܼ���������CategoryΪ6ʱ��Ч';
comment on column t_v_dprogram.subserial_ids
  is '�缯�İ������Ӽ�ID����
��ĿID���ԡ������ŷָ�
';
comment on column t_v_dprogram.formtype
  is '�缯���ͣ�
6���缯���ǣ�
7���缯�ĵ���
8���Ǿ缯
';
comment on column t_v_dprogram.copyRightType
  is '��Ȩ����ö��ֵ��1-ǿ��Ȩ��2-����Ȩ';
comment on column t_v_dprogram.videoname
  is '��Ƶ��������';
comment on column t_v_dprogram.vshortname
  is '��Ƶ���ݼ��';
comment on column t_v_dprogram.vauthor
  is '��Ƶ��������';
comment on column t_v_dprogram.directrecflag
  is '0-iphoneֱ������
1-��֧��ֱ���ط�
2-֧��ֱ���ط�
����������TypeΪ2(ֱ��)ʱ�����ֶ���Ч��֧��ֱ���طŹ�����Ҫ��ӵ��ֶΣ�֪ͨOMS
';
comment on column t_v_dprogram.area
  is '�ɶ��������֮���ԡ�|������
ö��ֵ��1-�ڵأ�2-��ۣ�3-���ţ�4-̨�壻5-���⣻6-�κ�����
';
comment on column t_v_dprogram.terminal
  is '�ɶ�����ն�֮���ԡ�|������
ö��ֵ��1-ȫƽ̨��2-�ֻ��նˣ�3-PC��4-IPTV��5-ƽ�壻6-����������
';
comment on column t_v_dprogram.way
  is 'ö��ֵ��1-�����ţ�2-�����أ�3-����';
comment on column t_v_dprogram.publish
  is '�Ƿ��׷���ö��ֵ��0-�ǣ�1-��';
comment on column t_v_dprogram.keyword
  is '���ؼ��ְ��չؼ������ȵĹ���ƴ����һ���ֶδ洢���ǹؼ��־ͷ���ǰ�棬���Ǿͷ�����棬ʹ�����ķֺŷָ�';
comment on column t_v_dprogram.cduration
  is '�����ݵĵ㲥�ļ�����ʱ����ȡ����һ���㲥�ļ�ʱ�������û�е㲥�ļ����ֵΪ�գ���λΪ��';
comment on column t_v_dprogram.displaytype
  is '����һ������ID��CMSϵͳ����';
comment on column t_v_dprogram.displayname
  is '����һ����������';
comment on column t_v_dprogram.assist
  is '����������Ϣ���ַ�����ʽ����0~n��һ������������ɣ��ԡ�|������';

comment on column t_v_dprogram.livestatus
  is 'ֱ����Ŀ����״̬:0,δ����1���Ѵ���2����ʧ��';
  comment on column t_v_dprogram.lupdate
  is '������ʱ��';
comment on column t_v_dprogram.exetime
  is 'ͬ������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_dprogram
  add constraint pk_tvdprogram_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvdprogram_pid on t_v_dprogram (programid);
create index idx_tvdprogram_cid on t_v_dprogram (cmsid);


-- Create table ��ƵͼƬ��
create table t_v_videoPic
(
  id              varchar2(32),
  programid       Varchar2(21),
  PIC_00_TV   VARCHAR2(500),
  PIC_01_V   VARCHAR2(500),
  pic_02_hsj1080h VARCHAR2(500),
  pic_03_hsj1080v VARCHAR2(500),
  pic_04_hsj720h  VARCHAR2(500),
  pic_05_hsj720v  VARCHAR2(500),
  PIC_06       VARCHAR2(500),
  PIC_07       VARCHAR2(500),
  PIC_08       VARCHAR2(500),
  PIC_99      VARCHAR2(500),
  lupdate         date,
  exetime         date
)
;
-- Add comments to the columns 
comment on column t_v_videoPic.id
  is '����ID';
comment on column t_v_videoPic.programid
  is 'OMS����ID����ĿID';
comment on column t_v_videoPic.PIC_00_TV
  is 'ǿ��Ȩ����240 �ߣ�320��XX_TV_CONTENT.jpg.����Ȩ��ͼ��Դͼ00-ANY';
comment on column t_v_videoPic.PIC_01_V
  is 'ǿ��Ȩ����132 �ߣ�176��XX_V_CONTENT. Jpg.����Ȩ��01-63*39';
comment on column t_v_videoPic.pic_02_hsj1080h
  is 'ǿ��Ȩ����508 �ߣ�330��XX_HSJ1080H. jpg.����Ȩ��02-28*18';
comment on column t_v_videoPic.pic_03_hsj1080v
  is 'ǿ��Ȩ����330 �ߣ�450��XX_HSJ1080V. jpg.����Ȩ��03-84*59';
comment on column t_v_videoPic.pic_04_hsj720h
  is 'ǿ��Ȩ����336 �ߣ�220��XX_HSJ720H. jpg.����Ȩ��04-41*26';
comment on column t_v_videoPic.pic_05_hsj720v
  is 'ǿ��Ȩ����220 �ߣ�330��XX_HSJ720V. jpg.����Ȩ��05-100*76';
comment on column t_v_videoPic.PIC_06
  is '����Ȩ��06-43*36';
comment on column t_v_videoPic.PIC_07
  is '����Ȩ��07-168*118';
comment on column t_v_videoPic.PIC_08
  is '����Ȩ��08-82*52';
comment on column t_v_videoPic.PIC_99
  is '����Ȩ��00-ANY';
comment on column t_v_videoPic.lupdate
  is '������ʱ��';
comment on column t_v_videoPic.exetime
  is '���ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoPic
  add constraint pk_tvpic_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpic_pid on t_v_videoPic (programid);



-- Create table��Ƶ��ǩ��
create table T_V_LABLES
(
  id        VARCHAR2(32) not null,
  programid VARCHAR2(21),
  labelid   VARCHAR2(10),
 CMSID      VARCHAR2(21),
  labelname VARCHAR2(100),
  labelzone VARCHAR2(100),
  lupdate   DATE,
  exetime   DATE
);
-- Add comments to the columns 
comment on column T_V_LABLES.id
  is '����ID';
comment on column T_V_LABLES.programid
  is 'OMS����ID����ĿID';
  comment on column T_V_LABLES.CMSID
  is 'CMS����ID';
comment on column T_V_LABLES.labelid
  is '��ǩID';

comment on column T_V_LABLES.labelname
  is '������ǩ������';
comment on column T_V_LABLES.labelzone
  is '��ǩ������,�����ñ��ǩ������е�����';
comment on column T_V_LABLES.lupdate
  is 'ͬ��������ʱ��';
comment on column T_V_LABLES.exetime
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LABLES
  add constraint PK_TVLAB_ID primary key (ID)
  using index ;
-- Create/Recreate indexes 
create index IDX_TVLAB_LID on T_V_LABLES (LABELID);
create index IDX_TVLAB_PID on T_V_LABLES (PROGRAMID);


-- Create table��Ƶý���
create table t_v_videoMedia
(
  id                   varchar2(32),
  programid            VARCHAR2(21),
  cms_id               varchar2(21),
  mediafileid          VARCHAR2(22),
  mediafilename        varchar2(100),
  sourcefilename       varchar2(100),
  visitpath            VARCHAR2(500),
  mediafilepath        VARCHAR2(500),
  mediafilepreviewpath VARCHAR2(500),
  mediafileaction      Number(5),
  mediasize            Number(10),
  duration             Number(5),
  mediatype            Number(2),
  mediausagecode       Number(2),
  mediacodeformat      VARCHAR2(2),
  mediacontainformat   VARCHAR2(2),
  mediacoderate        Number(5),
  medianettype         Number(2),
  mediamimetype        VARCHAR2(2),
  mediaresolution      VARCHAR2(2),
  mediaprofile         VARCHAR2(2),
  medialevel           VARCHAR2(2),
  lupdate              date,
  exetime              date
)
;
-- Add comments to the columns 
comment on column t_v_videoMedia.id
  is '����ID';
  comment on column t_v_videoMedia.programid
  is 'OMS����ID����ĿID';
comment on column t_v_videoMedia.cms_id
  is '10λ����Ψһ�̱��룬ý��ID
CMS��2��ͷ��MMS��3��ͷ,��ƵID
';
comment on column t_v_videoMedia.mediafileid
  is 'ý��ID��ÿ���ļ��ϴ���ʱ��ϵͳ���ɵ�ID
16λ���֣�˳���ţ�2���ڲ��ظ�
';
comment on column t_v_videoMedia.mediafilename
  is '����ý���ļ�����';
comment on column t_v_videoMedia.sourcefilename
  is 'ת��Դ�ļ������ֶο���Ϊ�գ�Ǩ��ʱ��û��ת��ǰԴ�ļ��������⣬�����µĹ淶����Ҫת�룬������Ҫ��¼���ɵ��ļ��Ǵ��ĸ�Դ�ļ����н���ת���õ��������Ҫ��¼����������ԭ��Ϊ��Դ�ļ���+�û��Զ���ʶ��+����';
comment on column t_v_videoMedia.visitpath
  is '����·�����û����ŵ�·����ԭ�������ص�ַ';
comment on column t_v_videoMedia.mediafilepath
  is 'ý���ļ����·��';
comment on column t_v_videoMedia.mediafilepreviewpath
  is 'ý���ļ�Ԥ��·��';
comment on column t_v_videoMedia.mediafileaction
  is 'ö��ֵ��
0-�����ļ�
1-�޸��ļ�
2-ɾ���ļ�
3-�ļ�����
˵����
���·�����ʱ�������ļ��ĸò�����д0�����޸�/�������ʱ���ò�������ʵ����д��0��ʾ����ý���ļ���1��ʾ�滻ý���ļ���2��ʾɾ��ý���ļ���3��ʾý���ļ����ֲ���
';
comment on column t_v_videoMedia.mediasize
  is '����ý���ļ���Сϵͳ�ɸ�����ѡ�ļ������������Զ������ļ���С��ƥ�䣬��ƥ��ɹ��������޸ģ���ƥ�䲻�ɹ�������Ҫ�������б����ѡ��
��λ:�ֽ�
';
comment on column t_v_videoMedia.duration
  is '���ļ���ʱ���������ݴ��ڶ��Դ�ļ���ת�����ļ�ʱ����Դ�ļ���ʱ����ͬ����λ��';
comment on column t_v_videoMedia.mediatype
  is '����ý���ļ����ͣ� 1-VOD �㲥 2-Live Broadcast ֱ�� 3-download����  4-ת��Դ 5-ģ��ֱ�� 6�����ݼ���Ԥ��ֵ�� 7-������� 8-����ֱ�� 9-����ģ��ֱ�� 10������㲥 11����������';
comment on column t_v_videoMedia.mediausagecode
  is '���ͱ�ŵȼ������������룬ö��ֵ�������޹أ����������ý�������ļ���صĶ�Ӧ��99����ת��ǰԴMPEG2���ļ���1,2,3,4,..,96,97,98,99
����ο�3.4.1��Ƶ
����ͬ�ı���������ͣ��㲥��ֱ�������ء�ģ��ֱ���߱���ȫ�����ı������
';
comment on column t_v_videoMedia.mediacodeformat
  is '����ý���ļ������ʽ��
01-TIVC/TIAC
02-TIVC/AAC
03-TIVC/AAC+
04-H.264/AAC
05-H.264/AAC+
06-H.264/AMR-NB
07-H.264/AMR-WB
08-H.263/AMR-NB
09-H.263/AMR-WB
10-H.263/AAC
11-H.263/AAC+
12-WMV9/WMA9
13-AAC
14-AAC+
15-AMR
16-MP3
17-JPEG
18-GIF
';
comment on column t_v_videoMedia.mediacontainformat
  is '�������װ��ʽ:ö��ֵ, ��Ƶ��Ļ����ѭLRC��׼,Lrc��ʽ
�����ֱ�������ݾ��Ƿ�װ��ʽ��TS���㲥��������ʽ��01-.mp4
02-.3gp
03-.avi
04-.wmv
05-.mpg
06-.mpeg
07-.ts
08-.mp3
09-.m4a
10-.amr
11-.aac
12-.lrc
13-.sdp
14-.smil
15-.xml
';
comment on column t_v_videoMedia.mediacoderate
  is '�������͵ȼ�
ö��ֵ�������Ǿ�������ʣ��������صĶ�Ӧ��ö��ֵ�ߴ�����õ��û����顣
';
comment on column t_v_videoMedia.medianettype
  is '֧����������:1-td-cdma��2-gprs/edge 3-WLAN 4-GAN 5-HSPAö��ֵ��Ŀǰֻ�ֱ�2G,3G';
comment on column t_v_videoMedia.mediamimetype
  is 'mime����:00-NULL
��Ƶ
01-video/3gp
02-video/3gpp
03-video/mp4
04-video/x-ms-wmv
05-video/mpeg
06-video/x-mpeg
����
11-audio/mp3
12-audio/x-mp3
13-audio/aac
14-audio/x-aac
15-audio/x-m4a
16-audio/x-m4b
ϵͳ�����ļ������ͱ������ͽ���ƥ�䣬�Զ�ѡ��Ҳ�ɴ������б����ѡ��
��Ƶ���� MIME��������Ҫ��ȷ��
';
comment on column t_v_videoMedia.mediaresolution
  is '�ֱ�������
01-QCIF 176*144
02-EDGE 240*180
03-QVGA 320*240
04-CIF 352*288
05-WQVGA 400*240
06-HVGA 320*480
07-480*360
08-VGA 640*480
09-D1 720*576
10-WVGA 800*480
11-720P 1280*720
12-1080i 1920*1080
13-1080P 1920*1080
14-400*300
';
comment on column t_v_videoMedia.mediaprofile
  is '00-NULL
01-Simple
02-Advanced Simple
03-H.264 Baseline
04-H.264Main
05-H.264High
06-H.263 Profile 0
07-H.263 Profile 3
08-H.263 Profile 5
09-H.263 Profile 7
10-AAC-LC
11-AAC-HE
';
comment on column t_v_videoMedia.medialevel
  is '00-NULL
01-H.264 Level 1
02-H.264 Level 1b
03-H.264 Level 1.1
04-H.264 Level 1.2
05-H.264 Level 1.3
06-H.264 Level 2.0
07-H.263 Level 10
08-H.263 Level 20
09-H.263 Level 30
10-H.263 Level 45
';
comment on column t_v_videoMedia.lupdate
  is 'ͬ��������ʱ��';
comment on column t_v_videoMedia.exetime
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoMedia
  add constraint pk_tvmedia_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvmedia_vid on t_v_videoMedia (cms_id);
create index idx_tvmedia_pid on t_v_videoMedia (programid);
create index idx_tvmedia_meid on t_v_videoMedia (mediafileid);


-- Create table��Ƶ���ݶ��������
create table t_v_videosPropertys
(
  id            varchar2(32),
  programid     VARCHAR2(21),
  cms_id        varchar2(21),
  propertykey   vARCHAR2(100),
  propertyvalue VARCHAR2(4000),
  lupdate       date,
  exetime       date
)
;
-- Add comments to the columns 
comment on column t_v_videosPropertys.id
  is '����ID';
    comment on column t_v_videosPropertys.programid
  is 'OMS����ID����ĿID';
comment on column t_v_videosPropertys.cms_id
  is '10λ����Ψһ�̱��룬ý��ID
CMS��2��ͷ��MMS��3��ͷ
';
comment on column t_v_videosPropertys.propertykey
  is '���ݶ�����������/���ݶ�����������';
comment on column t_v_videosPropertys.propertyvalue
  is '����Valueֵ�����ֵ��ƴ��ʱ�á�|���ָ���ܰ���</>������Щ�����ַ�';
comment on column t_v_videosPropertys.lupdate
  is 'ͬ��������ʱ��';
comment on column t_v_videosPropertys.exetime
  is '���ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videosPropertys
  add constraint pk_tvproper_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvproper_vid on t_v_videosPropertys (cms_id);
create index idx_tvproper_pid on t_v_videosPropertys (programid);


-- Create table��Ƶ���ܱ�
create table t_v_category
(
  id         varchar2(32),
  categoryid varchar2(32),
  cname      VARCHAR2(500),
  cdesc      varchar2(2000),
  pic        VARCHAR2(500),
  isshow     varchar2(2),
  parentcid  varchar2(32),
  sortid     Number(8),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_v_category.id
  is '����ID';
comment on column t_v_category.categoryid
  is '���ܱ���ID��Ĭ��';
comment on column t_v_category.cname
  is '��������';
comment on column t_v_category.cdesc
  is '��Ƶ��������';
comment on column t_v_category.pic
  is '��Ƶ����ͼƬ';
comment on column t_v_category.isshow
  is '�Ƿ����Ż�չʾ��1��չʾ��0����չʾ';
comment on column t_v_category.parentcid
  is '�����ܱ���ID,������IDΪ-1��ʾ������';
comment on column t_v_category.sortid
  is '�������';
comment on column t_v_category.lupdate
  is '������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_category
  add constraint pk_tvcategory_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_tvcategory_cid on t_v_category (categoryid);



-- Create table��Ƶ��Ʒ��
create table t_v_reference
(
  id         varchar2(32),
  programid  varchar2(21),
  categoryid VARCHAR2(32),
  cms_id     varchar2(21),
  pname      varchar2(128),
  sortid     Number(8),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_v_reference.id
  is '����ID';
comment on column t_v_reference.programid
  is 'OMS����ID����ĿID';
comment on column t_v_reference.categoryid
  is '���ܱ���ID';
comment on column t_v_reference.cms_id
  is '10λ����Ψһ�̱��룬ý��ID
CMS��2��ͷ��MMS��3��ͷ,��ƵID
';
comment on column t_v_reference.pname
  is '��Ŀ����';
comment on column t_v_reference.sortid
  is '�����';
comment on column t_v_reference.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_reference
  add constraint pk_tvreference_id primary key (ID);
-- Create/Recreate indexes 
create unique index IDX_TVREFERENCE_PCID on T_V_REFERENCE (PROGRAMID, CATEGORYID);



-- Create table��Ƶҵ���Ʒ��
create table t_v_propkg
(
  id           varchar2(32),
  servid       varchar2(21),
  prodname     varchar2(4000),
  protype      varchar2(21),
  propkgtype   varchar2(21),
  monthfeecode VARCHAR(21),
  dotfeecode   VARCHAR(21),
  freefeecode  VARCHAR(21),
  lupdate      date
)
;
-- Add comments to the columns 
comment on column t_v_propkg.id
  is '����ID';
comment on column t_v_propkg.servid
  is 'ҵ���ƷID';
comment on column t_v_propkg.prodname
  is '��Ʒ����';
comment on column t_v_propkg.protype
  is '��Ʒ���ͣ�1�����ӽ�  2������Ƶ 3.�������';
comment on column t_v_propkg.propkgtype
  is '��Ʒ�����ԣ�1����ֱ��Ŀ 2:Ʒ����Ŀ 3��V+ 4:��վ 5��G��G�� 6������7�����ӽ� 8������';
comment on column t_v_propkg.monthfeecode
  is '���¼Ʒѱ���';
comment on column t_v_propkg.dotfeecode
  is '���μƷѱ���';
comment on column t_v_propkg.freefeecode
  is '��ѼƷѱ���';
comment on column t_v_propkg.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_propkg
  add constraint pk_tvpropkg_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpropkg_sid on t_v_propkg (servid);


-- Create table ��Ƶ�Ʒѱ�
create table t_v_PRODUCT
(
  id      varchar2(32),
  feecode VARCHAR(21),
  feetype VARCHAR(4),
  fee     VARCHAR(4),
  productdesc     VARCHAR(2000),
  lupdate varchar2(20)
)
;
-- Add comments to the columns 
comment on column t_v_PRODUCT.id
  is '����ID';
comment on column t_v_PRODUCT.feecode
  is '�Ʒѱ���';
comment on column t_v_PRODUCT.feetype
  is '�Ʒ����ͣ�0	����
1	����
7	���
13	����
15	�����
';
comment on column t_v_PRODUCT.fee
  is '�۸񣺵�λ����';
  comment on column t_v_PRODUCT.productdesc
  is '��Ʒ����';
comment on column t_v_PRODUCT.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_PRODUCT
  add constraint pk_tvproduc_id primary key (ID);


-- Create table ��Ƶֱ����
create table T_V_LIVE
(
  id        VARCHAR2(32) not null,
  programid VARCHAR2(21),
  cmsid     VARCHAR2(21),
  playname  VARCHAR2(128),
  playday   VARCHAR2(8),
  starttime VARCHAR2(20),
  endtime   VARCHAR2(20),
  lupdate   DATE
);
-- Add comments to the columns 
comment on column T_V_LIVE.id
  is '����ID';
comment on column T_V_LIVE.programid
  is 'OMS����ID����ĿID';
comment on column T_V_LIVE.cmsid
  is 'CMS����ID';
comment on column T_V_LIVE.playname
  is '��Ŀ����';
comment on column T_V_LIVE.playday
  is '��Ŀ����Ӧ���ڸ�ʽΪ��YYYYMMDD';
comment on column T_V_LIVE.starttime
  is '��Ŀ��ʼʱ���ʽΪ��YYYY-MM-DD HH:mm';
comment on column T_V_LIVE.endtime
  is '��Ŀ����ʱ�� ��ʽΪ��YYYY-MM-DD HH:mm';
comment on column T_V_LIVE.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LIVE
  add constraint PK_TVLIVE_ID primary key (ID)
  using index ;
-- Create/Recreate indexes 
create index IDX_TVLIVE_PID on T_V_LIVE (PROGRAMID);


-- Create table��Ƶ�ȵ������
create table t_v_hotcontent
(
  id        varchar2(32),
  titleid   varchar2(21),
  titlename VARCHAR(50),
  pubtime   VARCHAR2(20),
  exestatus   VARCHAR2(2) default '0',
  lupdate   date
)
;
-- Add comments to the columns 
comment on column t_v_hotcontent.id
  is '����ID';
comment on column t_v_hotcontent.titleid
  is '�����ȵ�����ID';
comment on column t_v_hotcontent.titlename
  is '�ȵ���������';
  comment on column t_v_hotcontent.exestatus
  is 'xml��������״̬��1�Ѵ��� 0δ����2����ʧ��';
comment on column t_v_hotcontent.pubtime
  is '����ʱ�� ��ʽ��2014-09-19 14:32:19';
comment on column t_v_hotcontent.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_hotcontent
  add constraint pk_tvhotcontent_id primary key (ID);


-- Create table�ȵ�������ܹ�ϵ��
create table t_v_hotcatemap
(
  id         varchar2(32),
  titleid    VARCHAR(21),
  categoryid varchar2(32),
   type       VARCHAR2(2),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_v_hotcatemap.id
  is '����ID';
comment on column t_v_hotcatemap.titleid
  is '�����ȵ�����ID����һ������ID';
comment on column t_v_hotcatemap.categoryid
  is '���ܱ���ID';
  comment on column T_V_HOTCATEMAP.type
  is '����:1,��ʾ�ȵ����⣬2����ʾһ�����࣬';
comment on column t_v_hotcatemap.lupdate
  is 'ͬ��������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_hotcatemap
  add constraint pk_tvhotmap_id primary key (ID);


-- Create table����Ƶ����ϵ��
create table t_v_type
(
  id          varchar2(32),
  categoryid varchar2(32),
  cateid      VARCHAR(32),
  catename    varchar2(300),
  subcatename varchar2(300),
  taggruid    VARCHAR(32),
  taggroup    varchar2(300),
  tagname     varchar2(300),
  subtagname  varchar2(300)
)
;
-- Add comments to the columns 
comment on column t_v_type.id
  is '����ID';
comment on column t_v_type.categoryid
  is '���ܱ���ID';
comment on column t_v_type.cateid
  is 'һ������ID';
comment on column t_v_type.catename
  is 'һ����������';
comment on column t_v_type.subcatename
  is '������������';
comment on column t_v_type.taggruid
  is '��ǩ��ID';
comment on column t_v_type.taggroup
  is '��ǩ������';
comment on column t_v_type.tagname
  is 'һ����ǩ����';
comment on column t_v_type.subtagname
  is '������ǩ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_type
  add constraint pk_tvtype_id primary key (ID);


-- Create table��Ƶ�����Ʒѱ�
create table t_v_PKGsales
(
  id             varchar2(32),
  prdpack_id     varchar2(21),
  salesproductid VARCHAR2(21),
  salesdiscount  VARCHAR2(20),
  salescategory  NUMBER(2),
  salesstarttime VARCHAR2(19),
  salesendtime   VARCHAR2(19),
  lupdate        date
)
;
-- Add comments to the columns 
comment on column t_v_PKGsales.id
  is '����ID';
comment on column t_v_PKGsales.prdpack_id
  is '��Ʒ��ID';
comment on column t_v_PKGsales.salesproductid
  is '������ƷID';
comment on column t_v_PKGsales.salesdiscount
  is '�ۿ�';
comment on column t_v_PKGsales.salescategory
  is '��������    
1.Ԥ��  2.Ԥ��   3.����
';
comment on column t_v_PKGsales.salesstarttime
  is '������ʼʱ��
��ʽ��2014-09-19
';
comment on column t_v_PKGsales.salesendtime
  is '��������ʱ��
��ʽ��2014-09-19
';
comment on column t_v_PKGsales.lupdate
  is '������ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_PKGsales
  add constraint pk_tvpkgsale_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpkgsale_pid on t_v_PKGsales (prdpack_id);

-----------------------
------�����м��-------------
------------------------
-- Create table��ƵͼƬ�м��
create table t_v_videoPic_mid
(
  id              varchar2(32),
  programid       Varchar2(21),
  PIC_00_TV   VARCHAR2(500),
  PIC_01_V   VARCHAR2(500),
  pic_02_hsj1080h VARCHAR2(500),
  pic_03_hsj1080v VARCHAR2(500),
  pic_04_hsj720h  VARCHAR2(500),
  pic_05_hsj720v  VARCHAR2(500),
  PIC_06       VARCHAR2(500),
  PIC_07       VARCHAR2(500),
  PIC_08       VARCHAR2(500),
  PIC_99      VARCHAR2(500),
  lupdate         date,
  exetime         date
)
;
-- Add comments to the columns 
comment on column t_v_videoPic_mid.id
  is '����ID';
comment on column t_v_videoPic_mid.programid
  is 'OMS����ID����ĿID';
comment on column t_v_videoPic_mid.PIC_00_TV
  is 'ǿ��Ȩ����240 �ߣ�320��XX_TV_CONTENT.jpg.����Ȩ��ͼ��Դͼ00-ANY';
comment on column t_v_videoPic_mid.PIC_01_V
  is 'ǿ��Ȩ����132 �ߣ�176��XX_V_CONTENT. Jpg.����Ȩ��01-63*39';
comment on column t_v_videoPic_mid.pic_02_hsj1080h
  is 'ǿ��Ȩ����508 �ߣ�330��XX_HSJ1080H. jpg.����Ȩ��02-28*18';
comment on column t_v_videoPic_mid.pic_03_hsj1080v
  is 'ǿ��Ȩ����330 �ߣ�450��XX_HSJ1080V. jpg.����Ȩ��03-84*59';
comment on column t_v_videoPic_mid.pic_04_hsj720h
  is 'ǿ��Ȩ����336 �ߣ�220��XX_HSJ720H. jpg.����Ȩ��04-41*26';
comment on column t_v_videoPic_mid.pic_05_hsj720v
  is 'ǿ��Ȩ����220 �ߣ�330��XX_HSJ720V. jpg.����Ȩ��05-100*76';
  
comment on column t_v_videoPic_mid.PIC_06
  is '����Ȩ��06-43*36';
comment on column t_v_videoPic_mid.PIC_07
  is '����Ȩ��07-168*118';
comment on column t_v_videoPic_mid.PIC_08
  is '����Ȩ��08-82*52';
comment on column t_v_videoPic_mid.PIC_99
  is '����Ȩ��00-ANY';
comment on column t_v_videoPic_mid.lupdate
  is '������ʱ��';
comment on column t_v_videoPic_mid.exetime
  is '���ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoPic_mid
  add constraint pk_tvpicmid_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpicmid_pid on t_v_videoPic_mid (programid);




-- Create table��Ƶ��ǩ�м��
create table T_V_LABLES_mid
(
  id        VARCHAR2(32) not null,
  programid VARCHAR2(21),
  labelid   VARCHAR2(10),
  CMSID     VARCHAR2(21),
  labelname VARCHAR2(100),
  labelzone VARCHAR2(100),
  lupdate   DATE,
  exetime   DATE
);
-- Add comments to the columns 
comment on column T_V_LABLES_mid.id
  is '����ID';
comment on column T_V_LABLES_mid.programid
  is 'OMS����ID����ĿID';
  comment on column T_V_LABLES_mid.CMSID
  is 'CMS����ID';
comment on column T_V_LABLES_mid.labelid
  is '��ǩID';

comment on column T_V_LABLES_mid.labelname
  is '������ǩ������';
comment on column T_V_LABLES_mid.labelzone
  is '��ǩ������,�����ñ��ǩ������е�����';
comment on column T_V_LABLES_mid.lupdate
  is 'ͬ��������ʱ��';
comment on column T_V_LABLES_mid.exetime
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LABLES_mid
  add constraint PK_TVLABmid_ID primary key (ID)
  using index ;
-- Create/Recreate indexes 
create index IDX_TVLABmid_LID on T_V_LABLES_mid (LABELID);
create index IDX_TVLABmid_PID on T_V_LABLES_mid (PROGRAMID);

-- Create table��Ƶý���м��
create table t_v_videoMedia_mid
(
  id                   varchar2(32),
  programid            VARCHAR2(21),
  cms_id               varchar2(21),
  mediafileid          VARCHAR2(22),
  mediafilename        varchar2(100),
  sourcefilename       varchar2(100),
  visitpath            VARCHAR2(500),
  mediafilepath        VARCHAR2(500),
  mediafilepreviewpath VARCHAR2(500),
  mediafileaction      Number(5),
  mediasize            Number(10),
  duration             Number(5),
  mediatype            Number(2),
  mediausagecode       Number(2),
  mediacodeformat      VARCHAR2(2),
  mediacontainformat   VARCHAR2(2),
  mediacoderate        Number(5),
  medianettype         Number(2),
  mediamimetype        VARCHAR2(2),
  mediaresolution      VARCHAR2(2),
  mediaprofile         VARCHAR2(2),
  medialevel           VARCHAR2(2),
  lupdate              date,
  exetime              date
)
;
-- Add comments to the columns 
comment on column t_v_videoMedia_mid.id
  is '����ID';
  comment on column t_v_videoMedia_mid.programid
  is 'OMS����ID����ĿID';
comment on column t_v_videoMedia_mid.cms_id
  is '10λ����Ψһ�̱��룬ý��ID
CMS��2��ͷ��MMS��3��ͷ,��ƵID
';
comment on column t_v_videoMedia_mid.mediafileid
  is 'ý��ID��ÿ���ļ��ϴ���ʱ��ϵͳ���ɵ�ID
16λ���֣�˳���ţ�2���ڲ��ظ�
';
comment on column t_v_videoMedia_mid.mediafilename
  is '����ý���ļ�����';
comment on column t_v_videoMedia_mid.sourcefilename
  is 'ת��Դ�ļ������ֶο���Ϊ�գ�Ǩ��ʱ��û��ת��ǰԴ�ļ��������⣬�����µĹ淶����Ҫת�룬������Ҫ��¼���ɵ��ļ��Ǵ��ĸ�Դ�ļ����н���ת���õ��������Ҫ��¼����������ԭ��Ϊ��Դ�ļ���+�û��Զ���ʶ��+����';
comment on column t_v_videoMedia_mid.visitpath
  is '����·�����û����ŵ�·����ԭ�������ص�ַ';
comment on column t_v_videoMedia_mid.mediafilepath
  is 'ý���ļ����·��';
comment on column t_v_videoMedia_mid.mediafilepreviewpath
  is 'ý���ļ�Ԥ��·��';
comment on column t_v_videoMedia_mid.mediafileaction
  is 'ö��ֵ��
0-�����ļ�
1-�޸��ļ�
2-ɾ���ļ�
3-�ļ�����
˵����
���·�����ʱ�������ļ��ĸò�����д0�����޸�/�������ʱ���ò�������ʵ����д��0��ʾ����ý���ļ���1��ʾ�滻ý���ļ���2��ʾɾ��ý���ļ���3��ʾý���ļ����ֲ���
';
comment on column t_v_videoMedia_mid.mediasize
  is '����ý���ļ���Сϵͳ�ɸ�����ѡ�ļ������������Զ������ļ���С��ƥ�䣬��ƥ��ɹ��������޸ģ���ƥ�䲻�ɹ�������Ҫ�������б����ѡ��
��λ:�ֽ�
';
comment on column t_v_videoMedia_mid.duration
  is '���ļ���ʱ���������ݴ��ڶ��Դ�ļ���ת�����ļ�ʱ����Դ�ļ���ʱ����ͬ����λ��';
comment on column t_v_videoMedia_mid.mediatype
  is '����ý���ļ����ͣ� 1-VOD �㲥 2-Live Broadcast ֱ�� 3-download����  4-ת��Դ 5-ģ��ֱ�� 6�����ݼ���Ԥ��ֵ�� 7-������� 8-����ֱ�� 9-����ģ��ֱ�� 10������㲥 11����������';
comment on column t_v_videoMedia_mid.mediausagecode
  is '���ͱ�ŵȼ������������룬ö��ֵ�������޹أ����������ý�������ļ���صĶ�Ӧ��99����ת��ǰԴMPEG2���ļ���1,2,3,4,..,96,97,98,99
����ο�3.4.1��Ƶ
����ͬ�ı���������ͣ��㲥��ֱ�������ء�ģ��ֱ���߱���ȫ�����ı������
';
comment on column t_v_videoMedia_mid.mediacodeformat
  is '����ý���ļ������ʽ��
01-TIVC/TIAC
02-TIVC/AAC
03-TIVC/AAC+
04-H.264/AAC
05-H.264/AAC+
06-H.264/AMR-NB
07-H.264/AMR-WB
08-H.263/AMR-NB
09-H.263/AMR-WB
10-H.263/AAC
11-H.263/AAC+
12-WMV9/WMA9
13-AAC
14-AAC+
15-AMR
16-MP3
17-JPEG
18-GIF
';
comment on column t_v_videoMedia_mid.mediacontainformat
  is '�������װ��ʽ:ö��ֵ, ��Ƶ��Ļ����ѭLRC��׼,Lrc��ʽ
�����ֱ�������ݾ��Ƿ�װ��ʽ��TS���㲥��������ʽ��01-.mp4
02-.3gp
03-.avi
04-.wmv
05-.mpg
06-.mpeg
07-.ts
08-.mp3
09-.m4a
10-.amr
11-.aac
12-.lrc
13-.sdp
14-.smil
15-.xml
';
comment on column t_v_videoMedia_mid.mediacoderate
  is '�������͵ȼ�
ö��ֵ�������Ǿ�������ʣ��������صĶ�Ӧ��ö��ֵ�ߴ�����õ��û����顣
';
comment on column t_v_videoMedia_mid.medianettype
  is '֧����������:1-td-cdma��2-gprs/edge 3-WLAN 4-GAN 5-HSPAö��ֵ��Ŀǰֻ�ֱ�2G,3G';
comment on column t_v_videoMedia_mid.mediamimetype
  is 'mime����:00-NULL
��Ƶ
01-video/3gp
02-video/3gpp
03-video/mp4
04-video/x-ms-wmv
05-video/mpeg
06-video/x-mpeg
����
11-audio/mp3
12-audio/x-mp3
13-audio/aac
14-audio/x-aac
15-audio/x-m4a
16-audio/x-m4b
ϵͳ�����ļ������ͱ������ͽ���ƥ�䣬�Զ�ѡ��Ҳ�ɴ������б����ѡ��
��Ƶ���� MIME��������Ҫ��ȷ��
';
comment on column t_v_videoMedia_mid.mediaresolution
  is '�ֱ�������
01-QCIF 176*144
02-EDGE 240*180
03-QVGA 320*240
04-CIF 352*288
05-WQVGA 400*240
06-HVGA 320*480
07-480*360
08-VGA 640*480
09-D1 720*576
10-WVGA 800*480
11-720P 1280*720
12-1080i 1920*1080
13-1080P 1920*1080
14-400*300
';
comment on column t_v_videoMedia_mid.mediaprofile
  is '00-NULL
01-Simple
02-Advanced Simple
03-H.264 Baseline
04-H.264Main
05-H.264High
06-H.263 Profile 0
07-H.263 Profile 3
08-H.263 Profile 5
09-H.263 Profile 7
10-AAC-LC
11-AAC-HE
';
comment on column t_v_videoMedia_mid.medialevel
  is '00-NULL
01-H.264 Level 1
02-H.264 Level 1b
03-H.264 Level 1.1
04-H.264 Level 1.2
05-H.264 Level 1.3
06-H.264 Level 2.0
07-H.263 Level 10
08-H.263 Level 20
09-H.263 Level 30
10-H.263 Level 45
';
comment on column t_v_videoMedia_mid.lupdate
  is 'ͬ��������ʱ��';
comment on column t_v_videoMedia_mid.exetime
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoMedia_mid
  add constraint pk_tvmediamid_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvmediamid_vid on t_v_videoMedia_mid (cms_id);
create index idx_tvmediamid_pid on t_v_videoMedia_mid (programid);
create index idx_tvmediamid_meid on t_v_videoMedia_mid (mediafileid);


-- Create table��Ƶ���ݶ��������м��
create table t_v_videosPropertys_mid
(
  id            varchar2(32),
  programid     VARCHAR2(21),
  cms_id        varchar2(21),
  propertykey   vARCHAR2(100),
  propertyvalue VARCHAR2(4000),
  lupdate       date,
  exetime       date
)
;
-- Add comments to the columns 
comment on column t_v_videosPropertys_mid.id
  is '����ID';
    comment on column t_v_videosPropertys_mid.programid
  is 'OMS����ID����ĿID';
comment on column t_v_videosPropertys_mid.cms_id
  is '10λ����Ψһ�̱��룬ý��ID
CMS��2��ͷ��MMS��3��ͷ
';
comment on column t_v_videosPropertys_mid.propertykey
  is '���ݶ�����������/���ݶ�����������';
comment on column t_v_videosPropertys_mid.propertyvalue
  is '����Valueֵ�����ֵ��ƴ��ʱ�á�|���ָ���ܰ���</>������Щ�����ַ�';
comment on column t_v_videosPropertys_mid.lupdate
  is 'ͬ��������ʱ��';
comment on column t_v_videosPropertys_mid.exetime
  is '���ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videosPropertys_mid
  add constraint pk_tvpropermid_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpropermid_vid on t_v_videosPropertys_mid (cms_id);
create index idx_tvpropermid_pid on t_v_videosPropertys_mid (programid);

-- Create table��Ƶ����ʱ���
create table t_v_lasttime
(
  id      varchar2(32),
  lupdate date
)
;
-- Add comments to the columns 
comment on column t_v_lasttime.id
  is '��������ID';
comment on column t_v_lasttime.lupdate
  is '���ִ��ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_lasttime
  add constraint pk_tvlasttime_id primary key (ID);
  

----------������ ����---------


----------�������� ��ʼ---------
-- Create sequence 
create sequence SEQ_T_V_SPROGRAM_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


-- Create sequence 
create sequence SEQ_T_V_DPROGRAM_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

-- Create sequence 
create sequence SEQ_T_V_VIDEOPIC_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

-- Create table
create sequence SEQ_T_V_LABLES_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


-- Create table
create sequence SEQ_T_V_VIDEOMEDIA_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

-- Create table
create sequence SEQ_T_V_VIDEOSPROP_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

-- Create table
create sequence SEQ_T_V_CATEGORY_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


-- Create ��Ƶ���ܱ���
create sequence SEQ_T_V_CATEGORY_CID
minvalue 10000000
maxvalue 999999999999
start with 10000000
increment by 1
nocache
cycle;


-- Create table
create sequence SEQ_T_V_REFERENCE_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

-- Create table
create sequence SEQ_T_V_PROPKG_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

create sequence SEQ_T_V_PRODUCT_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

create sequence SEQ_T_V_LIVE_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

create sequence SEQ_T_V_HOTCONT_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

create sequence SEQ_T_V_HOTCATEMAP_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

create sequence SEQ_T_V_TYPE_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

create sequence SEQ_T_V_PKGSALES_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


create sequence SEQ_T_V_lasttime_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

----------�������� ����---------
