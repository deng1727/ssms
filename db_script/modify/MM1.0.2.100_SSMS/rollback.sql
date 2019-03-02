drop table t_r_reference;
drop table T_R_CATEGORY;
drop table t_r_base;
drop table t_sync_tactic;

drop sequence SEQ_SYNC_TACTIC_ID;

rename t_r_reference_bak to t_r_reference;

rename T_R_CATEGORY_bak to T_R_CATEGORY;

rename t_r_base_bak to t_r_base;

rename t_sync_tactic_bak to t_sync_tactic;

rename SEQ_SYNC_TACTIC_ID_bak to SEQ_SYNC_TACTIC_ID;

--»Ö¸´ÊÚÈ¨
grant select on T_R_REFERENCE to MM_DLS;
grant select on T_R_REFERENCE to NEWPAS;
grant select on T_R_REFERENCE to PORTALMO;
grant select on T_R_REFERENCE to PORTALWWW;

grant select on T_R_CATEGORY to MM_DLS;
grant select on T_R_CATEGORY to NEWPAS;
grant select on T_R_CATEGORY to PORTALMO;
grant select on T_R_CATEGORY to PORTALWWW;


grant select on T_R_BASE to MM_DLS;
grant select on T_R_BASE to NEWPAS;
grant select on T_R_BASE to PORTALMO;
grant select on T_R_BASE to PORTALWWW;