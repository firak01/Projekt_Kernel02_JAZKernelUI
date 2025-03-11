package basic.zKernelUI.component;

import static java.lang.System.out;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;	
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernelUI.component.model.EventComponentSelectionResetZZZ;
import basic.zKernelUI.component.model.IListenerSelectionResetZZZ;
import custom.zKernel.LogZZZ;

public abstract class KernelJTextAreaListening4ComponentSelectionResetZZZ extends JTextArea implements ITextAreaListening4ComponentSelectionResetZZZ, IObjectZZZ, IFlagZEnabledZZZ, IKernelUserZZZ, IListenerSelectionResetZZZ{
	private IKernelZZZ objKernel;
	private LogZZZ objLog;
	
	private EventComponentSelectionResetZZZ eventPrevious;
	private boolean bFlagDebug;
	private boolean bFlagInit;
	private boolean bFlagUseEventResetDefault;
private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	public KernelJTextAreaListening4ComponentSelectionResetZZZ(IKernelZZZ objKernel, String sTextInitial){
		super(sTextInitial);
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
	}
	
	public KernelJTextAreaListening4ComponentSelectionResetZZZ(IKernelZZZ objKernel, String sTextInitial, int iRow, int iColumn){
		super(sTextInitial, iRow, iColumn);
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
	}
	
	public final void doReset(EventComponentSelectionResetZZZ eventNew) {
		if(! eventNew.equals(this.getEventPrevious())){
			if(this.getFlag("useEventResetDefault")==true){
				String stemp = eventNew.getComponentText();
				this.setText(stemp);
			}
		
			this.doResetCustom(eventNew);
			
			this.setEventPrevious(eventNew);
		}
	}
	
	public IKernelZZZ getKernelObject() {		
		return this.objKernel;
	}

	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {		
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}

	public abstract void doResetCustom(EventComponentSelectionResetZZZ eventSelectionResetNew);

	public EventComponentSelectionResetZZZ getEventPrevious() {		
		return this.eventPrevious;
	}

	public void setEventPrevious(EventComponentSelectionResetZZZ eventSelectionResetNew) {
		this.eventPrevious = eventSelectionResetNew;
	}
	
	/* (non-Javadoc)
	 * @see basic.zBasic.IFunctionZZZ#getFlag(java.lang.String)
	 */
//	public boolean getFlag(String sFlagName) {
//		boolean bFunction = false;
//	main:{
//		if(sFlagName == null) break main;
//		if(sFlagName.equals("")) break main;
//		
//		// hier keine Superclass aufrufen, ist ja nicht ObjectZZZ
//		//bFunction = super.getFlag(sFlagName);
//		//if(bFunction == true) break main;
//		
//		// Die Flags dieser Klasse setzen
//		String stemp = sFlagName.toLowerCase();
//		if(stemp.equals("debug")){
//			bFunction = this.bFlagDebug;
//			break main;
//		}else if(stemp.equals("init")){
//			bFunction = this.bFlagInit;
//			break main;
//		}else if(stemp.equals("useeventresetdefault")){
//			bFunction = this.bFlagUseEventResetDefault;
//			break main;
//		}else{
//			bFunction = false;
//		}		
//	}	// end main:
//	
//	return bFunction;	
//	}

	/** Function can set the flags of this class or the super-class.
	 * The following new flags are supported:
	 * --- debug
 * @see basic.zBasic.IFunctionZZZ_loesch#setFlag(java.lang.String, boolean)
 */
//public boolean setFlag(String sFlagName, boolean bFlagValue){
//	boolean bFunction = false;
//	main:{
//		if(StringZZZ.isEmpty(sFlagName)) break main;
//		
//		// hier keine Superclass aufrufen, ist ja nicht von ObjectZZZ erbend
//		// bFunction = super.setFlag(sFlagName, bFlagValue);
//		// if(bFunction == true) break main;
//		
//		// Die Flags dieser Klasse setzen
//		String stemp = sFlagName.toLowerCase();
//		if(stemp.equals("debug")){
//			this.bFlagDebug = bFlagValue;
//			bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//			break main;
//		}else if(stemp.equals("init")){
//			this.bFlagInit = bFlagValue;
//			bFunction = true;
//			break main;
//		}else if(stemp.equals("useeventresetdefault")){
//			this.bFlagUseEventResetDefault = bFlagValue;
//			bFunction = true;
//			break main;
//		}else{
//			bFunction = false;
//		}	
//		
//	}	// end main:
//	
//	return bFunction;	
//}

//20170308: Enum FLAGZ nutzen
//### FlagMethods ##########################		
	public Class getClassFlagZ(){
		return ITextAreaListening4ComponentSelectionResetZZZ.FLAGZ.class;
	}

		public HashMap<String, Boolean>getHashMapFlag(){
			return this.hmFlag;
		} 
	
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			

			bFunction = this.proofFlagExists(sFlagName);												
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}

	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
	 * @param name 
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
//	public boolean proofFlagZExists(String sFlagName) throws ExceptionZZZ{
//		boolean bReturn = false;
//		main:{
//			if(StringZZZ.isEmpty(sFlagName))break main;
//				System.out.println("sFlagName = " + sFlagName);
//				
//				Class objClass4Enum = this.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
//				String sFilterName = objClass4Enum.getSimpleName();
//				
//				ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
//				if(listEmbedded == null) break main;
//				out.format("%s# ListEmbeddedClasses.size()...%s%n", ReflectCodeZZZ.getPositionCurrent(), listEmbedded.size());
//				
//				for(Class objClass : listEmbedded){
//					out.format("%s# Class...%s%n", ReflectCodeZZZ.getPositionCurrent(), objClass.getName());
//					Field[] fields = objClass.getDeclaredFields();
//					for(Field field : fields){
//						if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zurückgegeben.
//							//out.format("%s# Field...%s%n", ReflectCodeZZZ.getPositionCurrent(), field.getName());
//							if(sFlagName.equalsIgnoreCase(field.getName())){
//								bReturn = true;
//								break main;
//							}
//						}				
//				}//end for
//			}//end for
//				
//			//20170307: Durch das Verschieben von FLAGZ mit den Werten DEBUG und INIT in das IObjectZZZ Interface, muss man explizit auch dort nachsehen.
//		   //                Merke: Das Verschieben ist deshlab notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.
//		
//							
//				//TODO GOON: 
//				//Merke: bReturn = set.contains(sFlagName.toUpperCase());
//				//          Weil das nicht funktioniert meine Util-Klasse erstellen, die dann den String tatsächlich prüfen kann
//				
//				Class<FLAGZ> enumClass = FLAGZ.class;
//				//EnumSet<FLAGZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
//				
//				for(Object obj : FLAGZ.class.getEnumConstants()){
//					System.out.println(obj + "; "+obj.getClass().getName());
//					if(sFlagName.equalsIgnoreCase(obj.toString())) {
//						bReturn = true;
//						break main;
//					}
//					//set.add((FLAGZ) obj);
//				}				
//		}//end main:
//		return bReturn;
//	}


	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.WriteLineDate(sLog);
		}		
	}	
	
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
	 */
	public boolean getFlag(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
			Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
	
	@Override
	public String[] getFlagZ(boolean bFlagValueToSearchFor) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getFlagZ() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getFlagZ_passable(boolean bValueToSearchFor,
			IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getFlagZ_passable(IFlagZEnabledZZZ objUsingFlagZ)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
}

