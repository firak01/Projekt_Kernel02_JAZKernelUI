package basic.zKernelUI.module.config.DLG.strategy;

import basic.zBasic.persistence.interfaces.enums.IThiskeyProviderZZZ;
import basic.zBasic.persistence.interfaces.enums.IThiskeyUserZZZ;
import basic.zKernelUI.component.IDebugUiZZZ;
import basic.zKernelUI.module.config.DLG.strategy.DebugUIStrategyZZZ.EnumDebugUIStrategy;

import java.util.EnumSet;

import basic.zBasic.persistence.interfaces.enums.ICategoryProviderZZZ;

//public interface IEnumDebugUIStrategyZZZ extends IThiskeyUserZZZ,IThiskeyProviderZZZ<Long>,ICategoryProviderZZZ{
public interface IEnumDebugUIStrategyZZZ extends IThiskeyProviderZZZ<Long>,ICategoryProviderZZZ{
		
	//Dies sind die "technisch/fachlichen" Angaben zur "Strategie".
	public int getStrategyValue();
	public String getStrategyFlagName();
}
