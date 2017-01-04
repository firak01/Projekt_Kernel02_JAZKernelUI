/**
 * 
 */
package basic.zKernelUI.component.model.JTree;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;


/**Diese Klasse dient dazu bei der Erstellung eines JTrees zunächst das Root - Directory anzuzeigen. (z.B. : "Loading ... C:/")
 *  Danach kann das eigentliche BaumModell aufgebaut werden. (Was noch zu lange dauert)..... 
 *  Meke: Wenn dieses Model im Baum verwendet wird, dann sollte ein Listener für die "Auswahl im Baum" nicht reagieren.
 *           Das kann durch Abfragen mit "instanceof" erreicht werden.
 *           
 *           Beispielsweise: siehe ListernerTreeDirectorySelectionVIA
 *                                if(objRoot instanceof ModelJTreeNodeDirectoyZZZ){ ....mache nur dann was ....   }
 *  
 *  
 * @author 0823
 *
 */
public class ModelJTreeNodeRootDummyZZZ extends DefaultMutableTreeNode {
	private File fileRoot = null;
	public final static String sNODE_TEXT_DEFAULT = "Loading";
	public final static String sNODE_DRIVE_EMPTY = "No disk in drive";
	public ModelJTreeNodeRootDummyZZZ(File fileRoot){
		this.fileRoot = fileRoot;
		
		DefaultMutableTreeNode nodeRoot = new DefaultMutableTreeNode(sNODE_TEXT_DEFAULT + " " + this.fileRoot.getPath()+ "...");
		this.add(nodeRoot);
	}
	public ModelJTreeNodeRootDummyZZZ(){
		DefaultMutableTreeNode nodeRoot = new DefaultMutableTreeNode(sNODE_DRIVE_EMPTY + "...");
		this.add(nodeRoot);
	}
}
