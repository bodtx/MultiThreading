package net.bodt.forkjoin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ResizeAction extends RecursiveAction {

	private File[] fileToSearch;

	public ResizeAction(File[] fileToSearch) {
		super();
		this.fileToSearch = fileToSearch;
	}

	protected void compute() {
		
		if(fileToSearch.length ==1){
			//do the job
			/*long startDate = System.nanoTime();
			System.out.println("compute wait");
			while (true) {
				if (System.nanoTime() - startDate > 10000000000L)
					break;
			}*/
			try {
				ReducteurImage.scale(fileToSearch[0], 640, 480);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		else{
			//divide into two tasks
			File[] fileToSearchdown = (File[]) Arrays.copyOfRange(fileToSearch, 0, fileToSearch.length/2);
			File[] fileToSearchup = (File[]) Arrays.copyOfRange(fileToSearch, (fileToSearch.length+2-1)/2, fileToSearch.length);
			invokeAll(new ResizeAction(fileToSearchdown),
	                 new ResizeAction(fileToSearchup));

		}
		
		

		
	}
	
	public static void main( String[] args ) throws IOException, InterruptedException
    {
		long startDate = System.nanoTime();
		ForkJoinPool forkPool = new ForkJoinPool();
		
		File dir = new File("C:\\Documents and Settings\\akrier\\Mes documents\\Mes images");
       
        File[] files = dir.listFiles();
        
        forkPool.invoke(new ResizeAction(files));

        
        long endDate = System.nanoTime();
        
        System.out.println(endDate-startDate);


        

    }



	

}
