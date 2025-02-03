package basic.zKernelUI.component;

import javax.swing.JComponent;
import javax.swing.JLabel;
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
import custom.zKernel.LogZZZ;

public abstract class AbstractKernelProgramUIZZZ extends AbstractKernelProgramZZZ implements IProgramUIZZZ{
	private KernelJPanelCascadedZZZ panel = null;
	private String sComponent4update;
	private String sText2Update;    //Der Wert, der ins Label geschrieben werden soll. Hier als Variable, damit die interne Runner-Klasse darauf zugreifen kann.
	                                //Merke: Solche Werte duerfen keinen Wert haben. Muessen also "final" sein, damit die interne Runner-Klasse darauf zugreifen kann.
	
	//Anwendungsbeispiel: Projekt VIA: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
    private boolean bMarked;  //dito: für die interne Runner Klasse bereitstellen
	
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelProgramUIZZZ() throws ExceptionZZZ {
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
	
	//### Aus IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		return super.reset();
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		//super.resetValues();//gibt es nicht
		this.sText2Update = "";
		return true;
	}
	
	//### Getter / Setter
		public KernelJPanelCascadedZZZ getPanelParent(){
			return this.panel;
		}
		public void setPanelParent(KernelJPanelCascadedZZZ panel){
			this.panel = panel;
		}
	
	//#### METHODEN ###############################################	
    //Die erbenden Klassen sollen darin die Methoden dieser abstrakten Klasse aufrufen 
    //und den fuer sie vorgesehenen Komponentennamen uebergeben.
	public abstract void updateLabel(String stext) throws ExceptionZZZ;
	public abstract void updateValue(String stext) throws ExceptionZZZ;
	public abstract void updateMessage(String stext) throws ExceptionZZZ;
	
	
	
				
	public final void updateComponent(String sComponent4update, String sValue) throws ExceptionZZZ {
		main:{
			JComponent component = getPanelParent().searchComponent(sComponent4update);
			if(component==null) {
				String sLog = "Component not found '" + sComponent4update + "'";
				System.out.println(sLog);
				
				LogZZZ objLog = this.getLogObject();
				objLog.WriteLineDate(sLog);
				break main;
			}
			if(component instanceof JLabel) {
				updateLabel((JLabel)component, sValue);
			}else if(component instanceof JTextField) {
				updateTextField((JTextField)component, sValue);
			}else {
				String sLog = ReflectCodeZZZ.getPositionCurrent() + ": Component of type '" + component.getClass() + "' not handled in this update event."; 
				System.out.println(sLog);
				
				LogZZZ objLog = this.getLogObject();
				objLog.WriteLineDate(sLog);
			}
		}//end main
	}
	
	public final void updateComponentMarked(String sComponent4update, String sValue) throws ExceptionZZZ {
		main:{
			JComponent component = getPanelParent().searchComponent(sComponent4update);
			if(component==null) {
				String sLog = "Component not found '" + sComponent4update + "'";
				System.out.println(sLog);
				
				LogZZZ objLog = this.getLogObject();
				objLog.WriteLineDate(sLog);
				break main;
			}
			if(component.getClass().isInstance(JLabel.class)) {
				updateLabel((JLabel)component, sValue);
			}else if(component.getClass().isInstance(JTextField.class)) {
				updateTextFieldMarked((JTextField)component, sValue);
			}else {
				String sLog = ReflectCodeZZZ.getPositionCurrent() + ": Component of type '" + component.getClass() + "' not handled in this update event."; 
				System.out.println(sLog);
				
				LogZZZ objLog = this.getLogObject();
				objLog.WriteLineDate(sLog);
			}	
		}//end main:
	}
	
	private void updateMessage(String sComponent4update, String sValue) throws ExceptionZZZ {
		main:{
			JComponent component = getPanelParent().searchComponent(sComponent4update);
			if(component==null) {
				String sLog = "Component not found '" + sComponent4update + "'";
				System.out.println(sLog);
				
				LogZZZ objLog = this.getLogObject();
				objLog.WriteLineDate(sLog);
				break main;
			}
			if(component.getClass().isInstance(JLabel.class)) {
				updateLabel((JLabel)component, sValue);
			}else if(component.getClass().isInstance(JTextField.class)) {
				updateTextField((JTextField)component, sValue);
			}else {
				String sLog = ReflectCodeZZZ.getPositionCurrent() + ": Component of type '" + component.getClass() + "' not handled in this update event."; 
				System.out.println(sLog);
				
				LogZZZ objLog = this.getLogObject();
				objLog.WriteLineDate(sLog);
			}			
		}//end main:
	}
	
	private void updateTextField(JTextField textfield, String stext){
		updateTextField_(textfield, stext, false);
	}
	
	/**Methode, um das Feld für "das Ergebnis" anzubieten.
	 * Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 * 
	 * 
	* @param stext
	* 
	* lindhaueradmin; 17.01.2007 12:09:17
	 */
	private void updateTextFieldMarked(JTextField textfield, String stext){
			updateTextField_(textfield, stext, true);	
	}
	
	private void updateLabel(JLabel label, String stext){
		updateLabel_(label, stext);
	}
	
	private void updateLabel_(JLabel label, String sText) {
		main:{
		if(label==null) {
			ReportLogZZZ.write(ReportLogZZZ.DEBUG, "JTextField '" + sComponent4update + "' NOT FOUND in panel '" + getPanelParent().getName() + "' !!!");
			break main;
		}
		
		//Als Property Speichern, damit der interne Runner darauf zugreifen kann		
		this.sComponent4update = label.getName();		
		this.sText2Update = sText;		
				
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread uebergeben
		Runnable runnerUpdateLabel= new Runnable(){

			public void run(){
//				In das Textfeld den gefundenen Wert eintragen, der Wert ist ganz oben als private Variable deklariert			
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Writing '" + sText2Update + "' to the JLabel '" + sComponent4update + "'");				
				
				//20210707: Hier ggfs. auch in den Nachbarpanels nach dem Feld suchen!!!
				JLabel label = (JLabel) getPanelParent().searchComponent(sComponent4update);
				if(label!=null) {
					label.setText(sText2Update);
				}else {
					ReportLogZZZ.write(ReportLogZZZ.DEBUG, "JLabel '" + sComponent4update + "' NOT FOUND in panel '" + getPanelParent().getName() + "' !!!");										
				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdateLabel);	
		}//end main:
	
	}
	
	private void updateTextField_(JTextField textfield, String stext, boolean bMarkedIn) {
		this.sComponent4update = textfield.getName();
		this.sText2Update = stext;
		this.bMarked = bMarkedIn;
		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread uebergeben
		Runnable runnerUpdateLabel= new Runnable(){

			public void run(){
//				In das Textfeld den gefundenen Wert eintragen, der Wert ist ganz oben als private Variable deklariert			
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Writing '" + sText2Update + "' to the JLabel '" + sComponent4update + "'");				
				
				//20210707: Hier ggfs. auch in den Nachbarpanels nach dem Feld suchen!!!
				JTextField textfield = (JTextField) getPanelParent().searchComponent(sComponent4update);
				if(textfield!=null) {
					textfield.setText(sText2Update);
					if(bMarked) {
						JTextFieldHelperZZZ.markAndFocus(getPanelParent(),textfield);//Merke: Jetzt den Cursor noch verändern macht dies wieder rückgängig.
					}
				}else {
					ReportLogZZZ.write(ReportLogZZZ.DEBUG, "JTextField '" + sComponent4update + "' NOT FOUND in panel '" + getPanelParent().getName() + "' !!!");										
				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdateLabel);		
	}
}
