package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * Klasa w ktorej tworzymy polaczenie do bazy danych
 */
public class DB {
    /**
     * Konstruktor domyslny klasy DB
     */
    public DB() {
    }
    /**
     * Metoda statyczna zwracajaca instancje klasy DB
     * @return zwracana instancja klasy DB
     */
    public static DB getInstance(){
        return new DB();
    }
    /**
     * Metoda zwracajaca obiekt klasy EntityManager, w ktorym zdefiniowane jest polaczenie do bazy danych
     * @return zwracana jest instancja obiektu klasy EntityManager, w ktorym zdefiniowane jest polaczenie do bazy danych
     */
    public EntityManager getConnection(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }
}
