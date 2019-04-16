#define rand	pan_rand
#define pthread_equal(a,b)	((a)==(b))
#if defined(HAS_CODE) && defined(VERBOSE)
	#ifdef BFS_PAR
		bfs_printf("Pr: %d Tr: %d\n", II, t->forw);
	#else
		cpu_printf("Pr: %d Tr: %d\n", II, t->forw);
	#endif
#endif
	switch (t->forw) {
	default: Uerror("bad forward move");
	case 0:	/* if without executable clauses */
		continue;
	case 1: /* generic 'goto' or 'skip' */
		IfNotBlocked
		_m = 3; goto P999;
	case 2: /* generic 'else' */
		IfNotBlocked
		if (trpt->o_pm&1) continue;
		_m = 3; goto P999;

		 /* PROC :init: */
	case 3: // STATE 1 - feu.pml:48 - [(run feu())] (0:0:0 - 1)
		IfNotBlocked
		reached[2][1] = 1;
		if (!(addproc(II, 1, 0)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 4: // STATE 2 - feu.pml:49 - [-end-] (0:0:0 - 1)
		IfNotBlocked
		reached[2][2] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC ispanne */
	case 5: // STATE 2 - feu.pml:41 - [panne = non] (0:0:1 - 1)
		IfNotBlocked
		reached[1][2] = 1;
		(trpt+1)->bup.oval = now.panne;
		now.panne = 9;
#ifdef VAR_RANGES
		logval("panne", now.panne);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 6: // STATE 4 - feu.pml:42 - [panne = oui] (0:0:1 - 1)
		IfNotBlocked
		reached[1][4] = 1;
		(trpt+1)->bup.oval = now.panne;
		now.panne = 10;
#ifdef VAR_RANGES
		logval("panne", now.panne);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 7: // STATE 7 - feu.pml:45 - [-end-] (0:0:0 - 3)
		IfNotBlocked
		reached[1][7] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */

		 /* PROC feu */
	case 8: // STATE 1 - feu.pml:11 - [((etatfeu==initial))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][1] = 1;
		if (!((now.etatfeu==8)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 9: // STATE 2 - feu.pml:11 - [coulfeu = orangeclignotant] (0:0:1 - 1)
		IfNotBlocked
		reached[0][2] = 1;
		(trpt+1)->bup.oval = now.coulfeu;
		now.coulfeu = 3;
#ifdef VAR_RANGES
		logval("coulfeu", now.coulfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 10: // STATE 3 - feu.pml:12 - [etatfeu = normal] (0:0:1 - 1)
		IfNotBlocked
		reached[0][3] = 1;
		(trpt+1)->bup.oval = now.etatfeu;
		now.etatfeu = 7;
#ifdef VAR_RANGES
		logval("etatfeu", now.etatfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 11: // STATE 4 - feu.pml:13 - [coulfeu = rouge] (0:0:1 - 1)
		IfNotBlocked
		reached[0][4] = 1;
		(trpt+1)->bup.oval = now.coulfeu;
		now.coulfeu = 2;
#ifdef VAR_RANGES
		logval("coulfeu", now.coulfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 12: // STATE 6 - feu.pml:16 - [((etatfeu==normal))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][6] = 1;
		if (!((now.etatfeu==7)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 13: // STATE 7 - feu.pml:19 - [((panne==oui))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][7] = 1;
		if (!((now.panne==10)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 14: // STATE 8 - feu.pml:19 - [etatfeu = enpanne] (0:0:1 - 1)
		IfNotBlocked
		reached[0][8] = 1;
		(trpt+1)->bup.oval = now.etatfeu;
		now.etatfeu = 6;
#ifdef VAR_RANGES
		logval("etatfeu", now.etatfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 15: // STATE 10 - feu.pml:22 - [((coulfeu==rouge))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][10] = 1;
		if (!((now.coulfeu==2)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 16: // STATE 11 - feu.pml:22 - [coulfeu = vert] (0:0:1 - 1)
		IfNotBlocked
		reached[0][11] = 1;
		(trpt+1)->bup.oval = now.coulfeu;
		now.coulfeu = 5;
#ifdef VAR_RANGES
		logval("coulfeu", now.coulfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 17: // STATE 12 - feu.pml:23 - [((coulfeu==vert))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][12] = 1;
		if (!((now.coulfeu==5)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 18: // STATE 13 - feu.pml:23 - [coulfeu = orange] (0:0:1 - 1)
		IfNotBlocked
		reached[0][13] = 1;
		(trpt+1)->bup.oval = now.coulfeu;
		now.coulfeu = 4;
#ifdef VAR_RANGES
		logval("coulfeu", now.coulfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 19: // STATE 14 - feu.pml:24 - [((coulfeu==orange))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][14] = 1;
		if (!((now.coulfeu==4)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 20: // STATE 15 - feu.pml:24 - [coulfeu = rouge] (0:0:1 - 1)
		IfNotBlocked
		reached[0][15] = 1;
		(trpt+1)->bup.oval = now.coulfeu;
		now.coulfeu = 2;
#ifdef VAR_RANGES
		logval("coulfeu", now.coulfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 21: // STATE 23 - feu.pml:28 - [((etatfeu==enpanne))] (0:0:0 - 1)
		IfNotBlocked
		reached[0][23] = 1;
		if (!((now.etatfeu==6)))
			continue;
		_m = 3; goto P999; /* 0 */
	case 22: // STATE 24 - feu.pml:28 - [coulfeu = orangeclignotant] (0:0:1 - 1)
		IfNotBlocked
		reached[0][24] = 1;
		(trpt+1)->bup.oval = now.coulfeu;
		now.coulfeu = 3;
#ifdef VAR_RANGES
		logval("coulfeu", now.coulfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 23: // STATE 31 - feu.pml:35 - [coulfeu = orangeclignotant] (0:0:1 - 1)
		IfNotBlocked
		reached[0][31] = 1;
		(trpt+1)->bup.oval = now.coulfeu;
		now.coulfeu = 3;
#ifdef VAR_RANGES
		logval("coulfeu", now.coulfeu);
#endif
		;
		_m = 3; goto P999; /* 0 */
	case 24: // STATE 35 - feu.pml:37 - [-end-] (0:0:0 - 1)
		IfNotBlocked
		reached[0][35] = 1;
		if (!delproc(1, II)) continue;
		_m = 3; goto P999; /* 0 */
	case  _T5:	/* np_ */
		if (!((!(trpt->o_pm&4) && !(trpt->tau&128))))
			continue;
		/* else fall through */
	case  _T2:	/* true */
		_m = 3; goto P999;
#undef rand
	}

