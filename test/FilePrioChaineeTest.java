/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leopold
 */
public class FilePrioChaineeTest {
    
    FilePrioChainee instance;
    
    public FilePrioChaineeTest() {
    }
    
    @Before
    public void setUp() {
        instance = new FilePrioChainee();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of enfiler method, of class FilePrioChainee.
     */
    @Test
    public void testEnfiler() {
        System.out.println("enfiler");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        assertEquals(instance.toString(), "tete [ e1(3) ] fin");
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        assertEquals(instance.toString(), "tete [ e2(7), e1(3) ] fin");
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        assertEquals(instance.toString(), "tete [ e2(7), e3(7), e1(3) ] fin");
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        assertEquals(instance.toString(),
              "tete [ e2(7), e3(7), e4(5), e1(3) ] fin"); 
        
        TachePrio element5 = new TachePrio(7, "e5");
        instance.enfiler(element5);
        assertEquals(instance.toString(),
         "tete [ e2(7), e3(7), e5(7), e4(5), e1(3) ] fin");
        
        TachePrio element6 = new TachePrio(9, "e6");
        instance.enfiler(element6);
        assertEquals(instance.toString(),
         "tete [ e6(9), e2(7), e3(7), e5(7), e4(5), e1(3) ] fin");
       
        TachePrio element7 = new TachePrio(5, "e7");
        instance.enfiler(element7);
        assertEquals(instance.toString(),
         "tete [ e6(9), e2(7), e3(7), e5(7), e4(5), e7(5), e1(3) ] fin");
        
        TachePrio element8 = new TachePrio(3, "e8");
        instance.enfiler(element8);
        assertEquals(instance.toString(),
         "tete [ e6(9), e2(7), e3(7), e5(7), e4(5), e7(5), e1(3), e8(3) ] fin");

        TachePrio element9 = new TachePrio(2, "e9");
        instance.enfiler(element9);
        assertEquals(instance.toString(),
         "tete [ e6(9), e2(7), e3(7), e5(7), e4(5), e7(5), e1(3), e8(3), e9(2)"
                 + " ] fin");
    }

    @Test(expected = NullPointerException.class)
    public void testEnfilerElNul() {
        System.out.println("enfiler - except");
        
        TachePrio element = null;
        instance.enfiler(element);
    }

    @Test(expected = NullPointerException.class)
    public void testEnfilerElNul1() {
        System.out.println("enfiler - except");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element = null;
        instance.enfiler(element);
    }
    
    /**
     * Test of defiler method, of class FilePrioChainee.
     */
    @Test
    public void testDefiler_0args() throws Exception {
        System.out.println("defiler");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        assertEquals(instance.taille(), 4);
        TachePrio expResult = element2;
        ITachePrio result = instance.defiler();
        assertEquals(expResult, result);
        assertEquals(instance.toString(), "tete [ e3(7), e4(5), e1(3) ] fin");

        TachePrio expResult1 = element3;
        ITachePrio result1 = instance.defiler();
        assertEquals(expResult1, result1);
        assertEquals(instance.toString(), "tete [ e4(5), e1(3) ] fin");
        
        assertEquals(instance.taille(), 2);
   }

    @Test(expected = FileVideException.class)
    public void testDefiler_0argsFilVid() throws Exception {
        System.out.println("defiler - file vide except");
        
        ITachePrio result = instance.defiler();
    }

    /**
     * Test of defiler method, of class FilePrioChainee.
     */
    @Test(expected = FileVideException.class)
    public void testDefiler_intFilVid() throws Exception {
        System.out.println("defiler (int) - file vide");
        int priorite = 0;
        
        Object expResult = null;
        Object result = instance.defiler(priorite);
        assertEquals(expResult, result);
    }
    @Test
    public void testDefiler_int() throws Exception {
        System.out.println("defiler (int)");
                
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        TachePrio element5 = new TachePrio(7, "e5");
        instance.enfiler(element5);
        
        TachePrio element7 = new TachePrio(5, "e7");
        instance.enfiler(element7);
        
        assertEquals(instance.taille(), 6);
        assertEquals(instance.toString(),
                "tete [ e2(7), e3(7), e5(7), e4(5), e7(5), e1(3) ] fin");
        
        assertEquals(instance.defiler(5), element4);
        assertEquals(instance.toString(), 
                "tete [ e2(7), e3(7), e5(7), e7(5), e1(3) ] fin");
        assertEquals(instance.taille(), 5);
        
        assertEquals(instance.defiler(7), element2);
        assertEquals(instance.toString(), 
                "tete [ e3(7), e5(7), e7(5), e1(3) ] fin");
        assertEquals(instance.taille(), 4);
        
        assertEquals(instance.defiler(3), element1);
        assertEquals(instance.toString(), 
                "tete [ e3(7), e5(7), e7(5) ] fin");
        assertEquals(instance.taille(), 3);
        
        assertEquals(instance.defiler(0), null);
        assertEquals(instance.toString(), 
                "tete [ e3(7), e5(7), e7(5) ] fin");
        assertEquals(instance.taille(), 3);
        
    }

    /**
     * Test of defilerTous method, of class FilePrioChainee.
     */
    @Test
    public void testDefilerTous() throws Exception {
        System.out.println("defilerTous");
        
                
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        TachePrio element5 = new TachePrio(7, "e5");
        instance.enfiler(element5);
        
        TachePrio element7 = new TachePrio(5, "e7");
        instance.enfiler(element7);
        
        assertEquals(instance.toString(),
                "tete [ e2(7), e3(7), e5(7), e4(5), e7(5), e1(3) ] fin");
        assertEquals(instance.taille(), 6);
        
        assertEquals(instance.defilerTous(4).toString(),
                "tete [  ] fin"); 
        assertEquals(instance.toString(),
                "tete [ e2(7), e3(7), e5(7), e4(5), e7(5), e1(3) ] fin");
        assertEquals(instance.taille(), 6);
        
        assertEquals(instance.defilerTous(5).toString(),
                "tete [ e4(5), e7(5) ] fin");
        assertEquals(instance.toString(),
                "tete [ e2(7), e3(7), e5(7), e1(3) ] fin");
        assertEquals(instance.taille(), 4);
        
        assertEquals(instance.defilerTous(3).toString(), 
                "tete [ e1(3) ] fin");
        assertEquals(instance.toString(), "tete [ e2(7), e3(7), e5(7) ] fin");
        assertEquals(instance.taille(), 3);
        
        assertEquals(instance.defilerTous(7).toString(), 
                "tete [ e2(7), e3(7), e5(7) ] fin");
        assertEquals(instance.toString(), "tete [  ] fin");
        assertEquals(instance.taille(), 0);
        
    }
    @Test(expected = FileVideException.class)
    public void testDefilerTousFilVid() throws Exception {
        System.out.println("defilerTous - file vide");
        int priorite = 0;
        
        IFilePrio result = instance.defilerTous(priorite);
    }

    /**
     * Test of prioriteExiste method, of class FilePrioChainee.
     */
    @Test
    public void testPrioriteExiste() {
        System.out.println("prioriteExiste");
        int priorite = 0;
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        boolean expResult = false;
        boolean result = instance.prioriteExiste(priorite);
        assertEquals(expResult, result);
        
        assertTrue(instance.prioriteExiste(3));
        assertTrue(instance.prioriteExiste(5));
        assertTrue(instance.prioriteExiste(7));
    }
    @Test
    public void testPrioriteExisteFilVid() {
        System.out.println("prioriteExiste - file vide");
        int priorite = 0;
        
        boolean expResult = false;
        boolean result = instance.prioriteExiste(priorite);
        assertEquals(expResult, result);
    }

    /**
     * Test of estVide method, of class FilePrioChainee.
     */
    @Test
    public void testEstVide() {
        System.out.println("estVide");
        
        assertEquals(true, instance.estVide());
        
        instance.enfiler(new TachePrio(0));
        assertEquals(false, instance.estVide());
    }

    /**
     * Test of taille method, of class FilePrioChainee.
     */
    @Test
    public void testTaille_0args() {
        System.out.println("taille");
        
        int expResult = 0;
        int result = instance.taille();
        assertEquals(expResult, result);
        
        instance.enfiler(new TachePrio(0));
        instance.enfiler(new TachePrio(2));
        assertEquals(2, instance.taille());
        
        try {
            instance.defiler();
        } catch (FileVideException ex) {
        }
        assertEquals(1, instance.taille());
    }

//    /**
//     * Test of taille method, of class FilePrioChainee.
//     */
//    @Test
//    public void testTaille_int() {
//        System.out.println("taille");
//        int priorite = 0;
//        
//        int expResult = 0;
//        int result = instance.taille(priorite);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }

    /**
     * Test of premier method, of class FilePrioChainee.
     */
    @Test(expected = FileVideException.class)
    public void testPremier_0argsFilVid() throws Exception {
        System.out.println("premier - except");
        
        Object result = instance.premier();
    }
    @Test
    public void testPremier_0args() throws Exception {
        System.out.println("premier");
        
        TachePrio expResult = new TachePrio(0);
        instance.enfiler(expResult);
        instance.enfiler(new TachePrio(-1));
        TachePrio expResult2 = new TachePrio(2);
        instance.enfiler(expResult2);
        ITachePrio result = instance.premier();
        assertEquals(expResult2, result);
    }

//    /**
//     * Test of premier method, of class FilePrioChainee.
//     */
//    @Test
//    public void testPremier_int() throws Exception {
//        System.out.println("premier");
//        int priorite = 0;
//        
//        Object expResult = null;
//        Object result = instance.premier(priorite);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
//
    /**
     * Test of vider method, of class FilePrioChainee.
     */
    @Test
    public void testVider() {
        System.out.println("vider");
        
        instance.enfiler(new TachePrio(0));
        assertFalse(instance.estVide());
        
        instance.vider();
        assertTrue(instance.estVide());
    }

    /**
     * Test of sousFilePrio method, of class FilePrioChainee.
     */
    @Test
    public void testSousFilePrioFilVid() throws FileVideException {
        System.out.println("sousFilePrio - file vide");
        int priorite = 0;
        
        IFilePrio expResult = new FilePrioChainee();
        IFilePrio result = instance.sousFilePrio(priorite);
        assertEquals(expResult.toString(), result.toString());
        assertEquals(instance.taille(priorite), 0);
    }
    @Test
    public void testSousFilePrio() throws FileVideException {
        System.out.println("sousFilePrio");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        TachePrio element5 = new TachePrio(7, "e5");
        instance.enfiler(element5);
        
        TachePrio element7 = new TachePrio(5, "e7");
        instance.enfiler(element7);
        
        TachePrio element8 = new TachePrio(3, "e8");
        instance.enfiler(element8);
        assertEquals(instance.toString(),
         "tete [ e2(7), e3(7), e5(7), e4(5), e7(5), e1(3), e8(3) ] fin");
        
        assertEquals("tete [  ] fin"
                , instance.sousFilePrio(0).toString());
        assertEquals(instance.taille(0), 0);
        assertEquals(instance.premier(0), null);

        assertEquals("tete [ e1(3), e8(3) ] fin"
                , instance.sousFilePrio(3).toString());
        assertEquals(instance.taille(3), 2);
        assertEquals(instance.premier(3), element1);

        assertEquals("tete [ e2(7), e3(7), e5(7) ] fin"
                , instance.sousFilePrio(7).toString());
        assertEquals(instance.taille(7), 3);
        assertEquals(instance.premier(7), element2);
        
        assertEquals("tete [ e4(5), e7(5) ] fin"
                , instance.sousFilePrio(5).toString());

        
    }

    /**
     * Test of contient method, of class FilePrioChainee.
     */
    @Test
    public void testContientFilVid() {
        System.out.println("contient - file vide");
        TachePrio elem = null;
        
        boolean expResult = false;
        boolean result = instance.contient(elem);
        assertEquals(expResult, result);
        
        TachePrio elem2 = new TachePrio(0);
        assertFalse(instance.contient(elem2));
    }
    @Test
    public void testContient() {
        System.out.println("contient - file vide");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        assertFalse(instance.estVide());
        
        assertTrue(instance.contient(element1));
        assertTrue(instance.contient(element2));
        assertTrue(instance.contient(element3));
        assertTrue(instance.contient(element4));
        
        assertFalse(instance.contient(new TachePrio(0)));
        
        assertFalse(instance.contient(null));
    }

    /**
     * Test of normaliser method, of class FilePrioChainee.
     */
    @Test
    public void testNormaliser() throws FileVideException {
        System.out.println("normaliser");
        
        
        instance.normaliser();
        assertEquals(instance.toString(), "tete [  ] fin");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        assertEquals(instance.toString(), 
                "tete [ e2(7), e3(7), e4(5), e1(3) ] fin");
        assertEquals(instance.prioriteMax(), 7);
        assertEquals(instance.prioriteMin(), 3);
        
        instance.normaliser();
        assertEquals(instance.toString(), 
                "tete [ e2(3), e3(3), e4(2), e1(1) ] fin");


        
    }

    /**
     * Test of eliminerDoublons method, of class FilePrioChainee.
     */
    @Test
    public void testEliminerDoublons() {
        System.out.println("eliminerDoublons");
        
        instance.eliminerDoublons();
        assertEquals(instance.toString(), "tete [  ] fin");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        TachePrio element5 = new TachePrio(7, "e5");
        instance.enfiler(element5);
        
        TachePrio element7 = new TachePrio(5, "e7");
        instance.enfiler(element7);
        
        TachePrio element8 = new TachePrio(3, "e8");
        instance.enfiler(element8);
        
        instance.eliminerDoublons();
        assertEquals(instance.toString(), 
                "tete [ e2(7), e3(7), e5(7), e4(5), e7(5), e1(3), e8(3) ] fin");
 
        TachePrio element8_1 = new TachePrio(3, "e8");
        instance.enfiler(element8_1);  
        TachePrio element8_2 = new TachePrio(3, "e8");
        instance.enfiler(element8_2); 
        TachePrio element4_1 = new TachePrio(5, "e4");
        instance.enfiler(element4_1);  

        
        assertEquals(instance.toString(), 
                "tete [ e2(7), e3(7), e5(7), e4(5), e7(5), e4(5),"
                        + " e1(3), e8(3), e8(3), e8(3) ] fin");
        
        instance.eliminerDoublons();
        assertEquals(instance.toString(), 
                "tete [ e2(7), e3(7), e5(7), e4(5), e7(5), e1(3), e8(3) ] fin");


    }

    /**
     * Test of prioriteMax method, of class FilePrioChainee.
     */
//    @Test
//    public void testPrioriteMax() throws Exception {
//        System.out.println("prioriteMax");
//        
//        int expResult = 0;
//        int result = instance.prioriteMax();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of prioriteMin method, of class FilePrioChainee.
//     */
//    @Test
//    public void testPrioriteMin() throws Exception {
//        System.out.println("prioriteMin");
//        
//        int expResult = 0;
//        int result = instance.prioriteMin();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }

    /**
     * Test of copie method, of class FilePrioChainee.
     */
    @Test
    public void testCopieFilVid() {
        System.out.println("copie - file vide");
        
        IFilePrio result = instance.copie();
        assertEquals("tete [  ] fin", result.toString());
    }
    @Test
    public void testCopie() {
        System.out.println("copie");
        
        TachePrio element1 = new TachePrio(3, "e1");
        instance.enfiler(element1);
        
        TachePrio element2 = new TachePrio(7, "e2");
        instance.enfiler(element2);
        
        TachePrio element3 = new TachePrio(7, "e3");
        instance.enfiler(element3);
        
        TachePrio element4 = new TachePrio(5, "e4");
        instance.enfiler(element4);
        
        IFilePrio result = instance.copie();
        assertEquals("tete [ e2(7), e3(7), e4(5), e1(3) ] fin", result.toString());
        assertEquals("tete [ e2(7), e3(7), e4(5), e1(3) ] fin", instance.toString());
        assertNotEquals(instance, result);
        assertEquals(instance.toString(), result.toString());
    }

    /**
//     * Test of toString method, of class FilePrio.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
    
}
