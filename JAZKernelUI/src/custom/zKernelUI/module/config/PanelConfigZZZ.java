package custom.zKernelUI.module.config;

import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.DLG.Panel_NORTHZZZ;


public class PanelConfigZZZ extends KernelJPanelCascadedZZZ{
   private IKernelZZZ objKernelChoosen;
	
	public PanelConfigZZZ(IKernelZZZ objKernel, JFrame frameParent, IKernelZZZ objKernelChoosen) throws ExceptionZZZ{
		super(objKernel, frameParent);
		PanelConfigNew_(objKernelChoosen);
	}

	public PanelConfigZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent, IKernelZZZ objKernelChoosen) throws ExceptionZZZ{
		super(objKernel, frameParent);
		PanelConfigNew_(objKernelChoosen);
	}
	public IKernelZZZ getKernelConfigObject(){
		return this.objKernelChoosen;
	}
	public void setKernelConfigObject(IKernelZZZ objKernelConfig){
		this.objKernelChoosen = objKernelConfig;
	}
	
	//################################################
	//Method implemented by interface
	
	//################################################
	private  boolean PanelConfigNew_(IKernelZZZ objKernelChoosen) throws ExceptionZZZ {
	
		this.setKernelConfigObject(objKernelChoosen);
		
		//### Layout Manager
		this.setLayout(new BorderLayout());
			
		//### PANEL SOUTH
		PanelConfig_SOUTHZZZ objPanelSouth = new PanelConfig_SOUTHZZZ(objKernel, this, objKernelChoosen);
		this.add(objPanelSouth, BorderLayout.SOUTH); //Frontend hinzuf�gen
		this.setPanelSub("SOUTH", objPanelSouth);    //Backend Hashtable hinzuf�gen
		
		//### PANEL CENTER
		PanelConfig_CENTERZZZ objPanelCenter = new PanelConfig_CENTERZZZ(objKernel, this, objKernelChoosen);
		this.add(objPanelCenter, BorderLayout.CENTER); //Frontend hinzuf�gen
		this.setPanelSub("CENTER", objPanelCenter);       //Backend Hashtable hinzuf�gen
		
		return true;
	}

	
}//END Class
