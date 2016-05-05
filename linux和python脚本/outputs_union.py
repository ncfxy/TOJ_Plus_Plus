# -*- coding: utf-8 -*-
# 合并所有outputs文件到一个文件中
import csv

def compareTwoFiles(file1, file2):
	result = True
	file1Content = file1.readlines()
	file2Content = file2.readlines()
	if len(file1Content) != len(file2Content):
		result = False
		return result
	for i in range(len(file1Content)):
		if file1Content[i] != file2Content[i]:
			result = False
	return result

def union(version,testNumber):
    #read from file
	pDir = '../source/predicate_matrix/v' + str(version) + '/outputs/'
	inputFileName = pDir + 't1';
	outputFileName = pDir + 'outputs.csv'
	outputFile = open(outputFileName, 'w')
	for test in range(1,int(testNumber) + 1):
		inputFileName = pDir + 't' + str(test)
		originalInputFileName = '../source/predicate_matrix/v0/outputs/' + 't' + str(test)
		inputFile = open(inputFileName, 'r')
		originalFile = open(originalInputFileName, 'r')
		result = compareTwoFiles(inputFile,originalFile)
		inputFile.close()
		originalFile.close()
		if result:
			outputFile.write(str(0)+'\n')
		else:
			outputFile.write(str(1)+'\n')

    
	
import sys
lenn = sys.argv[1]  # number of versions
testNumber = sys.argv[2]  # number of test case
for i in range(1,int(lenn)+1):
	print(i)
	union(i, testNumber)

    

