package com.PCshang.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.PCshang.view.MainFrame;


public class TxThread extends Thread{
	OutputStream out;
	MainFrame mainfrm;
	public boolean stop;
	public boolean stopTxThread;
	
	public TxThread(OutputStream out,MainFrame mainfrm) {
		this.out = out;
	    this.mainfrm=mainfrm;
		stop=true;
		stopTxThread = false;
		
	}
	  @Override
	    public void run ()
	    {
	        while(stop){
	          
	            send();
	           
	            if(mainfrm.cb_timerSend.isSelected()){
	                try{
	                	mainfrm.btn_sendData.setText("停止");
	                    sleep(Integer.valueOf(mainfrm.tf_time.getText()));
	                }catch(InterruptedException ee){
	                    ShowUtil.infoShow("发送线程被中断");
	                }
	            }else{
	            	mainfrm.btn_sendData.setText("发送");
	                return;
	            }
	        }
	        mainfrm.txthread = null;
	    }

	    public void send(){
	        try
	        {
	            byte b[] = null;
	            if(mainfrm.cb_HexSend.isSelected()){//如果十六进制发送模式的话，直接发送16进制

	            	ArrayList<Byte> buf = new ArrayList<Byte>();
	                char[] hexCh=mainfrm.ta_sendArea.getText().toCharArray();
	                String set="0123456789abcdefABCDEF";
	                String reStr; 
	                Byte b5;
	                //这里需要改进一下，如果是一位的话自动往前补零？
	                for(int i=0;i<hexCh.length;i++){       
	                    if(set.contains(String.valueOf(hexCh[i]))){
	                        reStr=new String(hexCh,i,2);
	                        i++;
	                        b5=ByteUtil.HexToByte(reStr);
	                        buf.add(b5);
	                    }
	                }
	                byte[] buffer=new byte[buf.size()];
	                for(int i=0;i<buf.size();i++){
	                    buffer[i]=buf.get(i);
	                }
	                b=buffer;
	            }else{
	                try{
	                        b=mainfrm.ta_sendArea.getText().getBytes("gbk");
	                }catch(UnsupportedEncodingException ee){
	                       ShowUtil.infoShow("发送--错误的编码选择");
	                    }
	            }
	            
	            for(int i=0;i<b.length;i++){
	                if(stopTxThread){//立即结束发送
	                    return;
	                }
	                out.write(b,i,1);
	            }
	        }
	        catch ( IOException e ) {
	            ShowUtil.infoShow("串口已断开连接");

	        }catch(NullPointerException e2){
	            //串口连接未建立时发生的空指针异常。
	            ShowUtil.infoShow("串口未连接");
	        }
	    }
}
