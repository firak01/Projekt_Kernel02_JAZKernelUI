package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;

public interface IModelComponentGroupZZZ {
	public HashMapIndexedZZZ<Integer, ArrayList<String>> getComponentValues()  throws ExceptionZZZ;
}
