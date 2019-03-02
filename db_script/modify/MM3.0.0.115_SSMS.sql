alter table T_GAME_CONTENT add LOGO6 VARCHAR2(256);
comment on column T_GAME_CONTENT.LOGO6
  is '游戏基地新提供130*130的高清logo图标';
  create or replace synonym v_event_college 
  for v_event_college @DL_PPMS_DEVICE;