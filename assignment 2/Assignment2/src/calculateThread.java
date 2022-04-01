import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class calculateThread extends Thread{
    private int[] S;
    private int operation;
    private int mode;

    private final ReentrantLock lock = new ReentrantLock();
    public calculateThread(int[] S, int operation){
        this.S = S;
        this.operation = operation;
    }

    @Override
    public void run() {
        try{
            lock.lock();
            switch (operation){
                case 1: //2的x次方
                    for(int i = 0; i < S.length; i++){
                        S[i] = (int)Math.pow(2,S[i]);
                    }
//                    System.out.println("case1 is working");
                    break;
                case 2:  //x的平方
                    for (int i = 0; i < S.length; i++){
                        S[i] = (int)S[i]*S[i];
                    }
//                    System.out.println("case2 is working");
                    break;
                case 3:  //跟号x
                    for (int i = 0; i < S.length; i++){
                        S[i] = (int)Math.pow(S[i], 0.5);
                    }
//                    System.out.println("case3 is working");
                    break;
                case 4:  //logx
                    for (int i = 0; i < S.length; i++){
                        S[i] = (int)Math.log(S[i]);
                    }
//                    System.out.println("case4 is working");
                    break;
                default:
                    System.out.println("Thread is not working");
                    break;
            }
        }finally {
            lock.unlock();
        }




    }
}
