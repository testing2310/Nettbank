package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void registrerKonti_LoggetInn() {
        Konto konto = new Konto("01029624748", "123456789", 1231, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("Innlogget");
        when(adminRepository.registrerKonto(konto)).thenReturn("OK");
        String res = adminKontoController.registrerKonto(konto);

        assertEquals("OK", res);
    }

    @Test
    public void registrerKonti_IkkeLoggetInn() {
        Konto konto = new Konto("01029624748", "123456789", 1231, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn(null);
        String res = adminKontoController.registrerKonto(konto);

        assertEquals("Ikke innlogget", res);
    }
    @Test
    public void hentAlleKonti_OK(){

    }
    @Test
    public void hentAlleKonti_Feil(){

    }
    @Test
    public void registrerOK(){

    }
    @Test
    public void registrerFeil(){

    }
    @Test
    public void endreOK(){

    }
    @Test
    public void endreFeil(){

    }
    @Test
    public void slettOk(){

    }
    @Test
    public void slettFeil(){

    }
}
