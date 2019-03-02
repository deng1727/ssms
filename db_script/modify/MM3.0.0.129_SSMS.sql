-- 201412基地视频改版

----------创建表 开始---------
-- Create table节目概览表
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
  is '主键ID';
comment on column t_v_sprogram.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_sprogram.cmsid
  is 'CMS内容ID';
comment on column t_v_sprogram.channelid
  is '渠道ID';
  comment on column t_v_sprogram.pubtime
  is '发布时间';
  comment on column t_v_sprogram.status
  is '12已发布 22已下线';
    comment on column t_v_sprogram.exestatus
  is '增量处理状态：1已处理 0未处理，2处理失败';
comment on column t_v_sprogram.updatetime
  is '最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_sprogram
  add constraint pk_tvsprogram_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvprogram_pid on t_v_sprogram (programid);
create index idx_tvprogram_cid on t_v_sprogram (cmsid);


-- Create table节目详情表
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
  is '主键ID';
comment on column t_v_dprogram.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_dprogram.cmsid
  is 'CMS内容ID,视频ID';
comment on column t_v_dprogram.name
  is '节目名称';
comment on column t_v_dprogram.name1
  is '节目名称1，预留';
comment on column t_v_dprogram.name2
  is '节目名称2，预留';
comment on column t_v_dprogram.createtimev
  is '视频基地提供的创建时间格式如2014-09-19 14:32:19';
comment on column t_v_dprogram.updatetimev
  is '视频基地提供的更新时间格式如2014-09-19 14:32:19';
comment on column t_v_dprogram.publishtimev
  is '视频基地提供的发布时间格式如2014-09-19 14:32:19';
comment on column t_v_dprogram.prdpack_id
  is '产品包ID';
comment on column t_v_dprogram.product_id
  is '产品ID，可以为空，为空时内容价格以产品包配置价格为准';

comment on column t_v_dprogram.category
  is '内容类别枚举值: 1-视频;2-音频;3-图文;4-弃用;5-弃用；';
comment on column t_v_dprogram.type
  is '服务类型：1-点播；2-直播；3-下载；4-点播+下载；5-模拟直播 0-浏览 6－内容集（预留值） 7-精简编码 8-精简直播 9-精简模拟直播';
comment on column t_v_dprogram.serialcontentid
  is '单集所属剧集的内容ID，仅当Formtype为7时有效';
comment on column t_v_dprogram.serialsequence
  is '单集在剧集中的序号，仅当Category为7时有效
从1开始的连续数字
';
comment on column t_v_dprogram.serialcount
  is '剧集的总集数，仅当Category为6时有效';
comment on column t_v_dprogram.subserial_ids
  is '剧集的包含的子集ID集合
节目ID，以“，”号分割
';
comment on column t_v_dprogram.formtype
  is '剧集类型：
6：剧集（壳）
7：剧集的单集
8：非剧集
';
comment on column t_v_dprogram.copyRightType
  is '版权类型枚举值：1-强版权；2-弱版权';
comment on column t_v_dprogram.videoname
  is '视频内容名称';
comment on column t_v_dprogram.vshortname
  is '视频内容简称';
comment on column t_v_dprogram.vauthor
  is '视频内容作者';
comment on column t_v_dprogram.directrecflag
  is '0-iphone直播内容
1-不支持直播回放
2-支持直播回放
当内容类型Type为2(直播)时，该字段有效。支持直播回放功能需要添加的字段，通知OMS
';
comment on column t_v_dprogram.area
  is '可多个，地域之间以“|”隔开
枚举值：1-内地；2-香港；3-澳门；4-台湾；5-海外；6-任何区域
';
comment on column t_v_dprogram.terminal
  is '可多个，终端之间以“|”隔开
枚举值：1-全平台；2-手机终端；3-PC；4-IPTV；5-平板；6-数字有线网
';
comment on column t_v_dprogram.way
  is '枚举值：1-仅播放；2-仅下载；3-不限';
comment on column t_v_dprogram.publish
  is '是否首发：枚举值：0-是；1-否';
comment on column t_v_dprogram.keyword
  is '将关键字按照关键字优先的规则拼接在一个字段存储，是关键字就放最前面，不是就放最后面，使用中文分号分隔';
comment on column t_v_dprogram.cduration
  is '该内容的点播文件播放时长：取任意一个点播文件时长，如果没有点播文件这个值为空，单位为秒';
comment on column t_v_dprogram.displaytype
  is '内容一级分类ID，CMS系统生成';
comment on column t_v_dprogram.displayname
  is '内容一级分类名称';
comment on column t_v_dprogram.assist
  is '辅助分类信息，字符串形式，由0~n个一级分类名称组成，以“|”隔开';

comment on column t_v_dprogram.livestatus
  is '直播节目处理状态:0,未处理，1，已处理，2处理失败';
  comment on column t_v_dprogram.lupdate
  is '最后更新时间';
comment on column t_v_dprogram.exetime
  is '同步导入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_dprogram
  add constraint pk_tvdprogram_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvdprogram_pid on t_v_dprogram (programid);
create index idx_tvdprogram_cid on t_v_dprogram (cmsid);


-- Create table 视频图片表
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
  is '主键ID';
comment on column t_v_videoPic.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_videoPic.PIC_00_TV
  is '强版权：宽：240 高：320，XX_TV_CONTENT.jpg.弱版权：图标源图00-ANY';
comment on column t_v_videoPic.PIC_01_V
  is '强版权：宽：132 高：176，XX_V_CONTENT. Jpg.弱版权：01-63*39';
comment on column t_v_videoPic.pic_02_hsj1080h
  is '强版权：宽：508 高：330，XX_HSJ1080H. jpg.弱版权：02-28*18';
comment on column t_v_videoPic.pic_03_hsj1080v
  is '强版权：宽：330 高：450，XX_HSJ1080V. jpg.弱版权：03-84*59';
comment on column t_v_videoPic.pic_04_hsj720h
  is '强版权：宽：336 高：220，XX_HSJ720H. jpg.弱版权：04-41*26';
comment on column t_v_videoPic.pic_05_hsj720v
  is '强版权：宽：220 高：330，XX_HSJ720V. jpg.弱版权：05-100*76';
comment on column t_v_videoPic.PIC_06
  is '弱版权：06-43*36';
comment on column t_v_videoPic.PIC_07
  is '弱版权：07-168*118';
comment on column t_v_videoPic.PIC_08
  is '弱版权：08-82*52';
comment on column t_v_videoPic.PIC_99
  is '弱版权：00-ANY';
comment on column t_v_videoPic.lupdate
  is '最后更新时间';
comment on column t_v_videoPic.exetime
  is '入库时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoPic
  add constraint pk_tvpic_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpic_pid on t_v_videoPic (programid);



-- Create table视频标签表
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
  is '主键ID';
comment on column T_V_LABLES.programid
  is 'OMS内容ID，节目ID';
  comment on column T_V_LABLES.CMSID
  is 'CMS内容ID';
comment on column T_V_LABLES.labelid
  is '标签ID';

comment on column T_V_LABLES.labelname
  is '单个标签的名称';
comment on column T_V_LABLES.labelzone
  is '标签组名称,来配置表标签分类表中的数据';
comment on column T_V_LABLES.lupdate
  is '同步最后更新时间';
comment on column T_V_LABLES.exetime
  is '插入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LABLES
  add constraint PK_TVLAB_ID primary key (ID)
  using index ;
-- Create/Recreate indexes 
create index IDX_TVLAB_LID on T_V_LABLES (LABELID);
create index IDX_TVLAB_PID on T_V_LABLES (PROGRAMID);


-- Create table视频媒体表
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
  is '主键ID';
  comment on column t_v_videoMedia.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_videoMedia.cms_id
  is '10位内容唯一短编码，媒资ID
CMS以2开头，MMS以3开头,视频ID
';
comment on column t_v_videoMedia.mediafileid
  is '媒体ID：每个文件上传的时候，系统生成的ID
16位数字，顺序编号，2年内不重复
';
comment on column t_v_videoMedia.mediafilename
  is '单个媒体文件名称';
comment on column t_v_videoMedia.sourcefilename
  is '转码源文件名该字段可以为空，迁移时并没有转码前源文件名。另外，由于新的规范中需要转码，这里需要记录生成的文件是从哪个源文件名中进行转换得到，因此需要记录下来，命名原则为：源文件名+用户自定义识别+序列';
comment on column t_v_videoMedia.visitpath
  is '访问路径，用户播放的路径，原播放下载地址';
comment on column t_v_videoMedia.mediafilepath
  is '媒体文件存放路径';
comment on column t_v_videoMedia.mediafilepreviewpath
  is '媒体文件预览路径';
comment on column t_v_videoMedia.mediafileaction
  is '枚举值：
0-新增文件
1-修改文件
2-删除文件
3-文件不变
说明：
当新发内容时，所有文件的该参数填写0；当修改/变更内容时，该参数根据实际填写，0表示新增媒体文件，1表示替换媒体文件，2表示删除媒体文件，3表示媒体文件保持不变
';
comment on column t_v_videoMedia.mediasize
  is '单个媒体文件大小系统可根据所选文件的命名规则自动进行文件大小的匹配，若匹配成功，则不能修改；若匹配不成功，则需要从下拉列表框中选择
单位:字节
';
comment on column t_v_videoMedia.duration
  is '该文件的时长由于内容存在多个源文件，转码后的文件时长与源文件的时长相同，单位秒';
comment on column t_v_videoMedia.mediatype
  is '单个媒体文件类型： 1-VOD 点播 2-Live Broadcast 直播 3-download下载  4-转码源 5-模拟直播 6－内容集（预留值） 7-精简编码 8-精简直播 9-精简模拟直播 10－精简点播 11－精简下载';
comment on column t_v_videoMedia.mediausagecode
  is '类型编号等级，编码分类代码，枚举值和码率无关，具体见各种媒体类型文件相关的对应表。99代表转码前源MPEG2的文件。1,2,3,4,..,96,97,98,99
具体参考3.4.1视频
代表不同的编码分类类型，点播、直播、下载、模拟直播具备完全独立的编码分类
';
comment on column t_v_videoMedia.mediacodeformat
  is '单个媒体文件编码格式：
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
  is '容器或封装格式:枚举值, 音频字幕将遵循LRC标准,Lrc格式
如果是直播的内容就是封装格式如TS，点播叫容器格式。01-.mp4
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
  is '码率类型等级
枚举值，不考虑具体的码率，具体见相关的对应表，枚举值高代表更好的用户体验。
';
comment on column t_v_videoMedia.medianettype
  is '支持网络类型:1-td-cdma；2-gprs/edge 3-WLAN 4-GAN 5-HSPA枚举值，目前只分辨2G,3G';
comment on column t_v_videoMedia.mediamimetype
  is 'mime类型:00-NULL
视频
01-video/3gp
02-video/3gpp
03-video/mp4
04-video/x-ms-wmv
05-video/mpeg
06-video/x-mpeg
音乐
11-audio/mp3
12-audio/x-mp3
13-audio/aac
14-audio/x-aac
15-audio/x-m4a
16-audio/x-m4b
系统根据文件容器和编码类型进行匹配，自动选择。也可从下拉列表框中选择。
音频关于 MIME的类型需要明确。
';
comment on column t_v_videoMedia.mediaresolution
  is '分辩率类型
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
  is '同步最后更新时间';
comment on column t_v_videoMedia.exetime
  is '插入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoMedia
  add constraint pk_tvmedia_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvmedia_vid on t_v_videoMedia (cms_id);
create index idx_tvmedia_pid on t_v_videoMedia (programid);
create index idx_tvmedia_meid on t_v_videoMedia (mediafileid);


-- Create table视频内容二级分类表
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
  is '主键ID';
    comment on column t_v_videosPropertys.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_videosPropertys.cms_id
  is '10位内容唯一短编码，媒资ID
CMS以2开头，MMS以3开头
';
comment on column t_v_videosPropertys.propertykey
  is '内容二级分类名称/内容二级属性名称';
comment on column t_v_videosPropertys.propertyvalue
  is '属性Value值：多个值的拼接时用“|”分割；不能包含</>’”这些特殊字符';
comment on column t_v_videosPropertys.lupdate
  is '同步最后更新时间';
comment on column t_v_videosPropertys.exetime
  is '入库时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videosPropertys
  add constraint pk_tvproper_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvproper_vid on t_v_videosPropertys (cms_id);
create index idx_tvproper_pid on t_v_videosPropertys (programid);


-- Create table视频货架表
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
  is '主键ID';
comment on column t_v_category.categoryid
  is '货架编码ID，默认';
comment on column t_v_category.cname
  is '货架名称';
comment on column t_v_category.cdesc
  is '视频货架描述';
comment on column t_v_category.pic
  is '视频货架图片';
comment on column t_v_category.isshow
  is '是否在门户展示，1，展示；0，不展示';
comment on column t_v_category.parentcid
  is '父货架编码ID,父货架ID为-1表示根货架';
comment on column t_v_category.sortid
  is '排序序号';
comment on column t_v_category.lupdate
  is '最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_category
  add constraint pk_tvcategory_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_tvcategory_cid on t_v_category (categoryid);



-- Create table视频商品表
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
  is '主键ID';
comment on column t_v_reference.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_reference.categoryid
  is '货架编码ID';
comment on column t_v_reference.cms_id
  is '10位内容唯一短编码，媒资ID
CMS以2开头，MMS以3开头,视频ID
';
comment on column t_v_reference.pname
  is '节目名称';
comment on column t_v_reference.sortid
  is '排序号';
comment on column t_v_reference.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_reference
  add constraint pk_tvreference_id primary key (ID);
-- Create/Recreate indexes 
create unique index IDX_TVREFERENCE_PCID on T_V_REFERENCE (PROGRAMID, CATEGORYID);



-- Create table视频业务产品表
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
  is '主键ID';
comment on column t_v_propkg.servid
  is '业务产品ID';
comment on column t_v_propkg.prodname
  is '产品名称';
comment on column t_v_propkg.protype
  is '产品类型：1：和视界  2：和视频 3.内容输出';
comment on column t_v_propkg.propkgtype
  is '产品包属性：1：垂直栏目 2:品牌栏目 3：V+ 4:分站 5：G客G拍 6：本地7：和视界 8：其它';
comment on column t_v_propkg.monthfeecode
  is '包月计费编码';
comment on column t_v_propkg.dotfeecode
  is '按次计费编码';
comment on column t_v_propkg.freefeecode
  is '免费计费编码';
comment on column t_v_propkg.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_propkg
  add constraint pk_tvpropkg_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpropkg_sid on t_v_propkg (servid);


-- Create table 视频计费表
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
  is '主键ID';
comment on column t_v_PRODUCT.feecode
  is '计费编码';
comment on column t_v_PRODUCT.feetype
  is '计费类型：0	包月
1	按次
7	免费
13	包年
15	大包月
';
comment on column t_v_PRODUCT.fee
  is '价格：单位：分';
  comment on column t_v_PRODUCT.productdesc
  is '产品描述';
comment on column t_v_PRODUCT.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_PRODUCT
  add constraint pk_tvproduc_id primary key (ID);


-- Create table 视频直播表
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
  is '主键ID';
comment on column T_V_LIVE.programid
  is 'OMS内容ID，节目ID';
comment on column T_V_LIVE.cmsid
  is 'CMS内容ID';
comment on column T_V_LIVE.playname
  is '节目名称';
comment on column T_V_LIVE.playday
  is '节目单对应日期格式为：YYYYMMDD';
comment on column T_V_LIVE.starttime
  is '节目开始时间格式为：YYYY-MM-DD HH:mm';
comment on column T_V_LIVE.endtime
  is '节目结束时间 格式为：YYYY-MM-DD HH:mm';
comment on column T_V_LIVE.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LIVE
  add constraint PK_TVLIVE_ID primary key (ID)
  using index ;
-- Create/Recreate indexes 
create index IDX_TVLIVE_PID on T_V_LIVE (PROGRAMID);


-- Create table视频热点主题表
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
  is '主键ID';
comment on column t_v_hotcontent.titleid
  is '数字热点主题ID';
comment on column t_v_hotcontent.titlename
  is '热点主题名称';
  comment on column t_v_hotcontent.exestatus
  is 'xml增量处理状态：1已处理 0未处理，2处理失败';
comment on column t_v_hotcontent.pubtime
  is '发布时间 格式如2014-09-19 14:32:19';
comment on column t_v_hotcontent.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_hotcontent
  add constraint pk_tvhotcontent_id primary key (ID);


-- Create table热点主题货架关系表
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
  is '主键ID';
comment on column t_v_hotcatemap.titleid
  is '数字热点主题ID，或一级分类ID';
comment on column t_v_hotcatemap.categoryid
  is '货架编码ID';
  comment on column T_V_HOTCATEMAP.type
  is '分类:1,表示热点主题，2，表示一级分类，';
comment on column t_v_hotcatemap.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_hotcatemap
  add constraint pk_tvhotmap_id primary key (ID);


-- Create table分类频道关系表
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
  is '主键ID';
comment on column t_v_type.categoryid
  is '货架编码ID';
comment on column t_v_type.cateid
  is '一级分类ID';
comment on column t_v_type.catename
  is '一级分类名称';
comment on column t_v_type.subcatename
  is '二级分类名称';
comment on column t_v_type.taggruid
  is '标签组ID';
comment on column t_v_type.taggroup
  is '标签组名称';
comment on column t_v_type.tagname
  is '一级标签名称';
comment on column t_v_type.subtagname
  is '二级标签';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_type
  add constraint pk_tvtype_id primary key (ID);


-- Create table视频促销计费表
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
  is '主键ID';
comment on column t_v_PKGsales.prdpack_id
  is '产品包ID';
comment on column t_v_PKGsales.salesproductid
  is '促销产品ID';
comment on column t_v_PKGsales.salesdiscount
  is '折扣';
comment on column t_v_PKGsales.salescategory
  is '促销类型    
1.预售  2.预订   3.限免
';
comment on column t_v_PKGsales.salesstarttime
  is '促销开始时间
格式如2014-09-19
';
comment on column t_v_PKGsales.salesendtime
  is '促销结束时间
格式如2014-09-19
';
comment on column t_v_PKGsales.lupdate
  is '最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_PKGsales
  add constraint pk_tvpkgsale_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpkgsale_pid on t_v_PKGsales (prdpack_id);

-----------------------
------创建中间表-------------
------------------------
-- Create table视频图片中间表
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
  is '主键ID';
comment on column t_v_videoPic_mid.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_videoPic_mid.PIC_00_TV
  is '强版权：宽：240 高：320，XX_TV_CONTENT.jpg.弱版权：图标源图00-ANY';
comment on column t_v_videoPic_mid.PIC_01_V
  is '强版权：宽：132 高：176，XX_V_CONTENT. Jpg.弱版权：01-63*39';
comment on column t_v_videoPic_mid.pic_02_hsj1080h
  is '强版权：宽：508 高：330，XX_HSJ1080H. jpg.弱版权：02-28*18';
comment on column t_v_videoPic_mid.pic_03_hsj1080v
  is '强版权：宽：330 高：450，XX_HSJ1080V. jpg.弱版权：03-84*59';
comment on column t_v_videoPic_mid.pic_04_hsj720h
  is '强版权：宽：336 高：220，XX_HSJ720H. jpg.弱版权：04-41*26';
comment on column t_v_videoPic_mid.pic_05_hsj720v
  is '强版权：宽：220 高：330，XX_HSJ720V. jpg.弱版权：05-100*76';
  
comment on column t_v_videoPic_mid.PIC_06
  is '弱版权：06-43*36';
comment on column t_v_videoPic_mid.PIC_07
  is '弱版权：07-168*118';
comment on column t_v_videoPic_mid.PIC_08
  is '弱版权：08-82*52';
comment on column t_v_videoPic_mid.PIC_99
  is '弱版权：00-ANY';
comment on column t_v_videoPic_mid.lupdate
  is '最后更新时间';
comment on column t_v_videoPic_mid.exetime
  is '入库时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoPic_mid
  add constraint pk_tvpicmid_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpicmid_pid on t_v_videoPic_mid (programid);




-- Create table视频标签中间表
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
  is '主键ID';
comment on column T_V_LABLES_mid.programid
  is 'OMS内容ID，节目ID';
  comment on column T_V_LABLES_mid.CMSID
  is 'CMS内容ID';
comment on column T_V_LABLES_mid.labelid
  is '标签ID';

comment on column T_V_LABLES_mid.labelname
  is '单个标签的名称';
comment on column T_V_LABLES_mid.labelzone
  is '标签组名称,来配置表标签分类表中的数据';
comment on column T_V_LABLES_mid.lupdate
  is '同步最后更新时间';
comment on column T_V_LABLES_mid.exetime
  is '插入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_LABLES_mid
  add constraint PK_TVLABmid_ID primary key (ID)
  using index ;
-- Create/Recreate indexes 
create index IDX_TVLABmid_LID on T_V_LABLES_mid (LABELID);
create index IDX_TVLABmid_PID on T_V_LABLES_mid (PROGRAMID);

-- Create table视频媒体中间表
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
  is '主键ID';
  comment on column t_v_videoMedia_mid.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_videoMedia_mid.cms_id
  is '10位内容唯一短编码，媒资ID
CMS以2开头，MMS以3开头,视频ID
';
comment on column t_v_videoMedia_mid.mediafileid
  is '媒体ID：每个文件上传的时候，系统生成的ID
16位数字，顺序编号，2年内不重复
';
comment on column t_v_videoMedia_mid.mediafilename
  is '单个媒体文件名称';
comment on column t_v_videoMedia_mid.sourcefilename
  is '转码源文件名该字段可以为空，迁移时并没有转码前源文件名。另外，由于新的规范中需要转码，这里需要记录生成的文件是从哪个源文件名中进行转换得到，因此需要记录下来，命名原则为：源文件名+用户自定义识别+序列';
comment on column t_v_videoMedia_mid.visitpath
  is '访问路径，用户播放的路径，原播放下载地址';
comment on column t_v_videoMedia_mid.mediafilepath
  is '媒体文件存放路径';
comment on column t_v_videoMedia_mid.mediafilepreviewpath
  is '媒体文件预览路径';
comment on column t_v_videoMedia_mid.mediafileaction
  is '枚举值：
0-新增文件
1-修改文件
2-删除文件
3-文件不变
说明：
当新发内容时，所有文件的该参数填写0；当修改/变更内容时，该参数根据实际填写，0表示新增媒体文件，1表示替换媒体文件，2表示删除媒体文件，3表示媒体文件保持不变
';
comment on column t_v_videoMedia_mid.mediasize
  is '单个媒体文件大小系统可根据所选文件的命名规则自动进行文件大小的匹配，若匹配成功，则不能修改；若匹配不成功，则需要从下拉列表框中选择
单位:字节
';
comment on column t_v_videoMedia_mid.duration
  is '该文件的时长由于内容存在多个源文件，转码后的文件时长与源文件的时长相同，单位秒';
comment on column t_v_videoMedia_mid.mediatype
  is '单个媒体文件类型： 1-VOD 点播 2-Live Broadcast 直播 3-download下载  4-转码源 5-模拟直播 6－内容集（预留值） 7-精简编码 8-精简直播 9-精简模拟直播 10－精简点播 11－精简下载';
comment on column t_v_videoMedia_mid.mediausagecode
  is '类型编号等级，编码分类代码，枚举值和码率无关，具体见各种媒体类型文件相关的对应表。99代表转码前源MPEG2的文件。1,2,3,4,..,96,97,98,99
具体参考3.4.1视频
代表不同的编码分类类型，点播、直播、下载、模拟直播具备完全独立的编码分类
';
comment on column t_v_videoMedia_mid.mediacodeformat
  is '单个媒体文件编码格式：
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
  is '容器或封装格式:枚举值, 音频字幕将遵循LRC标准,Lrc格式
如果是直播的内容就是封装格式如TS，点播叫容器格式。01-.mp4
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
  is '码率类型等级
枚举值，不考虑具体的码率，具体见相关的对应表，枚举值高代表更好的用户体验。
';
comment on column t_v_videoMedia_mid.medianettype
  is '支持网络类型:1-td-cdma；2-gprs/edge 3-WLAN 4-GAN 5-HSPA枚举值，目前只分辨2G,3G';
comment on column t_v_videoMedia_mid.mediamimetype
  is 'mime类型:00-NULL
视频
01-video/3gp
02-video/3gpp
03-video/mp4
04-video/x-ms-wmv
05-video/mpeg
06-video/x-mpeg
音乐
11-audio/mp3
12-audio/x-mp3
13-audio/aac
14-audio/x-aac
15-audio/x-m4a
16-audio/x-m4b
系统根据文件容器和编码类型进行匹配，自动选择。也可从下拉列表框中选择。
音频关于 MIME的类型需要明确。
';
comment on column t_v_videoMedia_mid.mediaresolution
  is '分辩率类型
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
  is '同步最后更新时间';
comment on column t_v_videoMedia_mid.exetime
  is '插入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videoMedia_mid
  add constraint pk_tvmediamid_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvmediamid_vid on t_v_videoMedia_mid (cms_id);
create index idx_tvmediamid_pid on t_v_videoMedia_mid (programid);
create index idx_tvmediamid_meid on t_v_videoMedia_mid (mediafileid);


-- Create table视频内容二级分类中间表
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
  is '主键ID';
    comment on column t_v_videosPropertys_mid.programid
  is 'OMS内容ID，节目ID';
comment on column t_v_videosPropertys_mid.cms_id
  is '10位内容唯一短编码，媒资ID
CMS以2开头，MMS以3开头
';
comment on column t_v_videosPropertys_mid.propertykey
  is '内容二级分类名称/内容二级属性名称';
comment on column t_v_videosPropertys_mid.propertyvalue
  is '属性Value值：多个值的拼接时用“|”分割；不能包含</>’”这些特殊字符';
comment on column t_v_videosPropertys_mid.lupdate
  is '同步最后更新时间';
comment on column t_v_videosPropertys_mid.exetime
  is '入库时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_videosPropertys_mid
  add constraint pk_tvpropermid_id primary key (ID);
-- Create/Recreate indexes 
create index idx_tvpropermid_vid on t_v_videosPropertys_mid (cms_id);
create index idx_tvpropermid_pid on t_v_videosPropertys_mid (programid);

-- Create table视频增量时间表
create table t_v_lasttime
(
  id      varchar2(32),
  lupdate date
)
;
-- Add comments to the columns 
comment on column t_v_lasttime.id
  is '自增主键ID';
comment on column t_v_lasttime.lupdate
  is '最后执行时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_v_lasttime
  add constraint pk_tvlasttime_id primary key (ID);
  

----------创建表 结束---------


----------创建序列 开始---------
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


-- Create 视频货架编码
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

----------创建序列 结束---------
