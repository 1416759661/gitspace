
public class EqModel {
  	 private String EqName;  //设备名称
	 private String IP;  
	 private int Port;  
	 private String Flag;  //1表示上位机 0表示下位机
	 
	 public String getEqName() {  
	        return EqName;  
		 }  
		  
	 public void setEqName(String eqname) {  
	     this.EqName = eqname;  
	 }  
		 
	 public String getIP() {  
        return IP;  
	 }  
	  
	 public void setIP(String ip) {  
	     this.IP = ip;  
	 }  
	 
	 public int getPort() {  
	     return Port;  
	 }  
	  
	 public void setPort(int port) {  
	     this.Port=port;  
	 }  
	 
	 public String getFlag() {  
	     return Flag;  
	 }
	 
	 public void setFlag(String flag) {  
	     this.Flag = flag;  
	 }  
}
