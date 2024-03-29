package basic.zKernelUI.component.tray;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.KernelUIZZZ;

public abstract class AbstractKernelActionTrayZZZ extends AbstractKernelUseObjectZZZ  implements IActionTrayZZZ {
	protected volatile ITrayZZZ trayParent;
	
	public AbstractKernelActionTrayZZZ(IKernelZZZ objKernel, ITrayZZZ trayParent) throws ExceptionZZZ{
		super(objKernel);
		this.setTrayParent(trayParent);
	}
		
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)        
	 */
	public final void actionPerformed(ActionEvent ae) {
		try{
			boolean bQueryResult = this.actionPerformQueryCustom(ae); //Durch Ueberschreiben diesr Methode koennen erbende Klassen noch anderen Code ausfuehren			
			boolean bPerformResult = this.actionPerformCustom(ae, bQueryResult);  //Durch Ueberschreiben dieser Methode koennen erbende Klassen noch anderen Code ausfuehren
			if(bPerformResult == true){
				this.actionPerformPostCustom(ae, bQueryResult);
			}
		} catch (ExceptionZZZ ez) {
			try {
				//Protokolliern des Fehlers an allen moeglichen Stellen
				this.getLogObject().WriteLineDate(ez.getDetailAllLast());
				System.out.println(ez.getDetailAllLast());
				ez.printStackTrace();
				
				//Das ist z.B. sinnvoll um eine Fehlermeldung in einem Feld eines UIClients sichtbar zu machen			
				this.actionPerformCustomOnError(ae, ez);
			} catch (ExceptionZZZ e) {	
				System.out.println(ez.getDetailAllLast());
				e.printStackTrace();
			}
		}
	}

	public void setTrayParent(ITrayZZZ objTray) {
		this.trayParent = objTray;
	}
	
	public ITrayZZZ getTrayParent() {
		return this.trayParent;
	}
	
	

	
	/* Das ist eine Action innerhalb eines Panels. Also ist der Modulname ggfs. die Klasse des Panels.
	 * (non-Javadoc)
	 * @see basic.zKernel.KernelUseObjectZZZ#getModuleUsed()
	 */
	public String getModuleUsed(){
		String sReturn = new String("");
		main:{
			try{								
				if(this.getContextUsed() == null){													
					sReturn = KernelUIZZZ.getModuleUsedName(this);									
				}else{
					sReturn = this.getContextUsed().getProgramName();
				}
			} catch (ExceptionZZZ ez) {				
				ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());
			}
		}//end main
	return sReturn;
	}
	
	/* Das ist eine Action innerhalb eines Panels. Also ist der Programname ggfs. nicht der Name des Buttons, sondern ggfs. die Klasse des Panels.
	 * (non-Javadoc)
	 * @see basic.zKernel.KernelUseObjectZZZ#getProgramUsed()
	 */
	public String getProgramUsed(){
		String sReturn = new String("");
		main:{
			try{
				sReturn = KernelUIZZZ.getProgramUsedName(this);				
			} catch (ExceptionZZZ ez) {				
				ReportLogZZZ.write(ReportLogZZZ.ERROR, ez.getDetailAllLast());
			}
		}//end main
	return sReturn;
	}

	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IButtonEventZZZ#actionPerformCustom(java.awt.event.ActionEvent)
	 * 
	 * 
	 * Besser diese Methode abstract zu machen und die actionPerformed Methode gleichzeitig final zu machen.
	 * Als einen ExceptionZZZ - Hinweis auszugeben. Wie z.B.:
	 * 
	 	//Hinweis darauf, dass diese Methode �berschrieben werden muss !!!
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_ZFRAME_METHOD, iERROR_ZFRAME_METHOD, this, ReflectionZZZ.getMethodCurrentName()); 
			throw ez;
	 * 
	 * 
	 */
	public abstract boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ; 	
	
	
	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IButtonEventZZZ#actionPerformQueryCustom(java.awt.event.ActionEvent)
	 * 
	 * Besser diese Methode abstract zu machen und die actionPerformed Methode gleichzeitig final zu machen.
	 * Als einen ExceptionZZZ - Hinweis auszugeben. Wie z.B.:
	 * 
	 	//Hinweis darauf, dass diese Methode �berschrieben werden muss !!!
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_ZFRAME_METHOD, iERROR_ZFRAME_METHOD, this, ReflectionZZZ.getMethodCurrentName()); 
			throw ez;
	 * 
	 * 
	 */
	public abstract boolean actionPerformQueryCustom(ActionEvent ae) throws ExceptionZZZ;

	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IButtonEventZZZ#actionPerformPostCustom(java.awt.event.ActionEvent)
	 * 
	 * Besser diese Methode abstract zu machen und die actionPerformed Methode gleichzeitig final zu machen.
	 * Als einen ExceptionZZZ - Hinweis auszugeben. Wie z.B.:
	 * 
	 	//Hinweis darauf, dass diese Methode �berschrieben werden muss !!!
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_ZFRAME_METHOD, iERROR_ZFRAME_METHOD, this, ReflectionZZZ.getMethodCurrentName()); 
			throw ez;
	 * 
	 * 
	 */
	public abstract void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ;

	/* (non-Javadoc)
	 * @see basic.zKernelUI.component.IButtonEventZZZ#actionPerformCustomOnError(java.awt.event.ActionEvent, basic.zBasic.ExceptionZZZ)
	 */
	public abstract void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) throws ExceptionZZZ;
	
}
