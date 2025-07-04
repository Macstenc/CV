//package pl.kul.Sklep.Config;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//
//@Configuration
//public class MongoConfig {
//
//    @Bean
//    public MongoClient mongoClient() {
//        return MongoClients.create("mongodb+srv://<admin>:<admin123>@shop.l2uqq.mongodb.net/Sklep?retryWrites=true&w=majority");
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        return new MongoTemplate(mongoClient(), "Sklep");
//    }
//}
