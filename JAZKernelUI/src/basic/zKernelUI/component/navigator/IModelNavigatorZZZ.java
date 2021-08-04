package basic.zKernelUI.component.navigator;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

public interface IModelNavigatorZZZ {
	public HashMapIndexedZZZ<Integer, ArrayList<String>> getNavigatorValues()  throws ExceptionZZZ;
}
