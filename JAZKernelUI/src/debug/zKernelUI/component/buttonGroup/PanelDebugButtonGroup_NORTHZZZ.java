package debug.zKernelUI.component.buttonGroup;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zBasicUI.thread.SwingWorker;
import basic.zKernelUI.component.KernelActionCascadedZZZ;
import basic.zKernelUI.component.KernelJButtonGroupZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;


import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.KernelZZZ;

public class PanelDebugButtonGroup_NORTHZZZ extends KernelJPanelCascadedZZZ {
	private KernelZZZ objKernelChoosen;
	private static final int iLABEL_COLUMN_DEFAULT = 10;
	
    private static final String sBUTTON_PLUS = "buttonPlus";
    private static final String sBUTTON_MINUS = "buttonMinus";
    private static final String sBUTTON_ENABLE = "buttonEnable";
	
	public PanelDebugButtonGroup_NORTHZZZ(KernelZZZ objKernel, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try {		
			//TODO Komplizierteres aber sch�neres Layout durch einen anderen Layoutmanager
			this.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
	
			//Einen Rahmen um das Panel zeichnen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
					
			// +++ Werte aus der KoernelKonfiguration auslesen und anzeigen
			String sLabelButtonGroup01 = this.getKernelObject().getParameterByProgramAlias("PanelNorth", "LabelButtonGroup01");
			JLabel labelModuleText = new JLabel(sLabelButtonGroup01, SwingConstants.LEFT);
			this.add(labelModuleText);
			
			//++++ Die ButtongroupZZZ
			KernelJButtonGroupZZZ<String,JButton> groupButton = new KernelJButtonGroupZZZ<String,JButton>();
			
			
			//++++ Die Buttons
			JButton buttonPlus = new JButton("PLUS");			
			ActionPlus actionPlus = new ActionPlus(objKernel, this);
			buttonPlus.addActionListener(actionPlus);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_PLUS, buttonPlus);
			this.add(buttonPlus);
			groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_PLUS,buttonPlus);
			
			
			JButton buttonMinus = new JButton("MINUS");			
			ActionMinus actionMinus = new ActionMinus(objKernel, this);
			buttonMinus.addActionListener(actionMinus);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_MINUS, buttonMinus);
			this.add(buttonMinus);
			groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_MINUS,buttonMinus);
			
			JButton buttonEnable = new JButton("Enable all");
			ActionEnable actionEnable = new ActionEnable(objKernel, this);
			buttonEnable.addActionListener(actionEnable);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_ENABLE, buttonEnable);
			this.add(buttonEnable);
			groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_ENABLE,buttonEnable);
			
			
			//+++ ButtonGroup dem Panel hinzufügen
			this.getHashtableButtonGroup().put("EINS", groupButton);  //TODO HashtableButtonGroup ist immer null in den SwingWorkern??? warum
			
			} catch (ExceptionZZZ ez) {
				String sError = ReflectCodeZZZ.getMethodCurrentName() + ": " + ez.getDetailAllLast();
				System.out.println(sError);
				this.getLogObject().WriteLineDate(sError);
			}
		}//END main:
	}
	
	//20180819: VARIANTE MIT SWING WORKER
	//######################################
//PLUS BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionPlus extends  KernelActionCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionPlus(KernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent){
	super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'PLUS'");
										
	String[] saFlag = null;			
	KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
															
	SwingWorker4ProgramPLUS worker = new SwingWorker4ProgramPLUS(objKernel, panelParent, saFlag);
	worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

	return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
	return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramPLUS extends SwingWorker implements IObjectZZZ, IKernelUserZZZ{
	private KernelZZZ objKernel;
	private LogZZZ objLog;
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;
	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
				
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	public SwingWorker4ProgramPLUS(KernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super();
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
			System.out.println("Updating Panel ...");
			KernelJPanelCascadedZZZ objPanelParent = this.panel.getPanelParent();
			updatePanel(objPanelParent); //20180819: Damit das klappt muss eine Komponentenliste über alle Panels zusammengesucht werden....						
		
//		}catch(ExceptionZZZ ez){
//			System.out.println(ez.getDetailAllLast());
//			ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());					
//		}
		return "all done";
	}
	

	/**Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 *  
	* @param stext
	* 					
	 */
	public void updatePanel(KernelJPanelCascadedZZZ panel2updateStart){
		this.panel = panel2updateStart;
		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread übergeben
		Runnable runnerUpdatePanel= new Runnable(){

			public void run(){
//				try {							
					
					System.out.println("PLUS GECLICKT");
					
					KernelJButtonGroupZZZ groupButton = panel.getHashtableButtonGroup().get("EINS");
					if(groupButton!=null){
						groupButton.disableOther(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_PLUS);
					}
					
					panel.validate();
					panel.repaint();	
					
																									
					
					 							
//				} catch (ExceptionZZZ e) {
//					e.printStackTrace();
//				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdatePanel);		
		//Ggfs. nach dem Swing Worker eine Statuszeile etc. aktualisieren....

	}
	
	
	public KernelZZZ getKernelObject() {
		return this.objKernel;
	}

	public void setKernelObject(KernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################


//VARIANTE MIT SWING WORKER
//######################################
//MINUS BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionMinus extends  KernelActionCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionMinus(KernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent){
super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'MINUS'");
									
String[] saFlag = null;			
KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
														
SwingWorker4ProgramMINUS worker = new SwingWorker4ProgramMINUS(objKernel, panelParent, saFlag);
worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramMINUS extends SwingWorker implements IObjectZZZ, IKernelUserZZZ{
	private KernelZZZ objKernel;
	private LogZZZ objLog;
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;
	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
				
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	public SwingWorker4ProgramMINUS(KernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super();
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
			System.out.println("Updating Panel ...");
			KernelJPanelCascadedZZZ objPanelParent = this.panel.getPanelParent();
			updatePanel(objPanelParent); //20180819: Damit das klappt muss eine Komponentenliste über alle Panels zusammengesucht werden....						
		
//		}catch(ExceptionZZZ ez){
//			System.out.println(ez.getDetailAllLast());
//			ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());					
//		}
		return "all done";
	}
	

	/**Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 *  
	* @param stext
	* 					
	 */
	public void updatePanel(KernelJPanelCascadedZZZ panel2updateStart){
		this.panel = panel2updateStart;
		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread übergeben
		Runnable runnerUpdatePanel= new Runnable(){

			public void run(){
//				try {							
					
					System.out.println("MINUS GECLICKT");
					panel.validate();
					panel.repaint();	
					
																									
					
					 							
//				} catch (ExceptionZZZ e) {
//					e.printStackTrace();
//				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdatePanel);		
		//Ggfs. nach dem Swing Worker eine Statuszeile etc. aktualisieren....

	}
	
	
	public KernelZZZ getKernelObject() {
		return this.objKernel;
	}

	public void setKernelObject(KernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################
    

//######################################
//ENABLE BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionEnable extends  KernelActionCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionEnable(KernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent){
	super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'ENABLE'");
										
	String[] saFlag = null;			
	KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
															
	SwingWorker4ProgramENABLE worker = new SwingWorker4ProgramENABLE(objKernel, panelParent, saFlag);
	worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

	return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
	return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramENABLE extends SwingWorker implements IObjectZZZ, IKernelUserZZZ{
	private KernelZZZ objKernel;
	private LogZZZ objLog;
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;
	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
				
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	public SwingWorker4ProgramENABLE(KernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super();
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
			System.out.println("Updating Panel ...");
			KernelJPanelCascadedZZZ objPanelParent = this.panel.getPanelParent();
			updatePanel(objPanelParent); //20180819: Damit das klappt muss eine Komponentenliste über alle Panels zusammengesucht werden....						
		
//		}catch(ExceptionZZZ ez){
//			System.out.println(ez.getDetailAllLast());
//			ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());					
//		}
		return "all done";
	}
	

	/**Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
	 *  
	* @param stext
	* 					
	 */
	public void updatePanel(KernelJPanelCascadedZZZ panel2updateStart){
		this.panel = panel2updateStart;
		
//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread übergeben
		Runnable runnerUpdatePanel= new Runnable(){

			public void run(){
//				try {							
					
					System.out.println("ENABLE GECLICKT");
					panel.validate();
					panel.repaint();	
					
																									
					
					 							
//				} catch (ExceptionZZZ e) {
//					e.printStackTrace();
//				}
			}
		};
		
		SwingUtilities.invokeLater(runnerUpdatePanel);		
		//Ggfs. nach dem Swing Worker eine Statuszeile etc. aktualisieren....

	}
	
	
	public KernelZZZ getKernelObject() {
		return this.objKernel;
	}

	public void setKernelObject(KernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################


}
