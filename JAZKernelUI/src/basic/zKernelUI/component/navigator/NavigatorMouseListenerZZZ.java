package basic.zKernelUI.component.navigator;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.KernelMouseListenerCascadedZZZ;
import basic.zKernelUI.component.componentGroup.ISenderComponentGroupSwitchZZZ;
import basic.zKernelUI.component.navigator.ActionSwitchZZZ.SwingWorker4ProgramSWITCH;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;

public class NavigatorMouseListenerZZZ extends KernelMouseListenerCascadedZZZ implements INavigatorElementMouseListenerZZZ{
	protected String sNavigatorElementAlias=null;
	
	ISenderNavigatorElementSwitchZZZ objEventBroker; //Wird von der internen SwingWorker-Klasse verwendet.
	
	public NavigatorMouseListenerZZZ() {
		super();
	}
	
	public NavigatorMouseListenerZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);	
	}
	
	public NavigatorMouseListenerZZZ(IKernelZZZ objKernel, String sNavigatorElementAlias) throws ExceptionZZZ{
		super(objKernel);	
		NavigatorMouseListenerNew_(sNavigatorElementAlias);
	}
	
	public NavigatorMouseListenerZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent, String sNavigatorElementAlias) throws ExceptionZZZ{
		super(objKernel,panelParent);
		NavigatorMouseListenerNew_(sNavigatorElementAlias);
	}
	
	private void NavigatorMouseListenerNew_(String sNavigatorElementAlias) {
				this.sNavigatorElementAlias = sNavigatorElementAlias;
				
				
				//+++ der EventBroker wird verwendet um alle Komponenten über den MouseClick zu informieren
				//ISenderNavigatorElementSwitchZZZ objEventBroker = groupc.getSenderUsed();
				//this.setSenderUsed(objEventBroker);
	}
		
	@Override
	public String getNavigatorElementAlias() {
		return this.sNavigatorElementAlias;
	}

	@Override
	public void setNavigatorElementAlias(String sNavigatorElementAlias) {
		this.sNavigatorElementAlias = sNavigatorElementAlias;
	}

	
	@Override
	public boolean customMouseClicked(MouseEvent e, boolean bQueryResult) {
		System.out.println("Element clicked: " + this.getNavigatorElementAlias());
		
		//TODOGOON; //20210815 Benutze den SwingWorker, ggfs. besser wg. Thread Behandlung
		boolean bActiveState = true;
		
		String[] saFlag = null; //{"useProxy"};
		IPanelCascadedZZZ panelParent = this.getPanelParent();
		String sAlias = this.getNavigatorElementAlias();
		SwingWorker4ProgramCLICK worker = new SwingWorker4ProgramCLICK(objKernel, panelParent, sAlias, bActiveState, saFlag);
		worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.
			
		return true;
		//TODOGOON; //20210820
		//TODOGOON: Irgendwie die NavigatorCollection durchgehen und die anderen "nicht geclickt setzen", diese Element "geclickt setzen";
		
		//TODOGOON; //Verwende analog zu ActionSwitchZZZ, ggfs. die bessere Thread Behandlung und EINEN BROKER
		
		//TODOGOON: Danach einen Event werfen, damit die Panels, etc. reagieren können
		//          Ggfs. auch diesen Event für die anderen Navigator-Elemente zum "nicht geclickt setzen" verwenden.
		
	   
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
	
	//### Interface 
	public ISenderNavigatorElementSwitchZZZ getSenderUsed() {
		return this.objEventBroker;
	}

	public void setSenderUsed(ISenderNavigatorElementSwitchZZZ objEventBroker) {
		this.objEventBroker = objEventBroker;			
	}

	//#### Innere Klassen
	//##############################################################
	class SwingWorker4ProgramCLICK extends KernelSwingWorkerZZZ{
		private IPanelCascadedZZZ panel;
		private String[] saFlag4Program;	
		private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
													// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
						
		private String sNavigatorElementAlias = null;
		private boolean bActiveState;
		
		public SwingWorker4ProgramCLICK(IKernelZZZ objKernel, IPanelCascadedZZZ panel, String sNavigatorElementAlias, boolean bActiveState, String[] saFlag4Program){
			super(objKernel);
			this.panel = panel;
			this.saFlag4Program = saFlag4Program;	
			
			this.sNavigatorElementAlias = sNavigatorElementAlias;
			this.bActiveState = bActiveState;
		}
		
		//#### abstracte - Method aus SwingWorker
		public Object construct() {
//			try{	
				//Wird ein NavigatorElement aktiv geschaltet, gehören alle anderen NavigatorElemente passiv geschaltet.
				
				String sAlias = this.sNavigatorElementAlias;
				
				
				
				//Wird eine Gruppe aktiv geschaltet, gehören alle anderen Gruppen passiv geschaltet.
				//Hier das Umschalten macht man, indem man einen Event - Wirft, 
                //Alle am Event "registrierten" Labels/Componentent, bzw. die NavigatorElementCollection sollen dann reagieren.
				
				//### Den Event starten,
				//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#EVENTEVENT !!!!!!!!");							
				//EventNavigatorElementSwitchZZZ eventNew= new EventNavigatorElementSwitchZZZ(panel, 10002, sAlias, true);				
				//objEventBroker.fireEvent(eventNew);	
				
				//System.out.println("#Updating Panel ...");
				//IPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
				//updatePanel(objPanelParent);						
			
//			}catch(ExceptionZZZ ez){
//				System.out.println(ez.getDetailAllLast());
//				ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());					
//			}
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
						
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#SWITCH GECLICKT");
						logLineDate("SWITCH GECLICKT");					
												
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
	
	} //End Class SwingWorker: SwingWorker4ProgramSWITCH

}
