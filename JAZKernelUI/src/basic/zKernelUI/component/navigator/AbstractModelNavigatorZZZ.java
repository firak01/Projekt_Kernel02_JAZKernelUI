package basic.zKernelUI.component.navigator;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernelUI.component.IPanelCascadedZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernelUI.component.model.ModelComponentHelperZZZ;
import debug.zKernelUI.component.buttonSwitchLabelGroup.ModelRow2ZZZ;

public abstract class AbstractModelNavigatorZZZ extends AbstractKernelUseObjectZZZ  implements IModelNavigatorValueZZZ{
	//protected HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmNavigatorElement=null;
	protected ArrayListExtendedZZZ<INavigatorElementZZZ>alNavigatorElement=null;
		
	public AbstractModelNavigatorZZZ() {		
		super();
		AbstractModelNavigatorNew_();
	}
	
	public AbstractModelNavigatorZZZ(IKernelZZZ objKernel) throws ModelNavigatorExceptionZZZ, ExceptionZZZ {		
		super(objKernel);
		AbstractModelNavigatorNew_();
	}

	private void AbstractModelNavigatorNew_() {
		
	}
		
	//GETTER / SETTER	
				
	//##############################
//	public HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>> getNavigatorElementHashMap() throws ExceptionZZZ{
//		if(this.hmNavigatorElement==null) {
//			this.hmNavigatorElement = createNavigatorElementHashMap();
//		}
//		return this.hmNavigatorElement;
//	}
//	
//	public void setNavigatorElementHashMap(HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>hmNavigatorElement) {
//		this.hmNavigatorElement = hmNavigatorElement;
//	}
	
	public ArrayListExtendedZZZ<INavigatorElementZZZ>getNavigatorElementArrayList() throws ExceptionZZZ{
		if(this.alNavigatorElement==null) {
			this.alNavigatorElement = createNavigatorElementArrayList();			
		}
		return this.alNavigatorElement;
	}
	
	public void setNavigatorElementArrayList(ArrayListExtendedZZZ<INavigatorElementZZZ>alNavigatorElement) {
		this.alNavigatorElement = alNavigatorElement;
	}
	
	//public abstract HashMapIndexedZZZ<Integer,ArrayList<INavigatorElementZZZ>>createNavigatorElementHashMap() throws ExceptionZZZ;
	public abstract ArrayListExtendedZZZ<INavigatorElementZZZ>createNavigatorElementArrayList() throws ExceptionZZZ;
	public abstract INavigatorElementZZZ createNavigatorElement(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
}
