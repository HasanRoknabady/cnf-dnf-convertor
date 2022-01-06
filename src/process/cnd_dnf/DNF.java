package process.cnd_dnf;
import process.TrueFalseHanlder.Relation;
import java.util.ArrayList;

public class DNF
{

    public static String dnfConverter(ArrayList<Relation> literalData, Relation FinalAnswer)
    {
        StringBuilder converted_dnf = new StringBuilder(" ");
        for (int i = 0; i < FinalAnswer.zero_one_table.length; i++)
        {
            boolean b = false;
            if (FinalAnswer.zero_one_table[i] == 1)
            {
                StringBuilder tmp = new StringBuilder("(");
                for (Relation relation : literalData)
                {
                    if (b)
                    {
                        tmp.append(" &");
                    } else
                    {
                        b = true;
                    }
                    if (relation.zero_one_table[i] == 1)
                        tmp.append(" ").append(relation.name);
                    else
                        tmp.append(" ~").append(relation.name);
                }
                tmp.append(" )");
                converted_dnf.append(" | ").append(tmp);
            }
        }
        converted_dnf = new StringBuilder(converted_dnf.toString().replace("  | (", " ("));
        if (converted_dnf.toString().equals(" "))
            return "( " + literalData.get(0).name + " & ~" + literalData.get(0).name + " )" + "  *all false case";
        return converted_dnf.toString();
    }
}
