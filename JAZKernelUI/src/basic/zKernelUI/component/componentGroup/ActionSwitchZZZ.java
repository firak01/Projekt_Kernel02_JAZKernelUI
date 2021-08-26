package basic.zKernelUI.component.componentGroup;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelActionCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;

public class ActionSwitchZZZ extends  KernelActionCascadedZZZ implements IEventBrokerComponentGroupSwitchUserZZZ, IComponentGroupCollectionUserZZZ { //KernelUseObjectZZZ implements ActionListener{
	private int iIndexCurrent = 0;
	private JComponentGroupCollectionZZZ groupc = null;;//zur Verwaltung von HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed
	ISenderComponentGroupSwitchZZZ objEventBroker = null;; //wird von der inneren SwingWorker Klasse verwendet
	
	public ActionSwitchZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent, JComponentGroupCollectionZZZ groupc) throws ExceptionZZZ{
		super(objKernel, panelParent);
		ActionSwitchNew_(groupc);
	}
	private boolean ActionSwitchNew_(JComponentGroupCollectionZZZ groupc) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(groupc==null) {
				ExceptionZZZ ez = new ExceptionZZZ( "GroupCollection-Object missing.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
								
			HashMapIndexedZZZ<Integer,JComponentGroupZZZ>hmIndexed = groupc.getHashMapIndexed();	
			if(hmIndexed==null) {
				ExceptionZZZ ez = new ExceptionZZZ( "HashMapIndexedZZZ-Object in GroupCollection missing. Fill GroupCollection first.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			this.setComponentGroupCollection(groupc);
				
			//+++ der EventBroker wird verwendet um alle Komponenten der GroupCollection über den Buttonclick zu informieren
			ISenderComponentGroupSwitchZZZ objEventBroker = groupc.getSenderUsed();
			this.setSenderUsed(objEventBroker);
									
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
		ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'SWITCH'");
											
		String[] saFlag = null;			
		KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
			
		//Wenn die Gruppen einmal durchgeschaltet sind, wieder am Anfang beginnen.
		int iIndexCurrent = this.getIndexCurrent();		
		int iIndexNext = iIndexCurrent+1;
		
		HashMapIndexedZZZ<Integer,JComponentGroupZZZ>hmIndexed = this.groupc.getHashMapIndexed();
		if(hmIndexed.size() < iIndexNext+1) {//+1 wg. Index mit Grüße vergleichen und Index beginnt immer bei 0
			iIndexNext=0;		
		}
		this.setIndexCurrent(iIndexNext);
		
		boolean bActiveState = true;
		SwingWorker4ProgramSWITCH worker = new SwingWorker4ProgramSWITCH(objKernel, panelParent, hmIndexed, iIndexNext, bActiveState, saFlag);
		worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.
	
		return true;
	}
	
	public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
		return true;
	}
	
	public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
	}			
	
	//### Methoden, die über den reinen Button-Click hinausgehen
	public int getIndexCurrent() {
		return this.iIndexCurrent;
	}
	public void setIndexCurrent(int iIndex) {
		this.iIndexCurrent = iIndex;
	}
	
	
	//######## Getter/Setter
	public void setComponentGroupCollection(JComponentGroupCollectionZZZ groupc) {
		this.groupc = groupc;
	}
	public JComponentGroupCollectionZZZ getComponentGroupCollection() {
		return this.groupc;
	}
	
	
	//### Interface IEventBrokerUser
	public ISenderComponentGroupSwitchZZZ getSenderUsed() {
		return this.objEventBroker;
	}

	public void setSenderUsed(ISenderComponentGroupSwitchZZZ objEventBroker) {
		this.objEventBroker = objEventBroker;			
	}
	
	@Override
	public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
		// TODO Auto-generated method stub		
	}
	
	//#### Innere Klassen
	//##############################################################
	class SwingWorker4ProgramSWITCH extends KernelSwingWorkerZZZ{
		private KernelJPanelCascadedZZZ panel;
		private String[] saFlag4Program;	
		private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
													// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
						
		private HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed;
		private int iIndexUsed;
		private boolean bActiveState;
		
		public SwingWorker4ProgramSWITCH(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed, int iIndexCurrent, boolean bActiveState, String[] saFlag4Program){
			super(objKernel);
			this.panel = panel;
			this.saFlag4Program = saFlag4Program;	
			
			this.hmIndexed = hmIndexed;
			this.iIndexUsed = iIndexCurrent;
			this.bActiveState = bActiveState;
		}
		
		//#### abstracte - Method aus SwingWorker
		public Object construct() {
			try{										
				Integer intIndex = new Integer(this.iIndexUsed);							
				//Wird eine Gruppe aktiv geschaltet, gehören alle anderen Gruppen passiv geschaltet.
				//Hier das Umschalten macht man, indem man einen Event - Wirft, 
                //Alle am Event "registrierten" Labels/Componentent sollen dann reagieren.
				
				//### Den Event starten,
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#EVENTEVENT !!!!!!!!");
				HashMapIndexedZZZ<Integer, JComponentGroupZZZ> hmIndexed = this.hmIndexed;
				JComponentGroupZZZ group = (JComponentGroupZZZ) hmIndexed.getValue(this.iIndexUsed);
				EventComponentGroupSwitchZZZ eventNew= new EventComponentGroupSwitchZZZ(panel, 10002, group, this.iIndexUsed, true);				
				objEventBroker.fireEvent(eventNew);	
				
				System.out.println("#Updating Panel ...");
				KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
				updatePanel(objPanelParent);						
			
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
		public void updatePanel(KernelJPanelCascadedZZZ panel2updateStart){
			this.panel = panel2updateStart;
			
	//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread übergeben
			Runnable runnerUpdatePanel= new Runnable(){
	
				public void run(){
	//				try {							
						
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#SWITCH GECLICKT");
						logLineDate("SWITCH GECLICKT");					
												
						panel.revalidate();
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
	
	} //End Class SwingWorker: SwingWorker4ProgramSWITCH	
}