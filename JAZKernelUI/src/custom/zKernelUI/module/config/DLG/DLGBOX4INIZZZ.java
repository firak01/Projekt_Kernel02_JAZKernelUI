package custom.zKernelUI.module.config.DLG;


import javax.swing.JComponent;
import javax.swing.JFrame;

import basic.zBasic.IJNILotusscriptCallableZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.util.JFrameHelperZZZ;
import basic.zKernel.KernelZZZ;

public class DLGBOX4INIZZZ  extends KernelJFrameCascadedZZZ  implements IJNILotusscriptCallableZZZ {
	protected KernelZZZ objKernelChoosen;  //Merke: Das wird erst im launch() mitgegeben !!!
	
	public DLGBOX4INIZZZ(KernelZZZ objKernel, KernelJFrameCascadedZZZ frameParent) throws ExceptionZZZ{
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
			String sSystemKey=new String("");
			String sApplication = new String("");
			String sModule = new String("");	
			String sSystemNumber = new String("");
			if(saModule!=null){
				sSystemKey = saModule[0];				
				sModule = saModule[1];
				
				sApplication = StringZZZ.left(sSystemKey, "#");
				sSystemNumber = StringZZZ.right(sSystemKey, "#");
			}
			
			//Programme sollen erst auf Modulebenen konfigurierbar sein, trotzdem ein m�glicherweise wertvoller Parameter.
			String sProgram = new String("");
						
			//TEST: LogObjekt
			//LogZZZ objLogJAZ = new LogZZZ("d:\\tempfgl", "Testnow.txt");
			
			//Ein Kernel-Objekt f�r das zu startende Modul erstellen.
			this.objKernelChoosen = new KernelZZZ(sApplication, sSystemNumber, this.getKernelObject(),null);
						
			//Grundfl�che in den Rahmen hinzuf�gen...
			Panel_DLGBOXZZZ objPanel = new Panel_DLGBOXZZZ(this.getKernelObject(),this, this.objKernelChoosen, sModule, sProgram);
			
			//... das wird nun �ber das ContentPane der Frames gemacht
			this.getContentPane().add(objPanel);
			this.setPanelSub("ContentPane", objPanel);
						
			//... sichtbar machen erst, nachdem alle Elemente im Frame hinzugef�gt wurden !!!
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
