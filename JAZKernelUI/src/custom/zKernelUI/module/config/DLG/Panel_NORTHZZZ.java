package custom.zKernelUI.module.config.DLG;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import basic.zBasic.ExceptionZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;


import basic.zKernel.KernelZZZ;

public class Panel_NORTHZZZ extends KernelJPanelCascadedZZZ {
	private KernelZZZ objKernelChoosen;
	private static final int iLABEL_COLUMN_DEFAULT = 10;

	
	public Panel_NORTHZZZ(KernelZZZ objKernel, KernelZZZ objKernelChoosen, String sModule, String sProgram, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try {
		this.objKernelChoosen = objKernelChoosen;
		
		//TODO Komplizierteres aber schöneres Layout durch einen anderen Layoutmanager
		this.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
	
			//Einen Rahmen um das Panel zeichnen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
			
			
			// Die grundlegenden Informationen zum Kernel-Objekt anzeigen in JLabel-Objekten
			// +++ Das Modul muss übergeben werden. Falls das Modul nicht vorhanden ist. Hier einen Fehler als Wert anzeigen.			
			JLabel labelModuleText = new JLabel("Module: ", SwingConstants.RIGHT);
			this.add(labelModuleText);
			
			check:{				
				if(sModule==null){
//					Kein Modul übergeben: NULL
					JLabel labelModuleValue = new JLabel("No module selected", SwingConstants.LEFT);
					this.add(labelModuleValue);				
				}else if(sModule.equals("")){
					//Kein Modul übergeben: ""
					JLabel labelModuleValue = new JLabel("No module selected", SwingConstants.LEFT);
					this.add(labelModuleValue);					
				}else{
					//ModuleExists ?			
					boolean bModuleConfigured = this.objKernelChoosen.proofModuleFileIsConfigured(sModule);
					if(bModuleConfigured==false){
						//Fall: Modul nicht configuriert
						JLabel labelModuleValue = new JLabel(sModule + ", Error: This is not configured in the kernel main .ini-file.", SwingConstants.LEFT);
						this.add(labelModuleValue);
					}else{						
						boolean bModuleExists = this.objKernelChoosen.proofModuleFileExists(sModule);
						if(bModuleExists==false){
							//Fall: Konfiguriertes Modul existiert nicht physikalisch als Datei am erwarteten Ort/mit dem erwarteten Namen
							JLabel labelModuleValue = new JLabel(sModule + ", Error: The .ini-file does not exist.", SwingConstants.LEFT);
							this.add(labelModuleValue);
						}else{
							//Fall: Alles o.k.
							JLabel labelModuleValue = new JLabel(sModule, SwingConstants.LEFT);
							this.add(labelModuleValue);
						}
					}
				}	
			}//END check:
			
						
//			+++ Programm, diese Felder sollen unterhalb der Felder zum Module stehen
			JLabel labelProgramText = new JLabel("Program: ", SwingConstants.RIGHT);
			this.add(labelProgramText);
			JLabel labelProgramValue = new JLabel("Program configuration handling not yet available.", SwingConstants.LEFT);
			this.add(labelProgramValue);			
			
			
			//+++ ApplicationKey
			JLabel labelKeyText = new JLabel("Application key: ", SwingConstants.RIGHT);
			this.add(labelKeyText);
			
//			TODO GOON, ist nicht ganz sauber das eigene Kernel-Objekt hierfür zu verwenden
			//String sApplicationKey = this.getKernelObject().getApplicationKey();	
			String sApplicationKey = this.objKernelChoosen.getApplicationKey();
			JLabel labelKeyValue = new JLabel(sApplicationKey, SwingConstants.LEFT);
			this.add(labelKeyValue);
		
									
			//+++ System, diese Felder sollen unterhalb der Felder zum ApplicationKey stehen
			JLabel labelSystemText = new JLabel("System: ", SwingConstants.RIGHT);
			this.add(labelSystemText);
			
//			TODO GOON, ist nicht ganz sauber das eigene Kernel-Objekt hierfür zu verwenden
			//String sSystemNumber = this.getKernelObject().getSystemNumber();
			String sSystemNumber = this.objKernelChoosen.getSystemNumber();
			JLabel labelSystemValue = new JLabel(sSystemNumber, SwingConstants.LEFT);
			this.add(labelSystemValue);
			
			
			//+++ Das muss so sein, damit die anderen Labels an ihrem Platz bleiben und nicht "verschoben" werden
			JLabel label5Text = new JLabel(" ");
			this.add(label5Text);
			
			JLabel label6Text = new JLabel("  ");
			this.add(label6Text);
			
			JLabel label7Text = new JLabel(" ");
			this.add(label7Text);
			
			JLabel label8Text = new JLabel("  ");
			this.add(label8Text);
			
			} catch (ExceptionZZZ ez) {
				this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			}
		}//END main:
	}
    
}
