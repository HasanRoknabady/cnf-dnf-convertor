package process;
import process.TrueFalseHanlder.Relation;
import java.util.*;
import static process.cnd_dnf.CNF.cnfConverter;
import static process.cnd_dnf.DNF.dnfConverter;
import static table.Table.print;

public class Calculator
{
    static Stack<Relation> main_data = new Stack<>();
    public static String original_data;

    public static String process()
    {
        print();

        String propositional_form = new Scanner(System.in).nextLine();
        System.out.println("\n          *TRUTH TABLE*");
        original_data = propositional_form;
        ArrayList<String> characters = new ArrayList<>();
        StringTokenizer splinter = new StringTokenizer(propositional_form, " )(<>-~&|");
        while (true)
        {
            if (!splinter.hasMoreTokens()) break;
            String temp = splinter.nextToken();
            if (!characters.contains(temp))
            {
                characters.add(temp);
            }
        }
        int row = (int) Math.pow(2, characters.size());
        int col = characters.size();
        int[][] zero_one_table = new int[row][col];
        for (int i = 0; i < row; i++) {
            String temp = Integer.toBinaryString(i);
            while (temp.length() < characters.size())
            {
                temp = "0" + temp;
            }
            for (int j = 0; j < col; j++)
            {
                zero_one_table[i][j] = temp.toCharArray()[j] - 48;
            }
        }

        ArrayList<Relation> char_with_truthTableChar = new ArrayList<>();
        for (int i = 0; i < col; i++) {
            int[] tempTF = new int[(int) Math.pow(2, characters.size())];
            for (int j = 0; j < (int) Math.pow(2, characters.size()); j++) {
                tempTF[j] = zero_one_table[j][i];
            }
            char_with_truthTableChar.add(new Relation(characters.get(i), tempTF));
        }
        for (Relation literal : char_with_truthTableChar) {
            literal.truth_table_printer();
        }

        propositional_form = replace(propositional_form);
        splinter = new StringTokenizer(propositional_form, " ");

        while (true) {
            if (!splinter.hasMoreTokens()) break;
            String temp = splinter.nextToken();
            if (temp.equals("&"))
            {
                main_data.push(new Relation("&", null));
            }
            else if (temp.equals("|"))
            {
                main_data.push(new Relation("|", null));
            }
            else if (temp.equals("->"))
            {
                main_data.push(new Relation("->", null));
            }
            else if (temp.equals("<->"))
            {
                main_data.push(new Relation("<->", null));
            }
            else if (temp.equals("~"))
            {
                main_data.push(new Relation("~", null));

            }
            else if (temp.equals(")"))
            {
                Relation b = main_data.pop();
                Relation func = main_data.pop();
                if (func.name.equals("~")) {
                    Relation ans = Relation.handler(b, null, func);
                    ans.truth_table_printer();
                    main_data.push(ans);
                } else {
                    Relation a = main_data.pop();
                    Relation ans = Relation.handler(a, b, func);
                    ans.truth_table_printer();
                    main_data.push(ans);
                }
            } else {
                for (Relation literal : char_with_truthTableChar)
                {
                    if (temp.equals(literal.name))
                    {
                        main_data.push(literal);
                        break;
                    }
                }
            }
        }
        Relation FinalAnswer = main_data.pop();

        return dnfConverter(char_with_truthTableChar, FinalAnswer)
                + "Hasan_Roknabady-Logic-99222042"
                + cnfConverter(char_with_truthTableChar, FinalAnswer);

    }

    private static String replace(String data)
    {
        data = data.replace("(", " ( ");
        data = data.replace(")", " )");
        data = data.replace("~", " ~ ");
        data = data.replace("&", " & ");
        data = data.replace("|", " | ");
        data = data.replace("<->", " <-> ");
        data = data.replace("->", " -> ");
        return data;
    }

}
