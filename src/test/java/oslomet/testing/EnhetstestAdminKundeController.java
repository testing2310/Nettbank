package oslomet.testing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.bind.annotation.RequestBody;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class EnhetstestAdminKundeController {
    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleOk(){
        List<Kunde> kunder = new ArrayList<>();
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        kunder.add(enKunde);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKunder()).thenReturn(kunder);
        List result = adminKundeController.hentAlle();
        assertEquals(result,kunder);
    }
    @Test
    public void hentAlleKunde_IkkeOk() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }
    @Test
    public void lagreKundeOk(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(enKunde)).thenReturn("OK");
        String result = adminKundeController.lagreKunde(enKunde);
        assertEquals(result,"OK");
    }
    @Test
    public void lagreKundeFeil(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "0",
                "Asker", "22224444", "HeiHei");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKunde(enKunde)).thenReturn("Feil");
        String result = adminKundeController.lagreKunde(enKunde);
        assertEquals(result,"Feil");
    }
    @Test
    public void lagreKundeIkkeOk(){
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKundeController.lagreKunde(enKunde);

        // assert
        assertEquals(resultat,"Ikke logget inn");
    }
    @Test
    public void test_endreOK() {

        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270","Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Ok");
        String restulat = adminKundeController.endre(kunde1);
        assertEquals("Ok", restulat);
    }
    @Test
    public void test_endreFeil() {

        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "hhh","Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(kunde1)).thenReturn("Feil");
        String restulat = adminKundeController.endre(kunde1);
        assertEquals("Feil", restulat);
    }
    @Test
    public void endreEnKunde_IkkeOk(){
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270","Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);
        String result = adminKundeController.endre(kunde1);
        assertEquals("Ikke logget inn",result);

    }
    @Test
    public void test_slettOK() {
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKunde("01010110523")).thenReturn("OK");

        String resultat = adminKundeController.slett("01010110523");
        assertEquals("OK", resultat);
    }
    @Test
    public void test_slettKundeFeil() {
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.slettKunde("nn")).thenReturn("Feil");

        String resultat = adminKundeController.slett("nn");
        assertEquals("Feil", resultat);
    }

    @Test
    public void test_slettFeil() {
        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.slett("01010110523");
        assertEquals("Ikke logget inn", resultat);
    }



}
