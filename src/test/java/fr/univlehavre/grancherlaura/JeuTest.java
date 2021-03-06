package fr.univlehavre.grancherlaura;

import static org.junit.Assert.*;
import static fr.univlehavre.grancherlaura.Pile.Tablette.*;
import static fr.univlehavre.grancherlaura.Jeu.ModeDeJeu.*;

import java.util.ArrayList;


import org.junit.*;


public class JeuTest
{
	Jeu jeu;
	
	@Before
	public void setup()
	{	
		jeu = new Jeu(AUCUN);
	}
	
	@Test
	public void getListePileTest() 
	{		
		ArrayList<Pile> listeAttendu =new ArrayList<Pile>();
		
		Pile p1 = new Pile(VERTE);
		Pile p2 = new Pile(ROUGE);
		Pile p3 = new Pile(NOIRE);
		
		p2.addPile(p3);
		
		listeAttendu.add(p1);
		listeAttendu.add(p2);
		listeAttendu.add(p3);
		
		Jeu j = new Jeu(listeAttendu, AUCUN);
		
		ArrayList<Pile> listeTrouve = j.getListePile();
		
		assertEquals(listeAttendu, listeTrouve);	
	}
	
	@Test
	public void pileValideTest() 
	{
		boolean resultatTrouve = jeu.pileValide(2);

		boolean resultatTrouve2 = jeu.pileValide(-1);
		boolean resultatTrouve3 = jeu.pileValide(12);
		
		assertTrue(resultatTrouve);
		assertFalse(resultatTrouve2);
		assertFalse(resultatTrouve3);
	}
	
	@Test
	public void poserTest1()
	{
		ArrayList<Pile> listePileAttendu = new ArrayList<Pile>();
		
		Pile p1= new Pile(VERTE);
		Pile p2= new Pile(VERTE);
		Pile p3= new Pile(VERTE);
		Pile p4= new Pile(NOIRE);
		Pile p5= new Pile(NOIRE);
		Pile p6= new Pile(NOIRE);
		Pile p7= new Pile(ROUGE);
		Pile p8= new Pile(ROUGE);
		Pile p9= new Pile(ROUGE);
		Pile p10= new Pile(BEIGE);
		Pile p11= new Pile(BEIGE);
		Pile p12= new Pile(BEIGE);	
		
		p1.addPile(p4); // VERTE NOIRE
		p11.addPile(p5); // BEIGE NOIRE
		p1.addPile(p11); // VERTE NOIRE BEIGE NOIRE
		// p4 , p5 et p11 supprimees
		
		listePileAttendu.add(p1);
		listePileAttendu.add(p2);
		listePileAttendu.add(p3);
		listePileAttendu.add(p6);
		listePileAttendu.add(p7);
		listePileAttendu.add(p8);
		listePileAttendu.add(p9);
		listePileAttendu.add(p10);
		listePileAttendu.add(p12);
		
		Jeu jAttendu = new Jeu(listePileAttendu, AUCUN);
		
		Jeu jTrouve = new Jeu(AUCUN);
		
		jTrouve.poser(0,3); // VERT NOIRE, 3 est supprimee
		jTrouve.poser(9,3); // BEIGE NOIRE, 3 est supprimee
		jTrouve.poser(0,8); // VERTE NOIRE BEIGE NOIRE 8 est supprimee
	
		assertEquals(jAttendu.toString(), jTrouve.toString());	
	}	
	
	@Test
	public void poserTest2()
	{	
		boolean resultatTrouve = jeu.poser(0,15); // pas de pile à l'emplacement 15
		
		boolean resultatTrouve2 = jeu.poser(-1,3); // pas de pile à l'emplacement -1
	
		jeu.poser(1,2); // VERTE VERTE
		boolean resultatTrouve3 = jeu.poser(1, 8); // BEIGE sur VERTE VERTE
		
		assertFalse(resultatTrouve);	
		assertFalse(resultatTrouve2);
		assertFalse(resultatTrouve3);
	}
	
	@Test
	public void solutionTest1()
	{
		Pile p1 = new Pile(VERTE);
		Pile p2 = new Pile(ROUGE);
		Pile p3 = new Pile(BEIGE);
		
		p1.addPile(p2);
		
		ArrayList<Pile> listePile = new ArrayList<Pile>();
		listePile.add(p1);
		listePile.add(p3);
		
		Jeu j = new Jeu(listePile, AUCUN);
		
		boolean resultatTrouve = j.solution();
		
		assertFalse(resultatTrouve);		
	}

	@Test
	public void solutionTest2()
	{
		Pile p1 = new Pile(VERTE);
		Pile p2 = new Pile(ROUGE);
		Pile p3 = new Pile(ROUGE);
		
		p1.addPile(p2);
		
		ArrayList<Pile> listePile = new ArrayList<Pile>();
		listePile.add(p1);
		listePile.add(p3);
		
		Jeu j = new Jeu(listePile, AUCUN);
		
		boolean resultatTrouve = j.solution();
		
		assertTrue(resultatTrouve);		
	}
	
	@Test
	public void toStringTest()
	{
		String resultatAttendu = "1) Pile composée de 1 tablettes :\n- VERTE\n\n2) Pile composée de 2 tablettes :\n- NOIRE\n- ROUGE\n\n";
		Pile p1 = new Pile(VERTE);
		Pile p2 = new Pile(ROUGE);
		Pile p3 = new Pile(NOIRE);
		
		p2.addPile(p3);
		
		ArrayList<Pile> listePiles = new ArrayList<Pile>();
		listePiles.add(p1);
		listePiles.add(p2);
		
		Jeu j = new Jeu(listePiles, AUCUN);
		String resultatTrouve = j.toString();
		
		assertEquals(resultatAttendu, resultatTrouve);	
	}
}
