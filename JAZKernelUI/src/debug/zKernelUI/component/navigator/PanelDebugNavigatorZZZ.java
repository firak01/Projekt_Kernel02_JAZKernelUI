package debug.zKernelUI.component.navigator;

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
import basic.zKernel.flag.IFlagZLocalEnabledZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.component.PanelContentEmptyZZZ;
import custom.zKernelUI.module.config.DLG.Panel_NORTHZZZ;


public class PanelDebugNavigatorZZZ extends KernelJPanelCascadedZZZ implements IKernelModuleZZZ{
   private IKernelZZZ objKernelChoosen;
	
	public PanelDebugNavigatorZZZ(IKernelZZZ objKernel, JFrame frameParent) throws ExceptionZZZ{
		super(objKernel, frameParent);
        this.setKernelConfigObject(objKernelChoosen);
		
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
		
		PanelDebugNavigator_WESTZZZ objPanelWest = new PanelDebugNavigator_WESTZZZ(objKernel, this);
		this.add(objPanelWest, BorderLayout.WEST); //Frontend hinzufuegen
		this.setPanelSub("WEST", objPanelWest);       //Backend Hashtable hinzufuegen
		
		
		PanelContentEmptyZZZ objPanelCenterEmpty =   new PanelContentEmptyZZZ(objKernel, this);
		this.add(objPanelCenterEmpty, BorderLayout.CENTER); //Frontend hinzufuegen
		this.setPanelSub("CENTER",objPanelCenterEmpty);       //Backend Hashtable hinzufuegen
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
