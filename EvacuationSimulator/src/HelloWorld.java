import evacSim.util.RNG;
//import evacSim.util.Wall;

public class HelloWorld {
	public static void main(String[] args){
		System.out.println("Hello World!");
		System.out.println("test");
		RNG r = new RNG();
		for(int i = 0; i < 100; i++)
			System.out.println(r.nextI(1, 100));
//		Wall w = new Wall();
//		System.out.println(w.getWeight());
	}
}