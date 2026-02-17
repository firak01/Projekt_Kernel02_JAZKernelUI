package custom.zKernelUI.module.config.DLG;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import basic.zBasic.ExceptionZZZ;
import basic.zBasicUI.layoutmanager.GridLayout4VisibleZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;


import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.IKernelModuleZZZ;

public class Panel_NORTHZZZ extends KernelJPanelCascadedZZZ {
	private IKernelZZZ objKernelChoosen;
	private static final int iLABEL_COLUMN_DEFAULT = 10;

	
	public Panel_NORTHZZZ(IKernelZZZ objKernel, IKernelZZZ objKernelChoosen, IKernelModuleZZZ objModuleChoosen, String sProgram, JPanel panelParent) throws ExceptionZZZ {
		super(objKernel, panelParent);
		main:{
		try {
		this.objKernelChoosen = objKernelChoosen;//TODOGOON 20210310: Kann man kernelChoosen komplett durch ModuleChoosen ersetzen????
		this.setModule(objModuleChoosen);//Merke 20210310: Das ist ggfs. auch ein ganz abstraktes Modulobjekt, also nicht etwas, das konkret existiert - wie z.B. ein anderes Panel.
		
		
			//Einen Rahmen um das Panel zeichnen
			Border borderEtched = BorderFactory.createEtchedBorder();
			this.setBorder(borderEtched);
			
			
			
			// Die grundlegenden Informationen zum Kernel-Objekt anzeigen in JLabel-Objekten
			// +++ Das Modul muss �bergeben werden. Falls das Modul nicht vorhanden ist. Hier einen Fehler als Wert anzeigen.			
			JLabel labelModuleText = new JLabel("Module: ", SwingConstants.RIGHT);
			this.add(labelModuleText);
			
			check:{				
				if(objModule==null){
//					Kein Modul uebergeben: NULL
					JLabel labelModuleValue = new JLabel("No module selected", SwingConstants.LEFT);
					this.add(labelModuleValue);				
				}else if(objModule!=null && objModule.getModuleName().equals("")){
					//Kein Modul uebergeben: ""
					JLabel labelModuleValue = new JLabel("No module selected", SwingConstants.LEFT);
					this.add(labelModuleValue);					
				}else{
					//ModuleExists ?
					String sModule = objModule.getModuleName();
					
					boolean bModuleConfigured = this.objKernelChoosen.proofFileConfigModuleIsConfigured(sModule);
					if(bModuleConfigured==false){
						//Fall: Modul nicht configuriert
						JLabel labelModuleValue = new JLabel(sModule + ", Error: This is not configured in the kernel main .ini-file.", SwingConstants.LEFT);
						this.add(labelModuleValue);
					}else{						
						boolean bModuleExists = this.objKernelChoosen.proofFileConfigModuleExists(sModule);
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
			
			//Das ausgewählte Kernel-Objekt hierfuer verwenden	
			String sApplicationKey = this.objKernelChoosen.getApplicationKey();
			JLabel labelKeyValue = new JLabel(sApplicationKey, SwingConstants.LEFT);
			this.add(labelKeyValue);
		
									
			//+++ System, diese Felder sollen unterhalb der Felder zum ApplicationKey stehen
			JLabel labelSystemText = new JLabel("System: ", SwingConstants.RIGHT);
			this.add(labelSystemText);
			
			//Das ausgewählte Kernel-Objekt hierfuer verwenden
			String sSystemNumber = this.objKernelChoosen.getSystemNumber();
			JLabel labelSystemValue = new JLabel(sSystemNumber, SwingConstants.LEFT);
			this.add(labelSystemValue);
			
			
			//Nun erst das Layout, wichtig wg. Ermittlung der Anzahl Zeilen
			//Problem: GridLayout hält Platz auch für verborgene Components
			//         https://stackoverflow.com/questions/7727728/invisible-components-still-take-up-space-jpanel
			//this.setLayout(new GridLayout(6,2)); //6 Zeilen, 2 Spalten
			//Darum eine spezielle Variante dafür, nur für sichtbare Components			
			int iComponents = this.getComponents().length; //Wg. des "Wegtrimmens" der unsichtbaren Components ist es egal, dass auch unsichtbare hier "gezaehlt" werden.
			int iColumns = 2;
			int iRows = iComponents / iColumns;
			this.setLayout(new GridLayout4VisibleZZZ(iRows,iColumns));		
			
			} catch (ExceptionZZZ ez) {
				this.getLogObject().writeLineDate(ez.getDetailAllLast());
			}
		}//END main:
	}
    
}
