package basic.zKernelUI.component;

import java.util.ArrayList;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import basic.zBasic.ExceptionZZZ;

public interface IFormLayoutZZZ {
	public abstract boolean initFormLayoutDebug() throws ExceptionZZZ;
	public abstract boolean initFormLayoutContent() throws ExceptionZZZ;
	
	public abstract int getNumberOfRows();
	public abstract void setNumberOfRows(int iNumberOfRows);
	
	public abstract FormLayout buildFormLayoutUsed();
	public abstract FormLayout buildFormLayoutUsed(int iNumberOfRows);
	
	public abstract ArrayList<RowSpec> getRowSpecs();
	public abstract void setRowSpecs(ArrayList<RowSpec> listaRowSpec);
	public abstract ArrayList<RowSpec> buildRowSpecs();	
	public abstract ArrayList<RowSpec> buildRowSpecs(int iNumberOfRows);
	public abstract RowSpec buildRowSpecDebug();	
	
	public abstract ArrayList<ColumnSpec> getColumnSpecs();
	public abstract ArrayList<ColumnSpec> buildColumnSpecs();
		
	public abstract boolean fillRowDebug(CellConstraints cc) throws ExceptionZZZ;
	public abstract boolean fillRowContent(CellConstraints cc, int iRow) throws ExceptionZZZ;
	
}
