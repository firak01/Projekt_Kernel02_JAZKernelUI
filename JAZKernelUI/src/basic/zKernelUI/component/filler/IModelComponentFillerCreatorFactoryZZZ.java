package basic.zKernelUI.component.filler;

import java.awt.LayoutManager;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;

public interface IModelComponentFillerCreatorFactoryZZZ {
	public IComponentFillerCreatorZZZ getFillerCreatorObject(LayoutManager objLayoutManager)  throws ExceptionZZZ;
}
