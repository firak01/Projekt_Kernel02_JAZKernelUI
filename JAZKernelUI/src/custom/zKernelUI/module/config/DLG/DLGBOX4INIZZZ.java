package custom.zKernelUI.module.config.DLG;


import javax.swing.JComponent;
import javax.swing.JFrame;

import basic.zBasic.IJNILotusscriptCallableZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.util.JFrameHelperZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public class DLGBOX4INIZZZ  extends KernelJFrameCascadedZZZ  implements IJNILotusscriptCallableZZZ {
	protected IKernelZZZ objKernelChoosen;  //Merke: Das wird erst im launch() mitgegeben !!!
	
	public DLGBOX4INIZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
		super(objKernel, frameParent);
		this.setTitle("FGL non modale dialogbox: Started by using kernel-construktor and method .launch(...)");  //wenn denn mal .launch(...) ausgeführt wird
	}
	

	/* (non-Javadoc)
	 * @see basic.zzzKernel.KernelAssetJNILotusscriptCallableZZZ#lsimain(java.lang.String[])
	 */
	public boolean lsimain(String[] argv) {
		//!!! Diese Funktion soll von LotusScript aus aufgerufen werden, darum keinen Error in Funktionssignatur throwen !!!
		try {

			setTitle("FGL non modal dialogbox: Started with lsimain-function");
		
			//Aus Notes heraus gestartet funktioniert EXIT_ON_CLOSE nicht
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
			//Das Fenster nun starten
			launch(argv);
	
		} catch (ExceptionZZZ ez) {
			this.getLogObject().WriteLineDate(ez.getMessageLast());
			return false;
		}		
		return true;
	}
	
	
	/** boolean, launches a frame used as dialogbox. The launched frame displays the content of the module provided by saModule. 
	* Lindhauer; 03.05.2006 09:08:29
	 * @param argv
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public boolean launch(String[] saModule) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sSystemKey2Config=new String("");
			String sApplication2Config = new String("");
			String sModule2Config = new String("");	
			String sSystemNumber2Config = new String("");
			if(saModule!=null){
				sSystemKey2Config = saModule[0];				
				sModule2Config = saModule[1];
				
				sApplication2Config = StringZZZ.left(sSystemKey2Config, "!");
				sSystemNumber2Config = StringZZZ.right(sSystemKey2Config, "!");
			}
			
			//Programme sollen erst auf Modulebenen konfigurierbar sein, trotzdem ein moeglicherweise wertvoller Parameter.
			String sProgram = new String("");
			
			//Ein Kernel-Objekt fuer das zu startende Modul erstellen.
			//20211201: Problem: Normalerweise werden die Ini-Einträge übersetzt. D.h. aus <z:Null/> oder <z:Empty> wird ein Leerstring.
			//                   Das Ziel ist aber beim Speichern den gleichen Wert zurückzuschreiben.
			//this.objKernelChoosen = new KernelZZZ(sApplication, sSystemNumber, this.getKernelObject(),null);
			
			//20211201: Die im ausgewählten Kernel-Objekt gefundenen ini-Datei Werte sollen ja nur angezeigt und nicht umgewandelt werden
			IKernelZZZ objKernelConfig = this.getKernelObject(); //Damit bekommt man das aktuelle Objekt als Vorlage und vererbt Log, etc....
			String[] saArg = {"-z","{\"USEEXPRESSION\":false}"};	
			IKernelZZZ objKernelChoosen = new KernelZZZ(sApplication2Config, sSystemNumber2Config, objKernelConfig, saArg, null);
			this.objKernelChoosen = objKernelChoosen;
			
			
			//Grundflaeche in den Rahmen hinzufuegen...
			Panel_DLGBOXZZZ objPanel = new Panel_DLGBOXZZZ(this.getKernelObject(),this, this.objKernelChoosen, sModule2Config, sProgram);
			
			//... das wird nun �ber das ContentPane der Frames gemacht
			this.getContentPane().add(objPanel);
			this.setPanelSub("ContentPane", objPanel);
						
			//... versuche nicht sichtbare Komponenten "zu packen"
			this.pack();
			
			//... sichtbar machen erst, nachdem alle Elemente im Frame hinzugefuegt wurden !!!
			//depreciated in 1.5 frame.show();
			//statt dessen...
			setVisible(true);
			bReturn = true;

		}//END main:
		return bReturn;
	}


	public boolean launchCustom() throws ExceptionZZZ {	
		return false;
	}


	public KernelJPanelCascadedZZZ getPaneContent() {
		// TODO Auto-generated method stub
		//TODO: Hier muss noch das gew�nschte Panel mit new() erzeugt werden
		//           und dann ggf. der Hashtable unter den Alias "panelcontent" hinzugef�gt werden.
		return null;
	}


	public JComponent getPaneContent(String sAlias) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		//Hier wird nix in einen anderen Pane als den ContentPane gestellt.
		return null;
	}


	@Override
	public boolean setSizeDefault() throws ExceptionZZZ {
		JFrameHelperZZZ.setSizeDefault(this);
		return true;
	}

	
	//#########################
	// Methoden implementiert durch Schnittstellen	

}
