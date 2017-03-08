package basic.zKernelUI.component;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.IConstantZZZ;
import basic.zBasic.IFunctionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zBasicUI.adapter.AdapterJComponent4ScreenSnapperZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.KernelZZZ;
import basic.zUtil.io.KernelFileZZZ.FLAGZ;
import custom.zKernel.LogZZZ;

/** Class is base for all frames used by the configuration module
 * @author Lindhauer
 */
public abstract class KernelJFrameCascadedZZZ extends JFrame  implements IConstantZZZ, IObjectZZZ, IFunctionZZZ, IKernelUserZZZ, IComponentCascadedUserZZZ, IFrameCascadedZZZ, IFrameLaunchableZZZ, IScreenFeatureZZZ{
	private KernelZZZ objKernel;
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
	COMPONENTKERNELPROGRAM,COMPONENTDRAGGABLE,TERMINATE; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
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
	public KernelJFrameCascadedZZZ(KernelZZZ objKernel, JFrame frameBasic){
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
	public KernelJFrameCascadedZZZ(KernelZZZ objKernel) throws ExceptionZZZ{
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
	public KernelJFrameCascadedZZZ(KernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
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
	
	//### FlagMethods ##########################		
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

	
	//Aus IObjectZZZ, siehe FileZZZ
	@Override
	public boolean proofFlagZExists(String sFlagName) {
		boolean bReturn = false;
		main:{
			bReturn = ObjectZZZ.proofFlagZExists(this.getClass(), sFlagName);
		
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
	
	//#######################################
	//### Getter / Setter
	public boolean isLaunched(){
		return this.bLaunchedBefore;
	}
	
	
	//#######################################
	//Methods implemented by Interface
	public KernelZZZ getKernelObject() {
		return this.objKernel;
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

	public KernelJPanelCascadedZZZ getPanelSub(String sAlias) {		
		return (KernelJPanelCascadedZZZ) this.objHtPanelSub.get(sAlias);
	}
	public void setPanelSub(String sAlias, KernelJPanelCascadedZZZ objPanel) {
		this.objHtPanelSub.put(sAlias, objPanel);
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
				//!!! Hier wird das gew�nschte ContentPanel eingebaut !!!
				KernelJFrameCascadedZZZ.launchDoing(this, sTitle, this.bLaunchedBefore);
				bReturn = true;
				
				bReturn = this.launchCustom();
				if(bReturn){
					//2013-07-09 die Gr��e des Frames soll ggf. nicht von den Komponenten gesteuert werden
					//... mache also nix, wenn launchCustom()=true
				}else{					
					this.pack();
				}
			
				this.bLaunchedBefore = true;
				ReportLogZZZ.write(ReportLogZZZ.DEBUG, "Frame launched");		
				
//				... sichtbar machen erst, nachdem alle Elemente im Frame hinzugef�gt wurden !!!
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
				
				//Die Gr�sse des Frames, die Methode wird vom KernelFrame zur Verf�gung gestellt 
				frmCascaded.setSizeDefault();
				frmCascaded.setTitle(sTitle);
				
				//Grundfl�che(n) in den Rahmen hinzuf�gen...
				//	... das wird nun �ber das ContentPane der Frames gemacht. !!! Damit diese Grundfl�che "draggable" ist, muss zumindest der ContentPane �bergeben werden.
				KernelJPanelCascadedZZZ objPanel = frmCascaded.getPaneContent();  //default ist der ContentPane-Alias
				if(objPanel !=null){
					if(!frmCascaded.getContentPane().equals(objPanel))frmCascaded.getContentPane().add(objPanel);
					frmCascaded.setPanelSub(KernelJFrameCascadedZZZ.getAliasPanelContent(), objPanel);
				}
				
				//FGL 20080912: Ggf. ein weiteres Panel hinzuf�gen
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
				
				//FGL 20080912: Ggf. einen LayeredPane hinzuf�gen
				//Merke: Das LayeredPane muss entweder einem Panel hinzugef�gt werden oder (falls nicht vorhanden) direkt dem JFrame
				//Merke: Die LayoutManager der JPanels, m�ssen das Hinzuf�gen einer weiteren Komponente aber auch unterst�tzen
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
				
				//Men� in den Rahmen hinzuf�gen
				JMenuBar menu = frmCascaded.getMenuContent();
				if(menu != null){
					frmCascaded.setJMenuBar(menu);
				}
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

	
	public JMenuBar getMenuContent(){
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
