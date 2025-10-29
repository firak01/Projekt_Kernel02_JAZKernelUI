package basic.zKernelUI.component.tray;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ResourceEasyZZZ;
import basic.zKernel.AbstractKernelUseObjectOnStatusListeningZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernelUI.component.tray.ITrayMenuZZZ.TrayMenuTypeZZZ;
import basic.zKernelUI.component.tray.ITrayStatusMappedValueZZZ.TrayStatusTypeZZZ;

public abstract class AbstractKernelTrayUIZZZ extends AbstractKernelUseObjectOnStatusListeningZZZ implements ITrayZZZ {		
	private static final long serialVersionUID = 4170579821557468353L;
		
	protected volatile SystemTray objTray = null;                                    //Das gesamte SystemTray von Windows
	protected volatile TrayIcon objTrayIcon = null; //Das TrayIcon dieser Application
	protected volatile JPopupMenu objMenu = null;
	protected volatile IActionTrayZZZ objActionListener = null;
		
	public AbstractKernelTrayUIZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);
		TrayUINew_();
	}
		
	private void TrayUINew_() throws ExceptionZZZ{
		main:{
			if(this.getFlag("init")) break main;
			
			//Dieses muss beim Beenden angesprochen werden, um das TrayIcon wieder zu entfernen
			//Merke 20220718: Wohl unter Win10 nicht lauffähig
			TrayIcon objTrayIcon = this.getTrayIcon();
			
			IActionTrayZZZ objActionListener = this.getActionListenerTrayIcon();
			objTrayIcon.addActionListener((ActionListener) objActionListener);
			
			SystemTray objTray = this.getSystemTray();
			objTray.addTrayIcon(objTrayIcon);
			//Merke: Ueber unload() wird das TrayIcon wieder entfernt.
			
			//Den Process-Monitor auch schon vorbereiten, auch wenn ggfs. nicht schon am Anfang auf die Verbindung "gelistend" wird.
			//Er wird später auch am Backend-Objekt registriert, um dort Änderungen mitzubekommen.
			String sLog = ReflectCodeZZZ.getPositionCurrent() + ": Creating ServerMonitorRunner-Object";
			System.out.println(sLog);
			this.getLogObject().WriteLineDate(sLog);
			
			//### Registriere das Tray-Objekt selbst an ein mögliches Main-Objekt ##############
			//a) Fuer Aenderungen an den Main-Objekt-Flags. Das garantiert, das der Tray auch auf Änderungen der Flags reagiert, wenn ServerMain in einem anderen Thread ausgeführt wird.			
			//this.getMainObject().registerForFlagEvent(this);
			
			//b) Fuer Aenderung am Main-Objekt-Status. Das garantiert, das der Tray auch auf Änderungen des Status reagiert, wenn ServerMain in einem anderen Thread ausgeführt wird.
			//this.getMainObject().registerForStatusLocalEvent(this);
		}//END main
	}
		
	@Override
	public ImageIcon getImageIconByStatus(IEnumSetMappedZZZ enumMappedStatus)throws ExceptionZZZ{
		ITrayStatusMappedValueZZZ.TrayStatusTypeZZZ enumSTATUS = (TrayStatusTypeZZZ) enumMappedStatus;
		return AbstractKernelTrayUIZZZ.getImageIconByStatus(enumSTATUS);
	}
	
	@Override
	public String getCaptionByStatus(IEnumSetMappedZZZ enumMappedStatus)throws ExceptionZZZ{
		ITrayStatusMappedValueZZZ.TrayStatusTypeZZZ enumSTATUS = (TrayStatusTypeZZZ) enumMappedStatus;
		return AbstractKernelTrayUIZZZ.getCaptionByStatus(enumSTATUS);
	}
	
	public static ImageIcon getImageIconByStatus(ITrayStatusMappedValueZZZ.TrayStatusTypeZZZ enumSTATUS) throws ExceptionZZZ {
		ImageIcon objReturn = null;
		main:{
			URL url = null;
			ClassLoader objClassLoader = AbstractKernelTrayUIZZZ.class.getClassLoader(); 
			if(objClassLoader==null) {
				ExceptionZZZ ez = new ExceptionZZZ("unable to receiver classloader object", iERROR_RUNTIME, AbstractKernelTrayUIZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			String sPath= ResourceEasyZZZ.searchDirectoryAsStringRelative("resourceZZZ/image/tray"); //Merke: Innerhalb einer JAR-Datei soll hier ein src/ vorangestellt werden.					
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Using path for directory '"+sPath+"'");
						
			String sImageIcon = enumSTATUS.getIconFileName();
			String sPathTotal = FileEasyZZZ.joinFilePathNameForUrl(sPath, sImageIcon);
			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Using path for imageicon '"+sPathTotal+"'");			
			url= ClassLoader.getSystemResource(sPathTotal);
			if(url==null) {
				String sLog = "unable to receive url object. Path '" + sPathTotal + "' not found?";
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_RUNTIME, AbstractKernelTrayUIZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": URL = '"+url.toExternalForm() + "'");
			}
			objReturn = new ImageIcon(url);
		}//END main:
		return objReturn;
	}
	
	public static String getCaptionByStatus(ITrayStatusMappedValueZZZ.TrayStatusTypeZZZ enumSTATUS) throws ExceptionZZZ {
		String sReturn = null;
		main:{
						
			String sCaption = enumSTATUS.getCaption();
			sReturn = sCaption;
		}//END main:
		return sReturn;
	}
			
	@Override
	public boolean switchStatus(IEnumSetMappedZZZ objEnumStatusMappedIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: In den nutzenden Klassen dies Methode ueberschreiben und hier 
			//       einen TypCast auf das konkrete Enum verwenden
			IEnumSetMappedZZZ enumSTATUS = objEnumStatusMappedIn;
			
			//ImageIcon aendern
			ImageIcon objIcon = this.getImageIconByStatus(enumSTATUS);
			if(objIcon==null)break main;
						
			//+++++ Test: Logge den Menüpunkt			
			TrayStatusTypeZZZ objEnumStatus = (TrayStatusTypeZZZ) enumSTATUS;
			TrayMenuTypeZZZ objEnumMenu = (TrayMenuTypeZZZ) objEnumStatus.getAccordingTrayMenuType();
			if(objEnumMenu!=null){
				String sLog = ReflectCodeZZZ.getPositionCurrent() +": Menuepunkt=" + objEnumMenu.getMenu();
				System.out.println(sLog);
				this.logLineDate(sLog);
			}else {
				String sLog = ReflectCodeZZZ.getPositionCurrent() +": Kein Menuepunkt vorhanden.";
				System.out.println(sLog);
				this.logLineDate(sLog);
			}
			//++++++++++++++++++++++++++++++++
				
			this.getTrayIcon().setIcon(objIcon);
			
			String sLabel = this.getCaptionByStatus(enumSTATUS);
			this.getTrayIcon().setCaption(sLabel);
			
			bReturn = true;
		}//END main:
		return bReturn;
	}
	

	
	

	/**Loads an icon in the systemtray. 
	 * Right click on the item to show available menue entries.
	 * @return boolean
	 *
	 * javadoc created by: 0823, 11.07.2006 - 13:03:47
	 * @throws ExceptionZZZ 
	 */
	public boolean load() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
			this.getSystemTray().addTrayIcon(this.getTrayIcon());
			bReturn = true;
		}//END main:
		return bReturn;
	}

	

	/**Removes the icon from the systemtray
	 * AND removes any running "openvpn.exe" processes. (or how the exe-file is named)
	 * @return boolean
	 *
	 * javadoc created by: 0823, 11.07.2006 - 13:05:25
	 * @throws ExceptionZZZ 
	 */
	public boolean unload() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//+++ Merke1: Natuerlich muessen hier ggf. noch weitere Sachen gemacht werden, z.B. Threads beenden oder externe Processe (z.B. OVPN) killen.			
			
			//+++ Merke2: Windows merkt das Entfernen nicht sofort, sondern manchmal erst, wenn man den Mauszeiger auf den System-Tray-Bereich bewegt.
			this.objTray.removeTrayIcon(this.objTrayIcon);
			
			bReturn = true;
			System.exit(0);
		}
		return bReturn;
	}
	
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{						
			//Wenn das so ohne Thread gestartet wird, dann reagiert der Tray auf keine weiteren Clicks.
			//Z.B. den Status anzuzeigen.
			//this.getServerBackendObject().start(this);
			
			//Also dies über einen extra thread tun, damit z.B. das Anclicken des SystemTrays mit der linken Maustaste weiterhin funktioniert !!!
			//Problem dabei: Der SystemTray wird nicht aktualisiert.
			
			//Beispiel:
			//Thread objThreadMain = new Thread(this.getMainObject());
			//objThreadMain.start();
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//#######################
	//### GETTER / SETTER
	
	@Override 
	public SystemTray getSystemTray() throws ExceptionZZZ{
		if(this.objTray == null) {
			this.objTray = SystemTray.getDefaultSystemTray();
		}
		return this.objTray;
	}
	
	@Override
	public void setSystemTray(SystemTray objTray) {
		this.objTray = objTray;
	}
	
	@Override
	public TrayIcon getTrayIcon() throws ExceptionZZZ{
		if(this.objTrayIcon==null) {
			JPopupMenu menu = this.getMenu();
			ImageIcon objIcon = AbstractKernelTrayUIZZZ.getImageIconByStatus(TrayStatusTypeZZZ.NEW);
			String sLabelTray = AbstractKernelTrayUIZZZ.getCaptionByStatus(TrayStatusTypeZZZ.NEW);
			this.objTrayIcon = new TrayIcon(objIcon, sLabelTray, menu);
		}
		return this.objTrayIcon;
	}
	
	@Override
	public void setTrayIcon(TrayIcon objTrayIcon) {
		this.objTrayIcon = objTrayIcon;
	}
	
	//+++ Aus IListenerObjectFlagZsetZZZ
	@Override
	public boolean flagChanged(IEventObjectFlagZsetZZZ eventFlagZset) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			Enum objFlagEnum = eventFlagZset.getFlagEnum();
			if(objFlagEnum==null) break main;
			
			boolean bFlagValue = eventFlagZset.getFlagValue();
			if(bFlagValue==false)break main; //Hier interessieren nur "true" werte, die also etwas neues setzen.

		}//end main:
		return bReturn;
	}

	//+++ Aus IListenerObjectStatusLocalOVPN	
	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
				//Der Tray ist am MainObjekt registriert.
				//Wenn ein Event geworfen wird, dann reagiert er darauf, hiermit....
		boolean bReturn=false;
		main:{
		//Falls nicht zuständig, mache nix
	    boolean bProof = this.isEventRelevant2ChangeStatusLocal(eventStatusLocal);
		if(!bProof) break main;
		
		String sLog=null;
		
		//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
		IEnumSetMappedZZZ enumStatus = eventStatusLocal.getStatusLocal();
		if(enumStatus==null) {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Keinen Status aus dem Event-Objekt erhalten. Breche ab";
			System.out.println(sLog);
			this.logLineDate(sLog);
			break main;
		}
		
		//+++++++++++++++++++++
		//HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ>hmEnum = this.getHashMapEnumSetForCascadingStatusLocal();				
		HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ>hmEnum = this.getHashMapStatusLocal4Reaction_EnumStatus();
		if(hmEnum==null) {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Keine Mapping Hashmap fuer das StatusMapping vorhanden. Breche ab";
			System.out.println(sLog);
			this.logLineDate(sLog);
			break main;
		}
		
		//+++++++++++++++++++++
		
		IEnumSetMappedStatusZZZ objEnum = hmEnum.get(enumStatus);							
		if(objEnum==null) {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Keinen gemappten Status für en Status aus dem Event-Objekt erhalten. Breche ab";					
			this.logProtocolString(sLog);
			break main;
		}
		
		
		
		//Nur so als Beispiel, muss ueberschrieben werden:
		//Lies den Status (geworfen vom Backend aus)
		
		/* Loesung: DOWNCASTING mit instanceof , s.: https://www.positioniseverything.net/typeof-java/
	 	class Animal { }
		class Dog2 extends Animal {
			static void method(Animal j) {
			if(j instanceof Dog2){
			Dog2 d=(Dog2)j;//downcasting
			System.out.println(“downcasting done”);
			}
			}
			public static void main (String [] args) {
			Animal j=new Dog2();
			Dog2.method(j);
			}
		}
		*/
		if(eventStatusLocal instanceof IEventObjectStatusLocalZZZ) {
			IEventObjectStatusLocalZZZ eventStatusLocalReact = (IEventObjectStatusLocalZZZ) eventStatusLocal;					
			String sStatus = eventStatusLocalReact.getStatusMessage();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Methode muss ueberschrieben werden.");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sStatus='"+sStatus+"'");
		}else {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Event ist kein instanceof IEventObjectStatusLocalZZZ. Breche ab.";					
			this.logProtocolString(sLog);
		}
		
	}//end main:
	return bReturn;		
		
					
					
					/* Loesung: DOWNCASTING mit instanceof , s.: https://www.positioniseverything.net/typeof-java/
				 	class Animal { }
					class Dog2 extends Animal {
						static void method(Animal j) {
						if(j instanceof Dog2){
						Dog2 d=(Dog2)j;//downcasting
						System.out.println(“downcasting done”);
						}
						}
						public static void main (String [] args) {
						Animal j=new Dog2();
						Dog2.method(j);
						}
					}
				 */
					
					//Beispiel: 
					//+++ Mappe nun die eingehenden Status-Enums auf die eigenen
//					if(eventStatusLocalSet.getStatusEnum() instanceof IServerMainOVPN.STATUSLOCAL){					
//						bReturn = this.statusLocalChangedMainEvent_(eventStatusLocalSet);
//						break main;
//						
//					}
//					
//					else if(eventStatusLocalSet.getStatusEnum() instanceof IClientThreadProcessWatchMonitorOVPN.STATUSLOCAL) {
//						System.out.println(ReflectCodeZZZ.getPositionCurrent() +" :FGLTEST 02");
//						bReturn = this.statusLocalChangedMonitorEvent_(eventStatusLocalSet);
//						break main;
//					}
					
	}
	
	@Override
	public boolean isEventRelevantAny(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null)break main;
			
			bReturn = this.isEventRelevant2ChangeStatusLocal(eventStatusLocal);
			if(bReturn) break main;
			
			bReturn = this.isEventRelevant4ReactionOnStatusLocal(eventStatusLocal);
			if(bReturn) break main;
			
		}//end main:
		return bReturn;
	}
	
	
	@Override
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null)break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": Pruefe Relevanz des Events.";
			System.out.println(sLog);
			this.logLineDate(sLog);
			
			if(eventStatusLocal instanceof IEventObjectStatusLocalZZZ) {				
				IEnumSetMappedZZZ enumStatusFromEvent = ((IEventObjectStatusLocalZZZ) eventStatusLocal).getStatusLocal();				
				if(enumStatusFromEvent==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": KEINEN enumStatus empfangen. Beende.";
					System.out.println(sLog);
					this.logLineDate(sLog);							
					break main;
				}
				
				boolean bStatusValue = eventStatusLocal.getStatusValue();
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Einen enumStatus empfangen. Wert: " + bStatusValue;
				System.out.println(sLog);
				this.logLineDate(sLog);
					
				sLog = ReflectCodeZZZ.getPositionCurrent()+": enumFromEventStatus hat class='"+enumStatusFromEvent.getClass()+"'";
				System.out.println(sLog);
				this.logLineDate(sLog);	
					
				sLog = ReflectCodeZZZ.getPositionCurrent()+": enumFromEventStatus='" + enumStatusFromEvent.getAbbreviation()+"'";
				System.out.println(sLog);
				this.logLineDate(sLog);
				
				
				//#### Problemansatz: Mappen des Lokalen Status auf einen Status aus dem Event, verschiedener Klassen.
				String sStatusAbbreviationLocal = null;
				IEnumSetMappedZZZ objEnumStatusLocal = null;
	
				HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedZZZ>hm=this.createHashMapStatusLocal4ReactionCustom_Enum();
				objEnumStatusLocal = hm.get(enumStatusFromEvent);					
				//###############################
				
				if(objEnumStatusLocal==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Klasse '" + enumStatusFromEvent.getClass() + "' ist im Mapping nicht mit Wert vorhanden. Damit nicht relevant.";
					System.out.println(sLog);
					this.logLineDate(sLog);
					break main;
					//sStatusAbbreviationLocal = enumStatusFromEvent.getAbbreviation();
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Klasse '" + enumStatusFromEvent.getClass() + "' ist im Mapping mit Wert vorhanden. Damit relevant.";
					System.out.println(sLog);
					this.logLineDate(sLog);
					
					sStatusAbbreviationLocal = objEnumStatusLocal.getAbbreviation();
				}
				
				//+++ Pruefungen
				bReturn = this.isEventRelevant2ChangeStatusLocalByClass(eventStatusLocal);
				if(!bReturn) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Event werfenden Klasse ist fuer diese Klasse hinsichtlich eines Status nicht relevant. Breche ab.";
					System.out.println(sLog);
					this.logLineDate(sLog);				
					break main;
				}
				
	//für den Tray nicht relevant
	//			bReturn = this.isStatusLocalChanged(sStatusAbbreviationLocal, bStatusValue);
	//			if(!bReturn) {
	//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Status nicht geaendert. Breche ab.";
	//				System.out.println(sLog);
	//				this.getMainObject().logProtocolString(sLog);
	//				break main;
	//			}else {
	//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Status geaendert. Mache weiter.";
	//				System.out.println(sLog);
	//				this.getMainObject().logProtocolString(sLog);
	//			}
				
				//dito gibt es auch die Methode isStatusLocalRelevant(...) nicht, da der Tray kein AbstractObjectWithStatus ist, er verwaltet halt selbst keinen Status.
				
							
				bReturn = this.isEventRelevant2ChangeStatusLocalByStatusLocalValue(eventStatusLocal);
				if(!bReturn) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Statuswert nicht relevant. Breche ab.";
					System.out.println(sLog);
					this.logLineDate(sLog);				
					break main;
				}
				
			}//end if instanceof
						
			bReturn = true;
		}//end main:
		return bReturn;

	}

	@Override
	public boolean isEventRelevant2ChangeStatusLocalByClass(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		return true;
	}

	@Override
	public boolean isEventRelevant2ChangeStatusLocalByStatusLocalValue(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null)break main;
			
			boolean bStatusValue = eventStatusLocal.getStatusValue();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": Einen enumStatus empfangen. Wert: " + bStatusValue;
			System.out.println(sLog);
			this.logLineDate(sLog);	
		
			if(!bStatusValue)break main; //Hier interessieren nur "true" werte, die also etwas neues setzen.
			
			bReturn = true;
		}
		return bReturn;
	}
	
	@Override
	public JPopupMenu getMenu() throws ExceptionZZZ {
		if(this.objMenu==null) {
			this.objMenu = this.createMenuCustom();
		}
		return this.objMenu;	}

	@Override
	public void setMenu(JPopupMenu menu) throws ExceptionZZZ {
		this.objMenu = menu;
		this.getTrayIcon().setPopupMenu(menu);
	}

	@Override
	public IActionTrayZZZ getActionListenerTrayIcon() throws ExceptionZZZ {
		if(this.objActionListener==null) {
			IActionTrayZZZ objActionListener = new ActionTrayUIZZZ(this.getKernelObject(), this);
			this.setActionListenerTrayIcon(objActionListener);
		}
		return this.objActionListener;		
	}

	@Override
	public void setActionListenerTrayIcon(IActionTrayZZZ objActionListener) {
		this.objActionListener = objActionListener;
	}
	
	@Override
	public JPopupMenu createMenuCustom() throws ExceptionZZZ {
		JPopupMenu objReturn = new JPopupMenu();
		main:{
			IActionTrayZZZ objActionListener = this.getActionListenerTrayIcon();
			
			JMenuItem menueeintrag2 = new JMenuItem(ITrayMenuZZZ.TrayMenuTypeZZZ.START.getMenu());
			objReturn.add(menueeintrag2);
			menueeintrag2.addActionListener((ActionListener) objActionListener);
			
			JMenuItem menueeintrag3 = new JMenuItem(ITrayMenuZZZ.TrayMenuTypeZZZ.END.getMenu());
			objReturn.add(menueeintrag3);
			menueeintrag3.addActionListener((ActionListener) objActionListener);
			
			//DUMMY Einträge, sofort erkennen ob z.B. Server / Client
			//DUMMY Einträge, damit der unterste Eintrag ggfs. nicht durch die Windows Taskleiste verdeckt wird
			JMenuItem menueeintragLine = new JMenuItem("------------------");
			objReturn.add(menueeintragLine);
			//Kein actionListener für Dummy Eintrag
						
			JMenuItem menueeintragContext = new JMenuItem("DUMMY EINTRAG");
			objReturn.add(menueeintragContext);
			//Kein actionListener für Dummy Eintrag
			
			JMenuItem menueeintragDummy = new JMenuItem(" ");
			objReturn.add(menueeintragDummy);
			//Kein actionListener für Dummy Eintrag
			
			/* das scheint dann doch nicht notwendig zu sein !!!
			menueeintrag.addMouseListener(new MouseAdapter(){
				public void mouseReleased(MouseEvent me){
					System.out.println("mausi released");
				}
				public void mousePressed(MouseEvent me){
					System.out.println("mausi pressed");
				}
				public void mouseClicked(MouseEvent me){
					System.out.println("mausi clicked");
				}
				
				//Das wird erkannt
				public void mouseEntered(MouseEvent me){
					System.out.println("mausi entered");
				}
				
				//
				public void mouseExited(MouseEvent me){
					System.out.println("mausi exited");
				}
			});
			*/
			
		}//end main:
		return objReturn;
	}
	

	//public abstract HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ> getHashMapEnumSetForCascadingStatusLocal();
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IListenerObjectStatusLocalZZZ#createHashMapStatusLocal4ReactionCustom_EnumStatus()
	 */
	@Override
	public abstract HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ> createHashMapStatusLocal4ReactionCustom_EnumStatus();
	
}//END Class

