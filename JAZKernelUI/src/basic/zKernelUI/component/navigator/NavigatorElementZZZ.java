package basic.zKernelUI.component.navigator;

import basic.zKernel.IKernelConfigSectionEntryZZZ;

public class NavigatorElementZZZ extends AbstractNavigatorElementZZZ{
	public NavigatorElementZZZ() {
		super();
	}
	
	public NavigatorElementZZZ(IModelNavigatorValueZZZ objModelNavigator, String sAlias, int iPositionInModel,String sValue) {
		super(objModelNavigator, sAlias, iPositionInModel,sValue);
	}
	
	public NavigatorElementZZZ(IModelNavigatorValueZZZ objModelNavigator, IKernelConfigSectionEntryZZZ objEntry) {
		super(objModelNavigator, objEntry);
	}

	
}
