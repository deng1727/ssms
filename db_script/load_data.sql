--初始化管理员
delete from t_user;
insert into t_user(userid,name,password,state,BIRTHDAY,CERTTYPE,CERTID,COMPANYNAME) values('ponaadmin','ponaadmin','96E79218965EB72C92A549DD5A330112',10,'1111-11-11',10,'111111111111111','aspire');
delete from t_role;
insert into t_role(roleid,name,descs) values(1,'超级管理员','系统的超级管理员');
delete from t_userrole;
insert into t_userrole(userid,roleid) values('ponaadmin',1);

--资源管理的初始化
delete from t_r_base;
delete from t_r_category;
delete from t_r_reference;
delete from t_goods_his;
insert into t_r_base(id,parentid,path,type) values('100','100','{100}','nt:base');
insert into t_r_base(id,parentid,path,type) values('701','100','{100}.{701}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,changedate) values('701','货架分类','货架根分类',0,1,1,0,'W,O,P,A',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('702','100','{100}.{702}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,changedate) values('702','内容存储根分类','内容存储根分类',0,1,1,0,'W,O,P',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1006','701','{100}.{701}.{1006}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1006','终端彩铃','终端门户彩铃频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1007','701','{100}.{701}.{1007}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1007','WWW软件','WWW门户软件频道',0,1,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1008','701','{100}.{701}.{1008}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1008','WWW主题','WWW门户主题频道',0,1,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1009','701','{100}.{701}.{1009}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1009','WWW游戏','WWW门户游戏频道',0,1,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1010','701','{100}.{701}.{1010}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1010','终端资讯','终端门户资讯频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1011','701','{100}.{701}.{1011}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1011','终端应用','终端门户应用频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1012','701','{100}.{701}.{1012}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1012','终端音乐','终端音乐应用频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1013','701','{100}.{701}.{1013}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1013','机型推荐','机型推荐',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1014','701','{100}.{701}.{1014}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1014','视频频道','视频频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1015','701','{100}.{701}.{1015}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1015','图书频道','图书频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


--初始化应用频道子频道
insert into t_r_base(id,parentid,path,type) values('2001','1011','{100}.{701}.{1011}.{2001}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2001','应用软件','终端应用频道子频道－应用软件',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('2002','1011','{100}.{701}.{1011}.{2002}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2002','手机主题','终端应用频道子频道－手机主题',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('2003','1011','{100}.{701}.{1011}.{2003}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2003','手机游戏','终端应用频道子频道－手机游戏',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('2004','1011','{100}.{701}.{1011}.{2004}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2004','移动专区','终端应用频道子频道－移动专区',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--初始化音乐频道子频道
insert into t_r_base(id,parentid,path,type) values('3001','1012','{100}.{701}.{1012}.{3001}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3001','风格','终端音乐频道子频道－风格',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3002','1012','{100}.{701}.{1012}.{3002}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3002','语言','终端音乐频道子频道－语言',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3003','1012','{100}.{701}.{1012}.{3003}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3003','地区','终端音乐频道子频道－地区',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
--初始化音乐风格频道的子频道
insert into t_r_base(id,parentid,path,type) values('3101','3001','{100}.{701}.{1012}.{3001}.{3101}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3101','流行','终端音乐三级频道－流行',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3102','3001','{100}.{701}.{1012}.{3001}.{3102}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3102','民谣','终端音乐三级频道－民谣',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3103','3001','{100}.{701}.{1012}.{3001}.{3103}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3103','摇滚','终端音乐三级频道－摇滚',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3104','3001','{100}.{701}.{1012}.{3001}.{3104}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3104','布鲁斯','终端音风格乐频道子频道－布鲁斯',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3105','3001','{100}.{701}.{1012}.{3001}.{3105}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3105','电子','终端音乐三级频道－电子',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3106','3001','{100}.{701}.{1012}.{3001}.{3106}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3106','中国传统艺术','终端音乐三级频道－中国传统艺术',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3107','3001','{100}.{701}.{1012}.{3001}.{3107}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3107','说唱','终端音乐三级频道－说唱',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3108','3001','{100}.{701}.{1012}.{3001}.{3108}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3108','世界音乐','终端音乐三级频道－世界音乐',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3109','3001','{100}.{701}.{1012}.{3001}.{3109}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3109','舞曲','终端音乐三级频道－舞曲',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3110','3001','{100}.{701}.{1012}.{3001}.{3110}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3110','民歌','终端音乐三级频道－民歌',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3111','3001','{100}.{701}.{1012}.{3001}.{3111}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3111','爵士','终端音乐三级频道－爵士',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3112','3001','{100}.{701}.{1012}.{3001}.{3112}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3112','古典','终端音乐三级频道－古典',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3113','3001','{100}.{701}.{1012}.{3001}.{3113}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3113','其他','终端音乐三级频道－其他',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
--初始化音乐语言频道的子频道
insert into t_r_base(id,parentid,path,type) values('3201','3002','{100}.{701}.{1012}.{3002}.{3201}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3201','国语','终端音乐三级频道－国语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3202','3002','{100}.{701}.{1012}.{3002}.{3202}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3202','粤语','终端音乐三级频道－粤语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3203','3002','{100}.{701}.{1012}.{3002}.{3203}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3203','英语','终端音乐三级频道－英语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3204','3002','{100}.{701}.{1012}.{3002}.{3204}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3204','意大利语','终端音乐三级频道－意大利语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3205','3002','{100}.{701}.{1012}.{3002}.{3205}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3205','西班牙语','终端音乐三级频道－西班牙语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3206','3002','{100}.{701}.{1012}.{3002}.{3206}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3206','日语','终端音乐三级频道－日语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3207','3002','{100}.{701}.{1012}.{3002}.{3207}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3207','器乐','终端音乐三级频道－器乐',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3208','3002','{100}.{701}.{1012}.{3002}.{3208}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3208','其他语种','终端音乐三级频道－其他语种',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3209','3002','{100}.{701}.{1012}.{3002}.{3209}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3209','其他方言','终端音乐三级频道－其他方言',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3210','3002','{100}.{701}.{1012}.{3002}.{3210}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3210','闽南语','终端音乐三级频道－闽南语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3211','3002','{100}.{701}.{1012}.{3002}.{3211}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3211','韩语','终端音乐三级频道－韩语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3212','3002','{100}.{701}.{1012}.{3002}.{3212}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3212','法语','终端音乐三级频道－法语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3213','3002','{100}.{701}.{1012}.{3002}.{3213}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3213','俄语','终端音乐三级频道－俄语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3214','3002','{100}.{701}.{1012}.{3002}.{3214}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3214','德语','终端音乐三级频道－德语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3215','3002','{100}.{701}.{1012}.{3002}.{3215}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3215','国语','终端音乐三级频道－国语',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3216','3002','{100}.{701}.{1012}.{3002}.{3216}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3216','其他','终端音乐三级频道－其他',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
--初始化音乐地区频道的子频道
insert into t_r_base(id,parentid,path,type) values('3301','3003','{100}.{701}.{1012}.{3003}.{3301}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3301','内地男歌手','终端音乐三级频道－内地男歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3302','3003','{100}.{701}.{1012}.{3003}.{3302}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3302','内地女歌手','终端音乐三级频道－内地女歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3303','3003','{100}.{701}.{1012}.{3003}.{3303}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3303','内地组合','终端音乐三级频道－内地组合',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3304','3003','{100}.{701}.{1012}.{3003}.{3304}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3304','港台男歌手','终端音乐三级频道－港台男歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3305','3003','{100}.{701}.{1012}.{3003}.{3305}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3305','港台女歌手','终端音乐三级频道－港台女歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3306','3003','{100}.{701}.{1012}.{3003}.{3306}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3306','港台组合','终端音乐三级频道－港台组合',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3307','3003','{100}.{701}.{1012}.{3003}.{3307}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3307','欧美男歌手','终端音乐三级频道－欧美男歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3308','3003','{100}.{701}.{1012}.{3003}.{3308}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3308','欧美女歌手','终端音乐三级频道－欧美女歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3309','3003','{100}.{701}.{1012}.{3003}.{3309}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3309','欧美组合','终端音乐三级频道－欧美组合',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3310','3003','{100}.{701}.{1012}.{3003}.{3310}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3310','日韩男歌手','终端音乐三级频道－日韩男歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3311','3003','{100}.{701}.{1012}.{3003}.{3311}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3311','日韩女歌手','终端音乐三级频道－日韩女歌手',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3312','3003','{100}.{701}.{1012}.{3003}.{3312}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3312','日韩组合','终端音乐三级频道－日韩组合',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3313','3003','{100}.{701}.{1012}.{3003}.{3313}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3313','其他','终端音乐三级频道－其他',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--彩铃的特殊分类
insert into t_r_base(id,parentid,path,type) values('2009','1006','{100}.{701}.{1006}.{2009}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2009','其他分类','无法归类彩铃数据的专用分类',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


--新增视频分类，和广东图书分类
insert into t_r_base(id,parentid,path,type) values('1014','701','{100}.{701}.{1014}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1014','基地视频频道','基地视频频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


insert into t_r_base(id,parentid,path,type) values('1015','701','{100}.{701}.{1015}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1015','广东图书频道','广东图书频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1016','701','{100}.{701}.{1016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1016','基地音乐频道','基地音乐频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2016','1016','{100}.{701}.{1016}.{2016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2016','基地音乐排行榜','基地音乐排行榜',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1016') where c.id='2016';

---新增基地图书
insert into t_r_base(id,parentid,path,type) values('1017','701','{100}.{701}.{1017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1017','基地读书频道','基地读书频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2017','1017','{100}.{701}.{1017}.{2017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2017','读书分类','基地读书分类',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2017';

insert into t_r_base(id,parentid,path,type) values('2018','1017','{100}.{701}.{1017}.{2018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2018','基地读书排行榜','基地读书排行榜',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2018';

insert into t_r_base(id,parentid,path,type) values('2019','1017','{100}.{701}.{1017}.{2019}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2019','读书专区','基地读书专区',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2019';

--新增动漫分类
insert into t_r_base(id,parentid,path,type) values('1018','701','{100}.{701}.{1018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1018','动漫频道','动漫频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--WAP门户分类
insert into t_r_base(id,parentid,path,type) values('1020','701','{100}.{701}.{1020}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1020','WAP门户货架','WAP门户跟货架',0,1,1,0,'A',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--从t_r_category和t_r_base的关系中查找到对应每个货架的父货架编码，然后插入到t_r_category表中
update t_r_category t3 set t3.PARENTCATEGORYID = (select CATEGORYID from t_r_category where id=(select t2.parentid from t_r_category t1,t_r_base t2 where t2.id=t1.id and t1.id=t3.id));

---基地游戏分类和MM分类的对应关系
delete from t_game_cate_mapping;
insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('MMORPG', '其他');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('竞速', '体育竞技');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('动作冒险', '动作格斗');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('文字冒险', '冒险模拟');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('音乐', '休闲趣味');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('动作', '动作格斗');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('棋牌', '棋牌益智');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('策略', '策略回合');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('体育', '体育竞技');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('角色扮演', '角色扮演');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('射击', '射击飞行');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('益智', '棋牌益智');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('动作益智', '动作格斗');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('冒险', '冒险模拟');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('格斗', '动作格斗');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('养成', '策略回合');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('综合', '其他');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('对战类', '动作格斗');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('飞机类', '射击飞行');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('格斗类', '动作格斗');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('角色类', '角色扮演');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('棋牌类', '棋牌益智');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('体育类', '体育竞技');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('网游类', '其他');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('网游专区', '其他');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('养成类', '策略回合');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('益智类', '棋牌益智');
--内容运营数据初始化
delete from t_report_import_date;
insert into t_report_import_date  values(4,'20000101','内容运营日数据导入');
insert into t_report_import_date  values(5,'20000101','内容运营周数据导入');
insert into t_report_import_date  values(6,'20000101','内容运营月数据导入');

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





--版本信息
delete from dbversion;
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_SSMS','M-Market平台货架管理系统1.0版本');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_MOPAS','M-Market平台终端门户管理系统1.0版本');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_WWWPAS','M-Market平台WWW门户管理系统1.0版本');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_MOPPS','M-Market平台终端门户1.0版本');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_WWWPPS','M-Market平台WWW门户1.0版本');

commit;