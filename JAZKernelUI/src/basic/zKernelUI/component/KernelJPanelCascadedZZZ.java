package basic.zKernelUI.component;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
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
import basic.zKernelUI.component.labelGroup.JComponentGroupHelperZZZ;
import basic.zKernelUI.component.labelGroup.JComponentGroupZZZ;
import basic.zKernelUI.thread.KernelSwingWorkerZZZ;
import custom.zKernel.LogZZZ;

//TODOGOON: Wenn das funktioniert diese Klassen in ein vernünftiges PAckage verschieben.
import debug.zKernelUI.component.buttonSwitchLabelGroup.EventComponentGroupSwitchZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.ISenderComponentGroupSwitchZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.KernelSenderComponentGroupSwitchZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.PanelDebugHelperZZZ;

/** Klasse bietet als Erweiterung zu JPanel die Verschachtelung von Panels an.
 * Merke: Ohne ein JFrame als Parent funktioniert es nicht, das Panel per Drag mit der Maus zu bewegen.
 * 
 *  Merke: Die Panels können sowohl nur modulnutzer als auch selber Modul sein. Darum werden beide Interfaces implementiert.
 */
public abstract class KernelJPanelCascadedZZZ extends JPanel implements IPanelCascadedZZZ, IKernelModuleZZZ, IKernelModuleUserZZZ, IKernelUserZZZ, IObjectZZZ, IMouseFeatureZZZ, IDebugUiZZZ, IFlagUserZZZ{
	private static final String sBUTTON_SWITCH = "buttonSwitch";
   	
	
	protected IKernelZZZ objKernel;   //das "protected" erlaubt es hiervon erbende Klassen mit XYXErbendeKlasse.objKernel zu arbeiten.
	protected LogZZZ objLog;
	protected ExceptionZZZ objException;
	protected IKernelModuleZZZ objModule=null; //Das Modul, z.B. die Dialogbox, in der das Program gestartet wird.

	//Merke: Nur einige besondere Panels sind selbst Module.
	protected String sModuleName=null;         //Notwendig, wenn das Panel selbst das Modul ist.

	private JPanel panelParent;
	private KernelJFrameCascadedZZZ frameParent;
	private KernelJDialogExtendedZZZ dialogParent;
	
	private Hashtable<String,JPanel> htPanelSub=new Hashtable<String,JPanel>();
	private Hashtable<String,JComponent> htComponent = new Hashtable<String,JComponent>();
	private Hashtable <String, KernelButtonGroupZZZ<String, AbstractButton>> htButtonGroup = new Hashtable<String, KernelButtonGroupZZZ<String, AbstractButton>>();
	
	private ListenerMouseMove4DragableWindowZZZ listenerDraggableWindow = null; 

	private String sProgramName  = null; //ggf. der Name des Elternprogramms, s. KernelKonfiguration
	private String sProgramAlias = null; //ggf. der Alias des Elternprogramms, s. KernelKonfiguration
	
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
		KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)this); 
		KernelJPanelCascadedNew_(objKernel, frameParent, null);		
	}
	
	/** constructor used for creating a ROOT-Panel
	* Lindhauer; 10.05.2006 06:33:12
	 * @param objKernel
	 * @param panelParent
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
		super(); //Das initialisiert JPanel;		
		KernelJPanelCascadedNew_(objKernel, frameParent, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, JFrame frameBasic) throws ExceptionZZZ{
		frameBasic.getContentPane().add(this);
	     
		FrameCascadedRootDummyZZZ frameParent = new FrameCascadedRootDummyZZZ(objKernel, frameBasic);
		
		//20130625 Hinzugef�gt, um das Suchen des NAchbarpanels schon erm�glichen, bevor das launch() von KernelJFrameCascaded abgeschlossen ist.
		//Wird normalerweise im KErnelJFrameCascaded.lauchchDoing() gemacht, aber bei der Konfiguration der PAnels muss schon auf ein Nachbarpanel zugegriffen werden.
		frameParent.setPanelContent(this); //this: Ist das oberste Panel, wichtig wenn man aus einem Panel nach den Nachbarpanels sucht.
		//NEIN, nur DUMMY frameParent.getContentPane().add(this);		
			
		KernelJPanelCascadedNew_(objKernel, frameParent, null);
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
				KernelJPanelCascadedNew_(objKernel, frameParent, null);
				break main;
			}
			
			//2. nur cascadedFrames
			if(bIsCascadedFrame){				
				KernelJFrameCascadedZZZ frameParent =  (KernelJFrameCascadedZZZ) contentPane;   //SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)contentPane);
				frameParent.getContentPane().add(this);
				KernelJPanelCascadedNew_(objKernel, frameParent, null);
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
			KernelJPanelCascadedNew_(objKernel, frameParent, null);
		}//end main:
		
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog) throws ExceptionZZZ{
		//Das Panel f�gt sich dem Dialog selbst hinzu
		//Container objContainer = dialog.getContentPane();
		//NEIN objContainer.add(this);
		
		//TODOGOON 20210404 Das KernelJPanelCascadedNew_ packen, soweit möglich....
		//Damit auf die Hashtable der Dialogbox zugegriffen werden kann, um andere Panels zu erreichen
		this.setDialogParent(dialog);
		
		//Damit dieses Panel zur Verf�gung steht um "mit der Maus bewegt" zu werden.
		Container objContainer = dialog.getContentPane();
		//JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)objContainer);
		KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent) objContainer);
		
		//NEIN, sonst wird der ganze frameParent beim Ziehen des DialogboxPanels mit bewegt KernelJPanelCascadedNew_(objKernel, frameParent);
//		if(objKernel!= null){
//			this.setKernelObject(objKernel);
//			this.setLogObject(objKernel.getLogObject());
//		}		
//		this.setFrameParent(frameParent);	
		KernelJPanelCascadedNew_(objKernel, frameParent, null);
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
				
		this.setPanelParent(panelParent);
		//Das liefert aber beim Ersstellen zjmindest nur NULL JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)this);
		//Dann eher folgendes:
		KernelJPanelCascadedZZZ panelRoot = this.searchPanelRoot();
		KernelJFrameCascadedZZZ frameParent = panelRoot.getFrameParent();
		
		KernelJPanelCascadedNew_(objKernel, frameParent, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent, HashMap<String, Boolean> hmFlag) throws ExceptionZZZ{
		
		this.setPanelParent(panelParent);
		//Das liefert aber beim Ersstellen zjmindest nur NULL JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)this);
		//Dann eher folgendes:
		KernelJPanelCascadedZZZ panelRoot = this.searchPanelRoot();
		KernelJFrameCascadedZZZ frameParent = panelRoot.getFrameParent();
		
		KernelJPanelCascadedNew_(objKernel, frameParent, hmFlag);
	}
	
	private boolean KernelJPanelCascadedNew_(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent, HashMap<String, Boolean> hmFlag ) throws ExceptionZZZ{
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
		
		this.setFrameParent(frameParent);
		if(this.getFrameParent()!=null) {
			//20130625 Hinzugef�gt, um das Suchen des NAchbarpanels schon erm�glichen, bevor das launch() von KernelJFrameCascaded abgeschlossen ist.
			//Wird normalerweise im KErnelJFrameCascaded.lauchchDoing() gemacht, aber bei der Konfiguration der PAnels muss schon auf ein Nachbarpanel zugegriffen werden.
			frameParent.setPanelContent(this); //this: Ist das oberste Panel, wichtig wenn man aus einem Panel nach den Nachbarpanels sucht.
			//frameParent.getContentPane().add(this);	
		}
		
		//Ggfs. die DebugUI-Angaben hinzufügen, das kann z.B. nur das Label mit dem Klassennamen sein.
		//Gesteuert werde soll das durch Flags, die auch über die Kommandozeile übergeben werden können.
		boolean bDebugUI = createDebugUi("Cascaded");
		
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
			
			Hashtable<String,JPanel> htPanel = this.getHashtablePanel();
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
			Hashtable<String,JComponent> htComponent = this.getHashtableComponent();
			objReturn = htComponent.get(sKeyComponent);
			if(objReturn!=null) break main;
			
			Hashtable<String,JPanel> htPanel = this.getHashtablePanel();
			Set<String> setKey = htPanel.keySet();
			for(String sKeyPanel : setKey){
				KernelJPanelCascadedZZZ objPanel = (KernelJPanelCascadedZZZ) htPanel.get(sKeyPanel);
				if(objPanel!=null){
					objReturn = objPanel.searchComponent(sKeyComponent);
					if(objReturn!=null) break main;
				}
			}			
		}//end main
		return objReturn;
	}

	/** JPanel, searches all other parent panels untill no more parent panel is available. The last found panel seems to be the root panel for all cascaded panels. 
	* Lindhauer; 15.05.2006 09:00:08
	 * @return
	 */
	public KernelJPanelCascadedZZZ searchPanelRoot(){
		KernelJPanelCascadedZZZ panelReturn = null;
		main:{
			if(this.getPanelParent()==null){
				panelReturn = this;
			}else{
				//!!! Rekursiver aufruf
				KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
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
	public KernelJPanelCascadedZZZ getPanelNeighbour(String sAlias) throws ExceptionZZZ{
		KernelJDialogExtendedZZZ dialog = this.getDialogParent();
		if(dialog!=null){
			return (KernelJPanelCascadedZZZ) dialog.getPanelSub(sAlias);
		}else{		
			KernelJFrameCascadedZZZ frame = this.getFrameParent();
			if(frame!=null){
				KernelJPanelCascadedZZZ panelContent = frame.getPaneContent();
				if(sAlias.toLowerCase().equals("content")){
					return panelContent;
				}else{
					//Eine tiefergehende Suche m�sste noch entwickelt werden.
					KernelJPanelCascadedZZZ panelNeighbour = panelContent.getPanelSub(sAlias);
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
//	@Override
//	public String getModuleName() throws ExceptionZZZ {
//			String sReturn = new String("");
//			main:{	
//				//TODOGOON; //20210310: Jetzt gibt es ja noch ggfs. ein Abstraktes Module-Objekt.
//				//                    Wenn das abstrakte Modul Objekt vorhanden ist, dann den ModulNamen daraus verwenden.
//				//                    Ist das abstrakte Modul Objekt nicht vorhanden, dann den Modulnamen wie bisher anhand des Panels selbst ermitteln.				
//				if(StringZZZ.isEmpty(this.sModuleName)) {
//					if(this.getModule()!=null) {
//						this.sModuleName = KernelUIZZZ.getModuleUsedName((IKernelModuleZZZ)this.getModule());
//					}else {
//						this.sModuleName = KernelUIZZZ.getModuleUsedName((IPanelCascadedZZZ)this);
//					}
//				}
//				sReturn = this.sModuleName;
//			}//end main
//			return sReturn;
//		}

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
					this.sProgramAlias = KernelUIZZZ.readProgramAlias(this);
					
					//FGL 20180402: Es scheint mir sinnvoll, wenn der Alias nicht definiert ist, denn Programmnamen selbst zurückzugeben...
					if(StringZZZ.isEmpty(this.sProgramAlias)){
						this.sProgramAlias = KernelUIZZZ.getProgramUsedName(this);
					}					
				}
				sReturn = this.sProgramAlias;
			}//end main:
			return sReturn;
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
	
	public Hashtable<String,JPanel> getHashtablePanel(){
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
	public void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objPanelCascaded) {
		this.getHashtablePanel().put(sAlias, objPanelCascaded);
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IPanelCascadedZZZ#getPanelParent()
	 */
	public KernelJPanelCascadedZZZ getPanelParent() {
		return  (KernelJPanelCascadedZZZ) panelParent;
	}

	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IPanelCascadedZZZ#setPanelParent(basic.zKernelUI.component.KernelJPanelCascadedZZZ)
	 */
	public void setPanelParent(KernelJPanelCascadedZZZ objPanel) {
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
	
	//#### IComponentCascadedUserZZZ
	@Override
	public boolean createDebugUi(String sTitle) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String stemp;
					
			if(this.getFlagZ(IDebugUiZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())) {
				
								
				//20210419 Bei vielen Zeilen im Label "verwischt" dann das UI
				//Idee: Führe eine "Label-Gruppe" ein und einen Button, der diese Labels dann der Reihe nach durchschalten kann.

				//Die Anzahl der Texteinträge bestimmt die Anzahl der JLabel Objekte, bestimmt die Anzahl der Gruppen.
				//Mit einer einfachen ArrayList kann aber immer nur 1 Label pro Button definiert werden. Es muss eine Indizierte HashMap sein.
				//Teste mit mehreren Labels pro Gruppe.
				
				TODOGOON; //Die Action als eigene Klasse ausgliedern und alle beteiligten Klassen in ein passendes Package verschieben.
		                    //Den Debug/Testpanel für die Gruppenumschaltung soll dann auch diese nutzen.
				//TODOGOON; //Ein Button zum Umschalten ist auch erst im Panel notwendig, wenn es mehr als 1 Gruppenobjekt gibt.
				
				
								
				HashMapIndexedZZZ<Integer,ArrayList<JLabel>>hmLabel;
				hmLabel = PanelDebugHelperZZZ.createLabelHashMap(sTitle, this);
											
				//+++ Die Labels auf die Gruppen verteilen
				ArrayList<JComponentGroupZZZ>listaGroup = new ArrayList<JComponentGroupZZZ>();				
				int iIndex=-1;
				
				Iterator itListaLabel = hmLabel.iterator();
				while(itListaLabel.hasNext()) {
					ArrayList<JLabel>listaLabeltemp = (ArrayList<JLabel>) itListaLabel.next();
					
					iIndex=iIndex+1;						
					String sIndex = Integer.toString(iIndex);					
					JComponentGroupZZZ grouptemp = new JComponentGroupZZZ(objKernel, sIndex);
					
					boolean bAnyLabelAdded=false;
					for(JLabel labeltemp : listaLabeltemp) {
						if(labeltemp!=null) {												
							grouptemp.addComponent(labeltemp);
							bAnyLabelAdded=true;
						}
					}
					if(bAnyLabelAdded) {
						listaGroup.add(grouptemp);
					}
				}
				
				//Initiales Setzen der Sichtbarkeit
				JComponentGroupHelperZZZ.setVisible(listaGroup, 0);
					
				//Den EventBroker DER GRUPPE hinzufügen, damit darueber der Event abgefeuert werden kann
				//Merke: Dem EventBroker ist eine Reihefolge (über den Index) egal
				KernelSenderComponentGroupSwitchZZZ objEventBroker = new KernelSenderComponentGroupSwitchZZZ(objKernel);
								
				//++++++++++ Ggfs. mehrerer Gruppen zu der HashMap zusammenfassen.
				//Merke: Der Button steuert über den Index die Reihenfolge
				HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed = new HashMapIndexedZZZ<Integer,JComponentGroupZZZ>();
				for(JComponentGroupZZZ grouptemp : listaGroup) {
					if(grouptemp!=null) {
						objEventBroker.addListenerComponentGroupSwitch(grouptemp);
						hmIndexed.put(grouptemp);
					}
				}
						
				//######## Das UI gestalten. Die Reihenfolge der Componenten ist wichtig für die Reihenfolge im UI #################
				//++++ Der Umschaltebutton
				String sLabelButton = ">";//this.getKernelObject().getParameterByProgramAlias(sModule, sProgram, "LabelButton").getValue();
				JButton buttonSwitch = new JButton(sLabelButton);			
				ActionSwitch actionSwitch = new ActionSwitch(objKernel, this, hmIndexed);
				actionSwitch.setSenderUsed(objEventBroker);
				buttonSwitch.addActionListener(actionSwitch);								
				this.setComponent(KernelJPanelCascadedZZZ.sBUTTON_SWITCH, buttonSwitch);				
				this.add(buttonSwitch);
				
				int iIndexOuterMax = hmLabel.size() -1;
				for(int iIndexOuter=0; iIndexOuter <= iIndexOuterMax; iIndexOuter++) {
					ArrayList<JLabel>listaLabeltemp = (ArrayList<JLabel>) hmLabel.getValue(iIndexOuter);
					if(listaLabeltemp!=null) {
						
						//Die Labels der Arraylist abarbeiten und dem panel hinzufügen
						int iIndexInner=-1;				
						for(JLabel labeltemp : listaLabeltemp) {
							if(labeltemp!=null) {
								iIndexInner=iIndexInner+1;
								this.add(labeltemp);
								this.setComponent("LabelDebug"+iIndexOuter+"_"+iIndexInner, labeltemp);
							}
						}		
					}
				}																
			}		
		}//end main:
		return bReturn;		
	}
	
	
		//VARIANTE MIT SWING WORKER
		//######################################
		//SWITCH BUTTON GUI - Innere Klassen, welche eine Action behandelt	
		class ActionSwitch extends  KernelActionCascadedZZZ{ //KernelUseObjectZZZ implements ActionListener{
			private int iIndexCurrent = 0;
			private HashMapIndexedZZZ<Integer,JComponentGroupZZZ>hmIndexed;
			ISenderComponentGroupSwitchZZZ objEventBroker;
			
			public ActionSwitch(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent, HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed) throws ExceptionZZZ{
				super(objKernel, panelParent);
				ActionSwitchNew_(hmIndexed);
			}
			private boolean ActionSwitchNew_(HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed) throws ExceptionZZZ {
				boolean bReturn = false;
				main:{
					if(hmIndexed==null) {
						ExceptionZZZ ez = new ExceptionZZZ( "HashMapIndexedZZZ-Object missing.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;
					}
					
					this.hmIndexed = hmIndexed;
					
					bReturn = true;
				}//end main:
				return bReturn;
			}
			
			public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Performing action: 'SWITCH'");
													
				String[] saFlag = null;			
				KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.getPanelParent();
					
				//Wenn die Gruppen einmal durchgeschaltet sind, wieder am Anfang beginnen.
				int iIndexCurrent = this.getIndexCurrent();		
				int iIndexNext = iIndexCurrent+1;
				if(this.hmIndexed.size() < iIndexNext+1) {//+1 wg. Index mit Grüße vergleichen und Index beginnt immer bei 0
					iIndexNext=0;		
				}
				this.setIndexCurrent(iIndexNext);
				
				boolean bActiveState = true;
				SwingWorker4ProgramSWITCH worker = new SwingWorker4ProgramSWITCH(objKernel, panelParent, this.hmIndexed, iIndexNext, bActiveState, saFlag);
				worker.start();  //Merke: Das Setzen des Label Felds geschieht durch einen extra Thread, der mit SwingUtitlities.invokeLater(runnable) gestartet wird.
			
				return true;
			}
			
			public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
				return true;
			}
			
			public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
			}			
			
			//### Methoden, die über den reinen Button-Click hinausgehen
			public int getIndexCurrent() {
				return this.iIndexCurrent;
			}
			public void setIndexCurrent(int iIndex) {
				this.iIndexCurrent = iIndex;
			}
			
			//### Interface IEventBrokerUser
			public ISenderComponentGroupSwitchZZZ getSenderUsed() {
				return this.objEventBroker;
			}
		
			public void setSenderUsed(ISenderComponentGroupSwitchZZZ objEventSender) {
				this.objEventBroker = objEventSender;			
			}
			
			@Override
			public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
				// TODO Auto-generated method stub		
			}
			
			//#### Innere Klassen
			//##############################################################
			class SwingWorker4ProgramSWITCH extends KernelSwingWorkerZZZ{
				private KernelJPanelCascadedZZZ panel;
				private String[] saFlag4Program;	
				private String sText2Update;    //Der Wert, der ins Label geschreiben werden soll. Jier als Variable, damit die intene Runner-Klasse darauf zugreifen kann.
															// Auch: Dieser Wert wird aus dem Web ausgelesen und danach in das Label des Panels geschrieben.
								
				private HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed;
				private int iIndexUsed;
				private boolean bActiveState;
				
				public SwingWorker4ProgramSWITCH(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panel, HashMapIndexedZZZ<Integer,JComponentGroupZZZ> hmIndexed, int iIndexCurrent, boolean bActiveState, String[] saFlag4Program){
					super(objKernel);
					this.panel = panel;
					this.saFlag4Program = saFlag4Program;	
					
					this.hmIndexed = hmIndexed;
					this.iIndexUsed = iIndexCurrent;
					this.bActiveState = bActiveState;
				}
				
				//#### abstracte - Method aus SwingWorker
				public Object construct() {
					try{										
						Integer intIndex = new Integer(this.iIndexUsed);							
						//TODOGOON; ///20210424: Hier das Umschalten machen, indem man einen Event - Wirft, 
						                      //Alle am Event "registrierten" Labels/Componentent sollen dann reagieren.
						//Wird eine Gruppe aktiv geschaltet, gehören alle anderen Gruppen passiv geschaltet.
						
						//### Den Event starten,
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#EVENTEVENT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						HashMapIndexedZZZ<Integer, JComponentGroupZZZ> hmIndexed = this.hmIndexed;
						JComponentGroupZZZ group = (JComponentGroupZZZ) hmIndexed.getValue(this.iIndexUsed);
						EventComponentGroupSwitchZZZ eventNew= new EventComponentGroupSwitchZZZ(panel, 10002, group, true);				
						objEventBroker.fireEvent(eventNew);	
						
						System.out.println("#Updating Panel ...");
						KernelJPanelCascadedZZZ objPanelParent = this.panel; //.getPanelParent();
						updatePanel(objPanelParent);						
					
					}catch(ExceptionZZZ ez){
						System.out.println(ez.getDetailAllLast());
						ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());					
					}
					return "all done";
				}
			
		
				/**Aus dem Worker-Thread heraus wird ein Thread gestartet (der sich in die EventQueue von Swing einreiht.)
				 *  
				* @param stext
				* 					
				 */
				public void updatePanel(KernelJPanelCascadedZZZ panel2updateStart){
					this.panel = panel2updateStart;
					
			//		Das Schreiben des Ergebnisses wieder an den EventDispatcher thread übergeben
					Runnable runnerUpdatePanel= new Runnable(){
			
						public void run(){
			//				try {							
								
								System.out.println("SWITCH GECLICKT");
								logLineDate("SWITCH GECLICKT");//DAS IST EINE METHODE AUS KernelSwingWorkerZZZ					
														
								panel.revalidate();//.validate()
								panel.repaint();
														 							
			//				} catch (ExceptionZZZ e) {
			//					e.printStackTrace();
			//				}
						}
					};
					
					SwingUtilities.invokeLater(runnerUpdatePanel);		
					//Ggfs. nach dem Swing Worker eine Statuszeile etc. aktualisieren....
			
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
			
			} //End Class SwingWorker: SwingWorker4ProgramSWITCH	
		}//End class ...KernelActionCascaded....
		//##############################################
}
