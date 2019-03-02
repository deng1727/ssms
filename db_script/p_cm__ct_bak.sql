alter table cm_ct_appgame_tra add  SOFTWAREWORKDOCS      VARCHAR2(20);
alter table cm_ct_appgame_tra add   SOFTWAREWORKNO        VARCHAR2(60);
alter table cm_ct_appgame_tra add   VERSIONREPLYDOCS      VARCHAR2(20);
alter table cm_ct_appgame_tra add   VERSIONREPLYNO        VARCHAR2(60);
alter table cm_ct_appgame_tra add   FILINGNOTICENO        VARCHAR2(60);
alter table cm_ct_appgame_tra add   FILINGNOTICEURL       VARCHAR2(200);
alter table cm_ct_appgame_tra add   FILINGNOTICEDOCS      VARCHAR2(20);
  
comment on column cm_ct_appgame_tra.SOFTWAREWORKDOCS
  is '软著扫描件';
comment on column cm_ct_appgame_tra.SOFTWAREWORKNO
  is '软著登记号';
comment on column cm_ct_appgame_tra.VERSIONREPLYDOCS
  is '版权批复文件扫描件';
comment on column cm_ct_appgame_tra.VERSIONREPLYNO
  is '游戏出版号(ISBN)';
comment on column cm_ct_appgame_tra.FILINGNOTICENO
  is '备案通知文件号';
comment on column cm_ct_appgame_tra.FILINGNOTICEURL
  is '文化部游戏备案电子签链接';
comment on column cm_ct_appgame_tra.FILINGNOTICEDOCS
  is '游戏备案截图';
  
alter table cm_ct_appgame add  SOFTWAREWORKDOCS      VARCHAR2(20);
alter table cm_ct_appgame add   SOFTWAREWORKNO        VARCHAR2(60);
alter table cm_ct_appgame add   VERSIONREPLYDOCS      VARCHAR2(20);
alter table cm_ct_appgame add   VERSIONREPLYNO        VARCHAR2(60);
alter table cm_ct_appgame add   FILINGNOTICENO        VARCHAR2(60);
alter table cm_ct_appgame add   FILINGNOTICEURL       VARCHAR2(200);
alter table cm_ct_appgame add   FILINGNOTICEDOCS      VARCHAR2(20);
  
comment on column cm_ct_appgame.SOFTWAREWORKDOCS
  is '软著扫描件';
comment on column cm_ct_appgame.SOFTWAREWORKNO
  is '软著登记号';
comment on column cm_ct_appgame.VERSIONREPLYDOCS
  is '版权批复文件扫描件';
comment on column cm_ct_appgame.VERSIONREPLYNO
  is '游戏出版号(ISBN)';
comment on column cm_ct_appgame.FILINGNOTICENO
  is '备案通知文件号';
comment on column cm_ct_appgame.FILINGNOTICEURL
  is '文化部游戏备案电子签链接';
comment on column cm_ct_appgame.FILINGNOTICEDOCS
  is '游戏备案截图';
  
  
  
 



 alter table CM_CT_APPSoftware_TRA add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPSoftware_TRA add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPSoftware_TRA.SOFTWAREWORKDOCS
  is '软著登记号';
  comment on column CM_CT_APPSoftware_TRA.SOFTWAREWORKNO
  is '软著扫描件';
  
  
  
  alter table CM_CT_APPSoftware add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPSoftware add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPSoftware.SOFTWAREWORKDOCS
  is '软著登记号';
  comment on column CM_CT_APPSoftware.SOFTWAREWORKNO
  is '软著扫描件';
  
  
  
  

 alter table CM_CT_APPTheme_TRA add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPTheme_TRA add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPTheme_TRA.SOFTWAREWORKDOCS
  is '软著登记号';
  comment on column CM_CT_APPTheme_TRA.SOFTWAREWORKNO
  is '软著扫描件';
  
   alter table CM_CT_APPTheme add   SOFTWAREWORKDOCS      VARCHAR2(20);
 alter table CM_CT_APPTheme add   SOFTWAREWORKNO        VARCHAR2(60);
  
  comment on column CM_CT_APPTheme.SOFTWAREWORKDOCS
  is '软著登记号';
  comment on column CM_CT_APPTheme.SOFTWAREWORKNO
  is '软著扫描件';


  
  