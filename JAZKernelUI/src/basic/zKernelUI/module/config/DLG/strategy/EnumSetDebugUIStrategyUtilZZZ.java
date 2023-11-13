package basic.zKernelUI.module.config.DLG.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Set;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public class EnumSetDebugUIStrategyUtilZZZ extends EnumSetUtilZZZ{

	public EnumSetDebugUIStrategyUtilZZZ(){		
	}
	public EnumSetDebugUIStrategyUtilZZZ(EnumSet<?>enumSetUsed){
		super(enumSetUsed);
	}
	public EnumSetDebugUIStrategyUtilZZZ(Class<?>objClass)throws ExceptionZZZ{
		super(objClass);
	}
	public EnumSetDebugUIStrategyUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory, Class<?> objClass) throws ExceptionZZZ{
		super(objEnumSetFactory,objClass);
	}
	public EnumSetDebugUIStrategyUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory){
		super(objEnumSetFactory);
	}

	//###############	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int computeEnumConstant_StrategyValueForFlagUser(IFlagZUserZZZ obj) {
		int iReturn = 0;
		main:{
			if(obj==null)break main;
			
			 IEnumSetDebugUIStrategyZZZ[] enumaSetMapped = (IEnumSetDebugUIStrategyZZZ[]) DebugUIStrategyZZZ.getThiskeyEnumClassStatic().getEnumConstants();
			 if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt

			 for(IEnumSetDebugUIStrategyZZZ driver : enumaSetMapped) {
				 
				String sFlag = new String(driver.getStrategyFlagName());
				if(!StringZZZ.isEmpty(sFlag)){
					boolean bFlag = obj.getFlag(sFlag);
					if(bFlag) {
						int itemp = driver.getStrategyValue();  
						iReturn = iReturn + itemp;
					}
				}					
			 }
			 
			
		}//end main:
		return iReturn;
	}
	
	//###############	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Long readEnumConstant_ThiskeyValue(Class<IEnumSetDebugUIStrategyZZZ> clazz, String name) {
		Long lngReturn = new Long(-1);
		main:{
	    if (clazz==null || name==null || name.isEmpty()) break main;
	  
	    
	    //IEnumSetTroopFleetVariantTHM[] enumaSetMapped = clazz.getEnumConstants(); //Luse.thm.persistence.model.Immutabletext$EnumImmutabletext; cannot be cast to [Luse.thm.persistence.interfaces.enums.IEnumSetTroopFleetVariantTHM;
	    IEnumSetDebugUIStrategyZZZ[] enumaSetMapped = clazz.getEnumConstants();
	    if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt
	    
		for(IEnumSetDebugUIStrategyZZZ driver : enumaSetMapped) {
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
			
            String sTest = new String(driver.getThiskey().toString());
			if(!StringZZZ.isEmpty(sTest)){
			  if(driver.getName().equals(name)){
				  lngReturn = driver.getThiskey();
				  break main;
			  }
		  }
	
		}//end for
		}//end main:
		return lngReturn;
	}
	
	public static String readEnumConstant_UniquetextValue(Class<IEnumSetDebugUIStrategyZZZ> clazz, String name) {
		String sReturn = new String("");
		main:{
	    if (clazz==null || name==null || name.isEmpty()) break main;
	  
	    
	    IEnumSetDebugUIStrategyZZZ[] enumaSetMapped = clazz.getEnumConstants();
	    if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt
	    
	  	for(IEnumSetDebugUIStrategyZZZ driver : enumaSetMapped) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
		
	
		if(!StringZZZ.isEmpty(driver.getUniquetext())){
		  if(driver.getName().equals(name)){
			  sReturn = driver.getUniquetext();
			  break main;
		  }
	  }
		}//end for
		}//end main:
		return sReturn;
	}
	
	public static int readEnumConstant_StrategyValueByFlagName(Class<IEnumSetDebugUIStrategyZZZ> clazz, String sStrategyFlagName) {
		int iReturn = 0;
		main:{
	    if (clazz==null || sStrategyFlagName==null || sStrategyFlagName.isEmpty()) break main;
	  
	    
	    IEnumSetDebugUIStrategyZZZ[] enumaSetMapped = clazz.getEnumConstants();
	    if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt
	    
	  	for(IEnumSetDebugUIStrategyZZZ driver : enumaSetMapped) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
		
	
		if(!StringZZZ.isEmpty(driver.getStrategyFlagName())){
		  if(driver.getStrategyFlagName().equals(sStrategyFlagName)){
			  iReturn = driver.getStrategyValue();
			  break main;
		  }
	  }
		}//end for
		}//end main:
		return iReturn;
	}
	
	public static String readEnumConstant_CategorytextValue(Class<IEnumSetDebugUIStrategyZZZ> clazz, String name) {
		String sReturn = new String("");
		main:{
	    if (clazz==null || name==null || name.isEmpty()) break main;
	  
	    
	    IEnumSetDebugUIStrategyZZZ[] enumaSetMapped = clazz.getEnumConstants();
	    if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt
	    
	  	for(IEnumSetDebugUIStrategyZZZ driver : enumaSetMapped) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
		
	
		if(!StringZZZ.isEmpty(driver.getCategorytext())){
		  if(driver.getName().equals(name)){
			  sReturn = driver.getCategorytext();
			  break main;
		  }
	  }
		}//end for
		}//end main:
		return sReturn;
	}
	
	
	
}

