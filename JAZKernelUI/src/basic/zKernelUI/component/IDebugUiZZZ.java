package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;

public interface IDebugUiZZZ {
	public enum FLAGZ{
		DEBUGUI_PANELLABEL_ON;
	}
	public abstract boolean createDebugUi() throws ExceptionZZZ;//Mache eine Debugausgabe, um die Komponente zu indentifizieren
}
