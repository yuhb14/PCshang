package com.PCshang.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteUtil {

	public static byte[] concat(byte[] firstArray, byte[] secondArray) {

		if (firstArray == null) {
			byte[] bytes = new byte[secondArray.length];
			System.arraycopy(secondArray, 0, bytes, 0, secondArray.length);
			return bytes;
		} else if (secondArray == null) {
			byte[] bytes = new byte[firstArray.length];
			System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
			return bytes;
		} else {
			byte[] bytes = new byte[firstArray.length + secondArray.length];
			System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
			System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
			return bytes;
		}

	}

	public static String byteArrayToHexString(byte[] array) {
		if (array == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buffer.append(byteToHex(array[i]));
		}
		return buffer.toString();
	}

	public static String byteToHex(byte b) {

		String res = "";
		int a = Integer.valueOf(b & 0xFF);
		res = Integer.toHexString(a);
		res = res.toUpperCase();
		if (res.length() == 1) {
			res = "0" + res;
		}
		return res;
	}

	public static byte HexToByte(String s) {
		Integer add;
		s = s.toUpperCase();
		char[] ch = s.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			add = ch[0] - 'A' + 10;
		} else {
			add = ch[0] - '0';
		}
		add <<= 4;
		if (ch[1] >= 'A' && ch[1] <= 'F') {
			add += ch[1] - 'A' + 10;
		} else {
			add += ch[1] - '0';
		}
		return add.byteValue();
	}

	public static float[] byteToFloat(byte[] received_buf, int num) {
		int len = (num-2)/4;
		float[] res = new float[len];
		
		byte[][] buffer = new byte[len][4];
		//将得到
		for(int i =0; i<len; i++) {
			for(int j = 0; j<4; j++) {
				buffer[i][j] = received_buf[4*i+j+1];
			}
		}
		for(int i=0;i<len;i++) {
			res[i] =HexToFloat(buffer[i]); 
		}
		return res;
	}

	
	private static float HexToFloat(byte[] data) {
		
		 /*
    	//方法1 流输入，适用于ME/SE环境
    	//默认大端数，如果小端数，可以先翻转数组
    	DataInputStream dis=new DataInputStream(new ByteArrayInputStream(data));
    	float f = 0.0f;
		try {
			f = dis.readFloat();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(f);
    	*/
		
		////方法2 缓存输入，适用于SE/EE环境
		//方法二，网上说方法二好
		
    	ByteBuffer buf=ByteBuffer.allocateDirect(4); //无额外内存的直接缓存
    	//buf=buf.order(ByteOrder.LITTLE_ENDIAN);//默认大端，小端用这行
    	buf.put(data);
    	buf.rewind();
    	float f2=buf.getFloat();
    	return f2;
    	//System.out.println(f2);
    	
		
	}

}
