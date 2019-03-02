--资源表
delete from t_resource;

--系统公共信息
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_SYSTEM','操作失败，请联系管理员！','内部错误时候的统一提示');
insert into t_resource(resourceKey,resourceValue,remard) values('SYSTEM_COMMON_ERROR','操作失败，请联系管理员！','系统中如果出现非业务错误，如数据库连接失败、网络连接断开、存储空间不够等错误');
insert into t_resource(resourceKey,resourceValue,remard) values('SYSTEM_DUTY_DATA_ERROR','数据发生变化，操作失败，请检查！','脏数据错误');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_NEED_INPUT','{0}必须输入','表单中必填项没有输入时的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_LENGTH_ERROR1','{0}长度不能大于{1}个字符','表单中字段长度错误时的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_LENGTH_ERROR2','{0}长度不能小于{1}个字符','表单中字段长度错误时的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_LENGTH_ERROR3','{0}长度必须在{1}和{2}个字符之间','表单中字段长度错误时的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELDS_NEED_INPUT','{0}必须有一个输入。','表单中多个字段必须并且只能有一个可以输入时的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('PUBLIC_DELETE_ALERT','您确定要进行删除操作吗？','通用删除提示语');
insert into t_resource(resourceKey,resourceValue,remard) values('PUBLIC_REMOVE_ALERT','您确定要进行移除操作吗？','通用移除提示语');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_INTEGER_CHECK','{0}必须为整数！','整数类型检测的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_DOUBLE_CHECK','{0}必须为浮点数！','浮点数类型检测的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_DATETIME_CHECK','{0}必须为日期！','日期时间类型检测的提示');
insert into t_resource(resourceKey,resourceValue,remard) values('FORM_FIELD_TYPE_DATE_CHECK','{0}必须为日期！','日期类型检测的提示');

--用户管理部分
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_IMGCODE','您输入的附加码不正确！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_USER_NOT_EXISTED','用户{0}未注册。','未注册提示');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_FILLINFO_ERR','您的注册信息已经提交过了！','用户提交过注册信息后（状态已经不是预注册或者注册审核失败），又再次进行提交。');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_INVALID_PWD','用户名或者密码错误！','登录提示');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_RESETPWD_OK','重设用户密码成功。用户{0}的新密码为{1}。','重设用户密码成功');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_RESETPWD_ERR','重设用户密码失败。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_LOCKUSER_OK','锁定用户帐号成功。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_LOCKUSER_ERR','锁定用户帐号失败。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_UNLOCKUSER_OK','解锁用户帐号成功。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_UNLOCKUSER_ERR','解锁用户帐号失败。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODPWD_OK','密码修改成功。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODPWD_ERR','密码修改失败。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODUSERINFO_OK','保存用户个人信息成功。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODUSERINFO_ERR','保存用户个人信息失败。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_REG_USERNAME','用户名已经存在。','注册时发现用户名已经被使用');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_CHECK_SUCC','用户{0}的注册请求已经审核通过。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_CHECK_ERR','审核用户注册请求失败！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_CHECK_FAIL','用户{0}的注册请求已经被拒绝。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_PWD_ERROR','密码错误！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_USER_DETAIL','您已经成功预注册，请继续输入您的个人详细信息，以便通过管理员的注册审核。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REGISTER_SUCC','您的注册申请已经提交，正在等待审核。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USER_WAIT_FOR_CHECK','您的注册请求还未通过管理员的审核。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USER_REG_CHECK_FAIL','很遗憾的通知您，您的注册请求没有审核通过。原因如下：{0}。请填写清楚您的个人信息以便可以通过注册审核。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USER_LOCKED','您的帐号已被管理员锁定。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_PWD_RESETED','您的密码已被管理员复位，请立即修改您的密码。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_REG_FAIL','注册失败，请稍候再试。','用户注册的时候系统保存用户注册信息失败。');

--权限管理部分
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_SAVEROLE_OK','保存角色信息成功！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_SAVEROLE_ERR','保存角色信息失败！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_ROLE_EXIST','该角色名称已经存在，请返回重新输入。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DELROLE_OK','删除角色成功！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DELROLE_ERR','删除角色失败！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_ROLE_USED','存在属于该角色的用户，角色不能被删除！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USERROLE_OK','设定用户角色成功！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_USERROLE_ERR','设定用户角色失败！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ROLE_USER_OK','设定角色所属用户成功！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ROLE_USER_ERR','设定角色所属用户失败！','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_NO_RIGHT','对不起，您没有进行该操作的权限。','');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_ERR_NOT_LOGIN','您还未登录系统，或者是长时间没有操作，请登录系统！','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SAVERIGHT_OK','设定角色权限成功！','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SAVERIGHT_ERR','设定角色权限失败！','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SITE_RIGHT_OK','设定角色站点目录权限成功！','');
insert into t_resource(resourcekey,resourcevalue,remard) values('WEB_INF_SITE_RIGHT_ERR','设定角色站点目录权限失败！','');

--资源管理部分
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_FIELD_CHECK_001', '货架名称只能包含汉字、字母、_ 和数字。', '只能汉字、英文字母、_ 、数字');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_FIELD_CHECK_004', '关键字只能包含汉字、英文字母、数字和分隔符","。其中，最多只能输入10个关键字，它们从左到右优先级依次降低。', '关键字字段的检验');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_BO_CHECK_001', '在当前的货架下，已经存在输入的货架名称', '货架名称具有唯一性校验失败');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_BO_CHECK_002', '待删除的货架下面存在子货架或包含有商品！', '待删除的货架自身或下面任何一个子货架包含有资源');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_RESULT_001', '增加货架成功！', '货架增加成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_RESULT_002', '删除货架成功！', '货架删除成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CATE_RESULT_003', '编辑货架成功！', '货架编辑成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_COL_RESULT_001', '添加商品成功！', '商品增加成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_COL_RESULT_002', '移除商品成功！', '商品移除成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_COL_RESULT_003', '添加商品成功！', '增加未分类商品到栏目成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_RESULT_001', '增加内容成功！', '内容增加成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_RESULT_002', '编辑内容成功！', '资源内容成功');
insert into t_resource(resourceKey,resourceValue,remard) values('RESOURCE_CONTENT_RESULT_003', '删除内容成功！', '内容删除成功');
insert into t_resource(resourcekey,resourcevalue,remard) values('CONTENT_TAXIS_MOD_SUCC','排序序号设置成功!','排序序号设置成功');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.077_SSMS','MM1.0.0.080_SSMS');

commit;