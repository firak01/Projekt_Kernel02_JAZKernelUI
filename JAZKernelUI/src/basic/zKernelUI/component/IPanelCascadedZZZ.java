package basic.zKernelUI.component;


import javax.swing.JFrame;
import javax.swing.JPanel;


/**Interface defines methods for setting and getting handles on other JPanels
 * @author Lindhauer
 */
public abstract interface IPanelCascadedZZZ extends IComponentCascadedUserZZZ{
	public abstract KernelJPanelCascadedZZZ getPanelParent();
	public abstract void setPanelParent(KernelJPanelCascadedZZZ objPanel);
	
	public abstract KernelJPanelCascadedZZZ searchPanelRoot();
	
	public abstract KernelJPanelCascadedZZZ getPanelSub(String sAlias);
	public abstract void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objJPanel);
}
