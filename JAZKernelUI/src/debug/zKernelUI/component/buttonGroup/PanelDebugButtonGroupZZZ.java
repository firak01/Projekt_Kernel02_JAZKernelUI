package debug.zKernelUI.component.buttonGroup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.DLG.Panel_NORTHZZZ;


public class PanelDebugButtonGroupZZZ extends KernelJPanelCascadedZZZ{
   private IKernelZZZ objKernelChoosen;
	
	public PanelDebugButtonGroupZZZ(IKernelZZZ objKernel, JFrame frameParent) throws ExceptionZZZ{
		super(objKernel, frameParent);
        this.setKernelConfigObject(objKernelChoosen);
				
		//### Layout Manager
		this.setLayout(new BorderLayout());
		
		PanelDebugButtonGroup_NORTHZZZ objPanelNorth = new PanelDebugButtonGroup_NORTHZZZ(objKernel, this);
		this.add(objPanelNorth, BorderLayout.NORTH); //Frontend hinzuf�gen
		this.setPanelSub("NORTH", objPanelNorth);       //Backend Hashtable hinzuf�gen
	
		
		
		//### PANEL SOUTH
//		PanelConfig_SOUTHZZZ objPanelSouth = new PanelConfig_SOUTHZZZ(objKernel, this, objKernelChoosen);
//		this.add(objPanelSouth, BorderLayout.SOUTH); //Frontend hinzuf�gen
//		this.setPanelSub("SOUTH", objPanelSouth);    //Backend Hashtable hinzuf�gen
//		
		//### PANEL CENTER
//		PanelConfig_CENTERZZZ objPanelCenter = new PanelConfig_CENTERZZZ(objKernel, this, objKernelChoosen);
//		this.add(objPanelCenter, BorderLayout.CENTER); //Frontend hinzuf�gen
//		this.setPanelSub("CENTER", objPanelCenter);       //Backend Hashtable hinzuf�gen
//		
	
	}

	
	public IKernelZZZ getKernelConfigObject(){
		return this.objKernelChoosen;
	}
	public void setKernelConfigObject(IKernelZZZ objKernelConfig){
		this.objKernelChoosen = objKernelConfig;
	}
	
	//################################################
	//Method implemented by interface
	
}//END Class
