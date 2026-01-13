package basic.zKernelUI.component;

import java.util.ArrayList;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import basic.zBasic.ExceptionZZZ;

public interface IFormLayoutUserZZZ {	
	public abstract FormLayout getFormLayoutUsed() throws ExceptionZZZ;
	public abstract void setFormLayoutUsed(FormLayout formLayout) throws ExceptionZZZ;
}
