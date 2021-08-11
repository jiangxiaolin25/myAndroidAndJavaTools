@echo off
color a
chcp 65001
title 截屏
echo=
rem 按照时间拼接文件名称,因为小时可能只有一位,因此_后面的小时只有一位数.
set YYYYmmdd=%date:~3,4%%date:~8,2%%date:~11,2%
set hmmss=%time:~1,1%%time:~3,2%%time:~6,2%
set "filename=%YYYYmmdd%_%hmmss%.png"
rem 截屏保存在手机的位置
set screen=/sdcard/screen.png
rem adb截屏
adb shell screencap -p %screen%
rem 指定文件夹名称
set dir=C:\Users\jiangxiaolin\Desktop
rem 创建文件夹
if not exist %dir% (    
    md %dir%
) 
rem adb导出文件
adb pull %screen% %dir%%filename%
echo=
echo=
echo 截屏目录:%dir%   截屏图片:%filename%
echo=
echo=

pause
