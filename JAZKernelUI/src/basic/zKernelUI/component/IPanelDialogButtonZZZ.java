package basic.zKernelUI.component;

import java.awt.Component;

import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;

public interface IPanelDialogButtonZZZ {

	
		
	//In diesen Methoden können alternative Action-Listener zur Verfügung gestellt werden, die (wie auch die default Actions) vom KernelActionCascadedZZZ erben müssen.  
	public abstract KernelActionCascadedZZZ getActionListenerButtonCancel(KernelJPanelCascadedZZZ panelButton);  
	public abstract KernelActionCascadedZZZ getActionListenerButtonOk(KernelJPanelCascadedZZZ panelButton);
	
	//Dienen als Flag und sind überschreibbar
	public abstract boolean isButtonOkAvailable();
	public abstract boolean isButtonCancelAvailable();
}
