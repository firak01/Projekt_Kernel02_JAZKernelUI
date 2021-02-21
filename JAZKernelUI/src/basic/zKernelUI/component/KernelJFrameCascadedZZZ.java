package basic.zKernelUI.component;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zBasicUI.adapter.AdapterJComponent4ScreenSnapperZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagZZZ;
import basic.zKernel.flag.IFlagZZZ.FLAGZ;
import basic.zKernelUI.KernelUIZZZ;
import custom.zKernel.LogZZZ;

/** Class is base for all frames used by the configuration module
 * @author Lindhauer
 */
public abstract class KernelJFrameCascadedZZZ extends JFrame  implements IObjectZZZ, IFlagZZZ, IKernelUserZZZ, IKernelModuleZZZ, IComponentCascadedUserZZZ, IFrameCascadedZZZ, IFrameLaunchableZZZ, IScreenFeatureZZZ{
	private IKernelZZZ objKernel;
	private LogZZZ objLog; 
	private KernelJFrameCascadedZZZ frameParent=null;
	private JFrame frameBasic = null;  //Falls diese Klasse aus einem normalen JFrame erstellt werden soll.
	private Hashtable objHtFrameSub=new Hashtable();   //Damit kann man auf Frames zugreifen, die von diesem Frame aus gestartet wurden.
	private Hashtable objHtPanelSub=new Hashtable();     //Eigentlich enth�lt das hier nur ein Panel
	private Hashtable objHtComponent = new Hashtable(); //Soll Komponenenten, wie z.B. ein Textfield per "Alias" greifbar machen.
	 
	private ExceptionZZZ objException;
	
	private boolean bLaunchedBefore = false;
	
//	private boolean flagComponentKernelProgram = false; // 2013-07-08: Damit wird gesagt, dass f�r dieses Panel ein "Program-Abschnitt" in der Kernel - Konfigurations .ini - Datei vorhanden ist.
    //             Bei der Suche nach Parametern wird von der aktuellen Komponente weiter "nach oben" durchgegangen und der Parameter f�r jede Programkomponente gesucht.
//private boolean flagComponentDraggable = true;
//private boolean bFlagDebug = false;
//private boolean bFlagInit = false;
//private boolean bFlagTerminate = false;

public enum FLAGZ{
	COMPONENT_DRAGGABLE,TERMINATE; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
}

private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721 ersetzt die einzelnen Flags, irgendwann...
	
	public KernelJFrameCascadedZZZ(){
		super();
		KernelJFrameCascadedNew_();
	}
	
	/** Use this Constructor for the Basic Frame, which is a JFrame. So there is no parent frame.
	* lindhaueradmin; 13.03.2007 09:35:43
	 * @param objKernel
	 * @param frameBasic
	 */
	public KernelJFrameCascadedZZZ(IKernelZZZ objKernel, JFrame frameBasic){
		super();
		this.setKernelObject(objKernel);  //Merke: Diese Klasse erweiter JFrame und nicht KernelUseObjectZZZ
		this.setLogObject(objKernel.getLogObject());
		
	    if(frameBasic!=null) this.setFrameBasic(frameBasic);
	    
		this.setFrameParent(this); //!!! damit umgeht man das Problem, das dies null ist		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Merke: weil Basic-Frame.
	}
	 
	/**Use this Construktor for the Basic Frame, which has no parent.
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	public KernelJFrameCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
		this.setKernelObject(objKernel);  //Merke: Diese Klasse erweiter JFrame und nicht KernelUseObjectZZZ
		this.setLogObject(objKernel.getLogObject());
		
		this.setFrameParent(this); //!!! damit umgeht man das Problem, das dies null ist		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Merke: weil Basic-Frame.
	}
	
	/** use this constructor for a frame which has a parent frame.
	 * @param objKernel
	 * @param frameParent, if null => this will be handled as a basic frame.
	 * @throws ExceptionZZZ
	 */
	public KernelJFrameCascadedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
		super();
		
		this.setKernelObject(objKernel);   //Merke: Diese Klasse erweiter JFrame und nicht KernelUseObjectZZZ
		this.setLogObject(objKernel.getLogObject());
		
		if(frameParent!=null){
			this.setFrameParent(frameParent);
			
			
			//Nicht Exit_On_Close verwenden, denn sonst werden alle Fenster beendet
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}else{
			this.setFrameParent(this); //!!! damit umgeht man das Problem, das dies null ist
						
			//Damit dieses eine Fenster beendet wird und auch alle darunter ge�ffneten
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		setSizeDefault();
		
		KernelJFrameCascadedNew_();
	}
	
	private void KernelJFrameCascadedNew_(){
		
		if(this.isJComponentSnappedToScreen()){
			AdapterJComponent4ScreenSnapperZZZ snapAdapter = new AdapterJComponent4ScreenSnapperZZZ();
			this.addComponentListener(snapAdapter);
		}		
	}
	
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	@Override
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
	//### Den Font über alle vorhandenen Einträge einer Menubar ändern
	public int updateMenuBarFontAll(Font font) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			if(font==null) break main;
			
			//Die Menüeinträge hinsichtlich des Fonts ändern, alle über den UIManager.
			//siehe https://stackoverflow.com/questions/27318130/changing-a-jmenubars-font
			UIManager.put("MenuBar.font", font);
			UIManager.put("Menu.font", font);
			UIManager.put("MenuItem.font", font);
			//panel.getFrameParent().getMenuContent().repaint(); //KLAPPT ABER NICHT... statt dessen SwingUtilities.updateComponentTreeUI							
																							
			JMenuBar menubar = this.getMenuContent();
			Component[] menuComponents = (Component[]) menubar.getComponents();
			for(Component menuComponent : menuComponents){
				menuComponent.setFont(font);
				JMenu menu = (JMenu) menuComponent;
				Component[] menuItemComponents = menu.getMenuComponents();
				for(Component menuItemComponent : menuItemComponents){
					menuItemComponent.setFont(font);
//					JMenuItem menuItem = (JMenuItem) menuItemComponent;
//					System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": MenuItem.getText() = '" + menuItem.getText() + "'");
				}
			}
						
			//Noch hinzunehmen. siehe: https://stackoverflow.com/questions/38383694/update-jmenu-and-jmenu-font-after-jmenubar-is-visible
			menubar.revalidate();
			SwingUtilities.updateComponentTreeUI(menubar);
				
		}//end main:
		return iReturn;
	}
	
	
	//### FlagMethods ##########################		
	
	@Override
	public Class getClassFlagZ() {		
		return FLAGZ.class;
	}

	public HashMap<String, Boolean>getHashMapFlagZ(){
		return this.hmFlag;
	} 
	
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.(non-Javadoc)
	 */
	@Override
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
	
	
	/**
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 06.07.2013
	 */
	public boolean getFlag(String sFlagName) {
//		boolean bFunction = false;
//		main:{
//			if(sFlagName == null) break main;
//			if(sFlagName.equals("")) break main;
//			
//			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//			// bFunction = super.getFlag(sFlagName);
//			// if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("debug")){
//				bFunction = this.bFlagDebug;
//				break main;
//			}else if(stemp.equals("init")){
//				bFunction = this.bFlagInit;
//				break main;
//			}else if(stemp.equals("terminate")){
//				bFunction = this.bFlagTerminate;
//			}else if(stemp.equals("isdraggable")){
//				bFunction = this.flagComponentDraggable;
//			}else if(stemp.equals("iskernelprogram")){
//				bFunction = this.flagComponentKernelProgram;
//			}else{
//				bFunction = false;
//			}		
//		}	// end main:
//		
//		return bFunction;
		return this.getFlagZ(sFlagName);
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
	@Override
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
	
	/**
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 06.07.2013
	 * @throws ExceptionZZZ 
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue)  {
//		boolean bFunction = true;
//		main:{
//			if(sFlagName == null) break main;
//			if(sFlagName.equals("")) break main;
//			
//			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//			// bFunction = super.setFlag(sFlagName, bFlagValue);
//			// if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("debug")){
//				this.bFlagDebug = bFlagValue;
//				bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//				break main;
//			}else if(stemp.equals("init")){
//				this.bFlagInit = bFlagValue;
//				bFunction = true;
//				break main;
//			}else if(stemp.equals("terminate")){
//				this.bFlagTerminate = bFlagValue;
//				bFunction = true;
//				break main;
//			}else if(stemp.equals("isdraggabel")){
//				this.flagComponentDraggable = bFlagValue;
//				bFunction = true;
//				break main;
//			}else if(stemp.equals("iskernelprogram")){
//				this.flagComponentKernelProgram = bFlagValue;
//				bFunction = true;
//				break main;
//			}else{
//				bFunction = false;
//			}	
//			
//		}	// end main:
//		
//		return bFunction;
		try{
			return this.setFlagZ(sFlagName, bFlagValue);
		} catch (ExceptionZZZ e) {
			System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return objException;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}//end function

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
	
	@Override
	public boolean proofFlagZExists(String sFlagName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = FlagZHelperZZZ.proofFlagZExists(this.getClass(), sFlagName);
		}//end main:
		return bReturn;							
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
	
	public abstract boolean setSizeDefault() throws ExceptionZZZ;
	
	
    /**Centers the dialog based on its parent.
     * @return boolean
     *
     * javadoc created by: 0823, 05.01.2007 - 13:49:58
     */
    public boolean centerOnParent() {
    	boolean bReturn = false;
    	main:{	       
    		JFrame frameParent = this.getFrameParent();
    		if(frameParent == null) break main;
    		
    		 //Point parentFrameLocation = this.getParent().getLocation();
    		Point parentFrameLocation = frameParent.getLocation();    		
	        Dimension parentFrameSize = frameParent.getSize();
	        
	        Dimension thisDialogSize = this.getSize();
	
	        // Calculate locations
	        int xLocation = parentFrameLocation.x + parentFrameSize.width / 2 - thisDialogSize.width / 2;
	        int yLocation = parentFrameLocation.y + parentFrameSize.height / 2 - thisDialogSize.height / 2;
	
	        // Do not set location outside of screen
	        if (xLocation < 0) xLocation = 15;
	        if (yLocation < 0) yLocation = 15;
	
	        // Do not cover parent frame
	        if (xLocation <= parentFrameLocation.x + 15) xLocation = parentFrameLocation.x + 15;
	        if (yLocation <= parentFrameLocation.y + 15) yLocation = parentFrameLocation.y + 15;
	
	        this.setLocation(xLocation, yLocation);
	        bReturn = true;
    	}
    	return bReturn;
        
    }
    
    //##### aus IKernelModuleZZZ
    @Override
	public String getModuleName() throws ExceptionZZZ {
		return KernelUIZZZ.getModuleUsedName((IFrameCascadedZZZ)this);
	}
    
	//#######################################
	//### Getter / Setter
	public boolean isLaunched(){
		return this.bLaunchedBefore;
	}
	
	
	//#######################################
	//Methods implemented by Interface
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
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

	public KernelJPanelCascadedZZZ getPanelSub(String sAlias) {		
		return (KernelJPanelCascadedZZZ) this.objHtPanelSub.get(sAlias);
	}
	public void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objPanel) {
		this.objHtPanelSub.put(sAlias, objPanel);
	}
	public Hashtable getPanelSubAll(){
		return this.objHtPanelSub;
	}
	
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IFrameLaunchableZZZ#getPanelContent()
	 */
	public KernelJPanelCascadedZZZ getPaneContent() throws ExceptionZZZ{
		return (KernelJPanelCascadedZZZ) this.objHtPanelSub.get(KernelJFrameCascadedZZZ.getAliasPanelContent());
	}
	public void setPanelContent(KernelJPanelCascadedZZZ objPanelRoot){
		this.objHtPanelSub.put(KernelJFrameCascadedZZZ.getAliasPanelContent(), objPanelRoot);
	}

	public JComponent getComponent(String sAlias) {
		return (JComponent) this.objHtComponent.get(sAlias);
	}
	public void setComponent(String sAlias, JComponent objComponent) {
		this.objHtComponent.put(sAlias, objComponent);
	}
	
	public JFrame getFrameBasic(){
		if(this.frameBasic!= null){
			return this.frameBasic;
		}else{
			return (JFrame) this.getFrameParent();
		}
	}
	public void setFrameBasic(JFrame frameBasic){
		this.frameBasic = frameBasic;
	}
	

	public KernelJFrameCascadedZZZ getFrameParent(){
		return this.frameParent;  
	}
	public void setFrameParent(KernelJFrameCascadedZZZ frameParent){
		this.frameParent = frameParent;
	}
	
	public JFrame getFrameSub(String sAlias){
	return (JFrame) this.objHtFrameSub.get(sAlias);
}
	public void setFrameSub(String sAlias, JFrame objFrame){
	this.objHtFrameSub.put(sAlias, objFrame);
}
	
	/**Hole den Ausgangsframe.
	 * Merke: Der Ausgangsframe bekommt - im Konstruktor - sich selbst als ParentFrame zugewiesen, weil es im Konstruktor den ParentFrame nicht gibt. !!!
	* @return
	* 
	* lindhaueradmin; 02.03.2007 10:42:31
	 */
	public JFrame searchFrameRoot(){
		JFrame frameReturn = null;
		main:{
			KernelJFrameCascadedZZZ frameParent = this;
			frameReturn = (JFrame) frameParent;
			do{
				frameParent = frameParent.getFrameParent();				
				if(frameParent != null){
					if(frameParent.equals(frameReturn)) break main;
					frameReturn = (JFrame)frameParent; 							
				}
			}while (frameParent != null);			
		}
		return frameReturn;
	}

	public final boolean launch(String sTitle) throws ExceptionZZZ {		
		boolean bReturn = false;
		main:{
			if(this.frameParent==null){
				RunnerFrameMainZZZ runnerFrame = new RunnerFrameMainZZZ(this, sTitle, this.bLaunchedBefore);
				SwingUtilities.invokeLater(runnerFrame); //Damit also die Erzeugung des Frames in den EventDispatchThread stellen. Merke: Das macht nur Sinn, wenn in launchCustom() weiterer zeitaufwendiger Code ausgef�hrt wird.
				bReturn = true;
			}else{
				//!!! Hier wird das gewünschte ContentPanel eingebaut !!!
				KernelJFrameCascadedZZZ.launchDoing(this, sTitle, this.bLaunchedBefore);
				bReturn = true;
				
				bReturn = this.launchCustom();
				if(bReturn){
					//2013-07-09 die Größe des Frames soll ggf. nicht von den Komponenten gesteuert werden
					//... mache also nix, wenn launchCustom()=true
				}else{					
					this.pack();
				}
			
				this.bLaunchedBefore = true;
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Frame launched");		
				
//				... sichtbar machen erst, nachdem alle Elemente im Frame hinzugefügt wurden !!!
				//depreciated in 1.5 frame.show();
				//statt dessen...
				this.setVisible(true); //Meke: Trotz alledem wird das Fenster erst komplett angezeigt, wenn der code in launchCustom() beendet ist....															
			}											
		}//END main:
		return bReturn;
		//!!! Diese Mehthode muss dann durch die daraus erbenden Klassen erweitert werden. Dort sollte dann der Returnwert auch auf true gestzt werden. 
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IFramLaunchableZZZ#launch()
	 */
	public final boolean launch() throws ExceptionZZZ {
		return launch("");
	}
	
	
	/** Startet die Konfigurierten Frames.
	 *   Das wird entweder direkt von launch(...) aufgerufen
	 *   oder indierekt durch RunnerFrameCascadedZZZ. Letzteres macht nur Sinn, wenn es sich um den "Hauptframe" handelt.
	* @return
	* 
	* lindhaueradmin; 15.01.2007 11:05:16
	 * @throws ExceptionZZZ 
	 */
	private static boolean launchDoing(KernelJFrameCascadedZZZ frmCascaded, String sTitle, boolean bLaunchedBefore) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(bLaunchedBefore==true){
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Setting visible again the frame '" + sTitle + "' (" + ReflectCodeZZZ.getMethodCurrentName() + ").");
				//System.out.println("Setting visible again the frame '" + this.sTitle + "' (" + ReflectionZZZ.getMethodCurrentName() + ").");
				frmCascaded.setVisible(true);
			}else{
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Launching the frame '" + sTitle + "' (" + ReflectCodeZZZ.getMethodCurrentName() + ").");
				//System.out.println("Launching the frame '" + this.sTitle + "' (" + ReflectionZZZ.getMethodCurrentName() + ").");
				
			    //Aus Notes heraus gestartet funktioniert EXIT_ON_CLOSE nicht
				//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				//TODO GOON, Falls dies nicht als unterframe aufgerufen wird:    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				//Die Grösse des Frames, die Methode wird vom KernelFrame zur Verf�gung gestellt 
				frmCascaded.setSizeDefault();
				frmCascaded.setTitle(sTitle);
				
				//Grundfläche(n) in den Rahmen hinzufügen...
				//	... das wird nun �ber das ContentPane der Frames gemacht. !!! Damit diese Grundfl�che "draggable" ist, muss zumindest der ContentPane �bergeben werden.
				KernelJPanelCascadedZZZ objPanel = frmCascaded.getPaneContent();  //default ist der ContentPane-Alias
				if(objPanel !=null){
					if(!frmCascaded.getContentPane().equals(objPanel))frmCascaded.getContentPane().add(objPanel);
					frmCascaded.setPanelSub(KernelJFrameCascadedZZZ.getAliasPanelContent(), objPanel);
				}
				
				//FGL 20080912: Ggf. ein weiteres Panel hinzufügen
				KernelJPanelCascadedZZZ objPanelContent = (KernelJPanelCascadedZZZ) frmCascaded.getPaneContent(KernelJFrameCascadedZZZ.getAliasPanelContent() + "Sub");
				if(objPanelContent != null){
					if (objPanel != null){
						objPanel.setOpaque(false);
						if(!objPanel.equals(objPanelContent))objPanel.add(objPanelContent);
						objPanel.setPanelSub(KernelJFrameCascadedZZZ.getAliasPanelContent()+"Sub", objPanelContent);
					}else{
						if(!frmCascaded.getContentPane().equals(objPanelContent))frmCascaded.getContentPane().add(objPanelContent);
						frmCascaded.setPanelSub(KernelJFrameCascadedZZZ.getAliasPanelContent()+"Sub", objPanelContent);
					}
				}
				
				//FGL 20080912: Ggf. einen LayeredPane hinzufügen
				//Merke: Das LayeredPane muss entweder einem Panel hinzugefügt werden oder (falls nicht vorhanden) direkt dem JFrame
				//Merke: Die LayoutManager der JPanels, m�ssen das Hinzuf�gen einer weiteren Komponente aber auch unterstützen
				//Merke: Zu dem LayeredPane gibt es ein Beispiel im TryOutSwing-Projekt
				JLayeredPane objPaneLayered = (JLayeredPane) frmCascaded.getPaneContent("LayeredPane");
				if(objPaneLayered != null){
					if (objPanel != null){
						objPanel.setOpaque(true);
						objPanel.add(objPaneLayered);
					}else{
						if(objPanelContent != null){
							objPanelContent.setOpaque(true);
							objPanelContent.add(objPaneLayered);							
						}else{
							//Den konfigurierten Layered Pane direkt in die JForm bringen
							frmCascaded.setLayeredPane(objPaneLayered);
						}
				}
				}
				
				//Menü in den Rahmen hinzufügen
				JMenuBar menu = frmCascaded.getMenuContent();
				if(menu != null){
					frmCascaded.setJMenuBar(menu);
				}
			
		}//bLaunched before == true
		return bReturn;
	}//end main:
	}

	
	public static String getAliasPanelContent(){
		String sReturn = new String("ContentPane");
		return sReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IFramLaunchableZZZ#launchCustom()
	 * 
	 * wird in launch eingesetzt. 
	 * 
	 * Es ist besser diese Methode abstrakt zu machen, als eine ExceptionZZZ zu werfen:
	        ExceptionZZZ ez = new ExceptionZZZ("this function has to be overwritten by an inheriting class.", iERROR_ZFRAME_METHOD, this, ReflectionZZZ.getMethodCurrentName()); 			
			throw ez;
	 */
	public abstract boolean launchCustom() throws ExceptionZZZ;
			

	public boolean isJComponentSnappedToScreen() {
		return true;
	}

	
	public JMenuBar getMenuContent() throws ExceptionZZZ{
		return null;
	}
	
	
	
	private class RunnerFrameMainZZZ implements Runnable{
		/**Klasse bietet eine run() Methode an, die von KernelJFRameCascadedZZZ.launch() genutzt wird.
		 * Hintergrund: �ber SwingUtilities.invokeLater(RunnerJFrameCascadedZZZ) soll die Performance erh�ht werden,
		 *                   wenn in  .launchCustom() der erbenden Klasse noch weiterer Code steht, der zeitaufwendig ist.
		 *                   
		 * Ziel: Den Aufbau des Frontends in den EventDispatcher-Thread verlegen und den Hautpthread f�r den Custom-Code nutzen.
		 * 
		 * Merke: Das funktioniert aber nur/macht nur Sinn beim Starten des "Hauptframes". Danach wird jeder code, z.B: der eines Buttons,
		 *           eh im AWT-EventQueue ausgef�hrt. Hat also keinen Performancegewinn.
		 */
		private KernelJFrameCascadedZZZ frmCascaded;
		private boolean bLaunchedBefore;
		private String sTitle;
		public RunnerFrameMainZZZ(KernelJFrameCascadedZZZ frm2launch, String sTitle, boolean bLaunchedBefore){
			this.frmCascaded = frm2launch;
			this.bLaunchedBefore = bLaunchedBefore;
			this.sTitle = sTitle;
		}
		public void run() {
			try{
				main:	{	
					//Das doing der launch Methode als static-Methode.
					KernelJFrameCascadedZZZ.launchDoing(this.frmCascaded, this.sTitle, this.bLaunchedBefore);
					
					//Hier werden dann die speziellen Panels des Frames hinzugef�gt
					boolean bReturn = this.frmCascaded.launchCustom();
					if(bReturn){
						//nix machen, wenn so gestartet wurde. Dann soll die Frame-Gr��e nicht von den Komponenten abh�ngen.
					}else{
						this.frmCascaded.pack(); //Frame Gr��e h�ngt von den Komponenten ab
					}
					this.bLaunchedBefore = true;
					//das kostet sehr viel Performance:    ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Frame '" + this.sTitle + "' launched");		
					
	//				... sichtbar machen erst, nachdem alle Elemente im Frame hinzugef�gt wurden !!!
					//depreciated in 1.5 frame.show();
					//statt dessen...
					this.frmCascaded.setVisible(true); //Meke: Trotz alledem wird das Fenster erst komplett angezeigt, wenn der code in launchCustom() beendet ist....
				}//END main:
			} catch (ExceptionZZZ ez) {
				System.out.println(ez.getDetailAllLast()+"\n");
				ez.printStackTrace();
				ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());	
			}
		}//END run()
				
	}//END private class FrameMAinRunnerVIA
	
}//End Class
