package basic.zKernelUI.component;

import java.util.Hashtable;

import javax.swing.JFrame;

import basic.zKernel.IKernelUserZZZ;

public interface IActionCascadedZZZ extends IKernelUserZZZ{

	public  abstract KernelJFrameCascadedZZZ getFrameParent();
	public abstract void setFrameParent(KernelJFrameCascadedZZZ frameParent);

	public abstract IPanelCascadedZZZ getPanelParent();
	public abstract void setPanelParent(IPanelCascadedZZZ objPanel);
	
	//public  abstract JFrame getFrameSub(String sAlias);
	//public abstract void setFrameSub(String sAlias, JFrame objFrame);
	
	//public abstract KernelJPanelCascadedZZZ getPanelSub(String sAlias);
	//public abstract void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objJPanel);
	
	//public abstract Hashtable getPanelSubAll(); 
}