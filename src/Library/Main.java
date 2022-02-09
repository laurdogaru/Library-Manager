package Library;

import java.util.*;
public class Main {
    public static void main(String [] args) {
        NrCartiThread task = new NrCartiThread();
        Thread t = new Thread(task);
        t.start();

        Biblioteca b = Biblioteca.getInstance();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Comanda: ");
            String comanda = scanner.nextLine();
            String[] cuv = comanda.split(" ");
            switch( cuv[0] ) {
                case "exit" : System.exit(0);
                case "adaugaStudent" : Student s = new Student();
                    s.setNume(cuv[1]);
                    s.setFacultate(cuv[2]);
                    s.setAnStudiu(Integer.parseInt(cuv[3]));
                    s.setCod();
                    b.<Student>adaugaClient(s);
                    break;
                case "adaugaProfesor" : Profesor p = new Profesor();
                    p.setNume(cuv[1]);
                    p.setMaterie(cuv[2]);
                    p.setCod();
                    b.<Profesor>adaugaClient(p);
                    break;
                case "afiseazaClienti" : b.afiseazaClienti();
                    break;
                case "afiseazaStudenti" : b.afiseazaStudenti();
                    break;
                case "adaugaCarte" : Carte c = new Carte();
                    c.setTitlu(cuv[1]);
                    c.setAutor(cuv[2]);
                    c.setGen(cuv[3]);
                    c.setNrPagini(Integer.parseInt(cuv[4]));
                    c.setDisponibil(true);
                    c.setCod();
                    b.adaugaCarte(c);
                    break;
                case "afiseazaCarti" : b.afiseazaCarti();
                    break;
                case "afiseazaCartiDisponibile" : b.afiseazaCartiDisponibile();
                    break;
                case "cautaCarte" : b.cautaCarte(cuv[1]);
                    break;
                case "filtreazaCartiDupaGen" : b.filtreazaCartiDupaGen(cuv[1]);
                    break;
                case "sorteazaCarti" : b.sorteazaCarti();
                    break;
                case "sorteazaClienti" : b.sorteazaClienti();
                    break;
                case "celMaiFidelCititor" : b.celMaiFidelCitior();
                    break;
                case "imprumutaCarte" : b.imprumutaCarte(Integer.parseInt(cuv[1]));
                    break;
                case "returneazaCarte" : b.returneazaCarte(Integer.parseInt(cuv[1]));
                    break;
                case "stergeCarte" : b.stergeCarte(cuv[1]);
                    break;
                case "stergeClient" : b.stergeClient(cuv[1]);
                    break;
                case "arePenalitati" : b.arePenalitati(cuv[1]);
                    break;
                case "login" : b.login(cuv[1]);
                    break;
                case "currentUser" : b.currentUser();
                    break;
                case "verificareIstoricCarte" : b.verificareIstoricCarte(cuv[1]);
            }
        }
    }
}
