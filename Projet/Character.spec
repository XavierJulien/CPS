[GoLeft]:

/* pas de deplacement vertical */
Hgt(GoLeft(C)) = Hgt(C)

/* le joueur est à gauche de la map */
Wdt(C) = 0
	IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* le joueur est dans un trou */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) in {HOL}
	IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* un element bloque le joueur */
Environment::CellNature(Envi(C), Wdt(C) - 1, Hgt(C)) in {MTL, PLT} OR exists Character c in Environment::CellContent(Envi(C), Wdt(C) - 1, Hgt(C))
	IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* chut libre */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) not in {LAD,HDR}
	AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) - 1) not in {MTL, PLT, LAD}
		AND not exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
			IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* Cas Fonctionnels */
(Wdt(C) != 0)
	AND Environment::CellNature(Envi(C), Wdt(C) - 1, Hgt(C)) not in {MTL, PLT}
		AND not exists Character c in Environment::CellContent(Envi(C), Wdt(C) - 1, Hgt(C))
			AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) in {LAD, HDR}
				IMPLIES Wdt(GoUp(C)) = Wdt(C) - 1
			OR Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) = EMP
				AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) - 1) in {MTL, PLT, LAD} OR exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
					IMPLIES IMPLIES Wdt(GoUp(C)) = Wdt(C) - 1


[GoRight]:

/* pas de deplacement vertical */
Hgt(GoLeft(C)) = Hgt(C)

/* le joueur est à gauche de la map */
Wdt(C) = Environment::Wdt(Envi(C)) - 1
	IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* le joueur est dans un trou */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) in {HOL}
	IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* un element bloque le joueur */
Environment::CellNature(Envi(C), Wdt(C) + 1, Hgt(C)) in {MTL, PLT} OR exists Character c in Environment::CellContent(Envi(C), Wdt(C) + 1, Hgt(C))
	IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* chut libre */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) not in {LAD,HDR}
	AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) - 1) not in {MTL, PLT, LAD}
		AND not exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
			IMPLIES Wdt(GoUp(C)) = Wdt(C)

/* Cas Fonctionnels */
(Wdt(C) != Environment::Wdt(Envi(C)) - 1)
	AND Environment::CellNature(Envi(C), Wdt(C) - +, Hgt(C)) not in {MTL, PLT}
		AND not exists Character c in Environment::CellContent(Envi(C), Wdt(C) + 1, Hgt(C))
			AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) in {LAD, HDR}
				IMPLIES Wdt(GoUp(C)) = Wdt(C) + 1
			OR Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) = EMP
				AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) - 1) in {MTL, PLT, LAD} OR exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
					IMPLIES IMPLIES Wdt(GoUp(C)) = Wdt(C) + 1


[GoUp]:

/* pas de déplacement latéral */
Wdt(GoUp(C)) = Wdt(c)

/* le joueur est en haut de la map */
Hgt(C) = Environment::Hgt(Envi(C)) - 1
	IMPLIES Hgt(GoUp(C)) = Hgt(C)

/* un element bloque le joueur */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C) + 1) in {MTL, PLT, HDR} OR exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) + 1)
	IMPLIES Hgt(GoUp(C)) = Hgt(C)

/* chut libre */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) not in {LAD,HDR}
	AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) - 1) not in {MTL, PLT, LAD} 
		AND not exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
			IMPLIES Hgt(GoUp(C)) = Hgt(C)

/* le joueur essaie de sauter */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C) + 1) in {EMP}
		IMPLIES Hgt(GoUp(C)) = Hgt(C)

/* Cas Fonctionnels */
(Hgt(C) != Environment::Hgt(Envi(C)) - 1)
	AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) = EMP
		AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) + 1) = LAD
			AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) - 1) in {MTL, PLT} OR exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
				IMPLIES Hgt(GoUp(C)) = Hgt(C) + 1
	OR Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) = LAD
		AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) + 1) in {EMP, LAD}
			IMPLIES Hgt(GoUp(C)) = Hgt(C) + 1


[GoDown]:

/* pas de déplacement latéral */
Wdt(GoUp(C)) = Wdt(c)

/* le joueur est en bas de la map */
Hgt(C) = 0
	IMPLIES Hgt(GoUp(C)) = Hgt(C)

/* un element bloque le joueur */
Environment::CellNature(Envi(C), Wdt(C), Hgt(C) - 1) in {MTL, PLT, HDR} OR exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
	IMPLIES Hgt(GoUp(C)) = Hgt(C)

/* Cas Fonctionnels */
(Hgt(C) != 0)
	AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C)) = {EMP, LAD, HDR}
		AND Environment::CellNature(Envi(C), Wdt(C), Hgt(C) + 1) in {EMP,LAD}
			AND not exists Character c in Environment::CellContent(Envi(C), Wdt(C), Hgt(C) - 1)
				IMPLIES Hgt(GoUp(C)) = Hgt(C) - 1
