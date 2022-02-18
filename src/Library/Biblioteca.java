package Library;

import java.util.*;

import Exceptions.*;

public class Biblioteca {
    private ArrayList<Client> clienti;
    private ArrayList<Carte> carti;
    private Client currentClient;

    private Biblioteca() {
        clienti = new ArrayList<>();
        carti = new ArrayList<>();
    }

    private static Biblioteca SINGLETON;
    public static Biblioteca getInstance() {
        if(SINGLETON == null) {
            SINGLETON = new Biblioteca();
        }
        return SINGLETON;
    }


    public ArrayList<Client> getClienti() {
        return clienti;
    }

    public ArrayList<Carte> getCarti() {
        return carti;
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public <T extends Client>void adaugaClient(T client) {
        if(clienti.contains(client)) {
            throw new NumeExistentException();
        }
        clienti.add(client);
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
        carti.stream().filter(i -> i.isDisponibil()).forEach(i -> System.out.println(i.getTitlu()));
    }

    public void cautaCarte(String titlu) {
        Carte carte = carti.stream().filter(i -> i.getTitlu().equals(titlu)).findAny().orElse(null);
        if(carte != null) {
            System.out.println(carte);
            return;
        }
        System.out.println("Cartea nu este inregistrata");
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
        set.addAll(clienti);
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
        if(fidel != null) {
            System.out.println(fidel);
        }
    }

    public void imprumutaCarte(int cod) {
        Carte carte = carti.stream().filter(i -> i.getCod() == cod).findAny().orElse(null);
        try {
            /*exceptia este aruncata in cazurile:cartea nu exista,cartea este imprumutata, sau clientul
             are deja o carte imprumutata*/
            if( carte == null || !carte.isDisponibil() || currentClient.getDataRetur() != null) {
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
        Carte carte = carti.stream().filter(i -> i.getCod() == cod).findAny().orElse(null);
        int a = carte.getProprietari().size();
        //nu se poate returna o carte atunci cand lista sa de proprietari este goala sau ultimul
        //proprietar nu este clientul curent
        if(a > 0 && carte.getProprietari().get(a-1).equals(currentClient)) {
            carte.setDisponibil(true);
            currentClient.setDataRetur(null);
        } else {
            System.out.println("Nu detineti aceasta carte");
        }
    }

    public void stergeCarte(String titlu) {
        Carte carte = carti.stream().filter(i -> i.getTitlu().equals(titlu)).findAny().orElse(null);
        carti.remove(carte);
    }

    public void stergeClient(String nume) {
        Client client = clienti.stream().filter(i -> i.getNume().equals(nume)).findAny().orElse(null);
        clienti.remove(client);
    }

    public void arePenalitati(String nume) {
        Client client =  clienti.stream().filter(i -> i.getNume().equals(nume)).findAny().orElse(null);
        if(client == null) {
            System.out.println("Nu exista un client inregistrat cu acest nume.");
            return;
        }
        if(client.getDataRetur() == null) {
            System.out.println("Nu are penalitati");
            return;
        }

        Calendar prezent = Calendar.getInstance();
        Calendar returnare = Calendar.getInstance();

        String[] dataString = client.getDataRetur().split("/");

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
        Client client =  clienti.stream().filter(i -> i.getNume().equals(nume)).findAny().orElse(null);
        if(client == null) {
            System.out.println("Nu exista un client inregistrat cu acest nume.");
            return;
        }
        currentClient = client;
    }
    //afiseaza clientul curent
    public void currentUser() {
        if(currentClient == null) {
            System.out.println("Niciun utilizator logat");
        } else {
            System.out.println("Utilizatorul curent: " + currentClient.getNume());
        }
    }

    public void verificareIstoricCarte(String titlu) {
        Carte carte = carti.stream().filter(i -> i.getTitlu().equals(titlu)).findAny().orElse(null);
        if( carte != null ) {
            System.out.println("Cartea a avut " + carte.getProprietari().size() + " proprietari:");
            for( Client client : carte.getProprietari() ) {
                System.out.println(client.getNume());
            }
        } else {
            System.out.println("Cartea nu este inregistrata");
        }
    }
}
