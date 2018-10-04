package basic.zKernelUI;

import java.util.ArrayList;

import basic.zKernel.IKernelModuleUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernelUI.component.KernelJDialogExtendedZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;

/**Klasse dient zur Ermittlung von Informationen aus der KernelKonfiguration f�r die entsprechenden Komponenten.
 * Merke: Als Modul gilt nur das, was in der KErnel-Applikations-Konfigurations-Datei auch als Modul definiert wurde.
 *           D.h. es gibt dort einen Eintrag KernelConfigFile... , mit ... ist dann der Klassenname einer UI-Komponente (oder auch ein String)
 * @author lindhaueradmin
 *
 */
public class KernelUIZZZ implements IConstantZZZ{  //extends KernelUseObjectZZZ {
	/**Diese Klasse enth�lt nur statische Methoden, also ist es nicht notwendig weitere Parameter zu �bergeben.
	 * Darum ist der Konstruktor auch verborgen.
	 *  
	 * lindhaueradmin, 06.07.2013
	 */
	private KernelUIZZZ(){		
	}
	
	/*
	public KernelUIZZZ(KernelZZZ objKernel){
		super(objKernel);
	}*/
	
	public static String getProgramName(KernelJPanelCascadedZZZ panelCurrent) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(panelCurrent==null){
				ExceptionZZZ ez = new ExceptionZZZ("panelCurrent missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
		
			//if(panelCurrent.getFlag("isKernelProgram")){
			if(panelCurrent.getFlagZ(KernelJPanelCascadedZZZ.FLAGZ.COMPONENT_KERNEL_PROGRAM.name())){
				sReturn =  panelCurrent.getClass().getName();
			}else{				
				//Suche nach Elternpanel, das dieses Flag gesetzt hat ... als rekursiver Aufruf.
				KernelJPanelCascadedZZZ panelParent = panelCurrent.getPanelParent();
				if(panelParent!=null) {
					sReturn = KernelUIZZZ.getProgramName(panelParent); //Rekursion
				}else{
					//Suche ggf. nach einem ElternDialgo, das dieses Flag gesetzt hat.
					KernelJDialogExtendedZZZ dialogParent = panelCurrent.getDialogParent();
					if(dialogParent!=null){
						sReturn = KernelUIZZZ.getProgramName(dialogParent);
					}else{
						//Suche ggf. nach einem elternFrame
						KernelJFrameCascadedZZZ frameParent = panelCurrent.getFrameParent();
						if(frameParent!=null){
							sReturn = KernelUIZZZ.getProgramName(frameParent);
						}else{
							break main;
						}
					}					
				}
			}
		}//end main:
		return sReturn;
	}
		
		public static String getProgramName(KernelJDialogExtendedZZZ dialogCurrent) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(dialogCurrent==null){
					ExceptionZZZ ez = new ExceptionZZZ("dialogCurrent missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
					throw ez;
				}
			
				if(dialogCurrent.getFlag("isKernelProgram")){
					sReturn =  dialogCurrent.getClass().getName();
				}
				
			}//end main:
			return sReturn;
		}
		
		public static String getProgramName(KernelJFrameCascadedZZZ frameCurrent) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(frameCurrent==null){
					ExceptionZZZ ez = new ExceptionZZZ("frameCurrent missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
					throw ez;
				}
			
				if(frameCurrent.getFlag("isKernelProgram")){
					sReturn =  frameCurrent.getClass().getName();
				}
				
			}//end main:
			return sReturn;
		}
		
		public static String getProgramName(IKernelModuleUserZZZ objUsingKernelModule) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(objUsingKernelModule==null){
					ExceptionZZZ ez = new ExceptionZZZ("objUsingKernelModule missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
					throw ez;
				}
			
//				if(objUsingKernelModule.getFlag("isKernelProgram")){
//					sReturn =  objUsingKernelModule.getClass().getName();
//				}
				
				sReturn = objUsingKernelModule.getClass().getName();
				
			}//end main:
			return sReturn;
		}
		
		
		public static String getProgramAlias(IKernelModuleUserZZZ objUsingKernelModule) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(objUsingKernelModule==null){
					ExceptionZZZ ez = new ExceptionZZZ("objUsingKernelModule missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
					throw ez;
				}
							
				if(objUsingKernelModule instanceof KernelJFrameCascadedZZZ){
					sReturn = KernelUIZZZ.readProgramAlias((KernelJPanelCascadedZZZ) objUsingKernelModule);
				}else{				
					sReturn = objUsingKernelModule.getClass().getName();
				}
			}//end main:
			return sReturn;
		}
	
	/** Der Programmname einer Komponente entspricht dem des Elternpanels oder Dialog.
	* @param panel
	* @return
	* 
	* lindhauer; 20.07.2007 07:34:52
	 * @throws ExceptionZZZ 
	 */
	public static String readProgramAlias(KernelJPanelCascadedZZZ panelCurrent) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(panelCurrent==null){
				ExceptionZZZ ez = new ExceptionZZZ("panelCurrent missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
			
			/* FGL 2013-07-05: Ge�ndert, es wird die Methode in KErnelJPanelCascadedZZZ eingef�hrt.
			 * Es ist fraglich, ob das immer das dar�bergeordnete Panel sein muss.
			 */
			/*
			if(panelParent.getDialogParent()==null){
				sReturn = panelParent.getPanelParent().getClass().getName();
			}else{
				sReturn = panelParent.getDialogParent().getClass().getName();
			}*/
			//if(panelCurrent.getFlag("isKernelProgram")){
			String stemp = KernelJPanelCascadedZZZ.FLAGZ.COMPONENT_KERNEL_PROGRAM.name();
			if(panelCurrent.getFlagZ(stemp)){
				//NEIN, das w�re eine endlosschleife: sReturn = panelCurrent.getProgramAlias();
				
				//Versuch den Alias direkt aus der Konfiguration auszulesen.
				IKernelZZZ objKernel = panelCurrent.getKernelObject();
				sReturn = objKernel.getParameter(panelCurrent.getProgramName()); //Also: Der Alias ist auf Modulebene (z.B: in einem Abschnitt [THM#01]) definiert und steht dort hinter dem Package + Klassennamen der Komponente.			
			}else{				
				//Suche nach Elternpanel, das dieses Flag gesetzt hat ... als rekursiver Aufruf.
				KernelJPanelCascadedZZZ panelParent = panelCurrent.getPanelParent();
				if(panelParent==null)  break main;
				sReturn = KernelUIZZZ.readProgramAlias(panelParent);
			}
			
		}//End main
		return sReturn;
	}
	
	

	
	
	public static boolean isModuleConfigured(KernelJFrameCascadedZZZ frame) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(frame == null){
				ExceptionZZZ ez = new ExceptionZZZ("frame", iERROR_PARAMETER_MISSING, KernelUIZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//................ Das Array aller Module dahingehend pr�fen, ob der Klassenname dort auftaucht.
			IKernelZZZ objKernel = frame.getKernelObject();
			ArrayList listaModuleString = objKernel.getModuleAll(); //d.h. es werden die Module ber�cksichtigt, die in der Konfiguration definiert sind, aber die Existenz der Module ist nicht notwendig.
						
			KernelJFrameCascadedZZZ frameParentTemp = frame.getFrameParent();
			KernelJFrameCascadedZZZ frameParent = null;
			while(frameParentTemp!=null){
												
				//Merke: Der Root-Frame hat sich selbst zum ParentFrame
				if(frameParentTemp.equals(frameParent)) break main;
		
				frameParent = frameParentTemp;
				frameParentTemp = frameParentTemp.getFrameParent();
				String sClassnameParentTemp = frameParent.getClass().getName(); 
				String sClassnameParent = null;
				
//				!!! hier wird ggf. ein $xyz - Wert angeh�ngt, z.B. im Debugmodus mit JUnit, was wohletwas mit der ReflectionAPI zu tun haben muss
				if(StringZZZ.contains(sClassnameParentTemp, "$")){
					sClassnameParent = StringZZZ.left(sClassnameParentTemp, "$");
				}else{
					sClassnameParent = sClassnameParentTemp;
				}
				for(int icount = 0; icount<= listaModuleString.size()-1; icount++){
					String sModule = (String) listaModuleString.get(icount);
					
					if(sClassnameParent.equals(sModule)){
						bReturn = true;
						break main;
					}
				}			
			}
			
			
		
		}
		return bReturn;
	}
	
	public static String searchModuleFirstConfiguredClassname(KernelJFrameCascadedZZZ frame) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(frame == null){
				ExceptionZZZ ez = new ExceptionZZZ("KernelJFrameCascadedZZZ", iERROR_PARAMETER_MISSING, KernelUIZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//................ Das Array aller Module dahingehend pr�fen, ob der Klassenname dort auftaucht.
			//KernelZZZ objKernel = this.getKernelObject();
			IKernelZZZ objKernel = frame.getKernelObject();
			ArrayList listaModuleString = objKernel.getModuleAll(); //d.h. es werden die Module ber�cksichtigt, die in der Konfiguration definiert sind, aber die Existenz der Module ist nicht notwendig.
			if(listaModuleString.size() <= 0) break main;
			
			//.................. Alle frames mit ihren parent-Frames durchgehen. Pr�fen, ob der Framename als Modul definiert wurde.
			KernelJFrameCascadedZZZ frameParentTemp = frame.getFrameParent();
			KernelJFrameCascadedZZZ frameParent = null;
			
			//                   Merke: Dies als rekursiven Aufruf programmieren.
			//                   Damit hat man dann schon eine Argument, den man braucht, um eine Property aus dem ini-File auszulesen.
			//                   Als Program-Namen verwendet man am besten einen Alias. Merke: Die Aktion-Listener-Klassen mit ihren Namen sind dann zu konfigurieren.
			
			while(frameParentTemp!=null){
				String sClassnameParentTemp = frameParentTemp.getClass().getName(); 
				String sClassnameParent = null;
//				!!! hier wird ggf. ein $xyz - Wert angeh�ngt, z.B. im Debugmodus mit JUnit, was wohletwas mit der ReflectionAPI zu tun haben muss
				if(StringZZZ.contains(sClassnameParentTemp, "$")){
					sClassnameParent = StringZZZ.left(sClassnameParentTemp, "$");
				}else{
					sClassnameParent = sClassnameParentTemp;
				}
												
				for(int icount = 0; icount<= listaModuleString.size()-1; icount++){
					sReturn = (String) listaModuleString.get(icount);							
					if(sClassnameParent.equals(sReturn)) break main;
				}			
			
				//Merke: Der Root-Frame hat sich selbst zum ParentFrame
				if(frameParentTemp.equals(frameParent)) break main; //ggf. auch die Methode verlassen, ohne dass etwas gefunden wurde
				
				frameParent = frameParentTemp;
				frameParentTemp = frameParentTemp.getFrameParent();							
			}//end while
		}//end main
		return sReturn;
		}

	
	/** Sucht den obersten Frame und gibt dessen Klassennamen zur�ck.
	 *   Normalerweise ist das dann auch ein Modulname, was hier aber nicht explizit gepr�ft wird.
	 *   
	 *   Merke: Den obersten Frame erkennt man daran, dass er sich selbst als ParentFrame zur�ckgibt.
	* @param frame
	* @return  
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 04.03.2007 09:30:36
	 */
	public static String searchModuleRootClassname(KernelJFrameCascadedZZZ frame) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(frame==null){
				ExceptionZZZ ez = new ExceptionZZZ("KernelJFrameCascadedZZZ", iERROR_PARAMETER_MISSING, KernelUIZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			KernelJFrameCascadedZZZ frameParentTemp = frame.getFrameParent();
			KernelJFrameCascadedZZZ frameParent = null;
			while(frameParentTemp!=null){
												
				//Merke: Der Root-Frame hat sich selbst zum ParentFrame
				if(frameParentTemp.equals(frameParent)){
					sReturn = frameParentTemp.getClass().getName();
					break main;
				}
				frameParent = frameParentTemp;
				frameParentTemp = frameParentTemp.getFrameParent();
			}
			sReturn = frameParent.getClass().getName();   //diese Zeile sollte eigentlich nie erreicht werden.
		}//end main
		return sReturn;
	}
}
