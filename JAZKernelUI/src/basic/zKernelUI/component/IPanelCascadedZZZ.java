package basic.zKernelUI.component;


import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;


/**Interface defines methods for setting and getting handles on other JPanels
 * @author Lindhauer
 */
public interface IPanelCascadedZZZ extends IComponentCascadedUserZZZ, IFlagUserZZZ{
	public abstract KernelJPanelCascadedZZZ getPanelParent();
	public abstract void setPanelParent(KernelJPanelCascadedZZZ objPanel);
	
	public abstract KernelJPanelCascadedZZZ searchPanelRoot() throws ExceptionZZZ;
	
	public abstract KernelJPanelCascadedZZZ getPanelSub(String sAlias);
	public abstract void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objJPanel);
	
	
	public abstract KernelJFrameCascadedZZZ getFrameParent();
	public abstract KernelJDialogExtendedZZZ getDialogParent();
}
