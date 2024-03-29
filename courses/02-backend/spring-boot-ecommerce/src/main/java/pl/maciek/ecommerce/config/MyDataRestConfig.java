package pl.maciek.ecommerce.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import pl.maciek.ecommerce.entity.Country;
import pl.maciek.ecommerce.entity.Product;
import pl.maciek.ecommerce.entity.ProductCategory;
import pl.maciek.ecommerce.entity.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupporetedAction = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};


        disableHttpMethods(Product.class,config, theUnsupporetedAction);
        disableHttpMethods(ProductCategory.class,config, theUnsupporetedAction);
        disableHttpMethods(Country.class,config, theUnsupporetedAction);
        disableHttpMethods(State.class,config, theUnsupporetedAction);

        exposeIds(config);

    }

    private void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupporetedAction) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupporetedAction))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupporetedAction));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
