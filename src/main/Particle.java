import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.lang.Object;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Particle extends JPanel{
 
	int Spin;


	Particle(){
		Spin = RandSpin();
	}

	 static int RandSpin(){
		int SpinState=0;
		double rnd = Math.random();
		if(rnd<0.5){
			SpinState =-1; 
		}else if(rnd>=0.5){
			SpinState=1;
		}
		return SpinState;
	}

	 int getSpin(){
		 return this.Spin;
	 }

	 void setSpin(int NewSpin){
		 this.Spin = NewSpin;
	 }

	public void paintComponent(Graphics g) {
	  //      
		super.paintComponent(g);
		
	        if(this.Spin==1)g.setColor(Color.blue);	
		else if(this.Spin==-1)g.setColor(Color.orange);
		g.fillRect(0,0,100,100);
		g.setColor(Color.black);
		g.drawRect(0,0,99,99);
	//	repaint();

	}

}
