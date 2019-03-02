--回滚人工干预数据
drop table T_INTERVENOR;
drop table T_INTERVENOR_CATEGORY_MAP;
drop table T_INTERVENOR_GCONTENT_MAP;
drop  sequence SEQ_INTERVENOR_ID;

delete from t_right where rightid='2_1401_INTERVENOR' or parentid='2_1401_INTERVENOR';
delete from T_ROLERIGHT where RIGHTID='2_1401_INTERVENOR';

drop table t_export_toplist;
delete DBVERSION where PATCHVERSION = 'MM1.0.2.032_SSMS' and LASTDBVERSION = 'MM1.0.0.125_SSMS';

--还原二级分类过度方案。
create table t_new_old_cate_mapping
(
  newname   varchar2(50) not null,
  oldname   varchar2(50) not null,
  oldcateid varchar2(20) not null
)
;
-- Add comments to the columns 
comment on column t_new_old_cate_mapping.newname
  is '新分类名称';
comment on column t_new_old_cate_mapping.oldname
  is '旧分类名称';
comment on column t_new_old_cate_mapping.oldcateid
  is '旧分类id';

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('系统', '系统工具', '1');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('无线', '系统工具', '1');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('安全', '系统工具', '1');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('资讯', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('金融', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('理财', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('美化', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('生活', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('图像', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('词典', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('书籍', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('旅行', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('地图', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('医疗', '实用软件', '2');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('音乐', '多媒体软件', '3');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('视频', '多媒体软件', '3');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('通讯', '通信辅助', '4');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('聊天', '通信辅助', '4');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('输入', '通信辅助', '4');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('商务', '网络软件', '5');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('娱乐', '网络软件', '5');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('查询', '网络软件', '5');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('角色', '角色扮演', '6');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('动作', '动作格斗', '7');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('格斗', '动作格斗', '7');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('体育', '体育竞技', '8');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('赛车', '体育竞技', '8');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('射击', '射击飞行', '9');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('飞行', '射击飞行', '9');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('策略', '策略回合', '10');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('冒险', '冒险模拟', '11');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('经营', '冒险模拟', '11');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('休闲', '休闲趣味', '12');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('养成', '休闲趣味', '12');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('棋牌', '棋牌益智', '13');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('益智', '棋牌益智', '13');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('其他', '其他', '14');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('爱情', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('搞笑', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('星座', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('色彩', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('中国风', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('恐怖', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('涂鸦', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('创意', '酷图', '15');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('人物', '人物', '16');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('插画', '人物', '16');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('影视', '影视', '17');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('节日', '节日', '18');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('夜光', '科技', '19');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('动态', '科技', '19');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('军事', '科技', '19');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('游戏', '游戏', '20');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('体育', '体育', '21');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('动物', '动物', '22');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('卡通', '卡通', '23');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('明星', '明星', '24');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('汽车', '汽车', '25');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('风景', '风景', '26');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('建筑', '风景', '26');

insert into t_new_old_cate_mapping (NEWNAME, OLDNAME, OLDCATEID)
values ('植物', '风景', '26');
commit;