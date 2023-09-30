package basic.zKernelUI.component;

import java.util.Hashtable;

import javax.swing.JFrame;

import basic.zKernel.flag.IFlagZUserZZZ;

public interface IFrameCascadedZZZ extends IFlagZUserZZZ{
	public enum FLAGZ{
		COMPONENT_DRAGGABLE,TERMINATE;
	}

	public  abstract KernelJFrameCascadedZZZ getFrameParent();

	public abstract void setFrameParent(KernelJFrameCascadedZZZ frameParent);

	public  abstract JFrame getFrameSub(String sAlias);

	public abstract void setFrameSub(String sAlias, JFrame objFrame);
	
	public abstract IPanelCascadedZZZ getPanelSub(String sAlias);
	public abstract void setPanelSub(String sAlias, IPanelCascadedZZZ objJPanel);
	
	public abstract Hashtable getPanelSubAll(); 
}