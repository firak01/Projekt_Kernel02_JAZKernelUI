package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;

public interface IModelComponentGroupZZZ {
	public HashMapIndexedObjectZZZ<Integer, ArrayList<String>> getComponentValues()  throws ExceptionZZZ;
}
