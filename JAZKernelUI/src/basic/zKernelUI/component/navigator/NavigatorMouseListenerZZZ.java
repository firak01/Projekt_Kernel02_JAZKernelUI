package basic.zKernelUI.component.navigator;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.ITrayZZZ;
import basic.zKernelUI.component.KernelMouseListenerCascadedZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;

public class NavigatorMouseListenerZZZ extends KernelMouseListenerCascadedZZZ implements INavigatorElementMouseListenerZZZ{		
	ISenderNavigatorElementSwitchZZZ objEventBroker=null; //Wird von der internen SwingWorker-Klasse verwendet.
	protected String sNavigatorElementAlias=null;
	
	public NavigatorMouseListenerZZZ() {
		super();
	}
	
	public NavigatorMouseListenerZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);	
	}
		
	public NavigatorMouseListenerZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent, String sNavigatorElementAlias) throws ExceptionZZZ{
		super(objKernel);
		NavigatorMouseListenerNew_(panelParent, sNavigatorElementAlias);
	}
	
	private void NavigatorMouseListenerNew_(IPanelCascadedZZZ panelParent,String sNavigatorElementAlias) {
				this.setPanelParent(panelParent);
				this.setNavigatorElementAlias(sNavigatorElementAlias);
				
				
				//+++ der EventBroker wird verwendet um alle Komponenten über den MouseClick zu informieren
				//ISenderNavigatorElementSwitchZZZ objEventBroker = groupc.getSenderUsed();
				//this.setSenderUsed(objEventBroker);
	}
	
	@Override
	public boolean customMouseClicked(MouseEvent e, boolean bQueryResult) {
		System.out.println("Element MouseClicked: " + this.getNavigatorElementAlias());
		
		//20210815 Benutze den SwingWorker, ggfs. besser wg. Thread Behandlung
		boolean bActiveState = true;
		
		String[] saFlag = null;
		String sAlias = this.getNavigatorElementAlias();
		IPanelCascadedZZZ panelParent = this.getPanelParent();
		
		//Merke: Der MouseListener hängt am einzelnen NavigatorElement. Er bekommt von den anderen Elemente nichts mit.
		//       Im SwingWorker wird bei der Behandlung des Events abgeprüft, ob der Event zuvor schon geworfen worden ist.
		//       Wurde der gleiche Event schon vorher geworfen, dann wird abgebrochen und nicht weiter gemacht.
		
		
		//20210825 Was ist denn im Modell vorhanden, also das wo der Listener erstellt wird? Das sollte dann  IPanelCascadedZZZ panelParent = this.getPanelParent();		
		SwingWorker4ProgramCLICK worker = new SwingWorker4ProgramCLICK(objKernel, panelParent, sAlias, bActiveState, saFlag);						
		worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.
			
		return true;		
	}

	@Override
	public boolean customMousePressed(MouseEvent e, boolean bQueryResult) {
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
	@Override
	public ISenderNavigatorElementSwitchZZZ getSenderUsed() {
		return this.objEventBroker;
	}

	@Override
	public void setSenderUsed(ISenderNavigatorElementSwitchZZZ objEventBroker) {
		this.objEventBroker = objEventBroker;			
	}
	
	@Override
	public String getNavigatorElementAlias() {
		return this.sNavigatorElementAlias;
	}

	@Override
	public void setNavigatorElementAlias(String sNavigatorElementAlias) {
		this.sNavigatorElementAlias = sNavigatorElementAlias;
	}

	//#### Innere Klassen
	//##############################################################
	class SwingWorker4ProgramCLICK extends KernelSwingWorkerZZZ{
		private IPanelCascadedZZZ panel;
		private String sNavigatorElementAlias = null;
		private boolean bActiveState;
		
		private String[] saFlag4Program;	
		private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
													// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
						
		
		
		
		public SwingWorker4ProgramCLICK(IKernelZZZ objKernel, IPanelCascadedZZZ panel, String sNavigatorElementAlias, boolean bActiveState, String[] saFlag4Program){
			super(objKernel);
			this.panel = panel;
			this.saFlag4Program = saFlag4Program;	
			
			this.sNavigatorElementAlias = sNavigatorElementAlias;
			this.bActiveState = bActiveState;
		}
		
		//#### abstracte - Method aus SwingWorker
		public Object construct() {
			try {	
				//Wird ein NavigatorElement aktiv geschaltet, gehören alle anderen NavigatorElemente passiv geschaltet.
				
				String sAlias = this.sNavigatorElementAlias;
				System.out.println("Im SwingWorker4ProgramCLICK fuer den Alias '"+sAlias+"'");
				
				//Das Umschalten macht man, indem man einen Event - Wirft,
				//Alle am Event "registrierten" Labels/Componentent, bzw. die NavigatorElementCollection sollen dann reagieren.
				//Die NavigatorElementCollection durchgehen und die anderen "nicht geclickt setzen", diese Element "geclickt setzen";
				
				//### Den Event starten,
				if(objEventBroker!=null) {
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#EventBroker starten !!!!!!!!");							
					EventNavigatorElementSwitchZZZ eventNew= new EventNavigatorElementSwitchZZZ(panel, 10002, sAlias, true);				
					objEventBroker.fireEvent(eventNew);	
				}else {
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#ACHTUNG KEIN EventBroker verfuegbar zum Starten !!!!!!!!");
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
						
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#NAVIGATOR ELEMENT GECLICKT");
						logLineDate("NAVIGATOR ELEMENT GECLICKT");					
												
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

	@Override
	public ITrayZZZ getTrayParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTrayParent(ITrayZZZ objTray) {
		// TODO Auto-generated method stub
		
	}

}
