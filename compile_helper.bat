@echo on

rem 第一个参数代表代码文件所在文件夹
rem 第二个参数代表代码文件的名字
rem 第三个参数代表要执行的命令
cd /d %1
echo %2

if "%3"=="g++" (g++ %2.cpp -o %2.exe -ftest-coverage -fprofile-arcs)
if "%3"=="gcov" (gcov %2.cpp)
