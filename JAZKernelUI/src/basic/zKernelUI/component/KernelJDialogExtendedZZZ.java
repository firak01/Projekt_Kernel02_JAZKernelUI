package basic.zKernelUI.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasicUI.adapter.AdapterJComponent4ScreenSnapperZZZ;
import basic.zBasicUI.listener.ListenerMouseMove4DragableWindowZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.component.IKernelModuleUserZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagZLocalUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zKernelUI.util.JTextFieldHelperZZZ;
import custom.zKernel.LogZZZ;

/**Diese Klasse soll sicherstellen, das ein Dialogfenster auch nur einmal geoeffnet wird.
 * @author 0823
 *
 */
public abstract class KernelJDialogExtendedZZZ extends JDialog implements IDialogExtendedZZZ, IConstantZZZ, IObjectZZZ, IKernelUserZZZ, IKernelModuleZZZ, IKernelModuleUserZZZ, IScreenFeatureZZZ, IMouseFeatureZZZ, IFlagZUserZZZ, IFlagZLocalUserZZZ{
	protected IKernelZZZ objKernel;
	protected LogZZZ objLog;
	protected ExceptionZZZ objException;
	protected IKernelModuleZZZ objModule=null; //Das Modul, z.B. für die Dialogbox
	
	protected IPanelCascadedZZZ panelContent = null;
	protected IPanelCascadedZZZ panelButton = null;	
	protected IPanelCascadedZZZ panelNavigator = null;	
	
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

	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>();
	private HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>();
	private HashMap<String, Boolean>hmFlagLocal = new HashMap<String, Boolean>();
	
	/**
	 * DEFAULT Konstruktor, notwendig, damit man objClass.newInstance(); einfach machen kann.
	 *                                 
	 * lindhaueradmin, 23.07.2013
	 */
	public KernelJDialogExtendedZZZ(){// throws ExceptionZZZ{
		//Jetzt muss im Prinzip alles gemacht werden, das in KernelUseObjectZZZ auch gemacht wird. 
		//Leider kann diese Klasse nicht davon erben.
		
		//20080422 wenn objekte diese klasse erweitern scheint dies immer ausgeführt zu werden. Darum hier nicht setzen !!! this.setFlag("init", true);
	}
	public KernelJDialogExtendedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameOwner, boolean bModal, HashMap<String, Boolean> hmFlag) throws ExceptionZZZ{
		super(frameOwner, bModal);	//Das initialisiert JDialog
		KernelJDialogExtendedNew_(objKernel, frameOwner, bModal, null, hmFlag);
	}
	
	public KernelJDialogExtendedZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameOwner, boolean bModal, HashMap<String, Boolean> hmFlagLocal, HashMap<String, Boolean> hmFlag) throws ExceptionZZZ{
		super(frameOwner, bModal);	//Das initialisiert JDialog
		KernelJDialogExtendedNew_(objKernel, frameOwner, bModal, hmFlagLocal, hmFlag);
	}
	
	private boolean KernelJDialogExtendedNew_(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameOwner, boolean bModal, HashMap<String, Boolean> hmFlagLocal, HashMap<String, Boolean> hmFlag) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Das Lokale Flag wirkt sich nur in dieser Methode aus. Die anderen Flags auch aus hieraus erbenden Klassen.
			if(hmFlagLocal!=null){
				for(String sKey:hmFlagLocal.keySet()){				
					String stemp = sKey;
					boolean btemp = this.setFlagLocal(sKey, hmFlagLocal.get(sKey));
					if(btemp==false){
						ExceptionZZZ ez = new ExceptionZZZ( "the LOCAL flag '" + stemp + "' is not available (passed by hashmap). Maybe an interface is not implemented.", IFlagZLocalUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;		 
					}	
				}
			}
			if(this.getFlagLocal("INIT")){
				bReturn = true;
				break main; 
			}	
			
		
				
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				String stemp = sKey;
				boolean btemp = this.setFlag(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap).", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
			}
		}
		if(this.getFlag("INIT")){
			bReturn = true;
			break main; 
		}	
		
		//Jetzt muss im Prinzip alles gemacht werden, das in KernelUseObjectZZZ und ObjectZZZ auch gemacht wird. 
		//Leider kann diese Klasse nicht davon erben.
		this.setKernelObject(objKernel);
		this.setLogObject(objKernel.getLogObject());
		
		//++++++++++++++++++++++++++++++
		boolean btemp; String sLog;		
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
		if(this.isJComponentSnappedToScreen()){
			AdapterJComponent4ScreenSnapperZZZ snapAdapter = new AdapterJComponent4ScreenSnapperZZZ();
			this.addComponentListener(snapAdapter);
		}		
		
		this.getContentPane().setLayout(new BorderLayout());

		JPanel panelContent = (JPanel)this.getPanelContent();
		if(panelContent!=null) {
			panelContent.revalidate(); //Das Neuzeichnen ist wichtig!!!
			panelContent.repaint();
		}
		
		}//end main:
		return bReturn;
	}
	
	public void addPanelNavigator(KernelJPanelCascadedZZZ panelNavigator) throws ExceptionZZZ{
		main:{
		if(panelNavigator == null){
			//Nun das Standard NavigatorPanel hinzufuegen						
			KernelJPanelCascadedZZZ panel2add = (KernelJPanelCascadedZZZ) this.getPanelNavigator();
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
			
			this.getContentPane().add(panelNavigator, BorderLayout.WEST);	
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
				KernelJPanelCascadedZZZ panel2add = (KernelJPanelCascadedZZZ) this.getPanelContent();
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

	public void addPanelButton(KernelJPanelCascadedZZZ panelButton) throws ExceptionZZZ{
		//Nun den standard Button Panel hinzuf�gen
		if(panelButton == null){ 
			KernelJPanelCascadedZZZ panel2add = (KernelJPanelCascadedZZZ) this.getPanelButton();
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
			
			//Hier nun die Panels hinzufuegen, aber nur, wenn sie nicht schon hinzugefuegt worden sind
			//Merke: Die Panels sollten noch nicht im Konstruktor der Klasse hinzugefuegt werden, weil man sonst schwerer eigenschaften wie "Button-Text" aendern kann.
			//          So kann man erst das Objekt erzeugen und dann mit "setText4ButtonOk" den Button-Text aendern, der dann mit showDialog() angezeigt wird.
			
		  //TODOGOON;// 20210701: Zum Vereinfachten Debuggen die anderen Panels rausgenommen
         //bzw. Fehler tritt auf wenn anderes Panel eingebunden ist.
			if(this.bPanelCenterAdded==false){
				KernelJPanelCascadedZZZ panelContent = (KernelJPanelCascadedZZZ) this.createPanelContent();
				this.setPanelContent(panelContent);
				this.addPanelCenter(panelContent);
			}
			
			if(this.bPanelButtonAdded==false){
				KernelJPanelCascadedZZZ panelButton = (KernelJPanelCascadedZZZ) this.createPanelButton();
				this.setPanelButton(panelButton);
				this.addPanelButton(panelButton); //null soll bewirken, dass das default ButtonPanel hinzugef�gt wird.
			}  
			
			if(this.bPanelNavigatorAdded==false) {
				KernelJPanelCascadedZZZ panelNavigator = (KernelJPanelCascadedZZZ) this.createPanelNavigator();
				this.setPanelNavigator(panelNavigator);
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

			JPanel panelContent = (JPanel)this.getPanelContent();
			if(panelContent!=null) {				
				panelContent.revalidate(); //Das Neuzeichnen ist ggfs. wichtig!!!
				panelContent.repaint();
			}
			
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
	
	public void setVisible() {
		//Wenn man direkt auf den Dialog, z.B. aus einem Button heraus zugreifen möchte:
		this.setVisible(true);
		this.isDisposed(false);
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
	
	public IPanelCascadedZZZ getPanelButton(){
		return this.panelButton;
	}
	public void setPanelButton(IPanelCascadedZZZ panelButton) {
		this.panelButton = panelButton;
	}
	
	public IPanelCascadedZZZ getPanelContent() {
		return this.panelContent;
	}
	public void setPanelContent(IPanelCascadedZZZ panelContent) {
		this.panelContent = panelContent;
	}
	
	public IPanelCascadedZZZ getPanelNavigator() {
		return this.panelNavigator;		
	}
	public void setPanelNavigator(IPanelCascadedZZZ panelNavigator) {
		this.panelNavigator = panelNavigator;
	}
	
	
	public abstract IPanelCascadedZZZ createPanelButton() throws ExceptionZZZ;
	public abstract IPanelCascadedZZZ createPanelContent() throws ExceptionZZZ;	
	public abstract IPanelCascadedZZZ createPanelNavigator() throws ExceptionZZZ; 
	
	/** Kann von einer Dialogbox ueberschrieben werden, wenn ein anderes Panel als das "Default" Panel verwendet werden soll.
	* @return
	* 
	* lindhaueradmin; 11.01.2007 08:27:54
	 * @throws ExceptionZZZ 
	 */
	public KernelJPanelCascadedZZZ getPanelButtonDefault() throws ExceptionZZZ{
		KernelJPanelCascadedZZZ panel = new KernelJPanelDialogButtonDefaultZZZ(this.getKernelObject(), this, this.isButtonOkAvailable(), this.isButtonCancelAvailable(), this.isButtonCloseAvailable());
		return panel;
	}
	public KernelJPanelCascadedZZZ getPanelContentDefault() throws ExceptionZZZ{
		KernelJPanelCascadedZZZ panel = new KernelJPanelDialogContentDefaultZZZ(this.getKernelObject(), this, this.getText4ContentDefault());
		return panel;
	}
	public KernelJPanelCascadedZZZ getPanelNavigatorDefault() throws ExceptionZZZ {		
		KernelJPanelDialogContentEmptyZZZ panel = new KernelJPanelDialogContentEmptyZZZ(this.getKernelObject(), this);
		return panel;
	}
	
	//Defaultverhalten der Dialogbox beim OK, CLOSE, CANCEL
	public void onClose() throws ExceptionZZZ{
		if(this.getFlagLocal(KernelJDialogExtendedZZZ.FLAGZLOCAL.HIDE_ON_CLOSE)){
			this.setHidden(); 
		}else {
			this.setDisposed();
		}
	}
	
	public void onOk() throws ExceptionZZZ{
		if(this.getFlagLocal(KernelJDialogExtendedZZZ.FLAGZLOCAL.HIDE_ON_OK)){
			this.setHidden(); 
		}else {
			this.setDisposed();
			this.getKernelObject().getCacheObject().clear(); //!!! solange beim Schreiben der Werte in die ini noch 
		}		
	}
	
	public void onCancel() throws ExceptionZZZ{
		if(this.getFlagLocal(KernelJDialogExtendedZZZ.FLAGZLOCAL.HIDE_ON_CANCEL)){
			this.setHidden(); 
		}else {
			this.setDisposed();
		}
	}
	
	
		
	public String getText4ContentDefault(){
		return this.sText4ContentDefault;
	}
	public void setText4ContentDefault(String sText){
		this.sText4ContentDefault = sText;
	}
	
	public void setPanelContent(KernelJPanelCascadedZZZ panelContent) {
		this.panelContent = panelContent;
	}
	public void setPanelNavigator(KernelJPanelCascadedZZZ panelNavigator) {
		this.panelNavigator = panelNavigator;
	}
	public void setPanelButton(KernelJPanelCascadedZZZ panelButton) {
		this.panelButton = panelButton;
	}
		

	//######## aus interfaces #################
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
		public void logLineDate(String sLog) throws ExceptionZZZ {
			LogZZZ objLog = this.getLogObject();
			if(objLog==null) {
				String sTemp = KernelLogZZZ.computeLineDate(sLog);
				System.out.println(sTemp);
			}else {
				objLog.WriteLineDate(sLog);
			}		
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
	
		
	//#####################################
	/* (non-Javadoc)
	 * @see basic.zKernelUI.IScreenFeatureZZZ#isJComponentSnappedToScreen()
	 */
	public boolean isJComponentSnappedToScreen() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IMouseFeatureZZZ#isJComponentContentDraggable()
	 */
	public boolean isJComponentContentDraggable(){
		return this.getFlag(IDialogExtendedZZZ.FLAGZ.ISDRAGGABLE.name());
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IMouseFeatureZZZ#setJComponentContentDraggable(boolean)
	 */
	public void setJComponentContentDraggable(boolean bValue) throws ExceptionZZZ{
		this.setFlag(IDialogExtendedZZZ.FLAGZ.ISDRAGGABLE.name(), bValue);
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
//			@Override
//			public boolean getFlag(String sFlagName) {
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
//				return this.getFlag(sFlagName);
//			}
//			@Override
//			public boolean setFlag(String sFlagName, boolean bFlagValue) {
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
			
			/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
			 * 	 Weteire Voraussetzungen:
			 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
			 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
			 */
			public boolean getFlag(String sFlagName) {
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
		 * Weteire Voraussetzungen:
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
			
		@Override
		public HashMap<String, Boolean> getHashMapFlagPassed() {
			return this.hmFlagPassed;
		}
		@Override
		public void setHashMapFlagPassed(HashMap<String, Boolean> hmFlagPassed) {
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
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++
			/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
			 * 	 Weteire Voraussetzungen:
			 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
			 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
			 */
			public boolean getFlagLocal(String sFlagName) {
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
			
			
			//### Aus IDialogExtendedZZZ
			@Override
			public boolean getFlagLocal(IDialogExtendedZZZ.FLAGZLOCAL objEnumFlag) {
				return this.getFlagLocal(objEnumFlag.name());
			}
			
			@Override
			public boolean setFlagLocal(IDialogExtendedZZZ.FLAGZLOCAL objEnumFlag, boolean bValue) throws ExceptionZZZ {
				return this.setFlag(objEnumFlag.name(), bValue);
			}
			
			@Override
			public boolean[] setFlagLocal(IDialogExtendedZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
						baReturn = new boolean[objaEnumFlag.length];
						int iCounter=-1;
						for(IDialogExtendedZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
							iCounter++;
							boolean bReturn = this.setFlagLocal(objEnumFlag, bValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			
			@Override
			public boolean proofFlagLocalExists(IDialogExtendedZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objEnumFlag.name());
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
					if(StringZZZ.isEmpty(sFlagName))break main;
					bReturn = FlagZHelperZZZ.proofFlagZLocalExists(this.getClass(), sFlagName);				
				}//end main:
				return bReturn;
			}
				
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++
		
			
			/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
			 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
			 *    
			 * @return
			 * @throws ExceptionZZZ 
			 */
			@Override
			public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
				return this.getFlagZ_passable_(bValueToSearchFor, false, objUsingFlagZ);
			}
			
			/* (non-Javadoc)
			 * @see basic.zKernel.flag.IFlagUserZZZ#getFlagZ_passable(boolean, boolean, basic.zKernel.flag.IFlagUserZZZ)
			 */
			@Override
			public String[] getFlagZ_passable(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
				return this.getFlagZ_passable_(bValueToSearchFor, bLookupExplizitInHashMap, objUsingFlagZ);
			}
			
			private String[] getFlagZ_passable_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
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
			public String[] getFlagZ_passable(IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
				return this.getFlagZ_passable_(objUsingFlagZ);
			}
			
			private String[] getFlagZ_passable_(IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
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
		
		@Override
		public boolean proofFlagLocalSetBefore(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZLocalSetBefore(this, sFlagName);
			}
			return bReturn;
		}
		

		//### Functions #########################
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
	    //##### aus IKernelModuleZZZ
	    @Override
		public String getModuleName() throws ExceptionZZZ {
			return KernelUIZZZ.getModuleUsedName(this);
		}
	    
	/* (non-Javadoc)
	* @see basic.zKernel.component.IKernelModuleZZZ#resetModuleUsed()
	*/
	@Override
	public void resetModuleUsed() {	
		this.objModule = null; //Da der Modulename nicht gespeichert wird, sondern nur das Modulobjekt
	}
	
	@Override
	public boolean reset() {
		this.resetModuleUsed();
		return true;
	}

	    
		
	//### AUS IKernelModuleZZZ
	@Override
	public IKernelModuleZZZ getModule() throws ExceptionZZZ {
		if(this.objModule==null && this.getFlag(IKernelModuleUserZZZ.FLAGZ.ISKERNELMODULEUSER.name())) {
			this.objModule = KernelUIZZZ.searchModule(this);
		}
		return this.objModule;
	}

	@Override
	public void setModule(IKernelModuleZZZ objModule) {
		this.objModule = objModule;		
	}
	
	@Override
	public boolean getFlag(IKernelModuleZZZ.FLAGZ objEnumFlag) {
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
	
	//### Aus IFlagUserZZZ
	@Override
	public boolean getFlag(IFlagZUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IFlagZUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IFlagZUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFlagZUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IFlagZUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IFlagZUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### Aus IKernelModuleUserZZZ
	@Override
	public boolean getFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag) {
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
	
	//### Aus IMouseFeatureZZZ
	@Override
	public boolean getFlag(IMouseFeatureZZZ.FLAGZ objEnumFlag) {
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
	
	@Override
	public boolean resetFlags() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			HashMap<String,Boolean> hm = this.getHashMapFlag();
			if(hm.isEmpty())break main;
			
			ReferenceHashMapZZZ<String,Boolean>objhmReturn=new ReferenceHashMapZZZ<String,Boolean>();
			objhmReturn.set(hm);
			
			bReturn =FlagZHelperZZZ.resetFlags(objhmReturn); 			
		}//end main:
		return bReturn;
	}
	
	
}
