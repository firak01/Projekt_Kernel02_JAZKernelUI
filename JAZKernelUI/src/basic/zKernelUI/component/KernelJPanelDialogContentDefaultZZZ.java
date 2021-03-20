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

public class KernelJPanelDialogContentDefaultZZZ extends KernelJPanelCascadedZZZ implements IConstantZZZ,  IKernelUserZZZ, IObjectZZZ{
	private String sText4ContentDefault="";
	private boolean bIsTextContentDefaultAvailable=false;
	
	public KernelJPanelDialogContentDefaultZZZ() throws ExceptionZZZ{
		super();
	}
	
	public KernelJPanelDialogContentDefaultZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, String sText4ContentDefault){
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
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
	}

	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}

	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub		
	}
	
	
	//### Action Klassen
	//	 	   Standard listeners *************************************************************


}//End class ...panel...
