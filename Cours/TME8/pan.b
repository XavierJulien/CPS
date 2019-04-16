	switch (t->back) {
	default: Uerror("bad return move");
	case  0: goto R999; /* nothing to undo */

		 /* PROC :init: */

	case 3: // STATE 1
		;
		;
		delproc(0, now._nr_pr-1);
		;
		goto R999;

	case 4: // STATE 2
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC ispanne */

	case 5: // STATE 2
		;
		now.panne = trpt->bup.oval;
		;
		goto R999;

	case 6: // STATE 4
		;
		now.panne = trpt->bup.oval;
		;
		goto R999;

	case 7: // STATE 7
		;
		p_restor(II);
		;
		;
		goto R999;

		 /* PROC feu */
;
		;
		
	case 9: // STATE 2
		;
		now.coulfeu = trpt->bup.oval;
		;
		goto R999;

	case 10: // STATE 3
		;
		now.etatfeu = trpt->bup.oval;
		;
		goto R999;

	case 11: // STATE 4
		;
		now.coulfeu = trpt->bup.oval;
		;
		goto R999;
;
		;
		;
		;
		
	case 14: // STATE 8
		;
		now.etatfeu = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 16: // STATE 11
		;
		now.coulfeu = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 18: // STATE 13
		;
		now.coulfeu = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 20: // STATE 15
		;
		now.coulfeu = trpt->bup.oval;
		;
		goto R999;
;
		;
		
	case 22: // STATE 24
		;
		now.coulfeu = trpt->bup.oval;
		;
		goto R999;

	case 23: // STATE 31
		;
		now.coulfeu = trpt->bup.oval;
		;
		goto R999;

	case 24: // STATE 35
		;
		p_restor(II);
		;
		;
		goto R999;
	}

