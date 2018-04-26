package src;



public class Program {

	public static void main(String[] args) {
	    while(true) {   
		    Service service = new Service();
		    System.out.println("Pokrecem servis...\n");
	     	service.run();
        }
	}

}
