
//import sample.dataBase.BDConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MaxWeightData {
      private LinkedHashMap<String,Double> data;

    public MaxWeightData( ArrayList<String> exNames){
        data = new LinkedHashMap<>();
        int i = 0;
        for (String name: exNames ) {
            if(true
                    //check if this exercire alredy exist
                    )
            data.put(exNames.get(i++),new Double(0));
        }
//        String sql = "SELECT * FROM public.\"MaxWeights\" WHERE \"user_id\"="+user.getID()+";";
//        try {
//            ResultSet resultSet= BDConnection.createSelectQuery(sql);
//            resultSet.next();
//            for (int i = 0; i < TOTAL; i++) {
//              data.put(exNames.get(i),resultSet.getDouble("ex"+(i+1)));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

    public LinkedHashMap<String, Double> getData() {
        return data;
    }


}
