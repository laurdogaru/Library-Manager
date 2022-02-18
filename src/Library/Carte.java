package Library;

import java.util.*;

public class Carte implements Comparable<Carte> {
    private int cod;
    private String titlu;
    private String autor;
    private String gen;
    private int nrPagini;
    private boolean disponibil;
    private static int cod_aux; //ajuta la atribuirea codului la creare
    private ArrayList<Client> proprietari = new ArrayList<>();//retine proprietarii cartii de-a lungul timpului

    public int getCod() { return this.cod; }
    public String getTitlu() { return this.titlu; }
    public String getAutor() { return this.autor; }
    public String getGen() { return this.gen; }
    public  int getNrPagini() { return this.nrPagini; }
    public boolean isDisponibil() { return this.disponibil; }
    public ArrayList<Client> getProprietari() { return this.proprietari; }

    public void setCod() { this.cod = this.cod_aux++; }
    public void setTitlu(String titlu) { this.titlu = titlu; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setGen(String gen) { this.gen = gen; }
    public void setNrPagini(int nrPagini) { this.nrPagini = nrPagini; }
    public void setDisponibil(boolean disponibil) { this.disponibil = disponibil; }

    //pentru implementarea TreeSet-ului
    @Override
    public int compareTo(Carte c) {
        return this.nrPagini - c.nrPagini;
    }

    @Override
    public String toString() {
        String s = "Cod: " + cod + "\n";
        s+="Titlu: " + titlu + "\n";
        s+="Autor: " + autor + "\n";
        s+="Gen: " + gen + "\n";
        s+="Numar pagini: " + nrPagini + "\n";
        if(disponibil) {
            s+="Este disponibila";
        } else {
            s+="Nu este disponibila";
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(!(o instanceof Carte)) {
            return false;
        }
        Carte carte = (Carte) o;
        return titlu.equals(carte.titlu);
    }

    @Override
    public int hashCode() {
        return titlu.hashCode();
    }

}
