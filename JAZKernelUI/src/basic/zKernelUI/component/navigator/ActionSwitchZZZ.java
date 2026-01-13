package basic.zKernelUI.component.navigator;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.AbstractKernelActionListenerCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.KernelMouseListenerCascadedZZZ;
import basic.zKernelUI.component.tray.ITrayZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;


//TODOGOON: SOLL GELOESCHT WERDEN. Das Starten des Swing Workers findet in NavigatorMouseListenerZZZ statt.
public class ActionSwitchZZZ<T> extends  KernelMouseListenerCascadedZZZ implements IEventBrokerNavigatorElementSwitchUserZZZ, INavigatorCollectionUserZZZ { //KernelUseObjectZZZ implements ActionListener{
	private String sAlias = null;
	private ISenderNavigatorElementSwitchZZZ objEventBroker; //Dahinter steckt die NavigatorElementCollection mit der HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>> hmIndexed
	                                                         //Die Collection feuert also den Event ab.....
	
	public ActionSwitchZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent, ISenderNavigatorElementSwitchZZZ groupc, String sAlias) throws ExceptionZZZ{
		super(objKernel, panelParent);
		ActionSwitchNew_(groupc, sAlias);
	}
	private boolean ActionSwitchNew_(ISenderNavigatorElementSwitchZZZ groupc, String sAlias) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ( "NavigatorElement - Alias missing.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			if(groupc == null) {
				ExceptionZZZ ez = new ExceptionZZZ( "EventBrokerUSING - Object missing.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
												
			//+++ der EventBroker wird verwendet um alle Komponenten der GroupCollection über den Buttonclick zu informieren
			this.setSenderUsed(groupc);
									
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
//	public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
//		ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'SWITCH'");
//											
//		String[] saFlag = null;			
//		KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
//			
//		//Wenn die Gruppen einmal durchgeschaltet sind, wieder am Anfang beginnen.
//		int iIndexCurrent = this.getIndexCurrent();		
//		int iIndexNext = iIndexCurrent+1;
//		
//		HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmIndexed = this.groupc.getHashMapIndexed();
//		if(hmIndexed.size() < iIndexNext+1) {//+1 wg. Index mit Grüße vergleichen und Index beginnt immer bei 0
//			iIndexNext=0;		
//		}
//		this.setIndexCurrent(iIndexNext);
//		
//		boolean bActiveState = true;
//		SwingWorker4ProgramSWITCH worker = new SwingWorker4ProgramSWITCH(objKernel, panelParent, hmIndexed, iIndexNext, bActiveState, saFlag);
//		worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.
//	
//		return true;
//	}
//	
//	public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
//		return true;
//	}
//	
//	public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
//	}			
	
	//### Methoden, die über den reinen Button-Click hinausgehen
	public String getNavigatorElementAliasCurrent() {
		return this.sAlias;
	}
	public void setNavigatorElementAliasCurrent(String sAlias) {
		this.sAlias = sAlias;
	}
	
	
	//######## Getter/Setter	
	//### Interface IEventBrokerUser
	public ISenderNavigatorElementSwitchZZZ getSenderUsed() {
		return this.objEventBroker;
	}

	public void setSenderUsed(ISenderNavigatorElementSwitchZZZ objEventBroker) {
		this.objEventBroker = objEventBroker;			
	}
	
		
	//#### Innere Klassen
	//##############################################################
	class SwingWorker4ProgramSWITCH extends KernelSwingWorkerZZZ{
		private KernelJPanelCascadedZZZ panel= null;
		private String sAlias= null;
		
		private String[] saFlag4Program;	
		private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
													// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
						
		private boolean bActiveState;
		
		public SwingWorker4ProgramSWITCH(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, String sAlias, boolean bActiveState, String[] saFlag4Program){
			super(objKernel);
			this.panel = panel;
			this.sAlias = sAlias;			
			this.saFlag4Program = saFlag4Program;	
			this.bActiveState = bActiveState;
		}
		
		//#### abstracte - Method aus SwingWorker
		public Object construct() {
			try{										
				String sAlias = this.sAlias;
				
				//Wird eine Gruppe aktiv geschaltet, gehören alle anderen Gruppen passiv geschaltet.
				//Hier das Umschalten macht man, indem man einen Event - Wirft, 
                //Alle am Event "registrierten" Labels/Componentent sollen dann reagieren.
				
				//### Den Event starten,
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#MouseListener EVENTEVENT !!!!!!!!");				
				EventNavigatorElementSwitchZZZ eventNew= new EventNavigatorElementSwitchZZZ(panel, 10002, sAlias, true);				
				objEventBroker.fireEvent(eventNew); //Die ganze Collection feuert also den Event ab.	
				
				System.out.println("#Updating Panel ...");
				KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
				updatePanel(objPanelParent);									
			}catch(ExceptionZZZ ez){
				System.out.println(ez.getDetailAllLast());
				try {
					ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());
				} catch (ExceptionZZZ e) {					
					e.printStackTrace();
				}					
			}
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
						
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#SWITCH GECLICKT");
						logLineDate("SWITCH GECLICKT");					
												
						panel.revalidate();
						panel.repaint();
												 							
					} catch (ExceptionZZZ e) {
						e.printStackTrace();
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
	
	} //End Class SwingWorker: SwingWorker4ProgramSWITCH	

	//### INTERFACE METHODEN
	@Override
	public boolean customMouseClicked(MouseEvent e, boolean bQueryResult) throws ExceptionZZZ {
		ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'SWITCH'");
		
		String[] saFlag = null;			
		KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
			
		String sAlias = this.getNavigatorElementAliasCurrent();
		boolean bActiveState = true;
		SwingWorker4ProgramSWITCH worker = new SwingWorker4ProgramSWITCH(objKernel, panelParent, sAlias, bActiveState, saFlag);
		worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.
	
		return true;
	}
	@Override
	public boolean customMousePressed(MouseEvent e, boolean bQueryResult) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean customMouseReleased(MouseEvent e, boolean bQueryResult) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean customMouseEntered(MouseEvent e, boolean bQueryResult) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean customMouseExited(MouseEvent e, boolean bQueryResult) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean actionPerformQueryCustom(MouseEvent me) throws ExceptionZZZ {
		return true;
	}
	@Override
	public boolean actionPerformPostCustom(MouseEvent me, boolean bPerfomResultContinue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void actionPerformCustomOnError(MouseEvent me, ExceptionZZZ ez) {
		// TODO Auto-generated method stub
		
	}
}