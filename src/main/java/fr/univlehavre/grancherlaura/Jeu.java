package fr.univlehavre.grancherlaura;

import java.util.ArrayList;
import java.util.Scanner;

import fr.univlehavre.grancherlaura.Pile.Tablette;

public class Jeu
{
	public static enum ModeDeJeu {HASARD, INTERACTIF, AUCUN};
	private int tourJoueur = 1;
	private int gagnant=0;
	private final static int NB_TABLETTES_PAR_COULEUR=3;
	private ArrayList<Pile> listePiles;
	private Scanner scanner;
	
	public Jeu(ModeDeJeu choix)
	{
		listePiles = new ArrayList<Pile>();
		
		for(Tablette tablette : Tablette.values())
		{
			for(int i=0; i<NB_TABLETTES_PAR_COULEUR; i++)
				listePiles.add(new Pile(tablette));
		}
		
		if(choix.equals(ModeDeJeu.HASARD))
		{
			System.out.println("Joueur gagnant :"+this.jouerHasard());
			System.out.println(this.toString());
		}
		
		else if(choix.equals(ModeDeJeu.INTERACTIF))
		{
			this.jouer();
		}
	}
	
	public Jeu(ArrayList<Pile> listePiles, ModeDeJeu choix)
	{
		this.listePiles = listePiles;
		
		if(choix.equals(ModeDeJeu.HASARD))
		{
			System.out.println("Joueur gagnant :"+this.jouerHasard());
			System.out.println(this.toString());
		}
		
		else if(choix.equals(ModeDeJeu.INTERACTIF))
		{
			this.jouer();
		}
	}
	
	void changerJoueur() 
	{
		tourJoueur = 3 - tourJoueur;
	}
	
	// jeu au hasard
	public int jouerHasard()
	{
		while(gagnant==0)
		{				
			// on tire deux piles qu'on va essayer de poser
			int pile1=-1;
			int pile2=-1;
			boolean pose=false;
			
			// tant que l'on ne peut pas jouer, on reessaye avec deux autres piles
			while(pile1==pile2 || !pose)
			{
				pile1 = (int) (Math.random()*listePiles.size());
				pile2 = (int) (Math.random()*listePiles.size());
				
				if(pile1!=pile2)
				{
					pose=poser(pile1,pile2);
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
				changerJoueur();
			}
		}
		return gagnant;
	}
	
	public void jouer()
	{
		scanner = new Scanner(System.in);
		boolean pose=true; 
		boolean continuer=true;
		tourJoueur = 1;
		
		// tant que l'on peut continuer la partie
		while(continuer)
		{	
			// on affiche les piles
			if(pose)
			{
				System.out.println(this);
			}
			
			System.out.println("\n---Joueur "+tourJoueur+"---");
			
			int pileDessus = lecturePile(-1);
			int pileDessous= lecturePile(pileDessus);
			
			// on pose la pileDessus sur la pileDessous
			pose = poser(pileDessous, pileDessus);
			
			// si on ne peut pas poser la pileDessus sur la pileDessous
			if(!pose)
			{
				System.err.print("\nImpossible de poser la pile "+(pileDessus+1)+" sur la pile "+(pileDessous+1)+" ! \n\n");
			}
			
			// on verifie s'il reste des solutions
			continuer=solution();
			
			// si l'on a pu pose et que l'on peut continuer, on change de joueur
			if(pose && continuer)
			{
				changerJoueur();
			}
		}
		
		System.out.println(this+"\nBravo joueur "+tourJoueur+" !");
	}
	
	boolean pileInvalide(int pile) 
	{
		boolean nombreTropPetit;
		boolean nombreTropGrand;
		nombreTropPetit = pile < 0;
		nombreTropGrand = pile >= listePiles.size();
		return nombreTropPetit || nombreTropGrand;
	}

	public boolean pileValide(int pile)
	{
		return !pileInvalide(pile);
	}
	
	// demande au joueur le numero de la pile jusqu'a ce qu'il soit valide
	public int lecturePile(int dessus)
	{
		boolean erreurSaisie = false;
		int pile=-1;
		
		// tant que la pile n'est pas valide
		while(!pileValide(pile))
		{
			if(erreurSaisie)
			{
				System.err.print("Mauvais numéro de Pile ! Veuillez taper un nombre entre 1 et " +listePiles.size()+"\nNouvelle pile : \n\n");
			}
			
			else
			{	// on demande au joueur la pile du dessus
				if(dessus==-1)
				{
					System.out.println("Pile à prendre : ");
				}
				
				// on demande au joueur la pile du dessous
				else
				{
					System.out.println("Poser la pile "+(dessus+1)+" sur la pile :");
				}
			}
				
			
			String pileTapee = scanner.nextLine();
			
			// on regarde si ce que le joueur a rentré est bien un entier
			try 
			{
				pile = Integer.parseInt(pileTapee)-1;
			} 
			
			catch (NumberFormatException e) 
			{
				pile=-1;	
			}
			
			erreurSaisie = true;
		}
		
		return pile;
	}
	
	// retourne vrai si on peut encore jouer, faux si le jeu est fini
	public boolean solution()
	{
		boolean solutionExiste=false;
		
		// on parcourt les piles
		for(int pile1=0; pile1<listePiles.size()-1 && !solutionExiste ; pile1++)
		{
			for(int pile2=pile1+1; pile2<listePiles.size() && !solutionExiste ; pile2++)
			{
				if(listePiles.get(pile1).peutPoser(listePiles.get(pile2)))
				{
					solutionExiste=true;
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
		
			// si on a bien ajoute pileDessus sur pileDessous, on supprime pileDessus
			if(possible)
			{
				listePiles.remove(pileDessus);
			}
		}	
		
		return possible;
	}
	
	public ArrayList<Pile> getListePile()
	{
		return listePiles;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<listePiles.size(); i++)
		{
			sb.append((i+1)+") ");
			sb.append(listePiles.get(i));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public static void main(String args[])
	{
		//Jeu j = new Jeu(ModeDeJeu.HASARD);
		Jeu j2 = new Jeu(ModeDeJeu.INTERACTIF);
	}
}
