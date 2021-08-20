package basic.zKernelUI.component;

import java.awt.event.MouseEvent;

import basic.zBasic.ExceptionZZZ;

public interface IMouseEventsZZZ {
		
	public abstract boolean customMouseClicked(MouseEvent e, boolean bQueryResult);
	public abstract boolean customMousePressed(MouseEvent e, boolean bQueryResult);
	public abstract boolean customMouseReleased(MouseEvent e, boolean bQueryResult);
	public abstract boolean customMouseEntered(MouseEvent e, boolean bQueryResult);
	public abstract boolean customMouseExited(MouseEvent e, boolean bQueryResult);
	
	
	public abstract boolean actionPerformQueryCustom(MouseEvent me) throws ExceptionZZZ;
	public abstract boolean actionPerformPostCustom(MouseEvent me, boolean bPerfomResultContinue) throws ExceptionZZZ;
	
	
	/**Falls z.B. eine ExceptionZZZ (die beispielsweise in customMouse.... passierte) in einem Feld eines UIClients ausgegeben werden soll, so muss man diese Methode ueberschreiben.
	 */
	public abstract void actionPerformCustomOnError(MouseEvent me, ExceptionZZZ ez);
}
