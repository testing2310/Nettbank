package oslomet.testing;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(sjekk.loggetInn()).thenReturn("56789871234");
        Konto konto1 = new Konto("56789871234", "56789087",
                315.40, "Brukskonto", "NOK",null);

        List<Konto> kontoliste = new ArrayList<>();
        kontoliste.add(konto1);

        Mockito.when(adminRepository.hentAlleKonti()).thenReturn(kontoliste);

        List<Konto> resultat2 = adminKontoController.hentAlleKonti();

        assertEquals(kontoliste, resultat2);

    }
    @Test
    public void hentAlleKonti_Feil(){
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        Assertions.assertNull(resultat);
    }
    @Test
    public void registrerOK(){

        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(adminRepository.registrerKonto(konto1)).thenReturn("OK");

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        Assertions.assertEquals("OK", resultat);

        /**
        when(sjekk.loggetInn()).thenReturn(null);
        Konto konto1 = new Konto("5678987", "56789087",
                315.40, "Brukskonto", "NOK",null);

        Mockito.when(adminRepository.registrerKonto(any(Konto.class))).thenReturn("OK");

        String resultat1 = adminKontoController.registrerKonto(konto1);

        assertEquals("OK", resultat1);
         */
    }
    @Test
    public void registrerFeil(){
        when(sjekk.loggetInn()).thenReturn("56789871234");
        Konto konto1 = new Konto("56789871234", "56789087",
                315.40, "Brukskonto", "NOK",null);

        Mockito.when(adminRepository.registrerKonto(any(Konto.class))).thenReturn("Feil");

        String resultat1 = adminKontoController.registrerKonto(konto1);

        assertEquals("Feil", resultat1);
    }
    @Test
    public void endreOK(){
        when(sjekk.loggetInn()).thenReturn("56789871234");
        Konto konto1 = new Konto("56789871234", "56789087",
                315.40, "Brukskonto", "NOK",null);

        Mockito.when(adminRepository.endreKonto(any(Konto.class))).thenReturn("OK");

        String resultat11 = adminKontoController.endreKonto(konto1);

        assertEquals ("OK", resultat11);
    }
    @Test
    public void endreFeil(){
        when(sjekk.loggetInn()).thenReturn("56789871234");
        Konto konto1 = new Konto("56789871234", "56789087",
                315.40, "Brukskonto", "NOK",null);

        Mockito.when(adminRepository.endreKonto(any(Konto.class))).thenReturn("Feil");

        String resultat11 = adminKontoController.endreKonto(konto1);

        assertEquals ("Feil", resultat11);
    }
    @Test
    public void slettOk(){
        when(sjekk.loggetInn()).thenReturn("56789871234");
        Mockito.when(adminRepository.slettKonto("56789871234")).thenReturn("OK");

        String resultat3 = adminKontoController.slettKonto("56789871234");

        assertEquals("OK", resultat3);
    }
    @Test
    public void slettFeil(){
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.slettKonto(konto1.getKontonummer());

        // assert
        Assertions.assertEquals("Ikke innlogget", resultat);
    }
}
