drop table t_gametype;
drop table t_game_content;
drop table t_game_service_new;
drop table t_game_pkg;
drop table t_game_pkg_ref;
drop table t_game_tw_new;
drop table t_gamestop;

delete DBVERSION where PATCHVERSION = 'MM1.1.1.095_SSMS' and LASTDBVERSION = 'MM1.1.1.089_SSMS';
commit;