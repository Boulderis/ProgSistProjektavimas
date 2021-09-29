	Pastaba dėl porininko unit testų.
	Visi reikalavimai, paminėti I laboratorinio darbo aprašyme, ištestuojami.
Testuojami ir teisingi ir neteisingi variantai (ko, pavyzdžiui, mano testuose trūko).
Taigi sugaudoma išties nemažai klaidų ir neleidžiama blokuoti teisingų variantų.
	Tik turiu pastebėjimą dėl panaudotų throw'ų testavimo metu.
Tai reiškia, kad aš, realizuodamas testus, taip pat turėjau throwint iš metodų. Nemanau, kad šiuo atveju tai yra labai gera praktika,
kadangi paštus, telefono numerius, slaptažodžius įvedinėja vartotojai. Labai dažnai pasitaiko klaidų. Vartotojų klaidas galima (ir, manau, yra geriausia)
sutvarkyti su if'ais. Throw'ai būtų tinkamesni programuotojams, kai jie bando kviesti metodus. Pavyzdžiui, jei programuotojas bando paduoti objektą, kuris
yra drauždiamas to metodo, metodas gali iškart throwint ir programuotojas anksti pastebės klaidą ir taip bus sutaupyta laiko, nes paskui nereikės jos ieškoti.
Apie vartotojo įvestas klaidas rekomenduočiau pranešinėti boolean'ais arba error code'ais (ir jų masyvais).
Tačiau šis porininko pasirinktas testavimo būdas nesutrukdė lengvai kurti realizacijos klasių.