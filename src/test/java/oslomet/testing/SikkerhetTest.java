package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SikkerhetTest {
    @InjectMocks
    private Sikkerhet sikkerhet;       // klassen som skal testes

    @Mock
    private BankRepository rep;        // klassen som skal mockes

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;


    /**
     *  @Before annotation to specify that the initSession method should be run before each test case.
     *
     * The method creates a Map object called attributes that will be used to store key-value pairs corresponding to the attributes in the session.
     *
     * The code then sets up two behaviors for the mock session object using the doAnswer method from the Mockito framework.
     *
     * The first behavior is for the getAttribute method of the session. The doAnswer method is used to provide a custom implementation for this method that returns the value corresponding to the passed key from the attributes map.
     *
     * The second behavior is for the setAttribute method of the session. The doAnswer method is used to provide a custom implementation for this method that stores the passed key-value pair in the attributes map.
     *
     * Finally, the when method is used to specify that the custom implementation should be used for any call to getAttribute or setAttribute with any string argument for the key and any argument for the value.
     */
    @Before
    public void initSession(){
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());

    }



    // Tester sjekk LoggInn (Logget Inn - OK)
    @Test
    public void test_sjekkLoggInnLoggetInnOK() {
        // arrange
        when(rep.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");

        // act
        String resultat = sikkerhet.sjekkLoggInn("12345678901",
                "HeiHei");

        // assert
        assertEquals("OK", resultat);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Personnummer Eller Passord)
    @Test
    public void sjekkLoggInn_FeilPersonnummerEllerPassord() {
        // arrange
        when(rep.sjekkLoggInn(anyString(),anyString())).thenReturn(
                "Feil i personnummer eller passord");

        // act
        String resultat = sikkerhet.sjekkLoggInn("12345678905",
                "HeiHeiHei");

        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }


    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Med Regex Personnummer)
    @Test
    public void test_sjekkLoggInnFeilMedRegexPersonnummer() {
        // act
        // Kort Personnummer
        String resultat1 = sikkerhet.sjekkLoggInn("12345",
                "HeiHei");

        // Langt Personnummer
        String resultat2 = sikkerhet.sjekkLoggInn("12345678901234567890",
                "HeiHei");

        // Symbol/tegn som Personnummer
        String resultat3 = sikkerhet.sjekkLoggInn("//%%$$##__()",
                "HeiHei");

        // Bokstaver som Personnummer
        String resultat4 = sikkerhet.sjekkLoggInn("DuErKul",
                "HeiHei");

        // assert
        assertEquals("Feil i personnummer", resultat1);
        assertEquals("Feil i personnummer", resultat2);
        assertEquals("Feil i personnummer", resultat3);
        assertEquals("Feil i personnummer", resultat4);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Med Regex Passord)
    @Test
    public void test_sjekkLoggInnFeilMedRegexPassord() {
        // act
        // Tomt Passord
        String resultat1 = sikkerhet.sjekkLoggInn("12345678901",
                "");

        // For langt Passord
        String resultat2 = sikkerhet.sjekkLoggInn("12345678901",
                "HeiDuErKul1234567891234567890123456");

        // For kort Passord
        String resultat3 = sikkerhet.sjekkLoggInn("12345678901",
                "Du1");

        // assert
        assertEquals("Feil i passord", resultat1);
        assertEquals("Feil i passord", resultat2);
        assertEquals("Feil i passord", resultat3);
    }


    /***** Logg ut *****/

    @Test
    public void LoggUt(){
        //arrange
        session.setAttribute("Innlogget", null);
        sikkerhet.loggUt();

        //act
        String resultat = sikkerhet.loggetInn();

        //assert
        assertNull(resultat);
    }


    /***** LoggInn Admin *****/

    // Tester LoggInn Admin (Logget Inn - OK)
    @Test
    public void test_LoggInnAdminLoggetInnOK(){
        // arrange
        session.setAttribute("Innlogget", "Admin");

        // act
        String resultat = sikkerhet.loggInnAdmin("Admin","Admin");

        // assert
        assertEquals("Logget inn", resultat);
    }

    // Tester LoggInn Admin (Ikke Logget Inn - Feil)
    @Test
    public void test_LoggInnAdminIkkeLoggetInn(){
        // arrange
        session.setAttribute("Innlogget", null);

        // act
        String resultat = sikkerhet.loggInnAdmin("","");
        // assert
        assertEquals("Ikke logget inn", resultat);

    }


    /***** Logget Inn *****/

    // Tester Logget Inn (Logget Inn - OK)
    @Test
    public void test_LoggetInnLoggetInnOK(){
        // arrange
        session.setAttribute("Innlogget", "12345678901");

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertEquals("12345678901", resultat);
    }

    // Tester Logget Inn (Logget Inn - Feil)
    @Test
    public void test_LoggetInnIkkeLoggetInn(){
        // arrange
        session.setAttribute(null, null);

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertNull(resultat);
    }
}