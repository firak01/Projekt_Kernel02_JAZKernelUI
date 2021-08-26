package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;

public class NavigatorElementZZZ extends AbstractNavigatorElementZZZ{
	public NavigatorElementZZZ() {
		super();
	}
	
	public NavigatorElementZZZ(IKernelZZZ objKernel, IModelNavigatorValueZZZ objModelNavigator, String sAlias, int iPositionInModel,String sValue) throws ExceptionZZZ {
		super(objKernel, objModelNavigator, sAlias, iPositionInModel,sValue);
	}
	
	public NavigatorElementZZZ(IKernelZZZ objKernel, IModelNavigatorValueZZZ objModelNavigator, IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		super(objKernel, objModelNavigator, objEntry);
	}

	@Override
	public void doSwitch(EventNavigatorElementSwitchZZZ event) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSwitchCustom(EventNavigatorElementSwitchZZZ eventComponentGroupSwitchNew) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventNavigatorElementSwitchZZZ getEventPrevious() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEventPrevious(EventNavigatorElementSwitchZZZ eventComponentGroupSwitchNew) {
		// TODO Auto-generated method stub
		
	}

	
	
}
