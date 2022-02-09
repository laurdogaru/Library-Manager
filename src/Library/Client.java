package Library;

public class Client implements Comparable<Client> {
    private int cod;
    private String nume;
    private int nrCartiImprumutate;
    private String dataRetur;
    private static int cod_aux;//ajuta la atribuirea codului la creare

    public int getCod() { return this.cod; }
    public String getNume() { return this.nume; }
    public int getNrCartiImprumutate() { return this.nrCartiImprumutate; }
    public String getDataRetur() { return this.dataRetur; }

    public void setCod() { this.cod = this.cod_aux++; }
    public void setNume(String nume) { this.nume = nume; }
    public void setNrCartiImprumutate(int nrCartiImprumutate) { this.nrCartiImprumutate = nrCartiImprumutate; }
    public void setDataRetur(String dataRetur) { this.dataRetur = dataRetur; }


    @Override
    public int compareTo(Client c) {
        return this.nume.compareTo(c.nume);
    }

    @Override
    public boolean equals(Object o) {
        if( o == null ) return false;

        if( !(o instanceof Client) ) return false;

        Client c = (Client)o;
        if( (this.nume.compareTo(c.getNume()) == 0) )
            return true;
        else
            return false;
    }
}

