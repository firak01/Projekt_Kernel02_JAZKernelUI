package basic.zKernelUI.component;

import java.util.ArrayList;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import basic.zBasic.ExceptionZZZ;

public interface IFormLayoutUserZZZ {
	public abstract boolean initFormLayout() throws ExceptionZZZ;
	
	public abstract FormLayout getFormLayoutUsed();
	public abstract void setFormLayoutUsed(FormLayout formLayout);
	
	public abstract FormLayout buildFormLayoutUsed();
	
	public abstract ArrayList<RowSpec> buildRowSpecs();
	public abstract RowSpec buildRowSpecGap();
	public abstract RowSpec buildRowSpecDebug();	
	
	public abstract ArrayList<ColumnSpec> buildColumnSpecs();
	public abstract ColumnSpec buildColumnSpecGap();
	
	public abstract boolean fillRowDebug(CellConstraints cc);
	public abstract boolean fillRow(CellConstraints cc, int iRow) throws ExceptionZZZ;
	
}
