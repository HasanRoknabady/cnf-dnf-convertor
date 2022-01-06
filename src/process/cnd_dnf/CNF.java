package process.cnd_dnf;
import process.TrueFalseHanlder.Relation;
import java.util.ArrayList;

public class CNF
{

    public static String cnfConverter(ArrayList<Relation> literalData, Relation FinalAnswer) {
        StringBuilder converted_cnf = new StringBuilder(" ");
        for (int i = 0; i < FinalAnswer.zero_one_table.length; i++)
        {
            boolean b = false;
            if (FinalAnswer.zero_one_table[i] == 0)
            {
                StringBuilder temp = new StringBuilder("(");
                for (Relation relation : literalData)
                {
                    if (b)
                    {
                        temp.append(" |");
                    }
                    else
                    {
                        b = true;
                    }
                    if (relation.zero_one_table[i] != 1)
                    {
                        temp.append(" ").append(relation.name);
                    } else
                    {
                        temp.append(" ~").append(relation.name);
                    }
                }
                temp.append(" )");
                converted_cnf.append(" & ").append(temp);
            }
        }
        converted_cnf = new StringBuilder(converted_cnf.toString().replace("  & (", " ("));
        if (converted_cnf.toString().equals(" "))
            return "( " + literalData.get(0).name + " | ~" + literalData.get(0).name + " )" + "  *all true case";
        return converted_cnf.toString();
    }
}
