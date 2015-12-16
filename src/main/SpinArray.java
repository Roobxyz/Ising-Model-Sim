import java.lang.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.*;

class SpinArray extends JPanel implements ActionListener {

	int d;
	int[][] SA;//SpinArray
	double T;
	Container container;
	String DynString;
	JTextField[] ProbFields;
	JButton Randomiser;
	int maxDim = 300;

	SpinArray(int dim,double Temp,String Dynamics){
//		if((dim<maxDim)&&(dim>0)){
//			this.d=dim;
//		}else{
//			this.d=maxDim;
//		}
//		this.T=Temp;
//		this.DynString = Dynamics;
		this.setDyn(Dynamics);
		this.setTemp(Temp);
		this.setSize(dim);


		Randomiser = new JButton("Reset");
		Randomiser.setVerticalTextPosition(AbstractButton.CENTER);
		Randomiser.setHorizontalTextPosition(AbstractButton.LEADING);
		Randomiser.setActionCommand("Randomise");
		Randomiser.addActionListener(this);
		ProbFields = new JTextField[3];

		ProbFields[0] = new JTextField("Dynamics",1);
		ProbFields[1] = new JTextField("Temperature",1);
		ProbFields[2] = new JTextField("System Size",1);

		for(int i=0;i<3;i++){
			ProbFields[i].addActionListener(this);
		}

		SA = new int[d][d];
		for(int i =0;i<d;i++){
			for(int j =0;j<d;j++){
				SA[i][j] = SpinArray.RandSpin();	
			}
		}
	}

	//Randomly distributes +1,-1 onto the Array
	static int RandSpin(){

		int State=0;
		double rnd = Math.random();

		if(rnd<0.5){
			State =-1; 
		} else if(rnd>=0.5){
			State=1;
		}

		//System.out.println(rnd);

		return State;
	}


	//actionPerformed for ActionLener
	public void actionPerformed(ActionEvent AE){

		Object source = AE.getSource();

		if(source==ProbFields[0]){
			String NewDyn = ProbFields[0].getText();	
			this.setDyn(NewDyn);
		}

		if(source==ProbFields[1]){
			double NewTemp = Double.parseDouble(ProbFields[1].getText());	
			this.setTemp(NewTemp);
		}

		if(source==ProbFields[2]){
			int NewSize = Integer.parseInt(ProbFields[2].getText());	
			this.setSize(NewSize);
		}



		//Random Button is pressed
		if("Randomise".equals(AE.getActionCommand())){
			for(int i =0;i<d;i++){
				for(int j =0;j<d;j++){
					SA[i][j] = SpinArray.RandSpin();
				}
			}
		}
	}



	void flip(int i, int j){
		SA[i][j] *= -1;
	}

	void Evol(int n){
		while(n==0){
			if(DynString.equals("K") == true){
				KawasakiEvol(1);
			}else if(DynString.equals("G") == true){
				GlauberEvol(1);
			}
		}
		if(n!=0){
			if(DynString.equals("K") == true){
				KawasakiEvol(n);
			}else if(DynString.equals("G") == true){
				GlauberEvol(n);
			}
		}
	}



	void GlauberEvol(int nStep){
		for(int m=0;m<nStep;m++){

			int i = (int)((d) * Math.random());
			int j = (int)((d) * Math.random());

			double E1 = getEnergy(i,j);
			flip(i,j); //flip to E2
			double E2 = getEnergy(i,j);
			flip(i,j); //flip back to E1
			double E = E2-E1;

			double P=1;
			double Q=0;

			if(E<=0){
				flip(i,j);
			}else if(E>0){

				P = Math.exp(-E/T);
				Q = Math.random();
				if(T>0){

					if(P>=Q){
						flip(i,j);	
						//	System.out.println("Flipped!");
					}
				}
			}
			repaint();
		}
	}

	void KawasakiEvol(int nStep){

		for(int m=0;m<nStep;m++){

			int i1 = (int)((d) * Math.random());
			int j1 = (int)((d) * Math.random());
			int i2 = (int)((d) * Math.random());
			int j2 = (int)((d) * Math.random());

			//Two selected spins energy computed
			int spin1 = SA[i1][j1];
			int spin2 = SA[i2][j2];
			double E1 = getEnergy(i1,j1);
			double E2 = getEnergy(i2,j2);
			double TotE1 = E1+E2;

			//exchange two spins and re-compute energy
			SA[i1][j1] = spin2;
			SA[i2][j2] = spin1;
			E1 = getEnergy(i1,j1);
			E2 = getEnergy(i2,j2);
			double TotE2 = E1+E2;
			//compute Energy difference between unexchanged state and exchanged state
			double E = TotE2-TotE1;

			double P=1;
			double Q=0;

			if(E<=0){
				//Leave particles exchanged

			}else if(E>0){

				P = Math.exp((-1/T)*E);
				Q = Math.random();

				//back to original
				SA[i1][j1] = spin1;
				SA[i2][j2] = spin2;
				if(T>0){

					if(P>=Q){
						//exchange
						SA[i1][j1] = spin2;
						SA[i2][j2] = spin1;
					}

				}
			}
			repaint();
		}
	}

	//Paint method to Draw Array
	public void paintComponent(Graphics g) {

		int sizex = this.getWidth();
		int sizey = this.getHeight();
		sizex /= d; 
		sizey /= d;
		super.paintComponent(g);
		for(int i=0;i<d;i++){
			for(int j=0;j<d;j++){
				if(this.SA[i][j]==1) g.setColor(Color.blue);	
				else if(this.SA[i][j]==-1) g.setColor(Color.orange);
				g.fillRect(i*sizex,j*sizey,sizex,sizey);
				//g.setColor(Color.black);
				//g.drawRect(i*sizex,j*sizey,sizex,sizey);
			}
		}
	}




	//get and set methods

	int getParticleAt(int i, int j){
		return SA[i][j];
	}


	int getSpin(int i,int j){
		return SA[i][j];
	}

	int Dim(){
		return d;
	}

	void setAllSpnis(int state){
		for(int i = 0;i<d;i++){
			for(int j = 0;j<d;j++){
				setSpin(state,i,j);
			}
		}
	}

	void setSpin(int state, int i,int j){
		SA[i][j]= state;
	}

	void setTemp(double NewT){
		
		this.T = NewT;
		System.out.println("Temp: "+this.T);
	}

	void setDyn(String NewD){
		if((NewD.equals("G")) || (NewD.equals("K"))){
			this.DynString = NewD;
		}else{
			System.out.println("Dyamics G or K");
		}
		System.out.println("Dynamics: "+this.DynString);
	}

	void setSize(int NewN){
		if((NewN<=this.maxDim) && (NewN >0)){
			this.d = NewN;
		}else{
			System.out.println("choose System size between 0 - 300");
		}
		System.out.println("Size: "+this.d);
	}

	JTextField[] getJText(){
		return ProbFields;
	}

	JButton getButton(){
		return Randomiser;
	}


	double getEnergy(int i,int j){

		double E=0.;

		//E = -J Σ S_{i}S_{j}
		int Spin = SA[i][j];

		int SpinN = 0;
		int SpinE = 0;	
		int SpinS = 0;
		int SpinW = 0;

		if(j!=(d-1))  SpinE = SA[i][j+1];
		else if(j==(d-1))  SpinE = SA[i][0];

		if(SpinE != Spin){
			E-=SpinE;
		}

		if(j!=0)  SpinW = SA[i][j-1];
		else if(j==0)  SpinW = SA[i][(d-1)];

		if(SpinW != Spin){
			E-=SpinW;
		}

		if(i!=(d-1)) SpinN = SA[i+1][j];
		else if(i==(d-1))  SpinN = SA[0][j];

		if(SpinN != Spin){
			E-=SpinN;
		}

		if(i!=0  ) SpinS = SA[i-1][j];
		else if(i==0)  SpinS = SA[(d-1)][j];

		if(SpinS != Spin){
			E-=SpinS;
		}

		E*=Spin;
		return E;
	}

	double[] totMag(){
		double M[] = new double[2];
		for(int i =0;i<d;i++){
			for(int j =0;j<d;j++){
				M[0]+=SA[i][j];
				M[1]+=SA[i][j]*SA[i][j];
			}
		}
		M[0]/=(d*d);
		M[1]/=(d*d);

		return M;
	}

	double[] totEnergy(){
		double E[]  = new double[2];
		for(int i =0;i<d;i++){
			for(int j =0;j<d;j++){
				E[0]+=getEnergy(i,j);
				E[1]+=getEnergy(i,j)*getEnergy(i,j);
			}
		}
		E[0]/=2*(d*d);
		E[1]/=2*(d*d);
		return E;
	}
}

