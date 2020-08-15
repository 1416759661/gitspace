package com.yyjcw.testlot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatViewActivity extends Activity {
    private DatagramSocket ds = null;
    private InetAddress serverAddress = null;
    private static final String TAG = "ChatViewActivity";
    private static int port = 6000;
    private static String sendData = "AT";
    private TextView tvresmsg;
    private String msg;
    private boolean flag=true; 
    private String ip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_view);
		try {
		  ds = new DatagramSocket(8888);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        Button button = (Button)findViewById(R.id.btnsend);
        Button btnconn = (Button)findViewById(R.id.btnconn);
        //"swj_0_"+ip+"_"+port+"_"+"xiaomi"+"_conn";
        //发送消息统一格式： wsj_连接状态(1表示要转发，0表示不转发)_接收方ip_接收方端口_设备名称_发送内容
        btnconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	flag=false;
            	EditText tbip = (EditText) findViewById(R.id.tbip);
                EditText tbport = (EditText) findViewById(R.id.tbport);
                EditText tbmsg = (EditText) findViewById(R.id.tbmsg);
                Context ctx=getApplicationContext();
            	ip = tbip.getText().toString();
            	port =Integer.parseInt(tbport.getText().toString());
                tvresmsg = (TextView) findViewById(R.id.tvresmsg);                
                Thread t1=new Thread()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                        	 //"swj_0_"+ip+"_"+port+"_"+"xiaomi"+"_conn";
                            //发送消息统一格式： wsj_连接状态(1表示要转发，0表示不转发)_接收方ip_接收方端口_设备名称_发送内容
                        	String connmsg="swj_0_"+ip+"_"+port+"_"+"xiaomi"+"_conn";
                            byte[] buf =connmsg.getBytes("utf-8");
                            DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName(ip),port);
                            ds.send(dp);
                            String recvStr="";  
                            byte[] recvBuf = new byte[200];
                            DatagramPacket recvPacket= new DatagramPacket(recvBuf , recvBuf.length);
                            ds.receive(recvPacket);
                            recvStr+= new String(recvPacket.getData() , 0 ,recvPacket.getLength());
                            Message msg=new Message();
                            msg.obj=recvStr;
                            uiHandler.sendMessage(msg);                       
                            
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                t1.start();              
            	Toast.makeText(ChatViewActivity.this, "已请求连接服务器", Toast.LENGTH_SHORT).show();
            }
        });
	
	
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	flag=false;
            	EditText tbip = (EditText) findViewById(R.id.tbip);
                EditText tbport = (EditText) findViewById(R.id.tbport);
                EditText tbmsg = (EditText) findViewById(R.id.tbmsg);
                Context ctx=getApplicationContext();
            	ip = tbip.getText().toString();
            	port =Integer.parseInt(tbport.getText().toString());
                msg = tbmsg.getText().toString();
                tvresmsg = (TextView) findViewById(R.id.tvresmsg);                
                Thread t=new Thread()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
               
                        	msg="swj_1_"+ip+"_"+port+"_xiaomi_"+msg;
                            byte[] buf =msg.getBytes("utf-8");
                            DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName(ip),port);
                            ds.send(dp);
                            String recvStr="";   
                            
                            while(flag)
                            {
                            	byte[] recvBuf = new byte[200];
                                DatagramPacket recvPacket= new DatagramPacket(recvBuf , recvBuf.length);
                                ds.receive(recvPacket);
                                recvStr+= new String(recvPacket.getData() , 0 ,recvPacket.getLength());
                                Message msg=new Message();
                                msg.obj=recvStr;
                                uiHandler.sendMessage(msg);
                                
                            }                            
                            ds.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                flag=true;
                t.start();              
            	Toast.makeText(ChatViewActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	protected Handler  uiHandler=new Handler(){
		@Override
    	public void handleMessage(Message msg)
        {
            Log.d("收到消息", "更新界面");
            tvresmsg.setText("收到: \t" + msg.obj.toString());
            super.handleMessage(msg);
        }
        
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
