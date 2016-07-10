import csv
import os
import sys
import filecmp


csvFileName = sys.argv[1]
ansFileName = sys.argv[2]
outFileName = sys.argv[3]
caseNum = int(sys.argv[4])

'''
csvFileName = 'outputs'
ansFileName = 'ans'
outFileName = 'out'
caseNum = 3
'''
sourcedir = '../'

def cmpFile(f1,f2):
    return f1.read() == f2.read()

csvfile = open(csvFileName+'.csv','wb')
writer = csv.writer(csvfile, delimiter=' ',quotechar='|', quoting=csv.QUOTE_MINIMAL)
data = []

for i in range(1,caseNum+1):
    file1 = open(sourcedir+ansFileName+str(i)+'.txt','r')
    file2 = open(sourcedir+outFileName+str(i)+'.txt','r')
    same = cmpFile(file1,file2)
    if same == True:
        data = data + list('1')
    else:
        data = data + list('0')

print data

for e in data:
    writer.writerow(e)

csvfile.close()
    
