package custom.zKernelUI.component;

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
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernelUI.component.KernelJDialogExtendedZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelContentEmptyZZZ;
import basic.zKernelUI.component.KernelJPanelDialogContentEmptyZZZ;
import custom.zKernel.LogZZZ;

public class PanelContentEmptyZZZ extends KernelJPanelContentEmptyZZZ {
	
	public PanelContentEmptyZZZ() throws ExceptionZZZ{
		super();
	}
	
	public PanelContentEmptyZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		PanelContentEmptyNew_();
	}
	
	public PanelContentEmptyZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameExtended) throws ExceptionZZZ{
		super(objKernel, frameExtended);
		PanelContentEmptyNew_();		
	}
	
	public PanelContentEmptyZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelExtended) throws ExceptionZZZ{
		super(objKernel, panelExtended);
		PanelContentEmptyNew_();		
	}
	
	
	
	private boolean PanelContentEmptyNew_() {
		boolean bReturn = false;
		main:{
			JLabel label2 = new JLabel("TESTE: Empty ContentPanel");
			this.add(label2);
			bReturn=true;
		}//end main;
		return bReturn;
	}
	
	//#### Getter / Setter ##########################


	//#### Interfaces ##############################
	
	
	//### Action Klassen
	//	 	   Standard listeners *************************************************************


}//End class ...panel...
