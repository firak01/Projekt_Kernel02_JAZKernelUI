package basic.zKernelUI.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernelUI.KernelUIZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ReportLogZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

public abstract class KernelActionCascadedZZZ extends AbstractKernelUseObjectZZZ  implements ActionListener, IButtonEventZZZ, IActionCascadedZZZ {
	private IPanelCascadedZZZ panelParent;
	
	
	public KernelActionCascadedZZZ(IKernelZZZ objKernel, IPanelCascadedZZZ panelParent) throws ExceptionZZZ{
		super(objKernel);
		this.panelParent = panelParent;	
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
				//Protokolliern des Fehlers an allen m�glichen Stellen
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

	public IPanelCascadedZZZ getPanelParent() {
		return this.panelParent;
	}

	public void setPanelParent(IPanelCascadedZZZ objPanel) {
		this.panelParent = objPanel;
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


	/*  START FROM THE PARENT PANEL
	 * (non-Javadoc)
	 * @see basic.zKernelUI.IPanelCascadedZZZ#getFrameParent()
	 */
	public KernelJFrameCascadedZZZ getFrameParent() {
		KernelJFrameCascadedZZZ frameReturn = null;
		main:{
		if(this.panelParent==null){
			break main;					
		}else{
			KernelJPanelCascadedZZZ panelParent =(KernelJPanelCascadedZZZ) this.panelParent;
			frameReturn = panelParent.getFrameParent();			
		}
		}//END main:
		return frameReturn;
	}

	/* START FROM THE PARENT PANEL
	 * (non-Javadoc)
	 * @see basic.zKernelUI.IPanelCascadedZZZ#setFrameParent(javax.swing.JFrame)
	 */
	public void setFrameParent(KernelJFrameCascadedZZZ frameParent) {
		if(this.panelParent!=null){
			KernelJPanelCascadedZZZ panelParent =(KernelJPanelCascadedZZZ) this.panelParent;
			panelParent.setFrameParent(frameParent);			
		}
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
