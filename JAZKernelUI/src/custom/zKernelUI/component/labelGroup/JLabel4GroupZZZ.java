package custom.zKernelUI.component.labelGroup;

import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.labelGroup.EventComponentGroupSwitchZZZ;
import basic.zKernelUI.component.labelGroup.KernelJLabelListening4GroupSwitchZZZ;
import basic.zKernelUI.component.model.EventComponentSelectionResetZZZ;

public class JLabel4GroupZZZ extends KernelJLabelListening4GroupSwitchZZZ {

	public JLabel4GroupZZZ(IKernelZZZ objKernel, String sTextInitial) {
		super(objKernel, sTextInitial);		
	}
	public JLabel4GroupZZZ(IKernelZZZ objKernel, String sTextInitial, int iOrientation) {
		super(objKernel, sTextInitial, iOrientation);		
	}
	
	@Override
	public void doSwitchCustom(EventComponentGroupSwitchZZZ eventComponentGroupSwitchNew) {
	}
}
