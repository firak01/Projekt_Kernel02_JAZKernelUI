package basic.zKernelUI.component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public abstract class KernelMouseListenerCascadedZZZ extends KernelUseObjectZZZ implements MouseListener, IMouseEventsZZZ,	IActionCascadedZZZ{
	protected IPanelCascadedZZZ panelParent;
	
	public KernelMouseListenerCascadedZZZ() {
		super();
	}
	
	public KernelMouseListenerCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);		
	}
	
	public KernelMouseListenerCascadedZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent) throws ExceptionZZZ{
		super(objKernel);
		this.panelParent = panelParent;	
	}
	
	@Override
	public KernelJFrameCascadedZZZ getFrameParent() {
		// TODO Auto-generated method stub
		return null;
	}

	//Getter/Setter
	@Override
	public void setFrameParent(KernelJFrameCascadedZZZ frameParent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPanelCascadedZZZ getPanelParent() {
		return this.panelParent;
	}

	@Override
	public void setPanelParent(IPanelCascadedZZZ objPanel) {
		this.panelParent = objPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		try{
			boolean bQueryResult = this.actionPerformQueryCustom(me); //Durch Ueberschreiben diesr Methode koennen erbende Klassen noch anderen Code ausfuehren			
			boolean bPerformResult = this.customMouseClicked(me, bQueryResult);  //Durch Ueberschreiben dieser Methode koennen erbende Klassen noch anderen Code ausfuehren
			if(bPerformResult == true){
				this.actionPerformPostCustom(me, bQueryResult);
			}
		} catch (ExceptionZZZ ez) {
			//Protokolliern des Fehlers an allen moeglichen Stellen
			this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			System.out.println(ez.getDetailAllLast());
			ez.printStackTrace();
			
			//Das ist z.B. sinnvoll um eine Fehlermeldung in einem Feld eines UIClients sichtbar zu machen
			this.actionPerformCustomOnError(me, ez);
		}
	}

	@Override
	public void mousePressed(MouseEvent me) {
		try{
			boolean bQueryResult = this.actionPerformQueryCustom(me); //Durch Ueberschreiben diesr Methode koennen erbende Klassen noch anderen Code ausfuehren			
			boolean bPerformResult = this.customMousePressed(me, bQueryResult);  //Durch Ueberschreiben dieser Methode koennen erbende Klassen noch anderen Code ausfuehren
			if(bPerformResult == true){
				this.actionPerformPostCustom(me, bQueryResult);
			}
		} catch (ExceptionZZZ ez) {
			//Protokolliern des Fehlers an allen moeglichen Stellen
			this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			System.out.println(ez.getDetailAllLast());
			ez.printStackTrace();
			
			//Das ist z.B. sinnvoll um eine Fehlermeldung in einem Feld eines UIClients sichtbar zu machen
			this.actionPerformCustomOnError(me, ez);
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		try{
			boolean bQueryResult = this.actionPerformQueryCustom(me); //Durch Ueberschreiben diesr Methode koennen erbende Klassen noch anderen Code ausfuehren			
			boolean bPerformResult = this.customMouseReleased(me, bQueryResult);  //Durch Ueberschreiben dieser Methode koennen erbende Klassen noch anderen Code ausfuehren
			if(bPerformResult == true){
				this.actionPerformPostCustom(me, bQueryResult);
			}
		} catch (ExceptionZZZ ez) {
			//Protokolliern des Fehlers an allen moeglichen Stellen
			this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			System.out.println(ez.getDetailAllLast());
			ez.printStackTrace();
			
			//Das ist z.B. sinnvoll um eine Fehlermeldung in einem Feld eines UIClients sichtbar zu machen
			this.actionPerformCustomOnError(me, ez);
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		try{
			boolean bQueryResult = this.actionPerformQueryCustom(me); //Durch Ueberschreiben diesr Methode koennen erbende Klassen noch anderen Code ausfuehren			
			boolean bPerformResult = this.customMouseEntered(me, bQueryResult);  //Durch Ueberschreiben dieser Methode koennen erbende Klassen noch anderen Code ausfuehren
			if(bPerformResult == true){
				this.actionPerformPostCustom(me, bQueryResult);
			}
		} catch (ExceptionZZZ ez) {
			//Protokolliern des Fehlers an allen moeglichen Stellen
			this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			System.out.println(ez.getDetailAllLast());
			ez.printStackTrace();
			
			//Das ist z.B. sinnvoll um eine Fehlermeldung in einem Feld eines UIClients sichtbar zu machen
			this.actionPerformCustomOnError(me, ez);
		}
	}

	@Override
	public void mouseExited(MouseEvent me) {
		try{
			boolean bQueryResult = this.actionPerformQueryCustom(me); //Durch Ueberschreiben diesr Methode koennen erbende Klassen noch anderen Code ausfuehren			
			boolean bPerformResult = this.customMouseExited(me, bQueryResult);  //Durch Ueberschreiben dieser Methode koennen erbende Klassen noch anderen Code ausfuehren
			if(bPerformResult == true){
				this.actionPerformPostCustom(me, bQueryResult);
			}
		} catch (ExceptionZZZ ez) {
			//Protokolliern des Fehlers an allen moeglichen Stellen
			this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			System.out.println(ez.getDetailAllLast());
			ez.printStackTrace();
			
			//Das ist z.B. sinnvoll um eine Fehlermeldung in einem Feld eines UIClients sichtbar zu machen
			this.actionPerformCustomOnError(me, ez);
		}
	}

	@Override
	public abstract boolean customMouseClicked(MouseEvent e, boolean bQueryResult) throws ExceptionZZZ;

	@Override
	public abstract boolean customMousePressed(MouseEvent e, boolean bQueryResult) throws ExceptionZZZ;

	@Override
	public abstract boolean customMouseReleased(MouseEvent e,  boolean bQueryResult) throws ExceptionZZZ;

	@Override
	public abstract boolean customMouseEntered(MouseEvent e,  boolean bQueryResult) throws ExceptionZZZ;

	@Override
	public abstract boolean customMouseExited(MouseEvent e,  boolean bQueryResult) throws ExceptionZZZ;
	
	@Override
	public abstract boolean actionPerformQueryCustom(MouseEvent me) throws ExceptionZZZ;
	
	@Override
	public abstract boolean actionPerformPostCustom(MouseEvent me, boolean bPerfomResultContinue) throws ExceptionZZZ;
	
	
	/**Falls z.B. eine ExceptionZZZ (die beispielsweise in customMouse.... passierte) in einem Feld eines UIClients ausgegeben werden soll, so muss man diese Methode ueberschreiben.
	 */
	@Override
	public abstract void actionPerformCustomOnError(MouseEvent me, ExceptionZZZ ez);
	

}
