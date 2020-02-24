package com.PCshang.util;

import com.PCshang.view.MainFrame;

public class TableDisplayUtil {
	
	public static void realtabledisplay(MainFrame mainfrm,float[] f,float id) {
		mainfrm.t_dbrealtime.getModel().setValueAt(id, 0,1);
		if(f.length>=3) {
			mainfrm.t_dbrealtime.getModel().setValueAt(f[0], 1,1);//行列第一个坐标是0，0
			mainfrm.t_dbrealtime.getModel().setValueAt(f[1], 2,1);
			mainfrm.t_dbrealtime.getModel().setValueAt(f[2], 3,1);
			if(f.length>=6) {
				mainfrm.t_dbrealtime.getModel().setValueAt(f[3],4,1);
				mainfrm.t_dbrealtime.getModel().setValueAt(f[4], 5,1);
				mainfrm.t_dbrealtime.getModel().setValueAt(f[5], 6,1);
				
			}
		}
	
		
		
	}
		
	
}
