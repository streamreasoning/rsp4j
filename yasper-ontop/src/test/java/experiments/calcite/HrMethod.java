package experiments.calcite;

/**
 * Created by riccardo on 09/09/2017.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Object that will be used via reflection to create the "hr" schema.
 */
public class HrMethod {

    private Set<Employee> emps2;

    public Employee[] emps2() {
        return emps2 != null ? emps2.toArray(new Employee[emps2.size()]) : null;
    }

    public HrMethod(List<Employee> objects) {
        this.emps2 = new HashSet<>(objects);
    }
}