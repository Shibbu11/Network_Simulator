import java.util.*;
public class dhcp{
final static String net_id="192.168.10.0";
static Scanner sc=new Scanner(System.in);
static int num_host=sc.nextInt();
String ip=null;
dhcp(int i)
{
ip="192.168.10."+i;
}
public static void main(String args[])
{

int i;
for(i=1;i<=num_host;i++)
{
dhcp ob=new dhcp(i);

System.out.println(ob.ip);
}
System.out.println("Network id="+net_id);
}
}
