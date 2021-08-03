package basic.zKernelUI.component.adjustmentNavigator;

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
public interface IListenerAdjustmentNavigatorSwitchZZZ extends EventListener{
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
	public abstract void doSwitch(EventAdjustmentNavigatorSwitchZZZ event) throws ExceptionZZZ;
	
	/** die Methode, welche von den Komponenten �berschrieben werden muss.
	* @param eventSelectionResetNew
	* 
	* lindhaueradmin; 08.02.2007 10:12:54
	 */
	public abstract void doSwitchCustom(EventAdjustmentNavigatorSwitchZZZ eventComponentGroupSwitchNew)  throws ExceptionZZZ;
	
	/**Ziel: Den neuen Event mit dem alten vergleichen zu koennen. Falls die Events an entscheidender Stelle gleich sind, 
	 *        wird doSwitch nicht durchgefuehrt.
	* @return
	 */
	public abstract EventAdjustmentNavigatorSwitchZZZ getEventPrevious();    
	public abstract void setEventPrevious(EventAdjustmentNavigatorSwitchZZZ eventComponentGroupSwitchNew);
	
	public abstract String getGroupAlias();
	public abstract void setGroupAlias(String sAlias);
	
	public abstract String getGroupTitle();
	public abstract void setGroupTitle(String sTitle);
	
	public IModelAdjustmentNavigatorValueZZZ getComponentValueProvider();
	public void setComponentValueProvider(IModelAdjustmentNavigatorValueZZZ objComponentValueProvider);
	public HashMapIndexedZZZ<Integer,ArrayList<String>> getComponentValuesCustom() throws ExceptionZZZ; 
	
}
