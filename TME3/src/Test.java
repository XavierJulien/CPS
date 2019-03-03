
public class Test {

	public static void main(String[] args) {
		FilesPrioImpl<Integer> filenorm = new FilesPrioImpl<>();
		FilesPrioImplBug<Integer> filebug = new FilesPrioImplBug<>();
		filenorm.init();
		filebug.init();
		FilesPrioContract<Integer> file1 = new FilesPrioContract<>(filenorm);
		FilesPrioContract<Integer> file2 = new FilesPrioContract<>(filebug);
		file1.putPrio(0,new Integer(1));
		file2.putPrio(0,new Integer(1));
		file1.putPrio(0,new Integer(2));
		System.out.println(new Integer(1)==file1.remove());
		System.out.println(new Integer(2)==file1.remove());
		System.out.println(new Integer(1)==file2.remove());
	}

}
