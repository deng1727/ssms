LOAD DATA  
INFILE videoCode.txt
INTO TABLE t_vo_video_full 
REPLACE  
FIELDS TERMINATED BY X'1F'  
TRAILING NULLCOLS  
(
videoid ,
coderateid,
filepath  ,
downloadfilepath ,
filesize,
status,
hashc "Utl_Raw.Cast_To_Raw(sys.dbms_obfuscation_toolkit.md5(input_string =>:VIDEOID||:CODERATEID||:FILEPATH||:DOWNLOADFILEPATH||:FILESIZE))" 
)
