package basic.zKernelUI.component.adjustmentNavigator;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

public interface IModelAdjustmentNavigatorZZZ {
	public HashMapIndexedZZZ<Integer, ArrayList<String>> getNavigatorValues()  throws ExceptionZZZ;
}
