package basic.zKernelUI.module.config.DLG.strategy;

import java.util.EnumSet;

import basic.zBasic.persistence.interfaces.enums.ICategoryProviderZZZ;
import basic.zBasic.persistence.interfaces.enums.IThiskeyProviderZZZ;
import basic.zBasic.persistence.interfaces.enums.KeyImmutable;

public class DebugUIStrategyZZZ extends KeyImmutable implements IEnumDebugUIStrategyZZZ, ICategoryProviderZZZ, IThiskeyProviderZZZ<Long>{
   private Long lKey;
   private String sUniquetext;
   private String sCategorytext;

   //Spezielle für die STRATEGY
   private int iStrategyValue;	
   private String sStrategyFlagName;
	
	//Aus IEnumDebugUIStrategyZZZ
	@Override
	public int getStrategyValue() {
		return this.iStrategyValue;
	}
	@Override
	public String getStrategyFlagName() {
		return this.sStrategyFlagName;
	}
	
	//Aus ICategoryProviderZZZ
	@Override
	 public String getUniquetext(){
		 return this.sUniquetext;
	 }
	 protected void setUniquetext(String sUniquetext){
		 this.sUniquetext = sUniquetext;
	 }
	 
	@Override
	 public String getCategorytext(){
		 return this.sCategorytext;
	 }
	 protected void setCategorytext(String sCategorytext){
		 this.sCategorytext= sCategorytext;
	 }

	//Aus KeyImmutable
	//@Transient
	 @Override
	public Class getThiskeyEnumClass() {
		return DebugUIStrategyZZZ.getThiskeyEnumClassStatic();
	}

	//Aus IThiskeyUserZZZ
	@Override
	public EnumSet<?>getEnumSetUsed(){
		return EnumDebugUIStrategy.getEnumSet();
   }

	//### Statische Methode (um einfacher darauf zugreifen zu können). Muss überschrieben werden aus der Key(Immutable)... Klasse.
	public static Class getThiskeyEnumClassStatic(){	    
	    return EnumDebugUIStrategy.class;    	
	}
	
	//#######################################################
	//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
	//### int Key, String shorttext, String longtext, String description
	//#######################################################
	public enum EnumDebugUIStrategy implements IEnumSetDebugUIStrategyZZZ{			
		   	S01(11,"STRATEGIE_ENTRYFIRST","UI Strategie","DEBUGUI_PANELLIST_STRATEGIE_ENTRYFIRST",1), 
		   	S02(12,"STRATEGIE_ENTRYDUMMY","UI Strategie","DEBUGUI_PANELLIST_STRATEGIE_ENTRYDUMMY",2),
	   		S03(13,"STRATEGIE_ENTRYLAST","UI Strategie","DEBUGUI_PANELLIST_STRATEGIE_ENTRYLAST",4);
		
		 	private Long lKey;
		 	private String sUniquetext;
			private String sCategorytext;
			   
		   //Spezielle für die STRATEGY
		   private int iStrategyValue;	 
		   private String sStrategyFlagName;
		   
		   

		   //Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		   EnumDebugUIStrategy(int iKey, String sUniquetext, String sCategorytext, String sStrategyFlagName, int iStrategyValue){
		       this.lKey = Long.valueOf(iKey);
		       this.sUniquetext = sUniquetext;
		       this.sCategorytext = sCategorytext;
		      
		       this.iStrategyValue = iStrategyValue;
		       this.sStrategyFlagName = sStrategyFlagName;
		   }
		   
		   //Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		   //           In der Util-Klasse habe ich aber einen Workaround gefunden ( basic/zBasic/util/abstractEnum/EnumSetMappedUtilZZZ.java ).
		   EnumDebugUIStrategy(){	
		   }

	  //##################################################
	  //#### Folgende Methoden bring Enumeration von Hause aus mit:		
		   public String getName(){
			   return super.name();
		   }
		   
		   @Override
		   public String toString() {
		       return this.sUniquetext;
		   }

		   public int getIndex() {
		   		return ordinal();
		   }
		   
		 
		//#### Methode aus ICategoryProviderZZZ
		public String getCategorytext() {			
			return this.sCategorytext;
		}  
		
		
		//#### Methode aus IKeyProviderZZZ
		public Long getThiskey() {
			return this.lKey;
		}
				
		//##################################################
		//#### Folgende Methoden holen die definierten Werte.
		public String getUniquetext() {			
			return this.sUniquetext;
		}
		
		@Override
		public int getStrategyValue() {			
			return this.iStrategyValue;
		}
		
		@Override
		public String getStrategyFlagName() {
			return this.sStrategyFlagName;
		}

	   //### Folgende Methoden sind zum komfortablen arbeiten gedacht.
	   public int getPosition() {
	   	return getIndex() + 1;
	   }
				
	   // the valueOfMethod <--- Translating from DB
	   public static EnumDebugUIStrategy fromUniquetext(String s) {
	       for (EnumDebugUIStrategy state : values()) {
	           if (s.equals(state.getUniquetext()))
	               return state;
	       }
	       throw new IllegalArgumentException("Not a correct uniquetext: " + s);
	   }

	   @Override
	   public EnumSet<?>getEnumSetUsed(){
	   	return EnumDebugUIStrategy.getEnumSet();
	   }

		
		//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		//Werte für alle Spielsteine:
		//lKey : Der thiskey - Merke: Hierüber wird in der Klasse TroopVariantDaoFactory gesteuert, welches DAO Objekt geholt wird.
		//                                         Konvention dabei ist (monetan): 10-19 Sind Armeeeinheiten, 20-29 sind Flotten
		//lKey / sUniquetext / sCategorytext (Merke: "Land Unit" wird als hart verdrahteter Wert  im Konstruktor von TroopVariant verwendet, zur Steuerung der Bildverarbeitung)
		                                                       //TODO GOON 20180703: Hier soll kein String mehr rein (z.B. 'Infantry Unit', sondern die ThisKey-Id einer entsprechenden CategoryText Tabelle.
		//			/ iMoveRange / fHealthInitial /sImageUrl / 
		//iThisKeyDefaulttext / iThiskeyImmutabletext (Der Shorttext hiervon wird als Default, abgekürzt in der HexMap angezeigt)
		//
		//Speziell für TROOP; 
		//iDegreeOfCoverMax
 
			
		   @SuppressWarnings("rawtypes")
		   public static <E> EnumSet getEnumSet() {
		   	
		       //Merke: Das wird anders behandelt als FLAGZ Enumeration.
		   	//String sFilterName = "FLAGZ"; /
		   	//...
		   	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
		   	
		   	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
		   	Class<EnumDebugUIStrategy> enumClass = EnumDebugUIStrategy.class;
		   	EnumSet<EnumDebugUIStrategy> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
		   	
		   	for(Object obj : EnumDebugUIStrategy.class.getEnumConstants()){
		   		//System.out.println(obj + "; "+obj.getClass().getName());
		   		set.add((EnumDebugUIStrategy) obj);
		   	}
		   	return set;
		   	
		   }

		   //TODO: Mal ausprobieren was das bringt
		   //Convert Enumeration to a Set/List
		   private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
		   	  EnumSet<E> set=EnumSet.noneOf(enumClass);
		   	  long mask=1;
		   	  for (  E e : enumClass.getEnumConstants()) {
		   	    if ((mask & vector) == mask) {
		   	      set.add(e);
		   	    }
		   	    mask<<=1;
		   	  }
		   	  return set;
		   	}
		
	}//End inner class
}
