package basic.zKernelUI.component;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zBasicUI.listener.ListenerMouseMove4DragableWindowZZZ;
import basic.zKernel.IKernelModuleUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zUtil.io.KernelFileZZZ.FLAGZ;
import custom.zKernel.LogZZZ;

/** Klasse bietet als Erweiterung zu JPanel die Verschachtelung von Panels an.
 * Merke: Ohne ein JFrame als Parent funktioniert es nicht, das Panel per Drag mit der Maus zu bewegen 
 */
public class KernelJPanelCascadedZZZ extends JPanel implements IPanelCascadedZZZ, IComponentCascadedUserZZZ, IConstantZZZ,  IKernelUserZZZ, IKernelModuleUserZZZ, IObjectZZZ, IMouseFeatureZZZ, IFlagZZZ{
	protected KernelZZZ objKernel;   //das "protected" erlaubt es hiervon erbende Klassen mit XYXErbendeKlasse.objKernel zu arbeiten.
	protected LogZZZ objLog;
	private ExceptionZZZ objException;
	private JPanel panelParent;
	private KernelJFrameCascadedZZZ frameParent;
	private KernelJDialogExtendedZZZ dialogParent;
	
	private Hashtable htPanelSub=new Hashtable();
	private Hashtable htComponent = new Hashtable();
	
	private ListenerMouseMove4DragableWindowZZZ listenerDraggableWindow = null; 

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
	
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel) throws ExceptionZZZ{
		KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass(KernelJFrameCascadedZZZ.class, (JComponent)this); //ist eh null 
		KernelJPanelCascadedNew_(objKernel, frameParent, null);		
	}
	
	/** constructor used for creating a ROOT-Panel
	* Lindhauer; 10.05.2006 06:33:12
	 * @param objKernel
	 * @param panelParent
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
		
		//20130625 Hinzugef�gt, um das Suchen des NAchbarpanels schon erm�glichen, bevor das launch() von KernelJFrameCascaded abgeschlossen ist.
		//Wird normalerweise im KErnelJFrameCascaded.lauchchDoing() gemacht, aber bei der Konfiguration der PAnels muss schon auf ein Nachbarpanel zugegriffen werden.
		frameParent.setPanelContent(this); //this: Ist das oberste Panel, wichtig wenn man aus einem Panel nach den Nachbarpanels sucht.
		frameParent.getContentPane().add(this);		
		
		KernelJPanelCascadedNew_(objKernel, frameParent, null);
	}
	
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel, JFrame frameBasic) throws ExceptionZZZ{
		frameBasic.getContentPane().add(this);
	     
		FrameCascadedRootDummyZZZ frameParent = new FrameCascadedRootDummyZZZ(objKernel, frameBasic);
		
		//20130625 Hinzugef�gt, um das Suchen des NAchbarpanels schon erm�glichen, bevor das launch() von KernelJFrameCascaded abgeschlossen ist.
		//Wird normalerweise im KErnelJFrameCascaded.lauchchDoing() gemacht, aber bei der Konfiguration der PAnels muss schon auf ein Nachbarpanel zugegriffen werden.
		frameParent.setPanelContent(this); //this: Ist das oberste Panel, wichtig wenn man aus einem Panel nach den Nachbarpanels sucht.
		//NEIN, nur DUMMY frameParent.getContentPane().add(this);		
			
		KernelJPanelCascadedNew_(objKernel, frameParent, null);
	}
	
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel, Container contentPane) throws ExceptionZZZ{
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
	
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel, KernelJDialogExtendedZZZ dialog){
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
	
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel, KernelJDialogExtendedZZZ dialog,  HashMap<String, Boolean>hmFlag) throws ExceptionZZZ{
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
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ{
				
		this.setPanelParent(panelParent);
		//Das liefert aber beim Ersstellen zjmindest nur NULL JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)this);
		//Dann eher folgendes:
		KernelJPanelCascadedZZZ panelRoot = this.searchPanelRoot();
		KernelJFrameCascadedZZZ frameParent = panelRoot.getFrameParent();
		
		KernelJPanelCascadedNew_(objKernel, frameParent, null);
	}
	
	public KernelJPanelCascadedZZZ(KernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent, HashMap<String, Boolean> hmFlag) throws ExceptionZZZ{
		
		this.setPanelParent(panelParent);
		//Das liefert aber beim Ersstellen zjmindest nur NULL JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)this);
		//Dann eher folgendes:
		KernelJPanelCascadedZZZ panelRoot = this.searchPanelRoot();
		KernelJFrameCascadedZZZ frameParent = panelRoot.getFrameParent();
		
		KernelJPanelCascadedNew_(objKernel, frameParent, hmFlag);
	}
	
	private void KernelJPanelCascadedNew_(KernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent, HashMap<String, Boolean> hmFlag ) throws ExceptionZZZ{
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
			return (KernelJPanelCascadedZZZ) this.htPanelSub.get(sAlias);			
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
			//Zuerst pr�fen, ob es sich um das Root-Panel handelt, dann kann ja ggf. das Root-Panel selbst gesucht werden.
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
			Enumeration objEnum = htPanelSub.elements();					
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
	public static boolean proofFlagZExists(String sClassName, String sFlagName){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			if(StringZZZ.isEmpty(sClassName))break main;
			try {
				
				//Existiert in der Elternklasse oder in der aktuellen Klasse das Flag?
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# ObjektInstanz erzeugen für '" + sClassName + "'");
				Class objClass = Class.forName(sClassName);		
				
				//!!! f�r abstrakte Klassen gilt: Es kann per Reflection keine neue Objektinstanz geholt werden.
				if(!ReflectClassZZZ.isAbstract(objClass)){
				
					IFlagZZZ objcp = (IFlagZZZ)objClass.newInstance();  //Aus der Objektinstanz kann dann gut die Enumeration FLAGZ ausgelesen werden.				
					if(objcp==null){
					}else{
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# ObjektInstanz f�r '" + objcp.getClass().getName() + "' erfolgreich erzeugt. Nun daraus Enum Klasse holen... .");
						bReturn = ObjectZZZ.proofFlagZExists(objcp, sFlagName);
					}
				}else{
					System.out.println("Abstrakte Klasse, weiter zur Elternklasse.");
					Class objcp2 = objClass.getSuperclass();
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
	public KernelZZZ getKernelObject() {
		return objKernel;
	}

	public void setKernelObject(KernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}
	
	//#################### Interface IKernelModuleUserZZZ
		public String getModuleName() {
			String sReturn = new String("");
			main:{
				try{
					KernelJFrameCascadedZZZ frameParent = this.getFrameParent(); //panelParent.getFrameParent();
					if(frameParent==null){
						this.getPanelParent().searchPanelRoot().getFrameParent();
					}			
					if(frameParent==null) {
						throw new ExceptionZZZ("Keine FrameParent in diesem KernelJPanelCascadedZZZ vorhanden");
					}
					
					JFrame frameRoot = frameParent.searchFrameRoot();//frameParent.getFrameParent().getClass().getName(); 
					sReturn = frameRoot.getClass().getName();
				} catch (ExceptionZZZ ez) {				
					ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());
				}
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
					this.sProgramAlias = sReturn;
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
	
	
	public JComponent getComponent(String sAlias) {
		return (JComponent) this.htComponent.get(sAlias);
	}
	

	
	
//#################### Interface IComponentCascadedUserZZZ
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IComponentCascadedZZZ#setComponent(java.lang.String, javax.swing.JComponent)
	 * 
	 * 
	 * Merke: Hier�ber wird die JComponent per Aliasname "zugriefbar". Z.B. kann nun ein anderes Panel darauf zugreifen. 
	 */
	public void setComponent(String sAlias, JComponent objComponent) {
		this.htComponent.put(sAlias, objComponent);
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
		this.htPanelSub.put(sAlias, objPanelCascaded);
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

	public boolean proofFlagZExists(String sFlagName) {
		boolean bReturn = false;
		main:{
			bReturn = ObjectZZZ.proofFlagZExists(this.getClass().getName(), sFlagName);
		
			//Schon die oberste IObjectZZZ nutzende Klasse, darum ist der Aufruf einer Elternklasse mit der Methode nicht möglich. 
			//boolean bReturn = super.proofFlagZExists(sFlagName);
		
			if(!bReturn){			   
				Class<FLAGZ> enumClass = FLAGZ.class;		
				for(Object obj : FLAGZ.class.getEnumConstants()){
					//System.out.println(obj + "; "+obj.getClass().getName());
					if(sFlagName.equalsIgnoreCase(obj.toString())) {
						bReturn = true;
						break main;
					}
				}				
			}
		}//end main:
		return bReturn;
	}
}
