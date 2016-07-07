#include <cstdio>

int main(){
	int a,b;
	while(~scanf("%d%d",&a,&b)){
		if(a>10) a--;
		printf("%d\n", a+b);
	}
	return 0;
}