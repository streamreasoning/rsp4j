package experiments.calcite;

/**
 * Created by riccardo on 09/09/2017.
 */

import java.util.List;

/**
 * Object that will be used via reflection to create the "hr" schema.
 */
public class HrList {

    public List<Employee> emps1;

    public HrList(List<Employee> emps2) {
        this.emps1 = emps2;
    }
}