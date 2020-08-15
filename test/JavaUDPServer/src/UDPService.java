import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UDPService {
	private final static int PORT = 8881; 
	public static void main(String[] args) {
		//"swj_0_"+ip+"_"+port+"_"+"xiaomi"+"_conn";
        //客户端发送消息统一格式： wsj_连接状态(1表示要转发，0表示不转发)_接收方ip_接收方端口_设备名称_发送内容
		 new Thread(new Runnable() {
             @Override
             public void run() {
            	 try (DatagramSocket socket = new DatagramSocket(PORT)) {
         			List<EqModel> listEqs = new ArrayList<EqModel>();  
         			while (true) {
         				try {
         					DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
         					socket.receive(request);         					
         					String data2=new String(request.getData(),0,request.getLength()); 
         					String[] res=data2.split("_");
         					EqModel m=new EqModel();
         					m.setIP(request.getAddress().toString());
         					m.setPort(request.getPort());
         					m.setFlag(res[0]);    
         					m.setEqName(res[4]);
         					boolean hasrecord=false;
         					for(EqModel item:listEqs)
         					{
         						if(item.getFlag().equals(m.getFlag()) && item.getIP().equals(m.getIP()) && item.getPort()==m.getPort())
         						{
         							hasrecord=true;
         							break;
         						}					
         					}
         					if(!hasrecord)
         					{
         						listEqs.add(m);
         					}	
         					
         					String swjstr="";
         					String xwjstr="";
         					String swjip="";
         					int swjport=0;
         					String xwjip="";
         					int xwjport=0;
         					
         					for(EqModel item:listEqs)
         					{
         						if(item.getFlag().equals("swj"))
         						{
         							swjstr+=item.getEqName()+"_"+item.getIP().replace("/","")+"_"+item.getPort()+"_"+item.getFlag()+"_"+res[5];	         							
         							swjip=item.getIP().replace("/","");
         							swjport=item.getPort();
         						}
         						if(item.getFlag().equals("xwj"))
         						{
         							xwjstr+=item.getEqName()+"_"+item.getIP().replace("/","")+"_"+item.getPort()+"_"+item.getFlag()+"_"+res[5];	
         							xwjip=item.getIP().replace("/","");
         							xwjport=item.getPort();
         						}
         						
         							
         					}
         					
         					String daytime = new Date().toString();//系统时间        					
         					if(res[1].equals("1"))
         					{
         						String returnstr="";
         						String sendip="";
         						int sendport=0;
         						if(res[0].equals("swj"))
             					{
             						returnstr=xwjstr;
             						sendip=xwjip;
             						sendport=xwjport;
             					}
             					if(res[0].equals("xwj"))
             					{
             						returnstr=swjstr;
             						sendip=swjip;
             						sendport=swjport;
             					}
             					
             					byte[] data = returnstr.getBytes("utf-8");
             					//DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
             					DatagramPacket response = new DatagramPacket(data, data.length,InetAddress.getByName(sendip),sendport);
             					socket.send(response);
             					System.out.println("i get:"+daytime + "设备列表 "+listEqs.size()+" " + request.getAddress()+"data:"+data2);
             					
         					}
         					
         					if(res[1].equals("0"))
         					{
         						String eqstr="";
         						for(EqModel item:listEqs)
             					{
         							eqstr+=item.getEqName()+"_"+item.getFlag()+"_"+item.getIP().replace("/","")+"_"+item.getPort()+"\r\n";	         							
             					}      						
             					byte[] data = eqstr.getBytes("utf-8");
             					DatagramPacket response = new DatagramPacket(data, data.length,request.getAddress(),request.getPort());
             					socket.send(response);
             					System.out.println("设备列表 "+listEqs.size()+" " + request.getAddress()+"data:"+data2);
         					}
         					
         					
         				} catch (IOException e) {
         					e.printStackTrace();
         				}
         			}
         		} catch(IOException e)
         		{
         			
         		}
             }
         }).start();
	}
}