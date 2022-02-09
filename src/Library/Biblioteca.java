package Library;

import java.util.*;
import Exceptions.*;

public class Biblioteca {
    private Biblioteca() {

    }

    private static Biblioteca SINGLETON;
    public static Biblioteca getInstance() {
        if(SINGLETON == null) {
            SINGLETON = new Biblioteca();
        }
        return SINGLETON;
    }

    private ArrayList<Client> clienti = new ArrayList<>();
    private ArrayList<Carte> carti = new ArrayList<>();
    private Client currentClient;

    public ArrayList<Client> getClienti() {
        return clienti;
    }

    public ArrayList<Carte> getCarti() {
        return carti;
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public <T>void adaugaClient(T client) {
        for(Client c : clienti) {
            if( c.equals(client) ) {
                throw new NumeExistentException();
            }
        }
        Client cl = (Client) client;
        clienti.add(cl);
    }

    public void afiseazaClienti() {
        for(Client c : clienti) {
            System.out.println(c.getNume());
        }
    }

    public void afiseazaStudenti() {
        for(Client c : clienti) {
            if( c instanceof Student ) {
                System.out.println(c.getNume());
            }
        }
    }

    public void adaugaCarte(Carte c) {
        carti.add(c);
    }

    public void afiseazaCarti() {
        for(Carte c : carti) {
            System.out.println(c.getTitlu());
        }
    }

    public void afiseazaCartiDisponibile() {
        for(Carte c : carti) {
            if( c.isDisponibil() == true ) {
                System.out.println(c.getTitlu());
            }
        }
    }

    public void cautaCarte(String titlu) {
        boolean aux = true;
        for(Carte c : carti) {
            if(titlu.compareTo(c.getTitlu()) == 0) {
                aux = false;
                System.out.println(c.toString());
                break;//cand este gasita, se opreste cautarea
            }
        }
        if(aux == true) {
            System.out.println("Cartea nu este inregistrata");
        }

    }

    public void filtreazaCartiDupaGen(String gen) {
        carti.stream().filter( i -> i.getGen().compareToIgnoreCase(gen) == 0).forEach(i -> System.out.println(i.getTitlu()));
    }
    /*cartile sau clientii sunt introdusi intr-un TreeSet, care este dupa afisat*/
    public void sorteazaCarti() {
        TreeSet<Carte> set = new TreeSet<>();
        carti.forEach(i -> set.add(i));
        set.forEach(i -> System.out.println(i.getTitlu()));
    }

    public void sorteazaClienti() {
        TreeSet<Client> set = new TreeSet<>();
        clienti.forEach(i -> set.add(i));
        set.forEach(i -> System.out.println(i.getNume()));
    }

    public void celMaiFidelCitior() {
        int aux = -1;
        Client fidel = null;
        for(Client c : clienti) {
            if(c.getNrCartiImprumutate() > aux) {
                aux = c.getNrCartiImprumutate();
                fidel = c;
            }
        }
        if( fidel instanceof Student) {
            Student s = (Student)fidel;
            System.out.println(s.toString());
        }
        else {
            Profesor p = (Profesor)fidel;
            System.out.println(p.toString());
        }
    }

    public void imprumutaCarte(int cod) {
        Carte carte = null;
        for(Carte c : carti) {
            if( c.getCod() == cod ) {
                carte = c;
                break;
            }
        }
        try {
            /*exceptia este aruncata in cazurile:cartea nu exista,cartea este imprumutata, sau clientul
             are deja o carte imprumutata*/
            if( carte == null || carte.isDisponibil() == false || currentClient.getDataRetur()!=null) {
                throw new CarteIndisponibilaException();
            }
            carte.setDisponibil(false);

            carte.getProprietari().add(currentClient);//clientul este adaugat in lista de proprietari

            int a = currentClient.getNrCartiImprumutate();
            currentClient.setNrCartiImprumutate(a+1);//se incrementeaza numarul de carti imprumutate al clientului

            Calendar prezent = Calendar.getInstance();
            prezent.add((Calendar.MONTH),1);
            //data este setata la o luna dupa imprumut
            int data = prezent.get(Calendar.DATE);
            int luna = prezent.get(Calendar.MONTH) + 1;
            int an = prezent.get(Calendar.YEAR);
            currentClient.setDataRetur(data + "/" + luna + "/" + an);
            //data este memorata sub ca un string de formam "zi/luna/an"
        } catch(CarteIndisponibilaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void returneazaCarte(int cod) {
        Carte carte = null;
        for(Carte c : carti) {
            if( c.getCod() == cod) {
                carte = c;
                break;
            }
        }
        int a = carte.getProprietari().size();
        //nu se poate returna o carte atunci cand lista sa de proprietari este goala sau ultimul
        //proprietar nu este clientul curent
        if( a > 0 && carte.getProprietari().get(a-1).equals(currentClient)) {
            carte.setDisponibil(true);
            currentClient.setDataRetur(null);
        }
        else {
            System.out.println("Nu detineti aceasta carte");
        }
    }

    public void stergeCarte(String titlu) {
        for(int i = 0 ; i < carti.size() ; i++) {
            if( carti.get(i).getTitlu().compareTo(titlu) == 0 ) {
                carti.remove(i);
                break;
            }
        }
    }

    public void stergeClient(String nume) {
        for(int i = 0 ; i < clienti.size() ; i++) {
            if( clienti.get(i).getNume().compareTo(nume) == 0 ) {
                clienti.remove(i);
                break;
            }
        }
    }

    public void arePenalitati(String nume) {
        Client c = null;
        for(int i = 0; i < clienti.size() ; i++) {
            if( clienti.get(i).getNume().compareTo(nume) == 0 ) {
                c = clienti.get(i);
                break;
            }
        }
        if(c.getDataRetur() == null) {
            System.out.println("Nu are penalitati");
            return;
        }

        Calendar prezent = Calendar.getInstance();
        Calendar returnare = Calendar.getInstance();

        String[] dataString = c.getDataRetur().split("/");

        returnare.set(Calendar.DATE,Integer.parseInt(dataString[0]));
        returnare.set(Calendar.MONTH,Integer.parseInt(dataString[1]));
        returnare.set(Calendar.YEAR,Integer.parseInt(dataString[2]));
        //clientul are penalitati daca data din prezent este dupa data de retur
        if( prezent.after(returnare) ) {
            System.out.println("Are penalitati");
        }
        else {
            System.out.println("Nu are penalitati");
        }
    }
    //metoda pentru a seta utilizatorul curent
    public void login(String nume) {
        for(Client c : clienti) {
            if(nume.compareTo(c.getNume()) == 0) {
                currentClient = c;
                break;
            }
        }
    }
    //afiseaza clientul curent
    public void currentUser() {
        if( currentClient == null ) {
            System.out.println("Niciun utilizator logat");
        }
        else {
            System.out.println("Utilizatorul curent: " + currentClient.getNume());
        }
    }

    public void verificareIstoricCarte(String titlu) {
        Carte carte = null;
        for( Carte c : carti ) {
            if( c.getTitlu().compareTo(titlu) == 0 ) {
                carte = c;
                break;
            }
        }
        if( carte != null ) {
            System.out.println("Cartea a avut " + carte.getProprietari().size() + " proprietari:");
            for( Client client : carte.getProprietari() ) {
                System.out.println(client.getNume());
            }
        }
        else {
            System.out.println("Cartea nu este inregistrata");
        }
    }
}
