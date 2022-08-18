package custom.zKernelUI.module.config.DLG;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelKernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**	Den SwingWorker als innere Klasse zu verwenden, scheitert daran, dass der Import nicht klappt fuer: SwingWorker4IniSAVE worker = objPanelSubEast.new SwingWorker4IniSAVE(objKernel, objKernelChoosen, objPanelSubEast, objPanelCenter, (String[])null);
 * Grund: Das würde aus einer static inneren Klasse heraus aufgerufen 'ActionSaveSection'... 
	Also den SwingWorker als eigene Klasse definieren.
 * @author Fritz Lindhauer, 05.12.2021, 09:11:37
 * 
 */
final class SwingWorker4IniSAVE extends KernelSwingWorkerZZZ{
	private IKernelZZZ objKernelChoosen;
	private IPanelCascadedZZZ panel;
	private Panel_CENTERZZZ objPanelCenter;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
					
	
	
	
	public SwingWorker4IniSAVE(IKernelZZZ objKernel, IKernelZZZ objKernelChoosen, IPanelCascadedZZZ panel, Panel_CENTERZZZ panelCenter,String[] saFlag4Program){
		super(objKernel);
		this.objKernelChoosen=objKernelChoosen;
		this.panel = panel;
		this.objPanelCenter=panelCenter;
		this.saFlag4Program = saFlag4Program;	
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
		try {						
			System.out.println("Im SwingWorker4IniSAVE");
			
			
//### Merke: In deisem Swing Worker wird kein EventBroker verwendet, da keine anderen Objekte informiert werden müssen.
			//Wird ein NavigatorElement aktiv geschaltet, gehören alle anderen NavigatorElemente passiv geschaltet.					
			//Das Umschalten macht man, indem man einen Event - Wirft,
			//Alle am Event "registrierten" Labels/Componentent, bzw. die NavigatorElementCollection sollen dann reagieren.
			//Die NavigatorElementCollection durchgehen und die anderen "nicht geclickt setzen", diese Element "geclickt setzen";
			
			//### Den Event starten,
//			if(objEventBroker!=null) {
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#EventBroker starten !!!!!!!!");							
//				EventNavigatorElementSwitchZZZ eventNew= new EventNavigatorElementSwitchZZZ(panel, 10002, sAlias, true);				
//				objEventBroker.fireEvent(eventNew);	
//			}else {
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#ACHTUNG KEIN EventBroker verfuegbar zum Starten !!!!!!!!");
//			}
			
			
			
//			+++ Den Namen des Moduls auslesen = TabellenAlias
			String sModule = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ) objPanelCenter);
			//System.out.println("Name des Moduls: " + sModule);
			
			//+++ Den Namen der zu verarbeitenden Section auslesen
			String sSection = objPanelCenter.getTableAlias();
			this.getLogObject().WriteLineDate("Performing Action: ... on module '" + sModule + "' and section '" + sSection + "'");
						
			//+++ Fuellen einer Tabelle mit den Werten der JLabel und JTextfield Components
			Hashtable objHtValue = objPanelCenter.getTable(false);
			boolean bSuccess;
			if(objHtValue==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Array lengths of Labels and Textfields does not match.", iERROR_PARAMETER_VALUE,  ReflectCodeZZZ.getMethodCurrentName(), "");
				   //doesn�t work. Only works when > JDK 1.4
				   //Exception e = new Exception();
				   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
				   throw ez;	
			}else if(objHtValue.size()<=0){
				//TODO GOON Sicherheitsabfrage per Dialog
				//Section löschen				
				//Nicht das eigene KernelObjekt verwenden, sondern das im Dialog ausgewählte.
				FileIniZZZ objFileIni = this.objKernelChoosen.getFileModuleIniByAlias(sModule);
				objFileIni.deleteSection(sSection);
				
				//Ggfs. sind im Dialog Einträge über mehrere, verschiedene Sections hinweg. Diese auch löschen. 
				String sSectionNew = KernelKernelZZZ.computeSectionFromSystemSection(sSection);
				if(sSectionNew!=null) {
					if(sSection!=sSectionNew) {
						objFileIni.deleteSection(sSection);							
					}
				}
				bSuccess = objFileIni.save();
			}else{
				//TODO GOON Sicherheitsabfrage per Dialog
				//Talleneintraege der Section hinzufuegen. Zudem: Section in der Datei zuerst entfernen.
				
				//Es ist nicht sauber das eigenen KernelObjekt dafuer zu verwenden. Es muss ein anderes herangezogen werden.
				//FileIniZZZ objFileIni = this.getKernelObject().getFileConfigIniByAlias(sModule);
				FileIniZZZ objFileIni = this.objKernelChoosen.getFileModuleIniByAlias(sModule);
									
				//TODO: eine sortierte Hashtable uebergeben !!!
				objFileIni.setSection(sSection, objHtValue, false);	
				bSuccess = objFileIni.save();
			}
			
			//Erfolgsmeldung ausgeben
			//TODO: Das mit einer Dialogbox machen
			if(bSuccess==true){
				//System.out.println("Configuration file for module '" + sModule + "' successfully updated.");
				this.getLogObject().WriteLineDate("Performing Action: Successfully completed.");
			}else{
				//System.out.println("Configuration file for module '" + sModule + "' NOT updated.");
				this.getLogObject().WriteLineDate("Performing Action: NOT ended as expected.");
			}
			
			
			if(this.panel!=null) {
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Updating Panel ...");				
				updatePanel(this.panel);						
			}
				
		}catch(ExceptionZZZ ez){
			System.out.println(ez.getDetailAllLast());
			ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());					
		}
		return "all done";
	}


	/**Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 *  
	* @param stext
	* 					
	 */
	public void updatePanel(IPanelCascadedZZZ panel2updateStart){
		this.panel = panel2updateStart;
		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread übergeben
		Runnable runnerUpdatePanel= new Runnable(){

			public void run(){
//				try {							
					
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#INI SAVE DURCHGEFUEHRT");
					logLineDate("INI SAVE DURCHGEFUEHRT");					
											
					((JComponent) panel).revalidate();
					((Component) panel).repaint();
											 							
//				} catch (ExceptionZZZ e) {
//					e.printStackTrace();
//				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdatePanel);		
		//Ggfs. nach dem Swing Worker eine Statuszeile etc. aktualisieren....

	}
		
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}

} //End Class SwingWorker: SwingWorker4IniSave
