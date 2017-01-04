package basic.zKernelUI.component;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;

/** Im KernelActionCascadedZZZ wird die Methode actionPerformed(ActionEvent e) überschrieben.
 *   Es wird eine "Reihenfolge von Methoden" festgelegt, die dann in daraus erbenden Klassen mit Inhalt gefüllt werden können.
 *   
 *   Ziel ist es z.B. einen CANCEL Button zur Verfügung zu stellen,
 *   der eine Rückfrage erlaubt und basierend auf dem Ergebnis der Rückfrage 
 *   dann ggf. als actionPerformPostCustom(...) das Schliessen des ParentFrames bewirkt.
 *   
 *   Reihenfolge der "Events":
 *   boolean b1 = actionPerformQueryCustom(..);
 *   boolean b2 = actionPerformCustom(..., b1);
 *   
 *   //Hinweis 1: In dieser Methode wird tatsächlich der Rückgabewert der QueryCustom-Methode erwartet.
 *   //Hinweis 2: Diese Methode wird nur ausgeführt, wenn das Ergebnis von actionPerformCustom(..) true ist. 
 *   if(b2==true) actionPerformPostCustom(..., b1);  
 *   
 *   
 *  
 * @author lindhaueradmin
 *
 */
public interface IButtonEventZZZ {
	public abstract boolean actionPerformCustom(ActionEvent ae, boolean bQueryResultContinue) throws ExceptionZZZ;
	public abstract boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ;
	public abstract void actionPerformPostCustom(ActionEvent ae, boolean bPerfomResultContinue) throws ExceptionZZZ;
	
	
	/**Falls z.B. eine ExceptionZZZ (die beispielsweise in actionPerformCustom passierte) in einem Feld eines UIClients ausgegeben werden soll, so muß man diese Methode überschreiben.
	* @param ae
	* @param ez
	* 
	* lindhaueradmin; 08.03.2008 10:22:57
	 */
	public abstract void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez);	
}
