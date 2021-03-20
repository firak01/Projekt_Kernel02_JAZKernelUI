package basic.zKernelUI.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleUserZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zUtil.io.KernelFileZZZ.FLAGZ;
import custom.zKernel.LogZZZ;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.adapter.AdapterJComponent4ScreenSnapperZZZ;
import basic.zBasicUI.listener.ListenerMouseMove4DragableWindowZZZ;
import basic.zKernel.IKernelUserZZZ;

/**Diese Klasse soll sicherstellen, das ein Dialogfenster auch nur einmal geoeffnet wird.
 * @author 0823
 *
 */
public abstract class KernelJDialogExtendedZZZ extends JDialog implements IConstantZZZ, IObjectZZZ, IKernelUserZZZ, IKernelModuleZZZ, IKernelModuleUserZZZ, IScreenFeatureZZZ, IMouseFeatureZZZ, IFlagZZZ{	
	private IKernelZZZ objKernel;
	private LogZZZ objLog;
	protected IKernelModuleZZZ objModule=null; //Das Modul, z.B. für die Dialogbox
	
	private boolean bPanelCenterAdded=false;
	private boolean bPanelButtonAdded=false;
	private boolean bPanelNavigatorAdded=false;
	
	public static String sTEXT_ABORT="CANCEL";
	private String sText4ButtonCancel="";
	private boolean bIsCanceled=false;
	
	public static String sTEXT_CLOSE="CLOSE";
	private String sText4ButtonClose="";
	
	public static String sTEXT_USEIT="APPLY";
	private String sText4ButtonOk="";
	
	private String sText4ContentDefault = "";
	
	
	//Dient dazu festzuhalten, ob diese Dialogbox schon mal komplett geschlossen wurde, also komplett neu geladen werden muss.
	private boolean bDisposed=false;
	
	
	private Hashtable objHtPanelSub=new Hashtable();     //Die Panels, die im BorderLayout hinzugef�gt werden
	//private Hashtable objHtComponent = new Hashtable(); //Soll Komponenenten, wie z.B. ein Textfield per "Alias" greifbar machen.
	
	//private 	boolean flagComponentDraggable=true;
	//private boolean flagComponentKernelProgram = false; // 2013-07-08: Damit wird gesagt, dass f�r dieses Panel ein "Program-Abschnitt" in der Kernel - Konfigurations .ini - Datei vorhanden ist.
    //             Bei der Suche nach Parametern wird von der aktuellen Komponente weiter "nach oben" durchgegangen und der Parameter f�r jede Programkomponente gesucht.
	//private boolean flagComponentKernelModule = false; //20210124: Analog zum Program hinzugenommen.

	
//	private boolean bFlagDebug = false;
//	private boolean bFlagInit = false;
//	private boolean bFlagTerminate = false;
	public enum FLAGZ{
		TERMINATE,ISDRAGGABLE; //Merke: DEBUG und INIT über IFlagZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von anderer Objektklasse geerbt.
	}
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); 
	
	/**
	 * DEFAULT Konstruktor, notwendig, damit man objClass.newInstance(); einfach machen kann.
	 *                                 
	 * lindhaueradmin, 23.07.2013
	 */
	public KernelJDialogExtendedZZZ(){// throws ExceptionZZZ{
		//JFrame frameParent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)this);
	//20130723 raus:	KernelJFrameCascadedZZZ frameParent = (KernelJFrameCascadedZZZ) SwingUtilities.getAncestorOfClass((KernelJFrameCascadedZZZ.class), (JComponent) this);
		//20130723 raus:		KernelJPanelCascadedNew_(null, frameParent, null);
	}
	public KernelJDialogExtendedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameOwner, boolean bModal, HashMap<String, Boolean> hmFlag){
		super(frameOwner, bModal);		//Das initialisiert JDialog
		this.setKernelObject(objKernel);
		this.setLogObject(objKernel.getLogObject());
		
		//		Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				this.setFlag(sKey, hmFlag.get(sKey));
			}
		}
		
		
		if(this.isJComponentSnappedToScreen()){
			AdapterJComponent4ScreenSnapperZZZ snapAdapter = new AdapterJComponent4ScreenSnapperZZZ();
			this.addComponentListener(snapAdapter);
		}		
		
		this.getContentPane().setLayout(new BorderLayout());
	}
	
	public void addPanelNavigator(KernelJPanelCascadedZZZ panelNavigator) throws ExceptionZZZ{
		main:{
		if(panelNavigator == null){
			//Nun das Standard NavigatorPanel hinzufuegen						
			KernelJPanelCascadedZZZ panel2add = this.getPanelNavigator();
			if(panel2add==null){
				panel2add = new KernelJPanelDialogContentEmptyZZZ(this.getKernelObject(), this);
			}
			
			if(this.isJComponentContentDraggable()){								
				//Nun den Listener fuer diese Komponente hinzufuegen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
				ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panel2add, (JDialog)this);
				panel2add.addMouseListener(mml);
				panel2add.addMouseMotionListener(mml);
			}
			
			this.getContentPane().add(panel2add, BorderLayout.WEST);	
			this.setPanelSub("WEST", panel2add);
			this.bPanelNavigatorAdded = true;

		}else{
			if(this.isJComponentContentDraggable()){								
				//Nun den Listener fuer diese Komponente hinzufuegen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
				ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panelNavigator, (JDialog)this);
				panelNavigator.addMouseListener(mml);
				panelNavigator.addMouseMotionListener(mml);
			}
			
			this.getContentPane().add(panelNavigator, BorderLayout.CENTER);	
			this.setPanelSub("WEST", panelNavigator);
			this.bPanelNavigatorAdded = true;
			}
		}
	}
	
	public void addPanelCenter(KernelJPanelCascadedZZZ panelCenter) throws ExceptionZZZ{
		main:{
			if(panelCenter == null){
				//Nun das Standard Content Panel hinzuf�gen
				String sText = this.getText4ContentDefault();
				KernelJPanelCascadedZZZ panel2add = this.getPanelContent();
				if(panel2add==null){
					//FGL 20070305 check: KernelJPanelDialogContentDefaultZZZ panelContent2add = new KernelJPanelDialogContentDefaultZZZ(this.getKernelObject(), this, sText);
					panel2add = new KernelJPanelDialogContentDefaultZZZ(this.getKernelObject(), this, sText);
				}
				
				if(this.isJComponentContentDraggable()){								
					//Nun den Listener fuer diese Komponente hinzuf�gen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
					ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panel2add, (JDialog)this);
					panel2add.addMouseListener(mml);
					panel2add.addMouseMotionListener(mml);
				}
				
				this.getContentPane().add(panel2add, BorderLayout.CENTER);	
				this.setPanelSub("CENTER", panel2add);
				this.bPanelCenterAdded = true;

			}else{
				if(this.isJComponentContentDraggable()){								
					//Nun den Listener fuer diese Komponente hinzufuegen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
					ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panelCenter, (JDialog)this);
					panelCenter.addMouseListener(mml);
					panelCenter.addMouseMotionListener(mml);
				}
				
				this.getContentPane().add(panelCenter, BorderLayout.CENTER);	
				this.setPanelSub("CENTER", panelCenter);
				this.bPanelCenterAdded = true;
				}
			}
		}

	public void addPanelButton(KernelJPanelCascadedZZZ panelButton){
		//Nun den standard Button Panel hinzuf�gen
		if(panelButton == null){ 
			KernelJPanelCascadedZZZ panel2add = this.getPanelButton();
			if(panel2add==null){
				//FGL 20070305 check: KernelJPanelDialogButtonDefaultZZZ panel2add = new KernelJPanelDialogButtonDefaultZZZ(this.getKernelObject(), this, this.isButtonOkAvailable(), this.isButtonCancelAvailable());				
				//20190219: Die Dialogbox hinzuzufügen hat das Problem, dass Sie nicht als das Program-Definiert ist, welches für das Lesen/Schreiben der Werte in der Ini-Datei vorgesehen ist.
				//          Das ist nämlich ein anderes, benachbartes Panel.				
				panel2add = this.getPanelButtonDefault();
			}
			
			
			if(this.isJComponentContentDraggable()){								
	//			Nun den Listner fuer diese Komponente hinzufuegen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
				ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panel2add, (JDialog)this);
				panel2add.addMouseListener(mml);
				panel2add.addMouseMotionListener(mml);
			}
			this.getContentPane().add(panel2add, BorderLayout.SOUTH);
			this.setPanelSub("SOUTH", panel2add);
			this.bPanelButtonAdded=true;
		}else{
			if(this.isJComponentContentDraggable()){			
	//			Nun den Listner fuer diese Komponente hinzuf�gen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
				ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panelButton, (JDialog)this);
				panelButton.addMouseListener(mml);
				panelButton.addMouseMotionListener(mml);
			}		
			this.getContentPane().add(panelButton, BorderLayout.SOUTH);
			this.setPanelSub("SOUTH", panelButton);
			this.bPanelButtonAdded=true;
		}
	}

    /** Centers the dialog based on its parent.
     * @return boolean
     *
     * javadoc created by: 0823, 05.01.2007 - 14:49:02
     */
    public boolean centerOnParent() {
    	boolean bReturn = false;
    	main:{    		
	        Point parentFrameLocation = this.getParent().getLocation();
	        Dimension parentFrameSize = this.getParent().getSize();
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
    	}//End main:
    	return bReturn;
    }


    /**
     * Shows a message dialog.
     * @param message The message
     * @param title The dialog's title
     * @param messageType The message's type
     */
    public void showMessageDialog(final String message, final String title, final int messageType) {
		JOptionPane.showMessageDialog(KernelJDialogExtendedZZZ.this, message, title, messageType);
    }
    /* (non-Javadoc)
	 * @see basic.zKernelUI.IPanelDialogZZZ#showDialog(java.awt.Component, java.lang.String)
	 * 
	 * 
	 * Anregung aus "Core Java, Band 1", 2003, Kapitel 9 Seite 639f
	 */
	public boolean showDialog(Component objParentComponent, final String sTitle) throws ExceptionZZZ {
		boolean bReturn =false;
		main:{
			if(sTitle==null){
				ExceptionZZZ ez = new ExceptionZZZ ("Title", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			/* das darf in JDialog auch null sein
			if(objParentComponent == null){
				ExceptionZZZ ez = new ExceptionZZZ ("ParentComponent", iERROR_PARAMETER_MISSING, this, ReflectionZZZ.getMethodCurrentName());
				throw ez;
			}
			*/
			
			/*
			//Falls noch keine Button-Zeile hinzugefuegt worden ist, dies hier nachholen
			if(this.bPanelButtonAdded==false){
				this.addPanelButton(null); //null soll bewirken, dass das default ButtonPanel hinzugef�gt wird.
			}  //Merke: Normlerweise werden die Panels im Konstruktor der Dialogbox hinzugef�gt, der von KernelJDialogboxExtended erbt. 
			*/

			//Hier nun die Panels hinzufuegen, aber nur, wenn sie nicht schon hinzugefuegt worden sind
			//Merke: Die Panels sollten noch nicht im Konstruktor der Klasse hinzugefuegt werden, weil man sonst schwerer eigenschaften wie "Button-Text" aendern kann.
			//          So kann man erst das Objekt erzeugen und dann mit "setText4ButtonOk" den Button-Text aendern, der dann mit showDialog() angezeigt wird.
			if(this.bPanelButtonAdded==false){
				KernelJPanelCascadedZZZ panelButton = this.getPanelButton();				
				this.addPanelButton(panelButton); //null soll bewirken, dass das default ButtonPanel hinzugef�gt wird.
			}  
			
			if(this.bPanelCenterAdded==false){
				KernelJPanelCascadedZZZ panelContent = this.getPanelContent();
				this.addPanelCenter(panelContent);
			}
			
			if(this.bPanelNavigatorAdded==false) {
				KernelJPanelCascadedZZZ panelNavigator = this.getPanelNavigator();
				this.addPanelNavigator(panelNavigator);
			}
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			Frame owner = null;
			if(objParentComponent instanceof Frame){
				owner = (Frame) objParentComponent;
			}else{
				owner =(Frame) SwingUtilities.getAncestorOfClass(Frame.class, objParentComponent);				
			}
			
			if(this.isCentered()) this.centerOnParent();
			this.pack();
			
			this.setTitle(sTitle);
			this.setVisible(true);
			bReturn = true;			
		}//End main:
		return bReturn;
	}
	
	public void setPanelSub(String sAlias, KernelJPanelCascadedZZZ panel){
		this.objHtPanelSub.put(sAlias, panel);
	}
	public KernelJPanelCascadedZZZ getPanelSub(String sAlias){
		return (KernelJPanelCascadedZZZ) this.objHtPanelSub.get(sAlias);
	}
	public Hashtable getPanelSubAll(){
		return this.objHtPanelSub;
	}
	
	public Frame getFrameParent(){
		Frame frameParent = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, this);
		return frameParent;
	}

	//######## aus interfaces #################
	/* (non-Javadoc)
	 * @see basic.zBasic.IObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return null;
	}

	/* (non-Javadoc)
	 * @see basic.zBasic.IObjectZZZ#setExceptionObject(basic.zBasic.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {		
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

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZZZ#getKernelObject()
	 */
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZZZ#setKernelObject(custom.zKernel.KernelZZZ)
	 */
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZZZ#getLogObject()
	 */
	public LogZZZ getLogObject() {
		return this.objLog;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZZZ#setLogObject(custom.zKernel.LogZZZ)
	 */
	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}

	
	//### ######################
		public boolean isDisposed() {
			return this.bDisposed;
		}
		

		private void isDisposed(boolean bDispose) {
			this.bDisposed = bDispose;
		}


		public void setDisposed() {
			//Wenn man direkt auf den Dialog, z.B. aus einem Button heraus zugreifen möchte:
			//JDialog dialogParent = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this.getPanelParent());
			//dialogParent.dispose();
			this.dispose();
			this.isDisposed(true);
		}
		
		public void setHidden() {
			//Wenn man direkt auf den Dialog, z.B. aus einem Button heraus zugreifen möchte:
			//JDialog dialogParent = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this.getPanelParent());
			//dialogParent.setVisible(false);	//dialogParent.hide();
			this.setVisible(false);
			this.isDisposed(false);
		}
		
		//#####################################
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IScreenFeatureZZZ#isJComponentSnappedToScreen()
	 */
	public boolean isJComponentSnappedToScreen() {
		return false;
	}
	
	public boolean isJComponentContentDraggable(){
		return this.getFlag(KernelJDialogExtendedZZZ.FLAGZ.ISDRAGGABLE.name());
	}
	
	public void setJComponentContentDraggable(boolean bValue){
		this.setFlag(KernelJDialogExtendedZZZ.FLAGZ.ISDRAGGABLE.name(), bValue);
	}
	
	public String getText4ButtonOk(){
		String sReturn = this.sText4ButtonOk;
		if(StringZZZ.isEmpty(sReturn)) {
			sReturn = KernelJDialogExtendedZZZ.sTEXT_USEIT;
		}
		return sReturn;
	}
	public void setText4ButtonOk(String sText){
		this.sText4ButtonOk = sText;
	}
	
	public String getText4ButtonClose(){
		String sReturn = this.sText4ButtonClose;
		if(StringZZZ.isEmpty(sReturn)) {
			sReturn = KernelJDialogExtendedZZZ.sTEXT_CLOSE;
		}
		return sReturn;
	}
	public void setText4ButtonClose(String sText){
		this.sText4ButtonClose = sText;
	}
	
	public String getText4ButtonCancel(){
		String sReturn = this.sText4ButtonCancel;
		if(StringZZZ.isEmpty(sReturn)) {
			sReturn = KernelJDialogExtendedZZZ.sTEXT_ABORT;
		}
		return sReturn;
	}
	public void setText4ButtonCancel(String sText){
		this.sText4ButtonCancel = sText;
	}
	
	public abstract KernelJPanelCascadedZZZ getPanelButton();
	public abstract KernelJPanelCascadedZZZ getPanelContent() throws ExceptionZZZ;
	public abstract KernelJPanelCascadedZZZ getPanelNavigator(); 
	
	/** Kann von einer Dialogbox �berschrieben werden, wenn ein anderes Panel als das "Default" Panel verwendet werden soll.
	* @return
	* 
	* lindhaueradmin; 11.01.2007 08:27:54
	 */
	public KernelJPanelCascadedZZZ getPanelButtonDefault(){
		KernelJPanelCascadedZZZ panel = new KernelJPanelDialogButtonDefaultZZZ(this.getKernelObject(), this, this.isButtonOkAvailable(), this.isButtonCancelAvailable(), this.isButtonCloseAvailable());
		return panel;
	}
	public KernelJPanelCascadedZZZ getPanelContentDefault(){
		KernelJPanelCascadedZZZ panel = new KernelJPanelDialogContentDefaultZZZ(this.getKernelObject(), this, this.getText4ContentDefault());
		return panel;
	}
	public KernelJPanelCascadedZZZ getPanelNavigatorDefault() {		
		KernelJPanelDialogContentEmptyZZZ panel = new KernelJPanelDialogContentEmptyZZZ(this.getKernelObject(), this);
		return panel;
	}
	
	
	public String getText4ContentDefault(){
		return this.sText4ContentDefault;
	}
	public void setText4ContentDefault(String sText){
		this.sText4ContentDefault = sText;
	}
	

	/* (non-Javadoc)
	 * @see basic.zKernelUI.IPanelDialogZZZ#isButtonOkAvailable()
	 */
	public boolean isButtonOkAvailable() {
		return true;
	}

	/* (non-Javadoc)
	 * @see basic.zKernelUI.IPanelDialogZZZ#isButtonCancelAvailable()
	 */
	public boolean isButtonCancelAvailable() {		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IPanelDialogZZZ#isButtonCancelAvailable()
	 */
	public boolean isButtonCloseAvailable() {		
		return true;
	}

	/* (non-Javadoc)
	 * @see basic.zKernelUI.IPanelDialogZZZ#isCentered()
	 */
	public boolean isCentered() {		
		return true;
	}
		
	//### FlagMethods ##########################	
		@Override
		public Class getClassFlagZ(){
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
		
		
		/*
		 * @param sFlagName
		 * @return
		 * lindhaueradmin, 06.07.2013
		 */
		//Version Vor Java 1.6
//		public boolean getFlag(String sFlagName) {
//			boolean bFunction = false;
//			main:{
//				if(sFlagName == null) break main;
//				if(sFlagName.equals("")) break main;
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
//				}else if(stemp.equals("terminate")){
//					bFunction = this.bFlagTerminate;
//				}else if(stemp.equals("isdraggable")){
//					bFunction = this.flagComponentDraggable;
//				}else if(stemp.equals("iskernelprogram")){
//					bFunction = this.flagComponentKernelProgram;
//				}else if(stemp.equals("iskernelmodule")){
//					bFunction = this.flagComponentKernelModule;
//				}else{
//					bFunction = false;
//				}		
//			}	// end main:
//			
//			return bFunction;	
//			}

		/**
		 * @param sFlagName
		 * @param bFlagValue
		 * @return
		 * lindhaueradmin, 06.07.2013
		 */
//		public boolean setFlag(String sFlagName, boolean bFlagValue) {
//			boolean bFunction = true;
//			main:{
//				if(sFlagName == null) break main;
//				if(sFlagName.equals("")) break main;
//				
//				// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//				// bFunction = super.setFlag(sFlagName, bFlagValue);
//				// if(bFunction == true) break main;
//				
//				// Die Flags dieser Klasse setzen
//				String stemp = sFlagName.toLowerCase();
//				if(stemp.equals("debug")){
//					this.bFlagDebug = bFlagValue;
//					bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//					break main;
//				}else if(stemp.equals("init")){
//					this.bFlagInit = bFlagValue;
//					bFunction = true;
//					break main;
//				}else if(stemp.equals("terminate")){
//					this.bFlagTerminate = bFlagValue;
//					bFunction = true;
//					break main;
//				}else if(stemp.equals("isdraggable")){
//					this.flagComponentDraggable = bFlagValue;
//					bFunction = true;
//					break main;
//				}else if(stemp.equals("iskernelprogram")){
//					this.flagComponentKernelProgram = bFlagValue;
//					bFunction = true;
//					break main;
//				}else if(stemp.equals("iskernelmodule")){
//					this.flagComponentKernelModule = bFlagValue;
//					bFunction = true;
//					break main;
//				}else{
//					bFunction = false;
//				}	
//				
//			}	// end main:
//			
//			return bFunction;	
//		}
		
		
//		@Override
		public boolean getFlag(String sFlagName) {
			return this.getFlagZ(sFlagName);
		}
		@Override
		public boolean setFlag(String sFlagName, boolean bFlagValue) {
			try {
				return this.setFlagZ(sFlagName, bFlagValue);
			} catch (ExceptionZZZ e) {
				System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
				return false;
			}
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
			   //                Merke: Das Verschieben ist deshalb notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.
			
												
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
		
	    //##### aus IKernelModuleZZZ
	    @Override
		public String getModuleName() throws ExceptionZZZ {
			return KernelUIZZZ.getModuleUsedName(this);
		}
		
		//### AUS IKernelModuleUserZZZ
		@Override
		public IKernelModuleZZZ getModule() throws ExceptionZZZ {
			if(this.objModule==null && this.getFlagZ(IKernelModuleUserZZZ.FLAGZ.ISKERNELMODULEUSER.name())) {
				this.objModule = KernelUIZZZ.searchModule(this);
			}
			return this.objModule;
		}

		@Override
		public void setModule(IKernelModuleZZZ objModule) {
			this.objModule = objModule;		
		}
}
