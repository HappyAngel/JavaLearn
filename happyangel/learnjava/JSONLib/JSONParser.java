package happyangel.learnjava.JSONLib;

/**
 * Created by Lei on 2016/10/6.
 *
 * inspired by miloyip's JSON tutorial
 */

enum JSONType {
    NULL,
    TRUE,
    FALSE
}

class JSONValue {
    public JSONType type;
}

public class JSONParser {

    public JSONValue parse(String str) {
        assert !str.isEmpty();
        str = parseWhiteSpace(str);
        return parseValue(str);
    }

    private String parseWhiteSpace(String str) {
        return str;
    }

    private JSONValue parseValue(String str) {
        JSONValue result = new JSONValue();
        result.type = JSONType.NULL;
        return result;
    }
}
