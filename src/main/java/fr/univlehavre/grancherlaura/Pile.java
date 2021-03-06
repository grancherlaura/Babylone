package fr.univlehavre.grancherlaura;
import java.util.ArrayList;

public class Pile 
{
	private ArrayList<Tablette> listeTablettes;
	
	public static enum Tablette { VERTE, NOIRE, ROUGE, BEIGE};
	
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
			listeTablettes.addAll(p.getListeTablettes());
		}
		
		return possible;	
	}

	boolean memeHauteur(Pile autrePile)
	{
		return getHauteur() == autrePile.getHauteur();
	}
	
	boolean memeSommet(Pile autrePile) 
	{
		return getSommet().equals(autrePile.getSommet());
	}
	
	// retourne vrai s'il est possible de poser la pile p sur la pile courante
	public boolean peutPoser(Pile p)
	{
		return memeHauteur(p) || memeSommet(p);
	}
	
	public Tablette getSommet()
	{
		return listeTablettes.get(listeTablettes.size()-1);
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
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listeTablettes == null) ? 0 : listeTablettes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		
		if (obj == null)
		{
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		Pile other = (Pile) obj;
		
		if (listeTablettes == null) 
		{
			if (other.listeTablettes != null)
			{
				return false;
			}
		} 
		
		else if (!listeTablettes.equals(other.listeTablettes))
		{
			return false;
		}
		
		return true;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Pile composée de "+getHauteur()+" tablettes :");
		
		for(int i=getHauteur()-1; i>=0;i--)
		{
			sb.append("\n- ");
			sb.append(listeTablettes.get(i));
		}
		
		sb.append("\n");
		
		return sb.toString();
	}
}
