package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;
import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zKernelUI.component.IComponentValueModelZZZ;

/** Komponenten, sollen sich an andere Komponenten (, die als Sender auftreten, s. ISenderSelectionResetZZZ) anmelden k�nnen.
 *   Falls dann die Sender-Komponente einen event "abfeuert", dann wird doReset(...) durchgef�hrt.
 *    
 * @author lindhaueradmin
 *
 */
public interface IListenerComponentGroupSwitchZZZ extends EventListener{
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
	public abstract void doSwitch(EventComponentGroupSwitchZZZ event) throws ExceptionZZZ;
	
	/** die Methode, welche von den Komponenten �berschrieben werden muss.
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:12:54
	 */
	public abstract void doSwitchCustom(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew)  throws ExceptionZZZ;
	
	/**Ziel: Den neuen Event mit dem alten vergleichen zu koennen. Falls die Events an entscheidender Stelle gleich sind, 
	 *        wird doSwitch nicht durchgefuehrt.
	* @return
	 */
	public abstract EventComponentGroupSwitchZZZ getEventPrevious();    
	public abstract void setEventPrevious(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew);
	
	public abstract String getGroupAlias();
	public abstract void setGroupAlias(String sAlias);
	
	public abstract String getGroupTitle();
	public abstract void setGroupTitle(String sTitle);
	
	public IComponentValueModelZZZ getComponentValueProvider();
	public void setComponentValueProvider(IComponentValueModelZZZ objComponentValueProvider);
	public HashMapIndexedZZZ<Integer,ArrayList<String>> getComponentValuesCustom() throws ExceptionZZZ; 
	
}
