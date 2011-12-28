package net.bodtx.executor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.bodt.forkjoin.ReducteurImage;
import net.bodt.forkjoin.ResizeTask;

public class ResizeExecutors {
	
	static class ResizeExecutor implements Callable<File> {
        private final File myFile;
        ResizeExecutor(File theFile) {
            this.myFile = theFile;
        }
        
        public File call() {
        	
            try {
				return ReducteurImage.scale(myFile, 640, 480);
			} catch (IOException e) {
				e.printStackTrace();
			};
			return null;
        }                
    }

	public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException
    {
		long startDate = System.nanoTime();
		
		
		File dir = new File("C:\\Documents and Settings\\akrier\\Mes documents\\Mes images");
       
        File[] files = dir.listFiles();
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        List<Future<File>> results = new LinkedList<Future<File>>();
        for (File myFile : files) {
        	results.add(executor.submit(new ResizeExecutor(myFile)));
		}
        System.out.println("Je dois m'afficher avant la fin des taches asynchrones"); 
        executor.shutdown();
        for (Future<File> future : results) {
			System.out.println("tache terminée ?:"+future.isDone());
			System.out.println("nom généré " +(future.get()!=null?future.get().getName():"impossible image KO"));
			System.out.println("tache terminée !:"+future.isDone());
		}
        System.out.println("Je dois m'afficher après que toutes les taches soient terminées");
        System.out.println(executor.isTerminated());
        //tiens pas comptes des fichiers au mauvais format
        System.out.println("nb fichiers resizés :"+results.size());
        
        long endDate = System.nanoTime();
        
        System.out.println(endDate-startDate);


        

    }
}
