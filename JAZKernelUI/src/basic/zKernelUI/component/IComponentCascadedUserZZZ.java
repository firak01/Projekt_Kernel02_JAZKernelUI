package basic.zKernelUI.component;

import javax.swing.JComponent;

import basic.zKernel.IKernelUserZZZ;

public interface IComponentCascadedUserZZZ extends IKernelUserZZZ{
	public enum FLAGZ{
		DEBUGUI_PANELLABEL_ON;
	}
	public abstract JComponent getComponent(String sAlias);
	public abstract void setComponent(String sAlias, JComponent objComponent);

	
	public abstract boolean createDebugUI();//Mache eine Debugausgabe, um die Komponente zu indentifizieren
}
