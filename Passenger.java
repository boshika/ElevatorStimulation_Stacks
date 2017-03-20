/**
 * The Passenger class accepts paasenger data, and
 * returns them.
 *
 * @author  Boshika Tara
 * @version 1.0
 * @since   2016-10-01
 */
public class Passenger {
    
    String name;
    int enteryFloor;
    int exitFloor;
    int temporaryExit;


    /**
     * Constructor declaration for class Passenger
     */
    public Passenger(String name, int entryFloor, int exitFloor) {
        this.name = name;
        this.enteryFloor =entryFloor;
        this.exitFloor = exitFloor;
        this.temporaryExit=0;
    }

    /**
     * Methods
     *
     * @return the passenger information
     */
    public String getName() {
        return name;
    }

    public int getEnteryFloor() {
        return enteryFloor;
    }

    public int getExitFloor() {
        return exitFloor;
    }

    public int getTemporaryExit() {
        return temporaryExit;
    }
    
    public void temporaryExit()
    {
        temporaryExit = temporaryExit+1;
    }
}
