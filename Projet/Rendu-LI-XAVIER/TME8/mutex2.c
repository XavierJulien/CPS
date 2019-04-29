
// Solution "potentielle" pour le problème de l'exclusion mutuelle

/* D'après : Comm. of the ACM, Vol. 9, No. 1, p. 45)

  1 Boolean array b(0;1) integer k, i, j,
  2 comment process i, with i either 0 or 1 and j = 1-i;
  3 C0:	b(i) := false;
  4 C1:	if k != i then begin
  5 C2:	if not (b(j) then go to C2;
  6 	else k := i; go to C1 end;
  7 	else critical section;
  8 	b(i) := true;
  9 	remainder of program;
 10 	go to C0;
 11 	end
*/

// A vérifier en promela/spin

// (C) 2015 - F.Peschanski

#include <stdbool.h>
#include <stdlib.h>
#include <unistd.h>
#include <assert.h>
#include <stdio.h>

#include <pthread.h>

bool b1 = true;
bool b2 = true;

int turn = 1;

int cs_counter = 0;

void proc1(int repeat, int max_sleep) {
  int count = 0;

  while(count < repeat) {

    printf("[Proc1]: demande...\n");

    b1 = false;
    
  C1:
    if(turn != 1) {
    C2:
      if(!b2) {
	usleep(10);
	goto C2;
      } else {
	turn = 1;
	goto C1;
      }
      
      // Critical section
      printf("[Proc1]: entrée section critique...\n");
      cs_counter++;
      assert (cs_counter == 1);
    
      int msec = rand() % max_sleep; // Remarque: non-uniforme
    
      usleep(msec);
    
      cs_counter--;
      printf("[Proc1]: sortie section critique...\n");
    
      b1 = true;

      count += 1;
    }
  }
  
}

void proc2(int repeat, int max_sleep) {
  int count = 0;

  while(count < repeat) {

    printf("[Proc2]: demande...\n");

    b2 = false;
    
  C1:
    if(turn != 2) {
    C2:
      if(!b1) {
	usleep(10);
	goto C2;
      } else {
	turn = 2;
	goto C1;
      }
      
      // Critical section
      printf("[Proc2]: entrée section critique...\n");
      cs_counter++;
      assert (cs_counter == 1);
    
      int msec = rand() % max_sleep; // Remarque: non-uniforme
    
      usleep(msec);
    
      cs_counter--;
      printf("[Proc2]: sortie section critique...\n");
    
      b2 = true;

      count += 1;
    }
  }
  
}

#define INIT_SLEEP 500
#define REPEAT 20
#define MAX_SLEEP 1000

void *launch_proc1() {
  int msec = rand() % INIT_SLEEP;
  usleep(msec);
  proc1(REPEAT, MAX_SLEEP);
  return NULL;
}

void *launch_proc2() {
  int msec = rand() % INIT_SLEEP;
  usleep(msec);
  proc2(REPEAT, MAX_SLEEP);
  return NULL;
}

int main() {

  pthread_t th_proc1 = NULL;
  pthread_t th_proc2 = NULL;
  int res = 0;

  printf("START\n");

  res = pthread_create(&th_proc1, NULL, launch_proc1, NULL);
  assert(res == 0);

  res = pthread_create(&th_proc2, NULL, launch_proc2, NULL);
  assert(res == 0);

  
  res = pthread_join(th_proc1, NULL);
  assert(res == 0);
  pthread_join(th_proc2, NULL);
  assert(res == 0);
  
  printf("STOP\n");
  
  pthread_exit(NULL);
}
