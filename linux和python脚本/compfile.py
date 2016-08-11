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
sourcedir = ''

def cmpFile(f1,f2):
    return f1.read() == f2.read()

csvfile = open(csvFileName,'wb')
writer = csv.writer(csvfile, delimiter=' ',quotechar='|', quoting=csv.QUOTE_MINIMAL)
data = []

for i in range(0,caseNum):
    file1 = open(sourcedir+ansFileName+'_'+str(i)+'.out','r')
    file2 = open(sourcedir+outFileName+str(i)+'.out','r')
    same = cmpFile(file1,file2)
    if same == True:
        data.append(('1'))
    else:
        data.append(('0'))

print (data)

for e in data:
    writer.writerow(e)

csvfile.close()
    
