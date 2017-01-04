package basic.zKernelUI.component;

import javax.swing.JFrame;

public interface IFrameCascadedZZZ {

	public  abstract KernelJFrameCascadedZZZ getFrameParent();

	public abstract void setFrameParent(KernelJFrameCascadedZZZ frameParent);

	public  abstract JFrame getFrameSub(String sAlias);

	public abstract void setFrameSub(String sAlias, JFrame objFrame);
	
	public abstract KernelJPanelCascadedZZZ getPanelSub(String sAlias);
	public abstract void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objJPanel);
}