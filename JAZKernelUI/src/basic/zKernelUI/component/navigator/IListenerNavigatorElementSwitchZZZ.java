package basic.zKernelUI.component.navigator;

import java.util.ArrayList;
import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

/** Komponenten, sollen sich an andere Komponenten (, die als Sender auftreten, s. ISenderSelectionResetZZZ) anmelden k�nnen.
 *   Falls dann die Sender-Komponente einen event "abfeuert", dann wird doReset(...) durchgef�hrt.
 *    
 * @author lindhaueradmin
 *
 */
public interface IListenerNavigatorElementSwitchZZZ extends EventListener{
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
	 * @throws ExceptionZZZ 
	 */
	public abstract void doSwitch(EventNavigatorElementSwitchZZZ event) throws ExceptionZZZ;
	
	/** die Methode, welche von den Komponenten �berschrieben werden muss.
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:12:54
	 */
	public abstract void doSwitchCustom(EventNavigatorElementSwitchZZZ eventComponentGroupSwitchNew)  throws ExceptionZZZ;
	
	/**Ziel: Den neuen Event mit dem alten vergleichen zu koennen. Falls die Events an entscheidender Stelle gleich sind, 
	 *        wird doSwitch nicht durchgefuehrt.
	* @return
	 */
	public abstract EventNavigatorElementSwitchZZZ getEventPrevious();    
	public abstract void setEventPrevious(EventNavigatorElementSwitchZZZ eventComponentGroupSwitchNew);
	
	public abstract String getGroupAlias();
	public abstract void setGroupAlias(String sAlias);
	
	public abstract String getGroupTitle();
	public abstract void setGroupTitle(String sTitle);
	
	public IModelNavigatorValueZZZ getComponentValueProvider();
	public void setComponentValueProvider(IModelNavigatorValueZZZ objComponentValueProvider);
	public HashMapIndexedZZZ<Integer,ArrayList<String>> getComponentValuesCustom() throws ExceptionZZZ; 
	
}
