package net.bodt.forkjoin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("unchecked")
public class ResizeTask extends RecursiveTask {

	private File[] fileToSearch;
	private List<File> filesResized =  new LinkedList<File>();
	public ResizeTask(File[] fileToSearch) {
		super();
		this.fileToSearch = fileToSearch;
	}

	protected Object compute() {
		
		if(fileToSearch.length ==1){
			//do the job
			/*FULL CPU for 10secs
			long startDate = System.nanoTime();
			System.out.println("compute wait");
			while (true) {
				if (System.nanoTime() - startDate > 10000000000L)
					break;
			}*/
			try {
				filesResized.add(ReducteurImage.scale(fileToSearch[0], 640, 480));
				return filesResized;
				 
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		else{
			//divide into two tasks
			File[] fileToSearchdown = (File[]) Arrays.copyOfRange(fileToSearch, 0, fileToSearch.length/2);
			File[] fileToSearchup = (File[]) Arrays.copyOfRange(fileToSearch, (fileToSearch.length+2-1)/2, fileToSearch.length);
			
			ResizeTask subtask = new ResizeTask(fileToSearchup);
			subtask.fork();
			filesResized.addAll((List<File>)new ResizeTask(fileToSearchdown).compute());
			filesResized.addAll((List<File>)subtask.join());
			return filesResized;
		}
		return null;
		
		
		
		

		
	}
	
	public static void main( String[] args ) throws IOException, InterruptedException
    {
		long startDate = System.nanoTime();
		ForkJoinPool forkPool = new ForkJoinPool();
		
		File dir = new File("C:\\Documents and Settings\\akrier\\Mes documents\\Mes images");
       
        File[] files = dir.listFiles();
        
        List<File> filesResized =  new LinkedList<File>();
        filesResized = (List<File>) forkPool.invoke(new ResizeTask(files));
        //tiens pas comptes des fichiers au mauvais format
        System.out.println("nb fichiers resizés :"+filesResized.size());
        
        long endDate = System.nanoTime();
        
        System.out.println(endDate-startDate);


        

    }




	

}
