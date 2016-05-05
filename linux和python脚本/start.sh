#!/bin/sh

function runForLinux() {
# 插桩程序，运行测试用例，并保存结果
# 适用于gzip、grep、sed三个程序

# faultsList文件中保存要植入的错误，并且第一个错误为
# 空，即使得v0版本是无错误的版本
	source ./faultsList.sh

	export experiment_root=/data1/experiment/sirPrograms/linux/subjects
	
	fileName=$1
	from=$2
	to=$3

	for index in $(seq $from $to ) 
	#`fault in ${faults[*]}
	do
	{
		echo $index
		fault=${faults[index]}
		sourceDir=../source$index
		rm $sourceDir -rf
		mkdir $sourceDir -p
		cp ../versions.alt/versions.seeded/v1/* $sourceDir/
		cp ./beforeRun.sh ./afterRun.sh ./changed_runall.sh $sourceDir
		currentDir=`pwd`
		cd $sourceDir
		sh changed_runall.sh $index $sourceDir $fileName "$fault"
		cd $currentDir
		rm $sourceDir -rf
	}
	done
}

function runForSiemens(){
	fileName=$1
	from=$2
	to=$3
	sourceDir=../source
	#fileName=schedule
	# 谓词插桩程序的位置
	predicateRecognitionPath=/home/ncfxy/newDisk/svnDir/wiki/PredicateRecognition-0.0.1-SNAPSHOT.jar

	if [ ! -d $sourceDir ] ; then
    	mkdir $sourceDir -p
	fi

	for i in $(seq $from $to) 
	do 
	{
    	indexSourceDir=$sourceDir$i
    	if [ ! -d $indexSourceDir ] ; then
        	mkdir $indexSourceDir -p
    	fi
    	echo $indexSourceDir
    	rm -rf $indexSourceDir/$fileName*
    	cp ../versions.alt/versions.orig/v$i/* $indexSourceDir
    	cp beforeRun.sh afterRun.sh changed_runall.sh $indexSourceDir
    	cd $indexSourceDir
    	java -jar $predicateRecognitionPath $fileName.c
    	cp instrumented_$fileName.c $fileName.c
    	if [ $# -gt 3 ]; then
    		gcc $fileName.c -o $fileName.exe $4
    	else
    		gcc $fileName.c -o $fileName.exe
    	fi
    	sh changed_runall.sh $i $indexSourceDir $fileName
    	rm $indexSourceDir -rf
	}&     # 用来实现多线程同时运行
	done
}

# 第一个参数为程序的名字,不同的程序调用不同的函数
if [ $# -lt 3 ]; then
	echo "Input Error:"
	echo "	Please input: sh start.sh programeName startVersion endVersion (addtionalParameters)"
	echo "	For example: sh start.sh tcas 1 41"
	echo "	For example: sh start.sh replace 1 32 -ansi"
	exit 0
fi
if [ $1 == 'allfile' ] || [ $1 == 'grep' ] || [ $1 == 'sed' ];
then
	runForLinux $@
elif [ $1 == 'tcas' ];
then
	runForSiemens $@
else
	echo "This program can't run for this program"
fi


