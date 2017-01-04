package basic.zKernelUI.component.model;

import java.util.EventListener;


/** Komponenten, sollen sich an andere Komponenten (, die als Sender auftreten, s. ISenderSelectionResetZZZ) anmelden können.
 *   Falls dann die Sender-Komponente einen event "abfeuert", dann wird doReset(...) durchgeführt.
 *    
 * @author lindhaueradmin
 *
 */
public interface IListenerSelectionResetZZZ extends EventListener{
	/** Setzt die Komponente, welche dieses Interface einbindet zurück
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:07:59
	 */

	/** Im Kernel wird zuerst setEventPrevious(...) aufgerufen, dann doRestCustom(...)
	 * 
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:13:27
	 */
	public abstract void doReset(EventComponentSelectionResetZZZ eventSelectionResetNew);
	
	/** die Methode, welche von den Komponenten überschrieben werden muss.
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:12:54
	 */
	public abstract void doResetCustom(EventComponentSelectionResetZZZ eventSelectionResetNew);
	
	/**Ziel: Den neuen Event mit dem alten vergleichen zu können. Falls die Events an entscheidender Stelle gleich sind, 
	 *        wird doResetNicht durchgeführt.
	* @return
	* 
	* lindhaueradmin; 08.02.2007 10:07:23
	 */
	public abstract EventComponentSelectionResetZZZ getEventPrevious();    
	public abstract void setEventPrevious(EventComponentSelectionResetZZZ eventSelectionResetNew);
}
