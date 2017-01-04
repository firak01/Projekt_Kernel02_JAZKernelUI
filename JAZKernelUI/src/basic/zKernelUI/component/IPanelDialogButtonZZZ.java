package basic.zKernelUI.component;

import java.awt.Component;

import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;

public interface IPanelDialogButtonZZZ {

	
		
	//In diesen Methoden k�nnen alternative Action-Listener zur Verf�gung gestellt werden, die (wie auch die default Actions) vom KernelActionCascadedZZZ erben m�ssen.  
	public abstract KernelActionCascadedZZZ getActionListenerButtonCancel(KernelJPanelCascadedZZZ panelButton);  
	public abstract KernelActionCascadedZZZ getActionListenerButtonOk(KernelJPanelCascadedZZZ panelButton);
	
	//Dienen als Flag und sind �berschreibbar
	public abstract boolean isButtonOkAvailable();
	public abstract boolean isButtonCancelAvailable();
}
