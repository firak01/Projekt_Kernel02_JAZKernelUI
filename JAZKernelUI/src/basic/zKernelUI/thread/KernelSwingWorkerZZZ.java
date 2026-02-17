package basic.zKernelUI.thread;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectLogZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasicUI.thread.SwingWorker;

//TODO: Mal auf den anderen, neueren umstellen.
//import javax.swing.SwingWorker;

import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import custom.zKernel.LogZZZ;


/** Abstrakte Klasse, die ZKernel-Funktionalität für Programme (Button-Clicks, etc.) zur Verfügung stellt.
 *  Diese laufen dann in einem eigenen Thread. 
 *  Der Code für das Program wird im der construct() - Methode implementiert.
 *  
 *  ##########################
 *  Merke 20210418:
 *  Wenn ein Program eine Klasse aus einer anderen Bibliothek nutzt, 
 *  dann kann es sein, dass dies beim Compilieren funktioniert, beim Ausführen aber folgenden Fehler erzeugt:
 *  ClassNotFoundException
 *  
 *  Z.B.: Im JAZWebFTP-Projekt. Wird JSchException aus com.jcraft.jsch nicht gefunden (das ist eine Bibliothek zum Arbeiten mit SFTP, etc.),
 *        wenn man das Projekt in einem anderen Projekt nutzt.
 *  
 *  Eine reine Export-Anweisung für die MavenDependencies des Projekts reicht nicht.
 *  
 *   Lösung (leider nur ein Workaround): 
 *   Binde (z.B. über Maven) diese Bibliothek mit der im Classpath nicht gefundenene Klasse
 *   explizit auch in dem übergeordneten Projekt ein.
 *   
 *  #########################
 * 
 * @author Fritz Lindhauer, 18.04.2021, 09:33:38
 * 
 */
public abstract class KernelSwingWorkerZZZ extends SwingWorker  implements IObjectZZZ, IObjectLogZZZ, IKernelUserZZZ{
	protected IKernelZZZ objKernel = null;
	protected LogZZZ objLog = null;
			
	public KernelSwingWorkerZZZ() {
		super();
	}
	
	public KernelSwingWorkerZZZ(IKernelZZZ objKernel) {
		super();
		KernelSwingWorkerNew_(objKernel);
	}
	
	private boolean KernelSwingWorkerNew_(IKernelZZZ objKernel) {
		boolean bReturn = false;
		main:{
			this.setKernelObject(objKernel);
			this.setLogObject(this.getKernelObject().getLogObject());
			
			bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	@Override
	public LogZZZ getLogObject() {		
		return this.objLog;
	}

	@Override
	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;		
	}

	@Override
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
	}

	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	
	//+++ aus IObjectZZZ
	//Meine Variante Objekte zu clonen
	@Override
	public Object clonez() throws ExceptionZZZ {
		try {
			return this.clone();
		}catch(CloneNotSupportedException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
				
		}
	}
	
	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
//	@Override
//	public void logLineDate(String sLog) throws ExceptionZZZ {
//		LogZZZ objLog = this.getLogObject();
//		if(objLog==null) {
//			String sTemp = AbstractKernelLogZZZ.computeLineDate(this, sLog);
//			System.out.println(sTemp);
//		}else {
//			objLog.writeLineDate(sLog);
//		}		
//	}
//	
//	@Override
//	public void logLineDateWithPosition(String sLog) throws ExceptionZZZ {
//		LogZZZ objLog = this.getLogObject();
//		if(objLog==null) {
//			String sTemp = AbstractKernelLogZZZ.computeLineDateWithPosition(this, sLog);
//			System.out.println(sTemp);
//		}else {
//			objLog.WriteLineDateWithPosition(this, sLog);
//		}		
//	}
	
	@Override
	public synchronized void logLineDate(String sLog) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLog);
	}
	
	@Override
	public void logLineDate(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLogs);
	}
	
	@Override
	public synchronized void logLineDateWithPosition(String sLog) throws ExceptionZZZ {
		ObjectZZZ.logLineDateWithPosition(this, sLog);
	}
	
	
	@Override
	public synchronized void logLineDateWithPosition(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.logLineDateWithPosition(this, sLogs);
	}
	
	//Beim Konstruieren des SwingWorkers werden die notwendigen updateXYZ(...) Methoden aufgerufen.
	@Override
	public abstract Object construct();
	
	
	//+++++++++++++++++++++++++++++++++
	//wenn der Kerneleigene SwingWorker durch javax -Version ersetzt wurde
//	public void start() {
//		this.run();
//	}
//	
//	@Override
//	protected Object doInBackground() throws Exception {
//		this.start();
//	}	
}
