package debug.zKernelUI.component.buttonGroup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernel.flag.IFlagZLocalEnabledZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.DLG.Panel_NORTHZZZ;


public class PanelDebugButtonGroupZZZ extends KernelJPanelCascadedZZZ implements IKernelModuleZZZ{
   
   	public PanelDebugButtonGroupZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
   		super(objKernel, frameParent);
        //this.setKernelConfigObject(objKernelChoosen);
   		PanelDebugButtonGroup_();
   	}
	public PanelDebugButtonGroupZZZ(IKernelZZZ objKernel, JFrame frameParent) throws ExceptionZZZ{
		super(objKernel, frameParent);
        //this.setKernelConfigObject(objKernelChoosen);
		PanelDebugButtonGroup_();
	}

	
//	public IKernelZZZ getKernelConfigObject(){
//		return this.objKernelChoosen;
//	}
//	public void setKernelConfigObject(IKernelZZZ objKernelConfig){
//		this.objKernelChoosen = objKernelConfig;
//	}
	
	private boolean PanelDebugButtonGroup_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		//### Dieses Panel ist als Modul konkfiguriert
        String stemp; boolean btemp;       
        stemp = IKernelModuleZZZ.FLAGZ.ISKERNELMODULE.name();
		btemp = this.setFlag(stemp, true);	
		if(btemp==false){
			ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;		 
		}
		
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
		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//################################################
	//Method implemented by interface
	
}//END Class
