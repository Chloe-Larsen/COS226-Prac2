
# Practical 2

To develop an understanding of mutual exclution algorithms that support more than two concurrent threads. In contrast to the previous practical, these two algorithms must be able to implement mutual exclusion to an arbitrary number of threads. 

The multiple threads will be trying to access a shared resource, which will need to be coordinated.



## Authors

- [@Chloe Larsen - u25004141](https://github.com/Chloe-Larsen)

- [@Caleb Jennings - u25173805](https://github.com/cablexd)

- [@Anchen Kruger - u25073703](https://github.com/anchenk)
## Filter Lock

Threads must pass through multiple "levels" otherwise called filters to be able to enter the critical section. This is a hierarchical filtering approach.

For each level, there can only be one "victim" which would be the first thread to arrive at that level. 

The victim at a certain level must wait until all threads at that level or highers have moved on. 

If a thread is not the victim it can advance to the next level.
Only the most recent arrived thread is delayed, which allow other threads to proceed. 

Once there are no more threads on a certain level with the original thread, it can also advance.

## Bakery Lock 

Uses a ticket-based ordering system. 

Conceptually, this can be compared to when you walk into a bakery and you are given a ticket at arrival.

The lower your ticket number is, the sooner you are to be served. 

Makes use of a label to give a thread a ticket number.
## Variables

### Filter lock variables: 

    level: 

This tracks which filter level thread i is currently at. 
If the thread is at level 0, that means it is not competing for the lock, however if its at any number from 1 to n-1, the thread wants the lock. 
When it had reached the highest level, n-1 then it can officially enter the critical section. 

Therefore this acts as a priority indicator for the threads.

    victim: 

This identifies which thread is the victim at the certain level. 
Being the victim means that that is the most recent thread to have arrived at that level and must therefore wait for others. Only the victim will be delayed at that level which allows other threads to proceed. 


### Bakery lock variables:

    flag: 

This will indicate if a thread is interested in acquiring in the lock. If it is true, it wants to compete, if false it is either not competing or is finished and has entered the critical section.
This allows that threads that are not competing to not block any other threads. Otherwise there will always be a smallest label, 0, and will block all threads.

    label: 

This is the ticket number that has been assigned to a thread and what will determie the order in which the threads acquire the lock. This is a priority ticket, lower ticket numbers have a higher ticket priority. 

## Comparison

A Filter lock requires the threads to pass through multiple levels before reaching the critical section, whereas with Bakery Lock the threads get numbers and wait for their turn to come. 

The Filter Lock have an approximate FIFO system however it is not as strict as the Bakery Lock.

Filter Lock waits if that thread is the victim and if there are others at higher levels to the one you are on. The Bakery Lock makes the thread wait if there is a thread with a smaller ticket number.

Both only allow one thread to enter the critical section at a time however with different requirements.
There will always be a thread to enter into the critical section at some point.
There will be no deadlock and also no starvation.

The Filter Lock does not guarantee the FIFO sorting that the Bakery Lock guarantees, nor the deterministic ordering.

The Filter Lock has more complex logic than that of the Bakery lock, however this makes the Filter Lock better for scalability, large amounts of threads. 