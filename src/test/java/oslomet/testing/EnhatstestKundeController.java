package oslomet.testing;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class EnhatstestKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController kundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Test
    public void test_registrereKundeOK() {

        // arrage
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        // act
        String resultat = kundeController.lagreKunde(kunde1); // husk å bruke denne Controlleren, ikke opprett en ny!

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_registrereKundeFeil() {

        // arrage
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Feil");

        // act
        String resultat = kundeController.lagreKunde(kunde1); // husk å bruke denne Controlleren, ikke opprett en ny!

        // assert
        assertEquals("OK", resultat);
    }
}