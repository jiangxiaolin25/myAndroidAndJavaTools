::302400是秒，一个星期的时间，参数传需要是秒，所以需要定时多久直接转化为秒即可
schtasks /create /TN shutdown_test /ST 23:00 /ET 23:30 /SD 2021/06/11 /ED 2099/05/06 /SC weekly  /mo 1 /d FRI /TR "shutdown /s"
pause