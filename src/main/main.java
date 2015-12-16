import java.io.*;

class main{
	public static void main(String[] args)throws IOException {

	//default params
		int N = 300;
		double T = 1;
		String PartDyn = "G";

		SpinArray S = new SpinArray(N,T,PartDyn);
		FrameO frameo = new FrameO(S);
		frameo.draw();
		S.Evol(0);	
	}
}
