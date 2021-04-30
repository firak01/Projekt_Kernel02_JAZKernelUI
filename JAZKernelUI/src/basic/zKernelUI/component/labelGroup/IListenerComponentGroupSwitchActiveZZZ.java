package basic.zKernelUI.component.labelGroup;

import java.util.EventListener;

import basic.zKernelUI.component.model.EventComponentSelectionResetZZZ;


/** Komponenten, sollen sich an andere Komponenten (, die als Sender auftreten, s. ISenderSelectionResetZZZ) anmelden k�nnen.
 *   Falls dann die Sender-Komponente einen event "abfeuert", dann wird doReset(...) durchgef�hrt.
 *    
 * @author lindhaueradmin
 *
 */
public interface IListenerComponentGroupSwitchActiveZZZ extends EventListener{
	public enum FLAGZ{
		useEventSwitchDefault; 
	}
	
	/** Schaltet ggfs. die Komponente, welche dieses Interface einbindet. Z.B. sichtbar/verbergen
	* @param eventComponentSwicht
	 */

	/** Im Kernel wird zuerst setEventPrevious(...) aufgerufen, dann doRestCustom(...)
	 * 
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:13:27
	 */
	public abstract void doSwitch(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew);
	
	/** die Methode, welche von den Komponenten �berschrieben werden muss.
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:12:54
	 */
	public abstract void doSwitchCustom(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew);
	
	/**Ziel: Den neuen Event mit dem alten vergleichen zu koennen. Falls die Events an entscheidender Stelle gleich sind, 
	 *        wird doSwitch nicht durchgefuehrt.
	* @return
	 */
	public abstract EventComponentGroupSwitchZZZ getEventPrevious();    
	public abstract void setEventPrevious(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew);
}
