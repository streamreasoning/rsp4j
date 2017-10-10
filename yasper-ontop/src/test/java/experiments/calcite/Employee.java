package experiments.calcite;

/**
 * Created by riccardo on 09/09/2017.
 */

/**
 * Object that will be used via reflection to create the "emps" table.
 */
public class Employee {
    public final Integer empid;
    public final String name;

    public Employee(int empid, String name) {
        this.empid = empid;
        this.name = name;
    }
}