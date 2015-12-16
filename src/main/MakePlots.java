import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JButton;



class MakePlots{
	public static void main(String[] args)throws IOException {

	//	String PartNum = JOptionPane.showInputDialog("Enter dimension of system");
		int N = 50;//Integer.parseInt(PartNum);
	//	String PartTemp = JOptionPane.showInputDialog("Enter inital (scaled) System Temperature ");
		double T = 0.2;//Double.parseDouble(PartTemp);
	//	String PartTemp2 = JOptionPane.showInputDialog("Enter final (scaled) System Temperature ");
		double T2 = 5;//Double.parseDouble(PartTemp2);
		String PartDyn = "G";//JOptionPane.showInputDialog("Enter dynamics used in simulation (G or K)");


		SpinArray S = new SpinArray(N,T,PartDyn);
		String FileName1 = "";
		String FileName2 = "";
		String FileName3 = "";
		String FileName4 = "";

		if(PartDyn.equals("G")){

			FileName1="GSusecpt.txt";
			FileName2="GMagnetism.txt";
			FileName3="GEnergy.txt";
			FileName4="GHeatCap.txt";
		}else if(PartDyn.equals("K")){

			FileName1="KSusecpt.txt";
			FileName2="KMagnetism.txt";
			FileName3="KEnergy.txt";
			FileName4="KHeatCap.txt";
		}

		FileWriter outfile1 = new FileWriter(FileName1); 
		BufferedWriter out1 = new BufferedWriter(outfile1); 
		FileWriter outfile2 = new FileWriter(FileName2); 
		BufferedWriter out2 = new BufferedWriter(outfile2); 
		FileWriter outfile3 = new FileWriter(FileName3); 
		BufferedWriter out3 = new BufferedWriter(outfile3); 
		FileWriter outfile4 = new FileWriter(FileName4); 
		BufferedWriter out4 = new BufferedWriter(outfile4); 

		int count =0;
		int count2=0;
		int n=100000;

		int CycNum = 20;
		double dT=Math.abs(T-T2)/CycNum;


		double C[] = new double[CycNum];
		double Chi[] = new double[CycNum];

		for(int j =0;j<CycNum;j++){

			double[][] M= new double[2][n];
			double[][] E= new double[2][n];


			//for one simulated environment
			for(int i =0;i<n;i++){
				S.Evol(1);
				count++;

				M[0][i] = S.totMag()[0]; //avg M
				M[1][i] = S.totMag()[1]; //avg M^2
				E[0][i] = S.totEnergy()[0]; //avg E
				E[1][i] = S.totEnergy()[1]; //avg E^2
			}

			//avg 
			double avgM = Average(M[0]);
			double avgMSq = Average(M[1]);
			double sigM = Math.sqrt((avgMSq-avgM*avgM)/(M[0].length-1));
			Chi[j] = BootStrap(M,T)[0];
			double sigChi = BootStrap(M,T)[1];

			out1.write(T+" "+Chi[j]+" "+sigChi);
			out2.write(T+" "+avgM+" "+sigM);
			out1.newLine();
			out2.newLine();

			double avgE = Average(E[0]);
			double avgESq = Average(E[1]);
			double sigE = Math.sqrt((avgESq-avgE*avgE)/(E[0].length-1));				
			C[j] = BootStrap(E,T)[0];
			double sigC = BootStrap(E,T)[1];
			
			out3.write(T+" "+avgE+" "+sigE);
			out3.newLine();
			out4.write(T+" "+C[j]+" "+sigC);
			out4.newLine();

			T+=dT;
			S = new SpinArray(N,T,PartDyn);
			S.setTemp(T);

			//end cycle
			count= 0;
			//count2++;
		}

		out1.close();
		out2.close();
		out3.close();
		out4.close();
		outfile1.close();
		outfile2.close();
		outfile3.close();
		outfile4.close();

	}

	public static double Average(double[] data){
		double average=0;
		for(int i=0; i<data.length; i++ ){
			average+=data[i];
		}
		average = average/data.length;
		return average;
	}

	public static double []BootStrap(double [][] M,double T){
		
		double average=0;
		double c =0;
		double cSq =0;
		double[] C = new double[2];
		double avg1 = Average(M[0]);
		double avgSq1 = Average(M[1]);
		C[0]=(avgSq1 - (avg1*avg1))/T;


		for(int j=0;j<10000;j++){
			double[][] randData = new double[2][10000];
			for(int i=0; i<10000; i++ ){
				int rnd = (int)Math.random()*(M[0].length-1);
				randData[0][i] = M[0][rnd];
				randData[1][i] = M[1][rnd];
			}

			double avg = Average(randData[0]);
			double avgSq = Average(randData[1]);
			double value = (avgSq-(avg*avg))/T;
	
			c+=value;
			cSq+=(value*value);
		}

		c/=10000;
		cSq/=10000;
		double sigC = Math.sqrt(cSq - (c*c));
	//	C[0] = c;
		C[1] = sigC;
		return C;
	}
}


