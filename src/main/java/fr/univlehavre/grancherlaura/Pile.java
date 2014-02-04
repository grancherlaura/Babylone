package fr.univlehavre.grancherlaura;
import java.util.ArrayList;

public class Pile 
{
	private ArrayList<Tablette> listeTablettes;
	
	public enum Tablette { VERTE, NOIRE, ROUGE, BEIGE};
	
	public Pile(Tablette tablette)
	{
		listeTablettes = new ArrayList<Tablette>();
		listeTablettes.add(tablette);
	}
	
	public Pile(ArrayList<Tablette> liste)
	{
		listeTablettes = liste;
	}
	
	// ajoute la pile p sur la pile courante si cela est possible
	public boolean addPile(Pile p)
	{			
		boolean possible=peutPoser(p);
		
		if(possible)
		{
			for(int i=0; i<p.getHauteur(); i++)
				listeTablettes.add(p.get(i));
		}
		
		return possible;	
	}
	
	// retourne vrai s'il est possible de poser la pile p sur la pile courante
	public boolean peutPoser(Pile p)
	{
		boolean possible;
		
		// si les deux piles ont la même hauteur ou le même sommet
		if(this.getHauteur()==p.getHauteur() || this.getSommet().equals(p.getSommet()))
		{			
			possible=true;
		}
		
		else
		{
			possible=false;
		}
		
		return possible;
	}
	
	public Tablette getSommet()
	{
		return listeTablettes.get(listeTablettes.size()-1);
	}
	
	public Tablette get(int index)
	{
		return listeTablettes.get(index);
	}
	
	public int getHauteur()
	{
		return listeTablettes.size();
	}
	
	public ArrayList<Tablette> getListeTablettes()
	{
		return listeTablettes;
	}
	
	@Override
	public boolean equals(Object p)
	{
		boolean sontEgaux=true;
		
		if(this.getHauteur()!=((Pile) p).getHauteur())
		{
			sontEgaux = false;
		}
		
		for(int i=0; i<this.getHauteur()  && sontEgaux==true; i++)
		{
			if(!this.get(i).equals(((Pile) p).get(i)))
				sontEgaux =false;
		}
		
		return sontEgaux;
	}
	
	public String toString()
	{
		String s = "Pile composée de "+getHauteur()+" tablettes :";
		
		for(int i=getHauteur()-1; i>=0;i--)
			s+="\n- "+get(i);
		
		s+="\n";
		
		return s;
	}
}
