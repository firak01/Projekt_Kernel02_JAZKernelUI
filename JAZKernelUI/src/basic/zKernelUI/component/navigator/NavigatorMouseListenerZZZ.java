package basic.zKernelUI.component.navigator;

import java.awt.event.MouseEvent;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelMouseListenerCascadedZZZ;

public class NavigatorMouseListenerZZZ extends KernelMouseListenerCascadedZZZ implements INavigatorElementMouseListenerZZZ{
	protected String sNavigatorElementAlias=null;
	
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
		
		//TODOGOON; //20210815 Benutze den im Konstruktor übergebenen ActionSwitchZZZ, ggfs. besser wg. Thread Behandlung
				
		
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
		// TODO Auto-generated method stub
		return false;
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
