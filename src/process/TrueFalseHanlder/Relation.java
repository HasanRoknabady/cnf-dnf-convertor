package process.TrueFalseHanlder;

import java.util.Arrays;

public class Relation {
    public Relation(String name, int[] zero_one_table) {
        this.name = name;
        this.zero_one_table = zero_one_table;
    }

    public String name;
    public int[] zero_one_table;

    public void truth_table_printer() {
        System.out.print("         | " + name + " =: ");
        Arrays.stream(zero_one_table).forEach(i ->
        {
            if (i == 0)
                System.out.print("0 ");
            else
                System.out.print("1 ");
        });
        System.out.print("|");
        System.out.println();
    }

    public static Relation handler(Relation p, Relation q, Relation func)
    {
        if (func.name.equals("~"))
        {
            return negation(p);
        }
        if (func.name.equals("&"))
        {
            return and_func(p, q);
        }
        if (func.name.equals("|"))
        {
            return or_func(p, q);
        }
        if (func.name.equals("->"))
        {
            return eq_func(p, q);
        }
        if (func.name.equals("<->"))
        {
            return conditional(p, q);
        }
        return null;
    }

    //this is for : &
    static Relation and_func(Relation a, Relation b) {
        int[] tmp = new int[a.zero_one_table.length];
        for (int i = 0; i < a.zero_one_table.length; i++) {
            tmp[i] = a.zero_one_table[i] * b.zero_one_table[i];
        }
        return new Relation(a.name + "&" + b.name, tmp);
    }

    //this is for : |(or)
    static Relation or_func(Relation a, Relation b) {
        int[] tmp = new int[a.zero_one_table.length];
        for (int i = 0; i < a.zero_one_table.length; i++) {
            int p = a.zero_one_table[i] + b.zero_one_table[i];
            if (p == 2)
                p = 1;
            tmp[i] = p;
        }
        return new Relation(a.name + "|" + b.name, tmp);
    }

    // : ->
    static Relation eq_func(Relation a, Relation b) {
        int[] temp = new int[a.zero_one_table.length];
        for (int i = 0; i < a.zero_one_table.length; i++) {
            int p = a.zero_one_table[i];
            int q = b.zero_one_table[i];
            int r = -1;
            if (p == 1 && q == 0)
                r = 0;
            else if (p == 1 && q == 1)
                r = 1;
            else if (p == 0 && q == 1)
                r = 1;
            else if (p == 0 && q == 0)
                r = 1;
            temp[i] = r;
        }
        return new Relation(a.name + "->" + b.name, temp);
    }

    // : <->
    static Relation conditional(Relation a, Relation b) {
        int[] tmp = new int[a.zero_one_table.length];
        for (int i = 0; i < a.zero_one_table.length; i++) {
            int p = a.zero_one_table[i];
            int q = b.zero_one_table[i];
            int t = -1;
            if (p == 1 && q == 0)
                t = 0;
            else if (p == 1 && q == 1)
                t = 1;
            else if (p == 0 && q == 1)
                t = 0;
            else if (p == 0 && q == 0)
                t = 1;
            tmp[i] = t;
        }
        return new Relation(a.name + "<->" + b.name, tmp);
    }

    // : ~
    static Relation negation(Relation a) {
        int[] tmp = new int[a.zero_one_table.length];
        for (int i = 0; i < a.zero_one_table.length; i++) {
            if (a.zero_one_table[i] == 1)
                tmp[i] = 0;
            else
                tmp[i] = 1;
        }
        return new Relation("~" + a.name, tmp);
    }
}