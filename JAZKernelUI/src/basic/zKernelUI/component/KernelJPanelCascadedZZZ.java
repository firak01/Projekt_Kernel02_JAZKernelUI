package basic.zKernelUI.component;

import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectLogZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.listener.ListenerMouseMove4DragableWindowZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import basic.zKernel.component.IKernelModuleUserZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernelUI.component.IMouseFeatureZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagZLocalEnabledZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.componentGroup.JComponentGroupCollectionZZZ;
import basic.zKernelUI.component.componentGroup.JComponentGroupHelperZZZ;
import basic.zKernelUI.component.componentGroup.JComponentGroupZZZ;
import basic.zKernelUI.component.componentGroup.ModelPanelDebugZZZ;
import custom.zKernel.LogZZZ;

/** Klasse bietet als Erweiterung zu JPanel die Verschachtelung von Panels an.
 * Merke: Ohne ein JFrame als Parent funktioniert es nicht, das Panel per Drag mit der Maus zu bewegen.
 * 
 *  Merke: Die Panels können sowohl nur modulnutzer als auch selber Modul sein. Darum werden beide Interfaces implementiert.
 */
public abstract class KernelJPanelCascadedZZZ extends JPanel implements IPanelCascadedZZZ, IKernelModuleUserZZZ, IKernelUserZZZ, IObjectZZZ, IObjectLogZZZ, IMouseFeatureZZZ, IDebugUiZZZ, IFlagZEnabledZZZ, IFlagZLocalEnabledZZZ{
	protected static final String sBUTTON_SWITCH = "buttonSwitch";
		
	protected IKernelZZZ objKernel;   //das "protected" erlaubt es hiervon erbende Klassen mit XYXErbendeKlasse.objKernel zu arbeiten.
	protected LogZZZ objLog;
	protected IKernelModuleZZZ objModule=null; //Das Modul, z.B. die Dialogbox, in der das Program gestartet wird.

	//Zum Suchen das Panels einen Alias vergeben.
	protected String sAlias=null;
	
	//Merke: Nur einige besondere Panels sind selbst Module.
	protected String sModuleName=null;         //Notwendig, wenn das Panel selbst das Modul ist.

	protected IPanelCascadedZZZ panelParent;
	protected KernelJFrameCascadedZZZ frameParent;
	protected KernelJDialogExtendedZZZ dialogParent;
	
	protected Hashtable<String,IPanelCascadedZZZ> htPanelSub=new Hashtable<String,IPanelCascadedZZZ>();
	protected Hashtable<String,JComponent> htComponent = new Hashtable<String,JComponent>();
	protected Hashtable <String, KernelButtonGroupZZZ<String, AbstractButton>> htButtonGroup = new Hashtable<String, KernelButtonGroupZZZ<String, AbstractButton>>();
	
	protected ListenerMouseMove4DragableWindowZZZ listenerDraggableWindow = null; 

	protected String sProgramName  = null; //ggf. der Name des Elternprogramms, s. KernelKonfiguration
	protected String sProgramAlias = null; //ggf. der Alias des Elternprogramms, s. KernelKonfiguration
			
	private HashMap<String, Boolean>hmFlag=new HashMap<String, Boolean>();
	private HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>();
	private HashMap<String, Boolean>hmFlagLocal = new HashMap<String, Boolean>();
	
	/**
	 * DEFAULT Konstruktor, notwendig, damit man objClass.newInstance(); einfach machen kann.
	 *                                 
	 * lindhaueradmin, 23.07.2013
	 */
	public KernelJPanelCascadedZZZ(){// throws ExceptionZZZ{
		//Jetzt muss im Prinzip alles gemacht werden, das in KernelUseObjectZZZ auch gemacht wird. 
		//Leider kann diese Klasse nicht davon erben.
				
		//20080422 wenn objekte diese klasse erweitern scheint dies immer ausgeführt zu werden. Darum hier nicht setzen !!! this.setFlag("init", true);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(); //Das initialisiert JPanel;		
		KernelJPanelCascadedNew_(objKernel, null, null, null, null,null);		
	}
	
	/** constructor used for creating a ROOT-Panel
	* Lindhauer; 10.05.2006 06:33:12
	 * @param objKernel
	 * @param panelParent
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
		super(); //Das initialisiert JPanel;		
		KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null,null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, JFrame frameBasic) throws ExceptionZZZ{		
		FrameCascadedRootDummyZZZ frameParent = new FrameCascadedRootDummyZZZ(objKernel, frameBasic);				
		KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null,null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, Container contentPane) throws ExceptionZZZ{
		main:{
			//TODO die Fallunterscheidung dynamischer mit einer Reflection-Betrachtung machen, die auch "Vererbung" einschliesst, bzw. den Namen von "Elternklassen"
			//TODO Wie sieht es mit Klassen aus, die static - deklariert sind. contentPane.class.getName() geht da nicht !!!!
				
			//1. JFrames aber keine CascadedFrames
			Class clsContainer = contentPane.getClass();
			boolean bIsCascadedFrame = ReflectClassZZZ.isChildclassFrom(clsContainer, KernelJFrameCascadedZZZ.class);
			boolean bIsJFrame = ReflectClassZZZ.isChildclassFrom(clsContainer, JFrame.class);
			
			//1. nur normale Frames, oder was sich daraus ableitet
			if(bIsJFrame && !bIsCascadedFrame){
				JFrame frame = (JFrame) contentPane;
				frame.getContentPane().add(this);
			     
				FrameCascadedRootDummyZZZ frameParent = new FrameCascadedRootDummyZZZ(objKernel, frame);
				KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null,null);
				break main;
			}
			
			//2. nur cascadedFrames
			if(bIsCascadedFrame){				
				KernelJFrameCascadedZZZ frameParent =  (KernelJFrameCascadedZZZ) contentPane;   //SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)contentPane);
				frameParent.getContentPane().add(this);
				KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null,null);
				break main;
			}
			

			//3. der Rest, als auch ggf. andere Panels
			contentPane.add(this); // das liefert bei JFrame einen Fehler. NICHT L�SCHEN ggf. ist eine Fallunterscheidung f�r die verschiedenen Container notwendig
			
			boolean bIsCascadedPanel = ReflectClassZZZ.isChildclassFrom(clsContainer, KernelJPanelCascadedZZZ.class);
			if(bIsCascadedPanel){
				KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) contentPane;
				this.setPanelParent(panelParent);
			}
			
			//zu 3. ... jetzt scheint es wieder schwer den ParentFrame zu ermitteln
			KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)contentPane);
			if(frameParent==null) {
				frameParent = ((KernelJPanelCascadedZZZ) contentPane).getFrameParent();
			}
			KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null,null);
		}//end main:
		
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog) throws ExceptionZZZ{
		KernelJPanelCascadedNew_(objKernel, null, dialog, null, null,null);
	}
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog, String sFlagLocal) throws ExceptionZZZ{
		HashMap<String,Boolean>hmFlagLocal=new HashMap<String,Boolean>();
		if(!StringZZZ.isEmpty(sFlagLocal)) {
			hmFlagLocal.put(sFlagLocal, true);
		}
		KernelJPanelCascadedNew_(objKernel, null, dialog, null, hmFlagLocal, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog, KernelJPanelCascadedZZZ panelRoot) throws ExceptionZZZ{
		KernelJPanelCascadedNew_(objKernel, null, dialog, panelRoot, null,null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog,  HashMap<String, Boolean>hmFlag) throws ExceptionZZZ{
		KernelJPanelCascadedNew_(objKernel, null, dialog, null, null, hmFlag);
		
//		this(objKernel, dialog);
//		String stemp; boolean btemp; String sLog;
//		
//		//Die ggfs. vorhanden lokalen Flags setzen
//		if(hmFlagLocal!=null) {
//			for(String sKey:hmFlagLocal.keySet()){
//				stemp = sKey;
//				btemp = this.setFlagZLocal(sKey, hmFlagLocal.get(sKey));
//				if(btemp==false){
//					ExceptionZZZ ez = new ExceptionZZZ( "the LOCAL flag '" + stemp + "' is not available (passed by hashmap). Maybe an interface is not implemented.", IFlagLocalUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//					throw ez;		 
//				}			
//			}
//		}
//		
//		//Die ggf. vorhandenen Flags setzen.
//		if(hmFlag!=null){
//			for(String sKey:hmFlag.keySet()){
//				stemp = sKey;
//				btemp = this.setFlagZ(sKey, hmFlag.get(sKey));
//				if(btemp==false){
//					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap). Maybe an interface is not implemented.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//					throw ez;		 
//				}			
//			}
//		}
	}
	
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog,  HashMap<String, Boolean>hmFlagLocal, HashMap<String, Boolean>hmFlag) throws ExceptionZZZ{
		KernelJPanelCascadedNew_(objKernel, null, dialog, null, hmFlagLocal, hmFlag);
	}
	
	
	/** constructor used for creating a SUB-Panel
	* Lindhauer; 10.05.2006 06:33:12
	 * @param objKernel
	 * @param panelParent
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{		
		KernelJPanelCascadedNew_(objKernel, null, null, panelParent, null,null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent, HashMap<String, Boolean> hmFlag) throws ExceptionZZZ{	
		KernelJPanelCascadedNew_(objKernel, null, null, panelParent, hmFlag,null);
	}
	
	private boolean KernelJPanelCascadedNew_(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent, KernelJDialogExtendedZZZ dialog, KernelJPanelCascadedZZZ panelParent, HashMap<String, Boolean> hmFlagLocal, HashMap<String, Boolean> hmFlag ) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
		String stemp; boolean btemp; String sLog;
		
		//Merke: Das Lokale Flag wirkt sich nur in dieser Methode aus. Die anderen Flags auch aus hieraus erbenden Klassen.
		if(hmFlagLocal!=null){
			for(String sKey:hmFlagLocal.keySet()){				
				stemp = sKey;
				btemp = this.setFlagLocal(sKey, hmFlagLocal.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the LOCAL flag '" + stemp + "' is not available (passed by hashmap). Maybe an interface is not implemented.", IFlagZLocalEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}	
			}
		}
		if(this.getFlagLocal("INIT")==true){
			bReturn = true;
			break main; 
		}	
		
		
		//Das direkte Setzen der Flags, da nicht aus ObjectZZZ geerbt werden kann, hier ausführen.
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){				
				stemp = sKey;
				btemp = this.setFlag(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap). Maybe an interface is not implemented.", IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}	
			}
		}
		if(this.getFlag("INIT")==true){
			bReturn = true;
			break main; 
		}	
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
				
		//++++++++++++++++++++++++++++++					
		//HIER geht es darum ggfs. die Flags zu übernehmen, die irgendwo gesetzt werden sollen und aus dem Kommandozeilenargument -z stammen.
		//D.h. ggfs. stehen sie in dieser Klasse garnicht zur Verfügung
		//Kommandozeilen-Argument soll alles übersteuern. Darum auch FALSE setzbar. Darum auch nach den "normalen" Flags verarbeiten.
		if(this.getKernelObject()!=null) {
			IKernelConfigZZZ objConfig = this.getKernelObject().getConfigObject();
			if(objConfig!=null) {
				//Übernimm die als Kommandozeilenargument gesetzten FlagZ... die können auch "false" sein.
				Map<String,Boolean>hmFlagZpassed = objConfig.getHashMapFlagPassed();	
				if(hmFlagZpassed!=null) {
					Set<String> setFlag = hmFlagZpassed.keySet();
					Iterator<String> itFlag = setFlag.iterator();
					while(itFlag.hasNext()) {
						String sKey = itFlag.next();
						 if(!StringZZZ.isEmpty(sKey)){
							 Boolean booValue = hmFlagZpassed.get(sKey);
							 btemp = setFlag(sKey, booValue.booleanValue());//setzen der "auf Verdacht" indirekt übergebenen Flags
							 if(btemp==false){						 
								 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
								 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
		//						  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!
		//						  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
		//						  throw ez;		 
							  }
						 }
					}
				}	
			}
		}		
		//+++++++++++++++++++++++++++++++
		
		this.setDialogParent(dialog);	
		if(this.getDialogParent()==null) {
			KernelJDialogExtendedZZZ dialogSearched = (KernelJDialogExtendedZZZ) SwingUtilities.getAncestorOfClass(KernelJDialogExtendedZZZ.class, (JComponent) this);
			this.setDialogParent(dialogSearched);
		}
		
		this.setFrameParent(frameParent);
		if(this.getFrameParent()==null) {
			KernelJFrameCascadedZZZ frameParentSearched = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)this);
			this.setFrameParent(frameParentSearched);
		}
				
		this.setPanelParent(panelParent);
		if(this.getPanelParent()==null) {
			KernelJPanelCascadedZZZ panelParentSearched = (KernelJPanelCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJPanelCascadedZZZ.class, (JComponent)this);
			this.setPanelParent(panelParentSearched);
		}
		
		
		//Ggfs. die DebugUI-Angaben hinzufügen, das kann z.B. nur das Label mit dem Klassennamen sein.
		//Gesteuert werde soll das durch Flags, die auch über die Kommandozeile übergeben werden können.
		//Merke: Beispielsweise für FormLayoutete Panels - die aus PanelCascaded erben - macht das einfache Hinzufügen keinen Sinn. Das Layout wird dann eh ersetzt. Also überspringen.
		if(this.getFlagLocal(FLAGZLOCAL.SKIPDEBUGUI.name())==false) {
			boolean bDebugUI = this.createDebugUi();
		}
		
		//Einen Mouse Listener hinzufuegen, der es erlaubt Fenster zu ziehen (auch im Panel und nicht nur in der Titelleiste)
		//if(this.getFlag("isdraggable")){
		if(this.getFlag(IMouseFeatureZZZ.FLAGZ.COMPONENT_DRAGGABLE.name())){	
			this.setJComponentContentDraggable(this.isJComponentContentDraggable());
		}
			bReturn = true;
		}//end main:
		return bReturn;		
	}

	@Override
	public String getAlias() {
		return this.sAlias;
	}
	
	@Override
	public void setAlias(String sAlias) {
		this.sAlias = sAlias;
	}
	
	public KernelJDialogExtendedZZZ getDialogParent(){
		return this.dialogParent;
	}
	public void setDialogParent(KernelJDialogExtendedZZZ dialogExtended){
		this.dialogParent = dialogExtended;
	}
	
	
	public ArrayList<JComponent> searchComponentAll(){
		ArrayList<JComponent> listaReturn = new ArrayList<JComponent>();
		main:{
			Hashtable<String,JComponent> htComponent = this.getHashtableComponent();
			Collection<JComponent> colComponent = htComponent.values();
			listaReturn.addAll(colComponent);
			
			Hashtable<String,IPanelCascadedZZZ> htPanel = this.getHashtablePanel();
			Set<String> setKey = htPanel.keySet();
			for(String sKey : setKey){
				KernelJPanelCascadedZZZ objPanel = (KernelJPanelCascadedZZZ) htPanel.get(sKey);
				if(objPanel!=null){
					ArrayList<JComponent> listaComponent = objPanel.searchComponentAll();
					listaReturn.addAll(listaComponent);
				}
			}			
		}//end main
		return listaReturn;
	}
	
	public JComponent searchComponent(String sKeyComponent) throws ExceptionZZZ{
		JComponent objReturn = null;
		main:{
			objReturn = searchComponent(sKeyComponent, false);
			if(objReturn!=null)break main;
			
			objReturn = searchComponent(sKeyComponent, true);			
		}//end main
		return objReturn;
	}
			
	public JComponent searchComponent(String sKeyComponent, boolean bInNeighbours) throws ExceptionZZZ{
		JComponent objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sKeyComponent)) break main;
			
			if(bInNeighbours) {
				ArrayList<IPanelCascadedZZZ> listaPanelNeighbour = this.getPanelNeighbours();
				for(IPanelCascadedZZZ panelNeighbour : listaPanelNeighbour) {
					if(panelNeighbour!=null){
						objReturn = panelNeighbour.searchComponent(sKeyComponent, false);
						if(objReturn!=null) break main;
					}					
				}				
			}else {
				Hashtable<String,JComponent> htComponent = this.getHashtableComponent();
				objReturn = htComponent.get(sKeyComponent);
				if(objReturn!=null) break main;
				
				Hashtable<String,IPanelCascadedZZZ> htPanel = this.getHashtablePanel();
				Set<String> setKey = htPanel.keySet();
				for(String sKeyPanel : setKey){
					IPanelCascadedZZZ objPanel = htPanel.get(sKeyPanel);
					if(objPanel!=null){
						Hashtable<String,JComponent> htComponentInPanels = this.getHashtableComponent();
						objReturn = htComponentInPanels.get(sKeyComponent);
						if(objReturn!=null) break main;
					}
				}	
			}
			
		}//end main	
		return objReturn;
	}
	
	public ArrayList<IPanelCascadedZZZ> getPanelNeighbours() throws ExceptionZZZ {
		ArrayList<IPanelCascadedZZZ> listaReturn=new ArrayList<IPanelCascadedZZZ>();
		main:{
			IPanelCascadedZZZ panelParent = this.getPanelParent();
			if(panelParent!=null) {			
				Hashtable<String,IPanelCascadedZZZ> htPanel = panelParent.getHashtablePanel();
				Set<String> setKey = htPanel.keySet();
				for(String sKeyPanel : setKey){
					IPanelCascadedZZZ objPanel = htPanel.get(sKeyPanel);
					listaReturn.add(objPanel);
				}				
			}else{
				KernelJDialogExtendedZZZ dialog = this.getDialogParent();
				if(dialog!=null) {
					IPanelCascadedZZZ objPanelButton = dialog.getPanelButton();
					if(objPanelButton != null) {
						listaReturn.add(objPanelButton);
					}
					
					IPanelCascadedZZZ objPanelNavigator = dialog.getPanelNavigator();
					if(objPanelNavigator != null) {
						listaReturn.add(objPanelNavigator);
					}
					
					IPanelCascadedZZZ objPanelContent = dialog.getPanelContent();
					if(objPanelContent != null) {
						listaReturn.add(objPanelContent);
					}					
				}else {
					KernelJFrameCascadedZZZ frame = this.getFrameParent();
					if(frame!=null) {
						Hashtable<String,IPanelCascadedZZZ> htPanel = frame.getPanelSubAll();
						Set<String> setKey = htPanel.keySet();
						for(String sKeyPanel : setKey){
							IPanelCascadedZZZ objPanel = htPanel.get(sKeyPanel);
							listaReturn.add(objPanel);
						}		
					}
				}
			}
		}
		return listaReturn;
	}

	/** JPanel, searches all other parent panels until no more parent panel is available. The last found panel seems to be the root panel for all cascaded panels. 
	* Lindhauer; 15.05.2006 09:00:08
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public IPanelCascadedZZZ searchPanelRoot() throws ExceptionZZZ{
		IPanelCascadedZZZ panelReturn = null;
		main:{
			if(this.panelParent==null){
				if(this.getFlag(IKernelModuleZZZ.FLAGZ.ISKERNELMODULE.name())) {
					panelReturn = this;
				}else {
					KernelJFrameCascadedZZZ frameParent = this.getFrameParent();
					if(frameParent!=null) {
						panelReturn = frameParent.getPaneContent();
					}				
					if(panelReturn!=null) break main;
					
					KernelJDialogExtendedZZZ dialogParent = this.getDialogParent();
					if(dialogParent!=null) {
						panelReturn = dialogParent.getPanelContent();					
					}
					if(panelReturn!=null) break main;
					
					//Das sollte zuvor gefüllt worden sein in KernelJFrame.launchDoing()
					//frmCascaded.setPanelSub(KernelJFrameCascadedZZZ.getAliasPanelContent(), objPanel);
					//also hier wieder auslesen:
					//panelReturn = frameParent.getPanelSub(KernelJFrameCascadedZZZ.getAliasPanelContent());
					//if(panelReturn!=null) break main;
					
					//Letzter Ausweg: ContentPane
					//ABER BEIM CASTEN MISSMATCH panelReturn = (IPanelCascadedZZZ) frameParent.getContentPane();
				}
				
			}else{
				//!!! Rekursiver aufruf
				IPanelCascadedZZZ panelParent = this.getPanelParent();
				panelReturn = panelParent.searchPanelRoot();
			}
		}
		return panelReturn;
	}
	
	
	
	
	
	public KernelJPanelCascadedZZZ getPanelSub(String sAlias) {
		if(this.htPanelSub.containsKey(sAlias)){
			return (KernelJPanelCascadedZZZ) this.getHashtablePanel().get(sAlias);			
		}else{
			return null;
		}
	}
	
	/** Suche Rekursiv nach einem Panel mit dem angegebenen Alias. Faengt dabei bei sich selber an.
	* @param sAlias
	* @return
	* 
	* lindhaueradmin; 16.03.2007 11:51:02
	 * @throws ExceptionZZZ 
	 */
	public IPanelCascadedZZZ searchPanelSub(String sAlias) throws ExceptionZZZ{
		IPanelCascadedZZZ objReturn=null;
		main:{
			//Zuerst prüfen, ob es sich um das Root-Panel handelt, dann kann ja ggf. das Root-Panel selbst gesucht werden.
			if(this.getPanelParent()==null){
				KernelJFrameCascadedZZZ frameParent = this.getFrameParent();				
				String sRootAlias = KernelJFrameCascadedZZZ.getAliasPanelContent();
				if(sRootAlias.equals(sAlias)){
					objReturn = this;
					break main;
				}
			}
			
			//Ist das Panel selbst mit dem Alias versehen
			String sPanelAlias = this.getAlias();
			if(sAlias.equalsIgnoreCase(sPanelAlias)) {
				objReturn = this;
				break main;
			}
			
			//Nun direkte Unterpanels suchen
			objReturn = this.getPanelSub(sAlias);
			if(objReturn!=null) break main;
			
			//Nun alle Panel durchgehen
			Enumeration objEnum = this.getHashtablePanel().elements();					
			while(objEnum.hasMoreElements()){
				String stemp = (String) objEnum.nextElement();
				IPanelCascadedZZZ panelTemp = this.getPanelSub(stemp);
				if(panelTemp!=null){
					objReturn = panelTemp.searchPanelSub(sAlias);
				}
			}			
		}//ENd main
		return objReturn;
	}
	
	/** Suche Rekursiv nach einem Panel mit dem angegebenen Alias. Faengt dabei bei sich selber an.
	* @param sAlias
	* @return
	* 
	* lindhaueradmin; 16.03.2007 11:51:02
	 * @throws ExceptionZZZ 
	 */
	public IPanelCascadedZZZ searchPanel(String sAlias) throws ExceptionZZZ{
		IPanelCascadedZZZ objReturn=null;
		main:{
			//Zuerst prüfen, ob es sich um das Root-Panel handelt, dann kann ja ggf. das Root-Panel selbst gesucht werden.
			if(this.getPanelParent()==null){							
				String sRootAlias = KernelJFrameCascadedZZZ.getAliasPanelContent();
				if(sRootAlias.equals(sAlias)){
					objReturn = this;
					break main;
				}
			}
			
			KernelJFrameCascadedZZZ frameParent = this.getFrameParent();
			if(frameParent!=null) {				
				System.out.println("Hier fehlt was: Kann noch nicht vom Frame aus nach den Unterpanels suchen.");
			}
			
			KernelJDialogExtendedZZZ dialogParent = this.getDialogParent();
			if(dialogParent!=null) {	
				IPanelCascadedZZZ panelContent = dialogParent.getPanelContent();
				if(panelContent!=null) {
					objReturn = panelContent.searchPanelSub(sAlias);
					if(objReturn!=null)break main;
				}
			}
			
			
			ArrayList<IPanelCascadedZZZ>alPanelNeighbours = this.getPanelNeighbours();
			for(IPanelCascadedZZZ panelNeighbour : alPanelNeighbours) {
				//Pruefen, ob das Nachbarpanel nicht mit dem Alias definiert ist
				String sProgramAlias = panelNeighbour.getProgramAlias();
				if(sAlias.equalsIgnoreCase(sProgramAlias)) {
					objReturn = this;
					break main;
				}
				
				//In den Nachbarpanels das Unterpanel suchen
				objReturn = panelNeighbour.searchPanelSub(sAlias);
				if(objReturn!=null) break main;
			}			
		}//ENd main
		return objReturn;
	}
	
	/** Holt in der Dialogbox das Panel aus der Hashmap der Dialogbox
	* @param sAlias
	* @return
	* 
	* lindhaueradmin; 10.01.2007 10:20:30
	 * @throws ExceptionZZZ 
	 */ 
	public IPanelCascadedZZZ getPanelNeighbour(String sAlias) throws ExceptionZZZ{
		KernelJDialogExtendedZZZ dialog = this.getDialogParent();
		if(dialog!=null){
			return (KernelJPanelCascadedZZZ) dialog.getPanelSub(sAlias);
		}else{		
			KernelJFrameCascadedZZZ frame = this.getFrameParent();
			if(frame!=null){
				IPanelCascadedZZZ panelContent = frame.getPaneContent();
				if(sAlias.toLowerCase().equals("content")){
					return panelContent;
				}else{
					//Eine tiefergehende Suche muesste noch entwickelt werden.
					IPanelCascadedZZZ panelNeighbour = panelContent.getPanelSub(sAlias);
					return panelNeighbour;
				}
			}else{
				return null;
			}
		}		
	}
	
	//### Den Font über alle "registrierten", d.h. in den HashTablen gesetzten Komponenten ändern
	/**
	 * @param font
	 * @return Die Anzahl der mit dem Font tatsächlich aktualisierten Komponenten.
	 */
	public int updateComponentFontAll(Font font){
		int iReturn = 0;
		main:{
			ArrayList<JComponent> listaComponent = this.searchComponentAll();
			for(JComponent objComponent : listaComponent){
				objComponent.setFont(font);
				objComponent.validate();
				objComponent.repaint();
				iReturn++;
			}
		}
		return iReturn;
	}
		
	//#################### Interface IKernelUserZZZ
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelUserZZZ#getKernelObject()
	 */
	@Override
	public IKernelZZZ getKernelObject() {
		return objKernel;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelUserZZZ#setKernelObject(basic.zKernel.IKernelZZZ)
	 */
	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelLogUserZZZ#getLogObject()
	 */
	@Override
	public LogZZZ getLogObject() {
		return this.objLog;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelLogUserZZZ#setLogObject(custom.zKernel.LogZZZ)
	 */
	@Override
	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}
	
	//#################### Interface IKernelModuleUserZZZ
		/* (non-Javadoc)
		 * @see basic.zKernel.IKernelModuleUserZZZ#getProgramName()
		 */
		public String getProgramName() throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(StringZZZ.isEmpty(this.sProgramName)){
					this.sProgramName = KernelUIZZZ.getProgramUsedName(this);
				}
				sReturn = this.sProgramName;				
			}//end main:
			return sReturn;
		}
				
		/* (non-Javadoc)
		 * @see basic.zKernel.IKernelModuleUserZZZ#getProgramAlias()
		 */
		public String getProgramAlias() throws ExceptionZZZ {
			String sReturn = null;
			main:{
				if(StringZZZ.isEmpty(this.sProgramAlias)){
					String sProgramAlias = KernelUIZZZ.readProgramAlias(this); 
					this.sProgramAlias = sProgramAlias; 
					
					//FGL 20180402: Es scheint mir sinnvoll, wenn der Alias nicht definiert ist, denn Programmnamen selbst zurückzugeben...
					if(StringZZZ.isEmpty(this.sProgramAlias)){
						this.sProgramAlias = KernelUIZZZ.getProgramUsedName(this);
					}					
				}
				sReturn = this.sProgramAlias;
			}//end main:
			return sReturn;
		}
		
		/* (non-Javadoc)
		 * @see basic.zKernel.component.IKernelProgramZZZ#resetProgramUsed()
		 */
		public void resetProgramUsed() {
			this.sProgramName = null;
			this.sProgramAlias = null;
		}



	//#################### Interface IObjectZZZ
	/* (non-Javadoc)
	 * @see basic.zBasic.IObjectLogZZZ#logLineDate(java.lang.String)
	 */
//	@Override
//	public void logLineDate(String sLog) throws ExceptionZZZ {
//		LogZZZ objLog = this.getLogObject();
//		if(objLog==null) {
//			String sTemp = AbstractKernelLogZZZ.computeLineDate(this, sLog);
//			System.out.println(sTemp);
//		}else {
//			objLog.writeLineDate(sLog);
//		}		
//	}	
//	
//	@Override
//	public void logLineDateWithPosition(String sLog) throws ExceptionZZZ {
//		LogZZZ objLog = this.getLogObject();
//		if(objLog==null) {
//			String sTemp = AbstractKernelLogZZZ.computeLineDateWithPosition(this, sLog);
//			System.out.println(sTemp);
//		}else {
//			objLog.WriteLineDateWithPosition(this, sLog);
//		}	
//	}
				
	@Override
	public synchronized void logLineDate(String sLog) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLog);
	}
	
	@Override
	public void logLineDate(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLogs);
	}
	
	@Override
	public synchronized void logLineDateWithPosition(String sLog) throws ExceptionZZZ {
		ObjectZZZ.logLineDateWithPosition(this, sLog);
	}
	
	
	@Override
	public synchronized void logLineDateWithPosition(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.logLineDateWithPosition(this, sLogs);
	}

	
	
	public JComponent getComponent(String sAlias) {
		return (JComponent) this.getHashtableComponent().get(sAlias);
	}
	
	public Hashtable<String,JComponent> getHashtableComponent(){
		return this.htComponent;
	}
	
	public Hashtable<String,IPanelCascadedZZZ> getHashtablePanel(){
		return this.htPanelSub;
	}
	
	public Hashtable<String,KernelButtonGroupZZZ<String,AbstractButton>> getHashtableButtonGroup(){
		return this.htButtonGroup;
	}
	
	
//#################### Interface IComponentCascadedUserZZZ
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IComponentCascadedZZZ#setComponent(java.lang.String, javax.swing.JComponent)
	 * 
	 * 
	 * Merke: Hier�ber wird die JComponent per Aliasname "zugriefbar". Z.B. kann nun ein anderes Panel darauf zugreifen. 
	 */
	public void setComponent(String sAlias, JComponent objComponent) {
		objComponent.setName(sAlias);
		this.getHashtableComponent().put(sAlias, objComponent);
	}

	public KernelJFrameCascadedZZZ getFrameParent() {
		return this.frameParent;
	}

	public void setFrameParent(KernelJFrameCascadedZZZ frameParent) {
		this.frameParent = frameParent;
	}

	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IPanelCascadedZZZ#setPanelSub(java.lang.String, basic.zKernelUI.component.KernelJPanelCascadedZZZ)
	 */
	public void setPanelSub(String sAlias, IPanelCascadedZZZ objPanelCascaded) {
		this.getHashtablePanel().put(sAlias, objPanelCascaded);
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IPanelCascadedZZZ#getPanelParent()
	 */
	public IPanelCascadedZZZ getPanelParent() {
		return  (IPanelCascadedZZZ) panelParent;
	}

	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IPanelCascadedZZZ#setPanelParent(basic.zKernelUI.component.KernelJPanelCascadedZZZ)
	 */
	public void setPanelParent(IPanelCascadedZZZ objPanel) {
		this.panelParent = objPanel;
	}
	
	@Override
	public Object clonez() throws ExceptionZZZ {
		try {
			return this.clone();
		}catch(CloneNotSupportedException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
				
		}
	}
	
	//#################### Interface IMouseFeature
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IMouseFeatureZZZ#isJComponentContentDraggable()
	 */
	public boolean isJComponentContentDraggable() throws ExceptionZZZ {			
		return this.getFlag(IMouseFeatureZZZ.FLAGZ.COMPONENT_DRAGGABLE);
	}

	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IMouseFeatureZZZ#setJComponentContentDraggable(boolean)
	 */
	public void setJComponentContentDraggable(boolean bValue) {
		String stemp; boolean btemp;
		if(bValue==true){
			if(this.listenerDraggableWindow==null && this.getFrameParent()!=null){
				this.listenerDraggableWindow = new ListenerMouseMove4DragableWindowZZZ((JPanel)this,this.getFrameParent());
				this.addMouseListener(this.listenerDraggableWindow);
				this.addMouseMotionListener(this.listenerDraggableWindow);
			}
		}else{
			if(this.listenerDraggableWindow!=null){
				this.removeMouseListener(this.listenerDraggableWindow);
				this.removeMouseMotionListener(this.listenerDraggableWindow);
				
				this.listenerDraggableWindow = null;
			}
		}
		
		try {
			stemp = IMouseFeatureZZZ.FLAGZ.COMPONENT_DRAGGABLE.name();
			btemp = this.setFlag(stemp, bValue);
			if(btemp==false){
				ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		 
			}	
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//####################################### 
	//### Flag Handling 
	//#######################################
	 
	//### aus IFlagEnabledZZZ
	@Override
	public int adoptFlagZrelevantFrom(IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
		int iReturn = 0;		
		main:{
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objUsingFlagZ);
			if(StringArrayZZZ.isEmpty(saFlagZpassed)) break main;
			
			for(String sFlag: saFlagZpassed) {
				boolean bValue = objUsingFlagZ.getFlag(sFlag);
				boolean btemp = this.setFlag(sFlag, bValue);
				if(btemp) iReturn++;
			}
		}//end main:
		return iReturn;
	}
	
	
	@Override
	public int adoptFlagZrelevantFrom(IFlagZEnabledZZZ objUsingFlagZ, boolean bValueToSearchFor) throws ExceptionZZZ{
		int iReturn = 0;		
		main:{
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objUsingFlagZ, bValueToSearchFor);
			if(StringArrayZZZ.isEmpty(saFlagZpassed)) break main;
			
			for(String sFlag: saFlagZpassed) {
				boolean btemp = this.setFlag(sFlag, bValueToSearchFor);
				if(btemp) iReturn++;
			}
		}//end main:
		return iReturn;
	}

	//### aus IMouseFeatureZZZ
	@Override
	public boolean getFlag(IMouseFeatureZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IMouseFeatureZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}	
	
	@Override
	public boolean[] setFlag(IMouseFeatureZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IMouseFeatureZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IMouseFeatureZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IMouseFeatureZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### Aus IFlagUserZZZ
	@Override
	public boolean getFlag(IFlagZEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IFlagZEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}	
	
	@Override
	public boolean[] setFlag(IFlagZEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFlagZEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IFlagZEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name()); 
	}
	
	@Override
	public boolean proofFlagSetBefore(IFlagZEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name()); 
	}
	

	//######################
//			@Override
//			public boolean getFlag(String sFlagName) {
//				//Version Vor Java 1.6
//				boolean bFunction = false;
//			main:{
//				if(StringZZZ.isEmpty(sFlagName)) break main;
//				
//				// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//				// bFunction = super.getFlag(sFlagName);
//				// if(bFunction == true) break main;
//				
//				// Die Flags dieser Klasse setzen
//				String stemp = sFlagName.toLowerCase();
//				if(stemp.equals("debug")){
//					bFunction = this.bFlagDebug;
//					break main;
//				}else if(stemp.equals("init")){
//					bFunction = this.bFlagInit;
//					break main;
//				}else{
//					bFunction = false;
//				}		
//			}	// end main:
//	
//			return bFunction;	
//				return this.getFlag(sFlagName);
//			}
//			@Override
//			public boolean setFlag(String sFlagName, boolean bFlagValue) {
//				//Version Vor Java 1.6
//				boolean bFunction = true;
//				main:{
//					if(StringZZZ.isEmpty(sFlagName)) break main;
//					
//					// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//					// bFunction = super.setFlag(sFlagName, bFlagValue);
//					// if(bFunction == true) break main;
//					
//					// Die Flags dieser Klasse setzen
//					String stemp = sFlagName.toLowerCase();
//					if(stemp.equals("debug")){
//						this.bFlagDebug = bFlagValue;
//						bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//						break main;
//					}else if(stemp.equals("init")){
//						this.bFlagInit = bFlagValue;
//						bFunction = true;
//						break main;
//					}else{
//						bFunction = false;
//					}	
//					
//				}	// end main:
//				
//				return bFunction;	
//				try {
//					return this.setFlag(sFlagName, bFlagValue);
//				} catch (ExceptionZZZ e) {
//					System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
//					return false;
//				}
//			}
			
			@Override
			public boolean[] setFlag(String[] saFlagName, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!StringArrayZZZ.isEmptyTrimmed(saFlagName)) {
						baReturn = new boolean[saFlagName.length];
						int iCounter=-1;
						for(String sFlagName:saFlagName) {
							iCounter++;
							boolean bReturn = this.setFlag(sFlagName, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++
			/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
			 * 	 Weteire Voraussetzungen:
			 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
			 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
			 */
			public boolean getFlag(String sFlagName) throws ExceptionZZZ {
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
		/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
		 * Weitere Voraussetzungen:
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
				if(StringZZZ.isEmpty(sFlagName)) {
					bFunction = true;
					break main;
				}
							
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
			
		@Override
		public HashMap<String, Boolean>getHashMapFlag(){
			return this.hmFlag;
		}
		
		/**Gibt alle möglichen FlagZ Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ() throws ExceptionZZZ{
			String[] saReturn = null;
			main:{	
				saReturn = FlagZHelperZZZ.getFlagsZ(this.getClass());				
			}//end main:
			return saReturn;
		}
	
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ(boolean bValueToSearchFor) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, false);
		}
		
		public String[] getFlagZ(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, bLookupExplizitInHashMap);
		}
		
		private String[]getFlagZ_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				ArrayList<String>listasTemp=new ArrayList<String>();
				
				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
				if(bLookupExplizitInHashMap) {
					HashMap<String,Boolean>hmFlag=this.getHashMapFlag();
					if(hmFlag==null) break main;
					
					Set<String> setKey = hmFlag.keySet();
					for(String sKey : setKey){
						boolean btemp = hmFlag.get(sKey);
						if(btemp==bValueToSearchFor){
							listasTemp.add(sKey);
						}
					}
				}else {
					//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
					String[]saFlagZ = this.getFlagZ();
					
					//20211201:
					//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
					//         auch wenn sie garnicht gesetzt wurden.
					//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
					for(String sFlagZ : saFlagZ){
						boolean btemp = this.getFlag(sFlagZ);
						if(btemp==bValueToSearchFor ){ //also 'true'
							listasTemp.add(sFlagZ);
						}
					}
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
		
		/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
		 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
		 * @param name 
		 * @param sFlagName
		 * @return
		 * lindhaueradmin, 23.07.2013
		 * @throws ExceptionZZZ 
		 */
		public boolean proofFlagExists(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZExists(this.getClass(), sFlagName);				
			}//end main:
			return bReturn;
		}
		
		@Override
		public boolean proofFlagSetBefore(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZSetBefore(this, sFlagName);
			}
			return bReturn;
		}
		
		public boolean resetFlags() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				HashMap<String,Boolean> hm = this.getHashMapFlag();
				if(hm.isEmpty())break main;
				
				Set<String> setKey = hm.keySet();
				Iterator<String> itKey = setKey.iterator();
				while(itKey.hasNext()) {
					String sKey = itKey.next();
					Boolean objValue = hm.get(sKey);
					if(objValue!=null) {
						if(objValue.booleanValue()) {
							hm.put(sKey, false); //also nur die true werte auf false setzen und dann gibt diese Methode auch einen "Aenderungsvermerk" zurueck.
							bReturn = true;
						}
					}				
				}						
			}//end main:
			return bReturn;
		}
		
		
					
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//++++++++++++++++++++++++
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//++++++++++++++++++++++++
		/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
		 * 	 Weteire Voraussetzungen:
		 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
		 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
		 */
		public boolean getFlagLocal(String sFlagName) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName)) break main;
											
				HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
				Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
				if(objBoolean==null){
					bFunction = false;
				}else{
					bFunction = objBoolean.booleanValue();
				}
								
			}	// end main:
			
			return bFunction;	
		}
		
		/** DIESE METHODEN MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
		 * Weitere Voraussetzungen:
		 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
		 * - Innere Klassen müssen auch public deklariert werden.
		 * @param objClassParent
		 * @param sFlagName
		 * @param bFlagValue
		 * @return
		 * lindhaueradmin, 23.07.2013
		 */
		public boolean setFlagLocal(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName)) {
					bFunction = true;
					break main;
				}
							
				bFunction = this.proofFlagLocalExists(sFlagName);															
				if(bFunction == true){
					
					//Setze das Flag nun in die HashMap
					HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
					hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
					bFunction = true;								
				}										
			}	// end main:
			
			return bFunction;	
		}
		
		@Override
		public boolean[] setFlagLocal(String[] saFlag, boolean bValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!StringArrayZZZ.isEmptyTrimmed(saFlag)) {
					baReturn = new boolean[saFlag.length];
					int iCounter=-1;
					for(String sFlag:saFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(sFlag, bValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
			
		@Override
		public HashMap<String, Boolean>getHashMapFlagLocal(){
			return this.hmFlagLocal;
		}
		
		@Override
		public void setHashMapFlagLocal(HashMap<String, Boolean> hmFlagLocal) {
			this.hmFlagLocal = hmFlagLocal;
		}
		
		/**Gibt alle möglichen FlagZ Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZLocal() throws ExceptionZZZ{
			String[] saReturn = null;
			main:{	
				saReturn = FlagZHelperZZZ.getFlagsZDirectAvailable(this.getClass());				
			}//end main:
			return saReturn;
		}
	
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZLocal(boolean bValueToSearchFor) throws ExceptionZZZ{
			return this.getFlagZLocal_(bValueToSearchFor, false);
		}
		
		public String[] getFlagZLocal(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			return this.getFlagZLocal_(bValueToSearchFor, bLookupExplizitInHashMap);
		}
		
		private String[]getFlagZLocal_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				ArrayList<String>listasTemp=new ArrayList<String>();
				
				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
				if(bLookupExplizitInHashMap) {
					HashMap<String,Boolean>hmFlag=this.getHashMapFlagLocal();
					if(hmFlag==null) break main;
					
					Set<String> setKey = hmFlag.keySet();
					for(String sKey : setKey){
						boolean btemp = hmFlag.get(sKey);
						if(btemp==bValueToSearchFor){
							listasTemp.add(sKey);
						}
					}
				}else {
					//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
					String[]saFlagZ = this.getFlagZLocal();
					
					//20211201:
					//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
					//         auch wenn sie garnicht gesetzt wurden.
					//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
					for(String sFlagZ : saFlagZ){
						boolean btemp = this.getFlagLocal(sFlagZ);
						if(btemp==bValueToSearchFor ){ //also 'true'
							listasTemp.add(sFlagZ);
						}
					}
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
		
		/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
		 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
		 * @param name 
		 * @param sFlagName
		 * @return
		 * lindhaueradmin, 23.07.2013
		 * @throws ExceptionZZZ 
		 */
		public boolean proofFlagLocalExists(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
//				Die direkte Vererbung reicht nicht mehr
//				if(StringZZZ.isEmpty(sFlagName))break main;
//				bReturn = FlagZHelperZZZ.proofFlagZLocalDirectExists(this.getClass(), sFlagName);
					
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZLocalExists(this.getClass(), sFlagName);				
			}//end main:
			return bReturn;		
		}
		
		@Override
		public boolean proofFlagLocalSetBefore(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZLocalSetBefore(this, sFlagName);
			}
			return bReturn;
		}
			
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//++++++++++++++++++++++++
		@Override
		public HashMap<String, Boolean> getHashMapFlagPassed() {
			return this.hmFlagPassed;
		}
		
		@Override
		public void setHashMapFlagPassed(HashMap<String, Boolean> hmFlagPassed) {
			this.hmFlagPassed = hmFlagPassed;
		}
		
			
			
			/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
			 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
			 *    
			 * @return
			 * @throws ExceptionZZZ 
			 */
			public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
				return this.getFlagZ_passable_(bValueToSearchFor, false, objUsingFlagZ);
			}
			
			public String[] getFlagZ_passable(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
				return this.getFlagZ_passable_(bValueToSearchFor, bLookupExplizitInHashMap, objUsingFlagZ);
			}
			
			private String[] getFlagZ_passable_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
				String[] saReturn = null;
				main:{
					
					//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
					String[] saFlag = this.getFlagZ(bValueToSearchFor,bLookupExplizitInHashMap);
					
					//2. Hole alle FlagZ der Zielklasse
					String[] saFlagTarget = objUsingFlagZ.getFlagZ();
					
					//ArrayList<String>listasFlagPassable=new ArrayList<String>();
					//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
					
					saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
				}//end main:
				return saReturn;
			}
			
			/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
			 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
			 *    
			 * @return
			 * @throws ExceptionZZZ 
			 */
			public String[] getFlagZ_passable(IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
				return this.getFlagZ_passable_(objUsingFlagZ);
			}
			
			private String[] getFlagZ_passable_(IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
				String[] saReturn = null;
				main:{
					
					//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
					String[] saFlag = this.getFlagZ();
					
					//2. Hole alle FlagZ der Zielklasse
					String[] saFlagTarget = objUsingFlagZ.getFlagZ();
					
					ArrayList<String>listasFlagPassable=new ArrayList<String>();
					//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
					
					saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
				}//end main:
				return saReturn;
			}
		//#######################################

		//### Functions #########################
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//###Aus IKernelProgramZZZ
	@Override
	public boolean getFlag(IKernelProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelProgramZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelProgramZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelProgramZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}		
	
	@Override
	public boolean proofFlagExists(IKernelProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
			
	//### AUS IKernelModuleUserZZZ
	@Override
	public IKernelModuleZZZ getModule() throws ExceptionZZZ {
		if(this.objModule==null) {
			this.objModule = KernelUIZZZ.searchModule(this);
		}
		return this.objModule;
	}

	@Override
	public void setModule(IKernelModuleZZZ objModule) {
		this.objModule = objModule;		
	}
	
	
	@Override
	public boolean getFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelModuleUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelModuleUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
		}
	
	//### Aus IKernelModule
	@Override
	public String getModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(this.sModuleName)) {
				if(this.objModule!=null) {
					if(this.objModule.getClass().isInstance(this.getClass())||this.objModule.getClass().equals(this.getClass())) {
						sReturn = KernelUIZZZ.getModuleUsedName((IPanelCascadedZZZ)this);							
					}else {
						sReturn = KernelUIZZZ.getModuleUsedName(objModule);						
					}
				}else {
					
					IKernelModuleZZZ objModule = KernelUIZZZ.searchModule(this);
					if(objModule!=null) {
						this.setModule(objModule);
						sReturn = KernelUIZZZ.getModuleUsedName(objModule);					
					}else {						
						//"Nicht weiter suchen, sonst Endlosscheifengefahr": undefinierter Modulname als Ergebnis zurückgeben.						
						sReturn = this.sMODULE_UNDEFINED;							
					}
				}		
				this.sModuleName = sReturn;
			}else {
				sReturn = this.sModuleName;				
			}								
		}//end main:		
		return sReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.component.IKernelModuleZZZ#resetModuleUsed()
	 */
	public void resetModuleUsed() {
		this.sModuleName = null;
	}
	
	
	@Override
	public boolean getFlag(IKernelModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelModuleZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelModuleZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelModuleZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	//#### IComponentCascadedUserZZZ
	@Override
	public boolean createDebugUi() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String stemp;
					
			if(this.getFlag(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())) {
				String sTitle = "Cascaded";
								
				//20210419 Bei vielen Zeilen im Label "verwischt" dann das UI
				//Idee: Führe eine "Label-Gruppe" ein und einen Button, der diese Labels dann der Reihe nach durchschalten kann.

				//Die Anzahl der Texteinträge bestimmt die Anzahl der JLabel Objekte, bestimmt die Anzahl der Gruppen.
				//Mit einer einfachen ArrayList kann aber immer nur 1 Label pro Button definiert werden. Es muss eine Indizierte HashMap sein.
				//Teste mit mehreren Labels pro Gruppe.
				
				//Die Action als eigene Klasse ausgliedern und alle beteiligten Klassen in ein passendes Package verschieben.
		        //Den Debug/Testpanel für die Gruppenumschaltung soll dann auch diese nutzen.

				//Die Verwaltung der HashMap für die Componenten einer ComponentGroupCollection übertragen.	
				//Darin auch den Eventhandler/Eventbroker, etc. hinzufügen.
				
				//20210507 Vereinheitlichung die Definition der ComponentGroup im Debug-Test-Fall und im KernelJPanelCascadedZZZ.createDebugUI();

				//20210514 Modell hinzugefügt.
				          //Wg. Problematik der Reihenfolge der Panels hinzuzufügen 
				          //und daraufhin Probleme beim korrekten/gleichen ermitteln des Programnamens
						  //==> Bei jedem Umschalten die Werte der Componenten/Labels neu errechnen
				          //    und neu füllen.
						
				//TODOGOON; //Ein Button zum Umschalten ist auch erst im Panel notwendig, wenn es mehr als 1 Gruppenobjekt gibt.
																
											
				//++++ Die GroupCollection, basierend auf dem Modell
				ModelPanelDebugZZZ modelDebug = new ModelPanelDebugZZZ(this.getKernelObject(),sTitle, this);
				JComponentGroupCollectionZZZ groupc = new JComponentGroupCollectionZZZ(objKernel, modelDebug);
				groupc.setVisible(0); //Initiales Setzen der Sichtbarkeit
										
				//######## Das UI gestalten. Die Reihenfolge der Componenten ist wichtig für die Reihenfolge im UI #################
				//++++ Der Umschaltebutton
				JButton buttonSwitch = JComponentGroupHelperZZZ.createButtonSwitch(objKernel, this, groupc);						
				this.setComponent(KernelJPanelCascadedZZZ.sBUTTON_SWITCH, buttonSwitch);				
				this.add(buttonSwitch);
				
				//+++ Nun erst die Label dem Panel hinzufuegen	
				//Merke: Die auszutauschenden Komponenten müssen in die gleichen Zellen hinzugefügt werden. Sonst entstehen Leerzellen
				HashMapIndexedObjectZZZ<Integer,JComponentGroupZZZ> hmComponent = groupc.getHashMapIndexed();
				Iterator it = hmComponent.iterator();				
				int iIndexOuterMax = hmComponent.size() -1;
				for(int iIndexOuter=0; iIndexOuter <= iIndexOuterMax; iIndexOuter++) {
					JComponentGroupZZZ group = (JComponentGroupZZZ) hmComponent.getValue(iIndexOuter);
					if(group!=null) {
											
						ArrayList<JComponent>listaComponenttemp = (ArrayList<JComponent>) group.getComponents();
						if(listaComponenttemp!=null) {
						if(!listaComponenttemp.isEmpty()) {
							
							//Die Labels der Arraylist abarbeiten und dem panel hinzufügen
							int iIndexInner=-1;				
							for(JComponent componenttemp : listaComponenttemp) {
								if(componenttemp!=null) {
									iIndexInner=iIndexInner+1;
									this.add(componenttemp);
									this.setComponent("ComponentDebug"+iIndexOuter+"_"+iIndexInner, componenttemp);
								}
							}		
						}
						}
					}
				}
				bReturn = true;
			}//end if ... flag gesetzt? DEBUGUI_PANELLABEL_ON
		}//end main:
		return bReturn;		
	}	
	
	@Override
	public boolean reset() {
		this.resetModuleUsed();
		return true;
	}

}
