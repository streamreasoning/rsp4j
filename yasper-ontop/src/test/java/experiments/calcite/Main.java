package experiments.calcite;

import java.util.Arrays;

/**
 * Created by riccardo on 02/09/2017.
 */
public class Main {


    public static void main(String[] args) {

        Object o = new Employee(0, "Riccardo");

        Arrays.stream(o.getClass().getFields()).forEach(field -> System.out.println(field.getName()));
    }
}

