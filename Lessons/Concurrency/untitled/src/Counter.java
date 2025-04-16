

public class Counter implements Runnable {
    private String name;
    public Counter (String name){
        this.name = name;
    }
    public void run(){
        for(int i=0; i< 500; i++){
            System.out.println(name + ": " + i);
        }

    }
}
