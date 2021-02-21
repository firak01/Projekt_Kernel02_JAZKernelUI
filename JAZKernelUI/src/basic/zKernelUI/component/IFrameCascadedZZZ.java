package basic.zKernelUI.component;

import java.util.Hashtable;

import javax.swing.JFrame;

import basic.zKernel.flag.IFlagZZZ;

public interface IFrameCascadedZZZ extends IFlagZZZ{

	public  abstract KernelJFrameCascadedZZZ getFrameParent();

	public abstract void setFrameParent(KernelJFrameCascadedZZZ frameParent);

	public  abstract JFrame getFrameSub(String sAlias);

	public abstract void setFrameSub(String sAlias, JFrame objFrame);
	
	public abstract KernelJPanelCascadedZZZ getPanelSub(String sAlias);
	public abstract void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objJPanel);
	
	public abstract Hashtable getPanelSubAll(); 
}