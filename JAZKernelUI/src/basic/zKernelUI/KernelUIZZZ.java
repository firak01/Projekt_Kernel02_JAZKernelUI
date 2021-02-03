package basic.zKernelUI;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.module.IKernelModuleZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernelUI.component.IActionCascadedZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
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
	/**Diese Klasse enthält nur statische Methoden, also ist es nicht notwendig weitere Parameter zu übergeben.
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
		
			if(panelCurrent.getFlagZ(KernelJPanelCascadedZZZ.FLAGZ.COMPONENT_KERNEL_PROGRAM.name())){
				sReturn =  panelCurrent.getClass().getName();
			}else{				
				//Suche nach Elternpanel, das dieses Flag gesetzt hat ... als rekursiver Aufruf.
				KernelJPanelCascadedZZZ panelParent = panelCurrent.getPanelParent();
				if(panelParent!=null) {
					sReturn = KernelUIZZZ.getProgramName(panelParent); //Rekursion
				}else{					
					if(KernelUIZZZ.isChildOfDialog(panelCurrent)){//Suche ggf. nach einem ElternDialog, das dieses Flag gesetzt hat.					
						KernelJDialogExtendedZZZ dialogParent = panelCurrent.getDialogParent();
						KernelJPanelCascadedZZZ objPanel = KernelUIZZZ.searchProgramKernelComponentPanelFrom(dialogParent);
						if(objPanel!=null){
							sReturn = KernelUIZZZ.getProgramName(objPanel);
							break main;	
						}																
						sReturn = KernelUIZZZ.getProgramName(dialogParent);//Erst wenn es keine Panels gibt, den Dialog ggfs. als Programname
					}else if(KernelUIZZZ.isChildOfFrame(panelCurrent)){ //Suche ggf. nach einem elternFrame						
						KernelJFrameCascadedZZZ frameParent = panelCurrent.getFrameParent();
						KernelJPanelCascadedZZZ objPanel = KernelUIZZZ.searchProgramKernelComponentPanelFrom(frameParent);
						if(objPanel!=null){
							sReturn = KernelUIZZZ.getProgramName(objPanel);
							break main;	
						}						
						sReturn = KernelUIZZZ.getProgramName(frameParent);//Erst wenn es keine Panels gibt, den Frame ggfs. als Programname						
					}else{
						
					}
				}
			}
		}//end main:
		return sReturn;
	}
	
	public static KernelJPanelCascadedZZZ searchProgramKernelComponentPanelFrom(KernelJDialogExtendedZZZ dialogParent){
		KernelJPanelCascadedZZZ objReturn = null;
		main:{
			if(dialogParent==null) break main;
			
			//20190219: Jetzt ist es zu früh den Frame als Program zurückzugeben.
			//          Statt dessen musste man alle Unterpanels suchen und dahingehend analysieren, ob eines davon als "KernelProgram" per Flag defniert wurde.
			Hashtable htSub = dialogParent.getPanelSubAll();
			Set setPanel = htSub.keySet();
			Iterator itPanel = setPanel.iterator();
			while(itPanel.hasNext()){
				Object obj = itPanel.next();
				String sKey = (String) obj;								
				KernelJPanelCascadedZZZ objPanel = (KernelJPanelCascadedZZZ) htSub.get(sKey);
				boolean bIsProgram = objPanel.getFlagZ(IKernelModuleZZZ.FLAGZ.ISKERNELPROGRAM.name());
				if(bIsProgram){
					objReturn = objPanel;
					break main;
				}								
			}
			
		}//end main
		return objReturn;
	}
	
	public static KernelJPanelCascadedZZZ searchProgramKernelComponentPanelFrom(KernelJFrameCascadedZZZ frameParent){
		KernelJPanelCascadedZZZ objReturn = null;
		main:{
			if(frameParent==null) break main;
			
			//20190219: Jetzt ist es zu früh den Frame als Program zurückzugeben.
			//          Statt dessen musste man alle Unterpanels suchen und dahingehend analysieren, ob eines davon als "KernelProgram" per Flag defniert wurde.
			Hashtable htSub = frameParent.getPanelSubAll();
			Set setPanel = htSub.keySet();
			Iterator itPanel = setPanel.iterator();
			while(itPanel.hasNext()){
				Object obj = itPanel.next();
				String sKey = (String) obj;								
				KernelJPanelCascadedZZZ objPanel = (KernelJPanelCascadedZZZ) htSub.get(sKey);
				boolean bIsProgram = objPanel.getFlagZ(IKernelModuleZZZ.FLAGZ.ISKERNELPROGRAM.name());
				if(bIsProgram){
					objReturn = objPanel;
					break main;
				}								
			}
			
		}//end main
		return objReturn;
	}
		
	public static String getProgramName(IActionCascadedZZZ actionCurrent) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(actionCurrent==null){
				ExceptionZZZ ez = new ExceptionZZZ("actionCurrent missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
		
			KernelJFrameCascadedZZZ frameParent = actionCurrent.getFrameParent(); //panelParent.getFrameParent();
			if(frameParent==null){
				KernelJPanelCascadedZZZ panelParent = actionCurrent.getPanelParent();
				if(panelParent==null) {
					sReturn = actionCurrent.getKernelObject().getApplicationKey();
					break main;
				}else {
					sReturn = panelParent.getProgramName();
					break main;
				}					
			}else {
				//Wenn es ein frameParent gibt, ggfs. noch weiter runter, oder den Klassennamen als Modul
				JFrame frameRoot = frameParent.searchFrameRoot();//frameParent.getFrameParent().getClass().getName();
				if(frameRoot==null) {
					sReturn = frameParent.getClass().getName();
					break main;
				}else {
					sReturn = frameRoot.getClass().getName();
					break main;
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
				}else {
					//PROBLEM 20210124: Wenn man hier das Panel abfragt, wird es erzeugt. Beim Erzeugen werden ggfs. vom Panel wiederum Programname/Modulname abgefragt.
					//                  Man kommt also in eine Endlosschleife.
//					KernelJPanelCascadedZZZ panelContent = dialogCurrent.getPanelContent();
//					if(panelContent==null) {
//						sReturn = dialogCurrent.getKernelObject().getApplicationKey();
//					}else {
//						sReturn = panelContent.getProgramName();
//					}
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
		
		public static String getProgram(IKernelModuleZZZ objUsingKernelModule) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(objUsingKernelModule==null){
					ExceptionZZZ ez = new ExceptionZZZ("objUsingKernelModule missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
					throw ez;
				}
			
				if(objUsingKernelModule.getFlag("isKernelProgram")){
					sReturn =  objUsingKernelModule.getClass().getName();
				}
			}//end main:
			return sReturn;
		}
		
		
		public static String getProgramAlias(IKernelModuleZZZ objUsingKernelModule) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(objUsingKernelModule==null){
					ExceptionZZZ ez = new ExceptionZZZ("objUsingKernelModule missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
					throw ez;
				}
							
				if(objUsingKernelModule instanceof KernelJFrameCascadedZZZ){
					sReturn = KernelUIZZZ.readProgramAlias((KernelJPanelCascadedZZZ) objUsingKernelModule);
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
			String stemp = KernelJPanelCascadedZZZ.FLAGZ.COMPONENT_KERNEL_PROGRAM.name();
			if(panelCurrent.getFlagZ(stemp)){
				//NEIN, das w�re eine endlosschleife: sReturn = panelCurrent.getProgramAlias();
				
				//Versuch den Alias direkt aus der Konfiguration auszulesen.
				IKernelZZZ objKernel = panelCurrent.getKernelObject();
				sReturn = objKernel.getParameter(panelCurrent.getProgramName()).getValue(); //Also: Der Alias ist auf Modulebene (z.B: in einem Abschnitt [THM#01]) definiert und steht dort hinter dem Package + Klassennamen der Komponente.			
			}else{				
				//Suche nach Elternpanel, das dieses Flag gesetzt hat ... als rekursiver Aufruf.
				KernelJPanelCascadedZZZ panelParent = panelCurrent.getPanelParent();
				if(panelParent==null)  break main;
				sReturn = KernelUIZZZ.readProgramAlias(panelParent);
			}
			
		}//End main
		return sReturn;
	}
	
	
	public static boolean isChildOfDialog(KernelJPanelCascadedZZZ panel){
		boolean bReturn = false;
		main:{
			if(panel==null)break main;
			KernelJDialogExtendedZZZ objDialog = panel.getDialogParent();
			if(objDialog!=null) bReturn = true;
		}//end main
		return bReturn;
	}
	
	public static boolean isChildOfFrame(KernelJPanelCascadedZZZ panel){
		boolean bReturn = false;
		main:{
			if(panel==null)break main;
			KernelJFrameCascadedZZZ objFrame = panel.getFrameParent();
			if(objFrame!=null) bReturn = true;
		}//end main
		return bReturn;
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
			ArrayList<String> listaModuleString = objKernel.getModuleAll(); //d.h. es werden die Module ber�cksichtigt, die in der Konfiguration definiert sind, aber die Existenz der Module ist nicht notwendig.
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
	
	/** TODOGOON 20210203: wohl früher mal verwendet. sieht aber gut aus.
	 * 
	 * @param frame
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.02.2021, 09:16:18
	 */
	public static String searchModule(KernelJPanelCascadedZZZ panelCascaded) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(panelCascaded == null){
				ExceptionZZZ ez = new ExceptionZZZ("KernelJPanelCascadedZZZ", iERROR_PARAMETER_MISSING, KernelUIZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			KernelJFrameCascadedZZZ frameParent = panelCascaded.getFrameParent(); //panelParent.getFrameParent();
			if(frameParent==null){
				KernelJPanelCascadedZZZ panelParent = panelCascaded.getPanelParent();
				if(panelParent==null) {
					sReturn = panelCascaded.getKernelObject().getApplicationKey();
					break main;
				}else {
					KernelJPanelCascadedZZZ panelRoot = panelParent.searchPanelRoot();
					if(panelRoot==null) {
						sReturn = panelCascaded.getKernelObject().getApplicationKey();
						break main;
					}else {
						frameParent = panelRoot.getFrameParent();
						if(frameParent==null) {
							//Dann keinen Fehler werfen.
							//throw new ExceptionZZZ("Keine FrameParent in diesem KernelJPanelCascadedZZZ vorhanden");
							sReturn = panelRoot.getClass().getName();
							break main;
						}else {
							//Wenn es ein frameParent gibt, ggfs. noch weiter runter, oder den Klassennamen als Modul
							JFrame frameRoot = frameParent.searchFrameRoot();//frameParent.getFrameParent().getClass().getName();
							if(frameRoot==null) {
								sReturn = frameParent.getClass().getName();
								break main;
							}else {
								sReturn = frameRoot.getClass().getName();
								break main;
							}									
						}
					}
				}		
			}else {
				//Wenn es ein frameParent gibt, ggfs. noch weiter runter, oder den Klassennamen als Modul
				JFrame frameRoot = frameParent.searchFrameRoot();//frameParent.getFrameParent().getClass().getName();
				if(frameRoot==null) {
					sReturn = frameParent.getClass().getName();
					break main;
				}else {
					sReturn = frameRoot.getClass().getName();
					break main;
				}		
			}		
			
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
	
	public static String getModuleUsed(IActionCascadedZZZ actionCascaded) throws ExceptionZZZ{
		String sReturn = new String();
		main:{
			KernelJFrameCascadedZZZ frameParent = actionCascaded.getFrameParent(); //panelParent.getFrameParent();
			if(frameParent==null){
				KernelJPanelCascadedZZZ panelParent = actionCascaded.getPanelParent();
				if(panelParent==null) {
					sReturn = actionCascaded.getKernelObject().getApplicationKey();
					break main;
				}else {
					sReturn = panelParent.getModuleName();
				}					
			}else {
				//Wenn es ein frameParent gibt, ggfs. noch weiter runter, oder den Klassennamen als Modul
				JFrame frameRoot = frameParent.searchFrameRoot();//frameParent.getFrameParent().getClass().getName();
				if(frameRoot==null) {
					sReturn = frameParent.getClass().getName();
					break main;
				}else {
					sReturn = frameRoot.getClass().getName();
					break main;
				}		
			}						
		}//end main:
		return sReturn;
	}
	
	public static String getModule(IKernelModuleZZZ objUI) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			if(objUI==null){
				ExceptionZZZ ez = new ExceptionZZZ("UI Object missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
		
			if(objUI.getFlag(IKernelModuleZZZ.FLAGZ.ISKERNELMODULE.name())){
				sReturn =  objUI.getClass().getName();
			}else {
				//PROBLEM 20210124: Wenn man hier das Panel abfragt, wird es erzeugt. Beim Erzeugen werden ggfs. vom Panel wiederum Programname/Modulname abgefragt.
				//                  Man kommt also in eine Endlosschleife.
//				KernelJPanelCascadedZZZ panelContent = dialogCurrent.getPanelContent();
//				if(panelContent==null) {
//					sReturn = dialogCurrent.getKernelObject().getApplicationKey();
//				}else {
//					sReturn = panelContent.getModuleName();
//				}
			}				
		}//end main:
		return sReturn;
		
	}
	public static String getModuleUsed(KernelJDialogExtendedZZZ dialogCurrent) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			if(dialogCurrent==null){
				ExceptionZZZ ez = new ExceptionZZZ("dialogCurrent missing", iERROR_PARAMETER_MISSING, KernelUIZZZ.class,ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
		
			if(dialogCurrent.getFlag(IKernelModuleZZZ.FLAGZ.ISKERNELMODULE.name())){
				sReturn =  dialogCurrent.getClass().getName();
			}else {				
				//PROBLEM 20210124: Wenn man hier das Panel abfragt, wird es erzeugt. Beim Erzeugen werden ggfs. vom Panel wiederum Programname/Modulname abgefragt.
				//                  Man kommt also in eine Endlosschleife.
//				KernelJPanelCascadedZZZ panelContent = dialogCurrent.getPanelContent();
//				if(panelContent==null) {
//					sReturn = dialogCurrent.getKernelObject().getApplicationKey();
//				}else {
//					sReturn = panelContent.getModuleName();
//				}
			}				
		}//end main:
		return sReturn;
		
	}
	public static String getModuleUsed(IPanelCascadedZZZ panelCascaded) {
		String sReturn = new String("");
		main:{
			try{
				KernelJDialogExtendedZZZ dialog = panelCascaded.getDialogParent();
				KernelJFrameCascadedZZZ frameParent = null;
				if(dialog==null){
					frameParent = panelCascaded.getFrameParent();									
					sReturn = KernelUIZZZ.searchModuleFirstConfiguredClassname(frameParent); 						
				}else{
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# This is a dialog.....");
					sReturn = dialog.getModuleName();				
				}
			} catch (ExceptionZZZ ez) {				
				ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());
			}
		}//end main
		return sReturn;
	}
}
