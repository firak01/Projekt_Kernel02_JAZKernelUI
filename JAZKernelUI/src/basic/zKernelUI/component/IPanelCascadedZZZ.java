package basic.zKernelUI.component;


import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernel.flag.IFlagZLocalEnabledZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;


/**Interface defines methods for setting and getting handles on other JPanels
 * @author Lindhauer
 */
public interface IPanelCascadedZZZ extends IComponentCascadedUserZZZ, IKernelModuleZZZ, IKernelProgramZZZ,IFlagZEnabledZZZ,IFlagZLocalEnabledZZZ{
	public enum FLAGZLOCAL {
		SKIPDEBUGUI;
	}
	
	public String getAlias() throws ExceptionZZZ;
	public void setAlias(String sAlias) throws ExceptionZZZ;
	
	public abstract IPanelCascadedZZZ getPanelParent() throws ExceptionZZZ;
	public abstract void setPanelParent(IPanelCascadedZZZ objPanel) throws ExceptionZZZ;
	
	public abstract IPanelCascadedZZZ searchPanel(String string) throws ExceptionZZZ;
	public abstract IPanelCascadedZZZ searchPanelRoot() throws ExceptionZZZ;
	public abstract IPanelCascadedZZZ searchPanelSub(String sAlias) throws ExceptionZZZ;
	
	public abstract IPanelCascadedZZZ getPanelSub(String sAlias) throws ExceptionZZZ;
	public abstract void setPanelSub(String sAlias, IPanelCascadedZZZ objJPanel) throws ExceptionZZZ;
	
	
	public abstract KernelJFrameCascadedZZZ getFrameParent() throws ExceptionZZZ;
	public abstract KernelJDialogExtendedZZZ getDialogParent() throws ExceptionZZZ;
	
	public abstract JComponent searchComponent(String sKeyComponent) throws ExceptionZZZ;
	public abstract JComponent searchComponent(String sKeyComponent, boolean bInNeighbours) throws ExceptionZZZ;
	
	public Hashtable<String,IPanelCascadedZZZ> getHashtablePanel() throws ExceptionZZZ;
	
	
}
