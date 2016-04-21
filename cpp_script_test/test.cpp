#include<cstdio>
#include<fstream>

using namespace std;
int main()
{
    ofstream f;
    f.open("G:\\asdf.txt");
    f << "haha" << endl;
    int a, b;
    scanf("%d%d", &a, &b);
    printf("%d\n", a + b);
    f<< a + b<< endl;
    return 0;
}
