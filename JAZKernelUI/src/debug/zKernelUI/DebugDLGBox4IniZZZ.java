package debug.zKernelUI;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernelUI.module.config.DLG.DLGBOX4INIZZZ;

public class DebugDLGBox4IniZZZ {

	/** void
	 * Lindhauer; 03.05.2006 07:56:53
	 * @param args
	 */
	public static void main(String[] args) {
	//1. Kernel Objekt initialisieren
	KernelZZZ objKernel;
	try {
		objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigConfig_default.ini",(String)null);
		LogZZZ objLog = objKernel.getLogObject();
	
		//Dieses bietet die Möglichkeit auf die Komponenten einer Applikation zuzugreifen (auch Module genannt)
		//String sModule = "Kernel";
		String sModule = "JDXDataReader";
	
		//Einige Test- /Protokollausgaben					
		//System.out.println("Anzahl der Componenten im Parent-Panel: " + panelSubSouth.getComponentCount());
		objLog.writeLineDate("Action Edit Config to perform on module: " + sModule);
	
		DLGBOX4INIZZZ frameDLG = new DLGBOX4INIZZZ(objKernel, null);
	
		// !!! Diese section und dieses Modul muessen in der oben fuer die Kernel-Konfiguration angegebenen Datei stehen. 
		// !!! Die konfigurationsdatei fuer dieses Modul muss vorhanden sein.
		String[] saParam = new String[2];
		saParam[0]="JDX!01";
		saParam[1]=sModule;
		frameDLG.launch(saParam);
	
	} catch (ExceptionZZZ ez) {
		ez.printStackTrace();
	}
	}
}//END Class
