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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.listener.ListenerMouseMove4DragableWindowZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.component.IKernelModuleUserZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagUserZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.component.componentGroup.ActionSwitchZZZ;
import basic.zKernelUI.component.componentGroup.IModelComponentGroupValueZZZ;
import basic.zKernelUI.component.componentGroup.JComponentGroupCollectionZZZ;
import basic.zKernelUI.component.componentGroup.JComponentGroupHelperZZZ;
import basic.zKernelUI.component.componentGroup.JComponentGroupZZZ;
import basic.zKernelUI.component.componentGroup.KernelSenderComponentGroupSwitchZZZ;
import basic.zKernelUI.component.componentGroup.ModelPanelDebugZZZ;
import custom.zKernel.LogZZZ;

/** Klasse bietet als Erweiterung zu JPanel die Verschachtelung von Panels an.
 * Merke: Ohne ein JFrame als Parent funktioniert es nicht, das Panel per Drag mit der Maus zu bewegen.
 * 
 *  Merke: Die Panels können sowohl nur modulnutzer als auch selber Modul sein. Darum werden beide Interfaces implementiert.
 */
public abstract class KernelJPanelCascadedZZZ extends JPanel implements IPanelCascadedZZZ, IKernelModuleZZZ, IKernelModuleUserZZZ, IKernelUserZZZ, IObjectZZZ, IMouseFeatureZZZ, IDebugUiZZZ, IFlagUserZZZ{
	protected static final String sBUTTON_SWITCH = "buttonSwitch";
   	
	
	protected IKernelZZZ objKernel;   //das "protected" erlaubt es hiervon erbende Klassen mit XYXErbendeKlasse.objKernel zu arbeiten.
	protected LogZZZ objLog;
	protected ExceptionZZZ objException;
	protected IKernelModuleZZZ objModule=null; //Das Modul, z.B. die Dialogbox, in der das Program gestartet wird.

	//Merke: Nur einige besondere Panels sind selbst Module.
	protected String sModuleName=null;         //Notwendig, wenn das Panel selbst das Modul ist.

	private IPanelCascadedZZZ panelParent;
	private KernelJFrameCascadedZZZ frameParent;
	private KernelJDialogExtendedZZZ dialogParent;
	
	protected Hashtable<String,IPanelCascadedZZZ> htPanelSub=new Hashtable<String,IPanelCascadedZZZ>();
	protected Hashtable<String,JComponent> htComponent = new Hashtable<String,JComponent>();
	protected Hashtable <String, KernelButtonGroupZZZ<String, AbstractButton>> htButtonGroup = new Hashtable<String, KernelButtonGroupZZZ<String, AbstractButton>>();
	
	private ListenerMouseMove4DragableWindowZZZ listenerDraggableWindow = null; 

	protected String sProgramName  = null; //ggf. der Name des Elternprogramms, s. KernelKonfiguration
	protected String sProgramAlias = null; //ggf. der Alias des Elternprogramms, s. KernelKonfiguration
	
	/**20130721: Umgestellt auf HashMap und die Enum-Flags, Compiler auf 1.7 geaendert
	 * 
	 */
	/*private boolean flagComponentKernelProgram = false; // 2013-07-08: Damit wird gesagt, dass fuer dieses Panel ein "Program-Abschnitt" in der Kernel - Konfigurations .ini - Datei vorhanden ist.
	                                                    //             Bei der Suche nach Parametern wird von der aktuellen Komponente weiter "nach oben" durchgegangen und der Parameter f�r jede Programkomponente gesucht.
	private boolean flagComponentDraggable = true;
	private boolean bFlagDebug = false;
	private boolean bFlagInit = false;
	private boolean bFlagTerminate = false;*/	
	public enum FLAGZ{
		COMPONENT_DRAGGABLE, TERMINATE;
	}
	private HashMap<String, Boolean>hmFlag=new HashMap<String, Boolean>();
	private HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>();
	
	
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
		KernelJPanelCascadedNew_(objKernel, null, null, null, null);		
	}
	
	/** constructor used for creating a ROOT-Panel
	* Lindhauer; 10.05.2006 06:33:12
	 * @param objKernel
	 * @param panelParent
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
		super(); //Das initialisiert JPanel;		
		KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, JFrame frameBasic) throws ExceptionZZZ{		
		FrameCascadedRootDummyZZZ frameParent = new FrameCascadedRootDummyZZZ(objKernel, frameBasic);				
		KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null);
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
				KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null);
				break main;
			}
			
			//2. nur cascadedFrames
			if(bIsCascadedFrame){				
				KernelJFrameCascadedZZZ frameParent =  (KernelJFrameCascadedZZZ) contentPane;   //SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)contentPane);
				frameParent.getContentPane().add(this);
				KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null);
				break main;
			}
			

			//3. der Rest, als auch ggf. andere Panels
			contentPane.add(this); // das liefert bei JFrame einen Fehler. NICHT L�SCHEN ggf. ist eine Fallunterscheidung f�r die verschiedenen Container notwendig
			
			boolean bIsCascadedPanel = ReflectClassZZZ.isChildclassFrom(clsContainer, KernelJPanelCascadedZZZ.class);
			if(bIsCascadedPanel){
				KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) contentPane;
				this.setPanelParent(panelParent);
			}
			
			KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)contentPane);		
			KernelJPanelCascadedNew_(objKernel, frameParent, null, null, null);
		}//end main:
		
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog) throws ExceptionZZZ{
		KernelJPanelCascadedNew_(objKernel, null, dialog, null, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog, KernelJPanelCascadedZZZ panelRoot) throws ExceptionZZZ{
		KernelJPanelCascadedNew_(objKernel, null, dialog, panelRoot, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog,  HashMap<String, Boolean>hmFlag) throws ExceptionZZZ{
		this(objKernel, dialog);
		String stemp; boolean btemp; String sLog;
		
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				stemp = sKey;
				btemp = this.setFlagZ(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap). Maybe an interface is not implemented.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}			
			}
		}
	}
	
	
	/** constructor used for creating a SUB-Panel
	* Lindhauer; 10.05.2006 06:33:12
	 * @param objKernel
	 * @param panelParent
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{		
		KernelJPanelCascadedNew_(objKernel, null, null, panelParent, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent, HashMap<String, Boolean> hmFlag) throws ExceptionZZZ{	
		KernelJPanelCascadedNew_(objKernel, null, null, panelParent, hmFlag);
	}
	
	private boolean KernelJPanelCascadedNew_(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent, KernelJDialogExtendedZZZ dialog, KernelJPanelCascadedZZZ panelParent, HashMap<String, Boolean> hmFlag ) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
		String stemp; boolean btemp; String sLog;
		
		//Das direkte Setzen der Flags, da nicht aus ObjectZZZ geerbt werden kann, hier ausführen.
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){				
				stemp = sKey;
				btemp = this.setFlagZ(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap). Maybe an interface is not implemented.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
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
				Map<String,Boolean>hmFlagZpassed = objConfig.getHashMapFlagZpassed();	
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
		boolean bDebugUI = this.createDebugUi();
		
		//Einen Mouse Listener hinzufuegen, der es erlaubt Fenster zu ziehen (auch im Panel und nicht nur in der Titelleiste)
		//if(this.getFlag("isdraggable")){
		if(this.getFlagZ(FLAGZ.COMPONENT_DRAGGABLE.name())){	
			this.setJComponentContentDraggable(this.isJComponentContentDraggable());
		}
			bReturn = true;
		}//end main:
		return bReturn;		
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
	
	public JComponent searchComponent(String sKeyComponent){
		JComponent objReturn = null;
		main:{
			objReturn = searchComponent(sKeyComponent, false);
			if(objReturn!=null)break main;
			
			objReturn = searchComponent(sKeyComponent, true);			
		}//end main
		return objReturn;
	}
			
	public JComponent searchComponent(String sKeyComponent, boolean bInNeighbours){
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
	
	public ArrayList<IPanelCascadedZZZ> getPanelNeighbours() {
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
				if(this.getFlagZ(IKernelModuleZZZ.FLAGZ.ISKERNELMODULE.name())) {
					panelReturn = this;
				}else {
					KernelJFrameCascadedZZZ frameParent = this.frameParent;
					if(frameParent!=null) {
						panelReturn = frameParent.getPaneContent();
						break main;
					}				
					
					KernelJDialogExtendedZZZ dialogParent = this.dialogParent;
					if(dialogParent!=null) {
						panelReturn = dialogParent.getPanelContent();
						break main;
					}
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
	
	/** Suche Rekursiv nach einem Panel mit dem angegebenen Alias. F�ngt dabei bei sich selber an.
	* @param sAlias
	* @return
	* 
	* lindhaueradmin; 16.03.2007 11:51:02
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ searchPanelSub(String sAlias) throws ExceptionZZZ{
		KernelJPanelCascadedZZZ objReturn=null;
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
			
			//Nun direkte Unterpanels suchen
			objReturn = this.getPanelSub(sAlias);
			if(objReturn!=null) break main;
			
			//Nun alle Panel durchgehen
			Enumeration objEnum = this.getHashtablePanel().elements();					
			while(objEnum.hasMoreElements()){
				String stemp = (String) objEnum.nextElement();
				KernelJPanelCascadedZZZ panelTemp = this.getPanelSub(stemp);
				if(panelTemp!=null){
					objReturn = panelTemp.searchPanelSub(sAlias);
				}
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
	@Override
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		 this.objException = objException;
	}
	
	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public void logLineDate(String sLog) {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.WriteLineDate(sLog);
		}		
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
	
	//#################### Interface IComponentDraggable
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IMouseFeatureZZZ#isJComponentContentDraggable()
	 */
	public boolean isJComponentContentDraggable() {	
		//return this.flagComponentDraggable;
		return this.getFlagZ(FLAGZ.COMPONENT_DRAGGABLE.name());
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
			stemp = FLAGZ.COMPONENT_DRAGGABLE.name();
			btemp = this.setFlagZ(stemp, bValue);
			if(btemp==false){
				ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available. Maybe an interface is not implemented.", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		 
			}	
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//### FlagMethods ##########################		
			@Override
			public boolean getFlag(String sFlagName) {
				//Version Vor Java 1.6
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
				return this.getFlagZ(sFlagName);
			}
			@Override
			public boolean setFlag(String sFlagName, boolean bFlagValue) {
				//Version Vor Java 1.6
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
				try {
					return this.setFlagZ(sFlagName, bFlagValue);
				} catch (ExceptionZZZ e) {
					System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
					return false;
				}
			}
			
			/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
			 * 	 Weteire Voraussetzungen:
			 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
			 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
			 */
			public boolean getFlagZ(String sFlagName) {
				boolean bFunction = false;
				main:{
					if(StringZZZ.isEmpty(sFlagName)) break main;
												
					HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
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
		 * Weteire Voraussetzungen:
		 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
		 * - Innere Klassen müssen auch public deklariert werden.
		 * @param objClassParent
		 * @param sFlagName
		 * @param bFlagValue
		 * @return
		 * lindhaueradmin, 23.07.2013
		 */
		public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName)) {
					bFunction = true;
					break main;
				}
							
				bFunction = this.proofFlagZExists(sFlagName);															
				if(bFunction == true){
					
					//Setze das Flag nun in die HashMap
					HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
					hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
					bFunction = true;								
				}										
			}	// end main:
			
			return bFunction;	
		}
			
		@Override
		public HashMap<String, Boolean>getHashMapFlagZ(){
			return this.hmFlag;
		}
			
		@Override
		public HashMap<String, Boolean> getHashMapFlagZpassed() {
			return this.hmFlagPassed;
		}
		@Override
		public void setHashMapFlagZpassed(HashMap<String, Boolean> hmFlagPassed) {
			this.hmFlagPassed = hmFlagPassed;
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
				String[] saReturn = null;
				main:{
					
					
					ArrayList<String>listasTemp=new ArrayList<String>();
					
					//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
					//                                  Diese kann man nur durch Einzelprüfung ermitteln.
					if(bValueToSearchFor==true){
						HashMap<String,Boolean>hmFlag=this.getHashMapFlagZ();
						if(hmFlag==null) break main;
						
						Set<String> setKey = hmFlag.keySet();
						for(String sKey : setKey){
							boolean btemp = hmFlag.get(sKey);
							if(btemp==bValueToSearchFor){
								listasTemp.add(sKey);
							}
						}
					}else{
						String[]saFlagZ = this.getFlagZ();
						for(String sFlagZ : saFlagZ){
							boolean btemp = this.getFlagZ(sFlagZ);
							if(btemp==bValueToSearchFor ){ //also 'false'
								listasTemp.add(sFlagZ);
							}
						}
					}
					saReturn = listasTemp.toArray(new String[listasTemp.size()]);
				}//end main:
				return saReturn;
			}
			
			/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
			 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
			 *    
			 * @return
			 * @throws ExceptionZZZ 
			 */
			public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ{
				String[] saReturn = null;
				main:{
					
					//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
					String[] saFlag = this.getFlagZ(bValueToSearchFor);
					
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
			public String[] getFlagZ_passable(IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ{
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
			
			
			

		/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
		 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
		 * @param name 
		 * @param sFlagName
		 * @return
		 * lindhaueradmin, 23.07.2013
		 * @throws ExceptionZZZ 
		 */
		public boolean proofFlagZExists(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZExists(this.getClass(), sFlagName);				
			}//end main:
			return bReturn;
		}
		//#######################################

		//### Functions #########################
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
	public String getModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(this.sModuleName)) {
				if(this.objModule!=null) {
					if(this.objModule.getClass().isInstance(this.getClass())||this.objModule.getClass().equals(this.getClass())) {
						sReturn = KernelUIZZZ.getModuleUsedName((IPanelCascadedZZZ)this);	
						this.sModuleName = sReturn;
					}else {
						sReturn = KernelUIZZZ.getModuleUsedName(objModule);						
					}
				}else {
					sReturn = KernelUIZZZ.getModuleUsedName((IPanelCascadedZZZ)this);	
					this.sModuleName = sReturn;
				}				
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
	
	//#### IComponentCascadedUserZZZ
	@Override
	public boolean createDebugUi() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String stemp;
					
			if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())) {
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
				HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmComponent = groupc.getHashMapIndexed();
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
}
