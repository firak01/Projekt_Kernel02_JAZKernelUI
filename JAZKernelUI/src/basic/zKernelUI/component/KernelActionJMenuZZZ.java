package basic.zKernelUI.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

public class KernelActionJMenuZZZ extends AbstractKernelUseObjectZZZ  implements ActionListener{
	KernelJFrameCascadedZZZ frameParentCascaded = null;
	
	public KernelActionJMenuZZZ(IKernelZZZ objKernel, KernelJFrameCascadedZZZ frameParentCascaded ) throws ExceptionZZZ{
		super(objKernel);
		this.frameParentCascaded = frameParentCascaded;
	}
	
	//### Getter/Setter
	public KernelJFrameCascadedZZZ getFrameParent(){
		return this.frameParentCascaded;
	}
	public void setFrameParent(KernelJFrameCascadedZZZ frameParentCascaded){
		this.frameParentCascaded = frameParentCascaded;
	}
	
	
	//### Aus Interface
	public void actionPerformed(ActionEvent arg0) {
		//Muss dann �berschreiben werden
	}
}
