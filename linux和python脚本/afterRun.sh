#!/bin/sh
# 测试用例运行后需要进行的操作
# 把.c.csv或.cseq.csv复制到对应文件夹
# 把output文件复制到对应的文件夹

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib

function afterRunForLinux() {
	version=$1
	testNum=$2
	fileName=$3
	executePath=$4
	echo version=$version
	echo testNum=$testNum

	gcov $executePath/$fileName.c
	if [ ! -d ../source/predicate_matrix/v$version/outputs ] ; then
    	mkdir ../source/predicate_matrix/v$version/outputs -p
	fi

	cp $fileName.c.gcov ../source/predicate_matrix/v$version/$fileName$testNum.c.gcov

	cp ../outputs/test$testNum ../source/predicate_matrix/v$version/outputs/t$testNum
	cp ../outputs/t$testNum ../source/predicate_matrix/v$version/outputs/t$testNum
}

function afterRunForSiemens() {
	version=$1
	testNum=$2
	fileName=$3
	executePath=$4
	echo version=$version
	echo testNum=$testNum

	if [ ! -d ../source/predicate_matrix/v$version/outputs ] ; then
		mkdir ../source/predicate_matrix/v$version/outputs -p
	fi
	cp $fileName.c.gcov ../source/predicate_matrix/v$version/$fileName$testNum.c.gcov
	cp $fileName.cseq.csv ../source/predicate_matrix/v$version/$fileName$testNum.cseq.csv
	cp ../outputs/t$testNum ../source/predicate_matrix/v$version/outputs/t$testNum
}


if [ $# -lt 4 ]; then
	echo "Input Error:"
	echo "	Please input: sh afterRun.sh version testNum fileName executePath"
	exit 0
fi
if [ $3 == 'allfile' ] || [ $3 == 'grep' ] || [ $3 == 'sed' ];
then
	afterRunForLinux $@
elif [ $3 == 'tcas' ];
then
	afterRunForSiemens $@
else
	echo "This afterRun.sh can't support this program"
fi
