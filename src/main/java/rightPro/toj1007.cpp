#include<iostream>
using namespace std;
int main()
{
 int k,i,j,found,max,s[14],x;
 for(k=1;k<=13;k++)   
 {
  j=k+1;found=0;  
  while(!found)
  {
   i=0;max=2*k;
   while(1)
   { if((i+j-1)%max<k)
    {j++;break;}
    else
    {i=((i+j-1)%max);max--;
      if(max==k)
     {found=1;s[k]=j;break;}
    }
   }
  }
 }
 while(cin>>x&&x!=0)
 {
     cout<<s[x]<<endl;
 }
 return 0;
}