package ToDo.lang;

import java.util.List;
import java.util.stream.Collectors;

class LangService {

    private LangRepository repository;

    LangService(){
        this(new LangRepository());
    }

    public LangService(LangRepository repository)
    {
        this.repository=repository;
    }

    public List<LangDTO> findAll(){
        return repository.findAll().
                stream()
                .map(LangDTO::new)
                .collect(Collectors.toList());
    }
}
