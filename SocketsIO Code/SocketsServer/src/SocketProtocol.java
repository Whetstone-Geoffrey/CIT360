import org.json.JSONObject;

import java.io.IOException;

public class SocketProtocol {
    private static final int WAITING = 0;
    private static final int SENTCONNECTED = 1;
    private static final int SENTRESULT = 2;
    private static final int ANOTHER = 3;
    private int state = WAITING;

    private String country = "";
    private String city = "";

    public String processInput(String theInput) {
        String theOutput = null;
        JSONObject json = null;

        if (state == WAITING) {
            theOutput = "You've successfully connected to the server!";
            state = SENTCONNECTED;
        } else if (state == SENTCONNECTED) {
            String[] aInput = theInput.split("%");

            country = aInput[0];
            city = aInput[1];

            try {

                json = Example.getExample(country, city);
                JSONObject currObs = (JSONObject) json.get("example_json");

                theOutput = "Example: " + currObs.get("example")
                        + "\nItem1: " + currObs.get("item1_string")
                        + "\nItem2: " + currObs.get("item2_string")
                        + "\nItem3: " + currObs.get("item4_string")
                        + "\nItem5: " + currObs.get("item5_string")

                state = SENTRESULT;

            } catch (IOException e) {
                theOutput = e.getMessage();
            }
        } else {
            theOutput = "Closed";
        }

        return theOutput;
    }
}
