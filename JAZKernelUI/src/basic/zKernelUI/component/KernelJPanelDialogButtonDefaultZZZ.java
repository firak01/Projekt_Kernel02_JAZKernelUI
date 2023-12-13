package basic.zKernelUI.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelUserZZZ;

public class KernelJPanelDialogButtonDefaultZZZ extends KernelJPanelCascadedZZZ implements IPanelDialogButtonZZZ{
	//Buttons fuer den JPane	
	private JButton buttonOk = null;
	private JButton buttonCancel = null;
	private JButton buttonClose = null;
	
	//Flags
	private boolean bIsButtonCancelAvailable=true;
	private boolean bIsButtonOkAvailable=true;
	private boolean bIsButtonCloseAvailable=true;
		
	public KernelJPanelDialogButtonDefaultZZZ() throws ExceptionZZZ{
		super();
	}
	
	public KernelJPanelDialogButtonDefaultZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, boolean bIsButtonOkAvailable) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		KernelJPanelDialogButtonDefaultNew_(objKernel, dialogExtended, bIsButtonOkAvailable, false, false);
	}
	
	public KernelJPanelDialogButtonDefaultZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, boolean bIsButtonOkAvailable, boolean bIsButtonCancelAvailable) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		KernelJPanelDialogButtonDefaultNew_(objKernel, dialogExtended, bIsButtonOkAvailable, bIsButtonCancelAvailable, false);
	}
	
	public KernelJPanelDialogButtonDefaultZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, boolean bIsButtonOkAvailable, boolean bIsButtonCancelAvailable, boolean bIsButtonCloseAvailable) throws ExceptionZZZ{
		super(objKernel, dialogExtended);
		KernelJPanelDialogButtonDefaultNew_(objKernel, dialogExtended, bIsButtonOkAvailable, bIsButtonCancelAvailable, bIsButtonCloseAvailable);
	}
	
	private boolean KernelJPanelDialogButtonDefaultNew_(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, boolean bIsButtonOkAvailable, boolean bIsButtonCancelAvailable, boolean bIsButtonCloseAvailable){
		boolean bReturn = false;
		main:{

			//Panel gestalten. in dem uebergeordneten Panel gilt:  Border Layout mit ButtonPanel im SOUTH
			//TODO: ggf. ein Objekt fuer Rueckgabewerte definieren und uebergeben.
			 
			this.bIsButtonCancelAvailable = bIsButtonCancelAvailable;
			this.bIsButtonOkAvailable = bIsButtonOkAvailable;
			this.bIsButtonCloseAvailable = bIsButtonCloseAvailable;
			if(this.isButtonOkAvailable()){
				String sText = dialogExtended.getText4ButtonOk();
				buttonOk = new JButton(sText);
				buttonOk.addActionListener(this.getActionListenerButtonOk(this));
				this.add(buttonOk);
			}
			
			if(this.isButtonCloseAvailable()) {
				String sText = dialogExtended.getText4ButtonClose();
				buttonClose = new JButton(sText);
				buttonClose.addActionListener(this.getActionListenerButtonClose(this));
				this.add(buttonClose);
			}
			
			if(this.isButtonCancelAvailable()){
				String sText = dialogExtended.getText4ButtonCancel();
				buttonCancel = new JButton(sText);
				buttonCancel.addActionListener(this.getActionListenerButtonCancel(this));
				this.add(buttonCancel);
			}		
			
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	//#### Getter / Setter ##########################


	//#### Interfaces ##############################
	public AbstractKernelActionCascadedZZZ getActionListenerButtonOk(KernelJPanelCascadedZZZ panelButton){
		AbstractKernelActionCascadedZZZ objReturn = null;
		try {
			objReturn = new ActionListenerButtonOkDefaultZZZ(this.getKernelObject(), panelButton);
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
		}
		return objReturn;
	}
	public AbstractKernelActionCascadedZZZ getActionListenerButtonClose(KernelJPanelCascadedZZZ panelButton){
		AbstractKernelActionCascadedZZZ objReturn = null;
		try {
			objReturn = new ActionListenerButtonCloseDefaultZZZ(this.getKernelObject(), panelButton);
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
		}
		return objReturn;
	}
	public AbstractKernelActionCascadedZZZ getActionListenerButtonCancel(KernelJPanelCascadedZZZ panelButton){
		AbstractKernelActionCascadedZZZ objReturn = null;
		try {
			objReturn = new ActionListenerButtonCancelDefaultZZZ(this.getKernelObject(), panelButton);
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
		}
		return objReturn;
	}
	
	public boolean isButtonOkAvailable() {		
		return this.bIsButtonOkAvailable;
	}

	public boolean isButtonCancelAvailable() {
		return this.bIsButtonCancelAvailable;
	}
	
	public boolean isButtonCloseAvailable() {
		return this.bIsButtonCloseAvailable;
	}
	
	//### Action Klassen
	//	 	   Standard listeners *************************************************************

	/**Eine Klasse, die als ActionListener fuer einen "Cancel-Button" in einer Dialogbox dienen kann (ggf. sind die ...Custom - Methoden zu �berschreiben.
	 *   Dieser Standardbutton schliesst lediglich die Dialogbox.
	 * @author lindhaueradmin
	 *
	 */
	public class ActionListenerButtonCancelDefaultZZZ extends AbstractKernelActionCascadedZZZ {

		public ActionListenerButtonCancelDefaultZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ {
			super(objKernel, panelParent);
		}

		
		/**Durch Ueberschreiben dieser Methoden koennen erbende Klassen noch anderen Code ausfuehren
		* @param ActionEvent
		* @return true ==> es wird der weitere Code ausgef�hrt
		* 
		* lindhaueradmin; 09.01.2007 09:03:32
		 */
		@Override
		public boolean actionPerformQueryCustom(ActionEvent ae){
			return true;
		}
		
		@Override
		public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult){				
			return true;
		}
		
		@Override
		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ{		
			KernelJDialogExtendedZZZ dialogExtended = this.getPanelParent().getDialogParent();
			if(dialogExtended!=null) {
				dialogExtended.onCancel();
			}
		}

		@Override
		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
			
		}
		
		
		
	}//END class actionListenerButtonCancelDefault
	
	/**Eine Klasse, die als ActionListener fuer einen "Close-Button" in einer Dialogbox dienen kann (ggf. sind die ...Custom - Methoden zu �berschreiben.
	 *   Dieser Standardbutton schliesst lediglich die Dialogbox.
	 * @author lindhaueradmin
	 *
	 */
	public class ActionListenerButtonCloseDefaultZZZ extends AbstractKernelActionCascadedZZZ {

		public ActionListenerButtonCloseDefaultZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ {
			super(objKernel, panelParent);
		}
		
		/**Durch Ueberschreiben dieser Methoden koennen erbende Klassen noch anderen Code ausfuehren
		* @param ActionEvent
		* @return true ==> es wird der weitere Code ausgef�hrt
		* 
		* lindhaueradmin; 09.01.2007 09:03:32
		 */
		@Override
		public boolean actionPerformQueryCustom(ActionEvent ae)throws ExceptionZZZ{
			return true;
		}
		
		@Override
		public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ{				
			return true;
		}
		
		@Override
		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ{
			KernelJDialogExtendedZZZ dialogExtended = this.getPanelParent().getDialogParent();
			if(dialogExtended!=null) {
				dialogExtended.onClose();
			}
		}

		@Override
		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) throws ExceptionZZZ {
			
		}
		
		
		
	}//END class actionListenerButtonCloseDefault
	
	/** Eine Klasse, die als ActionListener f�r einen "OK-Button" in einer Dialogbox dienen kann (ggf. sind die ...Custom - Methoden zu �berschreiben.
	 *   Dieser Standardbutton schliesst lediglich die Dialogbox.
	 * @author lindhaueradmin
	 *
	 */
	public class ActionListenerButtonOkDefaultZZZ extends AbstractKernelActionCascadedZZZ implements ActionListener {

		public ActionListenerButtonOkDefaultZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) throws ExceptionZZZ {
			super(objKernel, panelParent);
		}
		
		/**Durch Ueberschreiben dieser Methoden koennen erbende Klassen noch anderen Code ausfuehren
		* @param ActionEvent
		* @return true ==> es wird der weitere Code ausgefuehrt
		* 
		* lindhaueradmin; 09.01.2007 09:03:32
		 */
		public boolean actionPerformQueryCustom(ActionEvent ae)throws ExceptionZZZ{
			return true;
		}
		
		@Override
		public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ{
			/* BEISPIEL, normalerweise wird eine andere Klasse verwendet, wenn zusaetzlich zur Default Aktion noch hier etwas gemacht werden soll
			// BEISPIEL: Den Inhalt des Labels in einem anderen Panel hier ausgeben.
			System.out.println("ok");					
			KernelJPanelCascadedZZZ panelButton = (KernelJPanelCascadedZZZ) this.getPanelParent();
			KernelJPanelCascadedZZZ panelCenter = (KernelJPanelCascadedZZZ) panelButton.getPanelNeighbour("CENTER");
			JTextField texttemp = (JTextField) panelCenter.getComponent("text1");
			String stemp = texttemp.getText();			
			System.out.println(stemp);
			*/
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# default ok button");			
			return true;
		}
		
		@Override
		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult) throws ExceptionZZZ{
			KernelJDialogExtendedZZZ dialogExtended = this.getPanelParent().getDialogParent();
			if(dialogExtended!=null) {
				dialogExtended.onOk();
			}
		}

		@Override
		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
			
		}
	}//END class actionListenerButtonCancelDefault
}//END Class ...panel...
