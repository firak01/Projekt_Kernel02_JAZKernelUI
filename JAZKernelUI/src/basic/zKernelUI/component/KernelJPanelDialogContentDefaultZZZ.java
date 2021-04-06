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
import custom.zKernel.LogZZZ;

public class KernelJPanelDialogContentDefaultZZZ extends KernelJPanelCascadedZZZ{
	private String sText4ContentDefault="";
	private boolean bIsTextContentDefaultAvailable=false;
	
	public KernelJPanelDialogContentDefaultZZZ() throws ExceptionZZZ{
		super();
	}
	
	public KernelJPanelDialogContentDefaultZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, String sText4ContentDefault) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		this.sText4ContentDefault= sText4ContentDefault;
		this.addContentDefault();
	}
	
	public boolean addContentDefault(){
		boolean bReturn=true;
		main:{
		if(this.sText4ContentDefault==null) break main;
		
		JLabel label = new JLabel(this.sText4ContentDefault);
		this.add(label);
		
		}//end main:
		return bReturn;
	}
	
	//#### Getter / Setter ##########################


	//#### Interfaces ##############################
	
	
	//### Action Klassen
	//	 	   Standard listeners *************************************************************


}//End class ...panel...
