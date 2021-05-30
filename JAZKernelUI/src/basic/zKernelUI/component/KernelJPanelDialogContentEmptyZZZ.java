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
import basic.zKernel.flag.IFlagUserZZZ;
import custom.zKernel.LogZZZ;

public class KernelJPanelDialogContentEmptyZZZ extends KernelJPanelCascadedZZZ{
	
	public KernelJPanelDialogContentEmptyZZZ() throws ExceptionZZZ{
		super();
	}
	
	public KernelJPanelDialogContentEmptyZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		KernelJPanelDialogContentEmptyNew_();
	}
	
	private boolean KernelJPanelDialogContentEmptyNew_() {
		boolean bReturn = false;
		main:{			
			if(this.getFlag(IFlagUserZZZ.FLAGZ.DEBUG.name())) {
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
