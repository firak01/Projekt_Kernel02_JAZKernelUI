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

public class KernelJPanelDialogButtonDefaultZZZ extends KernelJPanelCascadedZZZ implements IConstantZZZ,  IKernelUserZZZ, IObjectZZZ, IPanelDialogButtonZZZ{
	//Buttons f�r den JPane	
	private JButton buttonOk = null;
	private JButton buttonCancel = null;
	
	//Flags
	private boolean bIsButtonCancelAvailable=true;
	private boolean bIsButtonOkAvailable=true;
	
	
	public KernelJPanelDialogButtonDefaultZZZ() throws ExceptionZZZ{
		super();
	}
	
	public KernelJPanelDialogButtonDefaultZZZ(IKernelZZZ objKernel, KernelJDialogExtendedZZZ dialogExtended, boolean bIsButtonOkAvailable, boolean bIsButtonCancelAvailable){
		super(objKernel, dialogExtended);
		
		//Panel gestalten. in dem �bergeordneten Panel gilt:  Border Layout mit ButtonPanel im SOUTH
		//TODO: ggf. ein Objekt f�r R�ckgabewerte definieren und �bergeben.
		 
		this.bIsButtonCancelAvailable = bIsButtonCancelAvailable;
		this.bIsButtonOkAvailable = bIsButtonOkAvailable;
		if(this.isButtonOkAvailable()){
			String sText = dialogExtended.getText4ButtonOk();
			buttonOk = new JButton(sText);
			buttonOk.addActionListener(this.getActionListenerButtonOk(this));
			this.add(buttonOk);
		}
		
		if(this.isButtonCancelAvailable()){
			String sText = dialogExtended.getText4ButtonCancel();
			buttonCancel = new JButton(sText);
			buttonCancel.addActionListener(this.getActionListenerButtonCancel(this));
			this.add(buttonCancel);
		}		
	}
	
	
	//#### Getter / Setter ##########################


	//#### Interfaces ##############################
	public KernelActionCascadedZZZ getActionListenerButtonOk(KernelJPanelCascadedZZZ panelButton){
		return new ActionListenerButtonOkDefaultZZZ(this.getKernelObject(), panelButton);
	}
	public KernelActionCascadedZZZ getActionListenerButtonCancel(KernelJPanelCascadedZZZ panelButton){
		return new ActionListenerButtonCancelDefaultZZZ(this.getKernelObject(), panelButton);
	}
	
	public boolean isButtonOkAvailable() {		
		return this.bIsButtonOkAvailable;
	}

	public boolean isButtonCancelAvailable() {
		return this.bIsButtonCancelAvailable;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
	}

	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	public LogZZZ getLogObject() {
		return this.objLog;
	}

	public void setLogObject(LogZZZ objLog) {
		this.objLog = objLog;
	}

	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub
		
	}
	
	
	//### Action Klassen
	//	 	   Standard listeners *************************************************************

	/**Eine Klasse, die als ActionListener f�r einen "Cancel-Button" in einer Dialogbox dienen kann (ggf. sind die ...Custom - Methoden zu �berschreiben.
	 *   Dieser Standardbutton schliesst lediglich die Dialogbox.
	 * @author lindhaueradmin
	 *
	 */
	public class ActionListenerButtonCancelDefaultZZZ extends KernelActionCascadedZZZ {

		public ActionListenerButtonCancelDefaultZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) {
			super(objKernel, panelParent);
		}

		
		/**Durch �berschreiben dieser Methoden k�nnen erbende Klassen noch anderen Code ausf�hren
		* @param ActionEvent
		* @return true ==> es wird der weitere Code ausgef�hrt
		* 
		* lindhaueradmin; 09.01.2007 09:03:32
		 */
		public boolean actionPerformQueryCustom(ActionEvent ae){
			return true;
		}
		public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult){				
			return true;
		}
		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult){
			JDialog dialogParent = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this.getPanelParent());
			dialogParent.dispose();
		}

		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	}//END class actionListenerButtonCancelDefault
	
	/** Eine Klasse, die als ActionListener f�r einen "OK-Button" in einer Dialogbox dienen kann (ggf. sind die ...Custom - Methoden zu �berschreiben.
	 *   Dieser Standardbutton schliesst lediglich die Dialogbox.
	 * @author lindhaueradmin
	 *
	 */
	public class ActionListenerButtonOkDefaultZZZ extends KernelActionCascadedZZZ implements ActionListener {

		public ActionListenerButtonOkDefaultZZZ(IKernelZZZ objKernel, KernelJPanelCascadedZZZ panelParent) {
			super(objKernel, panelParent);
		}
		
		/**Durch �berschreiben dieser Methoden k�nnen erbende Klassen noch anderen Code ausf�hren
		* @param ActionEvent
		* @return true ==> es wird der weitere Code ausgef�hrt
		* 
		* lindhaueradmin; 09.01.2007 09:03:32
		 */
		public boolean actionPerformQueryCustom(ActionEvent ae){
			return true;
		}
		public boolean actionPerformCustom(ActionEvent ae, boolean bQueryResult){
			/* BEISPIEL, normalerweise wird eine andere Klasse verwendet, wenn zus�tzlich zur Default Aktion noch hier etwas gemacht werden soll
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
		public void actionPerformPostCustom(ActionEvent ae, boolean bQueryResult){
			JDialog dialogParent = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this.getPanelParent());
			dialogParent.dispose();
		}

		public void actionPerformCustomOnError(ActionEvent ae, ExceptionZZZ ez) {
			// TODO Auto-generated method stub
			
		}
	}//END class actionListenerButtonCancelDefault
}//END Class ...panel...
