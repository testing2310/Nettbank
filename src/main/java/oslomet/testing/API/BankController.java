package oslomet.testing.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.List;

@RestController
public class BankController {

    @Autowired
    BankRepository repository;

    @Autowired
    Sikkerhet sjekk;

    @GetMapping("/hentTransaksjoner")
    public Konto hentTransaksjoner(String kontoNr, String fraDato, String tilDato) {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            return repository.hentTransaksjoner(kontoNr, fraDato, tilDato);
        }
        return null;
    }

    @GetMapping("/hentKonti")
    public List<Konto> hentKonti() {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            return repository.hentKonti(personnummer);
        }
        return null;
    }

    @GetMapping("/hentSaldi")
    public List<Konto> hentSaldi() {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            return repository.hentSaldi(personnummer);
        }
        return null;
    }

    @PostMapping("/registrerBetaling")
    public String registrerBetaling(@RequestBody Transaksjon betaling) {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            return repository.registrerBetaling(betaling);
        }
        return null;
    }

    @GetMapping("/hentBetalinger")
    public List<Transaksjon> hentBetalinger() {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            return repository.hentBetalinger(personnummer);
        }
        return null;
    }

    @GetMapping("/utforBetaling")
    public List<Transaksjon> utforBetaling(int txID) {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            if (repository.utforBetaling(txID).equals("OK")) {
                return repository.hentBetalinger(personnummer);
            }
        }
        return null;
    }

    @GetMapping("/hentKundeInfo")
    public Kunde hentKundeInfo() {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            Kunde kunde = repository.hentKundeInfo(personnummer);
            return kunde;
        }
        return null;
    }

    @PostMapping("/endreKundeInfo")
    public String endre(Kunde innKunde) {
        String personnummer = sjekk.loggetInn();
        if (personnummer != null) {
            innKunde.setPersonnummer(personnummer);
            return repository.endreKundeInfo(innKunde);
        }
        return null;
    }

    @RestController
    @RequestMapping("/adminKunde")
    public static class AdminKundeController {
        @Autowired
        AdminRepository repository;

        @Autowired
        private Sikkerhet sjekk;

        @GetMapping("/hentAlle")
        public List<Kunde> hentAlle() {
            String personnummer = sjekk.loggetInn();
            if (personnummer != null) {
                return repository.hentAlleKunder();
            }
            return null;
        }

        @PostMapping("/lagre")
        public String lagreKunde(@RequestBody Kunde innKunde) {
            String personnummer = sjekk.loggetInn();
            if (personnummer != null) {
                return repository.registrerKunde(innKunde);
            }
            return "Ikke logget inn";
        }

        @PostMapping("/endre")
        public String endre(@RequestBody Kunde innKunde) {
            String personnummer = sjekk.loggetInn();
            if (personnummer != null) {
                return repository.endreKundeInfo(innKunde);
            }
            return "Ikke logget inn";
        }

        @GetMapping("/slett")
        public String slett(String personnummer) {
            String p = sjekk.loggetInn();
            if (p != null) {
                return repository.slettKunde(personnummer);
            }
            return "Ikke logget inn";
        }
    }
}
