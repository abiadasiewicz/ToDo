package ToDo.hello;

import ToDo.lang.Lang;
import ToDo.lang.LangRepository;
import org.junit.Test;

import java.util.Optional;
import static org.junit.Assert.assertEquals;

public class HelloServiceTest {
private final static String WELCOME = "Hello";
private final static String FALLBACK_ID_WELCOME = "Hola";

    @Test
    public void test_nullName_prepareGreeting_returnsFallbackName() throws Exception {
        //given
        var WELCOME = "Hello";
        var mocRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mocRepository);
        String name = null;

        //when
        var result = SUT.prepareGreeting(null,"-1");

        //then
        assertEquals(WELCOME+" "+ HelloService.FALLBACK_NAME+"!", result);
    }

    @Test
    public void test_name_prepareGreeting_returnsGreetingWithName() throws Exception {
        //given
        var mocRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mocRepository);
        String name = "test";

        //when
        var result = SUT.prepareGreeting(name, "-1");

        //then
        assertEquals(WELCOME+" "+name+"!", result);
    }
    @Test
    public void test_nullLang_prepareGreeting_returnsGreetingWithFallbackIdLang() throws Exception {
        //given
        var mocRepository = fallbackLangIdRepository();

        var SUT = new HelloService(mocRepository);
        //String name = "test";

        //when
        var result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELCOME+" "+HelloService.FALLBACK_NAME+"!", result);
    }
    @Test
    public void test_texLang_prepareGreeting_returnsGreetingWithFallbackIdLang() throws Exception {
        //given
        var mocRepository = fallbackLangIdRepository();

        var SUT = new HelloService(mocRepository);
        //String name = "test";

        //when
        var result = SUT.prepareGreeting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELCOME+" "+HelloService.FALLBACK_NAME+"!", result);
    }

    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang()
    {
        var mocRepository = new LangRepository(){
            Optional<Lang> findById(Long id)
            {
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mocRepository);

        var result = SUT.prepareGreeting(null, "-1");

        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg()+" "+HelloService.FALLBACK_NAME+"!",result);
    }

    private LangRepository fallbackLangIdRepository() {
        return new LangRepository(){
            @Override
            public Optional<Lang> findById(Integer id)
            {
               if(id.equals(HelloService.FALLBACK_LANG.getId()))
               {
                   return Optional.of(new Lang(null,FALLBACK_ID_WELCOME, null));
               }
               return Optional.empty();
            }
        };
    }

    private LangRepository alwaysReturningHelloRepository() {
       return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELCOME, null));
            }
        };

    }
}
