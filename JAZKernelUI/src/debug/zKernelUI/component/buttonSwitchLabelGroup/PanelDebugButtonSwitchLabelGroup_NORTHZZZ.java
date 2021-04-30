package debug.zKernelUI.component.buttonSwitchLabelGroup;

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
import basic.zKernelUI.component.KernelActionCascadedZZZ;
import basic.zKernelUI.component.KernelButtonGroupZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.labelGroup.JComponentGroupZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;

public class PanelDebugButtonSwitchLabelGroup_NORTHZZZ extends KernelJPanelCascadedZZZ implements IKernelProgramZZZ {
	private static final int iLABEL_COLUMN_DEFAULT = 10;
	
    private static final String sBUTTON_SWITCH = "buttonSwitch";
   	
	public PanelDebugButtonSwitchLabelGroup_NORTHZZZ(IKernelZZZ objKernel, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		String stemp; boolean btemp;
		main:{			
		try {		
			//Diese Panel ist Grundlage für diverse INI-Werte auf die über Buttons auf "Programname" zugegriffen wird.
			stemp = IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name();
			btemp = this.setFlagZ(stemp, true);	
			if(btemp==false){
				ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
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
			
			//++++ Die LabelGroupZZZ
//			KernelButtonGroupZZZ<String,AbstractButton> groupButton = new KernelButtonGroupZZZ<String,AbstractButton>();
//			KernelButtonGroupZZZ<String,AbstractButton> groupButton02 = new KernelButtonGroupZZZ<String,AbstractButton>();
			
			
			
			
			//++++ Die Buttons
			JButton buttonSwitch = new JButton("SWITCH");			
			ActionSwitch actionSwitch = new ActionSwitch(objKernel, this);
			buttonSwitch.addActionListener(actionSwitch);
			this.setComponent(PanelDebugButtonSwitchLabelGroup_NORTHZZZ.sBUTTON_SWITCH, buttonSwitch);
			this.add(buttonSwitch);
			
					
			//+++++++++++ GRUPPE 1 ++++++++++++++++++
			String sLabel02 = "Label 1A";
			JLabel label02 = new JLabel(sLabel02, SwingConstants.LEFT);
			this.add(label02);
			
									
			//++++++++++++
			String sLabel04 = "Label 1B";
			JLabel label04 = new JLabel(sLabel04, SwingConstants.LEFT);
			this.add(label04);
			
			//+++++++++++++
			JComponentGroupZZZ group1 = new JComponentGroupZZZ(objKernel, "EINS");
			group1.addComponent(label02);
			group1.addComponent(label04);
			
			
			

			
			
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
class ActionSwitch extends  KernelActionCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{						
public ActionSwitch(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
	super(objKernel, panelParent);			
}

public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'SWITCH'");
										
	String[] saFlag = null;			
	KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
															
	SwingWorker4ProgramSWITCH worker = new SwingWorker4ProgramSWITCH(objKernel, panelParent, saFlag);
	worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.

	return true;
}

public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
	return true;
}

public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
}			 							

class SwingWorker4ProgramSWITCH extends KernelSwingWorkerZZZ{
	private KernelJPanelCascadedZZZ panel;
	private String[] saFlag4Program;	
	private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
												// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
						
	public SwingWorker4ProgramSWITCH(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String[] saFlag4Program){
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
		
			TODOGOON; ///20210424: Hier das Umschalten machen, indem man einen Event - Wirft, 
			                      //Alle am Event "registrierten" Labels/Componentent sollen dann reagieren.
			
			
			
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
					
					System.out.println("SWITCH GECLICKT");
					logLineDate("SWITCH GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ					
										
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
	} //End Class SwingWorker: SwingWorker4ProgramSWITCH

}//End class ...KernelActionCascaded....
//##############################################


}
