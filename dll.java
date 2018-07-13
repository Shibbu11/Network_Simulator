import java.util.*;
class dll{
	/*int[] onesCompAdd(int arr1[],int arr2[])
	{
		int i,c=0,s=0;
		for(i=0;i<arr1.length;i++)
		{
			
			s=s+arr1[i]+arr2[i];
		    if(s>=2)
			{
				c=1;s=s-2;arr1[i]=s;
			}
	        else
			{
			    c=0;
				arr1[i]=s; 	
			}
			s=c;
            			
		 }
		 if(c==1)
		 {
			 int arr3[]=new int[arr1.length];
			 for(i=0;i<arr1.length;i++)
			 {
				 if(i==0)
				arr3[i]=1;
                 else
                arr3[i]=0;					 
			 }
			 onesCompAdd(arr1,arr3);
		 }
		 return arr1;
		
	}*/
	 void checksum()
	{
Scanner sc=new Scanner(System.in);
System.out.println("Enter the no of bit in array :");
int n=sc.nextInt();
int arr[]=new int[n];
int i;
System.out.println("Enter bits in array: ");
for( i=0;i<n;i++)
{
arr[i]=sc.nextInt();
}
int arr1[]=new int[n/2];
int arr2[]=new int[n/2];
int div=n/2;
for(i=0;i<div;i++)
{
arr1[i]=arr[i];
}
for(i=0;i<div;i++)
{
arr2[i]=arr[i+div];
}
int arr3[]=new int[div];
for(i=0;i<div;i++)
{
arr3[i]=arr1[i]^arr2[i];
}
System.out.println("bits after XOR are:");
for(i=0;i<div;i++)
{
System.out.print(arr3[i]);
}
System.out.println();
int arr4[]=new int[div];
for(i=0;i<div;i++)
{
arr4[i]=arr3[i]^1;
}
String s=new String();
System.out.println("The 1's compliment i.e. checksum to be appended is :");
for(i=0;i<div;i++)
{
System.out.print(arr4[i]);
}

System.out.println();

String rstr=new String();
//Select reciever
System.out.println("Select reciever to whom u want to send data");
System.out.println("1: 00000");
System.out.println("2: 11111");
System.out.println("3: 00001");
int r=sc.nextInt();
if(r==1)
	rstr="00000";
else if(r==2)
	rstr="11111";
else if(r==3)
	rstr="00001";
else
	System.out.println("Not a correct receiver");
s=s+rstr+" ";


System.out.println("Message sent to reciever");
for(i=0;i<arr1.length;i++)
	s=s+arr1[i];
s=s+" ";
for(i=0;i<arr2.length;i++)
	s=s+arr2[i];
s=s+" ";
for(i=0;i<div;i++)
{
s=s+arr4[i];
}

System.out.println(s);

//Reciever end

int arr5[]=new int[div];
for(i=0;i<div;i++)
{
arr5[i]=arr3[i]^arr4[i];
}
System.out.println("XOR addition:");
for(i=0;i<div;i++)
{
System.out.print(arr5[i]);
}
System.out.println();
for(i=0;i<div;i++)
{
arr5[i]=arr5[i]^1;
}
System.out.println("The final output is:");
int count=0;
for(i=0;i<div;i++)
{
System.out.print(arr5[i]);
if(arr5[i]==0)
	count++;
}
System.out.println();
if(count==arr1.length)
	System.out.println("No error found");
else
	System.out.println("DAta has been changed during transmission");
}

	
	public static void main(String args[])
	{
		Scanner cs=new Scanner(System.in);
		dll obj=new dll();
		obj.checksum();
		/*System.out.println("Enter Message :");
		String message=cs.next();
		String binary = new BigInteger(message.getBytes()).toString(2);
        System.out.printf("Message in binary form :"+ binary);*/
		
	}
}
