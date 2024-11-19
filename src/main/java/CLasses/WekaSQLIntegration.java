package CLasses;

import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class WekaSQLIntegration {
    public static Instances getDataFromSQL() {
        try {
            InstanceQuery query = new InstanceQuery();
            query.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/?user=root");
            query.setUsername("root");
            query.setPassword("1234");
            query.setQuery("SELECT * FROM Produto");
            Instances data = query.retrieveInstances();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
