package custom.zKernelUI.module.config.DLG;

import java.util.Hashtable;

import basic.zBasic.ExceptionZZZ;

public interface IComponentTableZZZ {

	/**Gibt den Inhalt des xten Textfeldes zur�ck
	 * @param iPosition
	 * @return
	 */
	public abstract String getValue(int iPosition);

	/**Im Kontruktor wurde das Modul mitgegeben. �ber dieses Methode kann man den Wert wieder auslesen.
	 * @return, String
	 */
	public abstract String getModule();

	/**Gibt den Alias der Tabelle zur�ck. Dies entspricht dem Namen der Section in der Ini-Datei.
	 * Merke: Der aktuelle Section-Name entspricht dem objKernel.getSystemKey()
	 * @return String
	 */
	public abstract String getTableAlias();

	/**Gibt den Inhalt der JLabel = JTextField Paare als Tabelle zur�ck. 
	 * (bzw. den Inhalt der Section als Wertpaare Property = Value)
	 * @return Hashtable
	 * @param  bExcludeValueEmpty, Leerwerte (d.h. der Inhalt von JTextField ist leer) werden dann nicht in die Tabelle �bernommen. 
	 * @throws ExceptionZZZ 
	 */
	public abstract Hashtable getTable(boolean bExcludeValueEmpty)
			throws ExceptionZZZ;

}