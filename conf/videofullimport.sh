#!/bin/sh
cd /opt/aspire/product/ssms/weblogic/user_projects/domains/ssmsdomain/AdminServer/ssms/ftpdata/baseData
day1=$1
d1=`date --date="$day1" +%Y%m%d`
if [ -e videoCode.txt ] ; then 
 ls -l *$d1; echo "文件已生成"; 
exit 1;
fi


cat "i_v-videoCode_"$d1"_"*".txt" > videoCode.txt

if [ -s  videoCode.txt ] ;
then 
 echo  videoCode.txt"数据文件正常";
else
 echo  videoCode.txt"数据文件为空"; 
 echo "执行删除videoCode.txt文件退出"; 
 rm -f videoCode.txt
exit 1;
fi

echo "开始执行将videoCode.txt文件导入临时表";

sqlldr userid=ssms/Rt9_mBiCwp@SSMSSERVICE control=videofullsqlin.ctl errors=2000000  silent=header discard =t_vo_video.dsc  bad=t_vo_video.bad

echo "将videoCode.txt文件导入临时表完成";

rm -f videoCode.txt
