package basic.zKernelUI.component;

import javax.swing.JComponent;

import basic.zKernel.IKernelUserZZZ;

public interface IComponentCascadedUserZZZ extends IKernelUserZZZ{

	public abstract JComponent getComponent(String sAlias);
	public abstract void setComponent(String sAlias, JComponent objComponent);

}
