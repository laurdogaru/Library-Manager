package Library;

public class NrCartiThread implements Runnable {
    Biblioteca b = Biblioteca.getInstance();
    @Override
    public void run() {
        try {
            while(true) {
                synchronized( b.getCarti() ) {
                    System.out.println("Nr. cartilor din biblioteca: " + b.getCarti().size() );
                    Thread.sleep(50000);
                }
            }
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}

