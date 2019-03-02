
----标签信息接入
---同一个数据库实例 创建同义词 
create synonym ppms_v_cm_content_tag       for &ppms.v_cm_content_tag        ;
create synonym ppms_v_om_tag       for &ppms.V_om_tag        ;

--- 不同的数据库实例 使用原来创建的dblink：DL_PPMS_DEVICE创建同义词，根据现网配置写的，
--- 测试环境需要替换DL_PPMS_DEVICE
create or replace synonym ppms_v_cm_content_tag for v_cm_content_tag@DL_PPMS_DEVICE;
create or replace synonym ppms_v_om_tag for v_om_tag@DL_PPMS_DEVICE;

----  AP应用刷榜榜单前60商品监控预警，提供给报表使用 根据现网榜单货架ID配置，
----- 测试环境需要修改相应货架ID
----  终端门户
create or replace view v_mo_top60reference as
select q.day,q.goodsid,q.contentid,q.sortid,q.subname, y.name
    from t_r_category y,

         (select to_char(sysdate, 'yyyyMMdd') day,
                 r.goodsid,
                 g.contentid,
                 r.sortid,
                 c.name subname,
                 c.parentcategoryid
            from t_r_reference r, t_r_Category c, t_r_gcontent g
           where r.categoryid = c.categoryid
             and r.refnodeid = g.id
             and c.id in ('1257460', '1257459', '1257457', '1257458',
        '1257462', '1257463', '1257464', '1257466',
                          '1257467', '1257468', '1257469', '1257471')
             and r.sortid > 940) q
   where y.categoryid = q.parentcategoryid;
-----WWW门户   
create or replace view v_www_top60reference as
select q.day,q.goodsid,q.contentid,q.sortid,q.subname, y.name
    from t_r_category y,

         (select to_char(sysdate, 'yyyyMMdd') day,
                 r.goodsid,
                 g.contentid,
                 r.sortid,
                 c.name subname,
                 c.parentcategoryid
            from t_r_reference r, t_r_Category c, t_r_gcontent g
           where r.categoryid = c.categoryid
             and r.refnodeid = g.id
             and c.id in ('1810803', '1810805', '1815681', '1814487')
             and r.sortid > 940) q
   where y.categoryid = q.parentcategoryid;

------轮换率重复率数据导出优化 添加索引,现网检查是否已经存在该索引，存在不执行

create index INDEX_CATEGORYID on R_CHARTS_TURNHIS (CATEGORYID);
create index INDEX_PHDATE on R_CHARTS_TURNHIS (PHDATE);
create index INDEX_ROWLIST on R_CHARTS_TURNHIS (ROWLIST);

comment on column T_CATERULE_COND.CONDTYPE
  is '条件类型 10：从产品库获取自有业务（软件，游戏，主题）不包含基地游戏；11：从产品库中取基地游戏业务；12：从产品库获取非自有业务；1：从精品库获取。';



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


--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.120_SSMS','MM1.0.0.123_SSMS');
commit;