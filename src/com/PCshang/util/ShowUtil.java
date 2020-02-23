package com.PCshang.util;

import javax.swing.JOptionPane;

public class ShowUtil {

	 public static void infoShow(String info){
	        JOptionPane.showMessageDialog(null,info,"UartAssis",JOptionPane.ERROR_MESSAGE);
	    }
	 
	 //这个是什么工具
	 public static String getFileSuffix(String fileName){

	        return fileName.substring(fileName.lastIndexOf(".")+1);
	    }
	
}
