#!/bin/sh
cd /opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/baseData
day1=$1
d1=`date --date="$day1" +%Y%m%d`
if [ -e videoCode.txt ] ; then 
 ls -l *$d1; echo "�ļ�������"; 
exit 1;
fi


cat "i_v-videoCode_"$d1"_"*".txt" > videoCode.txt

if [ -s  videoCode.txt ] ;
then 
 echo  videoCode.txt"�����ļ�����";
else
 echo  videoCode.txt"�����ļ�Ϊ��"; 
 echo "ִ��ɾ��videoCode.txt�ļ��˳�"; 
 rm -f videoCode.txt
exit 1;
fi

echo "��ʼִ�н�videoCode.txt�ļ�������ʱ��";

sqlldr userid=ssms/Rt9_mBiCwp@SSMSSERVICE control=videofullsqlin.ctl errors=2000000  silent=header discard =t_vo_video.dsc  bad=t_vo_video.bad

echo "��videoCode.txt�ļ�������ʱ�����";

rm -f videoCode.txt
