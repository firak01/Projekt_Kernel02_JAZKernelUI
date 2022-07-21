package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;

public interface IDebugUiZZZ {
	public enum FLAGZ{
		DEBUGUI_PANELLABEL_ON,DEBUGUI_PANELLIST_STRATEGIE_ENTRYFIRST,DEBUGUI_PANELLIST_STRATEGIE_ENTRYLAST,DEBUGUI_PANELLIST_STRATEGIE_ENTRYDUMMY;
	}	
	public abstract boolean createDebugUi() throws ExceptionZZZ;//Mache eine Debugausgabe, um die Komponente zu indentifizieren
}
