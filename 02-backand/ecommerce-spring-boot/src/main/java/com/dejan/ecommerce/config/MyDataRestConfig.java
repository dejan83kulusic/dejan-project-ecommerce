package com.dejan.ecommerce.config;

import com.dejan.ecommerce.entity.*;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private final EntityManager entityManager;

    public MyDataRestConfig(EntityManager entityManager, String[] alowedOrigins) {
        this.entityManager = entityManager;
        this.alowedOrigins = alowedOrigins;
    }


    @Value("${allowed.origins}")
    private String[] alowedOrigins;


    public void configureRepositoryRestConfiguration(@NotNull RepositoryRestConfiguration config, CorsRegistry cors ) {

       HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST,
                                            HttpMethod.DELETE, HttpMethod.PATCH};

        disableHttpMetodes(Product.class,config, theUnsupportedActions);
        disableHttpMetodes(ProductCategory.class,config, theUnsupportedActions);
        disableHttpMetodes(Country.class,config, theUnsupportedActions);
        disableHttpMetodes(State.class,config, theUnsupportedActions);
        disableHttpMetodes(Order.class,config, theUnsupportedActions);

        // call an internal helper method
       exposeIds(config);
       cors.addMapping(config.getBasePath() +"/**").allowedOrigins(alowedOrigins);
    }

    private void disableHttpMetodes(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
               .forDomainType(theClass)
               .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }


    private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }


}

















