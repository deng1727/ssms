alter table T_GAME_CONTENT add LOGO6 VARCHAR2(256);
comment on column T_GAME_CONTENT.LOGO6
  is '��Ϸ�������ṩ130*130�ĸ���logoͼ��';
  create or replace synonym v_event_college 
  for v_event_college @DL_PPMS_DEVICE;