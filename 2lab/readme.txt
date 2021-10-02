	Porininko "Unit" testų apibendrinimas.
	Visi reikalavimai, paminėti I laboratorinio darbo aprašyme, ištestuojami.
Testuojami ir teisingi ir neteisingi variantai.
Taigi sugaudoma išties nemažai klaidų ir neleidžiama blokuoti teisingų variantų.
	Tik turiu keletą pastebėjimų:
	Visų pirma dėl testuose naudotų throw'ų. Tai reiškia, kad aš, realizuodamas testus, taip pat turėjau throwint iš metodų. Nemanau, kad šiuo atveju tai yra labai gera praktika,
kadangi paštus, telefono numerius, slaptažodžius įvedinėja vartotojai. Labai dažnai pasitaiko klaidų. Vartotojų klaidas galima (ir, manau, yra geriausia)
sutvarkyti su if'ais. Throw'ai būtų tinkamesni programuotojams, kai jie bando kviesti metodus. Pavyzdžiui, jei programuotojas bando paduoti objektą, kuris
yra drauždiamas to metodo, metodas gali iškart throwint ir programuotojas anksti pastebės klaidą ir taip bus sutaupyta laiko, nes paskui nereikės jos ieškoti.
Apie vartotojo įvestas klaidas rekomenduočiau pranešinėti boolean'ais arba error code'ais (ir jų masyvais).
Tačiau šis porininko pasirinktas testavimo būdas nesutrukdė lengvai kurti realizacijos klasių.
    Taip pat teko pakeisti pirmuosius testus visose trijose klasėse, nes kitaip testai nesikompiliuotų.
    Mano porininkas buvo parašęs:


      @Test
        public void Should_ValidateEmail_When_EmailIsCorrect() {
            String email = "test.google4~_-09ASZ+56ual.proceed.com@example.com";

            emailValidator.validate(email);
        }

        @Test
        public void Should_ThrowException_When_EmailIsNull() {
            String email = null;

            assertThrows(InvalidEmailException.class, () -> {
                emailValidator.validate(email);
            });
        }


        Taigi matome, kad antrasis testas reikalauja, kad metodas turėtų throws InvalidEmailException.
        Tą ir parašau. Tačiau pirmasis testas sako tiesiog "emailValidator.validate(email);" Tačiau šito negali būti. Išeina taip,
        kad pirmasis metodas draudžia naudoti tai, ko reikalauja antrasis metodas. Prieštara, kodas nesikompiliuos.
        Reikia pakeisti. Kadangi mano metodas naudoja throws, reikia rašyti assertDoesNotThrow, nes kodas kitaip negali kompiliuotis.
        Dar žinoma, galima rašyti tiesiog, try, catch, bet assertDoesNotThrow yra prasmingiausia, kadangi tai yra testas.
        Taigi ištaisau:

            @Test
            public void Should_ValidateEmail_When_EmailIsCorrect() {
                String email = "test.google4~_-09ASZ+56ual.proceed.com@example.com";

                assertDoesNotThrow(() -> emailValidator.validate(email)); // Teko pakeisti.
            }

            Ir dabar pirmasis testas neprieštarauja, nei vienam iš likusių testų. Testai gali kompiliuotis.
            Šią "emailValidator.validate(email);" ar panašią pakeičiau ir kitose testų klasėse. Keičiau tik tiek, kad
            kodas galėtų kompiliuotis.
            Buvo tik šio tipo klaida, daugiau mano manymu, viskas yra padaryta gerai.