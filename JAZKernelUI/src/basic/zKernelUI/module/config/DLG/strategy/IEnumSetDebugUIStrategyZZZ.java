package basic.zKernelUI.module.config.DLG.strategy;

import basic.zBasic.persistence.interfaces.enums.IThiskeyProviderZZZ;
import basic.zBasic.persistence.interfaces.enums.IThiskeyUserZZZ;
import basic.zBasic.persistence.interfaces.enums.ICategoryProviderZZZ;

public interface IEnumSetDebugUIStrategyZZZ extends IEnumDebugUIStrategyZZZ{
	
	//Das bring ENUM von sich auch mit
	public String getName();  //Das ist name() von Enum
	public int getIndex();      //Das ist ordinal() von Enum
	public String toString();
}
