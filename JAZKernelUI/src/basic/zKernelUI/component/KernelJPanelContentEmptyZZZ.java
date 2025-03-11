package basic.zKernelUI.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import custom.zKernel.LogZZZ;

public class KernelJPanelContentEmptyZZZ extends KernelJPanelCascadedZZZ{	
	public KernelJPanelContentEmptyZZZ() throws ExceptionZZZ{
		super();
	}
	
	public KernelJPanelContentEmptyZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		KernelJPanelContentEmptyNew_();
	}
	
	public KernelJPanelContentEmptyZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameExtended) throws ExceptionZZZ{
		super(objKernel, frameExtended);
		KernelJPanelContentEmptyNew_();
	}
	
	public KernelJPanelContentEmptyZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelExtended) throws ExceptionZZZ{
		super(objKernel, panelExtended);
		KernelJPanelContentEmptyNew_();		
	}
	
	private boolean KernelJPanelContentEmptyNew_() {
		boolean bReturn = false;
		main:{			
			//if(this.getFlag(IFlagUserZZZ.FLAGZ.DEBUG.name())) {
			//!!! TODOGOON: Das soll in KernelJPanelCascadedZZZ.createDebugUI() gemacht werden!!!
			if(this.getFlag(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())) {
				JLabel label = new JLabel(this.getClass().getName());
				this.add(label);
			}		
			bReturn = true;
		}
		return bReturn;
	}
	
	//#### Getter / Setter ##########################


	//#### Interfaces ##############################
			
	//### Action Klassen
	//	 	   Standard listeners *************************************************************


}//End class ...panel...
