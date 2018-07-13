class Router{
    String name;
    String intrfaceNo[]=new String[4];
    String ip[]=new String[4];
    String subnetMask[]=new String[4];
    String type[]=new String[4];
    
    Router()
    {
        name="Router1";
        
        intrfaceNo[0]="1/0";
        ip[0]="1.2.3.1";
        subnetMask[0]="255.255.255.0";
        type[0]="HOST";
        
        intrfaceNo[1]="2/0";
        ip[1]="7.7.7.1";
        subnetMask[1]="255.255.255.0";
        type[1]="HOST";
        
        intrfaceNo[2]="3/0";
        ip[2]="6.6.6.1";
        subnetMask[2]="255.255.255.0";
        type[2]="HTTP_SERVER";
        
        intrfaceNo[3]="4/0";
        ip[3]="5.5.5.1";
        subnetMask[3]="255.255.255.0";
        type[3]="DNS_SERVER";
    }
    
    void sendPacketRouter(Switch swt,Router R,Switch swt2,HTTP h,DNS d,String destAddress,String intrface,int mode)
    {
        if(mode==0)//ping
        {
            int flag=0;
            for(int i=0;i<4;++i)
            {
                if(R.ip[i].equals(destAddress))
                {
                    System.out.println("Ping to "+R.ip[i]+" Successful.....");
                    flag=1;
                    break;
                }
                if(!R.intrfaceNo[i].equals(intrface))
                {
                    if(type[i]=="HOST")
                    {
                        int j=0;
                        while(swt2.portNo[j]!=null)
                        {
                            if(swt2.portNo[j].equals(destAddress))
                            {
                                System.out.println("Ping to "+swt2.portNo[j]+" Successful.....");
                                flag=1;
                                break;
                            }
                            j++;
                        }
                    }
                    if(type[i]=="HTTP_SERVER")
                    {
                        for(int j=0;j<4;++j)
                        {
                            if(R.type[j].equals("HTTP_SERVER"))
                            {
                                
                                if(R.ip[j].equals(destAddress))
                                {
                                    System.out.println("Ping to "+R.ip[j]+" Successful.....");
                                    flag=1;
                                    break;
                                }else if(h.getIp().equals(destAddress))
                                    {
                                        System.out.println("Ping to "+h.getIp()+" Successful.....");
                                        flag=1;
                                        break;
                                    }
                                
                                                                    
                            }
                        }
                    }
                    if(type[i]=="DNS_SERVER")
                    {
                        for(int j=0;j<4;++j)
                        {
                            if(R.type[j].equals("DNS_SERVER"))
                            {
                                
                                if(R.ip[j].equals(destAddress))
                                {
                                    System.out.println("Ping to "+R.ip[j]+" Successful.....");
                                    flag=1;
                                    break;
                                }else if(d.getIp().equals(destAddress))
                                    {
                                        System.out.println("Ping to "+d.getIp()+" Successful.....");
                                        flag=1;
                                        break;
                                    }
                                
                                                                    
                            }
                        }
                    }
               }
            }
            if(flag==0)
                {
                    System.out.println("Destination Un-Reachable.....");
                }
        }
        if(mode==1)
        {
            System.out.println("======> WEB BROWSER <======");
            for(int i=0;i<3;++i)
            {
                if(R.type.equals("HTTP_SERVER"))
                {
                    int index=i;
                    break;
                }
            }
            
            String ipAddr=h.getIp();
            if(ipAddr.equals(destAddress))
            {
                h.httpRequest();
            }else{
                System.out.print("Destination Un-Reachable.....");
            }
        }
        if(mode==2)
        {
            d.dnsRequest(destAddress);
            if(d.domainName.equals(destAddress))
            {
                R.sendPacketRouter(swt,R,swt2,h,d,d.ipAddress,swt.Rintrface,1);
            }
            else
            {
                System.out.print("Message From DNS : Destination Un-Reachable.....");
            }
        }
    }
    
        
    void DHCP(Host hs, Switch sw)
    {
        System.out.println("Processing DHCP Request By Host : "+hs.name+" ........");
        int lastOctet=sw.getCount();
        String routerInterface=sw.Rintrface;
        String ip=sw.Rip;
        int index=ip.lastIndexOf('.');
        ip=ip.substring(0,index);
        ip=ip+"."+lastOctet;
        hs.ip=ip;
        sw.portNo[hs.port]=hs.ip;
        System.out.println("DHCP Request Successful. New Ip Assigned : "+hs.ip);
        sw.incCount();
        System.out.println();
        
    }
}

class HTTP{
    String ip;
    HTTP()
    {
        ip="6.6.6.2";
    }
    String getIp()
    {
        return ip;
    }
    
    void httpRequest()
    {
        System.out.println("Displaying Webpage at "+ip);
    }
}
class DNS{
    String ip;
    String domainName="www.google.com";
    String ipAddress="6.6.6.2";
    DNS()
    {
        ip="5.5.5.2";
    }
    String getIp()
    {
        return ip;
    }
    
    void dnsRequest(String dn)
    {
        System.out.println("Searching DNS Server for Requested Domain : "+dn);
    }
}


class Switch{
    String name;
    String Rintrface;
    String Rip;
    String portNo[]=new String[6];
    int count=2;
    
       
    int getCount()
    {
        return count;
    }   
    void incCount()
    {
        count=count+1;
    }
    
    Switch(String name,String Rintrface,String Rip){
        this.name=name;
        this.Rintrface=Rintrface;
        this.Rip=Rip;
    }
    
    void sendPacketSwitch(Switch swt,Router R,Switch swt2,HTTP h,DNS d, String destAddress,int mode)
    {
        if(mode==0)//ping
        {
            int flag=0;
            int i=0;
            while(swt.portNo[i]!=null)
            {

               if(swt.portNo[i].equals(destAddress))
               {
                   System.out.println("Ping to "+swt.portNo[i]+" Successful.....");
                   flag=1;
                   break;
               }
               i++;
            }

            if(flag==0)
            {
                R.sendPacketRouter(swt,R,swt2,h,d,destAddress,swt.Rintrface,mode);
            }
        }
        if(mode==1)//HTTP
        {
            R.sendPacketRouter(swt,R,swt2,h,d,destAddress,swt.Rintrface,mode);
        }
        if(mode==2)//DNS
        {
            R.sendPacketRouter(swt,R,swt2,h,d,destAddress,swt.Rintrface,mode);
        }
    }
    
    void getDetails()
    {
        System.out.println("DISPLAYING SWITCH DETAILS");
        System.out.println("Switch Name : "+name);
        System.out.println("Switch Router Interface Connected : "+Rintrface);
        System.out.println("Switch Router IP Connected : "+Rip);
        System.out.println("Switch PORT[0] IP : "+portNo[0]);
        System.out.println("Switch PORT[1] IP : "+portNo[1]);
        System.out.println("Switch PORT[2] IP : "+portNo[2]);
        System.out.println("Switch PORT[3] IP : "+portNo[3]);
        System.out.println("Switch PORT[4] IP : "+portNo[4]);
        System.out.println();
    }
    
}

class Host{
    String name;
    String ip,mac,dg;
    int port;
    
    Host(String name,String mac,String dg,Switch sw,int port)
    {
        this.name=name;
        this.mac=mac;
        this.dg=dg;
        this.port=port;
        sw.portNo[port]=mac;
        
    }
    
    void ping(Switch swt,Router R,Switch swt2, HTTP h,DNS d,String destAddr,int mode)
    {
        if(mode==0)//ping
        {
            System.out.println("Pinging "+destAddr+" with 32 bit of Data.......");
            swt.sendPacketSwitch(swt,R,swt2, h, d,destAddr,mode);
        }
        if(mode==1)//HTTP
        {
            swt.sendPacketSwitch(swt,R,swt2, h, d,destAddr,mode);
        }
        if(mode==2)//DNS
        {
            swt.sendPacketSwitch(swt,R,swt2, h, d,destAddr,mode);
        }
        
    }
    
    void getDetails()
    {
        System.out.println("DISPLAYING HOST DETAILS");
        System.out.println("Host Name : "+name);
        System.out.println("Host MAC : "+mac);
        System.out.println("Host Default Gateway : "+dg);
        System.out.println("Host IP Address : "+ip);
        System.out.println();
    }
    
}

public class NetworksProject {

   
    public static void main(String[] args) {
        
        Router R=new Router();
        Switch sw=new Switch("Switch1",R.intrfaceNo[0],R.ip[0]);
        
        Host h1=new Host("PC1","A.B.C.D","1.2.3.1",sw,0);        
        R.DHCP(h1, sw);
        Host h2=new Host("PC2","E.F.G.H","1.2.3.1",sw,1);        
        R.DHCP(h2, sw);
        Host h3=new Host("PC3","I.J.K.L","1.2.3.1",sw,2);        
        R.DHCP(h3, sw);
        Host h4=new Host("PC4","M.N.O.P","1.2.3.1",sw,3);        
        R.DHCP(h4, sw);
        Host h5=new Host("PC5","Q.R.S.T","1.2.3.1",sw,4);        
        R.DHCP(h5, sw);
        
        Switch sw2=new Switch("Switch2",R.intrfaceNo[1],R.ip[1]);
        Host h6=new Host("PC6","AA.BB.CC.DD","7.7.7.1",sw2,0);
        R.DHCP(h6, sw2);
        Host h7=new Host("PC7","EE.FF.GG.HH","7.7.7.1",sw2,1);
        R.DHCP(h7, sw2);
        Host h8=new Host("PC8","II.JJ.KK.LL","7.7.7.1",sw2,2);
        R.DHCP(h8, sw2);
        Host h9=new Host("PC9","MM.NN.OO.PP","7.7.7.1",sw2,3);
        R.DHCP(h9, sw2);
        Host h10=new Host("PC10","QQ.RR.SS.TT","7.7.7.1",sw2,4);
        R.DHCP(h10, sw2);
        
        
        sw.getDetails();
        sw2.getDetails();
        
        HTTP h=new HTTP();
        DNS d=new DNS();

        h6.ping(sw2, R, sw, h,d,"1.2.3.1",0);
        h6.ping(sw2, R, sw, h,d,"7.7.7.1",0);
        h6.ping(sw2, R, sw, h,d,"5.5.5.1",0);
        h6.ping(sw2, R, sw, h,d,"6.6.6.1",0);
        
        h6.ping(sw2, R, sw, h,d,"1.2.3.2",0);
        h6.ping(sw2, R, sw, h,d,"7.7.7.2",0);
        h6.ping(sw2, R, sw, h,d,"5.5.5.2",0);
        h6.ping(sw2, R, sw, h,d,"6.6.6.2",0);
        
        h6.ping(sw2, R, sw, h,d,"6.6.6.1",1);
        h6.ping(sw2, R, sw, h,d,"6.6.6.2",1);
        
        h6.ping(sw2, R, sw, h,d,"www.gogle.com",2);
        h6.ping(sw2, R, sw, h,d,"www.google.com",2);
    }
    
}
