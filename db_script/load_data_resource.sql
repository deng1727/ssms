--��Դ��
delete from t_resource;

--ϵͳ������Ϣ
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_SYSTEM','����ʧ�ܣ�����ϵ����Ա��','�ڲ�����ʱ���ͳһ��ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('SYSTEM_COMMON_ERROR','����ʧ�ܣ�����ϵ����Ա��','ϵͳ��������ַ�ҵ����������ݿ�����ʧ�ܡ��������ӶϿ����洢�ռ䲻���ȴ���');
insert into t_resource(resourceKey,resourceValue,remard) values('SYSTEM_DUTY_DATA_ERROR','���ݷ����仯������ʧ�ܣ����飡','�����ݴ���');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_NEED_INPUT','{0}��������','���б�����û������ʱ����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_LENGTH_ERROR1','{0}���Ȳ��ܴ���{1}���ַ�','�����ֶγ��ȴ���ʱ����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_LENGTH_ERROR2','{0}���Ȳ���С��{1}���ַ�','�����ֶγ��ȴ���ʱ����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_LENGTH_ERROR3','{0}���ȱ�����{1}��{2}���ַ�֮��','�����ֶγ��ȴ���ʱ����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELDS_NEED_INPUT','{0}������һ�����롣','���ж���ֶα��벢��ֻ����һ����������ʱ����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('PUBLIC_DELETE_ALERT','��ȷ��Ҫ����ɾ��������','ͨ��ɾ����ʾ��');
insert into t_resource(resourceKey,resourceValue,remard) values('PUBLIC_REMOVE_ALERT','��ȷ��Ҫ�����Ƴ�������','ͨ���Ƴ���ʾ��');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_INTEGER_CHECK','{0}����Ϊ������','�������ͼ�����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_DOUBLE_CHECK','{0}����Ϊ��������','���������ͼ�����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_DATETIME_CHECK','{0}����Ϊ���ڣ�','����ʱ�����ͼ�����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_DATE_CHECK','{0}����Ϊ���ڣ�','�������ͼ�����ʾ');

--�û�������
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_IMGCODE','������ĸ����벻��ȷ��','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_USER_NOT_EXISTED','�û�{0}δע�ᡣ','δע����ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_FILLINFO_ERR','����ע����Ϣ�Ѿ��ύ���ˣ�','�û��ύ��ע����Ϣ��״̬�Ѿ�����Ԥע�����ע�����ʧ�ܣ������ٴν����ύ��');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_INVALID_PWD','�û��������������','��¼��ʾ');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_RESETPWD_OK','�����û�����ɹ����û�{0}��������Ϊ{1}��','�����û�����ɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_RESETPWD_ERR','�����û�����ʧ�ܡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_LOCKUSER_OK','�����û��ʺųɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_LOCKUSER_ERR','�����û��ʺ�ʧ�ܡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_UNLOCKUSER_OK','�����û��ʺųɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_UNLOCKUSER_ERR','�����û��ʺ�ʧ�ܡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODPWD_OK','�����޸ĳɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODPWD_ERR','�����޸�ʧ�ܡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODUSERINFO_OK','�����û�������Ϣ�ɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODUSERINFO_ERR','�����û�������Ϣʧ�ܡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_REG_USERNAME','�û����Ѿ����ڡ�','ע��ʱ�����û����Ѿ���ʹ��');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_CHECK_SUCC','�û�{0}��ע�������Ѿ����ͨ����','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_CHECK_ERR','����û�ע������ʧ�ܣ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_CHECK_FAIL','�û�{0}��ע�������Ѿ����ܾ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_PWD_ERROR','�������','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_USER_DETAIL','���Ѿ��ɹ�Ԥע�ᣬ������������ĸ�����ϸ��Ϣ���Ա�ͨ������Ա��ע����ˡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REGISTER_SUCC','����ע�������Ѿ��ύ�����ڵȴ���ˡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USER_WAIT_FOR_CHECK','����ע������δͨ������Ա����ˡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USER_REG_CHECK_FAIL','���ź���֪ͨ��������ע������û�����ͨ����ԭ�����£�{0}������д������ĸ�����Ϣ�Ա����ͨ��ע����ˡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USER_LOCKED','�����ʺ��ѱ�����Ա������','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_PWD_RESETED','���������ѱ�����Ա��λ���������޸��������롣','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_FAIL','ע��ʧ�ܣ����Ժ����ԡ�','�û�ע���ʱ��ϵͳ�����û�ע����Ϣʧ�ܡ�');

--Ȩ�޹�����
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_SAVEROLE_OK','�����ɫ��Ϣ�ɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_SAVEROLE_ERR','�����ɫ��Ϣʧ�ܣ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_ROLE_EXIST','�ý�ɫ�����Ѿ����ڣ��뷵���������롣','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DELROLE_OK','ɾ����ɫ�ɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DELROLE_ERR','ɾ����ɫʧ�ܣ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_ROLE_USED','�������ڸý�ɫ���û�����ɫ���ܱ�ɾ����','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USERROLE_OK','�趨�û���ɫ�ɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USERROLE_ERR','�趨�û���ɫʧ�ܣ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ROLE_USER_OK','�趨��ɫ�����û��ɹ���','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ROLE_USER_ERR','�趨��ɫ�����û�ʧ�ܣ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_NO_RIGHT','�Բ�����û�н��иò�����Ȩ�ޡ�','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_NOT_LOGIN','����δ��¼ϵͳ�������ǳ�ʱ��û�в��������¼ϵͳ��','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SAVERIGHT_OK','�趨��ɫȨ�޳ɹ���','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SAVERIGHT_ERR','�趨��ɫȨ��ʧ�ܣ�','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SITE_RIGHT_OK','�趨��ɫվ��Ŀ¼Ȩ�޳ɹ���','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SITE_RIGHT_ERR','�趨��ɫվ��Ŀ¼Ȩ��ʧ�ܣ�','');

--��Դ������
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_FIELD_CHECK_001', '��������ֻ�ܰ������֡���ĸ��_ �����֡�', 'ֻ�ܺ��֡�Ӣ����ĸ��_ ������');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_FIELD_CHECK_004', '�ؼ���ֻ�ܰ������֡�Ӣ����ĸ�����ֺͷָ���","�����У����ֻ������10���ؼ��֣����Ǵ��������ȼ����ν��͡�', '�ؼ����ֶεļ���');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_BO_CHECK_001', '�ڵ�ǰ�Ļ����£��Ѿ���������Ļ�������', '�������ƾ���Ψһ��У��ʧ��');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_BO_CHECK_002', '��ɾ���Ļ�����������ӻ��ܻ��������Ʒ��', '��ɾ���Ļ�������������κ�һ���ӻ��ܰ�������Դ');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_RESULT_001', '���ӻ��ܳɹ���', '�������ӳɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_RESULT_002', 'ɾ�����ܳɹ���', '����ɾ���ɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_RESULT_003', '�༭���ܳɹ���', '���ܱ༭�ɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_COL_RESULT_001', '�����Ʒ�ɹ���', '��Ʒ���ӳɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_COL_RESULT_002', '�Ƴ���Ʒ�ɹ���', '��Ʒ�Ƴ��ɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_COL_RESULT_003', '�����Ʒ�ɹ���', '����δ������Ʒ����Ŀ�ɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_RESULT_001', '�������ݳɹ���', '�������ӳɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_RESULT_002', '�༭���ݳɹ���', '��Դ���ݳɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_RESULT_003', 'ɾ�����ݳɹ���', '����ɾ���ɹ�');
insert into t_resource(resourcekey,resourcevalue,remard) values('CONTENT_TAXIS_MOD_SUCC','����������óɹ�!','����������óɹ�');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.077_SSMS','MM1.0.0.080_SSMS');

commit;