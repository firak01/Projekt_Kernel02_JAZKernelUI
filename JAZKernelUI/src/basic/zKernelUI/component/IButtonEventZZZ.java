package basic.zKernelUI.component;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import basic.zBasic.ExceptionZZZ;

/** Im KernelActionCascadedZZZ wird die Methode actionPerformed(ActionEvent e) �berschrieben.
 *   Es wird eine "Reihenfolge von Methoden" festgelegt, die dann in daraus erbenden Klassen mit Inhalt gef�llt werden k�nnen.
 *   
 *   Ziel ist es z.B. einen CANCEL Button zur Verf�gung zu stellen,
 *   der eine R�ckfrage erlaubt und basierend auf dem Ergebnis der R�ckfrage 
 *   dann ggf. als actionPerformPostCustom(...) das Schliessen des ParentFrames bewirkt.
 *   
 *   Reihenfolge der "Events":
 *   boolean b1 = actionPerformQueryCustom(..);
 *   boolean b2 = actionPerformCustom(..., b1);
 *   
 *   //Hinweis 1: In dieser Methode wird tats�chlich der R�ckgabewert der QueryCustom-Methode erwartet.
 *   //Hinweis 2: Diese Methode wird nur ausgef�hrt, wenn das Ergebnis von actionPerformCustom(..) true ist. 
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
	
	
	/**Falls z.B. eine ExceptionZZZ (die beispielsweise in actionPerformCustom passierte) in einem Feld eines UIClients ausgegeben werden soll, so mu� man diese Methode �berschreiben.
	* @param ae
	* @param ez
	* 
	* lindhaueradmin; 08.03.2008 10:22:57
	 * @throws ExceptionZZZ 
	 */
	public abstract void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) throws ExceptionZZZ;	
}
