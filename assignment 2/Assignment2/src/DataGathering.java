public class DataGathering {
    private int mode;
    private int operation;
    private int[] S;
    private final int  k = 2;
    private double time;
    public void setTime(double t){time = t;}
    public double getTime(){return time;}

    public DataGathering(int mode, int operation, int[] S){
        this.mode = mode;
        this.operation = operation;
        this.S = S;
    }


    public int[] getOperatedS() throws InterruptedException {
        int[] result = new int[S.length];
        int length = S.length;

        try{
            switch (mode) {
                case 1://when mode = 1
                    System.out.println("DataGathering says: mode1 is implement");
                    //k >= S.length
                    //一个线程1个数，一共有length个线程
                    if(k>=length){
                        System.out.println("DataGathering says: we come to the condition k>= length.");
                        for (int i = 0; i < length; i++) {
                            int[] temp = {S[i]};
                            calculateThread thread1 = new calculateThread(temp, operation);
                            thread1.start();
                            thread1.join();
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println("Operated number: " + temp[0]);
                            result[i] = temp[0];
                        }
                    }else{  //k < length
                        System.out.println("DataGathering says: we come to the condition k < length.");
                        int quntient = length / k;
                        int mod = Math.floorMod(length, k);
//                        System.out.println("q = " + quntient + " m = " + mod);
                        if(mod == 0){
                            //一共有k个线程，一个线程里有q个数
                            System.out.println("DataGathering says: we come to the condition mod = 0");
                            int[] temp = new int[quntient];
                            int count = 0;
                            while(count < k) {
                                for (int i = 0; i < quntient; i++) {
                                    temp[i] = S[i + count * quntient];
                                }
                                calculateThread thread = new calculateThread(temp, operation);
                                thread.start();
                                thread.join();
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                                for(int i = 0; i < quntient; i++){
                                    result[i + quntient*count] = temp[i];
                                }
                                count++;
                            }
                        }else{ //mod != 0
                            System.out.println("DataGathering says: we come to the condition mod != 0");
                            //第一段 有mod个线程 每个线程有q+1个数
                            int[] temp1 = new int[quntient+1];
                            int count1 = 0;
                            while(count1 < mod){
                                for(int i = 0; i < quntient+1; i++){
                                    temp1[i] = S[i + count1 * (quntient+1)];
                                }
                                calculateThread thread1 = new calculateThread(temp1, operation);
                                thread1.start();
                                thread1.join();
                                for(int i = 0; i < quntient+1; i++){
//                                    System.out.println("Operated number is " + temp1[i]);
                                    result[i + (quntient+1)*count1] = temp1[i];
                                }
                                count1++;
                            }

                            //第二段 有k-mod个线程 每个线程有q个数
                            int[] temp2 = new int[quntient];
                            int count2 = 0;
                            while(count2 < k-mod){
                                for(int i = 0;i < quntient; i++){
                                    temp2[i] = S[i+ (quntient+1)*mod + count2*quntient] ;
                                }

                                calculateThread thread2 = new calculateThread(temp2, operation);
                                thread2.start();
                                thread2.join();
//                                System.out.println("temp2[1] = " + temp2[1]);
                                for(int i = 0; i < quntient; i++){
//                                System.out.println("Operated number is " + temp2[i]);
                                    result[i+ (quntient+1)*mod + count2*quntient] = temp2[i];
                                }
                                count2++;
                            }

                        }

                    }

                    break;
                case 2: //when mode = 2
                    // k > S.length a number per thread
                    System.out.println("DataGathering says: mode2 is implement");
                    if (k >= S.length) {
                        System.out.println("DataGathering says: We come to the condition k >= length");
                        for (int i = 0; i < length; i++) {
                            int[] temp = {S[i]};
//                            System.out.println("This is the operating number " + S[i]);
                            calculateThread thread1 = new calculateThread(temp, operation);
                            thread1.start();
                            thread1.join();
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                            result[i] = temp[0];
//                System.out.println("This is the temp " + temp[0]);
//                System.out.println("This is the operated number " + result[i]);
                        }
                    } else {// k < S.length. For thread 1 to k-1, a number per thread. For thread k, has length+1-k numbers
                        //前半段 每个线程一个数字, 共k-1个线程
                        System.out.println("DataGathering says: We come to the condition k < length");
                        System.out.println("DataGathering says: Now we start phase 1: k-1 thread, 1 number per thread");
                        for (int i = 0; i < k - 1; i++) {
                            int[] temp = {S[i]};
                            calculateThread thread2 = new calculateThread(temp, operation);
                            thread2.start();
                            thread2.join();
                            result[i] = temp[0];
                            System.out.println("DataGathering says: each number after operation: " + result[i]);
                        }

//                        try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                        // 后半段 最后一个线程， 这个线程length-k+1个数字
                        System.out.println("DataGathering says: Now we come to phase2: 1 thread, length-k+1 number per thread");
                        int[] temp2 = new int[length + 1 - k];
                        for (int i = 0; i < S.length + 1 - k; i++) {
                            temp2[i] = S[i + k - 1];
                        }
                        calculateThread thread = new calculateThread(temp2, operation);
                        thread.start();
                        thread.join();

                        for (int i = 0; i < S.length + 1 - k; i++) {
                            result[i + k - 1] = temp2[i];
                            System.out.print(result[i + k - 1] + " ");
                        }
                        System.out.println();
                    }

                    break;
                default:
                    break;
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("DataGathering says: Now we have finished operation. ");
        System.out.println("DataGathering says: Here is the sequence after operation(int) ");
        for(int i = 0; i < result.length; i++){
            System.out.print(result[i] + " ") ;
        }
        System.out.println("");
        System.out.println("DataGathering says: I have sent the sequence(int) to Server");
        return result;

    }

}

