
// Solution "potentielle" pour le problème de l'exclusion mutuelle
// A vérifier en promela/spin

// (C) 2015 - F.Peschanski

#include <stdbool.h>
#include <stdlib.h>
#include <unistd.h>
#include <assert.h>
#include <stdio.h>

#include <pthread.h>

bool ask0 = false;
bool ask1 = false;

char turn = 0;

int cs_counter = 0;

void proc1(int repeat, int max_sleep) {
  int count = 0;

  while(count < repeat) {

    printf("[Proc1]: demande...\n");
    
    ask0 = true;
    while(ask1 == true) {
      if(turn!=0) {
	ask0 = false;
	while (turn != 0) {
	  // attente active
	}
	ask0 = true;
      }
    }

    // Critical section
    printf("[Proc1]: entrée section critique...\n");
    cs_counter++;
    assert (cs_counter == 1);
    
    int msec = rand() % max_sleep; // Remarque: non-uniforme
    
    usleep(msec);

    cs_counter--;
    printf("[Proc1]: sortie section critique...\n");

    turn = 1;
    ask0 = false;

    count += 1;
  }

}

void proc2(int repeat, int max_sleep) {
  int count = 0;

  while(count < repeat) {

    printf("[Proc2]: demande...\n");
    
    ask1 = true;
    while(ask0 == true) {
      if(turn!=1) {
	ask1 = false;
	while (turn != 1) {
	  // attente active
	}
	ask1 = true;
      }
    }

    // Critical section
    printf("[Proc2]: entrée section critique...\n");
    cs_counter++;
    assert (cs_counter == 1);
    
    int msec = rand() % max_sleep; // Remarque: non-uniforme
    
    usleep(msec);

    cs_counter--;
    printf("[Proc2]: sortie section critique...\n");
    turn = 0;
    ask1 = false;

    count += 1;
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
