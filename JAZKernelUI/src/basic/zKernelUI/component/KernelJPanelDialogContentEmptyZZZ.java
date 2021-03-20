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

public class KernelJPanelDialogContentEmptyZZZ extends KernelJPanelCascadedZZZ implements IConstantZZZ,  IKernelUserZZZ, IObjectZZZ{
	
	public KernelJPanelDialogContentEmptyZZZ() throws ExceptionZZZ{
		super();
	}
	
	public KernelJPanelDialogContentEmptyZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended){
		super(objKernel, dialogExtended);
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
