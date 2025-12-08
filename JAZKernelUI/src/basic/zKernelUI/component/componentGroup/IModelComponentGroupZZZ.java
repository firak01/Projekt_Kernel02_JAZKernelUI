package basic.zKernelUI.component.componentGroup;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjektZZZ;

public interface IModelComponentGroupZZZ {
	public HashMapIndexedObjektZZZ<Integer, ArrayList<String>> getComponentValues()  throws ExceptionZZZ;
}
