package basic.zKernelUI.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import basic.zKernel.KernelZZZ;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public abstract class KernelActionCascadedZZZ extends KernelUseObjectZZZ  implements ActionListener, IButtonEventZZZ {
	private KernelJPanelCascadedZZZ panelParent;
	
	
	public KernelActionCascadedZZZ(KernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent){
		super(objKernel);
		this.panelParent = panelParent;	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)        
	 */
	public final void actionPerformed(ActionEvent ae) {
		try{
			boolean bQueryResult = this.actionPerformQueryCustom(ae); //Durch �berschreiben diesr Methode k�nnen erbende Klassen noch anderen Code ausf�hren			
			boolean bPerformResult = this.actionPerformCustom(ae, bQueryResult);  //Durch �berschreiben dieser Methode k�nnen erbende Klassen noch anderen Code ausf�hren
			if(bPerformResult == true){
				this.actionPerformPostCustom(ae, bQueryResult);
			}
		} catch (ExceptionZZZ ez) {
			//Protokolliern des Fehlers an allen m�glichen Stellen
			this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			System.out.println(ez.getDetailAllLast());
			ez.printStackTrace();
			
			//Das ist z.B. sinnvoll um eine Fehlermeldung in einem Feld eines UIClients sichtbar zu machen
			this.actionPerformCustomOnError(ae, ez);
		}
	}

	public KernelJPanelCascadedZZZ getPanelParent() {
		return this.panelParent;
	}

	public void setPanelParent(KernelJPanelCascadedZZZ objPanel) {
		this.panelParent = objPanel;
	}

	/** JPanel, searches STARTING FROM THE PARENT PANEL all parent panels untill no more parent panel is available. The last found panel seems to be the root panel for all cascaded panels. 
	* Lindhauer; 15.05.2006 09:00:08
	 * @return
	 */
	public JPanel searchPanelRoot(){
		JPanel panelReturn = null;
		main:{
			if(this.panelParent==null){
				break main;
			}else{
				//!!! Rekursiver aufruf
				KernelJPanelCascadedZZZ panelParent = (KernelJPanelCascadedZZZ) this.panelParent;
				panelReturn = panelParent.searchPanelRoot();
			}
		}
		return panelReturn;
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
	public abstract void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez);
	
}
