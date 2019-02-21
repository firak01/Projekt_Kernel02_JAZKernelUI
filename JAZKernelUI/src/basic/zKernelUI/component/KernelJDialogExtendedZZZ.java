package basic.zKernelUI.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasicUI.adapter.AdapterJComponent4ScreenSnapperZZZ;
import basic.zBasicUI.listener.ListenerMouseMove4DragableWindowZZZ;
import basic.zKernel.IKernelUserZZZ;

/**Diese Klasse soll sicherstellen, das ein Dialogfenster auch nur einmal ge�ffnet wird.
 * !!! NOCH UNGETESTET !!!
 * @author 0823
 *
 */
public abstract class KernelJDialogExtendedZZZ extends JDialog implements IConstantZZZ, IObjectZZZ, IKernelUserZZZ, IScreenFeatureZZZ, IMouseFeatureZZZ{
	private IKernelZZZ objKernel;
	private LogZZZ objLog;
	private boolean bPanelCenterAdded=false;
	private boolean bPanelButtonAdded=false;
	private String sText4ButtonCancel="CANCEL";
	private String sText4ButtonOk="OK";
	private String sText4ContentDefault = "";
	
	private Hashtable objHtPanelSub=new Hashtable();     //Die Panels, die im BorderLayout hinzugef�gt werden
	//private Hashtable objHtComponent = new Hashtable(); //Soll Komponenenten, wie z.B. ein Textfield per "Alias" greifbar machen.
	
	private 	boolean flagComponentDraggable=true;
	private boolean flagComponentKernelProgram = false; // 2013-07-08: Damit wird gesagt, dass f�r dieses Panel ein "Program-Abschnitt" in der Kernel - Konfigurations .ini - Datei vorhanden ist.
    //             Bei der Suche nach Parametern wird von der aktuellen Komponente weiter "nach oben" durchgegangen und der Parameter f�r jede Programkomponente gesucht.


	private boolean bFlagDebug = false;
	private boolean bFlagInit = false;
	private boolean bFlagTerminate = false;
	
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
	
	/*
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 06.07.2013
	 */
	public boolean getFlag(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(sFlagName == null) break main;
			if(sFlagName.equals("")) break main;
			
			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
			// bFunction = super.getFlag(sFlagName);
			// if(bFunction == true) break main;
			
			// Die Flags dieser Klasse setzen
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("debug")){
				bFunction = this.bFlagDebug;
				break main;
			}else if(stemp.equals("init")){
				bFunction = this.bFlagInit;
				break main;
			}else if(stemp.equals("terminate")){
				bFunction = this.bFlagTerminate;
			}else if(stemp.equals("isdraggable")){
				bFunction = this.flagComponentDraggable;
			}else if(stemp.equals("iskernelprogram")){
				bFunction = this.flagComponentKernelProgram;
			}else{
				bFunction = false;
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
	public boolean setFlag(String sFlagName, boolean bFlagValue) {
		boolean bFunction = true;
		main:{
			if(sFlagName == null) break main;
			if(sFlagName.equals("")) break main;
			
			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
			// bFunction = super.setFlag(sFlagName, bFlagValue);
			// if(bFunction == true) break main;
			
			// Die Flags dieser Klasse setzen
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("debug")){
				this.bFlagDebug = bFlagValue;
				bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
				break main;
			}else if(stemp.equals("init")){
				this.bFlagInit = bFlagValue;
				bFunction = true;
				break main;
			}else if(stemp.equals("terminate")){
				this.bFlagTerminate = bFlagValue;
				bFunction = true;
				break main;
			}else if(stemp.equals("isdraggabel")){
				this.flagComponentDraggable = bFlagValue;
				bFunction = true;
				break main;
			}else if(stemp.equals("iskernelprogram")){
				this.flagComponentKernelProgram = bFlagValue;
				bFunction = true;
				break main;
			}else{
				bFunction = false;
			}	
			
		}	// end main:
		
		return bFunction;	
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
					//Nun den Listener f�r diese Komponente hinzuf�gen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
					ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panel2add, (JDialog)this);
					panel2add.addMouseListener(mml);
					panel2add.addMouseMotionListener(mml);
				}
				
				this.getContentPane().add(panel2add, BorderLayout.CENTER);	
				this.setPanelSub("CENTER", panel2add);
				this.bPanelCenterAdded = true;

			}else{
				if(this.isJComponentContentDraggable()){								
					//Nun den Listener f�r diese Komponente hinzuf�gen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
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
				//panel2add = new KernelJPanelDialogButtonDefaultZZZ(this.getKernelObject(), this, this.isButtonOkAvailable(), this.isButtonCancelAvailable());
				//20190219: Die Dialogbox hinzuzufügen hat das Problem, dass Sie nicht als das Program-Definiert ist, welches für das Lesen/Schreiben der Werte in der Ini-Datei vorgesehen ist.
				//          Das ist nämlich ein anderes, benachbartes Panel.
				panel2add = new KernelJPanelDialogButtonDefaultZZZ(this.getKernelObject(), this, this.isButtonOkAvailable(), this.isButtonCancelAvailable());
			}
			
			
			if(this.isJComponentContentDraggable()){								
	//			Nun den Listner f�r diese Komponente hinzuf�gen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
				ListenerMouseMove4DragableWindowZZZ mml = new ListenerMouseMove4DragableWindowZZZ((JPanel)panel2add, (JDialog)this);
				panel2add.addMouseListener(mml);
				panel2add.addMouseMotionListener(mml);
			}
			this.getContentPane().add(panel2add, BorderLayout.SOUTH);
			this.setPanelSub("SOUTH", panel2add);
			this.bPanelButtonAdded=true;
		}else{
			if(this.isJComponentContentDraggable()){			
	//			Nun den Listner f�r diese Komponente hinzuf�gen, der es erlaubt durch Drag mit der Maus auf das Panel des Dialogs den ganzen Dialog zu bewegen.
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
			//Falls noch keine Button-Zeile hinzugef�gt worden ist, dies hier nachholen
			if(this.bPanelButtonAdded==false){
				this.addPanelButton(null); //null soll bewirken, dass das default ButtonPanel hinzugef�gt wird.
			}  //Merke: Normlerweise werden die Panels im Konstruktor der Dialogbox hinzugef�gt, der von KernelJDialogboxExtended erbt. 
			*/

			//Hier nun die Panels hinzuf�gen, aber nur, wenn sie nicht schon hinzugef�gt worden sind
			//Merke: Die Panels sollten noch nicht im Konstruktor der Klasse hinzugef�gt werden, weil man sonst schwerer eigenschaften wie "Button-Text" �ndern kann.
			//          So kann man erst das Objekt erzeugen und dann mit "setText4ButtonOk" den Button-Text �ndern, der dann mit showDialog() angezeigt wird.
			if(this.bPanelButtonAdded==false){
				KernelJPanelCascadedZZZ panelButton = this.getPanelButton();				
				this.addPanelButton(panelButton); //null soll bewirken, dass das default ButtonPanel hinzugef�gt wird.
			}  
			
			if(this.bPanelCenterAdded==false){
				KernelJPanelCascadedZZZ panelContent = this.getPanelContent();
				this.addPanelCenter(panelContent);
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see basic.zBasic.IObjectZZZ#setExceptionObject(basic.zBasic.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub
		
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

	/* (non-Javadoc)
	 * @see basic.zKernelUI.IScreenFeatureZZZ#isJComponentSnappedToScreen()
	 */
	public boolean isJComponentSnappedToScreen() {
		return false;
	}
	
	public boolean isJComponentContentDraggable(){
		return this.flagComponentDraggable;
	}
	
	public void setJComponentContentDraggable(boolean bValue){
		this.flagComponentDraggable=bValue;
	}
	
	public String getText4ButtonOk(){
		return this.sText4ButtonOk;
	}
	public void setText4ButtonOk(String sText){
		this.sText4ButtonOk = sText;
	}
	
	public String getText4ButtonCancel(){
		return this.sText4ButtonCancel;
	}
	public void setText4ButtonCancel(String sText){
		this.sText4ButtonCancel = sText;
	}
	
	public abstract KernelJPanelCascadedZZZ getPanelButton();
	public abstract KernelJPanelCascadedZZZ getPanelContent() throws ExceptionZZZ;
	
	
	/** Kann von einer Dialogbox �berschrieben werden, wenn ein anderes Panel als das "Default" Panel verwendet werden soll.
	* @return
	* 
	* lindhaueradmin; 11.01.2007 08:27:54
	 */
	public KernelJPanelCascadedZZZ getPanelButtonDefault(){
		KernelJPanelCascadedZZZ panel = new KernelJPanelDialogButtonDefaultZZZ(this.getKernelObject(), this, this.isButtonOkAvailable(), this.isButtonCancelAvailable());
		return panel;
	}
	public KernelJPanelCascadedZZZ getPanelContentDefault(){
		KernelJPanelCascadedZZZ panel = new KernelJPanelDialogContentDefaultZZZ(this.getKernelObject(), this, this.getText4ContentDefault());
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
	 * @see basic.zKernelUI.IPanelDialogZZZ#isCentered()
	 */
	public boolean isCentered() {		
		return true;
	}
}
