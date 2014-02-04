package babylone;
import static babylone.Pile.Tablette.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import babylone.Pile.Tablette;

import org.junit.*;


public class PileTest 
{
	Pile p1, p2, p3, p4;
	
	@Before
	public void setup()
	{	
		p1 = new Pile(VERTE);
		p2 = new Pile(BEIGE);
		p3 = new Pile(ROUGE);
		p4 = new Pile(NOIRE);
	}
	
	@Test
	public void getSommetTest() 
	{		
		Tablette sommetAttendu = VERTE;
		Tablette sommetTrouve = p1.getSommet();
		
		Tablette sommetAttendu2 = BEIGE;
		Tablette sommetTrouve2 = p2.getSommet();
		
		Tablette sommetAttendu3 = ROUGE;
		Tablette sommetTrouve3 = p3.getSommet();
		
		Tablette sommetAttendu4 = NOIRE;
		Tablette sommetTrouve4 = p4.getSommet();
		
		p1.addPile(p2); // VERTE BEIGE
		p4.addPile(p3); // NOIRE ROUGE
		
		Tablette sommetTrouve5 = p1.getSommet();
		Tablette sommetTrouve6 = p4.getSommet();
		
		p1.addPile(p4); // VERTE BEIGE NOIRE ROUGE
		
		Tablette sommetTrouve7 = p1.getSommet();
		
		assertEquals(sommetAttendu, sommetTrouve);	
		assertEquals(sommetAttendu2, sommetTrouve2);	
		assertEquals(sommetAttendu3, sommetTrouve3);	
		assertEquals(sommetAttendu4, sommetTrouve4);	
		assertEquals(sommetAttendu2, sommetTrouve5);
		assertEquals(sommetAttendu3, sommetTrouve6);
		assertEquals(sommetAttendu3, sommetTrouve7);
		
	}
	
	@Test
	public void getTest() 
	{	
		Tablette tabletteAttendu = VERTE;
		Tablette tabletteAttendu2 = ROUGE;
		Tablette tabletteAttendu3 = BEIGE;
		Tablette tabletteAttendu4 = NOIRE;
		
		p1.addPile(p3); // VERTE ROUGE
		p2.addPile(p4); // BEIGE NOIRE
		p1.addPile(p2);	// VERTE ROUGE BEIGE NOIRE
		
		Tablette tabletteTrouve = p1.get(0);
		Tablette tabletteTrouve2 = p1.get(1);
		Tablette tabletteTrouve3 = p1.get(2);
		Tablette tabletteTrouve4 = p1.get(3);
				
		assertEquals(tabletteAttendu, tabletteTrouve);	
		assertEquals(tabletteAttendu2, tabletteTrouve2);	
		assertEquals(tabletteAttendu3, tabletteTrouve3);	
		assertEquals(tabletteAttendu4, tabletteTrouve4);	
	}
	
	@Test
	public void getHauteur() 
	{		
		int hauteurAttendu = 4;
		int hauteurAttendu2 = 2;
		int hauteurAttendu3 = 1;
		
		p3.addPile(p4); 
		p1.addPile(p2); 
		p3.addPile(p1);
		
		int hauteurTrouve = p3.getHauteur();
		int hauteurTrouve2 = p1.getHauteur();
		int hauteurTrouve3 = p2.getHauteur();
				
		assertEquals(hauteurAttendu, hauteurTrouve);
		assertEquals(hauteurAttendu2, hauteurTrouve2);
		assertEquals(hauteurAttendu3, hauteurTrouve3);
	}
	
	@Test
	public void getListeTablettesTest() 
	{		
		ArrayList<Tablette> listeAttendu =new ArrayList<Tablette>(Arrays.asList(ROUGE, NOIRE, VERTE, BEIGE));
		p3.addPile(p4); // ROUGE NOIRE
		p1.addPile(p2); // VERTE BEIGE
		
		p3.addPile(p1); // ROUGE NOIRE VERTE BEIGE
		
		ArrayList<Tablette> listeTrouve=p3.getListeTablettes();
				
		assertEquals(listeAttendu, listeTrouve);	
	}
	
	@Test
	public void peutPoserTest1() 
	{		
		boolean reponseAttendu = true;
		boolean reponseTrouve = p1.peutPoser(p2); // taille 1 sur taille 1
			
		p1.addPile(p2);
		p3.addPile(p4);
		
		boolean reponseTrouve2 = p1.peutPoser(p2); // sommet BEIGE sur sommet BEIGE
		boolean reponseTrouve3 = p3.peutPoser(p4); // sommet NOIR sur sommet NOIR
		boolean reponseTrouve4 = p1.peutPoser(p3); // taille 2 sur taille 2
		
		assertEquals(reponseAttendu, reponseTrouve);	
		assertEquals(reponseAttendu, reponseTrouve2);
		assertEquals(reponseAttendu, reponseTrouve3);
		assertEquals(reponseAttendu, reponseTrouve4);
	}
	
	@Test
	public void peutPoserTest2() 
	{		
		boolean reponseAttendu = false;
		
		p1.addPile(p2);	// VERTE BEIGE
		
		boolean reponseTrouve = p1.peutPoser(p3);  // sommet ROUGE sur sommet BEIGE, tailles 1 sur 2
		
		p3.addPile(p4); // ROUGE NOIRE
		
		boolean reponseTrouve2 = p4.peutPoser(p1);  // sommet BEIGE sur sommet NOIR, tailles 2 sur 1
		boolean reponseTrouve3 = p3.peutPoser(p2);  // sommet BEIGE sur sommet NOIR, tailles 1 sur 2
	
		assertEquals(reponseAttendu, reponseTrouve);
		assertEquals(reponseAttendu, reponseTrouve2);
		assertEquals(reponseAttendu, reponseTrouve3);
	}
	
	@Test
	public void addPileTest1() 
	{		
		ArrayList<Tablette> liste1 = new ArrayList<Tablette>(Arrays.asList(ROUGE, NOIRE));
		Pile pileAttendue = new Pile(liste1);
		
		ArrayList<Tablette> liste2 = new ArrayList<Tablette>(Arrays.asList(VERTE, BEIGE));
		Pile pileAttendue2 = new Pile(liste2);
		
		ArrayList<Tablette> liste3 = new ArrayList<Tablette>(Arrays.asList(ROUGE, NOIRE, VERTE, BEIGE));
		Pile pileAttendue3 = new Pile(liste3);
		
		ArrayList<Tablette> liste4 = new ArrayList<Tablette>(Arrays.asList(VERTE, BEIGE, ROUGE, NOIRE, VERTE, BEIGE));
		Pile pileAttendue4 = new Pile(liste4);
		
		p3.addPile(p4); // ROUGE NOIRE
		assertEquals(pileAttendue, p3);
		
		p1.addPile(p2); // VERTE BEIGE 
		assertEquals(pileAttendue2, p1);	
		
		p3.addPile(p1); // ROUGE NOIRE VERTE BEIGE 
		assertEquals(pileAttendue3, p3);
		
		p1.addPile(p3); // VERTE BEIGE ROUGE NOIRE VERTE BEIGE 
		assertEquals(pileAttendue4, p1);			
	}
	
	@Test
	public void addPileTest2() 
	{		
		boolean reponseAttendu = false;
		p1.addPile(p2); // 2 tablettes (VERTE et BEIGE) dans la pile
		boolean reponseTrouve = p1.addPile(p3); // on rajoute la pile contenant la tablette ROUGE
				
		assertEquals(reponseAttendu, reponseTrouve);	
	}
	
	@Test
	public void toStringTest() 
	{		
		String reponseAttendu = "Pile composée de 4 tablettes :\n- BEIGE\n- VERTE\n- NOIRE\n- ROUGE\n";
		p3.addPile(p4); 
		p1.addPile(p2); 
		p3.addPile(p1);
		
		String reponseTrouve = p3.toString();
				
		assertEquals(reponseAttendu, reponseTrouve);	
	}	
}
