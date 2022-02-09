package Library;

public class Profesor extends Client {
    private String materie;

    public String getMaterie() { return this.materie; }

    public void setMaterie(String materie) { this.materie = materie; }

    @Override
    public String toString() {
        String s = "Nume: " + this.getNume() + "\n";
        s+="Cod: " + this.getCod() + "\n";
        s+="Numar carti imprumutate: " + this.getNrCartiImprumutate() + "\n";
        s+="Data retur: " + this.getDataRetur() + "\n";
        s+="Materie predata: " + materie;
        return s;
    }
}
