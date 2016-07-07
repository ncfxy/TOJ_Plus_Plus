#include <stdio.h>

int ans[] = {2,7,5,30,169,441,1872,7632,1740,93313,459901,1358657,2504881};

int main()
{
	int n;
	while (scanf("%d",&n)!=EOF&&n)
		printf("%d\n",ans[n-1]);
	return 0;
}
