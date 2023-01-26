package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn(){

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }


    @Test
    public void hentKonti_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }



    /**** Hent Transaksjoner *****/

    // Tester hent Transaksjoner Logget Inn - OK
    @Test
    public void test_hentTransaksjonerLoggetInnOk(){

        List <Transaksjon> transaksjonList = new ArrayList<>();
        // new transactions
        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1","105010123456");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456", 720, "Lønnskonto", "NOK", transaksjonList);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konto1);

        Konto resultat = bankController.hentTransaksjoner("105010123456", "2015-03-15", "2015-03-20");

        assertEquals(resultat, konto1);


    }


    // Tester hent Transaksjoner Logget Inn - Feil
    @Test
    public void test_hentTransaksjonerIkkeLoggetInn(){

        when(sjekk.loggetInn()).thenReturn(null);
        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("", "", "");

        assertNull(resultat);
    }

    /*******  Hent Saldi  ********/

    // Tester hent Saldi Logget Inn - OK
    @Test
    public void test_hentSaldiLoggetInnOK() {
        // arrange
        List<Konto> konti = new ArrayList<>();

        Konto konto1 = new Konto("01010110523","105010123456", 720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("01010110523","105020123456", 100500, "Sparekonto", "NOK", null);

        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }



    // Tester hent saldi Logget Inn - Feil
    @Test
    public void test_hentSaldiLoggetInnFeil(){

        when(sjekk.loggetInn()).thenReturn(null);

        List<Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }


    /****** Register betaling ******/

    // Tester register betaling Logget Inn - OK
    @Test
    public void test_registerBetalingLoggetInnOK(){

        // arrange
        Transaksjon betaling = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(betaling)).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(betaling);

        // assert
        assertEquals("OK", resultat);
    }


    // Tester register betaling Logget Inn - Feil
    @Test
    public void test_registerBetalingLoggetInnFeil(){

        // arrange
        Transaksjon betaling = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        when(sjekk.loggetInn()).thenReturn(null);
        // act
        String resultat = bankController.registrerBetaling(betaling);

        // assert
        assertNull(resultat);
    }

    /***** Hent Betaling ****/
    @Test
    public void test_hentBetalingLoggetInnOK(){

        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1","105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);


        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjoner, resultat);


    }


    // Tester hent betaling Logget Inn - Feil
    @Test
    public void test_hentBetalingLoggetInnFeil(){

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);

    }

    /***** Utfør Betaling *****/
    @Test
    public void utforBetalingLoggetInnOK(){

        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        //Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1","105010123456");


        Konto konto1 = new Konto("01010110523", "105010123456", 720, "Lønnskonto", "NOK", transaksjoner);

        transaksjoner.add(transaksjon1);
        //transaksjoner.add(transaksjon2);


        when(sjekk.loggetInn()).thenReturn(konto1.getPersonnummer());

        when(repository.utforBetaling(transaksjon1.getTxID())).thenReturn("OK");

        when(repository.hentBetalinger(konto1.getPersonnummer())).thenReturn(transaksjoner);


        // act
        List<Transaksjon> resultat = bankController.utforBetaling(transaksjon1.getTxID());

        // assert
        assertEquals(transaksjoner, resultat);

    }


    // Tester utfør betaling Logget Inn - Feil
    @Test
    public void utforBetalingLoggetInnFeil(){

        // arrange
        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(transaksjon1.getTxID());

        // assert
        assertNull(resultat);
    }


    /***** Hent Kunde Info *****/
    @Test
    public void hentKundeInfoLoggetInnOK(){

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(enKunde.getPersonnummer());

        when(repository.hentKundeInfo(enKunde.getPersonnummer())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }


    // Tester hent kunde info Logget Inn - Feil
    @Test
    public void hentKundeInfoLoggetInnFeil(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    /***** Endre Kunde Info *****/
    @Test
    public void endreKundeInfoLoggetInnOK(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(enKunde.getPersonnummer());

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    // Tester endre kunde info Logget Inn - Feil
    @Test
    public void endreKundeInfoLoggetInnFEIL(){
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }
}

// kommentar
//kommentar 2
// kommentar3
// kommentar4
// kommentar 5
// kommentar 6
//kommentar 7