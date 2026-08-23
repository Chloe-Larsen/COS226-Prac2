/*
Caleb Jennings - u25173805
Anchen Kruger - u25073703
Chloe Larsen - u25004141
*/
public class BakeryLock implements Lock {

    private final int n;
    private final VolatileBoolean[] flag;
    private final VolatileInt[] label;

    public BakeryLock(int n) {
        this.n = n;
        flag = new VolatileBoolean[n];
        label = new VolatileInt[n];
        for (int i = 0; i < this.n; ++i) {
            flag[i] = new VolatileBoolean(false);
            label[i] = new VolatileInt(0);
        }
    }

    @Override
    public void lock(int threadId) {
        flag[threadId].value = true;// set value of threadId's flag to true
        int maxTicket = 0;
        for (int i = 0; i < n; ++i) {// get max ticket
            maxTicket = Math.max(maxTicket, label[i].value);
        }
        label[threadId].value = maxTicket + 1; // assign threadId to the largest ticket + 1

        // now added thread to queue no run until used

        while (true) {
            boolean blocked = false;
            for (int i = 0; i < n; ++i) {
                if (i == threadId) {
                    continue;
                }
                if (flag[i].value && (label[i].value < label[threadId].value
                        || (label[i].value == label[threadId].value && i < threadId))) {
                    blocked = true;
                    break;
                }
            }
            if (!blocked) {
                break;
            }
        }

        // enter critical
    }

    @Override
    public void unlock(int threadId) {
        flag[threadId].value = false;
    }
}