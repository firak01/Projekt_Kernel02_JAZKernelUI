package basic.zKernelUI.component;

import java.awt.Component;

import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;

public interface IPanelDialogButtonZZZ {
	
	//In diesen Methoden koennen alternative Action-Listener zur Verfuegung gestellt werden, die (wie auch die default Actions) vom KernelActionCascadedZZZ erben muessen.  
	public abstract KernelActionCascadedZZZ getActionListenerButtonCancel(KernelJPanelCascadedZZZ panelButton);  
	public abstract KernelActionCascadedZZZ getActionListenerButtonOk(KernelJPanelCascadedZZZ panelButton);
	public abstract KernelActionCascadedZZZ getActionListenerButtonClose(KernelJPanelCascadedZZZ panelButton);
	
	//Dienen als Flag und sind �berschreibbar
	public abstract boolean isButtonOkAvailable();
	public abstract boolean isButtonCancelAvailable();
	public abstract boolean isButtonCloseAvailable();
}
