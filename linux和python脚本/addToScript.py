# -*- coding: utf-8 -*-
# 修改runall文文件
import os

def addToScript():
	inputFileName = "runall.sh"
	outputFileName = "changed_runall.sh"
	inputFile = open(inputFileName)
	outputFile = open(outputFileName, "w")
	content = inputFile.readlines()

	outputStr = ''
	# 在文件最开始添加变量的初始化
	# version：要执行的软件版本
	# executePath：可执行文件的位置
	# fileName：
	outputStr += ("a=0\n")
	outputStr += ("version=$1\n")
	outputStr += ("executePath=$2\n")
	outputStr += ("fileName=$3\n")
	index = 1
	for line in content:
		if line.startswith("echo \">>>>"):
			if index > 1:
				outputStr += ("a=$(expr \"$a\" + \"1\");\n");
				outputStr += ("sh afterRun.sh $version $a $fileName $executePath\n");
			outputStr += ("if [ $# -gt 3 ]; then\n")
			outputStr += ("    #如果参数数量大于3，beforeRun中多添加一个编译参数\n")
			outputStr += ("    sh beforeRun.sh $fileName \"$4\"\n")
			outputStr += ("else\n")
			outputStr += ("    #否则，不添加编译参数\n")
			outputStr += ("    sh beforeRun.sh $fileName\n")
			outputStr += ("fi\n")

			index = index + 1
		outputStr += (line);
	outputStr += ("a=$(expr \"$a\" + \"1\");\n");
	outputStr += ("sh afterRun.sh $version $a $fileName $executePath\n");
	outputStr = outputStr.replace("../source","$executePath")
	outputFile.write(outputStr)




addToScript()
