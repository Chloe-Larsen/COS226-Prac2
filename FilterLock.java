/*
Caleb Jennings - u25173805
Anchen Kruger - u25073703
Chloe Larsen - u25004141
*/
public class FilterLock implements Lock {

    private final int n;
    private final VolatileInt[] level;
    private final VolatileInt[] victim;

    public FilterLock(int n) {
        // n is max number of threads
        this.n = n;
        level = new VolatileInt[n];
        // n - 1 levels (threads in level 0 do not get filtered, they are threads not
        // requesting the lock)
        victim = new VolatileInt[Math.max(n - 1, 1)];

        for (int i = 0; i < n; i++)
            level[i] = new VolatileInt(0); // level each thread is on
        for (int i = 0; i < n - 1; i++)
            victim[i] = new VolatileInt(0); // thread that is victim on this level
    }

    @Override
    public void lock(int threadId) {
        level[threadId].value = 1;
        victim[level[threadId].value - 1].value = threadId;

        while (true) {
            int currentLevel = level[threadId].value;
            boolean stayBack = false;

            if (victim[currentLevel - 1].value == threadId) {
                // thread is victim -> check if there are threads at or above current level

                for (int i = 0; i < n; i++) {
                    if (i == threadId)
                        continue; // skip current thread
                    if (level[i].value >= currentLevel) {
                        stayBack = true;
                        break;
                    }
                }
            }

            if (!stayBack) {
                int nextLevel = currentLevel + 1;

                if (nextLevel == n) {
                    // already in highest level -> can now enter critical section
                    break; // stop spinning
                }

                // go to next level and offer itself as victim
                level[threadId].value = nextLevel;
                victim[nextLevel - 1].value = threadId;
            }
        }
    }

    @Override
    public void unlock(int threadId) {
        level[threadId].value = 0;
    }
}