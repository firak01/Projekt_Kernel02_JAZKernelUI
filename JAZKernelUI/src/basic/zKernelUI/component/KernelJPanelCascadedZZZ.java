package basic.zKernelUI.component;

import java.awt.Container;
import java.awt.Font;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
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
import basic.zKernel.component.IKernelProgramZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagUserZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zUtil.io.KernelFileZZZ.FLAGZ;
import custom.zKernel.LogZZZ;

/** Klasse bietet als Erweiterung zu JPanel die Verschachtelung von Panels an.
 * Merke: Ohne ein JFrame als Parent funktioniert es nicht, das Panel per Drag mit der Maus zu bewegen.
 * 
 *  Merke: Die Panels können sowohl nur modulnutzer als auch selber Modul sein. Darum werden beide Interfaces implementiert.
 */
public abstract class KernelJPanelCascadedZZZ extends JPanel implements IPanelCascadedZZZ, IKernelModuleZZZ, IKernelModuleUserZZZ, IKernelUserZZZ, IObjectZZZ, IMouseFeatureZZZ, IFlagUserZZZ{
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
	
	/**20130721: Umgestellt auf HashMap und die Enum-Flags, Compiler auf 1.7 ge�ndert
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
			frameParent.getContentPane().add(this);	
		}
		
		//Ggfs. die DebugUI-Angaben hinzufügen, das kann z.B. nur das Label mit dem Klassennamen sein.
		//Gesteuert werde soll das durch Flags, die auch über die Kommandozeile übergeben werden können.
		boolean bDebugUI = createDebugUI();
		
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
	
	
	//### FLAGS ####################
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - �ueer Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	//Direkt aus Object ZZZ uebernommen
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

	public boolean setFlag(String sFlagName, boolean bFlagValue) {
		try {
			return this.setFlagZ(sFlagName, bFlagValue);
		} catch (ExceptionZZZ e) {
			System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
			return false;
		}
	}
	
	//Methode aus ObjectZZZ, notwendig, weil IFlagZ implementiert ist, aber nicht von ObjectZZZ geerbt wird. 
	public static boolean proofFlagZExists(String sClassName, String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			if(StringZZZ.isEmpty(sClassName))break main;
			try {
				
				//Existiert in der Elternklasse oder in der aktuellen Klasse das Flag?
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# ObjektInstanz erzeugen für '" + sClassName + "'");
				Class objClass = Class.forName(sClassName);		
				
				bReturn = FlagZHelperZZZ.proofFlagZExists(objClass, sFlagName);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}//end main:
		return bReturn;
	}
	
	//Methode aus ObjectZZZ übernommern, wg. IFlagZ
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
	
	public boolean getFlag(String sFlagName) {
		return this.getFlagZ(sFlagName);
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

	@Override
	public HashMap<String, Boolean> getHashMapFlagZ() {
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

	@Override
	public Class getClassFlagZ() {
		return FLAGZ.class;
	}
	
	//Aus IFlagZZZ, siehe ObjectZZZ
	/**Gibt alle möglichen FlagZ Werte als Array zurück. 
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getFlagZ() throws ExceptionZZZ{
		String[] saReturn = null;
		main:{				
				Class objClass4Enum = this.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
				String sFilterName = objClass4Enum.getSimpleName();
				
				ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
				if(listEmbedded == null) break main;
				//out.format("%s# ListEmbeddedClasses.size()...%s%n", ReflectCodeZZZ.getPositionCurrent(), listEmbedded.size());
				
				ArrayList <String> listasTemp = new ArrayList<String>();
				for(Class objClass : listEmbedded){
					//out.format("%s# Class...%s%n", ReflectCodeZZZ.getPositionCurrent(), objClass.getName());
					Field[] fields = objClass.getDeclaredFields();
					for(Field field : fields){
						if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zurückgegeben.
							//out.format("%s# Field...%s%n", ReflectCodeZZZ.getPositionCurrent(), field.getName());
							listasTemp.add(field.getName());
						}				
				}//end for
			}//end for
				
			//20170307: Durch das Verschieben von FLAGZ mit den Werten DEBUG und INIT in das IObjectZZZ Interface, muss man explizit auch dort nachsehen.
		   //                Merke: Das Verschieben ist deshlab notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.
		
											
				//+++ Nun die aktuelle Klasse 
				Class<FLAGZ> enumClass = FLAGZ.class;								
				for(Object obj : FLAGZ.class.getEnumConstants()){
					//System.out.println(obj + "; "+obj.getClass().getName());
					listasTemp.add(obj.toString());
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
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
			
			ArrayList<String>listasFlagPassable=new ArrayList<String>();
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

	public boolean proofFlagZExists(String sFlagName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = FlagZHelperZZZ.proofFlagZExists(this.getClass(), sFlagName);				
		}//end main:
		return bReturn;
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
	public boolean createDebugUI() {
		boolean bReturn = false;
		main:{
			String stemp;
			
			//Ein Label hinzufuegen, in dem der Panel-Klassennamen steht (zu Debug- und Analysezwecken)
			if(this.getFlagZ(IComponentCascadedUserZZZ.FLAGZ.DEBUGUI_PANELLABEL_ON.name())) {
				//Label, das keine Konfigurierten Module zur Verfuegung stehen
				stemp = this.getClass().getSimpleName();
				JLabel labelDebug = new JLabel("Cascaded: " + stemp);
				this.add(labelDebug);
				this.setComponent("LabelDebug", labelDebug);	
			}		
		}//end main:
		return bReturn;		
	}
}
