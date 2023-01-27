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

import static org.junit.Assert.assertEquals;
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

        Mockito.when(sjekk.loggetInn()).thenReturn("Admin");
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

        Mockito.when(sjekk.loggetInn()).thenReturn("Admin");
        Mockito.when(repository.registrerKunde(kunde1)).thenReturn("Feil");

        // act

        String resultat = kundeController.lagreKunde(kunde1); //
        // assert
        assertEquals("Feil", resultat);
    }

}