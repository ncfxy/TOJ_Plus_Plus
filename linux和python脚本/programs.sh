#!/bin/sh
# 此文件描述如何使用已有的脚本，列出了一些例子

:<<BLOCK
....
1.tcas
sh run.sh tcas 1 41

python union.py tcas 41 1608
python outputs_union.py 41 1608

sh run_seq.sh 1 41 tcas

python deal_with_seq_file.py tcas 41 1608
python seq_union.py tcas 41 1608

2.schedule
sh run.sh schedule 1 9

python union.py schedule 9 2650
python outputs_union.py 9 2650

sh run_seq.sh 1 9 schedule

python deal_with_seq_file.py schedule 9 2650
python seq_union.py schedule 9 2650

3.schedule2
sh run.sh schedule2 1 10

python union.py schedule2 10 2710
python outputs_union.py 10 2710

sh run_seq.sh 1 10 schedule2

python deal_with_seq_file.py schedule2 10 2710
python seq_union.py schedule2 10 2710

4.tot_info
sh run.sh tot_info 1 23 -lm

python union.py tot_info 23 1052
python outputs_union.py 23 1052

sh run_seq.sh 1 23 tot_info -lm

python deal_with_seq_file.py tot_info 23 1052
python seq_union.py tot_info 23 1052

5.replace
sh run.sh replace 1 32 -ansi
python union.py replace 32 5542
python outputs_union.py 32 5542

sh run_seq.sh 1 32 replace -ansi
python deal_with_seq_file.py replace 32 5542
python seq_union.py replace 32 5542

6. print_tokens
sh run.sh print_tokens 1 7 -lm

python union.py print_tokens 7 4130
python outputs_union.py 7 4130

sh run_seq.sh 1 7 print_tokens -lm

python deal_with_seq_file.py print_tokens 7 4130
python seq_union.py print_tokens 7 4130

BLOCK

