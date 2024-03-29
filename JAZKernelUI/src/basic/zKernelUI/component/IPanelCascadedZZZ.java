package basic.zKernelUI.component;


import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernel.flag.IFlagZLocalUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;


/**Interface defines methods for setting and getting handles on other JPanels
 * @author Lindhauer
 */
public interface IPanelCascadedZZZ extends IComponentCascadedUserZZZ, IKernelModuleZZZ, IKernelProgramZZZ,IFlagZUserZZZ,IFlagZLocalUserZZZ{
	public enum FLAGZLOCAL {
		SKIPDEBUGUI;
	}
	
	public String getAlias();
	public void setAlias(String sAlias);
	
	public abstract IPanelCascadedZZZ getPanelParent();
	public abstract void setPanelParent(IPanelCascadedZZZ objPanel);
	
	public abstract IPanelCascadedZZZ searchPanel(String string) throws ExceptionZZZ;
	public abstract IPanelCascadedZZZ searchPanelRoot() throws ExceptionZZZ;
	public abstract IPanelCascadedZZZ searchPanelSub(String sAlias) throws ExceptionZZZ;
	
	public abstract IPanelCascadedZZZ getPanelSub(String sAlias);
	public abstract void setPanelSub(String sAlias, IPanelCascadedZZZ objJPanel);
	
	
	public abstract KernelJFrameCascadedZZZ getFrameParent();
	public abstract KernelJDialogExtendedZZZ getDialogParent();
	
	public abstract JComponent searchComponent(String sKeyComponent);
	public abstract JComponent searchComponent(String sKeyComponent, boolean bInNeighbours);
	
	public Hashtable<String,IPanelCascadedZZZ> getHashtablePanel();
	
	
}
