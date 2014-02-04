package fr.univlehavre.grancherlaura;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import fr.univlehavre.grancherlaura.Pile.Tablette;

public class Jeu
{
	private int tourJoueur = 1;
	private int gagnant=0;
	private final static int NB_TABLETTES_PAR_COULEUR=3;
	private ArrayList<Pile> listePiles;
	private Scanner scanner;
	
	public Jeu(String choix)
	{
		listePiles = new ArrayList<Pile>();
		
		for(Tablette tablette : Tablette.values())
		{
			for(int i=0; i<NB_TABLETTES_PAR_COULEUR; i++)
				listePiles.add(new Pile(tablette));
		}
		
		if(choix.equals("hasard"))
		{
			System.out.println("Joueur gagnant :"+this.jouerHasard());
			System.out.println(this.toString());
		}
		
		else if(choix.equals("interactif"))
		{
			this.jouer();
		}
	}
	
	public Jeu(ArrayList<Pile> listePiles, String choix)
	{
		this.listePiles = listePiles;
		
		if(choix.equals("hasard"))
		{
			System.out.println("Joueur gagnant :"+this.jouerHasard());
			System.out.println(this.toString());
		}
		
		else if(choix.equals("interactif"))
		{
			this.jouer();
		}
	}
	
	// jeu au hasard
	public int jouerHasard()
	{
		while(gagnant==0)
		{				
			// on tire deux piles qu'on va essayer de poser
			int pile1 = (int) (Math.random()*listePiles.size());
			int pile2 = pile1;
			boolean pose=false;
			
			// tant que l'on ne peut pas jouer, on reessaye avec deux autres piles
			while(pile1==pile2 || !pose)
			{
				pile1 = (int) (Math.random()*listePiles.size());
				pile2 = (int) (Math.random()*listePiles.size());
				
				if(pile1!=pile2)
				{
					pose=poser(pile1,pile2);
					
					if(!pose)
						pose=poser(pile2,pile1);
				}
			}
			
			
			// s'il ne reste plus de solution, le joueur courant a gagne
			if(!solution())
			{
				gagnant=tourJoueur;
			
			}
			
			// sinon la partie continue !
			else
			{
				// on change de joueur
				if(tourJoueur==1)
					tourJoueur=2;
				
				else
					tourJoueur=1;
			}
		}
		
		return gagnant;
	}
	
	public boolean pileValide(int pile)
	{
		boolean pileValide=true;
		
		if(pile<0 || pile>=listePiles.size())
			pileValide=false;
		
		return pileValide;
	}
	
	public int joueurCourant(int compteur)
	{
		int joueurCourant=compteur%2;
		
		if(joueurCourant==0)
			joueurCourant=2;
		
		return joueurCourant;
	}
	
	public void jouer()
	{
		scanner = new Scanner(System.in);
		boolean pose=true;
		boolean continuer=true;
		int compteur=1;
		
		// tant que l'on peut continuer la partie
		while(continuer)
		{	
			// on recupere le joueur à qui c'est le tour
			tourJoueur=joueurCourant(compteur);
			
			int pileDessus=-1;
			int pileDessous=-1;
			int nbErreur=0;
			
			// on affiche les piles
			if(pose)
				System.out.println(this);
			
			System.out.println("\n---Joueur "+tourJoueur+"---");
			
			// tant que le numero de la pile du dessus n'est pas valide
			while(!pileValide(pileDessus))
			{
				if(nbErreur>0)
					System.err.print("Mauvais numéro de Pile ! Veuillez taper un nombre entre 1 et " +listePiles.size()+"\nNouvelle pile : \n\n");
				
				else
					System.out.println("Pile à prendre : ");
				
				String dessus = scanner.nextLine();
				
				try 
				{
					pileDessus = Integer.parseInt(dessus)-1;
				} 
				
				catch (NumberFormatException e) 
				{
					pileDessus=-1;
				}
	
				nbErreur++;
			}
			
			nbErreur=0;
			
			// tant que le numero de la pile du dessous n'est pas valide
			while(!pileValide(pileDessous))
			{
				if(nbErreur>0)
					System.err.print("Mauvais numéro de Pile ! Veuillez taper un nombre entre 1 et " +listePiles.size()+"\nNouvelle pile : \n\n");
				
				else
					System.out.println("Poser la pile "+(pileDessus+1)+" sur la pile :");
				
				String dessous = scanner.nextLine();
				pileDessous = Integer.parseInt(dessous)-1;
				nbErreur++;
			}
			
			// on pose la pile
			pose = poser(pileDessous, pileDessus);
			
			// si on ne peut pas poser la pileDessus sur la pileDessous
			if(!pose)
				System.err.print("\nImpossible de poser la pile "+(pileDessus+1)+" sur la pile "+(pileDessous+1)+" ! \n\n");
			
			// on verifie s'il reste des solutions
			continuer=solution();
			
			// si l'on a pu pose et que l'on peut continuer, on change de joueur
			if(pose && continuer)
				compteur++;
		}
		
		System.out.println(this+"\nBravo joueur : "+tourJoueur);
	}
	
	// retourne vrai si on peut encore jouer, faux si le jeu est fini
	public boolean solution()
	{
		boolean solutionExiste=false;
		
		for(int pile1=0; pile1<listePiles.size() && !solutionExiste ; pile1++)
		{
			for(int pile2=0; pile2<listePiles.size() && !solutionExiste ; pile2++)
			{
				if(pile1!=pile2)
				{
					if(listePiles.get(pile1).peutPoser(listePiles.get(pile2)) || listePiles.get(pile2).peutPoser(listePiles.get(pile1)))
					{
						solutionExiste=true;
					}	
				}
					
			}
		}
		
		return solutionExiste;
	}
	
	// pose la pileDessus sur la pileDessus si cela est possible
	public boolean poser(int pileDessous, int pileDessus)
	{
		boolean possible=false;
		
		// si les numeros de pile existent
		if(pileDessous>=0 && pileDessous<listePiles.size() && pileDessus>=0 && pileDessus<listePiles.size())
		{
			possible = listePiles.get(pileDessous).addPile(listePiles.get(pileDessus));
		
			if(possible)
				listePiles.remove(pileDessus);
		}	
		return possible;
	}
	
	public ArrayList<Pile> getListePile()
	{
		return listePiles;
	}
	
	public String toString()
	{
		String s="";
		
		for(int i=0; i<listePiles.size(); i++)
		{
			s+=(i+1)+") " +listePiles.get(i)+"\n";
		}
		
		return s;
	}
	
	public static void main(String args[])
	{
		Jeu j = new Jeu("hasard");
	}
}
