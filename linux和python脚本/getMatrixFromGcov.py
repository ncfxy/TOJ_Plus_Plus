# -*- coding: utf-8 -*-
def getLineIndex(line):
	result = ''
	for i in range(10,15):
		if line[i] == ' ':
			result += '0'
		else:
			result += line[i]
	return str(result)

def getString(num):
	if num < 10:
		return '0000'+str(num)
	elif num < 100:
		return '000'+str(num)
	elif num < 1000:
		return '00'+str(num)
	elif num < 10000:
		return '0'+str(num)
	else:
		return str(num)

def getTestResult(sourceDir,x):
	resultFileName = "G:/SI/oj/TOJ_Plus_Plus/data/toj_problem_2800/programs/commit_id_testid/outputs.csv"
	resultFile = open(resultFileName, 'r')
	resultContent = resultFile.readlines()
	return resultContent[x-1][0]


def getMatrixFromGcov(version,programName, testNum):
	sourceDir = "G:/SI/oj/TOJ_Plus_Plus/data/toj_problem_2800/programs/commit_id_testid"
	outputFileName = sourceDir + "/coverage_matrix.txt"
	errorTestCasesFileName = "./errorTestCases"
	outputFile = open(outputFileName, 'w')
	errorTestCasesFile = open(errorTestCasesFileName,'a')
	outputStr = ''
	outputStr += ('#Ver_# '+programName+'_'+str(version)+'\n')
	outputStr += ('#NOTS# '+str(testNum) +'\n')
	outputStr += ('#LOES# ')
	# 获取所有可执行行号
	executableLineNum = 0
	fileName = programName + str(1) + ".cpp.gcov"
	inputFile = open(fileName, 'r')
	for line in inputFile:
		if line[13] == ' ' and line[14] == '0':
			pass
		else:
			if line[9] == ':' and line[15] == ':':
				if line[8] != '-':
					executableLineNum = executableLineNum + 1
					outputStr += (getLineIndex(line) + ' ')
	inputFile.close()
	outputStr += ('\n')
	outputStr += ('#NOES# ' + str(executableLineNum) + '\n')
	outputStr += ('#NOF_# '+'\n')
	outputStr += ('#LOFS# '+'\n')
	for x in range(0,int(testNum)):
		outputStr += ('#CASE#'+getString(x)+'#R'+ getTestResult(sourceDir,x)+'# ')
		fileName = programName + str(x) + ".cpp.gcov"
		print(fileName)
		if os.path.exists(fileName):
			inputFile = open(fileName, 'r')
		else:
			# 如果不存在对应的gcov文件，则把对应信息保存到错误文件中
			errorTestCasesFile.write(str(x) + ' ')
			continue

		lineIndex = 1
		for line in inputFile:
			if len(line)<16:
				continue			
			print(line)
			if line[13] == ' ' and line[14] == '0':
				pass
			else:
				if line[9] == ':' and line[15] == ':':
					if line[8] != '-':
						pass;
						#if lineIndex > 1:
							#outputStr += (',')
						if line[8] != '#':
							outputStr +=('1 ')
							lineIndex = lineIndex + 1
						else:
							outputStr += ('0 ')
						lineIndex = lineIndex + 1
		outputStr += ('\n')
		inputFile.close()
	outputFile.write(outputStr)
	print(outputStr)
	outputFile.flush()
	errorTestCasesFile.write('\n')
	errorTestCasesFile.flush()
	errorTestCasesFile.close()
	outputFile.close()




import sys
import os
if len(sys.argv) < 3:
    print ('\nParameters is not enough.Please input using this pattern: \n\t\"python getMatrixFromGcov programName versionNum testCaseNum\"\n')
    sys.exit(0)
programName = sys.argv[1] #programName
lenn = sys.argv[2] # number of versions
testNum = sys.argv[3] # number of test case
for index in range(1,int(lenn)+1):
    getMatrixFromGcov(index,programName, testNum)
    print(index)
