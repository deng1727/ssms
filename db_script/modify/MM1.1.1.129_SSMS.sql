-- Add/modify columns 
alter table T_CATERULE_COND modify COUNT default -1;

-- Add/modify columns 
alter table T_VO_CATEGORY add updateTime date default sysdate;
-- Add comments to the columns 
comment on column T_VO_CATEGORY.updateTime
  is '最后更新时间';


-- Add/modify columns 
alter table T_VO_CATEGORY_TRA add updateTime date default sysdate;
-- Add comments to the columns 
comment on column T_VO_CATEGORY_TRA.updateTime
  is '最后更新时间';


-- Add/modify columns 
alter table T_GAME_TW_NEW add UPDATETIME date default sysdate;
-- Add comments to the columns 
comment on column T_GAME_TW_NEW.UPDATETIME
  is '最后更新时间';


-- Add/modify columns 
alter table T_RB_AUTHOR_NEW add LUPDATE date default sysdate;
-- Add comments to the columns 
comment on column T_RB_AUTHOR_NEW.LUPDATE
  is '最后更新时间';

-- Add/modify columns 
alter table T_RB_REFERENCE_NEW add LUPDATE date default sysdate;
-- Add comments to the columns 
comment on column T_RB_REFERENCE_NEW.LUPDATE
  is '最后更新时间';


--- 备份内容表
create table t_r_gcontent_dk1228 as select * from t_r_gcontent;

--- 备份货架表
create table t_r_category_dk1228 as select * from t_r_category;

--- 备份扩展资源表
create table t_key_resource_dk1228 as select * from t_key_resource;

-----CDN更新应用图片URL域名

update t_r_gcontent t
   set t.wwwpropapicture1      = replace(t.wwwpropapicture1,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.wwwpropapicture2      = replace(t.wwwpropapicture2,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.wwwpropapicture3      = replace(t.wwwpropapicture3,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.CLIENTPREVIEWPICTURE1 = replace(t.CLIENTPREVIEWPICTURE1,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.CLIENTPREVIEWPICTURE2 = replace(t.CLIENTPREVIEWPICTURE2,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.CLIENTPREVIEWPICTURE3 = replace(t.CLIENTPREVIEWPICTURE3,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.CLIENTPREVIEWPICTURE4 = replace(t.CLIENTPREVIEWPICTURE4,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.LOGO1                 = replace(t.LOGO1,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.LOGO2                 = replace(t.LOGO2,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.LOGO3                 = replace(t.LOGO3,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.LOGO4                 = replace(t.LOGO4,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.LOGO5                 = replace(t.LOGO5,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.PICTURE1              = replace(t.PICTURE1,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.PICTURE2              = replace(t.PICTURE2,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.PICTURE3              = replace(t.PICTURE3,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.PICTURE4              = replace(t.PICTURE4,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),
       t.PICTURE5              = replace(t.PICTURE5,
                                         'http://ota.mmarket.com:38080',
                                         'http://u5.mm-img.com:80'),    
       t.PICTURE6 = replace(t.PICTURE6,
                            'http://ota.mmarket.com:38080',
                            'http://u5.mm-img.com:80'),
       t.PICTURE7 = replace(t.PICTURE7,
                            'http://ota.mmarket.com:38080',
                            'http://u5.mm-img.com:80'),
       t.PICTURE8 = replace(t.PICTURE8,
                            'http://ota.mmarket.com:38080',
                            'http://u5.mm-img.com:80') 
;

-----CDN更新货架图片URL域名
update t_r_category c set c.picurl = replace(c.picurl,'http://ota.mmarket.com:38080', 'http://u5.mm-img.com:80');

-----CDN更新扩展资源图片URL域名
update t_key_resource c set c.value = replace(c.value,'http://ota.mmarket.com:38080', 'http://u5.mm-img.com:80');




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.125_SSMS','MM1.1.1.129_SSMS');


commit;