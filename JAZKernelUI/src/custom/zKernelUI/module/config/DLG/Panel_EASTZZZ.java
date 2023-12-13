package custom.zKernelUI.module.config.DLG;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.AbstractKernelActionCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

import custom.zKernelUI.module.config.DLG.Panel_EASTZZZ.SwingWorker4IniSAVEinner; 
//Merke: Tritzdem ist diese Innere Klasse nicht aus der static inneren Klasse verwendbar.
//Deshalb...
import custom.zKernelUI.module.config.DLG.SwingWorker4IniSAVE;

public class Panel_EASTZZZ  extends KernelJPanelCascadedZZZ {
	private IKernelZZZ objKernelChoosen=null;
	private IKernelModuleZZZ objModuleChoosen=null;
	
	private static final int iLABEL_COLUMN_DEFAULT = 10;
	
	public Panel_EASTZZZ(IKernelZZZ objKernel, JPanel panelParent, IKernelZZZ objKernelChoosen, IKernelModuleZZZ objModuleChoosen, String sProgram) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try{	
		this.setKernelChoosen(objKernelChoosen);//TODOGOON 20210310: Kann man kernelChoosen komplett durch ModuleChoosen ersetzen????
		this.setModuleChoosen(objModuleChoosen);//Merke 20210310: Das ist ggfs. auch ein ganz abstraktes Moduluobjekt, also nicht etwas, das konkret existiert wie z.B. ein anderes Panel.
		
		
			//TODO Komplizierteres aber sch�neres Layout durch einen anderen Layoutmanager
			//this.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
	
			//Einen Rand um das Panel ziehen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
			
			check:{
				//Kein Modul uebergeben
				if(this.getModuleChoosen()==null){
					//Statt Button eine Meldung TODO: Wie graut man einen Button aus ???
					JLabel labelModuleValue = new JLabel("Save unavailable", SwingConstants.LEFT);
					this.add(labelModuleValue);
					break main;
					//TODO: Falls kein Modul �bergeben wurde, so k�nnen sp�ter immer noch Buttons wie "create new module", etc. angezeigt werden.
				}else if(this.getModuleChoosen()!=null && this.getModuleChoosen().getModuleName().equals("")){
					//Statt Button eine Meldung TODO: Wie graut man einen Button aus ???
					JLabel labelModuleValue = new JLabel("Save unavailable", SwingConstants.LEFT);
					this.add(labelModuleValue);
					break main;
					//TODO: Falls kein Modul �bergeben wurde, so k�nnen sp�ter immer noch Buttons wie "create new module", etc. angezeigt werden.
				}else{
					//ModuleExists ?
					String sModule = this.getModuleChoosen().getModuleName();
										
					boolean bModuleConfigured = this.objKernelChoosen.proofFileConfigModuleIsConfigured(sModule);
					if(bModuleConfigured==false){
						//Fall: Modul nicht configuriert
						JLabel labelModuleValue = new JLabel(sModule + ", Error: This is not configured in the kernel main .ini-file.", SwingConstants.LEFT);
						this.add(labelModuleValue);
					}else{
						boolean bModuleExists = this.objKernelChoosen.proofFileConfigModuleExists(sModule);
						if(bModuleExists==false){
							//Fall: Konfiguriertes Modul existiert nicht physikalisch als Datei am erwarteten Ort/mit dem erwarteten Namen
							JLabel labelModuleValue = new JLabel(sModule + ", Error: The .ini-file does not exist.", SwingConstants.LEFT);
							this.add(labelModuleValue);
						}else{
							//Fall: Alles o.k.
							JButton buttonSave = new JButton("Save Section Values");
							
							//Merke: Das hier verwendete KernelObjectChoosen wurde zuvor extra erstellt als eigenes Objekt für ausgewählte Modul, etc.
							ActionSaveSection objActSAVE_SECTION = new ActionSaveSection(this.getKernelObject(), this.objKernelChoosen, this);
														
							buttonSave.addActionListener(objActSAVE_SECTION);
							this.add(buttonSave);
						}
					}
				}				
			}//END check:
			
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
			}
		}//END main:
	}
	
	public IKernelZZZ getKernelChoosen() {
		return this.objKernelChoosen;
	}
	public void setKernelChoosen(IKernelZZZ objKernelChoosen) {
		this.objKernelChoosen = objKernelChoosen;
	}
	
	public IKernelModuleZZZ getModuleChoosen() {
		return this.objModuleChoosen;
	}
	public void setModuleChoosen(IKernelModuleZZZ objModuleChoosen) {
		this.objModuleChoosen = objModuleChoosen;
	}
	
	
	
	
	public static class ActionSaveSection extends AbstractKernelActionCascadedZZZ  implements ActionListener{	
		private IKernelZZZ objKernelChoosen;
		public ActionSaveSection(IKernelZZZ objKernel,IKernelZZZ objKernelChoosen,  JPanel panelParent) throws ExceptionZZZ{
			super(objKernel,(IPanelCascadedZZZ) panelParent);			
			this.objKernelChoosen = objKernelChoosen;
		}
	
		//### Methoden kommen aus den Schnittstellen
		@Override
		public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
//			try {
			
				//+++ Protokolll Eintrag
				this.getLogObject().WriteLineDate("Performing Action: 'Save Section'");
					
				//+++ Zugriff auf das Panel, in dem die Informationen stehen 
				KernelJPanelCascadedZZZ objPanelSubEast = (KernelJPanelCascadedZZZ)this.getPanelParent();
				KernelJPanelCascadedZZZ objPanelDLGBox = (KernelJPanelCascadedZZZ)objPanelSubEast.getPanelParent();
				Panel_CENTERZZZ objPanelCenter = (Panel_CENTERZZZ)objPanelDLGBox.getPanelSub("CENTER");
				
				//Merke: Fuer jede Property=Value Zeile kommen 2 Component hinzu: JLabel + JTextField;						
				//System.out.println("Anzahl der Componenten im CENTER Panel: " + objPanelCenter.getComponentCount());
						
				//Den SwingWorker als innere Klasse zu verwenden, scheitert daran, dass der Import nicht klappt für: SwingWorker4IniSAVE worker = objPanelSubEast.new SwingWorker4IniSAVE(objKernel, objKernelChoosen, objPanelSubEast, objPanelCenter, (String[])null);
				//SwingWorker4IniSAVEinner worker = this.new SwingWorker4IniSAVEinner(objKernel, objKernelChoosen, objPanelSubEast, objPanelCenter, (String[])null);
				//Also den SwingWorker als eigene Klasse definieren.
				SwingWorker4IniSAVE worker = new SwingWorker4IniSAVE(objKernel, objKernelChoosen, objPanelSubEast, objPanelCenter, (String[])null);
				worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.
						
//				} catch (ExceptionZZZ ez) {
//					ez.printStackTrace();
//				}
			return true;
		}

		@Override
		public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {			
			return true;
		}

		@Override
		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {			
		}

		@Override
		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {			
		}
		
	}//End class
	
	//Merke: Diese inner Klasse steht hier nur als Beispiel dafür, dass es nicht möglich ist aus einer static inneren Klasse eine andere inner Klasse zu verwenden.
	class SwingWorker4IniSAVEinner extends KernelSwingWorkerZZZ{
		private IKernelZZZ objKernelChoosen;
		private IPanelCascadedZZZ panel;
		private Panel_CENTERZZZ objPanelCenter;
		private String[] saFlag4Program;	
		private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
													// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
						
		
		
		
		public SwingWorker4IniSAVEinner(IKernelZZZ objKernel, IKernelZZZ objKernelChoosen, IPanelCascadedZZZ panel, Panel_CENTERZZZ panelCenter,String[] saFlag4Program){
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
//				if(objEventBroker!=null) {
//					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#EventBroker starten !!!!!!!!");							
//					EventNavigatorElementSwitchZZZ eventNew= new EventNavigatorElementSwitchZZZ(panel, 10002, sAlias, true);				
//					objEventBroker.fireEvent(eventNew);	
//				}else {
//					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#ACHTUNG KEIN EventBroker verfuegbar zum Starten !!!!!!!!");
//				}
				
				
				
//				+++ Den Namen des Moduls auslesen = TabellenAlias
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
					FileIniZZZ objFileIni = this.objKernelChoosen.getFileConfigModuleIni(sModule);
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
					FileIniZZZ objFileIni = this.objKernelChoosen.getFileConfigModuleIni(sModule);
										
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
			
//			Das Schreiben des Ergebnisses wieder an den EventDispatcher thread übergeben
			Runnable runnerUpdatePanel= new Runnable(){

				public void run(){
//					try {							
						
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#INI SAVE DURCHGEFUEHRT");
						logLineDate("INI SAVE DURCHGEFUEHRT");					
												
						((JComponent) panel).revalidate();
						((Component) panel).repaint();
												 							
//					} catch (ExceptionZZZ e) {
//						e.printStackTrace();
//					}
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
	
	
	
}// END class
