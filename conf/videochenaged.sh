#!/bin/sh
cd /opt/aspire/product/mm_dcmq/dcmq/domain/AdminServer/ssms/ftpdata/baseData
day1=$1
d1=`date --date="$day1 yesterday" +%Y%m%d`
d2=`date --date="$day1" +%Y%m%d`
if [ -e del_i_v-videodetail_$d2 ] ; then 
 ls -l *$d2; echo "文件已生成"; 
exit 1;
fi
if [ -e new_i_v-videodetail_$d2 ] ; then
 ls -l *$d2; echo "文件已生成"; 
exit 1;
fi

cat "i_v-videodetail_"$d1"_"*".txt" | sort > sort_$d1
cat "i_v-videodetail_"$d2"_"*".txt" | sort > sort_$d2

if [ -s  sort_$d1 ] ;
then 
 echo  sort_$d1"数据文件正常";
else
 echo  sort_$d1"数据文件为空"; 
 echo "执行删除sort文件退出"; 
 rm -f sort_$d1 sort_$d2 
exit 1;
fi

if [ -s sort_$d2 ] ;
then
 echo sort_$d2"数据文件正常";
else
 echo   sort_$d2"数据文件为空"; 
 echo "执行删除sort文件退出"; 
  rm -f sort_$d1 sort_$d2 
exit 1;
fi 


echo "开始执行sort文件比对"; 

diff sort_$d1 sort_$d2 |awk '
/^[0-9,]*[adc]/{
  m=gensub(/[0-9,]*([adc]).*/, "\\1", "g", $0);
}
/^< /{ 
  gsub("< ","") ; 
  if (m=="d" || m=="c" ) print $0 >>   "del_i_v-videodetail";
} 
/^> /{
  gsub("> ","") ; 
  print $0 >>   "new_i_v-videodetail";
}
'
[ -e del_i_v-videodetail ] && mv del_i_v-videodetail del_i_v-videodetail_$d2
[ -e new_i_v-videodetail ] && mv new_i_v-videodetail new_i_v-videodetail_$d2
rm -f sort_$d1 sort_$d2 
