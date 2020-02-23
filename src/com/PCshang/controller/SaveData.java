package com.PCshang.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.PCshang.util.ShowUtil;
import com.PCshang.view.MainFrame;


public class SaveData implements ActionListener {
	MainFrame mainfrm;
	
	
	public SaveData(MainFrame mainfrm) {
		this.mainfrm = mainfrm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//save_txt();
		save_excel();
	}

	 private void save_excel() {
		/*
		 try {
				WritableWorkbook wwb=null;
				//创建可写入的Excel工作薄
				
				String fileName="D://book.xls";
				File file=new File(fileName);
				
				if(!file.exists()) {
					file.createNewFile();
				}
				//以fileName 为文件名来创建一个Workbook
				wwb=Workbook.createWorkbook(file);
				//创建工作表
				WritableSheet ws=wwb.createSheet("Test Shee 1", 0);
				
				//查询数据库中所有的数据
				List<StuEntity> list=StuService.getAllByDb();
				//要插入到的Excel表格的行号，默认从0开始
				
				Label labelId=new Label(0, 0, "编号（id）");
				Label labelName=new Label(1, 0, "姓名（name）");
				Label labelSex=new Label(2, 0, "性别(sex)");
				Label labelNum=new Label(3, 0, "薪水（num）");
				
				ws.addCell(labelId);
				ws.addCell(labelName);
				ws.addCell(labelSex);
				ws.addCell(labelNum);
				
				for (int i = 0; i < list.size(); i++) {
					Label labelId_i=new Label(0, i+1, list.get(i).getId()+"");
					Label labelName_i=new Label(1, i+1, list.get(i).getName());
					Label labelSex_i=new Label(2, i+1, list.get(i).getSex());
					Label labelNum_i=new Label(3, i+1, list.get(i).getNum()+"");
					ws.addCell(labelId_i);
					ws.addCell(labelName_i);
					ws.addCell(labelSex_i);
					ws.addCell(labelNum_i);
				}
				
				//写进文档
				wwb.write();
				System.out.println("数据写入成功");
				//关闭Excel工作簿对象
				
				wwb.close();
				
			} catch (Exception e) {
				
				System.out.println("数据写入失败");
				e.printStackTrace();
			}
			*/
		 //HSSFWorkbook和Workbook有什么
		 /*
		 //创建Workbook类
		Workbook wb=new HSSFWorkbook();
		Sheet sheet=wb.createSheet("聊天信息表");//sheet是一张表，创建时可以传入表名字
		Row row1=sheet.createRow(0);//由表创建行，需要传入行标，由0开始
		row1.createCell(0).setCellValue("发送者");//得到行对象后，按列写入值
		row1.createCell(1).setCellValue("接收者");
		row1.createCell(2).setCellValue("消息内容");
		for (int i=1;i<list.size();i++) {
			Message m=list.get(i);
			//行
			Row row=sheet.createRow(i);
			row.createCell(0).setCellValue(m.getSender());
			row.createCell(1).setCellValue(m.getReceiver());
			row.createCell(2).setCellValue(m.getMessage());
		}
		
		//下面给出文件和输出流，然后把excel数据写入
		File file=new File("save//用户"+user.getUserName()+"的消息记录表.xls");
//		if(!file.exists())file.createNewFile();
		try(OutputStream ops=new FileOutputStream(file)){
			wb.write(ops);
			//wb.close();
			ops.close();//这里关闭Workbook或者关闭OutputStream都可以，应该是Workbook关闭的时候顺带关闭了OutputStream
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}

		  * 
		  * */
	
	}

	public void save_txt(){
	    	
	        JFileChooser chooser=new JFileChooser();
	        
	        //文件过滤器对象，能够过滤一些文件名称
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                   "文本文档(*.txt)", "txt");
	        
	        //设置过滤器
	        chooser.setFileFilter(filter);//设置默认文件过滤器为txt
	        //弹出保存对话框，设置弹出的父窗口。
	        int returnVal = chooser.showSaveDialog(mainfrm.contentPane);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	            
	        	File file = chooser.getSelectedFile();
	           
	            if(!ShowUtil.getFileSuffix(file.getName()).equals("txt")){
	                file = new File(file.getAbsolutePath()+".txt");
	            }
	            
	            try{
	                FileOutputStream fos = new FileOutputStream(file);
	                if(mainfrm.cb_HexReceive.isSelected()){
	                    String content[] = mainfrm.ta_receiveArea.getText().split(" ");
	                    for(int i = 0;i<content.length;i++){
	                        fos.write((content[i]+" ").getBytes("gbk"));
	                        if(i%16==15){
	                            fos.write("\r\n".getBytes("gbk"));
	                        }
	                    }
	                }else{
	                    byte[] outData=mainfrm.ta_receiveArea.getText().replace("\n","\r\n").getBytes("gbk");
	                    fos.write(outData);
	                }
	                fos.close();
	            }catch(Exception ee){
	                ShowUtil.infoShow("保存数据异常");
	            }
	        }
	        
	    }
	
	
	
	
	}
		
		