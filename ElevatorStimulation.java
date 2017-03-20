/******************************************************************************
 *  Compilation:  javac ElevatorStimulation.java
 *  Execution:    elevator input data.txt
 *
 *  Algorithm for an elevator stimulation, using Stacks
 *
 *
 ******************************************************************************/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The class uses stacks to demonstrate the key features represented by the data structure
 * It demonstrates the usage of key stack methods like <em>push</em>, <em>pop</em> and <em>isEmpty</em>
 * and <em>size</em>
 * Items in the stack are iterated in LIFO order.
 *
 *
 * @author  Boshika Tara
 * @version 1.0
 * @since   2016-10-01
 */
public class ElevatorStimulation {

    /**
     *
     * @param text file with input data
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter name of input file: ");
        String fileName=input.nextLine();
        ArrayList<Passenger> people=getListOfPassenger(fileName);
        if(people!=null)
        {
            simulate(people);
        }
        else
        {
            System.out.println("Error occured while reading input file.");
        }
    }

    /**
     * Reads a text file line by line, and stores the information in an ArrayList.
     * Everytime a new entry is read from a text file it is instantiated  using the Passenger
     * class, and then stored in the arraylist
     *
     * @param textfile with passenger data
     * @return  an arraylist containing the passenger name, entry, and exit floors
     */
    public static ArrayList<Passenger> getListOfPassenger(String fileName)
    {
        ArrayList<Passenger> arr=new ArrayList<>();
        try 
        {
            BufferedReader input=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            while(input.ready())
            {
                String data=input.readLine();
                 if(!data.startsWith("//")&&data.length()>1)
                 {
                    Scanner scr=new Scanner(data);
                    String name=scr.next();
                    name=name.trim();
                    int entry=scr.nextInt();
                    int exit=scr.nextInt();
                    arr.add(new Passenger(name, entry, exit));
                 }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return arr;
    }

    /**
     * The program uses Stacks to implement passenger entry and exit to/from the elevator.
     * It uses two different stacks, one two track people using the elvator, another
     * temporary stack to remove/add people tempoarily from the elevator, to make room for people
     * exiting behind them.
     * The method, uses two seperate loops, one two tracj exits, both permamnent and temporary
     * Other to track passenger entries.
     *
     * @param list
     */
    public static void simulate(ArrayList<Passenger> list)
    {
        int numberOfPeopleRide=0;
        int numberOfPeopleDoNotRide=0;
        int emptyCount=0;
        int fullCount=0;
        
        boolean direction=true; //for up true for down false
        int targetFloor=-1; //target of lift
        int currentFloor=1;  //start the lift from here
        
        //stack for elevator
        Stack<Passenger> elevator=new Stack<Passenger>();
        // temporary stack
        Stack<Passenger> temp=new Stack<>();
        
        //keep track of number of people with exitfloor
        int target[]=new int[10];
        
        while(!list.isEmpty()||!elevator.isEmpty())
        {
            if(elevator.isEmpty())
            {
                System.out.println("Elevator is empty.");
                emptyCount++;

            }
            //first check to see if there people who want to exit at current floor
            if(currentFloor==targetFloor)
            {
                //if someone wants to exit
                //remove others in front of them
                while(target[currentFloor-1]!=0)
                {
                    //remove from elevator
                    Passenger r=elevator.pop();
                    //check if it is the passengers exit
                    if(r.getExitFloor()==currentFloor)
                    {
                        target[currentFloor-1]--;
                        System.out.println(r.getName()+" exit. His/her exit floor is--->"+r.getExitFloor()+" His/her total number of temporary exits----> "+r.getTemporaryExit());
                    }
                    else
                    {
                        temp.push(r);
                        r.temporaryExit();
                    }
                }
                
                //load the elevator with temporary exit person
                while(!temp.isEmpty())
                {
                    elevator.push(temp.pop());
                }
            }
            
            //now come to those who were waiting for lift
            if(elevator.size()==5)
            {
                System.out.println("Elevator is full.");
                fullCount++;
                //these people have to travel without elevator
                while(!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                {
                    System.out.println(list.get(0).getName()+" can't get into elevator so he/she take stairs. His/Her destination is floor: "+list.get(0).getExitFloor());
                    numberOfPeopleDoNotRide++;
                    list.remove(0);
                }
            }
            else
            {
                //fill until elevator become full or waiting people for this floor become zero
                while(elevator.size()<5&&!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                {
                    Passenger p=list.remove(0);
                    elevator.push(p);
                    numberOfPeopleRide++;
                    target[p.getExitFloor()-1]++;
                }
                
                //if elevator become full and waiting people are left then they have to take stairs
                if(elevator.size()==5&&!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                {
                    fullCount++;
                    System.out.println("Elevator is full.");
                    //these people have to travel without elevator
                     while(!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                     {
                            System.out.println(list.get(0).getName()+" can't get into elevator so he/she takes stairs. His/Her destination is floor: "+list.get(0).getExitFloor());
                            numberOfPeopleDoNotRide++;
                            list.remove(0);
                    }
                }
            }
            //find target floor
            if(direction)
            {
                    if(currentFloor==5)
                    {
                        direction=false;
                        for(int i=4;i>=0;i--)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             }
                        }
                        currentFloor--;
                    }
                    else
                    {
                        for(int i=currentFloor-1;i<5;i++)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             }
                        }
                        currentFloor++;
                    }
                    
            }
            else
            {
                    if(currentFloor==1)
                    {
                        direction=true;
                        for(int i=0;i<5;i++)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             }
                        }
                        currentFloor++;
                    }
                    else
                    {
                        for(int i=currentFloor-1;i>=0;i--)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             
                             }
                        }
                        currentFloor--;
                   }
           }
  
        }
        System.out.println("\n\n");
        System.out.println("Total number of passenger who took elevator: "+numberOfPeopleRide);
        System.out.println("Total number of passenger who don't take elevator: "+numberOfPeopleDoNotRide);
        System.out.println("Number of time elevator become empty: "+emptyCount);
        System.out.println("Number of time elevator become full: "+fullCount);
        
    }
    
}
