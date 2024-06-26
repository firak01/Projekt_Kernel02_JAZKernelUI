package debug.zKernelUI.component.buttonGroup;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zBasicUI.thread.SwingWorker;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.AbstractKernelActionListenerCascadedZZZ;
import basic.zKernelUI.component.KernelButtonGroupZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernel.flag.IFlagZLocalUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public class PanelDebugButtonGroup_NORTHZZZ extends KernelJPanelCascadedZZZ implements IKernelProgramZZZ {
	private static final int iLABEL_COLUMN_DEFAULT = 10;
	
    private static final String sBUTTON_PLUS = "buttonPlus";
    private static final String sBUTTON_MINUS = "buttonMinus";
    private static final String sBUTTON_ENABLE = "buttonEnable";
    private static final String sBUTTON_DISABLE = "buttonDisable";
    private static final String sBUTTON_TOGGLE_ALL = "buttonToggle_all";
    private static final String sBUTTON_DIFFER_ALL = "buttonDiffer_all";
    private static final String sBUTTON_DIFFER_ALL_REFFERENCE = "buttonDiffer_all_reference";
    private static final String sBUTTON_SAME_ALL = "buttonSame_all";
    private static final String sBUTTON_SAME_ALL_REFFERENCE = "buttonSame_all_reference";
	
	public PanelDebugButtonGroup_NORTHZZZ(IKernelZZZ objKernel, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		String stemp; boolean btemp;
		main:{			
		try {		
			//Diese Panel ist Grundlage für diverse INI-Werte auf die über Buttons auf "Programname" zugegriffen wird.
			stemp = IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name();
			btemp = this.setFlag(stemp, true);	
			if(btemp==false){
				ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		 
			}
			
			
			//Wichtige Informationen, zum Auslesen von Parametern aus der KernelConfiguration
			String sProgram; String sModule;
			sModule = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ) this);
			if(StringZZZ.isEmpty(sModule)){
				ExceptionZZZ ez = new ExceptionZZZ("No module configured for this component '" + this.getClass().getName() + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			sProgram = this.getProgramName();
			if(StringZZZ.isEmpty(sProgram)){
				ExceptionZZZ ez = new ExceptionZZZ("No program '" + sProgram + "' configured for the module: '" +  sModule + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//TODO Komplizierteres aber sch�neres Layout durch einen anderen Layoutmanager
			this.setLayout(new GridLayout(0,3)); //6 Zeilen, 3 Spalten. Merke new GridLayout(6,3)); funktioniert nicht, wohl wenn die Anzahl der Zellen nicht ganz passt. 
	
			//Einen Rahmen um das Panel zeichnen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
					
			// +++ Werte aus der KernelKonfiguration auslesen und anzeigen
			String sLabelButtonGroup01 = this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButtonGroup01").getValue();
			JLabel labelModuleText = new JLabel(sLabelButtonGroup01, SwingConstants.LEFT);
			this.add(labelModuleText);
			
			//++++ Die ButtongroupZZZ
			KernelButtonGroupZZZ<String,AbstractButton> groupButton = new KernelButtonGroupZZZ<String,AbstractButton>();
			KernelButtonGroupZZZ<String,AbstractButton> groupButton02 = new KernelButtonGroupZZZ<String,AbstractButton>();
			
			
			//++++ Die Buttons
			JButton buttonPlus = new JButton("PLUS");			
			ActionPlus actionPlus = new ActionPlus(objKernel, this);
			buttonPlus.addActionListener(actionPlus);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_PLUS, buttonPlus);
			this.add(buttonPlus);
			groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_PLUS,buttonPlus);
			groupButton02.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_PLUS,buttonPlus);
			
			
			JButton buttonMinus = new JButton("MINUS");			
			ActionMinus actionMinus = new ActionMinus(objKernel, this);
			buttonMinus.addActionListener(actionMinus);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_MINUS, buttonMinus);
			this.add(buttonMinus);
			groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_MINUS,buttonMinus);
			groupButton02.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_MINUS,buttonMinus);
			
			
			//+++++++++++
			String sLabel02 = ".";
			JLabel label02 = new JLabel(sLabel02, SwingConstants.LEFT);
			this.add(label02);
			
			JButton buttonEnable = new JButton("Enable all");
			ActionEnable actionEnable = new ActionEnable(objKernel, this);
			buttonEnable.addActionListener(actionEnable);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_ENABLE, buttonEnable);
			this.add(buttonEnable);
			//Also diesen Button nicht hinzufügen. Sonst kriegt man sie nie wieder enabled:
			//groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_ENABLE,buttonEnable);
			
			JButton buttonDisable = new JButton("Disable all");
			ActionDisable actionDisable = new ActionDisable(objKernel, this);
			buttonDisable.addActionListener(actionDisable);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DISABLE, buttonDisable);
			this.add(buttonDisable);
			//Also diesen Button nicht hinzufügen. Sonst kriegt man sie nie wieder enabled:
			//groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DISABLE,buttonDisable);
			
			
			//++++++++++++
			String sLabel03 = ".";
			JLabel label03 = new JLabel(sLabel03, SwingConstants.LEFT);
			this.add(label03);
			
			JButton buttonToggle_all = new JButton("Toggle all");
			ActionToggle_all actionToggle_all = new ActionToggle_all(objKernel, this);
			buttonToggle_all.addActionListener(actionToggle_all);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_TOGGLE_ALL, buttonToggle_all);
			this.add(buttonToggle_all);
			//Also diesen Button nicht hinzufügen. Sonst kriegt man sie nie wieder enabled:
			//groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DISABLE,buttonDisable);
			
			 final JToggleButton toggleButton1 = new JToggleButton("A Toggle Button");
			 this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DIFFER_ALL_REFFERENCE, toggleButton1);
			this.add(toggleButton1);
			groupButton02.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DIFFER_ALL_REFFERENCE,toggleButton1);
			groupButton02.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_SAME_ALL_REFFERENCE,toggleButton1);
//		        ActionListener actionListener = new ActionListener() {
//
//		            @Override
//		            public void actionPerformed(ActionEvent actionEvent) {
//		                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
//		                boolean selected = abstractButton.getModel().isSelected();
//		                System.out.println("Action - selected=" + selected + "\n");
//		                toggleButton1.setSelected(selected);
//		            }
//		        };
		       // toggleButton.addActionListener(actionListener);
			
			 
			//++++++++++++
			String sLabel04 = ".";
			JLabel label04 = new JLabel(sLabel04, SwingConstants.LEFT);
			this.add(label04);

			JButton buttonDiffer_all = new JButton("Differ all: Like 'A toggle button' selection");
			ActionDiffer_all actionDiffer_all = new ActionDiffer_all(objKernel, this);
			buttonDiffer_all.addActionListener(actionDiffer_all);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DIFFER_ALL, buttonDiffer_all);
			this.add(buttonDiffer_all);
			//Also diesen Button nicht hinzufügen. Sonst kriegt man sie nie wieder enabled:
			//groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DISABLE,buttonDisable);
			
			JButton buttonSame_all = new JButton("Same all: Like 'A toggle button' selection");
			ActionSame_all actionSame_all = new ActionSame_all(objKernel, this);
			buttonSame_all.addActionListener(actionSame_all);		
			this.setComponent(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_SAME_ALL, buttonSame_all);
			this.add(buttonSame_all);
			//Also diesen Button nicht hinzufügen. Sonst kriegt man sie nie wieder enabled:
			//groupButton.add(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DISABLE,buttonDisable);
						
			//+++ ButtonGroup dem Panel hinzufügen
			this.getHashtableButtonGroup().put("EINS", groupButton);  
			this.getHashtableButtonGroup().put("ZWEI", groupButton02); 
			
			//+++ Das Layout validieren, mit dem Ziel die Komponenten passend anzuordnen.
			this.validate();
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
class ActionPlus extends  AbstractKernelActionListenerCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionPlus(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
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

class SwingWorker4ProgramPLUS extends KernelSwingWorkerZZZ{
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
						
	public SwingWorker4ProgramPLUS(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super(objKernel);
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;	
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
			System.out.println("Updating Panel ...");
			KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
			updatePanel(objPanelParent);						
		
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
				try {							
					
					System.out.println("PLUS GECLICKT");
					logLineDate("PLUS GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ					
					KernelButtonGroupZZZ<String, AbstractButton> groupButton = panel.getHashtableButtonGroup().get("EINS");
					if(groupButton!=null){
						groupButton.disableOther(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_PLUS);
					}
					
					panel.validate();
					panel.repaint();	
					 							
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
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
class ActionMinus extends  AbstractKernelActionListenerCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionMinus(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
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

class SwingWorker4ProgramMINUS extends KernelSwingWorkerZZZ{
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
	public SwingWorker4ProgramMINUS(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super(objKernel);
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
		System.out.println("Updating Panel ...");
		KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
		updatePanel(objPanelParent);	
		
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
				try {							
					
					System.out.println("MINUS GECLICKT");
					logLineDate("MINUS GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ
					
					KernelButtonGroupZZZ<String, AbstractButton> groupButton = panel.getHashtableButtonGroup().get("EINS");
					if(groupButton!=null){
						groupButton.disableOther(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_MINUS);
					}
					panel.validate();
					panel.repaint();	
					
																									
					
					 							
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################
    

//######################################
//ENABLE BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionEnable extends  AbstractKernelActionListenerCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionEnable(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
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

class SwingWorker4ProgramENABLE extends KernelSwingWorkerZZZ{
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
	public SwingWorker4ProgramENABLE(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super(objKernel);
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
		System.out.println("Updating Panel ...");
		KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
		updatePanel(objPanelParent);	
		
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
				try {							
					
					System.out.println("ENABLE ALL GECLICKT");
					logLineDate("ENABLE ALL GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ
					
					KernelButtonGroupZZZ<String, AbstractButton> groupButton = panel.getHashtableButtonGroup().get("EINS");
					if(groupButton!=null){
						groupButton.enableAll();
					}
					KernelButtonGroupZZZ<String, AbstractButton> groupButton02 = panel.getHashtableButtonGroup().get("ZWEI");
					if(groupButton02!=null){
						groupButton02.enableAll();
					}
					panel.validate();
					panel.repaint();	
					
																									
					
					 							
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################

//######################################
//DISABLE BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionDisable extends  AbstractKernelActionListenerCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionDisable(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
	super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'DISABLE'");
										
	String[] saFlag = null;			
	KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
															
	SwingWorker4ProgramDISABLE worker = new SwingWorker4ProgramDISABLE(objKernel, panelParent, saFlag);
	worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

	return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
	return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramDISABLE extends KernelSwingWorkerZZZ {
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
	public SwingWorker4ProgramDISABLE(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super(objKernel);
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
		System.out.println("Updating Panel ...");
		KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
		updatePanel(objPanelParent);	
		
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
				try {							
					
					System.out.println("DISABLE GECLICKT");
					logLineDate("DISABLE GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ
					
					
					KernelButtonGroupZZZ<String, AbstractButton> groupButton = panel.getHashtableButtonGroup().get("EINS");
					if(groupButton!=null){
						groupButton.disableAll();
					}					
					panel.validate();
					panel.repaint();	
						 							
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################

//######################################
//TOGGLE_ALL BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionToggle_all extends  AbstractKernelActionListenerCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionToggle_all(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
	super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'TOGGLE_ALL'");
										
	String[] saFlag = null;			
	KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
															
	SwingWorker4ProgramTOGGLE_ALL worker = new SwingWorker4ProgramTOGGLE_ALL(objKernel, panelParent, saFlag);
	worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

	return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
	return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramTOGGLE_ALL extends KernelSwingWorkerZZZ implements IObjectZZZ, IKernelUserZZZ{
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
	public SwingWorker4ProgramTOGGLE_ALL(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super(objKernel);
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
		System.out.println("Updating Panel ...");
		KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
		updatePanel(objPanelParent);	
		
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
				try {							
					
					System.out.println("TOGGLE ALL GECLICKT");
					logLineDate("TOGGLE ALL GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ
					
					KernelButtonGroupZZZ<String, AbstractButton> groupButton = panel.getHashtableButtonGroup().get("EINS");
					if(groupButton!=null){
						groupButton.toggleAll();
					}					
					panel.validate();
					panel.repaint();	
						 							
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################

//######################################
//DIFFER_ALL BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionDiffer_all extends  AbstractKernelActionListenerCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionDiffer_all(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
	super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'DIFFER_ALL'");
										
	String[] saFlag = null;			
	KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
															
	SwingWorker4ProgramDIFFER_ALL worker = new SwingWorker4ProgramDIFFER_ALL(objKernel, panelParent, saFlag);
	worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

	return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
	return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramDIFFER_ALL extends KernelSwingWorkerZZZ {	
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
	public SwingWorker4ProgramDIFFER_ALL(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super(objKernel);				
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
		System.out.println("Updating Panel ...");
		KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
		updatePanel(objPanelParent);	
		
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
				try {							
					
					System.out.println("DIFFER ALL GECLICKT");
					logLineDate("DIFFER ALL GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ
					
					KernelButtonGroupZZZ<String, AbstractButton> groupButton = panel.getHashtableButtonGroup().get("ZWEI");
					if(groupButton!=null){
						groupButton.differAll(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_DIFFER_ALL_REFFERENCE);
					}					
					panel.validate();
					panel.repaint();	
						 							
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################

//######################################
//SAME_ALL BUTTON GUI - Innere Klassen, welche eine Action behandelt	
class ActionSame_all extends  AbstractKernelActionListenerCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionSame_all(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
	super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'SAME_ALL'");
										
	String[] saFlag = null;			
	KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
															
	SwingWorker4ProgramSAME_ALL worker = new SwingWorker4ProgramSAME_ALL(objKernel, panelParent, saFlag);
	worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

	return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
	return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramSAME_ALL extends KernelSwingWorkerZZZ implements IObjectZZZ, IKernelUserZZZ{
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
	
	public SwingWorker4ProgramSAME_ALL(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
		super(objKernel);
		this.panel = panel;
		this.saFlag4Program = saFlag4Program;					
	}
	
	//#### abstracte - Method aus SwingWorker
	public Object construct() {
//		try{
		
		System.out.println("Updating Panel ...");
		KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
		updatePanel(objPanelParent);	
		
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
				try {							
					
					System.out.println("SAME ALL GECLICKT");
					logLineDate("SAME ALL GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ
					
					KernelButtonGroupZZZ<String, AbstractButton> groupButton02 = panel.getHashtableButtonGroup().get("ZWEI");
					if(groupButton02!=null){
						groupButton02.sameAll(PanelDebugButtonGroup_NORTHZZZ.sBUTTON_SAME_ALL_REFFERENCE);
					}					
					panel.validate();
					panel.repaint();	
						 							
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
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

}

@Override
public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
	// TODO Auto-generated method stub
	
} //End Class MySwingWorker

}//End class ...KErnelActionCascaded....
//##############################################

}
