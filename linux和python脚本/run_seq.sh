#!/bin/sh
# 插桩程序，运行测试用例，并保存结果
# 此程序获得的结果包含谓词的执行顺序

from=$1
to=$2
fileName=$3

sourceDir=../source
#fileName=schedule
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
    java -jar $predicateRecognitionPath $fileName.c predicateSeq
    cp instrumented_$fileName.c $fileName.c
    if [ $# -gt 3 ]; then
    	gcc $fileName.c -o $fileName.exe $4
    else
    	gcc $fileName.c -o $fileName.exe
    fi
    sh changed_runall.sh $i $indexSourceDir $fileName
    rm $indexSourceDir -rf
}&
done
