package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EnhatstestKundeController {
    @InjectMocks
    // denne skal testes
    private AdminKundeController kundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void test_registrerKundeOK() {

        // arrage
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        Mockito.when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.registrerKunde(kunde1)).thenReturn("OK");


        // act
        String resultat = kundeController.lagreKunde(kunde1); //

        // assert
        assertEquals("OK", resultat);

    }

    @Test
    public void test_registrerKundeFeil() {

        // arrage
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        Mockito.when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.registrerKunde(kunde1)).thenReturn("Feil");

        // act

        String resultat = kundeController.lagreKunde(kunde1); //
        // assert
        assertEquals("Feil", resultat);
    }
    @Test
    public void test_hentAlleOK() {
        // arrage
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Mockito.when(sjekk.loggetInn()).thenReturn("01010110523");
        // arrage
        Kunde kunde2 = new Kunde("105010123456",
                "Per", "Hansen", "Drammenveien 23", "3070",
                "Drammen", "33334444", "HeiHei");
        Mockito.when(sjekk.loggetInn()).thenReturn("105010123456");
        List<Kunde> kunder= new ArrayList<>();
        kunder.add(kunde1);
        kunder.add(kunde2);
        Mockito.when(repository.hentAlleKunder()).thenReturn(kunder);
        List<Kunde> resultat= kundeController.hentAlle();
        assertEquals(kunder,resultat);
    }
    @Test
    public void test_hentAlleFiel() {
        // arrage
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Mockito.when(sjekk.loggetInn()).thenReturn("01010110523");
        // arrage
        Kunde kunde2 = new Kunde("105010123456",
                "Per", "Hansen", "Drammenveien 23", "3070",
                "Drammen", "33334444", "HeiHei");
        Mockito.when(sjekk.loggetInn()).thenReturn("105010123456");
        List<Kunde> kunder= new ArrayList<>();
        kunder.add(kunde1);
        kunder.add(kunde2);
        Mockito.when(repository.hentAlleKunder()).thenReturn(null);
        List<Kunde> resultat= kundeController.hentAlle();
        assertNull(resultat);
    }
}