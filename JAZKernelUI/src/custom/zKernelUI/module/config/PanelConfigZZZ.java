package custom.zKernelUI.module.config;

import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.DLG.Panel_NORTHZZZ;


public class PanelConfigZZZ extends KernelJPanelCascadedZZZ{
   private KernelZZZ objKernelChoosen;
	
	public PanelConfigZZZ(KernelZZZ objKernel, JFrame frameParent, KernelZZZ objKernelChoosen) throws ExceptionZZZ{
		super(objKernel, frameParent);
        this.setKernelConfigObject(objKernelChoosen);
				
		//### Layout Manager
		this.setLayout(new BorderLayout());
				
		//### PANEL SOUTH
		PanelConfig_SOUTHZZZ objPanelSouth = new PanelConfig_SOUTHZZZ(objKernel, this, objKernelChoosen);
		this.add(objPanelSouth, BorderLayout.SOUTH); //Frontend hinzufügen
		this.setPanelSub("SOUTH", objPanelSouth);    //Backend Hashtable hinzufügen
		
		//### PANEL CENTER
		PanelConfig_CENTERZZZ objPanelCenter = new PanelConfig_CENTERZZZ(objKernel, this, objKernelChoosen);
		this.add(objPanelCenter, BorderLayout.CENTER); //Frontend hinzufügen
		this.setPanelSub("CENTER", objPanelCenter);       //Backend Hashtable hinzufügen
		
	
	}

	
	public KernelZZZ getKernelConfigObject(){
		return this.objKernelChoosen;
	}
	public void setKernelConfigObject(KernelZZZ objKernelConfig){
		this.objKernelChoosen = objKernelConfig;
	}
	
	//################################################
	//Method implemented by interface
	
}//END Class
