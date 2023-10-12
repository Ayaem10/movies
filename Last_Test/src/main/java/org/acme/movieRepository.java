package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.text.WordUtils;


import java.util.List;
@ApplicationScoped
public class movieRepository implements PanacheRepository<movie>{
    public List<movie> findByCountry(String country) {
        return this.list("country" , country);
    }

    public List<movie> findByTitle(String title){
        return this.list("title" , title);

    }
}
