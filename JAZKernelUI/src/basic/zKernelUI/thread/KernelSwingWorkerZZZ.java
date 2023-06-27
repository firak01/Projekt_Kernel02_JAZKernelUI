package basic.zKernelUI.thread;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasicUI.thread.SwingWorker;

//TODO: Mal auf den anderen, neueren umstellen.
//import javax.swing.SwingWorker;

import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
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
public abstract class KernelSwingWorkerZZZ extends SwingWorker  implements IObjectZZZ, IKernelUserZZZ{
	protected IKernelZZZ objKernel = null;
	protected LogZZZ objLog = null;
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
		
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

	@Override
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public void logLineDate(String sLog) {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.WriteLineDate(sLog);
		}		
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
