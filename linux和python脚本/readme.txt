1、将该文件夹拷贝到项目的目录（如tcas下）
2、将项目script中的runall.sh覆盖到本目录
3、运行python addToScript.python
4、修改changed_runall.sh中的代码
	a. 去掉第一个
		a=$(expr "$a" + "1");
		sh afterRun.sh $version $a $fileName
	b. 替换../source为$executePath
5、根据program.sh中的命令运行代码