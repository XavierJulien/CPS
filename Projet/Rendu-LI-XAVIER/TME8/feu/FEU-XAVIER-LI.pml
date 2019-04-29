mtype = {vert,orange,orangeclignotant,rouge,rien};
mtype = {initial,normal,enpanne};
mtype = {oui,non};

mtype etatfeu = initial;
mtype panne = non;
mtype coulfeu = rien;

proctype feu() {
	if
		::etatfeu==initial -> coulfeu=orangeclignotant;
							  etatfeu=normal;
							  coulfeu=rouge;
		else ->
			do
				::etatfeu==normal -> 
				   do
					   	::if 
						   	::panne==oui -> etatfeu=enpanne;
						   	  else -> 
						   	   	if 
						   	   	::coulfeu==rouge -> coulfeu=vert;
						   		::coulfeu==vert -> coulfeu=orange;
						   		::coulfeu==orange -> coulfeu=rouge;
						   		fi
					   	fi
				   od 
				::etatfeu==enpanne -> coulfeu=orangeclignotant;
									  goto dpanne;
			od					 
			
	fi
	dpanne: 
		do
		::coulfeu=orangeclignotant;
		od
}

active proctype ispanne() {
	if 
		::true -> panne=non;
		::true -> panne=oui;
	fi

}

init {
 run feu();
}
