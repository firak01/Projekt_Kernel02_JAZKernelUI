package basic.zKernelUI.component.tray;

import java.awt.event.ActionEvent;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;

/** Der Icon unter Windows in der TaskLeiste.
 *  Aus ihm heraus werden:
 *  - ueber ein wechselndes Icon der aktuelle Status angezeigt.
 *  - die einzelnen Schritte gestartet
 *  - der Status im Detail angezeigt
 *  
 *  Merke: 
 *  Dieser Tray ist an den verschiedenen Monitor-Objekten, die den LocalStatus nutzen registriert.
 *  Er reagiert auf Events, die er empfaengt.
 *  Selbst hat der ClientTray keinen LocalStatus und feuert daher auch keine Events ab.
 *  
 * @author Fritz Lindhauer, 11.10.2023, 07:46:15
 * 
 */
public class ActionTrayUIZZZ extends AbstractKernelActionTrayZZZ {
	private static final long serialVersionUID = 1004331678604454588L;

	public ActionTrayUIZZZ(IKernelZZZ objKernel, ITrayZZZ objTrayParent) throws ExceptionZZZ{
		super(objKernel, objTrayParent);
		ActionTrayUINew_();
	}
		
	private boolean ActionTrayUINew_() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
			
			bReturn = true;
		}//END main
		return bReturn;
	}
	
	
	
	public boolean start() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			//BEISPIEL: 
			
			//Wenn das so ohne Thread gestartet wird, dann reagiert der Tray auf keine weiteren Clicks.
			//Z.B. den Status anzuzeigen.			
			//Den Staart ueber einen extra Thread durchfuehren, damit z.B. das Anclicken des SystemTrays mit der linken Maustaste weiterhin funktioniert !!!			
			//Thread objThreadMain = new Thread(this.getMainObject());
			//objThreadMain.start();
			
			bReturn = true;
		}//end main:
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
			bReturn = this.getTrayParent().unload();
		}
		return bReturn;
	}
					
		
	//#######################
	//### GETTER / SETTER
	
	@Override
	public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		try{
				String sCommand = ae.getActionCommand();
				//System.out.println("Action to perform: " + sCommand);
				if(sCommand.equals(ITrayMenuZZZ.TrayMenuTypeZZZ.END.getMenu())){
					this.unload();	
				}else if(sCommand.equals(ITrayMenuZZZ.TrayMenuTypeZZZ.START.getMenu())){
					this.start();				
				}
				
				bReturn = true;
		}catch(ExceptionZZZ ez){
			//Merke: diese Exception hier abhandeln. Damit das ImageIcon wieder zur�ckgesetzt werden kann.				
			ez.printStackTrace();
			String stemp = ez.getDetailAllLast();
			this.getKernelObject().getLogObject().writeLineDate(stemp);
			System.out.println(stemp);
			this.getTrayParent().switchStatus(TrayStatusMappedValueZZZ.TrayStatusTypeZZZ.ERROR);			
		}
		
		}//end main:
		return bReturn;
	}
		
		
		@Override
		public boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ {
			return true;
		}

		@Override
		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ {
			
		}

		@Override
		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) throws ExceptionZZZ {
			
		}
		
}//END Class
