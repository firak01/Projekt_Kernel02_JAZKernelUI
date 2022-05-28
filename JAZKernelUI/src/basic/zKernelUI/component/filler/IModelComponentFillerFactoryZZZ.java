package basic.zKernelUI.component.filler;

import java.awt.LayoutManager;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;

public interface IModelComponentFillerFactoryZZZ {
	public ArrayList<String> getFillerForLayoutManagerUsed(LayoutManager objLayoutManager) throws ExceptionZZZ;
}
