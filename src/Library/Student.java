package Library;

public class Student extends Client {
    private String facultate;
    private int anStudiu;

    public String getFacultate() { return this.facultate; }
    public int getAnStudiu() { return this.anStudiu; }

    public void setFacultate(String facultate) { this.facultate = facultate; }
    public void setAnStudiu(int anStudiu) { this.anStudiu = anStudiu; }

    @Override
    public String toString() {
        String s = "Nume: " + this.getNume() + "\n";
        s+="Cod: " + this.getCod() + "\n";
        s+="Numar carti imprumutate: " + this.getNrCartiImprumutate() + "\n";
        s+="Data retur: " + this.getDataRetur() + "\n";
        s+="Facultate: " + facultate + "\n";
        s+="An studiu: " + anStudiu;
        return s;
    }
}
