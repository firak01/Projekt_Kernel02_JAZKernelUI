package debug.zKernelUI.component.buttonGroup;
import javax.swing.JComponent;

import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJFrameCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.util.JFrameHelperZZZ;

public class FrameDebugButtonGroupZZZ extends KernelJFrameCascadedZZZ{
	private KernelZZZ objKernelChoosen;   //Dieser Frame stellt die Konfiguration eines anderen Kernels dar. Dieser andere, intern verwendete Kernel ist hiermit gemeint.
	
	
	
public FrameDebugButtonGroupZZZ(KernelZZZ objKernelConfig) throws ExceptionZZZ{
	super(objKernelConfig); //Es gibt keinen ParentFrame
	
	//DIE Anwendung der konfiguration soll pr�fen, ob alles korrekt konfiguriert ist. Darum wird daf�r ein internes Kernel-Objekt angelegt.
	//ABER: Dabei entsteht ein Log-File. DAS DARF NICHT SEIN (TODO)
//	KernelZZZ objKernelChoosen = new KernelZZZ(sApplication2Config, sSystemNumber2Config, objKernelConfig, null);
//	this.setKernelConfigObject(objKernelChoosen);
	
	//TODO: Titel aus der Konfiguration holen
	setTitle("setTitel() has to be set by the custom classes");

}
public boolean launchCustom(){
	return true;
	
	/* !!!! NICHT L�SCHEN: WICHTIGER KOMMENTAR:       .... das wird nun alles in KernelJFrameCascaded gemacht
	main:
	{
    //Aus Notes heraus gestartet funktioniert EXIT_ON_CLOSE nicht
	//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
	//Die Gr�sse des Frames, die Methode wird vom KernelFrame zur Verf�gung gestellt 
	setTitle(this.getKernelObject().getApplicationKey() + " module configuration");
	
	//Grundfl�che in den Rahmen hinzuf�gen...
	//	... das wird nun �ber das ContentPane der Frames gemacht
	PanelConfigZZZ objPanel = new PanelConfigZZZ(this.getKernelObject(), this, this.getKernelConfigObject());
	this.getContentPane().add(objPanel);
	this.setPanelSub("ContentPane", objPanel);
	
	
	//... sichtbar machen erst, nachdem alle Elemente im Frame hinzugef�gt wurden !!!
	//depreciated in 1.5 frame.show();
	//statt dessen...
	setVisible(true);
	bReturn = true;
	
	}//END main:
		*/
}
 
public KernelJPanelCascadedZZZ getPaneContent() throws ExceptionZZZ {
	PanelDebugButtonGroupZZZ objPanel = new PanelDebugButtonGroupZZZ(this.getKernelObject(), this);	
	this.setPanelContent(objPanel);
	return objPanel;
}


public KernelZZZ getKernelConfigObject(){
	return this.objKernelChoosen;
}
public void setKernelConfigObject(KernelZZZ objKernelConfig){
	this.objKernelChoosen = objKernelConfig;
}
public JComponent getPaneContent(String sAlias) throws ExceptionZZZ {
	//Hier wird nix in einen anderen Pane als den ContentPane gestellt.
	return null;
}
@Override
public boolean setSizeDefault() throws ExceptionZZZ {
	JFrameHelperZZZ.setSizeDefault(this);
	return true;
}
@Override
public IPanelCascadedZZZ createPanelContent() throws ExceptionZZZ {
	// TODO Auto-generated method stub
	return null;
}
}//END class
