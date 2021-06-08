package debug.zKernelUI.component.buttonSwitchLabelGroup;

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
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.DLG.Panel_NORTHZZZ;


public class PanelDebugButtonSwitchLabelGroupZZZ extends KernelJPanelCascadedZZZ implements IKernelModuleZZZ{
   private IKernelZZZ objKernelChoosen;
	
	public PanelDebugButtonSwitchLabelGroupZZZ(IKernelZZZ objKernel, JFrame frameParent) throws ExceptionZZZ{
		super(objKernel, frameParent);
        this.setKernelConfigObject(objKernelChoosen);
		
        //### Dieses Panel ist als Modul konkfiguriert
        String stemp; boolean btemp;       
        stemp = IKernelModuleZZZ.FLAGZ.ISKERNELMODULE.name();
		btemp = this.setFlagZ(stemp, true);	
		if(btemp==false){
			ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;		 
		}
		
		//### Layout Manager		 
		this.setLayout(new BorderLayout());
		
		PanelDebugButtonSwitchLabelGroup_NORTHZZZ objPanelNorth = new PanelDebugButtonSwitchLabelGroup_NORTHZZZ(objKernel, this);
		this.add(objPanelNorth, BorderLayout.NORTH); //Frontend hinzufuegen
		this.setPanelSub("NORTH", objPanelNorth);       //Backend Hashtable hinzufuegen
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
