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
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagZZZ;
import basic.zKernel.module.IKernelModuleZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zUtil.io.KernelFileZZZ.FLAGZ;
import custom.zKernel.LogZZZ;

/** Klasse bietet als Erweiterung zu JPanel die Verschachtelung von Panels an.
 * Merke: Ohne ein JFrame als Parent funktioniert es nicht, das Panel per Drag mit der Maus zu bewegen 
 */
public class KernelJPanelCascadedZZZ extends JPanel implements IPanelCascadedZZZ, IComponentCascadedUserZZZ, IConstantZZZ,  IKernelModuleZZZ, IObjectZZZ, IMouseFeatureZZZ, IFlagZZZ{
	protected IKernelZZZ objKernel;   //das "protected" erlaubt es hiervon erbende Klassen mit XYXErbendeKlasse.objKernel zu arbeiten.
	protected LogZZZ objLog;
	private ExceptionZZZ objException;
	private JPanel panelParent;
	private KernelJFrameCascadedZZZ frameParent;
	private KernelJDialogExtendedZZZ dialogParent;
	
	private Hashtable<String,JPanel> htPanelSub=new Hashtable<String,JPanel>();
	private Hashtable<String,JComponent> htComponent = new Hashtable<String,JComponent>();
	private Hashtable <String, KernelButtonGroupZZZ<String, AbstractButton>> htButtonGroup = new Hashtable<String, KernelButtonGroupZZZ<String, AbstractButton>>();
	
	private ListenerMouseMove4DragableWindowZZZ listenerDraggableWindow = null; 

	private String sModuleName = null;
	private String sProgramName  = null; //ggf. der Name des Elternprogramms, s. KernelKonfiguration
	private String sProgramAlias = null; //ggf. der Alias des Elternprogramms, s. KernelKonfiguration
	/*private boolean flagComponentKernelProgram = false; // 2013-07-08: Damit wird gesagt, dass f�r dieses Panel ein "Program-Abschnitt" in der Kernel - Konfigurations .ini - Datei vorhanden ist.
	                                                    //             Bei der Suche nach Parametern wird von der aktuellen Komponente weiter "nach oben" durchgegangen und der Parameter f�r jede Programkomponente gesucht.

	private boolean flagComponentDraggable = true;
	private boolean bFlagDebug = false;
	private boolean bFlagInit = false;
	private boolean bFlagTerminate = false;*/
	private HashMap<String, Boolean>hmFlag=new HashMap<String, Boolean>();
	/**20130721: Umgestellt auf HashMap und die Enum-Flags, Compiler auf 1.7 ge�ndert
	 * 
	 */
	public enum FLAGZ{
		COMPONENT_DRAGGABLE, TERMINATE, COMPONENT_KERNEL_PROGRAM;
	}
	
	
	/**
	 * DEFAULT Konstruktor, notwendig, damit man objClass.newInstance(); einfach machen kann.
	 *                                 
	 * lindhaueradmin, 23.07.2013
	 */
	public KernelJPanelCascadedZZZ(){// throws ExceptionZZZ{
		//JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)this);
	//20130723 raus:	KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass((KernelJFrameCascadedZZZ.class), (JComponent) this);
		//20130723 raus:		KernelJPanelCascadedNew_(null, frameParent, null);
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)this); //ist eh null 
		KernelJPanelCascadedNew_(objKernel, frameParent, null);		
	}
	
	/** constructor used for creating a ROOT-Panel
	* Lindhauer; 10.05.2006 06:33:12
	 * @param objKernel
	 * @param panelParent
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
		
		//20130625 Hinzugef�gt, um das Suchen des NAchbarpanels schon erm�glichen, bevor das launch() von KernelJFrameCascaded abgeschlossen ist.
		//Wird normalerweise im KErnelJFrameCascaded.lauchchDoing() gemacht, aber bei der Konfiguration der PAnels muss schon auf ein Nachbarpanel zugegriffen werden.
		frameParent.setPanelContent(this); //this: Ist das oberste Panel, wichtig wenn man aus einem Panel nach den Nachbarpanels sucht.
		frameParent.getContentPane().add(this);		
		
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
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog){
		//Das Panel f�gt sich dem Dialog selbst hinzu
		//Container objContainer = dialog.getContentPane();
		//NEIN objContainer.add(this);
		
		//Damit auf die Hashtable der Dialogbox zugegriffen werden kann, um andere Panels zu erreichen
		this.setDialogParent(dialog);
		
		//Damit dieses Panel zur Verf�gung steht um "mit der Maus bewegt" zu werden.
		Container objContainer = dialog.getContentPane();
		//JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)objContainer);
		KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent) objContainer);
		
		//NEIN, sonst wird der ganze frameParent beim Ziehen des DialogboxPanels mit bewegt KernelJPanelCascadedNew_(objKernel, frameParent);
		if(objKernel!= null){
			this.setKernelObject(objKernel);
			this.setLogObject(objKernel.getLogObject());
		}		
		this.setFrameParent(frameParent);		
	}
	
	public KernelJPanelCascadedZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialog,  HashMap<String, Boolean>hmFlag) throws ExceptionZZZ{
		this(objKernel, dialog);
		
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				this.setFlagZ(sKey, hmFlag.get(sKey));
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
	
	private void KernelJPanelCascadedNew_(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent, HashMap<String, Boolean> hmFlag ) throws ExceptionZZZ{
		if(objKernel!= null){
			this.objKernel = objKernel;
			this.objLog = objKernel.getLogObject();
		}
		
		this.setFrameParent(frameParent);
		
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				this.setFlagZ(sKey, hmFlag.get(sKey));
			}
		}
		
		
		//Einen Mouse Listener hinzuf�gen, der es erlaubt Fenster zu ziehen (auch im Panel und nicht nur in der Titelleiste)
		//if(this.getFlag("isdraggable")){
		if(this.getFlagZ(FLAGZ.COMPONENT_DRAGGABLE.name())){	
			
			this.setJComponentContentDraggable(this.isJComponentContentDraggable());
		
			/*
				ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)this, frameParent);
				this.addMouseListener(mml);
				this.addMouseMotionListener(mml);
			*/
		}
		
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
			//if(this.panelParent==null){
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
	/* (non-Javadoc)
	 * 
	 * DIESE METHODE BRAUCHT IN DEN ANDEREN KLASSE NICHT VORHANDEN ZU SEIN, DIE IHRE FLAGS SETZEN WOLLEN
	 * RUFT PER METHOD.INVOKE die Methode .proofFlagZExists(sClassName, sFlag, bValue) auf.
	 * 
	 * @see basic.zBasic.IFlagZZZ#setFlagZ(java.lang.String, boolean)
	 */
	/*
	public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sFlagName == null) break main;
			if(sFlagName.equals("")) break main;
			try{				
				String stemp = "Methode " + ReflectCodeZZZ.getMethodCurrentName() + ": f�r Flag '" + sFlagName + "'";
				System.out.println(stemp);
				this.getLogObject().WriteLineDate(stemp);
			
			//1. Hole alle SuperKlassen
			Class objClassStart = this.getClass();
			Class[] objaClass = Mopex.getSuperclasses( objClassStart );
		    if(objaClass==null){		    	
		    }else if(objaClass.length<=0){
		    }else{
		    	//2. Pr�fe die Klassen, ob sie IFlagZZZ implementieren.
				ArrayList<Class> listaClassImplementing = new ArrayList<Class>();
				for(Class objClass : objaClass ){
					if(ReflectClassZZZ.isImplementing(objClass, IFlagZZZ.class)){
						stemp = objClassStart.getSimpleName() + "." + ReflectCodeZZZ.getMethodCurrentName() + ": Gefundene implementierende Klasse = " + objClass.getSimpleName();
						System.out.println(stemp);
						this.getLogObject().WriteLineDate(stemp);
						//if(!objClass.getName().equalsIgnoreCase(this.getClass().getName())) listaClassImplementing.add(objClass); //Ohne des Abfangens der Gleichheit... fehler!!!
						listaClassImplementing.add(objClass);
					}
				}
				
				//3. Gehe die Liste durch und rufe f�r die Klassen die .setFlagZ-Methode auf.
			    for(Class<IFlagZZZ> objClass : listaClassImplementing){
			    	//!!! Es muss die .setFlagZ Methode der Klasse aufgerufen werden. Nur so bekommt man den Zugriff auf die Enumeration FLAGZ
			    	//3.1 Die Methode holen, per Reflection API
			    	System.out.println("Methode " + ReflectCodeZZZ.getMethodCurrentName() + "# Klassenobjekt, das IFlagZZZ implementiert: '" + objClass.getName() + "'");
					Class[] clsaParam = new Class[2];
					clsaParam[0]=String.class;//IFlagZZZ.class;
					clsaParam[1]=String.class;
					
					boolean bFunction = false;
					try {
						Method m = Mopex.getSupportedMethod(objClass, "proofFlagZExists", clsaParam);
						if(m!=null){
							Object[] objaParam = new Object[2];
							
							objaParam[0] = objClass.getName();//Class.forName(objClass.getName());//wo ist dabei der sinn... funktioniert zwar, aber .... this;
							objaParam[1] = sFlagName;
							Object objReturnType = m.invoke(this, objaParam);
							if(objReturnType!=null){
								bFunction = ((Boolean)objReturnType).booleanValue();
							}
						}	
						if(bFunction == true){
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# FLAGZ erfolgreich gefunden durch method.invoke(...).");
							
							//Setze das Flag nun in die HashMap
							HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
							hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
							
							bReturn = true; //Wurde das Flag in der Elternklasse gesetzt, dann braucht es niergendwo weiter gesetzt werden.							
						}else{
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# FLAGZ (noch) NICHT gefunden durch method.invoke(...).");
							
							//Existiert das Flag in dieser Klasse?
							bFunction = this.proofFlagZExists(sFlagName);
							if(bFunction == true){
								System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# FLAGZ erfolgreich gefunden, direkt in der Klasse.");
								
								//Setze das Flag nun in die HashMap
								HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
								hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
								
								bReturn = true;
							}else{
								System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# FLAGZ auch direkt in der Klasse nicht gefunden.");
								
								bReturn = false;
							}
						}
					} catch (NoSuchMethodException e) {
						//Eine Klasse, die das Interface Implementiert, braucht selbst diese Methoden (.getFlagZ(...) / .setFlagZ(...) NICHT zu besitzen. Das Kann auch eine Elternklasse tun.
						//Merke: Will die Klasse ABER eigene FLAGZ setzen, dann muss sie die Methoden selbst implementieren.
						System.out.println("NoSuchMethodException: In der Klasse '" + objClass.getName() + "' ist die Methode setFlagZ(cls,sFlag,bValue) nicht vorhanden. So k�nnen keine eigenen Flags gesetzt werden.");
					} catch (InvocationTargetException e) {
						//siehe oben, dito...
						System.out.println("InvocationTargetException: In der Klasse '" + objClass.getName() + "' ist die Methode setFlagZ(cls,sFlag,bValue) fehlerhaft aufgerufen worden. So k�nnen keine eigenen Flags gesetzt werden.\n"+e.toString());
					} 
			    }//end for				
		    }//end if objaClass==null

			
			}catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	// end main:
		
		return bReturn;	
	}
	*/
	
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - �ber Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	//Direkt aus Object ZZZ �bernommen
	public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			

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
	
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN ZU SEIN, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(...) aufgerufen von .setFlagZ(...)
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	/* 20170123 Teste, ob die aus ObjectZZZ entlehnet Methode nicht besser ist.
	public boolean proofFlagZExists(String sParentClassName, String sFlagName){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			if(StringZZZ.isEmpty(sParentClassName))break main;
			
			try {
	
			//Existiert in der Elternklasse oder in der aktuellen Klasse das Flag?
			Class objClassParent;
			objClassParent = Class.forName(sParentClassName);	
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# Parentklasse ist: '" + objClassParent.getClass().getName() + "' erzeuge hierzu KEINE neue Instanz.");
			if(objClassParent.getClass().getName().equals("java.lang.Class")){
				bReturn = false;
				break main;
			}
			
			//Nur von anderen Klassen versuchen eine neue Instanz zu machen.
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# Parentklasse ist: '" + objClassParent.getClass().getName() + "' erzeuge hierzu eine neue Instanz.");	
			
			//!!! f�r abstrakte Klassen gilt: Es kann per Reflection keine neue Objektinstanz geholt werden.
			if(!ReflectClassZZZ.isAbstract(objClassParent)){
			
				//Immer Fehler beim Instatiieren: ObjectZZZ objcp = (ObjectZZZ)objClassParent.newInstance();
				IFlagZZZ objcp = (IFlagZZZ)objClassParent.newInstance(); //darum auf cast von IFlagZZZ ausgewichen.
				if(objcp==null){
				}else{
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# ObjektInstanz f�r '" + objcp.getClass().getName() + "' erfolgreich erzeugt. Nun Enum Klasse holen... .");
					bReturn = ObjectZZZ.proofFlagZExists(objcp, sFlagName);
				}
			
			}else{
				System.out.println("Abstrakte Klasse, weiter zur Elternklasse.");
				Class objcp2 = objClassParent.getSuperclass();
				if(objcp2!=null){
					bReturn = ObjectZZZ.proofFlagZExists(objcp2.getName(), sFlagName);
				}
			}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end main:
		return bReturn;
	}
	*/
	
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
	
	/**
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 06.07.2013
	 */
	/*
	public boolean getFlag(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(sFlagName == null) break main;
			if(sFlagName.equals("")) break main;
			
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# Klasse getFlag 1");
			
			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
			// bFunction = super.getFlag(sFlagName);
			// if(bFunction == true) break main;
			
			//1. pr�fe, ob dieses Flag existiert. (Neu 20130721)
			boolean bFlagExists = ObjectZZZ.proofFlagExists(getClassFlag(), sFlagName);
			if(!bFlagExists){
				//ExceptionZZZ ez = new ExceptionZZZ("Flag does not exist. '" + sFlagName + "'", iERROR_PARAMETER_VALUE,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				//throw ez;
				bFunction = false;
				break main;
			}
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

	/**
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 06.07.2013
	 */
		/*
	public boolean setFlag(String sFlagName, boolean bFlagValue) {
		boolean bFunction = false;
		main:{
			if(sFlagName == null) break main;
			if(sFlagName.equals("")) break main;
			
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# Klasse setFlag 1");
			
			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
			// bFunction = super.setFlag(sFlagName, bFlagValue);
			// if(bFunction == true) break main;
			
			
			//Ab�nderung.... OHNE SUPERKLASS
			//boolean bFlagExists = ObjectZZZ.proofFlagExists(super.getClassFlag(), sFlagName);
			//if(!bFlagExists){
				//ExceptionZZZ ez = new ExceptionZZZ("Flag does not exist. '" + sFlagName + "'", iERROR_PARAMETER_VALUE,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				//throw ez;
				boolean bFlagExists = ObjectZZZ.proofFlagExists(this.getClassFlag(), sFlagName);
				if(!bFlagExists){
				bFunction = false;
				break main;
				}
			//}
			
			if(bFlagExists){
			this.getHashMapFlag().put(sFlagName.toUpperCase(), bFlagValue);
			bFunction = true;	
			}
		}	// end main:
		
		return bFunction;	
	}
	*/
	
	/** Erm�glicht den ZKernel-Objekten eine gewisse Reflektion.
	 * Hier wird gepr�ft, ob das Flag �berhaupt vorhanden ist.
	 * Entweder in der eigenen Klasse oder auch in der Super-Klasse.
	 */
	/*
	public static final boolean proofFlagExists(Class objClassWithEnum, String sFlagName) {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			if(objClassWithEnum==null)break main;
			try {
				//List<FLAG> list = Arrays.asList(FLAG.class.getEnumConstants());
				List<Object> list = Arrays.asList(objClassWithEnum.getEnumConstants());
				for(Object obj : list){
					//out.format("Enumg %s%n", obj.getClass().getName());
					Field[] fields = obj.getClass().getDeclaredFields();
					for(Field field : fields){
						if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zur�ckgegeben.
							//out.format("...%s%n", field.getName());
							if(sFlagName.equalsIgnoreCase(field.getName())){
								bReturn = true;
								break main;
							}
						}
					}//end for
				}//end for
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}//End main:
		return bReturn;
	}
	*/

	//#################### Interface IKernelUserZZZ
	public IKernelZZZ getKernelObject() {
		return objKernel;
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
	
	//#################### Interface IKernelModuleUserZZZ
		public String getModuleName() throws ExceptionZZZ {
			String sReturn = new String("");
			main:{	
				if(StringZZZ.isEmpty(this.sModuleName))
				sReturn = KernelUIZZZ.getModule(this);
				this.sModuleName = sReturn;
			}//end main
			return sReturn;
		}

		/* (non-Javadoc)
		 * @see basic.zKernel.IKernelModuleUserZZZ#getProgramName()
		 */
		public String getProgramName() throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(StringZZZ.isEmpty(this.sProgramName)){
					sReturn = KernelUIZZZ.getProgramName(this);
					this.sProgramName = sReturn;
				}else{
					sReturn = this.sProgramName;
				}
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
					sReturn = KernelUIZZZ.readProgramAlias(this);
					
					//FGL 20180402: Es scheint mir sinnvoll, wenn der Alias nicht definiert ist, denn Programmnamen selbst zurückzugeben...
					if(StringZZZ.isEmpty(this.sProgramAlias)){
						sReturn = KernelUIZZZ.getProgramName(this);
					}
					this.sProgramAlias = sReturn;
				}else{
					sReturn = this.sProgramAlias;
				}
			}
			return sReturn;
		}


	//#################### Interface IObjectZZZ
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}

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
		//this.flagComponentDraggable=bValue;
		try {
			this.setFlagZ(FLAGZ.COMPONENT_DRAGGABLE.name(), bValue);
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
	public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZZZ objUsingFlagZ) throws ExceptionZZZ{
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
	public String[] getFlagZ_passable(IFlagZZZ objUsingFlagZ) throws ExceptionZZZ{
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
}
