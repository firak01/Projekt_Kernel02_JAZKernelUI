package basic.zKernelUI.component.filler;


import static java.lang.System.out;

import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDatabaseMappedValueZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDriverMappedValueZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ;

/**
 *  
 * 
 * @author Fritz Lindhauer, 28.05.2022, 09:30:51
 * siehe: EnumSetFactoryZZZ als Ideenvorlage
 */
public class ModelComponentFillerFactoryZZZ extends ObjectZZZ implements IModelComponentFillerFactoryZZZ {
	private static ModelComponentFillerFactoryZZZ objComponentFactory = null;  //muss static sein, wg. getInstance()!!!
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	private ModelComponentFillerFactoryZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
	}
	private ModelComponentFillerFactoryZZZ(){
		super();
	}
	
	public static ModelComponentFillerFactoryZZZ getInstance(){
		if(objComponentFactory==null){
			objComponentFactory = new ModelComponentFillerFactoryZZZ();
		}
		return objComponentFactory;		
	}
	
	/*
	 * Das ist der Kerngedanke dieser Factory.
	 * Gib den Klassennamen an, dann kann man die static Methode aufrufen (hart verdrahtet, denn Reflection funktioniert leider nicht).
	 */
//	public EnumSet<?>getEnumSet(Class objClassEnum) throws ExceptionZZZ{
//		EnumSet<?>objEnumSet = null;
//		main:{
//			if(objClassEnum==null){
//				ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}
//			String sClassNameEnum = objClassEnum.getName();
//			objEnumSet = getEnumSet(sClassNameEnum);
//		}//end main:
//		return objEnumSet;
//	}
	
	public EnumSet<?>getEnumSet(String sClassNameEnum) throws ExceptionZZZ{
		EnumSet<?>objEnumSetReturn = null;
		main:{
			if(StringZZZ.isEmpty(sClassNameEnum)){
				ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Merke: Switch Anweisung mit String ist erst ab Java 1.7 möglich			
			if (sClassNameEnum.equals("basic.zBasic.util.persistence.jdbc.JdbcDatabaseMappedValueZZZ$JdbcDatabaseTypeZZZ")){
			//if (sClassNameEnum.equals("basic.zBasic.util.persistence.jdbc.JdbcDatabaseTypeZZZ")){
				//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
	        	out.format("%s# sClassNameEnum wurde hier gefunden: %s%n", ReflectCodeZZZ.getPositionCurrent(),sClassNameEnum);
	        	//objEnumSetReturn= JdbcDatabaseTypeZZZ.getEnumSet();     
	        	objEnumSetReturn= JdbcDatabaseMappedValueZZZ.JdbcDatabaseTypeZZZ.getEnumSet();
			}else if(sClassNameEnum.equals("basic.zBasic.util.persistence.jdbc.JdbcDriverMappedValueZZZ$JdbcDriverClassTypeZZZ")){
		 //}else if(sClassNameEnum.equals("basic.zBasic.util.persistence.jdbc.JdbcDriverClassTypeZZZ")){
				//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
				out.format("%s# sClassNameEnum wurde hier gefunden: %s%n", ReflectCodeZZZ.getPositionCurrent(),sClassNameEnum);
				//objEnumSetReturn= JdbcDriverClassTypeZZZ.getEnumSet();
				objEnumSetReturn= JdbcDriverMappedValueZZZ.JdbcDriverClassTypeZZZ.getEnumSet();				
				
		   }else if (sClassNameEnum.equals("basic.zKernel.config.KernelConfigDefaultEntryZZZ$EnumConfigDefaultEntryZZZ")) {//beachte: Innere Klasse, mit $ getrennt.
	        	//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
//		        	String sInfo = String.format("%1$s # als sClassNameEnum wird hier gefunden: %2$s", sClassNameEnum,ReflectCodeZZZ.getPositionCurrent());
//					System.out.print(sInfo);	        	
	        	objEnumSetReturn= KernelConfigDefaultEntryZZZ.EnumConfigDefaultEntryZZZ.getEnumSet();
	        	
	        }else{	        	     	
	          	//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
	        	//out.format("%s# sClassNameEnum wird hier NICHT gefunden: %s%n", ReflectCodeZZZ.getPositionCurrent(),sClassNameEnum);
				//String sError = out.toString(); //geht nicht, der Stream ist schon längst zuende
				
				//Lösung ist:
//				StringBuilder sbuf = new StringBuilder();
//				Formatter fmt = new Formatter(sbuf);
//				//Beispiel fmt.format("PI = %f%n", Math.PI);
//				fmt.format(": %1$s # als sClassNameEnum wird hier NICHT gefunden: %2$s", sClassNameEnum,ReflectCodeZZZ.getPositionCurrent());
//				String sError=sbuf.toString();
				
				//Oder noch kürzere Lösung ist:
				String sError = String.format("%1$s # als sClassNameEnum wird hier NICHT gefunden: %2$s", sClassNameEnum,ReflectCodeZZZ.getPositionCurrent());
				System.out.print(sError);	
				
				//Wenn es die Klasse nicht gibt. Keinen Fehler werfen, da ggfs. über einen Vererbungsmechanismus ja zuerst in der Superklasse nachgesehen wurde und dann ggfs. weiter in Kindklassen gesucht wird.
	        	//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
//	        	ExceptionZZZ ez  = new ExceptionZZZ(": " + sError, iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
	        }
		
	}//end main:
	return objEnumSetReturn;
	}
	
	public ArrayList<String>getFillerForLayoutManagerUsed(LayoutManager objLayoutManager) throws ExceptionZZZ{
		ArrayList<String>listaReturn = null;
		main:{
			if(objLayoutManager==null){
				ExceptionZZZ ez  = new ExceptionZZZ("LayoutManagerObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			
		}//end main:
		return listaReturn;
	}
}
