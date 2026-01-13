package basic.zKernelUI.component.model;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;


/** Komponenten, sollen sich an andere Komponenten (, die als Sender auftreten, s. ISenderSelectionResetZZZ) anmelden k�nnen.
 *   Falls dann die Sender-Komponente einen event "abfeuert", dann wird doReset(...) durchgef�hrt.
 *    
 * @author lindhaueradmin
 *
 */
public interface IListenerSelectionResetZZZ extends EventListener{
	/** Setzt die Komponente, welche dieses Interface einbindet zur�ck
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:07:59
	 */

	/** Im Kernel wird zuerst setEventPrevious(...) aufgerufen, dann doRestCustom(...)
	 * 
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:13:27
	 * @throws ExceptionZZZ 
	 */
	public abstract void doReset(EventComponentSelectionResetZZZ eventSelectionResetNew) throws ExceptionZZZ;
	
	/** die Methode, welche von den Komponenten �berschrieben werden muss.
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:12:54
	 */
	public abstract void doResetCustom(EventComponentSelectionResetZZZ eventSelectionResetNew) throws ExceptionZZZ;
	
	/**Ziel: Den neuen Event mit dem alten vergleichen zu k�nnen. Falls die Events an entscheidender Stelle gleich sind, 
	 *        wird doResetNicht durchgef�hrt.
	* @return
	* 
	* lindhaueradmin; 08.02.2007 10:07:23
	 */
	public abstract EventComponentSelectionResetZZZ getEventPrevious() throws ExceptionZZZ;    
	public abstract void setEventPrevious(EventComponentSelectionResetZZZ eventSelectionResetNew) throws ExceptionZZZ;
}
