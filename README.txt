/***

 *** The Ising Model

 ***/

	Ising Model of magnetism simulation using java. Each square in the simulation represents an atom, with its colour representing its spin state (up or down). The simulation os interactive, the user can modify the temperature, the Dynamics used for updating the grid and the system size.

	To compile:
		cd src/main/
		javac main.java

	To run: 
		java main


	./src/:
	
		Partilce.java extends JPanel and is the "Square" graphic object on the ContentPane of the frame. Overrides the paintComponent() method.
	
		SpinArray.java is a class which holds information about the overall system of particles. Methods retuning total system energy etc. are included here -- as are the Dynamical evolution methods (both Kawasaki and Glauber)
	
		FrameO.java is the frame for which to draw the particles on.
	
		MakePlots.java can be run to get plots of variables
	
		Kawasaki = K
		Glauber = G


	/***points to Note***/

	Glauber can change the relative numbers of spin up and spin down particles and hence the two eqm states are different. Implemented in SpinArray.java.

	Only Nearest neighbours with a differing Spin are considered rather than all nearest Neighbours. Implemented in SpinArray.java.

	Glauber and Kawasaki sample diff equilibriums. Kawasaki doesn't spin flip, it only exchanges particles, so the Magnetisation is conserved. 
	Glauber can change the relative numbers of spin up and spin down particles and hence the two eqm states are different. Implemented in SpinArray.java.

	High temp => Higher entropy => more disorder. 
	Low temp => lower entropy => more ordered appearance.
	
	Lower temperatures tend to cause particles to "clump" or stay together in low energy states. Whereas high temps see a much more random distribution of spins. This behaviour is evident for both systems. However Glauber tends to produce larger "clumps" at low temps, while Kawasaki produces many smaller "clumps".
	
	Kawasaki does not change total magnetisation, it only exchanges pairs geometrically within the array, hence, each M(T) is generated randomly and does not depend on T.

	When the temp is dropped (Below T_{Crit}) while the system is evolving, the system condenses into an equilibrium state dependent on the state it was in before the temp drop. 

	Glauber) If T is dropped to 0, the spins align either all together,  or with highly ordered "striped" regions given enough time.

	Kawasaki) T -> 0 gives a stable eqm state which has minor fluctiations (corresponding to E=0 considerations)


	/***
	  Aaron ReubenTaylor
	  reuben91@me.com
	 ***/
