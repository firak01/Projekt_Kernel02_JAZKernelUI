package basic.zKernelUI.component;

import java.util.Hashtable;

import javax.swing.JFrame;

import basic.zKernel.IKernelUserZZZ;

public interface IActionCascadedZZZ extends IKernelUserZZZ{

	public  abstract KernelJFrameCascadedZZZ getFrameParent();
	public abstract void setFrameParent(KernelJFrameCascadedZZZ frameParent);

	public abstract IPanelCascadedZZZ getPanelParent();
	public abstract void setPanelParent(IPanelCascadedZZZ objPanel);
	
	public abstract ITrayZZZ getTrayParent();
	public abstract void setTrayParent(ITrayZZZ objTray);
}