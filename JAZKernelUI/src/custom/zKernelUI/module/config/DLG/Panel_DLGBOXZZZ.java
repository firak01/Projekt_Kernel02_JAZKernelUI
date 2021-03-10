package custom.zKernelUI.module.config.DLG;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernelUI.component.KernelJPanelCascadedZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.component.AbstractKernelModuleZZZ;
import basic.zKernel.component.IKernelModuleZZZ;
import basic.zKernel.component.KernelModuleZZZ;

public class Panel_DLGBOXZZZ extends KernelJPanelCascadedZZZ {
	
	public Panel_DLGBOXZZZ(IKernelZZZ objKernel, JFrame frameParent, IKernelZZZ objKernelChoosen, String sModule, String sProgram) throws ExceptionZZZ{
		super(objKernel,frameParent); //Es gibt kein Parent Panel !!!
		//try {			
			this.setLayout(new BorderLayout());
			this.setKernelObject(objKernel);
			
			//TODOGOON; //20210306: Hier ein Abstaktes Modulobjekt erstellen
			IKernelModuleZZZ objModule = new KernelModuleZZZ(objKernel, sModule);
			//ausgehend von dem Modulnamen sModule,
			//Übergib dem Modul den Kernel!!!
			
			//### PANEL NORTH
			Panel_NORTHZZZ objPanelNorth = new Panel_NORTHZZZ(objKernel, objKernelChoosen, objModule, sProgram, this);
			this.add(objPanelNorth, BorderLayout.NORTH);
			this.setPanelSub("NORTH", objPanelNorth);
			
			
			//### PANEL CENTER
			/* NICHT L�SCHEN. FRAGE WARUM DAS SO IST ??? 
			//Laufleisten werden zwar hinzugef�gt, funktionieren aber nicht  !!!!!
			//+++ Als Zwischenschritt einen Scrollbaren Panel erzeugen 
			JPanel objPanelScroll = new JPanel();  
			objPanelScroll.setLayout(new BorderLayout());	 		
			JScrollBar hsb = new JScrollBar(Scrollbar.HORIZONTAL,1,10,1,100);			
			//hsb.addAdjustmentListener(this);
			 objPanelScroll.add(BorderLayout.SOUTH,hsb);  
			 Scrollbar vsb = new Scrollbar(Scrollbar.VERTICAL,  1,10,1,100);  
			 //vsb.addAdjustmentListener(this);
			 objPanelScroll.add(BorderLayout.EAST,vsb);  
			
			
		    Panel_CENTERZZZ objPanelCenter = new Panel_CENTERZZZ(objKernelJAZ, sModule, sProgram);	
		    objPanelScroll.add(objPanelCenter);  					
     		this.add(objPanelCenter, BorderLayout.CENTER);
     		
			//+++ Den Scrollbaren Panel hinzuf�gen
			this.add(objPanelScroll, BorderLayout.CENTER);
			*/
			
		
			//!!! Wichtig ist es, dass das EAST-Panel VOR dem CENTER-PANEL eingef�gt wird !!!
     		//### PANEL EAST
     		Panel_EASTZZZ objPanelEast = new Panel_EASTZZZ(objKernel, this, objKernelChoosen, objModule, sProgram);
     		this.add(objPanelEast, BorderLayout.EAST);
     		this.setPanelSub("EAST", objPanelEast); //Damit es von anderen Panels "greifbar" wird.

     		/* Am einfachsten ist es den JScrollPane anzuwenden. */
			Panel_CENTERZZZ objPanelCenter = new Panel_CENTERZZZ(objKernel, this, objKernelChoosen, objModule, sProgram);	
			JScrollPane jsp = new JScrollPane(objPanelCenter);					
     		this.add(jsp, BorderLayout.CENTER);  			 //Mit Bildlaufleisten. ALSO: �ber die JScrollPane wird das Panel_CENTERZZZ-Objekt eingebunden.
     		this.setPanelSub("CENTER", objPanelCenter); //Damit es von anderen Panels "greifbar" wird.
       		
     		
//     		TODO GOON die Inhalte der Textfelder des objPanelCenter f�r das objPanelEast abfragbar machen !!!
     		
     		
     		
     		//#########################################################################################################
     		//#########################################################################################################
     		 
     		//TODO die Inhalte der Textfelder f�r Notes abfragbar machen.      	
     		//TODO das Schliessen der Dialogbox f�r Notes abfragbar machen
			//String [] straToSet = {"a", "b", "c"}; 
					
			//Grundfl�che in Rahmen hinzuf�gen
			//objPanel = new LS2JPanel(objKernelJAZ, straToSet);
			//getContentPane().add(objPanel);	
			
			
			/*
			//Einfach mal 3 Felder, die dann auch per LotusScript ausgelesen werden k�nnen
			listaText = new ArrayList();
			if(saTextIn != null){
				for(int icount = 0; icount < saTextIn.length; icount ++){
				JTextField objText = new JTextField(saTextIn[icount], iTEXTFIELD_COLUMN_DEFAULT);
				listaText.add(objText);
				this.add(objText);
				}	
			} //END if saTextIn != null	
			*/
			
			/*
			// nun ein Feld, f�r den ApplicationKey, dies auch per LotusScript auslesbar machen.
			String strApplicationKey = this.getApplicationKey();
			
			JTextField objText = new JTextField(strApplicationKey, iTEXTFIELD_COLUMN_DEFAULT);
			listaText.add(objText);
			this.add(objText);	
			
			// nun den Wert aus der Konfiguration auslesen
			String strParam;
			strParam = this.getParameterValueByModuleAlias("SAPInterchange","TESTENTRY");
			
			JTextField objText2 = new JTextField(strParam, iTEXTFIELD_COLUMN_DEFAULT);
			listaText.add(objText2);
			this.add(objText2);	
			*/
		//	} catch (ExceptionZZZ ez) {
		//		ez.printStackTrace();
		//	}
	}
	

	
	
	//##################################################################
	//######### GETTER UND SETTER 
	
		
	}//END Class
	
