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
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernelUI.component.KernelJDialogExtendedZZZ;
import basic.zKernelUI.component.KernelJPanelDialogContentEmptyZZZ;
import custom.zKernel.LogZZZ;

public class PanelDialogContentEmptyZZZ extends KernelJPanelDialogContentEmptyZZZ {
	
	public PanelDialogContentEmptyZZZ() throws ExceptionZZZ{
		super();
	}
	
	public PanelDialogContentEmptyZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
	}
	
	private boolean PanelDialogContentEmptyNew_() {
		boolean bReturn = false;
		main:{
			
			bReturn=true;
		}//end main;
		return bReturn;
	}
	
	//#### Getter / Setter ##########################


	//#### Interfaces ##############################
	
	
	//### Action Klassen
	//	 	   Standard listeners *************************************************************


}//End class ...panel...
