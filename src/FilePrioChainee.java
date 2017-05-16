
import java.util.ArrayList;

/*
 * Classe qui implémente l'interface IFilePrio
*
 * @author Leopold, Quenum
 * code permanent : QUEL16107105
 * @version 29 mars 2017
 * @param <T> le type des elements dans cette file de priorite. 
 */

public class FilePrioChainee <T extends ITachePrio> implements IFilePrio<T>{

    //ATTRIBUTS D'INSTANCE
    Maillon<T> elements;
    int taille = 0;

    /**
    * Construit un efile de priorite vide
    * 
    */
    public FilePrioChainee() {
        elements = null;
    }
    
    /**
    * Enfile l'element (non null) dans cette file de priorite.
    * 
    * @param element l'element a enfiler dans cette file de priorite.
    * @throws NullPointerException si l'element donne en parametre est null.
    */
    @Override
    public void enfiler(T element) {
        Maillon<T> prec;
        
        if (element == null) {
            throw new NullPointerException();
        }
        
        if (estVide()) {
            //Cree un premier maillon
            elements = new Maillon(element);
        } else {
            //La priorite de l'element est plus grande que celle du premier 
            //element de la file, on insere un nouveau maillon au debut
            if (element.getPriorite() > elements.getInfo().getPriorite()) {
                elements = new Maillon(element, elements);
            } else {
                prec = elements;
                //Cette boucle trouve le maillon qui precedera le nouveau 
                //maillon contenant l'element a inserer
                while (prec.getSuivant() != null && element.getPriorite() 
                        <= prec.getSuivant().getInfo().getPriorite()) {                    
                    prec = prec.getSuivant();
                }
                if (prec.getSuivant() != null) {
                    //Insere un nouveau entre deux autres
                    prec.setSuivant(new Maillon(element, prec.getSuivant()));
                } else {
                    //Insere un nouveau maillon a la fin de la file
                    prec.setSuivant(new Maillon(element));
                }
            }
        }
        taille++;
    }

    /**
    * Defile l'element le plus prioritaire (premier arrivee de la plus grande 
    * priorite) de cette file de priorite.
    * 
    * @return l'element defile.
    * @throws FileVideException si cette file de priorite est vide avant l'appel 
    *         de cette methode.
    */
    @Override
    public T defiler() throws FileVideException {
        T elemDefile;
        
        lanceExcepSiFileVide();
        
        elemDefile = premier();
        elements = elements.getSuivant();//Supprime l'element en tete de file
        taille--;
        return elemDefile;
    }

    /**
    * Defile l'element le plus prioritaire de la priorite donnee en parametre. 
    * Si aucun element de la priorite donnee n'existe dans cette file de priorite, 
    * la methode retourne null et cette file de priorite n'est pas modifiee.
    * 
    * @param priorite la priorite de l'element a defiler.
    * @return l'element defile ou null si aucun element de la priorite donnee
    *         en parametre n'existe dans cette file de priorite.
    * @throws FileVideException si cette file de priorite est vide avant qu'on 
    *         ne tente de defiler l'element. 
    */
    @Override
    public T defiler(int priorite) throws FileVideException {
        T elemPrioDefile = premier(priorite);
        Maillon prec;
        
        if (elemPrioDefile != null) {
            if (premier().getPriorite() == priorite) {
                //Si le prenier element de la file est de la priorite donnee
                //on le defile
                defiler();
            } else {
                //On cherche le maillon precedant le maillon contenant 
                //l'element a supprimer
                prec = elements;
                while (prec.getSuivant() != null && prec.getSuivant().getInfo()
                        .getPriorite() != priorite) {
                    prec = prec.getSuivant();
                } 
                //S'il est trouve, il est supprime, sinon la file est inchangee
                if (prec.getSuivant() != null) {
                    prec.setSuivant(prec.getSuivant().getSuivant());
                    taille--;
                }
            }
        }
        return elemPrioDefile;
    }

    /**
    * Defile tous les elements de la priorite donnee. Si aucun element de cette
    * priorite n'existe dans cette file de priorite, celle-ci n'est pas modifiee.
    * La methode retourne une file de priorite contenant tous les elements 
    * defiles, dans le meme ordre que lorsqu'ils se trouvaient dans cette file
    * de priorite. Si aucun element n'est defile, la file retournee est vide.
    * 
    * @param priorite la priorite des elements a defiler de cette file de 
    *                 priorite.
    * @return Une file de priorite contenant tous les elements defiles, dans 
    *         le meme ordre.
    * @throws FileVideException si cette file de priorite est vide avant
    *         l'appel de cette methode.
    */
    @Override
    public IFilePrio<T> defilerTous(int priorite) throws FileVideException {
        IFilePrio<T> elementsDefiles = new FilePrioChainee<>();
        
        lanceExcepSiFileVide();
        
        while (prioriteExiste(priorite)) {            
            elementsDefiles.enfiler(defiler(priorite));
        }
        return elementsDefiles;
    }

    /**
    * Verifie si cette file de priorite contient au moins un element ayant la 
    * priorite donnee en parametre.
    * 
    * @param priorite la priorite dont on veut verifier l'existence dans cette
    *                 file de priorite.
    * @return true si au moins un element ayant la priorite donnee en parametre
    *         existe dans cette file de priorite, false sinon.
    */
    @Override
    public boolean prioriteExiste(int priorite) {
        return !sousFilePrio(priorite).estVide();
    }
    
    /**
    * Verifie si cette file de priorite contient des elements ou non.
    * @return true si cette file de priorite ne contient aucun element, false
    *         sinon.
    */
    @Override
    public boolean estVide() {
        return taille == 0;
    }
    
    /**
    * Permet d'obtenir le nombre d'elements contenus dans cette file de 
    * priorite.
    * 
    * @return le nombre d'elements dans cette file de priorite.
    */
    @Override
    public int taille() {
        return taille;
    }

    /**
    * Permet d'obtenir le nombre d'elements ayant la priorite donnee en parametre
    * qui sont contenus dans cette file de priorite.
    * @param priorite la priorite des elements dont on veut le nombre.
    * @return le nombre d'elements ayant la priorite donnee en parametre qui sont 
    *         contenus dans cette file de priorite.
    */
    @Override
    public int taille(int priorite) {
        return sousFilePrio(priorite).taille();
    }
    
    /**
    * Permet de consulter l'element en tete de cette file de priorite, sans 
    * modifier celle-ci. 
    * 
    * @return l'element en tete de cette file de priorite.
    * @throws FileVideException si cette file de priorite est vide avant l'appel
    *         de cette methode.
    */
    @Override
    public T premier() throws FileVideException {
        
        lanceExcepSiFileVide();
        return elements.getInfo();
    }

    /**
    * Permet de consulter l'element le plus prioritaire de la priorite donnee
    * en parametre, sans modifier cette file de priorite. Si aucun element 
    * de la priorite donnee existe dans cette file de priorite, la methode 
    * retourne null.
    * @param priorite la priorite de l'element le plus prioritaire que l'on veut
    *                 consulter.
    * @return l'element le plus prioritaire de la priorite donnee en parametre.
    * @throws FileVideException si cette file de priorite est vide avant l'appel
    *         de cette methode.
    */
    @Override
    public T premier(int priorite) throws FileVideException {
        T element = null;
        
        lanceExcepSiFileVide();
        
        if (prioriteExiste(priorite)) {
            element = sousFilePrio(priorite).premier();
        }
        return element;
    }

    /**
    * Retire tous les elements de cette file de priorite. 
    */
    @Override
    public void vider() {
        elements = null;
        taille = 0;
    }

    /**
    * Retourne une file de priorite contenant tous les elements ayant la priorite 
    * donnee en parametre se trouvant dans cette file de priorite. Les elements
    * dans la file de priorite a retourner doivent conserver l'ordre dans lequel
    * ils apparaissent dans cette file de priorite. Apres l'appel de cette methode,
    * cette file de priorite ne doit pas avoir ete modifiee. De plus, si aucun 
    * element ayant la priorite donnee ne se trouve dans cette file de priorite,
    * la methode retourne une file de priorite vide.
    * 
    * @param priorite la priorite des elements de la file de priorite a retourner.
    * @return une file de priorite contenant tous les elements ayant la priorite 
    *         donnee en parametre se trouvant dans cette file de priorite.
    */
    @Override
    public IFilePrio<T> sousFilePrio(int priorite) {
        IFilePrio<T> ssFilePrio = new FilePrioChainee<>();
        IFilePrio<T> copieFilePrio = copie();
        T element;
         
        while (!copieFilePrio.estVide()) {            
           try {
               element = copieFilePrio.defiler();
               if (element.getPriorite() == priorite) {
                   ssFilePrio.enfiler(element);
               }
           } catch (FileVideException ex) {
           }
        }   
        return ssFilePrio;
    }

    /**
    * Teste si cette file de priorite contient au moins un element identique a 
    * celui donne en parametre.
    * 
    * @param elem l'element dont on teste l'existence.
    * @return true s'il existe au moins un element dans cette file de priorite
    *         qui est identique a celui donne en parametre, false sinon.
    */
    @Override
    public boolean contient(T elem) {
        boolean contientElem = false;
        Maillon<T> maillon;
        
        if (elem != null) {
            maillon = elements;
            while (maillon != null && !contientElem) {
                if(elem.equals(maillon.getInfo())) {
                    contientElem = true;
                }
                maillon = maillon.getSuivant();
            }
        }
        return contientElem;
    }

    
    /**
    * Normalise les priorites des elements de cette file de priorite
    * en modifiant celles-ci pour que la plus petite priorite devienne 1, que 
    * la deuxieme plus petite priorite devienne 2, et ainsi de suite, jusqu'a la
    * plus grande priorite (qui correspondra au nombre de priorites differentes 
    * dans cette file de priorite). 
    * Si la file de priorite etait deja sous sa forme "normale", elle demeure 
    * inchangee.
    */
    @Override
    public void normaliser() {
        int index;
        Maillon<T> maillon = elements;
        ArrayList<Integer> listePriorites = null;
        try {
            listePriorites = listePrioritesFilePrio();
        } catch (FileVideException ex) {
        }
        
        while (maillon != null) {         
            index = listePriorites.indexOf(maillon.getInfo().getPriorite());
            //Les prirorites normalisees vont de 1 a nombre d'elements de liste
            //il faut augmenter l'indice dans la liste de 1 pour obtenir la 
            //priorite normalisee
            maillon.getInfo().setPriorite(index + 1);
            maillon = maillon.getSuivant();
        }
    }

    /**
    * Elimine les doublons de cette file de priorite. Si une sousFile de priorite
    * contient, par exemple, 3 elements identiques, la methode elimine les deux
    * moins prioritaire (en terme du moment d'entree dans la file).
    * 
    * Si la file de priorite ne contenait aucun doublon(s), elle demeure
    * inchangee.
    * 
    */
    @Override
    public void eliminerDoublons() {
        IFilePrio<T> nouvFilePrio = new FilePrioChainee<>();
        T elem;
        
        //On defile la file de priorite et on enfile l'element dans une 
        //nourvelle file que si cette file ne contient pas l'element défile
        while (!estVide()) {                
            try {
                elem = defiler();
                if (!nouvFilePrio.contient(elem)) {
                    nouvFilePrio.enfiler(elem);
                }
            } catch (FileVideException ex) {
            }
        }
            
        while (!nouvFilePrio.estVide()) {                
            try {
                enfiler(nouvFilePrio.defiler());
            } catch (FileVideException ex) {
            }
        }
    }

    /**
    * Permet d'obtenir la priorite la plus grande parmi les priorites de tous 
    * les elements de cette file de priorite.
    * @return la priorite maximum dans cette file de priorite.
    * @throws FileVideException si cette file de priorite est vide avant l'appel 
    *         de cette methode.
    */
    @Override
    public int prioriteMax() throws FileVideException {
        int maxPrio;
        Maillon maillon;
        
        lanceExcepSiFileVide();
        
        maillon = elements;
        maxPrio = elements.getInfo().getPriorite();
        
        while (maillon.getSuivant() != null) {   
            if (maillon.getSuivant().getInfo().getPriorite() > maxPrio) {
                maxPrio = maillon.getSuivant().getInfo().getPriorite();
            }
            maillon = maillon.getSuivant();
        }
        return maxPrio;
    }

    /**
    * Permet d'obtenir la priorite la plus petite parmi les priorites de tous 
    * les elements de cette file de priorite.
    * @return la priorite minimum dans cette file de priorite.
    * @throws FileVideException si cette file de priorite est vide avant l'appel 
    *         de cette methode.
    */
    @Override
    public int prioriteMin() throws FileVideException {
        int minPrio;
        Maillon maillon;
        
        lanceExcepSiFileVide();
        
        maillon = elements;
        minPrio = elements.getInfo().getPriorite();
        
        while (maillon.getSuivant() != null ) {   
            if (maillon.getSuivant().getInfo().getPriorite() < minPrio) {
                minPrio = maillon.getSuivant().getInfo().getPriorite();
            }
            maillon = maillon.getSuivant();
        }
        return minPrio;
    }

    /**
    * Retourne une copie de cette file de priorite. 
    *
    * @return une copie de cette file de priorite.
    */
    @Override
    public IFilePrio<T> copie() {
        IFilePrio<T> copieFilePrio = new FilePrioChainee<T>();
        Maillon maillon = elements;;
        
        while (maillon != null) {
            copieFilePrio.enfiler((T) maillon.getInfo());
            maillon = maillon.getSuivant();
        }
                
        return copieFilePrio;
    }

    /**
    * Construit une representation sous forme de chaine de caracteres de cette
    * file de priorite.
    * @return une representation sous forme de chaine de caracteres de cette
    *         file de priorite.
    */
   @Override
   public String toString() {
           String s = "tete [ ";
           Maillon<T> tmp = elements;
           if (tmp == null) {
                   s = s + " ] fin";
           } else {
                   while (tmp != null) {
                           s = s + tmp.getInfo() + ", ";
                           tmp = tmp.getSuivant();
                   }
                   s = s.substring(0, s.length() -2) + " ] fin";
           } 
           return s;
    }
    
    /**
    * Cette methode permet de lancer une exception si la file de priorite est 
    * vide
    */
    private void lanceExcepSiFileVide() throws FileVideException {
       if (estVide()) {
            throw new FileVideException();
        }
   }
   
   /**
    * Cette methode permet d'obtenir les valeurs des priorites, par ordre 
    * croissant  et sans repetition, de la file de  priorite dans une liste
    * 
    * @return une liste d'entiers avec les differentes priorites de la file de
    * priorite
    */
    private ArrayList<Integer> listePrioritesFilePrio() throws FileVideException {
       int valPriorites = prioriteMin();
       ArrayList<Integer> listePriorites = new ArrayList<>();
       
       //On parcours les entiers entre prioriteMin() et prioriteMax(), puis on 
       //ajoute dans la liste les valeurs des priorirtes existantes de la file
       while (valPriorites <= prioriteMax()) {           
           if (prioriteExiste(valPriorites)) {
               listePriorites.add(valPriorites);
           }
           valPriorites++;
       }
       return listePriorites;
   }
   
}
