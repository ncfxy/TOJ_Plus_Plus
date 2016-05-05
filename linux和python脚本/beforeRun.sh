#!/bin/sh
# 在测试用例运行前需要进行的操作
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib

function beforeRunForLinux() {

	fileName=$1
	# 使用之前的第二个附加参数来设置错误
	fault="$2"
	echo "$2"
	rm -rf *.gc*
	rm -rf *.c.gcov
	if [ "$fault" = "" ];
	then
		make build
	else
		echo ${fault}
		make build COMPILE_PARAMETERS="-D${fault}"
	fi
}

function beforeRunForSiemens() {
	echo "do nothing"	
}

if [ $# -lt 1 ]; then
	echo "Input Error:"
	echo "	Please input: sh beforeRun.sh programeName (additionalParameter)"
	echo "	For example: sh beforeRun.sh tcas"
	exit 0
fi
if [ $1 == 'allfile' ] || [ $1 == 'grep' ] || [ $1 == 'sed' ];
then
	beforeRunForLinux $1 "$2"
elif [ $1 == 'tcas' ];
then
	beforeRunForSiemens $@
else
	echo "This beforeRun.sh can't support this program"
fi


#rm -rf *.gc*
#rm -rf *.c.gcov
#/root/testHowToUse/gcc/gccBuild/bin/gcc tcas.c -fprofile-arcs -ftest-coverage -o tcas.exe
