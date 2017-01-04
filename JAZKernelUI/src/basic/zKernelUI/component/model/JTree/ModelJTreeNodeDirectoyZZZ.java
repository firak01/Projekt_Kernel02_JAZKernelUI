package basic.zKernelUI.component.model.JTree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

import basic.zBasic.ReflectCodeZZZ;


public final class ModelJTreeNodeDirectoyZZZ  extends DefaultMutableTreeNode{
	private final FileSystemView fileSystemView;
	private boolean bChildrenLoaded=false;
	private File fileDirectoryStart=null;
	private File fileDirectoryCurrent;
	
	/**Konstruktor, empfohlen nur für das Root-Verzeichnis
	* lindhaueradmin; 19.01.2007 11:22:18
	 * @param fileDirectoryStart
	 */
	public ModelJTreeNodeDirectoyZZZ(File fileDirectoryStart){
		super(fileDirectoryStart);
		this.fileSystemView = FileSystemView.getFileSystemView();
		this.fileDirectoryStart = fileDirectoryStart;
		this.fileDirectoryCurrent = fileDirectoryStart;
		
		loadChildren();
	}
	
	/**Konstruktor, der auch in der loadChildren-Rekursion aufgerufen wird.
	* lindhaueradmin; 19.01.2007 11:22:39
	 * @param fileDirectoryCurrent
	 * @param fileSystemViewUsed
	 */
	public ModelJTreeNodeDirectoyZZZ(File fileDirectoryCurrent, FileSystemView fileSystemViewUsed){
		super(fileDirectoryCurrent.getName());   //Nur den Namen des aktuellen Verzeichnisses hinzufügen, dieser soll nur angezeigt werden.
		this.fileSystemView = fileSystemViewUsed;
		this.fileDirectoryCurrent = fileDirectoryCurrent;
		
		loadChildren();
	}
	
	/** Ermittle die von der Dateisystemsicht gelieferten Kinder des Aktuellen Ordners. 
	 *  Unter diesen filtere die traversierbaren Ordner heraus, sortiere sie mit dem Standard-Comparator,
	 *  erzeuge die Kinderknoten und füge sie diesem Knoten hinzu.
	 *  
	* lindhaueradmin; 19.01.2007 10:10:32
	 */
	private void loadChildren(){
		main:{
			File[] fileaList =fileSystemView.getFiles(this.getDirectoryCurrent(), false); 
			if(null==fileaList) break main;
			
			//Sammle die traversierbaren Unterordner
			List childDirectories = new ArrayList();
			for(int i = 0; i < fileaList.length; i++){
				File file = fileaList[i];
				if(fileSystemView.isTraversable(file).booleanValue()){
					//System.out.println(ReflectionZZZ.getMethodCurrentName() + "#Sammle Ordner#" + file.getPath());
					childDirectories.add(file);   
				}
			}
			
			//Sortiere die Unterordner
			//Collections.sort(childDirectories);
			
			
			//Erzeuge die Kinderknoten und füge sie diesem Knoten zu
			Iterator it = childDirectories.iterator();
			while(it.hasNext()){
				File aDirectory = (File) it.next();
				
				// !!! Merke: Das dauert extrem lange, wenn in der Netzwerkumgebung gesucht wird. Vor allem, wenn die dort angegebenen Pfade nicht vorhanden sind.
				// Auch wenn man diesen Fall ausschliesst, so ist das doch ein Fall für die nebenläufige Programmierung !!!
				//System.out.println(ReflectionZZZ.getMethodCurrentName() + "# " + aDirectory.getPath());
				ModelJTreeNodeDirectoyZZZ node = new ModelJTreeNodeDirectoyZZZ(aDirectory, this.fileSystemView); 
				this.add(node);
			}
			
		}//end main:
		this.bChildrenLoaded = true;
	}
	
	
	
	//########## Getter / Setter
	public File getDirectoryCurrent(){
		return this.fileDirectoryCurrent;
	}
	
	FileSystemView getFileSystemView(){
		return this.fileSystemView;
	}
}//END class
	