package basic.zKernelUI.component.navigator;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjektZZZ;
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
	public void setVisible(boolean bVisible) {
		if(bVisible) {
			Border border = BorderFactory.createLineBorder(Color.red);
			this.getLabel().setBorder(border);
		}else {
			this.getLabel().setBorder(null);
		}	
	}
}
