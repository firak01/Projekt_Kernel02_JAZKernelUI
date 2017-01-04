package basic.zKernelUI.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import basic.zBasic.IConstantZZZ;

public class JFrameHelperZZZ implements IConstantZZZ{

	public static void setSizeDefault(JFrame objFrame){	
		main:{
			if(objFrame==null) break main;
			
			//setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			//Besser gefällt es mir die Bildschirmgröße variabel einzustellen
			Toolkit objKit = Toolkit.getDefaultToolkit();
			Dimension objDim = objKit.getScreenSize();
			int iWidth = objDim.width/2;
			int iHeight = objDim.height/2;		
			objFrame.setSize(iWidth, iHeight);
		}//end main:
	}
	
	public static void setSizeInScreenPercent(JFrame objFrame, int iPercent){	
		main:{
			if(objFrame==null) break main;
			if(iPercent <= 0) break main;
			
			//setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			//Besser gefällt es mir die Bildschirmgröße variabel einzustellen
			Toolkit objKit = Toolkit.getDefaultToolkit();
			Dimension objDim = objKit.getScreenSize();
			int iWidth = (objDim.width*iPercent)/100;
			int iHeight = (objDim.height*iPercent)/100;		
			objFrame.setSize(iWidth, iHeight);
		}//end main:
	}
}
