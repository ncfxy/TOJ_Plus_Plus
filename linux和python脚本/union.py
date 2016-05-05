# -*- coding: utf-8 -*-
# 合并所有.c.csv文件到一个文件中
import csv


def union(version, programName, testNum):
    #read from file
    pDir = '../source/predicate_matrix/v' + str(version) + '/'
    inputFileName = pDir + programName + '1.c.csv';
    outputFileName = pDir + programName + '_final.c.csv';
    reader = csv.reader(open(inputFileName,'r'))
    outputFile = open(outputFileName,'w')
    data = []
    i = 0
    for line in reader:
        data.append([])
        data[i].append(line[0])
	data[i].append(line[1])
        i = i + 1
    print ('data len')
    print (len(data))

    for x in range(1,int(testNum)+1):
        filename = pDir + programName + str(x) + '.c.csv';
        reader = csv.reader(open(filename,'r'))
        i = 0
        for line in reader:
	    #print(line)
	    if len(data) <= i:
		print (len(data))
		print i
		print x
	    a = data[i]
	    b = line[2]
            data[i].append(line[2])
            data[i].append(line[3])
            i = i + 1

    for i in range(len(data)):
        for j in range(len(data[i])):
            outputFile.write(str(data[i][j]))
            if j < len(data[i]) - 1:
                outputFile.write(',')
        outputFile.write('\n')
        #writer.writerow(data[i])

import sys
programName = sys.argv[1]  # programName
lenn = sys.argv[2]  # number of versions
testNum = sys.argv[3]  # number of test case
for index in range(1,int(lenn)+1):
    union(index, programName, testNum)
    print(index)

    

