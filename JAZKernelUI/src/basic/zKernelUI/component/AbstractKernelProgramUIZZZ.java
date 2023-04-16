package basic.zKernelUI.component;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.component.AbstractKernelProgramZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernelUI.util.JTextFieldHelperZZZ;

public abstract class AbstractKernelProgramUIZZZ extends AbstractKernelProgramZZZ{
	private KernelJPanelCascadedZZZ panel = null;
	private String sTextfield4Update;
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Hier als Variable, damit die interne Runner-Klasse darauf zugreifen kann.
	                                //Merke: Solche Werte duerfen keinen Wert haben. Muessen also "final" sein, damit die interne Runner-Klasse darauf zugreifen kann.
	//Anwendungsbeispiel: Projekt VIA: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
    private boolean bMarked;  //dito: für die interne Runner Klasse bereitstellen
	
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 */
	public AbstractKernelProgramUIZZZ() {
		super();
	}
	
	public AbstractKernelProgramUIZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel); 		
	}
	
	public AbstractKernelProgramUIZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel);
		AbstractKernelProgramUINew_(objKernel, panel, saFlagControl);
	}
	
	private void AbstractKernelProgramUINew_(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlagControl) throws ExceptionZZZ {
		main:{			
			check:{	 		
				if(saFlagControl != null){
					boolean btemp; String stemp; String sLog;
					for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
						stemp = saFlagControl[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							 String sKey = stemp;
							 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
							 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
							//							  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
							// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
							// throw ez;								   							   
						}
					}
					if(this.getFlag("init")) break main;										
				}							
			}//End check
		
		//WICHTIG, DAS IST DER UI UNTERSCHIED ZUM BACKEND PROGRAM
		this.setModule((IKernelModuleZZZ) panel);
//				
//		String sModuleName = this.getModuleName();
//		if(StringZZZ.isEmpty(sModuleName)){
//			ExceptionZZZ ez = new ExceptionZZZ("ModuleName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//				
//		String sProgramName = this.getProgramName();
//		if(StringZZZ.isEmpty(sProgramName)){
//			ExceptionZZZ ez = new ExceptionZZZ("ProgramName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//		
//		
		//### Prüfen, ob das Modul konfiguriert ist
//		boolean bIsConfigured = objKernel.proofModuleFileIsConfigured(sModuleAlias);
//		if(bIsConfigured==false){
//			ExceptionZZZ ez = new ExceptionZZZ("ModuleAlias='" + sModuleAlias + "' seems not to be configured for the Application '" + objKernel.getApplicationKey(), iERROR_CONFIGURATION_MISSING, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}		
//		boolean bExists = objKernel.proofModuleFileExists(sModuleAlias);
//		if(bExists==false){
//			ExceptionZZZ ez = new ExceptionZZZ("ModuleAlias='" + sModuleAlias + "' is configured, but the file does not exist for the Application '" + objKernel.getApplicationKey(), iERROR_CONFIGURATION_MISSING, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}	
		
		
	}//END main
		
	}
	
	//### Getter / Setter
		public KernelJPanelCascadedZZZ getPanelParent(){
			return this.panel;
		}
		public void setPanelParent(KernelJPanelCascadedZZZ panel){
			this.panel = panel;
		}
	
	//#### METHIDEN ###############################################	
	public abstract void updateLabel(String stext);
	public abstract void updateMessage(String stext);
	
	public void reset() {
		this.sText2Update = ""; 
	}
	
	public void updateLabel(String sComponentName, String stext){
		updateLabel_(sComponentName, stext, false);
	}
	
	/**Methode, um das Feld für "das Ergebnis" anzubieten.
	 * Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 * 
	 * 
	* @param stext
	* 
	* lindhaueradmin; 17.01.2007 12:09:17
	 */
	public void updateLabelMarked(String sComponentName, String stext){
			updateLabel_(sComponentName, stext, true);	
	}
	
	private void updateLabel_(String sComponentName, String stext, boolean bMarkedIn) {
		this.sTextfield4Update = sComponentName;
		this.sText2Update = stext;
		this.bMarked = bMarkedIn;
		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread uebergeben
		Runnable runnerUpdateLabel= new Runnable(){

			public void run(){
//				In das Textfeld den gefundenen Wert eintragen, der Wert ist ganz oben als private Variable deklariert			
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Writing '" + sText2Update + "' to the JTextField '" + sTextfield4Update + "'");				
				//JTextField textField = (JTextField) getPanelParent().getComponent(sTextfield4Update);
				
				//20210707: Hier ggfs. auch in den Nachbarpanels nach dem Feld suchen!!!
				JTextField textField = (JTextField) getPanelParent().searchComponent(sTextfield4Update);
				if(textField!=null) {
					textField.setText(sText2Update);
					if(bMarked) {
					JTextFieldHelperZZZ.markAndFocus(getPanelParent(),textField);//Merke: Jetzt den Cursor noch verändern macht dies wieder rückgängig.
					}
				}else {
					ReportLogZZZ.write(ReportLogZZZ.DEBUG, "JTextField '" + sTextfield4Update + "' NOT FOUND in panel '" + getPanelParent().getName() + "' !!!");										
				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdateLabel);		
	}
	
	
	/** Methode, um ein anderes Feld für "Nachrichten ans UI" anzubieten.
	 *  Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 * @param sComponentName
	 * @param stext
	 * @author Fritz Lindhauer, 16.04.2023, 17:00:24
	 */
	public void updateMessage(String sComponentName, String stext) {
		this.sTextfield4Update = sComponentName;
		this.sText2Update = stext;
		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread uebergeben
		Runnable runnerUpdateLabel= new Runnable(){

			public void run(){
//				In das Textfeld den gefundenen Wert eintragen, der Wert ist ganz oben als private Variable deklariert			
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Writing '" + sText2Update + "' to the JTextField '" + sTextfield4Update + "'");				
				//JTextField textField = (JTextField) getPanelParent().getComponent(sTextfield4Update);
				
				//20210707: Hier ggfs. auch in den Nachbarpanels nach dem Feld suchen!!!
				JTextField textField = (JTextField) getPanelParent().searchComponent(sTextfield4Update);
				if(textField!=null) {
					textField.setText(sText2Update);					
					JTextFieldHelperZZZ.markAndFocus(getPanelParent(),textField);//Merke: Jetzt den Cursor noch verändern macht dies wieder rückgängig.
				}else {
					ReportLogZZZ.write(ReportLogZZZ.DEBUG, "JTextField '" + sTextfield4Update + "' NOT FOUND in panel '" + getPanelParent().getName() + "' !!!");										
				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdateLabel);	
	}
	
}
