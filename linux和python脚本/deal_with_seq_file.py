# -*- coding: utf-8 -*-
# This program will deal with the programName#index.cseq.csv and 
# generate a programName#index.cseq_update.csv
# It combine the same sequence and add them together.
import csv

class MyMap:
    # This class is a map struct for this program
    def __init__(self,arg1,arg2,arg3,arg4,arg5,arg6,arg7):
	self.a1=arg1
	self.b1=arg2
	self.c1=arg3
	self.a2=arg4
	self.b2=arg5
	self.c2=arg6
	self.d=arg7
    # judge whether two object is equal, it just judge the first six attributes
    def equal(self, x):
	if self.a1 == x.a1 and self.b1 == x.b1 and self.c1 == x.c1 and self.a2 == x.a2 and self.b2 == x.b2 and self.c2 == x.c2:
	    result = 1
	else:
	    result = 0
	return result
    
    def output(self):
	print(str(self.a1) + ' ' + str(self.b1) + ' ' + str(self.c1) + ' ' + str(self.a2) +' ' +  str(self.b2) +' ' +  str(self.c2) + ' ' + str(self.d))
    
    def getString(self):
	return (str(self.a1) + ',' + str(self.b1) + ',' + str(self.c1) + ',' + str(self.a2) +',' +  str(self.b2) +',' +  str(self.c2) + ',' + str(self.d))


# add a new MyMap object to mapList. If there has been one same object,
# they will be combined.
def addToMyMap(mapList, find):
    for i in range(len(mapList)):
	result = mapList[i].equal(find)
	if result == 1:
	    mapList[i].d = mapList[i].d + find.d
	    return
    mapList.append(find)

# The main function for this file. It will deal with the 
# v#version/program#testNum.cseq.csv
def deal_with_seq_file(version, programName, testNum):
    #read from file
    pDir = '../source/predicate_matrix/v' + str(version) + '/'
    
    for x in range(1, int(testNum) + 1):
        filename = pDir + programName + str(x) + '.cseq.csv'
	outputFileName = pDir + programName + str(x) + '.cseq_update.csv'
	outputFile = open(outputFileName,'w')	
	reader = csv.reader(open(filename,'r'))
	i = 0
	a = b = c = 0
	edges = []
	for line in reader:
	    if len(line) < 3:
		continue
	    if i > 0:
		aMap = MyMap(a,b,c,line[0],line[1],line[2],1)
		addToMyMap(edges,aMap)
	    a = line[0]
	    b = line[1]
	    c = line[2]
	    i = i + 1
	for index in range(len(edges)):
	    outputFile.write(edges[index].getString())
	    outputFile.write('\n')

import sys
programName = sys.argv[1] # programName
lenn = sys.argv[2] # number of versions
testNum = sys.argv[3]  # number of test case
for index in range(1, int(lenn) + 1):
    deal_with_seq_file(index, programName, testNum)
    print(index)
