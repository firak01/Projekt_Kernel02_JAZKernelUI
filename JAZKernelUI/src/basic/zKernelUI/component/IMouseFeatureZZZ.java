/**
 * 
 */
package basic.zKernelUI.component;

import basic.zBasic.ExceptionZZZ;

/**
 * @author 0823
 *
 */
public interface IMouseFeatureZZZ {
	public enum FLAGZ{
		COMPONENT_DRAGGABLE, TERMINATE;
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IMouseFeatureZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IMouseFeatureZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
	public boolean[] setFlag(IMouseFeatureZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue);
	public boolean proofFlagZExists(IMouseFeatureZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	public abstract boolean isJComponentContentDraggable();
	public abstract void setJComponentContentDraggable(boolean bValue);
}
